//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-21
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessLogin.java,v $
//Revision 1.4.2.3.4.1.8.4.2.3  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.4.2.3.4.1.8.4.2.2  2007/06/11 15:36:41  tcs
//Changed sessional vars
//
//Revision 1.4.2.3.4.1.8.4.2.1  2007/03/30 17:21:38  tcs
//*** empty log message ***
//
//Revision 1.4.2.3.4.1.8.4  2007/03/19 00:46:43  tcs
//added sessional var
//
//Revision 1.4.2.3.4.1.8.3  2007/03/15 17:46:57  tcs
//New sessional variable
//
//Revision 1.4.2.3.4.1.8.2  2007/02/01 12:32:49  tcs
//Added username to sessional vars
//
//Revision 1.4.2.3.4.1.8.1  2007/01/28 00:50:49  tcs
//Minor edits
//
//Revision 1.4.2.3.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4.2.3  2005/01/06 16:25:12  tcs
//Removed autoappend of msg parameter if msg is empty
//
//Revision 1.4.2.2  2005/01/06 14:36:57  tcs
//Changed to use dispatcher instead of sendRedirect
//
//Revision 1.4.2.1  2004/12/10 14:33:41  tcs
//Added msg parameter
//
//Revision 1.4  2004/11/29 17:48:34  tcs
//Added request.getSession(true)
//
//Revision 1.3  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.2  2004/10/26 15:41:34  tcs
//Improved javadocs
//
//Revision 1.1  2004/10/21 12:23:54  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Customer;
import com.verilion.database.GenericTable;
import com.verilion.database.PageTemplate;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;

/**
 * Login to the app
 * 
 * <P>
 * October 21, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class ProcessLogin extends ProcessPage {

   private String customer_email_address = "";
   private String customer_password = "";
   private ResultSet rs = null;
   private Statement st = null;
   private String target_url = "";
   private String failed_target_url = "";
   private int customer_access_level = 0;
   private int homepage_id = 0;
   private String username = "";

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
         session = request.getSession(true);
         // get some application prefs
         homepage_id = SingletonObjects.getInstance().getHomepage_page_id();

         // set up PageTemplate bean
         PageTemplate myPageTemplate = new PageTemplate();
         myPageTemplate.setPage_id(homepage_id);
         myPageTemplate.setConn(conn);

         int customer_id = -1;
         customer_email_address = (String) request
               .getParameter("email_address");
         customer_password = (String) request.getParameter("password");
         if (request.getParameter("url") != null) {
            target_url = (String) request.getParameter("url");
         }
         
         if (request.getParameter("furl") != null) {
            failed_target_url = (String) request.getParameter("furl");
         }

         // try to log in
         try {
            customer_id = doLogin(
                  customer_email_address,
                  customer_password,
                  conn);
         } catch (Exception e) {
            e.printStackTrace();
         }

         // if we have a customer id > 0, we are logged in, so set
         // some sessional variables. Otherwise set an error message.
         if (customer_id > 0) {
            session.setAttribute("user", customer_email_address);
            session.setAttribute("customer_id", customer_id + "");
            try {
               Customer myCustomer = new Customer();
               myCustomer.setConn(conn);
               customer_access_level = myCustomer
                     .getAccessLevel(customer_email_address);
               username = myCustomer.getUsernameByEmail(customer_email_address);
               session.setAttribute(
                     "customer_access_level",
                     customer_access_level + "");
               session.setAttribute(
                     "username",
                     username);
               session.setAttribute("languageId", "1");
            } catch (Exception e) {
               e.printStackTrace();
            }
            
            // add the logged in username to db
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
               myUpdateTable.setUpdateWhat("logged_in_users");
               myUpdateTable.setSCustomWhere(" and logged_in_user_id = '" + drs.getInt("logged_in_user_id") + "' ");
               myUpdateTable.addUpdateFieldNameValuePair("username", username, "string");
               try {
                  myUpdateTable.updateRecord();
               } catch (Exception e) {
                  e.printStackTrace();
               }
            }
            session.setAttribute("Error", "Successfully logged in.");
            hm.put("PRESERVEMSG", "TRUE");
            if (target_url.length() > 0) {
               response.sendRedirect(target_url);
            } else {
               response.sendRedirect("/public/jpage/1/p/Home/content.do");
            }
         } else {
            session.setAttribute("Error", "Invalid login! Please try again.");
            hm.put("PRESERVEMSG", "TRUE");
            if (failed_target_url.length() > 0) {
               response.sendRedirect(failed_target_url);
            } else {
               response.sendRedirect("/public/jpage/1/p/Home/content.do");
            }
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

   public int doLogin(String inEmailAddress, String inPassword, Connection conn)
         throws Exception {

      int customer_id = -1;

      String sqlQuery = "select ce.customer_email, "
            + "c.customer_id, "
            + "c.customer_password "
            + "from customer_email_detail as ce, "
            + "customer as c "
            + "where ce.customer_email = '"
            + inEmailAddress
            + "' "
            + "and c.customer_id = ce.customer_id "
            + "and ce.customer_email_default_yn = 'y'";

      try {
         st = conn.createStatement();
         rs = st.executeQuery(sqlQuery);
         if (rs.next()) {
            String pass = rs.getString("customer_password");
            if (pass.equalsIgnoreCase(inPassword)) {
               customer_id = rs.getInt("customer_id");
            }
         }
         rs.close();
         rs = null;
         st.close();
         st = null;

      } catch (SQLException e) {
         throw new ServletException("Login:doLogin:SQLException:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return customer_id;
   }
}