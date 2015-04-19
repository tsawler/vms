//------------------------------------------------------------------------------
//Copyright (c) 2008 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2008-11-11
//Revisions
//------------------------------------------------------------------------------
//$Log: AuctionModule.java,v $
//Revision 1.1.2.2  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2008/11/11 17:53:08  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.object.Errors;
import com.verilion.utility.TextUtils;

/**
* Auction stuff for module inclusion on html page
* <P>
*11 Nov 2008
* <P>
* 
* @author tsawler
*  
*/
public class AuctionModule implements ModuleInterface {

	/**
	 * Builds auction module
	 * @return String
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session, HttpServletRequest request) throws Exception {

		String theText = "";
		GenericTable myGt = new GenericTable("auctions");
		XDisconnectedRowSet crs = new XDisconnectedRowSet();
		
		try {	
			theText = "<div class=\"vmodule\">\n";
			boolean haveAuction = true;
			myGt.setConn(conn);
			crs = myGt.getAllActiveRecordsDisconnected();

			if (crs.next()) {
				crs.previous();
				while (crs.next()) {
					// read in the news items
				   String description = TextUtils.StripHtml(crs.getString("description"));
				   if (description.length() > 100) {
				      description = description.substring(0, 99);
				   }
					theText +="<div class=\"vmodulebody\">\n"
						+ "<h2><a href=\"/public/jpage/1/p/Auction/a/auction/id/"
				    	+ crs.getInt("id")
						+ "/content.do\">"
						+ "For Auction!"
						+ "</a></h2>\n"
						+ "<a href=\"/public/jpage/1/p/Auction/a/auction/id/"
				    	+ crs.getInt("id")
						+ "/content.do\">"
						+ "<img src=\""
						+ crs.getString("image")
						+ "\" /></a><strong>"
						+ crs.getString("title")
						+ "</strong><br />"
						+ description
						+ "...<br /><br />"
						+ "<a href=\"/public/jpage/1/p/Auction/a/auction/id/"
				    	+ crs.getInt("id")
						+ "/content.do\">Place a bid!"
						+ "</a><br /><br /></div>";
					    
				}
			} else {
				// No news items to display
				haveAuction = false;
			}
			if (haveAuction)
				theText += "\n</div></div>\n";
			else
				theText = "";

			crs.close();
			crs = null;
		} catch (Exception e) {
			Errors.addError("AuctionModule.getHtmlOutput:Exception:"
					+ e.toString());
		} finally {
		    if (crs != null) {
		        crs.close();
		        crs = null;
		    }
		}
		return theText;
	}
}