//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessUserEdit.java,v $
//Revision 1.1.2.2.2.1  2005/05/20 17:27:05  tcs
//Fixed error when all roles removed
//
//Revision 1.1.2.2  2005/05/18 16:54:08  tcs
//First functional version
//
//Revision 1.1.2.1  2005/05/18 14:20:35  tcs
//Initial entry, in progress
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Customer;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Add/remove roles to a user
 * 
 * <P>
 * May 18 2005
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessUserEdit extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      XDisconnectedRowSet rs2 = new XDisconnectedRowSet();
      XDisconnectedRowSet rs3 = new XDisconnectedRowSet();
      Customer myCustomer = new Customer();
      int customerId = Integer.parseInt((String) request.getParameter("id"));

      myCustomer.setConn(conn);
      myCustomer.setCustomer_id(customerId);

      if (customerId > 0) {

         if (((String) request.getParameter("edit")).equals("false")) {
            // not processing data -- just populating form
            try {
               MasterTemplate.searchReplace("$CANCEL$", (String) request
                     .getParameter("cancelPage"));

               rs = myCustomer.GetOneCustomer();
               if (rs.next()) {
                  MasterTemplate.searchReplace("$USERNAME$", rs
                        .getString("customer_first_name")
                        + " "
                        + rs.getString("customer_last_name"));
                  MasterTemplate.searchReplace("$CUSTOMERID$", customerId + "");
                  // get excluded public pages
                  rs2 = myCustomer.getInactiveRolesForCustomerId(customerId);
                  String excludedPages = "";
                  while (rs2.next()) {
                     excludedPages += "<option value=\""
                           + rs2.getInt("role_id")
                           + "\">"
                           + rs2.getString("role_name")
                           + "</option>";
                  }
                  MasterTemplate.searchReplace("$EXPAGE$", excludedPages);
                  rs3 = myCustomer.getAllRolesForCustomerId(customerId);
                  String includedPages = "";
                  while (rs3.next()) {
                     includedPages += "<option value=\""
                           + rs2.getInt("role_id")
                           + "\">"
                           + rs2.getString("role_name")
                           + "</option>";
                  }
                  MasterTemplate.searchReplace("$INCPAGE$", includedPages);
               }

            } catch (Exception e) {
               e.printStackTrace();
               Errors.addError("ProcessUserEdit:ProcessForm:Exception:"
                     + e.toString());
            } finally {
               if (rs != null) {
                  rs.close();
                  rs = null;
               }
            }
            return MasterTemplate;
         } else {
            try {
            // get roles for this user
            String[] paramValues = new String[request
                  .getParameterValues("inc_pages").length];
            paramValues = request.getParameterValues("inc_pages");

            // delete the old entries
            myCustomer.DeleteRolesForUserId(customerId);

            // add new roles
            myCustomer.AddRolesForUserId(customerId, paramValues);
            } catch (Exception e) {
               // all roles removed from user
               myCustomer.DeleteRolesForUserId(customerId);
            }
            //myCustomer.deleteEntriesForRoleId(customerId);
            session.setAttribute("Error", "Changes have been saved");
            return this.BuildPage(
                  request,
                  response,
                  session,
                  conn,
                  MasterTemplate,
                  hm);

         }
      } else {
         return MasterTemplate;
      }

   }

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      XDisconnectedRowSet rs2 = new XDisconnectedRowSet();
      XDisconnectedRowSet rs3 = new XDisconnectedRowSet();

      Customer myCustomer = new Customer();
      int customerId = Integer.parseInt((String) request.getParameter("id"));

      myCustomer.setConn(conn);
      myCustomer.setCustomer_id(customerId);

      try {
         MasterTemplate.searchReplace("$CANCEL$", (String) request
               .getParameter("cancelPage"));

         rs = myCustomer.GetOneCustomer();
         if (rs.next()) {
            MasterTemplate.searchReplace("$USERNAME$", rs
                  .getString("customer_first_name")
                  + " "
                  + rs.getString("customer_last_name"));
            MasterTemplate.searchReplace("$CUSTOMERID$", customerId + "");
            // get excluded public pages
            rs2 = myCustomer.getInactiveRolesForCustomerId(customerId);
            String excludedPages = "";
            while (rs2.next()) {
               excludedPages += "<option value=\""
                     + rs2.getInt("role_id")
                     + "\">"
                     + rs2.getString("role_name")
                     + "</option>";
            }
            MasterTemplate.searchReplace("$EXPAGE$", excludedPages);
            rs3 = myCustomer.getAllRolesForCustomerId(customerId);
            String includedPages = "";
            while (rs3.next()) {
               includedPages += "<option value=\""
                     + rs2.getInt("role_id")
                     + "\">"
                     + rs2.getString("role_name")
                     + "</option>";
            }
            MasterTemplate.searchReplace("$INCPAGE$", includedPages);
         }

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ProcessUserEdit:ProcessForm:Exception:"
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