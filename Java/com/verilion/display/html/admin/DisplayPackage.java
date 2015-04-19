//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-12-23
//Revisions
//------------------------------------------------------------------------------
//$Log: DisplayPackage.java,v $
//Revision 1.5.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.5  2004/06/26 17:03:29  tcs
//Changes due to refactoring
//
//Revision 1.4  2004/06/26 03:16:01  tcs
//Modified to use XDisconnectedRowSets
//
//Revision 1.3  2004/06/24 17:58:04  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.2  2004/06/09 02:14:17  tcs
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
 * Displays individual package for editing
 * 
 * <P>
 * December 23, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DisplayPackage extends SecurePage {

   private static final long serialVersionUID = -6928594373353882138L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      XDisconnectedRowSet rs = new XDisconnectedRowSet();

      try {
         // get info from parameters
         int ct_package_id = Integer.parseInt((String) request
               .getParameter("id"));
         CtPackages myPackages = new CtPackages();
         myPackages.setConn(conn);
         myPackages.setCt_package_id(ct_package_id);
         rs = myPackages.getPackageDetail();

         if (rs.next()) {
            MasterTemplate.searchReplace("$PACKAGEID$", ct_package_id + "");
            MasterTemplate.searchReplace("$NAME$", rs
                  .getString("ct_package_name"));
            MasterTemplate.searchReplace("$PRICE$", rs
                  .getFloat("ct_package_price")
                  + "");
            MasterTemplate.searchReplace("$DESCRIPTION$", rs
                  .getString("ct_package_description"));
            if (rs.getString("ct_package_active_yn").equals("y")) {
               MasterTemplate.searchReplace("$CHECKED$", "checked");
            } else {
               MasterTemplate.searchReplace("$CHECKED$", "");
            }
            rs.close();
            rs = null;
         }
      } catch (SQLException e) {
         Errors
               .addError("com.verilion.display.html.admin.DisplayPackage:BuildPage:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("com.verilion.display.html.admin.DisplayPackage:BuildPage:SQLException:"
                     + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
   }
}