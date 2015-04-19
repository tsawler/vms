//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-22
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditCustomer.java,v $
//Revision 1.5.2.1.4.5.10.1  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.5.2.1.4.5  2005/08/19 13:46:29  tcs
//added customer_registration_date (it was lost in last rev)
//
//Revision 1.5.2.1.4.4  2005/08/18 18:03:37  tcs
//*** empty log message ***
//
//Revision 1.5.2.1.4.3  2005/08/18 16:54:44  tcs
//Simplified dates
//
//Revision 1.5.2.1.4.2  2005/08/12 16:56:25  tcs
//Changed way dates are handled
//
//Revision 1.5.2.1.4.1  2005/08/10 00:45:48  tcs
//Changes to dates (using date picker) & url handling
//
//Revision 1.5.2.1  2004/12/02 16:50:17  tcs
//Added parameters to methods that are to be overriden on subclasses
//
//Revision 1.5  2004/10/27 15:33:34  tcs
//Changes due to workflow
//
//Revision 1.4  2004/10/27 11:48:54  tcs
//Changes due to refactoring
//
//Revision 1.3  2004/10/22 16:09:42  tcs
//Moved some members around. Still have to eliminate duplicated members
//
//Revision 1.2  2004/10/22 16:04:31  tcs
//Added overrideable methods (for classes that extend this one)
//
//Revision 1.1  2004/10/22 15:34:51  tcs
//initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
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
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.html.HTMLFormDropDownList;
import com.verilion.object.html.Years;

