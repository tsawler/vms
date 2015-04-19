//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-01-12
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditNewsItem.java,v $
//Revision 1.1.2.4.4.4.6.1.2.1.2.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.4.4.4.6.1.2.1  2007/01/14 23:38:25  tcs
//Fixed typo
//
//Revision 1.1.2.4.4.4.6.1  2007/01/12 19:28:26  tcs
//Clean up html prior to insertion
//
//Revision 1.1.2.4.4.4  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.4.4.3  2005/08/19 15:24:21  tcs
//Modified to return to correct page on search screen
//
//Revision 1.1.2.4.4.2  2005/08/16 00:57:56  tcs
//Made date handling much simpler.
//
//Revision 1.1.2.4.4.1  2005/08/10 01:36:27  tcs
//Auto select current user as author on new item
//
//Revision 1.1.2.4  2005/04/21 16:41:52  tcs
//modified to remove returns from text.
//
//Revision 1.1.2.3  2005/01/12 16:33:02  tcs
//Corrected error stamp
//
//Revision 1.1.2.2  2005/01/12 16:28:38  tcs
//Check for non valid sgml character entities
//
//Revision 1.1.2.1  2005/01/12 14:07:28  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Customer;
import com.verilion.database.DBUtils;
import com.verilion.database.NewsCategories;
import com.verilion.database.NewsItem;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit/create a news item
 * 
 * <P>
 * January 12, 2005
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessEditNewsItem extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int news_id = 0;
      String news_title = "";
      String news_teaser_text = "";
      String news_start_date = "";
      String news_end_date = "";
      String news_active_yn = "";
      int news_author_id = 0;
      int news_categories_id = 0;
      String browser_title = "";
      String meta_tags = "";
      String theMenu = "";
      NewsItem myNewsItem = new NewsItem();
      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      XDisconnectedRowSet catrs = new XDisconnectedRowSet();
      boolean okay = true;
      String theError = "";

      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               news_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (news_id > 0) {
               // get news item information
               myNewsItem.setConn(conn);
               myNewsItem.setNews_id(news_id);
               rs = myNewsItem.getNewsItemById();

               if (rs.next()) {
            	   
            	   MasterTemplate.searchReplace("$BROWSERTITLE$", rs.getString("browser_title"));
            	   MasterTemplate.searchReplace("$METATAGS$", rs.getString("meta_tags"));

                  // build ddlb for authors
                  Customer myCustomer = new Customer();
                  myCustomer.setConn(conn);
                  crs = myCustomer.GetAllCustomersAboveAccessLevel2();

                  theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "news_author_id",
                        rs.getInt("news_author_id"),
                        crs,
                        "customer_id",
                        "customer_name",
                        0,
                        "");

                  // put the formatted drop down list box in HTML object
                  MasterTemplate.searchReplace("$DDLBAUTHOR$", theMenu);

                  // build ddlb for categories
                  NewsCategories myNewsCategories = new NewsCategories();
                  myNewsCategories.setConn(conn);
                  catrs = myNewsCategories.getAllActiveNewsCategories();

                  theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "news_categories_id",
                        rs.getInt("news_categories_id"),
                        catrs,
                        "news_categories_id",
                        "news_categories_name",
                        0,
                        "");

                  // put the formatted drop down list box on page
                  MasterTemplate.searchReplace("$DDLBCAT$", theMenu);

                  // put the title on the screen
                  news_title = rs.getString("news_title");
                  MasterTemplate.searchReplace("$NEWSTITLE$", news_title);

                  // put start date on screen
                  news_start_date = rs.getString("news_start_date");
