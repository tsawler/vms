//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: DBUtils.java,v $
//Revision 1.1.2.2  2005/04/26 14:17:09  tcs
//Improvements to way rows are counted
//
//Revision 1.1.2.1  2004/12/18 23:45:28  tcs
//Modifications for paging results table
//
//Revision 1.1  2004/05/23 04:52:45  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.verilion.object.Errors;


/**
 * Utils for the database (quotes, sql field val, etc)
 * <P>
 * Nov 5, 2003
 * <P>
 * package com.verilion
 * <P>
 * @author tsawler
 * 
 */
public class DBUtils {
   
   Statement st = null;
   ResultSet rs = null;
   Connection conn = null;
	
	/**
	 * Fixes single quotes for sql values.
	 * 
	 * @param value
	 * @return String
	 */
	public String fixSqlFieldValue(String value) {
		if (value == null)
			return null;
		int length = value.length();
		StringBuffer fixedValue = new StringBuffer((int) (length * 1.1));
		for (int i = 0; i < length; i++) {
			char c = value.charAt(i);
			if (c == '\'')
				fixedValue.append("''");
			else
				fixedValue.append(c);
		}
		return fixedValue.toString();
	}

	/**
	 * Removes doubled single quotes from string.
	 * 
	 * @param value
	 * @return String
	 */
	public String removeDoubleQuotes(String value) {
		char c;
		char last;
		if (value == null)
			return null;
		int length = value.length();
		StringBuffer fixedValue = new StringBuffer((int) length);
		last = value.charAt(0);
		fixedValue.append(last);
		for (int i = 1; i < length; i++) {
			c = value.charAt(i);
			if (!((c == '\'') && (last == '\''))) {
				fixedValue.append(c);
			}
			last = value.charAt(i);
		}
		return fixedValue.toString();
	}

	/**
	 * Searches and replaces values in a supplied string.
	 * 
	 * @param orig
	 * @param from
	 * @param to
	 * @return String
	 */
	public String replace(String orig, String from, String to) {
		int fromLength = from.length();

		if (fromLength == 0)
			throw new IllegalArgumentException("String to be replaced must not be empty");

		int start = orig.indexOf(from);
		if (start == -1)
			return orig;

		boolean greaterLength = (to.length() >= fromLength);

		StringBuffer buffer;
		// If the "to" parameter is longer than (or
		// as long as) "from", the final length will
		// be at least as large
		if (greaterLength) {
			if (from.equals(to))
				return orig;
			buffer = new StringBuffer(orig.length());
		} else {
			buffer = new StringBuffer();
		}

		char[] origChars = orig.toCharArray();

		int copyFrom = 0;
		while (start != -1) {
			buffer.append(origChars, copyFrom, start - copyFrom);
			buffer.append(to);
			copyFrom = start + fromLength;
			start = orig.indexOf(from, copyFrom);
		}
		buffer.append(origChars, copyFrom, origChars.length - copyFrom);

		return buffer.toString();
	}
	
	public int countRowsForTable(String theTable, String theCondition, String theColumn) throws SQLException,
	   Exception {

	      int rowCount = 0;
	      
	      try {
	         String query = "select count(" 
	            + theColumn
	            + ") from "
	            + theTable;
	         
	         if (theCondition.length() > 6) 
	            query += " "
	               + theCondition;

	         st = conn.createStatement();
	         rs = st.executeQuery(query);
	         
	         if (rs.next())
	            rowCount = rs.getInt(1);
	         rs = null;
	         st.close();
	         st = null;
	      } catch (SQLException e) {
	         Errors
	               .addError("DBUtils:countRowsForTable:SQLException:"
	                     + e.toString());
	      } catch (Exception e) {
	         Errors
	               .addError("DBUtils:countRowsForTable:Exception:"
	                     + e.toString());
	      } finally {
	         if (rs != null) {
	            rs.close();
	            rs = null;
	         }
	         if (st != null) {
	            st.close();
	            st = null;
	         }
	      }
	      return rowCount;
	   }
   /**
    * @return Returns the conn.
    */
   public Connection getConn() {
      return conn;
   }
   /**
    * @param conn The conn to set.
    */
   public void setConn(Connection conn) {
      this.conn = conn;
   }
   /**
    * @return Returns the rs.
    */
   public ResultSet getRs() {
      return rs;
   }
   /**
    * @param rs The rs to set.
    */
   public void setRs(ResultSet rs) {
      this.rs = rs;
   }
   /**
    * @return Returns the st.
    */
   public Statement getSt() {
      return st;
   }
   /**
    * @param st The st to set.
    */
   public void setSt(Statement st) {
      this.st = st;
   }
}