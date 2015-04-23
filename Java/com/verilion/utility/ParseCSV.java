//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-07-28
//Revisions
//------------------------------------------------------------------------------
//$Log: ParseCSV.java,v $
//Revision 1.1.2.1  2005/07/28 14:37:32  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ParseCSV extends StringTokenizer {
	private String tbt;
	private String d;
	private int startpos = 0;

	public ParseCSV(String str, String delim) {
		super(str, delim);
		tbt = new String(str);
		d = new String(delim);
	}

	public int countTokens() {
		int tokens = 0;
		int temp = startpos;
		while (true) {
			try {
				nextToken();
				tokens++;
			} catch (NoSuchElementException e) {
				break;
			}
		}
		startpos = temp;
		return tokens;
	}

	public boolean hasMoreElements() {
		return hasMoreTokens();
	}

	public boolean hasMoreTokens() {
		if (countTokens() > 0)
			return true;
		else
			return false;
	}

	public Object nextElement() {
		return (Object) d;
	}

	public String nextToken() throws NoSuchElementException {
		int result = 0;
		String s;

		if (startpos > tbt.length())
			throw (new NoSuchElementException());
		result = tbt.indexOf(d, startpos);
		if (result < 0)
			result = tbt.length();
		s = new String(tbt.substring(startpos, result));
		startpos = result + d.length();
		return s;
	}

	public String nextToken(String delim) throws NoSuchElementException {
		d = delim;
		return nextToken();
	}
}