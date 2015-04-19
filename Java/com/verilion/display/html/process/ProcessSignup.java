//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-12-10
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessSignup.java,v $
//Revision 1.1.2.1.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.1  2004/12/10 14:32:24  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.Customer;
import com.verilion.database.CustomerEmailDetail;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;
import com.verilion.object.mail.SendMessage;

/**
 * Form for a user (not customer) to join site
 * 
 * <P>
 * December 10, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class ProcessSignup extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      String theError = "";
      String email_address = "";
      String password = "";
      int customer_id = 0;
      boolean okay = true;

      try {
         // get parameters from calling form.
         email_address = (String) request.getParameter("email_address");
         password = (String) request.getParameter("password");

         // make sure they've filled out both email and password on form.
         if (email_address == null) {
            theError += "You must enter an email address! ";
            okay = false;
         }
         if (password == null) {
            theError += "You must enter a password! ";
            okay = false;
         }

         // make sure email and password have a length > 0
         if (okay) {
            if ((email_address.length() < 7)
                  || (email_address.indexOf("@") == -1)) {
               theError = "This is not a valid email address! ";
               okay = false;
            }
            if (password.length() < 4) {
               theError += "Your password is too short! ";
               okay = false;
            }
         }

         // check to see if this email address is already registered.
         if (okay) {
            // open connection to CustomerEmailDetail and check if email
            // address already exists
            CustomerEmailDetail myEmail = new CustomerEmailDetail();
            myEmail.setConn(conn);
            myEmail.setCustomer_email(email_address);
            customer_id = myEmail.getCustomerIdByEmailAddress();

            if (customer_id != 0) {
               // we got an id back, so this email is already on file
               theError += "This email address is already on file! ";
               theError += "If you've forgotten your password, we can ";
               theError += "<a href=\"/RecoverPassword/display/1/content.do\">";
               theError += "send it to you</a>.";
               okay = false;
            } else {
               // no problem -- we can register this person
               CreateAccount(email_address, password, conn);
            }
         }

         if (okay) {
            // success -- they're registered, so log them in.
            session.setAttribute(
                  "Error",
                  "Account successfully created! Welcome to "
                  + SingletonObjects.getInstance().getHost_name()
                  + ".");

            // now send the email to the customer
            SendMessage.setTo(email_address);
            SendMessage.setFrom(SingletonObjects.getInstance().getAdmin_email());
            SendMessage.setSubject("Your account information!");
            SendMessage
                  .setMessage("Thanks for joining "
                        + SingletonObjects.getInstance().getHost_name()
                        + ". Your password is "
                        + password
                        + " and your login id is your email, "
                        + email_address
                        + ".");
            SendMessage.SendEmail();

            // log them in and take them to the home page
            response.sendRedirect("/Home/login/1/content.do?email_address="
                  + email_address
                  + "&password="
                  + password
                  + "&msg=Account successfully created! Welcome to "
                  + SingletonObjects.getInstance().getHost_name()
                  + ".");

         } else {
            // no dice.
            session.setAttribute("Error", theError);
            response.sendRedirect((String)session.getAttribute("lastPage"));
         }

      } catch (Exception e) {
         Errors.addError("ProcessSignup:ProcessForm:Exception:"
               + e.toString());
      }
      return MasterTemplate;
   }

   public void CreateAccount(String inEmail, String inPassword, Connection conn)
         throws SQLException, Exception {
      int theId = 0;
      try {
         Customer myCustomer = new Customer();
         myCustomer.setConn((Connection) conn);
         myCustomer.setCustomer_password(inPassword);
         myCustomer.setCustomer_access_level(1);
         myCustomer.setCustomer_active_yn("y");
         theId = myCustomer.CustomerAddBuyer();

         if (theId != 0) {
            CustomerEmailDetail myEmail = new CustomerEmailDetail();
            myEmail.setConn(conn);
            myEmail.setCustomer_email(inEmail);
            myEmail.setCustomer_email_default_yn("y");
            myEmail.setCustomer_id(theId);
            myEmail.addCustomerEmailDetail();
         }
      } catch (SQLException e) {
         e.printStackTrace();
         Errors.addError("ProcessSignup:CreateAccount:SQLException:" + e.toString());
      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ProcessSignup:CreateAccount:Exception:" + e.toString());
      }
   }

}
