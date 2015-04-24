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
 * Manipulates component table in db
 * 
 * @author tsawler
 * 
 */
public class Components implements DatabaseInterface {

	private int component_id = 0;
	private String component_name = "";
	private String url = "";
	private String component_active_yn = "";
	private int iOffset = 0;
	private int iLimit = 0;
	private String sCustomWhere = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private PreparedStatement pst = null;
	private Statement st = null;

	DBUtils myDbUtil = new DBUtils();

	public Components() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateComponent() throws SQLException, Exception {
		try {
			String update = "UPDATE components SET " + "component_name = '"
					+ myDbUtil.fixSqlFieldValue(component_name) + "', "
					+ "component_active_yn = '" + component_active_yn + "' "
					+ "WHERE component_id = '" + component_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Components:updateComponent:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Components:updateComponent:Exception:"
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
	public int addComponent() throws SQLException, Exception {

		int new_story_id = 0;

		try {
			String save = "INSERT INTO components (" + "component_name, "
					+ "url, " + "component_active_yn) " + "VALUES(" + "'"
					+ myDbUtil.fixSqlFieldValue(component_name) + "', "
					+ myDbUtil.fixSqlFieldValue(url) + "', " + "'"
					+ component_active_yn + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();

			String getLast = "select currval('components_component_id_seq')";

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
			Errors.addError("Components:addComponent:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Components:addComponent:Exception:" + e.toString());
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
	 * Returns component for supplied id
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getComponentById() throws SQLException,
			Exception {
		try {
			String getId = "select * from components where component_id = '"
					+ component_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(getId);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Components:getComponentById:SQLException:"
					+ e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			Errors.addError("Components:getComponentById:Exception:"
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

	public String getComponentUrlById(int inId) throws SQLException, Exception {

		String theUrl = "";

		try {
			String getId = "select url from components where component_id = '"
					+ component_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(getId);
			while (rs.next()) {
				theUrl = rs.getString(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Components:getComponentUrlById:SQLException:"
					+ e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			Errors.addError("Components:getComponentUrlById:Exception:"
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

		return theUrl;
	}

	/**
	 * Returns all items
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllComponents() throws SQLException,
			Exception {
		try {
			String query = "select * from components ";
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
			Errors.addError("Components:getAllComponents:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Components:getAllComponents:Exception:"
					+ e.toString());
		}
		return crs;
	}

	public RowSetDynaClass getAllComponentsynaBean() throws SQLException,
			Exception {

		RowSetDynaClass resultset = null;

		try {
			String query = "select components.*, "
					+ "case when component_active_yn = 'y' then '<span style=\"color: green\">active</span>' "
					+ "when component_active_yn = 'p' then '<span style=\"color: orange\">pending</span>' "
					+ "else '<span style=\"color: red;\">inactive</a>' end as component_active_status "
					+ "from components ";
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
			Errors.addError("Components:getAllComponentsynaBean:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Components:getAllComponentsynaBean:Exception:"
					+ e.toString());
		}
		return resultset;
	}

	/**
	 * Change active status of a component
	 * 
	 * @throws Exception
	 */
	public void changeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update components set component_active_yn = '"
					+ component_active_yn + "' where comment_id = '"
					+ component_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Components:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Components:changeActiveStatus:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Delete a component by id
	 * 
	 * @throws Exception
	 */
	public void deleteComponent() throws SQLException, Exception {
		try {
			String query = "delete from components where component_id = '"
					+ component_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Components:deleteComponent:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Components:deleteComponent:Exception:"
					+ e.toString());
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
		this.setComponent_id(Integer.parseInt(theKey));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		this.deleteComponent();

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
		this.setComponent_active_yn(psStatus);
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

	public String getComponent_active_yn() {
		return component_active_yn;
	}

	public void setComponent_active_yn(String component_active_yn) {
		this.component_active_yn = component_active_yn;
	}

	public int getComponent_id() {
		return component_id;
	}

	public void setComponent_id(int component_id) {
		this.component_id = component_id;
	}

	public String getComponent_name() {
		return component_name;
	}

	public void setComponent_name(String component_name) {
		this.component_name = component_name;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}