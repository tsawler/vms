/*
 * Created on Mar 4, 2005
 */
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

/**
 * @author dholbrough
 *
 */
public class ProcessDisplayRolesAssigned extends ProcessPage {
	public XDisconnectedRowSet rs = new XDisconnectedRowSet();
	private String roles_assigned_active_yn = "n";
	private int roles_assigned_customer_id = 0;
	private int roles_assigned_module_id = 0;
	private int roles_assigned_role_id = 0;
	private int roles_assigned_id = 0;
	/**
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	public HTMLTemplateDb ProcessForm(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			Connection conn,
			HTMLTemplateDb MasterTemplate,
			HashMap hm) throws Exception {
		
		XDisconnectedRowSet rs = new XDisconnectedRowSet();

		try {
			
			// get our roles assigned role id, passed as parameter
			roles_assigned_role_id = Integer.parseInt((String) hm.get("id"));
			
			// get our roles assigned roles record
			RolesAssigned myRolesAssigned = new RolesAssigned();
			myRolesAssigned.setConn(conn);
			myRolesAssigned.setRoles_assigned_role_id(roles_assigned_role_id);
			rs = myRolesAssigned.GetOneRolesAssigned();
		
			
			// extract info from role table to local variables
			if (rs.next()) {
				
			}
			
			// Replace info on form with role info
			MasterTemplate.searchReplace("$ROLEsASSIGNEDID$", roles_assigned_id + "");
			MasterTemplate.searchReplace("$ROLEsASSIGNEDROLEID$", roles_assigned_role_id + "");
			MasterTemplate.searchReplace("$ROLEsASSIGNEDCUSTOMERID$", roles_assigned_customer_id + "");
			MasterTemplate.searchReplace("$ROLEsASSIGNEDMODULEID$", roles_assigned_module_id + "");
			MasterTemplate.searchReplace("$ROLEsASSIGNEDACTIVEYN$", roles_assigned_active_yn);
			
			rs.close();
			rs = null;
			
		} catch (Exception e) {
			
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
		return MasterTemplate;
	}
}
