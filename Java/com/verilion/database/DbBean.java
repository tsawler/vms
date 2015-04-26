package com.verilion.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Manages database connections. Uses JNDI naming to get driver, and assumes
 * that driver supports connection pooling.
 * <P>
 * Nov 5, 2003
 * <P>
 * package com.verilion
 * <P>
 * 
 * @author tsawler
 * 
 */
public class DbBean {

	private static Connection conn = null;
	private static String schema = "";
	private static DataSource ds = null;

	/**
	 * Gets a db connection.
	 * 
	 * @return Connection
	 * @throws Exception
	 */
	public static synchronized Connection getDbConnection() throws Exception {
		Context ctx = new InitialContext();

		if (ctx == null) {
			System.out.println("No context!");
			throw new Exception("No context!");
		}

		try {
			Context envCtx = (Context) ctx.lookup("java:comp/env");
			schema = (String) envCtx.lookup("schema");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		Statement st = null;
		try {
			ds = SingletonObjects.getInstance().getDs();
			conn = ds.getConnection();
			String query = "set search_path to " + schema;
			st = conn.createStatement();
			st.executeQuery(query);
			st.close();
			st = null;
		} catch (SQLException e) {
			// do nothing, as it's just going to give us a no result msg
			;
		} catch (Exception e) {
			System.out.println("Can't get datasource!");
		} finally {
			if (st != null) {
				st.close();
				st = null;
			}
		}
		return conn;
	}

	/**
	 * Closes db connection, set as property.
	 * 
	 * @throws Exception
	 */
	public synchronized static void closeDbConnection(Connection con)
			throws Exception {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			throw new Exception("DbBean:closeDbConnection:SQLException "
					+ e.getMessage());
		} catch (Exception e) {
			throw new Exception("DbBean:closeDbConnection:Exception "
					+ e.getMessage());
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
		DbBean.conn = conn;
	}
}