// ------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-04-27
//Revisions
//------------------------------------------------------------------------------
//$Log: ModuleUpload.java,v $
//Revision 1.1.2.5.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.5  2005/05/03 12:23:16  tcs
//Added support for modules with more than one class
//
//Revision 1.1.2.4  2005/05/02 17:03:14  tcs
//Completed support for uploaded jars
//
//Revision 1.1.2.3  2005/04/29 13:30:04  tcs
//Working on using JarClassLoader
//
//Revision 1.1.2.2  2005/04/27 18:10:57  tcs
//Modified due to refactoring
//
//Revision 1.1.2.1  2005/04/27 14:50:14  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.DbBean;
import com.verilion.database.Modules;
import com.verilion.database.SingletonObjects;
import com.verilion.object.html.modules.ModuleObject;
import com.verilion.utility.JarClassLoader;

/**
 * Handles uploading of modules. Expects a file, module name, and class name to
 * come as a multipart request.
 * <P>
 * April 27 2005
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ModuleUpload extends HttpServlet {

	private static final long serialVersionUID = 7362394743132902063L;
	public String type = "";
	public String filename = "";
	public String class_name = "";
	public String module_name = "";
	public String thePath = "";
	public String theHomePage = "";
	public Connection conn;
	public String realPath = "";
	public String jarfile = "";

	Modules myModule = new Modules();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		thePath = SingletonObjects.getInstance().getUpload_path();
		String uploadPath = SingletonObjects.getInstance().getSystem_path();

		if (session.getAttribute("userAdminId") != null) {
			try {

				MultipartRequest multi = new MultipartRequest(request,
						uploadPath + "UserJars", 10000 * 1024);

				Enumeration files = multi.getFileNames();

				while (files.hasMoreElements()) {
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
					type = multi.getContentType(name);
				}

				// File (or directory) to be moved
				boolean success = false;

				File myFile = new File(uploadPath + "UserJars/" + filename);
				if (myFile.isFile()) {
					if (myFile.canRead())
						success = true;
				}

				// now make sure that it's actually a jar file with a valid
				// class in
				// it
				try {
					JarClassLoader myJCL = new JarClassLoader(SingletonObjects
							.getInstance().getSystem_path() + "UserJars/",
							ModuleObject.class.getClassLoader());
					Class myClass = myJCL.LoadClass(
							multi.getParameter("class_name"), filename);

					Object instance = myClass.newInstance();

					if (instance != null) {

					}
				} catch (Exception e) {
					success = false;
					System.out.println("Error!");
					e.printStackTrace();
				}

				if (success) {

					// load all the classes in the file
					JarClassLoader myJCL = new JarClassLoader(SingletonObjects
							.getInstance().getSystem_path() + "UserJars/",
							ModuleObject.class.getClassLoader());
					myJCL.LoadAllClassDataFromJar(filename);

					// write info to the database
					class_name = (String) multi.getParameter("class_name");
					module_name = (String) multi.getParameter("module_name");

					// Get a connection
					try {
						conn = DbBean.getDbConnection();
					} catch (Exception e) {
						System.err.println("DbBean:getDbConnection:Exception "
								+ e.getMessage());
					}
					myModule.setConn(conn);
					myModule.setModule_class_name(class_name);
					myModule.setModule_active_yn("n");
					myModule.setModule_name(module_name);
					myModule.setModule_position_id(1);
					myModule.setJarfile(filename);
					myModule.addModule();

				} else {
					System.out.println("jar file not moved!");
					session.setAttribute("Error",
							"Problem with upload! Module not installed");
					response.sendRedirect(response
							.encodeRedirectURL((String) session
									.getAttribute("lastPage")));
				}

				DbBean.closeDbConnection(conn);

				session.setAttribute("Error", "Module successfully uploaded.");
				response.sendRedirect("/admin/jpage/1/p/ModulesChoice/a/moduleschoice/class/modules/clear/1/limit/10/offset/0/content.do?filter=1");

			} catch (java.io.IOException e) {
				e.printStackTrace();
				session.setAttribute("Error", "File is too large!");
				response.sendRedirect(response
						.encodeRedirectURL((String) session
								.getAttribute("lastPage")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			session.setAttribute("Error", "Invalid access to this page!");
			response.sendRedirect(theHomePage);
		}
	}
}