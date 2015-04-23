//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-08-11
//Revisions
//------------------------------------------------------------------------------
//$Log: ExecuteExternal.java,v $
//Revision 1.1.2.1.10.1  2007/10/30 17:45:17  tcs
//Added shell to cmd
//
//Revision 1.1.2.1  2005/08/12 16:57:44  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Execute an external program
 * 
 * @author Trevor
 * 
 */
public class ExecuteExternal {
	public String CmdExec(String cmdline) {

		String theResult = "";
		try {
			String[] cmd = { "/bin/bash", "-c", cmdline };
			String line;
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				theResult += line;
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return theResult;
	}
}