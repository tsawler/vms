//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessMaintainUsersChoose.java,v $
//Revision 1.1.2.2.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.2  2005/05/18 16:54:50  tcs
//Removed new button
//
//Revision 1.1.2.1  2005/05/18 12:58:50  tcs
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

import com.verilion.database.Customer;
import com.verilion.database.DBUtils;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.html.ResultsTable;

/**
* Maintain users chooser screen
* 
* <P>
* May 18, 2005
* <P>
* 
* @author tsawler
*  
*/
public class ProcessMaintainUsersChoose extends ProcessPage {

 /**
  * @param request
  * @param response
  * @param session
  * @throws Exception
  */
 public HTMLTemplateDb BuildPage(
       HttpServletRequest request,
       HttpServletResponse response,
       HttpSession session,
       Connection conn,
       HTMLTemplateDb MasterTemplate,
       HashMap hm) throws Exception {

    XDisconnectedRowSet rs = new XDisconnectedRowSet();
    Customer myCustomer = new Customer();
    myCustomer.setConn(conn);
    DBUtils myDbUtil = new DBUtils();
    
       try {
          if (hm.get("limit") != null) {
             myCustomer.setOffset(Integer.parseInt((String) hm
                   .get("offset")));
             myCustomer.setLimit(Integer
                   .parseInt((String) hm.get("limit")));
          }
          
          Enumeration e = session.getAttributeNames();
          String customWhere = "and ";
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
          myCustomer.createCustomWhere(customWhere);
          rs = myCustomer.getAllMembersWhoCanHaveRoles();

          ResultsTable myTable = new ResultsTable(hm, session);
          myTable.setBPublishColumn(false);
          myTable.setBUnpublishButton(false);
          myTable.setBPublishButton(false);
          myTable.setBDeleteButton(false);
          myTable.setBNewButton(false);

          myTable.setSClass("customer");
          String sUrl = "/admin/jpage/1/p/UsersChoose/a/userchoose/class/customer/limit/" 
             + (String) hm.get("limit")
             + "/offset/"
             + (String) hm.get("offset")
             + "/content.do";

          myTable.setSUrl(sUrl);
          myTable.setRs(rs);
          String[] sColHeadings = { "Last name", "First name" };
          String[] sColValues = { "customer_first_name" };
          myTable.setSColHeadings(sColHeadings);
          myTable.setSColValues(sColValues);
          boolean[] bSortable = { false };
          myTable.setBSortable(bSortable);
          myTable.setSLinkColName("customer_last_name");
          String[] qbeFields = { "customer_last_name" };
          myTable.setSFilterFields(qbeFields);
          String[] qbeDisplay = { "Last Name" };
          myTable.setSFilterFieldsDisplay(qbeDisplay);
          
          myDbUtil.setConn(conn);
          myTable.setINumResultsetRows(myDbUtil.countRowsForTable(
                "customer",
                customWhere,
                "customer_last_name"));

          myTable
                .setSEditPage("/admin/jpage/1/p/MaintainUser/a/maintainuser/content.do?"
                      + "cancelPage="
                      + sUrl
                      + "&id=");

          myTable.setSIdColumn("customer_id");
          
          String myResultsTable = myTable.GenerateHTMLResultsTable();

          MasterTemplate.searchReplace("<!--ResultsTable-->", myResultsTable);
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