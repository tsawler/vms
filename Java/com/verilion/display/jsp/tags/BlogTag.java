// ------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-08-24
//Revisions
//------------------------------------------------------------------------------
//$Log: BlogTag.java,v $
//Revision 1.1.2.1  2005/08/25 01:50:52  tcs
//Initial entry - stub
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * TagLib to display Blog page, and editor (if you're signed in)
 * 
 * @author tsawler
 *
 */
public class BlogTag extends BaseTag {

	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException {
		try {

		} catch (Exception e) {
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}
}