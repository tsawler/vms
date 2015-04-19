//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: GetMenu.java,v $
//Revision 1.12.2.3.4.3.6.3.2.7.2.5  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.12.2.3.4.3.6.3.2.7.2.4  2007/08/21 11:13:26  tcs
//Better checking for empty menu
//
//Revision 1.12.2.3.4.3.6.3.2.7.2.3  2007/08/13 19:06:53  tcs
//*** empty log message ***
//
//Revision 1.12.2.3.4.3.6.3.2.7.2.2  2007/06/18 17:34:50  tcs
//Added dd menu support
//
//Revision 1.12.2.3.4.3.6.3.2.7.2.1  2007/03/30 17:20:51  tcs
//Made headers clickable
//
//Revision 1.12.2.3.4.3.6.3.2.7  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//Revision 1.12.2.3.4.3.6.3.2.6  2007/02/25 01:52:28  tcs
//support for additional menu type (pop out left)
//
//Revision 1.12.2.3.4.3.6.3.2.5  2007/02/06 18:02:28  tcs
//Added missing li tag in collapsible menu
//
//Revision 1.12.2.3.4.3.6.3.2.4  2007/02/02 19:18:26  tcs
//Changed to support components linked in menu
//
//Revision 1.12.2.3.4.3.6.3.2.3  2007/02/01 12:35:00  tcs
//Corrected generated html for collapsible menu
//
//Revision 1.12.2.3.4.3.6.3.2.2  2007/01/29 16:15:21  tcs
//Added support for collapsible vertical menus
//
//Revision 1.12.2.3.4.3.6.3.2.1  2007/01/28 00:51:56  tcs
//Added support for per menu item access level restrictions
//
//Revision 1.12.2.3.4.3.6.3  2007/01/12 19:27:27  tcs
//fixed unclosed <td> tag typo
//
//Revision 1.12.2.3.4.3.6.2  2006/12/19 19:28:51  tcs
//Added jsptemplate stuff
//
//Revision 1.12.2.3.4.3.6.1  2006/04/07 18:19:13  tcs
//*** empty log message ***
//
//Revision 1.12.2.3.4.3  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.12.2.3.4.2  2005/08/18 18:21:18  tcs
//Improved formatting of vertical menu
//
//Revision 1.12.2.3.4.1  2005/08/17 17:41:45  tcs
//Changed vertical menu to be a table (more control over styling with css)
//
//Revision 1.12.2.3  2005/03/24 22:32:17  tcs
//span -> div
//
//Revision 1.12.2.2  2005/03/07 11:26:05  tcs
//Removed explicit connection, and use connection from setter
//
//Revision 1.12.2.1  2005/03/07 11:22:37  tcs
//Updated javadocs
//
//Revision 1.12  2004/11/05 13:07:59  tcs
//Changes due to simplified database structure for pages & admin pages
//
//Revision 1.11  2004/10/21 18:36:49  tcs
//Removed jsessionid stuff
//
//Revision 1.10  2004/07/20 14:49:35  tcs
//changed style for menu items
//
//Revision 1.9  2004/07/05 10:15:19  tcs
//changed div to span
//
//Revision 1.8  2004/06/28 16:58:17  tcs
//Modifications for cookieless users
//
//Revision 1.7  2004/06/25 16:33:45  tcs
//Changed some methods to return XDisconnectedRowSets
//
//Revision 1.6  2004/06/25 02:15:23  tcs
//Closed statement
//
//Revision 1.5  2004/06/24 17:58:16  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.4  2004/05/31 12:58:11  tcs
//Added additional XHTML stuff
//
//Revision 1.3  2004/05/31 11:47:17  tcs
//Removed style and default to content for menu
//
//Revision 1.2  2004/05/29 19:07:36  tcs
//Made xhtml compliant
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.database.Menu;
import com.verilion.database.PageTemplate;

