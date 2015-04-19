//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-08
//Revisions
//------------------------------------------------------------------------------
//$Log: FaqCategory.java,v $
//Revision 1.1.2.1  2007/02/08 19:27:34  tcs
//Initial entry
//
//Revision 1.1.2.1  2007/02/08 15:50:13  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.verilion.object.Errors;

/**
 * Database routines for faq category related activities.
 * 
 * @author tsawler
 * 
 */
public class FaqCategory implements DatabaseInterface {

   private Connection conn = null;
   DBUtils myDbUtil = new DBUtils();
   private PreparedStatement pst = null;
   private String sCustomWhere = "";
   private int faq_categories_id = 0;
   private int parent_id = 0;
   private String faq_categories_name = "";
   private String active_yn = "n";


   
   public void changeFaqStatus() throws SQLException, Exception {

      try {
         StringBuffer saveBuf = new StringBuffer("");

            saveBuf = new StringBuffer("update faq_categories ");
            saveBuf
                  .append("set active_yn = ? where faq_categories_id = ?");

            pst = conn.prepareStatement(saveBuf.toString());
            pst.setString(1, active_yn);
            pst.setInt(2, faq_categories_id);

            pst.executeUpdate();
            pst.close();
            pst = null;

      } catch (SQLException e) {
         Errors.addError("FaqCategory:changeFaqStatus:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("FaqCategory:changeFaqStatus:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }
   
   public void deleteFaq() throws SQLException, Exception {

      try {
         StringBuffer saveBuf = new StringBuffer("");

            saveBuf = new StringBuffer("delete from faq_categories where faq_categories_id = ?");

            pst = conn.prepareStatement(saveBuf.toString());
            pst.setInt(1, faq_categories_id);

            pst.executeUpdate();
            pst.close();
            pst = null;

      } catch (SQLException e) {
         Errors.addError("FaqCategory:deleteFaq:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("FaqCategory:deleteFaq:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public Connection getConn() {
      return conn;
   }

   public String getSCustomWhere() {
      return sCustomWhere;
   }

   public void setSCustomWhere(String customWhere) {
      sCustomWhere = customWhere;
   }
   
   public void createCustomWhere(String customWhere) {
      this.setSCustomWhere(customWhere);
   }


   public int getFaq_categories_id() {
      return faq_categories_id;
   }

   public void setFaq_categories_id(int faq_categories_id) {
      this.faq_categories_id = faq_categories_id;
   }

   public String getFaq_categories_name() {
      return faq_categories_name;
   }

   public void setFaq_categories_name(String faq_categories_name) {
      this.faq_categories_name = faq_categories_name;
   }


   public int getParent_id() {
      return parent_id;
   }

   public void setParent_id(int parent_id) {
      this.parent_id = parent_id;
   }

   public String getActive_yn() {
      return active_yn;
   }

   public void setActive_yn(String active_yn) {
      this.active_yn = active_yn;
   }

   public void changeActiveStatus(String psStatus) throws SQLException, Exception {
      this.setActive_yn(psStatus);
      this.changeFaqStatus();
   }

   public void deleteRecord() throws SQLException, Exception {
      this.deleteFaq();
   }

   public void setPrimaryKey(String theKey) {
      this.setFaq_categories_id(Integer.parseInt(theKey));
   }

}