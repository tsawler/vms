//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-12-23
//Revisions
//------------------------------------------------------------------------------
//$Log: DoEditPackage.java,v $
//Revision 1.3.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.3  2004/06/26 17:03:25  tcs
//Changes due to refactoring
//
//Revision 1.2  2004/06/09 02:14:19  tcs
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

import com.verilion.display.html.SecurePage;
import com.verilion.database.CtPackages;
import com.verilion.database.DBUtils;
import com.verilion.object.Errors;

/**
 * Saves changes for a given package to database
 * 
 * <P>
 * December 23, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DoEditPackage extends SecurePage {

   private static final long serialVersionUID = -2110563140351583533L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      String ct_package_active_yn = "";
      int ct_package_id = 0;
      String ct_package_name = "";
      float ct_package_price = 0.00f;
      String ct_package_description = "";
      boolean deleteIt = false;

      try {
         DBUtils myDbUtil = new DBUtils();
         // get info from parameters
         ct_package_id = Integer.parseInt((String) request
               .getParameter("package_id"));
         ct_package_name = (String) request.getParameter("package_name");
         Float Fobj = Float.valueOf(((String) request
               .getParameter("package_price")));
         ct_package_price = Fobj.floatValue();
         ct_package_description = (String) request
               .getParameter("package_description");
         ct_package_description = myDbUtil
               .fixSqlFieldValue(ct_package_description);
         if ((String) request.getParameter("ct_package_active_yn") != null) {
            ct_package_active_yn = "y";
         } else {
            ct_package_active_yn = "n";
         }
         if ((String) request.getParameter("delete") != null) {
            deleteIt = true;
         }

         CtPackages myPackages = new CtPackages();
         myPackages.setConn(conn);

         if (deleteIt == false) {
            // we're just modifiying this package, so do so
            myPackages.setCt_package_id(ct_package_id);
            myPackages.setCt_package_name(ct_package_name);
            myPackages.setCt_package_price(ct_package_price);
            myPackages.setCt_package_description(ct_package_description);
            myPackages.setCt_package_active_yn(ct_package_active_yn);
            myPackages.updatePackages();

            // Generate completion message
            session.setAttribute("Error", "Package successfully modified.");
            response
                  .sendRedirect("/servlet/com.verilion.display.html.admin.EditPackages?page=EditPackages");
         } else {
            // delete this package
            myPackages.setCt_package_id(ct_package_id);
            myPackages.deletePackage();
         }

      } catch (SQLException e) {
         Errors
               .addError("com.verilion.display.html.admin.DoEditPackage:BuildPage:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("com.verilion.display.html.admin.DoEditPackage:BuildPage:SQLException:"
                     + e.toString());
      }
   }
}