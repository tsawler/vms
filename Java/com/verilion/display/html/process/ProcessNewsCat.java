//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessNewsCat.java,v $
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
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.JspTemplate;
import com.verilion.database.NewsCategories;
import com.verilion.database.NewsItem;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;

/**
 * Put news archive on screen
 * 
 * <P>
 * October 18, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class ProcessNewsCat extends ProcessPage {

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int theCategory = 0;
      NewsCategories myCategories = new NewsCategories();
      myCategories.setConn(conn);
      NewsItem myNewsItem = new NewsItem();
      myNewsItem.setConn(conn);
      ResultSet rs = null;

      try {
         // get our news category id from hashmap
         theCategory = Integer.parseInt((String) hm.get("id"));

         myCategories.setNews_categories_id(theCategory);
         myNewsItem.setNews_categories_id(theCategory);

         MasterTemplate.searchReplace("$CATTITLE$", myCategories
               .getNameForCategoryId());

         // get our news item from the database
         rs = myNewsItem.getAllActiveNewsItemsForCategory();

         // find out what template to use for the news page
         String pageName = (String) hm.get("p");
         JspTemplate myJspTemplate = new JspTemplate();
         myJspTemplate.setConn(conn);
         String theTemplate = "";
         theTemplate = myJspTemplate.returnJspTemplateIdForPageName(pageName);

         String sOut = "<ul class=\"newslist\">";
         while (rs.next()) {
            sOut += "<li><a href=\"/"
                  + theTemplate
                  + "/jpage/1/p/News/a/newsitem/id/"
                  + rs.getString("news_id")
                  + "/content.do\">"
                  + rs.getString("news_title")
                  + "</a>&nbsp;<em>("
                  + rs.getDate("news_start_date").toString()
                  + ")</em></li>";
         }

         sOut += "</ul>";

         rs.close();
         rs = null;
         MasterTemplate.searchReplace("<!--newslist-->", sOut);
      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.ProcessNewsCat:BuildPage:Exception:"
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