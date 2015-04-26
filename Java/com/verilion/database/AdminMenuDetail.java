package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.verilion.object.Errors;

/**
 * Manipulates admin_menu_item_detail table in db, and related operations.
 * <p>
 * November 3, 2004
 * <p>
 * 
 * @author tsawler
 * 
 */
public class AdminMenuDetail implements DatabaseInterface {

	private String admin_menu_items_id = "";
	private String admin_menu_items_active_yn = "";
	private int page_id = 0;

	private String admin_menu_items_detail_target = "";
	private String admin_menu_item_detail_name = "";
	private String admin_menu_item_detail_uri = "";
	private int admin_menu_item_detail_order = 0;
	private String admin_menu_item_detail_id = "";
	private String admin_menu_item_detail_active_yn = "";

	private Connection conn;
	private PreparedStatement pst = null;

	public String sCustomWhere = "";
	public int iLimit = 0;
	public int iOffset = 0;

	public AdminMenuDetail() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {

		try {
			String sSQL = "delete from admin_menu_item_detail "
					+ "where admin_menu_item_detail_id = '"
					+ admin_menu_item_detail_id + "'";

			pst = conn.prepareStatement(sSQL);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenuDetail:deleteRecord:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenuDetail:deleteRecord:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Add detail menu item
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addAdminMenuItemDetail() throws SQLException, Exception {

		try {
			String sSQL = "insert into admin_menu_item_detail ("
					+ "admin_menu_item_detail_target, "
					+ "admin_menu_item_detail_name, "
					+ "admin_menu_item_detail_uri, "
					+ "admin_menu_item_detail_order, "
					+ "admin_menu_items_id) " + "values (" + "'"
					+ admin_menu_items_detail_target + "', " + "'"
					+ admin_menu_item_detail_name + "', ";

			if (page_id > 0) {
				sSQL += "(select page_invocation from page where "
						+ "page_id = '" + page_id + "'), ";
			} else {
				sSQL += "'javascript:undefined', ";
			}

			sSQL += "(select (max(admin_menu_item_detail_order) + 1) from admin_menu_item_detail), "
					+ "'" + admin_menu_items_id + "')";

			pst = conn.prepareStatement(sSQL);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenu:addAdminMenuItemDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:addAdminMenuItemDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Update detail menu item
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateAdminMenuItemDetail() throws SQLException, Exception {

		try {
			String sSQL = "update admin_menu_item_detail set "
					+ "admin_menu_item_detail_name = " + "'"
					+ admin_menu_item_detail_name + "', "
					+ "admin_menu_item_detail_uri = ";

			if (page_id > 0) {
				sSQL += "(select page_invocation from page where "
						+ "page_id = '" + page_id + "'), ";
			} else {
				sSQL += "'javascript:undefined', ";
			}

			sSQL += "admin_menu_items_id = " + "'" + admin_menu_items_id + "' "
					+ "where admin_menu_item_detail_id = '"
					+ admin_menu_item_detail_id + "'";

			pst = conn.prepareStatement(sSQL);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenu:updateAdminMenuItemDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:updateAdminMenuItemDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Change active status of admin menu detail item
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void changeAdminMenuItemDetailActiveStatus() throws SQLException,
			Exception {

		try {
			String sSQL = "update admin_menu_item_detail set "
					+ "admin_menu_item_detail_active_yn = " + "'"
					+ admin_menu_item_detail_active_yn + "'  "
					+ "where admin_menu_item_detail_id = " + "'"
					+ admin_menu_item_detail_id + "'";

			pst = conn.prepareStatement(sSQL);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenuDetail:changeAdminMenuItemDetailActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenuDetail:changeAdminMenuItemDetailActiveStatus:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verilion.database.DatabaseInterface#createCustomWhere(java.lang.String
	 * )
	 */
	public void createCustomWhere(String psCustomWhere) {
		this.sCustomWhere = psCustomWhere;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#setLimit(int)
	 */
	public void setLimit(int pLimit) {
		this.iLimit = pLimit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#setOffset(int)
	 */
	public void setOffset(int pOffset) {
		this.iOffset = pOffset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.
	 * String)
	 */
	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {
		this.setAdmin_menu_item_detail_active_yn(psStatus);
		this.changeAdminMenuItemDetailActiveStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
	 */
	public void setPrimaryKey(String inId) {
		this.admin_menu_item_detail_id = inId;
	}

	/**
	 * @return Returns the admin_menu_item_detail_active_yn.
	 */
	public String getAdmin_menu_item_detail_active_yn() {
		return admin_menu_item_detail_active_yn;
	}

	/**
	 * @param admin_menu_item_detail_active_yn
	 *            The admin_menu_item_detail_active_yn to set.
	 */
	public void setAdmin_menu_item_detail_active_yn(
			String admin_menu_item_detail_active_yn) {
		this.admin_menu_item_detail_active_yn = admin_menu_item_detail_active_yn;
	}

	/**
	 * @return Returns the admin_menu_item_detail_id.
	 */
	public String getAdmin_menu_item_detail_id() {
		return admin_menu_item_detail_id;
	}

	/**
	 * @param admin_menu_item_detail_id
	 *            The admin_menu_item_detail_id to set.
	 */
	public void setAdmin_menu_item_detail_id(String admin_menu_item_detail_id) {
		this.admin_menu_item_detail_id = admin_menu_item_detail_id;
	}

	/**
	 * @return Returns the admin_menu_item_detail_name.
	 */
	public String getAdmin_menu_item_detail_name() {
		return admin_menu_item_detail_name;
	}

	/**
	 * @param admin_menu_item_detail_name
	 *            The admin_menu_item_detail_name to set.
	 */
	public void setAdmin_menu_item_detail_name(
			String admin_menu_item_detail_name) {
		this.admin_menu_item_detail_name = admin_menu_item_detail_name;
	}

	/**
	 * @return Returns the admin_menu_item_detail_order.
	 */
	public int getAdmin_menu_item_detail_order() {
		return admin_menu_item_detail_order;
	}

	/**
	 * @param admin_menu_item_detail_order
	 *            The admin_menu_item_detail_order to set.
	 */
	public void setAdmin_menu_item_detail_order(int admin_menu_item_detail_order) {
		this.admin_menu_item_detail_order = admin_menu_item_detail_order;
	}

	/**
	 * @return Returns the admin_menu_item_detail_uri.
	 */
	public String getAdmin_menu_item_detail_uri() {
		return admin_menu_item_detail_uri;
	}

	/**
	 * @param admin_menu_item_detail_uri
	 *            The admin_menu_item_detail_uri to set.
	 */
	public void setAdmin_menu_item_detail_uri(String admin_menu_item_detail_uri) {
		this.admin_menu_item_detail_uri = admin_menu_item_detail_uri;
	}

	/**
	 * @return Returns the admin_menu_items_active_yn.
	 */
	public String getAdmin_menu_items_active_yn() {
		return admin_menu_items_active_yn;
	}

	/**
	 * @param admin_menu_items_active_yn
	 *            The admin_menu_items_active_yn to set.
	 */
	public void setAdmin_menu_items_active_yn(String admin_menu_items_active_yn) {
		this.admin_menu_items_active_yn = admin_menu_items_active_yn;
	}

	/**
	 * @return Returns the admin_menu_items_detail_target.
	 */
	public String getAdmin_menu_items_detail_target() {
		return admin_menu_items_detail_target;
	}

	/**
	 * @param admin_menu_items_detail_target
	 *            The admin_menu_items_detail_target to set.
	 */
	public void setAdmin_menu_items_detail_target(
			String admin_menu_items_detail_target) {
		this.admin_menu_items_detail_target = admin_menu_items_detail_target;
	}

	/**
	 * @return Returns the admin_menu_items_id.
	 */
	public String getAdmin_menu_items_id() {
		return admin_menu_items_id;
	}

	/**
	 * @param admin_menu_items_id
	 *            The admin_menu_items_id to set.
	 */
	public void setAdmin_menu_items_id(String admin_menu_items_id) {
		this.admin_menu_items_id = admin_menu_items_id;
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
	 * @return Returns the page_id.
	 */
	public int getPage_id() {
		return page_id;
	}

	/**
	 * @param page_id
	 *            The page_id to set.
	 */
	public void setPage_id(int page_id) {
		this.page_id = page_id;
	}
}