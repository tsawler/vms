//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-09-24
//Revisions
//------------------------------------------------------------------------------
//$Log: DoEditArchivePageEntry.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/09/24 18:05:30  tcs
//Initial entry (incomplete)
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.ArchivePageEntry;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
* Edits an archive page entry.
* 
* <P>
* September 24, 2004
* <P>
* 
* @author tsawler
*  
*/
public class DoEditArchivePageEntry
		extends
		SecurePage {

	/**
    * 
    */
   private static final long serialVersionUID = 3442098559842106582L;

   public void BuildPage(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
	    
		int action = 0;
		int archive_page_id = 0;
		String archive_page_entry_title = "";
		String archive_page_entry_content = "";
		String archive_page_entry_url = "";
		String archive_page_entry_phone = "";
		String archive_page_entry_fax = "";
		String archive_page_entry_email = "";
		String start_date = "";
		String end_date = "";
		String d = "";
		String m = "";
		String y = "";
		ArchivePageEntry myPage = new ArchivePageEntry();

		try {
		    archive_page_id = Integer.parseInt((String) request.getParameter("archive_page_id"));
		    action = Integer.parseInt((String) request.getParameter("action"));

			// now perform the appropriate action
			// 1 = save
			// 2 = cancel

			switch (action) {
				case 1 :

					// get parameters passed by form, and put them in the
					// format we need.
				    archive_page_entry_title = (String) request.getParameter("archive_page_entry_title");
				    archive_page_entry_content = (String) request.getParameter("archive_page_entry_content");
				    archive_page_entry_url = (String) request.getParameter("archive_page_entry_url");
				    archive_page_entry_phone = (String) request.getParameter("archive_page_entry_phone");
				    archive_page_entry_fax = (String) request.getParameter("archive_page_entry_fax");
				    archive_page_entry_email = (String) request.getParameter("archive_page_entry_email");
				    StringTokenizer st = new StringTokenizer(
					        (String) request.getParameter("start_date"), "-"); 
					d = st.nextToken();
					m = st.nextToken();
					y = st.nextToken();
					start_date = y + "-" + m + "-" + d;
					end_date = (String) request.getParameter("end_date");
					if (end_date.length() == 0) {
						end_date = "9999-12-31";
					} else {
					    st = new StringTokenizer(
						        (String) end_date, "-");
					    d = st.nextToken();
						m = st.nextToken();
						y = st.nextToken();
						end_date = y + "-" + m + "-" + d;
					}
					
					myPage.setConn(conn);
					myPage.setArchive_page_id(archive_page_id);
					myPage.setArchive_page_entry_content(archive_page_entry_content);
					myPage.setArchive_page_entry_email(archive_page_entry_email);
					myPage.setArchive_page_entry_fax(archive_page_entry_fax);
					myPage.setArchive_page_entry_phone(archive_page_entry_phone);
					myPage.setArchive_page_entry_title(archive_page_entry_title);
					myPage.setArchive_page_entry_url(archive_page_entry_url);
					myPage.setEnd_date(end_date);
					myPage.setStart_date(start_date);
					myPage.setArchive_page_entry_active_yn("n");
					
					myPage.addArchivePageEntry();
					
					session.setAttribute("Error", "Entry added.");
					response.sendRedirect("/servlet/com.verilion.display.html.admin."
							+ "EditArchivePageEntries?page=EditArchivePageEntries&id=" + archive_page_id);
					
					break;
					
				case 2 :
					session.setAttribute("Error", "Cancelled.");
					response.sendRedirect("/servlet/com.verilion.display.html.admin."
									+ "EditArchivePageEntries?page=EditArchivePageEntries&id=" + archive_page_id);
					break;
			}
		} catch (Exception e) {
			Errors
					.addError("DoAddArchivePageEntry:BuildPage:Exception:"
							+ e.toString());
		}
	}
}