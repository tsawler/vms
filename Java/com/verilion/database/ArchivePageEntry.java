//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-08
//Revisions
//------------------------------------------------------------------------------
//$Log: ArchivePageEntry.java,v $
//Revision 1.10.2.1.4.1  2005/08/21 15:37:14  tcs
//Removed unused membres, code cleanup
//
//Revision 1.10.2.1  2004/12/17 18:11:09  tcs
//Modified for changes to DatabaseInterface
//
//Revision 1.10  2004/11/02 17:45:45  tcs
//Changed mutator for setPrimaryKey to take String parameter
//
//Revision 1.9  2004/10/26 16:57:17  tcs
//Changed to implement DatabaseInterface
//
//Revision 1.8  2004/10/26 15:35:26  tcs
//Improved javadocs
//
//Revision 1.7  2004/09/17 16:42:24  tcs
//Added method to get formatted html for entry items for display
//
//Revision 1.6  2004/07/27 18:22:42  tcs
//Fixed yet more broken sql in add method
//
//Revision 1.5  2004/07/27 18:16:30  tcs
//Fixed broken sql in insert method
//
//Revision 1.4  2004/07/27 18:07:08  tcs
//Fixed apostrophe error in update
//
//Revision 1.3  2004/07/27 11:10:11  tcs
//Got another column in getArchivePageEntryNamesTitlesIdsForArchivePageId
//
//Revision 1.2  2004/07/26 15:39:41  tcs
//Added method to count entries for an archive page
//
//Revision 1.1  2004/07/08 14:36:49  tcs
//Initial entry into cvs
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
 * Manipulates archive_page_entry table in db, and related operations.
 * <P>
 * July 08, 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ArchivePageEntry implements DatabaseInterface {

   private int archive_page_entry_id = 0;
   private int archive_page_id = 0;
   private String archive_page_name = "";
   private String archive_page_entry_content = "";
   private String archive_page_entry_url = "";
   private String archive_page_entry_phone = "";
   private String archive_page_entry_fax = "";
   private String archive_page_entry_email = "";
   private String start_date = "";
   private String end_date = "9999-12-31";
   private String archive_page_entry_active_yn = "n";
   private String archive_page_entry_title = "";
   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;
   private DBUtils myDbUtil = new DBUtils();
   public String sCustomWhere = "";
   public int iLimit = 0;
   public int iOffset = 0;

   public ArchivePageEntry() {
      super();
   }

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateArchivePageEntry() throws SQLException, Exception {
      try {
         String update = "UPDATE archive_page_entry SET "
               + "archive_page_id = '"
               + archive_page_id
               + "', "
               + "archive_page_entry_content = '"
               + archive_page_entry_content
               + "', "
               + "archive_page_content = '"
               + archive_page_entry_content
               + "', "
               + "archive_page_entry_url = '"
               + archive_page_entry_url
               + "', "
               + "archive_page_entry_phone = '"
               + archive_page_entry_phone
               + "', "
               + "archive_page_entry_fax = '"
               + archive_page_entry_fax
               + "', "
               + "archive_page_entry_email = '"
               + archive_page_entry_email
               + "', "
               + "start_date = '"
               + start_date
               + "', "
               + "end_date = '"
               + "', "
               + "'"
               + "archive_page_entry_active_yn = '"
               + archive_page_entry_active_yn
               + "', "
               + "archive_page_entry_title = '"
               + archive_page_entry_title
               + "' "
               + "WHERE archive_page_entry_id = '"
               + archive_page_entry_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:updateArchivePageEntry:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors.addError("ArchivePageEntry:updateArchivePageEntry:Exception:"
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
   public void addArchivePageEntry() throws SQLException, Exception {
      try {
         String save = "INSERT INTO archive_page_entry ("
               + "archive_page_id, "
               + "archive_page_entry_content, "
               + "archive_page_entry_url, "
               + "archive_page_entry_phone, "
               + "archive_page_entry_fax, "
               + "archive_page_entry_email, "
               + "start_date, "
               + "end_date, "
               + "archive_page_entry_active_yn, "
               + "archive_page_entry_title) "
               + "VALUES("
               + "'"
               + archive_page_id
               + "', "
               + "'"
               + archive_page_entry_content
               + "', "
               + "'"
               + archive_page_entry_url
               + "', "
               + "'"
               + archive_page_entry_phone
               + "', "
               + "'"
               + archive_page_entry_fax
               + "', "
               + "'"
               + archive_page_entry_email
               + "', "
               + "'"
               + start_date
               + "', "
               + "'"
               + end_date
               + "', "
               + "'"
               + archive_page_entry_active_yn
               + "', "
               + "'"
               + archive_page_entry_title
               + "')";

         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("ArchivePage:addArchivePage:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePage:addArchivePage:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns all archive page entry ids and names.
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getArchivePageEntryNamesIds()
         throws SQLException, Exception {
      try {
         String GetCategory = "select archive_page_entry_id, archive_page_entry_title from archive_page_entry";
         st = conn.createStatement();
         rs = st.executeQuery(GetCategory);
         crs.populate(rs);
         rs.close();
         st.close();
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:getArchivePageEntryNamesIds:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePageEntry:getArchivePageEntryNamesIds:Exception:"
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
    * Gets all archive page entries for archive_page_id
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getArchivePageEntryNamesTitlesIdsForArchivePageId()
         throws SQLException, Exception {
      try {
         String GetCategory = "select "
               + "archive_page_entry_id, "
               + "archive_page_entry_title, "
               + "archive_page_entry_active_yn "
               + "from "
               + "archive_page_entry "
               + "where archive_page_id = '"
               + archive_page_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(GetCategory);
         crs.populate(rs);
         rs.close();
         st.close();
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:getArchivePageEntryNamesIdsForArchivePageId:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePageEntry:getArchivePageEntryNamesIdsForArchivePageId:Exception:"
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
    * Deletes Archive Page entry by id.
    * 
    * @throws Exception
    */
   public void deleteArchivePageEntryById() throws SQLException, Exception {
      try {
         String deleteRecord = "delete from archive_page_entry where archive_page_entry_id = "
               + archive_page_entry_id;
         pst = conn.prepareStatement(deleteRecord);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:deleteArchivePageEntryById:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePageEntry:deleteArchivePageEntryById:Exception:"
                     + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Added to support interface
    */
   public void deleteRecord() {
      try {
         this.deleteArchivePageEntryById();
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Change the active status of a record
    * 
    * @param psStatus
    * @throws SQLException
    * @throws Exception
    */
   public void changeActiveStatus(String psStatus) throws SQLException,
         Exception {
      try {
         String deleteRecord = "update archive_page_entry "
               + "set archive_page_entry_active_yn = '"
               + psStatus
               + "' where archive_page_entry_id = '"
               + archive_page_entry_id
               + "'";
         pst = conn.prepareStatement(deleteRecord);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("ArchivePageEntry:changeActiveStatus:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("ArchivePageEntry:changeActiveStatus:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }
   
   /* (non-Javadoc)
    * @see com.verilion.database.DatabaseInterface#createCustomWhere(java.lang.String)
    */
   public void createCustomWhere(String psCustomWhere) {
      this.sCustomWhere = psCustomWhere;
   }

   /* (non-Javadoc)
    * @see com.verilion.database.DatabaseInterface#setLimit(int)
    */
   public void setLimit(int pLimit) {
      this.iLimit = pLimit;
   }

   /* (non-Javadoc)
    * @see com.verilion.database.DatabaseInterface#setOffset(int)
    */
   public void setOffset(int pOffset) {
      this.iOffset = pOffset;
   }

   /**
    * Deletes all archive page entries for archive page id
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void deleteArchivePageEntriesForArchivePageId() throws SQLException,
         Exception {
      try {
         String deleteRecord = "delete from archive_page_entry where archive_page_id = "
               + archive_page_id;
         pst = conn.prepareStatement(deleteRecord);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:deleteArchivePageEntriesForArchivePageId:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePageEntry:deleteArchivePageEntriesForArchivePageId:Exception:"
                     + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns record for supplied id.
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getArchivePageEntry() throws SQLException,
         Exception {

      try {
         String query = "select * from archive_page_entry where archive_page_entry_id = '"
               + archive_page_entry_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("ArchivePageEntry:getArchivePageEntry:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("ArchivePageEntry:getArchivePageEntry:Exception:"
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
    * Gets currently active archive page entries names/titles/ids (active_yn =
    * yes, status published and not pending or expired)
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getActiveCurrentArchivePageEntryNamesTitlesIdsForArchivePageId()
         throws SQLException, Exception {
      try {
         String GetCategory = "select "
               + "archive_page_entry_id, archive_page_entry_title, archive_page_entry_title "
               + "from "
               + "archive_page_entry "
               + "where archive_page_id = '"
               + archive_page_id
               + "' "
               + "and "
               + "start_date <= NOW() "
               + "and "
               + "end_date >= NOW() "
               + "and "
               + "archive_page_entry_active_yn = 'y'";
         st = conn.createStatement();
         rs = st.executeQuery(GetCategory);
         crs.populate(rs);
         rs.close();
         st.close();
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:getActiveCurrentArchivePageEntryNamesTitlesIdsForArchivePageId:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePageEntry:getActiveCurrentArchivePageEntryNamesTitlesIdsForArchivePageId:Exception:"
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
    * Gets all active archive page entries for archive_page_id
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getActiveArchivePageEntryNamesTitlesIdsForArchivePageId()
         throws SQLException, Exception {
      try {
         String GetCategory = "select "
               + "archive_page_entry_id, archive_page_entry_title, archive_page_entry_title "
               + "from "
               + "archive_page_entry "
               + "where archive_page_id = '"
               + archive_page_id
               + "' "
               + "and "
               + "archive_page_entry_active_yn = 'y'";
         st = conn.createStatement();
         rs = st.executeQuery(GetCategory);
         crs.populate(rs);
         rs.close();
         st.close();
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:getActiveArchivePageEntryNamesTitlesIdsForArchivePageId:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePageEntry:getActiveArchivePageEntryNamesTitlesIdsForArchivePageId:Exception:"
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
    * Gives complete formatted HTML list of active archive page entries with all
    * supplied information for a given archive page id
    * 
    * @return String
    * @throws SQLException
    * @throws Exception
    */
   public String getFullActiveArchivePageEntriesForArchivePageId()
         throws SQLException, Exception {
      String sHtml = "";
      try {
         String sSQL = "select "
               + "* "
               + "from "
               + "archive_page_entry "
               + "where archive_page_id = "
               + "(select archive_page_id from archive_page where archive_page_name = '"
               + archive_page_name
               + "') "
               + "and "
               + "archive_page_entry_active_yn = 'y' "
               + "order by archive_page_entry_title";

         st = conn.createStatement();
         rs = st.executeQuery(sSQL);
         crs.populate(rs);
         rs.close();
         st.close();

         sHtml = "<table>";
         boolean foundsomething = false;
         while (crs.next()) {
            foundsomething = true;
            sHtml += "<tr>";
            sHtml += "<td class=\"content\">";
            sHtml += "<strong>";
            sHtml += crs.getString("archive_page_entry_title");
            sHtml += "</strong><br />";
            sHtml += crs.getString("archive_page_entry_content");
            sHtml += "</td></tr><tr><td class=\"content\">";
            if (crs.getString("archive_page_entry_phone") != null) {
               if (crs.getString("archive_page_entry_phone").length() > 0) {
                  sHtml += "Phone: "
                        + crs.getString("archive_page_entry_phone")
                        + "<br />";
               }
            }
            if (crs.getString("archive_page_entry_fax") != null) {
               if (crs.getString("archive_page_entry_fax").length() > 0) {
                  sHtml += "Fax: "
                        + crs.getString("archive_page_entry_fax")
                        + "<br />";
               }
            }
            if (crs.getString("archive_page_entry_url") != null) {
               if (crs.getString("archive_page_entry_url").length() > 0) {
                  sHtml += "<a href=\""
                        + crs.getString("archive_page_entry_url")
                        + "\">"
                        + crs.getString("archive_page_entry_url")
                        + "</a><br />";
               }
            }
            if (crs.getString("archive_page_entry_email") != null) {
               if (crs.getString("archive_page_entry_email").length() > 0) {
                  sHtml += "<a href=\"mailto:"
                        + crs.getString("archive_page_entry_email")
                        + "\">"
                        + crs.getString("archive_page_entry_email")
                        + "</a>";
               }
            }
            sHtml += "</td></tr>"
                  + "<tr><td class=\"content\">&nbsp;</td></tr>";
         }
         if (foundsomething) {
            sHtml += "</table>";
         } else {
            sHtml = "";
         }
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:getFullActiveArchivePageEntriesForArchivePageId:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePageEntry:getFullActiveArchivePageEntriesForArchivePageId:Exception:"
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
      return sHtml;
   }

   /**
    * Returns number of entries for a given archive_page_id
    * 
    * @return int
    * @throws SQLException
    * @throws Exception
    */
   public int getNumberOfEntriesForArchivePageId() throws SQLException,
         Exception {
      int numberOfEntries = 0;
      try {
         String GetCategory = "select "
               + "count(archive_page_entry_id) "
               + "from "
               + "archive_page_entry "
               + "where archive_page_id = '"
               + archive_page_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(GetCategory);
         while (rs.next()) {
            numberOfEntries = rs.getInt(1);
         }
         rs.close();
         st.close();
      } catch (SQLException e) {
         Errors
               .addError("ArchivePageEntry:getNumberOfEntriesForArchivePageId:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors
               .addError("ArchivePageEntry:getNumberOfEntriesForArchivePageId:Exception:"
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
      return numberOfEntries;
   }

   public void setPrimaryKey(String piId) {
      this.archive_page_entry_id = Integer.parseInt(piId);
   }

   /**
    * @return Returns the archive_page_entry_active_yn.
    */
   public String getArchive_page_entry_active_yn() {
      return archive_page_entry_active_yn;
   }

   /**
    * @param archive_page_entry_active_yn
    *           The archive_page_entry_active_yn to set.
    */
   public void setArchive_page_entry_active_yn(
         String archive_page_entry_active_yn) {
      this.archive_page_entry_active_yn = archive_page_entry_active_yn;
   }

   /**
    * @return Returns the archive_page_entry_content.
    */
   public String getArchive_page_entry_content() {
      return archive_page_entry_content;
   }

   /**
    * @param archive_page_entry_content
    *           The archive_page_entry_content to set.
    */
   public void setArchive_page_entry_content(String archive_page_entry_content) {
      this.archive_page_entry_content = myDbUtil
            .fixSqlFieldValue(archive_page_entry_content);
   }

   /**
    * @return Returns the archive_page_entry_email.
    */
   public String getArchive_page_entry_email() {
      return archive_page_entry_email;
   }

   /**
    * @param archive_page_entry_email
    *           The archive_page_entry_email to set.
    */
   public void setArchive_page_entry_email(String archive_page_entry_email) {
      this.archive_page_entry_email = archive_page_entry_email;
   }

   /**
    * @return Returns the archive_page_entry_fax.
    */
   public String getArchive_page_entry_fax() {
      return archive_page_entry_fax;
   }

   /**
    * @param archive_page_entry_fax
    *           The archive_page_entry_fax to set.
    */
   public void setArchive_page_entry_fax(String archive_page_entry_fax) {
      this.archive_page_entry_fax = archive_page_entry_fax;
   }

   /**
    * @return Returns the archive_page_entry_id.
    */
   public int getArchive_page_entry_id() {
      return archive_page_entry_id;
   }

   /**
    * @param archive_page_entry_id
    *           The archive_page_entry_id to set.
    */
   public void setArchive_page_entry_id(int archive_page_entry_id) {
      this.archive_page_entry_id = archive_page_entry_id;
   }

   /**
    * @return Returns the archive_page_entry_phone.
    */
   public String getArchive_page_entry_phone() {
      return archive_page_entry_phone;
   }

   /**
    * @param archive_page_entry_phone
    *           The archive_page_entry_phone to set.
    */
   public void setArchive_page_entry_phone(String archive_page_entry_phone) {
      this.archive_page_entry_phone = archive_page_entry_phone;
   }

   /**
    * @return Returns the archive_page_entry_title.
    */
   public String getArchive_page_entry_title() {
      return archive_page_entry_title;
   }

   /**
    * @param archive_page_entry_title
    *           The archive_page_entry_title to set.
    */
   public void setArchive_page_entry_title(String archive_page_entry_title) {
      this.archive_page_entry_title = myDbUtil
            .fixSqlFieldValue(archive_page_entry_title);
   }

   /**
    * @return Returns the archive_page_entry_url.
    */
   public String getArchive_page_entry_url() {
      return archive_page_entry_url;
   }

   /**
    * @param archive_page_entry_url
    *           The archive_page_entry_url to set.
    */
   public void setArchive_page_entry_url(String archive_page_entry_url) {
      this.archive_page_entry_url = archive_page_entry_url;
   }

   /**
    * @return Returns the archive_page_id.
    */
   public int getArchive_page_id() {
      return archive_page_id;
   }

   /**
    * @param archive_page_id
    *           The archive_page_id to set.
    */
   public void setArchive_page_id(int archive_page_id) {
      this.archive_page_id = archive_page_id;
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
    * @return Returns the end_date.
    */
   public String getEnd_date() {
      return end_date;
   }

   /**
    * @param end_date
    *           The end_date to set.
    */
   public void setEnd_date(String end_date) {
      this.end_date = end_date;
   }

   /**
    * @return Returns the start_date.
    */
   public String getStart_date() {
      return start_date;
   }

   /**
    * @param start_date
    *           The start_date to set.
    */
   public void setStart_date(String start_date) {
      this.start_date = start_date;
   }

   /**
    * @return Returns the archive_page_name.
    */
   public String getArchive_page_name() {
      return archive_page_name;
   }

   /**
    * @param archive_page_name
    *           The archive_page_name to set.
    */
   public void setArchive_page_name(String archive_page_name) {
      this.archive_page_name = archive_page_name;
   }
}