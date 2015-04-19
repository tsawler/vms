//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: DoSysPref.java,v $
//Revision 1.6.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.6  2004/06/27 02:39:35  tcs
//Removed dependence on context variables; changes for new listener logic
//
//Revision 1.5  2004/06/26 17:03:29  tcs
//Changes due to refactoring
//
//Revision 1.4  2004/06/11 21:37:28  tcs
//Added missing attribute set
//
//Revision 1.3  2004/06/11 18:59:37  tcs
//Initial entry into cvs
//
//Revision 1.2  2004/06/11 16:43:04  tcs
//Fixed redirect
//
//Revision 1.1  2004/06/11 16:38:42  tcs
//Initial entry into cvs
//
//Revision 1.5  2004/06/10 17:26:20  tcs
//moved fixedsqlfield value to db layer
//
//Revision 1.4  2004/06/10 17:18:31  tcs
//Changes due to interface modification
//
//Revision 1.3 2004/06/09 02:14:18 tcs
//Modified to use https
//
//Revision 1.2 2004/05/25 21:37:34 tcs
//Fixed redirect
//
//Revision 1.1 2004/05/23 04:52:49 tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.SingletonObjects;
import com.verilion.database.SystemPreferences;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
* Adds a site page.
* 
* <P>
* Nov 28, 2003
* <P>
* 
* @author tsawler
*  
*/
public class DoSysPref
		extends
		SecurePage {

   private static final long serialVersionUID = 6695477457864540919L;

   public void BuildPage(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {

		int action = 2;
		String system_online_yn = "";
		String use_sef_yn = "";
		String mailhost = "";
		String secure_port = "";
		String insecure_port = "";
		String database = "";
		String upload_path = "";
		String host_name = "";
		int homepage_page_id = 0;

		try {
			action = Integer.parseInt((String) request.getParameter("action"));

			// now perform the appropriate action
			// 1 = save
			// 2 = cancel

			switch (action) {
				case 1 :
					// get parameters
					system_online_yn = (String) request.getParameter("system_online_yn");
					use_sef_yn = (String) request.getParameter("use_sef_yn");
					mailhost = (String) request.getParameter("mailhost");
					secure_port = (String) request.getParameter("secure_port");
					insecure_port = (String) request.getParameter("insecure_port");
					database = (String) request.getParameter("database");
					upload_path = (String) request.getParameter("upload_path");
					host_name = (String) request.getParameter("host_name");
					homepage_page_id = Integer.parseInt((String) request.getParameter("homepage_page_id"));
					
					SystemPreferences myPrefs = new SystemPreferences();
					myPrefs.setConn(conn);
					myPrefs.setSystem_online_yn(system_online_yn);
					myPrefs.setUse_sef_yn(use_sef_yn);
					myPrefs.setMailhost(mailhost);
					myPrefs.setSecure_port(secure_port);
					myPrefs.setInsecure_port(insecure_port);
					myPrefs.setDatabase(database);
					myPrefs.setUpload_path(upload_path);
					myPrefs.setHost_name(host_name);
					myPrefs.setHomepage_page_id(homepage_page_id);
					
					myPrefs.updateSystemPreferences();
					
					// now change values in context
					//response.sendRedirect("/servlet/com.verilion.display.html.admin.DoSysPrefsContext");
					SingletonObjects.getInstance().setUse_sef_yn(use_sef_yn);
					SingletonObjects.getInstance().setSystem_online_yn(system_online_yn);
					SingletonObjects.getInstance().setMailhost(mailhost);
					SingletonObjects.getInstance().setSecure_port(secure_port);
					SingletonObjects.getInstance().setInsecure_port(insecure_port);
					SingletonObjects.getInstance().setDatabase(database);
					SingletonObjects.getInstance().setUpload_path(upload_path);
					SingletonObjects.getInstance().setHomepage_page_id(homepage_page_id);
					SingletonObjects.getInstance().setHost_name(host_name);
					
					response.sendRedirect("/servlet/com.verilion.display.html."
							+ "SecurePage?page=SearchCustomers");
					break;

				case 2 :
					session.setAttribute("Error", "Cancelled.");
					response.sendRedirect("/servlet/com.verilion.display.html."
									+ "SecurePage?page=SearchCustomers");
					break;
			}
		} catch (Exception e) {
			Errors
					.addError("com.verilion.display.admin.DoSysPref:BuildPage:Exception:"
							+ e.toString());
		}
	}
}