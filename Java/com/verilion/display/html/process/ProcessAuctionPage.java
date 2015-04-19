//------------------------------------------------------------------------------
//Copyright (c) 2008 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2008-11-12
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessAuctionPage.java,v $
//Revision 1.1.2.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;

/**
 * Auction page
 * 
 * Nov 12 2008
 * 
 * @author tsawler
 * 
 */
public class ProcessAuctionPage extends ProcessPage {

	public HTMLTemplateDb BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		XDisconnectedRowSet crs = new XDisconnectedRowSet();

		try {
			Date today = new Date();
			GenericTable myGt = new GenericTable("auctions");
			myGt.setConn(conn);
			myGt.setSSelectWhat("*");
			myGt.setSCustomWhere(" and active_yn = 'y'");
			crs = myGt.getAllRecordsDisconnected();
			String DisplayStartDate = "";
			String DisplayEndDate = "";
			boolean auctionIsLive = true;
			int id = 0;
			double max_bid = 0.0f;
			String timeRemaining = "";
			String username = "";

			while (crs.next()) {
				id = crs.getInt("id");
				MasterTemplate.searchReplace("$IMG$", crs.getString("image"));
				MasterTemplate.searchReplace("$ITEMNAME$", crs
						.getString("title"));
				MasterTemplate.searchReplace("$IMG$", crs.getString("image"));
				SimpleDateFormat sdf = new SimpleDateFormat(
					"EEEE MMMM dd yyyy, HH:mm:ss zzz");
				DisplayStartDate = sdf.format(crs.getTimestamp(
						"start_date_time").getTime());
				DisplayEndDate = sdf.format(crs.getTimestamp("end_date_time")
						.getTime());
				Date closeDate = new Date();
				
				closeDate.setTime(crs.getTimestamp("end_date_time").getTime());
				long closeTime = crs.getTimestamp("end_date_time").getTime();
				long currentTime = System.currentTimeMillis();
				
				
				
				if (today.after(closeDate))
					auctionIsLive = false;
				if (auctionIsLive) {
					MasterTemplate.searchReplace("$STATUS$",
							"<span style=\"color: green;\">Active</span>");
					MasterTemplate
							.searchReplace(
									"$BIDFORM$",
									"<tr><td colspan=\"2\">&nbsp;</td></tr><tr>"
											+ "<td colspan=\"2\"><strong>Place a bid</strong></td></tr><tr>"
											+ "<td>Bid Amount:</td><td><input type=\"text\" class=\"inputbox\" onchange=\"checkBid()\" name=\"amount\" /><span class=\"error\" id=\"amount-error\"></span> "
											+ "<input type=\"button\" value=\"Next step...\" onclick=\"performAction()\" class=\"inputbox\" /></td></tr>"
											+ "<tr><td colspan=\"2\">Your bid must be greater than $CURRENT$</td></tr>");
					// figure out time remaining
					long difference = closeTime - currentTime;
					difference = difference / 1000;
					long days = difference / 86400;
					long hours = (difference / 3600) - (days * 24);
					long minutes = (difference / 60) - (days * 1440) - (hours * 60);
					long seconds = difference % 60;
					timeRemaining = days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";

				} else {
					MasterTemplate
							.searchReplace("$STATUS$",
									"<span style=\"color: red;\">Bidding is closed</span>");
					MasterTemplate.searchReplace("$BIDFORM$", "");
					timeRemaining = "<span style=\"color: red; font-weight: bold;\">Auction is closed</span>";
				}
				MasterTemplate.searchReplace("$START$", DisplayStartDate);
				MasterTemplate.searchReplace("$END$", DisplayEndDate);
				MasterTemplate.searchReplace("$DESC$", crs
						.getString("description"));
				MasterTemplate.searchReplace("$ID$", crs.getInt("id") + "");
				MasterTemplate.searchReplace("$TR$", timeRemaining);
			}
			crs.close();
			crs = null;
			
			// put current bid on screen
			myGt.setSTable("auction_bids ab left join auction_bidders b on (ab.bidder_id = b.id)");
			myGt.setConn(conn);
			myGt.setSSelectWhat(" ab.bid_amount, b.username ");
			myGt.setSCustomOrder(" order by bid_amount desc limit 1");
			myGt.setSCustomWhere(" and auction_id = " + id);
			crs = myGt.getAllRecordsDisconnected();
			if (crs.next()) {
				crs.previous();
				max_bid = crs.getDouble("bid_amount");
				username = crs.getString("username");
			}
			
			NumberFormat currencyFormatter=
		         NumberFormat.getCurrencyInstance();
		         
			MasterTemplate.searchReplace("$CURRENT$", currencyFormatter.format(max_bid));
			MasterTemplate.searchReplace("$HIGH$", username);
			MasterTemplate.searchReplace("$OLDBID$", max_bid + "");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return MasterTemplate;
	}

