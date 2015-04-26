package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.Customer;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Allow a client to change the picture for their customer record
 * 
 * <P>
 * February 20, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ClientPictureAdmin extends SecurePage {

	/**
    * 
    */
	private static final long serialVersionUID = -507176929324050626L;
	public int customer_id = 0;
	public String customer_email = "";
	public String customer_password = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.display.html.PageDb#BuildPage(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * javax.servlet.http.HttpSession)
	 */
	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		int image_id = 0;

		try {
			// get our customer id from session
			customer_id = Integer.parseInt((String) session
					.getAttribute("customer_id"));

			// get the image id for this customer id
			Customer myCustomer = new Customer();
			myCustomer.setConn(conn);
			myCustomer.setCustomer_id(customer_id);
			image_id = myCustomer.getCustomersImageId();

			MasterTemplate.searchReplace("$IID$", image_id + "");

			AddCustomInfo(request, response, session);

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.admin.EditCustomer:BuildPage:Exception:"
					+ e.toString());
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
	public void AddCustomInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

	}
}