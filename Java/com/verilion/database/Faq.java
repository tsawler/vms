//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-08
//Revisions
//------------------------------------------------------------------------------
//$Log: Faq.java,v $
//Revision 1.1.2.5  2007/02/12 11:33:08  tcs
//Changed sort order for one method
//
//Revision 1.1.2.4  2007/02/10 16:11:10  tcs
//Added method
//
//Revision 1.1.2.3  2007/02/08 23:43:18  tcs
//Improved query so it sorts by parent/child on Faq Categories
//
//Revision 1.1.2.2  2007/02/08 19:27:49  tcs
//changed to implement databaseinterface
//
//Revision 1.1.2.1  2007/02/08 15:50:13  tcs
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
 * Database routines for faq related activities.
 * 
 * @author tsawler
 * 
 */
public class Faq implements DatabaseInterface {

   private Connection conn = null;
   DBUtils myDbUtil = new DBUtils();
   private PreparedStatement pst = null;
   private ResultSet rs = null;
   private Statement st = null;
   private String sCustomWhere = "";

   private int faq_id = 0;
   private int faq_categories_id = 0;
   private int parent_id = 0;
   private String sSelectTable = "faq";
   private String faq_categories_name = "";
   private String question = "";
   private String answer = "";
   private String username = "";
   private String active_yn = "n";

   private XDisconnectedRowSet crs = new XDisconnectedRowSet();

