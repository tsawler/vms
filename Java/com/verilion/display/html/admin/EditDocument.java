//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-22
//Revisions
//------------------------------------------------------------------------------
//$Log: EditDocument.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/07/22 13:57:44  tcs
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
* Edits a document that is attached to a page
* 
* <P>
* July 22 2004
* <P>
* @author tsawler
*  
*/
public class EditDocument extends SecurePage {

   private static final long serialVersionUID = 3286161500622334985L;

   public void BuildPage(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session)
		throws Exception {
		
		int page_id = Integer.parseInt((String)request.getParameter("page_id"));
		int document_id = Integer.parseInt((String)request.getParameter("document_id"));
		XDisconnectedRowSet crs = new XDisconnectedRowSet();
		String document_name = "";
		String document_title = "";
		String document_mime_type = "";
		String document_active_yn = "";
		int owner_id = 0;
		
		
		try {
		    // get the info for our document
		    Documents myDoc = new Documents();
		    myDoc.setConn(conn);
		    myDoc.setDocument_id(document_id);
		    crs = myDoc.getDocumentById();
		    
		    while (crs.next()) {
		        document_name = crs.getString("document_name");
		        document_title = crs.getString("document_title");
		        owner_id = crs.getInt("owner_id");
		        document_mime_type = crs.getString("document_mime_type");
		        document_active_yn = crs.getString("document_active_yn");
		    }
		    MasterTemplate.searchReplace("$FILENAME$", document_name);
		    MasterTemplate.searchReplace("$DOCTITLE$", document_title);
			MasterTemplate.searchReplace("$PAGEID$", page_id + "");
			MasterTemplate.searchReplace("$DOCID$", document_id + "");
			MasterTemplate.searchReplace("$ACTIVE$", document_active_yn);
			MasterTemplate.searchReplace("$OWNERID$", owner_id + "");
			MasterTemplate.searchReplace("$MIME$", document_mime_type);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Errors.addError(
				"com.verilion.display.admin.EditDocument:BuildPage:Exception:"
					+ e.toString());
		}
	}
}