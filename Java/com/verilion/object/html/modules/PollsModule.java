//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-03
//Revisions
//------------------------------------------------------------------------------
//$Log: PollsModule.java,v $
//Revision 1.1.2.2.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.1.2.2  2007/03/15 17:49:18  tcs
//Cosmetic changes
//
//Revision 1.1.2.1  2007/03/14 00:18:37  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.object.Errors;

/**
 * Polls module
 * 
 * @author tsawler
 * 
 */
public class PollsModule implements ModuleInterface {

	/**
	 * Builds poll or shows that they have already voded
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception {

		String thePollString = "";
		XDisconnectedRowSet crs = new XDisconnectedRowSet();
		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		XDisconnectedRowSet drs2 = new XDisconnectedRowSet();

		int poll_id = 0;
		String ip_address = "";

		try {
			thePollString = "<div class=\"vmodule\">\n"
					+ "<div class=\"vmoduleheading\">Polls</div>\n"
					+ "<div class=\"vmodulebody\">\n"
					+ "<form action=\"/castpoll\" method=\"post\">";

			GenericTable myPoll = new GenericTable();
			GenericTable myResponses = new GenericTable("polls_data");
			GenericTable myPollCheck = new GenericTable("polls_check");

			myPoll.setConn(conn);
			myResponses.setConn(conn);
			myPollCheck.setConn(conn);

			myPoll.setSTable("polls");
			myPoll.setSSelectWhat("*");
			myPoll.setSCustomWhere("and active_yn = 'y'");

			crs = myPoll.getAllRecordsDisconnected();

			if (crs.next()) {
				crs.previous();
				while (crs.next()) {
					boolean hasVoted = false;

					// read in poll
					poll_id = crs.getInt("poll_id");

					// see if they have voted
					try {
						HashMap hm = (HashMap) session.getAttribute("pHm");
						ip_address = hm.get("ip_address").toString();
						myPollCheck.setSSelectWhat("*");
						myPollCheck.setSCustomWhere("and poll_id = " + poll_id
								+ " and ip_address ilike '" + ip_address + "'");
						drs2 = myPollCheck.getAllRecordsDisconnected();
						if (drs2.next()) {
							hasVoted = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						hasVoted = false;
					}

					String theTitleText = "<div style=\"margin-left: 3px;\">"
							+ crs.getString("title")
							+ "</div><br /><div style=\"margin-left: 5px;\">";

					thePollString += theTitleText + "\n";
					myResponses.setSTable("polls_data");
					myResponses.setSSelectWhat("*");
					myResponses.setSCustomWhere("and poll_id = "
							+ crs.getInt("poll_id"));
					myResponses.setSCustomOrder("order by polls_data_id");
					drs = myResponses.getAllRecordsDisconnected();
					while (drs.next()) {
						if (hasVoted) {
							thePollString += drs.getString("poll_option_text")
									+ "<br />";
						} else {
							thePollString += "<input type=\"radio\" name=\"vote\" value=\""
									+ drs.getInt("polls_data_id")
									+ "\">"
									+ drs.getString("poll_option_text")
									+ "<br />";
						}
					}
					thePollString += "</div><div style=\"text-align: center;\">";
					if (!hasVoted) {
						thePollString += "<input type=\"hidden\" name=\"poll_id\" value=\""
								+ poll_id
								+ "\"> <input type=\"submit\" value=\"Vote!\" class=\"inputbox\">";
					}
					thePollString += "<br /><a href=\"/public/jpage/1/p/Polls/a/pollresults/pid/"
							+ poll_id
							+ "/content.do\" title=\"View poll results\">View results</a>";
					thePollString += "</div>";
				}
			} else {
				// No news items to display
				thePollString += "<br />Currently unavailable\n";
			}
			thePollString += "</form></div>\n</div>\n";

			crs.close();
			crs = null;
		} catch (Exception e) {
			Errors.addError("PollsModule.getHtmlOutput:Exception:"
					+ e.toString());
		} finally {
			if (crs != null) {
				crs.close();
				crs = null;
			}
		}
		return thePollString;
	}
}