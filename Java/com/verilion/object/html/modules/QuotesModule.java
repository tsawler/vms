//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-05
//Revisions
//------------------------------------------------------------------------------
//$Log: QuotesModule.java,v $
//Revision 1.2.14.2.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.2.14.2  2007/03/15 17:49:08  tcs
//Cosmetic changes
//
//Revision 1.2.14.1  2007/03/14 00:21:18  tcs
//Changed to use divs
//
//Revision 1.2  2004/11/10 19:52:15  tcs
//Implemented module interface
//
//Revision 1.1  2004/07/05 16:44:51  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.verilion.database.Quotes;
import com.verilion.object.Errors;

/**
 * Displays random quotes in a formatted box
 * <P>
 * July 5, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class QuotesModule implements ModuleInterface {

	public String title = "Quote";
	public String url = "";

	/**
	 * Builds an html table with a random quote
	 * 
	 * @return String - fully formatted quote as html
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception {

		String theFormattedQuote = "";
		Quotes myQuote = new Quotes();
		myQuote.setConn(conn);

		try {
			if (url.length() > 0) {
				theFormattedQuote = "<a href=\"" + url + "\">";
			}
			theFormattedQuote += "<div class=\"vmodule\">\n"
					+ "<div class=\"vmoduleheading\">" + title + "</div>\n"
					+ "<div class=\"vmodulebody\">\n";
			myQuote.setConn(conn);
			theFormattedQuote += myQuote.getRandomQuote();
			theFormattedQuote += "</div></div>";
			if (url.length() > 0) {
				theFormattedQuote += "</a>";
			}
		} catch (Exception e) {
			Errors.addError("QuoteModule.getHtmlOutput:Exception:"
					+ e.toString());
		}
		return theFormattedQuote;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}