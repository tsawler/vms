//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-13
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditStandardEmail.java,v $
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

/**
 * Edit standard email messages
 * 
 * @author tsawler
 * 
 */
public class ProcessEditStandardEmail extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int message_id = 0;
      String message_name = "";
      GenericTable myTable = new GenericTable("standard_email_messages");
      String message_text = "";
      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      myTable.setConn(conn);

      boolean okay = true;
      String theError = "";

      myTable.setConn(conn);

      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               message_id = Integer
                     .parseInt((String) request.getParameter("id"));
            }

            if (message_id > 0) {
               // get record information
               myTable.setSSelectWhat(" * ");
               myTable.setSCustomWhere("and message_id = " + message_id);
               rs = myTable.getAllRecordsDisconnected();

               if (rs.next()) {
                  // put the title on the screen
                  message_name = rs.getString("message_name");
                  message_text = rs.getString("message_text");
                  MasterTemplate.searchReplace("$NAME$", message_name);
                  message_text = message_text.replaceAll("\"", "\\\"");
                  message_text = message_text.replaceAll("\'", "&#8217;");
                  message_text = message_text.replaceAll("\n", " ");
                  message_text = message_text.replaceAll("\r", " ");
                  MasterTemplate.searchReplace("$TEXT$", message_text);
                  String cancelPage = (String) request
                        .getParameter("cancelPage");
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$NAME$", "");
               MasterTemplate.searchReplace("$TEXT$", "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request
                     .getParameter("cancelPage"));
            }

            // put id in hidden field
            MasterTemplate.searchReplace("$ID$", message_id + "");

         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditStandardEmail:ProcessForm:Exception:"
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
            message_id = Integer.parseInt((String) request.getParameter("id"));
            message_text = (String) request.getParameter("message_text");
            message_text = message_text.replaceAll("\"", "\\\"");
            message_text = message_text.replaceAll("\'", "&#8217;");
            message_text = message_text.replaceAll("\n", " ");
            message_text = message_text.replaceAll("\r", " ");
            message_name = (String) request.getParameter("message_name");

            if (okay) {
               myTable.addUpdateFieldNameValuePair(
                     "message_name",
                     message_name,
                     "String");
               myTable.addUpdateFieldNameValuePair(
                     "message_text",
                     message_text,
                     "String");

               if (message_id > 0) {
                  myTable.setSCustomWhere(" and message_id = " + message_id);
                  myTable.setUpdateWhat("standard_email_messages");
                  myTable.updateRecord();
               } else {
                  myTable.setSTable("standard_email_messages");
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
            Errors.addError("ProcessEditStandardEmail:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}