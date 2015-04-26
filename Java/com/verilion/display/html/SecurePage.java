package com.verilion.display.html;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.CtLanguages;
import com.verilion.database.DbBean;
import com.verilion.database.Menu;
import com.verilion.database.ModulePosition;
import com.verilion.database.PageRoutines;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;
import com.verilion.object.GetMenu;
import com.verilion.object.html.LanguageMenu;
import com.verilion.object.html.modules.ModuleObject;

/**
 * Generic servlet for creating HTML pages from HTML templates stored in the
 * database. Note that this will force the page to be delivered over HTTPS
 * (secure connection). Takes the page name passed as a parameter.
 * <P>
 * Nov 20, 2003
 * <P>
 *
 * @author tsawler
 * 
 */
public class SecurePage extends HttpServlet {

	/**
    * 
    */
	private static final long serialVersionUID = -3734708676302164654L;
	public String page_name = "";
	public String template_contents = "";
	public String page_detail_contents = "";
	public int page_access_level = 0;
	public String page_detail_title = "";
	public int page_id = 0;
	public int template_id = 0;
	public String page_active_yn = "";

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
	public String isSef = "";
	public String theModuleHtml = "";
	Package pkg = Package.getPackage("com.verilion");
	public Connection conn = null;
	public XDisconnectedRowSet rs = new XDisconnectedRowSet();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		page_name = (String) request.getParameter("page");
		thePort = Integer.parseInt(SingletonObjects.getInstance()
				.getSecure_port());
		isSef = SingletonObjects.getInstance().getUse_sef_yn();
		PrintWriter out;
		response.setContentType("text/html");
		out = response.getWriter();
		try {
			DisplayResult(out, request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.close();
	}

	public PrintWriter DisplayResult(PrintWriter out,
			HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		PrintWriter PWtemp;
		PWtemp = out;
		redirect = false;
		String theMenu = "";
		int myLanguageId = 1;
		String menuTag = "";

		try {
			// get our connection to the database
			conn = DbBean.getDbConnection();

			// Get the session
			HttpSession session = request.getSession(true);
			if (session.isNew()) {
				String theParameterList = "?";
				Enumeration paramNames = request.getParameterNames();
				while (paramNames.hasMoreElements()) {
					String theParameterName = (String) paramNames.nextElement();
					theParameterList += theParameterName;
					theParameterList += "=";
					theParameterList += request.getParameter(theParameterName);
					theParameterList += "&";
				}
				response.sendRedirect(response.encodeRedirectURL("https://"
						+ request.getServerName() + request.getRequestURI()
						+ theParameterList));
			}

			// make sure we're pointing to the right port
			if (request.getServerPort() != thePort) {
				String theParameterList = "?";
				Enumeration paramNames = request.getParameterNames();

				while (paramNames.hasMoreElements()) {
					String theParameterName = (String) paramNames.nextElement();
					theParameterList += theParameterName;
					theParameterList += "=";
					theParameterList += request.getParameter(theParameterName);
					theParameterList += "&";
				}
				response.sendRedirect(response.encodeRedirectURL("https://"
						+ request.getServerName() + request.getRequestURI()
						+ ";jsessionid=" + request.getRequestedSessionId()
						+ theParameterList));
			}

			// make sure we're pointing to the right server name
			if (!(request.getServerName().equals((String) SingletonObjects
					.getInstance().getHost_name()))) {
				String theParameterList = "?";
				Enumeration paramNames = request.getParameterNames();

				while (paramNames.hasMoreElements()) {
					String theParameterName = (String) paramNames.nextElement();
					theParameterList += theParameterName;
					theParameterList += "=";
					theParameterList += request.getParameter(theParameterName);
					theParameterList += "&";
				}
				response.sendRedirect(response.encodeRedirectURL("http://"
						+ (String) SingletonObjects.getInstance()
								.getHost_name() + request.getRequestURI()
						+ ";jsessionid=" + request.getRequestedSessionId()
						+ theParameterList));
			}

			// check our language choice status
			if (session.getAttribute("languageId") != null) {
				myLanguageId = Integer.parseInt((String) session
						.getAttribute("languageId"));
			}

			// Get page details
			PageRoutines myPage = new PageRoutines();
			myPage.setConn(conn);
			myPage.setPage_name(page_name);
			myPage.setCt_language_id(myLanguageId);
			myPage.PageInfoByPageName();
			page_id = myPage.getPage_id();
			template_id = myPage.getTemplate_id();
			template_contents = myPage.getTemplate_contents();
			page_access_level = myPage.getPage_access_level();
			page_detail_contents = myPage.getPage_detail_contents();
			page_detail_title = myPage.getPage_detail_title();
			page_active_yn = myPage.getPage_active_yn();

			// Kick them out if they aren't supposed to be here
			if (page_access_level > 1) {
				if (session.getAttribute("user") == null) {
					// our user is not logged in, so they shouldn't be here
					theError = "You must log in before accessing the requested page!";
					redirect = true;
				} else {
					if (Integer.parseInt((String) session
							.getAttribute("customer_access_level")) < page_access_level) {
						// user does not have sufficient rights to be here
						theError = "You do not have access to the requested page!";
						redirect = true;
					}
				}
			}

			// if the page is not active (unpublished) redirect to error page.
			// only check if we have page_name
			if ((page_active_yn.equals("n")) && (page_name != null)) {
				theError = "The page you have requested is not currently active!";
				redirect = true;
			}

			// Get page template
			MasterTemplate = new HTMLTemplateDb(template_contents);
			ReplaceString = "http://" + request.getServerName() + "/";
			MasterTemplate.replaceDefault(ReplaceString);
			MasterTemplate.setBase("http://" + request.getServerName());
			MasterTemplate
					.setServerPort(String.valueOf(request.getServerPort()));

			// put the menus on the page
			Menu myMenu = new Menu();
			myMenu.setConn(conn);
			rs = myMenu.getAllActiveMenuNamesTags();
			GetMenu.setConn(conn);

			while (rs.next()) {
				theMenu = GetMenu.getHTMLMenu(rs.getString("menu_name"),
						myLanguageId, isSef, request, response, session);
				menuTag = rs.getString("menu_tag");
				MasterTemplate.searchReplace(menuTag, theMenu);
			}

			// Put generated by message on page
			GregorianCalendar x = new GregorianCalendar();
			Date today = new Date();
			int year = x.get(Calendar.YEAR);
			MasterTemplate.searchReplace(
					"<!-- VERSION -->",
					"<!-- \n" + "Generated on " + today.toString() + " by "
							+ pkg.getSpecificationTitle() + "\n"
							+ "Specification-Vendor: "
							+ pkg.getSpecificationVendor() + "\n"
							+ "Specification-Version: "
							+ pkg.getSpecificationVersion() + "\n"
							+ "Specification-Title: "
							+ pkg.getSpecificationTitle() + "\n"
							+ "Implementation-Vendor: "
							+ pkg.getImplementationVendor() + "\n"
							+ "Implementation-Version: "
							+ pkg.getImplementationVersion() + "\n"
							+ "Copyright (c) 2003 - " + year + " "
							+ pkg.getImplementationVendor()
							+ " All rights reserved.\n" + "-->");

			// Put the date on the page
			MasterTemplate.searchReplace("$DATE$", today.toString());

			// check to see how many languages we have to deal with
			CtLanguages myLanguages = new CtLanguages();
			myLanguages.setConn(conn);
			int numberOfLanguages = myLanguages.getNumberOfActiveLanguages();

			// if there is only one active language, don't bother to display
			// the menu of available languages.
			if (numberOfLanguages > 1) {
				// put the language menu on the page
				LanguageMenu.setConn(conn);
				MasterTemplate.searchReplace("$LANGUAGEMENU$",
						LanguageMenu.getLanguageDropDownListHTML(session));
			} else {
				MasterTemplate.searchReplace("$LANGUAGEMENU$", "");
			}

			// Write out any error messages
			if (session.getAttribute("Error") != null) {
				MasterTemplate.searchReplace("<!--ERROR-->",
						(String) session.getAttribute("Error"));
				session.removeAttribute("Error");
			}

			// put the modules on the page
			ModulePosition myPositions = new ModulePosition();
			myPositions.setConn(conn);

			rs = myPositions.getAllRecords();

			while (rs.next()) {
				theModuleHtml = ModuleObject.makeModuleHtml(conn,
						rs.getInt("module_position_id"), session, request);
				if (theModuleHtml.length() > 0) {
					MasterTemplate.searchReplace(
							rs.getString("module_position_tag"), theModuleHtml);
				} else {
					MasterTemplate.searchReplace(
							rs.getString("module_position_tag"), "");
				}
			}

			// Do any template specific stuff
			BuildTemplate(request, response, session);

			// display the page contents
			MasterTemplate.searchReplace("$BODY$", page_detail_contents);

			// Put the title on the page
			MasterTemplate.searchReplace("$TITLE$", page_detail_title);

			// put our tag on internal links
			MasterTemplate.searchReplace("$SESSION$",
					";jsessionid=" + request.getRequestedSessionId());

			// Do any custom page stuff
			BuildPage(request, response, session);

			// Write where we are to a sessional variable so we can get back
			// here with an error message if needed.
			session.setAttribute("lastPage", request.getRequestURI() + "?page="
					+ page_name);

			// Get the fully formatted page from HTMLTemplateDb object
			sb = MasterTemplate.getSb();

			// Write it out to the browser
			if (!redirect) {
				out.print(sb.toString());
			} else {
				System.out.println("Error is " + theError);
				session.setAttribute("Error", theError);
				url = response
						.encodeRedirectURL("/servlet/com.verilion.display.html.Page?page=Home");
				response.sendRedirect(url);
			}

			// close our connection to the database
			DbBean.closeDbConnection(conn);
			conn = null;
		} catch (IOException e) {
			e.printStackTrace();
			Errors.addError("Page:IOException:" + e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("Page:SQLException:" + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("Page:Exception:" + e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (conn != null) {
				DbBean.closeDbConnection(conn);
				conn = null;
			}
		}
		return PWtemp;
	}

	/**
	 * Method where page specific operations are conducted. In base class,
	 * simply get page body contents and write it to the HTMLTemplateDb object.
	 * Extend this class for page specific operations.
	 * 
	 * @throws Exception
	 */
	public void BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		try {
			// extend this class and override this method to do custom
			// processing on the page.
		} catch (Exception e) {
			Errors.addError("SecurePage:BuildPage:Exception:" + e.toString());
		}
	}

	/**
	 * Do any custom processing on PageTemplate.
	 * 
	 * @throws Exception
	 */
	public void BuildTemplate(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		try {
			// extend this class and override this method to do custom
			// processing on the template.
		} catch (Exception e) {
			Errors.addError("SecurePage:BuildTemplate:Exception:"
					+ e.toString());
		}
	}
}