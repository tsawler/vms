package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.verilion.object.Errors;

/**
 * Database routines for polls related activities.
 * 
 * @author tsawler
 * 
 */
public class Quotes implements DatabaseInterface {

	private Connection conn = null;
	DBUtils myDbUtil = new DBUtils();
	private PreparedStatement pst = null;
	private String sCustomWhere = "";
	private int quote_id = 0;
	private String active_yn = "n";
	Statement st = null;
	ResultSet rs = null;

	public void changeQuoteStatus() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("update quotes ");
			saveBuf.append("set quote_active_yn = ? where quote_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setString(1, active_yn);
			pst.setInt(2, quote_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Quotes:changeQuoteStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Quotes:changeQuoteStatus:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void deleteQuote() throws SQLException, Exception {

		try {
			StringBuffer saveBuf = new StringBuffer("");

			saveBuf = new StringBuffer("delete from quotes where quote_id = ?");

			pst = conn.prepareStatement(saveBuf.toString());
			pst.setInt(1, quote_id);

			pst.executeUpdate();
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Quotes:deleteQuote:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Quotes:deleteQuote:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public String getRandomQuote() throws SQLException, Exception {
		String theQuote = "";
		String query = "";

		try {
			query = "select quote_text from quotes where quote_active_yn = 'y' order by random() limit 1";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				theQuote = rs.getString(1);
			} else {
				theQuote = "No active quotes";
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Quotes:getRandomQuote:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Quotes:getRandomQuote:Exception:" + e.toString());
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
		return theQuote;
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

	public void createCustomWhere(String customWhere) {
		this.setSCustomWhere(customWhere);
	}

	public int getQuote_id() {
		return quote_id;
	}

	public void setQuote_id(int inId) {
		this.quote_id = inId;
	}

	public String getActive_yn() {
		return active_yn;
	}

	public void setActive_yn(String active_yn) {
		this.active_yn = active_yn;
	}

	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {
		this.setActive_yn(psStatus);
		this.changeQuoteStatus();
	}

	public void deleteRecord() throws SQLException, Exception {
		this.deleteQuote();
	}

	public void setPrimaryKey(String theKey) {
		this.setQuote_id(Integer.parseInt(theKey));
	}

}