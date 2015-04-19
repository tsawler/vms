//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-12-14
//Revisions
//------------------------------------------------------------------------------
//$Log: RolesAssigned.java,v $
//Revision 1.1.2.6.10.1  2006/12/26 12:15:28  tcs
//Corrected Javadocs
//
//Revision 1.1.2.6  2005/05/18 02:22:22  deanh
//Fixed changeActiveStatus()
//
//Revision 1.1.2.5  2005/05/18 01:16:20  tcs
//Changted to select from view instead of table
//
//Revision 1.1.2.4  2005/03/05 15:53:37  deanh
//Changes for RolesAssigned.
//
//Revision 1.1.2.3  2005/02/04 02:15:24  deanh
//*** empty log message ***
//
//Revision 1.1.2.2  2004/12/17 18:11:09  tcs
//Modified for changes to DatabaseInterface
//
//Revision 1.1.2.1  2004/12/14 17:26:44  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates roles_assigned table in db
 * <P>
 * Dec 14, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class RolesAssigned implements DatabaseInterface {
	private Connection conn;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private int customer_id = 0;
	private XDisconnectedRowSet drs = new XDisconnectedRowSet();
	private int iLimit = 0;
	private int iOffset = 0;
	private PreparedStatement pst = null;
	private String roles_assigned_active_yn = "n";
	private int roles_assigned_customer_id = 0;
	
	private int roles_assigned_id = 0;
	private int roles_assigned_module_id = 0;
	private int roles_assigned_role_id = 0;
	private ResultSet rs = null;
	private String sCustomWhere = "";
	private Statement st = null;
	
	public RolesAssigned() {
		super();
	}
	
	/**
	 * Insert method.
	 * @return int
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public int addRolesAssigned() throws SQLException, Exception {
		int new_roles_assigned_id = 0;
		try {
			
			String save = "INSERT INTO roles_assigned ("
				+ "roles_assigned_role_id, "
				+ "roles_assigned_customer_id, "
				+ "roles_assigned_module_id) "
				+ "VALUES("
				+ "'"
				+ roles_assigned_role_id
				+ ", "
				+ "'"
				+ roles_assigned_customer_id
				+ ", "
				+ "'"
				+ roles_assigned_module_id
				+ "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			
			if (rs.next()) {
				new_roles_assigned_id = rs.getInt(1);
			}
			
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("RolesAssigned:addRoleAssigned:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:addRoleAssigned:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		
		return new_roles_assigned_id;
	}
	
	 /**
	    * Change active status of a role
	    * 
	    * @throws Exception
	    */
	   public void changeActiveStatus() throws SQLException, Exception {
	      try {
	         String query = "update role set roles_assigned_active_yn = '"
	               + roles_assigned_active_yn
	               + "' where roles_assigned_id = '"
	               + roles_assigned_id
	               + "'";
	         pst = conn.prepareStatement(query);
	         pst.executeUpdate();
	         pst.close();
	         pst = null;
	      } catch (SQLException e) {
	         Errors
	               .addError("RolesAssigned:changeActiveStatus:SQLException:" + e.toString());
	      } catch (Exception e) {
	         Errors.addError("RolesAssigned:changeActiveStatus:Exception:" + e.toString());
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
	    * @see com.verilion.database.DatabaseInterface#changeActiveStatus(java.lang.String)
	    */
	   public void changeActiveStatus(String psStatus) throws SQLException, Exception {
	   		this.setRoles_assigned_active_yn(psStatus);
	   		this.changeActiveStatus();

	   }
	
	/* (non-Javadoc)
	 * @see com.verilion.database.DatabaseInterface#createCustomWhere(java.lang.String)
	 */
	public void createCustomWhere(String psCustomWhere) {
		this.sCustomWhere = psCustomWhere;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#deleteRecord()
	 */
	public void deleteRecord() throws SQLException, Exception {
		
		try {
			String query = "delete from roles_assigned where roles_assigned_id = '" 
				+ roles_assigned_id + "'";
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}
	
	/**
	 * Returns all active roles assigned ordered
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveRolesAssigned() throws SQLException,
	Exception {
		try {
			String query = "select * from v_roles_assigned where roles_assigned_active_yn = 'y'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("RoleAssigned:getAllActiveRolesAssigned:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors
			.addError("RolesAssigned:getAllActiveRolesAssigned:Exception:" + e.toString());
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
	 * Returns all active roles assigned for a customer
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllActiveRolesForCustomer()
	throws SQLException, Exception {
		try {
			
			String query = "select * from roles_assigned where roles_assigned_active_yn = 'y' "
				+ "and customer_id = '"
				+ customer_id
				+ "' ";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Role:getAllActiveRolesAssignedForCustomer:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:getAllActiveRolesAssignedForCustomer:Exception:"
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
	public XDisconnectedRowSet getAllRolesAssigned() throws SQLException, Exception {
		try {
			String query = "select * from v_roles_assigned ";
			if (sCustomWhere.length() > 6) {
				query += " " + sCustomWhere;
			}
			query += " order by roles_assigned_id";
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
			Errors.addError("RolesAssigned:getAllRoles:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:getAllRoles:Exception:" + e.toString());
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
	 * returns number of active roles assigned for a customer
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getCountForCustomer() throws SQLException, Exception {
		int numberOfItems = 0;
		try {
			String query = "select count(roles_assigned_id) from role "
				+ "where customer_id = '"
				+ customer_id 
				+ "' "
				+ "and roles_assigned_active_yn = 'y' ";
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
			Errors.addError("RolesAssigned:getCountForCustomer:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:getCountForCustomer:Exception:" + e.toString());
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
	
	public XDisconnectedRowSet GetOneRolesAssigned() throws Exception, SQLException {
		try {
			String getOneCust = "SELECT * from v_roles_assigned WHERE roles_assigned_role_id="
				+ roles_assigned_role_id;
			st = conn.createStatement();
			rs = st.executeQuery(getOneCust);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
			.addError("RolesAssigned:GetOneRolesAssigned:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:GetOneRolesAssigned:Exception:" + e.toString());
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
	 * Returns roles assigned for supplied roles assigned id
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getRoleAssignedById() throws SQLException, Exception {
		try {
			String getId = "select * from v_roles_assigned where roles_assigned_id = '" + roles_assigned_id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(getId);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("RolesAssigned:getRolesAssignedById:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:getRolesAssignedById:Exception:" + e.toString());
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
	 * Gets all role names/ids
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getRoleAssignedForCustomerId()
	throws SQLException, Exception {
		try {
			String query = "select * from v_roles_assigned where "
				+ "roles_assigned_customer_id = '"
				+ roles_assigned_customer_id
				+ "'";
			
			st = conn.createStatement();
			rs = st.executeQuery(query);
			drs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors
			.addError("RolesAssigned:getRoleAssignedForCustomerId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:getRoleAssignedForCustomerId:Exception:"
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
		return drs;
	}
	
	/**
	 * @return Returns the roles_assigned_customer_id.
	 */
	public int getRoles_assigned_customer_id() {
		return roles_assigned_customer_id;
	}
	
	/**
	 * @return Returns the roles_assigned_id.
	 */
	public int getRoles_assigned_id() {
		return roles_assigned_id;
	}
	
	/**
	 * @return Returns the roles_assigned_module_id.
	 */
	public int getRoles_assigned_module_id() {
		return roles_assigned_module_id;
	}
	
	/**
	 * @return Returns the roles_assigned_role_id.
	 */
	public int getRoles_assigned_role_id() {
		return roles_assigned_role_id;
	}
	
	/**
	 * Gets role by id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getRolesAssignedById() throws SQLException,
	Exception {
		try {
			String query = "select * from v_roles_assigned where "
				+ "roles_assigned_id = '"
				+ roles_assigned_id
				+ "'";
			
			st = conn.createStatement();
			rs = st.executeQuery(query);
			drs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("RolesAssigned:getRoleAssignedById:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:getRoleAssignedById:Exception:"
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
		return drs;
	}
	/**
	 * @param conn The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	/* (non-Javadoc)
	 * @see com.verilion.database.DatabaseInterface#setLimit(int)
	 */
	public void setLimit(int pLimit) {
		this.iLimit = pLimit;
	}
	
	/* (non-Javadoc)
	 * @see com.verilion.database.DatabaseInterface#setOffset(int)
	 */
	public void setOffset(int pOffset) {
		this.iOffset = pOffset;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.database.DatabaseInterface#setPrimaryKey(java.lang.String)
	 */
	public void setPrimaryKey(String theKey) {
		this.setRoles_assigned_id(Integer.parseInt(theKey));
	}
	
	/**
	 * @param role_assigned_customer_id
	 *           The roles_assigned_customer_id to set.
	 */
	public void setRoles_assigned_customer_id(int role_assigned_customer_id) {
		this.roles_assigned_customer_id = role_assigned_customer_id;
	}
	
	/**
	 * @param role_assigned_id
	 *           The roles_assigned_id to set.
	 */
	public void setRoles_assigned_id(int role_assigned_id) {
		this.roles_assigned_id = role_assigned_id;
	}
	
	/**
	 * @param role_assigned_module_id
	 *           The roles_assigned_module_id to set.
	 */
	public void setRoles_assigned_module_id(int role_assigned_module_id) {
		this.roles_assigned_module_id = role_assigned_module_id;
	}
	
	/**
	 * @param role_assigned_role_id
	 *           The roles_assigned_role_id to set.
	 */
	public void setRoles_assigned_role_id(int role_assigned_role_id) {
		this.roles_assigned_role_id = role_assigned_role_id;
	}
	
	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateRolesAssigned() throws SQLException, Exception {
		try {
			String Update = "UPDATE roles_assigned SET "
				+ "roles_assigned_role_id = '"
				+ roles_assigned_role_id
				+ "', "
				+ "roles_assigned_customer_id ='"
				+ roles_assigned_customer_id
				+ ", "
				+ "roles_assigned_module_id = '"
				+ roles_assigned_module_id
				+ "' "
				+ "WHERE roles_assigned_id = '"
				+ roles_assigned_id
				+ "'";
			
			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("RolesAssigned:updateRoleAssigned:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("RolesAssigned:updateRoleAssigned:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}
	/**
	 * @return Returns the customer_id.
	 */
	public int getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id The customer_id to set.
	 */
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @return Returns the roles_assigned_active_yn.
	 */
	public String getRoles_assigned_active_yn() {
		return roles_assigned_active_yn;
	}
	/**
	 * @param roles_assigned_active_yn The roles_assigned_active_yn to set.
	 */
	public void setRoles_assigned_active_yn(String roles_assigned_active_yn) {
		this.roles_assigned_active_yn = roles_assigned_active_yn;
	}
}