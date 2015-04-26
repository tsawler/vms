package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates customer_messages table in DB
 * 
 * Feb 08 2007
 * 
 * @author tsawler
 * 
 */
public class CustomerMessages implements DatabaseInterface {

	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private String sCustomWhere = " where true ";
	private int customer_messages_id = 0;
	private int from_id = 0;
	private int to_id = 0;
	private String messageText = "";
	private String subjectText = "";
	private String isReadYn = "";
	private String username = "";
	private int customer_message_sent_id = 0;

	public CustomerMessages() {

	}

	/**
	 * Adds a message.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addMessage() throws SQLException, Exception {
		try {
			String save = "INSERT INTO customer_messages( "
					+ "from_user_id, to_user_id, message, is_read_yn, subject) "
					+ "VALUES (?, ?, ?, ?, ?);";
			pst = conn.prepareStatement(save);
			pst.setInt(1, from_id);
			pst.setInt(2, to_id);
			pst.setString(3, messageText);
			pst.setString(4, isReadYn);
			pst.setString(5, subjectText);

			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:addMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:addMessage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void deleteMessage() throws SQLException, Exception {
		try {
			String save = "DELETE FROM customer_messages where customer_message_id = ? and to_user_id = ?;";
			pst = conn.prepareStatement(save);
			pst.setInt(1, customer_messages_id);
			pst.setInt(2, to_id);

			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:deleteMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:deleteMessage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void deleteSentMessage() throws SQLException, Exception {
		try {
			String save = "DELETE FROM customer_messages_sent where customer_message_sent_id = ? and from_user_id = ?;";
			pst = conn.prepareStatement(save);
			pst.setInt(1, customer_message_sent_id);
			pst.setInt(2, from_id);

			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:deleteSentMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:deleteSentMessage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void addSentMessage() throws SQLException, Exception {
		try {
			String save = "INSERT INTO customer_messages_sent ( "
					+ "from_user_id, to_user_id, message, subject) "
					+ "VALUES (?, ?, ?, ?);";
			pst = conn.prepareStatement(save);
			pst.setInt(1, from_id);
			pst.setInt(2, to_id);
			pst.setString(3, messageText);
			pst.setString(4, subjectText);

			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:addSentMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:addSentMessage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void addMessageForUsername() throws SQLException, Exception {
		try {
			String save = "INSERT INTO customer_messages( "
					+ "from_user_id, to_user_id, message, is_read_yn, subject) "
					+ "VALUES (?, (select customer_id from customer where username = ?), ?, ?, ?);";
			pst = conn.prepareStatement(save);
			pst.setInt(1, from_id);
			pst.setString(2, username);
			pst.setString(3, messageText);
			pst.setString(4, isReadYn);
			pst.setString(5, subjectText);

			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:addMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:addMessage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public String addMessageForUsernamePublic() throws SQLException, Exception {

		String error = "";
		try {
			boolean foundUser = false;
			String save = "select customer_id from customer where username = ?";
			pst = conn.prepareStatement(save);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				foundUser = true;
			}
			rs.close();
			rs = null;
			pst.close();
			pst = null;
			if (foundUser) {
				save = "INSERT INTO customer_messages( "
						+ "from_user_id, to_user_id, message, is_read_yn, subject) "
						+ "VALUES (?, (select customer_id from customer where username = ?), ?, ?, ?);";
				pst = conn.prepareStatement(save);
				pst.setInt(1, from_id);
				pst.setString(2, username);
				pst.setString(3, messageText);
				pst.setString(4, isReadYn);
				pst.setString(5, subjectText);

				pst.executeUpdate();
				pst.close();
				pst = null;
			} else {
				return "No user found";
			}
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:addMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:addMessage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return error;
	}

	public void addSentMessageForUsername() throws SQLException, Exception {
		try {
			String save = "INSERT INTO customer_messages_sent( "
					+ "from_user_id, to_user_id, message, subject) "
					+ "VALUES (?, (select customer_id from customer where username = ?), ?, ?);";
			pst = conn.prepareStatement(save);
			pst.setInt(1, from_id);
			pst.setString(2, username);
			pst.setString(3, messageText);
			pst.setString(4, subjectText);

			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:addMessage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:addMessage:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Gets all messages to a particular customer_id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllMessagesForCustomerId()
			throws SQLException, Exception {
		try {
			String query = "select * from customer_messages where "
					+ sCustomWhere
					+ " and to_user_id = ? order by date_time desc";

			pst = conn.prepareStatement(query);
			pst.setInt(1, to_id);
			rs = pst.executeQuery();
			crs.populate(rs);
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:getAllMessagesForCustomerId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:getAllMessagesForCustomerId:Exception:"
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

	public RowSetDynaClass getAllMessagesForCustomerIdDyaBean()
			throws SQLException, Exception {

		RowSetDynaClass resultset = null;

		try {
			String query = "select '<input type=\"checkbox\" name=\"del_' || customer_message_id || '\" />' as checkbox, customer_message_id, case when subject = '' then 'no subject' else subject end as subject, "
					+ "time_sent, date_sent, (select username from customer where customer_id = from_user_id) as from_name, "
					+ "case when is_read_yn = 'y' then 'read' else '<strong>unread</strong>' end as status "
					+ "from customer_messages "
					+ sCustomWhere
					+ " and to_user_id = ? order by date_sent desc";

			pst = conn.prepareStatement(query);
			pst.setInt(1, to_id);
			rs = pst.executeQuery();
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:getAllMessagesForCustomerIdDyaBean:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:getAllMessagesForCustomerIdDyaBean:Exception:"
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
		return resultset;
	}

	public RowSetDynaClass getAllSentMessagesForCustomerIdDyaBean()
			throws SQLException, Exception {

		RowSetDynaClass resultset = null;

		try {
			String query = "select '<input type=\"checkbox\" name=\"del_' || customer_message_sent_id  || '\" />' as checkbox, customer_message_sent_id, case when subject = '' then 'no subject' else subject end as subject, "
					+ "time_sent, date_sent, (select username from customer where customer_id = to_user_id) as to_name "
					+ "from customer_messages_sent "
					+ sCustomWhere
					+ " and from_user_id = ? order by date_sent desc";

			pst = conn.prepareStatement(query);
			pst.setInt(1, to_id);
			rs = pst.executeQuery();
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:getAllSentMessagesForCustomerIdDyaBean:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:getAllSentMessagesForCustomerIdDyaBean:Exception:"
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
		return resultset;
	}

	/**
	 * Get number of unread messages for customer id
	 * 
	 * @return number of unread messages
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getNewMessageCountForCustomerId() throws SQLException, Exception {

		int messageCount = 0;
		try {
			String query = "select count(customer_message_id) from customer_messages "
					+ sCustomWhere + " and to_user_id = ? and is_read_yn = 'n'";

			pst = conn.prepareStatement(query);
			pst.setInt(1, to_id);
			rs = pst.executeQuery();
			if (rs.next()) {
				messageCount = rs.getInt(1);
			}
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:getNewMessageCountForCustomerId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:getNewMessageCountForCustomerId:Exception:"
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
		return messageCount;
	}

	/**
	 * Gets a single message
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getOneMessageById() throws SQLException,
			Exception {
		try {
			String query = "select cm.*, (select username from customer where customer_id = from_user_id) as from_name "
					+ "from customer_messages cm where customer_message_id = ?";

			pst = conn.prepareStatement(query);
			pst.setInt(1, customer_messages_id);
			rs = pst.executeQuery();
			crs.populate(rs);
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:getOneMessageById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:getOneMessageById:Exception:"
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

	public XDisconnectedRowSet getOneSentMessageById() throws SQLException,
			Exception {
		try {
			String query = "select cm.*, (select username from customer where customer_id = to_user_id) as to_name "
					+ "from customer_messages_sent cm where customer_message_sent_id = ?";

			pst = conn.prepareStatement(query);
			pst.setInt(1, customer_message_sent_id);
			rs = pst.executeQuery();
			crs.populate(rs);
			rs.close();
			rs = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:getOneSentMessageById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:getOneSentMessageById:Exception:"
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

	public void setMessageToRead(int inId) throws SQLException, Exception {
		try {
			String query = "update customer_messages set is_read_yn = 'y' where customer_message_id = ?";

			pst = conn.prepareStatement(query);
			pst.setInt(1, customer_messages_id);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerMessages:setMessageToRead:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerMessages:setMessageToRead:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public int getCustomer_messages_id() {
		return customer_messages_id;
	}

	public void setCustomer_messages_id(int customer_messages_id) {
		this.customer_messages_id = customer_messages_id;
	}

	public int getFrom_id() {
		return from_id;
	}

	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}

	public String getIsReadYn() {
		return isReadYn;
	}

	public void setIsReadYn(String isReadYn) {
		this.isReadYn = isReadYn;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getSCustomWhere() {
		return sCustomWhere;
	}

	public void setSCustomWhere(String customWhere) {
		sCustomWhere = customWhere;
	}

	public String getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
	}

	public int getTo_id() {
		return to_id;
	}

	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}

	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {
		// do nothing - not appropriate here. Just implement this method
		// to satisfy implementing DatabaseInterface

	}

	public void createCustomWhere(String psCustomWhere) {
		this.sCustomWhere += psCustomWhere;
	}

	public void deleteRecord() throws SQLException, Exception {
		String query = "delete from customer_messages where customer_messages_id = ?";
		pst = conn.prepareStatement(query);
		pst.setInt(1, customer_messages_id);
		pst.executeUpdate();
		pst.close();
		pst = null;
	}

	public void setPrimaryKey(String theKey) {
		this.setCustomer_messages_id(Integer.parseInt(theKey));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCustomer_message_sent_id() {
		return customer_message_sent_id;
	}

	public void setCustomer_message_sent_id(int customer_message_sent_id) {
		this.customer_message_sent_id = customer_message_sent_id;
	}

}