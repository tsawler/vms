package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Role;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;

/**
 * Process displaying a Role
 * 
 * <P>
 * January 22, 2005
 * <P>
 * 
 * @author dholbrough
 *  
 */
public class ProcessDisplayRole extends ProcessPage {
	
	public XDisconnectedRowSet rs = new XDisconnectedRowSet();
	private String role_active_yn = "n";
	private String role_creation_date = "";
	private String role_description = "";
	private int role_id = 0;
	private String role_name = "";
	
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
		
		System.out.println( "Customer: " + session.getAttribute( "customer_id"));
		try {
			
			// get our customer id, passed as parameter
			role_id = Integer.parseInt((String) hm.get("id"));
			
			// get our customer record
			Role MyRole = new Role();
			MyRole.setConn(conn);
			MyRole.setRole_id(role_id);
			rs = MyRole.GetOneRole();
			
			// extract info from role table to local variables
			if (rs.next()) {
				
			}
			
			// Replace info on form with role info
			MasterTemplate.searchReplace("$ROLEID$", role_id + "");
			MasterTemplate.searchReplace("$ROLENAME$", role_name);
			MasterTemplate.searchReplace("$ROLEDESCRIPTION$", role_description);
			MasterTemplate.searchReplace("$ROLEACTIVEYN$", role_active_yn);
			MasterTemplate.searchReplace("$ROLECREATIONDATE$", role_creation_date);
			
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

