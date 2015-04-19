//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: DbBean.java,v $
//Revision 1.9.2.1.4.1.2.2.8.1  2008/09/01 21:11:42  tcs
//*** empty log message ***
//
//Revision 1.9.2.1.4.1.2.2  2005/10/12 00:05:31  tcs
//Added try/catch to suppress spurious log entries
//
//Revision 1.9.2.1.4.1.2.1  2005/09/08 18:14:37  tcs
//Some changes for tomcat pooling
//
//Revision 1.9.2.1.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.9.2.1  2005/03/08 16:37:32  tcs
//Removed singlethreadmodel interface
//
//Revision 1.9  2004/06/26 17:35:40  tcs
//Changes due to refactoring
//
//Revision 1.8  2004/06/25 16:30:20  tcs
//Implements single thread model (for now)
//
//Revision 1.7  2004/06/25 02:14:34  tcs
//Closed statement
//
//Revision 1.6  2004/06/24 17:58:09  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.5  2004/06/23 13:35:45  tcs
//Changes due to implementation of listener
//
//Revision 1.4  2004/06/22 15:56:56  tcs
//Modified to get schema/jdbc source from ctx; modified to set schema
//
//Revision 1.3  2004/06/12 12:04:38  tcs
//Added stacktracke to catch
//
//Revision 1.2  2004/05/25 14:03:01  tcs
//modifications to make self-contained package
//
//Revision 1.1  2004/05/23 04:52:45  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Manages database connections. Uses JNDI naming to get driver, and assumes
 * that driver supports connection pooling.
 * <P>
 * Nov 5, 2003
 * <P>
 * package com.verilion
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DbBean {

   private static Connection conn = null;
   private static String schema = "";
   private static DataSource ds = null;

   /**
    * Gets a db connection.
    * 
    * @return Connection
    * @throws Exception
    */
   public static synchronized Connection getDbConnection() throws Exception {
      Context ctx = new InitialContext();

      if (ctx == null) {
         System.out.println("No context!");
         throw new Exception("No context!");
      }

      try {
         Context envCtx = (Context) ctx.lookup("java:comp/env");
         schema = (String) envCtx.lookup("schema");
      } catch (Exception e) {
    	  System.out.println(e.toString());
      }

      Statement st = null;
      try {
         ds = SingletonObjects.getInstance().getDs();
         conn = ds.getConnection();
         String query = "set search_path to " + schema;
         st = conn.createStatement();
         st.executeQuery(query);
         st.close();
         st = null;
      } catch (SQLException e) {
         // do nothing, as it's just going to give us a no result msg
         ;
      } catch (Exception e) {
         System.out.println("Can't get datasource!");
      } finally {
         if (st != null) {
            st.close();
            st = null;
         }
      }
      return conn;
   }

   /**
    * Closes db connection, set as property.
    * 
    * @throws Exception
    */
   public synchronized static void closeDbConnection(Connection con)
         throws Exception {
      try {
         if (con != null) {
            con.close();
            con = null;
         }
      } catch (SQLException e) {
         throw new Exception("DbBean:closeDbConnection:SQLException "
               + e.getMessage());
      } catch (Exception e) {
         throw new Exception("DbBean:closeDbConnection:Exception "
               + e.getMessage());
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
      DbBean.conn = conn;
   }
}