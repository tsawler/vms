//------------------------------------------------------------------------------
//Copyright (c) 2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-06-19
//Revisions
//------------------------------------------------------------------------------
//$Log: JSONFileBrowser.java,v $
//Revision 1.1.2.7  2007/07/03 15:47:05  susan
//upload with thumbnail working in IE and Firefox, removed tool tip since not working in IE, added current directory, fixed thumbnail viewing for gif files
//
//Revision 1.1.2.6  2007/06/26 18:39:30  susan
//Added File Filetering, Image Upload, thumbnail creation
//
//Revision 1.1.2.5  2007/06/26 13:48:42  susan
//Got filtering working, back button working, and showing corner instead of thumbnail
//
//Revision 1.1.2.4  2007/06/21 19:06:29  susan
//Added thumbnails option

//Revision 1.1.2.3  2007/06/19 15:51:12  tcs
//Added cvs header, cleaned up imports.

//------------------------------------------------------------------------------

package com.verilion.utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class for Servlet: JSONFileBrowser
 *
 */
public class JSONFileBrowser extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -928666255209142630L;
	private static String baseDir;
	private static String fileTypes;
	private static String[] types;
	String extraDir;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public JSONFileBrowser() {
		super();
	}

	/**
	 * Initialize the servlet.<br>
	 * Retrieve from the servlet configuration the "baseDir" which is the root
	 * of the file repository:<br>
	 * If not specified the value of "/UserFiles" will be used.
	 *
	 */
	public void init() throws ServletException {
		baseDir = getInitParameter("baseDir");
		if (baseDir == null) {
			baseDir = "/UserFiles/Image";
		}
		String realBaseDir = getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if (!baseFile.exists()) {
			baseFile.mkdir();
		}
		fileTypes = getInitParameter("fileTypes");
		if (fileTypes == null) {
			fileTypes = "png:jpeg:jpg:gif";
		}
		types = fileTypes.split(":");
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		procressRequest(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		procressRequest(request, response);
	}

	private void procressRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		extraDir = request.getParameter("directory");
		if (extraDir == null) {
			extraDir = "";
		} else if (!(extraDir.endsWith("/"))) {
			/* add final slash */
			extraDir = extraDir + "/";
		}
		String realBaseDir = getServletContext()
				.getRealPath(baseDir + extraDir);
		out.println(getFiles(realBaseDir));
		out.close();
	}

	@SuppressWarnings("unchecked")
	private JSONObject getFiles(String dirItem) {
		JSONObject return_obj = new JSONObject();
		JSONArray preview = new JSONArray();
		File file;
		String list[];

		file = new File(dirItem);
		if (file.isDirectory()) {
			list = file.list();
			java.util.Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
			for (int i = 0; i < list.length; i++) {
				JSONObject myObj = createFileObj(list[i]);
				if (myObj != null) {
					preview.add(myObj);
				}
			}
		}
		return_obj.put("previews", preview);
		return return_obj;
	}

	@SuppressWarnings("unchecked")
	private JSONObject createFileObj(String fileStr) {
		JSONObject return_obj = new JSONObject();
		File file;
		File t_file;
		file = new File(getServletContext().getRealPath(
				baseDir + extraDir + fileStr));
		if (file.isDirectory()) {
			return_obj.put("type", "directory");
		} else {
			if (isFileType(fileStr)) {
				return_obj.put("type", "image");
				t_file = new File(getServletContext().getRealPath(
						baseDir + extraDir + "/thumbnails/" + fileStr));
				if (t_file.exists()) {
					return_obj.put("thumb", "thumbnails/" + fileStr);
				} else {
					// if a gif file
					if (fileStr.endsWith(".gif")) {
						String pngThumbnail = fileStr.replace(".gif", ".png");
						t_file = new File(getServletContext().getRealPath(
								baseDir + extraDir + "/thumbnails/"
										+ pngThumbnail));
						if (t_file.exists()) {
							return_obj.put("thumb", "thumbnails/"
									+ pngThumbnail);
						}

					} else if (extraDir.contains("thumbnails")) {
						// if we are in the thumbnails directory
						return_obj.put("thumb", fileStr);
					} else {
						return_obj.put("thumb", "");
					}

				}
			} else {
				return null;
			}
		}
		return_obj.put("src", fileStr);

		return return_obj;
	}

	private boolean isFileType(String filename) {
		for (int x = 0; x < types.length; x++) {
			if (filename.endsWith("." + types[x])) {
				return true;
			}
		}
		return false;
	}

}