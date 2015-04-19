//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-03
//Revisions
//------------------------------------------------------------------------------
//$Log: MenuTag.java,v $
//Revision 1.1.2.3.4.1.6.2.2.1.2.3  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.3.4.1.6.2.2.1.2.2  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.3.4.1.6.2.2.1.2.1  2007/08/13 19:06:39  tcs
//Added support for style
//
//Revision 1.1.2.3.4.1.6.2.2.1  2007/01/28 00:49:43  tcs
//Added session to method call
//
//Revision 1.1.2.3.4.1.6.2  2006/12/26 12:15:11  tcs
//Corrected Javadocs
//
//Revision 1.1.2.3.4.1.6.1  2006/12/19 19:28:41  tcs
//*** empty log message ***
//
//Revision 1.1.2.3.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.3  2005/03/06 10:43:19  tcs
//Chanaged to derive from superclass
//
//Revision 1.1.2.2  2005/03/04 14:24:18  tcs
//Modified to get req/res from page context instead of parameters
//
//Revision 1.1.2.1  2005/03/03 17:11:41  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.verilion.object.GetMenu;

/**
 * TagLib to display Main Menu.
 * 
 * @author tsawler
 * 
 */
public class MenuTag extends BaseTag {

	private static final long serialVersionUID = -1222485868918330840L;
	String menutag = "";
	String pagegroup = "";
	private String style = "";

	public int doStartTag() throws JspException {
		try {

			GetMenu.setConn(conn);
			String theMenu = "";
			int ct_language_id = 1;

			if (pc.getSession().getAttribute("lang") != null) {
				ct_language_id = Integer.parseInt((String) pc.getSession()
						.getAttribute("lang"));
			}

			if (menutag.length() != 0) {
				if (style.length() == 0) {
					theMenu = GetMenu.getHTMLMenu(menutag, ct_language_id, "n",
							(HttpServletRequest) pc.getRequest(),
							(HttpServletResponse) pc.getResponse(),
							(HttpSession) pc.getSession());
				} else {

				}
				
			} else if (pagegroup.length() != 0) {
				theMenu = GetMenu.getHTMLPageGroupMenu(pagegroup,
						ct_language_id, "n", (HttpServletRequest) pc
								.getRequest(), (HttpServletResponse) pc
								.getResponse(), (HttpSession) pc.getSession());

			} else {
				if (style.length() == 0) {
					theMenu = GetMenu.getHTMLMenu("Main Menu", ct_language_id,
							"n", (HttpServletRequest) pc.getRequest(),
							(HttpServletResponse) pc.getResponse(),
							(HttpSession) pc.getSession());
				} else {

				}
			}

			pc.getOut().write(theMenu);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	public String getMenutag() {
		return menutag;
	}

	/**
	 * @param intag
	 *            The heights to set.
	 */
	public void setMenutag(String intag) {
		this.menutag = intag;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getPagegroup() {
		return pagegroup;
	}

	public void setPagegroup(String pagegroup) {
		this.pagegroup = pagegroup;
	}
}