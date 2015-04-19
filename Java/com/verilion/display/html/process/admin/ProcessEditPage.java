//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-12
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditPage.java,v $
//Revision 1.2.2.8.4.5.2.2.4.1.2.2.2.2  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.2.2.8.4.5.2.2.4.1.2.2.2.1  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.2.2.8.4.5.2.2.4.1.2.2  2007/01/28 00:50:13  tcs
//Added PRESERVEMSG to save message
//
//Revision 1.2.2.8.4.5.2.2.4.1.2.1  2007/01/25 19:09:35  tcs
//Added support for page history
//
//Revision 1.2.2.8.4.5.2.2.4.1  2006/12/19 19:28:31  tcs
//Added jsptemplate stuff
//
//Revision 1.2.2.8.4.5.2.2  2005/08/22 16:47:02  tcs
//Fixed id
//
//Revision 1.2.2.8.4.5.2.1  2005/08/22 16:22:13  tcs
//Added id to page name input
//
//Revision 1.2.2.8.4.5  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.8.4.4  2005/08/19 15:24:21  tcs
//Modified to return to correct page on search screen
//
//Revision 1.2.2.8.4.3  2005/08/16 16:13:54  tcs
//Added active/inactive page stuff
//
//Revision 1.2.2.8.4.2  2005/08/16 11:53:46  tcs
//Set default template id to 1
//
//Revision 1.2.2.8.4.1  2005/08/10 01:36:13  tcs
//Fixed urls for new model
//
//Revision 1.2.2.8  2005/03/30 10:37:51  tcs
//Removed carriage returns from content
//
//Revision 1.2.2.7  2005/01/17 16:07:35  tcs
//Added generation of page invocation to addpage
//
//Revision 1.2.2.6  2005/01/12 15:57:48  tcs
//Check for non valid sgml character entities
//
//Revision 1.2.2.5  2005/01/12 14:24:14  tcs
//Handle single quotes properly in html content
//
//Revision 1.2.2.4  2005/01/10 14:13:06  tcs
//Improved cancelPage logic
//
//Revision 1.2.2.3  2005/01/10 12:48:37  tcs
//Completed logic to add a page
//
//Revision 1.2.2.2  2005/01/07 18:35:54  tcs
//Changes for embedded html editor move
//
//Revision 1.2.2.1  2004/12/17 14:38:36  tcs
//Modified to get page id as parameter instead of from hm
//
//Revision 1.2  2004/11/12 17:26:25  tcs
//In progress
//
//Revision 1.1  2004/11/12 15:54:29  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.CtAccessLevel;
import com.verilion.database.DBUtils;
import com.verilion.database.Documents;
import com.verilion.database.GenericTable;
import com.verilion.database.JspTemplate;
import com.verilion.database.PageTemplate;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit a page
 * 
 * <P>
 * November 12, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessEditPage extends ProcessPage {

	@SuppressWarnings("unchecked")
	public HTMLTemplateDb ProcessForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		ResultSet rs = null;
		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		String pageName = "";
		int templateId = 1;
		int accessLevelId = 1;
		int ct_language_id = 1;
		String theContents = "";
		String theInvocation = "";
		String page_active_yn = "n";
		int thePageId = 0;
		String thePageDetailTitle = "";
		String theJspTemplateMenu = "";
		PageTemplate myPages = new PageTemplate();
		myPages.setConn(conn);
		boolean isNewPage = false;
		int jspTemplateId = 0;
		String cancelPage = "";
		int page_history_id = 0;
		int page_group_id = 0;
		String browser_title = "";
		String meta_tags = "";

		cancelPage = (String) request.getParameter("cancelPage");
		if (session.getAttribute("d-4014121-p") != null) {
			cancelPage = cancelPage + "?d-4014121-p="
					+ session.getAttribute("d-4014121-p");
		}

		if (((String) request.getParameter("edit")).equals("false")) {
			try {

				// Get the id/language of the page we are going to edit
				thePageId = Integer.parseInt((String) request
						.getParameter("id"));

				try {
					ct_language_id = Integer.parseInt((String) request
							.getParameter("ct_language_id"));
				} catch (Exception e) {
					ct_language_id = 1;
				}

				// initialize some variables
				int myTemplateId = 1;
				int myAccessLevelId = 1;
				String myContents = "";
				String myPageName = "";
				String theMenu = "";
				String myInvocation = "";
				String myPageDetailTitle = "";
				XDisconnectedRowSet crs = new XDisconnectedRowSet();
				String theDocs = "";

				// Get the info for the page we are going to edit from db
				PageTemplate myPageTemplate = new PageTemplate();
				myPageTemplate.setConn(conn);
				myPageTemplate.setPage_id(thePageId);
				myPageTemplate.setCt_language_id(ct_language_id);
				JspTemplate myJspT = new JspTemplate();
				myJspT.setConn(conn);
				jspTemplateId = myJspT.returnJspTemplateIdForPageId(thePageId);

				drs = myPageTemplate.getPageForEdit();

				// Store the values from db in local variables for display on
				// form
				if (drs.next()) {
					myTemplateId = drs.getInt("template_id");
					myAccessLevelId = drs.getInt("ct_access_level_id");
					myContents = drs.getString("page_detail_contents");
					myPageName = drs.getString("page_name");
					myInvocation = drs.getString("page_invocation");
					myPageDetailTitle = drs.getString("page_detail_title");
					page_active_yn = drs.getString("page_active_yn");
					meta_tags = drs.getString("meta_tags");
					browser_title = drs.getString("browser_title");
				} else {
					// we are editing a new language for the first time, so get
					// info from
					// default language
					myPageTemplate.setConn(conn);
					myPageTemplate.setPage_id(thePageId);
					myPageTemplate.setCt_language_id(1);
					myJspT = new JspTemplate();
					myJspT.setConn(conn);
					jspTemplateId = myJspT
							.returnJspTemplateIdForPageId(thePageId);

					drs = myPageTemplate.getPageForEdit();
					if (drs.next()) {
						myTemplateId = drs.getInt("template_id");
						myAccessLevelId = drs.getInt("ct_access_level_id");
						myPageName = drs.getString("page_name");
						myInvocation = drs.getString("page_invocation");
						page_active_yn = drs.getString("page_active_yn");
						meta_tags = drs.getString("meta_tags");
						browser_title = drs.getString("browser_title");
					}
				}

				MasterTemplate.searchReplace("$TEMPLATEID$", myTemplateId + "");
				MasterTemplate.searchReplace("$CANCEL$", cancelPage);
				MasterTemplate.searchReplace("$BROWSERTITLE$", browser_title);
				MasterTemplate.searchReplace("$METADESC$", meta_tags);

				// create drop down list of languages
				GenericTable myGtable = new GenericTable("ct_languages");
				myGtable.setConn(conn);
				myGtable.setSSelectWhat(" ct_language_id, ct_language_name ");
				myGtable.setSCustomOrder(" order by ct_language_name ");
				rs = myGtable.getAllRecordsDisconnected();

				theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
						"ct_language_id", ct_language_id, rs, "ct_language_id",
						"ct_language_name", -1, "",
						"onchange='changeLanguage()'");
				rs.close();
				rs = null;
				MasterTemplate.searchReplace("$LANGDDLB$", theMenu);
				
				// create drop down list of page groups
				GenericTable myT = new GenericTable("page_group_detail");
				myT.setConn(conn);
				myT.setSSelectWhat("page_group_id");
				myT.setSCustomWhere(" and page_id = " + thePageId);
				XDisconnectedRowSet pgDrs = new XDisconnectedRowSet();
				pgDrs = myT.getAllRecordsDisconnected();
				while (pgDrs.next()) {
					page_group_id = pgDrs.getInt(1);
				}

				GenericTable myGrouptable = new GenericTable("page_group");
				myGrouptable.setConn(conn);
				myGrouptable.setSSelectWhat(" page_group_id, page_group_name ");
				myGrouptable.setSCustomOrder(" order by page_group_name ");
				rs = myGrouptable.getAllRecordsDisconnected();

				theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
						"page_group_id", page_group_id, rs, "page_group_id",
						"page_group_name", -1, "No group");
				rs.close();
				rs = null;
				MasterTemplate.searchReplace("$PAGEGROUP$", theMenu);

				// create drop down list of access levels
				CtAccessLevel myCtAccessLevel = new CtAccessLevel();
				myCtAccessLevel.setConn(conn);
				rs = myCtAccessLevel.getAllAccessLevelNamesIds();

				theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
						"access_level_id", myAccessLevelId, rs,
						"ct_access_level_id", "ct_access_level_name", -1, "");
				rs.close();
				rs = null;
				MasterTemplate.searchReplace("$DDLB2$", theMenu);

				if (page_active_yn.equalsIgnoreCase("y")) {
					MasterTemplate.searchReplace("$ACTIVEYNY$", " selected ");
					MasterTemplate.searchReplace("$ACTIVEYNN$", "");
				} else {
					MasterTemplate.searchReplace("$ACTIVEYNY$", "");
					MasterTemplate.searchReplace("$ACTIVEYNN$", " selected ");
				}

				// get the documents associated with this page
				theDocs = "";
				Documents myDocs = new Documents();
				myDocs.setConn(conn);
				myDocs.setPage_id(thePageId);
				crs = myDocs.getAllDocumentsForPageId();

				while (crs.next()) {
					theDocs += crs.getString("document_name");
					theDocs += " ";
				}

				if (theDocs.length() < 1) {
					theDocs = "No documents attached";
				}
				crs.close();
				crs = null;

				MasterTemplate.searchReplace("$DOCS$", theDocs);

				// Now fill in the rest of the form with the data particular to
				// this page.
				myContents = myContents.replaceAll("\"", "\\\"");
				myContents = myContents.replaceAll("\'", "&#8217;");
				myContents = myContents.replaceAll("\n", " ");
				myContents = myContents.replaceAll("\r", " ");
				MasterTemplate.searchReplace("$EDITPAGECONTENTS$", myContents);

				JspTemplate myJspTemplate = new JspTemplate();
				myJspTemplate.setConn(conn);
				crs = myJspTemplate.GetAllTemplates();
				theJspTemplateMenu = HTMLFormDropDownList
						.makeDropDownListSnippet("jsp_template_id",
								jspTemplateId, crs, "jsp_template_id",
								"template_name", -1, "");

				MasterTemplate.searchReplace("$JSPTEMPLATE$",
						theJspTemplateMenu);

				// do page history form (limited by language)
				GenericTable myGt = new GenericTable("page_history");
				myGt.setConn(conn);
				myGt.setSSelectWhat(" page_history_id, date_time ");
				myGt.setSCustomOrder(" order by date_time ");
				myGt.setSCustomWhere(" and ct_language_id = " + ct_language_id + " and page_id = " + thePageId);
				crs = myGt.getAllRecordsDisconnected();
				String thePTMenu = HTMLFormDropDownList
						.makeDropDownListSnippet("page_history_id", 0, crs,
								"page_history_id", "date_time", 0,
								"Do not revert");

				MasterTemplate.searchReplace("$VERSIONDDLB$", thePTMenu);

				if (thePageId != -1) {
					MasterTemplate
							.searchReplace(
									"$PAGENAME$",
									"<input class=\"inputbox\" type=\"text\" name=\"page_name\" id=\"page_name\" value=\""
											+ myPageName + "\" />");
				} else {
					MasterTemplate
							.searchReplace(
									"$PAGENAME$",
									"<input class=\"inputbox\" type=\"text\" name=\"page_name\" id=\"page_name\" value=\"\" />");
				}
				MasterTemplate.searchReplace("$PAGEID$", thePageId + "");
				MasterTemplate.searchReplace("$LANG$", ct_language_id + "");
				MasterTemplate
						.searchReplace(
								"$INVOCATION$",
								myInvocation
										+ "<input type=\"hidden\" name=\"page_invocation\" value=\""
										+ myInvocation + "\">");
				MasterTemplate.searchReplace("$PAGETITLE$", myPageDetailTitle);

			} catch (Exception e) {
				e.printStackTrace();
				Errors.addError("ProcessEditPage:ProcessForm:Exception:"
						+ e.toString());
			} finally {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			}
		} else {
			Boolean bRollingBack = false;
			DBUtils myDbUtil = new DBUtils();
			browser_title = (String) request.getParameter("browser_title");
			meta_tags = (String) request.getParameter("metadesc");
			pageName = (String) request.getParameter("page_name");
			pageName = pageName.replaceAll(" ", "");
			pageName = pageName.replaceAll("'", "");
			pageName = pageName.replaceAll("&", "");
			pageName = pageName.replaceAll("\"", "");
			templateId = Integer.parseInt((String) request
					.getParameter("template_id"));
			jspTemplateId = Integer.parseInt((String) request
					.getParameter("jsp_template_id"));
			accessLevelId = Integer.parseInt((String) request
					.getParameter("access_level_id"));
			thePageId = Integer.parseInt((String) request
					.getParameter("page_id"));
			page_group_id = Integer.parseInt((String) request.getParameter("page_group_id"));
			ct_language_id = Integer.parseInt((String) request
					.getParameter("ct_language_id"));
			theInvocation = (String) request.getParameter("page_invocation");
			thePageDetailTitle = (String) request
					.getParameter("page_detail_title");
			theContents = (String) request.getParameter("page_detail_contents");
			page_active_yn = (String) request.getParameter("page_active_yn");
			try {
				page_history_id = Integer.parseInt((String) request
						.getParameter("page_history_id"));
			} catch (Exception e) {
				page_history_id = 0;
			}

			// clean up the messy codes put in by the html editor, if any
			theContents = myDbUtil.replace(theContents, "src=\"..", "src=\"");
			theContents = myDbUtil.replace(theContents, "&#39;", "&#8217;");
			int charVal = 146; // the sgml character used for single quotes
			char theChar = (char) charVal;
			String sQuote = String.valueOf(theChar);
			theContents = myDbUtil.replace(theContents, sQuote, "&#8217;");

			// create pagetemplate object and set db conn
			PageTemplate myPageTemplate = new PageTemplate();
			myPageTemplate.setConn(conn);

			// Update the record in page
			myPageTemplate.setPage_name(pageName);
			myPageTemplate.setTemplate_id(templateId);
			myPageTemplate.setPage_access_level(accessLevelId);
			myPageTemplate.setPage_invocation(theInvocation);
			myPageTemplate.setPage_secure_yn("n");
			myPageTemplate.setPage_active_yn(page_active_yn);
			myPageTemplate.setBrowser_title(browser_title);
			myPageTemplate.setMeta_tags(meta_tags);
			
			// Update the records in page_group_detail
			GenericTable myT = new GenericTable();
			myT.setConn(conn);
			myT.setSTable("page_group_detail");
			myT.setSCustomWhere(" and page_id = " + thePageId);
			myT.deleteRecords();
			
			if (page_group_id > 0){
				myT.setSCustomWhere("");
				myT.addSet("page_group_id", page_group_id + "", "int");
				myT.addSet("page_id", thePageId + "", "int");
				myT.insertRecord();
				myT.clearUpdateVectors();
			}

			// get the jsp template name
			JspTemplate myJspT = new JspTemplate();
			myJspT.setConn(conn);
			String jspTemplateName = "";
			jspTemplateName = myJspT
					.getJspTemplateNameForJspTemplateId(jspTemplateId);

			if (thePageId != -1) {
				myPageTemplate.setPage_id(thePageId);
				myPageTemplate.setPage_invocation("/" + jspTemplateName
						+ "/jpage/1/p/" + pageName + "/content.do");
				myPageTemplate.updatePage();
			} else {
				isNewPage = true;
				myPageTemplate.setPage_invocation("/" + jspTemplateName
						+ "/jpage/1/p/" + pageName + "/content.do");
				thePageId = myPageTemplate.addPage();
			}

			// Update the record in jsp_template_page
			myJspT.setConn(conn);
			myJspT.setJsp_template_id(jspTemplateId);
			if (!isNewPage) {
				myJspT.updateJspTemplateIdForPageId(thePageId);
			} else {
				myJspT.addJspTemplateIdForPageId(thePageId);
			}

			if (page_history_id > 0) {
				// we are reverting to a previous page version
				// get page version we are reverting to
				XDisconnectedRowSet crs = new XDisconnectedRowSet();
				myPageTemplate.setPage_id(thePageId);
				crs = myPageTemplate.returnOldPageVersion(page_history_id);
				while (crs.next()) {
					theContents = crs.getString(1);
				}
				crs.close();
				crs = null;
				bRollingBack = true;
			}

			if ((!isNewPage) && (!bRollingBack)) {
				// Put an entry into page_history
				// first get the old page contents
				String oldContents = "";
				myPageTemplate.setPage_id(thePageId);
				myPageTemplate.setCt_language_id(ct_language_id);
				XDisconnectedRowSet crs = new XDisconnectedRowSet();
				crs = myPageTemplate.getPageForEdit();
				while (crs.next()) {
					oldContents = crs.getString("page_detail_contents");
				}
				crs.close();
				crs = null;
				if (!oldContents.equals(theContents)) {
					GenericTable hTable = new GenericTable("page_history");
					hTable.setConn(conn);
					hTable.addSet("page_id", thePageId + "", "int");
					hTable.addSet("page_contents", oldContents, "string");
					hTable.addSet("ct_language_id", ct_language_id + "", "int");
					hTable.insertRecord();
				}
			}

			// Update the record in page_detail
			myPageTemplate.setPage_id(thePageId);
			myPageTemplate.setPage_detail_contents(theContents);
			myPageTemplate.setCt_language_id(ct_language_id);
			myPageTemplate.setPage_detail_title(thePageDetailTitle);

			// need to see if we are inserting into page_detail, or updating page_detail
			// if there is an entry in page_detail for the language_id, we are updating
			GenericTable myGt = new GenericTable("page_detail");
			myGt.setConn(conn);
			myGt.setSSelectWhat("page_detail_id");
			myGt.setSCustomWhere(" and ct_language_id = " + ct_language_id + " and page_id = " + thePageId);
			rs = myGt.getAllRecordsDisconnected();
			if (rs.next()){
				isNewPage = false;
			} else {
				isNewPage = true;
			}
			rs.close();
			rs = null;
			
			if (isNewPage) {
				myPageTemplate.addPageDetail();
			} else {
				myPageTemplate.updatePageDetail();

			}

			// Generate completion message
			session.setAttribute("Error", "Page update successful");
			hm.put("PRESERVEMSG", "TRUE");
			response.sendRedirect((String) request.getParameter("cancelPage"));

		}
		return MasterTemplate;
	}
}