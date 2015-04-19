//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-22
//Revisions
//------------------------------------------------------------------------------
//$Log: Banners.java,v $
//Revision 1.1.2.5.2.2  2008/09/01 21:11:42  tcs
//*** empty log message ***
//
//Revision 1.1.2.5.2.1  2007/06/11 15:35:18  tcs
//Added ability to delete banners
//
//Revision 1.1.2.5  2007/03/15 17:46:39  tcs
//Updated for multiple banners in one spot
//
//Revision 1.1.2.4  2007/02/07 17:46:22  tcs
//Modified to use Stringbuffers
//
//Revision 1.1.2.3  2007/01/23 19:27:39  tcs
//Minor correction
//
//Revision 1.1.2.2  2007/01/23 14:40:24  tcs
//Added a few methods
//
//Revision 1.1.2.1  2007/01/23 02:21:55  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Database routines for banner related activities.
 * 
 * @author tsawler
 * 
 */
public class Banners implements DatabaseInterface {

	private Connection conn = null;
	DBUtils myDbUtil = new DBUtils();
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private Statement st = null;
	private String sCustomWhere = "";

	private int banner_id = 0;
	private String sSelectTable = "banners";
	private String banner_name = "";
	private String url = "";
	private String banner_file = "";
	private int impressions = 0;
	private int clicks = 0;
	private String banner_active_yn = "";
	private Date from_date = null;
	private Date to_date = null;
	private int ct_banner_position_id = 0;

	private XDisconnectedRowSet crs = new XDisconnectedRowSet();

