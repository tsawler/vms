//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-05
//Revisions
//------------------------------------------------------------------------------
//$Log
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.verilion.object.Errors;

/**
 * Simple stub module example
 * 
 * @author tsawler
 * 
 */
public class SimpleTestModule implements ModuleInterface {

	/**
	 * Simplest possible module
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception {

		String theString = "";

		try {
			theString = "<table class=\"quoteBox\"><tr><td>";
			theString += "Hello, world.</td></tr>";
			theString += "</table>";
		} catch (Exception e) {
			Errors.addError("TestModule.getHtmlOutput:Exception:"
					+ e.toString());
		}
		return theString;
	}
}