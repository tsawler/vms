//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-22
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessDisplayCustomer.java,v $
//Revision 1.2.2.2.2.1.2.2  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.2.2.1.2.1  2005/08/19 13:46:08  tcs
//Removed hard coded cancelPage value
//
//Revision 1.2.2.2.2.1  2005/07/20 18:09:12  tcs
//Added stacktrace
//
//Revision 1.2.2.2  2005/05/03 12:49:01  tcs
//Added <br> to company description
//
//Revision 1.2.2.1  2005/04/22 18:13:50  tcs
//Modifications for jsp template model
//
//Revision 1.2  2004/10/27 11:48:54  tcs
//Changes due to refactoring
//
//Revision 1.1  2004/10/22 15:33:37  tcs
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

import com.verilion.database.Customer;
import com.verilion.database.DBUtils;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;

/**
 * Process displaying Customers
 * 
 * <P>
 * October 22, 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessDisplayCustomer extends ProcessPage {

   public XDisconnectedRowSet rs = new XDisconnectedRowSet();
   DBUtils myDbUtil = new DBUtils();
   public int customer_id = 0;
   public String theMenu = "";
   public int ct_salutation_id = 0;
   public String ct_salutation_name = "";
   public String customer_first_name = "";
   public String customer_last_name = "";
   public String account_created = "";
   public String next_billing_date = "";
   public int next_billing_year = 0;
   public int next_billing_month = 0;
   public int next_billing_day = 0;
   public String registration_date = "";
   public int registration_date_year = 0;
   public int registration_date_month = 0;
   public int registration_date_day = 0;
   public String customer_email = "";
   public String customer_password = "";
   public String phone_number = "";
   public String extension = "";
   public String fax = "";
   public String address = "";
   public String city = "";
   public String province = "";
   public int ct_province_state_id = 0;
   public String ct_province_state_name = "";
   public int ct_country_id = 0;
   public String ct_country_name = "";
   public String postal_code = "";
   public String billing_address = "";
   public String billing_city = "";
   public String billing_province = "";
   public int billing_province_id = 0;
   public String billing_province_name = "";
   public int billing_country_id = 0;
   public String billing_country_name = "";
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
   public String ct_package_name = "";
   public String ct_term_name = "";
   public int ct_term_id = 0;
   public int ct_credit_card_id = 0;
   public int customer_access_level = 0;
   public int ct_phone_type_id = 0;
   public String ct_credit_card_name = "";
   public String ct_access_level_name = "";
   public String customer_active_yn = "";
   public String cancelPage = "";

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

      XDisconnectedRowSet rs = new XDisconnectedRowSet();

      try {

         // get our customer id, passed as parameter
         //customer_id = Integer.parseInt((String) hm.get("id"));
         customer_id = Integer.parseInt((String) request.getParameter("id"));
         cancelPage = request.getParameter("cancelPage");
         if (session.getAttribute("d-49385-p") != null) {
            cancelPage += "?d-49385-p=" + session.getAttribute("d-49385-p");
         }
         MasterTemplate.searchReplace("$CANCELPAGE$", cancelPage);

         // get our customer record
         Customer myCustomer = new Customer();
         myCustomer.setConn(conn);
         myCustomer.setCustomer_id(customer_id);
         rs = myCustomer.GetOneCustomerFromView();

         // extract info from customer table to local variables
         if (rs.next()) {
            ct_salutation_id = rs.getInt("ct_salutation_id");
            ct_salutation_name = rs.getString("ct_salutation_name");
            customer_first_name = rs.getString("customer_first_name");
            customer_last_name = rs.getString("customer_last_name");
            customer_company_name = rs.getString("customer_company_name");
            next_billing_date = rs.getString("customer_next_billing_date");
            next_billing_year = Integer.parseInt(next_billing_date.substring(
                  0,
                  4));
            next_billing_month = Integer.parseInt(next_billing_date.substring(
                  5,
                  7));
            next_billing_day = Integer.parseInt(next_billing_date.substring(8));
            registration_date = rs.getString("customer_registration_date");
            registration_date_year = Integer.parseInt(registration_date
                  .substring(0, 4));
            registration_date_month = Integer.parseInt(registration_date
                  .substring(5, 7));
            registration_date_day = Integer.parseInt(registration_date
                  .substring(8));
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
            customer_company_description = myDbUtil.replace(customer_company_description, "\n", "<br />");
            ct_package_id = rs.getInt("ct_package_id");
            ct_term_id = rs.getInt("ct_term_id");
            ct_credit_card_id = rs.getInt("ct_credit_card_id");
            ct_credit_card_name = rs.getString("ct_credit_card_name");
            customer_access_level = rs.getInt("customer_access_level");
            address = rs.getString("customer_address");
            city = rs.getString("customer_town_city");
            province = rs.getString("ct_province_state_name");
            ct_province_state_id = rs.getInt("ct_province_state_id");
            ct_country_id = rs.getInt("ct_country_id");
            ct_country_name = rs.getString("ct_country_name");
            postal_code = rs.getString("customer_zip_postal");
            billing_address = rs.getString("billing_address");
            billing_city = rs.getString("billing_city");
            billing_province = rs.getString("billing_province");
            billing_province_id = rs.getInt("billing_province_id");
            billing_country_id = rs.getInt("billing_country_id");
            billing_country_name = rs.getString("billing_country_name");
            billing_postal_zip = rs.getString("billing_postal");
            phone_number = rs.getString("customer_phone");
            extension = rs.getString("extension");
            fax = rs.getString("fax");
            customer_name_on_card = rs.getString("customer_name_on_card");
            ct_package_name = rs.getString("ct_package_name");
            ct_term_name = rs.getString("ct_term_name");
            ct_access_level_name = rs.getString("ct_access_level_name");
            customer_active_yn = rs.getString("customer_active_yn");
         }

         // Replace info on form with customer info
         MasterTemplate.searchReplace("$CUSTOMERID$", customer_id + "");
         MasterTemplate.searchReplace("$SAL$", ct_salutation_name);
         MasterTemplate.searchReplace("$FIRSTNAME$", customer_first_name);
         MasterTemplate.searchReplace("$LASTNAME$", customer_last_name);
         MasterTemplate.searchReplace("$CREATEYEAR$", registration_date_year
               + " /");
         MasterTemplate.searchReplace("$CREATEMONTH$", registration_date_month
               + " /");
         MasterTemplate
               .searchReplace("$CREATEDAY$", registration_date_day + "");
         MasterTemplate.searchReplace("$NEXTBILLYEAR$", next_billing_year
               + " /");
         MasterTemplate.searchReplace("$NEXTBILLMONTH$", next_billing_month
               + " /");
         MasterTemplate.searchReplace("$NEXTBILLDAY$", next_billing_day + "");
         MasterTemplate.searchReplace("$COMPANYNAME$", customer_company_name);
         MasterTemplate.searchReplace("$URL$", customer_website_url);
         MasterTemplate.searchReplace("$PHONE$", phone_number);
         MasterTemplate.searchReplace("$FAX$", fax);
         if (extension.length() > 0) {
            MasterTemplate.searchReplace("$EXT$", " ext. " + extension);
         } else {
            MasterTemplate.searchReplace("$EXT$", "");
         }
         MasterTemplate.searchReplace("$ADDRESS$", address);
         MasterTemplate.searchReplace("$CITY$", city);
         MasterTemplate.searchReplace("$PROV$", province);
         MasterTemplate.searchReplace("$COUNTRY$", ct_country_name);
         MasterTemplate.searchReplace("$POSTAL$", postal_code);
         MasterTemplate.searchReplace("$PACKAGE$", ct_package_name);
         MasterTemplate.searchReplace("$TERM$", ct_term_name);
         MasterTemplate.searchReplace("$EMAIL$", customer_email);
         MasterTemplate.searchReplace("$CREDIT$", ct_credit_card_name);
         MasterTemplate.searchReplace(
               "$CARDNUMBER$",
               customer_credit_card_number);
         MasterTemplate.searchReplace("$CCMONTH$", customer_card_expiry_month
               + " /");
         MasterTemplate.searchReplace("$CARDYEAR$", customer_card_expiry_year
               + "");
         MasterTemplate.searchReplace("$NAMEONCARD$", customer_name_on_card);
         MasterTemplate.searchReplace("$PASSWORD$", customer_password);
         MasterTemplate.searchReplace(
               "$COMPANYDESC$",
               customer_company_description);
         MasterTemplate.searchReplace("$BILLADDRESS$", billing_address);
         MasterTemplate.searchReplace("$BILLCITY$", billing_city);
         MasterTemplate.searchReplace("$BILLPOSTAL$", billing_postal_zip);
         MasterTemplate.searchReplace("$BILLCOUNTRY$", billing_country_name);
         MasterTemplate.searchReplace("$ACCESSLEVEL$", ct_access_level_name
               + "");
         MasterTemplate.searchReplace("$ACTIVE$", customer_active_yn);
         MasterTemplate.searchReplace("$BILLPROV$", billing_province);

         AddCustomInfo(request, response, session);

         rs.close();
         rs = null;

      } catch (Exception e) {
         System.out.println("Error in ProcessDisplayCustomer: " + e.toString());
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
   public void AddCustomInfo(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

   }
}