//------------------------------------------------------------------------------
//Copyright (c) 2003-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-13
//Revisions
//------------------------------------------------------------------------------
//$Log: CastPoll.java,v $
//Revision 1.1.2.1  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DbBean;
import com.verilion.database.GenericTable;
import com.verilion.database.SingletonObjects;

/**
 * Handle click on a banner
 * 
 * @author tsawler
 * 
 */
public class CastPoll extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Connection conn = null;
	int poll_id = 0;
	int response_id = 0;

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
		int number_votes = 0;

		try {
			try {
				// Get a connection
				conn = DbBean.getDbConnection();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				poll_id = Integer.parseInt((String) request
						.getParameter("poll_id"));
				response_id = Integer.parseInt((String) request
						.getParameter("vote"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			GenericTable myTable = new GenericTable("polls");
			myTable.setConn(conn);
			myTable.setSSelectWhat("number_voters");
			myTable.setSCustomWhere("and poll_id = " + poll_id);
			drs = myTable.getAllRecordsDisconnected();
			while (drs.next()) {
				number_votes = drs.getInt("number_voters");
				number_votes++;
			}
			drs.close();
			drs = null;

			// update vote total
			myTable.addUpdateFieldNameValuePair("number_voters", number_votes
					+ "", "int");
			myTable.setUpdateWhat("polls");
			myTable.setSCustomWhere("and poll_id = " + poll_id);
			myTable.updateRecord();

			// add individual vote data
			myTable.clearUpdateVectors();
			myTable.setSTable("polls_data");
			myTable.setSSelectWhat("poll_option_count");
			myTable.setSCustomWhere("and polls_data_id = " + response_id);
			drs = myTable.getAllRecordsDisconnected();
			while (drs.next()) {
				number_votes = drs.getInt("poll_option_count");
				number_votes++;
			}
			myTable.clearUpdateVectors();
			myTable.setUpdateWhat("polls_data");
			myTable.addUpdateFieldNameValuePair("poll_option_count",
					number_votes + "", "int");
			myTable.setSCustomWhere("and polls_data_id = " + response_id);
			myTable.updateRecord();

			// add vote history
			myTable.clearUpdateVectors();
			myTable.setSTable("polls_check");
			;
			myTable.addUpdateFieldNameValuePair("poll_id", poll_id + "", "int");
			myTable.addUpdateFieldNameValuePair("ip_address",
					(String) request.getRemoteHost(), "string");
			myTable.insertRecord();

			// take them back
			response.sendRedirect(SingletonObjects.getInstance().getHomepage());
			conn.close();
			conn = null;
			return;

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
	}

}