//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtCreditCard.java,v $
//Revision 1.6.2.1.4.1  2005/08/21 15:37:14  tcs
//Removed unused membres, code cleanup
//
//Revision 1.6.2.1  2004/12/17 18:11:09  tcs
//Modified for changes to DatabaseInterface
//
//Revision 1.6  2004/11/02 17:45:45  tcs
//Changed mutator for setPrimaryKey to take String parameter
//
//Revision 1.5  2004/10/26 17:57:18  tcs
//Added comments
//
//Revision 1.4  2004/10/26 17:56:12  tcs
//Changed to implement DatabaseInterface
//
//Revision 1.3  2004/06/25 17:29:30  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:12  tcs
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
 * Manipulates ct_country table in db, and related operations.
 * <P>
 * Nov 30, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class CtCreditCard implements DatabaseInterface {

   private int ct_credit_card_id = 0;
   private String ct_credit_card_name = "";
   private String ct_credit_card_active_yn = "";
   private ResultSet rs = null;
   private XDisconnectedRowSet crs = new XDisconnectedRowSet();
   private Connection conn;
   private PreparedStatement pst = null;
   private Statement st = null;
   public String sCustomWhere = "";
   public int iLimit = 0;
   public int iOffset = 0;

   public CtCreditCard() {
      super();
   }

   /**
    * Update method.
    * 
    * @throws Exception
    */
   public void updateCtCreditCard() throws SQLException, Exception {
      try {
         String update = "UPDATE ct_credit_card SET "
               + "ct_credit_card_name = '"
               + ct_credit_card_name
               + "', "
               + "ct_credit_card_active_yn ='"
               + ct_credit_card_active_yn
               + "' "
               + "where ct_credit_card_id = '"
               + ct_credit_card_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtCreditCard:updateCtCreditCard:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtCreditCard:updateCtCreditCard:Exception:"
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
   public void addCtCreditCard() throws SQLException, Exception {
      try {
         String save = "INSERT INTO ct_credit_card ("
               + "ct_credit_card_name, "
               + "ct_credit_card_active_yn) "
               + "VALUES("
               + "'"
               + ct_credit_card_name
               + "', "
               + "'"
               + ct_credit_card_active_yn
               + "')";

         pst = conn.prepareStatement(save);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtCreditCard:addCtCreditCard:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtCreditCard:addCtCreditCard:Exception:"
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
    * @return XDisconnectedRowSet
    * @throws Exception
    */
   public XDisconnectedRowSet getAllCreditCards() throws SQLException,
         Exception {
      try {
         String GetCategory = "select * from ct_credit_card where ct_credit_card_active_yn = 'y'";
         st = conn.createStatement();
         rs = st.executeQuery(GetCategory);
         crs.populate(rs);
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("CtCreditCard:getAllCreditCards:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtCreditCard:getAllCreditCards:Exception:"
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
    * Returns credit card name by id.
    * 
    * @return String
    * @throws Exception
    */
   public String getCreditCardNameById() throws SQLException, Exception {
      String theName = "";
      try {
         String GetCategory = "select ct_credit_card_name from ct_credit_card where ct_credit_card_id = '"
               + ct_credit_card_id
               + "'";
         st = conn.createStatement();
         rs = st.executeQuery(GetCategory);
         if (rs.next()) {
            theName = rs.getString("ct_credit_card_name");
         }
         rs.close();
         rs = null;
         st.close();
         st = null;
      } catch (SQLException e) {
         Errors.addError("CtCreditCard:getCreditCardNameById:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtCreditCard:getCreditCardNameById:Exception:"
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
               + ct_credit_card_active_yn
               + "' "
               + "where ct_credit_card_id = '"
               + ct_credit_card_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtCreditCard:changeActiveStatus:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtCreditCard:changeActiveStatus:Exception:"
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
         String update = "delete from  ct_credit_card "
               + "where ct_credit_card_id = '"
               + ct_credit_card_id
               + "'";

         pst = conn.prepareStatement(update);
         pst.executeUpdate();
         pst.close();
         pst = null;
      } catch (SQLException e) {
         Errors.addError("CtCreditCard:deleteRecord:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("CtCreditCard:deleteRecord:Exception:" + e.toString());
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
      this.ct_credit_card_id = Integer.parseInt(piId);
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
    * @return Returns the ct_credit_card_active_yn.
    */
   public String getCt_credit_card_active_yn() {
      return ct_credit_card_active_yn;
   }

   /**
    * @param ct_credit_card_active_yn
    *           The ct_credit_card_active_yn to set.
    */
   public void setCt_credit_card_active_yn(String ct_credit_card_active_yn) {
      this.ct_credit_card_active_yn = ct_credit_card_active_yn;
   }

   /**
    * @return Returns the ct_credit_card_id.
    */
   public int getCt_credit_card_id() {
      return ct_credit_card_id;
   }

   /**
    * @param ct_credit_card_id
    *           The ct_credit_card_id to set.
    */
   public void setCt_credit_card_id(int ct_credit_card_id) {
      this.ct_credit_card_id = ct_credit_card_id;
   }

   /**
    * @return Returns the ct_credit_card_name.
    */
   public String getCt_credit_card_name() {
      return ct_credit_card_name;
   }

   /**
    * @param ct_credit_card_name
    *           The ct_credit_card_name to set.
    */
   public void setCt_credit_card_name(String ct_credit_card_name) {
      this.ct_credit_card_name = ct_credit_card_name;
   }
}