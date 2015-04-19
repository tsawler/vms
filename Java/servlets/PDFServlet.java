package servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.verilion.beans.StudentBeans;
import com.verilion.database.DbBean;
import com.verilion.database.GenericTable;
import com.verilion.object.mail.SendMessage;

/**
 * 
 * @author tcs
 * @version
 */
public class PDFServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2119950933610578640L;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws Exception
	 * @throws SQLException
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		response.setContentType("text/html");

		Connection conn = null;
		String first_name = "Jack";
		String last_name = "Will";
		String course_name = "My good course";
		int course_id = 0;
		int customer_id = 0;
		GenericTable myGt = new GenericTable();
		String hours = "1";
		String theDate = "15 June 2009";
		StudentBeans myStudent = new StudentBeans();
		HttpSession session = request.getSession(false);
		String course_completed_yn = "n";
		int cme = 0;
		int cmetype = 0;
		XDisconnectedRowSet drs = new XDisconnectedRowSet();
		String mainpro_yn = "n";
		String royal_college_yn = "n";
		boolean okay_to_do_cert = true;
		String theErrorMessage = "";
		String cert_type = "";
		String thePDF = "";
		int course_type_id = 0;

		
		Date dttNow = new Date();
		DateFormat MyDateFormatInstance = DateFormat.getDateTimeInstance(
				DateFormat.LONG, DateFormat.SHORT);
		theDate = MyDateFormatInstance.format(dttNow);
		
		// get the course id (as parameter)
		try {
			course_id = Integer.parseInt((String) request.getParameter("cid"));
		} catch (Exception e) {
			course_id = 0;
		}

		// get student info from bean (in session)
		if (session.getAttribute("StudentBean") != null) {
			myStudent = (StudentBeans) session.getAttribute("StudentBean");
			customer_id = myStudent.getCustomer_id();
			first_name = myStudent.getFirst_name();
			last_name = myStudent.getLast_name();
			cme = myStudent.getCme();
			cmetype = myStudent.getCmetype();
			thePDF = "certificate_" + last_name + "_" + System.currentTimeMillis()+ ".pdf";
		} else {
			// they are not logged in
			response.sendRedirect("/");
		}
		
		try {
			// Get a connection
			conn = DbBean.getDbConnection();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		// look up customer info to see if course completed
		if (customer_id > 0) {
			myGt.setConn(conn);
			myGt.setSTable("course_students");
			myGt.setSSelectWhat("*");
			myGt.setSCustomWhere("and course_id = " + course_id + " and customer_id = " + customer_id);
			drs = myGt.getAllRecordsDisconnected();
			while (drs.next()) {
				course_completed_yn = drs.getString("course_completed_yn");
			}
			drs.close();
			drs = null;
			System.out.println("cid: " + customer_id + " & cid = " + course_id + " & comp = " + course_completed_yn);
			if (course_completed_yn.equalsIgnoreCase("n")) {
				theErrorMessage = "You have not completed this course!";
				okay_to_do_cert = false;
			}
		}
		
		if (course_completed_yn.equalsIgnoreCase("y")) {
			// look up course info
			myGt.setConn(conn);
			myGt.setSTable("courses");
			myGt.setSSelectWhat("*");
			myGt.setSCustomWhere("and id = " + course_id);
			drs = myGt.getAllRecordsDisconnected();
			while (drs.next()) {
				course_name = drs.getString("course_name");
				hours = drs.getString("hours_value");
				mainpro_yn = drs.getString("mainpro_yn");
				royal_college_yn = drs.getString("royal_college_yn");
				course_type_id = drs.getInt("course_category_id");
			}

			// now see if the certificate is available for this course
			if (cme == 1) {
				// the student wants cme
				if (cmetype == 1) {
					// it's a mainpro course they want credits for
					if (mainpro_yn.equalsIgnoreCase("y")) {
						if (course_type_id != 2) {
							cert_type = "m";
						} else {
							cert_type = "d";
						}
					} else {
						theErrorMessage = "This course is not eligible for MAINPRO-1 credits.";
						okay_to_do_cert = false;
					}
				} else if (cmetype == 2) {
					// it's a royal college course they want credits for
					if (royal_college_yn.equalsIgnoreCase("y")) {
						cert_type = "r";
					} else {
						theErrorMessage = "This course is not eligible for Royal College credits.";
						okay_to_do_cert = false;
					}
				}
			} else {
				// cert of participation
				cert_type = "p";
			}

			// debug
			
			if (okay_to_do_cert) {

				if (cert_type.equalsIgnoreCase("m")) {
					// doing a mainpro cert
					PdfReader reader;
	
					reader = new PdfReader(
							"/home/httpd/grandroundsnow.com/http/mainpro.pdf");
					PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
							"/home/httpd/grandroundsnow.com/http/certs/" + thePDF));
	
					PdfContentByte cb;
					cb = stamp.getOverContent(1);
	
					// we tell the ContentByte we're ready to draw text
					cb.beginText();
	
					BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
							BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					cb.setFontAndSize(bf, 18);
	
					// we draw some text on a certain position
					// position is x,y where x is horizontal (from left) and y is
					// vertical (from bottom)
	
					// student name
					cb.setFontAndSize(bf, 18);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, first_name
							+ " " + last_name, 400, 315, 0);
	
					// course name
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, course_name,
							400, 240, 0);
	
					// hours course is worth
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, hours, 543,
							208, 0);
	
					// date of course
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, theDate, 205,
							115, 0);
	
					// finished drawing text
					cb.endText();
	
					stamp.close();
				} else if (cert_type.equalsIgnoreCase("r")) {
					// royal college
					PdfReader reader;
					
					reader = new PdfReader(
							"/home/httpd/grandroundsnow.com/http/royalcollege.pdf");
					PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
							"/home/httpd/grandroundsnow.com/http/certs/" + thePDF));
	
					PdfContentByte cb;
					cb = stamp.getOverContent(1);
	
					// we tell the ContentByte we're ready to draw text
					cb.beginText();
	
					BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
							BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					cb.setFontAndSize(bf, 18);
	
					// we draw some text on a certain position
					// position is x,y where x is horizontal (from left) and y is
					// vertical (from bottom)
	
					// student name
					cb.setFontAndSize(bf, 18);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, first_name
							+ " " + last_name, 400, 315, 0);
	
					// course name
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, course_name,
							400, 243, 0);
	
					// hours course is worth
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, hours, 543,
							208, 0);
	
					// date of course
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, theDate, 205,
							115, 0);
	
					// finished drawing text
					cb.endText();
	
					stamp.close();
				} else if (cert_type.equalsIgnoreCase("d")) {
					// doing a mainpro cert for DAL
					PdfReader reader;
	
					reader = new PdfReader(
							"/home/httpd/grandroundsnow.com/http/mainprodal.pdf");
					PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
							"/home/httpd/grandroundsnow.com/http/certs/" + thePDF));
	
					PdfContentByte cb;
					cb = stamp.getOverContent(1);
	
					// we tell the ContentByte we're ready to draw text
					cb.beginText();
	
					BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
							BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					cb.setFontAndSize(bf, 18);
	
					// we draw some text on a certain position
					// position is x,y where x is horizontal (from left) and y is
					// vertical (from bottom)
	
					// student name
					cb.setFontAndSize(bf, 18);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, first_name
							+ " " + last_name, 400, 315, 0);
	
					// course name
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, course_name,
							400, 240, 0);
	
					// hours course is worth
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, hours, 350,
							190, 0);
	
					// date of course
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, theDate, 205,
							115, 0);
	
					// finished drawing text
					cb.endText();
	
					stamp.close();
				} else {
					// participation
					PdfReader reader;
					
					reader = new PdfReader(
							"/home/httpd/grandroundsnow.com/http/participation.pdf");
					PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
							"/home/httpd/grandroundsnow.com/http/certs/" + thePDF));
	
					PdfContentByte cb;
					cb = stamp.getOverContent(1);
	
					// we tell the ContentByte we're ready to draw text
					cb.beginText();
	
					BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
							BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					cb.setFontAndSize(bf, 18);
	
					// we draw some text on a certain position
					// position is x,y where x is horizontal (from left) and y is
					// vertical (from bottom)
	
					// student name
					cb.setFontAndSize(bf, 18);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, first_name
							+ " " + last_name, 400, 315, 0);
	
					// course name
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, course_name,
							400, 213, 0);

					// date of course
					cb.setFontAndSize(bf, 12);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, theDate, 205,
							115, 0);
	
					// finished drawing text
					cb.endText();
	
					stamp.close();
				}
			}

		}
		try {
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		conn = null;
		PrintWriter out = response.getWriter();
		if (theErrorMessage.length() == 0){
			out.println("<a href=\"/certs/" + thePDF + "\">Click here to download your certificate</a>"
					+ "<br /><br />Please take a moment to complete the <a target\"_blank\" href=\"/survey.jsp?course=" 
					+ course_name
					+ "\">course evaluation</a>. <strong><br />(CME Credits are only valid if one submits a course evaluation).</strong>"
			);
			
			// email the student a cert
			String email = myStudent.getEmail_address();
			String subject = "Your Certificate from grandroundsnow.com";
			String from = "donotreply@grandroundsnow.com";
			String theText = "Attached is your certificate from grandroundsnow.com.";
			SendMessage.setTo(email);
			SendMessage.setFrom(from);
			SendMessage.setSubject(subject);
			SendMessage.setMessage(theText);
			SendMessage.SendEmailWithAttachment("/home/httpd/grandroundsnow.com/http/certs/", thePDF);
			System.out.println("Sent email to " + email);
			
		} else {
			out.println(theErrorMessage);
		}
		out.close();
		
	}


	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}
	// </editor-fold>
}
