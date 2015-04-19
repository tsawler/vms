//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-05
//Revisions
//------------------------------------------------------------------------------
//$Log: AddAdminMenuItem.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/10/05 13:15:59  tcs
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
 * Add an admin menu item
 * 
 * <P>
 * October 5 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class AddAdminMenuItem extends SecurePage {

   private static final long serialVersionUID = 5766536374992287953L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      String theMenu = "";
      try {
         // create drop down list pages
         PageTemplate myPage = new PageTemplate();
         myPage.setConn(conn);
         crs = myPage.getAllPageNamesIds();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "page_id",
               0,
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
               0,
               crs,
               "admin_menu_items_id",
               "admin_menu_items_name",
               0,
               "Top level item");

         MasterTemplate.searchReplace("$PARENTITEM$", theMenu);

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("AddAdminMenuItem:BuildPage:Exception:" + e.toString());
      }
   }
}