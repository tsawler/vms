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