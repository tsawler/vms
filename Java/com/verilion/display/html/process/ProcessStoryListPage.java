//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-12
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessStoryListPage.java,v $
//Revision 1.1.2.1.2.13.2.3  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.1.2.13.2.2  2007/04/04 16:54:09  tcs
//Added method=post to comment form. Duh.
//
//Revision 1.1.2.1.2.13.2.1  2007/03/30 17:22:02  tcs
//*** empty log message ***
//
//Revision 1.1.2.1.2.13  2007/03/19 00:46:56  tcs
//Cosmetic changes
//
//Revision 1.1.2.1.2.12  2007/03/15 17:47:56  tcs
//Cosmetic changes
//
//Revision 1.1.2.1.2.11  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.1.2.10  2007/02/25 01:52:05  tcs
//Added support for private messaging; removed tables from individual article view
//
//Revision 1.1.2.1.2.9  2007/01/30 19:48:05  tcs
//made storytable an id, not class, in generated css tag
//
//Revision 1.1.2.1.2.8  2007/01/28 00:50:30  tcs
//minor edit
//
//Revision 1.1.2.1.2.7  2007/01/26 19:04:40  tcs
//Removed some hardcoded style info
//
//Revision 1.1.2.1.2.6  2007/01/26 02:16:17  tcs
//Cosmetic changes
//
//Revision 1.1.2.1.2.5  2007/01/25 01:19:07  tcs
//Removed debugging info
//
//Revision 1.1.2.1.2.4  2007/01/22 19:25:33  tcs
//Cleaned up code & imports
//
//Revision 1.1.2.1.2.3  2007/01/22 19:21:03  tcs
//removed refs to nl.captcha
//
//Revision 1.1.2.1.2.2  2007/01/22 19:18:33  tcs
//Complete and working
//
//Revision 1.1.2.1.2.1  2007/01/16 16:30:20  tcs
//Changed generated HTML
//
//Revision 1.1.2.1  2007/01/12 19:28:48  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Comment;
import com.verilion.database.Customer;
import com.verilion.database.GenericTable;
import com.verilion.database.Story;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;
import com.verilion.utility.TextUtils;

