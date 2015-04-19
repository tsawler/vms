//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-08-15
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditMenuItem.java,v $
//Revision 1.1.2.4.6.1.2.3.2.1  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.4.6.1.2.3  2007/02/02 19:18:48  tcs
//Changed to support components linked in menu
//
//Revision 1.1.2.4.6.1.2.2  2007/01/29 16:15:42  tcs
//Added support for collapsible vertical menus, spacers & headings
//
//Revision 1.1.2.4.6.1.2.1  2007/01/28 00:51:44  tcs
//Added support for per menu item access level restrictions
//
//Revision 1.1.2.4.6.1  2006/12/22 15:18:33  tcs
//Added menu_id as a parameter
//
//Revision 1.1.2.4  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.3  2005/08/17 15:15:31  tcs
//Changed the pages that can be linked to to exclude system pages
//
//Revision 1.1.2.2  2005/08/15 17:34:42  tcs
//Complete to first working version
//
//Revision 1.1.2.1  2005/08/15 15:52:44  tcs
//In progress
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Components;
import com.verilion.database.CtAccessLevel;
import com.verilion.database.MenuItem;
import com.verilion.database.MenuItemDetail;
import com.verilion.database.PageTemplate;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit a menu item
 * 
 * @author tsawler
 *  
 */
