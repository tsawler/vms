//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-12-23
//Revisions
//------------------------------------------------------------------------------
//$Log: EditPackages.java,v $
//Revision 1.6.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.6  2004/06/26 17:03:31  tcs
//Changes due to refactoring
//
//Revision 1.5  2004/06/26 03:16:01  tcs
//Modified to use XDisconnectedRowSets
//
//Revision 1.4  2004/06/24 17:58:05  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.3  2004/06/11 11:36:49  tcs
//Modified for new interface
//
//Revision 1.2  2004/06/09 02:14:13  tcs
//Modified to use https
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.CtPackages;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Displays list of available packages for editing
 * 
 * <P>
 * December 23, 2003
 * <P>
 * 
 * @author tsawler
 *  
 */
public class EditPackages extends SecurePage {

   private static final long serialVersionUID = 1774827747901793980L;

   public void BuildPage(
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session)
		throws Exception {
	    
	    XDisconnectedRowSet crs = new XDisconnectedRowSet();

		try {
			boolean flip = false;
			int counter = 1;
			
			CtPackages myPackages = new CtPackages();
			myPackages.setConn(conn);
			crs = myPackages.getAllPackageNames();

			MasterTemplate.getRecordSet("<!--RECORD-->", "<!--/RECORD-->");

			while (crs.next()) {
				if (flip) {
					MasterTemplate.searchReplaceRecordSet("$ROWCOLOR$", "#fff");
					flip = false;
				} else {
					MasterTemplate.searchReplaceRecordSet("$ROWCOLOR$", "#eee");
					flip = true;
				}
				String theHref =
					"<a href=\"/servlet/com.verilion.display.html.admin.DisplayPackage?page=DisplayPackage&amp;id="
						+ crs.getString("ct_package_id")
						+ "\">"
						+ crs.getString("ct_package_name")
						+ "</a>";
				if (crs.getString("ct_package_active_yn").equals("y")) {
					MasterTemplate.searchReplaceRecordSet("$PUBLISHED$","<a "
							+ "class=\"pub\" "
							+ "title=\"Click to unpublish\" href=\"/servlet/"
							+ "com.verilion.display.html.admin.UnpublishPackages?item="
							+ crs.getString("ct_package_id") 
							+ "\">published</a>");
				} else {
					MasterTemplate.searchReplaceRecordSet("$PUBLISHED$","<a "
							+ "class=\"upub\" " 
							+ "title=\"Click to publish\" href=\"/servlet/"
							+ "com.verilion.display.html.admin.PublishPackages?item="
							+ crs.getString("ct_package_id") 
							+ "\">unpublished</a>");
				}
				MasterTemplate.searchReplaceRecordSet("$PACKAGE$", theHref);
				MasterTemplate.searchReplaceRecordSet("$COUNTER$", counter + "");
				MasterTemplate.searchReplaceRecordSet("$PACKAGEID$",crs.getInt("ct_package_id") + "");
				MasterTemplate.insertRecordSet();
				counter++;
			}
			MasterTemplate.replaceRecordSet();
			MasterTemplate.searchReplace("$PACKAGECOUNT$", counter + "");
			
			crs.close();
			crs = null;

		} catch (SQLException e) {
			Errors.addError(
				"com.verilion.display.html.admin.EditPackages:BuildPage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError(
				"com.verilion.display.html.admin.EditPackages:BuildPage:SQLException:"
					+ e.toString());
		} finally {
		    if (crs != null) {
		        crs.close();
		        crs = null;
		    }
		}
	}
}