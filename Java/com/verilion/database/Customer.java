//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: Customer.java,v $
//Revision 1.12.2.8.2.2.2.4.2.3.4.2.2.2.2.2  2007/06/11 15:35:26  tcs
//*** empty log message ***
//
//Revision 1.12.2.8.2.2.2.4.2.3.4.2.2.2.2.1  2007/03/23 11:02:27  tcs
//Changes to reflect new DB structure
//
//Revision 1.12.2.8.2.2.2.4.2.3.4.2.2.2  2007/02/01 12:34:08  tcs
//Added method to get username by email address
//
//Revision 1.12.2.8.2.2.2.4.2.3.4.2.2.1  2007/01/22 19:16:44  tcs
//Added method for username
//
//Revision 1.12.2.8.2.2.2.4.2.3.4.2  2006/12/19 19:27:06  tcs
//Fixed SQL typo
//
//Revision 1.12.2.8.2.2.2.4.2.3.4.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.12.2.8.2.2.2.4.2.3  2005/11/22 03:43:20  tcs
//Updated javadocs
//
//Revision 1.12.2.8.2.2.2.4.2.2  2005/11/12 12:40:15  tcs
//Added method to return last names as list
//
//Revision 1.12.2.8.2.2.2.4.2.1  2005/08/29 22:52:05  tcs
//Added all fields from v_customer to dynabean for getallcustomers method
//
//Revision 1.12.2.8.2.2.2.4  2005/08/19 13:46:49  tcs
//Removed debugging
//
//Revision 1.12.2.8.2.2.2.3  2005/08/18 18:20:55  tcs
//*** empty log message ***
//
//Revision 1.12.2.8.2.2.2.2  2005/08/12 16:58:03  tcs
//Fixed setting of package id during update
//
//Revision 1.12.2.8.2.2.2.1  2005/08/10 11:21:10  tcs
//Added generated column to getAllMembersDynaBean query
//
//Revision 1.12.2.8.2.2  2005/08/08 15:34:27  tcs
//Changes for new URL & jsp tag methodology
//
//Revision 1.12.2.8.2.1  2005/07/20 18:10:50  tcs
//Added getAllMembersDynaBean()
//
//Revision 1.12.2.8  2005/05/18 16:55:00  tcs
//Added some methods
//
//Revision 1.12.2.7  2005/05/18 14:20:56  tcs
//Added methods for role management
//
//Revision 1.12.2.6  2005/05/18 12:59:13  tcs
//Added method for v_user_roles view
//
//Revision 1.12.2.5  2005/05/03 12:41:40  tcs
//Added getAllMembers method
//
//Revision 1.12.2.4  2005/04/25 11:21:07  tcs
//improved javadocs
//
//Revision 1.12.2.3  2005/04/22 18:15:09  tcs
//added limit/offset/customwhere to getall method
//
//Revision 1.12.2.2  2005/04/21 16:42:42  tcs
//Added method to update company description
//
//Revision 1.12.2.1  2005/01/19 02:14:44  tcs
//implemented databaseinterface
//
//Revision 1.12  2004/10/27 17:58:54  tcs
//Made some searches case insensitive
//
//Revision 1.11  2004/10/26 15:35:26  tcs
//Improved javadocs
//
//Revision 1.10  2004/06/25 18:20:14  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.9  2004/06/24 17:58:12  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.8  2004/06/12 11:51:28  tcs
//Added double quote replace to getters
//
//Revision 1.7  2004/06/10 17:33:01  tcs
//Moved fixsqlfield value down here
//
//Revision 1.6  2004/06/07 17:05:53  tcs
//Added space b/w first/last name in GetAllCustomersAboveAccessLevel2
//
//Revision 1.5  2004/06/07 16:49:13  tcs
//Changed display of customer_name calculated field
//
//Revision 1.4  2004/06/07 16:46:32  tcs
//Added method to get rs of customers with access level > 2
//
//Revision 1.3  2004/06/07 16:02:29  tcs
//*** empty log message ***
//
//Revision 1.2  2004/06/01 22:21:21  tcs
//Added print stacktrace to catch on update method
//
//Revision 1.1  2004/05/23 04:52:45  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates customer table in database, and related operations.
 * <P>
 * Nov 5, 2003
 * <P>
 * package com.verilion
 * 
 * @author tsawler
 * 
 */
public class Customer implements DatabaseInterface {

   private int customer_id = 0;
   private int ct_salutation_id = 0;
   private String customer_first_name = "";
   private String customer_last_name = "";
   private String customer_company_name = "";
   private String customer_website_url = "";
   private String customer_email_address = "";
   private int customer_add_to_mailing_list = 0;
   private int ct_term_id = 0;
   private int ct_credit_card_id = 0;
   private String customer_credit_card_number = "";
   private int customer_card_expiry_month = 0;
   private int customer_credit_card_expiry_year = 0;
   private String customer_name_on_card = "";
   private String customer_registration_date = "";
   private String customer_next_billing_date = "";
   private String customer_password = "";
   private String customer_active_yn = "";
   private int customer_access_level = 0;
   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private int ct_package_id = 0;
   private String customer_company_description = "";
   private Connection conn = null;
   private Statement st = null;
   private PreparedStatement pst = null;
   private String customer_isnew_yn = "";
   DBUtils myDbUtils = new DBUtils();
   private String sCustomWhere;
   private int iLimit;
   private int iOffset;
   private String username = "";

   public Customer() {
      super();
   }