public class ProcessEditMenuItem extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      ResultSet rs = null;;
      int page_id = 0;
      int component_id = 0;
      String menu_detail_name = "";
      String menu_detail_tooltip = "";
      String menu_item_action = "";
      String menu_item_active_yn = "";
      String menu_item_is_heading = "";
      int menu_item_id = 0;
      int ct_access_level_id = 0;
      int ct_language_id = 1;

      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      String theMenu = "";

      if (((String) request.getParameter("edit")).equals("false")) {
         try {

            // Get the id of the menu item we are going to edit
            menu_item_id = Integer.parseInt((String) request.getParameter("id"));

            MenuItem myMenu = new MenuItem();
            myMenu.setConn(conn);
            myMenu.setMenu_item_id(menu_item_id);

            crs = myMenu.getMenuItemForEdit();

            if (crs.next()) {
               MasterTemplate.searchReplace("$MENUNAME$", crs
                     .getString("menu_item_detail_name"));
               MasterTemplate.searchReplace("$TOOLTIP$", crs
                     .getString("menu_item_detail_tooltip"));
               MasterTemplate.searchReplace("$EXTERNALLINK$", crs
                     .getString("menu_item_action"));
               MasterTemplate.searchReplace("$MENUITEMID$", menu_item_id + "");
               MasterTemplate.searchReplace("$MENUID$", crs.getInt("menu_id") + "");
               if (crs.getString("menu_item_active_yn").equals("y")) {
                  MasterTemplate.searchReplace("$ACTIVEYNY$", " selected ");
                  MasterTemplate.searchReplace("$ACTIVEYNN$", "");
               } else {
                  MasterTemplate.searchReplace("$ACTIVEYNY$", "");
                  MasterTemplate.searchReplace("$ACTIVEYNN$", " selected ");
               }
               if (crs.getString("menu_item_is_heading").equals("y")) {
                  MasterTemplate.searchReplace("$HEADINGYNY$", " selected ");
                  MasterTemplate.searchReplace("$HEADINGYNN$", "");
               } else {
                  MasterTemplate.searchReplace("$HEADINGYNY$", "");
                  MasterTemplate.searchReplace("$HEADINGYNN$", " selected ");
               }
               
               try {
                  component_id = crs.getInt("component_id");
               } catch (Exception e) {
                  component_id = 0;
               }
               
               // handle page menu
               PageTemplate myPage = new PageTemplate();
               myPage.setConn(conn);
               XDisconnectedRowSet drs = new XDisconnectedRowSet();
               myPage.createCustomWhere("and can_link_yn = 'y' ");
               drs = myPage.getActivePageNamesIds();

               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "page_id\" onchange=\"document.roleform.component_id.value=0\"",
                     crs.getInt("page_id"),
                     drs,
                     "page_Id",
                     "page_name",
                     0,
                     "No page selected");
               MasterTemplate.searchReplace("$DDLBPAGE$", theMenu);
               MasterTemplate.searchReplace("$CANCEL$", request
                     .getParameter("cancelPage"));
               
               // handle access level menu
               CtAccessLevel myCtAccessLevel = new CtAccessLevel();
               myCtAccessLevel.setConn(conn);
               drs = myCtAccessLevel.getAllAccessLevelNamesIds();
               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "ct_access_level_id",
                     crs.getInt("ct_access_level_id"),
                     drs,
                     "ct_access_level_id",
                     "ct_access_level_name",
                     0,
                     "Visible for everyone");
               MasterTemplate.searchReplace("$DDLBACCESSLEVELID$", theMenu);
               drs.close();
               drs = null;
               
               // handle component choice menu 
               Components myComponent = new Components();
               myComponent.setConn(conn);
               
               drs = myComponent.getAllComponents();
               
               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "component_id\" onchange=\"document.roleform.page_id.value=0\"",
                     component_id,
                     drs,
                     "component_id",
                     "component_name",
                     0,
                     "Do not link to a component");
               MasterTemplate.searchReplace("$DDLBCOMPONENT$", theMenu);
               
            } else {
               System.out.println("Got no results");
            }
         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditMenuItem:ProcessForm:Exception:"
                  + e.toString());
         } finally {
            if (rs != null) {
               rs.close();
               rs = null;
            }
         }
      } else {

         String theMsg = "";

         try {
            // get parameters
            menu_detail_name = request.getParameter("menu_name");
            menu_item_is_heading = request.getParameter("menu_item_heading");
            menu_item_id = Integer.parseInt((String) request.getParameter("id"));
            menu_detail_tooltip = request
                  .getParameter("menu_item_detail_tooltip");
            page_id = Integer
                  .parseInt((String) request.getParameter("page_id"));
            component_id = Integer.parseInt((String) request.getParameter("component_id"));
            menu_item_active_yn = request.getParameter("menu_item_active_yn");
            menu_item_action = request.getParameter("external_link");
            ct_access_level_id = Integer.parseInt((String) request.getParameter("ct_access_level_id"));
         } catch (Exception e) {
            theMsg = "Error getting parameters - failed.";
            e.printStackTrace();
         }
         
         // lang
         try {
        	 ct_language_id = Integer.parseInt((String) session.getAttribute("adminlang"));
         } catch (Exception e) {
        	 ct_language_id = 1;
         }

         // first do menu_item
         try {
            MenuItem myMenuItem = new MenuItem();
            myMenuItem.setConn(conn);
            myMenuItem.setMenu_item_id(menu_item_id);
            myMenuItem.setPage_id(page_id);
            myMenuItem.setMenu_item_active_yn(menu_item_active_yn);
            myMenuItem.setMenu_item_action(menu_item_action);
            myMenuItem.setCt_access_level_id(ct_access_level_id);
            myMenuItem.setMenu_item_is_heading(menu_item_is_heading);
            myMenuItem.setComponent_id(component_id);
            
            myMenuItem.updateMenuItemIgnoreOrder();
         } catch (Exception e) {
            theMsg = "Error updating MenuItem";
            e.printStackTrace();
         }
         // now do menu_item_detail
         try {
            MenuItemDetail myMenuItemDetail = new MenuItemDetail();
            myMenuItemDetail.setConn(conn);
            myMenuItemDetail.setCt_language_id(ct_language_id);
            myMenuItemDetail.setMenu_item_detail_name(menu_detail_name);
            myMenuItemDetail.setMenu_item_id(menu_item_id);
            myMenuItemDetail.setMenu_item_detail_tooltip(menu_detail_tooltip);

            myMenuItemDetail.updateMenuItemDetail();
            theMsg = "Changes saved.";
         } catch (Exception e) {
            theMsg = "Error updating menu_item_detail - failed";
            e.printStackTrace();
         }

         // Generate completion message
         session.setAttribute("Error", theMsg);
         response.sendRedirect((String) request.getParameter("cancelPage"));

      }
      return MasterTemplate;
   }
}