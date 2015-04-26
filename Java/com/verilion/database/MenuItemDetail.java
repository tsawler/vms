package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.verilion.object.Errors;

/**
 * Manipulates menu_item table in db, and related operations.
 * <P>
 * Dec 09, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class MenuItemDetail {

	private int menu_item_detail_id = 0;
	private int menu_item_id = 0;
	private String menu_item_detail_name = "";
	private String menu_item_detail_tooltip = "";
	private int ct_language_id = 0;
	private Connection conn;
	private PreparedStatement pst = null;

	public MenuItemDetail() {
		super();
	}

	/**
	 * Update menu_item_detail method.
	 * 
	 * @throws Exception
	 */
	public void updateMenuItemDetail() throws SQLException, Exception {
		try {
			String update = "UPDATE menu_item_detail SET " + "menu_item_id = '"
					+ menu_item_id + "', " + "ct_language_id = '"
					+ ct_language_id + "', " + "menu_item_detail_tooltip = '"
					+ menu_item_detail_tooltip.replaceAll("'", "''") + "', "
					+ "menu_item_detail_name = '"
					+ menu_item_detail_name.replaceAll("'", "''") + "' "
					+ "WHERE menu_item_id = '" + menu_item_id + "' "
					+ "and ct_language_id = '" + ct_language_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItemDetail:updateMenuItemDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItemDetail:updateMenuItemDetail:Exception:"
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
	public void addMenuItemDetail() throws SQLException, Exception {
		try {
			String save = "INSERT INTO menu_item_detail (" + "menu_item_id, "
					+ "ct_language_id, " + "menu_item_detail_name) "
					+ "VALUES(" + "'" + menu_item_id + "', " + "'"
					+ ct_language_id + "', " + "'"
					+ menu_item_detail_name.replaceAll("'", "''") + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItemDetail:addMenuItemDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItemDetail:addMenuItemDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Delete method.
	 * 
	 * @throws Exception
	 */
	public void deleteMenuItemDetailById() throws SQLException, Exception {
		try {
			String delete = "delete from menu_item_detail where menu_item_detail_id = '"
					+ menu_item_detail_id + "'";

			pst = conn.prepareStatement(delete);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItemDetail:deleteMenuItemDetailById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItemDetail:deleteMenuItemDetailById:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Delete children method.
	 * 
	 * @throws Exception
	 */
	public void deleteChildrenOfMenuId() throws SQLException, Exception {
		try {
			String delete = "delete from menu_item_detail where menu_item_id = '"
					+ menu_item_id + "'";

			pst = conn.prepareStatement(delete);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItemDetail:deleteChildrenOfMenuId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItemDetail:deleteChildrenOfMenuId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Delete children method.
	 * 
	 * @throws Exception
	 */
	public void deleteMenuItemDetailByMenuItemIdAndCtLanguageId()
			throws SQLException, Exception {
		try {
			String delete = "delete from menu_item_detail where "
					+ "menu_item_id = '" + menu_item_id + "' "
					+ "and ct_language_id = '" + ct_language_id + "'";

			pst = conn.prepareStatement(delete);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("MenuItemDetail:deleteMenuItemDetailByMenuItemIdAndCtLanguageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("MenuItemDetail:deleteMenuItemDetailByMenuItemIdAndCtLanguageId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
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
	 * @return Returns the ct_language_id.
	 */
	public int getCt_language_id() {
		return ct_language_id;
	}

	/**
	 * @param ct_language_id
	 *            The ct_language_id to set.
	 */
	public void setCt_language_id(int ct_language_id) {
		this.ct_language_id = ct_language_id;
	}

	/**
	 * @return Returns the menu_item_detail_id.
	 */
	public int getMenu_item_detail_id() {
		return menu_item_detail_id;
	}

	/**
	 * @param menu_item_detail_id
	 *            The menu_item_detail_id to set.
	 */
	public void setMenu_item_detail_id(int menu_item_detail_id) {
		this.menu_item_detail_id = menu_item_detail_id;
	}

	/**
	 * @return Returns the menu_item_detail_name.
	 */
	public String getMenu_item_detail_name() {
		return menu_item_detail_name;
	}

	/**
	 * @param menu_item_detail_name
	 *            The menu_item_detail_name to set.
	 */
	public void setMenu_item_detail_name(String menu_item_detail_name) {
		this.menu_item_detail_name = menu_item_detail_name;
	}

	/**
	 * @return Returns the menu_item_id.
	 */
	public int getMenu_item_id() {
		return menu_item_id;
	}

	/**
	 * @param menu_item_id
	 *            The menu_item_id to set.
	 */
	public void setMenu_item_id(int menu_item_id) {
		this.menu_item_id = menu_item_id;
	}

	/**
	 * @return Returns the menu_item_detail_tooltip.
	 */
	public String getMenu_item_detail_tooltip() {
		return menu_item_detail_tooltip;
	}

	/**
	 * @param menu_item_detail_tooltip
	 *            The menu_item_detail_tooltip to set.
	 */
	public void setMenu_item_detail_tooltip(String menu_item_detail_tooltip) {
		this.menu_item_detail_tooltip = menu_item_detail_tooltip;
	}
}