//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-26
//Revisions
//------------------------------------------------------------------------------
//$Log: DoEditArchivePage.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/07/26 18:46:54  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.ArchivePage;
import com.verilion.database.DBUtils;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Edit an archive page.
 * 
 * July 26, 2004
 * 
 * @author tsawler
 * 
 */
public class DoEditArchivePage extends SecurePage {

   private static final long serialVersionUID = -714870793298955319L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      String pageName = "";
      int templateId = 0;
      int accessLevelId = 0;
      String theContents = "";
      int thePageId = 0;
      String thePageTitle = "";
      int action = 0;
      String activeYn = "";

      try {
         XDisconnectedRowSet crs = new XDisconnectedRowSet();
         DBUtils myDbUtil = new DBUtils();
         // get our action
         action = Integer.parseInt((String) request.getParameter("action"));

         // get parameters passed by form
         pageName = (String) request.getParameter("page_name");
         templateId = Integer.parseInt((String) request
               .getParameter("template_id"));
         accessLevelId = Integer.parseInt((String) request
               .getParameter("access_level_id"));
         thePageId = Integer.parseInt((String) request.getParameter("page_id"));
         thePageTitle = (String) request.getParameter("page_title");
         theContents = (String) request.getParameter("contents");
         theContents = myDbUtil.replace(theContents, "src=\"..", "src=\"");

         // now perform the appropriate action
         // 1 = save
         // 2 = cancel

         switch (action) {
         case 1:
            // get the active status of this page and save it
            ArchivePage myTempPage = new ArchivePage();
            myTempPage.setConn(conn);
            myTempPage.setArchive_page_id(thePageId);
            crs = myTempPage.getArchivePageById();

            while (crs.next()) {
               activeYn = crs.getString("archive_page_active_yn");
            }

            // update the page
            ArchivePage myPage = new ArchivePage();
            myPage.setConn(conn);
            myPage.setArchive_page_active_yn(activeYn);
            myPage.setArchive_page_id(thePageId);
            myPage.setArchive_page_name(pageName);
            myPage.setTemplate_id(templateId);
            myPage.setCt_access_level_id(accessLevelId);
            myPage.setArchive_page_title(thePageTitle);
            myPage.setArchive_page_content(theContents);

            myPage.updateArchivePage();

            // Generate completion message
            session.setAttribute("Error", "Page update successful");
            response
                  .sendRedirect("/servlet/com.verilion.display.html.admin.EditArchivePageChoose"
                        + "?page=EditArchivePageChoose");

            break;

         case 2:
            // Generate cancel message
            session.setAttribute("Error", "Page edit cancelled");
            response
                  .sendRedirect("/servlet/com.verilion.display.html.admin.EditArchivePageChoose"
                        + "?page=EditArchivePageChoose");
            break;
         }

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("DoEditArchivePage:BuildPage:Exception:"
               + e.toString());
      }
   }
}