//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessJoinSite.java,v $
//Revision 1.8.2.2.4.2.6.2  2006/10/05 15:52:30  tcs
//Added discount code
//
//Revision 1.8.2.2.4.2.6.1  2006/06/11 22:39:02  tcs
//Fixed logic error
//
//Revision 1.8.2.2.4.2  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.8.2.2.4.1  2005/08/16 18:35:36  tcs
//Changed redirect URL as per jsp templates
//
//Revision 1.8.2.2  2005/03/08 16:46:44  tcs
//Javadoc fix
//
//Revision 1.8.2.1  2004/12/01 02:19:07  tcs
//General code improvement
//
//Revision 1.8  2004/11/29 18:35:02  tcs
//Additional validation
//
//Revision 1.7  2004/11/29 18:20:01  tcs
//Added AddCustomInfo to ProcessRejectedForm
//
//Revision 1.6  2004/11/29 17:48:07  tcs
//Added AddCustomInfo method for custom apps to override
//
//Revision 1.5  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.4  2004/10/26 15:41:34  tcs
//Improved javadocs
//
//Revision 1.3  2004/10/20 16:17:05  tcs
//Corrected handling of billing address
//
//Revision 1.2  2004/10/19 17:21:04  tcs
//Added routine to repopulate form
//
//Revision 1.1  2004/10/18 18:30:13  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.CtCountry;
import com.verilion.database.CtCreditCard;
import com.verilion.database.CtMonth;
import com.verilion.database.CtPackages;
import com.verilion.database.CtPhoneType;
import com.verilion.database.CtProvinceState;
import com.verilion.database.CtSalutation;
import com.verilion.database.CtTerm;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;
import com.verilion.object.html.Years;

