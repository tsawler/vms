//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-05
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditWebLinksCategory.java,v $
//Revision 1.1.2.1  2007/03/14 00:19:42  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Edit/create a web link category
 * 
 * @author tsawler
 *  
 */
public class ProcessEditWebLinksCategory extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int links_cat_id = 0;
      String title = "";
      GenericTable myTableObject = new GenericTable(" links_categories ");
      String active_yn = "n";
      
      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      XDisconnectedRowSet crs = new XDisconnectedRowSet();

      boolean okay = true;
      String theError = "";
      
      myTableObject.setConn(conn);
      System.out.println("Inside");
      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               links_cat_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (links_cat_id > 0) {
               // get link cat information
               myTableObject.setSSelectWhat(" * ");
               myTableObject.setSCustomWhere(" and links_cat_id = " + links_cat_id);
               rs = myTableObject.getAllRecordsDisconnected();

               if (rs.next()) {
                  // put the title on the screen
                  title = rs.getString("title");
                  MasterTemplate.searchReplace("$FAQNAME$", title);

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
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));

            }

            // put id in hidden field
            MasterTemplate.searchReplace("$ID$", links_cat_id + "");
            crs.close();
            crs = null;

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditWebLinksCategory:ProcessForm:Exception:"
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
            links_cat_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("title") != null)
                  && (((String) request.getParameter("title")).length() > 0)) {
               title = (String) request.getParameter("title");
            } else {
               theError = "You must supply a name!";
               okay = false;
            }

            active_yn = (String) request.getParameter("active_yn");


            if (okay) {
               
               myTableObject.setSCustomWhere(" and links_cat_id = " + links_cat_id);
               
               myTableObject.addUpdateFieldNameValuePair("title", title, "String");
               myTableObject.addUpdateFieldNameValuePair("active_yn", active_yn, "String");
               
               if (links_cat_id > 0) {
                  myTableObject.setUpdateWhat("links_categories");
                  myTableObject.updateRecord();
               } else {
                  myTableObject.setSTable("links_categories");
                  myTableObject.insertRecord();
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
            Errors.addError("ProcessEditWebLinksCategory:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}