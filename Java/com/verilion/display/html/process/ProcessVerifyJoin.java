//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-20
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessVerifyJoin.java,v $
//Revision 1.5.2.3.10.2  2006/10/05 15:52:39  tcs
//Added discount code
//
//Revision 1.5.2.3.10.1  2006/06/11 22:39:29  tcs
//Code cleanup
//
//Revision 1.5.2.3  2004/12/01 13:57:47  tcs
//Added preview of price to display
//
//Revision 1.5.2.2  2004/12/01 02:20:02  tcs
//Removed unused import
//
//Revision 1.5.2.1  2004/12/01 02:19:49  tcs
//Added ProcessCustomFormInfo method for children of this class
//
//Revision 1.5  2004/11/08 01:50:13  tcs
//Put vars in session after verification
//
//Revision 1.4  2004/11/06 11:35:27  tcs
//fixed error with billing postal code
//
//Revision 1.3  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.2  2004/10/26 15:41:34  tcs
//Improved javadocs
//
//Revision 1.1  2004/10/20 16:16:40  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.CtCountry;
import com.verilion.database.CtCreditCard;
import com.verilion.database.CtPackages;
import com.verilion.database.CtProvinceState;
import com.verilion.database.CtSalutation;
import com.verilion.database.CtTerm;
import com.verilion.database.Promotions;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;

/**
 * Verify Info for joining site
 * 
 * <P>
 * October 20, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 *  
 */
public class ProcessVerifyJoin extends ProcessPage {

   public int ct_salutation_id = 0;
   public String salutation = "";
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
   public String province_state = "";
   public String bProvince_state = "";
   public int ct_country_id = 0;
   public String country = "";
   public String bCountry = "";
   public String postal_zip = "";
   public int add_to_list = 0;
   public int ct_package_id = 0;
   public String thePackage = "";
   public int ct_term_id = 0;
   public String term = "";
   public int ct_credit_card_id = 0;
   public String credit_card = "";
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
   public String promoCode = "";
   private float discountRate = 0.0f;
   private String discountText = "";

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      // create the objects we are going to need
      CtSalutation mySalutation = new CtSalutation();
      CtProvinceState myProvinceState = new CtProvinceState();
      CtCountry myCountry = new CtCountry();
      CtPackages myPackage = new CtPackages();
      CtTerm myTerm = new CtTerm();
      CtCreditCard myCreditCard = new CtCreditCard();

      try {
         
         //   get values from calling form
         ct_salutation_id = Integer.parseInt((String) request
               .getParameter("salutation"));
         first_name = (String) request.getParameter("first_name");
         last_name = (String) request.getParameter("last_name");
         company_name = (String) request.getParameter("company_name");
         email_address = (String) request.getParameter("email_address");
         password = (String) request.getParameter("customer_password");
         website_url = (String) request.getParameter("website_url");
         phone_number = (String) request.getParameter("phone_number");
         ct_phone_type_id = Integer.parseInt((String) request
               .getParameter("phone_type"));
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
         promoCode = (String) request.getParameter("promo_code");

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
            billing_postal_zip = postal_zip;
         }

         company_description = (String) request
               .getParameter("company_description");
         
//       ------------------------------------------------------------------
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
         CtTerm theTerm = new CtTerm();
         theTerm.setConn(conn);
         theTerm.setCt_term_id(ct_term_id);
         rs = theTerm.getTerm();
         int iterm = 0;
         if (rs.next()) {
            iterm = rs.getInt("ct_term_quantity");
         }

         int intVal = (int) (cost * 100f);
         intVal = intVal * iterm;
         float percentFloat = ((float) intVal) / 100;
         NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
         String thePrice = currency.format(percentFloat);
         
         // check promo code for discount, if any
         if (promoCode != null) {
        	 if (promoCode.length() > 0) {
        		 Promotions myPromotion = new Promotions();
                 myPromotion.setConn(conn);
                 discountRate = myPromotion.getDiscountRate(promoCode);
                 if (discountRate > 0.0f) {
                	 discountText = promoCode;
	                 percentFloat = percentFloat * discountRate;
	                 thePrice = currency.format(percentFloat);
                 }
        	 } else {
        		 discountText = "No discount applied";
        	 }
         } else {
        	 discountText = "No discount applied";
         }
         
         
         //------------------------------------------------------------------
         // Get human readable values from IDs
         //------------------------------------------------------------------
         mySalutation.setCt_salutation_id(ct_salutation_id);
         mySalutation.setConn(conn);
         salutation = mySalutation.getSalutationName();
         myProvinceState.setConn(conn);
         myProvinceState.setCt_province_state_id(ct_province_state_id);
         province_state = myProvinceState.getProvinceStateNameById();
         myProvinceState.setCt_province_state_id(billing_ct_state_province_id);
         bProvince_state = myProvinceState.getProvinceStateNameById();
         myCountry.setCt_country_id(ct_country_id);
         myCountry.setConn(conn);
         country = myCountry.getCountryNameById();
         myCountry.setCt_country_id(billing_ct_country_id);
         bCountry = myCountry.getCountryNameById();
         myTerm.setConn(conn);
         myTerm.setCt_term_id(ct_term_id);
         term = myTerm.getTermNameById();
         myPackage.setConn(conn);
         myPackage.setCt_package_id(ct_package_id);
         thePackage = myPackage.getPackageNameById();
         myCreditCard.setConn(conn);
         myCreditCard.setCt_credit_card_id(ct_credit_card_id);
         credit_card = myCreditCard.getCreditCardNameById();

