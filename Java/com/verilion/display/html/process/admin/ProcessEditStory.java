//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-11
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditStory.java,v $
//Revision 1.1.2.1.4.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2007/01/12 19:27:57  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DBUtils;
import com.verilion.database.Story;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit/create a Story from admin tool
 * 
 * <P>
 * January 11, 2007
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessEditStory extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int story_id = 0;
      String story_title = "";
      String story = "";
      String story_author = "";
      String story_active_yn = "";
      int story_author_id = 0;
      int story_topic_id = 0;
      String browser_title = "";
      String meta_tags = "";
      String theMenu = "";
      Story myStory = new Story();
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
               story_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (story_id > 0) {
               // get story information
               myStory.setConn(conn);
               myStory.setStory_id(story_id);
               rs = myStory.getStoryById();

               if (rs.next()) {
            	   // build ddlb for categories
            	   int topicid = rs.getInt("story_topic_id");
            	   story_title = rs.getString("title");
            	   story_author_id = rs.getInt("customer_id");
            	   story_active_yn = rs.getString("story_active_yn");
            	   story_author = rs.getString("posted_by");
            	   story = rs.getString("story");
            	   browser_title = rs.getString("browser_title");
            	   meta_tags = rs.getString("meta_tags");
            	   
                  //myStory.setConn(conn);
                  catrs = myStory.getStoryCategories();

                  theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "story_topic_id",
                        topicid,
                        catrs,
                        "story_category_id",
                        "story_category_name",
                        0,
                        "");
                  // put browser title on screen
                  MasterTemplate.searchReplace("$BROWSERTITLE$", browser_title);
                  
                  // put meta tags on screen
                  MasterTemplate.searchReplace("$METATAGS$", meta_tags);
                  
                  // put the formatted drop down list box on page
                  MasterTemplate.searchReplace("$DDLBCAT$", theMenu);

                  MasterTemplate.searchReplace("$STORYTITLE$", story_title);
                  MasterTemplate.searchReplace("$AUTHORID$", story_author_id + "");
                  MasterTemplate.searchReplace("$AUTHOR$", story_author);
                  
                  // put published status on screen
                  
                  if (story_active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }
                  
                  

                  // put description on screen
                  
                  story = story.replaceAll("\"", "\\\"");
                  story = story.replaceAll("\'", "&#8217;");
                  story = story.replaceAll("\n", " ");
                  story = story.replaceAll("\r", " ");
                  MasterTemplate.searchReplace("$STORY$", story);
                  
                  String cancelPage = (String) request.getParameter("cancelPage");
                  if (session.getAttribute("d-1345801-p") != null) {
                     cancelPage += "?d-1345801-p=" + session.getAttribute("d-1345801-p");
                  }
                 
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
                  MasterTemplate.searchReplace("$ID$", story_id + "");
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$STORYTITLE$", "");
               MasterTemplate.searchReplace("$BROWSERTITLE$", "");
               MasterTemplate.searchReplace("$METATAGS$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "no");
               MasterTemplate.searchReplace("$AUTHOR$", "");
               MasterTemplate.searchReplace("$STORY$", "");
               MasterTemplate.searchReplace("$AUTHORID$", "0");
               MasterTemplate.searchReplace("$ID$", "0");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));


               myStory.setConn(conn);
               catrs = myStory.getStoryCategories();

               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "story_topic_id",
                     0,
                     catrs,
                     "story_category_id",
                     "story_category_name",
                     0,
                     "");

               // put the formatted drop down list box on page
               MasterTemplate.searchReplace("$DDLBCAT$", theMenu);

            }

            // put story id in hidden field
            MasterTemplate.searchReplace("$STORYID", story_id + "");

            crs.close();
            crs = null;
            catrs.close();
            catrs = null;

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditSTory:ProcessForm:Exception:"
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
            story_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("story_title") != null)
                  && (((String) request.getParameter("story_title")).length() > 0)) {
               story_title = (String) request.getParameter("story_title");
            } else {
               theError = "You must supply a title!";
               okay = false;
            }
            
            if (request.getParameter("browser_title") != null) {
            	browser_title = (String) request.getParameter("browser_title");
            } else {
            	browser_title = "";
            }
            
            story_author = (String) request.getParameter("story_author");
            if (story_author.length() < 1)
            	story_author = "admin";
            story = (String) request
                  .getParameter("story");
            // clean up the messy codes put in by the html editor, if any
            DBUtils myDbUtil = new DBUtils();
            story_author_id = Integer.parseInt((String) request.getParameter("story_author_id"));
            story = myDbUtil.replace(story, "src=\"..", "src=\"");
            story = myDbUtil.replace(story, "&#39;", "&#8217;");
            int charVal = 146; // the sgml character used for single quotes
            char theChar = (char) charVal;
            String sQuote = String.valueOf(theChar);
            story = myDbUtil.replace(story, sQuote, "&#8217;");
            story = story.replaceAll("\"", "\\\"");
            story = story.replaceAll("\'", "&#8217;");
            story = story.replaceAll("\n", " ");
            story = story.replaceAll("\r", " ");
            
            if (request.getParameter("meta_tags") != null) {
            	meta_tags = (String) request.getParameter("meta_tags");
            } else {
            	if (story.length() > 150) {
            		meta_tags = story.substring(0, 150) + "...";
            	} else {
            		meta_tags = story;
            	}
            }

            story_active_yn = (String) request.getParameter("active_yn");
            story_author_id = Integer.parseInt((String) request
                  .getParameter("story_author_id"));
            story_topic_id = Integer.parseInt((String) request
                  .getParameter("story_topic_id"));

            if (okay) {

               myStory.setConn(conn);
               myStory.setStory_title(story_title);
               myStory.setStory_id(story_id);
               myStory.setStory_text(story);
               myStory.setStory_topic_id(story_topic_id);
               myStory.setStory_active_yn(story_active_yn);
               myStory.setStory_author(story_author);
               myStory.setStory_author_id(story_author_id);
               myStory.setBrowser_title(browser_title);
               myStory.setMeta_tags(meta_tags);
               
               if (story_id > 0) {
                  myStory.setStory_id(story_id);
                  myStory.updateStory();
               } else {
                  story_id = myStory.addStory();
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
            Errors.addError("ProcessEditStory:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}