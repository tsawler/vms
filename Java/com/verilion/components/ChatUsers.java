//------------------------------------------------------------------------------
//Copyright (c) 2003-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-04-27
//Revisions
//------------------------------------------------------------------------------
//$Log: ChatUsers.java,v $
//Revision 1.1.2.1  2007/06/11 15:36:25  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.components;

import org.directwebremoting.Security;

/**
 * The Chat messsage object for Chat component
 * 
 * @author Trevor
 *
 */
public class ChatUsers {
	/**
	 * @param newUser
	 *            the new message text
	 */
	public ChatUsers(String newUser) {
		text = newUser;

		if (text.length() > 256) {
			text = text.substring(0, 256);
		}
		text = Security.replaceXmlCharacters(text);
	}

	/**
	 * @return the message id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the message itself
	 */
	public String getText() {
		return text;
	}

	/**
	 * When the message was created
	 */
	private long id = System.currentTimeMillis();

	/**
	 * The text of the message
	 */
	private String text;
}