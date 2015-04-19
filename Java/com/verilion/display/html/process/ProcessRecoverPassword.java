//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessRecoverPassword.java,v $
//Revision 1.4.6.1.4.2.2.1.2.2  2007/01/28 00:50:49  tcs
//Minor edits
//
//Revision 1.4.6.1.4.2.2.1.2.1  2007/01/25 01:18:55  tcs
//Trying to get msg to display...
//
//Revision 1.4.6.1.4.2.2.1  2006/08/01 11:02:48  tcs
//*** empty log message ***
//
//Revision 1.4.6.1.4.2  2005/12/24 03:23:59  tcs
//Corrected typo in url
//
//Revision 1.4.6.1.4.1  2005/12/24 03:22:26  tcs
//Modified redirects
//
//Revision 1.4.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.3  2004/10/26 15:41:34  tcs
//Improved javadocs
//
//Revision 1.2  2004/10/19 17:21:34  tcs
//Formatting
//
//Revision 1.1  2004/10/19 12:56:31  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.Customer;
import com.verilion.database.CustomerEmailDetail;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.GetErrorMessages;
import com.verilion.object.mail.SendMessage;

/**
 * Recover a lost password
 * 
 * <P>
 * October 19, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 *  
 */
public class ProcessRecoverPassword extends ProcessPage {

   private String customer_email_address = "";
   private ResultSet rs = null;


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

      int customer_id = 0;
      String thePassword = "";
      boolean foundCustomer = true;
      String from = "";
      String subject = "";
      String theMessage = "";
      String theError = "";

      try {
         // get the email address for which a password is needed
         customer_email_address = (String) request
               .getParameter("email_address");

         CustomerEmailDetail myEmail = new CustomerEmailDetail();
         myEmail.setConn(conn);
         myEmail.setCustomer_email(customer_email_address);

         // now get customer id for email. If no value found, we get
         // a zero back
         customer_id = myEmail.getCustomerIdByEmailAddress();

         if (customer_id != 0) {
            // we got a valid customer id, so look up the password
            Customer myCustomer = new Customer();
            myCustomer.setConn(conn);
            myCustomer.setCustomer_id(customer_id);
            rs = myCustomer.GetOneCustomer();

            if (rs.next()) {
               // get the password
               thePassword = rs.getString("customer_password");

               // now get the message text, and add our password to it.
               theMessage = GetErrorMessages.getErrorMessage(
                     session,
                     "Recover password email text")
                     + thePassword;

               // get the from email address
               from = GetErrorMessages.getErrorMessage(
                     session,
                     "Recover password from email address");

               // get the email subject
               subject = GetErrorMessages.getErrorMessage(
                     session,
                     "Recover password email subject");

               // now send the email to the customer
               SendMessage.setTo(customer_email_address);
               SendMessage.setFrom(from);
               SendMessage.setSubject(subject);
               SendMessage.setMessage(theMessage);
               try {
                  SendMessage.SendEmail();
               } catch (Exception e) {
                  e.printStackTrace();
               }

               theError = GetErrorMessages.getErrorMessage(
                     session,
                     "Recover password success message");
               theError += " " + customer_email_address;
            } else {
               foundCustomer = false;
               theError = GetErrorMessages.getErrorMessage(
                     session,
                     "Recover password no customer on file message");
            }
            rs.close();
            rs = null;
         } else {
            foundCustomer = false;
            theError = GetErrorMessages.getErrorMessage(
                  session,
                  "Recover password no customer on file message");
         }
         //System.out.println("Message: " + theError);
         session.setAttribute("Error", theError);
         hm.put("PRESERVEMSG", "TRUE");
         if (foundCustomer) {
            if (request.getParameter("redirect") != null) {
               response.sendRedirect(request.getParameter("redirect") + "?msg=" + theError);
            } else {
               response.sendRedirect("/public/jpage/1/p/Home/content.do"  + "?msg=" + theError);
            }
         } else {
            response.sendRedirect("/public/jpage/1/p"
                  + "/RecoverPassword/content.do"  + "?msg=" + theError);
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