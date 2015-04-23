//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-05
//Revisions
//------------------------------------------------------------------------------
//$Log
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.object.Errors;

/**
 * Simple stub module example
 * 
 * @author tsawler
 * 
 */
public class SessionCounterModule implements ModuleInterface {

	public String title = "";
	public String url = "";

	/**
	 * Return number of users online as a module box
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception {

		String theString = "";
		String query = "";
		ResultSet rs = null;
		Statement st = null;
		int sessionCount = 0;
		String visitorString = "";

		query = "select sessions from sessions";
		st = conn.createStatement();
		rs = st.executeQuery(query);

		while (rs.next()) {
			sessionCount = rs.getInt(1);
		}

		rs.close();
		rs = null;
		st.close();
		st = null;

		if (sessionCount < 1)
			sessionCount = 1;

		if (sessionCount > 1) {
			visitorString = " visitors ";
		} else {
			visitorString = " visitor ";
		}

		try {
			theString = "<div class=\"vmodule\">\n"
					+ "<div class=\"vmoduleheading\">Who's Online?</div>\n"
					+ "<div class=\"vmodulebody\">We have " + sessionCount
					+ visitorString + "online.</div>";
			GenericTable myTable = new GenericTable("logged_in_users");
			myTable.setConn(conn);
			myTable.setSSelectWhat("distinct username");
			myTable.setSCustomWhere("and username is not null");
			XDisconnectedRowSet drs = new XDisconnectedRowSet();
			drs = myTable.getAllRecordsDisconnected();
			if (drs.next()) {
				drs.previous();
				theString += "<div class=\"vmodulebody\">"
						+ "<br /><strong>Logged in users</strong><br />" + "";
				while (drs.next()) {
					theString += "&nbsp;&nbsp;" + drs.getString("username")
							+ "<br />";
				}
				theString += "</div>";
			}
			theString += "\n</div>\n";
		} catch (Exception e) {
			Errors.addError("SessionCounter.getHtmlOutput:Exception:"
					+ e.toString());
		}
		return theString;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}