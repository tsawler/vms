//------------------------------------------------------------------------------
//Copyright (c) 2003-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: Page.java,v $
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.8.2.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.8  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.7  2007/02/09 00:37:55  tcs
//Added another error message
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.6  2007/02/08 23:55:57  tcs
//Improved error messges, reduced required token count in URL to 5
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.5  2007/02/08 23:42:35  tcs
//Greatly simplified, and removed references obsolete way of doing things.
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.4  2007/02/08 19:28:19  tcs
//Trapped for failure to redirect with dispatcher, and use response.sendRedirect instead.
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.3  2007/01/28 03:41:55  tcs
//Updated javadoc, and removed commented code that's been hanging around forever.
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.2  2007/01/28 00:51:00  tcs
//Added session to method call
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4.2.1  2007/01/23 14:39:43  tcs
//Corrected copyright date
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.4  2007/01/12 19:29:06  tcs
//Fixed display of page generated date/time
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.3  2006/12/23 15:32:09  tcs
//Add parameterization for type safety
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.2  2006/11/09 12:16:30  tcs
//Added host name to system.out.println on startup
//
//Revision 1.42.2.29.2.1.2.1.2.2.4.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.42.2.29.2.1.2.1.2.2  2005/09/08 18:15:25  tcs
//Added port number to redirects
//
//Revision 1.42.2.29.2.1.2.1.2.1  2005/09/03 03:29:39  tcs
//removed meta refresh; it seems to confuse the db connection...
//
//Revision 1.42.2.29.2.1.2.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.42.2.29.2.1  2005/08/08 15:34:28  tcs
//Changes for new URL & jsp tag methodology
//
//Revision 1.42.2.29  2005/03/08 12:30:27  tcs
//Added support for generated message on jsp via tag
//
//Revision 1.42.2.28  2005/03/04 12:51:51  tcs
//Added sTargetPath to redirect when handling jsps
//
//Revision 1.42.2.27  2005/03/04 12:14:49  tcs
//Changed the way jsps are handled
//
//Revision 1.42.2.26  2005/02/26 14:05:25  tcs
//changed page generation time unit to seconds
//
//Revision 1.42.2.25  2005/02/25 12:16:47  tcs
//Formatting
//
//Revision 1.42.2.24  2005/02/24 19:47:45  tcs
//Working on caching
//
//Revision 1.42.2.23  2005/02/24 13:11:18  tcs
//read classobjects from singleton instead of db
//
//Revision 1.42.2.22  2005/02/23 16:39:13  tcs
//Added page generation stats
//
//Revision 1.42.2.21  2005/02/23 13:18:22  tcs
//Added full support for external html templates
//
//Revision 1.42.2.20  2005/02/22 19:49:55  tcs
//Preliminary support for external java server pages
//
//Revision 1.42.2.19  2005/02/21 18:50:29  tcs
//Modified to leave blank if no error message, to avoid page jump.
//
//Revision 1.42.2.18  2005/02/21 13:27:17  tcs
//Synchronized methods to fix multiple click problem
//
//Revision 1.42.2.17  2005/01/17 15:37:36  tcs
//Added wrapping try/catch & explicit conn close in event of failure
//
//Revision 1.42.2.16  2005/01/16 19:39:37  tcs
//Turned off explicit garbage collection, as it seemed to hang a lot.
//
//Revision 1.42.2.15  2005/01/13 13:33:34  tcs
//Disabled pdf & email icons until that code is complete
//
//Revision 1.42.2.14  2004/12/17 21:51:56  tcs
//Improved system messages
//
//Revision 1.42.2.13  2004/12/14 17:03:04  tcs
//Added check for valid actions
//
//Revision 1.42.2.12  2004/12/14 14:29:52  tcs
//Modified to redirect to correct host name and requested URI instead of
//hardcoded value
//
//Revision 1.42.2.11  2004/12/14 13:34:24  tcs
//Corrected typo in forward; added some additional validation
//
//Revision 1.42.2.10  2004/12/10 18:02:02  tcs
//Added generated meta refresh tag to avoid session timeout errors
//
//Revision 1.42.2.9  2004/12/10 16:35:42  tcs
//Added return after dispatcher.forward on session timeout redirect
//
//Revision 1.42.2.8  2004/12/10 14:33:08  tcs
//Added support for msg parameter
//
//Revision 1.42.2.7  2004/12/10 12:26:06  tcs
//Corrected redirect from incorrect server name
//
//Revision 1.42.2.6  2004/12/10 04:07:33  tcs
//Changed to dispatch to requested uri when creating session, instead of to
//hardcoded home page.
//
//Revision 1.42.2.5  2004/12/10 03:59:38  tcs
//Replaced most redirects with dispatcher.forwards, to cut down on server trips.
//
//Revision 1.42.2.4  2004/12/08 17:18:56  tcs
//Fixed up formatting
//
//Revision 1.42.2.3  2004/12/08 17:10:00  tcs
//Made error checking more robust, and fixed so that redirect is only
//attempted once when the world falls apart; moved the generated
//HTML comment to servlet init, so that the manifest is only
//queried once, at servlet creation
//
//Revision 1.42.2.2  2004/12/08 16:00:18  tcs
//Added auto insertion of <!--ResultsTable--> tag
//
//Revision 1.42.2.1  2004/12/02 16:32:07  tcs
//Added appending of parameters to url on redirect to secure/insecure port
//
//Revision 1.42  2004/11/30 19:15:26  tcs
//Decided to leave session timeouts to the container
//
//Revision 1.41  2004/11/30 18:30:42  tcs
//Corrected title display in admin tool
//
//Revision 1.40  2004/11/30 18:24:41  tcs
//Improved session handling
//
//Revision 1.39  2004/11/30 16:29:58  tcs
//Collapsed to include all admin functions and generalize actions
//
//Revision 1.38  2004/11/29 18:00:41  tcs
//Added hm.clear()
//
//Revision 1.37  2004/11/26 13:40:12  tcs
//Improved support for generic actions
//
//Revision 1.36  2004/11/16 16:53:52  tcs
//Made abstract
//
//Revision 1.35  2004/11/12 15:53:31  tcs
//Added three values to hashmap
//
//Revision 1.34  2004/11/06 11:11:05  tcs
//Added support for https connections being set in db record for page
//
//Revision 1.33  2004/11/06 10:52:07  tcs
//Modified to support both http & https connections. If hm.get("secure")
//is not null, then we have /secure/1/ or something like that in our url, and
//we are supposed to be on an https connection.
//
//Revision 1.32  2004/11/03 17:03:44  tcs
//Added ability to manually force refresh by passing refresh=true parameter
//
//Revision 1.31  2004/11/02 15:25:44  tcs
//Explicict serialVersionUID added
//
//Revision 1.30  2004/10/26 15:35:41  tcs
//Improved javadocs
//
//Revision 1.29  2004/10/26 13:51:40  tcs
//Changed email icon
//
//Revision 1.28  2004/10/25 17:32:44  tcs
//Added pdf and email icon display
//
//Revision 1.27  2004/10/25 09:34:02  tcs
//Initial support for PDF format.
//
//Revision 1.26  2004/10/22 18:07:51  tcs
//Added support for anonymous actions through reflection
//
//Revision 1.25  2004/10/22 14:24:18  tcs
//Corrected typo
//
//Revision 1.24  2004/10/21 18:01:18  tcs
//Additional error checking
//
//Revision 1.23  2004/10/21 12:31:32  tcs
//Added login/logout actions
//
//Revision 1.22  2004/10/20 18:11:11  tcs
//Changed generation of "generated by" comment
//
//Revision 1.21  2004/10/20 16:16:22  tcs
//Added actions for joining site
//
//Revision 1.20  2004/10/19 17:20:38  tcs
//Added another possible action
//
//Revision 1.19  2004/10/19 12:57:10  tcs
//Added recover action
//
//Revision 1.18  2004/10/18 18:45:09  tcs
//additional actions supported
//
//Revision 1.17  2004/10/18 18:01:16  tcs
//Added support for "action" directive
//
//Revision 1.16  2004/10/18 15:31:24  tcs
//Added support for name/value pairs in URL
//
//Revision 1.15  2004/10/18 09:50:55  tcs
//Removed SEF and rewrite URI parsing
//
//Revision 1.14  2004/09/17 18:29:26  tcs
//Added explicit garbage collection
//
//Revision 1.13  2004/07/30 17:55:52  tcs
//Trying to use <object> instead of iframe
//
//Revision 1.12  2004/07/29 18:56:34  tcs
//Added iframe support
//
//Revision 1.11  2004/07/21 18:33:19  tcs
//Added support for page attachments
//
//Revision 1.10  2004/07/08 18:14:48  tcs
//Removed single thread model
//
//Revision 1.9  2004/06/30 18:32:56  tcs
//Added support for multiple module positions
//
//Revision 1.8  2004/06/29 23:43:08  tcs
//Added check to make sure we're pointing to default hostname
//
//Revision 1.7  2004/06/29 17:39:24  tcs
//Mods for cookieless browsers
//
//Revision 1.6  2004/06/28 16:57:56  tcs
//Modifications for cookieless users
//
//Revision 1.5  2004/06/27 02:39:35  tcs
//Removed dependence on context variables; changes for new listener logic
//
//Revision 1.4  2004/06/26 17:03:34  tcs
//Changes due to refactoring
//
//Revision 1.3  2004/06/26 17:00:55  tcs
//Initial entry after collapsing page/template methods
//
//------------------------------------------------------------------------------
package com.verilion.display.html;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DbBean;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;

