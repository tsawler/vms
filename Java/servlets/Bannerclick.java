//------------------------------------------------------------------------------
//Copyright (c) 2003-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-23
//Revisions
//------------------------------------------------------------------------------
//$Log: Bannerclick.java,v $
//Revision 1.1.2.1  2007/01/23 14:40:04  tcs
//Initial entry
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

import com.verilion.database.Banners;
import com.verilion.database.DbBean;

/**
 * Handle click on a banner
 * 
 * @author tsawler
 * 
 */
public class Bannerclick extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Connection conn = null;
	int banner_id = 0;
	String url = "";

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

		try {
			try {
				// Get a connection
				conn = DbBean.getDbConnection();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				banner_id = Integer.parseInt((String) request
						.getParameter("id"));
				url = (String) request.getParameter("url");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Banners myBanner = new Banners();
			myBanner.setConn(conn);
			myBanner.addOneToClicks(banner_id);
			myBanner.addClickDetail(banner_id, request.getRemoteHost());
			response.sendRedirect(url);
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