/**
 * List "n" most recent stories on a single page, or single story if we have an
 * id
 * 
 * <P>
 * January 12th 2007
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessStoryListPage extends ProcessPage {

	public HTMLTemplateDb BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		int theItem = 0;
		Story myStories = new Story();
		myStories.setConn(conn);
		ResultSet rs = null;
		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		int numberOfStories = 10;
		String theHtml = "";
		String commentBox = "";
		int story_category_id = 0;
		int customer_access_level = 0;

		try {
			// get our parameter for the story item
			try {
				theItem = Integer.parseInt((String) hm.get("sid"));
			} catch (Exception e) {
				// we are doing a list of stories, and not a single one
				theItem = 0;
			}
			// see if we are limiting to a custom number of stories
			try {
				numberOfStories = Integer.parseInt((String) hm.get("n"));
			} catch (Exception e) {
				numberOfStories = 10;
			}
			// figure out our access level
			try {
				customer_access_level = Integer.parseInt((String) session
						.getAttribute("customer_access_level"));
			} catch (Exception e) {
				customer_access_level = 0;
			}

			try {
				// if the url contains /scat/n/ where n is an int, then we
				// are looking at a single category and have to paginate, etc.
				story_category_id = Integer.parseInt((String) hm.get("scat"));
			} catch (Exception e) {
				story_category_id = 0;
			}

			if (theItem == 0) {
				if (story_category_id > 0) {
					String catName = "";
					catName = myStories
							.returnStoryCategoryNameById(story_category_id);

					int l = 0;
					int o = 0;
					try {
						l = Integer.parseInt((String) hm.get("l"));
						o = Integer.parseInt((String) hm.get("o"));
					} catch (Exception ex) {
						l = 20;
						o = 0;
					}
					myStories.setSCustomWhere(" limit " + l + " offset " + o);
					myStories.setStory_topic_id(story_category_id);
					drs = myStories.getAllActiveStoriesItemsForCategory();

					theHtml = "<h1>Messages in " + catName
							+ " Forum</h1><br /><br /><div id=\"storytable\">";
					while (drs.next()) {
						String theStory = drs.getString("story");
						if (theStory.length() > 750) {
							theStory = theStory.substring(0, 749)
									+ "...<br /><div style=\"text-align: right\">"
									+ "<a href=\"/public/jpage/1/p/story/a/storypage/sid/"
									+ drs.getInt("story_id")
									+ "/content.do\">click to read more...</a></div>";
						}
						theHtml += "<div class=\"storyheader\">"
								+ "<a style=\"font-weight: bold;\" class=\"storyheader\" href=\"/public/jpage/1/p/story/a/storypage/sid/"
								+ drs.getInt("story_id")
								+ "/content.do\" "
								+ "title=\"Click to read message and comments\">"
								+ drs.getString("title") + "</a>" + "</div>"
								+ "<div class=\"subheading\">" + "Posted by ";
						if (customer_access_level > 1) {
							theHtml += "<a href=\"/members/jpage/1/p/MemberDirectory/content.do?uname="
									+ drs.getString("posted_by") + "\">";
						}
						theHtml += drs.getString("posted_by");
						if (customer_access_level > 1) {
							theHtml += "</a>";
						}
						theHtml += " on "
								+ drs.getTimestamp("datetime").toString()
										.substring(0, 16) + ". "
								+ drs.getInt("number_comments") + " comments."
								+ "&nbsp;" + drs.getInt("counter")
								+ " reads</div>" + "<div>&nbsp;</div><div>"
								+ theStory + "<br /><br />" + "</div>";
					}

					drs.close();
					drs = null;
					o = o + 20;

					theHtml += "</div>"
							+ "<div style=\"text-align: right;\"><a href=\"/public/jpage/1/p/storycat/a/storypage/scat/"
							+ story_category_id + "/l/" + l + "/o/" + o
							+ "/content.do\"><strong>>>> Next " + l
							+ " messages</strong></a></div>";
					MasterTemplate.appendString(theHtml);

				} else {
					// showing most recent stories
					if (((String) hm.get("p")).equals("Home")){
					
					} else {
						drs = myStories.getNMostRecentStories(numberOfStories);
						theHtml = "\n<div id=\"storytable\">\n";
						while (drs.next()) {
							String theStory = drs.getString("story");
	
							if (theStory.length() > 750) {
								theStory = theStory.substring(0, 749)
										+ "...<br />\n<div style=\"text-align: right\">\n"
										+ "<a href=\"/public/jpage/1/p/story/a/storypage/sid/"
										+ drs.getInt("story_id")
										+ "/content.do\">click to read more...</a>\n</div>\n";
							}
							theHtml += "<div class=\"storyheader\">\n"
									+ "<a class=\"storyheader\" style=\"font-weight: bold;\" href=\"/public/jpage/1/p/story/a/storypage/sid/"
									+ drs.getInt("story_id")
									+ "/content.do\" "
									+ "title=\"Click to read message and comments\">"
									+ drs.getString("title") + "</a>\n"
									+ "</div>\n" + "<div class=\"subheading\">\n"
									+ "Posted by ";
							if (customer_access_level > 1) {
								theHtml += "<a href=\"/members/jpage/1/p/MemberDirectory/content.do?uname="
										+ drs.getString("posted_by") + "\">";
							}
							theHtml += drs.getString("posted_by");
							if (customer_access_level > 1) {
								theHtml += "</a>";
							}
							theHtml += " on "
									+ drs.getTimestamp("datetime").toString()
											.substring(0, 16) + ". "
									+ drs.getInt("number_comments") + " comments."
									+ "&nbsp;" + drs.getInt("counter") + " reads"
									+ "\n</div>\n" + "<div>&nbsp;</div><div>\n"
									+ theStory + "<br /><br />\n" + "</div>\n";
						}
						drs.close();
						drs = null;
	
						theHtml += "</div>\n";
						MasterTemplate.appendString(theHtml);
					}
				}
			} else {
				// showing individual story & comments
				myStories.setStory_id(theItem);
				myStories.updateNumberReadsForStoryId(theItem);

				drs = myStories.getStoryById();

				while (drs.next()) {
					theHtml += "<div class=\"storyheader\">"
							+ drs.getString("title") + "</div>\n"
							+ "<div class=\"subheading\">" + "Posted by ";
					if (customer_access_level > 1) {
						theHtml += "<a href=\"/members/jpage/1/p/MemberDirectory/content.do?uname="
								+ drs.getString("posted_by") + "\">";
					}
					theHtml += drs.getString("posted_by");
					if (customer_access_level > 1) {
						theHtml += "</a>";
					}
					theHtml += " on "
							+ drs.getTimestamp("datetime").toString()
									.substring(0, 16) + "</div>\n"
							+ "<div>&nbsp;</div><div>" + drs.getString("story")
							+ "</div>" + "<div>&nbsp;</div>" + "<h2>"
							+ drs.getInt("number_comments") + " comments</h2>";
					Comment myComments = new Comment();
					myComments.setConn(conn);
					myComments.setStory_id(theItem);
					XDisconnectedRowSet crs = new XDisconnectedRowSet();
					crs = myComments.getAllCommentsForStory();
					while (crs.next()) {
						theHtml += "<div>&nbsp;</div><h2>"
								+ crs.getString("subject") + "</h2>"
								+ "<div class=\"subheading\">"
								+ "Comment posted by ";
						if (customer_access_level > 1) {
							theHtml += "<a href=\"/members/jpage/1/p/MemberDirectory/content.do?uname="
									+ crs.getString("username")
									+ "\">"
									+ crs.getString("username") + "</a>";
						} else {
							theHtml += crs.getString("username");
						}
						theHtml += " on "
								+ crs.getTimestamp("datetime").toString()
										.substring(0, 16) + ". </div>"
								+ "<div>" + crs.getString("story_comment")
								+ "</div>";
					}
					crs.close();
					crs = null;
				}
				drs.close();
				drs = null;

				theHtml += "<div>&nbsp;</div>";

				if (customer_access_level > 1) {
					commentBox = "<table style=\"width: 100%\">"
							+ "<tr><td><strong>Add comment</strong></td></tr><tr><td>"
							+ "<form name=\"cform\" id=\"cform\" method=\"post\" action=\"/public/jpage/1/p/story/a/storypage/sid/"
							+ theItem
							+ "/content.do\"><input type=\"hidden\" name=\"story_id\" value=\""
							+ theItem
							+ "\" /><input type=\"hidden\" name=\"edit\" value=\"true\" />"
							+ "Title: <input type=\"text\" size=\"30\" class=\"inputbox required\" name=\"subject\" value=\"\" />"
							+ "<br />Comment:<br /><textarea class=\"required\" name=\"comment\" style=\"width: 80%; height: 250px;\"></textarea>"
							+ "<br />Spam filter:<br /><img src=\"/Captcha.jpg\"><br />"
							+ "Enter the letters you see above: <input class=\"required\" type=\"text\" name=\"j_captcha_response\" />"
							+ "<br /><input type=\"submit\" value=\"Add Comment\" /></form></td></tr></table>";

					if (request.getAttribute("reject") != null) {
						commentBox = commentBox.replaceAll(
								"subject\" value=\"", "subject\" value=\""
										+ request.getParameter("subject"));
						commentBox = commentBox
								.replaceAll("</textarea>", request
										.getParameter("comment")
										+ "</textarea>");
					}

				} else {
					commentBox = "<h2>Add Comment</h2>You must be a registered member and logged in to post a comment.<br /> "
							+ "Not a member yet? <a href=\"/public/jpage/1/p/JoinSite/a/joinpmc/content.do\">Join</a> now!";
				}

				MasterTemplate.appendString(theHtml);
				MasterTemplate.appendString(commentBox);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Errors
					.addError("com.verilion.display.html.process.ProcessStoryListPage:BuildPage:Exception:"
							+ e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
		return MasterTemplate;
	}

	@SuppressWarnings("unchecked")
	public HTMLTemplateDb ProcessForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		String myErrorText = "";
		String subject = "";
		String comment = "";

		if (request.getParameter("j_captcha_response") != null) {
			try {
				String correctAnswer = "";
				Boolean isResponseCorrect = Boolean.FALSE;

				// retrieve the response
				String captcha_response = request
						.getParameter("j_captcha_response");

				// Call the Service method
				try {
					correctAnswer = (String) session
							.getAttribute(com.verilion.object.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY);
					if (correctAnswer.equalsIgnoreCase(captcha_response)) {
						isResponseCorrect = true;
					} else {
						isResponseCorrect = false;
						myErrorText = "Incorrect validation characters. Try again... ";
					}

				} catch (Exception e) {
					System.out
							.println("correct answer was "
									+ correctAnswer
									+ " and they entered "
									+ (String) session
											.getAttribute(com.verilion.object.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY));
					isResponseCorrect = false;
					myErrorText = "Incorrect spam code entered. Try again... ";
				}

				try {
					if (request.getParameter("subject").length() < 1) {
						isResponseCorrect = false;
						myErrorText += "You must enter a title for your comment!";
					} else {
						String tmpString = "";
						tmpString = request.getParameter("subject");
						subject = tmpString.replaceAll("\\<.*?>","");
					}
					if (request.getParameter("comment").length() < 1) {
						isResponseCorrect = false;
						myErrorText += "You must enter a comment!";
					} else {
						String tmpString = "";
						tmpString = request.getParameter("comment");
						comment = tmpString.replaceAll("\\<.*?>","");
						
					}
				} catch (Exception e) {
					isResponseCorrect = false;
					myErrorText = "An error occurred. Please try again.";
				}

				if (!isResponseCorrect) {
					session.setAttribute("Error", myErrorText);
					// hm.put("PRESERVEMSG", "TRUE");
					request.setAttribute("reject", "true");
					this.BuildPage(request, response, session, conn,
							MasterTemplate, hm);
				} else {
					// save comment and redirect
					// get username
					Customer myCustomer = new Customer();
					myCustomer.setConn(conn);
					int id = Integer.parseInt((String) session
							.getAttribute("customer_id"));
					String uname = myCustomer.returnUsernameForCustomerId(id);
					Comment myComment = new Comment();
					myComment.setConn(conn);
					myComment.setSubject(subject);
					myComment.setComment(TextUtils.NewLinesToBr(comment));
					myComment.setComment_active_yn("y");
					myComment.setStory_id(Integer.parseInt((String) request
							.getParameter("story_id")));
					myComment.setUsername(uname);
					myComment.addComment();

					// now update the comment count in stories
					Story myStory = new Story();
					myStory.setConn(conn);
					myStory
							.updateCommentCountForStory(Integer
									.parseInt((String) request
											.getParameter("story_id")));

					// send an email to the person who owns the story, if they
					// have elected
					// to receive such emails
					GenericTable myGt = new GenericTable();
					myGt.setConn(conn);
					myGt.setSTable("v_pmc_customer");
					myGt.setSSelectWhat("customer_email, notify_on_comment");
					myGt.setSCustomWhere(" and customer_id = (select customer_id from pmc_stories where story_id = "
									+ request.getParameter("story_id") + ")");
					
					XDisconnectedRowSet drs = new XDisconnectedRowSet();
					drs = myGt.getAllRecordsDisconnected();
					while (drs.next()) {
						if (drs.getString("notify_on_comment").equalsIgnoreCase("y")) {
							String to = drs.getString("customer_email");
							GenericTable myTable = new GenericTable("message_queue");
							myTable.setConn(conn);
							myTable.addSet(
								"message_queue_message",
								"Hello!<br /><br />"
								+ "You have received a comment on a posting you made on the Pacemaker Club website!"
								+ "<br /><br />"
								+ "To view the comment, go to "
								+ "<a href=\"http://www.pacemakerclub.com/public/jpage/1/p/story/a/storypage/sid/"
								+ request.getParameter("story_id")
								+ "/content.do\">"
								+ "http://www.pacemakerclub.com/public/jpage/1/p/story/a/storypage/sid/"
								+ request.getParameter("story_id")
								+ "/content.do</a>",
								"String");
							myTable.addUpdateFieldNameValuePair(
								"message_queue_from",
								"donotreply@pacemakerclub.com",
								"String");
							myTable.addUpdateFieldNameValuePair(
								"message_queue_to",
								to,
								"String");
							myTable.addUpdateFieldNameValuePair(
								"message_queue_subject",
								"A comment has been made on your Pacemaker Club posting!",
								"String");
							myTable.insertRecord();
						}
					}
					drs.close();
					drs = null;

					session.setAttribute("Error",
							"Your comment has been posted.");
					session.setAttribute("PRESERVEMSG", "TRUE");
					response
							.sendRedirect("/public/jpage/1/p/story/a/storypage/sid/"
									+ hm.get("sid") + "/content.do");
				}

			} catch (Exception e) {
				e.printStackTrace();
				Errors
						.addError("com.verilion.display.html.ProcessPage:ProcessForm:Exception:"
								+ e.toString());
			}
		}
		return MasterTemplate;
	}

}