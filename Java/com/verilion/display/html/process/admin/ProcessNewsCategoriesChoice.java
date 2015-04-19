//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-01-11
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessNewsCategoriesChoice.java,v $
//Revision 1.1.2.7.2.1  2005/08/08 15:34:28  tcs
//Changes for new URL & jsp tag methodology
//
//Revision 1.1.2.7  2005/04/25 17:39:24  tcs
//Changes for jsp templating
//
//Revision 1.1.2.6  2005/01/20 16:58:58  tcs
//Removed need for setting publish url directly
//
//Revision 1.1.2.5  2005/01/19 17:53:01  tcs
//Moved a lot of code up to ResultsTable from other classes
//
//Revision 1.1.2.4  2005/01/13 17:58:07  tcs
//Changed constructor for ResultsTable to include session
//
//Revision 1.1.2.3  2005/01/11 17:06:59  tcs
//Cleaned up some members
//
//Revision 1.1.2.2  2005/01/11 14:00:47  tcs
//changed edit page url
//
//Revision 1.1.2.1  2005/01/11 13:38:32  tcs
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
import com.verilion.database.NewsCategories;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.ResultsTable;

/**
 * Provides list of news categories for edit etc
 * 
 * <P>
 * January 11 2005
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ProcessNewsCategoriesChoice extends ProcessPage {

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet rs = new XDisconnectedRowSet();

      DBUtils myDbUtil = new DBUtils();

      NewsCategories myNewsCategories = new NewsCategories();

      myNewsCategories.setConn(conn);

      try {
         if (hm.get("limit") != null) {
            myNewsCategories.setOffset(Integer.parseInt((String) hm
                  .get("offset")));
            myNewsCategories.setLimit(Integer
                  .parseInt((String) hm.get("limit")));
         }

         rs = myNewsCategories.getAllNewsCategories();

         ResultsTable myTable = new ResultsTable(hm, session);
         String sUrl = "/admin/jpage/1/p/NewsCategoriesChoice/a/newscategorieschoice/class/newscategories/admin/1/"
     		+ "/content.do";
         myTable.setSUrl(sUrl);
         myTable.setRs(rs);
         String[] sColHeadings = { "Category Name" };
         myTable.setSColHeadings(sColHeadings);
         boolean[] bSortable = { false };
         myTable.setBSortable(bSortable);
         myTable.setSLinkColName("news_categories_name");

         myDbUtil.setConn(conn);
         myTable.setINumResultsetRows(myDbUtil.countRowsForTable(
               "news_categories",
               "",
               "news_categories_name"));

         myTable
               .setSEditPage("/admin/jpage/1/p/NewsCategoriesDetail/a/newscategorydetail/admin/1/content.do?"
                     + "cancelPage="
                     + sUrl
                     + "&id=");
         myTable
               .setSNewButtonUrl("/admin/jpage/1/p/NewsCategoriesDetail/a/newscategorydetail/admin/1/content.do?id=-1&edit=false"
                     + "&cancelPage="
                     + sUrl);
         myTable.setSIdColumn("news_categories_id");
         myTable.setSPublishCol("news_categories_active_yn");

         String myResultsTable = myTable.GenerateHTMLResultsTable();

         MasterTemplate.searchReplace("<!--ResultsTable-->", myResultsTable);
         rs.close();
         rs = null;

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ProcessNewsCategoriesChoice:BuildPage:Exception:"
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