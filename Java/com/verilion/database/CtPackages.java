package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates packages in db, for pricing/offering for sellers.
 * <P>
 * Jun 12, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class CtPackages {

	private int ct_package_id = 0;
	private String ct_package_name = "";
	private String ct_package_description = "";
	private float ct_package_price = 0.0f;
	private String ct_package_active_yn = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;
	DBUtils myDbUtils = new DBUtils();

	public CtPackages() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updatePackages() throws SQLException, Exception {
		try {
			String Update = "UPDATE ct_packages SET " + "ct_package_name = '"
					+ ct_package_name + "', " + "ct_package_description = '"
					+ ct_package_description + "', " + "ct_package_price = '"
					+ ct_package_price + "', " + "ct_package_active_yn = '"
					+ ct_package_active_yn + "' " + "WHERE ct_package_id = '"
					+ ct_package_id + "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("Packages:updatePackages:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("Packages:updatePackages:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Adds a package.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addPackage() throws SQLException, Exception {
		try {
			String save = "INSERT INTO ct_packages (" + "ct_package_name, "
					+ "ct_package_description, " + "ct_package_price, "
					+ "ct_package_active_yn) " + "VALUES(" + "'"
					+ ct_package_name + "', " + "'" + ct_package_description
					+ "', " + "'" + ct_package_price + "', " + "'"
					+ ct_package_active_yn + "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Packages:addPackage:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Packages:addPackage:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Gets all active package names/ids, sorted by package name.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getPackageNames() throws SQLException, Exception {
		try {
			String query = "Select ct_package_id, ct_package_name, sample_link from ct_packages "
					+ "where ct_package_active_yn = 'y' "
					+ "order by ct_package_name";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Packages:getPackageNames:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Packages:getPackageNames:Exception:"
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
	 * Gets all active package names/ids, sorted by package name.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getActivePackageNamesPrices()
			throws SQLException, Exception {
		try {
			String query = "Select ct_package_id, ct_package_name, ct_package_price from ct_packages "
					+ "where ct_package_active_yn = 'y' "
					+ "order by ct_package_name";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Packages:getPackageNames:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Packages:getPackageNames:Exception:"
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
	 * Gets all package names/ids, sorted by package name.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllPackageNames() throws SQLException,
			Exception {
		try {
			String query = "Select ct_package_id, ct_package_name, ct_package_active_yn from ct_packages "
					+ "order by ct_package_name";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Packages:getPackageNames:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Packages:getPackageNames:Exception:"
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
	 * Gets a package name by package id
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getPackageNameById() throws SQLException, Exception {
		String theName = "";
		try {
			String query = "Select ct_package_name from ct_packages "
					+ "where ct_package_id = '" + ct_package_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				theName = rs.getString("ct_package_name");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Packages:getPackageNameById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Packages:getPackageNameById:Exception:"
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
		return theName;
	}

	/**
	 * Gets package by ct_package_id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getPackageDetail() throws SQLException,
			Exception {
		try {
			String query = "Select * from ct_packages where ct_package_id = '"
					+ ct_package_id + "'";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Packages:getPackageDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Packages:getPackageDetail:Exception:"
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
	 * Deletes package by ct_package_id
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deletePackage() throws SQLException, Exception {
		try {
			String query = "delete from ct_packages where ct_package_id = '"
					+ ct_package_id + "'";

			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Packages:deletePackage:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Packages:deletePackage:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}

	}

	/**
	 * Change active status of a package
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void changeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update ct_packages set ct_package_active_yn = '"
					+ ct_package_active_yn + "' where ct_package_id = '"
					+ ct_package_id + "'";

			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Packages:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Packages:changeActiveStatus:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
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
		this.conn = conn;
	}

	/**
	 * @return Returns the ct_package_active_yn.
	 */
	public String getCt_package_active_yn() {
		return ct_package_active_yn;
	}

	/**
	 * @param ct_package_active_yn
	 *            The ct_package_active_yn to set.
	 */
	public void setCt_package_active_yn(String ct_package_active_yn) {
		this.ct_package_active_yn = ct_package_active_yn;
	}

	/**
	 * @return Returns the ct_package_description.
	 */
	public String getCt_package_description() {
		return ct_package_description;
	}

	/**
	 * @param ct_package_description
	 *            The ct_package_description to set.
	 */
	public void setCt_package_description(String ct_package_description) {
		this.ct_package_description = myDbUtils
				.fixSqlFieldValue(ct_package_description);
	}

	/**
	 * @return Returns the ct_package_id.
	 */
	public int getCt_package_id() {
		return ct_package_id;
	}

	/**
	 * @param ct_package_id
	 *            The ct_package_id to set.
	 */
	public void setCt_package_id(int ct_package_id) {
		this.ct_package_id = ct_package_id;
	}

	/**
	 * @return Returns the ct_package_name.
	 */
	public String getCt_package_name() {
		return ct_package_name;
	}

	/**
	 * @param ct_package_name
	 *            The ct_package_name to set.
	 */
	public void setCt_package_name(String ct_package_name) {
		this.ct_package_name = myDbUtils.fixSqlFieldValue(ct_package_name);
	}

	/**
	 * @return Returns the ct_package_price.
	 */
	public float getCt_package_price() {
		return ct_package_price;
	}

	/**
	 * @param ct_package_price
	 *            The ct_package_price to set.
	 */
	public void setCt_package_price(float ct_package_price) {
		this.ct_package_price = ct_package_price;
	}
}