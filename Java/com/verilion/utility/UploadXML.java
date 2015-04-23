//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-14
//Revisions
//------------------------------------------------------------------------------
//$Log: UploadXML.java,v $
//Revision 1.2.2.1.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.1  2004/12/14 17:13:26  tcs
//Added explicit serial uid
//
//Revision 1.2  2004/06/15 02:42:50  tcs
//Added case for template upload
//
//Revision 1.1  2004/06/14 18:29:09  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

/**
 * Handles uploading of XML files via http upload. Must be accompanied by an
 * integer describing the action to be taken, and routes uploaded file to an XML
 * parse specification appropriately. <br/>
 * <br />
 * So far we handle these actions:
 * <ul>
 * <li>1 = upload of user data in XML format</li>
 * <li>2 = upload of template file descriptor
 * </ul>
 * <br />
 * June 14, 2004 <br />
 * 
 * @author tsawler
 * 
 */
public class UploadXML extends HttpServlet {

	private static final long serialVersionUID = 3258688806134887989L;
	private String type = "";
	private String filename = "";
	private String thePath = "";
	private boolean okay = true;
	private String theErrorMessage = "";
	private int action = 0;

	public void init(ServletConfig config) {
		ServletContext context = config.getServletContext();
		thePath = (String) context.getAttribute("uploadPath");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get the session
		HttpSession session = request.getSession(true);

		// Kick them out if they aren't supposed to be here
		if (session.getAttribute("user") == null) {
			// our user is not logged in, so they shouldn't be here
			session.setAttribute("Error",
					"You must log in before accessing the requested page!");

		} else {
			if (Integer.parseInt((String) session
					.getAttribute("customer_access_level")) < 3) {
				// user does not have sufficient rights to be here
				session.setAttribute("Error",
						"You do not have access to the requested page!");
			}
		}

		try {
			// create a new MultiPartRequest object, 10 megs max
			MultipartRequest multi = new MultipartRequest(request, thePath,
					10000 * 1024);
			Enumeration files = multi.getFileNames();

			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				type = multi.getContentType(name);
			}

			// get our action
			action = Integer.parseInt((String) multi.getParameter("action"));

			try {
				// make sure we have a file, and that it's of the right type
				if (type == null) {
					theErrorMessage = "You must specifiy a file to upload!";
					okay = false;
				} else if (!(type.equals("text/xml"))) {
					theErrorMessage = "Uploaded file must be in xml format!";
					okay = false;
				}

				if (okay) {
					// decide where to go based on action

					switch (action) {
					case 1:
						ParseUserXML.parseFile(thePath + "/" + filename);
						break;
					case 2:
						ParseTemplateXML.parseFile(thePath + "/" + filename);
						break;
					}

					// additional actions handled here

					// redirect with message
					session.setAttribute("Error", "File upload successful");
					response.sendRedirect((String) session
							.getAttribute("last_page"));
				} else {
					// send them back with an error message
					session.setAttribute("Error", theErrorMessage);
					response.sendRedirect((String) session
							.getAttribute("last_page"));
				}

			} catch (java.io.FileNotFoundException e) {
				System.err.print("FileNotFoundException: ");
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (java.io.IOException e) {
			e.printStackTrace();
			session.setAttribute("Error",
					"File is too large! Maximum 10 meg file size");
			response.sendRedirect("/blewup.html");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}