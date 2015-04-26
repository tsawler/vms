package com.verilion.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manipulates career_locations table in db
 * 
 * @author tsawler
 * 
 */
public class CareerLocation implements DatabaseInterface {

	// private ResultSet rs = null;

	private Connection conn;

	private Statement st = null;

	private int career_locations_id = 0;

	private String career_location = "";

	DBUtils myDbUtil = new DBUtils();

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
		String query = "delete from career_locations where career_locations_id = "
				+ career_locations_id;
		st.executeUpdate(query);
		st.close();
		st = null;
	}

	public void setPrimaryKey(String theKey) {
		this.setCareer_locations_id(Integer.parseInt(theKey));

	}

	public int getCareer_locations_id() {
		return career_locations_id;
	}

	public void setCareer_locations_id(int career_id) {
		this.career_locations_id = career_id;
	}

	public String getCareer_location() {
		return career_location;
	}

	public void setCareer_location(String career_location) {
		this.career_location = career_location;
	}

}