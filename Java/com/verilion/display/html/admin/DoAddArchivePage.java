package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.ArchivePage;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Adds an archive page.
 * 
 * <P>
 * July 26, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DoAddArchivePage extends SecurePage {
	private static final long serialVersionUID = 260519625965602176L;

	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		String pageName = "";
		int templateId = 0;
		int accessLevelId = 0;
		String theContents = "";
		int parentId = 0;
		boolean exists = false;
		boolean addedPage = false;
		;
		String page_title = "";
		int action = 0;

		try {
			action = Integer.parseInt((String) request.getParameter("action"));

			// now perform the appropriate action
			// 1 = save
			// 2 = cancel

			switch (action) {
			case 1:

				// get parameters passed by form, and put them in the
				// format we need.
				pageName = (String) request.getParameter("page_name");
				templateId = Integer.parseInt((String) request
						.getParameter("template_id"));
				accessLevelId = Integer.parseInt((String) request
						.getParameter("access_level_id"));
				parentId = Integer.parseInt((String) request
						.getParameter("parent_archive_page_id"));
				theContents = (String) request.getParameter("contents");

				page_title = (String) request.getParameter("page_title");

				// only do the add if the user has no spaces or punctuation
				// in page
				// name
				if ((pageName.indexOf(" ") == -1)
						&& (pageName.indexOf("'") == -1)
						&& (pageName.indexOf("\"") == -1)
						&& (pageName.indexOf("&") == -1)
						&& (pageName.indexOf("?") == -1)) {

					// set database connection to ArchivePage
					ArchivePage myArchivePage = new ArchivePage();
					myArchivePage.setConn(conn);

					// Check to see if this is an existing page name
					myArchivePage.setArchive_page_name(pageName);
					exists = myArchivePage.checkForExistingPageName();

					if (!exists) {
						// this page does not exist yet, so add the record
						// to ArchivePage
						myArchivePage.setArchive_page_name(pageName);
						myArchivePage.setTemplate_id(templateId);
						myArchivePage.setCt_access_level_id(accessLevelId);
						myArchivePage.setArchive_page_active_yn("n");
						myArchivePage.setArchive_page_title(page_title);
						myArchivePage.setArchive_page_content(theContents);
						myArchivePage.setParent_id(parentId);

						myArchivePage.addArchivePage();

						addedPage = true;
					}

					if (addedPage) { // Generate completion message
						session.setAttribute("Error", "Add successful");
					} else {
						// Generate completion message
						session.setAttribute("Error",
								"Error! You must use a unique page name!");
						response.sendRedirect("/servlet/com.verilion.display.html."
								+ "admin.AddArchivePage?page=AddArchivePage");
					}
					response.sendRedirect("/servlet/com.verilion.display.html.admin."
							+ "EditArchivePageChoose?page=EditArchivePageChoose");
				} else {
					// Can't have spaces in page names
					session.setAttribute("Error",
							"You can't have spaces or punctuation in your page name!");
					response.sendRedirect("/servlet/com.verilion.display.html."
							+ "admin.AddArchivePage?page=AddArchivePage");
				}
				break;

			case 2:
				session.setAttribute("Error", "Cancelled.");
				response.sendRedirect("/servlet/com.verilion.display.html.admin."
						+ "EditArchivePageChoose?page=EditArchivePageChoose");
				break;
			}
		} catch (Exception e) {
			Errors.addError("DoAddArchivePage:BuildPage:Exception:"
					+ e.toString());
		}
	}
}