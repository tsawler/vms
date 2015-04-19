//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-11-29
//Revisions
//------------------------------------------------------------------------------
//$Log: ItemCategory.java,v $
//Revision 1.1.2.1  2005/11/30 19:47:37  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates item_category in db
 * <P>
 * November 29 2005
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ItemCategory {

   private int item_category_id = 0;
   private int item_id = 0;
   private int category_id = 0;
   private String customWhere = "";
   private String customOrder = "";
   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private Statement st = null;
   private PreparedStatement pst = null;
   DBUtils myDbUtils = new DBUtils();

   /**
    * Update method.
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void UpdateItemCategory() throws SQLException, Exception {
      try {
         String Update = "UPDATE item_catgory SET "
               + "item_id = '"
               + item_id
               + "', "
               + "category_id = '"
               + category_id
               + "' "
               + "WHERE item_category_id = '"
               + item_category_id
               + "'";

         pst = conn.prepareStatement(Update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         e.printStackTrace();
         Errors.addError("ItemCategory:UpdateItemCategory:SQLException:"
               + e.toString());
      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ItemCategory:UpdateItemCategory:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Add method
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void AddItemCategory() throws SQLException, Exception {
      try {
         String save = "INSERT INTO item_category ("
               + "item_id, "
               + "category_id) "
               + "VALUES("
               + "'"
               + item_id
               + "', "
               + "'"
               + category_id
               + "')";
         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("ItemCategory:AddItemCategory:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("ItemCategory:AddItemCategory:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Gets all active items for category_id
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet GetAllItemsForCategory() throws SQLException,
         Exception {
      try {
         String query = "select ic.*, i.item_name from item_category ic "
               + "left join item i on (i.item_id = ic.item_id) "
               + "where ic.category_id = '"
               + category_id
               + "' order by item_name";

         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("ItemCategory:GetAllItemsForCategory:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("ItemCategory:GetAllItemsForCategory:Exception:"
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
    * Custom query of this table
    * 
    * @return drs
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet GetRows() throws SQLException, Exception {
      try {
         String query = "Select * from item_category where "
               + customWhere
               + customOrder;

         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("ItemCategory:GetRows:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("ItemCategory:GetRows:Exception:" + e.toString());
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
    * Deletes item attribute by id
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void DeleteItemCategory() throws SQLException, Exception {
      try {
         String query = "delete from item_category where item_category_id = '"
               + item_category_id
               + "'";

         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("ItemCategory:DeleteItemCategory:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("ItemCategory:DeleteItemCategory:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }

   }

   /**
    * @return Returns the category_id.
    */
   public int getCategory_id() {
      return category_id;
   }

   /**
    * @param category_id
    *           The category_id to set.
    */
   public void setCategory_id(int category_id) {
      this.category_id = category_id;
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
    * @return Returns the customOrder.
    */
   public String getCustomOrder() {
      return customOrder;
   }

   /**
    * @param customOrder
    *           The customOrder to set.
    */
   public void setCustomOrder(String customOrder) {
      this.customOrder = customOrder;
   }

   /**
    * @return Returns the customWhere.
    */
   public String getCustomWhere() {
      return customWhere;
   }

   /**
    * @param customWhere
    *           The customWhere to set.
    */
   public void setCustomWhere(String customWhere) {
      this.customWhere = customWhere;
   }

   /**
    * @return Returns the item_category_id.
    */
   public int getItem_category_id() {
      return item_category_id;
   }

   /**
    * @param item_category_id
    *           The item_category_id to set.
    */
   public void setItem_category_id(int item_category_id) {
      this.item_category_id = item_category_id;
   }

   /**
    * @return Returns the item_id.
    */
   public int getItem_id() {
      return item_id;
   }

   /**
    * @param item_id
    *           The item_id to set.
    */
   public void setItem_id(int item_id) {
      this.item_id = item_id;
   }

}