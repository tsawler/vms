package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates ct_menu_types table in db
 * <P>
 * Dec 09, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class CtMenuTypes {

	private int ct_menu_type_id = 0;
	private String ct_menu_type_name = "";
	private static ResultSet rs = null;
	private static XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private static Connection conn;
	private static Statement st = null;
	private PreparedStatement pst = null;

	public CtMenuTypes() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateCtMenuTypes() throws SQLException, Exception {
		try {
			String Update = "UPDATE ct_menu_types SET " + "ct_menu_name = '"
					+ ct_menu_type_name + "' " + "WHERE ct_menu_type_id = '"
					+ ct_menu_type_id + "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CtMenuTypes:updateCtMenuTypes:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CtMenuTypes:updateCtMenuTypes:Exception:"
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
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addCtMenuType() throws SQLException, Exception {
		try {
			String save = "INSERT INTO ct_menu_type (" + "ct_menu_type_name) "
					+ "VALUES(" + "'', " + ct_menu_type_name + "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CtMenuTypes:addCtMenuType:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CtMenuTypes:addCtMenuType:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Gets all menu type names/ids
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public static XDisconnectedRowSet getCtMenuTypes() throws SQLException,
			Exception {
		try {
			String query = "select * from ct_menu_types";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("CtMenuTypes:getCtMenuTypes:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CtMenuTypes:getCtMenuTypes:Exception:"
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
	 * Gets ct menu type by id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getCtMenuTypeName() throws SQLException,
			Exception {
		try {
			String query = "select * from ct_menu_types where ct_menu_type_id = '"
					+ ct_menu_type_id + "'";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("CtMenuTypes:getCtMenuTypeName:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CtMenuTypes:getCtMenuTypeName:Exception:"
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
	public static Connection getConn() {
		return conn;
	}

	/**
	 * @param conn
	 *            The conn to set.
	 */
	public static void setConn(Connection conn) {
		CtMenuTypes.conn = conn;
	}

	/**
	 * @return Returns the ct_menu_type_id.
	 */
	public int getCt_menu_type_id() {
		return ct_menu_type_id;
	}

	/**
	 * @param ct_menu_type_id
	 *            The ct_menu_type_id to set.
	 */
	public void setCt_menu_type_id(int ct_menu_type_id) {
		this.ct_menu_type_id = ct_menu_type_id;
	}

	/**
	 * @return Returns the ct_menu_type_name.
	 */
	public String getCt_menu_type_name() {
		return ct_menu_type_name;
	}

	/**
	 * @param ct_menu_type_name
	 *            The ct_menu_type_name to set.
	 */
	public void setCt_menu_type_name(String ct_menu_type_name) {
		this.ct_menu_type_name = ct_menu_type_name;
	}

}