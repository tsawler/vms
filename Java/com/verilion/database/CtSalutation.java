//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtSalutation.java,v $
//Revision 1.3  2004/06/25 17:45:08  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:08  tcs
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

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates ct_salutation table in db, and related
 * operations.
 * <P>
 * Nov 28, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 *  
 */
public class CtSalutation {

	private int ct_salutation_id = 0;
	private String ct_salutation_name = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public CtSalutation() {
		super();
	}

	/**
	 * Update CtSalutation method.
	 * 
	 * @throws Exception
	 */
	public void updateCtSalutation() throws SQLException, Exception {
		try {
			String update =
				"UPDATE ct_salutation SET "
					+ "ct_salutation_name = '"
					+ ct_salutation_name
					+ "' "
					+ "WHERE ct_salutation_id = '"
					+ ct_salutation_id
					+ "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:updateCtSalutation:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:updateCtSalutation:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Insert template method.
	 * 
	 * @throws Exception
	 */
	public void addCtSalutation() throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO ct_salutation ("
					+ "ct_salutation_name) "
					+ "VALUES("
					+ "'"
					+ ct_salutation_name
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:addCtSalutation:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:addCtSalutation:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Returns all salutation ids and names.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllSalutationNamesIds() throws SQLException, Exception {
		try {
			String getAll =
				"select * from ct_salutation";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:getAllSalutationNamesIds:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:getAllSalutationNamesIds:Exception:"
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
	 * Deletes salutation by id.
	 * 
	 * @throws Exception
	 */
	public void deleteSalutationById() throws SQLException, Exception {
		try {
			String deleteRecord =
				"delete from ct_salutation where ct_salutation_id = " + ct_salutation_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:deleteSalutationById:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:deleteSalutationById:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Returns salutation name for supplied id.
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getSalutationName() throws SQLException, Exception {
		String theSalutation = "";
		try {
			String query =
				"select ct_salutation_name from ct_salutation where ct_salutation_id = '"
					+ ct_salutation_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theSalutation = rs.getString("ct_salutation_name");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:getSalutationName:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtSalutation:getSalutationName:Exception:"
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
		return theSalutation;
	}

	/**
	 * @return Returns the ct_salutation_id.
	 */
	public int getCt_salutation_id() {
		return ct_salutation_id;
	}
	
	/**
	 * @param ct_salutation_id The ct_salutation_id to set.
	 */
	public void setCt_salutation_id(int ct_salutation_id) {
		this.ct_salutation_id = ct_salutation_id;
	}
	
	/**
	 * @return Returns the ct_salutation_name.
	 */
	public String getCt_salutation_name() {
		return ct_salutation_name;
	}
	
	/**
	 * @param ct_salutation_name The ct_salutation_name to set.
	 */
	public void setCt_salutation_name(String ct_salutation_name) {
		this.ct_salutation_name = ct_salutation_name;
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
}