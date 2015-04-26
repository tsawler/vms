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
				System.out.println("post time for index " + index + " is "
						+ listOfUsers.element().getId());
				if (listOfUsers.element().getId() > (System.currentTimeMillis() + (1000 * 3))) {
					System.out.println("Timer class is removing user at index "
							+ index);
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