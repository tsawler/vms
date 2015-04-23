//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-11-29
//Revisions
//------------------------------------------------------------------------------
//$Log: StoreMenuTag.java,v $
//Revision 1.1.2.1.2.1  2006/12/26 12:15:28  tcs
//Corrected Javadocs
//
//Revision 1.1.2.1  2005/11/30 19:46:44  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.verilion.object.html.store.Store;

/**
 * TagLib to display menu for shopping.
 * 
 * @author tsawler
 * 
 */
public class StoreMenuTag extends BaseTag {

	private static final long serialVersionUID = -5747633735610809237L;
	private int mid = 0;

	public int doStartTag() throws JspException {
		try {
			String theMenu = Store.MakeMenuHtml(conn, mid, pc.getSession());
			pc.getOut().write(theMenu);
		} catch (Exception e) {
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	/**
	 * @return Returns the position.
	 */
	public int getMid() {
		return mid;
	}

	/**
	 * @param mid
	 *            The position to set.
	 */
	public void setMid(int mid) {
		this.mid = mid;
	}
}