//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEventCalendar.java,v $
//Revision 1.1.2.2  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2007/03/30 17:21:29  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.database.JspTemplate;
import com.verilion.display.HTMLTemplateDb;

/**
 * 
 * @author tsawler
 * 
 */
public class ProcessEventCalendar extends ProcessPage {

	@SuppressWarnings( { "unchecked", "deprecation" })
	public HTMLTemplateDb ProcessForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		// Figure out our page and template name
		String pageName = (String) hm.get("p");
		JspTemplate myJspTemplate = new JspTemplate();
		myJspTemplate.setConn(conn);
		String theTemplate = "";
		theTemplate = myJspTemplate.returnJspTemplateIdForPageName(pageName);

		GenericTable myGt = new GenericTable();
		Calendar cl = Calendar.getInstance();
		GregorianCalendar x = new GregorianCalendar();

		String year = x.get(Calendar.YEAR) + "";
		String month = (x.get(Calendar.MONTH) + 1) + "";
		String day = x.get(Calendar.DAY_OF_MONTH) + "";
		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		String theUrl = "";
		int fyear = 0;
		int fmonth = 0;
		int fday = 0;

		Format fm;
		fm = new SimpleDateFormat("yyyy-MM-dd");

		if (month.length() < 2) {
			month = "0" + month;
		}

		if (day.length() < 2) {
			day = "0" + day;
		}

		if (request.getParameter("f") != null) {
			StringTokenizer st = null;
			st = new StringTokenizer(request.getParameter("f"), "-");
			fyear = Integer.parseInt(st.nextToken());
			fmonth = (Integer.parseInt(st.nextToken())) - 1;
			fday = Integer.parseInt(st.nextToken());
		} else {
			fyear = Integer.parseInt(year);
			fmonth = (Integer.parseInt(month)) - 1;
			fday = Integer.parseInt(day);
		}

		cl.set(fyear, fmonth, fday);
		cl.add(Calendar.DATE, 1);
		cl.add(Calendar.DATE, -2);

		myGt.setConn(conn);
		myGt.setSTable("page");
		myGt.setSSelectWhat("page_invocation");
		myGt.setSCustomWhere("and page_name = 'Calendar'");
		drs = myGt.getAllRecordsDisconnected();
		while (drs.next()) {
			theUrl = drs.getString("page_invocation");
		}

