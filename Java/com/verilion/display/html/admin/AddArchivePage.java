//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-26
//Revisions
//------------------------------------------------------------------------------
//$Log: AddArchivePage.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/07/27 11:04:19  tcs
//Modified to only show active templates
//
//Revision 1.1  2004/07/26 15:40:05  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.ArchivePage;
import com.verilion.database.CtAccessLevel;
import com.verilion.database.CtLanguages;
import com.verilion.database.PageTemplate;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
* Allows site admin to add archive page to site.
* 
* <P>
* July 26, 2004
* <P>
* @author tsawler
*  
*/
public class AddArchivePage extends SecurePage {

   private static final long serialVersionUID = -5978948935738650737L;

   public void BuildPage(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session)
		throws Exception {
		
		String theMenu = "";
		ArchivePage myArchivePage = new ArchivePage();
		myArchivePage.setConn(conn);

		try {
			//create drop down list of available templates
			PageTemplate myPageTemplate = new PageTemplate();
			myPageTemplate.setConn(conn);
			XDisconnectedRowSet crs = new XDisconnectedRowSet();
			crs = myPageTemplate.getAllActiveTemplateNamesIds();
			
			theMenu =
				HTMLFormDropDownList.makeDropDownListSnippet(
					"template_id",
					0,
					crs,
					"template_id",
					"template_name",
					-1,
					"");

			MasterTemplate.searchReplace("$DDLB$", theMenu);

			// create drop down list of access levels
			CtAccessLevel myCtAccessLevel = new CtAccessLevel();
			myCtAccessLevel.setConn(conn);
			crs = myCtAccessLevel.getAllAccessLevelNamesIds();
			
			theMenu =
				HTMLFormDropDownList.makeDropDownListSnippet(
					"access_level_id",
					0,
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
			
			theMenu =
				HTMLFormDropDownList.makeDropDownListSnippet(
					"ct_language_id",
					0,
					crs,
					"ct_language_id",
					"ct_language_name",
					-1,
					"");

			MasterTemplate.searchReplace("$DDLB2$", theMenu);
			
			crs = myArchivePage.getArchivePageNamesIdsStatuses();
			
			theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
			        "parent_archive_page_id",
			        0,
			        crs,
			        "archive_page_id",
			        "archive_page_name",
			        0,
			        "Top Level Page");
			
			MasterTemplate.searchReplace("$DDLBPP$", theMenu);
		
		}
		catch (Exception e) {
			Errors.addError(
				"com.verilion.display.html.admin.AddArchivePage:BuildPage:Exception:"
					+ e.toString());
		}
	}
}