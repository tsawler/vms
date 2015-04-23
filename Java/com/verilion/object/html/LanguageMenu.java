//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: LanguageMenu.java,v $
//Revision 1.4  2004/06/25 16:34:05  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.3  2004/06/24 17:58:17  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.2  2004/05/26 13:11:01  tcs
//Added class inputbox for ddlb
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import java.sql.Connection;

import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.CtLanguages;
import com.verilion.object.Errors;

/**
 * Gets language menu and returns it as a drop down list for inclusion on html
 * page
 * <P>
 * Dec 05, 2003
 * <P>
 * package com.display.util
 * <P>
 * 
 * @author tsawler
 * 
 */
public class LanguageMenu {

	public static Connection conn = null;

	/**
	 * Generates a drop down list of available lanaguages
	 * 
	 */
	public static String getLanguageDropDownListHTML(HttpSession session)
			throws Exception {

		String theMenu = "";
		XDisconnectedRowSet rs = new XDisconnectedRowSet();
		int curLang = 1;

		try {
			// check to see if we already have a language choice
			if (session.getAttribute("languageId") != null) {
				curLang = Integer.parseInt((String) session
						.getAttribute("languageId"));
			}
			// open database connection for database object
			// conn = DbBean.getDbConnection();
			CtLanguages myLanguages = new CtLanguages();
			myLanguages.setConn(conn);

			// build our menu
			theMenu = "<form action=\"/servlet/com.verilion.DoLanguageChoice\" method = \"post\">";
			theMenu += "<select class=\"inputbox\" name=\"lang\">";

			rs = myLanguages.getAllActiveLanguageNamesIds();

			while (rs.next()) {
				theMenu += "<option value=\"";
				theMenu += rs.getInt("ct_language_id");
				theMenu += "\"";
				if (rs.getInt("ct_language_id") == curLang) {
					theMenu += " selected";
				}
				theMenu += ">";
				theMenu += rs.getString("ct_language_name");
				theMenu += "</option>";
			}

			theMenu += "</select>";
			theMenu += "<input type = \"submit\" value = \"Go\">";
			theMenu += "</form>";

			rs.close();
			rs = null;
		} catch (Exception e) {
			Errors.addError("com.verilion.display.util.LanguageMenu.getLanguageMenu:Exception:"
					+ e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
		// return the message
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
		LanguageMenu.conn = conn;
	}

}