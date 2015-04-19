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
 * Init a session object
 * 
 * @author tsawler
 * 
 */
public class InitSession extends HttpServlet {

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
		int customer_access_level = 0;
		String query = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		String theResponse = "OK";
		//HttpSession session = request.getSession(true);
		HttpSession session = request.getSession();
		if (session != null){
			request.getSession(false).invalidate();
		}
		session = request.getSession();
		try {
			// Get a connection
			conn = DbBean.getDbConnection();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			customer_id = Integer
					.parseInt((String) request.getParameter("cid"));
			System.out.println("customer id is " + customer_id);
			query = "select * from v_grn_customer where customer_id = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1, customer_id);

			rs = pst.executeQuery();
			drs.populate(rs);
			rs.close();
			rs = null;
			pst.close();
			pst = null;

			while (drs.next()) {
				StudentBeans myStudent = new StudentBeans();

				myStudent.setFirst_name(drs.getString("customer_first_name"));
				myStudent.setLast_name(drs.getString("customer_last_name"));
				myStudent.setEmail_address(drs.getString("customer_email"));
				myStudent.setCustomer_id(drs.getInt("customer_id"));
				myStudent.setSpeciality(drs.getInt("speciality_id"));
				myStudent.setStudent_type(drs.getInt("student_type_id"));
				myStudent.setCme(drs.getInt("cme"));
				myStudent.setCmetype(drs.getInt("cmetype"));
				myStudent.setParticipation(drs.getInt("certificate"));

				session.setAttribute("StudentBean", myStudent);
				session.setAttribute("customer_id", customer_id + "");
				session.setAttribute("studentname",
						drs.getString("customer_first_name"));
				System.out.println("set session, and customer id is " + session.getAttribute("customer_id"));
				try {
					customer_access_level = drs.getInt("customer_access_level");
					session.setAttribute("customer_access_level",
							customer_access_level + "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				theResponse = customer_id + "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		out.println(theResponse);
		out.close();
	}
}