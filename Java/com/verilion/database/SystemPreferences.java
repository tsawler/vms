//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: SystemPreferences.java,v $
//Revision 1.8.2.3.8.1.4.1  2007/03/14 00:19:42  tcs
//*** empty log message ***
//
//Revision 1.8.2.3.8.1  2005/12/03 03:22:28  tcs
//Added var
//
//Revision 1.8.2.3  2005/04/27 18:11:44  tcs
//Added systempath var
//
//Revision 1.8.2.2  2005/02/24 13:15:43  tcs
//Added cache timeout
//
//Revision 1.8.2.1  2004/12/10 18:01:19  tcs
//Added session_timeout attribute support
//
//Revision 1.8  2004/10/26 15:35:26  tcs
//Improved javadocs
//
//Revision 1.7  2004/10/20 17:26:28  tcs
//added support for admin_email
//
//Revision 1.6  2004/09/17 18:29:03  tcs
//Added memory threshold attribute
//
//Revision 1.5  2004/07/13 17:53:57  tcs
//Added setting of site description value
//
//Revision 1.4  2004/07/12 17:37:16  tcs
//Added site description
//
//Revision 1.3  2004/06/25 16:32:38  tcs
//Now returns DisconnectedRowSets instead of rs
//
//Revision 1.2  2004/06/24 17:58:10  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:46  tcs
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

import com.verilion.database.DBUtils;
import com.verilion.object.Errors;

/**
 * Manipulates system_preferences in db, and related operations.
 * 
 * May 06, 2004
 * 
 * 
 * @author tsawler
 * 
 */
public class SystemPreferences {

   private String system_online_yn = "";
   private String use_sef_yn = "";
   private String template_path = "";
   private String mailhost = "";
   private String secure_port = "";
   private String insecure_port = "";
   private String database = "";
   private String upload_path = "";
   private String host_name = "";
   private String template_directory = "";
   private int homepage_page_id = 1;
   private ResultSet rs = null;
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;
   private String homepage_sef = "n";
   private String site_description = "";
   private long memory_threshold;
   DBUtils myDbUtils = new DBUtils();
   private String admin_email = "";
   private String session_timeout = "";
   private String system_path = "";
   private int cache_timeout = 5;
   private int store_max_subcategories = 0;
   private String homepage = "";
   
