//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-21
//Revisions
//------------------------------------------------------------------------------
//$Log: EditPageDocuments.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/07/22 15:34:46  tcs
//Changes for doc attachments, better visual feedback
//
//Revision 1.1  2004/07/21 16:36:02  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Documents;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
* Manage documents associated with a page
* 
* <P>
* July 21 2004
* <P>
* @author tsawler
*  
*/
public class EditPageDocuments extends SecurePage {

   private static final long serialVersionUID = 4012827017509797786L;

   public void BuildPage(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session)
		throws Exception {
		
		int page_id = Integer.parseInt((String)request.getParameter("page_id"));
		XDisconnectedRowSet rs = new XDisconnectedRowSet();
		
		Documents myDocs = new Documents();
		myDocs.setConn(conn);

		try {
			myDocs.setPage_id(page_id);
			rs = myDocs.getAllDocumentsForPageId();
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
				MasterTemplate.searchReplaceRecordSet("$DOCID$",rs.getInt("document_id") + "");
				MasterTemplate.searchReplaceRecordSet("$COUNTER$", counter + "");
				MasterTemplate.searchReplaceRecordSet("$NAME$", rs.getString("document_name"));
				MasterTemplate.searchReplaceRecordSet("$DOCTITLE$", rs.getString("document_title"));
				if (rs.getString("document_active_yn").equals("y")) {
					MasterTemplate.searchReplaceRecordSet("$PUBLISHED$","<a "
							+ "class=\"pub\" "
							+ "title=\"Click to unpublish\" href=\"/servlet/"
							+ "com.verilion.display.html.admin.UnpublishDocument?item="
							+ rs.getString("document_id") 
							+ "&amp;page_id="
							+ page_id
							+ "\">published</a>");
				} else {
					MasterTemplate.searchReplaceRecordSet("$PUBLISHED$","<a "
							+ "class=\"upub\" " 
							+ "title=\"Click to publish\" href=\"/servlet/"
							+ "com.verilion.display.html.admin.PublishDocument?item="
							+ rs.getString("document_id") 
							+ "&amp;page_id="
							+ page_id
							+ "\">unpublished</a>");
				}
				MasterTemplate.insertRecordSet();
				counter++;
			}
			
			MasterTemplate.replaceRecordSet();
			MasterTemplate.searchReplace("$DOCCOUNT$", counter + "");
			
			MasterTemplate.searchReplace("$PAGEID$", page_id + "");
			
			rs.close();
			rs = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			Errors.addError(
				"com.verilion.display.admin.EditPageDocuments:BuildPage:Exception:"
					+ e.toString());
		} finally {
		    if (rs != null) {
		        rs.close();
		        rs = null;
		    }
		}
	}
}