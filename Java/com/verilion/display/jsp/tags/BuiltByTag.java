//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-08
//Revisions
//------------------------------------------------------------------------------
//$Log: BuiltByTag.java,v $
//Revision 1.1.2.2.4.1.10.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.2.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.2  2005/03/08 12:29:54  tcs
//Complete
//
//Revision 1.1.2.1  2005/03/08 11:23:12  tcs
//In progress
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * Builds generated comment tag
 * 
 * @author tsawler
 * 
 */
public class BuiltByTag extends BaseTag {

	private static final long serialVersionUID = -3419589131660336867L;

	public int doStartTag() throws JspException {
		try {
			if (pc.getSession().getAttribute("gMsg") != null) {
				String theMessage = pc.getSession().getAttribute("gMsg")
						.toString();
				pc.getOut().write(theMessage);
				pc.getSession().removeAttribute("gMsg");
			}
		} catch (Exception e) {
			// throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

}