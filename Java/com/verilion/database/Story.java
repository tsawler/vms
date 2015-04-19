// ------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-11
//Revisions
//------------------------------------------------------------------------------
//$Log: Story.java,v $
//Revision 1.1.2.1.2.4.2.1  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.1.2.1.2.4  2007/01/26 19:05:02  tcs
//Added a method
//
//Revision 1.1.2.1.2.3  2007/01/25 01:18:37  tcs
//Removed debugging info
//
//Revision 1.1.2.1.2.2  2007/01/22 19:30:58  tcs
//Removed debugging info
//
//Revision 1.1.2.1.2.1  2007/01/22 19:17:19  tcs
//Couple of new methods
//
//Revision 1.1.2.1  2007/01/12 19:29:30  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates news table in db
 * 
 * @author tsawler
 *  
 */
public class Story implements DatabaseInterface {

   private int story_id = 0;
   private String story_title = "";
   private String story_text = "";
   private String story_active_yn = "";
   private String story_author = "";
   private int story_author_id = 0;
   private int story_topic_id = 0;
   private int iOffset = 0;
   private int iLimit = 0;
   private String sCustomWhere = "";
   private String browser_title = "";
   private String meta_tags = "";

   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;

   DBUtils myDbUtil = new DBUtils();

   public Story() {
      super();
   }

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateStory() throws SQLException, Exception {
      try {
         String update = "UPDATE pmc_stories SET "
               + "title = '"
               + myDbUtil.fixSqlFieldValue(story_title)
               + "', "
               + "story = '"
               + myDbUtil.fixSqlFieldValue(story_text)
               + "', "
               + "story_topic_id = '"
               + story_topic_id
               + "', "
               + "browser_title = '"
               + browser_title.replaceAll("'", "''")
               + "', "
               + "meta_tags = '"
               + meta_tags.replaceAll("'", "''")
               + "', "
               + "story_active_yn = '"
               + story_active_yn
               + "' "
               + "WHERE story_id = '"
               + story_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Story:updateStory:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:updateStory:Exception:" + e.toString());
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
   public int addStory() throws SQLException, Exception {

      int new_story_id = 0;

      try {
         String save = "INSERT INTO pmc_stories ("
               + "title, "
               + "datetime, "
               + "story, "
               + "number_comments, "
               + "counter, "
               + "story_topic_id, "
               + "posted_by, "
               + "story_active_yn, "
               + "browser_title, "
               + "meta_tags, "
               + "customer_id) "
               + "VALUES("
               + "'"
               + myDbUtil.fixSqlFieldValue(story_title)
               + "', "
               + "NOW(), "
               + "'"
               + myDbUtil.fixSqlFieldValue(story_text)
               + "', "
               + "'0', "
               + "'0', "
               + "'"
               + story_topic_id
               + "', "
               + "'"
               + myDbUtil.fixSqlFieldValue(story_author)
               + "', "
               + "'"
               + story_active_yn
               + "', "
               + "'"
               + browser_title.replaceAll("'", "''")
               + "', "
               + "'"
               + meta_tags.replaceAll("'", "''")
               + "', "
               + "'"
               + story_author_id
               + "')";
         //System.out.println("query : " + save);
         pst = conn.prepareStatement(save);
         pst.executeUpdate();

         String getLast = "select currval('pmc_stories_story_id_seq')";

         st = conn.createStatement();
         rs = st.executeQuery(getLast);

         if (rs.next()) {
            new_story_id = rs.getInt(1);
         }

         rs.close();
         rs = null;
         st.close();
         st = null;
         pst.close();
         pst = null;

      } catch (SQLException e) {
         Errors.addError("Story:addStory:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:addStory:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
         if (st != null) {
            st.close();
            st = null;
         }
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }

      return new_story_id;
   }

   /**
    * Returns news item for supplied news id
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getStoryById() throws SQLException, Exception {
      try {
         String getId = "select * from pmc_stories where story_id = '" + story_id + "'";
         st = conn.createStatement();
         rs = st.executeQuery(getId);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Story:getStoryById:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:getStoryById:Exception:" + e.toString());
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
   
   public String returnStoryCategoryNameById(int inId) throws SQLException, Exception {
      String theName = "";
      try {
         String getId = "select category_text from pmc_story_category where story_category_id = '" + inId + "'";
         st = conn.createStatement();
         rs = st.executeQuery(getId);
         while (rs.next()) {
            theName = rs.getString(1);
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Story:returnStoryCategoryNameById:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:returnStoryCategoryNameById:Exception:" + e.toString());
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
    * Returns all items 
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllStories() throws SQLException, Exception {
      try {
         String query = "select * from pmc_stories ";
         if (sCustomWhere.length() > 6) {
            query += " " + sCustomWhere;
         }

         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Story:getAllStories:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:getAllStories:Exception:" + e.toString());
      }
      return crs;
   }
   
   public XDisconnectedRowSet getNMostRecentStories(int n) throws SQLException, Exception {
	      try {
	         String query = "select * from pmc_stories where story_active_yn = 'y' ";
	         if (sCustomWhere.length() > 6) {
	            query += " " + sCustomWhere;
	         }
	         query += " order by datetime desc limit " + n;

	         st = conn.createStatement();
	         rs = st.executeQuery(query);
	         crs.populate(rs);
	         rs.close();
	         rs = null;
	         st.close();
	         st = null;
	      } catch (SQLException e) {
	         Errors.addError("Story:getNMostRecentStories:SQLException:" + e.toString());
	      } catch (Exception e) {
	         Errors.addError("Story:getNMostRecentStories:Exception:" + e.toString());
	      }
	      return crs;
	   }
   
   public RowSetDynaClass getAllStoriesDynaBean() throws SQLException, Exception {
      
      RowSetDynaClass resultset = null;
      
      try {
         String query = "select pmc_stories.*, "
            + "case when story_active_yn = 'y' then '<span style=\"color: green\">active</span>' "
            + "else '<span style=\"color: red;\">inactive</a>' end as story_active_status "
            + "from pmc_stories ";
         if (sCustomWhere.length() > 6) {
            query += " " + sCustomWhere;
         }

         st = conn.createStatement();
         rs = st.executeQuery(query);
         resultset = new RowSetDynaClass(rs, false);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Story:getAllStoriesDynaBean:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:getAllStoriesDynaBean:Exception:" + e.toString());
      }
      return resultset;
   }
   
   public XDisconnectedRowSet getStoryCategories() throws SQLException, Exception {
	   try {
		   String query = "select * from pmc_story_category";
		   st = conn.createStatement();
		   rs = st.executeQuery(query);
		   crs.populate(rs);
		   rs.close();
		   rs = null;
		   st.close();
		   st = null;
	   } catch (Exception e) {
	         Errors
	               .addError("Story:getStoryCategories:Exception:" + e.toString());
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
    * Returns all active news items ordered by start date
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllActiveStories() throws SQLException,
         Exception {
      try {
         String query = "select * from pmc_stories where story_active_yn = 'y' order by datetime";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Story:getAllActiveStories:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors
               .addError("Story:getAllActiveStories:Exception:" + e.toString());
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
    * Returns all active news items for a category ordered by start date
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllActiveStoriesItemsForCategory()
         throws SQLException, Exception {
      try {
         String query = "select * from pmc_stories where story_active_yn = 'y' "
               + "and story_topic_id = '"
               + story_topic_id
               + "' "
               + "order by datetime desc "
               + sCustomWhere;
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Story:getAllActiveStoriesItemsForCategory:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:getAllActiveStoriesItemsForCategory:Exception:"
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
    * returns number of active items for a category
    * 
    * @return int
    * @throws SQLException
    * @throws Exception
    */
   public int getCountForCategory() throws SQLException, Exception {
      int numberOfItems = 0;
      try {
         String query = "select count(story_id) from pmc_stories "
               + "where news_categories_id = '"
               + story_topic_id
               + "' "
               + "and story_active_yn = 'y' ";
         st = conn.createStatement();
         rs = st.executeQuery(query);

         if (rs.next()) {
            numberOfItems = rs.getInt(1);
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Story:getCountForCategory:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:getCountForCategory:Exception:" + e.toString());
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
      return numberOfItems;
   }

   /**
    * Change active status of a story
    * 
    * @throws Exception
    */
   public void changeActiveStatus() throws SQLException, Exception {
      try {
         String query = "update pmc_stories set story_active_yn = '"
               + story_active_yn
               + "' where story_id = '"
               + story_id
               + "'";
         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("Story:changeActiveStatus:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:changeActiveStatus:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Delete a story by id
    * 
    * @throws Exception
    */
   public void deleteStory() throws SQLException, Exception {
      try {
         String query = "delete from pmc_stories where story_id = '" + story_id + "'";
         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Story:deleteStory:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:deleteStory:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }
   
   public void updateCommentCountForStory(int storyId) throws SQLException, Exception {
	   int count = 0;   
	   try {
	         String query = "select number_comments from pmc_stories where story_id = " + storyId;
	         ResultSet rs = null;
	         Statement st = null;
	         st = conn.createStatement();
	         rs = st.executeQuery(query);
	         while (rs.next()) {
	        	 count = rs.getInt(1);
	         }
	         rs.close();
	         rs = null;
	         st.close();
	         st = null;
	         count = count + 1;
	         query = "update pmc_stories set number_comments = " + count + " where story_id = " + storyId;
	         pst = conn.prepareStatement(query);
	         pst.executeUpdate();
	         pst.close();
	         pst = null;
	      } catch (SQLException e) {
	         Errors.addError("Story:updateCommentCountForStory:SQLException:" + e.toString());
	      } catch (Exception e) {
	         Errors.addError("Story:updateCommentCountForStory:Exception:" + e.toString());
	      } finally {
	         if (pst != null) {
	            pst.close();
	            pst = null;
	         }
	      }
	   }
   
   public void updateNumberReadsForStoryId(int storyId) throws SQLException, Exception {
	   int count = 0;   
	   try {
	         String query = "select counter from pmc_stories where story_id = " + storyId;
	         ResultSet rs = null;
	         Statement st = null;
	         st = conn.createStatement();
	         rs = st.executeQuery(query);
	         while (rs.next()) {
	        	 count = rs.getInt(1);
	         }
	         rs.close();
	         rs = null;
	         st.close();
	         st = null;
	         count = count + 1;
	         query = "update pmc_stories set counter = " + count + " where story_id = " + story_id;
	         pst = conn.prepareStatement(query);
	         pst.executeUpdate();
	         pst.close();
	         pst = null;
	      } catch (SQLException e) {
	         Errors.addError("Story:updateNumberReadsForStoryId:SQLException:" + e.toString());
	      } catch (Exception e) {
	         Errors.addError("Story:updateNumberReadsForStoryId:Exception:" + e.toString());
	      } finally {
	         if (pst != null) {
	            pst.close();
	            pst = null;
	         }
	      }
	   }

   /**
    * Change the category of a story
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void changeStoryCategory() throws SQLException, Exception {
      try {
         String query = "update pmc_stories set story_topic_id = '"
               + story_topic_id
               + "' where story_id = '"
               + story_id
               + "'";
         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("Story:changeStoryCategory:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Story:changeStoryCategory:Exception:" + e.toString());
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
      this.setStory_id(Integer.parseInt(theKey));

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#deleteRecord()
    */
   public void deleteRecord() throws SQLException, Exception {
      this.deleteStory();

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.String)
    */
   public void changeActiveStatus(String psStatus) throws SQLException,
         Exception {
      this.setStory_active_yn(psStatus);
      this.changeActiveStatus();

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

public Connection getConn() {
	return conn;
}

public void setConn(Connection conn) {
	this.conn = conn;
}

public String getStory_active_yn() {
	return story_active_yn;
}

public void setStory_active_yn(String story_active_yn) {
	this.story_active_yn = story_active_yn;
}

public String getStory_author() {
	return story_author;
}

public void setStory_author(String story_author) {
	this.story_author = story_author;
}

public int getStory_id() {
	return story_id;
}

public void setStory_id(int story_id) {
	this.story_id = story_id;
}

public String getStory_text() {
	return story_text;
}

public void setStory_text(String story_text) {
	this.story_text = story_text;
}

public String getStory_title() {
	return story_title;
}

public void setStory_title(String story_title) {
	this.story_title = story_title;
}

public int getStory_topic_id() {
	return story_topic_id;
}

public void setStory_topic_id(int story_topic_id) {
	this.story_topic_id = story_topic_id;
}

public int getStory_author_id() {
	return story_author_id;
}

public void setStory_author_id(int story_author_id) {
	this.story_author_id = story_author_id;
}

public String getBrowser_title() {
	return browser_title;
}

public void setBrowser_title(String browser_title) {
	this.browser_title = browser_title;
}

public String getMeta_tags() {
	return meta_tags;
}

public void setMeta_tags(String meta_tags) {
	this.meta_tags = meta_tags;
}

}