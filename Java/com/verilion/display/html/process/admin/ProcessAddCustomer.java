//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-27
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessAddCustomer.java,v $
//Revision 1.4.6.3.10.1  2007/06/11 15:39:57  tcs
//*** empty log message ***
//
//Revision 1.4.6.3  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4.6.2  2005/08/18 18:03:42  tcs
//*** empty log message ***
//
//Revision 1.4.6.1  2005/08/10 00:45:59  tcs
//Changes to dates (using date picker) & url handling
//
//Revision 1.4  2004/11/29 13:48:39  tcs
//Removed fixsql methods, as was put on database tier
//
//Revision 1.3  2004/11/26 13:39:22  tcs
//Added parameters to methods that get overridden
//
//Revision 1.2  2004/11/05 02:14:36  tcs
//Removed debugging info
//
//Revision 1.1  2004/10/27 15:33:34  tcs
//Changes due to workflow
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.CtAccessLevel;
import com.verilion.database.CtCountry;
import com.verilion.database.CtCreditCard;
import com.verilion.database.CtMonth;
import com.verilion.database.CtPackages;
import com.verilion.database.CtPhoneType;
import com.verilion.database.CtProvinceState;
import com.verilion.database.CtSalutation;
import com.verilion.database.CtTerm;
import com.verilion.database.Customer;
import com.verilion.database.CustomerAddressDetail;
import com.verilion.database.CustomerEmailDetail;
import com.verilion.database.CustomerPhoneDetail;
import com.verilion.database.DBUtils;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;
import com.verilion.object.html.Years;