	/**
	 * Insert method.
	 * 
	 * @throws Exception
	 */
	public int addBanner() throws SQLException, Exception {

		int newBannerId = 0;

		try {
			StringBuffer saveBuf = new StringBuffer(
					"INSERT INTO "
							+ sSelectTable
							+ " ("
							+ "banner_name, "
							+ "url, "
							+ "from_date, to_date, "
							+ "impressions, clicks, banner_file, banner_active_yn, ct_banner_position_id) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setString(1, banner_name);
			pst.setString(2, url);
			pst.setDate(3, from_date);
			pst.setDate(4, to_date);
			pst.setInt(5, impressions);
			pst.setInt(6, clicks);
			pst.setString(7, banner_file);
			pst.setString(8, banner_active_yn);
			pst.setInt(9, ct_banner_position_id);

			pst.executeUpdate();

			String getLast = "select currval('banners_banner_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);

			if (rs.next()) {
				newBannerId = rs.getInt(1);
			}

			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Banners:addBanner:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Banners:addBanner:Exception:" + e.toString());
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

		return newBannerId;
	}

	public void createCustomWhere(String customWhere) {
		this.setSCustomWhere(customWhere);
	}

	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {

	}

	public void setPrimaryKey(String theKey) {
		this.setBanner_id(Integer.parseInt(theKey));
	}

	public void deleteRecord() throws SQLException, Exception {
		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer(
					"delete from banners where banner_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setInt(1, banner_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors
					.addError("Banners:deleteRecord:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors.addError("Banners:deleteRecord:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Update method for banners
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateBanner() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("update " + sSelectTable
					+ " set " + "banner_name = ?, " + "url = ?, "
					+ "from_date = ?, " + "to_date = ?, " + "banner_file = ?,"
					+ "banner_active_yn = ?, " + "ct_banner_position_id = ? "
					+ "where banner_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setString(1, banner_name);
			pst.setString(2, url);
			pst.setDate(3, from_date);
			pst.setDate(4, to_date);
			pst.setString(5, banner_file);
			pst.setString(6, banner_active_yn);
			pst.setInt(7, ct_banner_position_id);
			pst.setInt(8, banner_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors
					.addError("Banners:updateBanner:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors.addError("Banners:updateBanner:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Increment banner impressions by 1 for banner id
	 * 
	 * @param inId
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addOneToImpressions(int inId) throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("update ");
			saveBuf.append("banner_banner_impressions");
			saveBuf.append(" set ");
			saveBuf.append("banner_impressions = (banner_impressions + 1) ");
			saveBuf.append("where banner_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setInt(1, inId);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Banners:addOneToImpressions:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Banners:addOneToImpressions:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Increment clicks by one for supplied banner id
	 * 
	 * @param inId
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addOneToClicks(int inId) throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("update ");
			saveBuf.append("banner_banner_clicks");
			saveBuf.append(" set ");
			saveBuf.append("banner_clicks = (banner_clicks + 1) ");
			saveBuf.append("where banner_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setInt(1, inId);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Banners:addOneToClicks:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Banners:addOneToClicks:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void addClickDetail(int inId, String IP) throws SQLException,
			Exception {

		try {
			int rowId = 0;
			GregorianCalendar x = new GregorianCalendar();
			int year = x.get(Calendar.YEAR);
			int month = x.get(Calendar.MONTH) + 1;

			// check to see if we have a month/year row for this banner
			GenericTable myGt = new GenericTable(
					"banner_banner_clicks_by_month");
			myGt.setConn(conn);
			myGt.setSSelectWhat("id");
			myGt.setSCustomWhere(" and banner_id = " + inId
					+ " and click_month = " + month + " and click_year = "
					+ year);
			XDisconnectedRowSet drs = new XDisconnectedRowSet();
			drs = myGt.getAllRecordsDisconnected();
			if (drs.next()) {
				rowId = drs.getInt("id");
			}
			drs.close();
			drs = null;

			if (rowId == 0) {
				// we don't have one, so add one
				myGt.setSTable("banner_banner_clicks_by_month");
				myGt.addUpdateFieldNameValuePair("banner_id", inId + "", "int");
				myGt.addUpdateFieldNameValuePair("banner_clicks", "0", "int");
				myGt.addUpdateFieldNameValuePair("click_month", month + "",
						"int");
				myGt
						.addUpdateFieldNameValuePair("click_year", year + "",
								"int");
				myGt.insertRecord();
				myGt.clearUpdateVectors();
				myGt.setSSequence("banner_banner_clicks_by_month_id_seq");
				rowId = myGt.returnCurrentSequenceValue();
			}

			String save = "update  banner_banner_clicks_by_month "
					+ " set banner_clicks = (banner_clicks + 1) "
					+ "where id = ?";

			pst = conn.prepareStatement(save);
			pst.setInt(1, rowId);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Banners:addClickDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Banners:addClickDetail:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void addImpressionDetail(int inId, String IP) throws SQLException,
			Exception {

		try {
			int rowId = 0;
			GregorianCalendar x = new GregorianCalendar();
			int year = x.get(Calendar.YEAR);
			int month = x.get(Calendar.MONTH) + 1;

			// check to see if we have a month/year row for this banner
			GenericTable myGt = new GenericTable(
					"banner_banner_impressions_by_month");
			myGt.setConn(conn);
			myGt.setSSelectWhat("id");
			myGt.setSCustomWhere(" and banner_id = " + inId
					+ " and impression_month = " + month
					+ " and impression_year = " + year);
			XDisconnectedRowSet drs = new XDisconnectedRowSet();
			drs = myGt.getAllRecordsDisconnected();
			if (drs.next()) {
				rowId = drs.getInt("id");
			}
			drs.close();
			drs = null;

			if (rowId == 0) {
				// we don't have one, so add one
				myGt.setSTable("banner_banner_impressions_by_month");
				myGt.addUpdateFieldNameValuePair("banner_id", inId + "", "int");
				myGt.addUpdateFieldNameValuePair("banner_impressions", "0",
						"int");
				myGt.addUpdateFieldNameValuePair("impression_month",
						month + "", "int");
				myGt.addUpdateFieldNameValuePair("impression_year", year + "",
						"int");
				myGt.insertRecord();
				myGt.clearUpdateVectors();
				myGt.setSSequence("banner_banner_impressions_by_month_id_seq");
				rowId = myGt.returnCurrentSequenceValue();
			}

			// update values for impressions

			String save = "update  banner_banner_impressions_by_month "
					+ " set banner_impressions = (banner_impressions + 1) "
					+ "where id = ?";

			pst = conn.prepareStatement(save);
			pst.setInt(1, rowId);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Banners:addImpressionDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Banners:addImpressionDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Return all banners, regardless of status, as drs
	 * 
	 * @return XDisconnectedRowset of all banners
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllBanners() throws Exception {
		st = conn.createStatement();
		StringBuffer queryBuf = new StringBuffer("select * from ");
		queryBuf.append(sSelectTable);
		rs = st.executeQuery(queryBuf.toString());
		crs.populate(rs);
		rs.close();
		rs = null;
		st.close();
		st = null;

		return crs;
	}

	/**
	 * Get a random banner for a position id
	 * 
	 * @param inId
	 * @return drs of a random banner
	 * @throws Exception
	 */
	public XDisconnectedRowSet getRandomBannersForPositionId(int inId,
			int inNumber) throws Exception {
		st = conn.createStatement();
		StringBuffer saveBuf = new StringBuffer(
				"SELECT b.*, bs.ct_banner_sizes_height as height, bs.ct_banner_sizes_width as width FROM ");
		saveBuf.append(sSelectTable);
		saveBuf
				.append(" b left join ct_banner_sizes bs on (b.ct_banner_sizes_id = bs.ct_banner_sizes_id) ");
		saveBuf
				.append(" where b.banner_active_yn = 'y' and b.ct_banner_position_id = ");
		saveBuf.append(inId);
		saveBuf.append(" order by RANDOM() LIMIT " + inNumber);
		rs = st.executeQuery(saveBuf.toString());
		crs.populate(rs);
		rs.close();
		rs = null;
		st.close();
		st = null;

		return crs;
	}
	
	public XDisconnectedRowSet getRandomBannersForPositionId(int inId,
			int inNumber, int langId) throws Exception {
		st = conn.createStatement();
		StringBuffer saveBuf = new StringBuffer(
				"SELECT b.*, bs.ct_banner_sizes_height as height, bs.ct_banner_sizes_width as width FROM ");
		saveBuf.append(sSelectTable);
		saveBuf
				.append(" b left join ct_banner_sizes bs on (b.ct_banner_sizes_id = bs.ct_banner_sizes_id) ");
		saveBuf
				.append(" where b.banner_active_yn = 'y' and b.ct_language_id = " + langId + " and b.ct_banner_position_id = ");
		saveBuf.append(inId);
		saveBuf.append(" order by RANDOM() LIMIT " + inNumber);
		rs = st.executeQuery(saveBuf.toString());
		crs.populate(rs);
		rs.close();
		rs = null;
		st.close();
		st = null;

		return crs;
	}

	public XDisconnectedRowSet getRandomBannerForPositionId(int inId)
			throws Exception {
		st = conn.createStatement();
		StringBuffer saveBuf = new StringBuffer(
				"SELECT b.*, bs.ct_banner_sizes_height as height, bs.ct_banner_sizes_width as width FROM ");
		saveBuf.append(sSelectTable);
		saveBuf
				.append(" b left join ct_banner_sizes bs on (b.ct_banner_sizes_id = bs.ct_banner_sizes_id) ");
		saveBuf
				.append(" where b.banner_active_yn = 'y' and b.ct_banner_position_id = ");
		saveBuf.append(inId);
		saveBuf.append(" order by RANDOM() LIMIT 1");
		rs = st.executeQuery(saveBuf.toString());
		crs.populate(rs);
		rs.close();
		rs = null;
		st.close();
		st = null;

		return crs;
	}

	public XDisconnectedRowSet getRandomBannerForPositionId(int inId, int langId)
			throws Exception {
		st = conn.createStatement();
		StringBuffer saveBuf = new StringBuffer(
				"SELECT b.*, bs.ct_banner_sizes_height as height, bs.ct_banner_sizes_width as width FROM ");
		saveBuf.append(sSelectTable);
		saveBuf
				.append(" b left join ct_banner_sizes bs on (b.ct_banner_sizes_id = bs.ct_banner_sizes_id) ");
		saveBuf
				.append(" where b.banner_active_yn = 'y' and b.ct_language_id = " + langId + " and b.ct_banner_position_id = ");
		saveBuf.append(inId);
		saveBuf.append(" order by RANDOM() LIMIT 1");
		rs = st.executeQuery(saveBuf.toString());
		crs.populate(rs);
		rs.close();
		rs = null;
		st.close();
		st = null;

		return crs;
	}

	/**
	 * Get a single banner as drs
	 * 
	 * @param inId
	 * @return drs of a single banner
	 * @throws Exception
	 */
	public XDisconnectedRowSet getBannerById(int inId) throws Exception {
		st = conn.createStatement();
		StringBuffer saveBuf = new StringBuffer("select * from ");
		saveBuf.append(sSelectTable);
		saveBuf.append(" where banner_id = ");
		saveBuf.append(inId);
		rs = st.executeQuery(saveBuf.toString());
		crs.populate(rs);
		rs.close();
		rs = null;
		st.close();
		st = null;

		return crs;
	}

	/**
	 * Get all banners for lookup table
	 * 
	 * @return dynabean of banners for lookup table
	 * @throws Exception
	 */
	public RowSetDynaClass getAllBannersDynaBean() throws Exception {

		RowSetDynaClass resultset = null;

		st = conn.createStatement();
		StringBuffer queryBuf = new StringBuffer(
				"select banner_name, banner_id, banner_active_yn, ");
		queryBuf
				.append("case when banner_active_yn = 'n' then '<span style=\"color: red;\">inactive</a>' else '<span style=\"color: green;\">active</a>' ");
		queryBuf.append("end as status from ");
		queryBuf.append(sSelectTable);
		queryBuf.append(" where true ");
		queryBuf.append(sCustomWhere);
		queryBuf.append(" order by banner_name");
		rs = st.executeQuery(queryBuf.toString());
		resultset = new RowSetDynaClass(rs, false);
		rs.close();
		rs = null;
		st.close();
		st = null;

		return resultset;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Connection getConn() {
		return conn;
	}

	public String getSCustomWhere() {
		return sCustomWhere;
	}

	public void setSCustomWhere(String customWhere) {
		sCustomWhere = customWhere;
	}

	public String getBanner_active_yn() {
		return banner_active_yn;
	}

	public void setBanner_active_yn(String banner_active_yn) {
		this.banner_active_yn = banner_active_yn;
	}

	public String getBanner_file() {
		return banner_file;
	}

	public void setBanner_file(String banner_file) {
		this.banner_file = banner_file;
	}

	public int getBanner_id() {
		return banner_id;
	}

	public void setBanner_id(int banner_id) {
		this.banner_id = banner_id;
	}

	public String getBanner_name() {
		return banner_name;
	}

	public void setBanner_name(String banner_name) {
		this.banner_name = banner_name;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public Date getFrom_date() {
		return from_date;
	}

	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}

	public int getImpressions() {
		return impressions;
	}

	public void setImpressions(int impressions) {
		this.impressions = impressions;
	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCt_banner_position_id() {
		return ct_banner_position_id;
	}

	public void setCt_banner_position_id(int ct_banner_position_id) {
		this.ct_banner_position_id = ct_banner_position_id;
	}

}