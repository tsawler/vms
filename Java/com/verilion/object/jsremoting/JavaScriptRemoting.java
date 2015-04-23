// ------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-28
//Revisions
//------------------------------------------------------------------------------
//$Log: JavaScriptRemoting.java,v $
//Revision 1.1.2.4.4.3.4.2.2.9.2.2  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.4.4.3.4.2.2.9.2.1  2007/03/30 17:20:02  tcs
//*** empty log message ***
//
//Revision 1.1.2.4.4.3.4.2.2.9  2007/03/19 00:46:29  tcs
//Added method
//
//Revision 1.1.2.4.4.3.4.2.2.8  2007/03/15 17:49:54  tcs
//New validation method
//
//Revision 1.1.2.4.4.3.4.2.2.7  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.4.4.3.4.2.2.6  2007/03/05 17:11:48  tcs
//Added method
//
//Revision 1.1.2.4.4.3.4.2.2.5  2007/02/06 18:24:35  tcs
//Removed debugging info
//
//Revision 1.1.2.4.4.3.4.2.2.4  2007/02/06 13:45:14  tcs
//Changes for dwr 2
//
//Revision 1.1.2.4.4.3.4.2.2.3  2007/02/04 01:02:40  tcs
//Added method to init sessions
//
//Revision 1.1.2.4.4.3.4.2.2.2  2007/02/03 23:39:24  tcs
//Removed deprecated method
//
//Revision 1.1.2.4.4.3.4.2.2.1  2007/02/02 14:01:01  tcs
//removed deprecated method in DWR, and use webcontext instead
//
//Revision 1.1.2.4.4.3.4.2  2006/02/07 19:32:17  tcs
//Improved comments
//
//Revision 1.1.2.4.4.3.4.1  2006/01/31 18:59:15  tcs
//Added method for drag & drop menu sorting
//
//Revision 1.1.2.4.4.3  2005/08/23 00:44:06  tcs
//Working on URL validator
//
//Revision 1.1.2.4.4.2  2005/08/22 18:15:55  tcs
//Removed unusued var
//
//Revision 1.1.2.4.4.1  2005/08/22 14:22:32  tcs
//Added validators
//
//Revision 1.1.2.4  2005/06/10 00:44:23  tcs
//Typo in sql
//
//Revision 1.1.2.3  2005/05/28 15:34:35  tcs
//Closed pst, rs, & conn in a method
//
//Revision 1.1.2.2  2005/05/28 12:40:17  tcs
//In progress
//
//Revision 1.1.2.1  2005/05/28 12:18:01  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.object.jsremoting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.validator.CreditCardValidator;
import org.apache.commons.validator.DateValidator;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.verilion.database.DbBean;
import com.verilion.database.MenuItem;

/**
 * Methods that are remotely invoked by javascript.
 * 
 * @author Trevor
 * 
 */
public class JavaScriptRemoting {

	public JavaScriptRemoting() {

	}

	Connection conn = null;

	/**
	 * Allows any local URL available to the local container to be translated to
	 * string and returned to the calling javascript as fully formated html.
	 * Works with html files and JSPs. Takes the url as an arguement. URL must
	 * begin with a /, but can take parameters (?id=7&name=Jack, for example)
	 * and will pass them on to a JSP page. Can also return a URL that does not
	 * point to an actual file, i.e. servlet.
	 * 
	 * @param pFileToInclude
	 * @return external url as html
	 * @throws Exception
	 */
	public String getIncludedFile(String pFileToInclude) throws Exception {
		WebContext wctx = WebContextFactory.get();
		return wctx.forwardToString(pFileToInclude);
	}

	/**
	 * Checks to see if a url is in a valid format
	 * 
	 * @param theUrl
	 * @return True if valid url
	 */
	public boolean URLValid(String theUrl) {
		UrlValidator urlValidator = new UrlValidator(
				UrlValidator.ALLOW_2_SLASHES + UrlValidator.ALLOW_ALL_SCHEMES);
		boolean okay = urlValidator.isValid(theUrl);
		return okay;
	}

	public boolean isInteger(String theVal) {
		boolean okay = true;
		try {
			Integer.parseInt(theVal);
		} catch (Exception e) {
			okay = false;
		}
		return okay;
	}

	public boolean isNumeric(String theVal) {
		boolean okay = true;
		try {
			Integer.parseInt(theVal);
			okay = true;
		} catch (Exception e) {
			okay = false;
		}
		try {
			Double.valueOf(theVal).doubleValue();
			okay = true;
		} catch (Exception e) {
			okay = false;
		}
		return okay;
	}

	/**
	 * Checks for date to conform to yyyy-mm-dd format
	 * 
	 * @param theDate
	 * @return True if date in YYYY-MM-DD format
	 */
	public boolean DateValid(String theDate) {
		boolean isValid = false;
		isValid = DateValidator.getInstance().isValid(theDate, "yyyy-MM-dd",
				true);
		return isValid;
	}

	/**
	 * Just used to keep a session alive
	 * 
	 * @return boolean
	 */
	public boolean keepAlive() {
		return true;
	}

	/**
	 * Checks to see if email is in valid format
	 * 
	 * @param email
	 * @return true if email in valid format
	 */
	public boolean EmailValid(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	/**
	 * Checks to see if credit card is valid number
	 * 
	 * @param cardnum
	 * @return true if number passes modulus check
	 */
	public boolean CreditCardValid(String cardnum) {
		CreditCardValidator myValidator = new CreditCardValidator(
				CreditCardValidator.VISA + CreditCardValidator.MASTERCARD);
		return myValidator.isValid(cardnum);
	}

	public boolean userNameOkay(String username) {
		boolean isOkay = false;
		String query = "select count(customer_id) from customer where username ilike ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection conn = DbBean.getDbConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, username);
			rs = pst.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) < 1) {
					isOkay = true;
				} else {
					isOkay = false;
				}
			}
			rs.close();
			rs = null;
			pst.close();
			pst = null;
			DbBean.closeDbConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			isOkay = false;
		} catch (Exception e) {
			e.printStackTrace();
			isOkay = false;
		}
		return isOkay;
	}

	public boolean isValidUsername(String username) {
		boolean isOkay = false;
		String query = "select count(customer_id) from customer where username ilike ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection conn = DbBean.getDbConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, username);
			rs = pst.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) < 1) {
					isOkay = false;
				} else {
					isOkay = true;
				}
			}
			rs.close();
			rs = null;
			pst.close();
			pst = null;
			DbBean.closeDbConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			isOkay = false;
		} catch (Exception e) {
			e.printStackTrace();
			isOkay = false;
		}
		return isOkay;
	}

	/**
	 * Allows for menu items to be sorted using drag and drop
	 * 
	 * @param postval
	 */
	public void UpdateMenuItemOrder(String postval) {
		try {
			Connection conn = DbBean.getDbConnection();
			MenuItem myMenuItem = new MenuItem();
			myMenuItem.setConn(conn);
			myMenuItem.UpdateSortOrder(postval);
			DbBean.closeDbConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initSession() {
		WebContext wctx = WebContextFactory.get();
		wctx.getScriptSession();
	}

}