	@Override
	public HTMLTemplateDb ProcessForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {
		
		// are they logged in?
		int bidder_id = 0;
		if (request.getParameter("task") == null) {
			try {
				bidder_id = Integer.parseInt((String) session.getAttribute("bidder_id"));
			} catch (Exception e) {
				bidder_id = 0;
			}
		}

		if (!(request.getParameter("task") == null)) {
			
			String task = "";
			String email = "";
			String password = "";
			String first_name = "";
			String last_name = "";
			String phone = "";
			String username = "";
			XDisconnectedRowSet drs = new XDisconnectedRowSet();

			task = (String) request.getParameter("task");
			
			if (task.equalsIgnoreCase("login")) {
				boolean okay = false;
				email = request.getParameter("email");
				password = request.getParameter("password");
				GenericTable myGt = new GenericTable("auction_bidders");
				myGt.setConn(conn);
				myGt.setSSelectWhat("*");
				myGt.setSCustomWhere(" and email_address ilike '" + email + "'");
				
				drs = myGt.getAllRecordsDisconnected();
				if (drs.next()) {
					drs.previous();
					while (drs.next()){
						if (drs.getString("password").equalsIgnoreCase(password)) {
							okay = true;
							session.setAttribute("bidder_id", drs.getInt("id") + "");
							bidder_id = drs.getInt("id");
							
							// okay, they are logged in. Record the bid.
							GenericTable bidTable = new GenericTable("auction_bids");
							bidTable.setConn(conn);
							bidTable.addSet("auction_id", request.getParameter("id"), "int");
							bidTable.addSet("bidder_id", bidder_id + "", "int");
							bidTable.addSet("bid_amount", request.getParameter("amount"), "float");
							bidTable.insertRecord();
							bidTable.clearUpdateVectors();
							
							//results screen
							MasterTemplate.replaceFullValue("<h2>Your Bid is Recorded</h2>");
							
						} else {
							MasterTemplate.replaceFullValue("Invalid username or password. Please use the back button in your browser to try again, or create an account.");
						}
					}
				} else {
					MasterTemplate.replaceFullValue("Invalid username or password. Please use the back button in your browser to try again, or create an account.");
				}
			} else if (task.equalsIgnoreCase("create")) {
				first_name = request.getParameter("first_name");
				last_name = request.getParameter("last_name");
				phone = request.getParameter("phone_number");
				password = request.getParameter("password");
				email = request.getParameter("email");
				username = request.getParameter("username");
				GenericTable bidTable = new GenericTable("auction_bidders");
				bidTable.setConn(conn);
				bidTable.addSet("first_name", first_name, "string");
				bidTable.addSet("last_name", last_name, "string");
				bidTable.addSet("phone", phone, "string");
				bidTable.addSet("email_address", email, "string");
				bidTable.addSet("password", password, "string");
				bidTable.addSet("username", username, "string");
				bidTable.insertRecord();
				bidTable.clearUpdateVectors();
				
				bidTable.setSSequence("auction_bidders_id_seq");
				bidder_id = bidTable.returnCurrentSequenceValue();
				session.setAttribute("bidder_id", bidder_id);
				
				bidTable = new GenericTable("auction_bids");
				bidTable.setConn(conn);
				bidTable.addSet("auction_id", request.getParameter("id"), "int");
				bidTable.addSet("bidder_id", bidder_id + "", "int");
				bidTable.addSet("bid_amount", request.getParameter("amount"), "float");
				bidTable.insertRecord();
				
				//results screen
				MasterTemplate.replaceFullValue("<h2>Your Bid is Recorded</h2>");
				
			}
			//MasterTemplate.replaceFullValue("Done");
		} else {
			MasterTemplate.replaceFullValue("<form action=\"/public/jpage/1/p/Auction/a/auction/content.do\" method=\"post\">"
					+ "<input type=\"hidden\" name=\"amount\" value=\""
					+ request.getParameter("amount")
					+ "\" />"
					+ "<input type=\"hidden\" name=\"id\" value=\""
					+ request.getParameter("id")
					+ "\" />"
					+ "<input type=\"hidden\" name=\"task\" value=\"login\" />"
					+ "You must be logged in to place a bid. Please log in, or create an account using the form below.<br /><br />");
			MasterTemplate.appendString("<fieldset><legend>Log in</legend><table style=\"width: 100%\">"
					+ "<tr><td>Email address:</td><td><input name=\"email\" type=\"text\"></td></tr>"
					+ "<tr><td>Password</td><td><input name=\"password\" type=\"text\" /></td></tr>"
					+ "<tr><td colspan=\"2\"><input type=\"submit\" value=\"Log in\" /></td></tr></table></fieldset></form>");
			MasterTemplate.appendString("<div align=\"center\"><br /><br /> or create an account</div>");
			MasterTemplate.appendString("<form action=\"/public/jpage/1/p/Auction/a/auction/content.do\" method=\"post\">"
					+ "<input type=\"hidden\" name=\"amount\" value=\""
					+ request.getParameter("amount")
					+ "\" />"
					+ "<input type=\"hidden\" name=\"id\" value=\""
					+ request.getParameter("id")
					+ "\" />"
					+ "<input type=\"hidden\" name=\"task\" value=\"create\" />"
					+ "<fieldset><legend>Create Account</legend><table style=\"width: 100%\">"
					+ "<tr><td>Username:</td><td><input name=\"username\" type=\"text\" /><td></tr>"
					+ "<tr><td>First name:</td><td><input name=\"first_name\" type=\"text\"></td></tr>"
					+ "<tr><td>Last name:</td><td><input name=\"last_name\" type=\"text\"></td></tr>"
					+ "<tr><td>Phone number:</td><td><input name=\"phone_number\" type=\"text\"></td></tr>"
					+ "<tr><td>Email address:</td><td><input name=\"email\" type=\"text\"></td></tr>"
					+ "<tr><td>Choose a password:</td><td><input name=\"password\" type=\"text\"></td></tr>"
					+ "<tr><td colspan=\"2\"><input type=\"submit\" value=\"Create account\" /></td></tr>"
					+ "<tr><td colspan=\"2\"><br /><strong>Note:</strong> your email address will not be displayed on our site, but "
					+ "your username will be displayed if you are the high bidder. No other details will be made publically available.</td></tr>"
					+ "</table></fieldset></form>");
		}
		
		
		return super.ProcessForm(request, response, session, conn, MasterTemplate, hm);
	}

}