		// The names of the months
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };

		// The days in each month.
		int dom[] = { 31, 28, 31, 30, /* jan feb mar apr */
		31, 30, 31, 31, /* may jun jul aug */
		30, 31, 30, 31 /* sep oct nov dec */
		};

		// The number of days to leave blank at the start of this month
		int leadGap = 0;
		Calendar mCal = Calendar.getInstance();
		mCal.setTime(new Date(fyear - 1900, fmonth - 1, 1, 0, 0, 0));
		fm = new SimpleDateFormat("yyyy-MM-dd");
		String pmonth = fm.format(mCal.getTime());
		mCal.setTime(new Date(fyear - 1900, fmonth + 1, 1, 0, 0, 0));
		String nmonth = fm.format(mCal.getTime());

		String MonthCal = "<table class=\"tabledatacollapse\" style=\"width: 100%;\">"
				+ "<tr><td colspan=\"7\" style=\"font-size: 14px; text-align: center; font-weight: 800;\">"
				+ months[fmonth]
				+ " "
				+ fyear
				+ "</td></tr><tr><td colspan=\"7\" style=\"background: #666; color: #fff; text-align: center;\">"
				+ "<a style=\"color: white;\" href=\""
				+ theUrl
				+ "?f="
				+ pmonth
				+ "\">&lt;--Previous</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "<a style=\"color: white;\" href=\""
				+ theUrl
				+ "?f="
				+ nmonth + "\">Next--&gt;</a></td></tr>";

		GregorianCalendar calendar = new GregorianCalendar(fyear, fmonth, 1);

		MonthCal += "<tr class=\"rowheader\" style=\"text-align: center;\">"
				+ "<td style=\"width: 14%; text-align: center;\">Sun</td>"
				+ "<td style=\"width: 14%; text-align: center;\">Mon</td>"
				+ "<td style=\"width: 14%; text-align: center;\">Tue</td>"
				+ "<td style=\"width: 14%; text-align: center;\">Wed</td>"
				+ "<td style=\"width: 14%; text-align: center;\">Thu</td>"
				+ "<td style=\"width: 14%; text-align: center;\">Fri</td>"
				+ "<td style=\"width: 16%; text-align: center;\">Sat</td>"
				+ "</tr>";

		// Compute how much to leave before the first.
		// getDay( ) returns 0 for Sunday, which is just right.
		leadGap = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		int daysInMonth = dom[fmonth];
		if (calendar.isLeapYear(calendar.get(Calendar.YEAR)) && fmonth == 1)
			++daysInMonth;

		MonthCal += "<tr>";
		// Blank out the labels before 1st day of month
		for (int i = 0; i < leadGap; i++) {
			MonthCal += "<td style=\"height: 50px; border: 1px solid gray;\">&nbsp;</td>";
		}

		myGt.setConn(conn);
		myGt.setSTable("v_events");
		myGt.setSSelectWhat("*");
		myGt.setSCustomWhere("and start_date_time >= '" + fyear + "-"
				+ (fmonth + 1) + "-" + "01" + " 00:00:00'"
				+ " and end_date_time <= '" + fyear + "-" + (fmonth + 1) + "-"
				+ daysInMonth + " 23:59:59'");
		myGt.setSCustomOrder("order by start_date_time");
		drs = myGt.getAllRecordsDisconnected();

		if (drs.next()) {
			// Fill in numbers for the day of month.
			for (int i = 1; i <= daysInMonth; i++) {
				Calendar myTestCal = Calendar.getInstance();
				myTestCal.setTime(new Date(fyear - 1900, fmonth, i, 0, 0, 0));
				int numEvents = 0;

				// get first test date
				Date date1 = myTestCal.getTime();

				// advance calendar one day less a second to get next test date
				// myCal.add(Calendar.DATE, 1);
				myTestCal.set(Calendar.HOUR, 23);
				myTestCal.set(Calendar.MINUTE, 59);
				myTestCal.set(Calendar.SECOND, 59);
				Date date2 = myTestCal.getTime();
				java.sql.Timestamp compareDate1 = new java.sql.Timestamp(date1
						.getTime());
				java.sql.Timestamp compareDate2 = new java.sql.Timestamp(date2
						.getTime());

				String eventTitle = "";
				String eventTitleDisplay = "";
				if (((drs.getTimestamp("start_date_time").after(compareDate1)) && (drs
						.getTimestamp("start_date_time").before(compareDate2)))
						|| (drs.getTimestamp("start_date_time")
								.equals(compareDate1))
						|| ((drs.getTimestamp("end_date_time")
								.after(compareDate1)) && (drs
								.getTimestamp("end_date_time")
								.before(compareDate1)))
						|| ((drs.getTimestamp("start_date_time")
								.before(compareDate1)) && (drs
								.getTimestamp("end_date_time")
								.after(compareDate2)))
						|| ((drs.getTimestamp("start_date_time")
								.after(compareDate1))
								&& (drs.getTimestamp("end_date_time")
										.after(compareDate2)) && (drs
								.getTimestamp("start_date_time")
								.before(compareDate2)))) {
					numEvents++;
					eventTitle += drs.getString("event_name") + "\n";
					eventTitleDisplay += drs.getString("event_name");
					boolean keepgoing = true;
					while (keepgoing) {
						if (drs.next()) {
							if (((drs.getTimestamp("start_date_time")
									.after(compareDate1)) && (drs
									.getTimestamp("start_date_time")
									.before(compareDate2)))
									|| (drs.getTimestamp("start_date_time")
											.equals(compareDate1))
									|| ((drs.getTimestamp("end_date_time")
											.after(compareDate1)) && (drs
											.getTimestamp("end_date_time")
											.before(compareDate1)))
									|| ((drs.getTimestamp("start_date_time")
											.before(compareDate1)) && (drs
											.getTimestamp("end_date_time")
											.after(compareDate2)))
									|| ((drs.getTimestamp("start_date_time")
											.after(compareDate1))
											&& (drs
													.getTimestamp("end_date_time")
													.after(compareDate2)) && (drs
											.getTimestamp("start_date_time")
											.before(compareDate2)))) {
								numEvents++;
								eventTitle += drs.getString("event_name")
										+ "\n";
								eventTitleDisplay += "<br />"
										+ drs.getString("event_name");
							} else {
								keepgoing = false;
							}
						} else {
							keepgoing = false;
						}
					}

					MonthCal += "<td style=\"height: 50px; border: 1px solid gray; text-align: left; background: #eee;\" valign=\"top\">"
							+ "<div style=\"text-align: right;\">"
							+ i
							+ "</div>"
							+ "<div style=\"margin-top: 5px; text-align: left;\">"
							+ "<a style=\"font-size: 80%\" title=\""
							+ eventTitle
							+ "\" href=\"/"
							+ theTemplate
							+ "/jpage/1/p/Events/a/events/content.do?f="
							+ fyear + "-" + (fmonth + 1) + "-" + i + "\">";
					MonthCal += eventTitleDisplay;
					MonthCal += "</a></div></td>";
				} else {
					MonthCal += "<td style=\"height: 50px; border: 1px solid gray; text-align: left;\" valign=\"top\">"
							+ "<div style=\"text-align: right;\">"
							+ i
							+ "</div><br /></td>";
				}

				if ((leadGap + i) % 7 == 0) { // wrap if end of line.
					MonthCal += "</tr><tr>";
				}
			}
		} else {
			// Fill in numbers for the day of month.
			for (int i = 1; i <= daysInMonth; i++) {
				MonthCal += "<td style=\"height: 50px; border: 1px solid gray; text-align: left;\" valign=\"top\">"
						+ "<div style=\"text-align: right;\">"
						+ i
						+ "</div><br /></td>";
				if ((leadGap + i) % 7 == 0) { // wrap if end of line.
					MonthCal += "</tr><tr>";
				}
			}
		}
		// fill in missing <td> tags if applicable.
		GregorianCalendar myCalendar = new GregorianCalendar(fyear, fmonth,
				daysInMonth);
		int k = myCalendar.get(Calendar.DAY_OF_WEEK) + 1;
		for (int i = k - 1; i <= 6; i++) {
			MonthCal += "<td style=\"height: 50px; border: 1px solid gray; text-align: center;\">&nbsp;</td>";
		}

		MonthCal += "</tr></table>";
		// session.setAttribute("MonthCal", MonthCal);
		drs.close();
		drs = null;

		MasterTemplate.replaceFullValue(MonthCal);
		return MasterTemplate;
	}

	@Override
	public HTMLTemplateDb BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {
		// TODO Auto-generated method stub

		this.ProcessForm(request, response, session, conn, MasterTemplate, hm);
		return super.BuildPage(request, response, session, conn,
				MasterTemplate, hm);
	}
}