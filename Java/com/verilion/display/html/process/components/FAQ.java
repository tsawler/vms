//------------------------------------------------------------------------------
//Copyright (c) 2004-2008 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2008-11-12
//Revisions
//------------------------------------------------------------------------------
//$Log: FAQ.java,v $
//Revision 1.1.2.2  2008/11/12 11:52:49  tcs
//Complete and functional
//
//Revision 1.1.2.1  2008/11/12 11:16:46  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.components;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Display Web links for end users
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class FAQ extends ProcessPage {

	@SuppressWarnings("unchecked")
	public HTMLTemplateDb BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		GenericTable myTableObject = new GenericTable(" faq_categories ");
		XDisconnectedRowSet drs = new XDisconnectedRowSet();

		try {

			MasterTemplate
					.appendString("<script type=\"text/javascript\">\n"
							+ "function showanswer(id) {\n"
							+ "var wheretogo = \"/faqhelper.jsp?id=\" + id + \"&a=2\";\n"
							+ "Validator.getIncludedFile(wheretogo, function(data) {\n"
							+ "dwr.util.setValue(\"questiondiv\", data, { escapeHtml:false });\n"
							+ "});\n"
							+ "}\n"
							+ "function showquestions() {\n"
							+ "var wheretogo = \"/faqhelper.jsp?id=\" + dwr.util.getValue(\"faq_cat_id\") + \"&a=1\";\n"
							+ "Validator.getIncludedFile(wheretogo, function(data) {\n"
							+ "dwr.util.setValue(\"questiondiv\", data, { escapeHtml:false });\n"
							+ "});\n"
							+ "dwr.util.setValue(\"resultdiv\", \"\");\n"
							+ "}\n" + "</script>\n");
			MasterTemplate.appendString("<fieldset><legend>FAQs</legend>");
			myTableObject.setConn(conn);
			myTableObject.setSSelectWhat(" * ");
			myTableObject.setSCustomOrder(" order by faq_categories_name");
			myTableObject.setSCustomWhere(" and active_yn = 'y' ");

			drs = myTableObject.getAllRecordsDisconnected();
			MasterTemplate.appendString("<div style=\"text-align: center\">\n");
			String theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
					"faq_cat_id", 0, "/public/jpage/1/p/FAQ/a/faqs/content.do",
					drs, "faq_categories_id", "faq_categories_name", "Go",
					"onchange=\"showquestions()\"", "faq_cat_id", "0",
					"Choose a category...");

			MasterTemplate.appendString("Show FAQs for " + theMenu
					+ "<div>&nbsp;</div>");
			MasterTemplate.appendString("</div>\n</fieldset>");
			MasterTemplate.appendString("<div id=\"questiondiv\"></div>\n"
					+ "<div>&nbsp;</div>"
					+ "<div id=\"resultdiv\"></div>");

		} catch (Exception e) {
			e.printStackTrace();
			Errors
					.addError("com.verilion.display.html.process.components.FAQ:BuildPage:Exception:"
							+ e.toString());
		}

		return MasterTemplate;
	}
}