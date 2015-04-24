// ------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-11
//Revisions
//------------------------------------------------------------------------------
//$Log: Comment.java,v $
//Revision 1.1.2.1.2.4.2.1  2007/03/30 17:22:15  tcs
//*** empty log message ***
//
//Revision 1.1.2.1.2.4  2007/02/02 14:19:24  tcs
//corrected javadocs
//
//Revision 1.1.2.1.2.3  2007/02/02 14:08:54  tcs
//Formatting
//
//Revision 1.1.2.1.2.2  2007/01/22 19:16:58  tcs
//Couple of new methods
//
//Revision 1.1.2.1.2.1  2007/01/16 16:31:13  tcs
//Added debugging stacktrace
//
//Revision 1.1.2.1  2007/01/12 19:29:47  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates comment table in db
 * 
 * @author tsawler
 * 
 */
public class Comment implements DatabaseInterface {

	private int comment_id = 0;
	private String subject = "";
	private String comment = "";
	private String comment_active_yn = "";
	private String username = "";
	private int story_id = 0;
	private int iOffset = 0;
	private int iLimit = 0;
	private String sCustomWhere = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	DBUtils myDbUtil = new DBUtils();

	public Comment() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateComment() throws SQLException, Exception {
		try {
			String update = "UPDATE pmc_stories_comments SET " + "subject = '"
					+ myDbUtil.fixSqlFieldValue(subject) + "', "
					+ "story_comment = '" + myDbUtil.fixSqlFieldValue(comment)
					+ "', " + "comment_active_yn = '" + comment_active_yn
					+ "' " + "WHERE comment_id = '" + comment_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Comment:updateComment:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Comment:updateComment:Exception:" + e.toString());
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
	public int addComment() throws SQLException, Exception {

		int new_story_id = 0;

		try {
			String save = "INSERT INTO pmc_stories_comments (" + "username, "
					+ "datetime, " + "story_id, " + "subject, "
					+ "story_comment, " + "comment_active_yn) " + "VALUES("
					+ "'" + myDbUtil.fixSqlFieldValue(username) + "', "
					+ "NOW(), " + "'" + story_id + "', " + "'"
					+ myDbUtil.fixSqlFieldValue(subject) + "', " + "'"
					+ myDbUtil.fixSqlFieldValue(comment) + "', " + "'"
					+ comment_active_yn + "')";
			// System.out.println("query : " + save);
			pst = conn.prepareStatement(save);
			pst.executeUpdate();

			String getLast = "select currval('pmc_stories_comments_comment_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);

			if (rs.next()) {
				new_story_id = rs.getInt(1);
			}

			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Comment:addComment:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Comment:addComment:Exception:" + e.toString());
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

		return new_story_id;
	}

	/**
	 * Returns commentfor supplied id
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getCommentById() throws SQLException, Exception {
		try {
			String getId = "select * from pmc_stories_comments where comment_id = '"
					+ comment_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(getId);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Comment:getCommentById:SQLException:"
					+ e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			Errors.addError("Comment:getCommentById:Exception:" + e.toString());
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
	 * Returns all items
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllComments() throws SQLException, Exception {
		try {
			String query = "select * from pmc_stories_comments ";
			if (sCustomWhere.length() > 6) {
				query += " " + sCustomWhere;
			}

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Comment:getAllComments:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Comment:getAllComments:Exception:" + e.toString());
		}
		return crs;
	}

	/**
	 * Returns all active comments for story id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllCommentsForStory() throws SQLException,
			Exception {
		try {
			String query = "select * from pmc_stories_comments where story_id = "
					+ story_id + " and comment_active_yn = 'y'";
			if (sCustomWhere.length() > 6) {
				query += " " + sCustomWhere;
			}
			query += " order by datetime asc";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Comment:getAllCommentsForStory:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Comment:getAllCommentsForStory:Exception:"
					+ e.toString());
		}
		return crs;
	}

	public RowSetDynaClass getAllCommentsDynaBean() throws SQLException,
			Exception {

		RowSetDynaClass resultset = null;

		try {
			String query = "select pmc_stories_comments.*, "
					+ "case when comment_active_yn = 'y' then '<span style=\"color: green\">active</span>' "
					+ "when comment_active_yn = 'p' then '<span style=\"color: orange\">pending</span>' "
					+ "else '<span style=\"color: red;\">inactive</a>' end as story_active_status "
					+ "from pmc_stories ";
			if (sCustomWhere.length() > 6) {
				query += " " + sCustomWhere;
			}

			st = conn.createStatement();
			rs = st.executeQuery(query);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Comment:getAllCommentsDynaBean:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Comment:getAllCommentsDynaBean:Exception:"
					+ e.toString());
		}
		return resultset;
	}

	/**
	 * Change active status of a comment
	 * 
	 * @throws Exception
	 */
	public void changeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update pmc_stories_comments set comment_active_yn = '"
					+ comment_active_yn
					+ "' where comment_id = '"
					+ comment_id
					+ "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Comment:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Comment:changeActiveStatus:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Delete a story by id
	 * 
	 * @throws Exception
	 */
	public void deleteComment() throws SQLException, Exception {
		try {
			String query = "delete from pmc_stories_comments where comment_id = '"
					+ comment_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Comment:deleteComment:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Comment:deleteComment:Exception:" + e.toString());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
	 */
	public void setPrimaryKey(String theKey) {
		this.setComment_id(Integer.parseInt(theKey));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		this.deleteComment();

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
		this.setComment_active_yn(psStatus);
		this.changeActiveStatus();

	}

	/**
	 * @return Returns the iLimit.
	 */
	public int getILimit() {
		return iLimit;
	}

	/**
	 * @param limit
	 *            The iLimit to set.
	 */
	public void setILimit(int limit) {
		iLimit = limit;
	}

	/**
	 * @return Returns the iOffset.
	 */
	public int getIOffset() {
		return iOffset;
	}

	/**
	 * @param offset
	 *            The iOffset to set.
	 */
	public void setIOffset(int offset) {
		iOffset = offset;
	}

	/**
	 * @return Returns the sCustomWhere.
	 */
	public String getSCustomWhere() {
		return sCustomWhere;
	}

	/**
	 * @param customWhere
	 *            The sCustomWhere to set.
	 */
	public void setSCustomWhere(String customWhere) {
		sCustomWhere = customWhere;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment_active_yn() {
		return comment_active_yn;
	}

	public void setComment_active_yn(String comment_active_yn) {
		this.comment_active_yn = comment_active_yn;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public int getStory_id() {
		return story_id;
	}

	public void setStory_id(int story_id) {
		this.story_id = story_id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}