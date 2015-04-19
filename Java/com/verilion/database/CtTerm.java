//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: CtTerm.java,v $
//Revision 1.4  2004/07/05 13:26:24  tcs
//Corrected insert method sql
//
//Revision 1.3  2004/06/25 17:47:58  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.2  2004/06/24 17:58:09  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.1  2004/05/23 04:52:45  tcs
//Initial entry into CVS
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
 * Manipulates ct_term code table
 * <P>
 * Jun 12, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * @author tsawler
 *  
 */
public class CtTerm {

	private int ct_term_id = 0;
	private String ct_term_name = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;

	public CtTerm() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateTerm() throws SQLException, Exception {
		try {
			String Update =
				"UPDATE ct_packages SET "
					+ "ct_term_name = '"
					+ ct_term_name
					+ "' "
					+ "WHERE ct_term_id = '"
					+ ct_term_id
					+ "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtTerm:updateTerm:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtTerm:updateTerm:Exception:" + e.toString());
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
	public void addTerm() throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO ct_term ("
			    	+ "ct_term_id, "
					+ "ct_term_name) "
					+ "VALUES("
					+ "'', "
					+ ct_term_name
					+ "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		}
		catch (SQLException e) {
			Errors.addError("CtTerm:addTerm:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("CtTerm:addTerm:Exception:" + e.toString());
		} finally {
		    if (pst != null) {
		        pst.close();
		        pst = null;
		    }
		}
	}

	/**
	 * Gets all term names/ids
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getTerms() throws SQLException, Exception {
		try {
			String query =
				"select * from ct_term";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtTerm:getTerms:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtTerm:getTerms:Exception:" + e.toString());
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
	 * Gets term by term_id, returning rowset
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getTermName() throws SQLException, Exception {
		try {
			String query =
				"select ct_term_name from ct_term where ct_term_id = '"
					+ ct_term_id
					+ "'";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtTerm:getTermName:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtTerm:getTermName:Exception:" + e.toString());
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
	 * Returns a single term name by term id
	 * 
	 * @return String - the term name
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getTermNameById() throws SQLException, Exception {
		String theName = "";
			try {
				String query =
					"select ct_term_name from ct_term where ct_term_id = '"
						+ ct_term_id
						+ "'";

				st = conn.createStatement();
				rs = st.executeQuery(query);
				if (rs.next()) {
					theName = rs.getString("ct_term_name");
				}
				rs.close();
				rs = null;
				st.close();
				st = null;
			}
			catch (SQLException e) {
				Errors.addError(
					"CtTerm:getTermName:SQLException:" + e.toString());
			}
			catch (Exception e) {
				Errors.addError(
					"CtTerm:getTermName:Exception:" + e.toString());
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
	 * Gets term by term_id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet getTerm() throws SQLException, Exception {
		try {
			String query =
				"select * from ct_term where ct_term_id = '"
					+ ct_term_id
					+ "'";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		}
		catch (SQLException e) {
			Errors.addError(
				"CtTerm:getTermName:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"CtTerm:getTermName:Exception:" + e.toString());
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
	 * @return Returns the conn.
	 */
	public Connection getConn() {
		return conn;
	}
	
	/**
	 * @param conn The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * @return Returns the ct_term_id.
	 */
	public int getCt_term_id() {
		return ct_term_id;
	}
	
	/**
	 * @param ct_term_id The ct_term_id to set.
	 */
	public void setCt_term_id(int ct_term_id) {
		this.ct_term_id = ct_term_id;
	}
	
	/**
	 * @return Returns the ct_term_name.
	 */
	public String getCt_term_name() {
		return ct_term_name;
	}
	
	/**
	 * @param ct_term_name The ct_term_name to set.
	 */
	public void setCt_term_name(String ct_term_name) {
		this.ct_term_name = ct_term_name;
	}
}