         //------------------------------------------------------------------
         // Write values to session
         //------------------------------------------------------------------
         session.setAttribute("ct_salutation_id", ct_salutation_id + "");
         session.setAttribute("first_name", first_name);
         session.setAttribute("last_name", last_name);
         session.setAttribute("company_name", company_name);
         session.setAttribute("email_address", email_address);
         session.setAttribute("password", password);
         session.setAttribute("website_url", website_url);
         session.setAttribute("phone_number", phone_number);
         session.setAttribute("ct_phone_type_id", ct_phone_type_id + "");
         session.setAttribute("extension", extension);
         session.setAttribute("fax_number", fax_number);
         session.setAttribute("address", address);
         session.setAttribute("city", city);
         session
               .setAttribute("ct_province_state_id", ct_province_state_id + "");
         session.setAttribute("ct_country_id", ct_country_id + "");
         session.setAttribute("postal_zip", postal_zip);
         session.setAttribute("add_to_list", add_to_list + "");
         session.setAttribute("ct_package_id", ct_package_id + "");
         session.setAttribute("ct_term_id", ct_term_id + "");
         session.setAttribute("ct_credit_card_id", ct_credit_card_id + "");
         session.setAttribute("card_number", card_number);
         session.setAttribute("name_on_card", name_on_card);
         session.setAttribute("expiry_month", expiry_month + "");
         session.setAttribute("expiry_year", expiry_year + "");
         session
               .setAttribute("billing_address_same", billing_address_same + "");
         if (billing_address_same == 0) {
            session.setAttribute("billing_address", billing_address);
            session.setAttribute("billing_city", billing_city);
            session.setAttribute(
                  "billing_ct_state_province_id",
                  billing_ct_state_province_id + "");
            session.setAttribute("billing_ct_country_id", billing_ct_country_id
                  + "");
            session.setAttribute("billing_postal_zip", billing_postal_zip);
         }
         session.setAttribute("company_description", company_description);

         //------------------------------------------------------------------
         // Write values to screen
         //------------------------------------------------------------------
         MasterTemplate.searchReplace("$SAL$", salutation);
         MasterTemplate.searchReplace("$FIRSTNAME$", first_name);
         MasterTemplate.searchReplace("$LASTNAME$", last_name);
         MasterTemplate.searchReplace("$COMPANYNAME$", company_name);
         MasterTemplate.searchReplace("$URL$", website_url);
         MasterTemplate.searchReplace("$PHONE$", phone_number);
         MasterTemplate.searchReplace("$FAX$", fax_number);
         if (extension.length() > 0) {
            MasterTemplate.searchReplace("$EXT$", " ext. " + extension);
         } else {
            MasterTemplate.searchReplace("$EXT$", "");
         }
         MasterTemplate.searchReplace("$ADDRESS$", address);
         MasterTemplate.searchReplace("$CITY$", city);
         MasterTemplate.searchReplace("$PROV$", province_state);
         MasterTemplate.searchReplace("$COUNTRY$", country);
         MasterTemplate.searchReplace("$POSTAL$", postal_zip);
         MasterTemplate.searchReplace("$PACKAGE$", thePackage);
         MasterTemplate.searchReplace("$TERM$", term + "");
         MasterTemplate.searchReplace("$EMAIL$", email_address);
         MasterTemplate.searchReplace("$CREDIT$", credit_card);
         MasterTemplate.searchReplace("$CARDNUMBER$", card_number);
         MasterTemplate.searchReplace("$CCMONTH$", expiry_month + " /");
         MasterTemplate.searchReplace("$CARDYEAR$", expiry_year + "");
         MasterTemplate.searchReplace("$NAMEONCARD$", name_on_card);
         MasterTemplate.searchReplace("$PASSWORD$", password);
         MasterTemplate.searchReplace("$COMPANYDESC$", company_description);
         MasterTemplate.searchReplace("$BILLADDRESS$", billing_address);
         MasterTemplate.searchReplace("$BILLCITY$", billing_city);
         MasterTemplate.searchReplace("$BILLPOSTAL$", billing_postal_zip);
         MasterTemplate.searchReplace("$BILLCOUNTRY$", bCountry);
         MasterTemplate.searchReplace("$BILLPROV$", bProvince_state);
         MasterTemplate.searchReplace("$PRICE$", thePrice);
         MasterTemplate.searchReplace("$DISCOUNTTEXT$", discountText);

         MasterTemplate = ProcessCustomFormInfo(
               request,
               response,
               session,
               conn,
               MasterTemplate,
               hm);

      } catch (Exception e) {
         MasterTemplate.replaceFullValue("An error has occurred. Please use the back button in your browser, and try again");
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessVerifyJoin:BuildPage:Exception:"
                     + e.toString());
      }
      return MasterTemplate;
   }

   public HTMLTemplateDb ProcessCustomFormInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) {

      return MasterTemplate;
   }
}