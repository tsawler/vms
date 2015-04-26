package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.verilion.database.DBUtils;
import com.verilion.object.Errors;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

/**
 * Manipulates template, page, and page_detail tables in db, and related
 * operations.
 * <P>
 * Nov 28, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class PageTemplate implements DatabaseInterface {

	private int template_id = 0;
	private String template_name = "";
	private String template_contents = "";
	private String template_active_yn = "";
	private int page_id = 0;
	private int page_access_level = 0;
	private String page_name = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;
	private String page_detail_contents = "";
	private int page_detail_id = 0;
	private int ct_language_id = 0;
	private String page_invocation = "";
	private String page_detail_title = "";
	private String page_active_yn = "";
	private String display_in_iframe_yn = "n";
	private String iframe_url = "";
	private String page_secure_yn = "";
	private String sCustomWhere = "";
	private int iLimit = 0;
	private int iOffset = 0;
	private String meta_tags = "";
	private String browser_title = "";

	DBUtils myDbUtil = new DBUtils();

	public PageTemplate() {
		super();
	}

	/**
	 * Update template method.
	 * 
	 * @throws Exception
	 */
	public void updateTemplate() throws SQLException, Exception {
		try {
			String update = "UPDATE template SET " + "template_name = '"
					+ template_name + "', " + "template_contents = '"
					+ template_contents + "', " + "template_active_yn = '"
					+ template_active_yn + "' " + "WHERE template_id = '"
					+ template_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:updateTemplate:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:updateTemplate:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Insert template method.
	 * 
	 * @throws Exception
	 */
	public void addTemplate() throws SQLException, Exception {
		try {
			String save = "INSERT INTO template (" + "template_name, "
					+ "template_active_yn, " + "template_contents) "
					+ "VALUES(" + "'" + template_name + "', " + "'"
					+ template_active_yn + "', " + "'" + template_contents
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:addTemplate:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:addTemplate:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void addPageToHistory() throws SQLException, Exception {
		try {
			String save = "INSERT INTO page_history (" + "page_id, "
					+ "page_contents) " + "VALUES(" + page_id + ", '"
					+ page_detail_contents + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:addPageToHistory:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:addPageToHistory:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public XDisconnectedRowSet returnPageHistoryForPageId(int inId)
			throws SQLException, Exception {
		try {
			String GetCategory = "select page_history_id, date_time from page_history where page_id = "
					+ inId + " order by date_time";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:returnPageHistoryForPageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:returnPageHistoryForPageId:Exception:"
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

	public XDisconnectedRowSet returnOldPageVersion(int inId)
			throws SQLException, Exception {
		try {
			String GetCategory = "select page_contents from page_history where page_history_id = "
					+ inId;
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:returnOldPageVeresion:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:returnOldPageVeresion:Exception:"
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
	 * Returns all template ids and names.
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllTemplateNamesIds() throws SQLException,
			Exception {
		try {
			String GetCategory = "select template_id, template_name from template order by template_name";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getAllTemplateNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getAllTemplateNamesIds:Exception:"
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
	 * Gets all editable template names, status, ids
	 * 
	 * @return drs
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllEditableTemplateNamesIdsStatuses()
			throws SQLException, Exception {
		try {
			String GetCategory = "select template_id, template_name, template_active_yn from template where system_template_yn = 'n'"
					+ " order by template_name";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getAllTemplateNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getAllTemplateNamesIds:Exception:"
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
	 * Returns active, non system templates
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveTemplateNamesIds()
			throws SQLException, Exception {
		try {
			String GetCategory = "select " + "template_id, template_name "
					+ "from template " + "where template_active_yn = 'y' "
					+ "and system_template_yn = 'n' "
					+ "order by template_name";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getAllActiveTemplateNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getAllActiveTemplateNamesIds:Exception:"
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
	 * Deletes template by template id.
	 * 
	 * @throws Exception
	 */
	public void deleteTemplateById() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from template where template_id = "
					+ template_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:deleteTemplateById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:deleteTemplateById:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Deletes page by page id.
	 * 
	 * @throws Exception
	 */
	public void deletePageById() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from page where page_id = " + page_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:deletePageById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:deletePageById:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Checks to see if a page name is already in use
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public boolean checkForExistingPageName() throws SQLException, Exception {
		boolean exists = false;
		try {
			String query = "select page_id from page where page_name = '"
					+ page_name + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				exists = true;
			}
			rs.close();
			rs = null;
			st.close();
			st = null;

		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:checkForExistingPageName:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:checkForExistingPageName:Exception:"
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
		return exists;
	}

	/**
	 * Checks to see if a page name is already in use
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public boolean checkForLanguageForPage() throws SQLException, Exception {
		boolean exists = false;
		try {
			String query = "" + "select pd.page_detail_id from "
					+ "page_detail as pd, " + "page as p "
					+ "where pd.ct_language_id = '" + ct_language_id + "' "
					+ "and p.page_id = pd.page_id " + "and p.page_name = '"
					+ page_name + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				exists = true;
			}
			rs.close();
			rs = null;
			st.close();
			st = null;

		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:checkForLanguageForPage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:checkForLanguageForPage:Exception:"
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
		return exists;
	}

	/**
	 * Checks to see if a page is active, by page name
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public boolean checkPageActiveStatus() throws SQLException, Exception {
		boolean active = false;
		String myActiveStatus = "";
		try {
			String query = "" + "select page_active_yn " + "from page where "
					+ "page_name = '" + page_name + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				myActiveStatus = rs.getString(1);
				if (myActiveStatus.equals("y")) {
					active = true;
				} else {
					active = false;
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;

		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:checkPageActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:checkPageActiveStatus:Exception:"
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
		return active;
	}

	/**
	 * Deletes pagedetail by page id.
	 * 
	 * @throws Exception
	 */
	public void deletePageDetailById() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from page_detail where page_id = '"
					+ page_id + "' " + "and ct_language_id = '"
					+ ct_language_id + "'";
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:deletePageDetailById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:deletePageDetailById:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns template id for supplied page id.
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getTemplateIdForPage() throws SQLException, Exception {
		int templateId = 0;
		try {
			String query = "select template_id from page where page_id = '"
					+ page_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				templateId = rs.getInt("template_id");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getTemplateIdForPage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getTemplateIdForPage:Exception:"
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
		return templateId;
	}

	/**
	 * Returns template id for supplied page id.
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getPageIdByPageName() throws SQLException, Exception {
		int pageId = 0;
		try {
			String query = "select page_id from page where page_name = '"
					+ page_name + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				pageId = rs.getInt("page_id");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageIdByPageName:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageIdByPageName:Exception:"
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
		return pageId;
	}

	/**
	 * Get a template by id
	 * 
	 * @return crs
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getOneTemplate() throws SQLException, Exception {

		try {
			String query = "select * from template where template_id = '"
					+ template_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getOneTemplate:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getOneTemplate:Exception:"
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
	 * Update page method.
	 * 
	 * @throws Exception
	 */
	public void updatePage() throws SQLException, Exception {
		try {
			String update = "UPDATE page SET " + "page_name = " + "'"
					+ page_name + "', " + "template_id = " + "'" + template_id
					+ "', " + "page_access_level = " + "'" + page_access_level
					+ "', " + "page_invocation = " + "'" + page_invocation
					+ "', " + "page_active_yn = '" + page_active_yn + "', "
					+ "display_in_iframe_yn = '" + display_in_iframe_yn + "', "
					+ "meta_tags = '" + meta_tags + "', " + "browser_title = '"
					+ browser_title + "', " + "iframe_url = " + "'"
					+ iframe_url + "' " + "WHERE page_id = '" + page_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:updatePage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:updatePage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Insert page method.
	 * 
	 * @throws Exception
	 */
	public int addPage() throws SQLException, Exception {
		int theId = 0;
		try {

			String save = "INSERT INTO page (" + "page_name, "
					+ "template_id, " + "page_access_level, "
					+ "display_in_iframe_yn, " + "iframe_url, "
					+ "page_secure_yn, " + "page_active_yn, "
					+ "page_invocation, " + "browser_title, " + "meta_tags) "
					+ "VALUES(" + "'"
					+ page_name
					+ "', "
					+ "'"
					+ template_id
					+ "', "
					+ "'"
					+ page_access_level
					+ "', "
					+ "'"
					+ display_in_iframe_yn
					+ "', "
					+ "'"
					+ iframe_url
					+ "', "
					+ "'"
					+ page_secure_yn
					+ "', "
					+ "'"
					+ page_active_yn
					+ "', "
					+ "'"
					+ page_invocation
					+ "', "
					+ "'"
					+ browser_title
					+ "', "
					+ "'" + meta_tags + "'" + ")";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();

			String getLast = "select currval('page_page_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);
			if (rs.next()) {
				theId = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:addPage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:addPage:Exception:"
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
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return theId;
	}

	/**
	 * Update page_detail method.
	 * 
	 * @throws Exception
	 */
	public void updatePageDetail() throws SQLException, Exception {
		try {
			String update = "UPDATE page_detail SET " + "page_detail_title = "
					+ "'" + page_detail_title + "', "
					+ "page_detail_contents = " + "'" + page_detail_contents
					+ "' " + "WHERE page_id = " + "'" + page_id + "' "
					+ "and ct_language_id = " + "'" + ct_language_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:updatePageDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:updatePageDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Insert page_detail method.
	 * 
	 * @throws Exception
	 */
	public void addPageDetail() throws SQLException, Exception {
		try {

			String save = "INSERT INTO page_detail (" + "page_id, "
					+ "page_detail_title, " + "page_detail_contents, "
					+ "ct_language_id) " + "VALUES (" + "'" + page_id + "', "
					+ "'" + page_detail_title + "', " + "'"
					+ page_detail_contents + "', " + "'" + ct_language_id
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:addPageDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:addPageDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns page for editing as resultset
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getPageForEdit() throws SQLException, Exception {

		try {
			String query = "select " + "p.template_id, " + "p.page_name, "
					+ "ct_a.ct_access_level_id, " + "pd.page_detail_contents, "
					+ "pd.page_detail_title, "
					+ "pd.page_detail_title_style_id, "
					+ "pd.page_detail_contents_style_id, "
					+ "p.page_invocation, " + "p.browser_title, "
					+ "p.meta_tags, " + "p.page_active_yn "
					+ "from page as p, " + "ct_access_level as ct_a, "
					+ "page_detail as pd " + "where p.page_id = '" + page_id
					+ "' " + "and pd.page_id = '" + page_id + "' "
					+ "and ct_a.ct_access_level_id = p.page_access_level "
					+ "and pd.ct_language_id = '" + ct_language_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageForEdit:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageForEdit:Exception:"
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
	 * Returns page names and ids, excluding system pages
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getPageNamesIds() throws SQLException, Exception {

		try {
			String query = "select page_id, page_name from page "
					+ "order by page_name";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageNamesIds:Exception:"
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
	 * Returns drs of all page pages including system pages provided they are
	 * active
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllPageNamesIds() throws SQLException,
			Exception {

		try {
			String query = "select page_id, page_name from page "
					+ "order by page_name";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageNamesIds:Exception:"
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
	 * Gets all non-immutable page names, ids, and active_yn status
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getPageNamesIdsStatuses() throws SQLException,
			Exception {

		try {
			String query = "select page_id, page_name, page_access_level, "
					+ "page_active_yn from page ";

			if (sCustomWhere.length() > 6) {
				query += sCustomWhere;
			}

			query += " order by page_name";

			if (this.iLimit > 0)
				query += " limit " + iLimit;
			if (this.iOffset > 0)
				query += " offset " + iOffset;

			st = conn.createStatement();
			rs = st.executeQuery(query);

			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("PageTemplate:getPageNamesIdsStatuses:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("PageTemplate:getEditablePageNamesIdsStatuses:Exception:"
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

	public RowSetDynaClass getPageNamesIdsStatusesDynaBean()
			throws SQLException, Exception {

		RowSetDynaClass resultset = null;
		try {
			String query = "select p.page_id, p.page_name, p.page_access_level, "
					+ "p.page_active_yn, pd.page_detail_title, "
					+ "case when p.page_active_yn = 'y' then '<span style=\"color: green\">active</span>' "
					+ "else '<span style=\"color: red;\">inactive</a>' end as page_status "
					+ "from page p "
					+ "left join page_detail pd on (p.page_id = pd.page_id and pd.ct_language_id = 1) ";

			if (sCustomWhere.length() > 6) {
				query += sCustomWhere;
			}

			query += " order by page_name";

			if (this.iLimit > 0)
				query += " limit " + iLimit;
			if (this.iOffset > 0)
				query += " offset " + iOffset;

			st = conn.createStatement();
			rs = st.executeQuery(query);

			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("PageTemplate:getPageNamesIdsStatuses:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("PageTemplate:getEditablePageNamesIdsStatuses:Exception:"
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
		return resultset;
	}

	/**
	 * Gets active, editable page names/ids
	 * 
	 * @return rs
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getActivePageNamesIds() throws SQLException,
			Exception {

		try {
			String query = "select page_id, page_name from page "
					+ "where page_active_yn = 'y' ";
			if (sCustomWhere.length() > 0) {
				query += sCustomWhere;
			}
			query += "order by page_name";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("PageTemplate:getActiveEditablePageNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("PageTemplate:getActiveEditablePageNamesIds:Exception:"
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
	 * Returns page names actions and ids, excluding system pages
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getPageNamesIdsActions() throws SQLException,
			Exception {

		try {
			String query = "select page_id, page_name, page_invocation from page "
					+ "order by page_name";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageNamesIdsActions:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getPageNamesIdsActions:Exception:"
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
	 * Returns page invocation url by page id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getInvocation() throws SQLException, Exception {
		ResultSet rs = null;
		try {
			String query = "select page_invocation from page where page_id = '"
					+ page_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getInvocation:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:getInvocation:Exception:"
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
	 * Returns page invocation url by page id as string
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getInvocationAsString() throws SQLException, Exception {
		ResultSet rs = null;
		String url = "";
		try {
			String query = "select page_invocation from page where page_id = '"
					+ page_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				url = rs.getString("page_invocation");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;

		} catch (SQLException e) {
			Errors.addError("PageTemplate:getInvocationAsString:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("PageTemplate:getInvocationAsString:Exception:"
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
		return url;
	}

	/**
	 * Change active status of a page
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void changeActiveStatus() throws SQLException, Exception {

		try {
			String query = "update page set page_active_yn = '"
					+ page_active_yn + "' where page_id = '" + page_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:changeActiveStatus:Exception:"
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

	public void changeTemplateActiveStatus() throws SQLException, Exception {

		try {
			String query = "update template set template_active_yn = '"
					+ template_active_yn + "' where template_id = '"
					+ template_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:changeTemplateActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:changeTemplateActiveStatus:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}

	}

	public int returnNumberOfPagesForTemplateId() throws SQLException,
			Exception {

		int iNumberOfPages = 0;

		try {
			String query = "select count(page_id) from page where template_id = '"
					+ template_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				iNumberOfPages = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;

		} catch (SQLException e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:returnNumberOfPagesForTemplateId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.admin.PageTemplate:returnNumberOfPagesForTemplateId:Exception:"
					+ e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (st != null) {
				pst.close();
				st = null;
			}
		}
		return iNumberOfPages;
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
		this.page_detail_contents = myDbUtil
				.fixSqlFieldValue(page_detail_contents);
	}

	/**
	 * @return Returns the page_detail_id.
	 */
	public int getPage_detail_id() {
		return page_detail_id;
	}

	/**
	 * @param page_detail_id
	 *            The page_detail_id to set.
	 */
	public void setPage_detail_id(int page_detail_id) {
		this.page_detail_id = page_detail_id;
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
		this.page_detail_title = myDbUtil.fixSqlFieldValue(page_detail_title);
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
	 * @return Returns the page_invocation.
	 */
	public String getPage_invocation() {
		return page_invocation;
	}

	/**
	 * @param page_invocation
	 *            The page_invocation to set.
	 */
	public void setPage_invocation(String page_invocation) {
		this.page_invocation = page_invocation;
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
		this.template_contents = myDbUtil.fixSqlFieldValue(template_contents);
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
	 * @return Returns the template_name.
	 */
	public String getTemplate_name() {
		return template_name;
	}

	/**
	 * @param template_name
	 *            The template_name to set.
	 */
	public void setTemplate_name(String template_name) {
		this.template_name = myDbUtil.fixSqlFieldValue(template_name);
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
	 * @return Returns the template_active_yn.
	 */
	public String getTemplate_active_yn() {
		return template_active_yn;
	}

	/**
	 * @param template_active_yn
	 *            The template_active_yn to set.
	 */
	public void setTemplate_active_yn(String template_active_yn) {
		this.template_active_yn = template_active_yn;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
	 */
	public void setPrimaryKey(String theKey) {
		this.setTemplate_id(Integer.parseInt(theKey));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	public String getMeta_tags() {
		return meta_tags;
	}

	public void setMeta_tags(String meta_tags) {
		this.meta_tags = meta_tags.replaceAll("'", "''");
	}

	public String getBrowser_title() {
		return browser_title;
	}

	public void setBrowser_title(String browser_title) {
		this.browser_title = browser_title.replaceAll("'", "''");
	}
}