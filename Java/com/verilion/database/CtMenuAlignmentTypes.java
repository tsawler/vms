//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtMenuAlignmentTypes.java,v $
//Revision 1.3  2004/06/25 17:31:53  tcs
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
 * Manipulates ct_menu_alignment_types table in db.
 * <P>
 * Dec 08, 2003
 * <P>
 * @author tsawler
 *  
 */
public class CtMenuAlignmentTypes {

	private int ct_menu_alignment_type_id = 0;
	private String ct_menu_alignment_type_name = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;

	public CtMenuAlignmentTypes() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateCtMenuAlignmentTypes() throws SQLException, Exception {
		try {
			String Update =
				"UPDATE ct_menu_aligment_types SET "
					+ "ct_menu_alignment_type_name = '"
					+ ct_menu_alignment_type_name
					+ "' "
					+ "WHERE ct_menu_alignment_type_id = '"
					+ ct_menu_alignment_type_id
					+ "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtMenuAlignmentTypes:updateCtMenuAlignmentTypes:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtMenuAlignmentTypes:updateCtMenuAlignmentTypes:Exception:" + e.toString());
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
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addCtMenuAlignmentType() throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO ct_menu_alignment_types ("
					+ "ct_menu_alignment_type_name) "
					+ "VALUES("
					+ "'', "
					+ ct_menu_alignment_type_name
					+ "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError("CtMenuAlignmentTypes:addCtMenuAlignmentType:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtMenuAlignmentTypes:addCtMenuAlignmentType:Exception:" + e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Gets all menu alignment type names/ids
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getCtMenuAlignmentTypes() throws SQLException, Exception {
		try {
			String query =
				"select * from ct_menu_alignment_types order by ct_menu_alignment_type_name";

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
				"CtMenuAlignmentTypes:getCtMenuAlignmentTypes:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtMenuAlignmentTypes:getCtMenuAlignmentTypes:Exception:" + e.toString());
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
	 * Gets ct menu alignment type by id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getCtMenuAlignmentTypeName() throws SQLException, Exception {
		try {
			String query =
				"select * from ct_menu_alignment_types where ct_menu_alignment_type_id = '"
					+ ct_menu_alignment_type_id
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
				"CtMenuAlignmentTypes:getCtMenuAlignmentTypeName:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtMenuAlignmentTypes:getCtMenuAlignmentTypeName:Exception:" + e.toString());
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
	 * @return Returns the ct_menu_alignment_type_id.
	 */
	public int getCt_menu_alignment_type_id() {
		return ct_menu_alignment_type_id;
	}

	/**
	 * @param ct_menu_alignment_type_id The ct_menu_alignment_type_id to set.
	 */
	public void setCt_menu_alignment_type_id(int ct_menu_alignment_type_id) {
		this.ct_menu_alignment_type_id = ct_menu_alignment_type_id;
	}

	/**
	 * @return Returns the ct_menu_alignment_type_name.
	 */
	public String getCt_menu_alignment_type_name() {
		return ct_menu_alignment_type_name;
	}

	/**
	 * @param ct_menu_alignment_type_name The ct_menu_alignment_type_name to set.
	 */
	public void setCt_menu_alignment_type_name(String ct_menu_alignment_type_name) {
		this.ct_menu_alignment_type_name = ct_menu_alignment_type_name;
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