/**
 * Form to join site
 * 
 * <P>
 * October 18, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class ProcessJoinSite extends ProcessPage {

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.display.html.ProcessPage#BuildPage(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse,
    *      javax.servlet.http.HttpSession, java.sql.Connection,
    *      com.verilion.display.HTMLTemplateDb, java.util.HashMap)
    */
   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      ResultSet rs = null;
      String theMenu = "";
      int year = 0;
      int month = 0;

      try {
         // ---------------------------------------------------------------------
         // create drop down list of salutations
         // ---------------------------------------------------------------------
         CtSalutation mySalutation = new CtSalutation();
         mySalutation.setConn(conn);
         rs = mySalutation.getAllSalutationNamesIds();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "salutation",
               0,
               rs,
               "ct_salutation_id",
               "ct_salutation_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBSAL$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of provinces/states
         // ---------------------------------------------------------------------
         CtProvinceState myProvince = new CtProvinceState();
         myProvince.setConn(conn);
         rs = myProvince.getAllProvinceState();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "state_province",
               0,
               rs,
               "ct_province_state_id",
               "ct_province_state_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBSP$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of countries
         // ---------------------------------------------------------------------
         CtCountry myCountry = new CtCountry();
         myCountry.setConn(conn);
         rs = myCountry.getAllCountries();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "country",
               0,
               rs,
               "ct_country_id",
               "ct_country_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBC$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of provinces/states for billing
         // ---------------------------------------------------------------------
         rs = myProvince.getAllProvinceState();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "billing_state_province_card",
               0,
               rs,
               "ct_province_state_id",
               "ct_province_state_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBSPB$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of countries for billing
         // ---------------------------------------------------------------------
         rs = myCountry.getAllCountries();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "billing_country",
               0,
               rs,
               "ct_country_id",
               "ct_country_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBCB$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of available packages
         // ---------------------------------------------------------------------
         CtPackages myPackages = new CtPackages();
         myPackages.setConn(conn);
         rs = myPackages.getActivePackageNamesPrices();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "the_package",
               0,
               rs,
               "ct_package_id",
               "ct_package_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBTP$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of terms
         // ---------------------------------------------------------------------
         CtTerm myTerm = new CtTerm();
         myTerm.setConn(conn);
         rs = myTerm.getTerms();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "term",
               0,
               rs,
               "ct_term_id",
               "ct_term_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBTT$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of credit cards
         // ---------------------------------------------------------------------
         CtCreditCard myCard = new CtCreditCard();
         myCard.setConn(conn);
         rs = myCard.getAllCreditCards();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "credit_card",
               0,
               rs,
               "ct_credit_card_id",
               "ct_credit_card_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBCC$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of months for credit card
         // ---------------------------------------------------------------------

         CtMonth myMonth = new CtMonth();
         myMonth.setConn(conn);
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

         // ---------------------------------------------------------------------
         // create drop down list years for credit card expiry
         // ---------------------------------------------------------------------
         Calendar cal = Calendar.getInstance();
         year = cal.get(Calendar.YEAR);

         MasterTemplate.searchReplace("$DDLBCCEY$", Years
               .makeDropDownListYears("expiry_year", year, year, year + 20));

         // ---------------------------------------------------------------------
         // create drop down list of phone types
         // ---------------------------------------------------------------------
         CtPhoneType myPhone = new CtPhoneType();
         myPhone.setConn(conn);
         rs = myPhone.getAllPhoneTypesIds();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "phone_type",
               0,
               rs,
               "ct_phone_type_id",
               "ct_phone_type_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBPT$", theMenu);
         MasterTemplate.searchReplace("$FIRSTNAME$", "");
         MasterTemplate.searchReplace("$LASTNAME$", "");
         MasterTemplate.searchReplace("$COMPANYNAME$", "");
         MasterTemplate.searchReplace("$EMAIL$", "");
         MasterTemplate.searchReplace("$PASSWORD$", "");
         MasterTemplate.searchReplace("$URL$", "");
         MasterTemplate.searchReplace("$PHONE$", "");
         MasterTemplate.searchReplace("$EXT$", "");
         MasterTemplate.searchReplace("$FAX$", "");
         MasterTemplate.searchReplace("$ADDRESS$", "");
         MasterTemplate.searchReplace("$CITY$", "");
         MasterTemplate.searchReplace("$ZIP$", "");
         MasterTemplate.searchReplace("$NAMEONCARD$", "");
         MasterTemplate.searchReplace("$CARDNUMBER$", "");
         MasterTemplate.searchReplace("$BADDRESS$", "");
         MasterTemplate.searchReplace("$BCITY$", "");
         MasterTemplate.searchReplace("$BZIP$", "");
         MasterTemplate.searchReplace("$DESCRIPTION$", "");
         MasterTemplate.searchReplace("$PCODE$", "");

         MasterTemplate = this.AddCustomInfo(
               request,
               response,
               session,
               conn,
               MasterTemplate,
               hm);

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessJoinSite:BuildPage:Exception:"
                     + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      return MasterTemplate;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.display.html.ProcessPage#ProcessForm(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse,
    *      javax.servlet.http.HttpSession, java.sql.Connection,
    *      com.verilion.display.HTMLTemplateDb, java.util.HashMap)
    */
   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      String first_name = "";
      String last_name = "";
      String company_name = "";
      String email_address = "";
      String password = "";
      String phone_number = "";
      String address = "";
      String city = "";
      String postal_zip = "";
      String card_number = "";
      String name_on_card = "";
      int billing_address_same = 0;
      String billing_address = "";
      String billing_city = "";
      String billing_postal_zip = "";
      String theError = "";

      try {
         // get values from calling form
         first_name = (String) request.getParameter("first_name");
         last_name = (String) request.getParameter("last_name");
         company_name = (String) request.getParameter("company_name");
         email_address = (String) request.getParameter("email_address");
         password = (String) request.getParameter("customer_password");
         phone_number = (String) request.getParameter("phone_number");
         address = (String) request.getParameter("address");
         city = (String) request.getParameter("city");
         postal_zip = (String) request.getParameter("postal_zip");
         card_number = (String) request.getParameter("card_number");
         name_on_card = (String) request.getParameter("name_on_card");
         billing_address_same = Integer.parseInt((String) request
               .getParameter("billing_same"));

         // if billing address diff, get values from parameters
         if (billing_address_same == 0) {
            billing_address = (String) request
                  .getParameter("billing_address_card");
            billing_city = (String) request.getParameter("billing_city_card");
            billing_postal_zip = (String) request
                  .getParameter("billing_zip_postal_card");
         }

         // ------------------------------------------------------------------
         // Validate info
         // ------------------------------------------------------------------
         // All fields except url, fax number, extension, & company
         // description are required
         // ------------------------------------------------------------------
         theError = "";

         if ((first_name == null) || (first_name.length() < 1)) {
            theError += "first name; ";
         }

         if ((last_name == null) || (last_name.length() < 1)) {
            theError += "last name; ";
         }

         if ((company_name == null) || (company_name.length() < 1)) {
            theError += "company name; ";
         }

         if ((email_address == null) || (email_address.length() < 1)) {
            theError += "email address; ";
         }

         if ((password == null) || (password.length() < 1)) {
            theError += "password; ";
         }

         if ((phone_number == null) || (phone_number.length() < 1)) {
            theError += "Phone Number; ";
         }

         if ((address == null) || (address.length() < 1)) {
            theError += "address; ";
         }

         if ((city == null) || (city.length() < 1)) {
            theError += "town/city; ";
         }

         if ((postal_zip == null) || (postal_zip.length() < 1)) {
            theError += "postal/zip code; ";
         }

         if ((card_number == null) || (card_number.length() < 1)) {
            theError += "credit card number; ";
         }

         if ((name_on_card == null) || (name_on_card.length() < 1)) {
            theError += "name on card; ";
         }

         if (billing_address_same == 0) {
            if ((billing_address == null) || (billing_address.length() < 1)) {
               theError += "billing address; ";
            }

            if ((billing_city == null) || (billing_city.length() < 1)) {
               theError += "billing town/city; ";
            }

            if ((billing_postal_zip == null)
                  || (billing_postal_zip.length() < 1)) {
               theError += "billing postal/zip; ";
            }
         }

         if (theError.length() > 0) {
            // we are missing some required field
            theError = "The following fields must be completed: " + theError;
            session.setAttribute("Error", theError);
         }

         if (session.getAttribute("Error") != null) {
            String theParameterList = "?reject=1&";
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
               String theParameterName = (String) paramNames.nextElement();
               theParameterList += theParameterName;
               theParameterList += "=";
               theParameterList += request.getParameter(theParameterName);
               theParameterList += "&";
            }
            response.sendRedirect(session.getAttribute("lastPage")
                  + theParameterList);
         } else {
            String theParameterList = "?";
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
               String theParameterName = (String) paramNames.nextElement();
               theParameterList += theParameterName;
               theParameterList += "=";
               theParameterList += request.getParameter(theParameterName);
               theParameterList += "&";
            }

            this.ProcessCustomFormInfo(
                  request,
                  response,
                  session,
                  conn,
                  MasterTemplate,
                  hm,
                  theParameterList);
         }

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.ProcessPage:ProcessForm:Exception:"
                     + e.toString());
      }
      return MasterTemplate;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.display.html.ProcessPage#ProcessRejectedForm(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse,
    *      javax.servlet.http.HttpSession, java.sql.Connection,
    *      com.verilion.display.HTMLTemplateDb, java.util.HashMap)
    */
   public HTMLTemplateDb ProcessRejectedForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      ResultSet rs = null;
      String theMenu = "";
      int year = 0;

      try {
         // ---------------------------------------------------------------------
         // create drop down list of salutations
         // ---------------------------------------------------------------------
         CtSalutation mySalutation = new CtSalutation();
         mySalutation.setConn(conn);
         rs = mySalutation.getAllSalutationNamesIds();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "salutation",
               Integer.parseInt((String) request.getParameter("salutation")),
               rs,
               "ct_salutation_id",
               "ct_salutation_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBSAL$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of provinces/states
         // ---------------------------------------------------------------------
         CtProvinceState myProvince = new CtProvinceState();
         myProvince.setConn(conn);
         rs = myProvince.getAllProvinceState();

         theMenu = HTMLFormDropDownList
               .makeDropDownListSnippet(
                     "state_province",
                     Integer.parseInt((String) request
                           .getParameter("state_province")),
                     rs,
                     "ct_province_state_id",
                     "ct_province_state_name",
                     -1,
                     "");

         MasterTemplate.searchReplace("$DDLBSP$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of countries
         // ---------------------------------------------------------------------
         CtCountry myCountry = new CtCountry();
         myCountry.setConn(conn);
         rs = myCountry.getAllCountries();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "country",
               Integer.parseInt((String) request.getParameter("country")),
               rs,
               "ct_country_id",
               "ct_country_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBC$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of provinces/states for billing
         // ---------------------------------------------------------------------
         rs = myProvince.getAllProvinceState();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "billing_state_province_card",
               Integer.parseInt((String) request
                     .getParameter("billing_state_province_card")),
               rs,
               "ct_province_state_id",
               "ct_province_state_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBSPB$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of countries for billing
         // ---------------------------------------------------------------------
         rs = myCountry.getAllCountries();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "billing_country",
               Integer.parseInt((String) request
                     .getParameter("billing_country")),
               rs,
               "ct_country_id",
               "ct_country_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBCB$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of available packages
         // ---------------------------------------------------------------------
         CtPackages myPackages = new CtPackages();
         myPackages.setConn(conn);
         rs = myPackages.getActivePackageNamesPrices();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "the_package",
               Integer.parseInt((String) request.getParameter("the_package")),
               rs,
               "ct_package_id",
               "ct_package_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBTP$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of terms
         // ---------------------------------------------------------------------
         CtTerm myTerm = new CtTerm();
         myTerm.setConn(conn);
         rs = myTerm.getTerms();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "term",
               Integer.parseInt((String) request.getParameter("term")),
               rs,
               "ct_term_id",
               "ct_term_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBTT$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of credit cards
         // ---------------------------------------------------------------------
         CtCreditCard myCard = new CtCreditCard();
         myCard.setConn(conn);
         rs = myCard.getAllCreditCards();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "credit_card",
               Integer.parseInt((String) request.getParameter("credit_card")),
               rs,
               "ct_credit_card_id",
               "ct_credit_card_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBCC$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list of months for credit card
         // ---------------------------------------------------------------------

         CtMonth myMonth = new CtMonth();
         myMonth.setConn(conn);
         rs = myMonth.getAllMonthNamesIds();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "expiry_month",
               Integer.parseInt((String) request.getParameter("expiry_month")),
               rs,
               "ct_month_id",
               "ct_month_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBCCEM$", theMenu);

         // ---------------------------------------------------------------------
         // create drop down list years for credit card expiry
         // ---------------------------------------------------------------------
         Calendar cal = Calendar.getInstance();
         year = cal.get(Calendar.YEAR);

         MasterTemplate.searchReplace("$DDLBCCEY$", Years
               .makeDropDownListYears(
                     "expiry_year",
                     Integer.parseInt((String) request
                           .getParameter("expiry_year")),
                     year,
                     year + 20));

         // ---------------------------------------------------------------------
         // create drop down list of phone types
         // ---------------------------------------------------------------------
         CtPhoneType myPhone = new CtPhoneType();
         myPhone.setConn(conn);
         rs = myPhone.getAllPhoneTypesIds();

         theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "phone_type",
               Integer.parseInt((String) request.getParameter("phone_type")),
               rs,
               "ct_phone_type_id",
               "ct_phone_type_name",
               -1,
               "");

         MasterTemplate.searchReplace("$DDLBPT$", theMenu);

         MasterTemplate.searchReplace("$FIRSTNAME$", (String) request
               .getParameter("first_name"));
         MasterTemplate.searchReplace("$LASTNAME$", (String) request
               .getParameter("last_name"));
         MasterTemplate.searchReplace("$COMPANYNAME$", (String) request
               .getParameter("company_name"));
         MasterTemplate.searchReplace("$EMAIL$", (String) request
               .getParameter("email_address"));
         MasterTemplate.searchReplace("$PASSWORD$", (String) request
               .getParameter("customer_password"));
         MasterTemplate.searchReplace("$URL$", (String) request
               .getParameter("website_url"));
         MasterTemplate.searchReplace("$PHONE$", (String) request
               .getParameter("phone_number"));
         MasterTemplate.searchReplace("$EXT$", (String) request
               .getParameter("ext"));
         MasterTemplate.searchReplace("$FAX$", (String) request
               .getParameter("fax_number"));
         MasterTemplate.searchReplace("$ADDRESS$", (String) request
               .getParameter("address"));
         MasterTemplate.searchReplace("$CITY$", (String) request
               .getParameter("city"));
         MasterTemplate.searchReplace("$ZIP$", (String) request
               .getParameter("postal_zip"));
         MasterTemplate.searchReplace("$CARDNUMBER$", (String) request
               .getParameter("card_number"));
         MasterTemplate.searchReplace("$NAMEONCARD$", (String) request
               .getParameter("name_on_card"));

         // if our billing address is not the same we need
         // to repopulate that part of the form
         if (Integer.parseInt((String) request.getParameter("billing_same")) == 0) {
            MasterTemplate.searchReplace("$BADDRESS$", (String) request
                  .getParameter("billing_address_card"));
            MasterTemplate.searchReplace("$BCITY$", (String) request
                  .getParameter("billing_city_card"));
            MasterTemplate.searchReplace("$BZIP$", (String) request
                  .getParameter("billing_zip_postal_card"));
         } else {
            // our billing address is the same, so just delete the
            // tags in that part of the form (replace them with nothing)
            MasterTemplate.searchReplace("$BADDRESS$", "");
            MasterTemplate.searchReplace("$BCITY$", "");
            MasterTemplate.searchReplace("$BZIP$", "");
         }
         MasterTemplate.searchReplace("$DESCRIPTION$", (String) request
               .getParameter("company_description"));

         // do any client specific things
         MasterTemplate = this.AddCustomInfo(
               request,
               response,
               session,
               conn,
               MasterTemplate,
               hm);

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessJoinSite:BuildPage:Exception:"
                     + e.toString());
      }
      return MasterTemplate;

   }

   public HTMLTemplateDb AddCustomInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) {

      return MasterTemplate;
   }

   /**
    * Override this method to do your redirect, and to add custom info to the
    * form
    * 
    * @param request
    * @param response
    * @param session
    * @param conn
    * @param MasterTemplate
    * @param hm
    * @return MasterTemplate
    */
   public HTMLTemplateDb ProcessCustomFormInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm,
         String theParameterList) {

      try {
         if (request.getParameter("fpage") == null) {
            response
                  .sendRedirect("/public/jpage/1/p/VerifyJoin/a/verifyjoin/content.do"
                        + theParameterList);
         } else {
            response.sendRedirect((String) request.getParameter("fpage")
                  + theParameterList);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      return MasterTemplate;
   }
}
