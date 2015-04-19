//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: Images.java,v $
//Revision 1.4  2004/11/03 13:21:28  tcs
//Added explicit serialization
//
//Revision 1.3  2004/06/25 18:29:54  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:10  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:45  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates images table in db, and related operations.
 * <P>
 * Dec 02, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 *  
 */
public class Images extends HttpServlet {

   private static final long serialVersionUID = 2276193323812968867L;
   private int image_id = 0;
   private int image_data = 0;
   private int height = 0;
   private int width = 0;
   private String file_type = "";
   private int customer_id = 0;
   private String caption = "";
   private int thumbnail_id = 0;
   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;

   public Images() {
      super();
   }

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateImages() throws SQLException, Exception {
      try {
         String update = "UPDATE images SET "
               + "image_data = '"
               + image_data
               + "', "
               + "height = '"
               + height
               + "', "
               + "width = '"
               + width
               + "', "
               + "file_type = '"
               + file_type
               + "', "
               + "customer_id = '"
               + customer_id
               + "', "
               + "image_id = '"
               + image_id
               + "' "
               + "where image_id = '"
               + image_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Images:updateImages:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:updateImages:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateImagesById() throws SQLException, Exception {
      try {
         String update = "UPDATE images SET "
               + "image_data = '"
               + image_data
               + "', "
               + "height = '"
               + height
               + "', "
               + "width = '"
               + width
               + "', "
               + "file_type = '"
               + file_type
               + "', "
               + "customer_id = '"
               + customer_id
               + "', "
               + "caption = '"
               + caption
               + "' "
               + "where image_id = '"
               + image_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("Images:updateImagesById:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:updateImagesById:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Insert method.
    * 
    * @throws Exception
    */
   public void addImage() throws SQLException, Exception {
      try {
         String save = "INSERT INTO images ("
               + "image_data, "
               + "height, "
               + "width, "
               + "file_type, "
               + "customer_id, "
               + "caption, "
               + "image_id) "
               + "VALUES("
               + "'"
               + image_data
               + "', '"
               + height
               + "', "
               + "'"
               + width
               + ", "
               + "'"
               + file_type
               + "', "
               + "'"
               + customer_id
               + "', "
               + "'"
               + caption
               + "', "
               + "'"
               + image_id
               + "')";

         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Images:addImage:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:addImage:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns height and width for supplied image id
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getHeightWidth() throws SQLException, Exception {
      try {
         String getImageHeightWidth = "select height, width from images "
               + "where image_id = '"
               + image_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(getImageHeightWidth);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Images:getHeightWidth:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:getHeightWidth:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns caption for supplied image id
    * 
    * @return String
    * @throws Exception
    */
   public String getImageCaption() throws SQLException, Exception {
      String theCaption = "";
      try {

         String getcaption = "select caption from images "
               + "where image_id = '"
               + image_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(getcaption);
         if (rs.next()) {
            theCaption = rs.getString("caption");
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Images:getImageCaption:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:getImageCaption:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return theCaption;
   }

   /**
    * Returns height and width for supplied image oid
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getHeightWidthById() throws SQLException,
         Exception {
      try {
         String getImageHeightWidth = "select height, width from images "
               + "where image_id = '"
               + image_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(getImageHeightWidth);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Images:getHeightWidthOid:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:getHeightWidthOid:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Gets image by customer id
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getImageIdByCustomerId() throws SQLException,
         Exception {
      try {
         String query = "select image_id from images "
               + "where customer_id = '"
               + customer_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Images:getImageOidByCustomerId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:getImageOidByCustomerId:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Gets image by image id
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getImageByImageId() throws SQLException,
         Exception {
      try {
         String query = "select image_id from images "
               + "where image_id = '"
               + image_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Images:getImageOidByImageId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:getImageOidByCustomerId:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return crs;
   }

   /**
    * Returns thumbnail id for image id, or 0 if none found.
    * 
    * @return int
    * @throws SQLException
    * @throws Exception
    */
   public int getThumbnailIdForImageId() throws SQLException, Exception {
      int myId = 0;
      try {
         String query = "select thumbnail_id from images "
               + "where image_id = '"
               + image_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         if (rs.next()) {
            myId = rs.getInt(1);
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Images:getImageOidByImageId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:getImageOidByCustomerId:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return myId;
   }

   /**
    * Updates thumbnail id for provided image id
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void updateThumbnailIdForImageId() throws SQLException, Exception {

      try {
         String query = "update images set "
               + "thumbnail_id = '"
               + thumbnail_id
               + "' "
               + "where image_id = '"
               + image_id
               + "'";
         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Images:getImageOidByImageId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Images:getImageOidByCustomerId:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * @return Returns the caption.
    */
   public String getCaption() {
      return caption;
   }

   /**
    * @param caption
    *           The caption to set.
    */
   public void setCaption(String caption) {
      this.caption = caption;
   }

   /**
    * @return Returns the conn.
    */
   public Connection getConn() {
      return conn;
   }

   /**
    * @param conn
    *           The conn to set.
    */
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   /**
    * @return Returns the customer_id.
    */
   public int getCustomer_id() {
      return customer_id;
   }

   /**
    * @param customer_id
    *           The customer_id to set.
    */
   public void setCustomer_id(int customer_id) {
      this.customer_id = customer_id;
   }

   /**
    * @return Returns the file_type.
    */
   public String getFile_type() {
      return file_type;
   }

   /**
    * @param file_type
    *           The file_type to set.
    */
   public void setFile_type(String file_type) {
      this.file_type = file_type;
   }

   /**
    * @return Returns the height.
    */
   public int getHeight() {
      return height;
   }

   /**
    * @param height
    *           The height to set.
    */
   public void setHeight(int height) {
      this.height = height;
   }

   /**
    * @return Returns the image_data.
    */
   public int getImage_data() {
      return image_data;
   }

   /**
    * @param image_data
    *           The image_data to set.
    */
   public void setImage_data(int image_data) {
      this.image_data = image_data;
   }

   /**
    * @return Returns the image_id.
    */
   public int getImage_id() {
      return image_id;
   }

   /**
    * @param image_id
    *           The image_id to set.
    */
   public void setImage_id(int image_id) {
      this.image_id = image_id;
   }

   /**
    * @return Returns the thumbnail_id.
    */
   public int getThumbnail_id() {
      return thumbnail_id;
   }

   /**
    * @param thumbnail_id
    *           The thumbnail_id to set.
    */
   public void setThumbnail_id(int thumbnail_id) {
      this.thumbnail_id = thumbnail_id;
   }

   /**
    * @return Returns the width.
    */
   public int getWidth() {
      return width;
   }

   /**
    * @param width
    *           The width to set.
    */
   public void setWidth(int width) {
      this.width = width;
   }
}