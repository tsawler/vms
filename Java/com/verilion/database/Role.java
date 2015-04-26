package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Database routines for role related activities.
 * 
 * @author dholbrough
 * 
 */
public class Role implements DatabaseInterface {
	private Connection conn = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	int customer_id = 0;
	private int iLimit = 0;
	private int iOffset = 0;

	DBUtils myDbUtil = new DBUtils();

	private PreparedStatement pst = null;
	private String role_active_yn = "n";
	private String role_creation_date = "";
	private String role_description = "";
	private int role_id = 0;
	private String role_name = "";

	private ResultSet rs = null;
	private String sCustomWhere = "";
	private Statement st = null;

	public boolean activateRole(int intRole) {
		return false;
	}

	/**
	 * Insert method.
	 * 
	 * @throws Exception
	 */
	public int addRole() throws SQLException, Exception {

		int new_role_id = 0;

		try {
			String save = "INSERT INTO role (" + "role_name, "
					+ "role_description, " + "role_active_yn) " + "VALUES("
					+ "'" + role_name + "', " + "'" + role_description + "', "
					+ "'" + role_active_yn + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();

			String getLast = "select currval('role_id_seq')";

			st = conn.createStatement();
			rs = st.executeQuery(getLast);

			if (rs.next()) {
				new_role_id = rs.getInt(1);
			}

			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;

		} catch (SQLException e) {
			Errors.addError("Role:addRole:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:addRole:Exception:" + e.toString());
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

		return new_role_id;
	}

	/**
	 * Insert method
	 * 
	 * @param strRoleName
	 * @param strRoleDescription
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addRole(String strRoleName, String strRoleDescription)
			throws SQLException, Exception {

		String sqlQuery = "";
		try {
			sqlQuery = "INSERT INTO role (" + "role_id, " + "role_name, "
					+ "role_description, " + "role_active_yn, "
					+ "role_creation_date ) " + "VALUES (" + "'', " + "'"
					+ strRoleName + "', " + "'" + strRoleDescription + "', "
					+ "'" + "y" + "', " + "'" + GregorianCalendar.YEAR
					+ (GregorianCalendar.MONTH + 1)
					+ GregorianCalendar.DAY_OF_MONTH
					+ GregorianCalendar.HOUR_OF_DAY + GregorianCalendar.MINUTE
					+ GregorianCalendar.SECOND + "' " + "";
			pst = conn.prepareStatement(sqlQuery);
			pst.executeUpdate();

			// String getLast = "select currval('role_id_seq')";

			pst.close();
			pst = null;
		} catch (SQLException e) {
			System.out.println("Query is " + sqlQuery);
			System.out.println("error in role is " + e.toString());
			e.printStackTrace();
			Errors.addError("Role:addRole:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:addRole:Exception:" + e.toString());
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
	}

	/**
	 * Change active status of a role
	 * 
	 * @throws Exception
	 */
	public void changeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update role set role_active_yn = '"
					+ role_active_yn + "' where role_id = '" + role_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:changeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:changeActiveStatus:Exception:" + e.toString());
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
	 * com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.
	 * String)
	 */
	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {
		this.setRole_active_yn(psStatus);
		this.changeActiveStatus();

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

	public void deactivateRole(int intRole) {
		if (this.getRole_active_yn() != "n") {
			this.setRole_active_yn("n");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		this.deleteRole();

	}

	/**
	 * Delete a role by id
	 * 
	 * @throws Exception
	 */
	public void deleteRole() throws SQLException, Exception {
		try {
			String query = "delete from role where role_id = '" + role_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:deleteRoleItem:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:deleteRoleItem:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Delete method
	 * 
	 * @param intRoleID
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deleteRole(int intRoleID) throws SQLException, Exception {
		String sqlQuery = "";
		try {
			sqlQuery = "delete from role where role_id = '" + intRoleID + "'";
			pst = conn.prepareStatement(sqlQuery);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:deleteRole:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:deleteRole:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void deleteEntriesForRoleId(int intRoleID) throws SQLException,
			Exception {
		String sqlQuery = "";
		try {
			sqlQuery = "delete from admin_page_role where role_id = '"
					+ intRoleID + "'";
			pst = conn.prepareStatement(sqlQuery);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:deleteEntriesForRoleId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:deleteEntriesForRoleId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void addEntriesForRoleId(int intRoleID, String[] theIds)
			throws SQLException, Exception {
		String sqlQuery = "";
		try {
			for (int i = 0; i < theIds.length; i++) {
				sqlQuery = "insert into admin_page_role (admin_page_id, role_id) values ('"
						+ theIds[i] + "', '" + intRoleID + "')";
				pst = conn.prepareStatement(sqlQuery);
				pst.executeUpdate();
			}
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:addEntriesForRoleId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:addEntriesForRoleId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void DeleteEntriesFromAdminPageRole(int intRoleID)
			throws SQLException, Exception {
		String sqlQuery = "";
		try {
			sqlQuery = "delete from admin_page_group_role where role_id = '"
					+ intRoleID + "'";
			pst = conn.prepareStatement(sqlQuery);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:DeleteEntriesFromAdminPageRole:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:DeleteEntriesFromAdminPageRole:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void AddEntriesToAdminPageRole(int intRoleID, String[] paramValues)
			throws SQLException, Exception {
		String sqlQuery = "";
		try {
			for (int i = 0; i < paramValues.length; i++) {
				st = conn.createStatement();
				String query = "select admin_page_id from admin_page_group_detail where admin_page_group_id = '"
						+ paramValues[i] + "'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					sqlQuery = "insert into admin_page_group_role (admin_page_id, role_id) values ('"
							+ rs.getString(1) + "', " + "'" + intRoleID + "')";
					pst = conn.prepareStatement(sqlQuery);
					pst.executeUpdate();
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:AddEntriesToAdminPageRole:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:AddEntriesToAdminPageRole:Exception:"
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
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns all active roles ordered by role name
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveRoles() throws SQLException,
			Exception {
		try {
			String query = "select * from role where role_active_yn = 'y' order by role_name";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:getAllActiveRoles:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:getAllActiveRole:Exception:" + e.toString());
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
	 * Returns all active roles for a user ordered by role name
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveRolesForCustomer()
			throws SQLException, Exception {
		try {

			String query = "select * from role where role_active_yn = 'y' "
					+ "and customer_id = '" + customer_id + "' "
					+ "order by role_name desc";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:getAllActiveRolesForCustomer:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:getAllActiveRolesForCustomer:Exception:"
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
	 * Returns all roles ordered by role name
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllRoles() throws SQLException, Exception {
		try {
			String query = "select * from role ";
			if (sCustomWhere.length() > 6) {
				query += " " + sCustomWhere;
			}
			query += " order by role_name";
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
			Errors.addError("Role:getAllRoles:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:getAllRoles:Exception:" + e.toString());
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
	 * returns number of active items for a customer
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getCountForCustomer() throws SQLException, Exception {
		int numberOfItems = 0;
		try {
			String query = "select count(role_id) from role "
					+ "where customer_id = '" + customer_id + "' "
					+ "and role_active_yn = 'y' ";
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
			Errors.addError("Role:getCountForCustomer:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:getCountForCustomer:Exception:"
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
	 * Get a single role by id
	 * 
	 * @return disconnected rowset
	 * @throws Exception
	 * @throws SQLException
	 */
	public XDisconnectedRowSet GetOneRole() throws Exception, SQLException {
		try {
			String getOneCust = "SELECT * FROM role WHERE role_id=" + role_id;
			st = conn.createStatement();
			rs = st.executeQuery(getOneCust);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:GetOneRole:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:GetOneRole:Exception:" + e.toString());
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

	public XDisconnectedRowSet GetExcludedPublicPagesForRoleId()
			throws Exception, SQLException {
		try {
			String getOneCust = "select page_name, page_id from page where page_id not in (select page_id from role_detail where role_id="
					+ role_id + ")";
			st = conn.createStatement();
			rs = st.executeQuery(getOneCust);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:Exception:"
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

	public boolean CheckPageForRole(int inId, int inRoleId) throws Exception,
			SQLException {
		boolean hasAccessToPage = false;
		try {
			String query = "select admin_page_id from admin_page_group_role where admin_page_id = '"
					+ inId + "' and role_id = '" + inRoleId + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				hasAccessToPage = true;
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:CheckPageForRole:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:CheckPageForRole:Exception:" + e.toString());
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
		return hasAccessToPage;
	}

	public XDisconnectedRowSet GetIncludedPublicPagesForRoleId()
			throws Exception, SQLException {
		try {
			String getOneCust = "select page_name, page_id from page where page_id in (select page_id from role_detail where role_id="
					+ role_id + ")";
			st = conn.createStatement();
			rs = st.executeQuery(getOneCust);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:Exception:"
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

	public XDisconnectedRowSet GetExcludedAdminPagesForRoleId()
			throws Exception, SQLException {
		try {
			String getOneCust = "select page_name, page_id from admin_page where page_id not in (select admin_page_id from role_detail where role_id="
					+ role_id + ")";
			st = conn.createStatement();
			rs = st.executeQuery(getOneCust);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:Exception:"
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

	public XDisconnectedRowSet GetExcludedPageGroupsForRoleId()
			throws Exception, SQLException {
		try {
			String getOneCust = "select admin_page_group_name, admin_page_group_id from admin_page_group "
					+ "where admin_page_group_id not in (select admin_page_id from admin_page_role where role_id="
					+ role_id
					+ ") and admin_page_group_active_yn = 'y' order by admin_page_group_name";
			st = conn.createStatement();
			rs = st.executeQuery(getOneCust);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:GetExcludedPageGroupsForRoleId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:Exception:"
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

	public XDisconnectedRowSet GetIncludedPageGroupsForRoleId()
			throws Exception, SQLException {
		try {
			String getOneCust = "select admin_page_group_name, admin_page_group_id from admin_page_group "
					+ "where admin_page_group_id in (select admin_page_id from admin_page_role where role_id="
					+ role_id
					+ ") and admin_page_group_active_yn = 'y' order by admin_page_group_name";
			st = conn.createStatement();
			rs = st.executeQuery(getOneCust);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:GetExcludedPageGroupsForRoleId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:Exception:"
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

	public XDisconnectedRowSet GetIncludedAdminPagesForRoleId()
			throws Exception, SQLException {
		try {
			String getOneCust = "select page_name, page_id from admin_page where page_id in (select admin_page_id from role_detail where role_id="
					+ role_id + ")";
			st = conn.createStatement();
			rs = st.executeQuery(getOneCust);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Role:GetExcludedPublicPagesForRoleId:Exception:"
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
	 * @return Returns the role_active_yn.
	 */
	public String getRole_active_yn() {
		return role_active_yn;
	}

	/**
	 * @return Returns the role_creation_date.
	 */
	public String getRole_creation_date() {
		return role_creation_date;
	}

	/**
	 * @return Returns the role_description.
	 */
	public String getRole_description() {
		return role_description;
	}

	/**
	 * @return Returns the role_id.
	 */
	public int getRole_id() {
		return role_id;
	}

	/**
	 * @return Returns the role_name.
	 */
	public String getRole_name() {
		return role_name;
	}

	/**
	 * Returns role for supplied role id
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getRoleById() throws SQLException, Exception {
		try {
			String getId = "select * from role where role_id = '" + role_id
					+ "'";
			st = conn.createStatement();
			rs = st.executeQuery(getId);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:getRoleById:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:getRoleById:Exception:" + e.toString());
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

	public void modifyRole(int intRoleID) {

	}

	/**
	 * @param conn
	 *            The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
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
		this.setRole_id(Integer.parseInt(theKey));

	}

	/**
	 * @param role_active_yn
	 *            The role_active_yn to set.
	 */
	public void setRole_active_yn(String role_active_yn) {
		this.role_active_yn = role_active_yn;
	}

	/**
	 * @param role_creation_date
	 *            The role_creation_date to set.
	 */
	public void setRole_creation_date(String role_creation_date) {
		this.role_creation_date = role_creation_date;
	}

	/**
	 * @param role_description
	 *            The role_description to set.
	 */
	public void setRole_description(String role_description) {
		this.role_description = role_description;
	}

	/**
	 * @param role_id
	 *            The role_id to set.
	 */
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	/**
	 * @param role_name
	 *            The role_name to set.
	 */
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	/**
	 * Update method.
	 * 
	 * @throws Exception
	 */
	public void updateRole() throws SQLException, Exception {
		try {
			String update = "UPDATE role SET " + "role_name = '" + role_name
					+ "', " + "role_description = '" + role_description + "', "
					+ "role_active_yn = '" + role_active_yn + "' "
					+ "WHERE role_id = '" + role_id + "'";

			pst = conn.prepareStatement(update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:updateRole:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:updateRole:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void ModifyRole(String pName, String pDesc, int pId)
			throws SQLException, Exception {
		try {
			String update = "UPDATE role SET " + "role_name = ?" + ", "
					+ "role_description = ? " + role_description
					+ "WHERE role_id = ?";

			pst = conn.prepareStatement(update);
			pst.setString(1, pName);
			pst.setString(2, pDesc);
			pst.setInt(3, pId);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Role:ModifyRole:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Role:ModifyRole:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	public void updateRole(int intRoleID, String strRoleName,
			String strRoleDescription) throws SQLException, Exception {
		String sqlQuery = "";
		try {
			sqlQuery = "UPDATE role SET " + "role_id = '" + role_id + "', "
					+ "'" + "role_name = '" + role_name + "', " + "'"
					+ "role_description = '" + role_description + "', " + "'"
					+ "role_active_yn = '" + role_active_yn + "', " + "'"
					+ "role_creation_date = '" + role_creation_date + "'";
			pst = conn.prepareStatement(sqlQuery);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("Role:updateRole:SQLException:" + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("Role:updateRole:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

}