//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-13
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditModule.java,v $
//Revision 1.1.2.1  2007/03/15 17:47:25  tcs
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

import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit/create a web link
 * 
 * @author tsawler
 * 
 */
public class ProcessEditModule extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int module_id = 0;
      int module_position_id = 0;
      String module_name = "";
      String active_yn = "n";
      GenericTable myTable = new GenericTable("modules");
      GenericTable myPosition = new GenericTable("module_position");
      String theMenu = "";
      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      myTable.setConn(conn);
      myPosition.setConn(conn);

      boolean okay = true;
      String theError = "";

      myTable.setConn(conn);

      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               module_id = Integer
                     .parseInt((String) request.getParameter("id"));
            }

            if (module_id > 0) {
               // get record information
               myTable.setSSelectWhat(" * ");
               myTable.setSCustomWhere("and module_id = " + module_id);
               rs = myTable.getAllRecordsDisconnected();

               if (rs.next()) {
                  // put the title on the screen
                  module_name = rs.getString("module_name");
                  MasterTemplate.searchReplace("$NAME$", module_name);
                  module_position_id = rs.getInt("module_position_id");

                  myPosition.setSSelectWhat(" * ");
                  myPosition
                        .setSCustomWhere("and module_position_active_yn = 'y'");
                  crs = myPosition.getAllRecordsDisconnected();

                  theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "module_position_id",
                        module_position_id,
                        crs,
                        "module_position_id",
                        "module_position_name",
                        0,
                        "");

                  // put the formatted drop down list box in HTML object
                  MasterTemplate.searchReplace("$DDLB$", theMenu);

                  // put published status on screen
                  active_yn = rs.getString("module_active_yn");
                  if (active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }

                  String cancelPage = (String) request
                        .getParameter("cancelPage");
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$NAME$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request
                     .getParameter("cancelPage"));

            }

            // put id in hidden field
            MasterTemplate.searchReplace("$ID$", module_id + "");

         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditModule:ProcessForm:Exception:"
                  + e.toString());
         } finally {
            if (rs != null) {
               rs.close();
               rs = null;
            }
         }
      } else {
         try {
            // get parameters
            module_id = Integer.parseInt((String) request.getParameter("id"));

            module_position_id = Integer.parseInt((String) request
                  .getParameter("module_position_id"));
            active_yn = (String) request.getParameter("active_yn");

            if (okay) {
               myTable.addUpdateFieldNameValuePair(
                     "module_active_yn",
                     active_yn,
                     "String");
               myTable.addUpdateFieldNameValuePair(
                     "module_position_id",
                     module_position_id + "",
                     "int");

               if (module_id > 0) {
                  myTable.setSCustomWhere(" and module_id = " + module_id);
                  myTable.setUpdateWhat("modules");
                  myTable.updateRecord();
               } else {
                  myTable.setSTable("modules");
                  myTable.insertRecord();
               }
            }

            if (okay) {
               session.setAttribute("Error", "Update successful");
               response.sendRedirect((String) request
                     .getParameter("cancelPage"));
            } else {
               session.setAttribute("Error", theError);
               response.sendRedirect((String) request
                     .getParameter("cancelPage"));
            }
         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditModule:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}