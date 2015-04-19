//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-26
//Revisions
//------------------------------------------------------------------------------
//$Log: EditArchivePageChoose.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/07/27 11:29:25  tcs
//Removed extra slash in generated url
//
//Revision 1.1  2004/07/26 15:40:42  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.ArchivePage;
import com.verilion.database.ArchivePageEntry;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
* Manage archive pages
* 
* <P>
* July 26, 2004
* <P>
* @author tsawler
*  
*/
public class EditArchivePageChoose extends SecurePage {

	/**
    * 
    */
   private static final long serialVersionUID = 8714647217256032965L;

   public void BuildPage(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session)
		throws Exception {
		
		ResultSet rs = null;
		int numberOfEntries = 0;
		
		ArchivePage myArchivePage = new ArchivePage();
		myArchivePage.setConn(conn);
		
		ArchivePageEntry myEntry = new ArchivePageEntry();
		myEntry.setConn(conn);

		try {
			rs = myArchivePage.getTopLevelArchivePageNamesIdsStatuses();
			boolean flip = false;
			int counter = 1;
			
			MasterTemplate.getRecordSet("<!-- RecordSet -->", "<!-- /RecordSet -->");
			
			while (rs.next()) {
				if (flip) {
					MasterTemplate.searchReplaceRecordSet("$ROWCOLOR$", "#fff");
					flip = false;
				} else {
					MasterTemplate.searchReplaceRecordSet("$ROWCOLOR$", "#eee");
					flip = true;
				}
				MasterTemplate.searchReplaceRecordSet("$PAGEID$",rs.getInt("archive_page_id") + "");
				MasterTemplate.searchReplaceRecordSet("$COUNTER$", counter + "");
				
				if (Integer.parseInt(rs.getString("ct_access_level_id")) > 1) {
				    MasterTemplate.searchReplaceRecordSet("$NAME$", "$NAME$" + " <img src=\"/images/locked.gif\" border=\"0\" />");
				}
		
				MasterTemplate.searchReplaceRecordSet("$NAME$", rs.getString("archive_page_name"));
				
				// check for number of entries
				numberOfEntries = 0;
				myEntry.setArchive_page_id(rs.getInt("archive_page_id"));
				numberOfEntries = myEntry.getNumberOfEntriesForArchivePageId();
				MasterTemplate.searchReplaceRecordSet("$ENTRIES$", " (<a href=\"/servlet"
				        + "/com.verilion.display.html.admin.EditArchivePageEntries?page=EditArchivePageEntries"
				        + "&id=" + rs.getInt("archive_page_id")
				        + "\">"
				        + numberOfEntries + " entries)</a>");
				
				if (rs.getString("archive_page_active_yn").equals("y")) {
					MasterTemplate.searchReplaceRecordSet("$PUBLISHED$","<a "
							+ "class=\"pub\" "
							+ "title=\"Click to unpublish\" href=\"/servlet/"
							+ "com.verilion.display.html.admin.UnpublishArchivePage?item="
							+ rs.getString("archive_page_id") 
							+ "\">published</a>");
				} else {
					MasterTemplate.searchReplaceRecordSet("$PUBLISHED$","<a "
							+ "class=\"upub\" " 
							+ "title=\"Click to publish\" href=\"/servlet/"
							+ "com.verilion.display.html.admin.PublishArchivePage?item="
							+ rs.getString("archive_page_id") 
							+ "\">unpublished</a>");
				}
				MasterTemplate.insertRecordSet();
				counter++;
			}
			
			MasterTemplate.replaceRecordSet();
			MasterTemplate.searchReplace("$PAGECOUNT$", counter + "");
			
			rs.close();
			rs = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			Errors.addError(
				"EditArchivePageChoose:BuildPage:Exception:"
					+ e.toString());
		} finally {
		    if (rs != null) {
		        rs.close();
		        rs = null;
		    }
		}
	}
}