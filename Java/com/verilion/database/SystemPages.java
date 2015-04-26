package com.verilion.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.verilion.object.Errors;

/**
 * Get system pages from db.
 * <P>
 * Nov 25, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class SystemPages {
	String tempSting = "";
	static Statement st = null;
	static ResultSet rs = null;
	private static Connection conn = null;

	/**
	 * Gets a system page by name, for inclusion into an HTML template.
	 * 
	 * @param inPageName
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public static String GetSystemPageByName(String inPageName)
			throws SQLException, Exception {
		String tempString = "";
		try {

			String sqlQuery = "select system_page_contents from "
					+ "system_pages  " + "where system_page_name = '"
					+ inPageName + "'";
			st = conn.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				tempString = rs.getString("system_page_contents");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.SystemPages:GetSystemPageByName:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.SystemPages:GetSystemPageByName:Exception:"
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
		return tempString;
	}

	/**
	 * Gets a system page by name and access level id, for pages which are
	 * different based upon a user's access level (i.e. message displayed
	 * instead of log in boxes)
	 * 
	 * @param inPageName
	 * @param accessLevelId
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public static String GetSystemPageByNameAndAccessLevelId(String inPageName,
			int accessLevelId) throws SQLException, Exception {
		String tempString = "";
		try {
			String sqlQuery = "select system_page_contents from "
					+ "system_pages  " + "where system_page_name = '"
					+ inPageName + "' " + "and ct_access_level_id = '"
					+ accessLevelId + "'";
			st = conn.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				tempString = rs.getString("system_page_contents");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.SystemPages:GetSystemPageByNameAndAccessLevelId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.SystemPages:GetSystemPageByNameAndAccessLevelId:Exception:"
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
		return tempString;
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
		SystemPages.conn = conn;
	}

}