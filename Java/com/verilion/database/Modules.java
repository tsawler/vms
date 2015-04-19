// ------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-03
//Revisions
//------------------------------------------------------------------------------
//$Log: Modules.java,v $
//Revision 1.8.2.4.14.1  2008/11/11 17:54:07  tcs
//Added support  for "order" column
//
//Revision 1.8.2.4  2005/05/03 12:22:42  tcs
//Added jarfile attribute
//
//Revision 1.8.2.3  2005/04/27 18:14:37  tcs
//Fixed typo in one method
//
//Revision 1.8.2.2  2005/04/27 18:13:52  tcs
//Corrected delete method sql
//
//Revision 1.8.2.1  2005/04/27 14:18:29  tcs
//Changed to implement database interface
//
//Revision 1.8  2004/06/30 18:34:09  tcs
//Added getAllActiveModulesForPositionId() method
//
//Revision 1.7  2004/06/25 18:41:11  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.6  2004/06/25 16:32:07  tcs
//Changed some methods to return XDisconnectedRowSets
//
//Revision 1.5  2004/06/24 17:58:11  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.4  2004/06/11 13:48:32  tcs
//Added comment
//
//Revision 1.3  2004/06/08 03:06:32  tcs
//Fixed quote problem in addModule routine
//
//Revision 1.2  2004/06/04 16:55:43  tcs
//Added support for module_class_name column used in reflection
//
//Revision 1.1  2004/06/03 14:51:07  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.verilion.database.DBUtils;
import com.verilion.object.Errors;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

/**
 * Manipulates modules table in db
 * 
 * @author tsawler
 *  
 */
public class Modules implements DatabaseInterface {

   private int module_id = 0;
   private String module_name = "";
   private String module_active_yn = "";
   private int module_position_id = 0;
   private String module_class_name = "";
   private String jarfile = "";
   private int iOffset = 0;
   private int iLimit = 0;
   private String sCustomWhere = "";

   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;

   DBUtils myDbUtil = new DBUtils();

   public Modules() {
      super();
   }

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateModules() throws SQLException, Exception {
      try {
         String update = "UPDATE modules SET "
               + "module_name = '"
               + myDbUtil.fixSqlFieldValue(module_name)
               + "', "
               + "module_position_id = '"
               + module_position_id
               + "', "
               + "module_active_yn = '"
               + module_active_yn
               + "', "
               + "module_class_name = '"
               + module_class_name
               + "' "
               + "WHERE module_id = '"
               + module_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Modules:updateModules:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Modules:updateModules:Exception:" + e.toString());
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
   public void addModule() throws SQLException, Exception {
      try {
         String save = "INSERT INTO modules ("
               + "module_name, "
               + "module_position_id, "
               + "module_class_name, "
               + "jarfile, "
               + "module_active_yn) "
               + "VALUES("
               + "'"
               + myDbUtil.fixSqlFieldValue(module_name)
               + "', "
               + "'"
               + module_position_id
               + "', "
               + "'"
               + module_class_name
               + "', "
               + "'"
               + jarfile
               + "', "
               + "'"
               + module_active_yn
               + "')";

         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Modules:addModule:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Modules:addModule:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns module name for supplied module id
    * 
    * @return String
    * @throws Exception
    */
   public String getModuleName() throws SQLException, Exception {
      String theName = "";
      try {
         String query = "select module_name from modules "
               + "where module_id = '"
               + module_id
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
         Errors.addError("Modules:getModuleName:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Modules:getModuleName:Exception:" + e.toString());
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
    * Returns module record for supplied module id
    * 
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getModuleRecordById() throws SQLException,
         Exception {

      try {
         String query = "select * from modules where module_id = '"
               + module_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Modules:getModuleRecordById:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Modules:getModuleRecordById:Exception:"
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
    * Returns all active modules ordered by name
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllActiveModules() throws SQLException,
         Exception {

      try {
         String query = "select * from modules "
               + "where module_active_yn = 'y' "
               + "order by module_name";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Modules:getAllActiveModules:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Modules:getAllActiveModules:Exception:"
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
    * Gets all active modules for a given position id
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getAllActiveModulesForPositionId()
         throws SQLException, Exception {

      try {
         String query = "select * from modules "
               + "where module_active_yn = 'y' "
               + "and module_position_id = '"
               + module_position_id
               + "' "
               + "order by module_order";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Modules:getAllActiveModules:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Modules:getAllActiveModules:Exception:"
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
    * Returns all modules ordered by name
    * 
    * @return ResultSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllModules() throws SQLException, Exception {
      try {
         String query = "select * from modules ";
         if (sCustomWhere.length() > 6) {
            query += " " + sCustomWhere;
         }
         query += " order by module_name ";
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
         Errors.addError("Modules:getAllModules:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Modules:getAllModules:Exception:" + e.toString());
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
    * Change active status of a module
    * 
    * @throws Exception
    */
   public void changeActiveStatus() throws SQLException, Exception {
      try {
         String query = "update modules set module_active_yn = '"
               + module_active_yn
               + "' where module_id = '"
               + module_id
               + "'";
         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("Modules:changeActiveStatus:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors
               .addError("Modules:changeActiveStatus:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Delete a module by id
    * 
    * @throws Exception
    */
   public void deleteModule() throws SQLException, Exception {
      try {
         String query = "delete from modules where module_id = '"
               + module_id
               + "'";
         pst = conn.prepareStatement(query);
         pst.executeUpdate();
         pst.close();
         pst = null;
         // could delete jar file here, if it was safe to do so
         // and wouldn't bring the server to its knees....
      } catch (SQLException e) {
         Errors.addError("Modules:deleteModule:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Modules:deleteModule:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
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
    * @return Returns the module_active_yn.
    */
   public String getModule_active_yn() {
      return module_active_yn;
   }

   /**
    * @param module_active_yn
    *           The module_active_yn to set.
    */
   public void setModule_active_yn(String module_active_yn) {
      this.module_active_yn = module_active_yn;
   }

   /**
    * @return Returns the module_id.
    */
   public int getModule_id() {
      return module_id;
   }

   /**
    * @param module_id
    *           The module_id to set.
    */
   public void setModule_id(int module_id) {
      this.module_id = module_id;
   }

   /**
    * @return Returns the module_name.
    */
   public String getModule_name() {
      return module_name;
   }

   /**
    * @param module_name
    *           The module_name to set.
    */
   public void setModule_name(String module_name) {
      this.module_name = module_name;
   }

   /**
    * @return Returns the module_position_id.
    */
   public int getModule_position_id() {
      return module_position_id;
   }

   /**
    * @param module_position_id
    *           The module_position_id to set.
    */
   public void setModule_position_id(int module_position_id) {
      this.module_position_id = module_position_id;
   }

   /**
    * @return Returns the module_class_name.
    */
   public String getModule_class_name() {
      return module_class_name;
   }

   /**
    * @param module_class_name
    *           The module_class_name to set.
    */
   public void setModule_class_name(String module_class_name) {
      this.module_class_name = module_class_name;
   }

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
      this.setModule_id(Integer.parseInt(theKey));

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#deleteRecord()
    */
   public void deleteRecord() throws SQLException, Exception {
      this.deleteModule();

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.String)
    */
   public void changeActiveStatus(String psStatus) throws SQLException,
         Exception {
      this.setModule_active_yn(psStatus);
      this.changeActiveStatus();

   }

   /**
    * @return Returns the jarfile.
    */
   public String getJarfile() {
      return jarfile;
   }

   /**
    * @param jarfile
    *           The jarfile to set.
    */
   public void setJarfile(String jarfile) {
      this.jarfile = jarfile;
   }
}