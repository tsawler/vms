//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-10
//Revisions
//------------------------------------------------------------------------------
//$Log: RSSFeedBoxModule.java,v $
//Revision 1.2.2.1.4.1.8.1.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.2.2.1.4.1.8.1  2007/03/14 00:18:48  tcs
//*** empty log message ***
//
//Revision 1.2.2.1.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.1  2005/04/27 14:59:23  tcs
//In progress...
//
//Revision 1.2  2004/11/10 19:52:15  tcs
//Implemented module interface
//
//Revision 1.1  2004/11/10 19:45:19  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.io.BufferedInputStream;
import java.net.URL;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.verilion.errorhandlers.ParseXMLErrorHandler;
import com.verilion.object.Errors;

/**
 * Displays RSS News feed(s) in formatted box
 * <P>
 * November 10, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class RSSFeedBoxModule implements ModuleInterface {

	/**
	 * Builds an html table with rss news feed
	 * 
	 * @return String - fully formatted quote as html
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception {

		String theFormattedQuote = "";

		try {

		} catch (Exception e) {
			Errors.addError("RSSFeedBoxModule.getHtmlOutput:Exception:"
					+ e.toString());
		}
		return theFormattedQuote;
	}

	/**
	 * Parse news feed passed as url string
	 * 
	 * @param psUrl
	 */
	public static final void ParseFeed(String psUrl) {

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);
			SAXParser parser = factory.newSAXParser();
			URL myUrl = new URL(psUrl);
			BufferedInputStream inStream = new BufferedInputStream(
					myUrl.openStream());

			// create a validating digester
			// Digester vDigester = new Digester();
			parser.parse(inStream, new ParseXMLErrorHandler());

		} catch (Exception e) {

		}
	}
}