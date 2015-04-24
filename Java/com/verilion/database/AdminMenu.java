//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-01
//Revisions
//------------------------------------------------------------------------------
//$Log: AdminMenu.java,v $
//Revision 1.12.2.1.4.1  2005/08/21 15:37:14  tcs
//Removed unused membres, code cleanup
//
//Revision 1.12.2.1  2004/12/17 18:11:09  tcs
//Modified for changes to DatabaseInterface
//
//Revision 1.12  2004/11/03 13:13:42  tcs
//implemented DatabaseInterface
//
//Revision 1.11  2004/10/06 18:59:11  tcs
//Fixed broken update sql
//
//Revision 1.10  2004/10/06 18:15:16  tcs
//Added method to get admin menu detail item by id
//
//Revision 1.9  2004/10/06 17:54:15  tcs
//Improved updates for menu items & menu item detail
//
//Revision 1.8  2004/10/05 17:48:04  tcs
//Completed methods for adding admin menu items
//
//Revision 1.7  2004/10/05 16:52:48  tcs
//Improved sql for inserts
//
//Revision 1.6  2004/10/05 02:16:18  tcs
//Corrected javadocs
//
//Revision 1.5  2004/10/04 18:15:57  tcs
//Improved dynamic creation of javascript
//
//Revision 1.4  2004/10/04 15:35:50  tcs
//Improved methods generating javascript
//
//Revision 1.3  2004/10/01 18:42:39  tcs
//Complete to version 1
//
//Revision 1.2  2004/10/01 16:39:07  tcs
//Formatting
//
//Revision 1.1  2004/10/01 16:25:42  tcs
//Initial entry
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
 * Manipulates admin menu tables in db, and related operations.
 * <p>
 * October 1, 2004
 * <p>
 * 
 * @author tsawler
 * 
 */
public class AdminMenu implements DatabaseInterface {

	private int admin_menu_id = 0;
	private int use_text_links = 0;
	private int start_all_open = 0;
	private int use_icons = 0;
	private int wrap_text = 0;
	private int preserve_state = 0;
	private int highlight = 0;
	private String highlight_color = "";
	private String highlight_bg = "";
	private String icon_path = "";
	private String folders_tree_id = "";
	private String menu_title = "";
	private String main_uri = "";

	private String admin_menu_items_id = "";
	private String admin_menu_items_name = "";
	private String admin_menu_items_uri = "";
	private int admin_menu_items_order = 0;
	private String admin_menu_items_active_yn = "";
	private int page_id = 0;

	private String admin_menu_items_detail_target = "";
	private String admin_menu_item_detail_name = "";
	private String admin_menu_item_detail_uri = "";
	private int admin_menu_item_detail_order = 0;
	private String admin_menu_item_detail_id = "";
	private String admin_menu_item_detail_active_yn = "";

	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public String sCustomWhere = "";

