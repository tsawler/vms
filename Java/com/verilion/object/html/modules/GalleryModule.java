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

import com.verilion.object.Errors;

/**
 * Simple gallery module
 * 
 * @author tsawler
 * 
 */
public class GalleryModule implements ModuleInterface {

	public String title = "";
	public String url = "";

	/**
	 * Return a single image from gallery as module item
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

		if (session.getAttribute("user") == null) {
			theString = "";
		} else {
			try {
				theString = "<div class=\"vmodule\">\n"
						+ "<div class=\"vmoduleheading\">Gallery Image</div>\n"
						+ "<div class=\"vmodulebody\">";

				query = "select img, username from gallery_detail where img like '%.jpg' order by random() limit 1";
				st = conn.createStatement();
				rs = st.executeQuery(query);

				if (rs.next()) {
					theString += "<div align=\"center\"><br />"
							+ "<a href=\"/membergallery/jpage/1/p/MemberGallery/content.do?id=1&username="
							+ rs.getString("username")
							+ "\">"
							+ "<img border=\"0\" src=\"/gallery/1/"
							+ rs.getString("img").replaceAll(".jpg",
									"_thumb.jpg") + "\"></a><br />By: "
							+ rs.getString("username") + "</div>";
				} else {
					theString += "No active images";
				}
				rs.close();
				rs = null;
				st.close();
				st = null;

				theString += "</div>";

				theString += "\n</div>\n";
			} catch (Exception e) {
				Errors.addError("GalleryModule.getHtmlOutput:Exception:"
						+ e.toString());
			}
		}
		return theString;
	}
}