/**
 * Add a customer
 * 
 * <P>
 * October 27, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessAddCustomer extends ProcessPage {

   /**
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      String theMenu = "";
      int year = 0;
      int month = 0;


      try {
         // create month object
         CtMonth myMonth = new CtMonth();
         myMonth.setConn(conn);

         // create drop down list of salutations
         CtSalutation mySalutation = new CtSalutation();
         mySalutation.setConn(conn);
         rs = mySalutation.getAllSalutationNamesIds();

         MasterTemplate.getRecordSet("<!--RECORD-->", "<!--/RECORD-->");

         while (rs.next()) {
            String sal = new Integer(rs.getInt("ct_salutation_id")).toString();
            String sname = rs.getString("ct_salutation_name");
            MasterTemplate.searchReplaceRecordSet("$SID$", sal);
            MasterTemplate.searchReplaceRecordSet("$SNAME$", sname);
            MasterTemplate.insertRecordSet();
         }

         MasterTemplate.replaceRecordSet();

         // create drop down list of provinces/states
         CtProvinceState myProvince = new CtProvinceState();
         myProvince.setConn(conn);
         rs = myProvince.getAllProvinceState();

         MasterTemplate.getRecordSet("<!--RECORD3-->", "<!--/RECORD3-->");

         while (rs.next()) {
            String ctpid = new Integer(rs.getInt("ct_province_state_id"))
                  .toString();
            String ctpn = rs.getString("ct_province_state_name");
            MasterTemplate.searchReplaceRecordSet("$SPID$", ctpid);
            MasterTemplate.searchReplaceRecordSet("$STATEPROVINCE$", ctpn);
            MasterTemplate.insertRecordSet();
         }

         MasterTemplate.replaceRecordSet();

         // create drop down list of countries
         CtCountry myCountry = new CtCountry();
         myCountry.setConn(conn);
         rs = myCountry.getAllCountries();

         MasterTemplate.getRecordSet("<!--RECORD4-->", "<!--/RECORD4-->");

         while (rs.next()) {
            String cid = new Integer(rs.getInt("ct_country_id")).toString();
            String cnm = rs.getString("ct_country_name");
            MasterTemplate.searchReplaceRecordSet("$CID$", cid);
            MasterTemplate.searchReplaceRecordSet("$COUNTRY$", cnm);
            MasterTemplate.insertRecordSet();
         }

         MasterTemplate.replaceRecordSet();

         // create drop down list of available packages
         CtPackages myPackages = new CtPackages();
         myPackages.setConn(conn);
         rs = myPackages.getPackageNames();

         MasterTemplate.getRecordSet("<!--RECORD5-->", "<!--/RECORD5-->");

         while (rs.next()) {
            String pid = new Integer(rs.getInt("ct_package_id")).toString();
            String pname = rs.getString("ct_package_name");
            MasterTemplate.searchReplaceRecordSet("$PID$", pid);
            MasterTemplate.searchReplaceRecordSet("$PACKAGENAME$", pname);
            MasterTemplate.insertRecordSet();
         }

         MasterTemplate.replaceRecordSet();

         // create drop down list of terms
         CtTerm myTerm = new CtTerm();
         myTerm.setConn(conn);
         rs = myTerm.getTerms();

         MasterTemplate.getRecordSet("<!--RECORD6-->", "<!--/RECORD6-->");

         while (rs.next()) {
            String tid = new Integer(rs.getInt("ct_term_id")).toString();
            String tname = rs.getString("ct_term_name");
            MasterTemplate.searchReplaceRecordSet("$TID$", tid);
            MasterTemplate.searchReplaceRecordSet("$TERM$", tname);
            MasterTemplate.insertRecordSet();
         }

         MasterTemplate.replaceRecordSet();

         // create drop down list of credit cards
         CtCreditCard myCard = new CtCreditCard();
         myCard.setConn(conn);
         rs = myCard.getAllCreditCards();

         MasterTemplate.getRecordSet("<!--RECORD7-->", "<!--/RECORD7-->");

         while (rs.next()) {
            String ccid = new Integer(rs.getInt("ct_credit_card_id"))
                  .toString();
            String ccname = rs.getString("ct_credit_card_name");
            MasterTemplate.searchReplaceRecordSet("$CCID$", ccid);
            MasterTemplate.searchReplaceRecordSet("$CREDITCARDNAME$", ccname);
            MasterTemplate.insertRecordSet();
         }

         MasterTemplate.replaceRecordSet();

         // create drop down list of months for credit card
         Calendar cal = Calendar.getInstance();
         month = cal.get(Calendar.MONTH);

         rs = myMonth.getAllMonthNamesIds();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "expiry_month",
               month + 1,
               rs,
               "ct_month_id",
               "ct_month_name",
               -1,
               "");
         MasterTemplate.searchReplace("$DDLBCCEM$", theMenu);

         // create drop down list years for credit card expiry
         year = cal.get(Calendar.YEAR);
         MasterTemplate.searchReplace("$DDLBCCEY$", Years
               .makeDropDownListYears("expiry_year", year, year, year + 20));

         // create drop down list of access levels
         CtAccessLevel myCtAccessLevel = new CtAccessLevel();
         myCtAccessLevel.setConn(conn);
         rs = myCtAccessLevel.getAllAccessLevelNamesIds();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "customer_access_level",
               2,
               rs,
               "ct_access_level_id",
               "ct_access_level_name",
               -1,
               "");
         MasterTemplate.searchReplace("$DDLBAL$", theMenu);

         // create drop down list of years for next billing year, and select
         // this year
//         year = cal.get(Calendar.YEAR);
//         MasterTemplate.searchReplace("$DDLBNBY$", Years.makeDropDownListYears(
//               "next_billing_year",
//               year,
//               year,
//               year + 20));
//
//         // create drop down list of months for next billing and select
//         // this month
//         month = cal.get(Calendar.MONTH);
//         rs = myMonth.getAllMonthNamesIds();
//
//         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
//               "next_billing_month",
//               month + 1,
//               rs,
//               "ct_month_id",
//               "ct_month_name",
//               -1,
//               "");
//         MasterTemplate.searchReplace("$DDLBNBM$", theMenu);
//
//         // create drop down list of day of month for next bill
//         day = cal.get(Calendar.DAY_OF_MONTH);
//
//         MasterTemplate.searchReplace("$DDLBNBD$", DaysOfMonth
//               .makeDropDownListDaysOfMonth("next_billing_day", day));

         // create drop down list of phone types
         CtPhoneType myPhone = new CtPhoneType();
         myPhone.setConn(conn);
         rs = myPhone.getAllPhoneTypesIds();

         MasterTemplate.getRecordSet("<!--RECORD11-->", "<!--/RECORD11-->");

         while (rs.next()) {
            String cid = new Integer(rs.getInt("ct_phone_type_id")).toString();
            String cname = rs.getString("ct_phone_type_name");
            MasterTemplate.searchReplaceRecordSet("$PHONETYPEID$", cid);
            MasterTemplate.searchReplaceRecordSet("$PHONETYPENAME$", cname);
            MasterTemplate.insertRecordSet();
         }

         MasterTemplate.replaceRecordSet();

         AddCustomInfo(request, response, session, conn, MasterTemplate, hm);

         rs.close();
         rs = null;
      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ProcessAddCustomer:BuildPage:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      return MasterTemplate;
   }

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int customer_id = 0;
      int salutation_id = 0;
      String first_name = "";
      String last_name = "";

      String account_created_date = "";
      String next_billing_month = "";
      String next_billing_date = "";
      String company_name = "";
      String email_address = "";
      String customer_password = "";
      String phone_number = "";
      String ext = "";
      String fax_number = "";
      String address = "";
      String city = "";
      int state_province_id = 0;
      int country_id = 0;
      String postal_zip = "";
      int add_to_list = 0;
      int package_id = 0;
      int term_id = 0;
      int credit_card_id = 0;
      String credit_card_number = "";
      int credit_card_expiry_year = 0;
      int credit_card_expiry_month_id = 0;
      String name_on_card = "";
      int billing_address_same = 0;
      String billing_address = "";
      String billing_city = "";
      int billing_address_state_province_id = 0;
      int billing_address_country_id = 0;
      String billing_zip_postal = "";
      String customer_active = "";
      String company_description = "";
      String website_url = "";
      int customer_access_level = 0;

      try {

         DBUtils myDbUtil = new DBUtils();
         // get values passed by form
         salutation_id = Integer.parseInt((String) request
               .getParameter("salutation"));
         first_name = (String) request.getParameter("first_name");
         last_name = (String) request.getParameter("last_name");
         account_created_date = (String) request.getParameter("create_date");
         company_name = (String) request.getParameter("company_name");
         email_address = (String) request.getParameter("email_address");
         customer_password = (String) request.getParameter("customer_password");
         phone_number = (String) request.getParameter("phone_number");
         ext = (String) request.getParameter("ext");
         fax_number = (String) request.getParameter("fax_number");
         address = (String) request.getParameter("address");
         city = (String) request.getParameter("city");
         state_province_id = Integer.parseInt((String) request
               .getParameter("state_province"));
         country_id = Integer
               .parseInt((String) request.getParameter("country"));
         postal_zip = (String) request.getParameter("postal_zip");
         add_to_list = Integer.parseInt((String) request
               .getParameter("add_to_list"));
         package_id = Integer.parseInt((String) request
               .getParameter("the_package"));
         term_id = Integer.parseInt((String) request.getParameter("term"));
         credit_card_number = (String) request.getParameter("card_number");
         credit_card_id = Integer.parseInt((String) request
               .getParameter("credit_card"));
         credit_card_expiry_year = Integer.parseInt((String) request
               .getParameter("expiry_year"));
         credit_card_expiry_month_id = Integer.parseInt((String) request
               .getParameter("expiry_month"));
         name_on_card = (String) request.getParameter("name_on_card");
         billing_address_same = Integer.parseInt((String) request
               .getParameter("billing_same"));

         if (billing_address_same == 0) {
            billing_address = (String) request
                  .getParameter("billing_address_card");
            billing_city = (String) request.getParameter("billing_city_card");
            billing_address_state_province_id = Integer
                  .parseInt((String) request
                        .getParameter("billing_state_province_card"));
            billing_address_country_id = Integer.parseInt((String) request
                  .getParameter("billing_country"));
            billing_zip_postal = (String) request
                  .getParameter("billing_zip_postal_card");
         }

         customer_active = (String) request.getParameter("customer_active");
         company_description = (String) request
               .getParameter("company_description");
         company_description = myDbUtil.replace(
               company_description,
               "\r",
               "<br />");
         website_url = (String) request.getParameter("website_url");
         customer_access_level = Integer.parseInt((String) request
               .getParameter("customer_access_level"));

         // build our date formats
         if (next_billing_month.length() < 2) {
            next_billing_month = "0" + next_billing_month;
         }

         next_billing_date = "";
         
         // set database connection to Customer
         Customer myCustomer = new Customer();
         myCustomer.setConn(conn);

         // set Customer attributes
         myCustomer.setCt_salutation_id(salutation_id);
         myCustomer.setCustomer_first_name(first_name);
         myCustomer.setCustomer_last_name(last_name);
         myCustomer.setCustomer_registration_date(account_created_date);
         myCustomer.setCustomer_next_billing_date(next_billing_date);
         myCustomer.setCustomer_company_name(company_name);
         myCustomer.setCustomer_password(customer_password);
         myCustomer.setCt_package_id(package_id);
         myCustomer.setCt_term_id(term_id);
         myCustomer.setCt_credit_card_id(credit_card_id);
         myCustomer.setCustomer_name_on_card(name_on_card);
         myCustomer
               .setCustomer_credit_card_expiry_year(credit_card_expiry_year);
         myCustomer.setCustomer_card_expiry_month(credit_card_expiry_month_id);
         myCustomer.setCustomer_credit_card_number(credit_card_number);
         myCustomer.setCustomer_active_yn(customer_active);
         myCustomer.setCustomer_company_description(company_description);
         myCustomer.setCustomer_website_url(website_url);
         myCustomer.setCustomer_access_level(customer_access_level);
         myCustomer.setCustomer_add_to_mailing_list(add_to_list);
         myCustomer.setUsername(email_address);

         // Write info to customer table and get our new customer id back
         customer_id = myCustomer.CustomerAdd();

         // Now enter the address information. Billing address is only
         // entered if
         // it is different from the primary address
         CustomerAddressDetail myAddress = new CustomerAddressDetail();
         myAddress.setConn(conn);

         // enter primary_address info into customer_address_detail
         myAddress.setCt_address_type_id(1);
         myAddress.setCustomer_address(address);
         myAddress.setCustomer_town_city(city);
         myAddress.setCt_province_state_id(state_province_id);
         myAddress.setCt_country_id(country_id);
         myAddress.setCustomer_zip_postal(postal_zip);
         myAddress.setPrimary_address_yn("y");
         myAddress.setCustomer_id(customer_id);

         myAddress.addCustomerAddressDetail();

         // enter billing address into customer_address_detail,
         // only if different from main address
         if (billing_address_same == 0) {
            myAddress.setCt_address_type_id(3);
            myAddress.setCustomer_address(billing_address);
            myAddress.setCustomer_town_city(billing_city);
            myAddress
                  .setCt_province_state_id(billing_address_state_province_id);
            myAddress.setCt_country_id(billing_address_country_id);
            myAddress.setCustomer_zip_postal(billing_zip_postal);
            myAddress.setPrimary_address_yn("n");
            myAddress.setCustomer_id(customer_id);

            myAddress.addCustomerAddressDetail();
         } else {
            // billing address same as home, but put it in anyway
            myAddress.setCt_address_type_id(3);
            myAddress.setCustomer_address(address);
            myAddress.setCustomer_town_city(city);
            myAddress.setCt_province_state_id(state_province_id);
            myAddress.setCt_country_id(country_id);
            myAddress.setCustomer_zip_postal(postal_zip);
            myAddress.setPrimary_address_yn("n");
            myAddress.setCustomer_id(customer_id);

            myAddress.addCustomerAddressDetail();
         }

         // enter email information into the customer_email_detail
         CustomerEmailDetail myEmail = new CustomerEmailDetail();
         myEmail.setConn(conn);
         myEmail.setCustomer_id(customer_id);
         myEmail.setCustomer_email(email_address);
         myEmail.setCustomer_email_default_yn("y");

         myEmail.addCustomerEmailDetail();

         // enter phone info to customer_phone_detail
         CustomerPhoneDetail myPhone = new CustomerPhoneDetail();
         myPhone.setConn(conn);

         // Do phone first
         myPhone.setCt_phone_type_id(1);
         myPhone.setCustomer_phone(phone_number);
         myPhone.setCustomer_phone_extension(ext);
         myPhone.setCustomer_phone_default_yn("y");
         myPhone.setCustomer_id(customer_id);

         myPhone.addCustomerPhoneDetail();

         // Now do fax, if applicable
         if (fax_number.length() > 0) {
            myPhone.setCt_phone_type_id(4);
            myPhone.setCustomer_phone(fax_number);
            myPhone.setCustomer_phone_extension("");
            myPhone.setCustomer_phone_default_yn("n");
            myPhone.setCustomer_id(customer_id);

            myPhone.addCustomerPhoneDetail();
         } else {
            myPhone.setCt_phone_type_id(4);
            myPhone.setCustomer_phone("no fax");
            myPhone.setCustomer_phone_extension("");
            myPhone.setCustomer_phone_default_yn("n");
            myPhone.setCustomer_id(customer_id);

            myPhone.addCustomerPhoneDetail();
         }

         // do any client specific stuff
         EditCustomInfo(
               request,
               response,
               session,
               conn,
               MasterTemplate,
               hm,
               customer_id);

         // Generate completion message
         session.setAttribute("Error", "Customer successfully added.");
         response.sendRedirect((String) request.getParameter("cancelPage"));

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("AddCustomer:ProcessForm:Exception:" + e.toString());
      }
      return MasterTemplate;
   }

   /**
    * Extend this class and override this method for client specific
    * enhancements.
    * 
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public void AddCustomInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

   }

   /**
    * Extend this class and override this method for client specific
    * enhancements.
    * 
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public void EditCustomInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm,
         int customer_id) throws Exception {

   }
}