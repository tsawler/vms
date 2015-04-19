//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-02
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessAdminMenu.java,v $
//Revision 1.3.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.3  2004/11/30 16:35:13  tcs
//Changed generated url for new single servlet methodlogy
//
//Revision 1.2  2004/11/03 14:19:20  tcs
//Changed publish/unpublish in html to conform to performaction standard
//
//Revision 1.1  2004/11/02 15:24:11  tcs
//In progress
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.AdminMenu;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Provides list of admin menu items
 * 
 * <P>
 * November 02, 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessAdminMenu extends ProcessPage {

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      XDisconnectedRowSet drs = new XDisconnectedRowSet();

      AdminMenu myAdminMenu = new AdminMenu();
      myAdminMenu.setConn(conn);

      try {
         crs = myAdminMenu.getAdminMenuItemsForView();

         int counter = 1;

         MasterTemplate.getRecordSet(
               "<!-- RecordSet -->",
               "<!-- /RecordSet -->");

         while (crs.next()) {
            MasterTemplate.searchReplaceRecordSet("$ROWCOLOR$", "#ddd");
            MasterTemplate.searchReplaceRecordSet("$MENUID$", crs
                  .getInt("admin_menu_items_id")
                  + "");
            MasterTemplate.searchReplaceRecordSet("$COUNTER$", counter + "");
            MasterTemplate.searchReplaceRecordSet("$NAME$", crs
                  .getString("admin_menu_items_name"));
            if (crs.getString("admin_menu_items_active_yn").equals("y")) {
               MasterTemplate
                     .searchReplaceRecordSet(
                           "$PUBLISHED$",
                           "<a "
                                 + "class=\"pub\" "
                                 + "title=\"Click to unpublish\" href=\"/AdminMenuItemChoose/"
                                 + "performaction/1/class/AdminMenu/admin/1/content.content.do?count=1&amp;"
                                 + "action_1=1&amp;cancelPage=/AdminMenuItemChoose/adminmenu/1/content.do"
                                 + "&amp;action=2&amp;item_id_1="
                                 + crs.getString("admin_menu_items_id")
                                 + "\">published</a>");
            } else {
               MasterTemplate
                     .searchReplaceRecordSet(
                           "$PUBLISHED$",
                           "<a "
                                 + "class=\"upub\" "
                                 + "title=\"Click to publish\" href=\"/AdminMenuItemChoose/"
                                 + "performaction/1/class/AdminMenu/admin/1/content.do?count=1&amp;"
                                 + "action_1=1&amp;cancelPage=/AdminMenuItemChoose/adminmenu/1/admin/1/content.do"
                                 + "&amp;action=1&amp;item_id_1="
                                 + crs.getString("admin_menu_items_id")
                                 + "\">unpublished</a>");
            }
            AdminMenu myMenuItems = new AdminMenu();
            myMenuItems.setConn(conn);
            myMenuItems.setAdmin_menu_items_id(crs
                  .getString("admin_menu_items_id"));
            drs = myMenuItems.getAdminMenuItemsDetailForView();

            String sRow = "";
            while (drs.next()) {
               sRow += "<tr style=\"background: #eee;\"><td>&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;"
                     + "<a href=\"/servlet/com.verilion.display.html.admin.EditAdminMenuDetailItem?"
                     + "page=EditAdminMenuDetailItem&id="
                     + drs.getString("admin_menu_item_detail_id")
                     + "&mid="
                     + drs.getString("admin_menu_items_id")
                     + "\">"
                     + drs.getString("admin_menu_item_detail_name")
                     + "</a></td>";
               if (drs.getString("admin_menu_item_detail_active_yn")
                     .equals("y")) {
                  sRow += "<td><a "
                        + "class=\"pub\" "
                        + "title=\"Click to unpublish\" href=\"/AdminMenuItemChoose/"
                        + "performaction/1/class/AdminMenuDetail/admin/1/content.do?count=1&amp;"
                        + "action_1=1&amp;cancelPage=/AdminMenuItemChoose/adminmenu/1/admin/1/content.do"
                        + "&amp;action=2&amp;item_id_1="
                        + drs.getString("admin_menu_item_detail_id")
                        + "\">published</a></td></tr>";
               } else {
                  sRow += "<td><a "
                        + "class=\"upub\" "
                        + "title=\"Click to publish\" href=\"/AdminMenuItemChoose/"
                        + "performaction/1/class/AdminMenuDetail/admin/1/content.do?count=1&amp;"
                        + "action_1=1&amp;cancelPage=/AdminMenuItemChoose/adminmenu/1/admin/1/content.do"
                        + "&amp;action=1&amp;item_id_1="
                        + drs.getString("admin_menu_item_detail_id")
                        + "\">unpublished</a></td></tr>";
               }
            }
            MasterTemplate.searchReplaceRecordSet("<!-- $ITEM$ -->", sRow);

            MasterTemplate.insertRecordSet();
            counter++;
         }

         MasterTemplate.replaceRecordSet();
         MasterTemplate.searchReplace("$MENUCOUNT$", counter + "");

         crs.close();
         crs = null;
      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.admin.TemplateChoose:BuildPage:Exception:"
                     + e.toString());
      } finally {
         if (crs != null) {
            crs.close();
            crs = null;
         }
      }

      return MasterTemplate;
   }
}