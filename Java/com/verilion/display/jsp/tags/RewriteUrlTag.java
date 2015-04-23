//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-08
//Revisions
//------------------------------------------------------------------------------
//$Log: RewriteUrlTag.java,v $
//Revision 1.1.2.1  2005/03/08 16:38:21  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Rewrites URL to the format necessary to invoke controller servlet. Should be
 * used only to link to jsp pages handled by the controller servlet
 * 
 * @author tsawler
 * 
 */
public class RewriteUrlTag extends SimpleTagSupport {

	String page = "";
	String action = "";
	String displaystyle = "";
	String style = "";
	String tooltip = "";

	public void doTag() throws JspException, IOException {

		JspWriter out = getJspContext().getOut();
		JspFragment body = getJspBody();

		if (body != null) {
			out.print("<a ");
			if (style.length() > 0) {
				out.print(" class=\"" + style + "\" ");
			}
			if (tooltip.length() > 0) {
				out.print(" title=\"" + tooltip + "\" ");
			}
			if (displaystyle.length() > 0) {
				out.print(" style=\"" + displaystyle + "\" ");
			}
			out.print("href=\"/public/jpage/1/p/" + page);

			if (action.length() > 0) {
				out.print("/a/" + action + "/content.do\">");
			} else {
				out.print("/content.do\">");
			}
			body.invoke(null);
			out.println("</a>");
		}

	}

	/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return Returns the page.
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page
	 *            The page to set.
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return Returns the style.
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            The style to set.
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return Returns the displaystyle.
	 */
	public String getDisplaystyle() {
		return displaystyle;
	}

	/**
	 * @param displaystyle
	 *            The displaystyle to set.
	 */
	public void setDisplaystyle(String displaystyle) {
		this.displaystyle = displaystyle;
	}

	/**
	 * @return Returns the tooltip.
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * @param tooltip
	 *            The tooltip to set.
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}