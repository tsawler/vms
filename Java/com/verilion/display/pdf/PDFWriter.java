//------------------------------------------------------------------------------
//Copyright (c) 2003-3004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-10-24
//Revisions
//------------------------------------------------------------------------------
//$Log: PDFWriter.java,v $
//Revision 1.9.2.1.4.1.6.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.9.2.1.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.9.2.1  2005/03/08 16:47:38  tcs
//Javadoc fix
//
//Revision 1.9  2004/11/05 13:55:02  tcs
//Cleaned up code
//
//Revision 1.8  2004/11/05 13:50:16  tcs
//Added todo
//
//Revision 1.7  2004/10/26 15:41:51  tcs
//Added intial test code for html parser
//
//Revision 1.6  2004/10/25 17:33:22  tcs
//Added support for news items
//
//Revision 1.5  2004/10/24 21:31:07  tcs
//Cleanedup code.
//
//Revision 1.4  2004/10/24 16:49:36  tcs
//Moved stripping of newlines over to TextUtils
//
//Revision 1.3  2004/10/24 15:57:39  tcs
//Corrected stripping of newlines
//
//Revision 1.2  2004/10/24 15:47:03  tcs
//Simplified code
//
//Revision 1.1  2004/10/24 12:24:14  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------

package com.verilion.display.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.verilion.database.DbBean;
import com.verilion.database.NewsItem;
import com.verilion.database.PageRoutines;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.utility.TextUtils;
import com.verilion.utility.html.parser.HTMLTokenizer;

/**
 * 
 * Gernic servlet that will generate a PDF document and send the document to the
 * client via ServletOutputStream
 * 
 * @author tsawler
 * 
 */
public class PDFWriter extends HttpServlet {

	private static final long serialVersionUID = -2552549899324924536L;
	public String page_name = "";
	public String action = "";
	public int myLanguageId = 1;
	public String page_detail_contents = "";
	public int page_access_level = 0;
	public String page_detail_title = "";
	public int page_id = 0;
	public String page_active_yn = "";
	public int thePort = 0;
	public StringBuffer sb;
	public HTMLTemplateDb MasterTemplate;
	public boolean redirect = false;
	public String url = "";
	public String theError = "";
	Package pkg = Package.getPackage("com.verilion");
	public Connection conn = null;
	public HashMap hm = new HashMap();
	public XDisconnectedRowSet rs = new XDisconnectedRowSet();

