//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-21
//Revisions
//------------------------------------------------------------------------------
//$Log: ShowFileBytea.java,v $
//Revision 1.2.2.2.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.2  2005/03/08 16:39:09  tcs
//Removed singlethreadmodel interface
//
//Revision 1.2.2.1  2004/12/14 14:36:53  tcs
//added pst.setInt()
//
//Revision 1.2  2004/07/21 18:47:44  tcs
//Removed auto mime-type so that IE doesn't croak
//
//Revision 1.1  2004/07/21 16:35:02  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.verilion.database.DbBean;

/**
 * Gets file from db, and sends to browser with correct mime type, from pgsql
 * back end with data stored in bytea column.
 * <P>
 * July 21, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ShowFileBytea extends HttpServlet {

   private static final long serialVersionUID = 3906646392938968115L;
   private int theId = 0;
   private ResultSet rs = null;
   private Connection conn = null;
   Statement st = null;

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      String sqlQuery = "";
      theId = Integer.parseInt((String) request.getParameter("id"));

      // get file data and write it to the browser
      try {
         conn = DbBean.getDbConnection();

         sqlQuery = "select document_data, document_name, document_mime_type "
               + "from documents "
               + "where document_id = ?";
         PreparedStatement pst = conn.prepareStatement(sqlQuery);
         pst.setInt(1, theId);
         rs = pst.executeQuery();

         if (rs != null) {
            while (rs.next()) {
               byte[] theFileData = rs.getBytes(1);
               response.setHeader("Content-Disposition", "attachment;"
                     + " filename="
                     + rs.getString("document_name"));
               response.setContentType("application/x-octet-stream");
               ServletOutputStream out = response.getOutputStream();
               out.write(theFileData);
               out.close();
            }
         }
         rs.close();
         rs = null;
         pst.close();
         pst = null;
         DbBean.closeDbConnection(conn);

      } catch (SQLException e) {
         throw new ServletException("ShowFileBytea:SQLException");
      } catch (Exception e) {
         System.out.println("Error in show image" + e.toString());
         e.printStackTrace();
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e1) {

            }
            rs = null;
         }
      }
   }
}