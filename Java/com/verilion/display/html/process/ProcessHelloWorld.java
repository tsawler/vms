//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-11
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessHelloWorld.java,v $
//Revision 1.1.2.1.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.1  2005/02/24 19:48:11  tcs
//Just for testing/dev. Remove me someday
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Produces RSS news feed
 * <P>
 * July 11, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessHelloWorld extends HttpServlet {

   private static final long serialVersionUID = -2051608489922775060L;

   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      this.doPost(request, response);
   }

   /*
    * (non-Javadoc)
    * 
    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      PrintWriter out;
      // response.setContentType("application/xml");
      out = response.getWriter();
      try {
         DisplayResult(out, request, response);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
      out.close();
   }

   public PrintWriter DisplayResult(
         PrintWriter out,
         HttpServletRequest request,
         HttpServletResponse response) throws Exception {
      PrintWriter PWtemp;
      PWtemp = out;

      String theOutput = "<div><strong>Hello, world</strong></div>";
      out.print(theOutput);
      return PWtemp;
   }
}