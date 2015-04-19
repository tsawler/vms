//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-26
//Revisions
//------------------------------------------------------------------------------
//$Log: EditArchivePage.java,v $
//Revision 1.3.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.3  2004/07/27 11:04:48  tcs
//Modified to only show active templates
//
//Revision 1.2  2004/07/26 18:27:52  tcs
//Polished it a bit
//
//Revision 1.1  2004/07/26 15:40:33  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.ArchivePage;
import com.verilion.database.ArchivePageEntry;
import com.verilion.database.CtAccessLevel;
import com.verilion.database.CtLanguages;
import com.verilion.database.PageTemplate;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit an archive page
 * 
 * <P>
 * July 26, 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class EditArchivePage extends SecurePage {

   private static final long serialVersionUID = 4626647893854388703L;

   public void BuildPage(HttpServletRequest request,
            HttpServletResponse response, HttpSession session) throws Exception {

        String theMenu = "";
        XDisconnectedRowSet prs = new XDisconnectedRowSet();
        int archive_page_id = Integer.parseInt((String) request
                .getParameter("page_id"));
        int numberOfEntries = 0;
        ArchivePage myArchivePage = new ArchivePage();
        myArchivePage.setConn(conn);

        try {
            // get info for our page
            myArchivePage.setArchive_page_id(archive_page_id);
            prs = myArchivePage.getArchivePageById();
            ArchivePageEntry myEntry = new ArchivePageEntry();
            
            while (prs.next()) {
                
                MasterTemplate.searchReplace("$APNAME$", prs.getString("archive_page_name"));
                MasterTemplate.searchReplace("$APTITLE$", prs.getString("archive_page_title"));
                MasterTemplate.searchReplace("$APCONTENT$", prs.getString("archive_page_content"));
                
                //create drop down list of available templates
                PageTemplate myPageTemplate = new PageTemplate();
                myPageTemplate.setConn(conn);
                XDisconnectedRowSet crs = new XDisconnectedRowSet();
                crs = myPageTemplate.getAllActiveTemplateNamesIds();

                theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "template_id",
                        prs.getInt("template_id"),
                        crs,
                        "template_id",
                        "template_name",
                        0,
                        "");

                MasterTemplate.searchReplace("$DDLB$", theMenu);

                // create drop down list of access levels
                CtAccessLevel myCtAccessLevel = new CtAccessLevel();
                myCtAccessLevel.setConn(conn);
                crs = myCtAccessLevel.getAllAccessLevelNamesIds();

                theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "access_level_id",
                        prs.getInt("ct_access_level_id"),
                        crs,
                        "ct_access_level_id",
                        "ct_access_level_name",
                        -1,
                        "");

                MasterTemplate.searchReplace("$DDLB1$", theMenu);

                // create drop down list of available languages.
                CtLanguages myLanguages = new CtLanguages();
                myLanguages.setConn(conn);
                crs = myLanguages.getAllLanguageNamesIds();

                theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "ct_language_id",
                        0,
                        crs,
                        "ct_language_id",
                        "ct_language_name",
                        -1,
                        "");

                MasterTemplate.searchReplace("$DDLB2$", theMenu);
                
                ArchivePage myAPForMenu = new ArchivePage();
                myAPForMenu.setConn(conn);
                crs = myAPForMenu.getArchivePageNamesIdsStatuses();

                theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                        "parent_archive_page_id",
                        prs.getInt("parent_id"),
                        crs,
                        "archive_page_id",
                        "archive_page_name",
                        -1,
                        "Top Level Page");

                MasterTemplate.searchReplace("$DDLBPP$", theMenu);
                
                MasterTemplate.searchReplace("$PAGEID$", archive_page_id + "");
                
                numberOfEntries = 0;
                myEntry.setConn(conn);
				myEntry.setArchive_page_id(archive_page_id);
				numberOfEntries = myEntry.getNumberOfEntriesForArchivePageId();
				
				MasterTemplate.searchReplace("$ENTRIES$", numberOfEntries + " entries");
                
            }
        } catch (Exception e) {
            Errors
                    .addError("EditArchivePage:BuildPage:Exception:"
                            + e.toString());
        }
    }
}