   /**
    * Insert method.
    * 
    * @throws Exception
    */
   public int addFaqQuestionAnswer() throws SQLException, Exception {

      int newFaqId = 0;

      try {
         StringBuffer saveBuf = new StringBuffer("INSERT INTO ");
         saveBuf.append(sSelectTable);
         saveBuf
               .append(" (faq_categories_id, question, answer, username, active_yn) VALUES(?, ?, ?, ?, ?)");

         pst = conn.prepareStatement(saveBuf.toString());
         pst.setInt(1, faq_categories_id);
         pst.setString(2, question);
         pst.setString(3, answer);
         pst.setString(4, username);
         pst.setString(5, active_yn);
         pst.executeUpdate();

         String getLast = "select currval('faq_faq_id_seq')";

         st = conn.createStatement();
         rs = st.executeQuery(getLast);

         if (rs.next()) {
            newFaqId = rs.getInt(1);
         }

         rs.close();
         rs = null;
         st.close();
         st = null;
         pst.close();
         pst = null;

      } catch (SQLException e) {
         Errors.addError("Faq:addFaqQuestionAnswer:SQLException:"
               + e.toString());
      } catch (Exception e) {
         Errors.addError("Faq:addFaqQuestionAnswer:Exception:" + e.toString());
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

      return newFaqId;
   }

   /**
    * Update method for faq
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void updateFaqQuestionAnswer() throws SQLException, Exception {

      try {
         StringBuffer saveBuf = new StringBuffer("update ");
         saveBuf.append(sSelectTable);
         saveBuf
               .append(" set faq_categories_id = ?, question = ?, answer = ?, username = ?, active_yn = ? where faq_id = ?");

         pst = conn.prepareStatement(saveBuf.toString());
         pst.setInt(1, faq_categories_id);
         pst.setString(2, question);
         pst.setString(3, answer);
         pst.setString(4, username);
         pst.setString(5, active_yn);
         pst.setInt(6, faq_id);

         pst.executeUpdate();
         pst.close();
         pst = null;

      } catch (SQLException e) {
         e.printStackTrace();
         Errors.addError("Faq:updateFaqQuestionAnswer:SQLException:"
               + e.toString());
      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("Faq:updateFaqQuestionAnswer:Exception:"
               + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }

   /**
    * Return all faqs, regardless of status, as drs
    * 
    * @return XDisconnectedRowset of all faqs
    * @throws Exception
    */
   public XDisconnectedRowSet getAllFaqs() throws Exception {
      st = conn.createStatement();
      StringBuffer queryBuf = new StringBuffer("select * from ");
      queryBuf.append(sSelectTable);
      rs = st.executeQuery(queryBuf.toString());
      crs.populate(rs);
      rs.close();
      rs = null;
      st.close();
      st = null;

      return crs;
   }

   /**
    * Get a single faq as drs
    * 
    * @param inId
    * @return drs of a single faq
    * @throws Exception
    */
   public XDisconnectedRowSet getFaqById(int inId) throws Exception {
      pst = conn.prepareStatement("select * from faq where faq_id = ?");
      pst.setInt(1, inId);
      rs = pst.executeQuery();
      crs.populate(rs);
      rs.close();
      rs = null;
      pst.close();
      pst = null;

      return crs;
   }
   
   public XDisconnectedRowSet getFaqCategoryById(int inId) throws Exception {
      pst = conn.prepareStatement("select * from faq_categories where faq_categories_id = ?");
      pst.setInt(1, inId);
      rs = pst.executeQuery();
      crs.populate(rs);
      rs.close();
      rs = null;
      pst.close();
      pst = null;

      return crs;
   }

   /**
    * Get all faqs for lookup table
    * 
    * @return dynabean of banners for lookup table
    * @throws Exception
    */
   public RowSetDynaClass getAllFaqsDynaBean() throws Exception {

      RowSetDynaClass resultset = null;

      st = conn.createStatement();
      StringBuffer queryBuf = new StringBuffer(
            "select faq_id, case when char_length(question)> 50 then (substring(question from 0 for 49) || '...') ");
      queryBuf.append("else question end as question, ");
      queryBuf.append("case when active_yn = 'y' then '<span style=\"color: green\">active</span>' else ");
      queryBuf.append("'<span style=\"color: red\">inactive</span>' end as status ");
      queryBuf.append("from ");
      queryBuf.append(sSelectTable);
      queryBuf.append(" where true ");
      queryBuf.append(sCustomWhere);
      queryBuf.append(" order by question");
      rs = st.executeQuery(queryBuf.toString());
      resultset = new RowSetDynaClass(rs, false);
      rs.close();
      rs = null;
      st.close();
      st = null;

      return resultset;
   }

   /**
    * Get all faq categories as dynabean
    * 
    * @return dynabean
    * @throws Exception
    */
   public RowSetDynaClass getAllFaqCategoriesDynaBean() throws Exception {

      RowSetDynaClass resultset = null;

      st = conn.createStatement();
      
      StringBuffer queryBuf = new StringBuffer("select faq_categories_id, parent_id, ");
      queryBuf.append("case when parent_id > 0 then ('&nbsp;&nbsp;&nbsp;' || faq_categories_name)");
      queryBuf.append("else faq_categories_name end as faq_categories_name, ");
      queryBuf.append("case when active_yn = 'y' then '<span style=\"color: green\">active</span>' else ");
      queryBuf.append("'<span style=\"color: red\">inactive</span>' end as status, ");
      queryBuf.append("case when parent_id = 0 then faq_categories_id || '-0' else parent_id || 'faq_categories_id' end as sorter ");
      queryBuf.append("from faq_categories order by sorter");
      rs = st.executeQuery(queryBuf.toString());
      resultset = new RowSetDynaClass(rs, false);
      rs.close();
      rs = null;
      st.close();
      st = null;

      return resultset;
   }
   
   public XDisconnectedRowSet getAllActiveFaqCategories() throws Exception {

      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      
      st = conn.createStatement();
      StringBuffer queryBuf = new StringBuffer(
            "select faq_categories_id, parent_id, case when parent_id > 0 then ('&nbsp;&nbsp;&nbsp;' || faq_categories_name)");
      queryBuf.append("else faq_categories_name end as faq_categories_name, ");
      queryBuf.append("case when parent_id = 0 then faq_categories_id || '-0' else parent_id || 'faq_categories_id' end as sorter ");
      queryBuf.append("from faq_categories ");
      queryBuf.append(" where true ");
      queryBuf.append(sCustomWhere);
      queryBuf.append(" and active_yn = 'y' order by sorter");
      rs = st.executeQuery(queryBuf.toString());
      drs.populate(rs);
      rs.close();
      rs = null;
      st.close();
      st = null;

      return drs;
   }
   
   public XDisconnectedRowSet getAllActiveQuestionsForCategory(int inId) throws Exception {

      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      
      StringBuffer queryBuf = new StringBuffer("select question, faq_id from faq where faq_categories_id = ?");
      pst = conn.prepareStatement(queryBuf.toString());
      pst.setInt(1, inId);
      rs = pst.executeQuery();
      drs.populate(rs);
      rs.close();
      rs = null;
      pst.close();
      pst = null;

      return drs;
   }

   /**
    * Add a faq category
    * 
    * @return id of new entry
    * @throws SQLException
    * @throws Exception
    */
   public int addFaqCategory() throws SQLException, Exception {

      int newFaqCatId = 0;

      try {
         StringBuffer saveBuf = new StringBuffer("INSERT INTO faq_categories ");
         saveBuf.append(" (faq_categories_name, parent_id, active_yn) VALUES(?, ?, ?)");

         pst = conn.prepareStatement(saveBuf.toString());
         pst.setString(1, faq_categories_name);
         pst.setInt(2, faq_categories_id);
         pst.setString(3, active_yn);

         pst.executeUpdate();

         String getLast = "select currval('faq_categories_faq_categories_id_seq')";

         st = conn.createStatement();
         rs = st.executeQuery(getLast);

         if (rs.next()) {
            newFaqCatId = rs.getInt(1);
         }

         rs.close();
         rs = null;
         st.close();
         st = null;
         pst.close();
         pst = null;

      } catch (SQLException e) {
         Errors.addError("Faq:addFaqCategory:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Faq:addFaqCategory:Exception:" + e.toString());
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

      return newFaqCatId;
   }

   /**
    * update a faq category
    * 
    * @throws SQLException
    * @throws Exception
    */
   public void updateFaqCategory() throws SQLException, Exception {

      try {
         StringBuffer saveBuf = new StringBuffer("");

         if (parent_id > 0) {
            saveBuf = new StringBuffer("update faq_categories ");
            saveBuf
                  .append("set faq_categories_name = ?, parent_id = ?, active_yn = ? where faq_categories_id = ?");

            pst = conn.prepareStatement(saveBuf.toString());
            pst.setString(1, faq_categories_name);
            pst.setInt(2, parent_id);
            pst.setString(3, active_yn);
            pst.setInt(4, faq_categories_id);

            pst.executeUpdate();
            pst.close();
            pst = null;
         } else {
            saveBuf = new StringBuffer("update faq_categories ");
            saveBuf
                  .append("set faq_categories_name = ?, parent_id = 0, active_yn = ? where faq_categories_id = ?");

            pst = conn.prepareStatement(saveBuf.toString());
            pst.setString(1, faq_categories_name);
            pst.setString(2, active_yn);
            pst.setInt(3, faq_categories_id);

            pst.executeUpdate();
            pst.close();
            pst = null;
         }

      } catch (SQLException e) {
         Errors.addError("Faq:updateFaqCategory:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Faq:updateFaqCategory:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }
   
   public void changeFaqStatus() throws SQLException, Exception {

      try {
         StringBuffer saveBuf = new StringBuffer("");

            saveBuf = new StringBuffer("update faq ");
            saveBuf
                  .append("set active_yn = ? where faq_id = ?");

            pst = conn.prepareStatement(saveBuf.toString());
            pst.setString(1, active_yn);
            pst.setInt(2, faq_id);

            pst.executeUpdate();
            pst.close();
            pst = null;

      } catch (SQLException e) {
         Errors.addError("Faq:changeFaqStatus:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Faq:changeFaqStatus:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
   }
   
   public void deleteFaq() throws SQLException, Exception {

      try {
         StringBuffer saveBuf = new StringBuffer("");

            saveBuf = new StringBuffer("delete from faq where faq_id = ?");

            pst = conn.prepareStatement(saveBuf.toString());
            pst.setInt(1, faq_id);

            pst.executeUpdate();
            pst.close();
            pst = null;

      } catch (SQLException e) {
         Errors.addError("Faq:deleteFaq:SQLException:" + e.toString());
      } catch (Exception e) {
         Errors.addError("Faq:deleteFaq:Exception:" + e.toString());
      } finally {
         if (pst != null) {
            pst.close();
            pst = null;
         }
      }
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

   public String getAnswer() {
      return answer;
   }

   public void setAnswer(String answer) {
      this.answer = answer;
   }

   public int getFaq_categories_id() {
      return faq_categories_id;
   }

   public void setFaq_categories_id(int faq_categories_id) {
      this.faq_categories_id = faq_categories_id;
   }

   public String getFaq_categories_name() {
      return faq_categories_name;
   }

   public void setFaq_categories_name(String faq_categories_name) {
      this.faq_categories_name = faq_categories_name;
   }

   public int getFaq_id() {
      return faq_id;
   }

   public void setFaq_id(int faq_id) {
      this.faq_id = faq_id;
   }

   public int getParent_id() {
      return parent_id;
   }

   public void setParent_id(int parent_id) {
      this.parent_id = parent_id;
   }

   public String getQuestion() {
      return question;
   }

   public void setQuestion(String question) {
      this.question = question;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getActive_yn() {
      return active_yn;
   }

   public void setActive_yn(String active_yn) {
      this.active_yn = active_yn;
   }

   public void changeActiveStatus(String psStatus) throws SQLException, Exception {
      this.setActive_yn(psStatus);
      this.changeFaqStatus();
   }

   public void deleteRecord() throws SQLException, Exception {
      this.deleteFaq();
   }

   public void setPrimaryKey(String theKey) {
      this.setFaq_id(Integer.parseInt(theKey));
   }

}