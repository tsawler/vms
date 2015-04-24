//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-05
//Revisions
//------------------------------------------------------------------------------
//$Log: AdminLogin.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/11/03 13:23:40  tcs
//Added explicit serialization
//
//Revision 1.1  2004/07/05 18:57:38  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.Customer;
import com.verilion.database.DbBean;

/**
 * Log in functions (for admin page).
 * <P>
 * July 7, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class AdminLogin extends HttpServlet {

	private static final long serialVersionUID = 7477099467277090039L;
	private String customer_email_address = "";
	private String customer_password = "";
	private ResultSet rs = null;
	private Connection conn;
	private Statement st = null;
	private int customer_access_level = 0;

	/**
	 * Validates log in info. If success, return customer id. If failure, return
	 * -1.
	 * 
	 * @param inEmailAddress
	 * @param inPassword
	 * @return int
	 * @throws Exception
	 */
	public int doLogin(String inEmailAddress, String inPassword)
			throws Exception {

		int customer_id = -1;

		String sqlQuery = "select ce.customer_email, " + "c.customer_id, "
				+ "c.customer_password " + "from customer_email_detail as ce, "
				+ "customer as c " + "where ce.customer_email = '"
				+ inEmailAddress + "' " + "and c.customer_id = ce.customer_id "
				+ "and ce.customer_email_default_yn = 'y'";

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				String pass = rs.getString("customer_password");
				if (pass.equals(inPassword)) {
					customer_id = rs.getInt("customer_id");
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;

		} catch (SQLException e) {
			throw new ServletException("Login:doLogin:SQLException:"
					+ e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}
		}
		return customer_id;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		// get some application prefs

		// set up database connection
		try {
			conn = DbBean.getDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int customer_id = -1;
		customer_email_address = (String) request.getParameter("email_address");
		customer_password = (String) request.getParameter("password");

		// try to log in
		try {
			customer_id = doLogin(customer_email_address, customer_password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// if we have a customer id, we are logged in, so set
		// some sessional variables. Otherwise set an error message.
		if (customer_id > 0) {
			session.setAttribute("user", customer_email_address);
			session.setAttribute("customer_id", customer_id + "");
			try {
				Customer myCustomer = new Customer();
				myCustomer.setConn(conn);
				customer_access_level = myCustomer
						.getAccessLevel(customer_email_address);
				session.setAttribute("customer_access_level",
						customer_access_level + "");
				session.setAttribute("languageId", "1");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			session.setAttribute("Error", "Invalid login!");
		}

		// redirect to appropriate URL
		try {
			response.sendRedirect(response
					.encodeRedirectURL("/servlet/com.verilion.display.html.SecurePage?page=SearchCustomers"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// close connection
		try {
			DbBean.closeDbConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}