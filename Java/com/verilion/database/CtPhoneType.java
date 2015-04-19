//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtPhoneType.java,v $
//Revision 1.3  2004/06/25 17:41:20  tcs
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates ct_phone_type table in db, and related
 * operations.
 * <P>
 * Dec 1, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 *  
 */
public class CtPhoneType {

	private int ct_phone_type_id = 0;
	private String ct_phone_type_name = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public CtPhoneType() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateCtPhoneType() throws SQLException, Exception {
		try {
			String update =
				"UPDATE ct_phone_type SET "
					+ "ct_phone_type_name = '"
					+ ct_phone_type_name
					+ "' "
					+ "WHERE ct_phone_type_id = '"
					+ ct_phone_type_id
					+ "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtPhoneType:updateCtPhoneType:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtPhoneType:updateCtPhoneType:Exception:"
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
	public void addCtPhoneType() throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO ct_salutation ("
					+ "ct_phone_type_name) "
					+ "VALUES("
					+ "'"
					+ ct_phone_type_name
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtPhoneType:addCtPhoneType:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtPhoneType:addCtPhoneType:Exception:"
					+ e.toString());
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
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllPhoneTypesIds() throws SQLException, Exception {
		try {
			String getAll =
				"select * from ct_phone_type";
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
				"CtPhoneType:getAllPhoneTypesIds:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtPhoneType:getAllPhoneTypesIds:Exception:"
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
	 * Deletes phone type by id.
	 * 
	 * @throws Exception
	 */
	public void deletePhoneTypeById() throws SQLException, Exception {
		try {
			String deleteRecord =
				"delete from ct_phone_type where ct_phone_type_id = " + ct_phone_type_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtPhoneType:deletePhoneTypeById:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtPhoneType:deletePhoneTypeById:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Returns phone type name for supplied id.
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getPhoneTypeName() throws SQLException, Exception {
		String theName = "";
		try {
			String query =
				"select ct_phone_type_name from ct_salutation where ct_phone_type_id = '"
					+ ct_phone_type_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theName = rs.getString("ct_phone_type_name");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtPhoneType:getPhoneTypeName:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtPhoneType:getPhoneTypeName:Exception:"
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
	 * @return Returns the ct_phone_type_name.
	 */
	public String getCt_phone_type_name() {
		return ct_phone_type_name;
	}
	/**
	 * @param ct_phone_type_name The ct_phone_type_name to set.
	 */
	public void setCt_phone_type_name(String ct_phone_type_name) {
		this.ct_phone_type_name = ct_phone_type_name;
	}
}