//------------------------------------------------------------------------------
//Copyright (c) 2004-2009 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2009-04-30
//Revisions
//------------------------------------------------------------------------------
//$Log: WhosInChatModule.java,v $
//Revision 1.1.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.verilion.components.Chat;
import com.verilion.components.ChatUsers;
import com.verilion.object.Errors;

/**
 * Displays list of who is in chat
 * 
 * @author tsawler
 * 
 */
public class WhosInChatModule implements ModuleInterface {

	/**
	 * displays list of who is in chat
	 * 
	 * @return String - box as html
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception {

		String theModuleText = "";

		try {

			if (session.getAttribute("user") == null) {
				theModuleText = "";
			} else {
				theModuleText = "<div class=\"vmodule\">\n"
						+ "<div class=\"vmoduleheading\">Who's in Chat Now?</div>\n"
						+ "<div class=\"vmodulebody\">\n" + "<ul>\n";
				Chat c = new Chat();
				LinkedList<ChatUsers> lst = (LinkedList<ChatUsers>) c
						.getListOfUsers();
				if (lst.isEmpty()) {
					theModuleText += "<li>No one in chat</li>";
				} else {
					ListIterator itr = lst.listIterator();
					while (itr.hasNext()) {
						ChatUsers cu = (ChatUsers) itr.next();
						theModuleText += "<li>" + cu.getText() + "</li>";
					}
				}
				theModuleText += "</ul></div></div>";
			}
		} catch (Exception e) {
			Errors.addError("LoginBoxesModule.getHtmlOutput:Exception:"
					+ e.toString());
		}
		return theModuleText;
	}
}