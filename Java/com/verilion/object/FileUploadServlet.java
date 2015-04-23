//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: FileUploadServlet.java,v $
//Revision 1.2.2.1.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.1  2004/12/08 19:31:00  tcs
//Updated code to use SingletonObjects & such
//
//Revision 1.2  2004/06/24 17:58:17  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.DbBean;
import com.verilion.database.SingletonObjects;

/**
 * Handles uploading of files via http upload.
 * <P>
 * July 01, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 3256726169339901495L;
	private int theId = 0;

	private String sqlQuery = "";
	private String type = "";
	private String filename = "";
	private PreparedStatement pst = null;

	private Connection conn;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		try {
			MultipartRequest multi = new MultipartRequest(request,
					SingletonObjects.getInstance().getUpload_path(), 50 * 1024);

			theId = Integer.parseInt((String) multi.getParameter("id"));

			Enumeration files = multi.getFileNames();
			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				type = multi.getContentType(name);
			}

			// Get a connection
			try {
				conn = DbBean.getDbConnection();
			} catch (Exception e) {
				System.err.println("DbBean:getDbConnection:Exception "
						+ e.getMessage());
			}

			// write to db
			try {
				sqlQuery = "update images set file_type = '" + type
						+ "', image_data = ? where image_id = " + theId;

				pst = conn.prepareStatement(sqlQuery);

				java.io.File theFile = new java.io.File(SingletonObjects
						.getInstance().getUpload_path(), filename);

				int fileLength = (int) theFile.length();
				java.io.InputStream fin = new java.io.FileInputStream(theFile);
				pst.setBinaryStream(1, fin, fileLength);
				pst.execute();
				theFile.delete();
				pst.close();
				pst = null;
				DbBean.closeDbConnection(conn);
			} catch (java.io.FileNotFoundException e) {
				System.err.print("ClassNotFoundException: ");
				System.err.println(e.getMessage());
			} catch (SQLException ex) {
				System.err.print("SQLException: ");
				System.err.println(ex.getMessage());
			} finally {
				if (pst != null) {
					pst.close();
					pst = null;
				}
			}
			// probably should send them somewhere better.
			session.setAttribute("Error", "Upload complete.");
			response.sendRedirect((String) session.getAttribute("lastPage"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}