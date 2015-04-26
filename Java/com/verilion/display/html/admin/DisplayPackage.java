package com.verilion.display.html.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.CtPackages;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Displays individual package for editing
 * 
 * <P>
 * December 23, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DisplayPackage extends SecurePage {

	private static final long serialVersionUID = -6928594373353882138L;

	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		XDisconnectedRowSet rs = new XDisconnectedRowSet();

		try {
			// get info from parameters
			int ct_package_id = Integer.parseInt((String) request
					.getParameter("id"));
			CtPackages myPackages = new CtPackages();
			myPackages.setConn(conn);
			myPackages.setCt_package_id(ct_package_id);
			rs = myPackages.getPackageDetail();

			if (rs.next()) {
				MasterTemplate.searchReplace("$PACKAGEID$", ct_package_id + "");
				MasterTemplate.searchReplace("$NAME$",
						rs.getString("ct_package_name"));
				MasterTemplate.searchReplace("$PRICE$",
						rs.getFloat("ct_package_price") + "");
				MasterTemplate.searchReplace("$DESCRIPTION$",
						rs.getString("ct_package_description"));
				if (rs.getString("ct_package_active_yn").equals("y")) {
					MasterTemplate.searchReplace("$CHECKED$", "checked");
				} else {
					MasterTemplate.searchReplace("$CHECKED$", "");
				}
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			Errors.addError("com.verilion.display.html.admin.DisplayPackage:BuildPage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.display.html.admin.DisplayPackage:BuildPage:SQLException:"
					+ e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
	}
}