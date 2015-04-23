//------------------------------------------------------------------------------
//Copyright (c) 2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-23
//Revisions
//------------------------------------------------------------------------------
//$Log: BannerTag.java,v $
//Revision 1.1.2.6.2.1  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.6  2007/03/15 17:48:55  tcs
//Updated for multiple banners in one spot
//
//Revision 1.1.2.5  2007/02/09 01:07:03  tcs
//Added sanity check for a url starting with http://
//
//Revision 1.1.2.4  2007/02/01 12:35:39  tcs
//Added alt tag to banner image
//
//Revision 1.1.2.3  2007/01/25 01:19:25  tcs
//Open banner in new window by default
//
//Revision 1.1.2.2  2007/01/23 19:28:17  tcs
//Handle case where there is no banner
//
//Revision 1.1.2.1  2007/01/23 14:39:28  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.sql.Connection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Banners;

/**
 * TagLib to display banner
 * 
 * @author tsawler
 * 
 */
public class BannerTag extends BaseTag {

	private static final long serialVersionUID = 1L;
	private XDisconnectedRowSet drs = new XDisconnectedRowSet();
	private String theHTML = "";
	int banner_id = 0;
	String banner_file = "";
	String url = "";
	Connection c = null;
	int ct_language_id = 1;
	String alt_tag = "";

	private int numberOfBanners = 0;
	private int position_id = 0;

	public int doStartTag() throws JspException {

		try {
			try {
				ct_language_id = Integer.parseInt((String) pc.getSession()
						.getAttribute("lang"));
			} catch (Exception e) {
				ct_language_id = 1;
			}
			Banners myBanner = new Banners();
			myBanner.setConn(conn);
			if (numberOfBanners < 2) {
				drs = myBanner.getRandomBannerForPositionId(position_id,
						ct_language_id);
			} else {
				drs = myBanner.getRandomBannersForPositionId(position_id,
						numberOfBanners, ct_language_id);
			}
			if (drs.next()) {
				drs.previous();
				theHTML = "<div id=\"";
				if (drs.getInt("ct_banner_position_id") == 1) {
					theHTML += "topbanner\">";
				} else if (drs.getInt("ct_banner_position_id") == 2) {
					theHTML += "rightbanner\">";
				} else if (drs.getInt("ct_banner_position_id") == 3) {
					theHTML += "leftbanner\">";
				} else if (drs.getInt("ct_banner_position_id") == 4) {
					theHTML += "bottombanner\">";
				} else {
					theHTML += "banner\">";
				}
				while (drs.next()) {
					String myUrl = drs.getString("url");
					if (!myUrl.startsWith("http://"))
						myUrl = "http://" + myUrl;

					theHTML += "<a target=\"_blank\" href=\"/bannerclick?id="
							+ drs.getInt("banner_id") + "&amp;url=" + myUrl
							+ "\"><img src=\"/banners/"
							+ drs.getString("banner_file") + "\" height=\""
							+ drs.getString("height") + "\" width=\""
							+ drs.getString("width")
							+ "\" style=\"border: 0;\" alt=\""
							+ drs.getString("alt_text") + "\" /></a>";
					myBanner.addOneToImpressions(drs.getInt("banner_id"));
					myBanner.addImpressionDetail(drs.getInt("banner_id"), pc
							.getRequest().getRemoteHost().toString());
					if (drs.next()) {
						drs.previous();
						theHTML += "<div>&nbsp;</div>";
					}
				}
				theHTML += "</div>";
			} else {
				theHTML = "";
			}
			pc.getOut().write(theHTML);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	public int getPosition_id() {
		return position_id;
	}

	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}

	public int getNumberOfBanners() {
		return numberOfBanners;
	}

	public void setNumberOfBanners(int numberfBanners) {
		this.numberOfBanners = numberfBanners;
	}
}