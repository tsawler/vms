//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-12
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessRoleEdit.java,v $
//Revision 1.1.2.6  2005/05/20 12:33:40  tcs
//Finished role manipulation
//
//Revision 1.1.2.5  2005/05/13 16:02:36  tcs
//Completed role add functionality
//
//Revision 1.1.2.4  2005/05/13 15:16:21  tcs
//Functional and complete
//
//Revision 1.1.2.3  2005/05/13 13:04:14  tcs
//Added update of role detail functionality
//
//Revision 1.1.2.2  2005/05/13 11:25:12  tcs
//In progress...
//
//Revision 1.1.2.1  2005/05/12 20:42:54  tcs
//Initial entry
//
//------------------------------------------------------------------------------
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
import com.verilion.object.Errors;

/**
 * Edit/add a role
 * 
 * <P>
 * May 12 2005
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessRoleEdit extends ProcessPage {

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
      Role myRole = new Role();
      int roleId = Integer.parseInt((String) request.getParameter("id"));

      myRole.setConn(conn);
      myRole.setRole_id(roleId);

      if (roleId > 0) {

         if (((String) request.getParameter("edit")).equals("false")) {
            // not processing data -- just populating form
            try {
               MasterTemplate.searchReplace("$CANCEL$", (String) request
                     .getParameter("cancelPage"));

               rs = myRole.GetOneRole();
               if (rs.next()) {
                  MasterTemplate.searchReplace("$ROLENAME$", rs
                        .getString("role_name"));
                  MasterTemplate.searchReplace("$ROLEDESC$", rs
                        .getString("role_description"));
                  MasterTemplate.searchReplace("$ROLEID$", roleId + "");
                  // get excluded public pages
                  rs2 = myRole.GetExcludedPageGroupsForRoleId();
                  String excludedPages = "";
                  while (rs2.next()) {
                     excludedPages += "<option value=\""
                           + rs2.getInt("admin_page_group_id")
                           + "\">"
                           + rs2.getString("admin_page_group_name")
                           + "</option>";
                  }
                  MasterTemplate.searchReplace("$EXPAGE$", excludedPages);
                  rs3 = myRole.GetIncludedPageGroupsForRoleId();
                  String includedPages = "";
                  while (rs3.next()) {
                     includedPages += "<option value=\""
                           + rs2.getInt("admin_page_group_id")
                           + "\">"
                           + rs2.getString("admin_page_group_name")
                           + "</option>";
                  }
                  MasterTemplate.searchReplace("$INCPAGE$", includedPages);
               }

            } catch (Exception e) {
               e.printStackTrace();
               Errors.addError("ProcessRoleEdit:ProcessForm:Exception:"
                     + e.toString());
            } finally {
               if (rs != null) {
                  rs.close();
                  rs = null;
               }
            }
            return MasterTemplate;
         } else {

            // get role name and description
            String roleName = "";
            String roleDescription = "";
            roleName = request.getParameter("role_name");
            roleDescription = request.getParameter("role_description");
            myRole.ModifyRole(roleName, roleDescription, roleId);

            // get our new values for page groupings that are part of this role
            try {
               String[] paramValues = new String[request
                     .getParameterValues("inc_pages").length];
               paramValues = request.getParameterValues("inc_pages");
               // delete the old entries
               myRole.deleteEntriesForRoleId(roleId);

               // save the new entries for this role
               myRole.addEntriesForRoleId(roleId, paramValues);
               
               // add page information for page groupings to admin_page_group_role
               // first delete existing entries
               myRole.DeleteEntriesFromAdminPageRole(roleId);
               
               // now add new entries
               myRole.AddEntriesToAdminPageRole(roleId, paramValues);
            } catch (Exception e) {
               // no page groupings selected
               myRole.deleteEntriesForRoleId(roleId);
            }

            // update admin_page_role
            // first delete existing entries
            

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
         if (request.getParameter("edit").equals("false")) {
            // adding a role
            MasterTemplate.searchReplace("$CANCEL$", (String) request
                  .getParameter("cancelPage"));
            MasterTemplate.searchReplace("$ROLEID$", "-1");
            MasterTemplate.searchReplace("$INCPAGE$", "");
            MasterTemplate.searchReplace("$ROLENAME$", "");
            MasterTemplate.searchReplace("$ROLEDESC$", "");
            rs2 = myRole.GetExcludedPageGroupsForRoleId();
            String excludedPages = "";
            while (rs2.next()) {
               excludedPages += "<option value=\""
                     + rs2.getInt("admin_page_group_id")
                     + "\">"
                     + rs2.getString("admin_page_group_name")
                     + "</option>";

            }
            MasterTemplate.searchReplace("$EXPAGE$", excludedPages);
            return MasterTemplate;
         } else {
            // processing request to add a role
            String roleName = "";
            String roleDescription = "";
            String[] paramValues = new String[request
                  .getParameterValues("inc_pages").length];
            paramValues = request.getParameterValues("inc_pages");
            roleName = request.getParameter("role_name");
            roleDescription = request.getParameter("role_description");

            // add to role table and get new id
            myRole.setRole_active_yn("n");
            myRole.setRole_name(roleName);
            myRole.setRole_description(roleDescription);
            roleId = myRole.addRole();

            // add new page groupings for this role id
            myRole.addEntriesForRoleId(roleId, paramValues);
            myRole.setRole_id(roleId);

            MasterTemplate.searchReplace("$ROLEID$", roleId + "");
            MasterTemplate.searchReplace("$CANCEL$", (String) request
                  .getParameter("cancelPage"));
            MasterTemplate.searchReplace("$ROLENAME$", roleName);
            MasterTemplate.searchReplace("$ROLEDESC$", roleDescription);

            // get excluded public pages
            rs2 = myRole.GetExcludedPageGroupsForRoleId();
            String excludedPages = "";
            while (rs2.next()) {
               excludedPages += "<option value=\""
                     + rs2.getInt("admin_page_group_id")
                     + "\">"
                     + rs2.getString("admin_page_group_name")
                     + "</option>";
            }
            MasterTemplate.searchReplace("$EXPAGE$", excludedPages);
            rs3 = myRole.GetIncludedPageGroupsForRoleId();
            String includedPages = "";
            while (rs3.next()) {
               includedPages += "<option value=\""
                     + rs2.getInt("admin_page_group_id")
                     + "\">"
                     + rs2.getString("admin_page_group_name")
                     + "</option>";
            }
            MasterTemplate.searchReplace("$INCPAGE$", includedPages);
            session.setAttribute("Error", "Role successfully added.");
            return MasterTemplate;
         }

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

      Role myRole = new Role();
      int roleId = Integer.parseInt((String) request.getParameter("id"));

      myRole.setConn(conn);
      myRole.setRole_id(roleId);

      try {
         MasterTemplate.searchReplace("$CANCEL$", (String) request
               .getParameter("cancelPage"));

         rs = myRole.GetOneRole();
         if (rs.next()) {
            MasterTemplate.searchReplace("$ROLENAME$", rs
                  .getString("role_name"));
            MasterTemplate.searchReplace("$ROLEDESC$", rs
                  .getString("role_description"));
            MasterTemplate.searchReplace("$ROLEID$", roleId + "");
            // get excluded public pages
            rs2 = myRole.GetExcludedPageGroupsForRoleId();
            String excludedPages = "";
            while (rs2.next()) {
               excludedPages += "<option value=\""
                     + rs2.getInt("admin_page_group_id")
                     + "\">"
                     + rs2.getString("admin_page_group_name")
                     + "</option>";
            }
            MasterTemplate.searchReplace("$EXPAGE$", excludedPages);
            rs3 = myRole.GetIncludedPageGroupsForRoleId();
            String includedPages = "";
            while (rs3.next()) {
               includedPages += "<option value=\""
                     + rs2.getInt("admin_page_group_id")
                     + "\">"
                     + rs2.getString("admin_page_group_name")
                     + "</option>";
            }
            MasterTemplate.searchReplace("$INCPAGE$", includedPages);
         }

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ProcessRoleEdit:ProcessForm:Exception:"
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