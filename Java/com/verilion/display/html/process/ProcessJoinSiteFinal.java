//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-06
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessJoinSiteFinal.java,v $
//Revision 1.1.2.4.4.1.6.1  2006/06/11 22:38:49  tcs
//Fixed logic error
//
//Revision 1.1.2.4.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.4  2005/03/08 16:46:19  tcs
//Javadoc fix
//
//Revision 1.1.2.3  2005/01/17 04:31:15  tcs
//Removed debugging messages
//
//Revision 1.1.2.2  2005/01/16 03:08:05  tcs
//Forgot to add one to the month... fixed
//
//Revision 1.1.2.1  2004/12/01 15:26:48  tcs
//Complete
//
//Revision 1.1  2004/11/08 01:51:09  tcs
//in progress
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.CtPackages;
import com.verilion.database.CtTerm;
import com.verilion.database.Customer;
import com.verilion.database.CustomerAddressDetail;
import com.verilion.database.CustomerEmailDetail;
import com.verilion.database.CustomerPhoneDetail;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;
import com.verilion.object.mail.SendMessage;

/**
 * Complete joining site
 * 
 * <P>
 * November 6, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class ProcessJoinSiteFinal extends ProcessPage {

   public int ct_salutation_id = 0;
   public String first_name = "";
   public String last_name = "";
   public String company_name = "";
   public String email_address = "";
   public String password = "";
   public String website_url = "";
   public String phone_number = "";
   public String extension = "";
   public int ct_phone_type_id = 0;
   public String fax_number = "";
   public String address = "";
   public String city = "";
   public int ct_province_state_id = 0;
   public int ct_country_id = 0;
   public String postal_zip = "";
   public int add_to_list = 0;
   public int ct_package_id = 0;
   public int ct_term_id = 0;
   public int ct_credit_card_id = 0;
   public String card_number = "";
   public int expiry_month = 0;
   public int expiry_year = 0;
   public String name_on_card = "";
   public int billing_address_same = 0;
   public String billing_address = "";
   public String billing_city = "";
   public int billing_ct_state_province_id = 0;
   public int billing_ct_country_id = 0;
   public String billing_postal_zip = "";
   public String company_description = "";
   public String theError = "";
   public int newCustomerId = 0;

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      try {
         // ------------------------------------------------------------------
         // Get values from session
         // ------------------------------------------------------------------
         ct_salutation_id = Integer.parseInt((String) session
               .getAttribute("ct_salutation_id"));
         first_name = (String) session.getAttribute("first_name");
         last_name = (String) session.getAttribute("last_name");
         company_name = (String) session.getAttribute("company_name");
         email_address = (String) session.getAttribute("email_address");
         password = (String) session.getAttribute("password");
         website_url = (String) session.getAttribute("website_url");
         phone_number = (String) session.getAttribute("phone_number");
         ct_phone_type_id = Integer.parseInt((String) session
               .getAttribute("ct_phone_type_id"));
         if (session.getAttribute("extension") != null) {
            extension = (String) session.getAttribute("extension");
         }
         if (session.getAttribute("fax_number") != null) {
            fax_number = (String) session.getAttribute("fax_number");
         }
         address = (String) session.getAttribute("address");
         city = (String) session.getAttribute("city");
         ct_province_state_id = Integer.parseInt((String) session
               .getAttribute("ct_province_state_id"));
         ct_country_id = Integer.parseInt((String) session
               .getAttribute("ct_country_id"));
         postal_zip = (String) session.getAttribute("postal_zip");
         add_to_list = Integer.parseInt((String) session
               .getAttribute("add_to_list"));
         ct_package_id = Integer.parseInt((String) session
               .getAttribute("ct_package_id"));
         ct_term_id = Integer.parseInt((String) session
               .getAttribute("ct_term_id"));
         ct_credit_card_id = Integer.parseInt((String) session
               .getAttribute("ct_credit_card_id"));
         card_number = (String) session.getAttribute("card_number");
         name_on_card = (String) session.getAttribute("name_on_card");
         expiry_month = Integer.parseInt((String) session
               .getAttribute("expiry_month"));
         expiry_year = Integer.parseInt((String) session
               .getAttribute("expiry_year"));
         billing_address_same = Integer.parseInt((String) session
               .getAttribute("billing_address_same"));

         if (billing_address_same == 0) {
            billing_address = (String) session.getAttribute("billing_address");
            billing_city = (String) session.getAttribute("billing_city");
            billing_ct_state_province_id = Integer.parseInt((String) session
                  .getAttribute("billing_ct_state_province_id"));
            billing_ct_country_id = Integer.parseInt((String) session
                  .getAttribute("billing_ct_country_id"));
            billing_postal_zip = (String) session
                  .getAttribute("billing_postal_zip");
         } else {
            billing_address = address;
            billing_ct_state_province_id = ct_province_state_id;
            billing_city = city;
            billing_ct_country_id = ct_country_id;
            billing_postal_zip = postal_zip;
         }

         company_description = (String) session
               .getAttribute("company_description");

         // ------------------------------------------------------------------
         // Calculate price and taxes
         // ------------------------------------------------------------------
         float cost = 1f;

         // get our package price
         CtPackages myPackages = new CtPackages();
         myPackages.setConn(conn);
         myPackages.setCt_package_id(ct_package_id);
         ResultSet rs = myPackages.getPackageDetail();

         if (rs.next()) {
            cost = rs.getFloat("ct_package_price");
         }

         // get our term
         CtTerm myTerm = new CtTerm();
         myTerm.setConn(conn);
         myTerm.setCt_term_id(ct_term_id);
         rs = myTerm.getTerm();
         int term = 0;
         if (rs.next()) {
            term = rs.getInt("ct_term_quantity");
         }

         int intVal = (int) (cost * 100f);
         intVal = intVal * term;


         // ------------------------------------------------------------------
         // Do any client specific validation/form processing
         // ------------------------------------------------------------------
         ProcessCustomFormInfo(
               request,
               response,
               session,
               conn,
               MasterTemplate,
               hm);

         // ------------------------------------------------------------------
         // Write validated data to database
         // ------------------------------------------------------------------
         WriteNewCustomerToDatabase(request, response, session, conn);

         // ------------------------------------------------------------------
         // Do any additional detail table stuff on a per client basis
         // ------------------------------------------------------------------
         DoAdditionalDatabaseStuff(request, response, session, conn);

         // send email notification to site owner
         SendMessage.setTo(SingletonObjects.getInstance().getAdmin_email());
         SendMessage.setFrom(SingletonObjects.getInstance().getAdmin_email());
         SendMessage.setSubject("new  signup");
         SendMessage.setMessage("There is a new signup!");
         try {
            SendMessage.SendEmail();
         } catch (Exception e) {
            e.printStackTrace();
         }

         // send email notification to customer
         SendMessage.setTo((String) session.getAttribute("email_address"));
         SendMessage.setFrom(SingletonObjects.getInstance().getAdmin_email());
         SendMessage.setSubject("Confirmation");
         SendMessage
               .setMessage("Thanks for joining our site. You will recieve additional instructions via email shortly.");
         try {
            SendMessage.SendEmail();
         } catch (Exception e) {
            e.printStackTrace();
         }

         // remove sessional variables
         session.removeAttribute("ct_salutation_id");
         session.removeAttribute("first_name");
         session.removeAttribute("last_name");
         session.removeAttribute("company_name");
         session.removeAttribute("email_address");
         session.removeAttribute("password");
         session.removeAttribute("website_url");
         session.removeAttribute("phone_number");
         session.removeAttribute("ct_phone_type_id");
         session.removeAttribute("extension");
         session.removeAttribute("fax_number");
         session.removeAttribute("address");
         session.removeAttribute("city");
         session.removeAttribute("ct_province_state_id");
         session.removeAttribute("ct_country_id");
         session.removeAttribute("postal_zip");
         session.removeAttribute("add_to_list");
         session.removeAttribute("ct_package_id");
         session.removeAttribute("ct_term_id");
         session.removeAttribute("ct_credit_card_id");
         session.removeAttribute("card_number");
         session.removeAttribute("name_on_card");
         session.removeAttribute("expiry_month");
         session.removeAttribute("expiry_year");
         session.removeAttribute("billing_address_same");
         session.removeAttribute("billing_address");
         session.removeAttribute("billing_city");
         session.removeAttribute("billing_ct_state_province_id");
         session.removeAttribute("billing_ct_country_id");
         session.removeAttribute("billing_postal_zip");
         session.removeAttribute("company_description");

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessJoinSiteFinal:ProcessForm:Exception:"
                     + e.toString());
      }
      return MasterTemplate;
   }

   /**
    * Extend this class and override this method for custom changes.
    * 
    * @param request
    * @param response
    * @param session
    * @param conn
    * @param MasterTemplate
    * @param hm
    * @return HTMLTemplateDb - the html template object
    */
   public HTMLTemplateDb ProcessCustomFormInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) {

      return MasterTemplate;
   }

   /**
    * Write new customer information to database Extend this class and override
    * this method for client specific enhancements.
    * 
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public void WriteNewCustomerToDatabase(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn) throws Exception {

      String todaysDate = "";
      String nextBillingDate = "";
      String year = "";
      String month = "";
      String day = "";

      try {
         // get today's date, for registration date
         GregorianCalendar theCurrentDate = new GregorianCalendar();

         year = theCurrentDate.get(Calendar.YEAR) + "";
         month = (theCurrentDate.get(Calendar.MONTH) + 1) + "";
         day = theCurrentDate.get(Calendar.DAY_OF_MONTH) + "";

         if (month.length() < 2) {
            month = "0" + month;
         }
         if (day.length() < 2) {
            day = "0" + day;
         }
         todaysDate = year + "-" + month + "-" + day;

         // currently not using next billing date
         // so set it to a dummy value
         nextBillingDate = "2020-01-01";

         Customer myCustomer = new Customer();
         myCustomer.setConn(conn);
         myCustomer.setCt_salutation_id(ct_salutation_id);
         myCustomer.setCustomer_first_name(first_name);
         myCustomer.setCustomer_last_name(last_name);
         myCustomer.setCustomer_company_name(company_name);
         myCustomer.setCustomer_website_url(website_url);
         myCustomer.setCustomer_add_to_mailing_list(add_to_list);
         myCustomer.setCt_term_id(ct_term_id);
         myCustomer.setCt_credit_card_id(ct_credit_card_id);
         myCustomer.setCustomer_credit_card_number(card_number);
         myCustomer.setCustomer_card_expiry_month(expiry_month);
         myCustomer.setCustomer_credit_card_expiry_year(expiry_year);
         myCustomer.setCustomer_name_on_card(name_on_card);
         myCustomer.setCustomer_registration_date(todaysDate);
         myCustomer.setCustomer_next_billing_date(nextBillingDate);
         myCustomer.setCustomer_active_yn("n");
         myCustomer.setCustomer_password(password);
         myCustomer.setCustomer_access_level(2);
         myCustomer.setCt_package_id(ct_package_id);
         myCustomer.setCustomer_company_description(company_description);

         // ------------------------------------------------------------------
         // Do any client specific gets/sets prior to committing new record
         // ------------------------------------------------------------------
         DoCustomGetsSets(request, response, session);

         // write customer to database, and get our new id
         newCustomerId = myCustomer.CustomerAdd();

         // now add address, email, and phone info
         CustomerEmailDetail myEmail = new CustomerEmailDetail();
         myEmail.setConn(conn);
         myEmail.setCustomer_id(newCustomerId);
         myEmail.setCustomer_email(email_address);
         myEmail.setCustomer_email_default_yn("y");
         myEmail.addCustomerEmailDetail();

         // enter address info. 2 entries for everyone
         CustomerAddressDetail myAddress = new CustomerAddressDetail();
         myAddress.setConn(conn);
         myAddress.setCustomer_id(newCustomerId);
         myAddress.setCt_address_type_id(1);
         myAddress.setCustomer_address(address);
         myAddress.setCustomer_town_city(city);
         myAddress.setCt_province_state_id(ct_province_state_id);
         myAddress.setCustomer_zip_postal(postal_zip);
         myAddress.setCt_country_id(ct_country_id);
         myAddress.setPrimary_address_yn("y");
         myAddress.addCustomerAddressDetail();

         myAddress.setCustomer_id(newCustomerId);
         myAddress.setCt_address_type_id(3);
         myAddress.setCustomer_address(billing_address);
         myAddress.setCustomer_town_city(billing_city);
         myAddress.setCt_province_state_id(billing_ct_state_province_id);
         myAddress.setCustomer_zip_postal(billing_postal_zip);
         myAddress.setCt_country_id(billing_ct_country_id);
         myAddress.setPrimary_address_yn("n");
         myAddress.addCustomerAddressDetail();

         // phone - type 4 = fax
         // main number
         CustomerPhoneDetail myPhone = new CustomerPhoneDetail();
         myPhone.setConn(conn);
         myPhone.setCustomer_id(newCustomerId);
         myPhone.setCt_phone_type_id(Integer.parseInt((String) session
               .getAttribute("ct_phone_type_id")));
         myPhone.setCustomer_phone_default_yn("y");
         myPhone.setCustomer_phone(phone_number);
         myPhone.setCustomer_phone_extension(extension);
         myPhone.addCustomerPhoneDetail();

         // fax number, if any
         if (session.getAttribute("fax_number") != null) {
            myPhone.setCustomer_id(newCustomerId);
            myPhone.setCt_phone_type_id(4);
            myPhone.setCustomer_phone_default_yn("n");
            myPhone.setCustomer_phone(fax_number);
            myPhone.setCustomer_phone_extension("");
            myPhone.addCustomerPhoneDetail();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   /**
    * Do custom gets/sets prior to writing to database. Extend this class and
    * override this method for client specific enhancements.
    * 
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public void DoCustomGetsSets(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      try {

      } catch (Exception e) {

      }

   }

   /**
    * Do custom database etc on a per client basis. Extend this class and
    * override this method for client specific enhancements.
    * 
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public void DoAdditionalDatabaseStuff(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn) throws Exception {

      try {

      } catch (Exception e) {

      }

   }
}