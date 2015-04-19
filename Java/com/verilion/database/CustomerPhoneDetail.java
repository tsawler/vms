//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CustomerPhoneDetail.java,v $
//Revision 1.4  2004/10/26 15:35:26  tcs
//Improved javadocs
//
//Revision 1.3  2004/06/25 18:26:00  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:13  tcs
//Mods for listeners and connection pooling improvements
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

import org.sourceforge.jxutil.sql.*;

import com.verilion.object.Errors;

/**
 * Manipulates customer_phone_detail table in db, and related operations.
 * <P>
 * Dec 1, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 *  
 */
public class CustomerPhoneDetail {

	private int customer_phone_id = 0;
	private int ct_phone_type_id = 0;
	private String customer_phone = "";
	private String customer_phone_extension = "";
	private String customer_phone_default_yn = "";
	private int customer_id = 0;
	private String customer_email_default_yn = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public CustomerPhoneDetail() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateCustomerPhoneDetail()
		throws SQLException, Exception {
		try {
			String update =
				"UPDATE customer_phone_detail SET "
					+ "ct_phone_type_id = '"
					+ ct_phone_type_id
					+ "', "
					+ "customer_phone = '"
					+ customer_phone
					+ "', "
					+ "customer_phone_extension = '"
					+ customer_phone_extension
					+ "', "
					+ "customer_phone_default_yn = '"
					+ customer_phone_default_yn
					+ "', "
					+ "customer_id = '"
					+ customer_id
					+ "' "
					+ "WHERE customer_phone_id = '"
					+ customer_phone_id
					+ "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:updateCustomerPhoneDetail:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:updateCustomerPhoneDetail:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Update primary customer phone info method.
	 * 
	 * @throws Exception
	 */
	public void updateCustomerPhoneDetailPrimaryPhoneNumber()
		throws SQLException, Exception {
		try {
			String update =
				"UPDATE customer_phone_detail SET "
					+ "ct_phone_type_id = '"
					+ ct_phone_type_id
					+ "', "
					+ "customer_phone = '"
					+ customer_phone
					+ "', "
					+ "customer_phone_extension = '"
					+ customer_phone_extension
					+ "' "
					+ "WHERE customer_id = '"
					+ customer_id
					+ "' "
					+ "AND customer_phone_default_yn = 'y'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:updateCustomerPhoneDetailPrimaryPhoneNumber:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:updateCustomerPhoneDetailPrimaryPhoneNumber:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Update customer fax  method.
	 * 
	 * @throws Exception
	 */
	public void updateCustomerPhoneDetailFaxNumber()
		throws SQLException, Exception {
		try {
			String update =
				"UPDATE customer_phone_detail SET "
					+ "customer_phone = '"
					+ customer_phone
					+ "', "
					+ "customer_phone_extension = '"
					+ customer_phone_extension
					+ "' "
					+ "WHERE customer_id = '"
					+ customer_id
					+ "' "
					+ "AND ct_phone_type_id = '4'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:updateCustomerPhoneDetailFaxNumber:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:updateCustomerPhoneDetailFaxNumber:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Insert method.
	 * 
	 * @throws Exception
	 */
	public void addCustomerPhoneDetail()
		throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO customer_phone_detail ("
					+ "ct_phone_type_id, "
					+ "customer_phone, "
					+ "customer_phone_extension, "
					+ "customer_phone_default_yn, "
					+ "customer_id) "
					+ "VALUES("
					+ "'"
					+ ct_phone_type_id
					+ "', "
					+ "'"
					+ customer_phone
					+ "', "
					+ "'"
					+ customer_phone_extension
					+ "', "
					+ "'"
					+ customer_phone_default_yn
					+ "', "
					+ "'"
					+ customer_id
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:addCustomerPhoneDetail:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:addCustomerPhoneDetail:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Returns all phone numbers for customer id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllPhoneForCustomerId()
		throws SQLException, Exception {
		try {
			String getemails =
				"select * from customer_phone_detail "
					+ "where customer_id = '"
					+ customer_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(getemails);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:getAllPhoneForCustomerId:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:getAllPhoneForCustomerId:Exception:"
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
	 * Deletes phone number by id.
	 * 
	 * @throws Exception
	 */
	public void deletePhoneNumberById() throws SQLException, Exception {
		try {
			String deleteRecord =
				"delete from customer_phone_detail where customer_phone_id = "
					+ customer_phone_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:deletePhoneNumberById:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:deletePhoneNumberById:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Deletes all customer phone numbers by customer id.
	 * 
	 * @throws Exception
	 */
	public void deleteAllCustomerPhoneNumbers()
		throws SQLException, Exception {
		try {
			String deleteRecord =
				"delete from customer_phone_detail where customer_id = "
					+ customer_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:deleteAllCustomerPhoneNumbers:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:deleteAllCustomerPhoneNumbers:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Returns primary phone number and extension for supplied customer id.
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getPrimaryPhoneForCustomerId()
		throws SQLException, Exception {
		String thePhone = "";
		try {
			String query =
				"select customer_phone, "
					+ "customer_phone_extension "
					+ "from customer_phone_detail where "
					+ "customer_id = '"
					+ customer_id
					+ "' "
					+ "and customer_phone_default_yn = 'y'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				thePhone = rs.getString("customer_phone");
				if (rs.getString("customer_phone_extension").length() > 0) {
					thePhone += " ext. "
						+ rs.getString("customer_phone_extension");
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:getPrimaryPhoneForCustomerId:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:getPrimaryPhoneForCustomerId:Exception:"
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
		return thePhone;
	}

	/**
	 * Returns primary phone number and extension for supplied customer id.
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getFaxForCustomerId() throws SQLException, Exception {
		String theFax = "";
		try {
			String query =
				"select customer_phone, "
					+ "customer_phone_extension "
					+ "from customer_phone_detail where "
					+ "customer_id = '"
					+ customer_id
					+ "' "
					+ "and ct_phone_type_id = '4'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				theFax = rs.getString("customer_phone");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CustomerPhoneDetail:getFaxForCustomerId:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CustomerPhoneDetail:getFaxForCustomerId:Exception:"
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
		return theFax;
	}


	/**
	 * @return Returns the conn.
	 */
	public Connection getConn() {
		return conn;
	}
	/**
	 * @param conn The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	/**
	 * @return Returns the ct_phone_type_id.
	 */
	public int getCt_phone_type_id() {
		return ct_phone_type_id;
	}
	/**
	 * @param ct_phone_type_id The ct_phone_type_id to set.
	 */
	public void setCt_phone_type_id(int ct_phone_type_id) {
		this.ct_phone_type_id = ct_phone_type_id;
	}
	/**
	 * @return Returns the customer_email_default_yn.
	 */
	public String getCustomer_email_default_yn() {
		return customer_email_default_yn;
	}
	/**
	 * @param customer_email_default_yn The customer_email_default_yn to set.
	 */
	public void setCustomer_email_default_yn(String customer_email_default_yn) {
		this.customer_email_default_yn = customer_email_default_yn;
	}
	/**
	 * @return Returns the customer_id.
	 */
	public int getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id The customer_id to set.
	 */
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @return Returns the customer_phone.
	 */
	public String getCustomer_phone() {
		return customer_phone;
	}
	/**
	 * @param customer_phone The customer_phone to set.
	 */
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	/**
	 * @return Returns the customer_phone_default_yn.
	 */
	public String getCustomer_phone_default_yn() {
		return customer_phone_default_yn;
	}
	/**
	 * @param customer_phone_default_yn The customer_phone_default_yn to set.
	 */
	public void setCustomer_phone_default_yn(String customer_phone_default_yn) {
		this.customer_phone_default_yn = customer_phone_default_yn;
	}
	/**
	 * @return Returns the customer_phone_extension.
	 */
	public String getCustomer_phone_extension() {
		return customer_phone_extension;
	}
	/**
	 * @param customer_phone_extension The customer_phone_extension to set.
	 */
	public void setCustomer_phone_extension(String customer_phone_extension) {
		this.customer_phone_extension = customer_phone_extension;
	}
	/**
	 * @return Returns the customer_phone_id.
	 */
	public int getCustomer_phone_id() {
		return customer_phone_id;
	}
	/**
	 * @param customer_phone_id The customer_phone_id to set.
	 */
	public void setCustomer_phone_id(int customer_phone_id) {
		this.customer_phone_id = customer_phone_id;
	}
}