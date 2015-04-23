//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-26
//Revisions
//------------------------------------------------------------------------------
//$Log: TextToken.java,v $
//Revision 1.1  2004/10/26 12:23:17  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.utility.html.parser;

/**
 * This represents a block of text.
 * 
 * @see HTMLTokenizer
 */
public class TextToken {

	/** The content of the token. */
	private StringBuffer text;

	/**
	 * Constructs a new token.
	 */
	public TextToken() {
		text = new StringBuffer();
	}

	/**
	 * Sets the content of the Token.
	 * 
	 * @param newText
	 *            the new content of the Token.
	 */
	public void setText(String newText) {

		// Replace the old StringBuffer with a new one.
		text = new StringBuffer(newText);
	}

	/**
	 * Sets the content of the Token.
	 * 
	 * @param newText
	 *            the new content of the Token.
	 */
	public void setText(StringBuffer newText) {

		// Replace the old StringBuffer with a new one.
		text = newText;
	}

	/**
	 * Appends some content to the token.
	 * 
	 * @param more
	 *            the new content to add.
	 */
	public void appendText(String more) {
		text.append(more);
	}

	/**
	 * Returns the contents of the token.
	 */
	public String getText() {
		return new String(text);
	}

	/**
	 * Returns a string version of the TextToken.
	 * 
	 */
	public String toString() {
		return text.toString();
	}
}