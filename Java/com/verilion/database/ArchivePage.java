package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates archive_page table in db, and related operations.
 * <p>
 * July 08, 2004
 * <p>
 * 
 * @author tsawler
 * 
 */
public class ArchivePage implements DatabaseInterface {

	private int archive_page_id = 0;
	private String archive_page_name = "";
	private String archive_page_title = "";
	private String archive_page_content = "";
	private int parent_id = 0;
	private int template_id = 1;
	private int ct_access_level_id = 1;
	private String archive_page_active_yn = "n";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;
	private DBUtils myDbUtil = new DBUtils();
	public String sCustomWhere = "";
	public int iLimit = 0;
	public int iOffset = 0;

	public ArchivePage() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateArchivePage() throws SQLException, Exception {
		try {
			String update = "UPDATE archive_page SET "
					+ "archive_page_name = '"
					+ archive_page_name
					+ "', "
					+ "archive_page_title = '"
					+ archive_page_title
					+ "', "
					+ "archive_page_content = '"
					+ archive_page_content
					+ "', "
					+ "parent_id = '"
					+ parent_id
					+ "', "
					+ "template_id = '"
					+ template_id
					+ "', "
					+ "ct_access_level_id = '"
					+ ct_access_level_id
					+ "', "
					+ "archive_page_active_yn = '"
					+ archive_page_active_yn
					+ "' "
					+ "WHERE archive_page_id = '" + archive_page_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("ArchivePage:updateArchivePage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:updateArchivePage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Insert method.
	 * 
	 * @throws Exception
	 */
	public void addArchivePage() throws SQLException, Exception {
		try {
			String save = "INSERT INTO archive_page (" + "archive_page_name, "
					+ "archive_page_title, " + "archive_page_content, "
					+ "parent_id, " + "template_id, " + "ct_access_level_id, "
					+ "archive_page_active_yn) " + "VALUES(" + "'"
					+ archive_page_name + "', " + "'" + archive_page_title
					+ "', " + "'" + archive_page_content + "', " + "'"
					+ parent_id + "', " + "'" + template_id + "', " + "'"
					+ ct_access_level_id + "', " + "'" + archive_page_active_yn
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("ArchivePage:addArchivePage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:addArchivePage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns all archive page ids and names.
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getTopLevelArchivePageNamesIdsStatuses()
			throws SQLException, Exception {
		try {
			String GetCategory = "select archive_page_id, archive_page_name, "
					+ "ct_access_level_id, archive_page_active_yn "
					+ "from archive_page " + "where parent_id = 0";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("ArchivePage:getTopLevelArchivePageNamesIdsStatuses:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:getTopLevelArchivePageNamesIdsStatuses:Exception:"
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
	 * Returns all archive page ids and names.
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getArchivePageNamesIdsStatuses()
			throws SQLException, Exception {
		try {
			String GetCategory = "select archive_page_id, archive_page_name, "
					+ "ct_access_level_id, archive_page_active_yn from archive_page";
			st = conn.createStatement();
			rs = st.executeQuery(GetCategory);
			crs.populate(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			Errors.addError("ArchivePage:getArchivePageNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:getArchivePageNamesIds:Exception:"
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
	 * Deletes Archive Page by id.
	 * 
	 * @throws Exception
	 */
	public void deleteArchivePageById() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from archive_page where archive_page_id = "
					+ archive_page_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("ArchivePage:deleteArchivePageById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:deleteArchivePageById:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Added to support interface requirements. Forwards request to
	 * deleteArchivePageById
	 * 
	 */
	public void deleteRecord() {
		try {
			this.deleteArchivePageById();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change active status of record
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void changeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update archive_page "
					+ "set archive_page_active_yn = '" + archive_page_active_yn
					+ "' where archive_page_id = " + archive_page_id;
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("ArchivePage:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:changeActiveStatus:Exception:"
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
			String query = "update archive_page "
					+ "set archive_page_active_yn = '" + archive_page_active_yn
					+ "' where archive_page_id = " + archive_page_id;
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("ArchivePage:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:changeActiveStatus:Exception:"
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
	 * Returns record for supplied id.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getArchivePageById() throws SQLException,
			Exception {

		try {
			String query = "select * from archive_page where archive_page_id = '"
					+ archive_page_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("ArchivePage:getArchivePage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:getArchivePage:Exception:"
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
	 * Gets an archive page by name
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getArchivePageByName() throws SQLException,
			Exception {

		try {
			String query = "select * from archive_page where archive_page_name = '"
					+ archive_page_name + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("ArchivePage:getArchivePage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:getArchivePage:Exception:"
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
	 * Check for an existing archive page name
	 * 
	 * @return boolean
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean checkForExistingPageName() throws SQLException, Exception {
		boolean exists = false;
		try {
			String query = "select archive_page_id from archive_page where archive_page_name = '"
					+ archive_page_name + "'";
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
			Errors.addError("ArchivePage:checkForExistingPageName:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:checkForExistingPageName:Exception:"
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

	public int returnNumberOfPagesForTemplateId() throws SQLException,
			Exception {
		int iNumberOfPages = 0;
		try {
			String query = "select count(archive_page_id) from archive_page where template_id = '"
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
			Errors.addError("ArchivePage:checkForExistingPageName:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ArchivePage:checkForExistingPageName:Exception:"
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
		return iNumberOfPages;
	}

	public void setPrimaryKey(String piId) {
		this.archive_page_id = Integer.parseInt(piId);
	}

	/**
	 * @return Returns the archive_page_active_yn.
	 */
	public String getArchive_page_active_yn() {
		return archive_page_active_yn;
	}

	/**
	 * @param archive_page_active_yn
	 *            The archive_page_active_yn to set.
	 */
	public void setArchive_page_active_yn(String archive_page_active_yn) {
		this.archive_page_active_yn = archive_page_active_yn;
	}

	/**
	 * @return Returns the archive_page_content.
	 */
	public String getArchive_page_content() {
		return archive_page_content;
	}

	/**
	 * @param archive_page_content
	 *            The archive_page_content to set.
	 */
	public void setArchive_page_content(String archive_page_content) {
		this.archive_page_content = myDbUtil
				.fixSqlFieldValue(archive_page_content);
	}

	/**
	 * @return Returns the archive_page_id.
	 */
	public int getArchive_page_id() {
		return archive_page_id;
	}

	/**
	 * @param archive_page_id
	 *            The archive_page_id to set.
	 */
	public void setArchive_page_id(int archive_page_id) {
		this.archive_page_id = archive_page_id;
	}

	/**
	 * @return Returns the archive_page_name.
	 */
	public String getArchive_page_name() {
		return archive_page_name;
	}

	/**
	 * @param archive_page_name
	 *            The archive_page_name to set.
	 */
	public void setArchive_page_name(String archive_page_name) {
		this.archive_page_name = myDbUtil.fixSqlFieldValue(archive_page_name);
	}

	/**
	 * @return Returns the archive_page_title.
	 */
	public String getArchive_page_title() {
		return archive_page_title;
	}

	/**
	 * @param archive_page_title
	 *            The archive_page_title to set.
	 */
	public void setArchive_page_title(String archive_page_title) {
		this.archive_page_title = myDbUtil.fixSqlFieldValue(archive_page_title);
	}

	/**
	 * @return Returns the conn.
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @return Returns the ct_access_level_id.
	 */
	public int getCt_access_level_id() {
		return ct_access_level_id;
	}

	/**
	 * @param ct_access_level_id
	 *            The ct_access_level_id to set.
	 */
	public void setCt_access_level_id(int ct_access_level_id) {
		this.ct_access_level_id = ct_access_level_id;
	}

	/**
	 * @param conn
	 *            The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * @return Returns the parent_id.
	 */
	public int getParent_id() {
		return parent_id;
	}

	/**
	 * @param parent_id
	 *            The parent_id to set.
	 */
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
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
}