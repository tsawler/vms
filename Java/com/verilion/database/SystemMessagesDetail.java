//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: SystemMessagesDetail.java,v $
//Revision 1.4  2004/10/26 15:35:25  tcs
//Improved javadocs
//
//Revision 1.3  2004/06/25 18:56:29  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:07  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:46  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates system_messages_detail table in db, and related operations.
 * <P>
 * Dec 05, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 *  
 */
public class SystemMessagesDetail {

	private int system_messages_detail_id = 0;
	private int system_message_id = 0;
	private String system_messages_detail_display_name = "";
	private String system_messages_detail_message = "";
	private int ct_language_id = 0;

	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	public SystemMessagesDetail() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateSystemMessagesDetail()
		throws SQLException, Exception {
		try {
			String update =
				"UPDATE system_messages_detail SET "
					+ "system_message_id = '"
					+ system_message_id
					+ "', "
					+ "system_messages_detail_display_name = '"
					+ system_messages_detail_display_name
					+ "', "
					+ "system_messages_detail_message = '"
					+ system_messages_detail_message
					+ ", "
					+ "ct_language_id = '"
					+ ct_language_id
					+ "' "
					+ "WHERE system_messages_detail_id = '"
					+ system_messages_detail_id
					+ "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"SystemMessagesDetail:updateSystemMessagesDetail:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"SystemMessagesDetail.updateSystemMessagesDetail:updateAccessLevel:Exception:"
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
	public void addSystemMessagesDetail()
		throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO system_messages_detail ("
					+ "system_message_id, "
					+ "system_messages_detail_display_name, "
					+ "system_messages_detail_message, "
					+ "ct_language_id) "
					+ "VALUES("
					+ "'"
					+ system_message_id
					+ "', "
					+ system_messages_detail_display_name
					+ "', "
					+ "'"
					+ system_messages_detail_message
					+ "', "
					+ "'"
					+ ct_language_id
					+ "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"SystemMessagesDetail:addSystemMessagesDetail:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"SystemMessagesDetail:addSystemMessagesDetail:Exception:"
					+ e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Deletes system message detail record by id.
	 * 
	 * @throws Exception
	 */
	public void deleteSystemMessagesDetailById()
		throws SQLException, Exception {
		try {
			String deleteRecord =
				"delete from system_messages_detail where system_message_detail_id = "
					+ system_messages_detail_id;
			pst = conn.prepareStatement(deleteRecord);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"SystemMessagesDetail:deleteSystemMessagesDetailById:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"SystemMessagesDetail:deleteSystemMessagesDetailById:Exception:"
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
	public XDisconnectedRowSet getSystemMessagesDetailById()
		throws SQLException, Exception {

		try {
			String query =
				"select * from system_messages_detail where system_messages_detail_id = '"
					+ system_messages_detail_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"SystemMessagesDetail:getSystemMessagesDetailById:SQLException:"
					+ e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"SystemMessagesDetail:getSystemMessagesDetailById:Exception:"
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
	 * @return Returns the conn.
	 */
	public Connection getConn() {
		return conn;
	}
	/**
	 * @param conn The conn to set.
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
	 * @param ct_language_id The ct_language_id to set.
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
	 * @param system_message_id The system_message_id to set.
	 */
	public void setSystem_message_id(int system_message_id) {
		this.system_message_id = system_message_id;
	}
	/**
	 * @return Returns the system_messages_detail_display_name.
	 */
	public String getSystem_messages_detail_display_name() {
		return system_messages_detail_display_name;
	}
	/**
	 * @param system_messages_detail_display_name The system_messages_detail_display_name to set.
	 */
	public void setSystem_messages_detail_display_name(
			String system_messages_detail_display_name) {
		this.system_messages_detail_display_name = system_messages_detail_display_name;
	}
	/**
	 * @return Returns the system_messages_detail_id.
	 */
	public int getSystem_messages_detail_id() {
		return system_messages_detail_id;
	}
	/**
	 * @param system_messages_detail_id The system_messages_detail_id to set.
	 */
	public void setSystem_messages_detail_id(int system_messages_detail_id) {
		this.system_messages_detail_id = system_messages_detail_id;
	}
	/**
	 * @return Returns the system_messages_detail_message.
	 */
	public String getSystem_messages_detail_message() {
		return system_messages_detail_message;
	}
	/**
	 * @param system_messages_detail_message The system_messages_detail_message to set.
	 */
	public void setSystem_messages_detail_message(
			String system_messages_detail_message) {
		this.system_messages_detail_message = system_messages_detail_message;
	}
}