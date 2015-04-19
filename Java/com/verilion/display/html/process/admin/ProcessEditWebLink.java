//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-13
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditWebLink.java,v $
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
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit/create a web link
 * 
 * @author tsawler
 *  
 */
public class ProcessEditWebLink extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int link_id = 0;
      int link_cat_id = 0;
      String url = "";
      String description = "";
      String title = "";
      String active_yn = "n";
      
      String theMenu = "";
      GenericTable myTable = new GenericTable("links");
      GenericTable myLinkCat = new GenericTable("links_categories");
      
      
      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      XDisconnectedRowSet crs = new XDisconnectedRowSet();

      boolean okay = true;
      String theError = "";
      
      myTable.setConn(conn);
      myLinkCat.setConn(conn);

      if (((String) request.getParameter("edit")).equals("false")) {

         try {
            // see if we have an id passed to us. If we do, this is not
            // our first time to this form
            if (request.getParameter("id") != null) {
               link_id = Integer.parseInt((String) request
                     .getParameter("id"));
            }

            if (link_id > 0) {
               // get record information
               myTable.setSSelectWhat(" * ");
               myTable.setSCustomWhere("and link_id = " + link_id);
               rs = myTable.getAllRecordsDisconnected();

               if (rs.next()) {

                  // build ddlb for categories
                  myLinkCat.setSSelectWhat(" * ");
                  crs = myLinkCat.getAllRecordsDisconnected();

                  theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "link_cat_id",
                        rs.getInt("link_cat_id"),
                        crs,
                        "links_cat_id",
                        "title",
                        0,
                        "");

                  // put the formatted drop down list box in HTML object
                  MasterTemplate.searchReplace("$DDLBCAT$", theMenu);


                  // put the title on the screen
                  title = rs.getString("title");
                  MasterTemplate.searchReplace("$TITLE$", title);
                  
                  // put the url on the screen
                  url = rs.getString("url");
                  MasterTemplate.searchReplace("$URL$", url);

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
                  description = rs.getString("description");
                  description = description.replaceAll("\"", "\\\"");
                  description = description.replaceAll("\'", "&#8217;");
                  description = description.replaceAll("\n", " ");
                  description = description.replaceAll("\r", " ");
                  MasterTemplate.searchReplace("$DESCRIPTION$", description);
                  
                  String cancelPage = (String) request.getParameter("cancelPage");
                  MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               }
            } else {
               // we have no news item, so put blanks everywhere
               MasterTemplate.searchReplace("$TITLE$", "");
               MasterTemplate.searchReplace("$SELECTYNY$", "");
               MasterTemplate.searchReplace("$SELECTYNN$", "");
               MasterTemplate.searchReplace("$URL$", "");
               MasterTemplate.searchReplace("$DESCRIPTION$", "");
               MasterTemplate.searchReplace("$CANCEL$", (String) request.getParameter("cancelPage"));

               // build ddlb for categories
               myLinkCat.setSSelectWhat(" * ");
               crs = myLinkCat.getAllRecordsDisconnected();

               theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                     "link_cat_id",
                     0,
                     crs,
                     "links_cat_id",
                     "title",
                     0,
                     "");
               
               MasterTemplate.searchReplace("$DDLBCAT$", theMenu);

            }

            // put id in hidden field
            MasterTemplate.searchReplace("$ID$", link_id + "");
            crs.close();
            crs = null;

         } catch (Exception e) {
            e.printStackTrace();
            Errors
                  .addError("ProcessEditWebLink:ProcessForm:Exception:"
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
            link_id = Integer
                  .parseInt((String) request.getParameter("id"));
            if (((String) request.getParameter("title") != null)
                  && (((String) request.getParameter("title")).length() > 0)) {
               title = (String) request.getParameter("title");
            } else {
               theError = "You must supply a question!";
               okay = false;
            }
            url = (String) request
                  .getParameter("url");

            // clean up the messy codes put in by the html editor, if any
            description = request.getParameter("description");

            active_yn = (String) request.getParameter("active_yn");

            link_cat_id = Integer.parseInt((String) request
                  .getParameter("link_cat_id"));

            if (okay) {

               myTable.addUpdateFieldNameValuePair("link_cat_id", link_cat_id + "", "int");
               myTable.addUpdateFieldNameValuePair("url", url, "String");
               myTable.addUpdateFieldNameValuePair("description", description, "String");
               myTable.addUpdateFieldNameValuePair("active_yn", active_yn, "String");
               myTable.addUpdateFieldNameValuePair("title", title, "String");
               
               if (link_id > 0) {
                  myTable.setSCustomWhere(" and link_id = " + link_id);
                  myTable.setUpdateWhat("links");
                  myTable.updateRecord();
               } else {
                  myTable.setSTable("links");
                  myTable.insertRecord();
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
            Errors.addError("ProcessEditWebLink:ProcessForm:Exception:"
                  + e.toString());
         }

      }
      return MasterTemplate;
   }
}