//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-21
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessLogout.java,v $
//Revision 1.4.6.1.4.1.4.3.2.2  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.4.6.1.4.1.4.3.2.1  2007/06/11 15:36:41  tcs
//Changed sessional vars
//
//Revision 1.4.6.1.4.1.4.3  2007/02/01 12:33:17  tcs
//Removes username from session
//
//Revision 1.4.6.1.4.1.4.2  2007/01/28 00:50:49  tcs
//Minor edits
//
//Revision 1.4.6.1.4.1.4.1  2007/01/22 19:17:37  tcs
//Explicit removal of sessional vars
//
//Revision 1.4.6.1.4.1  2005/12/24 03:17:05  tcs
//Instead of destroying session, just remove appropriate vars
//
//Revision 1.4.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4  2004/11/30 18:24:21  tcs
//Improved logout procedure
//
//Revision 1.3  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.2  2004/10/26 15:41:34  tcs
//Improved javadocs
//
//Revision 1.1  2004/10/21 12:31:18  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.database.PageTemplate;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;

/**
 * Logout of the app
 * 
 * <P>
 * October 21, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class ProcessLogout extends ProcessPage {

   private ResultSet rs = null;
   private int homepage_id = 0;

   /**
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      try {

         // get some application prefs
         homepage_id = SingletonObjects.getInstance().getHomepage_page_id();

         // set up PageTemplate bean
         PageTemplate myPageTemplate = new PageTemplate();
         myPageTemplate.setPage_id(homepage_id);
         myPageTemplate.setConn(conn);
         session.removeAttribute("user");
         session.removeAttribute("customer_id");
         session.removeAttribute("customer_access_level");
         session.removeAttribute("username");
         
         // remove logged in info from db
         GenericTable myTable = new GenericTable("logged_in_users");
         myTable.setConn(conn);
         myTable.setSSelectWhat("*");
         myTable.setSCustomWhere(" and session_id = '" + session.getId() + "'");
         myTable.setSCustomLimit("limit 1");
         XDisconnectedRowSet drs = new XDisconnectedRowSet();
         drs = myTable.getAllRecordsDisconnected();
         while (drs.next()) {
            GenericTable myUpdateTable = new GenericTable();
            myUpdateTable.setConn(conn);
            myUpdateTable.setSTable("logged_in_users");
            myUpdateTable.setSPrimaryKey("logged_in_user_id");
            myUpdateTable.setIPrimaryKey(drs.getInt("logged_in_user_id"));
            try {
               myUpdateTable.deleteRecord();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         
         session.invalidate();
         session = request.getSession(true);
         hm.put("PRESERVEMSG", "TRUE");
         session.setAttribute("Error", "Successfully logged out.");
         if (request.getParameter("url") == null) {

            try {
               response.sendRedirect(response.encodeRedirectURL("http://"
                     + request.getServerName()
                     + myPageTemplate.getInvocationAsString()));
            } catch (SQLException e1) {
               e1.printStackTrace();
            } catch (Exception e1) {
               e1.printStackTrace();
            }
         } else {
            response.sendRedirect((String) request.getParameter("url"));
         }
      } catch (Exception e) {

      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      return MasterTemplate;
   }
}