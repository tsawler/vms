//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: DoLanguageChoice.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/06/25 16:37:33  tcs
//Changed redirect to last page
//
//Revision 1.1  2004/05/23 04:52:45  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Change language choice
 * <P>
 * Dec 05, 2003
 * <P>
 * package com.verilion
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DoLanguageChoice extends HttpServlet {

	private static final long serialVersionUID = 4744038572672080173L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get language choice
		String theLanguage = (String) request.getParameter("lang");

		// put it in the session
		HttpSession session = request.getSession(true);
		session.setAttribute("languageId", theLanguage);

		response.sendRedirect((String) session.getAttribute("lastPage"));
	}
}