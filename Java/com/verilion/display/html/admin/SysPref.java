//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-06-11
//Revisions
//------------------------------------------------------------------------------
//$Log: SysPref.java,v $
//Revision 1.6.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.6  2004/11/05 13:08:00  tcs
//Changes due to simplified database structure for pages & admin pages
//
//Revision 1.5  2004/06/26 17:03:28  tcs
//Changes due to refactoring
//
//Revision 1.4  2004/06/26 03:15:59  tcs
//Modified to use XDisconnectedRowSets
//
//Revision 1.3  2004/06/24 17:58:03  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.2  2004/06/11 16:11:50  tcs
//Changed to show only active pages for home page menu
//
//Revision 1.1  2004/06/11 15:45:24  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.PageTemplate;
import com.verilion.database.SystemPreferences;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Manage system preferences
 * 
 * <P>
 * June 11, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class SysPref extends SecurePage {

   private static final long serialVersionUID = -2294252918843693262L;
   XDisconnectedRowSet rs = new XDisconnectedRowSet();
   XDisconnectedRowSet rs2 = new XDisconnectedRowSet();

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      String theMenu = "";

      try {
         // Create SystemPreferences object and set connection
         SystemPreferences myPrefs = new SystemPreferences();
         myPrefs.setConn(conn);

         rs = myPrefs.getSystemPreferences();

         if (rs.next()) {
            // create drop down list of available pages
            PageTemplate myPageTemplate = new PageTemplate();
            myPageTemplate.setConn(conn);
            rs2 = myPageTemplate.getActivePageNamesIds();

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "homepage_page_id",
                  rs.getInt("homepage_page_id"),
                  rs2,
                  "page_id",
                  "page_name",
                  -1,
                  "");
            MasterTemplate.searchReplace("$DDLBHP$", theMenu);

            if (rs.getString("system_online_yn").equals("y")) {
               MasterTemplate.searchReplace("$ONLINEYNY$", "selected");
               MasterTemplate.searchReplace("$ONLINEYNN$", "");
            } else {
               MasterTemplate.searchReplace("$ONLINEYNY$", "");
               MasterTemplate.searchReplace("$ONLINEYNN$", "selected");
            }

            if (rs.getString("use_sef_yn").equals("y")) {
               MasterTemplate.searchReplace("$USESEFYNY", "selected");
               MasterTemplate.searchReplace("$USESEFYNN", "");
            } else {
               MasterTemplate.searchReplace("$USESEFYNY", "");
               MasterTemplate.searchReplace("$USESEFYNN", "selected");
            }

            MasterTemplate
                  .searchReplace("$MAILHOST$", rs.getString("mailhost"));
            MasterTemplate.searchReplace("$SECUREPORT$", rs
                  .getString("secure_port"));
            MasterTemplate.searchReplace("$INSECUREPORT$", rs
                  .getString("insecure_port"));
            MasterTemplate.searchReplace("$UPLOADPATH$", rs
                  .getString("upload_path"));
            MasterTemplate.searchReplace("$HOSTNAME$", rs
                  .getString("host_name"));
         }

         rs.close();
         rs = null;
         rs2.close();
         rs2 = null;

      } catch (Exception e) {
         Errors
               .addError("com.verilion.display.admin.AddPage:BuildPage:Exception:"
                     + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (rs2 != null) {
            rs2.close();
            rs2 = null;
         }
      }
   }
}