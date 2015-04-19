//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-02
//Revisions
//------------------------------------------------------------------------------
//$Log: ClassObjects.java,v $
//Revision 1.2.2.3.4.1.6.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.2.2.3.4.1  2005/08/21 15:37:14  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.3  2005/02/24 19:47:27  tcs
//debugging info removed
//
//Revision 1.2.2.2  2005/02/24 13:11:38  tcs
//added method to get all classobjects as hm
//
//Revision 1.2.2.1  2004/12/14 17:02:30  tcs
//Added method to test for valid action
//
//Revision 1.2  2004/11/03 13:14:10  tcs
//Corrected column names in query
//
//Revision 1.1  2004/11/02 15:19:51  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.verilion.object.Errors;

/**
 * Manipulates class_objects table in db, and related operations.
 * <p>
 * November 2, 2004
 * <p>
 * 
 * @author tsawler
 * @since 1.2.10
 * 
 */
public class ClassObjects {

   public ClassObjects() {
      super();
   }

   private String class_object = "";
   private String class_object_name = "";
   private int class_object_id = 0;
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateClassObject() throws SQLException, Exception {
      try {
         String update = "UPDATE class_objects SET "
               + "class_object = '"
               + class_object
               + "', "
               + "class_object_name = '"
               + class_object_name
               + "' "
               + "WHERE class_object_id = '"
               + class_object_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("ClassObjects:updateClassObject:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("ClassObjects:updateClassObject:Exception:"
               + e.toString());
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
   public void addClassObject() throws SQLException, Exception {
      try {
         String save = "INSERT INTO class_objects ("
               + "class_object_name, "
               + "class_object) "
               + "VALUES("
               + "'"
               + class_object_name
               + "', "
               + "'"
               + class_object
               + "')";

         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("ClassObjects:addClassObject:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("ClassObjects:addClassObject:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Get class name for class_object_name
    * 
    * @return String
    * @throws Exception
    */
   public String getClassObjectClassByName() throws Exception {

      String className = "";
      ResultSet irs = null;

      try {
         String query = "select class_object_name from class_objects where class_object = ?";
         pst = conn.prepareStatement(query);
         pst.setString(1, class_object);
         irs = pst.executeQuery();
         if (irs.next()) {
            className = irs.getString(1);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (irs != null) {
            irs.close();
            irs = null;
         }
         if (st != null) {
            pst.close();
            pst = null;
         }
      }
      return className;
   }
   
   /**
    * Build hashmap of class objects for use in singleton class
    * 
    * @return hashmap
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
public HashMap getAllClassObjects() throws Exception {

      HashMap hm = new HashMap();
      ResultSet irs = null;

      try {
         String query = "select class_object_name, class_object from class_objects";
         pst = conn.prepareStatement(query);
         irs = pst.executeQuery();
         while (irs.next()) {
            hm.put(irs.getString("class_object"), irs.getString("class_object_name"));
         }
         irs.close();
         irs = null;
         pst.close();
         pst = null;
      } catch (Exception e) {
         //e.printStackTrace();
      } finally {
         if (irs != null) {
            irs.close();
            irs = null;
         }
         if (st != null) {
            pst.close();
            pst = null;
         }
      }
      return hm;
   }

   /**
    * Check for valid action
    * 
    * @return true if supplied action exists
    * @throws Exception
    */
   public boolean isValidAction() throws Exception {

      boolean isValidAction = false;
      ResultSet irs = null;

      try {
         String query = "select class_object_name from class_objects where class_object = ?";
         pst = conn.prepareStatement(query);
         pst.setString(1, class_object);
         irs = pst.executeQuery();
         if (irs.next()) {
            isValidAction = true;
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (irs != null) {
            irs.close();
            irs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return isValidAction;
   }

   /**
    * @return Returns the class_object.
    */
   public String getClass_object() {
      return class_object;
   }

   /**
    * @param class_object
    *           The class_object to set.
    */
   public void setClass_object(String class_object) {
      this.class_object = class_object;
   }

   /**
    * @return Returns the class_object_id.
    */
   public int getClass_object_id() {
      return class_object_id;
   }

   /**
    * @param class_object_id
    *           The class_object_id to set.
    */
   public void setClass_object_id(int class_object_id) {
      this.class_object_id = class_object_id;
   }

   /**
    * @return Returns the class_object_name.
    */
   public String getClass_object_name() {
      return class_object_name;
   }

   /**
    * @param class_object_name
    *           The class_object_name to set.
    */
   public void setClass_object_name(String class_object_name) {
      this.class_object_name = class_object_name;
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
}