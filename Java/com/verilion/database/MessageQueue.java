package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates message_queue table in db, and related operations.
 * <P>
 * October 11, 2005
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class MessageQueue {

	private int message_queue_id = 0;
	private String to = "";
	private String from = "";
	private String subject = "";
	private String message = "";
	private Date send_date;
	private Date sent_date;
	private String message_sent_yn = "n";
	private int message_queue_attachment_id = 0;
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;

	public MessageQueue() {

	}

	/**
	 * Returns all month ids and names.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet GetAllUnsentMessages() throws SQLException,
			Exception {
		try {
			String getAll = "select message_queue_id, message_queue_to, message_queue_from, "
					+ "message_queue_subject, message_queue_send_date, message_queue_sent_date, message_queue_message from message_queue where message_queue_sent_yn = 'n'";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllUnsentMessages:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllUnsentMessages:Exception:"
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

	public RowSetDynaClass GetAllUnsentMessagesDynaBean() throws SQLException,
			Exception {
		RowSetDynaClass resultset = null;
		try {
			String getAll = "select  message_queue_id, message_queue_to, message_queue_from, "
					+ "message_queue_subject, message_queue_send_date, message_queue_sent_date from message_queue where message_queue_sent_yn = 'n'";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllUnsentMessages:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllUnsentMessages:Exception:"
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

	public XDisconnectedRowSet GetAllSentMessages() throws SQLException,
			Exception {
		try {
			String getAll = "select message_queue_id, message_queue_to, message_queue_from, "
					+ "message_queue_subject, message_queue_send_date, message_queue_sent_date from message_queue where message_queue_sent_yn = 'y'";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllSentMessages:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllSentMessages:Exception:"
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

	public RowSetDynaClass GetAllSentMessagesDynaBean() throws SQLException,
			Exception {
		RowSetDynaClass resultset = null;
		try {
			String getAll = "select  message_queue_id, message_queue_to, message_queue_from, "
					+ "message_queue_subject, message_queue_send_date, message_queue_sent_date from message_queue where message_queue_sent_yn = 'y'";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllUnsentMessages:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllUnsentMessages:Exception:"
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

	public RowSetDynaClass GetAllMessagesDynaBean() throws SQLException,
			Exception {
		RowSetDynaClass resultset = null;
		try {
			String getAll = "select message_queue_id, message_queue_to, message_queue_from, "
					+ "message_queue_subject, message_queue_send_date, message_queue_sent_date  from message_queue";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllUnsentMessages:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:GetAllUnsentMessages:Exception:"
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

	public XDisconnectedRowSet GetMessageById() throws SQLException, Exception {
		try {
			String getAll = "select * from message_queue where message_queue_id = ?";
			pst = conn.prepareStatement(getAll);
			pst.setInt(1, this.getMessage_queue_id());
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

	public int InsertMessage() throws SQLException, Exception {
		int newId = 0;
		try {
			Timestamp t = new Timestamp(System.currentTimeMillis());
			String query = "insert into message_queue (message_queue_to, message_queue_from, message_queue_message, "
					+ "message_queue_send_date, message_queue_sent_yn, message_queue_subject,"
					+ "message_queue_attachment_id) values ("
					+ "?, ?, ?, ?, ?, ?, ?)";
			pst = conn.prepareStatement(query);
			pst.setString(1, this.getTo());
			pst.setString(2, this.getFrom());
			pst.setString(3, this.getMessage());
			pst.setTimestamp(4, t);
			pst.setString(5, this.getMessage_sent_yn());
			pst.setString(6, this.getSubject());
			pst.setInt(7, this.getMessage_queue_attachment_id());
			pst.executeUpdate();
			pst.close();
			pst = null;
			query = "select currval('message_queue_message_queue_id_seq')";
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
			if (st != null) {
				st.close();
				st = null;
			}
		}
		return newId;
	}

	public void DeleteMessageFromQueue() throws SQLException, Exception {
		try {
			String getAll = "delete from message_queue where message_queue_id = ?";
			pst = conn.prepareStatement(getAll);
			pst.setInt(1, this.getMessage_queue_id());
			pst.executeUpdate(getAll);
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:DeleteMessageFromQueue:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:DeleteMessageFromQueue:Exception:"
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

	public void MarkMessageSent() throws SQLException, Exception {
		try {
			Timestamp t = new Timestamp(System.currentTimeMillis());
			String getAll = "update message_queue set message_queue_sent_yn = 'y', message_queue_sent_date = ? where message_queue_id = ?";
			pst = conn.prepareStatement(getAll);
			pst.setTimestamp(1, t);
			pst.setInt(2, this.getMessage_queue_id());
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageQueue:DeleteMessageFromQueue:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageQueue:DeleteMessageFromQueue:Exception:"
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
	 * @return Returns the ct_month_id.
	 */
	public int getCt_month_id() {
		return message_queue_id;
	}

	/**
	 * @param ct_month_id
	 *            The ct_month_id to set.
	 */
	public void setCt_month_id(int ct_month_id) {
		this.message_queue_id = ct_month_id;
	}

	/**
	 * @return Returns the from.
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            The from to set.
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the message_queue_id.
	 */
	public int getMessage_queue_id() {
		return message_queue_id;
	}

	/**
	 * @param message_queue_id
	 *            The message_queue_id to set.
	 */
	public void setMessage_queue_id(int message_queue_id) {
		this.message_queue_id = message_queue_id;
	}

	/**
	 * @return Returns the send_date.
	 */
	public Date getSend_date() {
		return send_date;
	}

	/**
	 * @param send_date
	 *            The send_date to set.
	 */
	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	/**
	 * @return Returns the sent_date.
	 */
	public Date getSent_date() {
		return sent_date;
	}

	/**
	 * @param sent_date
	 *            The sent_date to set.
	 */
	public void setSent_date(Date sent_date) {
		this.sent_date = sent_date;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the to.
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            The to to set.
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return Returns the message_sent_yn.
	 */
	public String getMessage_sent_yn() {
		return message_sent_yn;
	}

	/**
	 * @param message_sent_yn
	 *            The message_sent_yn to set.
	 */
	public void setMessage_sent_yn(String message_sent_yn) {
		this.message_sent_yn = message_sent_yn;
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