//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-13
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessPollResults.java,v $
//Revision 1.1.2.3  2007/03/19 00:46:56  tcs
//Cosmetic changes
//
//Revision 1.1.2.2  2007/03/15 17:48:18  tcs
//Cosmetic changes
//
//Revision 1.1.2.1  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;

/**
 * Show past poll results
 * 
 * @author tsawler
 * 
 */
public class ProcessPollResults extends ProcessPage {

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      GenericTable myPollResults = new GenericTable();
      myPollResults.setConn(conn);
      XDisconnectedRowSet drs = new XDisconnectedRowSet();

      try {
         myPollResults.setSTable("polls");
         myPollResults.setSSelectWhat("*");
         drs = myPollResults.getAllRecordsDisconnected();
         MasterTemplate.appendString("<h2>Past Polls</h2><table style=\"tabledata\">");
         while (drs.next()){
            MasterTemplate.appendString("<tr>\n<td>\n");
            MasterTemplate.appendString("<a href=\"#\" onclick=\"showresults("
                  + drs.getInt("poll_id")
                  + ")\">"
                  + drs.getString("title")
                  + "</a>\n</td>\n</tr>\n");
         }
         MasterTemplate.appendString("</table>");
        if (hm.get("pid") != null) {
           int pid = Integer.parseInt((String) hm.get("pid"));
           MasterTemplate.appendString("<script type=\"text/javascript\">\nshowresults("
                 + pid
                 + ");\n</script>\n");
        }
      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessPollResults:BuildPage:Exception:"
                     + e.toString());
      }
      return MasterTemplate;
   }
}