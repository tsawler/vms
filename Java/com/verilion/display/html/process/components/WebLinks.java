//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-27
//Revisions
//------------------------------------------------------------------------------
//$Log: WebLinks.java,v $
//Revision 1.1.2.2  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2007/02/27 19:08:23  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.components;

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
 * Display Web links for end users
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 * 
 */
public class WebLinks extends ProcessPage {

   @SuppressWarnings("unchecked")
   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      GenericTable myTableObject = new GenericTable(" links_categories ");
      XDisconnectedRowSet drs = new XDisconnectedRowSet();

      try {
         
         MasterTemplate
               .appendString("\n<script type=\"text/javascript\">\n"
                     + "function showlinks() {\n"
                     + "var wheretogo = \"/linkshelper.jsp?id=\" + dwr.util.getValue(\"links_cat_id\");\n"
                     + "Validator.getIncludedFile(wheretogo, function(data) {\n"
                     + "dwr.util.setValue(\"linksdiv\", data, { escapeHtml:false });\n"
                     + "});\n}\n"
                     + "</script>\n\n");
         MasterTemplate.appendString("<fieldset><legend>Links</legend>");
         myTableObject.setConn(conn);
         myTableObject.setSSelectWhat(" * ");
         myTableObject.setSCustomOrder(" order by title");
         myTableObject.setSCustomWhere(" and active_yn = 'y' ");

         drs = myTableObject.getAllRecordsDisconnected();
         MasterTemplate.appendString("<div style=\"text-align: center\">\n");
         String theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
               "linkform",
               0,
               "/public/jpage/1/p/Links/a/weblinks/content.do",
               drs,
               "links_cat_id",
               "title",
               "Go",
               "onchange=\"showlinks()\"",
               "links_cat_id",
               "0",
               "Choose a category...");
         
         MasterTemplate.appendString("Show links for " + theMenu + "<div>&nbsp;</div>");
         MasterTemplate.appendString("</div>\n</fieldset>");
         MasterTemplate.appendString("<div id=\"linksdiv\"></div>");
         
      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.process.components.WebLinks:BuildPage:Exception:"
                     + e.toString());
      }

      return MasterTemplate;
   }
}