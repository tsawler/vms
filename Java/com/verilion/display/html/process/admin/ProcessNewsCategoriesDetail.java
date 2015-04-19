//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-01-11
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessNewsCategoriesDetail.java,v $
//Revision 1.1.2.1  2005/01/11 14:50:36  tcs
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

import com.verilion.database.NewsCategories;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Edit/create a news category
 * 
 * <P>
 * January 11 2005
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessNewsCategoriesDetail extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      //    initialize variables
      String news_categories_name = "";
      int news_categories_id = 0;

      if (((String) request.getParameter("edit")).equals("false")) {
         NewsCategories myNewsCategories = new NewsCategories();
         XDisconnectedRowSet crs = new XDisconnectedRowSet();

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               news_categories_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (news_categories_id > 0) {
               // get news item information
               myNewsCategories.setConn(conn);
               myNewsCategories.setNews_categories_id(news_categories_id);
               crs = myNewsCategories.getNewsCategoryRecordById();

               if (crs.next()) {
                  // put the title on the screen
                  news_categories_name = crs.getString("news_categories_name");
                  MasterTemplate.searchReplace(
                        "$NEWSCATNAME$",
                        news_categories_name);
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$NEWSCATNAME$", "");
            }

            // put id in hidden field (for image upload)
            MasterTemplate
                  .searchReplace("$NEWSCATID$", news_categories_id + "");
            MasterTemplate.searchReplace("$CANCEL$", (String) request
                  .getParameter("cancelPage"));
            crs.close();
            crs = null;
         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("com.verilion.display.html.admin.NewsCategoriesDetail:BuildPage:Exception:"
                        + e.toString());
         } finally {
            if (crs != null) {
               crs.close();
               crs = null;
            }
         }
         
      } else {
         
         try {
            // get parameters
            news_categories_id = Integer.parseInt((String) request
                  .getParameter("id"));
            news_categories_name = (String) request
                  .getParameter("news_categories_name");

            NewsCategories myNewsCategory = new NewsCategories();
            myNewsCategory.setConn(conn);

            myNewsCategory.setNews_categories_name(news_categories_name);

            if (news_categories_id > 0) {
               myNewsCategory.setNews_categories_id(news_categories_id);
               myNewsCategory.updateNewsCategoriesName();
            } else {
               myNewsCategory.setNews_categories_active_yn("n");
               myNewsCategory.addNewsCategories();
            }

            session
                  .setAttribute("Error", "News category successfully modified");
            response.sendRedirect((String) request.getParameter("cancelPage"));

         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("DoNewsCategoriesDetail:BuildPage:Exception:"
                  + e.toString());
         }
      }
      return MasterTemplate;
   }
}