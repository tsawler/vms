//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessNewsArchive.java,v $
//Revision 1.4.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4  2004/10/27 11:48:11  tcs
//Changes due to refactoring
//
//Revision 1.3  2004/10/26 15:41:34  tcs
//Improved javadocs
//
//Revision 1.2  2004/10/19 17:21:21  tcs
//Removed unused members
//
//Revision 1.1  2004/10/18 16:16:11  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.NewsCategories;
import com.verilion.database.NewsItem;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;

/**
 * Put news archive on screen
 * 
 * <P>
 * October 18, 2004
 * <P>
 * 
 * @author tsawler
 * @see com.verilion.display.html.Page
 *  
 */
public class ProcessNewsArchive extends ProcessPage {

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int numberOfItems = 0;
      NewsCategories myCategories = new NewsCategories();
      myCategories.setConn(conn);
      NewsItem myNewsItem = new NewsItem();
      myNewsItem.setConn(conn);
      ResultSet rs = null;

      try {
         MasterTemplate.getRecordSet("<!--RECORDSET-->", "<!--/RECORDSET-->");

         // get our news item from the database
         rs = myCategories.getAllActiveNewsCategories();

         while (rs.next()) {
            MasterTemplate.searchReplaceRecordSet("$CAT$", rs
                  .getString("news_categories_name"));
            MasterTemplate.searchReplaceRecordSet("$CID$", rs
                  .getInt("news_categories_id")
                  + "");
            myNewsItem.setNews_categories_id(rs.getInt("news_categories_id"));
            numberOfItems = myNewsItem.getCountForCategory();
            MasterTemplate.searchReplaceRecordSet("$NUM$", numberOfItems + "");
            MasterTemplate.insertRecordSet();
         }
         MasterTemplate.replaceRecordSet();
         rs.close();
         rs = null;

      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("com.verilion.display.html.ProcessPage:BuildPage:Exception:"
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