package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Add a document to a page
 * 
 * <P>
 * July 21 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class AddDocument extends SecurePage {

	private static final long serialVersionUID = -3149005369331575559L;

	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		int page_id = Integer
				.parseInt((String) request.getParameter("page_id"));

		try {
			MasterTemplate.searchReplace("$PAGEID$", page_id + "");

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.admin.EditPageDocuments:BuildPage:Exception:"
					+ e.toString());
		}
	}
}