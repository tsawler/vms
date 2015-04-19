//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-08-14
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessSearchSite.java,v $
//Revision 1.1.2.1  2007/08/21 11:12:27  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.display.HTMLTemplateDb;

/**
 * Search Site
 * 
 * February 25 2005
 * 
 * @author tsawler
 * 
 */
public class ProcessSearchSite extends ProcessPage {

   public String searchQuery = "select p.page_invocation, pd.page_detail_title, "
         + "pd.page_detail_contents from page p "
         + "left join page_detail pd on (p.page_id = pd.page_id) "
         + "where p.page_active_yn = 'y' "
         + "and (pd.page_detail_title ilike ? or pd.page_detail_contents ilike ?)";

   /**
    * Do site search
    * 
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      String searchterms = "";
      PreparedStatement pst = null;
      ResultSet rs = null;
      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      String searchResults = "<h3>Search Results</h3>";

      String testString = request.getParameter("st");
      if (testString.length() > 0) {
         searchterms = (String) request.getParameter("st");
         pst = conn.prepareStatement(searchQuery);
         pst.setString(1, "%" + searchterms + "%");
         pst.setString(2, "%" + searchterms + "%");
         rs = pst.executeQuery();
         drs.populate(rs);
         rs.close();
         rs = null;
         pst.close();
         pst = null;
         searchResults+= "<strong>Searching for: \""
            + searchterms
            + "\"</strong><br />";
      }

      if (drs.next()) {
         drs.previous();
         while (drs.next()) {
            String theStory = drs.getString("page_detail_contents");
            if (theStory.length() > 750) {
               theStory = theStory.substring(0, 749) + "...";
            }

            //theStory = theStory.replaceAll("<br />", "186282ytrrdsdf");
            //theStory = theStory.replaceAll("<br>", "186282ytrrdsdf");
            theStory = com.verilion.utility.TextUtils.StripHtml(theStory);
            theStory = theStory.replaceAll("<", "");

            //theStory = theStory.replaceAll("186282ytrrdsdf", "<br />");
            theStory = theStory.replaceAll(
                  searchterms,
                  "<span style=\"background: yellow;\">"
                        + searchterms
                        + "</span>");

            searchResults += "<p class=\"storyheader\">"
                  + "<em>Found in</em> "
                  + "<a class=\"storyheader\" style=\"font-weight: bold;\" href=\""
                  + drs.getString("page_invocation")
                  + "\">"
                  + drs.getString("page_detail_title")
                  + "</a></p><p>"
                  + theStory
                  + "</p><p>&nbsp;</p>";

         }
      } else {
         searchResults = "No results found.";
      }

      drs.close();
      drs = null;
      
      MasterTemplate.appendString(searchResults);

      return MasterTemplate;
   }

   public String getSearchQuery() {
      return searchQuery;
   }

   public void setSearchQuery(String searchQuery) {
      this.searchQuery = searchQuery;
   }

}