//                  StringTokenizer st = new StringTokenizer(news_start_date, "-");
//                  y = st.nextToken();
//                  m = st.nextToken();
//                  d = st.nextToken();
//                  news_start_date = d + "-" + m + "-" + y;
                  MasterTemplate.searchReplace("$STARTDATE$", news_start_date);

                  // put end date on screen
                  news_end_date = rs.getString("news_end_date");

                  if (news_end_date.equals("9999-12-31")) {
                     MasterTemplate.searchReplace("$ENDDATE$", "");
                  } else {
//                     st = new StringTokenizer(news_end_date, "-");
//                     y = st.nextToken();
//                     m = st.nextToken();
//                     d = st.nextToken();
//                     news_end_date = d + "-" + m + "-" + y;
                     MasterTemplate.searchReplace("$ENDDATE$", news_end_date);
                  }

                  // put published status on screen
                  news_active_yn = rs.getString("news_active_yn");
                  if (news_active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }

                  // put description on screen
                  news_teaser_text = rs.getString("news_teaser_text");
                  news_teaser_text = news_teaser_text.replaceAll("\"", "\\\"");
                  news_teaser_text = news_teaser_text.replaceAll("\'", "&#8217;");
                  news_teaser_text = news_teaser_text.replaceAll("\n", " ");
                  news_teaser_text = news_teaser_text.replaceAll("\r", " ");
                  MasterTemplate.searchReplace("$INTRO$", news_teaser_text);
                  String cancelPage = (String) request.getParameter("cancelPage");
                  if (session.getAttribute("d-5394226-p") != null) {
                     cancelPage += "?d-5394226-p=" + session.getAttribute("d-5394226-p");
                  }
                  
                  // see if it belongs on the news scroller
                  String query = "select * from news_ticker where news_id = " + news_id;
                  Statement st = null;
                  st = conn.createStatement();
                  ResultSet nrs = null;
                  nrs = st.executeQuery(query);
                  if (nrs.next()) {
                	  MasterTemplate.searchReplace("$FNSELECTYNY$", "selected");
                	  MasterTemplate.searchReplace("$FNSELECTYNN$", "");
                  } else {
                	  MasterTemplate.searchReplace("$FNSELECTYNN$", "selected");
                	  MasterTemplate.searchReplace("$FNSELECTYNY$", "");
                  }
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$NEWSTITLE$", "");
               MasterTemplate.searchReplace("$STARTDATE$", "");
               MasterTemplate.searchReplace("$ENDDATE$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "no");
               MasterTemplate.searchReplace("$INTRO$", "");
               MasterTemplate.searchReplace("$MAINTEXT$", "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));
               MasterTemplate.searchReplace("$FNSELECTYNN$", "");
               MasterTemplate.searchReplace("$FNSELECTYNY$", "");
               MasterTemplate.searchReplace("$BROWSERTITLE$", "");
        	   MasterTemplate.searchReplace("$METATAGS$", "");

               // build ddlb for authors
               Customer myCustomer = new Customer();
               myCustomer.setConn(conn);
               crs = myCustomer.GetAllCustomersAboveAccessLevel2();
               int myId = 0;
               try {
                  myId = Integer.parseInt((String)session.getAttribute("userAdminId"));
               } catch (Exception e) {
                  myId = 0;
               }
               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "news_author_id",
                     myId,
                     crs,
                     "customer_id",
                     "customer_name",
                     0,
                     "");

               // put the formatted drop down list box in HTML object
               MasterTemplate.searchReplace("$DDLBAUTHOR$", theMenu);

               // build ddlb for categories
               NewsCategories myNewsCategories = new NewsCategories();
               myNewsCategories.setConn(conn);
               catrs = myNewsCategories.getAllActiveNewsCategories();

               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "news_categories_id",
                     0,
                     catrs,
                     "news_categories_id",
                     "news_categories_name",
                     0,
                     "");

               // put the formatted drop down list box on page
               MasterTemplate.searchReplace("$DDLBCAT$", theMenu);

            }

            // put sire id in hidden field (for image upload)
            MasterTemplate.searchReplace("$NEWSID$", news_id + "");

            crs.close();
            crs = null;
            catrs.close();
            catrs = null;

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditNewsItem:ProcessForm:Exception:"
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
            if (catrs != null) {
               catrs.close();
               catrs = null;
            }
         }
      } else {
         try {
            // get parameters
            news_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("news_title") != null)
                  && (((String) request.getParameter("news_title")).length() > 0)) {
               news_title = (String) request.getParameter("news_title");
            } else {
               theError = "You must supply a title!";
               okay = false;
            }
            try {
            	browser_title = (String) request.getParameter("browser_title");
            } catch (Exception e) {
            	browser_title = "";
            }
            try {
            	meta_tags = (String) request.getParameter("meta_tags");
            } catch (Exception e) {
            	meta_tags = "";
            }
            
            news_teaser_text = (String) request
                  .getParameter("news_teaser_text");
            // clean up the messy codes put in by the html editor, if any
            DBUtils myDbUtil = new DBUtils();
            news_teaser_text = myDbUtil.replace(news_teaser_text, "src=\"..", "src=\"");
            news_teaser_text = myDbUtil.replace(news_teaser_text, "&#39;", "&#8217;");
            int charVal = 146; // the sgml character used for single quotes
            char theChar = (char) charVal;
            String sQuote = String.valueOf(theChar);
            news_teaser_text = myDbUtil.replace(news_teaser_text, sQuote, "&#8217;");
            news_teaser_text = news_teaser_text.replaceAll("\"", "\\\"");
            news_teaser_text = news_teaser_text.replaceAll("\'", "&#8217;");
            news_teaser_text = news_teaser_text.replaceAll("\n", " ");
            news_teaser_text = news_teaser_text.replaceAll("\r", " ");
