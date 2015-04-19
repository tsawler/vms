//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-04
//Revisions
//------------------------------------------------------------------------------
//$Log: ModulePosition.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:14  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/06/25 18:39:17  tcs
//Implemented use of disconnected rowsets
//
//Revision 1.1  2004/06/04 17:13:18  tcs
//Initial entry into CVS
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
* Manipulates module_position table in db
* 
* @author tsawler
*  
*/
public class ModulePosition {
	
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;

	DBUtils myDbUtil = new DBUtils();

	public ModulePosition() {
		super();
	}


	/**
	 * Returns all module position records
	 * 
	 * @return XDisconnectedRowSet
	 * @throws Exception
	 */
	public XDisconnectedRowSet getAllRecords() throws SQLException, Exception {

		try {
			String query = "select * from module_position ";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("ModulePosition:getAllRecords:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ModulePosition:getAllRecords:Exception:" + e.toString());
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
}