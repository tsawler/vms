//------------------------------------------------------------------------------
//Copyright (c) 2003-2009 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2009-06-01
//Revisions
//------------------------------------------------------------------------------
//$Log: LMSServlet.java,v $
//Revision 1.1.2.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.beans.StudentBeans;
import com.verilion.database.DbBean;

/**
 * Handle student progress calls
 * 
 * @author tsawler
 * 
 */
public class LMSServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Connection conn = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */

	public synchronized void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// forward all get requests to the post handler
		this.doPost(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public synchronized void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		int customer_id = 0;
		int course_section_detail_id = 0;
		String action = "";
		String query = "";
		PreparedStatement pst = null;
		final int DELTA_MINUTES = -20;
		ResultSet rs = null;
		String theResponse = "OK";
		String course_completed_yn = "n";
		StudentBeans myStudent = new StudentBeans();
		HttpSession session = request.getSession(false);

		try {

			try {
				// Get a connection
				conn = DbBean.getDbConnection();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			try {
				action = (String) request.getParameter("action");
				try {
					course_section_detail_id = Integer.parseInt((String) request
							.getParameter("cid"));
				} catch (Exception e) {
					course_section_detail_id = Integer.parseInt((String) request.getParameter("id"));
				}

				// get info from bean
				if (session.getAttribute("StudentBean") != null) {
					myStudent = (StudentBeans) session
							.getAttribute("StudentBean");
					customer_id = myStudent.getCustomer_id();
					course_completed_yn = myStudent
							.getCurrent_course_completed_yn();
				} else {
					System.out.println("Student bean is null");
				}

				if (course_completed_yn.equalsIgnoreCase("n")) {
					// first see if there is an entry with no end date, <
					// DELTA_MINUTES in the past
					Calendar testCal = null;
					testCal = Calendar.getInstance();

					testCal.add(Calendar.MINUTE, DELTA_MINUTES);
					Date testCalDate = testCal.getTime();

					Timestamp testTimeStamp = new Timestamp(testCalDate
							.getTime());

					query = "select * from course_student_progress where "
							+ "customer_id = ? "
							+ "and start_date_time_access < ? "
							+ "and course_section_id = ? "
							+ "and end_date_time_access is null";

					pst = conn.prepareStatement(query);
					pst.setInt(1, customer_id);
					pst.setTimestamp(2, testTimeStamp);
					pst.setInt(3, course_section_detail_id);

					rs = pst.executeQuery();
					drs.populate(rs);
					rs.close();
					rs = null;
					pst.close();
					pst = null;
					// Calendar myCal = Calendar.getInstance();
					int tmpId = 0;

					while (drs.next()) {
						tmpId = drs.getInt("id");
						pst = conn
								.prepareStatement("update course_student_progress set end_date_time_access = (start_date_time_access + (20 || ' minutes')::INTERVAL) "
										+ "where id = ?");
						pst.setInt(1, tmpId);
						pst.executeUpdate();
						pst.close();
						pst = null;
					}

					// if action = a, insert record
					if (action.equalsIgnoreCase("a")) {
						// starting a course section, so insert record
						try {
							long entryDate = System.currentTimeMillis();
					        Timestamp original = new Timestamp(entryDate);
					        
							query = "insert into course_student_progress (customer_id, course_section_id, start_date_time_access) "
									+ "values (?, ?, ?)";
							pst = conn.prepareStatement(query);
							pst.setInt(1, customer_id);
							pst.setInt(2, course_section_detail_id);
							pst.setTimestamp(3, original);
							pst.executeUpdate();
							pst.close();
							pst = null;
						} catch (Exception e) {
							e.printStackTrace();
							theResponse = "Failed: " + e.toString();
						}
						
						if ((course_section_detail_id > 614) && (course_section_detail_id < 664)){
							try {
								int cspid = 0;
								query = "select id from course_student_progress where customer_id = ? "
										+ "and course_section_id = ? "
										+ "and end_date_time_access is null";
								pst = conn.prepareStatement(query);
								pst.setInt(1, customer_id);
								pst.setInt(2, course_section_detail_id);
								rs = pst.executeQuery();
								drs.populate(rs);
								rs.close();
								rs = null;
								pst.close();
								pst = null;
								
								long retryDate = System.currentTimeMillis();

						        int sec = 25;

						        Timestamp original = new Timestamp(retryDate);
						        Calendar cal = Calendar.getInstance();
						        cal.setTimeInMillis(original.getTime());
						        cal.add(Calendar.SECOND, sec);
						        Timestamp later = new Timestamp(cal.getTime().getTime());
						        
								while (drs.next()) {
									cspid = drs.getInt("id");
									query = "update course_student_progress set end_date_time_access = ? where id = ?";
									pst = conn.prepareStatement(query);
									pst.setTimestamp(1, later);
									pst.setInt(2, cspid);
									
									pst.executeUpdate();
									pst.close();
									pst = null;
								}
							} catch (Exception e) {
								e.printStackTrace();
								theResponse = "Failed: " + e.toString();
							}
							
						}

					} else {
						// updating the record
						try {
							int cspid = 0;
							query = "select id from course_student_progress where customer_id = ? "
									+ "and course_section_id = ? "
									+ "and end_date_time_access is null";
							pst = conn.prepareStatement(query);
							pst.setInt(1, customer_id);
							pst.setInt(2, course_section_detail_id);
							rs = pst.executeQuery();
							drs.populate(rs);
							rs.close();
							rs = null;
							pst.close();
							pst = null;
							long entryDate = System.currentTimeMillis();
					        Timestamp original = new Timestamp(entryDate);
							
							while (drs.next()) {
								cspid = drs.getInt("id");
								query = "update course_student_progress set end_date_time_access = ? where id = ?";
								pst = conn.prepareStatement(query);
								pst.setTimestamp(1, original);
								pst.setInt(2, cspid);
								pst.executeUpdate();
								pst.close();
								pst = null;
							}
						} catch (Exception e) {
							e.printStackTrace();
							theResponse = "Failed: " + e.toString();
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			// close the connection
			conn.close();
			conn = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				conn = null;
			}
		}

		PrintWriter out = response.getWriter();

		out.println(theResponse);
		out.close();
	}

}