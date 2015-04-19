//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-03
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditAdminMenuItem.java,v $
//Revision 1.2  2004/11/03 18:04:37  tcs
//Complete
//
//Revision 1.1  2004/11/03 14:59:39  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.AdminMenu;
import com.verilion.database.PageTemplate;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit an Admin menu item
 * 
 * <P>
 * November 03, 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessEditAdminMenuItem extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      String theMenu = "";
      String theMenuItemId = (String) request.getParameter("menu_id");

      AdminMenu myMenu = new AdminMenu();
      myMenu.setConn(conn);
      myMenu.setAdmin_menu_items_id(theMenuItemId);
      drs = myMenu.getAdminMenuItemsById();

      while (drs.next()) {
         try {
            if (request.getParameter("edit") == null) {
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
               MasterTemplate.searchReplace("$ID$", theMenuItemId);

               // create a drop down list of parent items
               AdminMenu myAdminMenu = new AdminMenu();
               myAdminMenu.setConn(conn);
               crs = myAdminMenu.getAdminMenuItemsNamesIds();

               MasterTemplate.searchReplace("$NAME$", drs
                     .getString("admin_menu_items_name"));
            } else {
               myMenu.setConn(conn);
               myMenu.setPage_id(Integer.parseInt((String) request
                     .getParameter("page_id")));
               myMenu.setAdmin_menu_items_name((String) request
                     .getParameter("menu_text"));
               myMenu.setAdmin_menu_items_id((String) request
                     .getParameter("menu_id"));
               myMenu.updateAdminMenuItem();

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
                  Errors.addError("ProcessEditAdminMenuItem:ProcessForm:Exception:"
                        + e.toString());
               }
               session.setAttribute("Error", "Changes saved.");
               response.sendRedirect((String) request.getParameter("cancelPage") + "?refresh=true");
            }

         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditAdminMenuItem:ProcessForm:Exception:"
                  + e.toString());
         }
      }
      return MasterTemplate;
   }

}