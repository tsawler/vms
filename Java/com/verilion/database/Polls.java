package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.verilion.object.Errors;

/**
 * Database routines for polls related activities.
 * 
 * @author tsawler
 * 
 */
public class Polls implements DatabaseInterface {

	private Connection conn = null;
	DBUtils myDbUtil = new DBUtils();
	private PreparedStatement pst = null;
	private String sCustomWhere = "";
	private int poll_id = 0;
	private String active_yn = "n";

	public void changePollStatus() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("update polls ");
			saveBuf.append("set active_yn = ? where poll_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setString(1, active_yn);
			pst.setInt(2, poll_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Links:changePollStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Links:changePollStatus:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void deletePoll() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("delete from polls where poll_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setInt(1, poll_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Links:deletePoll:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Links:deletePoll:Exception:" + e.toString());
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

	public int getPoll_id() {
		return poll_id;
	}

	public void setPoll_id(int inId) {
		this.poll_id = inId;
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
		this.changePollStatus();
	}

	public void deleteRecord() throws SQLException, Exception {
		this.deletePoll();
	}

	public void setPrimaryKey(String theKey) {
		this.setPoll_id(Integer.parseInt(theKey));
	}

}