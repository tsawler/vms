// ------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-03
//Revisions
//------------------------------------------------------------------------------
//$Log: NewsCategories.java,v $
//Revision 1.9.2.4  2005/01/11 17:07:32  tcs
//Fixed database interface logic
//
//Revision 1.9.2.3  2005/01/11 16:39:04  tcs
//Corrected deleteRecord method
//
//Revision 1.9.2.2  2005/01/11 14:52:09  tcs
//Added update name method
//
//Revision 1.9.2.1  2005/01/11 13:37:59  tcs
//Changed to implement databaseinterface
//
//Revision 1.9  2004/07/04 23:40:37  tcs
//Corrected typo in update sql
//
//Revision 1.8  2004/06/25 18:43:44  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.7  2004/06/24 17:58:09  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.6  2004/06/14 01:16:58  tcs
//Moved myDbUtil.fixSqlFieldValue to setter
//
//Revision 1.5  2004/06/13 17:03:33  tcs
//Corrected column name in changeActiveStatus()
//
//Revision 1.4  2004/06/13 16:44:05  tcs
//Added method to get all news categories
//
//Revision 1.3  2004/06/10 11:50:58  tcs
//Added method to get category name by id
//
//Revision 1.2 2004/06/03 13:00:56 tcs
//Formatted code
//
//Revision 1.1 2004/06/03 12:55:19 tcs
//Initial entry into CVS
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
 * Manipulates news_categories table in db
 * 
 * @author tsawler
 *  
 */
public class NewsCategories implements DatabaseInterface {

   private int news_categories_id = 0;
   private String news_categories_name = "";
   private String news_categories_active_yn = "";

   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;

   DBUtils myDbUtil = new DBUtils();
   private String sCustomWhere = "";
   private int iLimit = 0;
   private int iOffset = 0;

