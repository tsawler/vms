package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.Customer;
import com.verilion.database.CustomerEmailDetail;
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
public class DoBuyerSelfAdmin extends SecurePage {

	private static final long serialVersionUID = -8365733499262379198L;
	public int customer_id = 0;
	public String customer_email = "";
	public String customer_password = "";
	public String customer_password2 = "";
	public boolean okay = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.display.html.PageDb#BuildPage(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * javax.servlet.http.HttpSession)
	 */
	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		try {

			// get our customer id from session
			customer_id = Integer.parseInt((String) session
					.getAttribute("customer_id"));

			// get parameters
			customer_email = (String) request.getParameter("email_address");
			customer_password = (String) request.getParameter("password");
			customer_password2 = (String) request.getParameter("password2");

			// passwords must match
			if (!(customer_password.equals(customer_password2))) {
				theError = "Passwords do not match!";
				session.setAttribute("Error", theError);
				okay = false;
			} else {
				okay = true;
			}

			AddCustomInfo(request, response, session);

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.admin.DoBuyerSelfAdmin:BuildPage:Exception:"
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

		if (okay) {
			// first update the password in the customer table
			Customer myCustomer = new Customer();
			myCustomer.setConn(conn);
			myCustomer.setCustomer_password(customer_password);
			myCustomer.setCustomer_id(customer_id);
			myCustomer.CustomerUpdateBuyer();

			// now update the email address
			CustomerEmailDetail myEmail = new CustomerEmailDetail();
			myEmail.setConn(conn);
			myEmail.setCustomer_id(customer_id);
			myEmail.setCustomer_email(customer_email);
			myEmail.updateCustomerEmailDetailPrimaryEmail();

			// set a success message and take them back
			session.setAttribute("Error", "Information successfully changed.");

		}
		response.sendRedirect("/servlet/com.verilion.display.html.admin.BuyerSelfEdit?page=BuyerSelfEdit");

	}
}