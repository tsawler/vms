//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-27
//Revisions
//------------------------------------------------------------------------------
//$Log: EditArchivePageEntries.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/07/27 18:17:41  tcs
//Added archive_page_id as hidden field
//
//Revision 1.1  2004/07/27 11:17:49  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.ArchivePageEntry;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
* Manage archive page entries
* 
* <P>
* July 27, 2004
* <P>
* @author tsawler
*  
*/
public class EditArchivePageEntries extends SecurePage {

   private static final long serialVersionUID = -7491294821587248352L;

   public void BuildPage(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session)
		throws Exception {
		
		ResultSet rs = null;
		int archive_page_id = Integer.parseInt((String)request.getParameter("id"));
		
		ArchivePageEntry myEntry = new ArchivePageEntry();
		myEntry.setConn(conn);
		myEntry.setArchive_page_id(archive_page_id);
		
		try {
		    MasterTemplate.searchReplace("$ARPAGEID$", archive_page_id + "");
			rs = myEntry.getArchivePageEntryNamesTitlesIdsForArchivePageId();
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
				MasterTemplate.searchReplaceRecordSet("$PAGEID$",rs.getInt("archive_page_entry_id") + "");
				MasterTemplate.searchReplaceRecordSet("$COUNTER$", counter + "");
		
				MasterTemplate.searchReplaceRecordSet("$NAME$", rs.getString("archive_page_entry_title"));
				
				if (rs.getString("archive_page_entry_active_yn").equals("y")) {
					MasterTemplate.searchReplaceRecordSet("$PUBLISHED$","<a "
							+ "class=\"pub\" "
							+ "title=\"Click to unpublish\" href=\"/servlet/"
							+ "com.verilion.display.html.admin.UnpublishArchivePageEntry?item="
							+ rs.getString("archive_page_entry_id") 
							+ "\">published</a>");
				} else {
					MasterTemplate.searchReplaceRecordSet("$PUBLISHED$","<a "
							+ "class=\"upub\" " 
							+ "title=\"Click to publish\" href=\"/servlet/"
							+ "com.verilion.display.html.admin.PublishArchivePageEntry?item="
							+ rs.getString("archive_page_entry_id") 
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
				"EditArchivePageEntries:BuildPage:Exception:"
					+ e.toString());
		} finally {
		    if (rs != null) {
		        rs.close();
		        rs = null;
		    }
		}
	}
}