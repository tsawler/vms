//------------------------------------------------------------------------------
//Copyright (c) 2006 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-12-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditCareerLocation.java,v $
//Revision 1.1.2.1  2006/12/19 19:28:10  tcs
//Inital entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Edit/add a career location
 * 
 * <P>
 * December 18, 2006
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessEditCareerLocation extends ProcessPage {

	public HTMLTemplateDb ProcessForm(
	         HttpServletRequest request,
	         HttpServletResponse response,
	         HttpSession session,
	         Connection conn,
	         HTMLTemplateDb MasterTemplate,
	         HashMap hm) throws Exception {

	      ResultSet rs = null;
	      
	      String theTitle = "";
	      String cancelPage = "";
	      int career_location_id = 0;
	      cancelPage = (String) request.getParameter("cancelPage");
	      career_location_id = Integer.parseInt((String) request.getParameter("id"));
	      
	      if (((String) request.getParameter("edit")).equals("false")) {
	         try {
	        	if (career_location_id == 0) {
		            MasterTemplate.searchReplace("$TITLE$", "");
		            MasterTemplate.searchReplace("$CANCEL$", cancelPage);
		            MasterTemplate.searchReplace("$ID$", career_location_id + "");
	        	} else {
	        		Statement st = null;
	        		st = conn.createStatement();
	        		String query = "select * from career_locations where career_locations_id = " + career_location_id;
	        		ResultSet myrs = null;
	        		myrs = st.executeQuery(query);
	        		if (myrs.next()) {
		        		theTitle = myrs.getString("career_location");
	        		}
	        		myrs.close();
	        		myrs = null;
	        		st.close();
	        		st = null;
	        		MasterTemplate.searchReplace("$TITLE$", theTitle);
		            MasterTemplate.searchReplace("$CANCEL$", cancelPage);
		            MasterTemplate.searchReplace("$ID$", career_location_id + "");
	        	}
	         } catch (Exception e) {
	            e.printStackTrace();
	            Errors
	                  .addError("ProcessEditCareerLocation:ProcessForm:Exception:"
	                        + e.toString());
	         } finally {
	            if (rs != null) {
	               rs.close();
	               rs = null;
	            }
	         }
	      } else {

	         theTitle = (String) request
	               .getParameter("title");
	         
	         if (career_location_id > 0) {
	        	 String query = "update career_locations set career_location = ? where career_locations_id = ?";
	        	 PreparedStatement pst = null;
	        	 pst = conn.prepareStatement(query);
	        	 pst.setString(1, theTitle);
	        	 pst.setInt(2, career_location_id);
	        	 pst.executeUpdate();
	        	 pst.close();
	        	 pst = null;
	         } else {
	        	 String query = "insert into career_locations (career_location) values (?)";
	        	 PreparedStatement pst = null;
	        	 pst = conn.prepareStatement(query);
	        	 pst.setString(1, theTitle);
	        	 pst.executeUpdate();
	        	 pst.close();
	        	 pst = null;
	         }

	         // Generate completion message
	         session.setAttribute("Error", "Update successful");
	         response
	               .sendRedirect((String) request.getParameter("cancelPage"));

	      }
	      return MasterTemplate;
	   }
}