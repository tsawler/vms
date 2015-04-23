//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-14
//Revisions
//------------------------------------------------------------------------------
//$Log: MenuJsTag.java,v $
//Revision 1.1.2.2  2007/03/19 00:47:38  tcs
//In progress. Does nothing yet.
//
//Revision 1.1.2.1  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.sql.Connection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.verilion.database.DbBean;

/**
 * TagLib to create javascript for special menus, if required
 * 
 * @author tsawler
 * 
 */
public class MenuJsTag extends BaseTag {

	private static final long serialVersionUID = 1L;
	String menutag = "";
	private Connection c = null;
	private String theJavaScript = "";

	public int doStartTag() throws JspException {
		try {
			if (conn == null) {
				c = DbBean.getDbConnection();
			}

			// rs = myPageTemplate..
			if (conn == null) {
				DbBean.closeDbConnection(c);
			}

			pc.getOut().write(theJavaScript);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}
}