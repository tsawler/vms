//------------------------------------------------------------------------------
//Copyright (c) 2003-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-06-27
//Revisions
//------------------------------------------------------------------------------
//$Log: JSONImageUpload.java,v $
//Revision 1.1.2.3  2007/07/03 15:47:05  susan
//upload with thumbnail working in IE and Firefox, removed tool tip since not working in IE, added current directory, fixed thumbnail viewing for gif files
//
//Revision 1.1.2.2  2007/06/28 18:24:53  susan
//Got tool tip working, working on problem with IE and fancy upload
//
//Revision 1.1.2.1  2007/06/26 18:39:30  susan
//Added File Filetering, Image Upload, thumbnail creation


//Based on com.verilion.object.ImageUpload
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.oreilly.servlet.MultipartRequest;

/**
 * Handles uploading of files via http upload.
 * <P>
 * June 26, 2007
 * <P>
 * 
 * @author sdouglas
 * 
 */
public class JSONImageUpload extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7711690909411704743L;
	private static String baseDir;
	private static String fileTypes;
	private static String[] types;

	/**
	 * Initialize the servlet.<br>
	 * Retrieve from the servlet configuration the "baseDir" which is the root of the file repository:<br>
	 * If not specified the value of "/UserFiles" will be used.
	 *
	 */
	public void init() throws ServletException {
		baseDir=getInitParameter("baseDir");
		if(baseDir==null) {
			baseDir="/UserFiles/Image";
		}
		String realBaseDir=getServletContext().getRealPath(baseDir);
		File baseFile=new File(realBaseDir);
		if(!baseFile.exists()){
			baseFile.mkdir();
		}
		fileTypes=getInitParameter("fileTypes");
		if(fileTypes==null)  {
			fileTypes="png:jpeg:jpg:gif";
		}
		types = fileTypes.split(":");
	}


	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		try {

			String isPath = "";
			isPath = req.getParameter("path");
			if (isPath==null) {
				isPath ="/";
			}

			String filePath = getServletContext().getRealPath(baseDir + isPath);
			MultipartRequest multi =
				new MultipartRequest(req, filePath , 100 *1024 * 1024);


//			Show which files we received
			Enumeration files = multi.getFileNames();
			while (files.hasMoreElements()) {
				String name = (String)files.nextElement();
				File f = multi.getFile(name);
				String little = "";
				// Only want images
				if (!isFileType(f.getName())) {
					f.delete();
				} else {
					//only make thumbnail in non thumbnail driectory
					if (!isPath.endsWith("thumbnails")) {
						little = getServletContext().getRealPath(baseDir + isPath + "/thumbnails/"+ f.getName());
						if (little.endsWith("gif")) {
							little = little.replace(".gif", ".png");
						}
						//making the thumbnail
						String realThumbDir=getServletContext().getRealPath(baseDir + isPath + "/thumbnails");
						File thumbDir=new File(realThumbDir);
						if(!thumbDir.exists()){
							thumbDir.mkdir();
						}
						Thumbnail t = new Thumbnail();
						t.createThumbnail(
								f.getAbsolutePath(),
								little,
								100);
					}
				}
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		out.println("Upload Complete");
	}

	private boolean isFileType(String filename){
		for (int x=0; x<types.length; x++) {
			if (filename.endsWith("." + types[x])) {
				return true;
			}
		}
		return false;
	}

}