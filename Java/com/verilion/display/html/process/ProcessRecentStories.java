//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-12
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessRecentStories.java,v $
//Revision 1.1.2.2  2007/06/11 15:36:52  tcs
//Minor formatting
//
//Revision 1.1.2.1  2007/03/23 11:02:50  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;

/**
 * List stories posted in the last seven days
 * 
 * @author tsawler
 * 
 */
public class ProcessRecentStories extends ProcessPage {

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {


      GregorianCalendar theDate = new GregorianCalendar();
      ResultSet rs = null;
      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      String theHtml = "";
      int customer_access_level = 0;

      try {
         theDate.add(GregorianCalendar.DATE, -7);
         Date d = theDate.getTime();
         PreparedStatement pst = null;
         String query = "select * from pmc_stories where story_active_yn = 'y' and datetime > ? order by datetime desc";
         pst = conn.prepareStatement(query);
         pst.setDate(1, new java.sql.Date(d.getTime()));
         rs = pst.executeQuery();
         drs.populate(rs);
         rs.close();
         rs = null;
         pst.close();
         pst = null;
         
         try {
            customer_access_level = Integer.parseInt((String) session
                  .getAttribute("customer_access_level"));
         } catch (Exception e) {
            customer_access_level = 0;
         }
         
         // showing most recent stories
         theHtml = "<h1>Messages From Last 7 Days</h1><br />\n<div id=\"storytable\">\n";
         while (drs.next()) {
            String theStory = drs.getString("story");

            if (theStory.length() > 750) {
               theStory = theStory.substring(0, 749)
                     + "...<br />\n<div style=\"text-align: right\">\n"
                     + "<a href=\"/public/jpage/1/p/story/a/storypage/sid/"
                     + drs.getInt("story_id")
                     + "/content.do\">click to read more...</a>\n</div>\n";
            }
            theHtml += "<div class=\"storyheader\">\n"
                  + "<a class=\"storyheader\" style=\"font-weight: bold;\" href=\"/public/jpage/1/p/story/a/storypage/sid/"
                  + drs.getInt("story_id")
                  + "/content.do\" "
                  + "title=\"Click to read message and comments\">"
                  + drs.getString("title")
                  + "</a>\n"
                  + "</div>\n"
                  + "<div class=\"subheading\">\n"
                  + "Posted by ";
            if (customer_access_level > 1) {
               theHtml += "<a href=\"/members/jpage/1/p/MemberDirectory/content.do?uname="
                     + drs.getString("posted_by")
                     + "\">";
            }
            theHtml += drs.getString("posted_by");
            if (customer_access_level > 1) {
               theHtml += "</a>";
            }
            theHtml += " on "
                  + drs.getTimestamp("datetime").toString().substring(0, 16)
                  + ". "
                  + drs.getInt("number_comments")
                  + " comments."
                  + "&nbsp;"
                  + drs.getInt("counter")
                  + " reads"
                  + "\n</div>\n"
                  + "<div>&nbsp;</div><div>\n"
                  + theStory
                  + "<br /><br />\n"
                  + "</div>\n";
         }
         drs.close();
         drs = null;

         theHtml += "</div>\n";
         MasterTemplate.appendString(theHtml);

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessStoryListPage:BuildPage:Exception:"
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