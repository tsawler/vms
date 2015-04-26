package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.verilion.object.Errors;

/**
 * Database routines for event related activities.
 * 
 * @author tsawler
 * 
 */
public class Event implements DatabaseInterface {

	private Connection conn = null;
	DBUtils myDbUtil = new DBUtils();
	private PreparedStatement pst = null;
	private String sCustomWhere = "";
	private int event_id = 0;
	private String active_yn = "n";

	public void changeEventStatus() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("update events ");
			saveBuf.append("set event_active_yn = ? where event_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setString(1, active_yn);
			pst.setInt(2, event_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Event:changeEventStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Event:changeEventStatus:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void deleteEvent() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("delete from events where event_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setInt(1, event_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Event:deleteEvent:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Event:deleteEvent:Exception:" + e.toString());
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

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int inId) {
		this.event_id = inId;
	}

	public String getActive_yn() {
		return active_yn;
	}

	public void setActive_yn(String active_yn) {
		this.active_yn = active_yn;
	}

	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {
		this.setActive_yn(psStatus);
		this.changeEventStatus();
	}

	public void deleteRecord() throws SQLException, Exception {
		this.deleteEvent();
	}

	public void setPrimaryKey(String theKey) {
		this.setEvent_id(Integer.parseInt(theKey));
	}

}