   public NewsCategories() {
      super();
   }

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateNewsCategories() throws SQLException, Exception {
      try {
         String update = "UPDATE news_categories SET "
               + "news_categories_name = '"
               + news_categories_name
               + "', "
               + "news_categories_active_yn = '"
               + news_categories_active_yn
               + "' "
               + "WHERE news_categories_id = '"
               + news_categories_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("NewsCategories:updateNewsCategories:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:updateNewsCategories:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }
   
   public void updateNewsCategoriesName() throws SQLException, Exception {
      try {
         String update = "UPDATE news_categories SET "
               + "news_categories_name = '"
               + news_categories_name
               + "' "
               + "WHERE news_categories_id = '"
               + news_categories_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("NewsCategories:updateNewsCategories:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:updateNewsCategories:Exception:"
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
   public void addNewsCategories() throws SQLException, Exception {
      try {
         String save = "INSERT INTO news_categories ("
               + "news_categories_name, "
               + "news_categories_active_yn) "
               + "VALUES("
               + "'"
               + news_categories_name
               + "', "
               + "'"
               + news_categories_active_yn
               + "')";

         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("NewsCategories:addNewsCategories:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:addNewsCategories:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns news category name for supplied news id
    * 
    * @return String
    * @throws Exception
    */
   public String getNewsCategoryById() throws SQLException, Exception {
      String theName = "";
      try {
         String query = "select news_categories_name from news_categories "
               + "where news_categories_id = '"
               + news_categories_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);

         if (rs.next()) {
            theName = rs.getString(1);
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("NewsCategories:getNewsCategoryById:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:getNewsCategoryById:Exception:"
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
      return theName;
   }

   /**
    * Returns news category record for supplied news id
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getNewsCategoryRecordById() throws SQLException,
         Exception {

      try {
         String query = "select * from news_categories where news_categories_id = '"
               + news_categories_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors
               .addError("NewsCategories:getNewsCategoryRecordById:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:getNewsCategoryRecordById:Exception:"
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
    * Returns all active news categories ordered by name
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllActiveNewsCategories() throws SQLException,
         Exception {
      try {
         String query = "select * from news_categories "
               + "where news_categories_active_yn = 'y' "
               + "order by news_categories_name";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors
               .addError("NewsCategories:getAllActiveNewsCategories:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:getAllActiveNewsCategories:Exception:"
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
    * Returns all active news categories ordered by name
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllNewsCategories() throws SQLException,
         Exception {
      try {
         String query = "select * from news_categories "
               + "order by news_categories_name";
         if (this.iLimit > 0)
            query += " limit " + iLimit;
         if (this.iOffset > 0)
            query += " offset " + iOffset;
         
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("NewsCategories:getAllNewsCategories:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:getAllNewsCategories:Exception:"
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
    * Returns name of a news category
    * 
    * @return String
    * @throws SQLException
    * @throws Exception
    */
   public String getNameForCategoryId() throws SQLException, Exception {
      String myName = "";
      try {
         String query = "select news_categories_name from news_categories "
               + "where news_categories_id = '"
               + news_categories_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);

         if (rs.next()) {
            myName = rs.getString(1);
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("NewsCategories:getNameForCategoryId:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:getNameForCategoryId:Exception:"
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
      return myName;
   }

   /**
    * Change active status of a news category
    * 
    * @throws Exception
    */
   public void changeActiveStatus() throws SQLException, Exception {
      try {
         String query = "update news_categories set news_categories_active_yn = '"
               + news_categories_active_yn
               + "' where news_categories_id = '"
               + news_categories_id
               + "'";
         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("NewsCategories:changeActiveStatus:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:changeActiveStatus:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Delete a news category by id
    * 
    * @throws Exception
    */
   public void deleteNewsCategory() throws SQLException, Exception {
      try {
         String query = "delete from news_categories where news_categories_id = '"
               + news_categories_id
               + "'";
         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("NewsCategories:deleteNewsCategory:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("NewsCategories:deleteNewsCategory:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#createCustomWhere(java.lang.String)
    */
   public void createCustomWhere(String psCustomWhere) {
      this.sCustomWhere = psCustomWhere;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#setLimit(int)
    */
   public void setLimit(int pLimit) {
      this.iLimit = pLimit;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#setOffset(int)
    */
   public void setOffset(int pOffset) {
      this.iOffset = pOffset;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
    */
   public void setPrimaryKey(String theKey) {
      this.setNews_categories_id(Integer.parseInt(theKey));

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#deleteRecord()
    */
   public void deleteRecord() throws SQLException, Exception {
      this.deleteNewsCategory();

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.String)
    */
   public void changeActiveStatus(String psStatus) throws SQLException,
         Exception {
      this.setNews_categories_active_yn(psStatus);
      this.changeActiveStatus();

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
    * @return Returns the news_categories_active_yn.
    */
   public String getNews_categories_active_yn() {
      return news_categories_active_yn;
   }

   /**
    * @param news_categories_active_yn
    *           The news_categories_active_yn to set.
    */
   public void setNews_categories_active_yn(String news_categories_active_yn) {
      this.news_categories_active_yn = news_categories_active_yn;
   }

   /**
    * @return Returns the news_categories_id.
    */
   public int getNews_categories_id() {
      return news_categories_id;
   }

   /**
    * @param news_categories_id
    *           The news_categories_id to set.
    */
   public void setNews_categories_id(int news_categories_id) {
      this.news_categories_id = news_categories_id;
   }

   /**
    * @return Returns the news_categories_name.
    */
   public String getNews_categories_name() {
      return news_categories_name;
   }

   /**
    * @param news_categories_name
    *           The news_categories_name to set.
    */
   public void setNews_categories_name(String news_categories_name) {
      this.news_categories_name = myDbUtil
            .fixSqlFieldValue(news_categories_name);
   }

   /**
    * @return Returns the sCustomWhere.
    */
   public String getSCustomWhere() {
      return sCustomWhere;
   }

   /**
    * @param customWhere
    *           The sCustomWhere to set.
    */
   public void setSCustomWhere(String customWhere) {
      sCustomWhere = customWhere;
   }

   /**
    * @return Returns the iLimit.
    */
   public int getILimit() {
      return iLimit;
   }

   /**
    * @param limit
    *           The iLimit to set.
    */
   public void setILimit(int limit) {
      iLimit = limit;
   }

   /**
    * @return Returns the iOffset.
    */
   public int getIOffset() {
      return iOffset;
   }

   /**
    * @param offset
    *           The iOffset to set.
    */
   public void setIOffset(int offset) {
      iOffset = offset;
   }
}