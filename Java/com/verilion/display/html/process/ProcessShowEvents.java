//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessShowEvents.java,v $
//Revision 1.1.2.2  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2007/03/30 17:21:52  tcs
//Initial entry
//
//Revision 1.4.6.1.6.2.2.1  2007/02/09 00:37:41  tcs
//removed hardcoded template name
//
//Revision 1.4.6.1.6.2  2006/12/19 19:27:51  tcs
//Changed url generation
//
//Revision 1.4.6.1.6.1  2006/06/11 22:39:16  tcs
//Code cleanup
//
//Revision 1.4.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.3  2004/10/26 15:41:33  tcs
//Improved javadocs
//
//Revision 1.2  2004/10/20 16:16:49  tcs
//Formatting
//
//Revision 1.1  2004/10/18 18:00:17  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;

/**
 * Put events for a given day on screen
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class ProcessShowEvents extends ProcessPage {

   @Override
   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      GenericTable myGt = new GenericTable();
      myGt.setConn(conn);
      myGt.setSTable("events");
      myGt.setSSelectWhat("*");
      
      int fyear = 0;
      int fmonth = 0;
      int fday = 0;
      
      try {
         // get our parameters
         if (request.getParameter("f") != null) {
            StringTokenizer st = null;
            st = new StringTokenizer(request.getParameter("f"), "-");
            fyear = Integer.parseInt(st.nextToken());
            fmonth = (Integer.parseInt(st.nextToken())) - 1;
            fday = Integer.parseInt(st.nextToken());
            
         }
         myGt.setSCustomWhere("and start_date_time <= '"
               + fyear
               + "-"
               + (fmonth + 1)
               + "-"
               + fday
               + " 00:00:00'"
               + " and end_date_time >= '"
               + fyear
               + "-"
               + (fmonth + 1)
               + "-"
               + fday
               + " 23:59:59'"
               + " and event_active_yn = 'y'");
         myGt.setSCustomOrder("order by start_date_time");
         drs = myGt.getAllRecordsDisconnected();
         String sOut = "<div><strong>Events for "
            + request.getParameter("f")
            + "</strong><br /><br />";
         while (drs.next()) {
            sOut += "<strong>"
               + drs.getString("event_name")
               + "</strong>"
               + "<br />"
               + drs.getString("description")
               + "<br /><br />";
         }

         sOut += "</div>";
         System.out.println("event; " + sOut);
         MasterTemplate.replaceFullValue(sOut);
      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessShowEvents:BuildPage:Exception:"
                     + e.toString());
      }
      return MasterTemplate;
   }

   @Override
   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {
      this.ProcessForm(request, response, session, conn, MasterTemplate, hm);
      return super.BuildPage(
            request,
            response,
            session,
            conn,
            MasterTemplate,
            hm);
   }
}