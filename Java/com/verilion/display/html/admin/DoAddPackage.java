package com.verilion.display.html.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.display.html.SecurePage;
import com.verilion.database.CtPackages;
import com.verilion.object.Errors;

/**
 * Adds a package to database
 * 
 * <P>
 * December 23, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DoAddPackage extends SecurePage {

	private static final long serialVersionUID = -1642591645664535480L;

	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		try {

			// get info from parameters
			String ct_package_name = (String) request
					.getParameter("package_name");
			Float Fobj = Float.valueOf(((String) request
					.getParameter("package_price")));
			float ct_package_price = Fobj.floatValue();
			String ct_package_description = (String) request
					.getParameter("package_description");

			CtPackages myPackages = new CtPackages();
			myPackages.setConn(conn);
			myPackages.setCt_package_name(ct_package_name);
			myPackages.setCt_package_price(ct_package_price);
			myPackages.setCt_package_description(ct_package_description);
			myPackages.setCt_package_active_yn("n");
			myPackages.addPackage();

			// Generate completion message
			session.setAttribute("Error", "Package successfully added.");
			response.sendRedirect("/servlet/com.verilion.display.html.admin.EditPackages?page=EditPackages");

		} catch (SQLException e) {
			Errors.addError("com.verilion.display.html.admin.DoAddPackage:BuildPage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.display.html.admin.DoEditPackage:BuildPage:SQLException:"
					+ e.toString());
		}
	}
}