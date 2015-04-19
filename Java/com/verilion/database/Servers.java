//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-02-25
//Revisions
//------------------------------------------------------------------------------
//$Log: Servers.java,v $
//Revision 1.1.2.1.4.1  2005/08/21 15:37:14  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.1  2005/02/25 13:25:20  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates servers table in db, and related operations.
 * <p>
 * Feb 25, 2005
 * <p>
 * 
 * @author tsawler
 *  
 */
public class Servers {

   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private Statement st = null;

   public Servers() {
      super();
   }

   /**
    * Get all active servers
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getAllActiveServers() throws SQLException,
         Exception {

      try {
         String query = "select * "
               + "from servers where server_active_yn = 'y' "
               + "order by server_name";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Servers:getAllServers:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Servers:getAllServers:Exception:" + e.toString());
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
    * Get all servers
    * 
    * @return XDisconnectedRowSet
    * @throws SQLException
    * @throws Exception
    */
   public XDisconnectedRowSet getAllServers() throws SQLException, Exception {

      try {
         String query = "select * " + "from servers " + "order by server_name";
         st = conn.createStatement();
         rs = st.executeQuery(query);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("Servers:getAllServers:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Servers:getAllServers:Exception:" + e.toString());
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