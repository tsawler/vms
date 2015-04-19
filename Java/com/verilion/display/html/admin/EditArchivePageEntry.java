//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-09-24
//Revisions
//------------------------------------------------------------------------------
//$Log: EditArchivePageEntry.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/09/24 18:05:43  tcs
//Initial entry (incomplete)
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.ArchivePageEntry;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
* Form for editng an archive page entry
* 
* <P>
* September 24 2004
* <P>
* 
* @author tsawler
*  
*/
public class EditArchivePageEntry
		extends
		SecurePage {

   private static final long serialVersionUID = 3375646443308046501L;

   public void BuildPage(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
	    
	    XDisconnectedRowSet drs = new XDisconnectedRowSet();
	    ArchivePageEntry myEntry = new ArchivePageEntry();
	    int archive_page_entry_id = 0;
	    int archive_page_id = 0;
	    
		try {
		    
		    archive_page_entry_id = Integer.parseInt((String) request.getParameter("page_id"));
		    archive_page_id = Integer.parseInt((String) request.getParameter("apage_id"));
		    myEntry.setConn(conn);
		    myEntry.setArchive_page_entry_id(archive_page_entry_id);
		    
		    drs = myEntry.getArchivePageEntry();
		    
		    while (drs.next()) {
		        MasterTemplate.searchReplace("$APPAGEID$", archive_page_entry_id + "");
		        MasterTemplate.searchReplace("$APEPAGEID$", archive_page_id + "");
		        MasterTemplate.searchReplace("$APTITLE$", drs.getString("archive_page_entry_title"));
		        MasterTemplate.searchReplace("$STARTDATE$", drs.getString("start_date"));
		        MasterTemplate.searchReplace("$ENDDATE$", drs.getString("end_date"));
		        MasterTemplate.searchReplace("$PHONE$", drs.getString("archive_page_entry_phone"));
		        MasterTemplate.searchReplace("$FAX$", drs.getString("archive_page_entry_fax"));
		        MasterTemplate.searchReplace("$EMAIL$", drs.getString("archive_page_entry_email"));
		        MasterTemplate.searchReplace("$URI$", drs.getString("archive_page_entry_url"));
		        MasterTemplate.searchReplace("$APTEXT$", drs.getString("archive_page_entry_content"));
		        
		    }

		} catch (Exception e) {
			e.printStackTrace();
			Errors
					.addError("EditArchivePageEntry:BuildPage:Exception:"
							+ e.toString());
		} finally {
		    if (rs != null) {
		        rs.close();
		        rs = null;
		    }
		}
	}
}