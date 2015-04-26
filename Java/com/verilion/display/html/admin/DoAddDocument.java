package com.verilion.display.html.admin;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.Documents;
import com.verilion.database.SingletonObjects;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Worker for adding document to a page.
 * 
 * <P>
 * July 21, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DoAddDocument extends SecurePage {

	private static final long serialVersionUID = 1727822344892889895L;

	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		int action = 0;
		int page_id = 0;
		String thePath = "";
		String filename = "";
		String type = "";

		try {
			// get info from post
			thePath = SingletonObjects.getInstance().getUpload_path();
			Documents myDoc = new Documents();

			MultipartRequest multi = new MultipartRequest(request, thePath,
					10000 * 1024);

			Enumeration files = multi.getFileNames();

			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				type = multi.getContentType(name);
			}

			action = Integer.parseInt((String) multi.getParameter("action"));
			page_id = Integer.parseInt((String) multi.getParameter("page_id"));

			// now perform the appropriate action
			// 1 = save
			// 2 = cancel

			switch (action) {
			case 1:

				try {
					if (type == null) {
						// we didn't get a file uploaded, so send them
						// back with an error message
						session.setAttribute("Error",
								"You must specify an file to upload!");
						response.sendRedirect(response
								.encodeRedirectURL((String) session
										.getAttribute("lastPage")));
					}
					myDoc.setConn(conn);
					myDoc.setPage_id(page_id);
					myDoc.setDocument_active_yn("n");
					myDoc.setDocument_mime_type(type);
					myDoc.setDocument_name(filename);
					myDoc.setDocument_title((String) multi
							.getParameter("document_title"));
					myDoc.addDocument(thePath + filename);
					session.setAttribute("Error", "Success.");
					response.sendRedirect("/servlet/com.verilion.display.html.admin."
							+ "EditPageDocuments?page=EditPageDocuments&page_id="
							+ page_id);

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 2:
				session.setAttribute("Error", "Cancelled.");
				response.sendRedirect("/servlet/com.verilion.display.html.admin."
						+ "EditPageDocuments?page=EditPageDocuments&page_id="
						+ page_id);
				break;
			}
		} catch (Exception e) {
			Errors.addError("com.verilion.display.admin.DoAddDocument:BuildPage:Exception:"
					+ e.toString());
		}
	}
}