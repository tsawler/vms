package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.verilion.object.Errors;

/**
 * Database routines for links category related activities.
 * 
 * @author tsawler
 * 
 */
public class LinksCategories implements DatabaseInterface {

	private Connection conn = null;
	DBUtils myDbUtil = new DBUtils();
	private PreparedStatement pst = null;
	private String sCustomWhere = "";
	private int links_cat_id = 0;
	private String title = "";
	private String active_yn = "n";

	public void changeLinkStatus() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("update links_categories ");
			saveBuf.append("set active_yn = ? where links_cat_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setString(1, active_yn);
			pst.setInt(2, links_cat_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("LinksCategories:changeLinkStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("LinksCategories:changeLinkStatus:Exception:"
					+ e.toString());
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

			saveBuf = new StringBuffer(
					"delete from links_categories where links_cat_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setInt(1, links_cat_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("LinksCategories:deleteLink:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("LinksCategories:deleteLink:Exception:"
					+ e.toString());
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

	public int getFaq_categories_id() {
		return links_cat_id;
	}

	public void setLinks_cat_id(int faq_categories_id) {
		this.links_cat_id = faq_categories_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String sName) {
		this.title = sName;
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
		this.setLinks_cat_id(Integer.parseInt(theKey));
	}

}