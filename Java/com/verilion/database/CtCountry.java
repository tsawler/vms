//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtCountry.java,v $
//Revision 1.3  2004/06/25 17:27:13  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:12  tcs
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
 * Manipulates ct_country table in db, and related operations.
 * <P>
 * Nov 30, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * @author tsawler
 *  
 */
public class CtCountry {

	private int ct_country_id = 0;
	private String ct_country_name = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public CtCountry() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateCtCountry() throws SQLException, Exception {
		try {
			String update =
				"UPDATE ct_country SET "
					+ "ct_country_name = '"
					+ ct_country_name
					+ "' "
					+ "where ct_country_id = '"
					+ ct_country_id
					+ "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
		}
		catch (SQLException e) {
			Errors.addError("CtCountry:updateCtCountry:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtCountry:updateCtCountry:Exception:" + e.toString());
		}
	}

	/**
	 * Insert method.
	 * 
	 * @throws Exception
	 */
	public void addCtCountry() throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO ct_country ("
					+ "ct_country_name) "
					+ "VALUES("
					+ "'"
					+ ct_country_name
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
		}
		catch (SQLException e) {
			Errors.addError("CtCountry:addCtCountry:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtCountry:addCtCountry:Exception:" + e.toString());
		}
	}

	/**
	 * Returns all records.
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllCountries() throws SQLException, Exception {
		try {
			String GetCategory = "select * from ct_country";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
		}
		catch (SQLException e) {
			Errors.addError("CtCountry:getAllCountries:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtCountry:getAllCountries:Exception:" + e.toString());
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
	 * Returns country_name 
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getCountryNameById() throws SQLException, Exception {
		String theName = "";
		try {
			String GetCategory = "select ct_country_name from ct_country "
				+ "where ct_country_id = '"
				+ ct_country_id
				+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			
			if (rs.next()) {
				theName = rs.getString("ct_country_name");
			}
			rs.close();
			rs = null;
		}
		catch (SQLException e) {
			Errors.addError("CtCountry:getCountryNameById:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtCountry:getCountryNameById:Exception:" + e.toString());
		} finally {
		    if (rs != null) {
		        rs.close();
		        rs = null;
		    }
		}
		return theName;
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
	 * @return Returns the ct_country_id.
	 */
	public int getCt_country_id() {
		return ct_country_id;
	}
	/**
	 * @param ct_country_id The ct_country_id to set.
	 */
	public void setCt_country_id(int ct_country_id) {
		this.ct_country_id = ct_country_id;
	}
	/**
	 * @return Returns the ct_country_name.
	 */
	public String getCt_country_name() {
		return ct_country_name;
	}
	/**
	 * @param ct_country_name The ct_country_name to set.
	 */
	public void setCt_country_name(String ct_country_name) {
		this.ct_country_name = ct_country_name;
	}
}