/**
 * <p>
 * Controller servlet for creating HTML pages from JSP templates. Decisions
 * about what to do are based on parsing the URI. The URI looks like this:
 * </p>
 * <p>
 * 
 * <pre>
 *  http://site/templatename/jpage/1/p/Pagename/a/action/name/value/name2/value2/content.do
 * </pre>
 * 
 * </p>
 * <p>
 * This tells the servlet to build the page content using "Pagename" with a
 * language of 1 (English), processed by action "action", and send the result to
 * the end user through the JSP template named "templatename".jsp. Subsequent
 * informaion is stored in a hashmap that subclasses can access, in the
 * name/value pair for everything after the first few bits of standard info. If
 * "action" is left out, then no action is performed on the page from within
 * this servlet (although it could be in the JSP, of course).
 * </p>
 * 
 * @author tsawler
 * 
 */
public abstract class Page extends HttpServlet {

	private static final long serialVersionUID = 4654495960170767713L;
	public String isGeneratedComment = "";
	public String page_name = "";
	public String action = "";
	public int myLanguageId = 1;
	public String template_contents = "";
	public String page_detail_contents = "";
	public int page_access_level = 0;
	public String page_detail_title = "";
	public int page_id = 0;
	public int template_id = 0;
	public String page_active_yn = "";
	public String display_in_iframe_yn = "n";
	public String iframe_url = "";
	public int thePort = 0;
	public StringBuffer sb;
	public HTMLTemplateDb MasterTemplate;
	public boolean redirect = false;
	public String ReplaceString = "";
	public String url = "";
	public String theError = "";
	public String copyrightInfo = "";
	public String theMessage = "";
	public String isHorizontal = "";
	public String isSef = "n";
	public String theModuleHtml = "";
	Package pkg = Package.getPackage("com.verilion");
	public Connection conn = null;
	public HashMap hm = new HashMap();
	public XDisconnectedRowSet rs = new XDisconnectedRowSet();
	public String secure_page_yn = "";
	public String external_template = "";
	public long lPageGenTime = 0;
	public long lPageGenTimeEnd = 0;
	public Date today;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// get our date
		Date dttNow = new Date();
		DateFormat MyDateFormatInstance = DateFormat.getDateTimeInstance(
				DateFormat.LONG, DateFormat.SHORT);
		String sDate = MyDateFormatInstance.format(dttNow);
		System.err.println(SingletonObjects.getInstance().getHost_name()
				+ " Servlet Starting - " + sDate);

