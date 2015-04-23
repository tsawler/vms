//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-02-25
//Revisions
//------------------------------------------------------------------------------
//$Log: TestSocket.java,v $
//Revision 1.1.2.1  2005/02/25 13:25:01  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Just open a test socket to specified server/port.
 * 
 * @author tsawler
 *
 */
public class TestSocket {

	/**
	 * Just open a test socket to specified server/port.
	 * 
	 * @param host
	 * @param port
	 * @return true if up, false if down
	 * @throws IOException
	 */
	public boolean CheckSocket(String host, int port) throws IOException {

		Socket echoSocket = null;
		boolean okay = true;

		try {
			echoSocket = new Socket(host, port);
		} catch (UnknownHostException e) {
			okay = false;
		} catch (IOException e) {
			okay = false;
		}

		echoSocket.close();

		return okay;
	}

}