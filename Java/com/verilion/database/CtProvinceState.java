//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtProvinceState.java,v $
//Revision 1.3.8.1.8.1  2007/03/23 11:02:27  tcs
//Changes to reflect new DB structure
//
//Revision 1.3.8.1  2005/09/01 17:06:16  tcs
//Changed order of get province state method
//
//Revision 1.3  2004/06/25 17:42:50  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:11  tcs
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
 * Manipulates ct_province_state table in db, and related operations.
 * <P>
 * Jul 02, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * @author tsawler
 *  
 */
public class CtProvinceState {

	private int ct_province_state_id = 0;
	private String ct_province_state_name = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public CtProvinceState() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateCtProvinceState() throws SQLException, Exception {
		try {
			String update =
				"UPDATE ct_province_state SET "
					+ "ct_province_state_name = '"
					+ ct_province_state_name
					+ "' "
					+ "where ct_province_state_id = '"
					+ ct_province_state_id
					+ "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError("CtProvinceState:updateCtProvinceState:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtProvinceState:updateCtProvinceState:Exception:" + e.toString());
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
	public void addCtProvinceState() throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO ct_province_state ("
					+ "ct_province_state_id, "
					+ "ct_province_state_name) "
					+ "VALUES("
					+ "'"
					+ ct_province_state_id
					+ "', '"
					+ ct_province_state_name
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError("CtProvinceState:addCtProvinceState:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtProvinceState:addCtProvinceState:Exception:" + e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Returns all records.
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllProvinceState() throws SQLException, Exception {
		try {
			String GetCategory = "select * from v_province_state order by ct_country_id, ct_province_state_name";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError("CtProvinceState:getAllProvinceState:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtProvinceState:getAllProvinceState:Exception:" + e.toString());
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
	 * Returns province or state name for supplied id
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getProvinceStateNameById() throws SQLException, Exception {
		String theName = "";
		try {
			String query = "select ct_province_state_name from ct_province_state "
				+ "where ct_province_state_id = '"
				+ ct_province_state_id
				+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theName = rs.getString("ct_province_state_name");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError("CtProvinceState:getProvinceStateNameById:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtProvinceState:getProvinceStateNameById:Exception:" + e.toString());
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
	 * @return Returns the ct_province_state_id.
	 */
	public int getCt_province_state_id() {
		return ct_province_state_id;
	}
	
	/**
	 * @param ct_province_state_id The ct_province_state_id to set.
	 */
	public void setCt_province_state_id(int ct_province_state_id) {
		this.ct_province_state_id = ct_province_state_id;
	}
	
	/**
	 * @return Returns the ct_province_state_name.
	 */
	public String getCt_province_state_name() {
		return ct_province_state_name;
	}
	
	/**
	 * @param ct_province_state_name The ct_province_state_name to set.
	 */
	public void setCt_province_state_name(String ct_province_state_name) {
		this.ct_province_state_name = ct_province_state_name;
	}
	
}