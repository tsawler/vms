//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-12
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessRoleChoose.java,v $
//Revision 1.1.2.2.2.1  2005/08/08 15:34:28  tcs
//Changes for new URL & jsp tag methodology
//
//Revision 1.1.2.2  2005/05/13 16:02:21  tcs
//Fixed new button URL
//
//Revision 1.1.2.1  2005/05/12 20:42:54  tcs
//Initial entry
//
//------------------------------------------------------------------------------
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
* Provides list of roles for edit etc
* 
* <P>
* May 12 2005
* <P>
* 
* @author tsawler
*  
*/
public class ProcessRoleChoose extends ProcessPage {

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
        if (theElement.length() > 7) {
            if (theElement.substring(0, 7).equals("filter_")) {
               if (session.getAttribute(theElement) != null) {
                  if (((String)session.getAttribute(theElement)).length() < 1) {
                     session.removeAttribute(theElement);
                  } else {
                     customWhere += theElement.substring(7, theElement.length())
                     + " ilike '"
                     + session.getAttribute(theElement)
                     + "%'";
                  }
               } else {
                  session.removeAttribute(theElement);
               }
            }
        }
     }
     myRole.createCustomWhere(customWhere);
     rs = myRole.getAllRoles();

     ResultsTable myTable = new ResultsTable(hm, session);

     myTable.setSClass("role");
     String sUrl = "/admin/jpage/1/p/RolesChoose/a/roleschoose/class/roles/admin/1/"
			+ "content.do";
     myTable.setSUrl(sUrl);
     
     myTable.setRs(rs);
     String[] sColHeadings = { "Role name" };
     myTable.setSColHeadings(sColHeadings);
     boolean[] bSortable = { false, false };
     myTable.setBSortable(bSortable);
     myTable.setSLinkColName("role_name");
     String[] qbeFields = { "role_name" };
     myTable.setSFilterFields(qbeFields);
     String[] qbeDisplay = { "Name" };
     myTable.setSFilterFieldsDisplay(qbeDisplay);
     
     myDbUtil.setConn(conn);
     myTable.setINumResultsetRows(myDbUtil.countRowsForTable(
           "role",
           customWhere,
           "role_name"));

     myTable
           .setSEditPage("/admin/jpage/1/p/RoleEdit/a/roleedit/class/roles/admin/1/content.do?"
                 + "cancelPage="
                 + sUrl
                 + "&id=");
     myTable
           .setSNewButtonUrl("/admin/jpage/1/p/RoleEdit/a/roleedit/class/roles/admin/1/content.do?"
                 + "cancelPage="
                 + sUrl
                 + "&id=-1&edit=false");
     myTable.setSIdColumn("role_id");
     myTable.setSPublishCol("role_active_yn");

     String myResultsTable = myTable.GenerateHTMLResultsTable();

     MasterTemplate.searchReplace("<!--ResultsTable-->", myResultsTable);
     rs.close();
     rs = null;

  } catch (Exception e) {
     e.printStackTrace();
     Errors.addError("ProcessRoleChoose:BuildPage:Exception:"
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