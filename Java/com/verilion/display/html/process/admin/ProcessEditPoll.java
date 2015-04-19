//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-13
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditPoll.java,v $
//Revision 1.1.2.1.2.1  2008/03/07 12:03:39  tcs
//FIxed typo
//
//Revision 1.1.2.1  2007/03/14 00:19:42  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.sql.Statement;
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
 * Edit/create a poll
 * 
 * @author tsawler
 *  
 */
public class ProcessEditPoll extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int poll_id = 0;
      String title = "";
      String active_yn = "n";
      String r1 = "";
      String r2 = "";
      String r3 = "";
      String r4 = "";
      String r5 = "";
      int r1id = 0;
      int r2id = 0;
      int r3id = 0;
      int r4id = 0;
      int r5id = 0;
      
      GenericTable myPoll = new GenericTable("polls");
      GenericTable myResponses = new GenericTable("polls_data");
      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      
      boolean okay = true;
      String theError = "";
      
      myPoll.setConn(conn);
      myResponses.setConn(conn);
      
      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               poll_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (poll_id > 0) {
               // get record information
               myPoll.setSSelectWhat(" * ");
               myPoll.setSCustomWhere("and poll_id = " + poll_id);
               rs = myPoll.getAllRecordsDisconnected();

               if (rs.next()) {

                  // put the title on the screen
                  title = rs.getString("title");
                  MasterTemplate.searchReplace("$TITLE$", title);

                  // put published status on screen
                  active_yn = rs.getString("active_yn");
                  if (active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }

                  myResponses.setSSelectWhat("*");
                  myResponses.setSCustomWhere(" and poll_id = " + poll_id);
                  myResponses.setSCustomOrder("order by polls_data_id");
                  crs = myResponses.getAllRecordsDisconnected();
                  
                  int i = 0;
                  while (crs.next()){
                     i++;
                     String r = crs.getString("poll_option_text");
                     int tmpId = crs.getInt("polls_data_id");
                     MasterTemplate.searchReplace("$R" + i + "$", r);
                     MasterTemplate.searchReplace("$R" + i + "ID$", tmpId + "");
                  }
                  for (int j=i; j<=5; j++) {
                     MasterTemplate.searchReplace("$R" + j + "$", "");
                     MasterTemplate.searchReplace("$R" + j + "ID$", "0");
                  }
                  
                  String cancelPage = (String) request.getParameter("cancelPage");
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               }
            } else {
               // we have no item, so put blanks everywhere
               MasterTemplate.searchReplace("$TITLE$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));
               for (int i=1; i<=5;i++){
                  MasterTemplate.searchReplace("$R" + i + "$", "");
                  MasterTemplate.searchReplace("$R" + i + "ID$", "0");
               }
            }

            // put id in hidden field
            MasterTemplate.searchReplace("$ID$", poll_id + "");
            crs.close();
            crs = null;

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditPoll:ProcessForm:Exception:"
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
         }
      } else {
         try {
            // get parameters
            poll_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("title") != null)
                  && (((String) request.getParameter("title")).length() > 0)) {
               title = (String) request.getParameter("title");
            } else {
               theError = "You must supply a question!";
               okay = false;
            }

            active_yn = (String) request.getParameter("active_yn");
            
            if (((String) request.getParameter("r1") != null)
                  && (((String) request.getParameter("r1")).length() > 0)) {
               r1 = (String) request.getParameter("r1");
               r1id = Integer.parseInt((String) request.getParameter("r1id"));
            }
            if (((String) request.getParameter("r2") != null)
                  && (((String) request.getParameter("r2")).length() > 0)) {
               r2 = (String) request.getParameter("r2");
               r2id = Integer.parseInt((String) request.getParameter("r2id"));
            }
            if (((String) request.getParameter("r3") != null)
                  && (((String) request.getParameter("r3")).length() > 0)) {
               r3 = (String) request.getParameter("r3");
               r3id = Integer.parseInt((String) request.getParameter("r3id"));
            }
            if (((String) request.getParameter("r4") != null)
                  && (((String) request.getParameter("r4")).length() > 0)) {
               r4 = (String) request.getParameter("r4");
               r4id = Integer.parseInt((String) request.getParameter("r4id"));
            }
            if (((String) request.getParameter("r5") != null)
                  && (((String) request.getParameter("r5")).length() > 0)) {
               r5 = (String) request.getParameter("r5");
               r5id = Integer.parseInt((String) request.getParameter("r5id"));
            }


            if (okay) {

               myPoll.addUpdateFieldNameValuePair("title", title, "String");
               myPoll.addUpdateFieldNameValuePair("active_yn", active_yn, "String");

               if (poll_id > 0) {
                  myPoll.setSCustomWhere(" and poll_id = " + poll_id);
                  myPoll.setUpdateWhat("polls");
                  myPoll.updateRecord();
               } else {
                  myPoll.setSTable("polls");
                  myPoll.insertRecord();
                  myPoll.setSSequence("polls_poll_id_seq");
                  poll_id = myPoll.returnCurrentSequenceValue();
               }
               
               // now do responses
               if (r1.length() > 0) {
                  myResponses.addUpdateFieldNameValuePair("poll_option_text", r1, "String");
                  if (r1id > 0) {
                     myResponses.setUpdateWhat("polls_data");
                     myResponses.setSCustomWhere("and polls_data_id = " + r1id);
                     myResponses.updateRecord();
                     myResponses.clearUpdateVectors();
                  } else {
                     myResponses.addUpdateFieldNameValuePair("poll_id", poll_id + "", "int");
                     myResponses.setSTable("polls_data");
                     myResponses.insertRecord();
                     myResponses.clearUpdateVectors();
                  }
               }
               if (r2.length() > 0) {
                  myResponses.addUpdateFieldNameValuePair("poll_option_text", r2, "String");
                  if (r2id > 0) {
                     myResponses.setUpdateWhat("polls_data");
                     myResponses.setSCustomWhere("and polls_data_id = " + r2id);
                     myResponses.updateRecord();
                     myResponses.clearUpdateVectors();
                  } else {
                     myResponses.addUpdateFieldNameValuePair("poll_id", poll_id + "", "int");
                     myResponses.setSTable("polls_data");
                     myResponses.insertRecord();
                     myResponses.clearUpdateVectors();
                  }
               }
               if (r3.length() > 0) {
                  myResponses.addUpdateFieldNameValuePair("poll_option_text", r3, "String");
                  if (r3id > 0) {
                     myResponses.setUpdateWhat("polls_data");
                     myResponses.setSCustomWhere("and polls_data_id = " + r3id);
                     myResponses.updateRecord();
                     myResponses.clearUpdateVectors();
                  } else {
                     myResponses.addUpdateFieldNameValuePair("poll_id", poll_id + "", "int");
                     myResponses.setSTable("polls_data");
                     myResponses.insertRecord();
                     myResponses.clearUpdateVectors();
                  }
               }
               if (r4.length() > 0) {
                  myResponses.addUpdateFieldNameValuePair("poll_option_text", r4, "String");
                  if (r4id > 0) {
                     myResponses.setUpdateWhat("polls_data");
                     myResponses.setSCustomWhere("and polls_data_id = " + r4id);
                     myResponses.updateRecord();
                     myResponses.clearUpdateVectors();
                  } else {
                     myResponses.addUpdateFieldNameValuePair("poll_id", poll_id + "", "int");
                     myResponses.setSTable("polls_data");
                     myResponses.insertRecord();
                     myResponses.clearUpdateVectors();
                  }
               }
               if (r5.length() > 0) {
                  myResponses.addUpdateFieldNameValuePair("poll_option_text", r5, "String");
                  if (r5id > 0) {
                     myResponses.setUpdateWhat("polls_data");
                     myResponses.setSCustomWhere("and polls_data_id = " + r5id);
                     myResponses.updateRecord();
                     myResponses.clearUpdateVectors();
                  } else {
                     myResponses.addUpdateFieldNameValuePair("poll_id", poll_id + "", "int");
                     myResponses.setSTable("polls_data");
                     myResponses.insertRecord();
                     myResponses.clearUpdateVectors();
                  }
               }
               // only one poll can be active
               if (active_yn.equalsIgnoreCase("y")) {
                  String q = "update polls set active_yn = 'n' where poll_id <> " + poll_id;
                  Statement st = null;
                  st = conn.createStatement();
                  st.executeUpdate(q);
                  st.close();
                  st = null;
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
            Errors.addError("ProcessEditPoll:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}