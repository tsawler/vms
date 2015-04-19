//------------------------------------------------------------------------------
//Copyright (c) 2004-2006 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-11-29
//Revisions
//------------------------------------------------------------------------------
//$Log: JspTemplate.java,v $
//Revision 1.1.2.3.2.2.2.1  2007/03/30 17:22:41  tcs
//*** empty log message ***
//
//Revision 1.1.2.3.2.2  2007/02/09 00:38:34  tcs
//Added method to get template name for page name
//
//Revision 1.1.2.3.2.1  2007/01/23 02:11:35  tcs
//Corrected comments
//
//Revision 1.1.2.3  2006/12/22 17:48:51  tcs
//Added GetTemplateById(int)
//
//Revision 1.1.2.2  2006/12/22 16:23:53  tcs
//Added Dynabean method for get all templates
//
//Revision 1.1.2.1  2006/12/19 19:27:17  tcs
//Inital entry into CVS
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
 * Database routines for jsp templates related activities.
 * 
 * @author tsawler
 * 
 */
public class JspTemplate {

	private Connection conn = null;
	DBUtils myDbUtil = new DBUtils();
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private Statement st = null;
	private int jsp_template_id = 0;
	private String jsp_template_name = "";
	private String jsp_template_description = "";
	private String jsp_template_active = "n";
	private String sCustomWhere = "";
	
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();


	/**
	 * Insert method.
	 * 
	 * @throws Exception
	 */
	public int addJspTemplate() throws SQLException, Exception {

		int jsp_template_id = 0;

		try {
			String save = "INSERT INTO jsp_templates (" 
					+ "jsp_template_name, "
					+ "jsp_template_description, " 
					+ "jsp_template_active_yn) " 
					+ "VALUES(?, ?, ?)";

			pst = conn.prepareStatement(save);
			pst.setString(1, jsp_template_name);
			pst.setString(2, jsp_template_description);
			pst.setString(3, jsp_template_active);
			pst.executeUpdate();

			String getLast = "select currval('jsp_template_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);

			if (rs.next()) {
				jsp_template_id = rs.getInt(1);
			}

			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("JspTemplate:addJspTemplate:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("JspTemplate:addJspTemplate:Exception:" + e.toString());
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

		return jsp_template_id;
	}

	public int returnJspTemplateIdForPageId(int id) throws SQLException, Exception {
		st = conn.createStatement();
		String query = "select * from jsp_template_page where page_id = " + id;
		int theId = 0;
		rs = st.executeQuery(query);
		if (rs.next()) {
			theId = rs.getInt("jsp_template_id");
		}
		rs.close();
		rs = null;
		st.close();
		st = null;
		return theId;
	}
    
    public String returnJspTemplateIdForPageName(String inString) throws SQLException, Exception {
        st = conn.createStatement();
        String query = "select jst.jsp_template_name from jsp_templates jst where "
           + "jsp_template_id = (select jsp_template_id from jsp_template_page where page_id = "
           + "(select page_id from page where page_name = '"
           + inString
           + "'))";
        String theTemplate = "";
        rs = st.executeQuery(query);
        if (rs.next()) {
           theTemplate = rs.getString(1);
        }
        rs.close();
        rs = null;
        st.close();
        st = null;
        return theTemplate;
    }
	
	public XDisconnectedRowSet GetAllTemplates() throws Exception {
		st = conn.createStatement();
		String query = "select jsp_template_name, jsp_template_description as template_name, jsp_template_id from jsp_templates where true "
			+ sCustomWhere 
			+ " and jsp_template_active_yn = 'y' order by jsp_template_description";
		rs = st.executeQuery(query);
		crs.populate(rs);
		rs.close();
		rs = null;
		st.close();
		st = null;
		
		return crs;
	}
	
	public XDisconnectedRowSet GetTemplateById(int inId) throws Exception {
		st = conn.createStatement();
		String query = "select * from jsp_templates where jsp_template_id = " + inId;
		rs = st.executeQuery(query);
		crs.populate(rs);
		rs.close();
		rs = null;
		st.close();
		st = null;
		
		return crs;
	}
	public RowSetDynaClass GetAllTemplatesDynaBean() throws Exception {
		
		RowSetDynaClass resultset = null;
		
		st = conn.createStatement();
		String query = "select jsp_template_name, jsp_template_description as template_name, jsp_template_id, jsp_template_active_yn, "
			+ "case when jsp_template_active_yn = 'n' then '<span style=\"color: red;\">inactive</a>' else '<span style=\"color: green;\">active</a>' "
			+ "end as status "
			+ "from jsp_templates where true "
			+ sCustomWhere
			+ " order by jsp_template_description";
		rs = st.executeQuery(query);
		resultset = new RowSetDynaClass(rs, false);
		rs.close();
		rs = null;
		st.close();
		st = null;
		
		return resultset;
	}
	public void updateJspTemplateIdForPageId(int id) throws SQLException, Exception {
		String query = "update jsp_template_page set jsp_template_id = ? where page_id = ?";
		pst = conn.prepareStatement(query);
		pst.setInt(1, jsp_template_id);
		pst.setInt(2, id);
		pst.executeUpdate();
		pst.close();
		pst = null;
	}
	
	public void addJspTemplateIdForPageId(int id) throws SQLException, Exception {
		String query = "insert into jsp_template_page (page_id, jsp_template_id) values (?, ?)";
		pst = conn.prepareStatement(query);
		pst.setInt(1, id);
		pst.setInt(2, jsp_template_id);
		pst.executeUpdate();
		pst.close();
		pst = null;
	}

	public String getJspTemplateNameForJspTemplateId(int id) throws Exception {
		String query = "select jsp_template_name from jsp_templates where jsp_template_id = " + id;
		st = conn.createStatement();
		rs = st.executeQuery(query);
		rs.next();
		String theName = rs.getString(1);
		rs.close();
		rs = null;
		st.close();
		st = null;
		return theName;
	}
	

	/**
	 * @param conn
	 *            The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
	 */
	public void setPrimaryKey(String theKey) {
		this.setJsp_template_id(Integer.parseInt(theKey));

	}

	public String getJsp_template_active() {
		return jsp_template_active;
	}

	public void setJsp_template_active(String jsp_template_active) {
		this.jsp_template_active = jsp_template_active;
	}

	public String getJsp_template_description() {
		return jsp_template_description;
	}

	public void setJsp_template_description(String jsp_template_description) {
		this.jsp_template_description = jsp_template_description;
	}

	public int getJsp_template_id() {
		return jsp_template_id;
	}

	public void setJsp_template_id(int jsp_template_id) {
		this.jsp_template_id = jsp_template_id;
	}

	public String getJsp_template_name() {
		return jsp_template_name;
	}

	public void setJsp_template_name(String jsp_template_name) {
		this.jsp_template_name = jsp_template_name;
	}

	public String getSCustomWhere() {
		return sCustomWhere;
	}

	public void setSCustomWhere(String customWhere) {
		sCustomWhere = customWhere;
	}	

}