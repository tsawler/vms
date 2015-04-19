//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-13
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditQuote.java,v $
//Revision 1.1.2.1  2007/03/14 00:19:42  tcs
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

import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Edit/create a web link
 * 
 * @author tsawler
 *  
 */
public class ProcessEditQuote extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int quote_id = 0;
      String quote_name = "";
      String quote_text = "";
      String quote_active_yn = "n";
      GenericTable myTable = new GenericTable("quotes");
      XDisconnectedRowSet rs = new XDisconnectedRowSet();

      boolean okay = true;
      String theError = "";
      
      myTable.setConn(conn);

      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               quote_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (quote_id > 0) {
               // get record information
               myTable.setSSelectWhat(" * ");
               myTable.setSCustomWhere("and quote_id = " + quote_id);
               rs = myTable.getAllRecordsDisconnected();

               if (rs.next()) {
                  // put the title on the screen
                  quote_name = rs.getString("quote_name");
                  MasterTemplate.searchReplace("$NAME$", quote_name);
                  
                  // put the url on the screen
                  quote_text = rs.getString("quote_text");
                  MasterTemplate.searchReplace("$QUOTE$", quote_text);

                  // put published status on screen
                  quote_active_yn = rs.getString("quote_active_yn");
                  if (quote_active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }
                  
                  String cancelPage = (String) request.getParameter("cancelPage");
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$NAME$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "");
               MasterTemplate.searchReplace("$QUOTE$", "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));

            }

            // put id in hidden field
            MasterTemplate.searchReplace("$ID$", quote_id + "");

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditQuote:ProcessForm:Exception:"
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
            quote_id = Integer
                  .parseInt((String) request.getParameter("id"));

            if (((String) request.getParameter("quote_name") != null)
                  && (((String) request.getParameter("quote_name")).length() > 0)) {
               quote_name = (String) request.getParameter("quote_name");
            } else {
               theError = "You must supply a question!";
               okay = false;
            }
            quote_text = (String) request
                  .getParameter("quote_text");

            quote_active_yn = (String) request.getParameter("quote_active_yn");

            if (okay) {
               myTable.addUpdateFieldNameValuePair("quote_text", quote_text, "String");
               myTable.addUpdateFieldNameValuePair("quote_name", quote_name, "String");
               myTable.addUpdateFieldNameValuePair("quote_active_yn", quote_active_yn, "String");;
               
               if (quote_id > 0) {
                  myTable.setSCustomWhere(" and quote_id = " + quote_id);
                  myTable.setUpdateWhat("quotes");
                  myTable.updateRecord();
               } else {
                  myTable.setSTable("quotes");
                  myTable.insertRecord();
               }
            }

            if (okay) {
               session.setAttribute("Error", "Update successful");
               response
                     .sendRedirect((String) request.getParameter("cancelPage"));
            } else {
               session.setAttribute("Error", theError);
               response
                     .sendRedirect((String) request.getParameter("cancelPage"));
            }
         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditQuote:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}