//------------------------------------------------------------------------------
//Copyright (c) 2003-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2009-04-30
//Revisions
//------------------------------------------------------------------------------
//$Log: ShoutBoxModuleObject.java,v $
//Revision 1.1.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

public class ShoutBoxModuleObject {

	private static String lastShout = new String();

	public String getMostRecentShout() {
		return lastShout;
	}

	public void setMostRecentShout(String theShout) {
		lastShout = theShout;
	}
}