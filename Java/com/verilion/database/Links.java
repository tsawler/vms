package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.verilion.object.Errors;

/**
 * Database routines for links related activities.
 * 
 * @author tsawler
 * 
 */
public class Links implements DatabaseInterface {

	private Connection conn = null;
	DBUtils myDbUtil = new DBUtils();
	private PreparedStatement pst = null;
	private String sCustomWhere = "";
	private int link_id = 0;
	private String active_yn = "n";

	public void changeLinkStatus() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("update links ");
			saveBuf.append("set active_yn = ? where link_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setString(1, active_yn);
			pst.setInt(2, link_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Links:changeLinkStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Links:changeLinkStatus:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void deleteLink() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("delete from links where link_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setInt(1, link_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Links:deleteLink:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Links:deleteLink:Exception:" + e.toString());
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

	public int getLink_id() {
		return link_id;
	}

	public void setLink_id(int inId) {
		this.link_id = inId;
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
		this.changeLinkStatus();
	}

	public void deleteRecord() throws SQLException, Exception {
		this.deleteLink();
	}

	public void setPrimaryKey(String theKey) {
		this.setLink_id(Integer.parseInt(theKey));
	}

}