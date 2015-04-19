//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: BuyerSelfAdmin.java,v $
//Revision 1.4.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4  2004/06/26 17:03:26  tcs
//Changes due to refactoring
//
//Revision 1.3  2004/06/26 03:16:02  tcs
//Modified to use XDisconnectedRowSets
//
//Revision 1.2  2004/06/24 17:58:03  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Customer;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Allow a buyer to edit their own information
 * 
 * <P>
 * February 17, 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class BuyerSelfAdmin extends SecurePage {

	/**
    * 
    */
   private static final long serialVersionUID = -5005408889667400281L;
   public XDisconnectedRowSet crs = new XDisconnectedRowSet();
	public int customer_id = 0;
	public String customer_email = "";
	public String customer_password = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.display.html.PageDb#BuildPage(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse,
	 *      javax.servlet.http.HttpSession)
	 */
	public void BuildPage(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session)
		throws Exception {

		try {

			// get our customer id from session
			customer_id =
				Integer.parseInt((String) session.getAttribute("customer_id"));

			// get our customer record
			Customer myCustomer = new Customer();
			myCustomer.setConn(conn);
			myCustomer.setCustomer_id(customer_id);
			crs = myCustomer.GetOneBuyerFromView();


			// extract info from customer table to local variables
			if (crs.next()) {
				customer_email = crs.getString("customer_email");
				customer_password = crs.getString("customer_password");
			}
			crs.close();
			crs = null;
			// Replace info on form with customer info
			MasterTemplate.searchReplace("$EMAIL$", customer_email);
			MasterTemplate.searchReplace("$PASSWORD$", customer_password);

			AddCustomInfo(request, response, session);

		}
		catch (Exception e) {
			e.printStackTrace();
			Errors.addError(
				"com.verilion.display.admin.EditCustomer:BuildPage:Exception:"
					+ e.toString());
		} finally {
		    if (crs != null) {
		        crs.close();
		        crs = null;
		    }
		}
	}

	/**
	 * Extend this class and override this method for client specific
	 * enhancements.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	public void AddCustomInfo(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session)
		throws Exception {

	}
}