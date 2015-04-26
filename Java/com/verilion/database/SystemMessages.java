package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates system_messages table in db, and related operations.
 * <P>
 * Dec 04, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class SystemMessages {

	private int system_message_id = 0;
	private String system_message_name = "";
	private int ct_language_id = 0;

	private ResultSet rs = null;
	XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public SystemMessages() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateSystemMessage() throws SQLException, Exception {
		try {
			String update = "UPDATE system_messages SET "
					+ "system_message_name = '" + system_message_name + "' "
					+ "WHERE system_message_id = '" + system_message_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("SystemMessages:updateSystemMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("SystemMessages.updateSystemMessage:updateAccessLevel:Exception:"
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
	public void addSystemMessage() throws SQLException, Exception {
		try {
			String save = "INSERT INTO system_messages ("
					+ "system_message_name) " + "VALUES(" + "'"
					+ system_message_name + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("SystemMessages:addSystemMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("SystemMessages:addSystemMessage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns all system messages for a particular language id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllSystemMessagesForLanguageId()
			throws SQLException, Exception {
		try {
			String query = "select " + "sm.system_message_id, "
					+ "sm.system_message_name, "
					+ "smd.system_messages_detail_display_name "
					+ "smd.system_messages_detail_message, " + "from "
					+ "system_messages as sm, "
					+ "system_messages_detail as smd " + "where "
					+ "smd.ct_language_id = '" + ct_language_id + "' " + "and "
					+ "sm.system_message_id = smd.system_message_id "
					+ "order by system_messages_detail_display_name";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("SystemMessages:getAllSystemMessagesForLanguageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("SystemMessages:getAllSystemMessagesForLanguageId:Exception:"
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
	 * Returns all system message names and ids for a particular language id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllSystemMessageNamesIdsForLanguageId()
			throws SQLException, Exception {
		try {
			String query = "select " + "sm.system_message_detail_id, "
					+ "smd.system_messages_detail_display_name "
					+ "from system_messages as sm, "
					+ "system_messages_detail as smd "
					+ "where smd.ct_language_id = '" + ct_language_id + "' "
					+ "and sm.system_message_id = smd.system_message_id "
					+ "order by system_messages_detail_display_name";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("SystemMessages:getAllSystemMessagesForLanguageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("SystemMessages:getAllSystemMessagesForLanguageId:Exception:"
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
	 * Deletes system message record by id.
	 * 
	 * @throws Exception
	 */
	public void deleteSystemMessageById() throws SQLException, Exception {
		try {
			String deleteRecord = "delete from system_messages where system_message_id = "
					+ system_message_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("SystemMessages:deleteSystemMessageById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("SystemMessages:deleteSystemMessageById:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns system message by name and language id
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getSystemMessageByNameAndLanguageId() throws SQLException,
			Exception {
		String theMessage = "";
		try {
			String query = "select smd.system_messages_detail_message from "
					+ "system_messages as sm, "
					+ "system_messages_detail as smd " + "where "
					+ "sm.system_message_name = '" + system_message_name + "' "
					+ "and sm.system_message_id = smd.system_message_id "
					+ "and smd.ct_language_id = '" + ct_language_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theMessage = rs.getString("system_messages_detail_message");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("SystemMessages:getSystemMessageByNameAndLanguageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("SystemMessages:getSystemMessageByNameAndLanguageId:Exception:"
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
		return theMessage;
	}

	/**
	 * Returns record for supplied id.
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getSystemMessageByIdAndLanaguageId() throws SQLException,
			Exception {
		ResultSet rs = null;
		String theMessage = "";
		try {
			String query = "select " + "smd.system_messages_detail_message "
					+ "from " + "system_messages as sm, "
					+ "system_messages_detail as smd " + "where "
					+ "sm.system_message_id = '" + system_message_id + "' "
					+ "and sm.system_message_id = smd.system_message_id "
					+ "and smd.ct_language_id = '" + ct_language_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theMessage = rs.getString("system_messages_detail_message");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("SystemMessages:getSystemMessageByIdAndLanaguageId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("SystemMessages:getSystemMessageByIdAndLanaguageId:Exception:"
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
		return theMessage;
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
	 * @return Returns the system_message_id.
	 */
	public int getSystem_message_id() {
		return system_message_id;
	}

	/**
	 * @param system_message_id
	 *            The system_message_id to set.
	 */
	public void setSystem_message_id(int system_message_id) {
		this.system_message_id = system_message_id;
	}

	/**
	 * @return Returns the system_message_name.
	 */
	public String getSystem_message_name() {
		return system_message_name;
	}

	/**
	 * @param system_message_name
	 *            The system_message_name to set.
	 */
	public void setSystem_message_name(String system_message_name) {
		this.system_message_name = system_message_name;
	}
}