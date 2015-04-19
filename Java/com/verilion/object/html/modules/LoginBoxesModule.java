// ------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-06
//Revisions
//------------------------------------------------------------------------------
//$Log: LoginBoxesModule.java,v $
//Revision 1.5.2.1.10.1.2.2.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.5.2.1.10.1.2.2  2007/03/19 00:46:16  tcs
//Added display of waiting methods
//
//Revision 1.5.2.1.10.1.2.1  2007/03/15 17:49:25  tcs
//Cosmetic changes
//
//Revision 1.5.2.1.10.1  2006/11/16 13:53:36  tcs
//Corrected comments
//
//Revision 1.5.2.1  2004/12/08 17:30:57  tcs
//Refactored SystemPages to be in com.verilion.database
//
//Revision 1.5  2004/11/10 19:52:15  tcs
//Implemented module interface
//
//Revision 1.4  2004/06/25 02:15:37  tcs
//Closed statement
//
//Revision 1.3  2004/06/24 17:58:19  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.2  2004/06/06 21:51:39  tcs
//corrected display of logged in box/menu
//
//Revision 1.1  2004/06/06 21:06:53  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.GenericTable;
import com.verilion.database.SystemPages;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.Errors;

/**
 * Displays login boxes or logged in message/menu
 * <P>
 * Jun 3, 2006
 * <P>
 * 
 * @author tsawler
 * 
 */
public class LoginBoxesModule implements ModuleInterface {

   /**
    * displays login boxes or logged in messages/menu
    * 
    * @return String - login box as html
    * @throws Exception
    */
   public String getHtmlOutput(Connection conn, HttpSession session, HttpServletRequest request)
			throws Exception {

		String theLoginText = "";
		StringBuffer sb;
		int msgCount = 0;
		String originalUrl = "";
		
		try {
			SystemPages.setConn(conn);
			HashMap hm = (HashMap) session.getAttribute("pHm");
			originalUrl = (String) hm.get("original_url");
			
			if (session.getAttribute("user") == null) {
				theLoginText = (SystemPages.GetSystemPageByName("LoginBoxes"));
				//theLoginText = theLoginText.replaceAll("<!--url-->", request.getRequestURI());
				theLoginText = theLoginText.replaceAll("<!--url-->", originalUrl);
			} else {
				String myText = SystemPages
						.GetSystemPageByNameAndAccessLevelId(
								"Logged In",
								Integer.parseInt((String) session
										.getAttribute("customer_access_level")));
				HTMLTemplateDb myTemplate = new HTMLTemplateDb(myText);
				String userName = "";
                try {
                   userName = (String) session.getAttribute("username");
                } catch (Exception e){
                   int pos = ((String) session.getAttribute("user")).indexOf("@");
                   userName = ((String) session.getAttribute("user")).substring(
                        0,
                        pos);
                }
				GenericTable myTable = new GenericTable("customer_messages");
                myTable.setSSelectWhat("count(customer_message_id) as msgCount");
                myTable.setSCustomWhere("and is_read_yn = 'n' and to_user_id = " + session.getAttribute("customer_id"));
                myTable.setConn(conn);
                XDisconnectedRowSet drs = new XDisconnectedRowSet();
                drs = myTable.getAllRecordsDisconnected();
                while (drs.next()) {
                   msgCount = drs.getInt("msgCount");
                }
                drs.close();
                drs = null;
                myTemplate.searchReplace("$PM$", msgCount + "");
				myTemplate.searchReplace("$USER$", "<div style=\"text-align: center;\">Hi, " + userName + "</div>");
				sb = myTemplate.getSb();
				theLoginText = sb.toString();
			}
		} catch (Exception e) {
			Errors.addError("LoginBoxesModule.getHtmlOutput:Exception:"
					+ e.toString());
		}
		return theLoginText;
	}
}