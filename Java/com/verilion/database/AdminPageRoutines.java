package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.verilion.object.Errors;

/**
 * Admin Page related routines
 * <P>
 * October 21 2004
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class AdminPageRoutines implements DatabaseInterface {

	private String page_name = "";
	private int ct_language_id = 0;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private Connection conn = null;
	private int page_id = 0;
	private int template_id = 0;
	private String template_contents = "";
	private int page_access_level = 0;
	private String page_detail_contents = "";
	private String page_detail_title = "";
	private String page_active_yn = "n";
	private String display_in_iframe_yn = "n";
	private String iframe_url = "";
	private String page_secure_yn = "";
	public String sCustomWhere = "";
	public int iLimit = 0;
	public int iOffset = 0;

	public AdminPageRoutines() {
		super();
	}

	/**
	 * Checks for valid page name
	 * 
	 * @return true if valid page name supplied
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean isValidPageName() throws SQLException, Exception {

		boolean isValid = false;
		try {
			String query = "select page_id from admin_page where page_name = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, page_name);
			rs = pst.executeQuery();
			if (rs.next()) {
				isValid = true;
			}
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (Exception e) {

		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return isValid;
	}

	/**
	 * Gets page information for display on HTML page
	 * 
	 * @throws Exception
	 */
	public void PageInfoByPageName() throws SQLException, Exception {
		try {
			String query = "SELECT " + "p.page_id, " + "p.template_id, "
					+ "p.page_access_level, " + "p.page_active_yn, "
					+ "p.display_in_iframe_yn, " + "p.iframe_url, "
					+ "p.page_secure_yn, " + "pd.page_detail_title, "
					+ "pd.page_detail_contents, " + "t.template_contents "
					+ "FROM " + "admin_page AS p, "
					+ "admin_page_detail AS pd, " + "template as t " + "WHERE "
					+ "p.page_name = ?" + " " + "AND "
					+ "p.page_id = pd.page_id " + "AND "
					+ "pd.ct_language_id = ?" + " " + "AND "
					+ "p.template_id = t.template_id";

			pst = conn.prepareStatement(query);
			pst.setString(1, page_name);
			pst.setInt(2, ct_language_id);
			rs = pst.executeQuery();

			while (rs.next()) {
				page_id = rs.getInt("page_id");
				template_id = rs.getInt("template_id");
				page_access_level = rs.getInt("page_access_level");
				page_detail_title = rs.getString("page_detail_title");
				page_detail_contents = rs.getString("page_detail_contents");
				template_contents = rs.getString("template_contents");
				page_active_yn = rs.getString("page_active_yn");
				display_in_iframe_yn = rs.getString("display_in_iframe_yn");
				iframe_url = rs.getString("iframe_url");
				page_secure_yn = rs.getString("page_secure_yn");
			}

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("AdminPageRoutines:PageInfoByPageName:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("AdminPageRoutines:PageInfoByPageName::Exception:"
					+ e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
	 */
	public void setPrimaryKey(String theKey) {
		this.page_id = Integer.parseInt(theKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		try {
			String query = "delete from admin_page where page_id = ?";

			pst = conn.prepareStatement(query);
			pst.setInt(1, page_id);
			pst.executeQuery();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("AdminPageRoutines:deleteRecord:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("AdminPageRoutines:deleteRecord::Exception:"
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
	 * @see
	 * com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.
	 * String)
	 */
	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {
		try {
			String query = "update admin_page set page_active_yn = ? where page_id = ?";

			pst = conn.prepareStatement(query);
			pst.setString(1, psStatus);
			pst.setInt(2, page_id);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("AdminPageRoutines:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("AdminPageRoutines:changeActiveStatus:Exception:"
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
	 * @see
	 * com.verilion.database.DatabaseInterface#createCustomWhere(java.lang.String
	 * )
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
	 *            The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * @return Returns the ct_language_id.
	 */
	public int getCt_language_id() {
		return ct_language_id;
	}

	/**
	 * @param ct_language_id
	 *            The ct_language_id to set.
	 */
	public void setCt_language_id(int ct_language_id) {
		this.ct_language_id = ct_language_id;
	}

	/**
	 * @return Returns the page_access_level.
	 */
	public int getPage_access_level() {
		return page_access_level;
	}

	/**
	 * @param page_access_level
	 *            The page_access_level to set.
	 */
	public void setPage_access_level(int page_access_level) {
		this.page_access_level = page_access_level;
	}

	/**
	 * @return Returns the page_active_yn.
	 */
	public String getPage_active_yn() {
		return page_active_yn;
	}

	/**
	 * @param page_active_yn
	 *            The page_active_yn to set.
	 */
	public void setPage_active_yn(String page_active_yn) {
		this.page_active_yn = page_active_yn;
	}

	/**
	 * @return Returns the page_detail_contents.
	 */
	public String getPage_detail_contents() {
		return page_detail_contents;
	}

	/**
	 * @param page_detail_contents
	 *            The page_detail_contents to set.
	 */
	public void setPage_detail_contents(String page_detail_contents) {
		this.page_detail_contents = page_detail_contents;
	}

	/**
	 * @return Returns the page_detail_title.
	 */
	public String getPage_detail_title() {
		return page_detail_title;
	}

	/**
	 * @param page_detail_title
	 *            The page_detail_title to set.
	 */
	public void setPage_detail_title(String page_detail_title) {
		this.page_detail_title = page_detail_title;
	}

	/**
	 * @return Returns the page_id.
	 */
	public int getPage_id() {
		return page_id;
	}

	/**
	 * @param page_id
	 *            The page_id to set.
	 */
	public void setPage_id(int page_id) {
		this.page_id = page_id;
	}

	/**
	 * @return Returns the page_name.
	 */
	public String getPage_name() {
		return page_name;
	}

	/**
	 * @param page_name
	 *            The page_name to set.
	 */
	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}

	/**
	 * @return Returns the template_contents.
	 */
	public String getTemplate_contents() {
		return template_contents;
	}

	/**
	 * @param template_contents
	 *            The template_contents to set.
	 */
	public void setTemplate_contents(String template_contents) {
		this.template_contents = template_contents;
	}

	/**
	 * @return Returns the template_id.
	 */
	public int getTemplate_id() {
		return template_id;
	}

	/**
	 * @param template_id
	 *            The template_id to set.
	 */
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}

	/**
	 * @return Returns the display_in_iframe_yn.
	 */
	public String getDisplay_in_iframe_yn() {
		return display_in_iframe_yn;
	}

	/**
	 * @param display_in_iframe_yn
	 *            The display_in_iframe_yn to set.
	 */
	public void setDisplay_in_iframe_yn(String display_in_iframe_yn) {
		this.display_in_iframe_yn = display_in_iframe_yn;
	}

	/**
	 * @return Returns the iframe_url.
	 */
	public String getIframe_url() {
		return iframe_url;
	}

	/**
	 * @param iframe_url
	 *            The iframe_url to set.
	 */
	public void setIframe_url(String iframe_url) {
		this.iframe_url = iframe_url;
	}

	/**
	 * @return Returns the page_secure_yn.
	 */
	public String getPage_secure_yn() {
		return page_secure_yn;
	}

	/**
	 * @param page_secure_yn
	 *            The page_secure_yn to set.
	 */
	public void setPage_secure_yn(String page_secure_yn) {
		this.page_secure_yn = page_secure_yn;
	}

}