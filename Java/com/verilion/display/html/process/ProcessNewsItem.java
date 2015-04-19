//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessNewsItem.java,v $
//Revision 1.4.14.1  2007/02/08 19:28:34  tcs
//Check for invalid news id
//
//Revision 1.4  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.3  2004/10/26 15:41:34  tcs
//Improved javadocs
//
//Revision 1.2  2004/10/25 17:33:08  tcs
//Changes due to PDF file format display
//
//Revision 1.1  2004/10/18 18:00:28  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.NewsItem;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;

/**
 * Put news item on screen
 * 
 * <P>
 * October 18, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class ProcessNewsItem extends ProcessPage {

   @SuppressWarnings("unchecked")
   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int theItem = 0;
      NewsItem myNewsItem = new NewsItem();
      myNewsItem.setConn(conn);
      ResultSet rs = null;

      try {
         // get our parameter for the news item
         try {
            theItem = Integer.parseInt((String) hm.get("id"));
         } catch (Exception e) {
            theItem = 0;
         }
         if (theItem > 0) {
            myNewsItem.setNews_id(theItem);

            // get our news item from the database
            rs = myNewsItem.getNewsItemById();

            if (rs.next()) {
               Locale loc = request.getLocale();
               DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, loc);
               String myDate = df.format(rs.getDate("news_start_date"));
               MasterTemplate.searchReplace("$NEWSTITLE$", rs
                     .getString("news_title"));
               MasterTemplate.searchReplace("$NEWSDATE$", myDate);
               MasterTemplate.searchReplace("$TEASER$", rs
                     .getString("news_teaser_text"));
               MasterTemplate.searchReplace("$BODY$", rs
                     .getString("news_body_text"));
            }
            rs.close();
            rs = null;
         } else {
            session.setAttribute("Error", "Invalid news item selected");
            hm.put("PRESERVEMSG", "TRUE");
            response.sendRedirect("/");
         }

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessNewsItem:BuildPage:Exception:"
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