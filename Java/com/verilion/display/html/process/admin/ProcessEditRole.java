//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-02-03
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditRole.java,v $
//Revision 1.1.2.3.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
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


import com.verilion.database.DBUtils;

import com.verilion.database.Role;
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
public class ProcessEditRole extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int role_id = 0;
      String role_name = "";
      String role_description = "";
      String role_active_yn = "";
      Role myRole = new Role();
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
               role_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }
            if (role_id > 0) {
               // get news item information
               myRole.setConn(conn);
               myRole.setRole_id(role_id);
               rs = myRole.getRoleById();
               
               if (rs.next()) {
                  // put the title on the screen
                  role_name = rs.getString("role_name");
                  MasterTemplate.searchReplace("$ROLENAME$", role_name);

                  // put published status on screen
                  role_active_yn = rs.getString("role_active_yn");
                  if (role_active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }

                  // put description on screen
                  role_description = rs.getString("role_description");
                  MasterTemplate.searchReplace("$ROLEDESCRIPTION$", role_description);
                  MasterTemplate.searchReplace("$ROLEID$", role_id + "");
                  MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$ROLENAME$", "");
               MasterTemplate.searchReplace("$ROLEDESCRIPTION$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "no");
               MasterTemplate.searchReplace("$ROLEID$", role_id + "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));
            }
            crs.close();
            crs = null;
            catrs.close();
            catrs = null;

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditRole:ProcessForm:Exception:"
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
            role_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("role_name") != null)
                  && (((String) request.getParameter("role_name")).length() > 0)) {
               role_name = (String) request.getParameter("role_name");
            } else {
               theError = "You must supply a title!";
               okay = false;
            }
            role_description = (String) request
                  .getParameter("role_description");
            // clean up the messy codes put in by the html editor, if any
            DBUtils myDbUtil = new DBUtils();
            role_description = myDbUtil.replace(role_description, "src=\"..", "src=\"");
            role_description = myDbUtil.replace(role_description, "&#39;", "&#8217;");
            int charVal = 146; // the sgml character used for single quotes
            char theChar = (char) charVal;
            String sQuote = String.valueOf(theChar);
            role_description = myDbUtil.replace(role_description, sQuote, "&#8217;");
 
            role_active_yn = (String) request.getParameter("active_yn");

            if (okay) {

               myRole.setConn(conn);

               myRole.setRole_description(role_description);
               myRole.setRole_active_yn(role_active_yn);
               myRole.setRole_name(role_name);

               if (role_id > 0) {
                  myRole.setRole_id(role_id);
                  myRole.updateRole();
               } else {
                  role_id = myRole.addRole();
               }
            }
            
            if (okay) {
               session.setAttribute("Error", "Role update successful");
               response
                     .sendRedirect((String) request.getParameter("cancelPage"));
            } else {
               session.setAttribute("Error", theError);
               response
                     .sendRedirect((String) request.getParameter("cancelPage"));
            }
         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditRole:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
   
   
   
}