	public PDFWriter() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * 
	 * implement doGet so that we process all HTTP GET requests
	 * 
	 * @param request
	 *            HTTP request object
	 * @param response
	 *            HTTP response object
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {

		ByteArrayOutputStream baosPDF = null;
		HttpSession session = request.getSession();

		try {

			StringTokenizer st = new StringTokenizer(request.getRequestURI()
					.substring(1, request.getRequestURI().lastIndexOf("/")),
					"/");
			int i = st.countTokens();

			if (i < 3) {
				// we have to have at least three arguments - page, action, and
				// the
				// content.do part
				session.setAttribute("Error", "Invalid request");
				response.sendRedirect("/Home/display/1/content.do");
				return;
			}

			// get the page name
			if (st.hasMoreElements()) {
				page_name = (String) st.nextToken();
			}

			// get the action
			if (st.hasMoreElements()) {
				action = (String) st.nextToken();
			}

			// get the language
			if (st.hasMoreElements()) {
				myLanguageId = Integer.parseInt((String) st.nextToken());
			}

			// now read any other values into a hashmap, as
			// name value pairs
			while (st.hasMoreElements()) {
				hm.put(st.nextToken(), st.nextToken());
			}

			thePort = Integer.parseInt(SingletonObjects.getInstance()
					.getInsecure_port());

			// make sure we're pointing to the right port
			if (request.getServerPort() != thePort) {
				response.sendRedirect(response.encodeRedirectURL("http://"
						+ request.getServerName() + request.getRequestURI()));
				return;
			}

			// make sure we're pointing to the right server name
			if (!(request.getServerName().equals((String) SingletonObjects
					.getInstance().getHost_name()))) {
				response.sendRedirect(response.encodeRedirectURL("http://"
						+ (String) SingletonObjects.getInstance()
								.getHost_name() + request.getRequestURI()));
				return;
			}

			// check our language choice status
			if (session.getAttribute("languageId") != null) {
				myLanguageId = Integer.parseInt((String) session
						.getAttribute("languageId"));
			}

			try {
				// Get page details
				conn = DbBean.getDbConnection();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			PageRoutines myPage = new PageRoutines();
			myPage.setConn(conn);
			myPage.setPage_name(page_name);
			myPage.setCt_language_id(myLanguageId);

			try {
				myPage.PageInfoByPageName();
				if (myPage.getPage_id() == 0) {
					session.setAttribute("Error", "Invalid request");
					response.sendRedirect("/Home/display/1/content.do");
					return;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				session.setAttribute("Error", "Invalid request");
				response.sendRedirect("/Home/display/1/content.do");
				return;
			} catch (Exception e1) {
				e1.printStackTrace();
				session.setAttribute("Error", "Invalid request");
				response.sendRedirect("/Home/display/1/content.do");
				return;
			}
			if (action.equals("newsitem")) {
				int theItem = 0;
				NewsItem myNewsItem = new NewsItem();
				myNewsItem.setConn(conn);
				ResultSet rs = null;

				try {
					// get our parameter for the news item
					theItem = Integer.parseInt((String) hm.get("id"));
					myNewsItem.setNews_id(theItem);

					// get our news item from the database
					rs = myNewsItem.getNewsItemById();

					if (rs.next()) {
						page_detail_title = rs.getString("news_title");
						page_detail_contents = rs.getString("news_teaser_text")
								+ rs.getString("news_body_text");
					}
					rs.close();
					rs = null;
				} catch (Exception e) {

				}
			} else {
				page_id = myPage.getPage_id();
				page_access_level = myPage.getPage_access_level();
				page_detail_contents = myPage.getPage_detail_contents();
				page_detail_title = myPage.getPage_detail_title();
				page_active_yn = myPage.getPage_active_yn();
			}

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
			} else if (page_name == null) {
				theError = "The page you have requested is not currently active!";
				redirect = true;
			} else if (page_name.length() < 1) {
				theError = "The page you have requested is not currently active!";
				redirect = true;
			}

			if (!redirect) {
				Date today = new Date();
				page_detail_contents = TextUtils
						.RemoveHtmlEquivs(page_detail_contents);
				HTMLTokenizer ht = new HTMLTokenizer(page_detail_contents, true);
				Enumeration enumTokenList = ht.getTokens();
				while (enumTokenList.hasMoreElements()) {
					/**
					 * TODO parse html and change to pdf code
					 * 
					 */
					String sToken = enumTokenList.nextElement().toString();
					System.out.println("token: " + sToken);
					System.out.println("size: " + sToken.length());
				}

				baosPDF = generatePDFDocumentBytes(request, page_detail_title,
						today.toString(), "http://" + request.getServerName()
								+ "/" + request.getRequestURI(),
						page_detail_contents, this.getServletContext());

				StringBuffer sbFilename = new StringBuffer();
				sbFilename.append("filename_");
				sbFilename.append(System.currentTimeMillis());
				sbFilename.append(".pdf");

				response.setHeader("Cache-Control", "max-age=30");
				response.setContentType("application/pdf");

				StringBuffer sbContentDispValue = new StringBuffer();
				sbContentDispValue.append("inline");
				sbContentDispValue.append("; filename=");
				sbContentDispValue.append(sbFilename);

				response.setHeader("Content-disposition",
						sbContentDispValue.toString());
				response.setContentLength(baosPDF.size());
				ServletOutputStream sos;
				sos = response.getOutputStream();

				baosPDF.writeTo(sos);

				sos.flush();
			} else {
				session.setAttribute("Error", theError);
				url = response.encodeRedirectURL("/Home/display/1/content.do");
				response.sendRedirect(url);
				return;
			}

		} catch (DocumentException dex) {
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			writer.println(this.getClass().getName() + " caught an exception: "
					+ dex.getClass().getName() + "<br>");
			writer.println("<pre>");
			dex.printStackTrace(writer);
			writer.println("</pre>");
		} finally {
			if (baosPDF != null) {
				baosPDF.reset();
			}
		}

	}

	/**
	 * Generates PDF Document as outputstream.
	 * 
	 * @param request
	 * @param psTitle
	 * @param psFooter
	 * @param psURL
	 * @param psBodyText
	 * @param ctx
	 * @return ByteArrayOutputStream
	 * @throws DocumentException
	 */
	protected ByteArrayOutputStream generatePDFDocumentBytes(
			final HttpServletRequest request, final String psTitle,
			final String psFooter, final String psURL, final String psBodyText,
			final ServletContext ctx) throws DocumentException

	{
		// Document doc = new Document();
		Document doc = new Document(PageSize.LETTER, 72, 72, 72, 36);

		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfWriter docWriter = null;

		try {
			docWriter = PdfWriter.getInstance(doc, baosPDF);

			doc.addAuthor(this.getClass().getName());
			doc.addCreationDate();
			doc.addProducer();
			doc.addCreator(this.getClass().getName());
			doc.addTitle(psTitle);

			Phrase hPhrase = new Phrase(new Chunk(psTitle, FontFactory.getFont(
					FontFactory.TIMES_BOLD, 12)));
			HeaderFooter header = new HeaderFooter(hPhrase, false);
			header.setAlignment(1);
			header.setBorder(2);
			doc.setHeader(header);

			Phrase fPhrase = new Phrase(new Chunk(
					"This document can be found at " + psURL + "\nCreated on "
							+ psFooter, FontFactory.getFont(
							FontFactory.TIMES_ITALIC, 9)));
			HeaderFooter footer = new HeaderFooter(fPhrase, false);
			footer.setAlignment(1);
			doc.setFooter(footer);
			doc.open();
			String formattedText = psBodyText;
			// formattedText = psBodyText.replaceAll("<br />", "\n");
			formattedText = TextUtils.StripNewlines(formattedText);
			formattedText = TextUtils.StripHtml(formattedText);

			Paragraph p = new Paragraph(new Paragraph(new Chunk(formattedText,
					FontFactory.getFont(FontFactory.TIMES, 10))));
			p.setAlignment("JUSTIFY");
			doc.add(p);

		} catch (DocumentException dex) {
			baosPDF.reset();
			throw dex;
		} finally {
			if (doc != null) {
				doc.close();
			}
			if (docWriter != null) {
				docWriter.close();
			}
		}

		if (baosPDF.size() < 1) {
			throw new DocumentException("document has " + baosPDF.size()
					+ " bytes");
		}
		return baosPDF;
	}
}