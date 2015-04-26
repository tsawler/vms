package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates menu_item table in db, and related operations.
 * <P>
 * Dec 07, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class MenuItem implements DatabaseInterface {

	private int menu_id = 0;
	private int menu_item_id = 0;
	private String menu_item_action = "";
	private String menu_item_description = "";
	private String menu_item_active_yn = "";
	private int menu_item_order = 0;
	private int ct_access_level_id = 0;
	private int ct_language_id = 0;
	private int page_id = 0;
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;
	private String sCustomWhere = "";
	private String menu_item_is_heading = "n";
	private String menu_item_is_spacer = "n";
	private int component_id = 0;

	public MenuItem() {
		super();
	}

	/**
	 * Update menu_item method.
	 * 
	 * @throws Exception
	 */
	public void updateMenuItem() throws SQLException, Exception {
		try {
			String update = "";
			if ((page_id > 0) && (component_id == 0)) {
				update = "UPDATE menu_item SET " + "menu_id = '" + menu_id
						+ "', " + "menu_item_action = '" + menu_item_action
						+ "', " + "menu_item_description = '"
						+ menu_item_description + "', " + "menu_item_order = '"
						+ menu_item_order + "', " + "menu_item_active_yn = '"
						+ menu_item_active_yn + "', "
						+ "ct_access_level_id = '" + ct_access_level_id + "', "
						+ "component_id = null, " + "page_id = '" + page_id
						+ "' " + "WHERE menu_id = '" + menu_id + "' "
						+ "and menu_item_id = '" + menu_item_id + "'";
			} else if ((page_id == 0) && (component_id > 0)) {
				update = "UPDATE menu_item SET " + "menu_id = '" + menu_id
						+ "', " + "menu_item_action = '" + menu_item_action
						+ "', " + "menu_item_description = '"
						+ menu_item_description + "', " + "menu_item_order = '"
						+ menu_item_order + "', " + "ct_access_level_id = '"
						+ ct_access_level_id + "', "
						+ "menu_item_active_yn = '" + menu_item_active_yn
						+ "', " + "component_id = '" + component_id + "', "
						+ "page_id = null " + "WHERE menu_id = '" + menu_id
						+ "' " + "and menu_item_id = '" + menu_item_id + "'";
			} else {
				update = "UPDATE menu_item SET " + "menu_id = '" + menu_id
						+ "', " + "menu_item_action = '" + menu_item_action
						+ "', " + "menu_item_description = '"
						+ menu_item_description + "', " + "menu_item_order = '"
						+ menu_item_order + "', " + "ct_access_level_id = '"
						+ ct_access_level_id + "', "
						+ "menu_item_active_yn = '" + menu_item_active_yn
						+ "', " + "component_id = null," + "page_id = null "
						+ "WHERE menu_id = '" + menu_id + "' "
						+ "and menu_item_id = '" + menu_item_id + "'";
			}

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItem:updateMenuItem:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItem:updateMenuItem:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void updateMenuItemIgnoreOrder() throws SQLException, Exception {
		try {
			String update = "";

			if ((page_id > 0) && (component_id == 0)) {
				update = "UPDATE menu_item SET " + "menu_item_action = '"
						+ menu_item_action + "', " + "menu_item_active_yn = '"
						+ menu_item_active_yn + "', "
						+ "ct_access_level_id = '" + ct_access_level_id + "', "
						+ "page_id = '" + page_id + "' ,"
						+ "component_id = null, " + "menu_item_is_heading = '"
						+ menu_item_is_heading + "' "
						+ "WHERE menu_item_id = '" + menu_item_id + "'";
			} else if ((page_id == 0) && (component_id > 0)) {
				update = "UPDATE menu_item SET " + "menu_item_action = '"
						+ menu_item_action + "', " + "menu_item_active_yn = '"
						+ menu_item_active_yn + "', "
						+ "ct_access_level_id = '" + ct_access_level_id + "', "
						+ "page_id = null, " + "component_id = '"
						+ component_id + "', " + "menu_item_is_heading = '"
						+ menu_item_is_heading + "' "
						+ "WHERE menu_item_id = '" + menu_item_id + "'";
			} else {
				update = "UPDATE menu_item SET " + "menu_item_action = '"
						+ menu_item_action + "', " + "menu_item_active_yn = '"
						+ menu_item_active_yn + "', "
						+ "ct_access_level_id = '" + ct_access_level_id + "', "
						+ "page_id = null, " + "component_id = null, "
						+ "menu_item_is_heading = '" + menu_item_is_heading
						+ "' " + "WHERE menu_item_id = '" + menu_item_id + "'";
			}
			// System.out.println(update);
			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("com.verilion.database.MenuItem:updateMenuItemIgnorOrder:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.database.MenuItem:updateMenuItemIgnoreOrder:Exception:"
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
	public int addMenuItem() throws SQLException, Exception {

		int theId = 0;
		try {
			int order = 0;
			String query = "select max(menu_item_order) + 1 as menu_order from menu_item";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			if (rs.next()) {
				order = rs.getInt(1);
			}
			rs.close();
			rs = null;
			String save = "";
			if ((page_id > 0) && (component_id == 0)) {
				save = "INSERT INTO menu_item (" + "menu_id, "
						+ "menu_item_action, " + "menu_item_description, "
						+ "menu_item_order, " + "menu_item_active_yn, "
						+ "page_id, " + "menu_item_is_spacer,"
						+ "menu_item_is_heading) " + "VALUES(" + "'"
						+ menu_id
						+ "', "
						+ "'"
						+ menu_item_action
						+ "', "
						+ "'"
						+ menu_item_description
						+ "', "
						+ "'"
						+ order
						+ "', "
						+ "'"
						+ menu_item_active_yn
						+ "', "
						+ "'"
						+ page_id
						+ "', "
						+ "'"
						+ menu_item_is_heading
						+ "', "
						+ "'" + menu_item_is_spacer + "', null)";
			} else if ((page_id == 0) && (component_id > 0)) {
				save = "INSERT INTO menu_item (" + "menu_id, "
						+ "menu_item_action, " + "menu_item_description, "
						+ "menu_item_order, " + "menu_item_active_yn, "
						+ "page_id, " + "menu_item_is_heading,"
						+ "menu_item_is_spacer) " + "VALUES(" + "'"
						+ menu_id
						+ "', "
						+ "'"
						+ menu_item_action
						+ "', "
						+ "'"
						+ menu_item_description
						+ "', "
						+ "'"
						+ order
						+ "', "
						+ "'"
						+ menu_item_active_yn
						+ "', "
						+ "null, "
						+ "'"
						+ menu_item_is_heading
						+ "', "
						+ "'"
						+ menu_item_is_spacer
						+ "', "
						+ "'"
						+ component_id
						+ "')";
			} else {
				save = "INSERT INTO menu_item (" + "menu_id, "
						+ "menu_item_action, " + "menu_item_description, "
						+ "menu_item_order, " + "menu_item_active_yn, "
						+ "page_id, " + "menu_item_is_heading,"
						+ "menu_item_is_spacer, " + "component_id) "
						+ "VALUES(" + "'"
						+ menu_id
						+ "', "
						+ "'"
						+ menu_item_action
						+ "', "
						+ "'"
						+ menu_item_description
						+ "', "
						+ "'"
						+ order
						+ "', "
						+ "'"
						+ menu_item_active_yn
						+ "', "
						+ "null, "
						+ "'"
						+ menu_item_is_heading
						+ "', "
						+ "'"
						+ menu_item_is_spacer + "'," + "null" + ")";
			}
			// System.out.println("query is " + save);
			pst = conn.prepareStatement(save);
			pst.executeUpdate();

			String getLast = "select currval('menu_item_menu_item_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);
			if (rs.next()) {
				theId = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItem:addMenuItem:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItem:addMenuItem:Exception:"
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
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return theId;
	}

	/**
	 * Returns all menu item actions and names for a particular menu.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveMenuItemsActions()
			throws SQLException, Exception {
		try {
			String getMenuItems = "select " + "mi.menu_item_action, "
					+ "mi.page_id, " + "mid.menu_item_detail_name " + "from "
					+ "menu_item as mi, " + "menu_item_detail as mid "
					+ "where " + "mid.menu_item_id = mi.menu_item_id " + "and "
					+ "mid.ct_language_id = '" + ct_language_id + "' "
					+ "and mi.menu_item_active_yn = 'y' "
					+ "order by mi.menu_item_order";
			st = conn.createStatement();
			rs = st.executeQuery(getMenuItems);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItem:getAllActiveMenuItemsActions:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItem:getAllActiveMenuItemsActions:Exception:"
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
	 * Returns all menu_items, active or not, for a particular menu
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllMenuItems() throws SQLException, Exception {
		try {
			String getMenuItems = "select "
					+ "mi.menu_item_id, "
					+ "mi.menu_id, "
					+ "mi.menu_item_is_spacer, "
					+ "mi.menu_item_is_heading, "
					+ "mi.menu_item_action, "
					+ "mi.menu_item_order, "
					+ "case when mi.menu_item_active_yn = 'y' then '<span style=\"color: green;\">active</span>' "
					+ "else '<span style=\"color: red;\">inactive</span>' end as status, "
					+ "mi.page_id, "
					+ "mid.menu_item_detail_name, "
					+ "'<a href=\"/menu/jpage/1/p/MenuChoose/admin/1/content.do?action=6&rid=' || mi.menu_item_id  || '&mid=' || mi.menu_id || '\">up</a>"
					+ "&nbsp;&nbsp;<a href=\"/menu/jpage/1/p/MenuChoose/admin/1/content.do?action=7&rid=' || mi.menu_item_id  || '&mid=' || mi.menu_id || '\">"
					+ "down</a>' as order_items, " + "mid.menu_item_detail_id "
					+ "from " + "menu_item as mi, "
					+ "menu_item_detail as mid " + "where "
					+ "mid.menu_item_id = mi.menu_item_id " + "and "
					+ "mi.menu_id = '" + menu_id + "' " + "and "
					+ "mid.ct_language_id = '" + ct_language_id + "' "
					+ "order by mi.menu_item_order";
			st = conn.createStatement();
			rs = st.executeQuery(getMenuItems);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItem:getAllMenuItems:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItem:getAllMenuItems:Exception:"
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
	 * Get all menus for display (in admin tool).
	 * 
	 * @return RowSetDynaClass
	 * @throws SQLException
	 * @throws Exception
	 */
	public RowSetDynaClass getAllMenuItemsDynaBean() throws SQLException,
			Exception {

		RowSetDynaClass resultset = null;

		try {
			String getMenuItems = "select "
					+ "mi.menu_item_id, "
					+ "mi.menu_item_action, "
					+ "mi.menu_item_order, "
					+ "case when mi.menu_item_active_yn = 'y' then '<span style=\"color: green;\">active</span>' "
					+ "else '<span style=\"color: red;\">inactive</span>' end as status, "
					+ "mi.page_id, "
					+ "mid.menu_item_detail_name, "
					+ "'<a href=\"/menu/jpage/1/p/MenuChoose/admin/1/content.do?action=6&rid=' || mi.menu_item_id  || '&mid=' || mi.menu_id || '\">up</a>"
					+ "&nbsp;&nbsp;<a href=\"/menu/jpage/1/p/MenuChoose/admin/1/content.do?action=7&rid=' || mi.menu_item_id  || '&mid=' || mi.menu_id || '\">"
					+ "down</a>' as order_items, " + "mid.menu_item_detail_id "
					+ "from " + "menu_item as mi, "
					+ "menu_item_detail as mid " + "where "
					+ "mid.menu_item_id = mi.menu_item_id " + "and "
					+ "mi.menu_id = '" + menu_id + "' " + "and "
					+ "mid.ct_language_id = '" + ct_language_id + "' "
					+ "order by mi.menu_item_order";
			st = conn.createStatement();
			rs = st.executeQuery(getMenuItems);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItem:getAllMenuItems:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItem:getAllMenuItems:Exception:"
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
		return resultset;
	}

	/**
	 * Gets values for a menu item, for edit
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getMenuItemForEdit() throws SQLException,
			Exception {

		try {
			String getMenuItems = "select "
					+ "mi.menu_item_id, "
					+ "mi.menu_id, "
					+ "mi.component_id, "
					+ "mi.ct_access_level_id, "
					+ "mi.menu_item_action, "
					+ "mi.menu_item_order, "
					+ "mi.menu_item_active_yn, "
					+ "mi.page_id, "
					+ "mi.menu_item_is_spacer, "
					+ "mi.menu_item_is_heading, "
					+ "mid.menu_item_detail_name, "
					+ "mid.menu_item_detail_id, "
					+ "mid.menu_item_detail_tooltip "
					+ "from "
					+ "menu_item as mi "
					+ "left join menu_item_detail mid on (mi.menu_item_id = mid.menu_item_id) "
					+ "where " + "mi.menu_item_id = '" + menu_item_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(getMenuItems);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("com.verilion.database.MenuItem:getMenuItemForEdit:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.database.MenuItem:getMenuItemForEdit:Exception:"
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

	public void UpdateSortOrder(String theNewOrder) throws SQLException,
			Exception {
		StringTokenizer stoken = new StringTokenizer(theNewOrder, ":");
		int i = 1;
		while (stoken.hasMoreTokens()) {
			String theId = stoken.nextToken();
			try {
				String update = "update menu_item set " + "menu_item_order = '"
						+ i + "' where menu_item_id = '" + theId + "'";
				pst = conn.prepareStatement(update);
				pst.executeUpdate();
				pst.close();
				pst = null;

			} catch (SQLException e) {
				Errors.addError("com.verilion.database.MenuItem:UpdateSortOrder:SQLException:"
						+ e.toString());
			} catch (Exception e) {
				Errors.addError("com.verilion.database.MenuItem:UpdateSortOrder:Exception:"
						+ e.toString());
			} finally {
				if (pst != null) {
					pst.close();
					pst = null;
				}
			}
			i++;
		}
	}

	public void ChangeItemOrderUp(int theIdToChange, int theMenuId)
			throws SQLException, Exception {

		int newOrder = 0;
		int swapId = 0;
		try {
			String select = "select menu_item_order from menu_item where menu_item_id = '"
					+ theIdToChange + "'";

			pst = conn.prepareStatement(select);
			rs = pst.executeQuery();
			if (rs.next()) {
				newOrder = rs.getInt(1);
			}
			rs.close();
			rs = null;
			pst.close();
			pst = null;

			if (newOrder > 0) {
				String update = "UPDATE menu_item SET " + "menu_item_order = '"
						+ (newOrder - 1) + "' WHERE menu_item_id = '"
						+ theIdToChange + "'";

				pst = conn.prepareStatement(update);
				pst.executeUpdate();
				pst.close();
				pst = null;

				select = "select menu_item_id from menu_item where menu_item_order = '"
						+ (newOrder - 1)
						+ "' and menu_id = '"
						+ theMenuId
						+ "' and menu_item_id <> '" + theIdToChange + "'";

				pst = conn.prepareStatement(select);
				rs = pst.executeQuery();
				if (rs.next()) {
					swapId = rs.getInt(1);
				}
				rs.close();
				rs = null;
				pst.close();
				pst = null;

				update = "update menu_item set " + "menu_item_order = '"
						+ newOrder + "' where menu_item_id = '" + swapId + "'";
				pst = conn.prepareStatement(update);
				pst.executeUpdate();
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItem:ChangeItemOrderUp:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItem:ChangeItemOrderUp:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void ChangeItemOrderDown(int theIdToChange, int theMenuId)
			throws SQLException, Exception {

		int newOrder = 0;
		int swapId = 0;

		try {
			String select = "select menu_item_order from menu_item where menu_item_id = '"
					+ theIdToChange + "'";

			pst = conn.prepareStatement(select);
			rs = pst.executeQuery();
			if (rs.next()) {
				newOrder = rs.getInt(1);
			}
			rs.close();
			rs = null;
			pst.close();
			pst = null;

			if (newOrder > 0) {
				String update = "UPDATE menu_item SET " + "menu_item_order = '"
						+ (newOrder + 1) + "' WHERE menu_item_id = '"
						+ theIdToChange + "'";

				pst = conn.prepareStatement(update);
				pst.executeUpdate();
				pst.close();
				pst = null;

				select = "select menu_item_id from menu_item where menu_item_order = '"
						+ (newOrder + 1)
						+ "' and menu_id = '"
						+ theMenuId
						+ "' and menu_item_id <> '" + theIdToChange + "'";

				pst = conn.prepareStatement(select);
				rs = pst.executeQuery();
				if (rs.next()) {
					swapId = rs.getInt(1);
				}

				rs.close();
				rs = null;
				pst.close();
				pst = null;

				update = "update menu_item set " + "menu_item_order = '"
						+ newOrder + "' where menu_item_id = '" + swapId + "'";
				pst = conn.prepareStatement(update);
				pst.executeUpdate();
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MenuItem:ChangeItemOrderDown:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MenuItem:ChangeItemOrderDown:Exception:"
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
	 * @return Returns the menu_id.
	 */
	public int getMenu_id() {
		return menu_id;
	}

	/**
	 * @param menu_id
	 *            The menu_id to set.
	 */
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	/**
	 * @return Returns the menu_item_action.
	 */
	public String getMenu_item_action() {
		return menu_item_action;
	}

	/**
	 * @param menu_item_action
	 *            The menu_item_action to set.
	 */
	public void setMenu_item_action(String menu_item_action) {
		this.menu_item_action = menu_item_action;
	}

	/**
	 * @return Returns the menu_item_active_yn.
	 */
	public String getMenu_item_active_yn() {
		return menu_item_active_yn;
	}

	/**
	 * @param menu_item_active_yn
	 *            The menu_item_active_yn to set.
	 */
	public void setMenu_item_active_yn(String menu_item_active_yn) {
		this.menu_item_active_yn = menu_item_active_yn;
	}

	/**
	 * @return Returns the menu_item_description.
	 */
	public String getMenu_item_description() {
		return menu_item_description;
	}

	/**
	 * @param menu_item_description
	 *            The menu_item_description to set.
	 */
	public void setMenu_item_description(String menu_item_description) {
		this.menu_item_description = menu_item_description;
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
	 * @return Returns the menu_item_order.
	 */
	public int getMenu_item_order() {
		return menu_item_order;
	}

	/**
	 * @param menu_item_order
	 *            The menu_item_order to set.
	 */
	public void setMenu_item_order(int menu_item_order) {
		this.menu_item_order = menu_item_order;
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#setOffset(int)
	 */
	public void setOffset(int pOffset) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
	 */
	public void setPrimaryKey(String theKey) {
		this.setMenu_item_id(Integer.parseInt(theKey));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		String update = "delete from menu_item where menu_item_id = '"
				+ menu_item_id + "'";
		pst = conn.prepareStatement(update);
		pst.executeUpdate();
		pst.close();
		pst = null;

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

		String update = "update menu_item set menu_item_active_yn = '"
				+ menu_item_active_yn + "' where menu_item_id = '"
				+ menu_item_id + "'";

		pst = conn.prepareStatement(update);
		pst.executeUpdate();
		pst.close();
		pst = null;

	}

	/**
	 * @return Returns the sCustomWhere.
	 */
	public String getSCustomWhere() {
		return sCustomWhere;
	}

	/**
	 * @param customWhere
	 *            The sCustomWhere to set.
	 */
	public void setSCustomWhere(String customWhere) {
		sCustomWhere = customWhere;
	}

	public int getCt_access_level_id() {
		return ct_access_level_id;
	}

	public void setCt_access_level_id(int ct_access_level_id) {
		this.ct_access_level_id = ct_access_level_id;
	}

	public String getMenu_item_is_spacer() {
		return menu_item_is_spacer;
	}

	public void setMenu_item_is_spacer(String menu_item_is_spacer) {
		this.menu_item_is_spacer = menu_item_is_spacer;
	}

	public String getMenu_item_is_heading() {
		return menu_item_is_heading;
	}

	public void setMenu_item_is_heading(String menu_item_is_heading) {
		this.menu_item_is_heading = menu_item_is_heading;
	}

	public int getComponent_id() {
		return component_id;
	}

	public void setComponent_id(int component_id) {
		this.component_id = component_id;
	}
}