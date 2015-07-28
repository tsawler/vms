//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-1
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessPostStory.java,v $
//Revision 1.1.2.8.2.2  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.8.2.1  2007/03/30 17:22:02  tcs
//*** empty log message ***
//
//Revision 1.1.2.8  2007/03/19 00:46:56  tcs
//Cosmetic changes
//
//Revision 1.1.2.7  2007/03/15 17:48:18  tcs
//Cosmetic changes
//
//Revision 1.1.2.6  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.5  2007/01/28 00:50:49  tcs
//Minor edits
//
//Revision 1.1.2.4  2007/01/26 02:16:17  tcs
//Cosmetic changes
//
//Revision 1.1.2.3  2007/01/22 19:25:33  tcs
//Cleaned up code & imports
//
//Revision 1.1.2.2  2007/01/22 19:21:03  tcs
//removed refs to nl.captcha
//
//Revision 1.1.2.1  2007/01/22 16:48:48  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.Customer;
import com.verilion.database.Story;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;
import com.verilion.utility.TextUtils;

/**
 * Post a story
 * <P>
 * July 17 2007
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessPostStory extends ProcessPage {

	@Override
	public HTMLTemplateDb BuildPage(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Connection conn, HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		String theMenu = "";

		try {
			int customer_access_level = 0;
			try {
				customer_access_level = Integer.parseInt((String) session.getAttribute("customer_access_level"));
			} catch (Exception e) {
				customer_access_level = 0;
			}

			if (customer_access_level < 2) {
				MasterTemplate.replaceFullValue("You must be a registered member and logged in to post a message.<br />"
						+ "Not a member yet? <a href=\"/public/jpage/1/p/JoinSite/a/joinpmc/content.do\">Join</a> now!");
			} else {
				ResultSet crs = null;
				Statement cst = null;
				cst = conn.createStatement();

				crs = cst.executeQuery("select * from pmc_story_category order by category_text");

				theMenu = HTMLFormDropDownList.makeDropDownListSnippet("story_cat_id", 0, crs, "story_category_id",
						"category_text", 0, "Select a topic");

				crs.close();
				crs = null;
				cst.close();
				cst = null;
				// put the formatted drop down list box in HTML object
				MasterTemplate.searchReplace("$DDLB$", theMenu);
			}

			if (request.getParameter("story_cat_id") != null) {
				// this is a rejected form
				MasterTemplate.searchReplace("name=\"subject\"",
						"name=\"subject\" value=\"" + request.getParameter("subject") + "\"");
				MasterTemplate.searchReplace("</textarea>", request.getParameter("comment") + "</textarea>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.html.ProcessPage:BuildPage:Exception:" + e.toString());
		}
		return MasterTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HTMLTemplateDb ProcessForm(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Connection conn, HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		boolean isValid = true;

		isValid = this.ValidateData(request, response, session, conn, MasterTemplate, hm);

		String theStory = "";
		String theSubject = "";
		String meta_title = "";
		String meta_tags = "";
		int theCategory = 0;

		if (isValid) {

			try {
				int customer_access_level = 0;
				try {
					customer_access_level = Integer.parseInt((String) session.getAttribute("customer_access_level"));
				} catch (Exception e) {
					customer_access_level = 0;
				}
				if (customer_access_level > 1) {

					Story myStory = new Story();
					myStory.setConn(conn);
					Customer myCustomer = new Customer();
					myCustomer.setConn(conn);

					try {
						String tmpString = "";
						tmpString = request.getParameter("subject");
						theSubject = tmpString.replaceAll("\\<.*?>", "");
					} catch (Exception e) {
						theSubject = "untitled";
					}

					try {
						String tmpString = "";
						tmpString = request.getParameter("comment");
						theStory = tmpString.replaceAll("\\<.*?>", "");
					} catch (Exception e) {
						theStory = "";
					}

					if (theStory.length() > 150) {
						meta_tags = theStory.substring(0, 150) + "...";
					} else {
						meta_tags = theStory;
					}

					if (theSubject.length() > 50) {
						meta_title = theSubject.substring(0, 50) + "...";
					} else {
						meta_title = theSubject;

					}
					meta_title += " - Pacemaker Club";

					theCategory = Integer.parseInt((String) request.getParameter("story_cat_id"));

					myStory.setStory_title(theSubject);
					myStory.setStory_active_yn("y");
					myStory.setStory_text(TextUtils.NewLinesToBr(theStory));
					int id = Integer.parseInt((String) session.getAttribute("customer_id"));
					String uname = myCustomer.returnUsernameForCustomerId(id);
					myStory.setStory_author_id(id);
					myStory.setStory_author(uname);
					myStory.setStory_topic_id(theCategory);
					myStory.setMeta_tags(meta_tags.replaceAll("'", "''"));
					myStory.setBrowser_title(meta_title.replaceAll("'", "''"));
					myStory.addStory();

					session.setAttribute("Error", "Your message has been posted.");
					hm.put("PRESERVEMSG", "TRUE");
					response.sendRedirect("/public/jpage/1/p/Home/a/storypage/content.do");

				}

			} catch (Exception e) {

			}
		} else {
			this.BuildPage(request, response, session, conn, MasterTemplate, hm);
		}
		return super.ProcessForm(request, response, session, conn, MasterTemplate, hm);
	}

	@Override
	public HTMLTemplateDb ProcessRejectedForm(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Connection conn, HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {
		// TODO Auto-generated method stub
		return super.ProcessRejectedForm(request, response, session, conn, MasterTemplate, hm);
	}

	@SuppressWarnings("unchecked")
	public boolean ValidateData(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Connection conn, HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		String subject = "";
		String story = "";
		boolean isValid = true;
		String errormessage = "";

		story = request.getParameter("comment");
		subject = request.getParameter("subject");
		
		if (story.length() < 1) {
			errormessage += "You must enter a message! ";
			isValid = false;
		}

		if (subject.length() < 1) {
			isValid = false;
			errormessage += "You must enter a title for your message!";
		}

		if (errormessage.length() > 0) {
			session.setAttribute("Error", errormessage);
		}

		return isValid;
	}

}