   public SystemPreferences() {
      super();
   }

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateSystemPreferences() throws SQLException, Exception {
      try {
         String update = "UPDATE system_preferences SET "
               + "system_online_yn = '"
               + system_online_yn
               + "', "
               + "use_sef_yn ='"
               + use_sef_yn
               + "', "
               + "template_path = '"
               + template_path
               + "', "
               + "mailhost = '"
               + mailhost
               + "', "
               + "secure_port = '"
               + secure_port
               + "', "
               + "insecure_port = '"
               + insecure_port
               + "', "
               + "database = '"
               + database
               + "', "
               + "upload_path = '"
               + upload_path
               + "', "
               + "host_name = '"
               + host_name
               + "', "
               + "template_directory ='"
               + template_directory
               + "', "
               + "homepage_page_id = '"
               + homepage_page_id
               + "', "
               + "homepage_sef = '"
               + homepage_sef
               + "', "
               + "site_description = '"
               + site_description
               + "', "
               + "cache_timeout = '"
               + cache_timeout
               + "', "
               + "store_max_subcategories = '"
               + store_max_subcategories
               + "', "
               + "admin_email = '"
               + admin_email
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors
               .addError("SystemPreferences:updateSystemPreferences:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors.addError("SystemPreferences:updateSystemPreferences:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns all records.
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getSystemPreferences() throws SQLException,
         Exception {

      XDisconnectedRowSet crs = new XDisconnectedRowSet();

      try {
         String query = "select * from system_preferences";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("SystemPreferences:getSystemPreferences:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("SystemPreferences:getSystemPreferences:Exception:"
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

   public void getSystemPreferencesByProperties() throws SQLException,
         Exception {

      try {
         String query = "select * from system_preferences";
         st = conn.createStatement();
         rs = st.executeQuery(query);

         if (rs.next()) {
            system_online_yn = rs.getString("system_online_yn");
            use_sef_yn = rs.getString("use_sef_yn");
            template_path = rs.getString("template_path");
            mailhost = rs.getString("mailhost");
            secure_port = rs.getString("secure_port");
            insecure_port = rs.getString("insecure_port");
            database = rs.getString("database");
            upload_path = rs.getString("upload_path");
            host_name = rs.getString("host_name");
            template_directory = rs.getString("template_directory");
            homepage_page_id = rs.getInt("homepage_page_id");
            site_description = rs.getString("site_description");
            memory_threshold = rs.getLong("memory_threshold");
            admin_email = rs.getString("admin_email");
            session_timeout = rs.getString("session_timeout");
            cache_timeout = rs.getInt("cache_timeout");
            system_path = rs.getString("system_path");
            store_max_subcategories = rs.getInt("store_max_subcategories");
            homepage = rs.getString("homepage");
         }
         rs.close();
         rs = null;
         st.close();
         st = null;

      } catch (SQLException e) {

      } catch (Exception e) {

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
    * @return Returns the database.
    */
   public String getDatabase() {
      return database;
   }

   /**
    * @param database
    *           The database to set.
    */
   public void setDatabase(String database) {
      this.database = database;
   }

   /**
    * @return Returns the host_name.
    */
   public String getHost_name() {
      return host_name;
   }

   /**
    * @param host_name
    *           The host_name to set.
    */
   public void setHost_name(String host_name) {
      this.host_name = host_name;
   }

   /**
    * @return Returns the insecure_port.
    */
   public String getInsecure_port() {
      return insecure_port;
   }

   /**
    * @param insecure_port
    *           The insecure_port to set.
    */
   public void setInsecure_port(String insecure_port) {
      this.insecure_port = insecure_port;
   }

   /**
    * @return Returns the mailhost.
    */
   public String getMailhost() {
      return mailhost;
   }

   /**
    * @param mailhost
    *           The mailhost to set.
    */
   public void setMailhost(String mailhost) {
      this.mailhost = mailhost;
   }

   /**
    * @return Returns the secure_port.
    */
   public String getSecure_port() {
      return secure_port;
   }

   /**
    * @param secure_port
    *           The secure_port to set.
    */
   public void setSecure_port(String secure_port) {
      this.secure_port = secure_port;
   }

   /**
    * @return Returns the system_online_yn.
    */
   public String getSystem_online_yn() {
      return system_online_yn;
   }

   /**
    * @param system_online_yn
    *           The system_online_yn to set.
    */
   public void setSystem_online_yn(String system_online_yn) {
      this.system_online_yn = system_online_yn;
   }

   /**
    * @return Returns the template_directory.
    */
   public String getTemplate_directory() {
      return template_directory;
   }

   /**
    * @param template_directory
    *           The template_directory to set.
    */
   public void setTemplate_directory(String template_directory) {
      this.template_directory = template_directory;
   }

   /**
    * @return Returns the template_path.
    */
   public String getTemplate_path() {
      return template_path;
   }

   /**
    * @param template_path
    *           The template_path to set.
    */
   public void setTemplate_path(String template_path) {
      this.template_path = template_path;
   }

   /**
    * @return Returns the upload_path.
    */
   public String getUpload_path() {
      return upload_path;
   }

   /**
    * @param upload_path
    *           The upload_path to set.
    */
   public void setUpload_path(String upload_path) {
      this.upload_path = upload_path;
   }

   /**
    * @return Returns the use_sef_yn.
    */
   public String getUse_sef_yn() {
      return use_sef_yn;
   }

   /**
    * @param use_sef_yn
    *           The use_sef_yn to set.
    */
   public void setUse_sef_yn(String use_sef_yn) {
      this.use_sef_yn = use_sef_yn;
   }

   /**
    * @return Returns the homepage_page_id.
    */
   public int getHomepage_page_id() {
      return homepage_page_id;
   }

   /**
    * @param homepage_page_id
    *           The homepage_page_id to set.
    */
   public void setHomepage_page_id(int homepage_page_id) {
      this.homepage_page_id = homepage_page_id;
   }

   /**
    * @return Returns the homepage_sef.
    */
   public String getHomepage_sef() {
      return homepage_sef;
   }

   /**
    * @param homepage_sef
    *           The homepage_sef to set.
    */
   public void setHomepage_sef(String homepage_sef) {
      this.homepage_sef = homepage_sef;
   }

   /**
    * @return Returns the site_descripton.
    */
   public String getSite_descripton() {
      return site_description;
   }

   /**
    * @param site_description
    *           The site_description to set.
    */
   public void setSite_descripton(String site_description) {
      this.site_description = myDbUtils.fixSqlFieldValue(site_description);
   }

   /**
    * @return Returns the memory_threshold.
    */
   public long getMemory_threshold() {
      return memory_threshold;
   }

   /**
    * @param memory_threshold
    *           The memory_threshold to set.
    */
   public void setMemory_threshold(long memory_threshold) {
      this.memory_threshold = memory_threshold;
   }

   /**
    * @return Returns the admin_email.
    */
   public String getAdmin_email() {
      return admin_email;
   }

   /**
    * @param admin_email
    *           The admin_email to set.
    */
   public void setAdmin_email(String admin_email) {
      this.admin_email = admin_email;
   }

   /**
    * @return Returns the session_timeout.
    */
   public String getSession_timeout() {
      return session_timeout;
   }

   /**
    * @param session_timeout
    *           The session_timeout to set.
    */
   public void setSession_timeout(String session_timeout) {
      this.session_timeout = session_timeout;
   }

   /**
    * @return Returns the cache_timeout.
    */
   public int getCache_timeout() {
      return cache_timeout;
   }

   /**
    * @param cache_timeout
    *           The cache_timeout to set.
    */
   public void setCache_timeout(int cache_timeout) {
      this.cache_timeout = cache_timeout;
   }

   /**
    * @return Returns the system_path.
    */
   public String getSystem_path() {
      return system_path;
   }

   /**
    * @param system_path
    *           The system_path to set.
    */
   public void setSystem_path(String system_path) {
      this.system_path = system_path;
   }

   /**
    * @return Returns the store_max_subcategories.
    */
   public int getStore_max_subcategories() {
      return store_max_subcategories;
   }

   /**
    * @param store_max_subcategories
    *           The store_max_subcategories to set.
    */
   public void setStore_max_subcategories(int store_max_subcategories) {
      this.store_max_subcategories = store_max_subcategories;
   }

   public String getHomepage() {
      return homepage;
   }

   public void setHomepage(String homepage) {
      this.homepage = homepage;
   }
}