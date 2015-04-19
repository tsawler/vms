//------------------------------------------------------------------------------
//Copyright (c) 2008 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2008-11-11
//Revisions
//------------------------------------------------------------------------------
//$Log: NewsSummaryModule.java,v $
//Revision 1.1.2.2  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2008/11/11 17:53:19  tcs
//Initial entry
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
* Gets news headlines and summary for inclusion on html page
* <P>
*11 Nov 2008
* <P>
* 
* @author tsawler
*  
*/
public class NewsSummaryModule implements ModuleInterface {

	/**
	 * Builds news headlines & Summary for inclusion on page
	 * 
	 * @return String - fully formatted news headlines as html
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session, HttpServletRequest request) throws Exception {

		String theNewsItems = "";
		NewsItem myNews = new NewsItem();
		XDisconnectedRowSet crs = new XDisconnectedRowSet();
		
		try {	
			theNewsItems = "<div class=\"vmodule\">\n";

			myNews.setConn(conn);
			crs = myNews.getNewsHeadlines();
			
			int i = 0;

			if (crs.next()) {
				crs.previous();
				while (crs.next() && i < 3) {
					i++;
					// read in the news items
				   String theTitleText = TextUtils.StripHtml(crs.getString("news_teaser_text"));
				   if (theTitleText.length() > 100) {
				      theTitleText = theTitleText.substring(0, 99);
				   }
					theNewsItems +="<div class=\"vmodulebody\">\n"
						+ "<h2><a href=\"/public/jpage/1/p/News/a/newsitem/id/"
				    	+ crs.getInt("news_id")
						+ "/content.do\">"
						+ crs.getString("news_title")
						+ "</a></h2>\n"
						+ theTitleText
						+ "...<br /><br /></div>";
					    
				}
			} else {
				// No news items to display
				theNewsItems += "<div class=\"vmodulebody\">Currently unavailable</div>\n";
			}
			theNewsItems += "\n<div style=\"text-align: center\"><a href=\"/newsfeed.doxml\">"
			   + "<br /><img src=\"/images/rss.png\" height=\"14\" "
			   + "width=\"36\" style=\"border: 0\" alt=\"rss\""
			   + " title=\"Subscribe to our RSS news feed\" /></a></div></div>\n";

			crs.close();
			crs = null;
		} catch (Exception e) {
			Errors.addError("NewsSummaryModule.getNewsHeadlines:Exception:"
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