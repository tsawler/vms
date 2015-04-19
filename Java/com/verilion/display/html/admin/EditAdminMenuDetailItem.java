//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-06
//Revisions
//------------------------------------------------------------------------------
//$Log: EditAdminMenuDetailItem.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/10/06 18:59:34  tcs
//Added a field for id
//
//Revision 1.1  2004/10/06 18:15:33  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.AdminMenu;
import com.verilion.database.PageTemplate;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit an admin menu item detail entry
 * 
 * <P>
 * October 6 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class EditAdminMenuDetailItem extends SecurePage {

   private static final long serialVersionUID = 8775379941702717200L;

   public void BuildPage(HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {

      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      String theMenu = "";
      String admin_menu_item_detail_id = "";

      try {
         // get parameters
         admin_menu_item_detail_id = (String) request.getParameter("id");

         AdminMenu myOldData = new AdminMenu();
         myOldData.setConn(conn);
         myOldData.setAdmin_menu_item_detail_id(admin_menu_item_detail_id);
         drs = myOldData.getAdminMenuItemDetailById();

         while (drs.next()) {
            // create drop down list pages
            PageTemplate myPage = new PageTemplate();
            myPage.setConn(conn);
            crs = myPage.getAllPageNamesIds();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "page_id",
                  drs.getInt("page_id"),
                  crs,
                  "page_id",
                  "page_name",
                  0,
                  "Does not link");

            MasterTemplate.searchReplace("$LINKSTO$", theMenu);

            // create a drop down list of parent items
            AdminMenu myAdminMenu = new AdminMenu();
            myAdminMenu.setConn(conn);
            crs = myAdminMenu.getAdminMenuItemsNamesIds();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "parent_id",
                  drs.getInt("admin_menu_items_id"),
                  crs,
                  "admin_menu_items_id",
                  "admin_menu_items_name",
                  0,
                  "Top level item");

            MasterTemplate.searchReplace("$PARENTITEM$", theMenu);

            MasterTemplate.searchReplace("$NAME$", drs
                  .getString("admin_menu_item_detail_name"));
            
            MasterTemplate.searchReplace("$ID$", admin_menu_item_detail_id);
         }
      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("EditAdminMenuDetailItem:BuildPage:Exception:"
               + e.toString());
      }
   }
}