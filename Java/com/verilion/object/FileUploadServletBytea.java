//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: FileUploadServletBytea.java,v $
//Revision 1.4.2.4.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.4.2.4  2005/05/06 11:55:16  tcs
//*** empty log message ***
//
//Revision 1.4.2.3  2005/05/04 13:56:52  tcs
//Working on new image manipulation (commented out right now)
//
//Revision 1.4.2.2  2004/12/16 17:36:03  tcs
//Adding height/width to entry; checked for attempt to close nonexistent rs
//
//Revision 1.4.2.1  2004/12/15 13:09:18  tcs
//Corrected logic for deciding if an upload was insert or update
//
//Revision 1.4  2004/11/04 16:39:45  tcs
//Added explicit serialization UID
//
//Revision 1.3  2004/07/21 16:34:35  tcs
//Changed to get thePath from singleton
//
//Revision 1.2  2004/06/24 17:58:16  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.DBUtils;
import com.verilion.database.DbBean;
import com.verilion.database.Images;
import com.verilion.database.SingletonObjects;

/**
 * Handles uploading of files via http upload for postgres back end, using bytea
 * columns to store data.
 * <P>
 * Jan 21 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class FileUploadServletBytea extends HttpServlet {

   private static final long serialVersionUID = -372805989305660804L;
   public int image_id = 0;
   public int thumbnail_id = 0;
   public int customer_id = 0;
   public ResultSet rs = null;
   public String sqlQuery = "";
   public String type = "";
   public String filename = "";
   public PreparedStatement pst = null;
   public String thePath = "";
   public String caption = "";
   public boolean okay = true;
   public boolean isNewImage = true;

   public Connection conn;

   public void init(ServletConfig config) {

   }

   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      HttpSession session = request.getSession(true);
      thePath = SingletonObjects.getInstance().getUpload_path();

      try {
         Images myImage = new Images();
         DBUtils myDbUtil = new DBUtils();

         MultipartRequest multi = new MultipartRequest(request, thePath,
               50 * 1024);

         Enumeration files = multi.getFileNames();

         while (files.hasMoreElements()) {
            String name = (String) files.nextElement();
            filename = multi.getFilesystemName(name);
            type = multi.getContentType(name);
         }

         // Get a connection
         try {
            conn = DbBean.getDbConnection();
         } catch (Exception e) {
            System.err.println("DbBean:getDbConnection:Exception "
                  + e.getMessage());
         }

         try {
            // Check for an id being passed as a paramter. If we have one,
            // get it and store it in theId
            if (multi.getParameter("id") != null) {
               image_id = Integer.parseInt((String) multi.getParameter("id"));
               isNewImage = false;
            }

            // Check for a customer id being passed as a parameter. If
            // we have one, get it and store it in customer_id. We use
            // customer_id to check for one customer attempting to
            // change an image that does not belong to them.
            if (multi.getParameter("customer_id") != null) {
               customer_id = Integer.parseInt((String) multi
                     .getParameter("customer_id"));
            }

            // Check for a customer id in session. If
            // we have one, get it and store it in customer_id. We use
            // customer_id to check for one customer attempting to
            // change an image that does not belong to them.
            if (session.getAttribute("customer_id") != null) {
               customer_id = Integer.parseInt((String) session
                     .getAttribute("customer_id"));
            }

            // Check for a caption value
            if (multi.getParameter("caption") != null) {
               caption = (String) multi.getParameter("caption");
               caption = myDbUtil.fixSqlFieldValue(caption);
            }

            if (type == null) {
               // we didn't get a file uploaded, so send them
               // back with an error message
               session.setAttribute(
                     "Error",
                     "You must specify an image to upload!");
               response.sendRedirect(response
                     .encodeRedirectURL((String) session
                           .getAttribute("lastPage")));
               okay = false;
               return;
            } else if (!(type.equals("image/jpeg"))
                  && !(type.equals("image/gif"))
                  && !(type.equals("image/png"))) {
               // if we are not getting a gif, jpeg, or png, generate an
               // error and cancel the database write
               session.setAttribute(
                     "Error",
                     "Image must be in gif, jpeg, or png format!");
               okay = false;
               response.sendRedirect(response
                     .encodeRedirectURL((String) session
                           .getAttribute("lastPage")));
               return;
            }

            if (okay) {
               // decide if we are putting a new image into the table,
               // or updating an existing one. If we have an image id,
               // then we are updating. Build our sql query appropriately.
               if (image_id < 1) {
                  isNewImage = true;
                  sqlQuery = "insert into images "
                        + "(file_type, "
                        + "customer_id, "
                        + "image_data, "
                        + "height, "
                        + "width, "
                        + "caption) "
                        + "values "
                        + "('"
                        + type
                        + "', "
                        + "'"
                        + customer_id
                        + "',"
                        + "?, ?, ?, '"
                        + caption
                        + "')";
               } else {
                  isNewImage = false;
                  sqlQuery = "update images set "
                        + "file_type = '"
                        + type
                        + "', "
                        + "caption = '"
                        + caption
                        + "', "
                        + "image_data = ?, "
                        + "height =  ?, "
                        + "width = ? "
                        + "where image_id = '"
                        + image_id
                        + "'";
               }

               // now open the file, and get a fileinputstream from it
               File theFile = new File(thePath + "/" + filename);
               FileInputStream fis = new FileInputStream(theFile);
               
               // prepare our statement, and put the contents of the file
               // into the query statement
               pst = conn.prepareStatement(sqlQuery);
               pst.setBinaryStream(1, fis, (int) theFile.length());
               
               // create an image from file to get height/width
               ImageIO.setUseCache(false);
               BufferedImage image = ImageIO.read(theFile);
               
               pst.setInt(2, image.getHeight());
               pst.setInt(3, image.getWidth());
               
               // write the file to the database
               pst.executeUpdate();

               // if this is a first time insertion of the image, we need
               // to get back the id so we can do any client specific
               // processing in DoExtra(), below. Otherwise, we already
               // have the image id.
               if (isNewImage) {
                  String getLast = "select currval('images_image_id_seq')";
                  Statement st = conn.createStatement();
                  rs = st.executeQuery(getLast);
                  if (rs.next()) {
                     image_id = rs.getInt(1);
                  }
               }

               // now we need to insert the thumbnail of the image
               
               // get original height/width
               int h = image.getHeight();
               int w = image.getWidth();

               // only want to create a thumbnail if w > 75 px
               if (w > 75) {
                  //GenerateImageThumbnail theTnImage = new GenerateImageThumbnail(theFile, 75, 75);
                  //File tnFile = theTnImage.process();
                  //System.out.println("tnFile is " + tnFile.getAbsolutePath());
                  // our ration is original 75/original width
                  double ratio = (double) 75 / (double) w;
                  AffineTransform at = AffineTransform.getScaleInstance(
                        ratio,
                        ratio);
                  AffineTransformOp op = new AffineTransformOp(at,
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                  BufferedImage scaledImage = op.filter(image, null);
                  File out = new File(thePath + "/tn_" + filename);
                  ImageIO.write(scaledImage, "jpg", out);

                  // now add the thumbnail to the image table
                  // open the thumbnail file, and get a fileinputstream
                  File theThumbnailFile = new File(thePath + "/tn_" + filename);
                  FileInputStream tfis = new FileInputStream(theThumbnailFile);

                  ImageIO.setUseCache(false);
                  BufferedImage tnImage = ImageIO.read(theThumbnailFile);
                  h = tnImage.getHeight();
                  w = tnImage.getWidth();
                  
                  // prepare our statement, and put the contents of the
                  // file into the query statement

                  myImage.setConn(conn);
                  myImage.setImage_id(image_id);
                  myImage.setCustomer_id(customer_id);
                  thumbnail_id = myImage.getThumbnailIdForImageId();

                  if (thumbnail_id < 1) {
                     sqlQuery = "insert into images "
                           + "(file_type, "
                           + "customer_id, "
                           + "image_data, "
                           + "height, "
                           + "width, "
                           + "caption) "
                           + "values "
                           + "('"
                           + type
                           + "', "
                           + "'"
                           + customer_id
                           + "',"
                           + "?, "
                           + h
                           + ", "
                           + w
                           + ", '"
                           + caption
                           + "')";

                  } else {
                     sqlQuery = "update images set "
                           + "file_type = '"
                           + type
                           + "', "
                           + "caption = '"
                           + caption
                           + "', "
                           + "height = "
                           + h
                           + ", "
                           + "width = "
                           + w
                           + ", "
                           + "image_data = ? "
                           + "where image_id = '"
                           + thumbnail_id
                           + "'";
                  }

                  pst = conn.prepareStatement(sqlQuery);
                  pst.setBinaryStream(1, tfis, (int) theThumbnailFile.length());

                  // write the file to the database
                  pst.executeUpdate();

                  // we need to get the image id for
                  // the thumbnail of this thumbnail if it's a new one
                  if (thumbnail_id < 1) {
                     String getLast = "select currval('images_image_id_seq')";
                     Statement st = conn.createStatement();
                     rs = st.executeQuery(getLast);
                     if (rs.next()) {
                        thumbnail_id = rs.getInt(1);
                     }
                  }

                  // now update the parent record with the thumbnail id
                  myImage.setImage_id(image_id);
                  myImage.setThumbnail_id(thumbnail_id);
                  myImage.updateThumbnailIdForImageId();

                  // delete the file off of the filesystem
                  theThumbnailFile.delete();

                  // close the file inputstream
                  tfis.close();
               }

               // delete the file off of the filesystem
               theFile.delete();

               // close the file input stream
               fis.close();

               // do any client specific stuff by extending this class
               // and overriding DoExtra()
               DoExtra(request, response, session, customer_id, image_id, multi);

               if (rs != null) {
                  rs.close();
                  rs = null;
               }
               pst.close();
               pst = null;
               DbBean.closeDbConnection(conn);
            } else {
               session.setAttribute(
                     "Error",
                     "An unexpected error has occurred. This has been logged, "
                           + "and the system administrator has been notified.");
               response.sendRedirect(response
                     .encodeRedirectURL((String) session
                           .getAttribute("lastPage")));
            }
         } catch (java.io.FileNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
         } catch (SQLException ex) {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            if (rs != null) {
               rs.close();
               rs = null;
            }
            if (pst != null) {
               pst.close();
               pst = null;
            }
         }

      } catch (java.io.IOException e) {
         e.printStackTrace();
         session.setAttribute(
               "Error",
               "File is too large! Maximum 50K file size");
         response.sendRedirect(response.encodeRedirectURL((String) session
               .getAttribute("lastPage")));
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e.toString());
      }
   }

   /**
    * Extend this class and override this method to do client specific stuff
    * 
    * @param request
    * @param response
    * @param inCustId
    * @param multi
    * @throws Exception
    */
   public void DoExtra(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         int inCustId,
         int image_id,
         MultipartRequest multi) throws Exception {

      try {

      } catch (Exception e) {

      }

   }
}