//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-15
//Revisions
//------------------------------------------------------------------------------
//$Log: TextUtils.java,v $
//Revision 1.6.2.1.12.1  2007/01/18 11:53:04  tcs
//Added method to replace line breaks with br's
//
//Revision 1.6.2.1  2004/12/11 22:21:10  tcs
//checked for &#39; tags
//
//Revision 1.6  2004/10/26 13:52:15  tcs
//Added method to remove html versions of certain strings (i.e. &copy;);
//
//Revision 1.5  2004/10/25 17:34:24  tcs
//Corrected cleaning of linefeeds
//
//Revision 1.4  2004/10/24 21:31:54  tcs
//Cleanedup code.
//
//Revision 1.3  2004/10/24 16:49:22  tcs
//Added method to strip newlines
//
//Revision 1.2  2004/07/15 16:13:08  tcs
//Changed method name
//
//Revision 1.1  2004/07/15 16:10:01  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.utility;

/**
 * Utils for manipulating strings
 * <P>
 * July 15 2004
 * <P>
 * package com.verilion
 * <P>
 * 
 * @author tsawler
 * 
 */
public class TextUtils {

	/**
	 * Searches and replaces values in a supplied string.
	 * 
	 * @param orig
	 * @param from
	 * @param to
	 * @return String
	 */
	public final static String replace(String orig, String from, String to) {
		int fromLength = from.length();

		if (fromLength == 0)
			throw new IllegalArgumentException(
					"String to be replaced must not be empty");

		int start = orig.indexOf(from);
		if (start == -1)
			return orig;

		boolean greaterLength = (to.length() >= fromLength);

		StringBuffer buffer;
		// If the "to" parameter is longer than (or
		// as long as) "from", the final length will
		// be at least as large
		if (greaterLength) {
			if (from.equals(to))
				return orig;
			buffer = new StringBuffer(orig.length());
		} else {
			buffer = new StringBuffer();
		}

		char[] origChars = orig.toCharArray();

		int copyFrom = 0;
		while (start != -1) {
			buffer.append(origChars, copyFrom, start - copyFrom);
			buffer.append(to);
			copyFrom = start + fromLength;
			start = orig.indexOf(from, copyFrom);
		}
		buffer.append(origChars, copyFrom, origChars.length - copyFrom);

		return buffer.toString();
	}

	/**
	 * Strips HTML tags from supplied text
	 * 
	 * @param theText
	 * @return String - the stripped text
	 */
	public final static String StripHtml(String theText) {
		String theStrippedText = "";
		try {
			theStrippedText = theText.replaceAll("\\<.*?\\>", "");
			theStrippedText = theStrippedText.replaceAll("&#39;", "'");
			theStrippedText = theStrippedText.replaceAll("&nbsp;", " ");
			theStrippedText = theStrippedText.replaceAll("\n", " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theStrippedText;
	}

	/**
	 * Strip newlines from a string
	 * 
	 * @param theText
	 * @return the original text minus any newline chars
	 */
	public final static String StripNewlines(String theText) {
		String theStrippedText = "";
		try {
			theStrippedText = theText.replaceAll("\r\n", " ");
			theStrippedText = theStrippedText.replaceAll("\n\r", " ");
			theStrippedText = theStrippedText.replaceAll("\r", " ");
			theStrippedText = theStrippedText.replaceAll("\n", " ");
			theStrippedText = theStrippedText.replaceAll("\t", " ");
			theStrippedText = theStrippedText.replaceAll("\f", " ");
			theStrippedText = theStrippedText.replaceAll("<br />", "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theStrippedText;
	}

	/**
	 * Convert newlines to br characters (html newline)
	 * 
	 * @param theText
	 * @return the text with newlines converted to br's
	 */
	public final static String NewLinesToBr(String theText) {
		String theStrippedText = "";
		try {
			theStrippedText = theText.replaceAll("\r\n", "<br />");
			theStrippedText = theStrippedText.replaceAll("\n\r", "<br />");
			theStrippedText = theStrippedText.replaceAll("\r", "<br />");
			theStrippedText = theStrippedText.replaceAll("\n", "<br />");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theStrippedText;
	}

	/**
	 * Removes special HTML characters and replaces with appropriate value (i.e.
	 * &copy; becomes the copyright symbol)
	 * 
	 * @param theText
	 * @return the supplied text with &value; replaced with appropriate text
	 */
	public final static String RemoveHtmlEquivs(String theText) {
		String theReplacedText = "";
		try {
			theReplacedText = theText.replaceAll("&quot;", "\"");
			theReplacedText = theReplacedText.replaceAll("&amp;", "&");
			theReplacedText = theReplacedText.replaceAll("&gt;", ">");
			theReplacedText = theReplacedText.replaceAll("&lt;", "<");
			theReplacedText = theReplacedText.replaceAll("&#8221;", "\"");
			theReplacedText = theReplacedText.replaceAll("&#8220;", "\"");
			theReplacedText = theReplacedText.replaceAll("&copy;", "©");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theReplacedText;
	}
}