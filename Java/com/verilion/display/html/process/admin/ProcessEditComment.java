//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-11
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditComment.java,v $
//Revision 1.1.2.1.2.1  2007/01/25 01:19:40  tcs
//Removed debugging info
//
//Revision 1.1.2.1  2007/01/12 19:27:57  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Comment;
import com.verilion.database.DBUtils;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Edit/create a comment from admin tool
 * 
 * <P>
 * January 11, 2007
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessEditComment extends ProcessPage {

	public HTMLTemplateDb ProcessForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		int comment_id = 0;
		String subject = "";
		String story_comment = "";
		String username = "";
		String comment_active_yn = "";
		Comment myComment = new Comment();
		XDisconnectedRowSet rs = new XDisconnectedRowSet();
		XDisconnectedRowSet crs = new XDisconnectedRowSet();
		XDisconnectedRowSet catrs = new XDisconnectedRowSet();
		boolean okay = true;
		String theError = "";

		if (((String) request.getParameter("edit")).equals("false")) {

			try {
				// see if we have an id passed to us. If we do, this is not
				// our first time to this form
				if (request.getParameter("id") != null) {
					comment_id = Integer.parseInt((String) request
							.getParameter("id"));
				}

				if (comment_id > 0) {
					// get news item information
					myComment.setConn(conn);
					myComment.setComment_id(comment_id);
					rs = myComment.getCommentById();

					if (rs.next()) {
						// build ddlb for categories
						subject = rs.getString("subject");
						comment_active_yn = rs.getString("comment_active_yn");
						username = rs.getString("username");
						story_comment = rs.getString("story_comment");

						MasterTemplate.searchReplace("$SUBJECT$", subject);
						MasterTemplate.searchReplace("$AUTHOR$", username);

						// put published status on screen

						if (comment_active_yn.equals("y")) {
							MasterTemplate.searchReplace("$SELECTYNY$",
									"selected");
							MasterTemplate.searchReplace("$SELECTYNN$", "");
						} else {
							MasterTemplate.searchReplace("$SELECTYNY$", "");
							MasterTemplate.searchReplace("$SELECTYNN$",
									"selected");
						}

						// put description on screen

						story_comment = story_comment.replaceAll("\"", "\\\"");
						story_comment = story_comment.replaceAll("\'",
								"&#8217;");
						story_comment = story_comment.replaceAll("\n", " ");
						story_comment = story_comment.replaceAll("\r", " ");
						MasterTemplate
								.searchReplace("$COMMENT$", story_comment);

						String cancelPage = (String) request
								.getParameter("cancelPage");
						if (session.getAttribute("d-3997966-p") != null) {
							cancelPage += "?d-3997966-p="
									+ session.getAttribute("d-1345801-p");
						}

						MasterTemplate.searchReplace("$CANCEL$", cancelPage);
						MasterTemplate.searchReplace("$ID$", comment_id + "");
					}
				} else {
					// we have no news item, so put blanks everywhere
					MasterTemplate.searchReplace("$SUBJECT$", "");
					MasterTemplate.searchReplace("$SELECTYNY$", "");
					MasterTemplate.searchReplace("$SELECTYNN$", "no");
					MasterTemplate.searchReplace("$AUTHOR$", "");
					MasterTemplate.searchReplace("$COMMENT$", "");
					MasterTemplate.searchReplace("$ID$", "0");
					MasterTemplate.searchReplace("$CANCEL$", (String) request
							.getParameter("cancelPage"));

				}

				// put story id in hidden field
				MasterTemplate.searchReplace("$ID", comment_id + "");

				crs.close();
				crs = null;
				catrs.close();
				catrs = null;

			} catch (Exception e) {
				e.printStackTrace();
				Errors.addError("ProcessEditComment:ProcessForm:Exception:"
						+ e.toString());
			} finally {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (crs != null) {
					crs.close();
					crs = null;
				}
				if (catrs != null) {
					catrs.close();
					catrs = null;
				}
			}
		} else {
			try {
				// get parameters
				comment_id = Integer.parseInt((String) request
						.getParameter("id"));
				if (((String) request.getParameter("subject") != null)
						&& (((String) request.getParameter("subject")).length() > 0)) {
					subject = (String) request.getParameter("subject");
				} else {
					theError = "You must supply a subject!";
					okay = false;
				}
				username = (String) request.getParameter("username");
				if (username.length() < 1)
					username = "admin";
				story_comment = (String) request.getParameter("story");
				// clean up the messy codes put in by the html editor, if any
				DBUtils myDbUtil = new DBUtils();
				story_comment = myDbUtil.replace(story_comment, "src=\"..",
						"src=\"");
				story_comment = myDbUtil.replace(story_comment, "&#39;",
						"&#8217;");
				int charVal = 146; // the sgml character used for single quotes
				char theChar = (char) charVal;
				String sQuote = String.valueOf(theChar);
				story_comment = myDbUtil.replace(story_comment, sQuote,
						"&#8217;");
				story_comment = story_comment.replaceAll("\"", "\\\"");
				story_comment = story_comment.replaceAll("\'", "&#8217;");
				story_comment = story_comment.replaceAll("\n", " ");
				story_comment = story_comment.replaceAll("\r", " ");

				comment_active_yn = (String) request.getParameter("active_yn");

				if (okay) {

					myComment.setConn(conn);
					myComment.setSubject(subject);
					myComment.setComment(story_comment);
					myComment.setComment_active_yn(comment_active_yn);

					if (comment_id > 0) {
						myComment.setComment_id(comment_id);
						myComment.updateComment();
					} else {
						comment_id = myComment.addComment();
					}
				}

				if (okay) {
					session.setAttribute("Error", "update successful");
					response.sendRedirect((String) request
							.getParameter("cancelPage"));
				} else {
					session.setAttribute("Error", theError);
					response.sendRedirect((String) request
							.getParameter("cancelPage"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				Errors.addError("ProcessEditComment:ProcessForm:Exception:"
						+ e.toString());
			}

		}
		return MasterTemplate;
	}
}