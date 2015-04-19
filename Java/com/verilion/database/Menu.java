//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: Menu.java,v $
//Revision 1.5.6.2.6.1.2.4.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.5.6.2.6.1.2.4  2007/02/25 01:53:52  tcs
//*** empty log message ***
//
//Revision 1.5.6.2.6.1.2.3  2007/02/02 19:18:10  tcs
//Changed to support components linked in menu
//
//Revision 1.5.6.2.6.1.2.2  2007/01/29 16:15:59  tcs
//Added support for collapsible vertical menus, spacers & headings
//
//Revision 1.5.6.2.6.1.2.1  2007/01/28 00:51:25  tcs
//Added support for per menu item access level restrictions
//
//Revision 1.5.6.2.6.1  2006/04/07 18:19:26  tcs
//*** empty log message ***
//
//Revision 1.5.6.2  2005/08/16 00:23:30  tcs
//Modified to implement databaseinterface
//
//Revision 1.5.6.1  2005/08/16 00:10:00  tcs
//Corrected sql in add method
//
//Revision 1.5  2004/06/25 18:35:10  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.4  2004/06/25 16:31:37  tcs
//Changed some methods to return XDisconnectedRowSets
//
//Revision 1.3  2004/06/24 17:58:08  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.2  2004/05/31 12:58:11  tcs
//Added additional XHTML stuff
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
 * Manipulates menu table in db, and permits retrieval of menus for display (so
 * it touches menu_item and menu item detail as well)
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class Menu implements DatabaseInterface {

	private int menu_id = 0;
	private String menu_name = "";
	private String menu_divider = "";
	private int ct_menu_type_id = 0;
	private int ct_menu_alignment_type_id = 0;
	private int ct_language_id = 0;
	private String menu_active_yn = "";
	private String menu_tag = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;
	private String sCustomWhere = "";

	public Menu() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateMenu() throws SQLException, Exception {
		try {
			String update = "UPDATE menu SET " + "menu_name = '" + menu_name
					+ "', " + "menu_divider = '" + menu_divider + "', "
					+ "ct_menu_type_id = '" + ct_menu_type_id + "', "
					+ "ct_menu_alignment_type_id = '"
					+ ct_menu_alignment_type_id + "', " + "menu_active_yn = '"
					+ menu_active_yn + "', " + "menu_tag = '" + menu_tag + "' "
					+ "WHERE menu_id = '" + menu_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:updateMenu:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.Menu:updateMenu:Exception:"
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
	public int addMenu() throws SQLException, Exception {
		int theId = 0;
		try {
			String save = "INSERT INTO menu (" + "menu_name, "
					+ "menu_divider, " + "ct_menu_type_id, "
					+ "ct_menu_alignment_type_id, " + "menu_active_yn, "
					+ "style_id, " + "menu_tag) " + "VALUES(" + "'" + menu_name
					+ "', " + "'" + menu_divider + "', " + "'"
					+ ct_menu_type_id + "', " + "'" + ct_menu_alignment_type_id
					+ "', " + "'" + menu_active_yn + "', " + "'0', " + "'"
					+ menu_tag + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;

			String getLast = "select currval('menu_menu_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);
			if (rs.next()) {
				theId = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.Menu:addMenu:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.Menu:addMenu:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return theId;
	}

	/**
	 * Returns menu id for supplied menu name
	 * 
	 * @return int
	 * @throws Exception
	 */
	public int getMenuIdByMenuName() throws SQLException, Exception {
		int theId = 0;
		try {
			String getId = "select menu_id from menu " + "where menu_name = '"
					+ menu_name + "'";
			st = conn.createStatement();
			rs = st.executeQuery(getId);
			if (rs.next()) {
				theId = rs.getInt("menu_id");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuIdByMenuName:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuIdByMenuName:Exception:"
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
		return theId;
	}

	/**
	 * Returns all menu ids and names.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllMenuNamesIds() throws SQLException,
			Exception {
		try {
			String GetCategory = "select menu_id, menu_name from menu order by menu_name";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:getAllMenuNamesIds:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getAllMenuNamesIds:Exception:"
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
	 * Returns all active menu ids and names.
	 * 
	 * @return XDisconntedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveMenuNamesIds() throws SQLException,
			Exception {
		try {
			String GetCategory = "select menu_id, menu_name from menu where menu_active_yn = 'y' order by menu_name";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:getAllActiveMenuNamesIds:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getAllActiveMenuNamesIds:Exception:"
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
	 * Returns all active menu ids, tags and names.
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveMenuNamesTags() throws SQLException,
			Exception {

		try {
			String GetCategory = "select menu_id, menu_name, menu_tag from menu where menu_active_yn = 'y' order by menu_name";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:getAllActiveMenuNamesTags:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getAllActiveMenuNamesTags:Exception:"
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
	 * Deletes menu by menu id
	 * 
	 * @throws Exception
	 */
	public void deleteMenuById() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from menu where menu_id = '"
					+ menu_id + "'";
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:deleteMenuById:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:deleteMenuById:Exception:"
							+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns menu name for supplied id.
	 * 
	 * @return String - the menu name
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getMenuName() throws SQLException, Exception {
		String menuName = "";
		try {
			String query = "select menu_name from menu where menu_id = '"
					+ menu_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				menuName = rs.getString("menu_name");
			}
			rs.close();
			st.close();
			rs = null;
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuName:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.Menu:getMenuName:Exception:"
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
		return menuName;
	}

	/**
	 * Returns record for supplied id.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getMenu() throws SQLException, Exception {

		try {
			String query = "select * from menu where menu_id = '" + menu_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.Menu:getMenu:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.Menu:getMenu:Exception:"
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
	 * Gets menu for display
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getMenuForDisplay() throws SQLException,
			Exception {

		try {
			String query = "select "
					+ "mid.menu_item_detail_name, "
					+ "mid.menu_item_detail_tooltip, "
					+ "mi.menu_item_action, "
					+ "mi.menu_item_is_heading, "
					+ "mi.menu_item_is_spacer, "
					+ "mi.ct_access_level_id, "
					+ "case when mi.page_id is not null then mi.page_id "
					+ "else 0 end as page_id, "
					+ "case when mi.component_id > 0 then mi.component_id "
					+ "else 0 end as component_id, "
					+ "case when mi.component_id > 0 then "
					+ "(select component_link from components where component_id = mi.component_id) "
					+ "else '' end as component_url, "
					+ "case when mi.component_id > 0 then "
					+ "(select component_link_extra from components where component_id = mi.component_id) "
					+ "else '' end as component_url_extra, "
					+ "case when mi.component_id > 0 then "
					+ "(select component_name from components where component_id = mi.component_id) "
					+ "else '' end as component_name "
					+ "from menu_item as mi, " + "menu_item_detail as mid "
					+ "where " + "mi.menu_id = '" + menu_id + "' "
					+ "and mid.menu_item_id = mi.menu_item_id "
					+ "and mid.ct_language_id = '" + ct_language_id + "' "
					+ "and mi.menu_item_active_yn = 'y' "
					+ "order by menu_item_order";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuForDisplay:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuForDisplay:Exception:"
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
	 * Gets a divider for a menu.
	 * 
	 * @return String the divider for a menu
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getDivider() throws SQLException, Exception {
		ResultSet rs = null;
		String divider = "";
		try {
			String query = "select menu_divider from menu where menu_id = '"
					+ menu_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				divider = rs.getString("menu_divider");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:getDivider:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.Menu:getDivider:Exception:"
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
		return divider;
	}

	/**
	 * Returns alignment of menu
	 * 
	 * @return String - the alignment for a menu
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getMenuAlignment(String menu_name) throws SQLException,
			Exception {
		ResultSet rs = null;
		int ct_menu_alignment_type_id = 0;
		try {
			String query = "select ct_menu_alignment_type_id from menu where menu_name = '"
					+ menu_name + "'";
			Statement st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				ct_menu_alignment_type_id = rs
						.getInt("ct_menu_alignment_type_id");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.getMenuAlignment:getMenu:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuAlignment:Exception:"
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
		return ct_menu_alignment_type_id;
	}

	public int getMenuAlignment(int mid) throws SQLException, Exception {
		ResultSet rs = null;
		int ct_menu_alignment_type_id = 0;
		try {
			String query = "select ct_menu_alignment_type_id from menu where menu_id = '"
					+ mid + "'";
			Statement st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				ct_menu_alignment_type_id = rs
						.getInt("ct_menu_alignment_type_id");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.getMenuAlignment:getMenu:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuAlignment:Exception:"
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
		return ct_menu_alignment_type_id;
	}

	/**
	 * Returns true if menu is horizontal, false if vertical.
	 * 
	 * @return boolean
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean getMenuHorizontalOrVertical(String menu_name)
			throws SQLException, Exception {
		ResultSet rs = null;
		boolean isHorizontal = false;
		try {
			String query = "select ct_menu_type_id from menu where menu_name = '"
					+ menu_name + "'";
			Statement st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("ct_menu_type_id") == 1) {
					isHorizontal = true;
				} else {
					isHorizontal = false;
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.getMenuHorizontalOrVertical:getMenu:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuHorizontalOrVertical:Exception:"
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
		return isHorizontal;
	}

	public boolean getMenuHorizontalOrVertical(int menu_id)
			throws SQLException, Exception {
		ResultSet rs = null;
		boolean isHorizontal = false;
		try {
			String query = "select ct_menu_type_id from menu where menu_id = '"
					+ menu_id + "'";
			Statement st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("ct_menu_type_id") == 1) {
					isHorizontal = true;
				} else {
					isHorizontal = false;
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.getMenuHorizontalOrVertical:getMenu:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getMenuHorizontalOrVertical:Exception:"
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
		return isHorizontal;
	}

	public int getTypeOfMenu(String menu_name) throws SQLException, Exception {
		ResultSet rs = null;
		int theIdType = 0;
		try {
			String query = "select ct_menu_type_id from menu where menu_name = '"
					+ menu_name + "'";
			Statement st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theIdType = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.getTypeOfMenu:getMenu:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getTypeOfMenu:Exception:"
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
		return theIdType;
	}
	
	public int getTypeOfMenu(int mid) throws SQLException, Exception {
		ResultSet rs = null;
		int theIdType = 0;
		try {
			String query = "select ct_menu_type_id from menu where menu_id = '"
					+ mid + "'";
			Statement st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theIdType = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.getTypeOfMenu:getMenu:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Menu:getTypeOfMenu:Exception:"
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
		return theIdType;
	}

	/**
	 * Gets a menu's style id.
	 * 
	 * @return int - the menu's style id
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getStyleId() throws SQLException, Exception {
		ResultSet rs = null;
		int theStyleId = 0;
		try {
			String query = "select style_id from menu where menu_id = '"
					+ menu_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theStyleId = rs.getInt("style_id");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Menu:getStyleId:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.Menu:getStyleId:Exception:"
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
		return theStyleId;
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
		this.setMenu_id(Integer.parseInt(theKey));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		String update = "delete from menu where menu_id = '" + menu_id + "'";
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

		String update = "update menu set menu_active_yn = '" + menu_active_yn
				+ "' where menu_id = '" + menu_id + "'";

		pst = conn.prepareStatement(update);
		pst.executeUpdate();
		pst.close();
		pst = null;

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
	 * @return Returns the ct_menu_alignment_type_id.
	 */
	public int getCt_menu_alignment_type_id() {
		return ct_menu_alignment_type_id;
	}

	/**
	 * @param ct_menu_alignment_type_id
	 *            The ct_menu_alignment_type_id to set.
	 */
	public void setCt_menu_alignment_type_id(int ct_menu_alignment_type_id) {
		this.ct_menu_alignment_type_id = ct_menu_alignment_type_id;
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
	 * @return Returns the menu_active_yn.
	 */
	public String getMenu_active_yn() {
		return menu_active_yn;
	}

	/**
	 * @param menu_active_yn
	 *            The menu_active_yn to set.
	 */
	public void setMenu_active_yn(String menu_active_yn) {
		this.menu_active_yn = menu_active_yn;
	}

	/**
	 * @return Returns the menu_divider.
	 */
	public String getMenu_divider() {
		return menu_divider;
	}

	/**
	 * @param menu_divider
	 *            The menu_divider to set.
	 */
	public void setMenu_divider(String menu_divider) {
		this.menu_divider = menu_divider;
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
	 * @return Returns the menu_name.
	 */
	public String getMenu_name() {
		return menu_name;
	}

	/**
	 * @param menu_name
	 *            The menu_name to set.
	 */
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	/**
	 * @return Returns the menu_tag.
	 */
	public String getMenu_tag() {
		return menu_tag;
	}

	/**
	 * @param menu_tag
	 *            The menu_tag to set.
	 */
	public void setMenu_tag(String menu_tag) {
		this.menu_tag = menu_tag;
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
}