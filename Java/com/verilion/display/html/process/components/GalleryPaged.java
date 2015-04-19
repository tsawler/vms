//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2009-04-16
//Revisions
//------------------------------------------------------------------------------
//$Log: GalleryPaged.java,v $
//Revision 1.1.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.components;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

public class GalleryPaged extends ProcessPage {

	public GalleryPaged() {
	}

	@SuppressWarnings("unchecked")
	public HTMLTemplateDb ProcessForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {
		int id = 0;
		GenericTable myTableObject = new GenericTable(
				" gallery_detail gd left join gallery g on (g.gallery_id = gd.gallery_id) ");
		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		int limit = 25;
		int offset = 0;
		boolean foundsome = false;

		try {
			if (request.getParameter("id") != null)
				id = Integer.parseInt(request.getParameter("id"));
			if (hm.get("id") != null)
				id = Integer.parseInt((String) hm.get("id"));
			myTableObject.setConn(conn);
			myTableObject.setSSelectWhat(" gd.*, g.imageset ");
			myTableObject.setSCustomWhere((new StringBuilder(
					" and gd.gallery_id = ")).append(id).toString());
			if (hm.get("lim") != null)
				limit = Integer.parseInt((String) hm.get("lim"));
			else
				limit = 25;
			if (hm.get("off") != null)
				offset = Integer.parseInt((String) hm.get("off"));
			else
				offset = 0;
			if (offset < -25)
				offset = -25;
			String tmp = "";
			if (limit > 0)
				tmp = (new StringBuilder(String.valueOf(tmp))).append("limit ")
						.append(limit).append(" ").toString();
			if (offset > 0)
				tmp = (new StringBuilder(String.valueOf(tmp))).append(
						" offset ").append(offset).append(" ").toString();
			if (tmp.length() > 0)
				myTableObject.setSCustomLimit(tmp);
			myTableObject.setSCustomOrder("order by gallery_detail_id");
			drs = myTableObject.getAllRecordsDisconnected();
			if (drs.next()) {
				foundsome = true;
				drs.previous();
				MasterTemplate.appendString((new StringBuilder(
						"<div align=\"center\"><a href=\"/")).append(
						hm.get("page_name")).append("/jpage/1/p/").append(
						hm.get("p")).append("/a/showgallery/lim/")
						.append(limit).append("/off/").append(offset - 25)
						.append("/id/").append(id).append("/content.do?id=")
						.append(id).append("\">Previous Page</a>&nbsp;&nbsp;")
						.append("<a href=\"/").append(hm.get("page_name"))
						.append("/jpage/1/p/").append(hm.get("p")).append(
								"/a/showgallery/lim/").append(limit).append(
								"/off/").append(offset + 25).append("/id/")
						.append(id).append("/content.do?id=").append(id)
						.append("\">Next Page</a><br /><br /></div>")
						.toString());
				String tmpString;
				for (; drs.next(); MasterTemplate
						.appendString((new StringBuilder(
								"<div class=\"thumbwrapper\"><a href=\"/gallery/"))
								.append(drs.getInt("gallery_id")).append("/")
								.append(drs.getString("img")).append(
										"\" rel=\"gb_imageset[").append(
										drs.getString("imageset")).append(
										"]\" title=\"").append(
										drs.getString("title")).append("\">")
								.append("<img alt=\"\" src=\"/gallery/")
								.append(drs.getInt("gallery_id")).append("/")
								.append(tmpString).append(
										"_thumb.jpg\" /> </a></div>\n")
								.toString())) {
					String img = drs.getString("img");
					tmpString = img.substring(0, img.length() - 4);
				}

			}
			if (!foundsome)
				MasterTemplate.appendString("No images to display");
		} catch (Exception e) {
			e.printStackTrace();
			Errors
					.addError((new StringBuilder(
							"com.verilion.display.html.process.components.Gallery:ProcessForm:Exception:"))
							.append(e.toString()).toString());
		}
		return MasterTemplate;
	}
}
