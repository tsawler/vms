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
 * Manipulates news table in db
 * 
 * @author tsawler
 * 
 */
public class NewsItem implements DatabaseInterface {

	private int news_id = 0;
	private String news_title = "";
	private String news_teaser_text = "";
	private String news_active_yn = "";
	private String news_body_text = "";
	private String news_start_date = "";
	private String news_end_date = "9999-01-01";
	private int news_author_id = 0;
	private int news_categories_id = 0;
	private int iOffset = 0;
	private int iLimit = 0;
	private String sCustomWhere = "";
	private String meta_tags = "";
	private String browser_title = "";

	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	DBUtils myDbUtil = new DBUtils();

	public NewsItem() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateNews() throws SQLException, Exception {
		try {
			String update = "UPDATE news SET " + "news_title = '"
					+ myDbUtil.fixSqlFieldValue(news_title) + "', "
					+ "news_teaser_text = '"
					+ myDbUtil.fixSqlFieldValue(news_teaser_text) + "', "
					+ "news_body_text = '"
					+ myDbUtil.fixSqlFieldValue(news_body_text) + "', "
					+ "news_start_date = '" + news_start_date + "', "
					+ "news_end_date = '" + news_end_date + "', "
					+ "news_active_yn = '" + news_active_yn + "', "
					+ "news_author_id = '" + news_author_id + "', "
					+ "browser_title = '" + browser_title.replaceAll("'", "''")
					+ "', " + "meta_tags = '" + meta_tags.replaceAll("'", "''")
					+ "', " + "news_categories_id = '" + news_categories_id
					+ "' " + "WHERE news_id = '" + news_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("News:updateNews:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("News:updateNews:Exception:" + e.toString());
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
	public int addNews() throws SQLException, Exception {

		int new_news_id = 0;

		try {
			String save = "INSERT INTO news (" + "news_title, "
					+ "news_teaser_text, " + "news_body_text, "
					+ "news_start_date, " + "news_end_date, "
					+ "news_active_yn, " + "news_author_id, "
					+ "news_categories_id, " + "browser_title, meta_tags) "
					+ "VALUES(" + "'"
					+ myDbUtil.fixSqlFieldValue(news_title)
					+ "', "
					+ "'"
					+ myDbUtil.fixSqlFieldValue(news_teaser_text)
					+ "', "
					+ "'"
					+ myDbUtil.fixSqlFieldValue(news_body_text)
					+ "', "
					+ "'"
					+ news_start_date
					+ "', "
					+ "'"
					+ news_end_date
					+ "', "
					+ "'"
					+ news_active_yn
					+ "', "
					+ "'"
					+ news_author_id
					+ "', "
					+ "'"
					+ news_categories_id
					+ "', "
					+ "'"
					+ browser_title.replaceAll("'", "''")
					+ "',"
					+ "'"
					+ meta_tags.replaceAll("'", "''") + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();

			String getLast = "select currval('news_news_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);

			if (rs.next()) {
				new_news_id = rs.getInt(1);
			}

			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("News:addNews:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("News:addNews:Exception:" + e.toString());
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

		return new_news_id;
	}

	/**
	 * Returns news item for supplied news id
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getNewsItemById() throws SQLException, Exception {
		try {
			String getId = "select * from news where news_id = '" + news_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(getId);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("News:getNewsItemById:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("News:getNewsItemById:Exception:" + e.toString());
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
	 * Returns all news items ordered by start date
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllNewsItems() throws SQLException, Exception {
		try {
			String query = "select * from news ";
			if (sCustomWhere.length() > 6) {
				query += " " + sCustomWhere;
			}
			query += " order by news_start_date desc";
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
			Errors.addError("News:getAllNewsItems:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("News:getAllNewsItems:Exception:" + e.toString());
		}
		return crs;
	}

	public RowSetDynaClass getAllNewsItemsDynaBean() throws SQLException,
			Exception {

		RowSetDynaClass resultset = null;

		try {
			String query = "select news.*, "
					+ "case when news_active_yn = 'y' then '<span style=\"color: green\">active</span>' "
					+ "else '<span style=\"color: red;\">inactive</a>' end as news_active_status "
					+ "from news ";
			if (sCustomWhere.length() > 6) {
				query += " " + sCustomWhere;
			}
			query += " order by news_start_date desc";
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
			Errors.addError("News:getAllNewsItemsDynaBean:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("News:getAllNewsItemsDynaBean:Exception:"
					+ e.toString());
		}
		return resultset;
	}

	/**
	 * Returns all active news items ordered by start date
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveNewsItems() throws SQLException,
			Exception {
		try {
			String query = "select * from news where news_active_yn = 'y' order by news_start_date";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("News:getAllActiveNewsItems:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("News:getAllActiveNewsItems:Exception:"
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
	 * Returns all active news items for a category ordered by start date
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveNewsItemsForCategory()
			throws SQLException, Exception {
		try {
			String query = "select * from news where news_active_yn = 'y' "
					+ "and news_categories_id = '" + news_categories_id + "' "
					+ "and news_start_date <= NOW() and "
					+ "news_end_date >= NOW() "
					+ "order by news_start_date desc";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("News:getAllActiveNewsItemsForCategory:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("News:getAllActiveNewsItemsForCategory:Exception:"
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
	 * returns number of active items for a category
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getCountForCategory() throws SQLException, Exception {
		int numberOfItems = 0;
		try {
			String query = "select count(news_id) from news "
					+ "where news_categories_id = '" + news_categories_id
					+ "' " + "and news_active_yn = 'y' "
					+ "and news_start_date <= NOW() and "
					+ "news_end_date >= NOW()";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				numberOfItems = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("News:getCountForCategory:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("News:getCountForCategory:Exception:"
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
		return numberOfItems;
	}

	/**
	 * Change active status of a news item
	 * 
	 * @throws Exception
	 */
	public void changeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update news set news_active_yn = '"
					+ news_active_yn + "' where news_id = '" + news_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("News:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("News:changeActiveStatus:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Delete a news item by id
	 * 
	 * @throws Exception
	 */
	public void deleteNewsItem() throws SQLException, Exception {
		try {
			String query = "delete from news where news_id = '" + news_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("News:deleteNewsItem:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("News:deleteNewsItem:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Change the category of a news item
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void changeNewsCategory() throws SQLException, Exception {
		try {
			String query = "update news set news_categories_id = '"
					+ news_categories_id + "' where news_id = '" + news_id
					+ "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("News:changeNewsCategory:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("News:changeNewsCategory:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Gets five newest news items (title & id)
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getNewsHeadlines() throws SQLException,
			Exception {
		try {
			String query = "select news_id, news_title, news_teaser_text "
					+ "from news where news_active_yn = 'y' "
					+ "and news_start_date <= NOW() "
					+ "and news_end_date >= NOW() "
					+ "order by news_start_date desc limit 5";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("News:getNewsHeadlines:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("News:getNewsHeadlines:Exception:" + e.toString());
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
	 * Gets five newest news items (title, description & id) for rss
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getNewsHeadlinesForRss() throws SQLException,
			Exception {
		try {
			String query = "select news_id, news_title, news_teaser_text, "
					+ "news_start_date "
					+ "from news where news_active_yn = 'y' "
					+ "and news_start_date <= NOW() "
					+ "and news_end_date >= NOW() "
					+ "order by news_start_date desc limit 5";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("News:getNewsHeadlines:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("News:getNewsHeadlines:Exception:" + e.toString());
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
		this.setNews_id(Integer.parseInt(theKey));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		this.deleteNewsItem();

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
		this.setNews_active_yn(psStatus);
		this.changeActiveStatus();

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
	 * @return Returns the news_active_yn.
	 */
	public String getNews_active_yn() {
		return news_active_yn;
	}

	/**
	 * @param news_active_yn
	 *            The news_active_yn to set.
	 */
	public void setNews_active_yn(String news_active_yn) {
		this.news_active_yn = news_active_yn;
	}

	/**
	 * @return Returns the news_author_id.
	 */
	public int getNews_author_id() {
		return news_author_id;
	}

	/**
	 * @param news_author_id
	 *            The news_author_id to set.
	 */
	public void setNews_author_id(int news_author_id) {
		this.news_author_id = news_author_id;
	}

	/**
	 * @return Returns the news_body_text.
	 */
	public String getNews_body_text() {
		return news_body_text;
	}

	/**
	 * @param news_body_text
	 *            The news_body_text to set.
	 */
	public void setNews_body_text(String news_body_text) {
		this.news_body_text = news_body_text;
	}

	/**
	 * @return Returns the news_end_date.
	 */
	public String getNews_end_date() {
		return news_end_date;
	}

	/**
	 * @param news_end_date
	 *            The news_end_date to set.
	 */
	public void setNews_end_date(String news_end_date) {
		this.news_end_date = news_end_date;
	}

	/**
	 * @return Returns the news_id.
	 */
	public int getNews_id() {
		return news_id;
	}

	/**
	 * @param news_id
	 *            The news_id to set.
	 */
	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	/**
	 * @return Returns the news_start_date.
	 */
	public String getNews_start_date() {
		return news_start_date;
	}

	/**
	 * @param news_start_date
	 *            The news_start_date to set.
	 */
	public void setNews_start_date(String news_start_date) {
		this.news_start_date = news_start_date;
	}

	/**
	 * @return Returns the news_teaser_text.
	 */
	public String getNews_teaser_text() {
		return news_teaser_text;
	}

	/**
	 * @param news_teaser_text
	 *            The news_teaser_text to set.
	 */
	public void setNews_teaser_text(String news_teaser_text) {
		this.news_teaser_text = news_teaser_text;
	}

	/**
	 * @return Returns the news_title.
	 */
	public String getNews_title() {
		return news_title;
	}

	/**
	 * @param news_title
	 *            The news_title to set.
	 */
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	/**
	 * @return Returns the news_categories_id.
	 */
	public int getNews_categories_id() {
		return news_categories_id;
	}

	/**
	 * @param news_categories_id
	 *            The news_categories_id to set.
	 */
	public void setNews_categories_id(int news_categories_id) {
		this.news_categories_id = news_categories_id;
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

	public String getMeta_tags() {
		return meta_tags;
	}

	public void setMeta_tags(String meta_tags) {
		this.meta_tags = meta_tags;
	}

	public String getBrowser_title() {
		return browser_title;
	}

	public void setBrowser_title(String browser_title) {
		this.browser_title = browser_title;
	}

}