/**
 * Routines for getting and formatting an html menu
 * <P>
 * Dec 09, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class GetMenu {

	private static Connection conn;
	private static boolean horizontal = false;
	private static String divider = "";
	private static int ct_menu_type_id = 0;

	public GetMenu() {
		super();
	}

	public static String getHTMLPageGroupMenu(String pageGroupName,
			int ctLanguageId, String useSef, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		String theMenu = "";
		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		GenericTable myTable = new GenericTable("page_group");
		int alignment = 0;
		int menu_id = 0;
		int ct_access_level_id = 0;
		int customer_access_level_id = 0;
		XDisconnectedRowSet rs = new XDisconnectedRowSet();
		XDisconnectedRowSet rs2 = new XDisconnectedRowSet();
		String theAction = "";
		String page_name = "";
		int page_id = 0;
		int page_group_id = 0;
		
		// figure out which menu to display based on page group name
//		myTable.setConn(conn);
//		myTable.setSSelectWhat("page_group_id");
//		myTable.setSCustomWhere(" and page_group_name = '" + pageGroupName
//				+ "'");
		
		// figure out what page we are on, and whether or not to display a menu
		HashMap hm = (HashMap) session.getAttribute("pHm");
		page_name = (String) hm.get("p");
//		myTable.setConn(conn);
//		myTable.setSSelectWhat(" page_group_id ");
//		myTable.setSCustomWhere(" and page_id = (select page_id from page where page_name = '"
//				+ page_name
//				+ "') ");
//		
//		drs = myTable.getAllRecordsDisconnected();
//		while (drs.next()){
//			page_group_id = drs.getInt(1);
//		}
		
		myTable.setConn(conn);
		myTable.setSTable("v_page_group");
		myTable.setSSelectWhat("menu_id");
		myTable.setSCustomWhere(" and page_name = '" + page_name + "' ");
		drs = myTable.getAllRecordsDisconnected();
		while (drs.next()) {
			menu_id = drs.getInt(1);
		}

		try {
			customer_access_level_id = Integer.parseInt((String) session
					.getAttribute("customer_access_level"));
		} catch (Exception e) {
			customer_access_level_id = 0;
		}

		try {
			// open connection to database
			Menu myMenu = new Menu();
			myMenu.setConn(conn);

			if (menu_id == 0)
				return "";

			// get the alignment for our menu
			alignment = myMenu.getMenuAlignment(menu_id);

			// now get divider, if any
			myMenu.setMenu_id(menu_id);
			divider = myMenu.getDivider();

			// decide if our menu is horizontal or not
			horizontal = myMenu.getMenuHorizontalOrVertical(menu_id);
			ct_menu_type_id = myMenu.getTypeOfMenu(menu_id);

			// if our menu is horizontal, start with
			// the divider, if any
			if ((horizontal) && (divider.length() > 0)) {
				// theMenu += divider + " ";
			}

			if (ct_menu_type_id != 3) {
				if (alignment == 1) {
					theMenu += "<div style=\"text-align: left;\" align=\"left\">";
				} else if (alignment == 2) {
					theMenu += "<div style=\"text-align: right;\" align=\"right\">";
				} else {
					theMenu += "<div style=\"text-align: center;\" align=\"center\">";
				}
			}

			// now get resultset containing menu names/actions
			PageTemplate myPageTemplate = new PageTemplate();
			myPageTemplate.setConn(conn);
			myMenu.setMenu_id(menu_id);
			myMenu.setCt_language_id(ctLanguageId);

			rs = myMenu.getMenuForDisplay();

			if (ct_menu_type_id == 3) {
				// we are doing a pop out left menu

				theMenu += "\n<!-- start of menu id " + menu_id + "-->\n"
						+ "<div id=\"leftpopmenu" + menu_id
						+ "\" class=\"yuimenu\">\n" + "<div class=\"bd\">\n"
						+ "<ul class=\"first-of-type\">\n";
				// build our html menu
				while (rs.next()) {
					ct_access_level_id = rs.getInt("ct_access_level_id");
					if (rs.getString("menu_item_is_heading").equalsIgnoreCase(
							"y")) {
						// theMenu += "<li><a href=\"#\">"
						// + rs.getString("menu_item_detail_name").replaceAll(
						// "&",
						// "&amp;")
						// + "</a></li>\n";

						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\"" + theAction
											+ "\" " + extra + ">"
											+ rs.getString("component_name")
											+ "</a></li>";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							}
						}
					}
				}

				// close our div tag
				theMenu += "</ul>\n</div>\n</div>\n<!-- end of menu id "
						+ menu_id + " -->";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;
				// -------------------------+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			} else if (ct_menu_type_id == 4) {
				// drop down menu
				theMenu += "\n<!-- start of dd menu-->\n"
						+ "<div class=\"ddmenu\">\n" + "<ul id=\"menu_top\">\n";
				boolean firstItem = true;
				// build our html menu
				while (rs.next()) {
					ct_access_level_id = rs.getInt("ct_access_level_id");
					if (rs.getString("menu_item_is_heading").equalsIgnoreCase(
							"y")) {
						if (firstItem) {
							firstItem = false;
						} else {
							theMenu += "</ul>\n</li>\n";
						}
						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a>\n<ul>";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a>\n<ul>";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\""
											+ theAction
											+ "\" "
											+ extra
											+ ">"
											+ rs.getString("component_name")
											+ "</a>\n<ul>";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							}
						}
					} else {
						// ================================================================================================================
						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>\n";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>\n";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\"" + theAction
											+ "\" " + extra + ">"
											+ rs.getString("component_name")
											+ "</a></li>\n";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							}
						}
					}
				}

				// close our div tag
				theMenu += "</ul></ul>\n</div>\n</div>\n<!-- end of dd menu id "
						+ menu_id + " -->";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;

			} else {
				// it's either a standard horizontal or vertial menu.
				if (rs.next()) {
					rs.previous();
					
					if ((!horizontal) && (ct_menu_type_id == 2)) {
						theMenu += "<table class=\"menutable\">";
					}

					// build our html menu
					while (rs.next()) {
						ct_access_level_id = rs.getInt("ct_access_level_id");
						if (ct_access_level_id <= customer_access_level_id) {
							if (!horizontal) {
								theMenu += "<tr>";
								if (alignment == 1) {
									theMenu += "<td align=\"left\">";
								} else if (alignment == 2) {
									theMenu += "<td align=\"right\">";
								} else {
									theMenu += "<td>";
								}
							}
						}

						if (rs.getString("menu_item_is_heading").equals("n")) {
							if (ct_access_level_id == 0) {
								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {

									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ rs
														.getString("menu_item_action")
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ myPageTemplate
														.getInvocationAsString()
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>";
									}
								} else {
									if (!(horizontal)) {
										theMenu += "&nbsp;";
									}
								}
							} else {
								if ((ct_access_level_id == 1)
										&& (customer_access_level_id >= 1)) {

									if (rs.getString("menu_item_is_spacer")
											.equals("n")) {
										if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") == 0)) {
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ rs
															.getString("menu_item_action")
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") > 0)
												&& (rs.getInt("component_id") == 0)) {
											myPageTemplate.setPage_id(rs
													.getInt("page_id"));
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ myPageTemplate
															.getInvocationAsString()
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("component_id") > 0)
												&& (rs.getInt("page_id") == 0)) {
											theAction = rs
													.getString("component_url");
											String extra = rs
													.getString("component_url_extra");
											if (extra == null)
												extra = "";
											if (theAction != null) {
												if (theAction.length() < 1) {
													theAction = "#";
												}
											} else {
												theAction = "#";
											}
											theMenu += "<a href=\""
													+ theAction
													+ "\" "
													+ extra
													+ ">"
													+ rs
															.getString("component_name")
													+ "</a>";
										}
									} else {
										if (!(horizontal)) {
											theMenu += "&nbsp;";
										}
									}
								} else if ((ct_access_level_id == 2)
										&& (customer_access_level_id >= 2)) {

									if (rs.getString("menu_item_is_spacer")
											.equals("n")) {
										if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") == 0)) {
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ rs
															.getString("menu_item_action")
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") > 0)
												&& (rs.getInt("component_id") == 0)) {
											myPageTemplate.setPage_id(rs
													.getInt("page_id"));
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ myPageTemplate
															.getInvocationAsString()
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") > 0)) {
											theAction = rs
													.getString("component_url");
											String extra = rs
													.getString("component_url_extra");
											if (extra == null)
												extra = "";
											if (theAction != null) {
												if (theAction.length() < 1) {
													theAction = "#";
												}
											} else {
												theAction = "#";
											}
											theMenu += "<a href=\""
													+ theAction
													+ "\" "
													+ extra
													+ ">"
													+ rs
															.getString("component_name")
													+ "</a>";
										}
									} else {
										if (!(horizontal)) {
											theMenu += "&nbsp;";
										}
									}
								} else if ((ct_access_level_id == 3)
										&& (customer_access_level_id == 3)) {

									if (rs.getString("menu_item_is_spacer")
											.equals("n")) {
										if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") == 0)) {
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ rs
															.getString("menu_item_action")
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") > 0)
												&& (rs.getInt("component_id") == 0)) {
											myPageTemplate.setPage_id(rs
													.getInt("page_id"));
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ myPageTemplate
															.getInvocationAsString()
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") > 0)) {
											theAction = rs
													.getString("component_url");
											String extra = rs
													.getString("component_url_extra");
											if (extra == null)
												extra = "";
											if (theAction != null) {
												if (theAction.length() < 1) {
													theAction = "#";
												}
											} else {
												theAction = "#";
											}
											theMenu += "<a href=\""
													+ theAction
													+ "\" "
													+ extra
													+ ">"
													+ rs
															.getString("component_name")
													+ "</a>";
										}
									} else {
										if (!(horizontal)) {
											theMenu += "&nbsp;";
										}
									}
								}
							}
						} else {
							// this is a heading. Just print the text

							/*
							 * theMenu += "<h1>" +
							 * rs.getString("menu_item_detail_name").replaceAll(
							 * "&", "&amp;") + "</h1>";
							 */
							if ((rs.getInt("page_id") == 0)
									&& (rs.getInt("component_id") == 0)) {
								theMenu += "<a title=\""
										+ rs.getString(
												"menu_item_detail_tooltip")
												.replaceAll("&", "and")
										+ "\" href=\""
										+ rs.getString("menu_item_action")
										+ "\"><h1>"
										+ rs.getString("menu_item_detail_name")
												.replaceAll("&", "&amp;")
										+ "</h1></a>";
							} else if ((rs.getInt("page_id") > 0)
									&& (rs.getInt("component_id") == 0)) {
								myPageTemplate.setPage_id(rs.getInt("page_id"));
								theMenu += "<a title=\""
										+ rs.getString(
												"menu_item_detail_tooltip")
												.replaceAll("&", "and")
										+ "\" href=\""
										+ myPageTemplate
												.getInvocationAsString()
										+ "\"><h1>"
										+ rs.getString("menu_item_detail_name")
												.replaceAll("&", "&amp;")
										+ "</h1></a>";
							} else if ((rs.getInt("page_id") == 0)
									&& (rs.getInt("component_id") > 0)) {
								theAction = rs.getString("component_url");
								String extra = rs
										.getString("component_url_extra");
								if (extra == null)
									extra = "";
								if (theAction != null) {
									if (theAction.length() < 1) {
										theAction = "#";
									}
								} else {
									theAction = "#";
								}
								theMenu += "<a href=\"" + theAction + "\" "
										+ extra + "><h1>"
										+ rs.getString("component_name")
										+ "</h1></a>";
							}
						}
						if (ct_access_level_id <= customer_access_level_id) {
							if (!(horizontal)) {
								theMenu += "</td></tr>";
							}
							if ((horizontal) && (divider.length() > 0)) {
								theMenu += " " + divider + " ";
							}
						}

					} // while

					if (!horizontal) {
						theMenu += "</table>";
					}
				}

				// close our div tag
				if (ct_menu_type_id != 3)
					theMenu += "</div>";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("GetMenu:Exception:" + e.getMessage());
		} finally {
			if (rs2 != null) {
				rs2.close();
				rs2 = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
		return theMenu;
	}

	/**
	 * Get a formatted menu. Can set divider (between links), and whether menu
	 * is to be horizontal or vertical.
	 * 
	 * @param menuName
	 * @param ctLanguageId
	 * @param useSef
	 * @param request
	 * @param response
	 * @return String - the html formatted menu
	 * @throws Exception
	 */
	public static String getHTMLMenu(String menuName, int ctLanguageId,
			String useSef, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		String theMenu = "";
		int alignment = 0;
		int menu_id = 0;
		int ct_access_level_id = 0;
		int customer_access_level_id = 0;
		XDisconnectedRowSet rs = new XDisconnectedRowSet();
		XDisconnectedRowSet rs2 = new XDisconnectedRowSet();
		String theAction = "";

		try {
			customer_access_level_id = Integer.parseInt((String) session
					.getAttribute("customer_access_level"));
		} catch (Exception e) {
			customer_access_level_id = 0;
		}

		try {
			// open connection to databse
			Menu myMenu = new Menu();
			myMenu.setConn(conn);

			// get our menu id from menu name
			myMenu.setMenu_name(menuName);
			menu_id = myMenu.getMenuIdByMenuName();

			if (menu_id == 0)
				return "";

			// get the alignment for our menu
			alignment = myMenu.getMenuAlignment(menuName);

			// now get divider, if any
			myMenu.setMenu_id(menu_id);
			divider = myMenu.getDivider();

			// decide if our menu is horizontal or not
			horizontal = myMenu.getMenuHorizontalOrVertical(menuName);
			ct_menu_type_id = myMenu.getTypeOfMenu(menuName);

			// if our menu is horizontal, start with
			// the divider, if any
			if ((horizontal) && (divider.length() > 0)) {
				// theMenu += divider + " ";
			}

			if (ct_menu_type_id != 3) {
				if (alignment == 1) {
					theMenu += "<div style=\"text-align: left;\" align=\"left\">";
				} else if (alignment == 2) {
					theMenu += "<div style=\"text-align: right;\" align=\"right\">";
				} else {
					theMenu += "<div style=\"text-align: center; width: 100%;\" align=\"center\">";
				}
			}

			// now get resultset containing menu names/actions
			PageTemplate myPageTemplate = new PageTemplate();
			myPageTemplate.setConn(conn);
			myMenu.setMenu_id(menu_id);
			myMenu.setCt_language_id(ctLanguageId);

			rs = myMenu.getMenuForDisplay();

			if (ct_menu_type_id == 3) {
				// we are doing a pop out left menu

				theMenu += "\n<!-- start of menu id " + menu_id + "-->\n"
						+ "<div id=\"leftpopmenu" + menu_id
						+ "\" class=\"yuimenu\">\n" + "<div class=\"bd\">\n"
						+ "<ul class=\"first-of-type\">\n";
				// build our html menu
				while (rs.next()) {
					ct_access_level_id = rs.getInt("ct_access_level_id");
					if (rs.getString("menu_item_is_heading").equalsIgnoreCase(
							"y")) {
						// theMenu += "<li><a href=\"#\">"
						// + rs.getString("menu_item_detail_name").replaceAll(
						// "&",
						// "&amp;")
						// + "</a></li>\n";

						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\"" + theAction
											+ "\" " + extra + ">"
											+ rs.getString("component_name")
											+ "</a></li>";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							}
						}
					}
				}

				// close our div tag
				theMenu += "</ul>\n</div>\n</div>\n<!-- end of the menu id "
						+ menu_id + " -->";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;
				// -------------------------+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			} else if (ct_menu_type_id == 4) {
				// drop down menu
				theMenu += "\n<!-- start of dd menu-->\n"
						+ "<ul id=\"menu_top\">\n";
				boolean firstItem = true;
				// build our html menu
				while (rs.next()) {
					ct_access_level_id = rs.getInt("ct_access_level_id");
					if (rs.getString("menu_item_is_heading").equalsIgnoreCase(
							"y")) {
						if (firstItem) {
							firstItem = false;
						} else {
							theMenu += "</ul>\n</li>\n";
						}
						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a>\n<ul>";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a>\n<ul>";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\""
											+ theAction
											+ "\" "
											+ extra
											+ ">"
											+ rs.getString("component_name")
											+ "</a>\n<ul>";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							}
						}
					} else {
						// ================================================================================================================
						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>\n";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>\n";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\"" + theAction
											+ "\" " + extra + ">"
											+ rs.getString("component_name")
											+ "</a></li>\n";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							}
						}
					}
				}

				// close our div tag
				theMenu += "</ul>\n</ul>\n</div>\n<!-- end of menu id "
						+ menu_id + " -->";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;

			} else {

				// it's either a standard horizontal or vertial menu.
				if (rs.next()) {
					rs.previous();

					if ((!horizontal) && (ct_menu_type_id == 2)) {
						theMenu += "<table class=\"menutable\">";
					}

					// build our html menu
					while (rs.next()) {
						ct_access_level_id = rs.getInt("ct_access_level_id");
						if (ct_access_level_id <= customer_access_level_id) {
							if (!horizontal) {
								theMenu += "<tr>";
								if (alignment == 1) {
									theMenu += "<td align=\"left\">";
								} else if (alignment == 2) {
									theMenu += "<td align=\"right\">";
								} else {
									theMenu += "<td>";
								}
							}
						}

						if (rs.getString("menu_item_is_heading").equals("n")) {
							if (ct_access_level_id == 0) {
								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {

									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ rs
														.getString("menu_item_action")
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ myPageTemplate
														.getInvocationAsString()
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>";
									}
								} else {
									if (!(horizontal)) {
										theMenu += "&nbsp;";
									}
								}
							} else {
								if ((ct_access_level_id == 1)
										&& (customer_access_level_id >= 1)) {

									if (rs.getString("menu_item_is_spacer")
											.equals("n")) {
										if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") == 0)) {
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ rs
															.getString("menu_item_action")
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") > 0)
												&& (rs.getInt("component_id") == 0)) {
											myPageTemplate.setPage_id(rs
													.getInt("page_id"));
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ myPageTemplate
															.getInvocationAsString()
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("component_id") > 0)
												&& (rs.getInt("page_id") == 0)) {
											theAction = rs
													.getString("component_url");
											String extra = rs
													.getString("component_url_extra");
											if (extra == null)
												extra = "";
											if (theAction != null) {
												if (theAction.length() < 1) {
													theAction = "#";
												}
											} else {
												theAction = "#";
											}
											theMenu += "<a href=\""
													+ theAction
													+ "\" "
													+ extra
													+ ">"
													+ rs
															.getString("component_name")
													+ "</a>";
										}
									} else {
										if (!(horizontal)) {
											theMenu += "&nbsp;";
										}
									}
								} else if ((ct_access_level_id == 2)
										&& (customer_access_level_id >= 2)) {

									if (rs.getString("menu_item_is_spacer")
											.equals("n")) {
										if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") == 0)) {
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ rs
															.getString("menu_item_action")
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") > 0)
												&& (rs.getInt("component_id") == 0)) {
											myPageTemplate.setPage_id(rs
													.getInt("page_id"));
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ myPageTemplate
															.getInvocationAsString()
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") > 0)) {
											theAction = rs
													.getString("component_url");
											String extra = rs
													.getString("component_url_extra");
											if (extra == null)
												extra = "";
											if (theAction != null) {
												if (theAction.length() < 1) {
													theAction = "#";
												}
											} else {
												theAction = "#";
											}
											theMenu += "<a href=\""
													+ theAction
													+ "\" "
													+ extra
													+ ">"
													+ rs
															.getString("component_name")
													+ "</a>";
										}
									} else {
										if (!(horizontal)) {
											theMenu += "&nbsp;";
										}
									}
								} else if ((ct_access_level_id == 3)
										&& (customer_access_level_id == 3)) {

									if (rs.getString("menu_item_is_spacer")
											.equals("n")) {
										if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") == 0)) {
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ rs
															.getString("menu_item_action")
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") > 0)
												&& (rs.getInt("component_id") == 0)) {
											myPageTemplate.setPage_id(rs
													.getInt("page_id"));
											theMenu += "<a title=\""
													+ rs
															.getString(
																	"menu_item_detail_tooltip")
															.replaceAll("&",
																	"and")
													+ "\" href=\""
													+ myPageTemplate
															.getInvocationAsString()
													+ "\">"
													+ rs
															.getString(
																	"menu_item_detail_name")
															.replaceAll("&",
																	"&amp;")
													+ "</a>";
										} else if ((rs.getInt("page_id") == 0)
												&& (rs.getInt("component_id") > 0)) {
											theAction = rs
													.getString("component_url");
											String extra = rs
													.getString("component_url_extra");
											if (extra == null)
												extra = "";
											if (theAction != null) {
												if (theAction.length() < 1) {
													theAction = "#";
												}
											} else {
												theAction = "#";
											}
											theMenu += "<a href=\""
													+ theAction
													+ "\" "
													+ extra
													+ ">"
													+ rs
															.getString("component_name")
													+ "</a>";
										}
									} else {
										if (!(horizontal)) {
											theMenu += "&nbsp;";
										}
									}
								}
							}
						} else {
							// this is a heading. Just print the text

							/*
							 * theMenu += "<h1>" +
							 * rs.getString("menu_item_detail_name").replaceAll(
							 * "&", "&amp;") + "</h1>";
							 */
							if ((rs.getInt("page_id") == 0)
									&& (rs.getInt("component_id") == 0)) {
								theMenu += "<a title=\""
										+ rs.getString(
												"menu_item_detail_tooltip")
												.replaceAll("&", "and")
										+ "\" href=\""
										+ rs.getString("menu_item_action")
										+ "\"><h1>"
										+ rs.getString("menu_item_detail_name")
												.replaceAll("&", "&amp;")
										+ "</h1></a>";
							} else if ((rs.getInt("page_id") > 0)
									&& (rs.getInt("component_id") == 0)) {
								myPageTemplate.setPage_id(rs.getInt("page_id"));
								theMenu += "<a title=\""
										+ rs.getString(
												"menu_item_detail_tooltip")
												.replaceAll("&", "and")
										+ "\" href=\""
										+ myPageTemplate
												.getInvocationAsString()
										+ "\"><h1>"
										+ rs.getString("menu_item_detail_name")
												.replaceAll("&", "&amp;")
										+ "</h1></a>";
							} else if ((rs.getInt("page_id") == 0)
									&& (rs.getInt("component_id") > 0)) {
								theAction = rs.getString("component_url");
								String extra = rs
										.getString("component_url_extra");
								if (extra == null)
									extra = "";
								if (theAction != null) {
									if (theAction.length() < 1) {
										theAction = "#";
									}
								} else {
									theAction = "#";
								}
								theMenu += "<a href=\"" + theAction + "\" "
										+ extra + "><h1>"
										+ rs.getString("component_name")
										+ "</h1></a>";
							}
						}
						if (ct_access_level_id <= customer_access_level_id) {
							if (!(horizontal)) {
								theMenu += "</td></tr>";
							}
							if ((horizontal) && (divider.length() > 0)) {
								theMenu += " " + divider + " ";
							}
						}

					} // while

					if (!horizontal) {
						theMenu += "</table>";
					}
				}

				// close our div tag
				if (ct_menu_type_id != 3)
					theMenu += "</div>";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("GetMenu:Exception:" + e.getMessage());
		} finally {
			if (rs2 != null) {
				rs2.close();
				rs2 = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
		return theMenu;
	}

	public static String getHTMLMenu(String menuName, int ctLanguageId,
			String useSef, String style, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		String theMenu = "";
		int alignment = 0;
		int menu_id = 0;
		int ct_access_level_id = 0;
		int customer_access_level_id = 0;
		XDisconnectedRowSet rs = new XDisconnectedRowSet();
		XDisconnectedRowSet rs2 = new XDisconnectedRowSet();
		String theAction = "";

		try {
			customer_access_level_id = Integer.parseInt((String) session
					.getAttribute("customer_access_level"));
		} catch (Exception e) {
			customer_access_level_id = 0;
		}

		try {
			// open connection to databse
			Menu myMenu = new Menu();
			myMenu.setConn(conn);

			// get our menu id from menu name
			myMenu.setMenu_name(menuName);
			menu_id = myMenu.getMenuIdByMenuName();

			// get the alignment for our menu
			alignment = myMenu.getMenuAlignment(menuName);

			// now get divider, if any
			myMenu.setMenu_id(menu_id);
			divider = myMenu.getDivider();

			// decide if our menu is horizontal or not
			horizontal = myMenu.getMenuHorizontalOrVertical(menuName);
			ct_menu_type_id = myMenu.getTypeOfMenu(menuName);

			// if our menu is horizontal, start with
			// the divider, if any
			if ((horizontal) && (divider.length() > 0)) {
				// theMenu += divider + " ";
			}

			if (ct_menu_type_id != 3) {
				if (alignment == 1) {
					theMenu += "<div style=\"text-align: left;\" align=\"left\">";
				} else if (alignment == 2) {
					theMenu += "<div style=\"text-align: right;\" align=\"right\">";
				} else {
					theMenu += "<div style=\"text-align: center;\" align=\"center\">";
				}
			}

			// now get resultset containing menu names/actions
			PageTemplate myPageTemplate = new PageTemplate();
			myPageTemplate.setConn(conn);
			myMenu.setMenu_id(menu_id);
			myMenu.setCt_language_id(ctLanguageId);

			rs = myMenu.getMenuForDisplay();

			if (ct_menu_type_id == 3) {
				// we are doing a pop out left menu

				theMenu += "\n<!-- start of menu id " + menu_id + "-->\n"
						+ "<div id=\"leftpopmenu" + menu_id
						+ "\" class=\"yuimenu\">\n" + "<div class=\"bd\">\n"
						+ "<ul class=\"first-of-type\">\n";
				// build our html menu
				while (rs.next()) {
					ct_access_level_id = rs.getInt("ct_access_level_id");
					if (rs.getString("menu_item_is_heading").equalsIgnoreCase(
							"y")) {
						// theMenu += "<li><a href=\"#\">"
						// + rs.getString("menu_item_detail_name").replaceAll(
						// "&",
						// "&amp;")
						// + "</a></li>\n";

						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\"" + theAction
											+ "\" " + extra + ">"
											+ rs.getString("component_name")
											+ "</a></li>";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>";
									}
								}
							}
						}
					}
				}

				// close our div tag
				theMenu += "</ul>\n</div>\n</div>\n<!-- end of menu id "
						+ menu_id + " -->";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;
				// -------------------------+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			} else if (ct_menu_type_id == 4) {
				// drop down menu
				theMenu += "\n<!-- start of dd menu-->\n"
						+ "<ul id=\"menu_top\">\n";
				boolean firstItem = true;
				// build our html menu
				while (rs.next()) {
					ct_access_level_id = rs.getInt("ct_access_level_id");
					if (rs.getString("menu_item_is_heading").equalsIgnoreCase(
							"y")) {
						if (firstItem) {
							firstItem = false;
						} else {
							theMenu += "</ul>\n</li>\n";
						}
						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a>\n<ul>";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a>\n<ul>";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\""
											+ theAction
											+ "\" "
											+ extra
											+ ">"
											+ rs.getString("component_name")
											+ "</a>\n<ul>";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>\n<ul>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>\n<ul>";
									}
								}
							}
						}
					} else {
						// ================================================================================================================
						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theAction = rs
											.getString("menu_item_action");
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>\n";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theAction = myPageTemplate
											.getInvocationAsString();
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ theAction
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a></li>\n";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<li><a href=\"" + theAction
											+ "\" " + extra + ">"
											+ rs.getString("component_name")
											+ "</a></li>\n";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theAction = rs
												.getString("menu_item_action");
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theAction = myPageTemplate
												.getInvocationAsString();
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ theAction
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a></li>\n";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<li><a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a></li>\n";
									}
								}
							}
						}
						// ====================================================================================================================
					}
				}

				// close our div tag
				theMenu += "</ul>\n</div>\n</div>\n<!-- end of menu id "
						+ menu_id + " -->";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;

				// -----------------------------------------------------------------------------------------------------------------------
			} else {

				// it's either a standard horizontal or vertial menu.

				if ((!horizontal) && (ct_menu_type_id == 2)) {
					theMenu += "<table class=\"" + style + "\">";
				}

				// build our html menu
				while (rs.next()) {
					ct_access_level_id = rs.getInt("ct_access_level_id");
					if (ct_access_level_id <= customer_access_level_id) {
						if (!horizontal) {
							theMenu += "<tr>";
							if (alignment == 1) {
								theMenu += "<td align=\"left\">";
							} else if (alignment == 2) {
								theMenu += "<td align=\"right\">";
							} else {
								theMenu += "<td>";
							}
						}
					}

					if (rs.getString("menu_item_is_heading").equals("n")) {
						if (ct_access_level_id == 0) {
							if (rs.getString("menu_item_is_spacer").equals("n")) {

								if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") == 0)) {
									theMenu += "<a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ rs.getString("menu_item_action")
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a>";
								} else if ((rs.getInt("page_id") > 0)
										&& (rs.getInt("component_id") == 0)) {
									myPageTemplate.setPage_id(rs
											.getInt("page_id"));
									theMenu += "<a title=\""
											+ rs.getString(
													"menu_item_detail_tooltip")
													.replaceAll("&", "and")
											+ "\" href=\""
											+ myPageTemplate
													.getInvocationAsString()
											+ "\">"
											+ rs.getString(
													"menu_item_detail_name")
													.replaceAll("&", "&amp;")
											+ "</a>";
								} else if ((rs.getInt("page_id") == 0)
										&& (rs.getInt("component_id") > 0)) {
									theAction = rs.getString("component_url");
									String extra = rs
											.getString("component_url_extra");
									if (extra == null)
										extra = "";
									if (theAction != null) {
										if (theAction.length() < 1) {
											theAction = "#";
										}
									} else {
										theAction = "#";
									}
									theMenu += "<a href=\"" + theAction + "\" "
											+ extra + ">"
											+ rs.getString("component_name")
											+ "</a>";
								}
							} else {
								if (!(horizontal)) {
									theMenu += "&nbsp;";
								}
							}
						} else {
							if ((ct_access_level_id == 1)
									&& (customer_access_level_id >= 1)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ rs
														.getString("menu_item_action")
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ myPageTemplate
														.getInvocationAsString()
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("component_id") > 0)
											&& (rs.getInt("page_id") == 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>";
									}
								} else {
									if (!(horizontal)) {
										theMenu += "&nbsp;";
									}
								}
							} else if ((ct_access_level_id == 2)
									&& (customer_access_level_id >= 2)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ rs
														.getString("menu_item_action")
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ myPageTemplate
														.getInvocationAsString()
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>";
									}
								} else {
									if (!(horizontal)) {
										theMenu += "&nbsp;";
									}
								}
							} else if ((ct_access_level_id == 3)
									&& (customer_access_level_id == 3)) {

								if (rs.getString("menu_item_is_spacer").equals(
										"n")) {
									if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") == 0)) {
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ rs
														.getString("menu_item_action")
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") > 0)
											&& (rs.getInt("component_id") == 0)) {
										myPageTemplate.setPage_id(rs
												.getInt("page_id"));
										theMenu += "<a title=\""
												+ rs
														.getString(
																"menu_item_detail_tooltip")
														.replaceAll("&", "and")
												+ "\" href=\""
												+ myPageTemplate
														.getInvocationAsString()
												+ "\">"
												+ rs
														.getString(
																"menu_item_detail_name")
														.replaceAll("&",
																"&amp;")
												+ "</a>";
									} else if ((rs.getInt("page_id") == 0)
											&& (rs.getInt("component_id") > 0)) {
										theAction = rs
												.getString("component_url");
										String extra = rs
												.getString("component_url_extra");
										if (extra == null)
											extra = "";
										if (theAction != null) {
											if (theAction.length() < 1) {
												theAction = "#";
											}
										} else {
											theAction = "#";
										}
										theMenu += "<a href=\""
												+ theAction
												+ "\" "
												+ extra
												+ ">"
												+ rs
														.getString("component_name")
												+ "</a>";
									}
								} else {
									if (!(horizontal)) {
										theMenu += "&nbsp;";
									}
								}
							}
						}
					} else {
						// this is a heading. Just print the text

						/*
						 * theMenu += "<h1>" +
						 * rs.getString("menu_item_detail_name").replaceAll(
						 * "&", "&amp;") + "</h1>";
						 */
						if ((rs.getInt("page_id") == 0)
								&& (rs.getInt("component_id") == 0)) {
							theMenu += "<a title=\""
									+ rs.getString("menu_item_detail_tooltip")
											.replaceAll("&", "and")
									+ "\" href=\""
									+ rs.getString("menu_item_action")
									+ "\"><h1>"
									+ rs.getString("menu_item_detail_name")
											.replaceAll("&", "&amp;")
									+ "</h1></a>";
						} else if ((rs.getInt("page_id") > 0)
								&& (rs.getInt("component_id") == 0)) {
							myPageTemplate.setPage_id(rs.getInt("page_id"));
							theMenu += "<a title=\""
									+ rs.getString("menu_item_detail_tooltip")
											.replaceAll("&", "and")
									+ "\" href=\""
									+ myPageTemplate.getInvocationAsString()
									+ "\"><h1>"
									+ rs.getString("menu_item_detail_name")
											.replaceAll("&", "&amp;")
									+ "</h1></a>";
						} else if ((rs.getInt("page_id") == 0)
								&& (rs.getInt("component_id") > 0)) {
							theAction = rs.getString("component_url");
							String extra = rs.getString("component_url_extra");
							if (extra == null)
								extra = "";
							if (theAction != null) {
								if (theAction.length() < 1) {
									theAction = "#";
								}
							} else {
								theAction = "#";
							}
							theMenu += "<a href=\"" + theAction + "\" " + extra
									+ "><h1>" + rs.getString("component_name")
									+ "</h1></a>";
						}
					}
					if (ct_access_level_id <= customer_access_level_id) {
						if (!(horizontal)) {
							theMenu += "</td></tr>";
						}
						if ((horizontal) && (divider.length() > 0)) {
							theMenu += " " + divider + " ";
						}
					}
				}

				if (!horizontal) {
					theMenu += "</table>";
				}

				// close our div tag
				if (ct_menu_type_id != 3)
					theMenu += "</div>";

				rs2.close();
				rs2 = null;
				rs.close();
				rs = null;

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("GetMenu:Exception:" + e.getMessage());
		} finally {
			if (rs2 != null) {
				rs2.close();
				rs2 = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
		return theMenu;
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
		GetMenu.conn = conn;
	}

	/**
	 * @return Returns the divider.
	 */
	public static String getDivider() {
		return divider;
	}

	/**
	 * @param divider
	 *            The divider to set.
	 */
	public static void setDivider(String divider) {
		GetMenu.divider = divider;
	}

	/**
	 * @return Returns the horizontal.
	 */
	public static boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * @param horizontal
	 *            The horizontal to set.
	 */
	public static void setHorizontal(boolean horizontal) {
		GetMenu.horizontal = horizontal;
	}
}