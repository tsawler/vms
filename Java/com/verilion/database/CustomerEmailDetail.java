package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates customer_email_detail code table in db, and related operations.
 * <P>
 * Dec 1, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class CustomerEmailDetail {

	private int customer_email_id = 0;
	private String customer_email = "";
	private int customer_id = 0;
	private String customer_email_default_yn = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public CustomerEmailDetail() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateCustomerEmailDetail() throws SQLException, Exception {
		try {
			String update = "UPDATE customer_email_detail SET "
					+ "customer_email = '" + customer_email + "', "
					+ "customer_id = '" + customer_id + "', "
					+ "customer_email_default_yn = '"
					+ customer_email_default_yn + "' "
					+ "WHERE customer_email_id = '" + customer_email_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:updateCustomerEmailDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:updateCustomerEmailDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void updateAllEmailsForCustomerId() throws SQLException, Exception {
		try {
			String update = "UPDATE customer_email_detail SET "
					+ "customer_email = '" + customer_email + "' "
					+ "WHERE customer_id = '" + customer_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:updateCustomerEmailDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:updateCustomerEmailDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Update primary email address method.
	 * 
	 * @throws Exception
	 */
	public void updateCustomerEmailDetailPrimaryEmail() throws SQLException,
			Exception {
		try {
			String update = "UPDATE customer_email_detail SET "
					+ "customer_email = '" + customer_email + "' "
					+ "WHERE customer_id = '" + customer_id + "' "
					+ "AND customer_email_default_yn = 'y'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:updateCustomerEmailDetailPrimaryEmail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:updateCustomerEmailDetailPrimaryEmail:Exception:"
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
	public void addCustomerEmailDetail() throws SQLException, Exception {
		try {
			String save = "INSERT INTO customer_email_detail ("
					+ "customer_id, " + "customer_email, "
					+ "customer_email_default_yn) " + "VALUES(" + "'"
					+ customer_id + "', " + "'" + customer_email + "', " + "'"
					+ customer_email_default_yn + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:addCustomerEmailDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:addCustomerEmailDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns all access level ids and names.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllEmailsForCustomerId() throws SQLException,
			Exception {
		try {
			String getemails = "select * from customer_email_detail "
					+ "where customer_id = '" + customer_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(getemails);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:getAllEmailsForCustomerId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:getAllEmailsForCustomerId:Exception:"
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
	 * Deletes email by id.
	 * 
	 * @throws Exception
	 */
	public void deleteEmailByEmailId() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from customer_email_detail where customer_email_id = "
					+ customer_email_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:deleteEmailByEmailId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:deleteEmailByEmailId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Deletes all email for customer id
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deleteAllEmailsForCustomerId() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from customer_email_detail where customer_id = "
					+ customer_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:deleteAllEmailsForCustomerId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:deleteAllEmailsForCustomerId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns primary email for supplied customer id.
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getPrimaryEmailAddressForCustomerId() throws SQLException,
			Exception {
		String theEmail = "";
		try {
			String query = "select customer_email from customer_email_detail where "
					+ "customer_id = '"
					+ customer_id
					+ "' "
					+ "and customer_email_default_yn = 'y'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				theEmail = rs.getString("customer_email");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:getPrimaryEmailAddressForCustomerId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:getPrimaryEmailAddressForCustomerId:Exception:"
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
		return theEmail;
	}

	/**
	 * Returns customer id for supplied email.
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getCustomerIdByEmailAddress() throws SQLException, Exception {
		int theId = 0;
		try {
			String query = "select customer_id from customer_email_detail where "
					+ "customer_email = '" + customer_email + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				theId = rs.getInt("customer_id");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("CustomerEmailDetail:getPrimaryEmailAddressForCustomerId:Exception:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerEmailDetail:getPrimaryEmailAddressForCustomerId:Exception:"
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
		return theId;
	}

	/**
	 * @return Returns the conn.
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn
	 *            The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * @return Returns the customer_email.
	 */
	public String getCustomer_email() {
		return customer_email;
	}

	/**
	 * @param customer_email
	 *            The customer_email to set.
	 */
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	/**
	 * @return Returns the customer_email_default_yn.
	 */
	public String getCustomer_email_default_yn() {
		return customer_email_default_yn;
	}

	/**
	 * @param customer_email_default_yn
	 *            The customer_email_default_yn to set.
	 */
	public void setCustomer_email_default_yn(String customer_email_default_yn) {
		this.customer_email_default_yn = customer_email_default_yn;
	}

	/**
	 * @return Returns the customer_email_id.
	 */
	public int getCustomer_email_id() {
		return customer_email_id;
	}

	/**
	 * @param customer_email_id
	 *            The customer_email_id to set.
	 */
	public void setCustomer_email_id(int customer_email_id) {
		this.customer_email_id = customer_email_id;
	}

	/**
	 * @return Returns the customer_id.
	 */
	public int getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id
	 *            The customer_id to set.
	 */
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
}