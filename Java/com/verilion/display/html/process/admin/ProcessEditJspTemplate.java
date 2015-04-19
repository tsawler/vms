//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-12-22
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditJspTemplate.java,v $
//Revision 1.1.2.2  2006/12/22 17:48:15  tcs
//Added TODO, so I won't forget...
//
//Revision 1.1.2.1  2006/12/22 17:47:15  tcs
//In progress
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.JspTemplate;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Edit/create a news item
 * 
 * <P>
 * January 12, 2005
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessEditJspTemplate extends ProcessPage {

	@SuppressWarnings("deprecation")
	public HTMLTemplateDb ProcessForm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn,
			HTMLTemplateDb MasterTemplate, HashMap hm) throws Exception {

		String jsp_template_name = "";
		String template_description = "";
		String template_code = "";
		String active_yn = "";
		int template_id = 0;
		XDisconnectedRowSet crs = new XDisconnectedRowSet();

		JspTemplate myJspTemplate = new JspTemplate();

		boolean okay = true;
		String theError = "";
		String cancelPage = request.getParameter("cancelPage");

		if (((String) request.getParameter("edit")).equals("false")) {

			try {
				// see if we have an id passed to us. If we do, this is not
				// our first time to this form
				if (request.getParameter("id") != null) {
					template_id = Integer.parseInt((String) request
							.getParameter("id"));
				}

				if (template_id > 0) {
					// get template info
					myJspTemplate.setConn(conn);
					crs = myJspTemplate.GetTemplateById(template_id);

					if (crs.next()) {
						// put published status on screen
						active_yn = crs.getString("jsp_template_active_yn");
						if (active_yn.equals("y")) {
							MasterTemplate.searchReplace("$SELECTYNY$",
									"selected");
							MasterTemplate.searchReplace("$SELECTYNN$", "");
						} else {
							MasterTemplate.searchReplace("$SELECTYNY$", "");
							MasterTemplate.searchReplace("$SELECTYNN$",
									"selected");
						}
						MasterTemplate
								.searchReplace(
										"$TEMPLATENAME$",
										crs.getString("jsp_template_name")
												+ ".jsp"
												+ "<input id=\"template_name\" type=\"hidden\" name=\"template_name\" value=\""
												+ crs
														.getString("jsp_template_name")
												+ "\">");
						MasterTemplate.searchReplace("$DESCRIPTION$", crs
								.getString("jsp_template_description"));
						MasterTemplate.searchReplace("$CANCEL$", cancelPage);
						MasterTemplate.searchReplace("$ID$", template_id + "");
						// now read in the jsp file
						// open file
						FileInputStream fstream = new FileInputStream(
								SingletonObjects.getInstance().getSystem_path()
										+ crs.getString("jsp_template_name")
										+ ".jsp");
						DataInputStream in = new DataInputStream(fstream);

						while (in.available() != 0) {
							template_code += (in.readLine());
							template_code += "\n";
						}
						in.close();
						MasterTemplate.searchReplace("$TEMPLATE$",
								template_code);
					}
				} else {
					// we have no template, so put blanks everywhere
					MasterTemplate.searchReplace("$ID$", "0");
					MasterTemplate
							.searchReplace("$TEMPLATENAME$",
									"<input id=\"template_name\" type=\"text\" class=\"inputbox\" name=\"template_name\" value=\"\" />");
					MasterTemplate.searchReplace("$DESCRIPTION$", "");
					MasterTemplate.searchReplace("$SELECTYNY$", "");
					MasterTemplate.searchReplace("$SELECTYNN$", "");
					MasterTemplate.searchReplace("$TEMPLATE$", "");
					MasterTemplate.searchReplace("$CANCEL$", cancelPage);
				}
				crs.close();
				crs = null;

			} catch (Exception e) {
				e.printStackTrace();
				Errors.addError("ProcessEditNewsItem:ProcessForm:Exception:"
						+ e.toString());
			} finally {
				if (crs != null) {
					crs.close();
					crs = null;
				}
			}
		} else {
			if (request.getParameter("edit").equals("true")) {
				try {
					// get parameters
					template_id = Integer.parseInt((String) request
							.getParameter("id"));
					template_description = (String) request
							.getParameter("description");
					template_code = (String) request.getParameter("template");
					jsp_template_name = (String) request
							.getParameter("template_name");
					active_yn = (String) request.getParameter("active_yn");

					if (okay) {

						myJspTemplate.setConn(conn);

						myJspTemplate.setJsp_template_name(jsp_template_name);
						myJspTemplate.setJsp_template_id(template_id);
						myJspTemplate
								.setJsp_template_description(template_description);
						myJspTemplate.setJsp_template_active(active_yn);

						if (template_id > 0) {
							//** TODO - update call
						} else {
							template_id = myJspTemplate.addJspTemplate();
							// now write the template file out
							FileOutputStream out;
							PrintStream p;

							if (!jsp_template_name.endsWith(".jsp")) {
								jsp_template_name += ".jsp";
							}
							out = new FileOutputStream(SingletonObjects
									.getInstance().getSystem_path()
									+ jsp_template_name);
							p = new PrintStream(out);
							p.print(template_code);
							p.close();
						}
					}

					if (okay) {
						session.setAttribute("Error",
								"Template update successful");
						response.sendRedirect((String) request
								.getParameter("cancelPage"));
					} else {
						session.setAttribute("Error", theError);
						response.sendRedirect((String) request
								.getParameter("cancelPage"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					Errors
							.addError("ProcessEditJspTemplate:ProcessForm:Exception:"
									+ e.toString());
				}
			}

		}
		return MasterTemplate;
	}
}