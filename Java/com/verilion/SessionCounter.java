//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-29
//Revisions
//------------------------------------------------------------------------------
//$Log: SessionCounter.java,v $
//Revision 1.1.2.1.2.1  2007/06/11 15:34:22  tcs
//added "if rs.next()" logic
//
//Revision 1.1.2.1  2007/01/29 19:41:34  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.verilion.database.DbBean;

public class SessionCounter implements HttpSessionListener, Serializable {

	private static final long serialVersionUID = -6099675011925232339L;
	private static int activeSessions = 0;
	private Statement st = null;
	private ResultSet rs = null;
	private Connection conn = null;
	private String query = null;

	public void sessionCreated(HttpSessionEvent se) {

		try {
			conn = DbBean.getDbConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// get most recent session count.
		try {
			query = "select * from sessions";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				activeSessions = rs.getInt("sessions");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
				rs = null;
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {

				}
				st = null;
			}
		}

		activeSessions++;

		// a session was created; add one, and add to db
		try {
			query = "update sessions set sessions = '" + activeSessions + "'";
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			st = null;

		} catch (SQLException e) {
			// e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {

				}
				st = null;
			}
		}

		try {
			DbBean.closeDbConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		try {
			conn = DbBean.getDbConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (activeSessions > 0)
			activeSessions--;
		// a session was dropped; remove one, and write to db
		try {
			query = "update sessions set sessions = '" + activeSessions + "'";
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			st = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {

				}
				st = null;
			}
		}
		try {
			DbBean.closeDbConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getActiveSessions() {
		return activeSessions;
	}
}