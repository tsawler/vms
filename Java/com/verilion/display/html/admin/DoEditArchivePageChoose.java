//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-27
//Revisions
//------------------------------------------------------------------------------
//$Log: DoEditArchivePageChoose.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/07/27 11:39:22  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.ArchivePage;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Manage archive page items
 * 
 * July 27, 2004
 * 
 * @author tsawler
 * 
 */
public class DoEditArchivePageChoose extends SecurePage {

   private static final long serialVersionUID = 3360364985549637170L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      // initialize variables
      int pageCount = 0;
      int action = 0;
      int temp_id = 0;

      try {
         // get our parameters
         action = Integer.parseInt((String) request.getParameter("action"));
         pageCount = Integer.parseInt((String) request
               .getParameter("pageCount"));

         // create necessary objects
         ArchivePage myPage = new ArchivePage();
         myPage.setConn(conn);

         // now perform the appropriate action on the selected rows
         // 1 = publish
         // 2 = unpublish
         // 3 = delete

         switch (action) {
         case 1:
            // want to publish all selected rows
            for (int i = 1; i <= pageCount; i++) {
               if (request.getParameter("action_" + i) != null) {
                  temp_id = Integer.parseInt((String) request
                        .getParameter("page_id" + i));
                  myPage.setArchive_page_id(temp_id);
                  myPage.setArchive_page_active_yn("y");
                  myPage.changeActiveStatus();
               }
            }
            break;

         case 2:
            // want to unpublish all selected rows
            for (int i = 1; i <= pageCount; i++) {
               if (request.getParameter("action_" + i) != null) {
                  temp_id = Integer.parseInt((String) request
                        .getParameter("page_id" + i));
                  myPage.setArchive_page_id(temp_id);
                  myPage.setArchive_page_active_yn("n");
                  myPage.changeActiveStatus();
               }
            }
            break;

         case 3:
            // want to delete all selected rows
            for (int i = 1; i <= pageCount; i++) {
               if (request.getParameter("action_" + i) != null) {
                  temp_id = Integer.parseInt((String) request
                        .getParameter("page_id" + i));
                  myPage.setArchive_page_id(temp_id);
                  myPage.deleteArchivePageById();
               }
            }
            break;
         }

         response
               .sendRedirect("/servlet/com.verilion.display.html.admin.EditArchivePageChoose?"
                     + "page=EditArchivePageChoose");

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("DoEditArchivePageChoose:BuildPage:Exception:"
               + e.toString());
      }
   }
}