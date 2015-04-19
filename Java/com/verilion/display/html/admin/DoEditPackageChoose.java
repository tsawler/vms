//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-11
//Revisions
//------------------------------------------------------------------------------
//$Log: DoEditPackageChoose.java,v $
//Revision 1.4.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4  2004/06/26 17:03:24  tcs
//Changes due to refactoring
//
//Revision 1.3  2004/06/26 03:15:12  tcs
//Corrected redirect
//
//Revision 1.2  2004/06/14 17:39:34  tcs
//Changed redirect from lastPage sessional var
//
//Revision 1.1  2004/06/11 11:53:31  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.display.html.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.database.CtPackages;
import com.verilion.display.html.SecurePage;
import com.verilion.object.Errors;

/**
 * Manage package items
 * 
 * June 11, 2004
 * 
 * @author tsawler
 * 
 */
public class DoEditPackageChoose extends SecurePage {

   private static final long serialVersionUID = 3480069747920759110L;

   public void BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session) throws Exception {

      // initialize variables
      int packageCount = 0;
      int action = 0;
      int temp_id = 0;

      try {
         // get our parameters
         action = Integer.parseInt((String) request.getParameter("action"));
         packageCount = Integer.parseInt((String) request
               .getParameter("packageCount"));

         // set attributes for Package Item
         CtPackages myPackage = new CtPackages();
         myPackage.setConn(conn);

         // now perform the appropriate action on the selected rows
         // 1 = publish
         // 2 = unpublish
         // 3 = delete

         switch (action) {
         case 1:
            // want to publish all selected rows
            for (int i = 1; i <= packageCount; i++) {
               if (request.getParameter("action_" + i) != null) {
                  temp_id = Integer.parseInt((String) request
                        .getParameter("package_id_" + i));
                  myPackage.setCt_package_id(temp_id);
                  myPackage.setCt_package_active_yn("y");
                  myPackage.changeActiveStatus();
               }
            }
            break;

         case 2:
            // want to unpublish all selected rows
            for (int i = 1; i <= packageCount; i++) {
               if (request.getParameter("action_" + i) != null) {
                  temp_id = Integer.parseInt((String) request
                        .getParameter("package_id_" + i));
                  myPackage.setCt_package_id(temp_id);
                  myPackage.setCt_package_active_yn("n");
                  myPackage.changeActiveStatus();
               }
            }
            break;

         case 3:
            // want to delete all selected rows
            for (int i = 1; i <= packageCount; i++) {
               if (request.getParameter("action_" + i) != null) {
                  temp_id = Integer.parseInt((String) request
                        .getParameter("package_id_" + i));
                  myPackage.setCt_package_id(temp_id);
                  myPackage.deletePackage();
               }
            }
            break;
         }

         response.sendRedirect("/servlet/com.verilion.display.html."
               + "admin.EditPackages?page=EditPackages");

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("DoEditPackageChoose:BuildPage:Exception:"
               + e.toString());
      }
   }
}