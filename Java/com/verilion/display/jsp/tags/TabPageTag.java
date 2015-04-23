//------------------------------------------------------------------------------
//Copyright (c) 2005-2006 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-03-28
//Revisions
//------------------------------------------------------------------------------
//$Log: TabPageTag.java,v $
//Revision 1.1.2.1  2006/03/30 19:46:21  tcs
//In progress...
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

/**
 * Top level tab page tag
 * 
 * @author tsawler
 * 
 */
public class TabPageTag extends BaseTag {

	private static final long serialVersionUID = 5706910740179264083L;

	public void setPageContext(PageContext p) {
		pc = p;
	}

	/**
	 * TODO this is in progress
	 */
	public int doStartTag() throws JspException {
		try {
			pc.getOut()
					.write("<script type=\"text/javascript\" src=\"tabjs.js\"></script>");

		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

}