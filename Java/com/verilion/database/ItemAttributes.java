package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates item_attributes in db
 * <P>
 * November 29 2005
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ItemAttributes {

	private int item_attributes_id = 0;
	private int item_id = 0;
	private String item_attributes_name = "";
	private String item_attributes_active_yn = "";
	private int item_attributes_order = 0;
	private String customWhere = "";
	private String customOrder = "";
	private ResultSet rs = null;
	private XDisconnectedRowSet crs = new XDisconnectedRowSet();
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;
	DBUtils myDbUtils = new DBUtils();

	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void UpdateItemAttributes() throws SQLException, Exception {
		try {
			String Update = "UPDATE item_attributes SET " + "item_id = '"
					+ item_id + "', " + "item_attributes_name = '"
					+ item_attributes_name + "', "
					+ "item_attributes_active_yn = '"
					+ item_attributes_active_yn + "', "
					+ "item_attributes_order = '" + item_attributes_order
					+ "' " + "WHERE item_attributes_id = '"
					+ item_attributes_id + "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("ItemAttributes:UpdateItemAttributes:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("ItemAttributes:UpdateItemAttributes:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Adds method
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void AddItemAttributes() throws SQLException, Exception {
		try {
			String save = "INSERT INTO item_attributes (" + "item_id, "
					+ "item_attributes_name, " + "item_attributes_active_yn, "
					+ "item_attributes_order) " + "VALUES(" + "'" + item_id
					+ "', " + "'" + item_attributes_name + "', " + "'"
					+ item_attributes_active_yn + "', " + "'"
					+ item_attributes_order + "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("ItemAttributes:AddItemAttributes:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ItemAttributes:AddItemAttributes:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Gets all active attributes for item
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet GetAllActiveItemAttributes()
			throws SQLException, Exception {
		try {
			String query = "Select * from item_attributes where item_attributes_active_yn = 'y' order by item_attributes_order";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("ItemAttributes:GetAllActiveItemAttributes:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ItemAttributes:GetAllActiveItemAttributes:Exception:"
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
	 * Custom query of this table
	 * 
	 * @return drs
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet GetRows() throws SQLException, Exception {
		try {
			String query = "Select * from item_attributes where " + customWhere
					+ customOrder;

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("ItemAttributes:GetRows:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ItemAttributes:GetRows:Exception:" + e.toString());
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
	 * Deletes item attribute by id
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void DeleteItem() throws SQLException, Exception {
		try {
			String query = "delete from item_attributes where item_attributes_id = '"
					+ item_attributes_id + "'";

			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("ItemAttributes:DeleteItemAttribute:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("ItemAttributes:DeleteItemAttribute:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}

	}

	/**
	 * Change active status of an item_attribute
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void ChangeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update item_attributes set item_attributes_active = '"
					+ item_attributes_active_yn
					+ "' where item_attributes_id = '"
					+ item_attributes_id
					+ "'";

			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Item:ChangeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Item:ChangeActiveStatus:Exception:" + e.toString());
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
	 * @return Returns the customOrder.
	 */
	public String getCustomOrder() {
		return customOrder;
	}

	/**
	 * @param customOrder
	 *            The customOrder to set.
	 */
	public void setCustomOrder(String customOrder) {
		this.customOrder = customOrder;
	}

	/**
	 * @return Returns the customWhere.
	 */
	public String getCustomWhere() {
		return customWhere;
	}

	/**
	 * @param customWhere
	 *            The customWhere to set.
	 */
	public void setCustomWhere(String customWhere) {
		this.customWhere = customWhere;
	}

	/**
	 * @return Returns the item_attributes_active_yn.
	 */
	public String getItem_attributes_active_yn() {
		return item_attributes_active_yn;
	}

	/**
	 * @param item_attributes_active_yn
	 *            The item_attributes_active_yn to set.
	 */
	public void setItem_attributes_active_yn(String item_attributes_active_yn) {
		this.item_attributes_active_yn = item_attributes_active_yn;
	}

	/**
	 * @return Returns the item_attributes_id.
	 */
	public int getItem_attributes_id() {
		return item_attributes_id;
	}

	/**
	 * @param item_attributes_id
	 *            The item_attributes_id to set.
	 */
	public void setItem_attributes_id(int item_attributes_id) {
		this.item_attributes_id = item_attributes_id;
	}

	/**
	 * @return Returns the item_attributes_name.
	 */
	public String getItem_attributes_name() {
		return item_attributes_name;
	}

	/**
	 * @param item_attributes_name
	 *            The item_attributes_name to set.
	 */
	public void setItem_attributes_name(String item_attributes_name) {
		this.item_attributes_name = item_attributes_name;
	}

	/**
	 * @return Returns the item_attributes_order.
	 */
	public int getItem_attributes_order() {
		return item_attributes_order;
	}

	/**
	 * @param item_attributes_order
	 *            The item_attributes_order to set.
	 */
	public void setItem_attributes_order(int item_attributes_order) {
		this.item_attributes_order = item_attributes_order;
	}

	/**
	 * @return Returns the item_id.
	 */
	public int getItem_id() {
		return item_id;
	}

	/**
	 * @param item_id
	 *            The item_id to set.
	 */
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

}