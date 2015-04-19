//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-02-03
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditRolesAssigned.java,v $
//Revision 1.1.2.1.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.1  2005/03/05 15:53:37  deanh
//Changes for RolesAssigned.
//
//Revision 1.1.2.3  2005/03/02 01:13:45  tcs
//Removed debugging info & minor fixes
//
//Revision 1.1.2.2  2005/02/24 02:25:32  deanh
//*** empty log message ***
//
//Revision 1.1.2.1  2005/02/04 02:15:24  deanh
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.RolesAssigned;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;


/**
 * Edit/create a role
 * 
 * <P>
 * February 03, 2005
 * <P>
 * 
 * @author dholbrough
 *  
 */
public class ProcessEditRolesAssigned extends ProcessPage {
	
	public HTMLTemplateDb ProcessForm(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			Connection conn,
			HTMLTemplateDb MasterTemplate,
			HashMap hm) throws Exception {
		
		int roles_assigned_id = 0;
		int roles_assigned_role_id = 0;
		int roles_assigned_customer_id = 0;
		String roles_assigned_active_yn = "";
		RolesAssigned myRole = new RolesAssigned();
		XDisconnectedRowSet rs = new XDisconnectedRowSet();
		XDisconnectedRowSet crs = new XDisconnectedRowSet();
		XDisconnectedRowSet catrs = new XDisconnectedRowSet();
		boolean okay = true;
		String theError = "";
		
		if (((String) request.getParameter("edit")).equals("false")) {
			
			try {
				// see if we have an id passed to us. If we do, this is not
				// our first time to this form
				if (request.getParameter("id") != null) {
					roles_assigned_id = Integer.parseInt((String) request
							.getParameter("id"));
				}
				if (roles_assigned_id > 0) {
					// get news item information
					myRole.setConn(conn);
					myRole.setRoles_assigned_id(roles_assigned_id);
					rs = myRole.getRolesAssignedById();
					
					if (rs.next()) {
						// put the title on the screen
						roles_assigned_role_id = rs.getInt("roles_assigned_role_id");
						MasterTemplate.searchReplace("$ROLESASSIGNEDROLEID$", roles_assigned_role_id + "");
						
						// put published status on screen
						roles_assigned_active_yn = rs.getString("roles_assigned_active_yn");
						if (roles_assigned_active_yn.equals("y")) {
							MasterTemplate.searchReplace("$SELECTYNY$", "selected");
							MasterTemplate.searchReplace("$SELECTYNN$", "");
						} else {
							MasterTemplate.searchReplace("$SELECTYNY$", "");
							MasterTemplate.searchReplace("$SELECTYNN$", "selected");
						}
						
						// put description on screen
						roles_assigned_customer_id = rs.getInt("roles_assigned_customer_id");
						MasterTemplate.searchReplace("$ROLESASSIGNEDCUSTOMERID$", roles_assigned_customer_id + "");
						MasterTemplate.searchReplace("$ROLEASSIGNEDID$", roles_assigned_id + "");
						MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));
					}
				} else {
					// we have no news item, so put blanks everywhere
					MasterTemplate.searchReplace("$ROLESASSIGNEDCUSTOMERID$", "");
					MasterTemplate.searchReplace("$ROLESASSIGNEDMODULEID$", "");
					MasterTemplate.searchReplace("$SELECTYNY$", "");
					MasterTemplate.searchReplace("$SELECTYNN$", "no");
					MasterTemplate.searchReplace("$ROLESASSIGNEDID$", roles_assigned_id + "");
					MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));
				}
				crs.close();
				crs = null;
				catrs.close();
				catrs = null;
				
			} catch (Exception e) {
				e.printStackTrace();
				Errors
				.addError("ProcessEditRolesAssigned:ProcessForm:Exception:"
						+ e.toString());
			} finally {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (crs != null) {
					crs.close();
					crs = null;
				}
				if (catrs != null) {
					catrs.close();
					catrs = null;
				}
			}
		} else {
			try {
				// get parameters
				roles_assigned_id = Integer
				.parseInt((String) request.getParameter("id"));
				if (((String) request.getParameter("roles_assigned_id") != null)
						&& (((String) request.getParameter("roles_assigned_role_id")).length() > 0)) {
					roles_assigned_role_id =   Integer.parseInt( request.getParameter("roles_assigned_customer_id"));
				} else {
					theError = "You must supply a title!";
					okay = false;
				}
				roles_assigned_customer_id = Integer.parseInt( request.getParameter("roles_assigned_customer_id"));	
				
				roles_assigned_active_yn = (String) request.getParameter("active_yn");
				
				if (okay) {
					
					myRole.setConn(conn);
					
					myRole.setRoles_assigned_customer_id(roles_assigned_customer_id);
					myRole.setRoles_assigned_active_yn(roles_assigned_active_yn);
					myRole.setRoles_assigned_role_id(roles_assigned_role_id);
					
					if (roles_assigned_id > 0) {
						myRole.setRoles_assigned_id(roles_assigned_id);
						myRole.updateRolesAssigned();
					} else {
						roles_assigned_id = myRole.addRolesAssigned();
					}
				}
				
				if (okay) {
					session.setAttribute("Error", "Roles Assigned update successful");
					response
					.sendRedirect((String) request.getParameter("cancelPage"));
				} else {
					session.setAttribute("Error", theError);
					response
					.sendRedirect((String) request.getParameter("cancelPage"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				Errors.addError("ProcessEditRolesAssigned:ProcessForm:Exception:"
						+ e.toString());
			}
			
		}
		return MasterTemplate;
	}
}