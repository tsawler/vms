//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtLanguages.java,v $
//Revision 1.7.2.1.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.7.2.1  2004/12/17 18:11:09  tcs
//Modified for changes to DatabaseInterface
//
//Revision 1.7  2004/11/02 17:45:45  tcs
//Changed mutator for setPrimaryKey to take String parameter
//
//Revision 1.6  2004/10/26 18:00:11  tcs
//Changed to implement DatabaseInterface
//
//Revision 1.5  2004/10/26 15:35:25  tcs
//Improved javadocs
//
//Revision 1.4  2004/06/26 03:16:40  tcs
//Modified to use XDisconnectedRowSets
//
//Revision 1.3  2004/06/25 16:29:06  tcs
//Now returns DisconnectedRowSets instead of rs
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

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates ct_languages table in db, and related operations.
 * <P>
 * Dec 05, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class CtLanguages implements DatabaseInterface {

   private int ct_language_id = 0;
   private String ct_language_name = "";
   private String ct_language_active_yn = "";
   private ResultSet rs = null;
   XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;
   public String sCustomWhere = "";
   public int iLimit = 0;
   public int iOffset = 0;

   public CtLanguages() {
      super();
   }

   /**
    * Update CtLanguages method.
    * 
    * @throws Exception
    */
   public void updateCtLanguages() throws SQLException, Exception {
      try {
         String update = "UPDATE ct_languages SET "
               + "ct_langauge_name = '"
               + ct_language_name
               + "', "
               + "ct_language_active_yn = '"
               + ct_language_active_yn
               + "' "
               + "WHERE ct_language_id = '"
               + ct_language_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtLanguages:updateCtLanguages:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:updateCtLanguages:Exception:"
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
   public void addCtLanguage() throws SQLException, Exception {
      try {
         String save = "INSERT INTO ct_languages ("
               + "ct_language_name,"
               + "ct_language_active_yn) "
               + "VALUES("
               + "'"
               + ct_language_name
               + "', "
               + "'"
               + ct_language_active_yn
               + "')";

         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtLanguages:addCtLanguage:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:addCtLanguage:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns all language ids and names.
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllLanguageNamesIds() throws SQLException,
         Exception {

      try {
         String getAll = "select * from ct_languages order by ct_language_name";
         st = conn.createStatement();
         rs = st.executeQuery(getAll);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("CtLanguages:getAllLanguageNamesIds:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:getAllLanguageNamesIds:Exception:"
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
    * Returns all active language ids and names.
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllActiveLanguageNamesIds()
         throws SQLException, Exception {
      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      try {
         String getAll = "select * from ct_languages "
               + "where ct_language_active_yn = 'y' "
               + "order by ct_language_name";
         st = conn.createStatement();
         rs = st.executeQuery(getAll);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors
               .addError("CtLanguages:getAllActiveLanguageNamesIds:SQLException:"
                     + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:getAllActiveLanguageNamesIds:Exception:"
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
    * Deletes language by id.
    * 
    * @throws Exception
    */
   public void deleteLanguageById() throws SQLException, Exception {
      try {
         String deleteRecord = "delete from ct_languages where ct_language_id = "
               + ct_language_id;
         pst = conn.prepareStatement(deleteRecord);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtLanguages:deleteLanguageById:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:deleteLanguageById:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns salutation name for supplied id.
    * 
    * @return int
    * @throws SQLException
    * @throws Exception
    */
   public String getLanguageName() throws SQLException, Exception {
      String theSalutation = "";
      try {
         String query = "select ct_salutation_name from ct_salutation where ct_salutation_id = '"
               + ct_language_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         if (rs.next()) {
            theSalutation = rs.getString("ct_salutation_name");
         }
         rs.close();
         st.close();
         rs = null;
         st = null;
      } catch (SQLException e) {
         Errors.addError("CtLanguages:getLanguageName:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:getLanguageName:Exception:"
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
      return theSalutation;
   }

   /**
    * Returns number of active languages from ct_languages
    * 
    * @return int - the number of active languages
    * @throws SQLException
    * @throws Exception
    */
   public int getNumberOfActiveLanguages() throws SQLException, Exception {
      int numberOfActiveLanguages = 0;
      try {
         String query = "select count(ct_language_id) from ct_languages "
               + "where ct_language_active_yn = 'y'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         if (rs.next()) {
            numberOfActiveLanguages = rs.getInt(1);
         }
         rs.close();
         st.close();
         rs = null;
         st = null;
      } catch (SQLException e) {
         Errors.addError("CtLanguages:getNumberOfActiveLanguages:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:getNumberOfActiveLanguages:Exception:"
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
      return numberOfActiveLanguages;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.String)
    */
   public void changeActiveStatus(String psStatus) throws SQLException,
         Exception {
      try {
         String update = "UPDATE ct_credit_card SET "
               + "ct_credit_card_active_yn ='"
               + ct_language_active_yn
               + "' "
               + "where ct_credit_card_id = '"
               + ct_language_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtLanguages:changeActiveStatus:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:changeActiveStatus:Exception:"
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
    * @see com.verilion.database.DatabaseInterface#deleteRecord()
    */
   public void deleteRecord() throws SQLException, Exception {
      try {
         String update = "delete from  ct_languages "
               + "where ct_language_id = '"
               + ct_language_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtLanguages:deleteRecord:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtLanguages:deleteRecord:Exception:" + e.toString());
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
    * @see com.verilion.database.DatabaseInterface#setPrimaryKey(int)
    */
   public void setPrimaryKey(String piId) {
      this.ct_language_id = Integer.parseInt(piId);
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
    * @return Returns the ct_language_active_yn.
    */
   public String getCt_language_active_yn() {
      return ct_language_active_yn;
   }

   /**
    * @param ct_language_active_yn
    *           The ct_language_active_yn to set.
    */
   public void setCt_language_active_yn(String ct_language_active_yn) {
      this.ct_language_active_yn = ct_language_active_yn;
   }

   /**
    * @return Returns the ct_language_id.
    */
   public int getCt_language_id() {
      return ct_language_id;
   }

   /**
    * @param ct_language_id
    *           The ct_language_id to set.
    */
   public void setCt_language_id(int ct_language_id) {
      this.ct_language_id = ct_language_id;
   }

   /**
    * @return Returns the ct_language_name.
    */
   public String getCt_language_name() {
      return ct_language_name;
   }

   /**
    * @param ct_language_name
    *           The ct_language_name to set.
    */
   public void setCt_language_name(String ct_language_name) {
      this.ct_language_name = ct_language_name;
   }
}