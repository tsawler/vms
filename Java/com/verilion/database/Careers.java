// ------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-12-01
//Revisions
//------------------------------------------------------------------------------
//$Log: Careers.java,v $
//Revision 1.1.2.2  2006/12/20 18:51:16  tcs
//Added sCustomWhere field & getter/setter
//
//Revision 1.1.2.1  2006/12/19 19:26:33  tcs
//Inital entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.beanutils.RowSetDynaClass;

import com.verilion.object.Errors;

/**
 * Manipulates careers table in db
 * 
 * @author tsawler
 * 
 */
public class Careers implements DatabaseInterface {

	private ResultSet rs = null;
	private Connection conn;
	private Statement st = null;
	private int career_id = 0;
	private String sCustomWhere = "";
	DBUtils myDbUtil = new DBUtils();

	public RowSetDynaClass getAllCareersDynaBean() throws SQLException,
			Exception {

		RowSetDynaClass resultset = null;

		try {
			String query = "select c.career_id, c.career_title, cl.career_location as location, ct.career_type as type from "
					+ "careers c "
					+ "left join career_locations cl on (c.career_locations_id = cl.career_locations_id) "
					+ "left join career_type ct on (c.career_type = ct.career_type_id) "
					+ sCustomWhere
					+ " "
					+ "order by career_title";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Careers:getAllCareers:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Careers:getAllCareers:Exception:" + e.toString());
		}
		return resultset;
	}

	/**
	 * @return Returns the conn.
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn
	 *            The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {

	}

	public void createCustomWhere(String psCustomWhere) {

	}

	public void deleteRecord() throws SQLException, Exception {
		st = conn.createStatement();
		String query = "delete from careers where career_id = " + career_id;
		st.executeUpdate(query);
		st.close();
		st = null;
	}

	public void setPrimaryKey(String theKey) {
		this.setCareer_id(Integer.parseInt(theKey));

	}

	public int getCareer_id() {
		return career_id;
	}

	public void setCareer_id(int career_id) {
		this.career_id = career_id;
	}

	public String getSCustomWhere() {
		return sCustomWhere;
	}

	public void setSCustomWhere(String customWhere) {
		sCustomWhere = customWhere;
	}

}