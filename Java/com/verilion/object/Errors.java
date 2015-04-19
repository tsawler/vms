//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: Errors.java,v $
//Revision 1.2.2.4.4.1.8.1  2007/02/09 00:38:11  tcs
//Write detailed message to error log
//
//Revision 1.2.2.4.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2.2.4  2004/12/14 14:32:58  tcs
//Cleaned up imports
//
//Revision 1.2.2.3  2004/12/14 10:52:47  tcs
//*** empty log message ***
//
//Revision 1.2.2.2  2004/12/11 18:30:06  tcs
//Removed useless stacktrace
//
//Revision 1.2.2.1  2004/12/10 18:26:15  tcs
//Added email notification for errors
//
//Revision 1.2  2004/06/24 17:58:15  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.verilion.database.DBUtils;
import com.verilion.database.DbBean;
import com.verilion.database.SingletonObjects;

/**
 * Saves error messages to db.
 * <P>
 * November 19, 2003
 * <P>
 * 
 * @author tsawler
 *  
 */
public class Errors {

   private ResultSet rs = null;
   private static Connection conn;
   private Statement st = null;
   private static PreparedStatement pst = null;

   public Errors() {
      super();
   }

   /**
    * Insert method.
    * 
    * @throws Exception
    */
   public static void addError(String theError) throws SQLException, Exception {
      String save = "";
      try {
         Date myDate = new Date();
         
         // clean up our error message
         DBUtils myDbUtil = new DBUtils();
         theError = myDbUtil.fixSqlFieldValue(theError);
         theError = myDbUtil.replace(theError, "\r", "");
         theError = myDbUtil.replace(theError, "\n", "");
         
         // write a message to the System log
         System.out.println("***********************************************************************");
         System.out.println("**");
         System.out.println("** Error in " + SingletonObjects.getInstance().getHost_name());
         System.out.println("** Error occurred at " + myDate.toString());
         System.out.println("** Error was " + theError);
         System.out.println("**");
         System.out.println("***********************************************************************");
         
         // write error to errors table
         save = "INSERT INTO errors ("
               + "error_date, "
               + "error_message) "
               + "VALUES ("
               + "CURRENT_TIMESTAMP, "
               + "'"
               + theError
               + "')";
         try {
            conn = DbBean.getDbConnection();
            conn.setAutoCommit(false);
         } catch (Exception e) {
            System.out.println("vMaintain: Error getting db connection. This is normal "
                  + "during system startup.");
         }

         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         conn.commit();
         pst.close();
         pst = null;
         DbBean.closeDbConnection(conn);
      } catch (SQLException e) {
         System.out.println("Error in errors: " + e.toString()
               + " This is expected behaviour during system startup only");
         e.printStackTrace();
         conn.rollback();
         throw new SQLException("Errors:addError:SQLException:"
               + e.getMessage());
      } catch (Exception e) {
         // do nothing. There is nothing useful we can do here.
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Returns all error messages.
    * 
    * @return ResultSet
    * @throws Exception
    */
   public ResultSet getAllErrors() throws Exception {
      try {
         String sqlQuery = "select * from errors";

         try {
            conn = DbBean.getDbConnection();
         } catch (Exception e) {
            System.err.println("DbBean:getDbConnection:Exception "
                  + e.getMessage());
         }

         st = conn.createStatement();
         rs = st.executeQuery(sqlQuery);
      } catch (SQLException e) {
         System.out.println("Error in errors!" + e.toString());
         throw new Exception("Errors:getAllErrors:SQLException:"
               + e.getMessage());
      } catch (Exception e) {
         System.out.println("Error in errors!" + e.toString());
         throw new Exception("Errors:getAllErrors:Exception " + e.getMessage());
      } finally {
         st.close();
         st = null;
         conn.close();
         conn = null;
      }
      return rs;
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
      Errors.conn = conn;
   }

}