//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessPage.java,v $
//Revision 1.6.2.5.4.2.2.1.4.1.2.2.2.3  2008/11/11 17:53:50  tcs
//Removed debug code
//
//Revision 1.6.2.5.4.2.2.1.4.1.2.2.2.2  2008/03/07 12:03:53  tcs
//*** empty log message ***
//
//Revision 1.6.2.5.4.2.2.1.4.1.2.2.2.1  2007/04/04 16:53:52  tcs
//Increased max file size for uploaded files
//
//Revision 1.6.2.5.4.2.2.1.4.1.2.2  2007/01/23 19:28:52  tcs
//Fixed comments
//
//Revision 1.6.2.5.4.2.2.1.4.1.2.1  2007/01/22 19:18:21  tcs
//Changed validate data to boolean return type
//
//Revision 1.6.2.5.4.2.2.1.4.1  2006/12/26 12:15:28  tcs
//Corrected Javadocs
//
//Revision 1.6.2.5.4.2.2.1  2005/08/22 14:23:07  tcs
//Stub for non jsremoting validator
//
//Revision 1.6.2.5.4.2  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.6.2.5.4.1  2005/08/15 17:00:08  tcs
//Added support for n number of actions
//
//Revision 1.6.2.5  2005/04/26 14:16:46  tcs
//Changes to pagination for filtered search
//
//Revision 1.6.2.4  2005/04/22 18:14:38  tcs
//Modifications for jsp template model
//
//Revision 1.6.2.3  2005/01/25 17:04:13  tcs
//Fixed logic error in handling "filter" for resultstable
//
//Revision 1.6.2.2  2005/01/13 17:59:43  tcs
//Inital code for filtering query
//
//Revision 1.6.2.1  2005/01/13 15:57:55  tcs
//Added support for changing number of rows on ResultsTable
//
//Revision 1.6  2004/11/03 18:04:28  tcs
//Removed debugging info
//
//Revision 1.5  2004/11/02 15:25:10  tcs
//Formatting
//
//Revision 1.4  2004/10/27 18:06:31  tcs
//Removed debugging info
//
//Revision 1.3  2004/10/27 15:38:46  tcs
//Removed unused method
//
//Revision 1.2  2004/10/27 15:36:53  tcs
//Added handling of "action" parameter for menu choices
//
//Revision 1.1  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.admin.ProcessPerformAction;
import com.verilion.object.Errors;