	public AdminMenu() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateAdminMenu() throws SQLException, Exception {
		try {
			String update = "UPDATE admin_menu SET " + "use_textlinks = '"
					+ use_text_links + "', " + "start_all_open = '"
					+ start_all_open + "', " + "use_icons = '" + use_icons
					+ "', " + "wrap_text = '" + wrap_text + "', "
					+ "preserve_state = '" + preserve_state + "', "
					+ "highlight = '" + highlight + "', "
					+ "highlight_color = '" + highlight_color + "', "
					+ "highlight_bg = '" + highlight_bg + "', "
					+ "icon_path = '" + icon_path + "', " + "menu_title = '"
					+ menu_title + ", " + "main_uri = '" + main_uri + "' "
					+ "WHERE admin_menu_id = '" + admin_menu_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenu:updateAdminMenu:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:updateAdminMenu:Exception:"
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
	public void addAdminMenu() throws SQLException, Exception {
		try {
			String save = "INSERT INTO admin_menu (" + "use_textlinks, "
					+ "start_all_open, " + "use_icons, " + "wrap_text, "
					+ "preserve_state, " + "highlight, " + "highlight_color, "
					+ "highlight_bg, " + "icon_path, " + "folders_tree_id, "
					+ "menu_title, " + "main_uri) " + "VALUES(" + "'"
					+ use_text_links
					+ "', "
					+ "'"
					+ start_all_open
					+ "', "
					+ "'"
					+ use_icons
					+ "', "
					+ "'"
					+ wrap_text
					+ "', "
					+ "'"
					+ preserve_state
					+ "', "
					+ "'"
					+ highlight
					+ "', "
					+ "'"
					+ highlight_color
					+ "', "
					+ "'"
					+ highlight
					+ "', "
					+ "'"
					+ highlight_bg
					+ "', "
					+ "'"
					+ icon_path
					+ "', "
					+ "'"
					+ folders_tree_id + "', " + "'" + main_uri + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenu:addAdminMenu:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:addAdminMenu:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns admin menu
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	private XDisconnectedRowSet getAdminMenu() throws SQLException, Exception {
		try {
			String GetCategory = "select * from admin_menu";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenu:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenu:Exception:" + e.toString());
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
	 * Get all admin menu items
	 * 
	 * @return drs
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAdminMenuItems() throws SQLException,
			Exception {
		try {
			String GetCategory = "select * from admin_menu_items order by admin_menu_items_order";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenuItems:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenuItems:Exception:"
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
	 * Gets active admin menu items
	 * 
	 * @return drs
	 * @throws SQLException
	 * @throws Exception
	 */
	private XDisconnectedRowSet getActiveAdminMenuItems() throws SQLException,
			Exception {
		try {
			String GetCategory = "select * from admin_menu_items "
					+ "where admin_menu_items_active_yn = 'y' "
					+ "order by admin_menu_items_order";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenuItems:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenuItems:Exception:"
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
	 * Get detail items for menu item
	 * 
	 * @param inId
	 * @return drs
	 * @throws SQLException
	 * @throws Exception
	 */
	public ResultSet getAdminMenuItemsDetail(int inId) throws SQLException,
			Exception {
		try {
			String GetCategory = "select * from admin_menu_item_detail "
					+ "where admin_menu_items_id = '" + inId
					+ "' order by admin_menu_item_detail_order";
			System.out.println(GetCategory);
			System.out.println("Conn is " + conn.toString());
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenuItems:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenuItems:Exception:"
					+ e.toString());
		}
		return rs;
	}

	/**
	 * Get admin menu items for viewing on edit screen
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAdminMenuItemsForView() throws SQLException,
			Exception {
		try {
			String GetCategory = "select admin_menu_items_id, "
					+ "admin_menu_items_name, " + "admin_menu_items_active_yn "
					+ "from admin_menu_items "
					+ "order by admin_menu_items_order";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenuItemsForView:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenuItemsForView:Exception:"
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
	 * Return drs of names/ids of admin menu items
	 * 
	 * @return drs
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAdminMenuItemsNamesIds() throws SQLException,
			Exception {
		try {
			String GetCategory = "select admin_menu_items_id, "
					+ "admin_menu_items_name " + "from admin_menu_items "
					+ "where admin_menu_items_active_yn = 'y' "
					+ "order by admin_menu_items_order";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenuItemsNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenuItemsNamesIds:Exception:"
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
	 * Get admin menu item detail entries for display on edit screen
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAdminMenuItemsDetailForView()
			throws SQLException, Exception {
		try {
			String GetCategory = "select admin_menu_item_detail_id, "
					+ "admin_menu_item_detail_active_yn, "
					+ "admin_menu_items_id, " + "admin_menu_item_detail_name "
					+ "from admin_menu_item_detail "
					+ "where admin_menu_items_id = '" + admin_menu_items_id
					+ "' order by admin_menu_item_detail_order";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenuItemsDetailForView:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenuItemsDetailForView:Exception:"
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
	 * Add a menu item
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addAdminMenuItem() throws SQLException, Exception {

		try {
			String sSQL = "insert into admin_menu_items ("
					+ "admin_menu_items_name, " + "admin_menu_items_uri, "
					+ "admin_menu_items_order) " + "values (" + "'"
					+ admin_menu_items_name + "', ";

			if (page_id > 0) {
				sSQL += "(select page_invocation from page where "
						+ "page_id = '" + page_id + "'), ";
			} else {
				sSQL += "'javascript:undefined', ";
			}

			sSQL += "(select (max(admin_menu_items_order) + 1) from admin_menu_items))";

			pst = conn.prepareStatement(sSQL);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenu:addAdminMenuItem:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:addAdminMenuItem:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Update an admin menu item by id
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateAdminMenuItem() throws SQLException, Exception {
		String sSQL = "";
		try {
			sSQL = "update admin_menu_items set " + "admin_menu_items_name = '"
					+ admin_menu_items_name + "', ";

			if (page_id > 0) {
				sSQL += "admin_menu_items_uri = (select page_invocation from page where "
						+ "page_id = '" + page_id + "'), ";
			} else {
				sSQL += "admin_menu_items_uri = 'javascript:undefined', ";
			}

			sSQL += "page_id = '" + page_id + "' "
					+ "where admin_menu_items_id = '" + admin_menu_items_id
					+ "'";

			pst = conn.prepareStatement(sSQL);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenu:updateAdminMenuItem:SQLException:"
					+ e.toString() + "\nsql = " + sSQL);
		} catch (Exception e) {
			Errors.addError("AdminMenu:updateAdminMenuItem:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Deletes an admin menu item
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deleteAdminMenuItem() throws SQLException, Exception {

		try {
			String sSQL = "delete from admin_menu_items "
					+ "where admin_menu_items_id = '" + admin_menu_items_id
					+ "'";

			pst = conn.prepareStatement(sSQL);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenu:deleteAdminMenuItem:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:deleteAdminMenuItem:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void deleteRecord() throws SQLException, Exception {

		try {
			String sSQL = "delete from admin_menu_items "
					+ "where admin_menu_items_id = '" + admin_menu_items_id
					+ "'";

			pst = conn.prepareStatement(sSQL);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("AdminMenu:deleteRecord:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:deleteRecord:Exception:" + e.toString());
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
	 * Generate conf that describes tree menu
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String generateMenuJavaScript() throws Exception {
		String sJavaScript = "";
		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		XDisconnectedRowSet drs2 = new XDisconnectedRowSet();
		ResultSet irs = null;
		try {
			drs = getAdminMenu();

			while (drs.next()) {
				sJavaScript = "USETEXTLINKS = "
						+ drs.getString("use_textlinks") + "\n";
				sJavaScript += "STARTALLOPEN = "
						+ drs.getString("start_all_open") + "\n";
				sJavaScript += "USEFRAMES = 0" + "\n";
				sJavaScript += "USEICONS = " + drs.getString("use_icons")
						+ "\n";
				sJavaScript += "WRAPTEXT = " + drs.getString("wrap_text")
						+ "\n";
				sJavaScript += "PRESERVESTATE = "
						+ drs.getString("preserve_state") + "\n";
				sJavaScript += "HIGHLIGHT = " + drs.getString("highlight")
						+ "\n";
				sJavaScript += "HIGHLIGHT_COLOR = '"
						+ drs.getString("highlight_color") + "'\n";
				sJavaScript += "HIGHLIGHT_BG = '"
						+ drs.getString("highlight_bg") + "'\n";
				sJavaScript += "ICONPATH = '" + drs.getString("icon_path")
						+ "'\n";
				sJavaScript += "foldersTree = gFld(\"<b>"
						+ drs.getString("menu_title") + "</b>\", \"";
				sJavaScript += drs.getString("main_uri");
				sJavaScript += "\")" + "\n";
			}
			drs.close();
			sJavaScript += "foldersTree.treeID = \"t2\"\n";

			drs2 = this.getActiveAdminMenuItems();

			while (drs2.next()) {
				sJavaScript += "aux2 = insFld(foldersTree, gFld(\"";
				sJavaScript += drs2.getString("admin_menu_items_name");
				sJavaScript += "\", \"";
				sJavaScript += drs2.getString("admin_menu_items_uri");
				sJavaScript += "\"))";
				sJavaScript += "\n";

				String sSQL = "select * from admin_menu_item_detail "
						+ "where admin_menu_items_id = '"
						+ drs2.getInt("admin_menu_items_id") + "' "
						+ "and admin_menu_item_detail_active_yn = 'y' "
						+ "order by admin_menu_item_detail_order";
				st = conn.createStatement();
				irs = st.executeQuery(sSQL);

				if (irs != null) {
					while (irs.next()) {
						sJavaScript += "insDoc(aux2, gLnk(\"";
						sJavaScript += irs
								.getString("admin_menu_item_detail_target");
						sJavaScript += "\", ";
						sJavaScript += "\"";
						sJavaScript += irs
								.getString("admin_menu_item_detail_name");
						sJavaScript += "\", ";
						sJavaScript += "\"";
						sJavaScript += irs
								.getString("admin_menu_item_detail_uri");
						sJavaScript += "\"))\n";
					}
				}
			}

			irs.close();
			irs = null;
			st.close();
			st = null;
			drs2.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (irs != null) {
				irs.close();
				irs = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}
		}
		return sJavaScript;
	}

	/**
	 * Get one admin menu item by id
	 * 
	 * @return drs
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAdminMenuItemsById() throws SQLException,
			Exception {
		try {
			String GetCategory = "select * from admin_menu_items "
					+ "where admin_menu_items_id = '" + admin_menu_items_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenuItemsById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenuItemsById:Exception:"
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
	 * Get a single admin menu item detail entry by id
	 * 
	 * @return drs
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAdminMenuItemDetailById()
			throws SQLException, Exception {
		try {
			String GetCategory = "select * from admin_menu_item_detail "
					+ "where admin_menu_item_detail_id = '"
					+ admin_menu_item_detail_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("AdminMenu:getAdminMenuItemDetailById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("AdminMenu:getAdminMenuItemDetailById:Exception:"
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
	 * Change active status of admin menu item
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void changeAdminMenuItemActiveStatus() throws SQLException,
			Exception {

		try {
			String sSQL = "update admin_menu_items set "
					+ "admin_menu_items_active_yn = " + "'"
					+ admin_menu_items_active_yn + "'  "
					+ "where admin_menu_items_id = " + "'"
					+ admin_menu_items_id + "'";

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
	public void changeAdminMenuDetailItemActiveStatus() throws SQLException,
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
	 * @see
	 * com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
	 */
	public void setPrimaryKey(String inId) {
		this.admin_menu_items_id = inId;
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
		this.setAdmin_menu_items_active_yn(psStatus);
		this.changeAdminMenuItemActiveStatus();
	}

	/**
	 * @return Returns the admin_menu_id.
	 */
	public int getAdmin_menu_id() {
		return admin_menu_id;
	}

	/**
	 * @param admin_menu_id
	 *            The admin_menu_id to set.
	 */
	public void setAdmin_menu_id(int admin_menu_id) {
		this.admin_menu_id = admin_menu_id;
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
	 * @return Returns the folders_tree_id.
	 */
	public String getFolders_tree_id() {
		return folders_tree_id;
	}

	/**
	 * @param folders_tree_id
	 *            The folders_tree_id to set.
	 */
	public void setFolders_tree_id(String folders_tree_id) {
		this.folders_tree_id = folders_tree_id;
	}

	/**
	 * @return Returns the highlight.
	 */
	public int getHighlight() {
		return highlight;
	}

	/**
	 * @param highlight
	 *            The highlight to set.
	 */
	public void setHighlight(int highlight) {
		this.highlight = highlight;
	}

	/**
	 * @return Returns the highlight_bg.
	 */
	public String getHighlight_bg() {
		return highlight_bg;
	}

	/**
	 * @param highlight_bg
	 *            The highlight_bg to set.
	 */
	public void setHighlight_bg(String highlight_bg) {
		this.highlight_bg = highlight_bg;
	}

	/**
	 * @return Returns the highlight_color.
	 */
	public String getHighlight_color() {
		return highlight_color;
	}

	/**
	 * @param highlight_color
	 *            The highlight_color to set.
	 */
	public void setHighlight_color(String highlight_color) {
		this.highlight_color = highlight_color;
	}

	/**
	 * @return Returns the icon_path.
	 */
	public String getIcon_path() {
		return icon_path;
	}

	/**
	 * @param icon_path
	 *            The icon_path to set.
	 */
	public void setIcon_path(String icon_path) {
		this.icon_path = icon_path;
	}

	/**
	 * @return Returns the main_uri.
	 */
	public String getMain_uri() {
		return main_uri;
	}

	/**
	 * @param main_uri
	 *            The main_uri to set.
	 */
	public void setMain_uri(String main_uri) {
		this.main_uri = main_uri;
	}

	/**
	 * @return Returns the menu_title.
	 */
	public String getMenu_title() {
		return menu_title;
	}

	/**
	 * @param menu_title
	 *            The menu_title to set.
	 */
	public void setMenu_title(String menu_title) {
		this.menu_title = menu_title;
	}

	/**
	 * @return Returns the preserve_state.
	 */
	public int getPreserve_state() {
		return preserve_state;
	}

	/**
	 * @param preserve_state
	 *            The preserve_state to set.
	 */
	public void setPreserve_state(int preserve_state) {
		this.preserve_state = preserve_state;
	}

	/**
	 * @return Returns the start_all_open.
	 */
	public int getStart_all_open() {
		return start_all_open;
	}

	/**
	 * @param start_all_open
	 *            The start_all_open to set.
	 */
	public void setStart_all_open(int start_all_open) {
		this.start_all_open = start_all_open;
	}

	/**
	 * @return Returns the use_icons.
	 */
	public int getUse_icons() {
		return use_icons;
	}

	/**
	 * @param use_icons
	 *            The use_icons to set.
	 */
	public void setUse_icons(int use_icons) {
		this.use_icons = use_icons;
	}

	/**
	 * @return Returns the wrap_text.
	 */
	public int getWrap_text() {
		return wrap_text;
	}

	/**
	 * @param wrap_text
	 *            The wrap_text to set.
	 */
	public void setWrap_text(int wrap_text) {
		this.wrap_text = wrap_text;
	}

	/**
	 * @return Returns the use_text_links.
	 */
	public int getUse_text_links() {
		return use_text_links;
	}

	/**
	 * @param use_text_links
	 *            The use_text_links to set.
	 */
	public void setUse_text_links(int use_text_links) {
		this.use_text_links = use_text_links;
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
	 * @return Returns the admin_menu_items_name.
	 */
	public String getAdmin_menu_items_name() {
		return admin_menu_items_name;
	}

	/**
	 * @param admin_menu_items_name
	 *            The admin_menu_items_name to set.
	 */
	public void setAdmin_menu_items_name(String admin_menu_items_name) {
		this.admin_menu_items_name = admin_menu_items_name;
	}

	/**
	 * @return Returns the admin_menu_items_order.
	 */
	public int getAdmin_menu_items_order() {
		return admin_menu_items_order;
	}

	/**
	 * @param admin_menu_items_order
	 *            The admin_menu_items_order to set.
	 */
	public void setAdmin_menu_items_order(int admin_menu_items_order) {
		this.admin_menu_items_order = admin_menu_items_order;
	}

	/**
	 * @return Returns the admin_menu_items_uri.
	 */
	public String getAdmin_menu_items_uri() {
		return admin_menu_items_uri;
	}

	/**
	 * @param admin_menu_items_uri
	 *            The admin_menu_items_uri to set.
	 */
	public void setAdmin_menu_items_uri(String admin_menu_items_uri) {
		this.admin_menu_items_uri = admin_menu_items_uri;
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