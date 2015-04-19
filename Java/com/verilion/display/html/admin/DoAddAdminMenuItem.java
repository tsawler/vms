//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-05
//Revisions
//------------------------------------------------------------------------------
//$Log: DoAddAdminMenuItem.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/10/06 17:56:22  tcs
//Corrected method name in error trap
//
//Revision 1.1  2004/10/05 16:52:13  tcs
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
 * Worker for adding an admin menu item
 * 
 * October 05, 2004
 * 
 * @author tsawler
 * 
 */
public class DoAddAdminMenuItem extends SecurePage {

   private static final long serialVersionUID = -8950599306725316819L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      // initialize variables
      int action = 0;

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
            if (((String) request.getParameter("parent_id")).equals("0")) {
               myMenu.setPage_id(Integer.parseInt((String) request
                     .getParameter("page_id")));
               myMenu.setAdmin_menu_items_name((String) request
                     .getParameter("menu_text"));
               myMenu.addAdminMenuItem();
            } else {
               myMenu.setPage_id(Integer.parseInt((String) request
                     .getParameter("page_id")));
               myMenu.setAdmin_menu_item_detail_name((String) request
                     .getParameter("menu_text"));
               myMenu.setAdmin_menu_items_detail_target("S");
               myMenu.setAdmin_menu_items_id((String) request
                     .getParameter("parent_id"));
               myMenu.addAdminMenuItemDetail();
            }

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
               Errors.addError("DoAdminMenuItemChoose:BuildPage:Exception:"
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
         Errors.addError("DoAddAdminMenuItem:BuildPage:Exception:"
               + e.toString());
      }
   }
}