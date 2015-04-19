//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-03
//Revisions
//------------------------------------------------------------------------------
//$Log: NewsModule.java,v $
//Revision 1.17.2.3.4.1.6.1.2.2.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.17.2.3.4.1.6.1.2.2  2007/02/25 01:52:43  tcs
//Converted to use divs
//
//Revision 1.17.2.3.4.1.6.1.2.1  2007/02/01 12:34:36  tcs
//Corrected generated html format
//
//Revision 1.17.2.3.4.1.6.1  2006/01/31 18:59:28  tcs
//Clean up
//
//Revision 1.17.2.3.4.1  2005/08/16 00:58:23  tcs
//Changed to make links use external JSP templates (much faster)
//
//Revision 1.17.2.3  2005/02/23 17:12:09  tcs
//Changed to give RSS feed a .doxml extension (simplifies setting response header)
//
//Revision 1.17.2.2  2004/12/11 22:21:42  tcs
//Added ellipses to generated text
//
//Revision 1.17.2.1  2004/12/11 18:50:38  tcs
//Added title tag to generated links.
//
//Revision 1.17  2004/11/10 19:52:15  tcs
//Implemented module interface
//
//Revision 1.16  2004/10/18 18:21:00  tcs
//Changed to match new URL structure
//
//Revision 1.15  2004/07/25 19:00:18  tcs
//moved class=newsbox entry
//
//Revision 1.14  2004/07/24 02:01:42  tcs
//Modified to use custom style newsbox; returned to table for layout
//
//Revision 1.13  2004/07/20 14:50:17  tcs
//Moved location of rss pic
//
//Revision 1.12  2004/07/12 20:20:24  tcs
//*** empty log message ***
//
//Revision 1.11  2004/07/12 18:39:05  tcs
//Added link to RSS feed
//
//Revision 1.10  2004/07/09 18:40:13  tcs
//Changed to use div instead of table tags
//
//Revision 1.9  2004/06/29 17:39:02  tcs
//Mods for cookieless browsers
//
//Revision 1.8  2004/06/25 16:43:38  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.7  2004/06/24 17:58:19  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.6  2004/06/06 21:06:53  tcs
//*** empty log message ***
//
//Revision 1.5  2004/06/06 01:13:57  tcs
//Changes due to refactoring
//
//Revision 1.4  2004/06/05 18:35:10  tcs
//Removed hardcoded style info
//
//Revision 1.3  2004/06/05 02:56:34  tcs
//Complete to version 1.0, as external module
//
//Revision 1.2 2004/06/04 18:46:32 tcs
//Version 1.0 complete
//
//Revision 1.1 2004/06/04 16:53:17 tcs
//Initial entry into CVS - using as stub; not finished
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.NewsItem;
import com.verilion.object.Errors;
import com.verilion.utility.TextUtils;

/**
* Gets news headlines and items for inclusion on html page
* <P>
* Jun 3, 2004
* <P>
* 
* @author tsawler
*  
*/
public class NewsModule implements ModuleInterface {

	/**
	 * Builds an html table with news headlines for inclusion on page
	 * 
	 * @return String - fully formatted news headlines as html
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session, HttpServletRequest request) throws Exception {

		String theNewsItems = "";
		NewsItem myNews = new NewsItem();
		XDisconnectedRowSet crs = new XDisconnectedRowSet();
		
		try {	
			theNewsItems = "<div class=\"vmodule\">\n"
			    + "<div class=\"vmoduleheading\">Latest News</div>\n"
                + "<div class=\"vmodulebody\">\n"
                + "<ul>\n";

			myNews.setConn(conn);
			crs = myNews.getNewsHeadlines();

			if (crs.next()) {
				crs.previous();
				while (crs.next()) {
					// read in the news items
				   String theTitleText = TextUtils.StripHtml(crs.getString("news_teaser_text"));
				   if (theTitleText.length() > 100) {
				      theTitleText = theTitleText.substring(0, 99);
				   }
					theNewsItems +="<li>"
					    + "<a title=\""
					    + theTitleText
					    + "..."
					    + "\" href=\"/public/jpage/1/p/News/a/newsitem/id/"
				    	+ crs.getInt("news_id")
						+ "/content.do\">"
						+ crs.getString("news_title")
						+ "</a></li>\n";
					    
				}
			} else {
				// No news items to display
				theNewsItems += "<li>Currently unavailable</li>\n";
			}
			theNewsItems += "</ul>\n<div style=\"text-align: center\"><a href=\"/newsfeed.doxml\">"
			   + "<img src=\"/images/rss.png\" height=\"14\" "
			   + "width=\"36\" style=\"border: 0\" alt=\"rss\""
			   + " title=\"Subscribe to our RSS news feed\" /></a>\n</div>\n</div>\n</div>\n";

			crs.close();
			crs = null;
		} catch (Exception e) {
			Errors.addError("NewsModule.getNewsHeadlines:Exception:"
					+ e.toString());
		} finally {
		    if (crs != null) {
		        crs.close();
		        crs = null;
		    }
		}
		return theNewsItems;
	}
}