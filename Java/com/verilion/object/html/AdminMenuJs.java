//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-01
//Revisions
//------------------------------------------------------------------------------
//$Log: AdminMenuJs.java,v $
//Revision 1.1.2.1  2004/12/14 14:34:41  tcs
//Cleaned up imports
//
//Revision 1.1  2004/10/01 18:42:11  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.verilion.database.AdminMenu;
import com.verilion.database.DbBean;

/**
 * Generates conf file for admin tree menu
 * <P>
 * October 1, 2004
 * <P>
 * @author tsawler
 *  
 */
public class AdminMenuJs extends HttpServlet {

   private static final long serialVersionUID = 3257284729769375025L;
   private String theJs = "";
   private Connection conn = null;

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      // Get a connection
      try {
         conn = DbBean.getDbConnection();
      } catch (Exception e) {
         System.err
               .println("AdminMenuJs:DbBean:getDbConnection:Exception "
                     + e.getMessage());
      }

      // get string and write it to the browser
      try {
         AdminMenu myMenu = new AdminMenu();
         myMenu.setConn(conn);

         theJs = myMenu.generateMenuJavaScript();
         response.setContentType("text/plain");
         PrintWriter out;
         out = response.getWriter();
         out.write(theJs);
         out.close();
         conn.close();
         conn = null;
      } catch (Exception e) {
         throw new ServletException("AdminMenuJs:Exception");
      } finally {
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException e1) {
               e1.printStackTrace();
            }
            conn = null;

         }
      }
   }
}