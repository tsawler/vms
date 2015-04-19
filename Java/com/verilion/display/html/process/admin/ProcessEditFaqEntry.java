//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-08
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditFaqEntry.java,v $
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

import com.verilion.database.DBUtils;
import com.verilion.database.Faq;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit/create a faq item
 * 
 * @author tsawler
 *  
 */
public class ProcessEditFaqEntry extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int faq_id = 0;
      int faq_categories_id = 0;
      String question = "";
      String answer = "";
      String theMenu = "";
      Faq myFaq = new Faq();
      String active_yn = "n";
      
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
               faq_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (faq_id > 0) {
               // get faq information
               
               rs = myFaq.getFaqById(faq_id);

               if (rs.next()) {

                  // build ddlb for categories
                  crs = myFaq.getAllActiveFaqCategories();

                  theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "faq_categories_id",
                        rs.getInt("faq_categories_id"),
                        crs,
                        "faq_categories_id",
                        "faq_categories_name",
                        0,
                        "");

                  // put the formatted drop down list box in HTML object
                  MasterTemplate.searchReplace("$DDLBCAT$", theMenu);


                  // put the title on the screen
                  question = rs.getString("question");
                  MasterTemplate.searchReplace("$QUESTION$", question);

                  // put published status on screen
                  active_yn = rs.getString("active_yn");
                  if (active_yn.equals("y")) {
                     MasterTemplate.searchReplace("$SELECTYNY$", "selected");
                     MasterTemplate.searchReplace("$SELECTYNN$", "");
                  } else {
                     MasterTemplate.searchReplace("$SELECTYNY$", "");
                     MasterTemplate.searchReplace("$SELECTYNN$", "selected");
                  }

                  // put description on screen
                  answer = rs.getString("answer");
                  answer = answer.replaceAll("\"", "\\\"");
                  answer = answer.replaceAll("\'", "&#8217;");
                  answer = answer.replaceAll("\n", " ");
                  answer = answer.replaceAll("\r", " ");
                  MasterTemplate.searchReplace("$ANSWER$", answer);
                  
                  String cancelPage = (String) request.getParameter("cancelPage");
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$QUESTION$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "");
               MasterTemplate.searchReplace("$ANSWER$", "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));

               // build ddlb for categories
               crs = myFaq.getAllActiveFaqCategories();

               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "faq_categories_id",
                     0,
                     crs,
                     "faq_categories_id",
                     "faq_categories_name",
                     0,
                     "");
               MasterTemplate.searchReplace("$DDLBCAT$", theMenu);

            }

            // put id in hidden field
            MasterTemplate.searchReplace("$ID$", faq_id + "");
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
            faq_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("question") != null)
                  && (((String) request.getParameter("question")).length() > 0)) {
               question = (String) request.getParameter("question");
            } else {
               theError = "You must supply a question!";
               okay = false;
            }
            answer = (String) request
                  .getParameter("answer");

            // clean up the messy codes put in by the html editor, if any
            DBUtils myDbUtil = new DBUtils();
            answer = myDbUtil.replace(answer, "src=\"..", "src=\"");
            answer = myDbUtil.replace(answer, "&#39;", "&#8217;");
            int charVal = 146; // the sgml character used for single quotes
            char theChar = (char) charVal;
            String sQuote = String.valueOf(theChar);
            answer = myDbUtil.replace(answer, sQuote, "&#8217;");
            answer = answer.replaceAll("\"", "\\\"");
            answer = answer.replaceAll("\'", "&#8217;");
            answer = answer.replaceAll("\n", " ");
            answer = answer.replaceAll("\r", " ");

            active_yn = (String) request.getParameter("active_yn");

            faq_categories_id = Integer.parseInt((String) request
                  .getParameter("faq_categories_id"));

            if (okay) {

               myFaq.setConn(conn);

               myFaq.setQuestion(question);
               myFaq.setAnswer(answer);
               myFaq.setFaq_categories_id(faq_categories_id);
               myFaq.setActive_yn(active_yn);
               
               if (faq_id > 0) {
                  myFaq.setFaq_id(faq_id);
                  myFaq.updateFaqQuestionAnswer();
               } else {
                  faq_id = myFaq.addFaqQuestionAnswer();
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
            Errors.addError("ProcessEditFaqEntry:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}