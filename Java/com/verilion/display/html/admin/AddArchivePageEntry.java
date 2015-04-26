package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Form for adding entry to archive page.
 * 
 * <P>
 * Nov 28, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class AddArchivePageEntry extends SecurePage {

	private static final long serialVersionUID = -8378935471918149334L;

	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		try {
			MasterTemplate.searchReplace("$ARPAGEID$",
					(String) request.getParameter("archive_page_id"));

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("AddArchivePageEntry:BuildPage:Exception:"
					+ e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
	}
}