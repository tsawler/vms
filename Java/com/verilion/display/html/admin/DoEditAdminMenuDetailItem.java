//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-06
//Revisions
//------------------------------------------------------------------------------
//$Log: DoEditAdminMenuDetailItem.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/10/06 18:26:56  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.AdminMenu;
import com.verilion.database.SingletonObjects;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Worker for editing an admin menu detail item
 * 
 * October 06, 2004
 * 
 * @author tsawler
 * 
 */
public class DoEditAdminMenuDetailItem extends SecurePage {

   private static final long serialVersionUID = -8744272573366422611L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      // initialize variables
      int action = 0;
      int admin_menu_item_detail_id = Integer.parseInt((String) request
            .getParameter("id"));
      int parent_id = Integer.parseInt((String) request
            .getParameter("parent_id"));
      String name = (String) request.getParameter("menu_text");

      try {
         // get our parameters
         action = Integer.parseInt((String) request.getParameter("action"));

         // now perform the appropriate action
         // 1 = save
         // 2 = cancel

         switch (action) {
         case 1:
            // save
            AdminMenu myMenu = new AdminMenu();
            myMenu.setConn(conn);
            myMenu.setPage_id(Integer.parseInt((String) request
                  .getParameter("page_id")));
            myMenu.setAdmin_menu_item_detail_name(name);
            myMenu.setAdmin_menu_item_detail_id(admin_menu_item_detail_id + "");
            myMenu.setAdmin_menu_items_id(parent_id + "");
            myMenu.updateAdminMenuItemDetail();

            AdminMenu myNewMenu = new AdminMenu();
            myNewMenu.setConn(conn);
            FileOutputStream out;
            PrintStream p;

            try {
               out = new FileOutputStream(SingletonObjects.getInstance()
                     .getUpload_path()
                     + "../js/adminMenu.js");
               p = new PrintStream(out);
               p.print(myNewMenu.generateMenuJavaScript());
            } catch (Exception e) {
               e.printStackTrace();
               Errors.addError("DoEditAdminMenuItem:BuildPage:Exception:"
                     + e.toString());
            }

            break;

         case 2:
            // cancel

            break;

         }

         response.sendRedirect("/servlet/com.verilion.display.html."
               + "admin.AdminMenuItemChoose?page=AdminMenuItemChoose");

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("DoEditAdminMenuDetailItem:BuildPage:Exception:"
               + e.toString());
      }
   }
}