//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-11-16
//Revisions
//------------------------------------------------------------------------------
//$Log: AutoComplete.java,v $
//Revision 1.1.2.2.2.1.4.2  2007/03/05 17:11:58  tcs
//handle mixed cases
//
//Revision 1.1.2.2.2.1.4.1  2007/01/25 19:12:02  tcs
//Removed debugging info
//
//Revision 1.1.2.2.2.1  2005/12/08 13:52:20  tcs
//Added check for apostrophe in lookup (broke SQL)
//
//Revision 1.1.2.2  2005/11/18 12:18:36  tcs
//Initial code to generalize
//
//Revision 1.1.2.1  2005/11/16 19:00:04  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.jsremoting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.servlets.BaseAjaxServlet;

import com.verilion.database.DbBean;

/**
 * Servlet to handle ajax requests for autocomplete (ajax). Current (initial)
 * version is just for customer last name.
 * 
 * @author Trevor
 * 
 */
public class AutoComplete extends BaseAjaxServlet {

	private static final long serialVersionUID = -3475058107755007051L;

	public String getXmlContent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int action = 0;
		int ignoreCase = 0;
		int returnAsIs = 0;
		String theXML = "";

		try {
			ignoreCase = Integer.parseInt((String) request.getParameter("lc"));
		} catch (Exception e) {
			ignoreCase = 0;
		}

		try {
			returnAsIs = Integer.parseInt((String) request.getParameter("ai"));
		} catch (Exception e) {
			returnAsIs = 0;
		}

		try {
			action = Integer.parseInt((String) request.getParameter("action"));
		} catch (Exception e) {
			action = 1;
		}

		// Action = 1: autocomplete request
		// ---------------------------------------------------------------
		if (action == 1) {
			boolean okay = true;
			String name = "";
			String theField = "";
			String theTable = "";
			try {
				name = request.getParameter("searchname");
				theField = request.getParameter("field");
				theTable = request.getParameter("table");
				if ((name == null) || (theField == null) || (theTable == null)) {
					okay = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				okay = false;
			}
			if (okay) {
				PreparedStatement pst = null;
				Connection conn = DbBean.getDbConnection();
				theXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<ajax-response>\n" + "<response>\n";
				try {
					name = name.replaceAll("\"", "");
					name = name.replaceAll("'", "''");
					String query = "select distinct " + theField + " from "
							+ theTable + " where " + theField;
					if (ignoreCase > 0) {
						query += " ilike '";
					} else {
						query += " like '";
					}
					query += name.replaceAll("'", "''") + "%' " + "order by "
							+ theField + " limit 20";

					pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						theXML += "<item>\n" + "<name>" + rs.getString(1)
								+ "</name>\n" + "<value>";
						if (returnAsIs > 0) {
							theXML += rs.getString(1);
						} else {
							theXML += rs.getString(1).toLowerCase();
						}
						theXML += "</value>\n" + "</item>";
					}
					rs.close();
					rs = null;
					pst.close();
					pst = null;
					DbBean.closeDbConnection(conn);
					conn = null;

					theXML += "</response>\n</ajax-response>";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Action = 2: html frag from database request
			// ---------------------------------------------------------------
		} else if (action == 2) {
			String name = request.getParameter("searchname");
			String theField = request.getParameter("field");
			String theTable = request.getParameter("table");
			PreparedStatement pst = null;
			Connection conn = DbBean.getDbConnection();
			try {
				String query = "select  " + theField + " from " + theTable
						+ " where " + theField + " = '" + name + "' ";
				pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					theXML = rs.getString(1);
				}
				rs.close();
				rs = null;
				pst.close();
				pst = null;
				DbBean.closeDbConnection(conn);
				conn = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return theXML;
	}

}
