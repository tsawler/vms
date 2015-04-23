//------------------------------------------------------------------------------
//Copyright (c) 2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-23
//Revisions
//------------------------------------------------------------------------------
//$Log: GoogleAdTag.java,v $
//Revision 1.1.2.5.2.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.5  2007/03/15 17:48:43  tcs
//Testing support for multiple publishers for a single space
//
//Revision 1.1.2.4  2007/01/24 12:18:26  tcs
//Complete and functional
//
//Revision 1.1.2.3  2007/01/24 02:47:53  tcs
//Removed useless getter/setter pair
//
//Revision 1.1.2.2  2007/01/24 02:46:42  tcs
//Fixed comment
//
//Revision 1.1.2.1  2007/01/24 02:46:23  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * TagLib to display google ad
 * 
 * @author tsawler
 * 
 */
public class GoogleAdTag extends BaseTag {

	private static final long serialVersionUID = 1L;

	private String theHTML = "";
	private String publisher_id = "";
	private String ad_format = "234x60_as";
	private String w = "234";
	private String h = "60";
	private String border_color = "FFFFFF";
	private String bg_color = "FFFFFF";
	private String link_color = "0000FF";
	private String text_color = "000000";
	private String url_color = "008000";

	public int doStartTag() throws JspException {
		try {

			theHTML = "<div class=\"googlead\">\n<script type=\"text/javascript\"><!--\n"
					+ "google_ad_client = \""
					+ publisher_id
					+ "\";\n"
					+ "google_ad_width = "
					+ w
					+ ";\n"
					+ "google_ad_height = "
					+ h
					+ ";\n"
					+ "google_ad_format = \""
					+ ad_format
					+ "\";\n"
					+ "google_ad_type = \"text_image\";\n"
					+ "google_ad_channel = \"\";\n"
					+ "google_color_border = \""
					+ border_color
					+ "\";\n"
					+ "google_color_bg = \""
					+ bg_color
					+ "\";\n"
					+ "google_color_link = \""
					+ link_color
					+ "\";\n"
					+ "google_color_text = \""
					+ text_color
					+ "\";\n"
					+ "google_color_url = \""
					+ url_color
					+ "\";\n"
					+ "//--></script>\n"
					+ "<script type=\"text/javascript\"\n"
					+ "  src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n"
					+ "</script>\n</div>\n";

			pc.getOut().write(theHTML);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	public String getAd_format() {
		return ad_format;
	}

	public void setAd_format(String ad_format) {
		this.ad_format = ad_format;
	}

	public String getBg_color() {
		return bg_color;
	}

	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}

	public String getBorder_color() {
		return border_color;
	}

	public void setBorder_color(String border_color) {
		this.border_color = border_color;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getLink_color() {
		return link_color;
	}

	public void setLink_color(String link_color) {
		this.link_color = link_color;
	}

	public String getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}

	public String getText_color() {
		return text_color;
	}

	public void setText_color(String text_color) {
		this.text_color = text_color;
	}

	public String getUrl_color() {
		return url_color;
	}

	public void setUrl_color(String url_color) {
		this.url_color = url_color;
	}

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}
}