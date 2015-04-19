//------------------------------------------------------------------------------
//Copyright (c) 2003-2006 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-10-05
//Revisions
//------------------------------------------------------------------------------
//$Log: Promotions.java,v $
//Revision 1.1.2.1  2006/10/05 15:53:02  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates promotions table in db, and related operations.
 * <P>
 * Oct 05, 2006
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class Promotions {

	private int promotion_id = 0;
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;

	public Promotions() {
		super();
	}

	/**
	 * Returns all promotions.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllPromotions() throws SQLException,
			Exception {
		try {
			String getAll = "select * from promotions";
			st = conn.createStatement();
			rs = st.executeQuery(getAll);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Promotions:getAllPromotions:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.Promotions.CtMonth:getAllPromotions:Exception:"
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
	 * Returns promotion name for supplied id.
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getPromotionNameById() throws SQLException, Exception {
		String thePromotionName = "";
		try {
			String query = "select promotion_name from promotion where promotion_id = '"
					+ promotion_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				thePromotionName = rs.getString("promotion_name");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
					.addError("com.verilion.database.Promotions:getPromotionNameById:SQLException:"
							+ e.toString());
		} catch (Exception e) {
			Errors
					.addError("com.verilion.database.Promotions:getPromotionNameById:Exception:"
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
		return thePromotionName;
	}

	public float getDiscountRate(String promoName) throws SQLException,
			Exception {
		float theDiscount = 0.0f;
		try {
			String query = "select discount_rate from promotions where promotion_name ilike '"
					+ promoName + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theDiscount = rs.getFloat(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (Exception e) {
			Errors
			.addError("com.verilion.database.Promotions:getDiscountRate:Exception:"
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
		return theDiscount;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}