package com.verilion.components;

import java.net.MalformedURLException;
import java.net.URL;

import org.directwebremoting.Security;

/**
 * The Chat messsage object for Chat component
 * 
 * @author Trevor
 *
 */
public class ChatMessage {
	/**
	 * @param newtext
	 *            the new message text
	 */
	public ChatMessage(String newtext) {
		text = newtext;

		if (text.length() > 256) {
			text = text.substring(0, 256);
		}
		text = Security.replaceXmlCharacters(text);

		try {
			if (text.startsWith("http://")) {
				URL url = new URL(text);
				text = "<a href='#' onclick='window.open(\""
						+ url.toExternalForm()
						+ "\", \"\", \"resizable=yes,scrollbars=yes,status=yes\");'>"
						+ url.toExternalForm() + "</a>";
			}
		} catch (MalformedURLException ex) {
			// Ignore - it's not a URL
		}
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