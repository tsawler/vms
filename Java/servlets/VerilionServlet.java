//------------------------------------------------------------------------------
//Copyright (c) 2003-3004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-16
//Revisions
//------------------------------------------------------------------------------
//$Log: VerilionServlet.java,v $
//Revision 1.1  2004/11/16 16:53:11  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package servlets;

import com.verilion.display.html.Page;

/**
 * <p>
 * Generic servlet for creating HTML pages from HTML templates stored in the
 * database. reads first three values in url as page name, action, language id.
 * Subsequent values in the url like this
 * </p>
 * <p>
 * http://site/Page/action/languageid/name/value/content.do)
 * </p>
 * <p>
 * are read as name value pairs
 * </p>
 * <P>
 * Nov 16, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class VerilionServlet extends Page {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

}