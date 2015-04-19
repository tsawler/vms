//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: ShowImage.java,v $
//Revision 1.2.2.2.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.2  2005/03/08 16:39:09  tcs
//Removed singlethreadmodel interface
//
//Revision 1.2.2.1  2004/12/14 14:39:49  tcs
//changed to use preparedstatement
//
//Revision 1.2  2004/06/24 17:58:17  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.verilion.database.DbBean;

/**
 * Gets images from blob fields in db, and sends to browser with correct mime
 * type.
 * <P>
 * Jul 01, 2003
 * <P>
 * com.verilion.images
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ShowImage extends HttpServlet {

   private static final long serialVersionUID = 3258133552829837618L;
   private int theId = 0;
   private ResultSet rs = null;
   private Connection conn = null;
   PreparedStatement pst = null;

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      String sqlQuery = "";
      String mime_type = "";
      theId = Integer.parseInt((String) request.getParameter("id"));

      sqlQuery = "select image_data, file_type from images where image_id = ?";

      // Get a connection
      try {
         conn = DbBean.getDbConnection();
      } catch (Exception e) {
         System.err
               .println("com.verilion.images.ShowImage:DbBean:getDbConnection:Exception "
                     + e.getMessage());
      }

      // get image data and write it to the browser
      try {
         pst = conn.prepareStatement(sqlQuery);
         pst.setInt(1, theId);
         rs = pst.executeQuery();
         if (rs.next()) {
            mime_type = rs.getString("file_type");
            byte[] theImage = rs.getBytes(1);
            response.setContentType(mime_type);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            ServletOutputStream out = response.getOutputStream();
            out.write(theImage);
            out.close();
         } else {
            // there is no image, so show the "no image" image.
            sqlQuery = "select image_data, file_type from images where customer_id = 0";
            rs = pst.executeQuery();
            if (rs.next()) {
               mime_type = rs.getString("file_type");
               byte[] theImage = rs.getBytes(1);
               response.setContentType(mime_type);
               response.setHeader("Cache-Control", "no-cache"); // HTTP
               response.setHeader("Pragma", "no-cache"); // HTTP 1.0
               response.setDateHeader("Expires", 0);
               ServletOutputStream out = response.getOutputStream();
               out.write(theImage);
               out.close();
            }

         }
         rs.close();
         rs = null;
         pst.close();
         pst = null;
         try {
            DbBean.closeDbConnection(conn);
         } catch (Exception e1) {

         }
      } catch (SQLException e) {
         throw new ServletException("ImageServlet:SQLException");
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e1) {

            }
            rs = null;
         }
         if (pst != null) {
            try {
               pst.close();
            } catch (SQLException e1) {
               e1.printStackTrace();
            }
            pst = null;
         }
      }
   }
}