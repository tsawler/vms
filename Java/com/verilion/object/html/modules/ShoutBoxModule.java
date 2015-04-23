//------------------------------------------------------------------------------
//Copyright (c) 2004-2009 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2009-04-30
//Revisions
//------------------------------------------------------------------------------
//$Log: ShoutBoxModule.java,v $
//Revision 1.1.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.verilion.object.Errors;

/**
 * Shoutbx module
 * 
 * @author tsawler
 * 
 */
public class ShoutBoxModule implements ModuleInterface {

	/**
	 * displays a shout as a module
	 * 
	 * @return String - box as html
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception {

		String theModuleText = "";

		try {

			if (session.getAttribute("user") == null) {
				theModuleText = "";
			} else {
				String theUrl = "";
				String user = "";
				HashMap hm = (HashMap) session.getAttribute("pHm");
				user = (String) session.getAttribute("username");
				theUrl = (String) hm.get("original_url");
				theModuleText = "<div class=\"vmodule\">\n"
						+ "<form method=\"post\" action=\"/shoutbox.jsp\">"
						+ "<div class=\"vmoduleheading\">Shoutbox!</div>\n"
						+ "<div class=\"vmodulebody\" style=\"text-align: center\">\n"
						+ "<strong>"
						+ "Most recent shout:</strong><br /><br />";
				ShoutBoxModuleObject c = new ShoutBoxModuleObject();
				String theShout = c.getMostRecentShout();
				if (theShout.length() == 0) {
					theModuleText += "No shouts...<br /><br />";
				} else {
					theModuleText += theShout + "<br /><br />";
				}
				theModuleText += "<input type=\"text\" size=\"12\" name=\"shout\" />"
						+ "<input type=\"submit\" value=\"Shout!\">"
						+ "\n<input type=\"hidden\" name=\"url\" value=\""
						+ theUrl
						+ "\" />"
						+ "\n"
						+ "<input type=\"hidden\" name=\"user\" value=\""
						+ user + "\" />";
				theModuleText += "</div></form></div>";
			}
		} catch (Exception e) {
			Errors.addError("LoginBoxesModule.getHtmlOutput:Exception:"
					+ e.toString());
		}
		return theModuleText;
	}
}