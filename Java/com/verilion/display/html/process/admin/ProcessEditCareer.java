//------------------------------------------------------------------------------
//Copyright (c) 2006 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-11-29
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditCareer.java,v $
//Revision 1.1.2.2  2006/12/20 18:51:56  tcs
//Added support for competition numbers
//
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

import com.verilion.database.DBUtils;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit/add a career
 * 
 * <P>
 * November 29, 2006
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessEditCareer extends ProcessPage {

	public HTMLTemplateDb ProcessForm(
	         HttpServletRequest request,
	         HttpServletResponse response,
	         HttpSession session,
	         Connection conn,
	         HTMLTemplateDb MasterTemplate,
	         HashMap hm) throws Exception {

	      ResultSet rs = null;
	      
	      String theContents = "";
	      String theTitle = "";
	      String cancelPage = "";
	      String competitionNumber = "";
	      String theMenu = "";
	      int career_location_id = 0;
	      int career_type_id = 0;
	      String myContents = "";
	      int career_id = 0;
	      cancelPage = (String) request.getParameter("cancelPage");
	      career_id = Integer.parseInt((String) request.getParameter("id"));
	      
	      if (((String) request.getParameter("edit")).equals("false")) {
	         try {
	        	if (career_id == 0) {
		            MasterTemplate.searchReplace("$CAREERTITLE$", "");
		            MasterTemplate.searchReplace("$COMPETITION$", "");
		            MasterTemplate.searchReplace("$CANCEL$", cancelPage);
		            MasterTemplate.searchReplace("$EDITPAGECONTENTS$", "");
		            MasterTemplate.searchReplace("$ID$", career_id + "");
	        	} else {
	        		Statement st = null;
	        		st = conn.createStatement();
	        		String query = "select * from careers where career_id = " + career_id;
	        		ResultSet myrs = null;
	        		myrs = st.executeQuery(query);
	        		if (myrs.next()) {
		        		theContents = myrs.getString("career_text");
		        		competitionNumber = myrs.getString("competition_number");
		        		theTitle = myrs.getString("career_title");
		        		career_location_id = myrs.getInt("career_locations_id");
		        		career_type_id = myrs.getInt("career_type");
	        		}
	        		myrs.close();
	        		myrs = null;
	        		st.close();
	        		st = null;
	        		MasterTemplate.searchReplace("$CAREERTITLE$", theTitle);
		            MasterTemplate.searchReplace("$CANCEL$", cancelPage);
		            MasterTemplate.searchReplace("$COMPETITION$", competitionNumber);
		            myContents = theContents;
		            myContents = myContents.replaceAll("\"", "\\\"");
		            myContents = myContents.replaceAll("\'", "&#8217;");
		            myContents = myContents.replaceAll("\n", " ");
		            myContents = myContents.replaceAll("\r", " ");
		            MasterTemplate.searchReplace("$EDITPAGECONTENTS$", myContents);
		            MasterTemplate.searchReplace("$ID$", career_id + "");
	        	}
	            // create drop down list of locations
	            Statement st = null;
	            st = conn.createStatement();
	            String query = "select * from career_locations order by career_location";
	            rs = st.executeQuery(query);

	            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
	                  "career_locations_id",
	                  career_location_id,
	                  rs,
	                  "career_locations_id",
	                  "career_location",
	                  -1,
	                  "");
	            rs.close();
	            rs = null;
	            st.close();
	            st = null;
	            MasterTemplate.searchReplace("$ddlb2$", theMenu);
	            
	            st = conn.createStatement();
	            query = "select * from career_type order by career_type";
	            rs = st.executeQuery(query);

	            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
	                  "career_type_id",
	                  career_type_id,
	                  rs,
	                  "career_type_id",
	                  "career_type",
	                  -1,
	                  "");
	            rs.close();
	            rs = null;
	            st.close();
	            st = null;
	            MasterTemplate.searchReplace("$ddlb1$", theMenu);
	            
	            // Now fill in the rest of the form with the data particular to
	            // this page.
	            myContents = myContents.replaceAll("\"", "\\\"");
	            myContents = myContents.replaceAll("\'", "&#8217;");
	            myContents = myContents.replaceAll("\n", " ");
	            myContents = myContents.replaceAll("\r", " ");
	            //myContents = myContents.replaceAll("\p", " ");
	            MasterTemplate.searchReplace("$EDITPAGECONTENTS$", myContents);

	         } catch (Exception e) {
	            e.printStackTrace();
	            Errors
	                  .addError("ProcessEditPage:ProcessForm:Exception:"
	                        + e.toString());
	         } finally {
	            if (rs != null) {
	               rs.close();
	               rs = null;
	            }
	         }
	      } else {
	    	 DBUtils myDbUtil = new DBUtils();
	         theTitle = (String) request
	               .getParameter("career_title");
	         competitionNumber = (String) request.getParameter("competition_number");
	         theContents = (String) request.getParameter("career_text");
	         theContents = myDbUtil.replace(theContents, "src=\"..", "src=\"");
	         theContents = myDbUtil.replace(theContents, "&#39;", "&#8217;");
	         int charVal = 146; // the sgml character used for single quotes
	         char theChar = (char) charVal;
	         String sQuote = String.valueOf(theChar);
	         theContents = myDbUtil.replace(theContents, sQuote, "&#8217;");
	         career_location_id = Integer.parseInt((String) request.getParameter("career_locations_id"));
	         career_type_id = Integer.parseInt((String) request.getParameter("career_type_id"));
	         
	         if (career_id > 0) {
	        	 String query = "update careers set career_title = ?, career_text = ?, "
	        		 + "career_locations_id = ?, career_type = ?, competition_number = ? where career_id = ?";
	        	 PreparedStatement pst = null;
	        	 pst = conn.prepareStatement(query);
	        	 pst.setString(1, theTitle);
	        	 pst.setString(2, theContents);
	        	 pst.setInt(3, career_location_id);
	        	 pst.setInt(4, career_type_id);
	        	 pst.setString(5, competitionNumber);
	        	 pst.setInt(6, career_id);
	        	 pst.executeUpdate();
	        	 pst.close();
	        	 pst = null;
	         } else {
	        	 String query = "insert into careers (career_title, career_text, career_locations_id, "
	        		 + "career_type, competition_number) values (?, ?, ?, ?, ?)";
	        	 PreparedStatement pst = null;
	        	 pst = conn.prepareStatement(query);
	        	 pst.setString(1, theTitle);
	        	 pst.setString(2, theContents);
	        	 pst.setInt(3, career_location_id);
	        	 pst.setInt(4, career_type_id);
	        	 pst.setString(5, competitionNumber);
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