		GregorianCalendar x = new GregorianCalendar();
		today = new Date();
		int year = x.get(Calendar.YEAR);

		// generate the comment that is put on every generated page
		isGeneratedComment = " by " + pkg.getSpecificationTitle() + "\n"
				+ "Specification-Vendor: " + pkg.getSpecificationVendor()
				+ "\n" + "Specification-Version: "
				+ pkg.getSpecificationVersion() + "\n"
				+ "Specification-Title: " + pkg.getSpecificationTitle() + "\n"
				+ "Implementation-Vendor: " + pkg.getImplementationVendor()
				+ "\n" + "Implementation-Version: "
				+ pkg.getImplementationVersion() + "\n"
				+ "Copyright (c) 2003 - " + year + " "
				+ pkg.getImplementationVendor() + " All rights reserved.\n";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */

	public synchronized void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// forward all get requests to the post handler
		this.doPost(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	public synchronized void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		lPageGenTime = System.currentTimeMillis();
		String originalUrl = "";
		originalUrl = request.getRequestURI();
		
		try {
			// make sure we're pointing to the right server name
			if (!(request.getServerName().equals((String) SingletonObjects
					.getInstance().getHost_name()))) {
				response.sendRedirect("http://"
						+ SingletonObjects.getInstance().getHost_name() + ":"
						+ SingletonObjects.getInstance().getInsecure_port()
						+ request.getRequestURI());
				return;
			}

			// assume this is a valid request until proven otherwise
			redirect = false;

			// get the session, but don't request a new one.
			HttpSession session = request.getSession(false);

			// if we don't have a session, get one
			if (session == null) {
				session = request.getSession(true);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(request.getRequestURI());
				if (dispatcher != null)
					try {
						dispatcher.forward(request, response);
					} catch (Exception e) {
						response.sendRedirect("/");
					}
				return;
			}

			try {
				session.setAttribute("lastPage", request.getRequestURI());
			} catch (Exception e) {

			}

			doPostExtra(request, response, session);

			try {
				// Get a connection
				conn = DbBean.getDbConnection();
			} catch (Exception e2) {
				e2.printStackTrace();
				theError = "Unable to connect to database.";
				redirect = true;
			}

			/*
			 * break up the url into tokens
			 * 
			 * values are:
			 * 
			 * 1) jsp page 2) action (should always be jpage) 3) language id (1
			 * is english) 4) p (standing for page) 5) name of page found in
			 * table "page" 6) a (for action) 7) name of action (matches entry
			 * in class_objects table) 8) additional name/value pairs as
			 * required ends with content.do (although anything.do will suffice)
			 * 
			 * example url:
			 * 
			 * http://mysite.com/public/jpage/1/p/Home/a/someaction/id/1/sid/2/
			 * content.do
			 */
			StringTokenizer st = null;
			st = new StringTokenizer(request.getRequestURI().substring(1,
					request.getRequestURI().lastIndexOf("/")), "/");
			int i = 0;
			i = st.countTokens();

			if (i < 5) {
				// we have to have at least five arguments - jsp page, action,
				// language id, "p",
				// page name
				theError = "Invalid page request - malformed URL.";
				redirect = true;
			}

			// clear our hashmap
			hm.clear();

			// store the IP address in session, in case we need it in a module
			try {
				hm.put("ip_address", (String) request.getRemoteHost());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				hm.put("original_url", originalUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// get the page name
			if (st.hasMoreElements()) {
				page_name = (String) st.nextToken();
				hm.put("page_name", page_name);
			}

			// get the action
			if (st.hasMoreElements()) {
				action = (String) st.nextToken();
				if (!action.equals("jpage")) {
					redirect = true;
					theError = "Invalid page request - not a valid page";
				} else {
					hm.put("action", action);
				}
			}

			// get the language
			if (st.hasMoreElements()) {
				try {
					myLanguageId = Integer.parseInt((String) st.nextToken());
					hm.put("lang", myLanguageId + "");
				} catch (Exception e) {
					theError = "Invalid page request - invalid language specified";
					redirect = true;
				}
			}

			// now read any other values into a hashmap, as
			// name value pairs
			while (st.hasMoreElements()) {
				try {
					hm.put(st.nextToken(), st.nextToken());
				} catch (Exception e) {
					theError = "Invalid page request - unbalanced url";
					redirect = true;
				}
			}

			if (!redirect) {
				// If we have an action of jpage, we are being
				// forwarded to a java server page.
				// If we have an entry "path" in our hashmap, this
				// is the path to the jpage on the file system. Directories
				// are separated by !, so replace all ! with / to get
				// the path to the jsp. Note that the jsp must include
				// "/PageSecurity.jsp" in order to prevent direct access
				// to it without passing through this servlet, if we want
				// to enforce page security.
				if (action.equals("jpage")) {
					if (hm.get("admin") == null) {
						RequestDispatcher dispatcher = null;
						String sPath = "";
						String sTargetPath = "";
						if (hm.get("path") != null) {
							sPath = (String) hm.get("path");
							sTargetPath = sPath.replaceAll("!", "/") + "/";
						}
						// put our hashmap in the session
						session.setAttribute("pHm", hm);

						// create generated by message
						Date rightNow = new Date();
						String gMsg = "<!-- \n" + "Generated on "
								+ rightNow.toString() + isGeneratedComment
								+ "-->";
						session.setAttribute("gMsg", gMsg);

						// forward request
						dispatcher = request.getRequestDispatcher("/"
								+ sTargetPath + hm.get("page_name") + ".jsp");

						if (dispatcher != null) {
							dispatcher.forward(request, response);
							return;
						} else {
							response.sendRedirect("http://"
									+ SingletonObjects.getInstance()
											.getHost_name()
									+ ":"
									+ SingletonObjects.getInstance()
											.getInsecure_port() + "/"
									+ sTargetPath + hm.get("jpage") + ".jsp");
							return;
						}
					} else {
						RequestDispatcher dispatcher = null;

						// put our hashmap in the session
						session.setAttribute("pHm", hm);

						// create generated by message
						Date rightNow = new Date();
						String gMsg = "<meta http-equiv=\"refresh\" content=\""
								+ SingletonObjects.getInstance()
										.getSession_timeout()
								+ "; URL=http://"
								+ SingletonObjects.getInstance().getHost_name()
								+ "/public/jpage/1/p/Home/content.do?msg=Your session has timed out.\" />\n"
								+ "<!-- \n" + "Generated on "
								+ rightNow.toString() + isGeneratedComment
								+ "-->";
						session.setAttribute("gMsg", gMsg);

						// forward request
						dispatcher = request.getRequestDispatcher("/"
								+ "admin/" + hm.get("page_name") + ".jsp");

						if (dispatcher != null) {
							dispatcher.forward(request, response);
							return;
						} else {
							response.sendRedirect("http://"
									+ SingletonObjects.getInstance()
											.getHost_name() + "/admin/"
									+ hm.get("jpage") + ".jsp");
							return;
						}
					}
				} else {
					// hmm... we need an action of jpage at least...
					session.setAttribute("Error", "Invalid page requested");
					response.sendRedirect("/");
				}
			} else {
				session.setAttribute("Error", theError);
				response.sendRedirect("/");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				conn = null;
			}
		}
	}

	/**
	 * Extend this class and overrride this method to do client specific page
	 * processing.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	public synchronized void doPostExtra(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

	}

}