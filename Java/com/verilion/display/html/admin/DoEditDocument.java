//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-22
//Revisions
//------------------------------------------------------------------------------
//$Log: DoEditDocument.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/07/22 13:57:08  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.Documents;
import com.verilion.database.SingletonObjects;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Worker for changing a document associated to a page.
 * 
 * <P>
 * July 22, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DoEditDocument extends SecurePage {

   private static final long serialVersionUID = 4123289261850195829L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      int action = 0;
      int page_id = 0;
      int document_id = 0;
      String thePath = "";
      String filename = "";
      String type = "";

      try {
         // get info from post
         thePath = SingletonObjects.getInstance().getUpload_path();
         Documents myDoc = new Documents();

         MultipartRequest multi = new MultipartRequest(request, thePath,
               10000 * 1024);

         Enumeration files = multi.getFileNames();

         while (files.hasMoreElements()) {
            String name = (String) files.nextElement();
            filename = multi.getFilesystemName(name);
            type = multi.getContentType(name);
         }

         action = Integer.parseInt((String) multi.getParameter("action"));
         page_id = Integer.parseInt((String) multi.getParameter("page_id"));
         document_id = Integer.parseInt((String) multi
               .getParameter("document_id"));
         // now perform the appropriate action
         // 1 = save
         // 2 = cancel

         switch (action) {
         case 1:

            try {
               if (multi.getParameter("newdoc") != null) {
                  if (type == null) {
                     // we didn't get a file uploaded, so send them
                     // back with an error message
                     session.setAttribute(
                           "Error",
                           "You must specify an file to upload!");
                     response.sendRedirect(response
                           .encodeRedirectURL((String) session
                                 .getAttribute("lastPage")));
                  }
                  myDoc.setConn(conn);
                  myDoc.setDocument_id(document_id);
                  myDoc.setPage_id(page_id);
                  myDoc.setDocument_active_yn((String) multi
                        .getParameter("document_active_yn"));
                  myDoc.setDocument_mime_type(type);
                  myDoc.setDocument_name(filename);
                  myDoc.setDocument_title((String) multi
                        .getParameter("document_title"));
                  myDoc.updateDocument(thePath + filename);
                  session.setAttribute("Error", "Success.");
                  response
                        .sendRedirect("/servlet/com.verilion.display.html.admin."
                              + "EditPageDocuments?page=EditPageDocuments&page_id="
                              + page_id);
               } else {
                  // we have no document attached, so just update the
                  // other info
                  myDoc.setConn(conn);
                  myDoc.setDocument_id(document_id);
                  myDoc.setPage_id(page_id);
                  myDoc.setDocument_active_yn((String) multi
                        .getParameter("document_active_yn"));
                  myDoc.setDocument_mime_type((String) multi
                        .getParameter("document_mime_type"));
                  myDoc.setDocument_name((String) multi
                        .getParameter("document_name"));
                  myDoc.setOwner_id(Integer.parseInt((String) multi
                        .getParameter("owner_id")));
                  myDoc.setDocument_title((String) multi
                        .getParameter("document_title"));
                  myDoc.updateDocumentInfo();
                  session.setAttribute("Error", "Success.");
                  response
                        .sendRedirect("/servlet/com.verilion.display.html.admin."
                              + "EditPageDocuments?page=EditPageDocuments&page_id="
                              + page_id);
               }

            } catch (Exception e) {
               e.printStackTrace();
            }
            break;

         case 2:
            session.setAttribute("Error", "Cancelled.");
            response.sendRedirect("/servlet/com.verilion.display.html.admin."
                  + "EditPageDocuments?page=EditPageDocuments&page_id="
                  + page_id);
            break;
         }
      } catch (Exception e) {
         Errors
               .addError("com.verilion.display.admin.DoAddDocument:BuildPage:Exception:"
                     + e.toString());
      }
   }
}