//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessCustomerSelfEdit.java,v $
//Revision 1.1.2.1  2005/01/11 17:14:14  tcs
//Initial entry
//
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
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;
import com.verilion.object.html.Years;

/**
 * Allow a customer to edit their own information
 * 
 * <P>
 * February 13, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessCustomerSelfEdit extends ProcessPage {

   public XDisconnectedRowSet rs = new XDisconnectedRowSet();
   public int customer_id = 0;
   public String theMenu = "";
   public int ct_salutation_id = 0;
   public String customer_first_name = "";
   public String customer_last_name = "";
   public String account_created = "";
   public String customer_email = "";
   public String customer_password = "";
   public String phone_number = "";
   public String extension = "";
   public String fax = "";
   public String address = "";
   public String city = "";
   public int ct_province_state_id = 0;
   public String ct_province_state_name = "";
   public int ct_country_id = 0;
   public String ct_country_name = "";
   public String postal_code = "";
   public String billing_address = "";
   public String billing_city = "";
   public int billing_province_id = 0;
   public String billing_province_name = "";
   public int billing_country_id = 0;
   public String billing_postal_zip = "";
   public String add_to_mailing_list_yn = "";
   public String customer_company_name = "";
   public String customer_website_url = "";
   public String customer_credit_card_number = "";
   public String customer_name_on_card = "";
   public int customer_card_expiry_month = 0;
   public int customer_card_expiry_year = 0;
   public String customer_company_description = "";
   public int ct_package_id = 0;
   public int ct_term_id = 0;
   public int ct_credit_card_id = 0;
   public int customer_access_level = 0;
   public int ct_phone_type_id = 0;
   public String customer_next_billing_date = "";
   public String customer_registration_date = "";

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.display.html.PageDb#BuildPage(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse,
    *      javax.servlet.http.HttpSession)
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
         //customer_id = Integer.parseInt((String) hm.get("id"));
         customer_id = Integer.parseInt((String)session.getAttribute("customer_id"));

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
            }

            // Replace info on form with customer info
            MasterTemplate.searchReplace("$CUSTOMERID$", customer_id + "");
            MasterTemplate.searchReplace("$FIRSTNAME$", customer_first_name);
            MasterTemplate.searchReplace("$LASTNAME$", customer_last_name);
            MasterTemplate.searchReplace("$COMPANYNAME$", customer_company_name);
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

            // write the package to the screen (not editable)
            CtPackages myPackages = new CtPackages();
            myPackages.setConn(conn);
            myPackages.setCt_package_id(ct_package_id);
            MasterTemplate.searchReplace("$DDLBPACKAGE$", myPackages
                  .getPackageNameById());

            // write term to screen (not editable)
            CtTerm myTerm = new CtTerm();
            myTerm.setConn(conn);
            myTerm.setCt_term_id(ct_term_id);
            MasterTemplate
                  .searchReplace("$DDLBTERM$", myTerm.getTermNameById());

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

            // Show access level (read only)
            CtAccessLevel myCtAccessLevel = new CtAccessLevel();
            myCtAccessLevel.setConn(conn);
            myCtAccessLevel.setCt_access_level_id(customer_access_level);
            String myAccessLevel = myCtAccessLevel.getAccessLevelName();
            MasterTemplate
                  .searchReplace(
                        "$ACCESSLEVEL$",
                        myAccessLevel);

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

            EditCustomInfo(request, response, session, conn, MasterTemplate, hm);

            rs.close();
            rs = null;
         } else {
            // Get info from calling form/session

			// we get the id from session instead of form so that people can't
			// fake their identity
			customer_id = Integer.parseInt((String) session
					.getAttribute("customer_id"));
			
			// get our customer record as it currently exists so that we don't
			// accidentally overwrite the info that they are not allowed to
			// change.
			Customer myCustomer = new Customer();
			myCustomer.setConn(conn);
			myCustomer.setCustomer_id(customer_id);
			rs = myCustomer.GetOneCustomerFromView();

			// extract info from customer table to local variables
			if (rs.next()) {
				customer_registration_date = rs
						.getString("customer_registration_date");
				customer_next_billing_date = rs
						.getString("customer_next_billing_date");
				ct_package_id = rs.getInt("ct_package_id");
				ct_term_id = rs.getInt("ct_term_id");
				customer_access_level = rs.getInt("customer_access_level");
			}

			// now get info from the calling form
			ct_salutation_id = Integer.parseInt((String) request
					.getParameter("ct_salutation_id"));
			customer_first_name = (String) request.getParameter("first_name");
			customer_last_name = (String) request.getParameter("last_name");

			customer_company_name = (String) request.getParameter("company_name");
			customer_email = (String) request.getParameter("email_address");
			customer_password = (String) request
					.getParameter("customer_password");
			customer_website_url = (String) request.getParameter("website_url");
			phone_number = (String) request.getParameter("phone_number");
			ct_phone_type_id = Integer.parseInt((String) request
					.getParameter("ct_phone_type_id"));
			if (request.getParameter("ext") != null) {
				extension = (String) request.getParameter("ext");
			} else {
				extension = "no fax number";
			}
			fax = (String) request.getParameter("fax_number");
			address = (String) request.getParameter("address");
			city = (String) request.getParameter("city");
			ct_province_state_id = Integer.parseInt((String) request
					.getParameter("customer_state_province_id"));
			ct_country_id = Integer.parseInt((String) request
					.getParameter("ct_country_id"));
			postal_code = (String) request.getParameter("postal_zip");
			ct_credit_card_id = Integer.parseInt((String) request
					.getParameter("ct_credit_card_id"));
			customer_credit_card_number = (String) request.getParameter("card_number");
			customer_card_expiry_month = Integer
					.parseInt((String) request
							.getParameter("customer_credit_card_expiry_month"));
			customer_card_expiry_year = Integer.parseInt((String) request
					.getParameter("card_expiry_year"));
			customer_name_on_card = (String) request.getParameter("name_on_card");
			billing_address = (String) request
					.getParameter("billing_address_card");
			billing_city = (String) request
					.getParameter("billing_city_card");
			billing_province_id = Integer
					.parseInt((String) request
							.getParameter("billing_customer_state_province_id"));
			billing_country_id = Integer.parseInt((String) request
					.getParameter("billing_ct_country_id"));
			billing_postal_zip = (String) request
					.getParameter("billing_zip_postal_card");
			
			customer_company_description = (String) request
					.getParameter("company_description");

			// We have our info from calling form, so now update the necesary
			// tables.

			// update the customer table
			myCustomer.setCustomer_id(customer_id);
			myCustomer.setCustomer_first_name(customer_first_name);
			myCustomer.setCustomer_last_name(customer_last_name);
			myCustomer.setCt_salutation_id(ct_salutation_id);
			myCustomer.setCustomer_registration_date(customer_registration_date);
			myCustomer.setCustomer_next_billing_date(customer_next_billing_date);
			myCustomer.setCustomer_company_name(customer_company_name);
			myCustomer.setCustomer_password(customer_password);
			myCustomer.setCustomer_website_url(customer_website_url);
			myCustomer.setCt_package_id(ct_package_id);
			myCustomer.setCt_term_id(ct_term_id);
			myCustomer.setCt_credit_card_id(ct_credit_card_id);
			myCustomer.setCustomer_credit_card_number(customer_credit_card_number);
			myCustomer.setCustomer_credit_card_expiry_year(customer_card_expiry_year);
			myCustomer.setCustomer_card_expiry_month(customer_card_expiry_month);
			myCustomer.setCustomer_name_on_card(customer_name_on_card);
			myCustomer.setCustomer_active_yn("y");
			myCustomer.setCustomer_access_level(customer_access_level);
			myCustomer.setCustomer_company_description(customer_company_description);

			myCustomer.CustomerUpdate();

			// now update the customer address detail table

			// first do primary address
			CustomerAddressDetail myAddress = new CustomerAddressDetail();
			myAddress.setConn(conn);
			myAddress.setCustomer_id(customer_id);
			myAddress.setCt_address_type_id(1);
			myAddress.setCustomer_address(address);
			myAddress.setCustomer_town_city(city);
			myAddress.setCt_province_state_id(ct_province_state_id);
			myAddress.setCt_country_id(ct_country_id);
			myAddress.setCustomer_zip_postal(postal_code);
			myAddress.setPrimary_address_yn("y");
			myAddress.updateCustomerAddressByCustomerIdAndCtAddressTypeId();

			// now do billing address
			myAddress.setCustomer_id(customer_id);
			myAddress.setCt_address_type_id(3);
			myAddress.setCustomer_address(billing_address);
			myAddress.setCustomer_town_city(billing_city);
			myAddress.setCt_province_state_id(billing_province_id);
			myAddress.setCt_country_id(billing_country_id);
			myAddress.setCustomer_zip_postal(billing_postal_zip);
			myAddress.setPrimary_address_yn("n");
			myAddress.updateCustomerAddressByCustomerIdAndCtAddressTypeId();

			// now update email table
			CustomerEmailDetail myEmail = new CustomerEmailDetail();
			myEmail.setConn(conn);
			myEmail.setCustomer_id(customer_id);
			myEmail.setCustomer_email(customer_email);
			myEmail.updateCustomerEmailDetailPrimaryEmail();

			// now do the phone numbers
			CustomerPhoneDetail myPhone = new CustomerPhoneDetail();
			myPhone.setConn(conn);
			myPhone.setCustomer_id(customer_id);

			// first do the primary phone number
			myPhone.setCustomer_phone(phone_number);
			myPhone.setCt_phone_type_id(ct_phone_type_id);
			myPhone.setCustomer_phone_default_yn("y");
			myPhone.setCustomer_phone_extension(extension);
			myPhone.updateCustomerPhoneDetailPrimaryPhoneNumber();

			// now do fax, if any (it'll write "no fax" if there isn't one)
			myPhone.setCustomer_phone(fax);
			myPhone.setCustomer_phone_extension("");
			myPhone.updateCustomerPhoneDetailFaxNumber();

			// do any client specific stuff
			ProcessCustomFormInfo(request, response, session, conn, MasterTemplate, hm);
			
			rs.close();
			rs = null;

			// Generate completion message
			session.setAttribute("Error", "Information successfully modified.");
			response.sendRedirect(request.getRequestURI() + "?edit=false");
         }

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.admin.EditCustomer:BuildPage:Exception:"
                     + e.toString());
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
   public void EditCustomInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

   }
   
   public void ProcessCustomFormInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {
      
   }
}