//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-08-03
//Revisions
//------------------------------------------------------------------------------
//$Log: MailPage.java,v $
//Revision 1.2.6.1.8.1.2.1  2009/07/22 16:29:36  tcs
//*** empty log message ***
//
//Revision 1.2.6.1.8.1  2007/01/28 00:49:22  tcs
//Minor edit
//
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/10/26 18:08:36  tcs
//Deprecated this class
//
//Revision 1.1  2004/08/03 18:57:01  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.CtLanguages;
import com.verilion.database.DbBean;
import com.verilion.database.Documents;
import com.verilion.database.Menu;
import com.verilion.database.ModulePosition;
import com.verilion.database.PageRoutines;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;
import com.verilion.object.GetMenu;
import com.verilion.object.html.modules.ModuleObject;
import com.verilion.object.mail.SendMessage;

/**
 * Email a page to someone.
 * 
 * @author tsawler
 * @deprecated we'll mail a link to the page instead. This is outdated and no
 *             longer completely functional.
 */
public class MailPage extends HttpServlet {

	private static final long serialVersionUID = -1620714046095837623L;
	public String to_email_address = "";
	public String from_email_address = "";
	public String page_name = "";
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
				.getInsecure_port());
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
		String theDocs = "";

		try {
			// get our parameters
			from_email_address = (String) request.getParameter("from");
			to_email_address = (String) request.getParameter("to");

			// get our connection to the database
			conn = DbBean.getDbConnection();

			// Get the session
			HttpSession session = request.getSession(true);

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
			display_in_iframe_yn = myPage.getDisplay_in_iframe_yn();
			iframe_url = myPage.getIframe_url();

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
			MasterTemplate.searchReplace("$SESSION$", "");

			// put any attached documents on page
			Documents myDoc = new Documents();
			myDoc.setConn(conn);
			myDoc.setPage_id(page_id);
			theDocs = "";

			rs = myDoc.getAllActiveDocumentsForPageId();

			while (rs.next()) {
				theDocs += "Attachment: " + rs.getString("document_title")
						+ " <a href=\"/library/download/"
						+ rs.getInt("document_id") + "/"
						+ rs.getString("document_name") + "\">"
						+ rs.getString("document_name") + "</a><br />";
			}

			MasterTemplate.searchReplace("<!--$DOCS$-->", theDocs);

			// Do any custom page stuff
			BuildPage(request, response, session);

			// Get the fully formatted page from HTMLTemplateDb object
			sb = MasterTemplate.getSb();

			SendMessage.setFrom(from_email_address);
			SendMessage.setTo(to_email_address);
			SendMessage.setSubject("A link from " + from_email_address);
			SendMessage.setMailHost(SingletonObjects.getInstance()
					.getMailhost());
			SendMessage.setMessage(sb.toString());

			SendMessage.SendHtmlEmail();

			session.setAttribute("Error", "Message sent");
			response.sendRedirect((String) session.getAttribute("lastPage"));

			rs.close();
			rs = null;

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
			Errors.addError("Page:BuildPage:Exception:" + e.toString());
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
			Errors.addError("Page:BuildTemplate:Exception:" + e.toString());
		}
	}
}