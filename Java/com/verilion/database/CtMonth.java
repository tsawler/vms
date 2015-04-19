//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtMonth.java,v $
//Revision 1.3.6.1  2005/08/21 15:37:14  tcs
//Removed unused membres, code cleanup
//
//Revision 1.3  2004/06/25 17:35:49  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:07  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:45  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates ct_month table in db, and related
 * operations.
 * <P>
 * Nov 30, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 *  
 */
public class CtMonth {

	private int ct_month_id = 0;
	private String ct_month_name = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;

	public CtMonth() {
		super();
	}

	/**
	 * Returns all month ids and names.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllMonthNamesIds() throws SQLException, Exception {
		try {
			String getAll =
				"select * from ct_month";
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
				"com.verilion.database.CtMonth:getAllMonthNamesIds:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtMonth:getAllMonthNamesIds:Exception:"
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
	 * Returns month name for supplied id.
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getMonthById() throws SQLException, Exception {
		String theMontb = "";
		try {
			String query =
				"select ct_month_name from ct_month where ct_month_id = '"
					+ ct_month_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theMontb = rs.getString("ct_month_name");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtMonth:getMonthById:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtMonth:getMonthById:Exception:"
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
		return theMontb;
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
	 * @return Returns the ct_month_id.
	 */
	public int getCt_month_id() {
		return ct_month_id;
	}
	/**
	 * @param ct_month_id The ct_month_id to set.
	 */
	public void setCt_month_id(int ct_month_id) {
		this.ct_month_id = ct_month_id;
	}
	/**
	 * @return Returns the ct_month_name.
	 */
	public String getCt_month_name() {
		return ct_month_name;
	}
	/**
	 * @param ct_month_name The ct_month_name to set.
	 */
	public void setCt_month_name(String ct_month_name) {
		this.ct_month_name = ct_month_name;
	}
}