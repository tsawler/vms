//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-21
//Revisions
//------------------------------------------------------------------------------
//$Log: DoEditPageDocuments.java,v $
//Revision 1.3.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.3  2004/07/27 16:33:25  tcs
//Added missing redirect
//
//Revision 1.2  2004/07/22 14:50:43  tcs
//Removed unused code.
//
//Revision 1.1  2004/07/21 16:35:35  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.Documents;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
* Manage page items
* 
* July 21, 2004
* 
* @author tsawler
*  
*/
public class DoEditPageDocuments
		extends
		SecurePage {

   private static final long serialVersionUID = -378551706200039738L;

   public void BuildPage(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {

		// initialize variables
		int docCount = 0;
		int action = 0;
		int temp_id = 0;

		try {
			// get our parameters
			action = Integer.parseInt((String) request.getParameter("action"));
			docCount = Integer.parseInt((String) request
					.getParameter("docCount"));

			// set attributes for item
			Documents myDoc = new Documents();
			myDoc.setConn(conn);

			// now perform the appropriate action on the selected rows
			// 1 = publish
			// 2 = unpublish
			// 3 = delete
			// 4 = cancel

			switch (action) {
				case 1 :
					// want to publish all selected rows
					for (int i = 1; i <= docCount; i++) {
						if (request.getParameter("action_" + i) != null) {
							temp_id = Integer.parseInt((String) request
									.getParameter("doc_id" + i));
							myDoc.setDocument_id(temp_id);
							myDoc.setDocument_active_yn("y");
							myDoc.changeActiveStatus();
						}
					}
					break;

				case 2 :
					// want to unpublish all selected rows
					for (int i = 1; i <= docCount; i++) {
						if (request.getParameter("action_" + i) != null) {
							temp_id = Integer.parseInt((String) request
									.getParameter("doc_id" + i));
							myDoc.setDocument_id(temp_id);
							myDoc.setDocument_active_yn("n");
							myDoc.changeActiveStatus();
						}
					}
					break;

				case 3 :
					// want to delete all selected rows
					for (int i = 1; i <= docCount; i++) {
						if (request.getParameter("action_" + i) != null) {
							temp_id = Integer.parseInt((String) request
									.getParameter("doc_id" + i));
							myDoc.setDocument_id(temp_id);
							myDoc.deleteDocument();
						}
					}
					break;
			    case 4:
			        // cancel
			        response.sendRedirect(
			                "/servlet/com.verilion.display.html.admin.EditPage?"
			                + "page=EditPage&page_id="
			                + (String) request.getParameter("page_id")
			                + "&ct_language_id=1");
			        break;
			        
			}
			response.sendRedirect(
			        "/servlet/com.verilion.display.html.admin.EditPageDocuments?"
			        	+ "&page=EditPageDocuments&page_id="
			        	+ (String) request.getParameter("page_id"));

		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("DoEditPageDocuments:BuildPage:Exception:"
					+ e.toString());
		}
	}
}