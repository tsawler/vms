package com.verilion.database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates message_queue_attachment table in db, and related operations.
 * <P>
 * October 12, 2005
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class MessageQueueAttachment {

	private int message_queue_attachment_id = 0;
	private Connection conn;
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	public MessageQueueAttachment() {

	}

	public int InsertMessage(String pFile) throws SQLException, Exception {
		int newId = 0;
		try {
			String query = "insert into message_queue_attachment (message_queue_attachment) values (?)";
			File theFile = new File(pFile);
			FileInputStream fis = new FileInputStream(theFile);
			pst = conn.prepareStatement(query);
			pst.setBinaryStream(1, fis, (int) theFile.length());
			pst.executeUpdate();
			pst.close();
			pst = null;
			query = "select currval('message_queue_attachment_message_queue_attachment_id_seq')";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			if (rs.next()) {
				newId = rs.getInt(1);
			}
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:GetMessageById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:GetMessageById:Exception:"
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
		return newId;
	}

	public XDisconnectedRowSet GetMessageAttachment(int id)
			throws SQLException, Exception {
		XDisconnectedRowSet crs = new XDisconnectedRowSet();
		try {
			String query = "select * from message_queue_attachment where message_queue_attachment_id = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			crs.populate(rs);
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:GetMessageById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:GetMessageById:Exception:"
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
		return crs;
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
	 * @return Returns the message_queue_attachment_id.
	 */
	public int getMessage_queue_attachment_id() {
		return message_queue_attachment_id;
	}

	/**
	 * @param message_queue_attachment_id
	 *            The message_queue_attachment_id to set.
	 */
	public void setMessage_queue_attachment_id(int message_queue_attachment_id) {
		this.message_queue_attachment_id = message_queue_attachment_id;
	}
}