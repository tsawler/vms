//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-04-27
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessModuleChoose.java,v $
//Revision 1.1.2.2.2.1  2005/08/08 15:34:28  tcs
//Changes for new URL & jsp tag methodology
//
//Revision 1.1.2.2  2005/04/27 14:49:53  tcs
//Fixed new button url
//
//Revision 1.1.2.1  2005/04/27 14:16:48  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DBUtils;
import com.verilion.database.Modules;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.ResultsTable;

/**
 * Provides list of modules for edit etc
 * 
 * <P>
 * April 27 2005
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessModuleChoose extends ProcessPage {

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet rs = new XDisconnectedRowSet();

      DBUtils myDbUtil = new DBUtils();
      Modules myModules = new Modules();

      myModules.setConn(conn);

      try {
         if (hm.get("limit") != null) {
            myModules.setOffset(Integer.parseInt((String) hm.get("offset")));
            myModules.setLimit(Integer.parseInt((String) hm.get("limit")));
         }

         Enumeration e = session.getAttributeNames();
         String customWhere = "where ";
         while (e.hasMoreElements()) {
            String theElement = (String) e.nextElement();
            if (theElement.length() > 7) {
               if (theElement.substring(0, 7).equals("filter_")) {
                  if (session.getAttribute(theElement) != null) {
                     if (((String) session.getAttribute(theElement)).length() < 1) {
                        session.removeAttribute(theElement);
                     } else {
                        customWhere += theElement.substring(7, theElement
                              .length())
                              + " ilike '"
                              + session.getAttribute(theElement)
                              + "%'";
                     }
                  } else {
                     session.removeAttribute(theElement);
                  }
               }
            }
         }
         myModules.createCustomWhere(customWhere);
         rs = myModules.getAllModules();

         ResultsTable myTable = new ResultsTable(hm, session);

         myTable.setSClass("modules");
         String sUrl = "/admin/jpage/1/p/ModulesChoice/a/moduleschoice/class/modules/admin/1/"
               + (String) hm.get("limit")
               + "/content.do";
         myTable.setSUrl(sUrl);

         myTable.setRs(rs);
         String[] sColHeadings = { "Module name" };
         myTable.setSColHeadings(sColHeadings);
         boolean[] bSortable = { false, false };
         myTable.setBSortable(bSortable);
         myTable.setSLinkColName("module_name");
         String[] qbeFields = { "module_name" };
         myTable.setSFilterFields(qbeFields);
         String[] qbeDisplay = { "Name" };
         myTable.setSFilterFieldsDisplay(qbeDisplay);

         myDbUtil.setConn(conn);
         myTable.setINumResultsetRows(myDbUtil.countRowsForTable(
               "modules",
               customWhere,
               "module_name"));

         myTable
               .setSEditPage("/admin/jpage/1/p/ModuleAdd/a/moduledetail/admin/1/content.do?"
                     + "cancelPage="
                     + sUrl
                     + "&id=");
         myTable.setSNewButtonUrl("/admin/jpage/1/p/ModuleAdd/admin/1/content.do");
         myTable.setSIdColumn("module_id");
         myTable.setSPublishCol("module_active_yn");

         String myResultsTable = myTable.GenerateHTMLResultsTable();

         MasterTemplate.searchReplace("<!--ResultsTable-->", myResultsTable);
         rs.close();
         rs = null;

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ProcessModuleChoose:BuildPage:Exception:"
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