//            StringTokenizer st = new StringTokenizer((String) request
//                  .getParameter("start_date"), "-");
//            d = st.nextToken();
//            m = st.nextToken();
//            y = st.nextToken();
//            news_start_date = y + "-" + m + "-" + d;
            news_start_date = request.getParameter("start_date");
            news_end_date = (String) request.getParameter("end_date");
            if (news_end_date.length() == 0) {
               news_end_date = "9999-12-31";
            } 
//            else {
//               st = new StringTokenizer((String) news_end_date, "-");
//               d = st.nextToken();
//               m = st.nextToken();
//               y = st.nextToken();
//               news_end_date = y + "-" + m + "-" + d;
//            }
            news_active_yn = (String) request.getParameter("active_yn");
            news_author_id = Integer.parseInt((String) request
                  .getParameter("news_author_id"));
            news_categories_id = Integer.parseInt((String) request
                  .getParameter("news_categories_id"));

            if (okay) {

               myNewsItem.setConn(conn);

               myNewsItem.setNews_title(news_title);
               myNewsItem.setNews_teaser_text(news_teaser_text);
               myNewsItem.setNews_start_date(news_start_date);
               myNewsItem.setNews_end_date(news_end_date);
               myNewsItem.setNews_active_yn(news_active_yn);
               myNewsItem.setNews_author_id(news_author_id);
               myNewsItem.setNews_categories_id(news_categories_id);
               myNewsItem.setBrowser_title(browser_title);
               myNewsItem.setMeta_tags(meta_tags);
               
               if (news_id > 0) {
                  myNewsItem.setNews_id(news_id);
                  myNewsItem.updateNews();
               } else {
                  news_id = myNewsItem.addNews();
               }
               
               // now handle the news ticker stuff
               if (request.getParameter("feature_yn").equals("y")) {
            	   Statement st = null;
            	   st = conn.createStatement();
            	   st.executeUpdate("delete from news_ticker where news_id = " + news_id);
            	   st.close();
            	   st = null;
            	   st = conn.createStatement();
            	   st.executeUpdate("insert into news_ticker (news_id) values ('" + news_id + "')");
            	   st.close();
            	   st = null;
               } else {
            	   Statement st = null;
            	   st = conn.createStatement();
            	   st.executeUpdate("delete from news_ticker where news_id = " + news_id);
            	   st.close();
            	   st = null;
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
            Errors.addError("ProcessEditNewsItem:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}