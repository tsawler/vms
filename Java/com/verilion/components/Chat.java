//------------------------------------------------------------------------------
//Copyright (c) 2003-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-01
//Revisions
//------------------------------------------------------------------------------
//$Log: Chat.java,v $
//Revision 1.1.2.4.2.3  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.4.2.2  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.4.2.1  2007/06/11 15:36:25  tcs
//*** empty log message ***
//
//Revision 1.1.2.4  2007/02/04 01:02:26  tcs
//Added method to init sessions
//
//Revision 1.1.2.3  2007/02/03 01:50:57  tcs
//Changed order of chat messages - newest come last.
//
//Revision 1.1.2.2  2007/02/02 13:53:39  tcs
//Updated for use with DWR version 2
//
//Revision 1.1.2.1  2007/02/01 12:32:24  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.components;

import java.util.Collection;
import java.util.LinkedList;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.directwebremoting.util.Logger;

/**
 * Chat component object for site chat component
 * 
 * @author Trevor
 * 
 */
public class Chat {

	/**
	 * Add a chat message for chat component
	 * 
	 * @param text
	 */
	public void addMessage(String text) {
		try {
			// Make sure we have a list of the list 10 messages
			if (text != null && text.trim().length() > 0) {

				listOfMessages.addLast(new ChatMessage(text));

				while (listOfMessages.size() > 20) {
					listOfMessages.removeFirst();
				}

			}

			WebContext wctx = WebContextFactory.get();
			String currentPage = wctx.getCurrentPage();

			// Clear the input box in the browser that kicked off this page only
			Util utilThis = new Util(wctx.getScriptSession());
			utilThis.setValue("text", "");

			// For all the browsers on the current page:
			Collection sessions = wctx.getScriptSessionsByPage(currentPage);
			Util utilAll = new Util(sessions);

			// Clear the list and add in the new set of messages
			utilAll.removeAllOptions("chatlog");
			utilAll.addOptions("chatlog", listOfMessages, "text");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addUser(String text) {

		try {
			listOfUsers.addLast(new ChatUsers(text));
			WebContext wctx = WebContextFactory.get();
			String currentPage = wctx.getCurrentPage();

			// Clear the input box in the browser that kicked off this page only
			Util utilThis = new Util(wctx.getScriptSession());
			utilThis.setValue("text", "");

			// For all the browsers on the current page:
			Collection sessions = wctx.getScriptSessionsByPage(currentPage);
			Util utilAll = new Util(sessions);

			// Clear the list and add in the new set of messages
			utilAll.removeAllOptions("userlist");
			utilAll.addOptions("userlist", listOfUsers, "text");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeUser(String text) {
		try {
			System.out.println("trying to remove" + text);
			int index = 0;
			while (index < listOfUsers.size()) {
				// listOfUsers.g
				String testName = listOfUsers.get(index).getText();
				System.out.println("checking against " + testName);
				if (testName.equals(text)) {
					listOfUsers.remove(index);
					System.out.println("removed " + testName + " at index "
							+ index);
				} else {
					System.out.println("not a match");
					index++;
				}
			}

			WebContext wctx = WebContextFactory.get();
			String currentPage = wctx.getCurrentPage();

			// For all the browsers on the current page:
			Collection sessions = wctx.getScriptSessionsByPage(currentPage);
			Util utilAll = new Util(sessions);

			// Clear the list and add in the new set of messages
			utilAll.removeAllOptions("userlist");
			utilAll.addOptions("userlist", listOfUsers, "text");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initSession() {
		WebContext wctx = WebContextFactory.get();
		wctx.getScriptSession();
		String currentPage = wctx.getCurrentPage();

		// Clear the input box in the browser that kicked off this page only
		Util utilThis = new Util(wctx.getScriptSession());
		utilThis.setValue("text", "");

		// For all the browsers on the current page:
		Collection sessions = wctx.getScriptSessionsByPage(currentPage);
		Util utilAll = new Util(sessions);

		// Clear the list and add in the new set of messages
		utilAll.removeAllOptions("chatlog");
		utilAll.addOptions("chatlog", listOfMessages, "text");

		utilAll.removeAllOptions("userlist");
		utilAll.addOptions("userlist", listOfUsers, "text");
	}

	public void removeAllUsers() {
		try {
			int index = 0;
			while (index < listOfUsers.size()) {
				listOfUsers.remove(index);
				index++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeInactiveUsers() {
		System.out.println("Checking inactive users...");
		try {
			int index = 0;
			while (index < listOfUsers.size()) {
				System.out.println("post time for index " + index + " is " + listOfUsers.element().getId());
				if (listOfUsers.element().getId() > (System.currentTimeMillis() + (1000 * 3))){
					System.out.println("Timer class is removing user at index " + index);
					listOfUsers.remove(index);
				}
				index++;
			}
			WebContext wctx = WebContextFactory.get();
			wctx.getScriptSession();
			String currentPage = wctx.getCurrentPage();
			Collection sessions = wctx.getScriptSessionsByPage(currentPage);
			Util utilAll = new Util(sessions);
			utilAll.removeAllOptions("userlist");
			utilAll.addOptions("userlist", listOfUsers, "text");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done checking inactive users.");
	}

	/**
	 * The current set of messages
	 */
	// private LinkedList chatMessages = new LinkedList();
	private static LinkedList<ChatMessage> listOfMessages = new LinkedList<ChatMessage>();

	/**
	 * The current list of users
	 */
	private static LinkedList<ChatUsers> listOfUsers = new LinkedList<ChatUsers>();
	
	public LinkedList<ChatUsers> getListOfUsers() {
		return listOfUsers;
	}
	/**
	 * The log stream
	 */
	protected static final Logger log = Logger.getLogger(Chat.class);
}