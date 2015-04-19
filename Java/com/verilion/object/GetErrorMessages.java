//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: GetErrorMessages.java,v $
//Revision 1.2  2004/06/24 17:58:16  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.DbBean;
import com.verilion.database.SystemMessages;

/**
 * Gets error messages and such from database, in correct language
 * <P>
 * Dec 04, 2003
 * <P>
 * @author tsawler
 *  
 */
public class GetErrorMessages {

	public static String theMessage = "";
	public static Connection conn = null;

	/**
	 * Gets an error message by name
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param messageName
	 * @return String
	 * @throws Exception
	 */
	public static String getErrorMessage(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session,
		String messageName, 
		String targetUrl)
		throws Exception {

		try {
			// open database connection for database object
			conn = DbBean.getDbConnection();
			SystemMessages myMessage = new SystemMessages();
			myMessage.setConn(conn);

			// find out what language id to use from session;
			// if it's not there, assume English (1st entry in code
			// table)
			if (session.getAttribute("languageId") != null) {
				myMessage.setCt_language_id(
					Integer.parseInt(
						(String) session.getAttribute("languageId")));
			} else {
				myMessage.setCt_language_id(1);
			}

			// now set attributes of database object and get the message, by
			// name
			myMessage.setSystem_message_name(messageName);
			theMessage = myMessage.getSystemMessageByNameAndLanguageId();
			
			// now redirect where you want the user to go
			response.sendRedirect(targetUrl);
			DbBean.closeDbConnection(conn);

		} catch (Exception e) {
			Errors.addError(
				"GetErrorMessages.getErrorMessage:Exception:"
					+ e.toString());
		}
		// return the message
		return theMessage;
	}

	public static String getErrorMessage(
		HttpSession session,
		String messageName)
		throws Exception {
		try {
			// open database connection for database object
			conn = DbBean.getDbConnection();
			SystemMessages myMessage = new SystemMessages();
			myMessage.setConn(conn);

			// find out what language id to use from session;
			// if it's not there, assume English (1st entry in code
			// table)
			if (session.getAttribute("languageId") != null) {
				myMessage.setCt_language_id(
					Integer.parseInt(
						(String) session.getAttribute("languageId")));
			} else {
				myMessage.setCt_language_id(1);
			}

			// now set attributes of database object and get the message, by
			// name
			myMessage.setSystem_message_name(messageName);
			theMessage = myMessage.getSystemMessageByNameAndLanguageId();
			DbBean.closeDbConnection(conn);

		} catch (Exception e) {
			Errors.addError(
				"GetErrorMessages.getErrorMessage:Exception:"
					+ e.toString());
		}
		// return the message
		return theMessage;
	}
}