   /**
    * Deletes record by customer_id
    * 
    * @throws ServletException
    */
   public void CustomerDelete() throws SQLException, Exception {
      String sqlQuery = "";
      try {
         sqlQuery = "delete from customer where customer_id = '"
               + customer_id
               + "'";
         pst = conn.prepareStatement(sqlQuery);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("Customer:CustomerDelete:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:CustomerDelete:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns list of Customer last names, as a list.
    * 
    * @param pName
    * @return List
    * @throws SQLException
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   public List SearchCustomerLastName(String pName) throws SQLException,
         Exception {
      String sqlQuery = "";
      List lastNamesList = new ArrayList();
      try {
         sqlQuery = "select distinct customer_last_name from customer where customer_last_name ilike ? order by customer_last_name";
         pst = conn.prepareStatement(sqlQuery);
         pst.setString(1, pName);
         rs = pst.executeQuery();
         while (rs.next()) {
            lastNamesList.add(rs.getString(1));
         }
         rs.close();
         rs = null;
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("Customer:CustomerDelete:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:CustomerDelete:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
      return lastNamesList;
   }

   /**
    * Updates record by customer id.
    * 
    * @throws ServletException
    */
   public void CustomerUpdate() throws SQLException, Exception {
      String sqlQuery = "";
      try {
         sqlQuery = "UPDATE customer SET "
               + "ct_salutation_id = '"
               + ct_salutation_id
               + "', "
               + "customer_first_name = '"
               + customer_first_name
               + "', "
               + "customer_last_name = '"
               + customer_last_name
               + "', "
               + "customer_company_name = '"
               + customer_company_name
               + "', "
               + "customer_website_url = '"
               + customer_website_url
               + "', "
               + "customer_add_to_mailing_list = '"
               + customer_add_to_mailing_list
               + "', "
               + "ct_term_id = '"
               + ct_term_id
               + "', "
               + "ct_credit_card_id = '"
               + ct_credit_card_id
               + "', "
               + "customer_credit_card_number = '"
               + customer_credit_card_number
               + "', "
               + "customer_card_expiry_month = '"
               + customer_card_expiry_month
               + "', "
               + "customer_credit_card_expiry_year = '"
               + customer_credit_card_expiry_year
               + "', "
               + "customer_name_on_card = '"
               + customer_name_on_card
               + "', "
               + "customer_registration_date = '"
               + customer_registration_date
               + "', "
               + "customer_next_billing_date = '"
               + customer_next_billing_date
               + "', "
               + "customer_password = '"
               + customer_password
               + "', "
               + "customer_active_yn = '"
               + customer_active_yn
               + "', "
               + "customer_access_level = '"
               + customer_access_level
               + "', "
               + "ct_package_id = '"
               + ct_package_id
               + "', "
               + "customer_company_description = '"
               + customer_company_description
               + "' "
               + "WHERE customer_id = '"
               + customer_id
               + "'";
         pst = conn.prepareStatement(sqlQuery);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         e.printStackTrace();
         Errors
               .addError("Customer:CustomerUpdate:SQLException:" + e.toString());
      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("Customer:CustomerUpdate:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Adds record to customer
    * 
    * @return int
    * @throws ServletException
    */
   public int CustomerAdd() throws SQLException, Exception {
      String sqlQuery = "";
      int newCustomerId = 0;
      try {
         if (customer_next_billing_date.length() > 0) {
            sqlQuery = "INSERT INTO customer ("
                  + "ct_salutation_id, "
                  + "customer_first_name, "
                  + "customer_last_name, "
                  + "customer_company_name, "
                  + "customer_website_url, "
                  + "customer_add_to_mailing_list, "
                  + "ct_term_id, "
                  + "ct_credit_card_id, "
                  + "customer_credit_card_number, "
                  + "customer_card_expiry_month, "
                  + "customer_credit_card_expiry_year, "
                  + "customer_name_on_card, "
                  + "customer_registration_date, "
                  + "customer_next_billing_date, "
                  + "customer_password, "
                  + "customer_active_yn, "
                  + "customer_access_level, "
                  + "ct_package_id, "
                  + "customer_company_description) "
                  + "VALUES ("
                  + "'"
                  + ct_salutation_id
                  + "', "
                  + "'"
                  + customer_first_name
                  + "', "
                  + "'"
                  + customer_last_name
                  + "', "
                  + "'"
                  + customer_company_name
                  + "', "
                  + "'"
                  + customer_website_url
                  + "', "
                  + "'"
                  + customer_add_to_mailing_list
                  + "', "
                  + "'"
                  + ct_term_id
                  + "', "
                  + "'"
                  + ct_credit_card_id
                  + "', "
                  + "'"
                  + customer_credit_card_number
                  + "', "
                  + "'"
                  + customer_card_expiry_month
                  + "', "
                  + "'"
                  + customer_credit_card_expiry_year
                  + "', "
                  + "'"
                  + customer_name_on_card
                  + "', "
                  + "'"
                  + customer_registration_date
                  + "', "
                  + "'"
                  + customer_next_billing_date
                  + "', "
                  + "'"
                  + customer_password
                  + "', "
                  + "'"
                  + customer_active_yn
                  + "', "
                  + "'"
                  + customer_access_level
                  + "', "
                  + "'"
                  + ct_package_id
                  + "', "
                  + "'"
                  + customer_company_description
                  + "')";

         } else {
            sqlQuery = "INSERT INTO customer ("
                  + "ct_salutation_id, "
                  + "customer_first_name, "
                  + "customer_last_name, "
                  + "customer_company_name, "
                  + "customer_website_url, "
                  + "customer_add_to_mailing_list, "
                  + "ct_term_id, "
                  + "ct_credit_card_id, "
                  + "customer_credit_card_number, "
                  + "customer_card_expiry_month, "
                  + "customer_credit_card_expiry_year, "
                  + "customer_name_on_card, "
                  + "customer_registration_date, "
                  + "customer_password, "
                  + "customer_active_yn, "
                  + "customer_access_level, "
                  + "ct_package_id, "
                  + "customer_company_description) "
                  + "VALUES ("
                  + "'"
                  + ct_salutation_id
                  + "', "
                  + "'"
                  + customer_first_name
                  + "', "
                  + "'"
                  + customer_last_name
                  + "', "
                  + "'"
                  + customer_company_name
                  + "', "
                  + "'"
                  + customer_website_url
                  + "', "
                  + "'"
                  + customer_add_to_mailing_list
                  + "', "
                  + "'"
                  + ct_term_id
                  + "', "
                  + "'"
                  + ct_credit_card_id
                  + "', "
                  + "'"
                  + customer_credit_card_number
                  + "', "
                  + "'"
                  + customer_card_expiry_month
                  + "', "
                  + "'"
                  + customer_credit_card_expiry_year
                  + "', "
                  + "'"
                  + customer_name_on_card
                  + "', "
                  + "'"
                  + customer_registration_date
                  + "', "
                  + "'"
                  + customer_password
                  + "', "
                  + "'"
                  + customer_active_yn
                  + "', "
                  + "'"
                  + customer_access_level
                  + "', "
                  + "'"
                  + ct_package_id
                  + "', "
                  + "'"
                  + customer_company_description
                  + "')";
         }

         pst = conn.prepareStatement(sqlQuery);
         pst.executeUpdate();

         String getLast = "select currval('customer_customer_id_seq')";

         st = conn.createStatement();
         rs = st.executeQuery(getLast);
         if (rs.next()) {
            newCustomerId = rs.getInt(1);
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
         pst.close();
         pst = null;

      } catch (SQLException e) {
         System.out.println("error in customer is " + e.toString());
         e.printStackTrace();
         Errors.addError("Customer:CustomerAdd:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:CustomerAdd:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
      return newCustomerId;
   }

   /**
    * Adds a buyer to the database (email and password only)
    * 
    * @return int - newly added customer_Id
    * @throws SQLException
    * @throws Exception
    */
   public int CustomerAddBuyer() throws SQLException, Exception {
      String sqlQuery = "";
      int newCustomerId = 0;
      try {
         sqlQuery = "INSERT INTO customer ("
               + "customer_password, "
               + "customer_active_yn, "
               + "customer_access_level) "
               + "VALUES ("
               + "'"
               + customer_password
               + "', "
               + "'"
               + customer_active_yn
               + "', "
               + "'"
               + customer_access_level
               + "')";

         pst = conn.prepareStatement(sqlQuery);
         pst.executeUpdate();

         String getLast = "select currval('customer_customer_id_seq')";

         st = conn.createStatement();
         rs = st.executeQuery(getLast);
         if (rs.next()) {
            newCustomerId = rs.getInt(1);
         }

         rs.close();
         rs = null;
         st.close();
         st = null;
         pst.close();
         pst = null;

      } catch (SQLException e) {
         System.out.println("error in customer is " + e.toString());
         e.printStackTrace();
         Errors.addError("Customer:CustomerAddBuyer:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:CustomerAddBuyer:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
      return newCustomerId;
   }

   /**
    * Updates a buyer in the database (email and password only)
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void CustomerUpdateBuyer() throws SQLException, Exception {
      String sqlQuery = "";
      try {
         sqlQuery = "UPDATE customer set "
               + "customer_password = '"
               + customer_password
               + "' "
               + "WHERE customer_id = '"
               + customer_id
               + "'";

         pst = conn.prepareStatement(sqlQuery);
         pst.executeUpdate();
         pst.close();
         pst = null;

      } catch (SQLException e) {
         Errors.addError("Customer:CustomerUpdateBuyer:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:CustomerUpdateBuyer:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Update customer company description in db
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void UpdateCompanyDescription() throws SQLException, Exception {
      String sqlQuery = "";
      try {
         sqlQuery = "UPDATE customer set "
               + "customer_company_description = '"
               + customer_company_description
               + "' "
               + "WHERE customer_id = '"
               + customer_id
               + "'";

         pst = conn.prepareStatement(sqlQuery);
         pst.executeUpdate();
         pst.close();
         pst = null;

      } catch (SQLException e) {
         Errors.addError("Customer:UpdateCompanyDescription:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:UpdateCompanyDescription:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Gets all active customers, ordered by last name.
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet GetInfo() throws SQLException, Exception {
      try {
         String GetProfile = "select * from customer where customer_active = 'y' order by customer_last_name";
         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:GetInfo:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:GetInfo:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Gets all customers
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getAllCustomers() throws SQLException, Exception {
      try {
         String GetProfile = "select * from customer ";

         if (sCustomWhere.length() > 7) {
            GetProfile = GetProfile + sCustomWhere;
         }

         GetProfile += " order by customer_last_name ";

         if (this.iLimit > 0)
            GetProfile += " limit " + iLimit;
         if (this.iOffset > 0)
            GetProfile += " offset " + iOffset;

         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:getAllCustomers:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getAllCustomers:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns drs of all members with access level > 1
    * 
    * @return disconnected rowset
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getAllMembers() throws SQLException, Exception {
      try {
         String GetProfile = "select * from customer where customer_access_level > 1 ";

         if (sCustomWhere.length() > 7) {
            GetProfile = GetProfile + sCustomWhere;
         }

         GetProfile += " order by customer_last_name ";

         if (this.iLimit > 0)
            GetProfile += " limit " + iLimit;
         if (this.iOffset > 0)
            GetProfile += " offset " + iOffset;

         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:getAllCustomers:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getAllCustomers:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns dynabean of all members, for displaytag search results
    * 
    * @return dynabean
    * @throws SQLException
    * @throws Exception
    */
   public RowSetDynaClass getAllMembersDynaBean() throws SQLException,
         Exception {

      RowSetDynaClass resultset = null;

      try {
         String GetProfile = "select '<input type=\"hidden\" name=\"item_id_' || customer_id || '\">' as hiddenfield, v_customer.*, "
               + "case when customer_active_yn = 'y' then '<span style=\"color: green\">active</span>' "
               + "when customer_active_yn = 'p' then '<span style=\"color: red\">pending</span>' "
               + "else '<span style=\"color: red;\">inactive</a>' end as customer_status, "
               + "case when customer_active_yn = 'y' then 'active' "
               + "when customer_active_yn = 'p' then 'pending' "
               + "else 'inactive' end as customer_status_for_display "
               + "from v_customer where customer_access_level > 1 ";

         if (sCustomWhere.length() > 7) {
            GetProfile = GetProfile + sCustomWhere;
         }

         GetProfile += " order by customer_last_name ";

         if (this.iLimit > 0)
            GetProfile += " limit " + iLimit;
         if (this.iOffset > 0)
            GetProfile += " offset " + iOffset;
         //System.out.println(GetProfile);
         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         resultset = new RowSetDynaClass(rs, false);

         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("Error: " + e.toString());
         Errors.addError("Customer:getAllMembersDynaBean:SQLException:"
               + e.toString());
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Error: " + e.toString());
         Errors.addError("Customer:getAllMembersDynaBean:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return resultset;
   }

   /**
    * Returns list of all customers > access level 2 as dynabean
    * 
    * @return dynabean
    * @throws SQLException
    * @throws Exception
    */
   public RowSetDynaClass getAllUsersDynaBean() throws SQLException, Exception {

      RowSetDynaClass resultset = null;

      try {
         String GetProfile = "select customer_id, customer_last_name, customer_first_name, "
               + "customer_email, customer_phone, customer_company_name, username, "
               + "case when customer_active_yn = 'y' then '<span style=\"color: green\">active</span>' "
               + "else '<span style=\"color: red;\">inactive</a>' end as customer_status "
               + "from v_customer where customer_access_level > 2 ";

         if (sCustomWhere.length() > 7) {
            GetProfile = GetProfile + sCustomWhere;
         }

         GetProfile += " order by customer_last_name ";

         if (this.iLimit > 0)
            GetProfile += " limit " + iLimit;
         if (this.iOffset > 0)
            GetProfile += " offset " + iOffset;

         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         resultset = new RowSetDynaClass(rs, false);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("Error: " + e.toString());
         Errors.addError("Customer:getAllMembersDynaBean:SQLException:"
               + e.toString());
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Error: " + e.toString());
         Errors.addError("Customer:getAllMembersDynaBean:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return resultset;
   }

   /**
    * Returns customers with roles.
    * 
    * @return drs
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getAllMembersWithRoles() throws SQLException,
         Exception {
      try {
         String GetProfile = "select distinct customer_id, customer_last_name, customer_first_name from v_user_roles ";

         if (sCustomWhere.length() > 7) {
            GetProfile = GetProfile + sCustomWhere;
         }

         GetProfile += " order by customer_last_name ";

         if (this.iLimit > 0)
            GetProfile += " limit " + iLimit;
         if (this.iOffset > 0)
            GetProfile += " offset " + iOffset;

         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:getAllMembersWithRoles:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getAllMembersWithRoles:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns users who can be assigned roles (are active, and > access level 1)
    * 
    * @return drs
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getAllMembersWhoCanHaveRoles()
         throws SQLException, Exception {
      try {
         String GetProfile = "select distinct customer_id, customer_last_name, customer_first_name from customer "
               + "where customer_access_level > 1 and customer_active_yn = 'y' ";

         if (sCustomWhere.length() > 7) {
            GetProfile = GetProfile + sCustomWhere;
         }

         GetProfile += " order by customer_last_name ";

         if (this.iLimit > 0)
            GetProfile += " limit " + iLimit;
         if (this.iOffset > 0)
            GetProfile += " offset " + iOffset;

         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:getAllMembersWhoCanHaveRoles:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getAllMembersWhoCanHaveRoles:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns all roles for customer
    * 
    * @param inId
    * @return drs
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getAllRolesForCustomerId(int inId)
         throws SQLException, Exception {
      try {
         String GetProfile = "select role_id, role_name from v_user_roles where customer_id = '"
               + inId
               + "'";

         if (this.iLimit > 0)
            GetProfile += " limit " + iLimit;
         if (this.iOffset > 0)
            GetProfile += " offset " + iOffset;

         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:getAllRolesForCustomerId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getAllRolesForCustomerId:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns roles not currently assigned to a user.
    * 
    * @param inId
    * @return drs
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getInactiveRolesForCustomerId(int inId)
         throws SQLException, Exception {
      try {
         String GetProfile = "select role_id, role_name from role where role_id not in (select role_id from v_user_roles where customer_id = '"
               + inId
               + "')";

         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:getInactiveRolesForCustomerId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getInactiveRolesForCustomerId:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Adds roles to a customer
    * 
    * @param inId
    * @param paramValues
    * @throws SQLException
    * @throws Exception
    */
   public void AddRolesForUserId(int inId, String[] paramValues)
         throws SQLException, Exception {
      String sqlQuery = "";
      try {
         for (int i = 0; i < paramValues.length; i++) {
            sqlQuery = "insert into user_role (role_id, customer_id) values ('"
                  + paramValues[i]
                  + "', '"
                  + inId
                  + "')";
            pst = conn.prepareStatement(sqlQuery);
            pst.executeUpdate();
         }
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Role:AddRolesForUserId:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Role:AddRolesForUserId:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Deletes roles from a customer
    * 
    * @param inId
    * @throws SQLException
    * @throws Exception
    */
   public void DeleteRolesForUserId(int inId) throws SQLException, Exception {
      try {
         String GetProfile = "delete from user_role where customer_id = '"
               + inId
               + "'";

         pst = conn.prepareStatement(GetProfile);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Customer:DeleteRolesForUserId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:DeleteRolesForUserId:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Gets list of customers who have access level > 2
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet GetAllCustomersAboveAccessLevel2()
         throws SQLException, Exception {
      try {
         String GetProfile = "select customer_first_name || ' ' || customer_last_name as customer_name, "
               + "customer_id  "
               + "from customer where customer_access_level > 2 order by customer_last_name";
         st = conn.createStatement();
         rs = st.executeQuery(GetProfile);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:GetInfo:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:GetInfo:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Gets image id for a customer id
    * 
    * @return int
    * @throws Exception
    */
   public int getCustomersImageId() throws SQLException, Exception {
      int imageId = 1;
      try {
         String query = "select image_id from customer where customer_id = '"
               + customer_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         if (rs.next()) {
            imageId = rs.getInt(1);
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:getCustomersImageId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getCustomersImageId:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return imageId;
   }

   /**
    * Gets one record by customer_id
    * 
    * @return XDisconnectedRowSet
    * @throws ServletException
    * @throws SQLException
    */
   public XDisconnectedRowSet GetOneCustomer() throws Exception, SQLException {
      try {
         String getOneCust = "SELECT * FROM customer WHERE customer_id="
               + customer_id;
         st = conn.createStatement();
         rs = st.executeQuery(getOneCust);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors
               .addError("Customer:GetOneCustomer:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:GetOneCustomer:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Gets one record by customer_id, from view
    * 
    * @return XDisconnectedRowSet
    * @throws ServletException
    * @throws SQLException
    */
   public XDisconnectedRowSet GetOneCustomerFromView() throws Exception,
         SQLException {
      try {
         String getOneCust = "SELECT * FROM v_customer WHERE customer_id="
               + customer_id;
         st = conn.createStatement();
         rs = st.executeQuery(getOneCust);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:GetOneCustomerFromView:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:GetOneCustomerFromView:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Gets one buyer record by customer_id, from view
    * 
    * @return XDisconnectedRowSet
    * @throws ServletException
    * @throws SQLException
    */
   public XDisconnectedRowSet GetOneBuyerFromView() throws Exception,
         SQLException {
      try {
         String getOneCust = "SELECT * FROM v_buyers WHERE customer_id="
               + customer_id;
         st = conn.createStatement();
         rs = st.executeQuery(getOneCust);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:GetOneBuyerFromView:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:GetOneBuyerFromView:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Called during log in process to determine user's access level
    * 
    * @param inEmailAddress
    * @return int access_level
    * @throws ServletException
    * @throws SQLException
    */
   public int getAccessLevel(String inEmailAddress) throws SQLException,
         Exception {
      try {
         String sqlQuery = "select c.customer_access_level "
               + "from customer_email_detail as ce, "
               + "customer as c "
               + "where ce.customer_email = '"
               + inEmailAddress
               + "' "
               + "and c.customer_id = ce.customer_id "
               + "and ce.customer_email_default_yn = 'y'";
         st = conn.createStatement();
         rs = st.executeQuery(sqlQuery);
         if (rs.next()) {
            customer_access_level = rs.getInt("customer_access_level");
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors
               .addError("Customer:getAccessLevel:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getAccessLevel:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return customer_access_level;
   }

   /**
    * Get customer username by email address
    * 
    * @param inEmailAddress
    * @return username
    * @throws SQLException
    * @throws Exception
    */
   public String getUsernameByEmail(String inEmailAddress) throws SQLException,
         Exception {
      String theResult = "";
      try {
         String sqlQuery = "select c.username "
               + "from customer_email_detail as ce, "
               + "customer as c "
               + "where ce.customer_email = '"
               + inEmailAddress
               + "' "
               + "and c.customer_id = ce.customer_id "
               + "and ce.customer_email_default_yn = 'y'";
         st = conn.createStatement();
         rs = st.executeQuery(sqlQuery);
         if (rs.next()) {
            theResult = rs.getString("username");
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:getUsernameByEmail:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:getUsernameByEmail:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return theResult;
   }

   /**
    * Returns rows matching supplied name information.
    * 
    * @return XDisconnectedRowSet
    */
   public XDisconnectedRowSet SearchCustomerByName() throws SQLException,
         Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where c.customer_first_name like '"
               + customer_first_name
               + "%' "
               + "and c.customer_last_name like '"
               + customer_last_name
               + "%' "
               + "and ced.customer_id = c.customer_id "
               + "and c.customer_active_yn = 'y' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:SearchCustomerByName:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:SearchCustomerByName:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * search all customers matching supplied email, first, last name
    * 
    * @return crs
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet SearchCustomerByNameEmail() throws SQLException,
         Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where upper(c.customer_first_name) like upper('"
               + customer_first_name
               + "%') "
               + "and upper(c.customer_last_name) like upper('"
               + customer_last_name
               + "%') "
               + "and ced.customer_id = c.customer_id "
               + "and c.customer_active_yn = 'y' "
               + "and c.customer_access_level > 1 "
               + "and upper(ced.customer_email) like upper('"
               + customer_email_address
               + "%') "
               + "order by customer_last_name, "
               + "customer_first_name";

         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:SearchCustomerByName:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:SearchCustomerByName:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns rows matching supplied name information for inactive customers.
    * 
    * @return XDisconnectedRowSet
    */
   public XDisconnectedRowSet SearchInactiveCustomerByName()
         throws SQLException, Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where upper(c.customer_first_name) like upper('"
               + customer_first_name
               + "%') "
               + "and upper(c.customer_last_name like upper('"
               + customer_last_name
               + "%') "
               + "and ced.customer_id = c.customer_id "
               + "and c.customer_active_yn = 'n' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:SearchCustomerByName:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:SearchCustomerByName:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Search inactive customers by name & email
    * 
    * @return drs
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet SearchInactiveCustomerByNameEmail()
         throws SQLException, Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where upper(c.customer_first_name) like upper('"
               + customer_first_name
               + "%') "
               + "and upper(c.customer_last_name like upper('"
               + customer_last_name
               + "%') "
               + "and upper(ced.customer_email) like upper('"
               + customer_email_address
               + "%') "
               + "and ced.customer_id = c.customer_id "
               + "and c.customer_active_yn = 'n' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:SearchCustomerByName:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:SearchCustomerByName:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns new customers, searching on customer name
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet GetAllNewCustomersByName() throws SQLException,
         Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where upper(c.customer_first_name) like upper('"
               + customer_first_name
               + "%') "
               + "and upper(c.customer_last_name) like upper('"
               + customer_last_name
               + "%') "
               + "and ced.customer_id = c.customer_id "
               + "and c.customer_isnew_yn = 'y' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:GetAllNewCustomersByName:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:GetAllNewCustomersByName:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * find new customers by name & email
    * 
    * @return drs
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet GetAllNewCustomersByNameEmail()
         throws SQLException, Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where upper(c.customer_first_name) like upper('"
               + customer_first_name
               + "%') "
               + "and upper(c.customer_last_name) like upper('"
               + customer_last_name
               + "%') "
               + "and upper(ced.customer_email) like upper('"
               + customer_email_address
               + "%')"
               + "and ced.customer_id = c.customer_id "
               + "and c.customer_isnew_yn = 'y' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:GetAllNewCustomersByName:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:GetAllNewCustomersByName:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns rows matching supplied name information, active customers only.
    * 
    * @return XDisconnectedRowSet
    */
   public XDisconnectedRowSet SearchAllCustomerByName() throws SQLException,
         Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where upper(c.customer_first_name) like upper('"
               + customer_first_name
               + "%') "
               + "and upper(c.customer_last_name) like upper('"
               + customer_last_name
               + "%') "
               + "and ced.customer_id = c.customer_id "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:SearchAllCustomer:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors
               .addError("Customer:SearchAllCustomer:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Return rows matching supplied email address for active customers only.
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet SearchCustomerByEmail() throws SQLException,
         Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where ced.customer_id = c.customer_id and "
               + "upper(ced.customer_email) like upper('"
               + customer_email_address
               + "%') "
               + "and c.customer_active_yn = 'y' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:SearchCustomerByEmail:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:SearchCustomerByEmail:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Return rows matching supplied email address for inactive customers only.
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet SearchInactiveCustomerByEmail()
         throws SQLException, Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where ced.customer_id = c.customer_id and "
               + "ced.customer_email like '"
               + customer_email_address
               + "%' "
               + "and c.customer_active_yn = 'n' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:SearchCustomerByEmail:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:SearchCustomerByEmail:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Searches all customers by email address (includes inactive)
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet SearchAllCustomerByEmail() throws SQLException,
         Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where ced.customer_id = c.customer_id and "
               + "ced.customer_email like '"
               + customer_email_address
               + "%' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:SearchAllCustomerByEmail:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:SearchAllCustomerByEmail:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Gets all new (unprocessed) customers, searching on email
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet GetAllNewCustomersByEmail() throws SQLException,
         Exception {
      try {
         String sql = "select c.customer_id, "
               + "c.customer_first_name, "
               + "c.customer_last_name, "
               + "c.customer_active_yn, "
               + "c.customer_isnew_yn, "
               + "ced.customer_email "
               + "from customer as c, "
               + "customer_email_detail as ced "
               + "where ced.customer_id = c.customer_id and "
               + "ced.customer_email like '"
               + customer_email_address
               + "%' "
               + "and c.customer_isnew_yn = 'y' "
               + "and c.customer_access_level > 1 "
               + "order by customer_last_name, "
               + "customer_first_name";
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:GetAllNewCustomers:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:GetAllNewCustomers:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Gets record by customer id.
    * 
    * @return XDisconnectedRowSet
    */
   public XDisconnectedRowSet GetCustomerDetail() throws SQLException,
         Exception {
      try {
         String sql = "select * from customer where customer_id = "
               + customer_id;
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:GetCustomerDetail:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors
               .addError("Customer:GetCustomerDetail:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   public String returnUsernameForCustomerId(int id) throws SQLException,
         Exception {
      String userName = "";
      try {
         String sql = "select username from customer where customer_id = " + id;
         st = conn.createStatement();
         rs = st.executeQuery(sql);
         if (rs.next()) {
            userName = rs.getString(1);
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Customer:returnUsernameForCustomerId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:returnUsernameForCustomerId:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return userName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
    */
   public void setPrimaryKey(String theKey) {
      this.setCustomer_id(Integer.parseInt(theKey));

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#deleteRecord()
    */
   public void deleteRecord() throws SQLException, Exception {
      this.CustomerDelete();

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.String)
    */
   public void changeActiveStatus(String psStatus) throws SQLException,
         Exception {

      try {
         String sql = "update customer set customer_active_yn = '"
               + psStatus
               + "' "
               + "where customer_id = '"
               + customer_id
               + "'";
         pst = conn.prepareStatement(sql);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Customer:changeActiveStatus:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:changeActiveStatus:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }

   }

   /**
    * Change the status of a customer from new to y/n
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void ChangeCustomerNewStatus() throws SQLException, Exception {
      try {
         String sql = "update customer set customer_isnew_yn = '"
               + customer_isnew_yn
               + "' "
               + "where customer_id = '"
               + customer_id
               + "'";
         pst = conn.prepareStatement(sql);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Customer:ChangeCustomerNewStatus:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Customer:ChangeCustomerNewStatus:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#createCustomWhere(java.lang.String)
    */
   public void createCustomWhere(String psCustomWhere) {
      this.sCustomWhere = psCustomWhere;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#setLimit(int)
    */
   public void setLimit(int pLimit) {
      this.iLimit = pLimit;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#setOffset(int)
    */
   public void setOffset(int pOffset) {
      this.iOffset = pOffset;
   }

   /**
    * @return Returns the conn.
    */
   public Connection getConn() {
      return conn;
   }

   /**
    * @param conn
    *           The conn to set.
    */
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   /**
    * @return Returns the ct_credit_card_id.
    */
   public int getCt_credit_card_id() {
      return ct_credit_card_id;
   }

   /**
    * @param ct_credit_card_id
    *           The ct_credit_card_id to set.
    */
   public void setCt_credit_card_id(int ct_credit_card_id) {
      this.ct_credit_card_id = ct_credit_card_id;
   }

   /**
    * @return Returns the ct_package_id.
    */
   public int getCt_package_id() {
      return ct_package_id;
   }

   /**
    * @param ct_package_id
    *           The ct_package_id to set.
    */
   public void setCt_package_id(int ct_package_id) {
      this.ct_package_id = ct_package_id;
   }

   /**
    * @return Returns the ct_salutation_id.
    */
   public int getCt_salutation_id() {
      return ct_salutation_id;
   }

   /**
    * @param ct_salutation_id
    *           The ct_salutation_id to set.
    */
   public void setCt_salutation_id(int ct_salutation_id) {
      this.ct_salutation_id = ct_salutation_id;
   }

   /**
    * @return Returns the ct_term_id.
    */
   public int getCt_term_id() {
      return ct_term_id;
   }

   /**
    * @param ct_term_id
    *           The ct_term_id to set.
    */
   public void setCt_term_id(int ct_term_id) {
      this.ct_term_id = ct_term_id;
   }

   /**
    * @return Returns the customer_access_level.
    */
   public int getCustomer_access_level() {
      return customer_access_level;
   }

   /**
    * @param customer_access_level
    *           The customer_access_level to set.
    */
   public void setCustomer_access_level(int customer_access_level) {
      this.customer_access_level = customer_access_level;
   }

   /**
    * @return Returns the customer_active_yn.
    */
   public String getCustomer_active_yn() {
      return customer_active_yn;
   }

   /**
    * @param customer_active_yn
    *           The customer_active_yn to set.
    */
   public void setCustomer_active_yn(String customer_active_yn) {
      this.customer_active_yn = customer_active_yn;
   }

   /**
    * @return Returns the customer_add_to_mailing_list.
    */
   public int getCustomer_add_to_mailing_list() {
      return customer_add_to_mailing_list;
   }

   /**
    * @param customer_add_to_mailing_list
    *           The customer_add_to_mailing_list to set.
    */
   public void setCustomer_add_to_mailing_list(int customer_add_to_mailing_list) {
      this.customer_add_to_mailing_list = customer_add_to_mailing_list;
   }

   /**
    * @return Returns the customer_card_expiry_month.
    */
   public int getCustomer_card_expiry_month() {
      return customer_card_expiry_month;
   }

   /**
    * @param customer_card_expiry_month
    *           The customer_card_expiry_month to set.
    */
   public void setCustomer_card_expiry_month(int customer_card_expiry_month) {
      this.customer_card_expiry_month = customer_card_expiry_month;
   }

   /**
    * @return Returns the customer_company_description.
    */
   public String getCustomer_company_description() {
      return myDbUtils.replace(customer_company_description, "\'\'", "\'");
   }

   /**
    * @param customer_company_description
    *           The customer_company_description to set.
    */
   public void setCustomer_company_description(
         String customer_company_description) {
      this.customer_company_description = myDbUtils
            .fixSqlFieldValue(customer_company_description);
   }

   /**
    * @return Returns the customer_company_name.
    */
   public String getCustomer_company_name() {
      return myDbUtils.replace(customer_company_name, "\'\'", "\'");
   }

   /**
    * @param customer_company_name
    *           The customer_company_name to set.
    */
   public void setCustomer_company_name(String customer_company_name) {
      this.customer_company_name = myDbUtils
            .fixSqlFieldValue(customer_company_name);
   }

   /**
    * @return Returns the customer_credit_card_expiry_year.
    */
   public int getCustomer_credit_card_expiry_year() {
      return customer_credit_card_expiry_year;
   }

   /**
    * @param customer_credit_card_expiry_year
    *           The customer_credit_card_expiry_year to set.
    */
   public void setCustomer_credit_card_expiry_year(
         int customer_credit_card_expiry_year) {
      this.customer_credit_card_expiry_year = customer_credit_card_expiry_year;
   }

   /**
    * @return Returns the customer_credit_card_number.
    */
   public String getCustomer_credit_card_number() {
      return customer_credit_card_number;
   }

   /**
    * @param customer_credit_card_number
    *           The customer_credit_card_number to set.
    */
   public void setCustomer_credit_card_number(String customer_credit_card_number) {
      this.customer_credit_card_number = customer_credit_card_number;
   }

   /**
    * @return Returns the customer_email_address.
    */
   public String getCustomer_email_address() {
      return customer_email_address;
   }

   /**
    * @param customer_email_address
    *           The customer_email_address to set.
    */
   public void setCustomer_email_address(String customer_email_address) {
      this.customer_email_address = customer_email_address;
   }

   /**
    * @return Returns the customer_first_name.
    */
   public String getCustomer_first_name() {
      return myDbUtils.replace(customer_first_name, "\'\'", "\'");
   }

   /**
    * @param customer_first_name
    *           The customer_first_name to set.
    */
   public void setCustomer_first_name(String customer_first_name) {
      this.customer_first_name = myDbUtils
            .fixSqlFieldValue(customer_first_name);
   }

   /**
    * @return Returns the customer_id.
    */
   public int getCustomer_id() {
      return customer_id;
   }

   /**
    * @param customer_id
    *           The customer_id to set.
    */
   public void setCustomer_id(int customer_id) {
      this.customer_id = customer_id;
   }

   /**
    * @return Returns the customer_isnew_yn.
    */
   public String getCustomer_isnew_yn() {
      return customer_isnew_yn;
   }

   /**
    * @param customer_isnew_yn
    *           The customer_isnew_yn to set.
    */
   public void setCustomer_isnew_yn(String customer_isnew_yn) {
      this.customer_isnew_yn = customer_isnew_yn;
   }

   /**
    * @return Returns the customer_last_name.
    */
   public String getCustomer_last_name() {
      return myDbUtils.replace(customer_last_name, "\'\'", "\'");
   }

   /**
    * @param customer_last_name
    *           The customer_last_name to set.
    */
   public void setCustomer_last_name(String customer_last_name) {
      this.customer_last_name = myDbUtils.fixSqlFieldValue(customer_last_name);
   }

   /**
    * @return Returns the customer_name_on_card.
    */
   public String getCustomer_name_on_card() {
      return myDbUtils.replace(customer_name_on_card, "\'\'", "\'");
   }

   /**
    * @param customer_name_on_card
    *           The customer_name_on_card to set.
    */
   public void setCustomer_name_on_card(String customer_name_on_card) {
      this.customer_name_on_card = myDbUtils
            .fixSqlFieldValue(customer_name_on_card);
   }

   /**
    * @return Returns the customer_next_billing_date.
    */
   public String getCustomer_next_billing_date() {
      return customer_next_billing_date;
   }

   /**
    * @param customer_next_billing_date
    *           The customer_next_billing_date to set.
    */
   public void setCustomer_next_billing_date(String customer_next_billing_date) {
      this.customer_next_billing_date = customer_next_billing_date;
   }

   /**
    * @return Returns the customer_password.
    */
   public String getCustomer_password() {
      return customer_password;
   }

   /**
    * @param customer_password
    *           The customer_password to set.
    */
   public void setCustomer_password(String customer_password) {
      this.customer_password = myDbUtils.fixSqlFieldValue(customer_password);
   }

   /**
    * @return Returns the customer_registration_date.
    */
   public String getCustomer_registration_date() {
      return customer_registration_date;
   }

   /**
    * @param customer_registration_date
    *           The customer_registration_date to set.
    */
   public void setCustomer_registration_date(String customer_registration_date) {
      this.customer_registration_date = customer_registration_date;
   }

   /**
    * @return Returns the customer_website_url.
    */
   public String getCustomer_website_url() {
      return customer_website_url;
   }

   /**
    * @param customer_website_url
    *           The customer_website_url to set.
    */
   public void setCustomer_website_url(String customer_website_url) {
      this.customer_website_url = customer_website_url;
   }

   /**
    * @return Returns the iLimit.
    */
   public int getILimit() {
      return iLimit;
   }

   /**
    * @param limit
    *           The iLimit to set.
    */
   public void setILimit(int limit) {
      iLimit = limit;
   }

   /**
    * @return Returns the iOffset.
    */
   public int getIOffset() {
      return iOffset;
   }

   /**
    * @param offset
    *           The iOffset to set.
    */
   public void setIOffset(int offset) {
      iOffset = offset;
   }

   /**
    * @return Returns the sCustomWhere.
    */
   public String getSCustomWhere() {
      return sCustomWhere;
   }

   /**
    * @param customWhere
    *           The sCustomWhere to set.
    */
   public void setSCustomWhere(String customWhere) {
      sCustomWhere = customWhere;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }
}