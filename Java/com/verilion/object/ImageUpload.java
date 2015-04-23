//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-04
//Revisions
//------------------------------------------------------------------------------
//$Log: ImageUpload.java,v $
//Revision 1.1.2.2.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.2  2005/05/06 11:55:11  tcs
//Complete
//
//Revision 1.1.2.1  2005/05/04 13:56:08  tcs
//Initial entry to cvs
//
//------------------------------------------------------------------------------
package com.verilion.object;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

/**
 * Handles uploading of files via http upload.
 * <P>
 * May 04 2005
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ImageUpload extends HttpServlet {

	private static final long serialVersionUID = -7648379165311374336L;

	private String cancelPage = "";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		try {
			String isPath = "";
			DiskFileUpload upload = new DiskFileUpload();
			List items = upload.parseRequest(request);
			isPath = request.getParameter("path");
			cancelPage = request.getParameter("cancelPage");
			Iterator itr = items.iterator();

			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()) {
					File fullFile = new File(item.getName());
					System.out.println("saving to " + isPath + ", file is "
							+ fullFile.getName());
					File f = new File(isPath, fullFile.getName());
					item.write(f);
				}
			}

			session.setAttribute("Error", "Upload complete.");
			response.sendRedirect(cancelPage);

		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}