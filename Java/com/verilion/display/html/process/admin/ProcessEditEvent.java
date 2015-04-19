//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-29
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditEvent.java,v $
//Revision 1.1.2.2  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2007/03/30 17:21:11  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DBUtils;
import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Edit/create an event from admin tool
 * 
 * 
 * @author tsawler
 *  
 */
public class ProcessEditEvent extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int event_id = 0;
      String event_name = "";
      String description = "";
      String start_date = "";
      String end_date = "";
      String event_active_yn = "";
      GenericTable myGt = new GenericTable();
      String tmpString = "";
      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      boolean okay = true;
      String theError = "";

      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               event_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (event_id > 0) {
               // get news item information
               myGt.setConn(conn);
               myGt.setSTable("events");
               myGt.setSSelectWhat("*");
               myGt.setSCustomWhere("and event_id = " + event_id);
               drs = myGt.getAllRecordsDisconnected();

               if (drs.next()) {
                   event_name = drs.getString("event_name");
                   description = drs.getString("description");
                   event_active_yn = drs.getString("event_active_yn");
                   tmpString = drs.getString("start_date_time");
                   start_date = tmpString.substring(0, 10);
                   tmpString = drs.getString("end_date_time");
                   end_date = tmpString.substring(0,10);
                   

                  MasterTemplate.searchReplace("$EVENTNAME$", event_name);
                  MasterTemplate.searchReplace("$STARTDATE$", start_date);
                  MasterTemplate.searchReplace("$ENDDATE$", end_date);
                  
                  // put published status on screen
                  
                  if (event_active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }

                  // put description on screen
                  
                  description = description.replaceAll("\"", "\\\"");
                  description = description.replaceAll("\'", "&#8217;");
                  description = description.replaceAll("\n", " ");
                  description = description.replaceAll("\r", " ");
                  MasterTemplate.searchReplace("$STORY$", description);
                  
                  String cancelPage = (String) request.getParameter("cancelPage");
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
                  MasterTemplate.searchReplace("$ID$", event_id + "");
               }
            } else {
               // we have no event, so put blanks everywhere
               MasterTemplate.searchReplace("$EVENTNAME$", "");
               MasterTemplate.searchReplace("$STARTDATE$", "");
               MasterTemplate.searchReplace("$ENDDATE$", "");
               MasterTemplate.searchReplace("$STORY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$ID$", "0");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));
            }

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditEvent:ProcessForm:Exception:"
                        + e.toString());
         }
      } else {
         try {
            // get parameters
            event_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("event_name") != null)
                  && (((String) request.getParameter("event_name")).length() > 0)) {
               event_name = (String) request.getParameter("event_name");
            } else {
               theError = "You must supply a name!";
               okay = false;
            }
            description = (String) request
                  .getParameter("story");
            // clean up the messy codes put in by the html editor, if any
            DBUtils myDbUtil = new DBUtils();
            description = myDbUtil.replace(description, "src=\"..", "src=\"");
            description = myDbUtil.replace(description, "&#39;", "&#8217;");
            int charVal = 146; // the sgml character used for single quotes
            char theChar = (char) charVal;
            String sQuote = String.valueOf(theChar);
            description = myDbUtil.replace(description, sQuote, "&#8217;");
            description = description.replaceAll("\"", "\\\"");
            description = description.replaceAll("\'", "&#8217;");
            description = description.replaceAll("\n", " ");
            description = description.replaceAll("\r", " ");

            event_active_yn = (String) request.getParameter("active_yn");
            start_date = request.getParameter("start_date");
            end_date = request.getParameter("end_date");

            if (okay) {

               myGt.setConn(conn);
               myGt.setUpdateWhat("events");
               myGt.addUpdateFieldNameValuePair("event_name", event_name, "string");
               myGt.addUpdateFieldNameValuePair("description", description, "string");
               myGt.addUpdateFieldNameValuePair("event_active_yn", event_active_yn, "string");
               myGt.addUpdateFieldNameValuePair("start_date_time", start_date + " 00:00:00", "timestamp");
               if (end_date != null){
                  if (end_date.length() > 0) {
                     myGt.addUpdateFieldNameValuePair("end_date_time", end_date + " 23:59:59", "timestamp");
                  } else {
                     myGt.addUpdateFieldNameValuePair("end_date_time", start_date + " 23:59:59", "timestamp");
                  }
               } else {
                  myGt.addUpdateFieldNameValuePair("start_date_time", end_date + " 23:59:59", "timestamp");
               }
               
               
               if (event_id > 0) {
                  myGt.setSCustomWhere("and event_id = " + event_id);
                  myGt.updateRecord();
               } else {
                  myGt.setSTable("events");
                  myGt.insertRecord();
                  // get last insert id
                  myGt.setSSequence("events_event_id_seq");
                  event_id = myGt.returnCurrentSequenceValue();
               }
               
               if (end_date != null) {
            	   if (!(end_date.equals(start_date))){
            		   // this is a recurring event. Put entries into events_recurring
            		   GenericTable gT = new GenericTable();
            		   gT.setConn(conn);
            		   
            		   // loop from start date to end date
            		   // first figure out ymd
            		   StringTokenizer stoken = new StringTokenizer(start_date, "-");
            		   int y = Integer.parseInt((String)stoken.nextToken());
            		   int m = Integer.parseInt((String)stoken.nextToken());
            		   int d = Integer.parseInt((String)stoken.nextToken());
            
            		   GregorianCalendar myStart = new GregorianCalendar(y,(m - 1),d);
            		   
            		   stoken = new StringTokenizer(end_date, "-");
            		   y = Integer.parseInt((String)stoken.nextToken());
            		   m = Integer.parseInt((String)stoken.nextToken());
            		   d = Integer.parseInt((String)stoken.nextToken());
            		   
            		   GregorianCalendar myEnd = new GregorianCalendar(y,(m -1),d);
            		   
            		   GregorianCalendar testDate = myStart;
            		   testDate.add(GregorianCalendar.DATE, 1);
            		   
            		   // first delete all entries in eventsRecurring
            		   GenericTable eGt = new GenericTable("events_recurring");
            		   eGt.setConn(conn);
            		   eGt.setSCustomWhere(" and event_id = " + event_id);
            		   eGt.deleteRecords();
            		   
            		   while (!(testDate.after(myEnd))) {
            			   // for each day, enter appropriate info in events_recurring
            			   // set up date format
            			   String theDate = "";
            			   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            			   theDate = df.format(testDate.getTime());
            			   eGt.setConn(conn);
            			   eGt.addSet("event_id", event_id + "", "int");
            			   eGt.addSet("start_date_time", theDate + " 00:00:00", "timestamp");
            			   eGt.addSet("end_date_time", theDate + " 23:59:59", "timestamp");
            			   eGt.insertRecord();
            			   // add one day to testDate
            			   testDate.add(GregorianCalendar.DATE, 1);
            			   eGt.clearUpdateVectors();
            		   }
            	   }
               }
               
            }

            if (okay) {
               session.setAttribute("Error", "Page update successful");
               response
                     .sendRedirect((String) request.getParameter("cancelPage"));
            } else {
               session.setAttribute("Error", theError);
               response
                     .sendRedirect((String) request.getParameter("cancelPage"));
            }
         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditEvent:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}