/**
 * Base class for pages that require processing
 * 
 * <P>
 * October 18, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessPage implements ProcessPageInterface {

	/**
	 * Decides whether to display a page, process a form, repopulate a form with
	 * rejected data, or display an empty form with generated menus, lists,
	 * initial data, etc.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param conn
	 * @param MasterTemplate
	 * @param hm
	 * @return HTMLTemplateDb
	 * @throws Exception
	 */
	public HTMLTemplateDb ChooseAction(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		int action = 0;

		try {
			// if this is a multipart request, otherwise execute what's in the
			// catch clause
			MultipartRequest multi = new MultipartRequest(request,
					SingletonObjects.getInstance().getSystem_path() + "tmp/",
					50000 * 1024);

			try {
				try {
					action = Integer.parseInt(((String) multi
							.getParameter("action")));
				} catch (Exception ex) {

					action = 0;
				}
				if (action > 0) {
					if (Integer
							.parseInt(((String) multi.getParameter("action"))) > 0) {
						// we are processing a menu choice
						ProcessPerformAction myAction = new ProcessPerformAction();
						myAction.ProcessFormMultipart(request, response,
								session, conn, MasterTemplate, hm, multi);
					}
				} else {
					if (multi.getParameterNames().hasMoreElements()) {
						// we are getting posted form information

						if (multi.getParameter("reject") != null) {
							MasterTemplate = ProcessRejectedFormMultipart(
									request, response, session, conn,
									MasterTemplate, hm);
						} else if (request.getParameter("limit") != null) {
							// we are rebuilding a page with a new offset/limit
							// value
							// String theUrl = request.getRequestURI();
							String theUrl = (String) session
									.getAttribute("lastPage");
							theUrl = theUrl.replaceFirst(
									"/limit/" + hm.get("limit"), "/limit/"
											+ multi.getParameter("limit"));
							response.sendRedirect(theUrl);
						} else if (request.getParameter("filter") != null) {
							if (hm.get("clear") == null) {
								Enumeration enumParam = multi
										.getParameterNames();
								while (enumParam.hasMoreElements()) {
									String theElement = (String) enumParam
											.nextElement();
									if (theElement.length() > 7) {
										if (theElement.substring(0, 7).equals(
												"filter_")) {
											session.setAttribute(
													theElement,
													(String) multi
															.getParameter(theElement));
										}
									}
								}
							} else {
								Enumeration enumParam = session
										.getAttributeNames();
								while (enumParam.hasMoreElements()) {
									String theElement = (String) enumParam
											.nextElement();
									if (theElement.length() > 7) {
										if (theElement.substring(0, 7).equals(
												"filter_")) {
											session.removeAttribute(theElement);
										}
									}
								}
							}
							MasterTemplate = BuildPageMultipart(request,
									response, session, conn, MasterTemplate, hm);

						} else {
							// we are processing a submitted form

							MasterTemplate = ProcessFormMultipart(request,
									response, session, conn, MasterTemplate,
									hm, multi);
						}
					} else {
						// no form information - just displaying a page

						MasterTemplate = BuildPageMultipart(request, response,
								session, conn, MasterTemplate, hm);
					}
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		} catch (Exception e) {
			// not a multipart request

			try {
				// if we are being passed action, we are processing a menu item
				// such as publish, unpublish, delete, or cancel.
				try {
					action = Integer.parseInt(((String) request
							.getParameter("action")));
				} catch (Exception ex) {
					action = 0;
				}

				if (action > 0) {
					if (Integer.parseInt(((String) request
							.getParameter("action"))) > 0) {
						// we are processing a menu choice
						ProcessPerformAction myAction = new ProcessPerformAction();
						myAction.ProcessForm(request, response, session, conn,
								MasterTemplate, hm);
					}
				} else {
					if (request.getParameterNames().hasMoreElements()) {
						// we are getting posted form information
						if (request.getParameter("reject") != null) {
							MasterTemplate = ProcessRejectedForm(request,
									response, session, conn, MasterTemplate, hm);
						} else if (request.getParameter("limit") != null) {
							// we are rebuiling a page with a new offset/limit
							// value
							// String theUrl = request.getRequestURI();
							String theUrl = (String) session
									.getAttribute("lastPage");
							theUrl = theUrl.replaceFirst(
									"/limit/" + hm.get("limit"), "/limit/"
											+ request.getParameter("limit"));
							response.sendRedirect(theUrl);
						} else if (request.getParameter("filter") != null) {
							if (hm.get("clear") == null) {
								Enumeration enumParam = request
										.getParameterNames();
								while (enumParam.hasMoreElements()) {
									String theElement = (String) enumParam
											.nextElement();
									if (theElement.length() > 7) {
										if (theElement.substring(0, 7).equals(
												"filter_")) {
											session.setAttribute(
													theElement,
													(String) request
															.getParameter(theElement));
										}
									}
								}
							} else {
								Enumeration enumParam = session
										.getAttributeNames();
								while (enumParam.hasMoreElements()) {
									String theElement = (String) enumParam
											.nextElement();
									if (theElement.length() > 7) {
										if (theElement.substring(0, 7).equals(
												"filter_")) {
											session.removeAttribute(theElement);
										}
									}
								}
							}
							MasterTemplate = BuildPage(request, response,
									session, conn, MasterTemplate, hm);

						} else {
							// we are processing a submitted form
							MasterTemplate = ProcessForm(request, response,
									session, conn, MasterTemplate, hm);
						}
					} else {
						// System.out.println("calling buildpage");
						// no form information - just displaying a page
						MasterTemplate = BuildPage(request, response, session,
								conn, MasterTemplate, hm);
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				Errors.addError("com.verilion.display.html.process.ProcessPage:BuildPage:Exception:"
						+ ex.toString());
			}
		}

		return MasterTemplate;
	}

	/**
	 * Method to display a page. Extend this class and override this method.
	 * Used for page specific processing (i.e. displaying dynamically created
	 * forms, tables of data, etc.)
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param conn
	 * @param MasterTemplate
	 * @param hm
	 * @return HTMLTemplateDb
	 * @throws Exception
	 */
	public HTMLTemplateDb BuildPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		try {

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.html.ProcessPage:BuildPage:Exception:"
					+ e.toString());
		}
		return MasterTemplate;
	}

	public HTMLTemplateDb BuildPageMultipart(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		try {

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.html.ProcessPage:BuildPageMultipart:Exception:"
					+ e.toString());
		}
		return MasterTemplate;
	}

	/**
	 * Method to take posted form information and do something with it. Extend
	 * this class and override this method.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param conn
	 * @param MasterTemplate
	 * @param hm
	 * @return HTMLTemplateDb
	 * @throws Exception
	 */
	public HTMLTemplateDb ProcessForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		try {

			this.ValidateData(request, response, session, conn, MasterTemplate,
					hm);

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.html.ProcessPage:ProcessForm:Exception:"
					+ e.toString());
		}
		return MasterTemplate;
	}

	public HTMLTemplateDb ProcessFormMultipart(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm, MultipartRequest multi)
			throws Exception {

		try {

			this.ValidateData(request, response, session, conn, MasterTemplate,
					hm);

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.html.ProcessPage:ProcessFormMultipart:Exception:"
					+ e.toString());
		}
		return MasterTemplate;
	}

	/**
	 * Repopulate rejected form with original data. The assumption here is that
	 * we will be getting the original data back as part of the request, so we
	 * can repopulate the form.
	 * 
	 * Extend this class and override this method.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param conn
	 * @param MasterTemplate
	 * @param hm
	 * @return HTMLTemplateDb
	 * @throws Exception
	 */
	public HTMLTemplateDb ProcessRejectedForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		try {

			this.BuildPage(request, response, session, conn, MasterTemplate, hm);

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.html.ProcessPage:ProcessRejectedForm:Exception:"
					+ e.toString());
		}
		return MasterTemplate;
	}

	public HTMLTemplateDb ProcessRejectedFormMultipart(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		try {

			this.BuildPage(request, response, session, conn, MasterTemplate, hm);

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("com.verilion.display.html.ProcessPage:ProcessRejectedFormMultipart:Exception:"
					+ e.toString());
		}
		return MasterTemplate;
	}

	/**
	 * Perform custom validation. Extend this class and override this method.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param conn
	 * @param MasterTemplate
	 * @param hm
	 * @return HTMLTemplateDb
	 * @throws Exception
	 */
	public boolean ValidateData(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {
		boolean isValid = true;
		try {

		} catch (Exception e) {

		}

		return isValid;

	}
}