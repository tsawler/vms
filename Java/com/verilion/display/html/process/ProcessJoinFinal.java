//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-20
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessJoinFinal.java,v $
//Revision 1.5.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.5  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.4  2004/10/26 15:41:34  tcs
//Improved javadocs
//
//Revision 1.3  2004/10/20 17:28:58  tcs
//In progress...
//
//Revision 1.2  2004/10/20 16:51:39  tcs
//In progress...
//
//Revision 1.1  2004/10/20 16:17:45  tcs
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
 * Final step in signup
 * 
 * <P>
 * October 20, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 *  
 */
public class ProcessJoinFinal extends ProcessPage {

   private int ct_salutation_id = 0;
   private String first_name = "";
   private String last_name = "";
   private String company_name = "";
   private String email_address = "";
   private String password = "";
   private String website_url = "";
   private String phone_number = "";
   private String extension = "";

   private String fax_number = "";
   private String address = "";
   private String city = "";
   private int ct_province_state_id = 0;
   private int ct_country_id = 0;
   private String postal_zip = "";
   private int add_to_list = 0;
   private int ct_package_id = 0;
   private int ct_term_id = 0;
   private int ct_credit_card_id = 0;
   private String card_number = "";
   private int expiry_month = 0;
   private int expiry_year = 0;
   private String name_on_card = "";
   private int billing_address_same = 0;
   private String billing_address = "";
   private String billing_city = "";
   private int billing_ct_state_province_id = 0;
   private int billing_ct_country_id = 0;
   private String billing_postal_zip = "";
   private String company_description = "";
   private int newCustomerId = 0;

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      try {

         // get values from calling form
         ct_salutation_id = Integer.parseInt((String) request
               .getParameter("salutation"));
         first_name = (String) request.getParameter("first_name");
         last_name = (String) request.getParameter("last_name");
         company_name = (String) request.getParameter("company_name");
         email_address = (String) request.getParameter("email_address");
         password = (String) request.getParameter("customer_password");
         website_url = (String) request.getParameter("website_url");
         phone_number = (String) request.getParameter("phone_number");

         extension = (String) request.getParameter("ext");
         fax_number = (String) request.getParameter("fax_number");
         address = (String) request.getParameter("address");
         city = (String) request.getParameter("city");
         ct_province_state_id = Integer.parseInt((String) request
               .getParameter("state_province"));
         ct_country_id = Integer.parseInt((String) request
               .getParameter("country"));
         postal_zip = (String) request.getParameter("postal_zip");
         add_to_list = Integer.parseInt((String) request
               .getParameter("add_to_list"));
         ct_package_id = Integer.parseInt((String) request
               .getParameter("the_package"));
         ct_term_id = Integer.parseInt((String) request.getParameter("term"));
         ct_credit_card_id = Integer.parseInt((String) request
               .getParameter("credit_card"));
         card_number = (String) request.getParameter("card_number");
         expiry_month = Integer.parseInt((String) request
               .getParameter("expiry_month"));
         expiry_year = Integer.parseInt((String) request
               .getParameter("expiry_year"));
         name_on_card = (String) request.getParameter("name_on_card");
         billing_address_same = Integer.parseInt((String) request
               .getParameter("billing_same"));

         // if billing address diff, get values from parameters
         if (billing_address_same == 0) {
            billing_address = (String) request
                  .getParameter("billing_address_card");
            billing_city = (String) request.getParameter("billing_city_card");
            billing_ct_state_province_id = Integer.parseInt((String) request
                  .getParameter("billing_state_province_card"));
            billing_ct_country_id = Integer.parseInt((String) request
                  .getParameter("billing_country"));
            billing_postal_zip = (String) request
                  .getParameter("billing_zip_postal_card");
         } else {
            billing_address = address;
            billing_city = city;
            billing_ct_state_province_id = ct_province_state_id;
            billing_ct_country_id = ct_country_id;
         }

         company_description = (String) request
               .getParameter("company_description");

         //------------------------------------------------------------------
         // Calculate price and taxes
         //------------------------------------------------------------------
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



         
         String todaysDate = "";
 		String nextBillingDate = "";
 		String year = "";
 		String month = "";
 		String day = "";

 		try {
 			// get today's date, for registration date
 			GregorianCalendar theCurrentDate = new GregorianCalendar();

 			year = theCurrentDate.get(Calendar.YEAR) + "";
 			month = theCurrentDate.get(Calendar.MONTH) + "";
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
 		
 		// send email notification to site owner
		SendMessage.setTo(SingletonObjects.getInstance().getAdmin_email());
		SendMessage.setFrom(SingletonObjects.getInstance().getAdmin_email());
		SendMessage.setSubject("new hosting signup");
		SendMessage.setMessage("There is a new hosting signup!");
		try {
			SendMessage.SendEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// send email notification to customer
		SendMessage.setTo(email_address);
		SendMessage.setFrom(SingletonObjects.getInstance().getAdmin_email());
		SendMessage.setSubject("Confirmation");
		SendMessage.setMessage("Thanks for joining our site. You will recieve additional instructions via email shortly.");
		try {
			SendMessage.SendEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessJoinFinal:ProcessForm:Exception:"
                     + e.toString());
      }
      return MasterTemplate;
   }
}