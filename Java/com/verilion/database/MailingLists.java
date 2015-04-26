package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates mailing_lists table in db, and related operations.
 * <P>
 * Nov 28, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class MailingLists {

	public int list_id = 0;
	public int customer_id = 0;
	public int breed_id = 0;
	private PreparedStatement pst = null;
	private Statement st = null;
	public Connection conn = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private ResultSet rs = null;

	public MailingLists() {
		super();
	}

	/**
	 * Insert method.
	 * 
	 * @throws Exception
	 */
	public void addCustomerToList() throws SQLException, Exception {
		try {
			String save = "INSERT INTO mailing_lists (" + "customer_id, "
					+ "breed_id) " + "VALUES(" + "'" + customer_id + "', "
					+ "'" + breed_id + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MailingLists:.addCustomerToList:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MailingLists:addCustomerToList:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns all customer ids for given breed id.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getCustomerIdsForBreedId() throws SQLException,
			Exception {
		try {
			String query = "select customer_id from mailing_lists where breed_id = '"
					+ breed_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MailingLists:getCustomerIdsForBreedId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MailingLists:getCustomerIdsForBreedId:Exception:"
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
	 * Returns all breed ids for given customer id.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getBreedsForCustomerId() throws SQLException,
			Exception {
		try {
			String query = "select breed_id from mailing_lists where customer_id = '"
					+ customer_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MailingLists:getBreedsForCustomerId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MailingLists:getBreedsForCustomerId:Exception:"
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
	 * Deletes all records for a customer id.
	 * 
	 * @throws Exception
	 */
	public void deleteAllByCustId() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from mailing_lists where customer_id = "
					+ customer_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MailingLists:deleteAllByCustId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MailingLists:deleteAllByCustId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Deletes a breed for a customer id
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deleteBreedForCustId() throws SQLException, Exception {

		try {
			String query = "delete from mailing_lists where breed_id = '"
					+ breed_id + "'" + "and customer_id = '" + customer_id
					+ "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.CtAccessLevel:getAccessLevelName:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.CtAccessLevel:getAccessLevelName:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * @return Returns the breed_id.
	 */
	public int getBreed_id() {
		return breed_id;
	}

	/**
	 * @param breed_id
	 *            The breed_id to set.
	 */
	public void setBreed_id(int breed_id) {
		this.breed_id = breed_id;
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

	/**
	 * @return Returns the list_id.
	 */
	public int getList_id() {
		return list_id;
	}

	/**
	 * @param list_id
	 *            The list_id to set.
	 */
	public void setList_id(int list_id) {
		this.list_id = list_id;
	}
}