//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: Quotes.java,v $
//Revision 1.6.14.1  2007/03/14 00:19:42  tcs
//*** empty log message ***
//
//Revision 1.6  2004/07/06 12:34:49  tcs
//Fixed typo in sql
//
//Revision 1.5  2004/07/05 16:42:44  tcs
//Corrected sql error
//
//Revision 1.4  2004/07/05 16:29:18  tcs
//Added quote_name column
//
//Revision 1.3  2004/07/05 13:36:28  tcs
//Added return of no active quotes message for getRandomQuote()
//
//Revision 1.2  2004/07/05 13:28:00  tcs
//Added changeActiveStatus method
//
//Revision 1.1  2004/07/05 13:25:42  tcs
//Initial entry into cvs
//
//Revision 1.3  2004/06/25 17:47:58  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:09  tcs
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

import com.verilion.object.Errors;

/**
 * Database routines for polls related activities.
 * 
 * @author tsawler
 * 
 */
public class Quotes implements DatabaseInterface {

   private Connection conn = null;
   DBUtils myDbUtil = new DBUtils();
   private PreparedStatement pst = null;
   private String sCustomWhere = "";
   private int quote_id = 0;
   private String active_yn = "n";
   Statement st = null;
   ResultSet rs = null;


   public void changeQuoteStatus() throws SQLException, Exception {

      try {
         StringBuffer saveBuf = new StringBuffer("");

            saveBuf = new StringBuffer("update quotes ");
            saveBuf
                  .append("set quote_active_yn = ? where quote_id = ?");

            pst = conn.prepareStatement(saveBuf.toString());
            pst.setString(1, active_yn);
            pst.setInt(2, quote_id);

            pst.executeUpdate();
            pst.close();
            pst = null;

      } catch (SQLException e) {
         Errors.addError("Quotes:changeQuoteStatus:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Quotes:changeQuoteStatus:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }
   
   public void deleteQuote() throws SQLException, Exception {

      try {
         StringBuffer saveBuf = new StringBuffer("");

            saveBuf = new StringBuffer("delete from quotes where quote_id = ?");

            pst = conn.prepareStatement(saveBuf.toString());
            pst.setInt(1, quote_id);

            pst.executeUpdate();
            pst.close();
            pst = null;

      } catch (SQLException e) {
         Errors.addError("Quotes:deleteQuote:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Quotes:deleteQuote:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }
   
   public String getRandomQuote() throws SQLException, Exception {
      String theQuote = "";
      String query = "";
      
      try {
          query = "select quote_text from quotes where quote_active_yn = 'y' order by random() limit 1";
          st = conn.createStatement();
          rs = st.executeQuery(query);

          if (rs.next()) {
              theQuote = rs.getString(1);
          } else {
              theQuote = "No active quotes";
          }
          rs.close();
          rs = null;
          st.close();
          st = null;
      } catch (SQLException e) {
          Errors.addError("Quotes:getRandomQuote:SQLException:"
                  + e.toString());
      } catch (Exception e) {
          Errors.addError("Quotes:getRandomQuote:Exception:" + e.toString());
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
      return theQuote;
  }

   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public Connection getConn() {
      return conn;
   }

   public String getSCustomWhere() {
      return sCustomWhere;
   }

   public void setSCustomWhere(String customWhere) {
      sCustomWhere = customWhere;
   }
   
   public void createCustomWhere(String customWhere) {
      this.setSCustomWhere(customWhere);
   }


   public int getQuote_id() {
      return quote_id;
   }

   public void setQuote_id(int inId) {
      this.quote_id = inId;
   }

   public String getActive_yn() {
      return active_yn;
   }

   public void setActive_yn(String active_yn) {
      this.active_yn = active_yn;
   }

   public void changeActiveStatus(String psStatus) throws SQLException, Exception {
      this.setActive_yn(psStatus);
      this.changeQuoteStatus();
   }

   public void deleteRecord() throws SQLException, Exception {
      this.deleteQuote();
   }

   public void setPrimaryKey(String theKey) {
      this.setQuote_id(Integer.parseInt(theKey));
   }

}