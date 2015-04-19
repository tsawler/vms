//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-08
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditFaqCategory.java,v $
//Revision 1.1.2.2  2007/02/08 23:43:00  tcs
//Improved query so it sorts by parent/child on Faq Categories
//
//Revision 1.1.2.1  2007/02/08 19:28:53  tcs
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

import com.verilion.database.Faq;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit/create a faq category
 * 
 * @author tsawler
 *  
 */
public class ProcessEditFaqCategory extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int faq_categories_id = 0;
      String faq_categories_name = "";
      String theMenu = "";
      Faq myFaq = new Faq();
      String active_yn = "n";
      int parent_id = 0;
      
      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      XDisconnectedRowSet crs = new XDisconnectedRowSet();

      boolean okay = true;
      String theError = "";
      
      myFaq.setConn(conn);

      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               faq_categories_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (faq_categories_id > 0) {
               // get faq information
               
               rs = myFaq.getFaqCategoryById(faq_categories_id);

               if (rs.next()) {

                  // build ddlb for categories
                  myFaq.setSCustomWhere(" and parent_id = 0 ");
                  crs = myFaq.getAllActiveFaqCategories();

                  theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "parent_id",
                        rs.getInt("parent_id"),
                        crs,
                        "faq_categories_id",
                        "faq_categories_name",
                        0,
                        "No parent item");

                  // put the formatted drop down list box in HTML object
                  MasterTemplate.searchReplace("$DDLBCAT$", theMenu);


                  // put the title on the screen
                  faq_categories_name = rs.getString("faq_categories_name");
                  MasterTemplate.searchReplace("$FAQNAME$", faq_categories_name);

                  // put published status on screen
                  active_yn = rs.getString("active_yn");
                  if (active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }
                  
                  String cancelPage = (String) request.getParameter("cancelPage");
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               }
            } else {
               // we have no item, so put blanks everywhere
               MasterTemplate.searchReplace("$FAQNAME$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "");
               MasterTemplate.searchReplace("$ANSWER$", "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));

               // build ddlb for categories
               myFaq.setSCustomWhere(" and parent_id = 0 ");
               crs = myFaq.getAllActiveFaqCategories();

               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "parent_id",
                     0,
                     crs,
                     "faq_categories_id",
                     "faq_categories_name",
                     0,
                     "No parent item");
               MasterTemplate.searchReplace("$DDLBCAT$", theMenu);

            }

            // put id in hidden field
            MasterTemplate.searchReplace("$ID$", faq_categories_id + "");
            crs.close();
            crs = null;

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditFaqEntry:ProcessForm:Exception:"
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
         }
      } else {
         try {
            // get parameters
            faq_categories_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("faq_categories_name") != null)
                  && (((String) request.getParameter("faq_categories_name")).length() > 0)) {
               faq_categories_name = (String) request.getParameter("faq_categories_name");
            } else {
               theError = "You must supply a name!";
               okay = false;
            }

            active_yn = (String) request.getParameter("active_yn");

            parent_id = Integer.parseInt((String) request
                  .getParameter("parent_id"));

            if (okay) {
               myFaq.setConn(conn);
               myFaq.setFaq_categories_name(faq_categories_name);
               myFaq.setFaq_categories_id(faq_categories_id);
               myFaq.setParent_id(parent_id);
               myFaq.setActive_yn(active_yn);
               
               if (faq_categories_id > 0) {
                  myFaq.setFaq_categories_id(faq_categories_id);
                  myFaq.updateFaqCategory();
               } else {
                  faq_categories_id = myFaq.addFaqCategory();
               }
            }

            if (okay) {
               session.setAttribute("Error", "Update successful");
               response
                     .sendRedirect((String) request.getParameter("cancelPage"));
            } else {
               session.setAttribute("Error", theError);
               response
                     .sendRedirect((String) request.getParameter("cancelPage"));
            }
         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditFaqCategory:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}