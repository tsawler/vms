//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtAccessLevel.java,v $
//Revision 1.4  2004/07/08 11:49:27  tcs
//Cleaned up comments
//
//Revision 1.3  2004/06/25 17:25:30  tcs
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

import com.verilion.object.Errors;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

/**
 * Manipulates ct_access_level code table in db, and related
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
public class CtAccessLevel {

	private int ct_access_level_id = 0;
	private String ct_access_level_name = "";
	private int ct_access_level = 0;
	private int page_access_level = 0;
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public CtAccessLevel() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateAccessLevel() throws SQLException, Exception {
		try {
			String update =
				"UPDATE ct_access_level SET "
					+ "ct_access_level_name = '"
					+ ct_access_level_name
					+ "', "
					+ "ct_access_level = '"
					+ ct_access_level
					+ "' "
					+ "WHERE ct_access_level_id = '"
					+ ct_access_level_id
					+ "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:updateAccessLevel:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:updateAccessLevel:Exception:"
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
	public void addAccessLevel() throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO ct_access_level ("
					+ "ct_access_level_name, "
					+ "ct_access_level) "
					+ "VALUES("
					+ "'"
					+ ct_access_level_name
					+ "', '"
					+ "'"
					+ ct_access_level
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:addAccessLevel:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:addAccessLevel:Exception:"
					+ e.toString());
		}
	}

	/**
	 * Returns all access level ids and names.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllAccessLevelNamesIds() throws SQLException, Exception {
		try {
			String GetCategory =
				"select ct_access_level_id, ct_access_level_name from ct_access_level";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:getAllAccessLevelNamesIds:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:getAllAccessLevelNamesIds:Exception:"
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
	 * Deletes access level by id.
	 * 
	 * @throws Exception
	 */
	public void deleteAccessLevelById() throws SQLException, Exception {
		try {
			String deleteRecord =
				"delete from ct_access_level where ct_access_level_id = " + ct_access_level_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:deleteAccessLevelById:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:deleteAccessLevelById:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Returns access level name for supplied id.
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getAccessLevelName() throws SQLException, Exception {
		String theName = "";
		try {
			String query =
				"select ct_access_level_name from ct_access_level where ct_access_level_id = '"
					+ ct_access_level_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theName = rs.getString("ct_access_level_name");
			}
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:getAccessLevelName:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:getAccessLevelName:Exception:"
					+ e.toString());
		} finally {
		    if (rs != null) {
		        rs.close();
		        rs = null;
		    }
		}
		return theName;
	}

	/**
	 * Returns record for supplied id.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAccessLevel() throws SQLException, Exception {

		try {
			String query =
				"select * from ct_access_level where ct_access_level_id = '"
					+ ct_access_level_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:getAccessLevel:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.database.CtAccessLevel:getAccessLevel:Exception:"
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
	 * @return Returns the ct_access_level.
	 */
	public int getCt_access_level() {
		return ct_access_level;
	}
	/**
	 * @param ct_access_level The ct_access_level to set.
	 */
	public void setCt_access_level(int ct_access_level) {
		this.ct_access_level = ct_access_level;
	}
	/**
	 * @return Returns the ct_access_level_id.
	 */
	public int getCt_access_level_id() {
		return ct_access_level_id;
	}
	/**
	 * @param ct_access_level_id The ct_access_level_id to set.
	 */
	public void setCt_access_level_id(int ct_access_level_id) {
		this.ct_access_level_id = ct_access_level_id;
	}
	/**
	 * @return Returns the ct_access_level_name.
	 */
	public String getCt_access_level_name() {
		return ct_access_level_name;
	}
	/**
	 * @param ct_access_level_name The ct_access_level_name to set.
	 */
	public void setCt_access_level_name(String ct_access_level_name) {
		this.ct_access_level_name = ct_access_level_name;
	}
	/**
	 * @return Returns the page_access_level.
	 */
	public int getPage_access_level() {
		return page_access_level;
	}
	/**
	 * @param page_access_level The page_access_level to set.
	 */
	public void setPage_access_level(int page_access_level) {
		this.page_access_level = page_access_level;
	}
}