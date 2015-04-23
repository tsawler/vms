//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-10
//Revisions
//------------------------------------------------------------------------------
//$Log: ModuleInterface.java,v $
//Revision 1.1.2.1.14.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2005/04/29 13:28:57  tcs
//Made public
//
//Revision 1.1  2004/11/10 19:51:50  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Define methods required for all module objects implementing this interface.
 * 
 * @author tsawler
 * @since 1.2.12
 * 
 */
public interface ModuleInterface {

	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception;

}