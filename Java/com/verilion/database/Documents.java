package com.verilion.database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates documents table in db, and related operations.
 * <p>
 * July 21, 2004
 * <p>
 * 
 * @author tsawler
 * 
 */
public class Documents {

	private int document_id = 0;
	private String document_name = "";
	private String document_title = "";
	private String document_mime_type = "";
	private String document_active_yn = "";
	private int page_id = 0;
	private int owner_id = 1;
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;
	private DBUtils myDbUtil = new DBUtils();

	public Documents() {
		super();
	}

	/**
	 * Update method. Takes full pathname/filename of doc as arg.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateDocument(String myFile) throws SQLException, Exception {
		try {
			String update = "UPDATE documents SET " + "document_mime_type = '"
					+ document_mime_type + "', " + "document_data = " + "?"
					+ ", " + "document_name = '" + document_name + "', "
					+ "document_title = '" + document_title + "', "
					+ "page_id = '" + page_id + "', " + "owner_id = '"
					+ owner_id + "' " + "WHERE document_id = '" + document_id
					+ "'";

			File theFile = new File(myFile);
			FileInputStream fis = new FileInputStream(theFile);

			pst = conn.prepareStatement(update);
			pst.setBinaryStream(1, fis, (int) theFile.length());
			pst.executeUpdate();
			pst.close();
			pst = null;
			theFile.delete();
			fis.close();
		} catch (SQLException e) {
			Errors.addError("Documents:updateDocument:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:updateDocument:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Update document info, but not bytea column.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateDocumentInfo() throws SQLException, Exception {
		try {
			String update = "UPDATE documents SET " + "document_mime_type = '"
					+ document_mime_type + "', " + "document_name = '"
					+ document_name + "', " + "document_title = '"
					+ document_title + "', " + "page_id = '" + page_id + "', "
					+ "owner_id = '" + owner_id + "' "
					+ "WHERE document_id = '" + document_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Documents:updateDocumentInfo:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:updateDocumentInfo:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Insert method. Takes full pathname/filename of file to add as arg.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public int addDocument(String myFile) throws SQLException, Exception {
		int newDocumentId = 0;
		try {
			String save = "INSERT INTO documents (" + "document_mime_type, "
					+ "document_data, " + "document_name, " + "page_id, "
					+ "owner_id, " + "document_title, "
					+ "document_active_yn) " + "VALUES(" + "'"
					+ document_mime_type + "', " + "?" + ", " + "'"
					+ document_name + "', " + "'" + page_id + "', " + "'"
					+ owner_id + "', " + "'" + document_title + "', " + "'"
					+ document_active_yn + "')";

			File theFile = new File(myFile);
			FileInputStream fis = new FileInputStream(theFile);

			pst = conn.prepareStatement(save);
			pst.setBinaryStream(1, fis, (int) theFile.length());
			pst.executeUpdate();

			String getLast = "select currval('document_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);
			if (rs.next()) {
				newDocumentId = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;
			theFile.delete();
			fis.close();
		} catch (SQLException e) {
			Errors.addError("Documents:addDocument:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:addDocument:Exception:" + e.toString());
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
		return newDocumentId;
	}

	/**
	 * Deletes Document by id.
	 * 
	 * @throws Exception
	 * @throws SQLException
	 */
	public void deleteDocument() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from documents where document_id = "
					+ document_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Documents:deleteDocument:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:deleteDocument:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Deletes all docs for page id
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deleteDocumentsForPageId() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from documents where page_id = "
					+ page_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Documents:deleteDocumentsForPageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:deleteDocumentsForPageId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns record for supplied id.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getDocumentById() throws SQLException, Exception {

		try {
			String query = "select " + "document_name, " + "document_title, "
					+ "document_active_yn, " + "page_id, " + "owner_id, "
					+ "document_mime_type "
					+ "from documents where document_id = '" + document_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Documents:getDocumentById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:getDocumentById:Exception:"
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
	 * Returns all document names/titles/ids for a given page id.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllDocumentsForPageId() throws SQLException,
			Exception {

		try {
			String query = "select document_id, document_name, document_title,"
					+ "document_active_yn "
					+ "from documents where page_id = '" + page_id + "' "
					+ "order by document_title";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Documents:getAllDocumentsForPageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:getAllDocumentsForPageId:Exception:"
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
	 * Returns all active docs for the supplied page id
	 * 
	 * @return XDisconnectedResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveDocumentsForPageId()
			throws SQLException, Exception {

		try {
			String query = "select document_id, document_name, document_title,"
					+ "document_active_yn "
					+ "from documents where page_id = '" + page_id + "' "
					+ "and document_active_yn = 'y' "
					+ "order by document_title";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Documents:getAllDocumentsForPageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:getAllDocumentsForPageId:Exception:"
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
	 * Get all active names/ids
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveDocumentNamesIds()
			throws SQLException, Exception {

		try {
			String query = "select document_id, document_name "
					+ "from documents where document_active_yn = 'y' "
					+ "order by document_title";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Documents:getAllActiveDocumentNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:getAllActiveDocumentNamesIds:Exception:"
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
	 * Get all document names/ids, sorted by title
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllDocumentNamesIds() throws SQLException,
			Exception {

		try {
			String query = "select document_id, document_name "
					+ "from documents " + "order by document_title";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Documents:getAllDocumentNamesIds:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:getAllDocumentNamesIds:Exception:"
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
	 * Change active status of a document
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void changeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update documents set document_active_yn = '"
					+ document_active_yn + "' where document_id = '"
					+ document_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Documents:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:changeActiveStatus:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns true if a page has documents.
	 * 
	 * @return boolean
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean hasAttachments() throws SQLException, Exception {
		boolean foundDocs = false;
		try {
			String query = "select count(document_id) from "
					+ "documents where " + "page_id = '" + page_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					foundDocs = true;
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Documents:hasAttachments:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Documents:hasAttachments:Exception:"
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
		return foundDocs;
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
	 * @return Returns the document_active_yn.
	 */
	public String getDocument_active_yn() {
		return document_active_yn;
	}

	/**
	 * @param document_active_yn
	 *            The document_active_yn to set.
	 */
	public void setDocument_active_yn(String document_active_yn) {
		this.document_active_yn = document_active_yn;
	}

	/**
	 * @return Returns the document_id.
	 */
	public int getDocument_id() {
		return document_id;
	}

	/**
	 * @param document_id
	 *            The document_id to set.
	 */
	public void setDocument_id(int document_id) {
		this.document_id = document_id;
	}

	/**
	 * @return Returns the document_mime_type.
	 */
	public String getDocument_mime_type() {
		return document_mime_type;
	}

	/**
	 * @param document_mime_type
	 *            The document_mime_type to set.
	 */
	public void setDocument_mime_type(String document_mime_type) {
		this.document_mime_type = document_mime_type;
	}

	/**
	 * @return Returns the document_name.
	 */
	public String getDocument_name() {
		return document_name;
	}

	/**
	 * @param document_name
	 *            The document_name to set.
	 */
	public void setDocument_name(String document_name) {
		this.document_name = myDbUtil.fixSqlFieldValue(document_name);
	}

	/**
	 * @return Returns the document_title.
	 */
	public String getDocument_title() {
		return document_title;
	}

	/**
	 * @param document_title
	 *            The document_title to set.
	 */
	public void setDocument_title(String document_title) {
		this.document_title = myDbUtil.fixSqlFieldValue(document_title);
	}

	/**
	 * @return Returns the owner_id.
	 */
	public int getOwner_id() {
		return owner_id;
	}

	/**
	 * @param owner_id
	 *            The owner_id to set.
	 */
	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
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
}