/**
 * Edit customers
 * 
 * <P>
 * October 22, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessEditCustomer extends ProcessPage {

   XDisconnectedRowSet rs = new XDisconnectedRowSet();
   int customer_id = 0;
   String theMenu = "";
   int ct_salutation_id = 0;
   String customer_first_name = "";
   String customer_last_name = "";
   String account_created = "";
   String next_billing_date = "";
   int next_billing_year = 0;
   int next_billing_month = 0;
   int next_billing_day = 0;
   String registration_date = "";

   String customer_email = "";
   String customer_password = "";
   String phone_number = "";
   String extension = "";
   String fax = "";
   String address = "";
   String city = "";
   int ct_province_state_id = 0;
   String ct_province_state_name = "";
   int ct_country_id = 0;
   String ct_country_name = "";
   String postal_code = "";
   String billing_address = "";
   String billing_city = "";
   int billing_province_id = 0;
   String billing_province_name = "";
   int billing_country_id = 0;
   String billing_postal_zip = "";
   String add_to_mailing_list_yn = "";
   String customer_company_name = "";
   String customer_website_url = "";
   String customer_credit_card_number = "";
   String customer_name_on_card = "";
   int customer_card_expiry_month = 0;
   int customer_card_expiry_year = 0;
   String customer_company_description = "";
   int ct_package_id = 0;
   int ct_term_id = 0;
   int ct_credit_card_id = 0;
   int customer_access_level = 0;
   int ct_phone_type_id = 0;
   String customer_active_yn = "";
   String customer_isnew_yn = "";
   String first_name = "";
   String last_name = "";
   String customer_registration_date = "";
   String customer_next_billing_date = "";
   String company_name = "";
   String email_address = "";
   String website_url = "";
   String ext = "";
   String fax_number = "";
   int customer_state_province_id = 0;
   String postal_zip = "";
   int add_to_list = 0;
   String card_number = "";
   int customer_credit_card_expiry_month = 0;
   int card_expiry_year = 0;
   String name_on_card = "";
   String billing_address_card = "";
   String billing_city_card = "";
   int billing_customer_state_province_id = 0;
   int billing_ct_country_id = 0;
   String billing_zip_postal_card = "";
   String customer_active = "";
   int ct_access_level_id = 0;
   String company_description = "";

   /**
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      try {
         // get our customer id, passed as parameter
         customer_id = Integer.parseInt((String) hm.get("id"));

         // decide whether we are processing this form,
         // or displaying it
         if (((String) request.getParameter("edit")).equals("false")) {
            // get our customer record
            Customer myCustomer = new Customer();
            myCustomer.setConn(conn);
            myCustomer.setCustomer_id(customer_id);
            rs = myCustomer.GetOneCustomerFromView();

            // extract info from customer table to local variables
            if (rs.next()) {
               ct_salutation_id = rs.getInt("ct_salutation_id");
               customer_first_name = rs.getString("customer_first_name");
               customer_last_name = rs.getString("customer_last_name");
               customer_company_name = rs.getString("customer_company_name");
               customer_active_yn = rs.getString("customer_active_yn");
               customer_isnew_yn = rs.getString("customer_isnew_yn");
               next_billing_date = rs.getString("customer_next_billing_date");
               customer_email = rs.getString("customer_email");
               customer_website_url = rs.getString("customer_website_url");
               customer_credit_card_number = rs
                     .getString("customer_credit_card_number");
               customer_card_expiry_month = rs
                     .getInt("customer_card_expiry_month");
               customer_card_expiry_year = rs
                     .getInt("customer_credit_card_expiry_year");
               customer_password = rs.getString("customer_password");
               customer_company_description = rs
                     .getString("customer_company_description");
               ct_package_id = rs.getInt("ct_package_id");
               ct_term_id = rs.getInt("ct_term_id");
               ct_credit_card_id = rs.getInt("ct_credit_card_id");
               customer_access_level = rs.getInt("customer_access_level");
               address = rs.getString("customer_address");
               city = rs.getString("customer_town_city");
               ct_province_state_id = rs.getInt("ct_province_state_id");
               ct_country_id = rs.getInt("ct_country_id");
               postal_code = rs.getString("customer_zip_postal");
               billing_address = rs.getString("billing_address");
               billing_city = rs.getString("billing_city");
               billing_province_id = rs.getInt("billing_province_id");
               billing_country_id = rs.getInt("billing_country_id");
               billing_postal_zip = rs.getString("billing_postal");
               phone_number = rs.getString("customer_phone");
               extension = rs.getString("extension");
               fax = rs.getString("fax");
               customer_name_on_card = rs.getString("customer_name_on_card");
               registration_date = rs.getString("customer_registration_date");
               add_to_list = rs.getInt("customer_add_to_mailing_list");
            }

            // Replace info on form with customer info
            MasterTemplate.searchReplace("$CUSTOMERID$", customer_id + "");
            MasterTemplate.searchReplace("$FIRSTNAME$", customer_first_name);
            MasterTemplate.searchReplace("$LASTNAME$", customer_last_name);
            MasterTemplate
                  .searchReplace("$COMPANYNAME$", customer_company_name);
            MasterTemplate.searchReplace("$URL$", customer_website_url);
            MasterTemplate.searchReplace("$PHONE$", phone_number);
            MasterTemplate.searchReplace("$FAX$", fax);
            MasterTemplate.searchReplace("$EXT$", extension);
            MasterTemplate.searchReplace("$ADDRESS$", address);
            MasterTemplate.searchReplace("$CITY$", city);
            MasterTemplate.searchReplace("$POSTAL$", postal_code);
            MasterTemplate.searchReplace("$EMAIL$", customer_email);
            MasterTemplate.searchReplace(
                  "$CARDNUMBER$",
                  customer_credit_card_number);
            MasterTemplate.searchReplace("$NAMEONCARD$", customer_name_on_card);
            MasterTemplate.searchReplace("$PASSWORD$", customer_password);
            MasterTemplate.searchReplace(
                  "$COMPANYDESC$",
                  customer_company_description);
            MasterTemplate.searchReplace("$BILLADDRESS$", billing_address);
            MasterTemplate.searchReplace("$BILLCITY$", billing_city);
            MasterTemplate.searchReplace("$BILLPOSTAL$", billing_postal_zip);
            
            if (add_to_list == 1) {
            	MasterTemplate.searchReplace("$ATLY$", "selected");
            	MasterTemplate.searchReplace("$ATLN$", "");
            } else {
            	MasterTemplate.searchReplace("$ATLY$", "");
            	MasterTemplate.searchReplace("$ATLN$", "selected");
            }

            // now build the drop down menus and select correct values for
            // each, based on info from database

            // build salutation menu and select correct value
            CtSalutation mySalutation = new CtSalutation();
            mySalutation.setConn(conn);
            rs = mySalutation.getAllSalutationNamesIds();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_salutation_id",
                  ct_salutation_id,
                  rs,
                  "ct_salutation_id",
                  "ct_salutation_name",
                  -1,
                  "");
            MasterTemplate.searchReplace("$DDLBSAL$", theMenu);

            // create drop down list of months for credit card
            CtMonth myMonth = new CtMonth();
            myMonth.setConn(conn);
            rs = myMonth.getAllMonthNamesIds();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "customer_credit_card_expiry_month",
                  customer_card_expiry_month,
                  rs,
                  "ct_month_id",
                  "ct_month_name",
                  -1,
                  "");
            MasterTemplate.searchReplace("$CCMONTH$", theMenu);

            // create drop down list of years for card expiry
            MasterTemplate.searchReplace("$CARDYEAR$", Years
                  .makeDropDownListYears(
                        "card_expiry_year",
                        customer_card_expiry_year,
                        1970,
                        2020));

            MasterTemplate.searchReplace("$CREATEDATE$", registration_date);
            MasterTemplate.searchReplace("$BILLDATE$", next_billing_date);
            
            // create drop down list of provinces/states
            CtProvinceState myProvince = new CtProvinceState();
            myProvince.setConn(conn);
            rs = myProvince.getAllProvinceState();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "customer_state_province_id",
                  ct_province_state_id,
                  rs,
                  "ct_province_state_id",
                  "ct_province_state_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBPROV$", theMenu);

            // create drop down list of provinces/states for billing address
            rs = myProvince.getAllProvinceState();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "billing_customer_state_province_id",
                  billing_province_id,
                  rs,
                  "ct_province_state_id",
                  "ct_province_state_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBBILLPROV$", theMenu);

            // create drop down list of countries
            CtCountry myCountry = new CtCountry();
            myCountry.setConn(conn);
            rs = myCountry.getAllCountries();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_country_id",
                  ct_country_id,
                  rs,
                  "ct_country_id",
                  "ct_country_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBCOUNTRY$", theMenu);

            // create drop down list of countries for billing address

            rs = myCountry.getAllCountries();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "billing_ct_country_id",
                  billing_country_id,
                  rs,
                  "ct_country_id",
                  "ct_country_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBBILLCOUNTRY$", theMenu);

            // create drop down list of available packages
            CtPackages myPackages = new CtPackages();
            myPackages.setConn(conn);
            rs = myPackages.getPackageNames();
            
            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_package_id",
                  ct_package_id,
                  rs,
                  "ct_package_id",
                  "ct_package_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBPACKAGE$", theMenu);

            // create drop down list of terms
            CtTerm myTerm = new CtTerm();
            myTerm.setConn(conn);
            rs = myTerm.getTerms();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_term_id",
                  ct_term_id,
                  rs,
                  "ct_term_id",
                  "ct_term_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBTERM$", theMenu);

            // create drop down list of credit cards
            CtCreditCard myCard = new CtCreditCard();
            myCard.setConn(conn);
            rs = myCard.getAllCreditCards();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_credit_card_id",
                  ct_credit_card_id,
                  rs,
                  "ct_credit_card_id",
                  "ct_credit_card_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBCREDIT$", theMenu);

            // create drop down list of access levels
            CtAccessLevel myCtAccessLevel = new CtAccessLevel();
            myCtAccessLevel.setConn(conn);
            rs = myCtAccessLevel.getAllAccessLevelNamesIds();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_access_level_id",
                  customer_access_level,
                  rs,
                  "ct_access_level_id",
                  "ct_access_level_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBACCESSLEVEL$", theMenu);

            // create drop down list of phone types
            CtPhoneType myPhone = new CtPhoneType();
            myPhone.setConn(conn);
            rs = myPhone.getAllPhoneTypesIds();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_phone_type_id",
                  ct_phone_type_id,
                  rs,
                  "ct_phone_type_id",
                  "ct_phone_type_name",
                  0,
                  "");
            MasterTemplate.searchReplace("$DDLBPHONETYPE$", theMenu);

            // is customer active?
            if (customer_active_yn.equals("y")) {
               MasterTemplate.searchReplace("$CUSTACTIVEY$", "selected");
               MasterTemplate.searchReplace("$CUSTACTIVEN$", "");
               MasterTemplate.searchReplace("$CUSTACTIVEP$", "");
            } else if (customer_active_yn.equals("n")){
               MasterTemplate.searchReplace("$CUSTACTIVEY$", "");
               MasterTemplate.searchReplace("$CUSTACTIVEN$", "selected");
               MasterTemplate.searchReplace("$CUSTACTIVEP$", "");
            } else {
               MasterTemplate.searchReplace("$CUSTACTIVEY$", "");
               MasterTemplate.searchReplace("$CUSTACTIVEN$", "");
               MasterTemplate.searchReplace("$CUSTACTIVEP$", "selected");
            }

            // set hidden field to determine whether this is a new customer or
            // not
            MasterTemplate.searchReplace(
                  "$ISNEW$",
                  "<input type=\"hidden\" name=\"isnew\" value=\""
                        + customer_active_yn
                        + "\"");

            MasterTemplate = AddCustomInfo(
                  request,
                  response,
                  session,
                  conn,
                  MasterTemplate,
                  hm);

            rs.close();
            rs = null;
         } else {
            //try {
            // Get info from calling form
            ct_salutation_id = Integer.parseInt((String) request
                  .getParameter("ct_salutation_id"));
            first_name = (String) request.getParameter("first_name");
            last_name = (String) request.getParameter("last_name");
            customer_isnew_yn = (String) request.getParameter("isnew");
            customer_registration_date = (String) request.getParameter("create_date");
            customer_next_billing_date = (String) request.getParameter("next_billing_date");
            company_name = (String) request.getParameter("company_name");
            email_address = (String) request.getParameter("email_address");
            customer_password = (String) request
                  .getParameter("customer_password");
            website_url = (String) request.getParameter("website_url");
            phone_number = (String) request.getParameter("phone_number");
            ct_phone_type_id = Integer.parseInt((String) request
                  .getParameter("ct_phone_type_id"));
            add_to_list = Integer.parseInt((String) request.getParameter("add_to_list"));
            if (request.getParameter("ext") != null) {
               ext = (String) request.getParameter("ext");
            } else {
               ext = "no fax number";
            }
            fax_number = (String) request.getParameter("fax_number");
            address = (String) request.getParameter("address");
            city = (String) request.getParameter("city");
            customer_state_province_id = Integer.parseInt((String) request
                  .getParameter("customer_state_province_id"));
            ct_country_id = Integer.parseInt((String) request
                  .getParameter("ct_country_id"));
            postal_zip = (String) request.getParameter("postal_zip");
            ct_package_id = Integer.parseInt((String) request
                  .getParameter("ct_package_id"));
            ct_term_id = Integer.parseInt((String) request
                  .getParameter("ct_term_id"));
            ct_credit_card_id = Integer.parseInt((String) request
                  .getParameter("ct_credit_card_id"));
            card_number = (String) request.getParameter("card_number");
            customer_credit_card_expiry_month = Integer
                  .parseInt((String) request
                        .getParameter("customer_credit_card_expiry_month"));
            card_expiry_year = Integer.parseInt((String) request
                  .getParameter("card_expiry_year"));
            name_on_card = (String) request.getParameter("name_on_card");
            billing_address_card = (String) request
                  .getParameter("billing_address_card");
            billing_city_card = (String) request
                  .getParameter("billing_city_card");
            billing_customer_state_province_id = Integer
                  .parseInt((String) request
                        .getParameter("billing_customer_state_province_id"));
            billing_ct_country_id = Integer.parseInt((String) request
                  .getParameter("billing_ct_country_id"));
            billing_zip_postal_card = (String) request
                  .getParameter("billing_zip_postal_card");
            customer_active = (String) request.getParameter("customer_active");
            ct_access_level_id = Integer.parseInt((String) request
                  .getParameter("ct_access_level_id"));
            company_description = (String) request
                  .getParameter("company_description");

            // We have our info from calling form, so now update the necesary
            // tables.

            // update the customer table
            Customer myCustomer = new Customer();
            myCustomer.setConn(conn);
            myCustomer.setCustomer_id(customer_id);
            myCustomer.setCustomer_first_name(first_name);
            myCustomer.setCustomer_last_name(last_name);
            myCustomer.setCt_salutation_id(ct_salutation_id);
            myCustomer
                  .setCustomer_registration_date(customer_registration_date);
            myCustomer
                  .setCustomer_next_billing_date(customer_next_billing_date);
            myCustomer.setCustomer_company_name(company_name);
            myCustomer.setCustomer_password(customer_password);
            myCustomer.setCustomer_website_url(website_url);
            myCustomer.setCt_package_id(ct_package_id);
            myCustomer.setCt_term_id(ct_term_id);
            myCustomer.setCt_credit_card_id(ct_credit_card_id);
            myCustomer.setCustomer_credit_card_number(card_number);
            myCustomer.setCustomer_credit_card_expiry_year(card_expiry_year);
            myCustomer
                  .setCustomer_card_expiry_month(customer_credit_card_expiry_month);
            myCustomer.setCustomer_name_on_card(name_on_card);
            myCustomer.setCustomer_active_yn(customer_active);
            myCustomer.setCustomer_access_level(ct_access_level_id);
            myCustomer.setCustomer_company_description(company_description);
            myCustomer.setCustomer_add_to_mailing_list(add_to_list);

            // if we make a change to this record, the customer is considered
            // processed
            myCustomer.setCustomer_isnew_yn("n");

            myCustomer.CustomerUpdate();

            // now update the customer address detail table

            // first do primary address
            CustomerAddressDetail myAddress = new CustomerAddressDetail();
            myAddress.setConn(conn);
            myAddress.setCustomer_id(customer_id);
            myAddress.setCt_address_type_id(1);
            myAddress.setCustomer_address(address);
            myAddress.setCustomer_town_city(city);
            myAddress.setCt_province_state_id(customer_state_province_id);
            myAddress.setCt_country_id(ct_country_id);
            myAddress.setCustomer_zip_postal(postal_zip);
            myAddress.setPrimary_address_yn("y");
            myAddress.updateCustomerAddressByCustomerIdAndCtAddressTypeId();

            // now do billing address
            myAddress.setCustomer_id(customer_id);
            myAddress.setCt_address_type_id(3);
            myAddress.setCustomer_address(billing_address_card);
            myAddress.setCustomer_town_city(billing_city_card);
            myAddress
                  .setCt_province_state_id(billing_customer_state_province_id);
            myAddress.setCt_country_id(billing_ct_country_id);
            myAddress.setCustomer_zip_postal(billing_zip_postal_card);
            myAddress.setPrimary_address_yn("n");
            myAddress.updateCustomerAddressByCustomerIdAndCtAddressTypeId();

            // now update email table
            CustomerEmailDetail myEmail = new CustomerEmailDetail();
            myEmail.setConn(conn);
            myEmail.setCustomer_id(customer_id);
            myEmail.setCustomer_email(email_address);
            myEmail.updateCustomerEmailDetailPrimaryEmail();

            // now do the phone numbers
            CustomerPhoneDetail myPhone = new CustomerPhoneDetail();
            myPhone.setConn(conn);
            myPhone.setCustomer_id(customer_id);

            // first do the primary phone number
            myPhone.setCustomer_phone(phone_number);
            myPhone.setCt_phone_type_id(ct_phone_type_id);
            myPhone.setCustomer_phone_default_yn("y");
            myPhone.setCustomer_phone_extension(ext);
            myPhone.updateCustomerPhoneDetailPrimaryPhoneNumber();

            // now do fax, if any (it'll write "no fax" if there isn't one)
            myPhone.setCustomer_phone(fax_number);
            myPhone.setCustomer_phone_extension("");
            myPhone.updateCustomerPhoneDetailFaxNumber();

            // do any client specific stuff
            MasterTemplate = EditCustomInfo(
                  request,
                  response,
                  session,
                  conn,
                  MasterTemplate,
                  hm);

            // Generate completion message
            session.setAttribute("Error", "Customer successfully modified.");
            response.sendRedirect((String) request.getParameter("cancelPage"));
         } 
      } catch (Exception e) {
         System.out.println("Error: " + e.toString());
         e.printStackTrace();
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
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
   public HTMLTemplateDb AddCustomInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

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
   public HTMLTemplateDb EditCustomInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      return MasterTemplate;

   }
}