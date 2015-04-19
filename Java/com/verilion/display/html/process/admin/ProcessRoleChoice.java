/*
 * Created on Feb 1, 2005
 */
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DBUtils;
import com.verilion.database.Role;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.ResultsTable;

/**
 * @author dholbrough
 *
 */
public class ProcessRoleChoice  extends ProcessPage {
	   public HTMLTemplateDb BuildPage(
	         HttpServletRequest request,
	         HttpServletResponse response,
	         HttpSession session,
	         Connection conn,
	         HTMLTemplateDb MasterTemplate,
	         HashMap hm) throws Exception {

	      XDisconnectedRowSet rs = new XDisconnectedRowSet();

	      DBUtils myDbUtil = new DBUtils();
	      Role myRole = new Role();

	      myRole.setConn(conn);

	      try {
	         if (hm.get("limit") != null) {
	            myRole.setOffset(Integer.parseInt((String) hm
	                  .get("offset")));
	            myRole.setLimit(Integer
	                  .parseInt((String) hm.get("limit")));
	         }
	         
	         Enumeration e = session.getAttributeNames();
	         String customWhere = "where ";
	         while (e.hasMoreElements()) {
	            String theElement = (String) e.nextElement();
	            System.out.println("--------------------");
	            System.out.println("Element is " + theElement);
	            System.out.println("Element's value is " + session.getAttribute(theElement));
	            if (theElement.length() > 7) {
	                if (theElement.substring(0, 7).equals("filter_")) {
	                   System.out.println("=" + theElement.substring(7, theElement.length()) + "=");
	                   if (session.getAttribute(theElement) != null) {
	                      System.out.println("element not null");
	                      if (((String)session.getAttribute(theElement)).length() < 1) {
	                         System.out.println("element length is 0, removing...");
	                         session.removeAttribute(theElement);
	                      } else {
	                         customWhere += theElement.substring(7, theElement.length())
	                         + " ilike '"
	                         + session.getAttribute(theElement)
	                         + "%'";
	                         
	                      }
	                   } else {
	                      System.out.println("element is null");
	                      session.removeAttribute(theElement);
	                   }
	                }
	            }
	            	            
	         }
	         
	         myRole.createCustomWhere(customWhere);
	         rs = myRole.getAllRoles();

	         ResultsTable myTable = new ResultsTable(hm, session);

	         myTable.setSClass("roleobject");
	         myTable.setSUrl((String)request.getRequestURI());
	         myTable.setRs(rs);
	         String[] sColHeadings = { "Role Name" };
	         myTable.setSColHeadings(sColHeadings);
	         boolean[] bSortable = { true };
	         myTable.setBSortable(bSortable);
	         myTable.setSLinkColName("role_name");
	         String[] qbeFields = { "role_name" };
	         myTable.setSFilterFields(qbeFields);
	         String[] qbeDisplay = { "Role" };
	         myTable.setSFilterFieldsDisplay(qbeDisplay);

	         myDbUtil.setConn(conn);
	         myTable.setINumResultsetRows(myDbUtil.countRowsForTable(
	               "role",
	               "",
	               "role_name"));

	         myTable
	               .setSEditPage("/RoleDetail/roledetail/1/admin/1/content.do?"
	                     + "cancelPage="
	                     + request.getRequestURI()
	                     + "&id=");
	         myTable
	               .setSNewButtonUrl("/RoleDetail/roledetail/1/admin/1/content.do?id=-1&edit=false"
	                     + "&cancelPage="
	                     + request.getRequestURI());
	         myTable.setSIdColumn("role_id");
	         myTable.setSPublishCol("role_active_yn");

	         myTable.setSPublishedColumnName( "Current Status" );
	         myTable.setSPublishButton( "Activate Selected" );
	         myTable.setSPublishText( "Activated" );
	         myTable.setSPublishTitle( "This item is activated, it will be used to designate roles for this application. Click to deactivate." );
	         
	         myTable.setSUnpublishButton( "Deactivate Selected" );
	         myTable.setSUnpublishText( "Deactivated" );
	         myTable.setSUnpublishTitle( "This item is deactivated, it will not be used to designate roles for this application. Click to activate." );
	         
	         String myResultsTable = myTable.GenerateHTMLResultsTable();

	         MasterTemplate.searchReplace("<!--ResultsTable-->", myResultsTable);
	         rs.close();
	         rs = null;

	      } catch (Exception e) {
	         e.printStackTrace();
	         Errors.addError("ProcessRoleChoice:BuildPage:Exception:"
	               + e.toString());
	      } finally {
	         if (rs != null) {
	            rs.close();
	            rs = null;
	         }
	      }
	      return MasterTemplate;
	   }	
}