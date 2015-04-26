package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates message_archive table in db, and related operations.
 * <P>
 * March 21, 2006
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class MessageArchive {

	private int message_archive_id = 0;
	private String message = "";
	private Date sent_date;
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;

	public MessageArchive() {

	}

	/**
	 * Returns all month ids and names.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet GetAllMessages() throws SQLException, Exception {
		try {
			String getAll = "select * from message_archive order by date_sent";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageArchive:GetAllMessages:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageArchive:GetAllMessages:Exception:"
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

	public RowSetDynaClass GetAllMessagesDynaBean() throws SQLException,
			Exception {
		RowSetDynaClass resultset = null;
		try {
			String getAll = "select message_archive_id, date_sent, replace(replace(replace(replace(replace(replace("
					+ "substring(message from 1 for 100), '<p>', ''), '</p>', ''), '<br>', ''), "
					+ "'<br/>',''), '&nbsp;',''), '<div>','') || '...' as message from cappdt.message_archive"
					+ " order by date_sent desc";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageArchive:GetAllMessagesDynaBean:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageArchive:GetAllMessagesDynaBean:Exception:"
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
			String getAll = "select * from message_archive where message_archive_id = ?";
			pst = conn.prepareStatement(getAll);
			pst.setInt(1, this.getMessage_queue_id());
			rs = pst.executeQuery();
			crs.populate(rs);
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("com.verilion.database.MessageArchive:GetMessageById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageArchive:GetMessageById:Exception:"
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
			String query = "insert into message_archive (message) values (?)";
			pst = conn.prepareStatement(query);
			pst.setString(1, this.getMessage());
			pst.executeUpdate();
			pst.close();
			pst = null;
			query = "select currval('message_archive_message_archive_id_seq')";
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
			Errors.addError("com.verilion.database.MessageArchive:InsertMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("com.verilion.database.MessageArchive:InsertMessage:Exception:"
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
		return message_archive_id;
	}

	/**
	 * @param ct_month_id
	 *            The ct_month_id to set.
	 */
	public void setCt_month_id(int ct_month_id) {
		this.message_archive_id = ct_month_id;
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
		return message_archive_id;
	}

	/**
	 * @param message_queue_id
	 *            The message_queue_id to set.
	 */
	public void setMessage_queue_id(int message_queue_id) {
		this.message_archive_id = message_queue_id;
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

}