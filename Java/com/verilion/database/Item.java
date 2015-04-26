package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates item in db
 * <P>
 * November 29 2005
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class Item {

	private int item_id = 0;
	private String item_name = "";
	private String item_short_description = "";
	private String item_long_description = "";
	private int inventory_on_hand = 0;
	private int inventory_threshold = 0;
	private String item_sku = "";
	private float item_price = 0.0f;
	private String customWhere = "";
	private String customOrder = "";
	private String item_active_yn = "";
	private String item_taxable_yn = "";
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
	public void UpdateItem() throws SQLException, Exception {
		try {
			String Update = "UPDATE item SET " + "item_name = '" + item_name
					+ "', " + "item_price = '" + item_price + "', "
					+ "item_short_description = '" + item_short_description
					+ "', " + "item_long_description = '"
					+ item_long_description + "', "
					+ "item_inventory_on_hand = '" + inventory_on_hand + "', "
					+ "item_inventory_threshold = '" + inventory_threshold
					+ "', " + "item_sku = '" + item_sku + "', "
					+ "item_taxable_yn = '" + item_taxable_yn + "', "
					+ "item_active_yn = '" + item_active_yn + "' "
					+ "WHERE item_id = '" + item_id + "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("Item:UpdateItem:SQLException:" + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("Item:UpdateItem:Exception:" + e.toString());
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
	public void AddItem() throws SQLException, Exception {
		try {
			String save = "INSERT INTO item (" + "item_name, " + "item_price, "
					+ "item_short_description, " + "item_long_description, "
					+ "item_inventory_on_hand, " + "item_inventory_threshold, "
					+ "item_sku, " + "item_taxable_yn, " + "item_active_yn) "
					+ "VALUES(" + "'"
					+ item_name
					+ "', "
					+ "'"
					+ item_price
					+ "', "
					+ "'"
					+ item_short_description
					+ "', "
					+ "'"
					+ item_long_description
					+ "', "
					+ "'"
					+ inventory_on_hand
					+ "', "
					+ "'"
					+ inventory_threshold
					+ "', "
					+ "'"
					+ item_sku
					+ "', "
					+ "'"
					+ item_taxable_yn
					+ "', "
					+ "'"
					+ item_active_yn + "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Item:AddItem:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Item:AddItem:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Gets all active items, sorted by item name.
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet GetAllActiveItems() throws SQLException,
			Exception {
		try {
			String query = "Select * from item where item_active_yn = 'y' order by item_name";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Item:GetAllActiveItems:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Item:GetAllActiveItems:Exception:" + e.toString());
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

	public XDisconnectedRowSet GetRows() throws SQLException, Exception {
		try {
			String query = "Select * from item where " + customWhere
					+ customOrder;

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Item:GetRows:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Item:GetRows:Exception:" + e.toString());
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
	 * Gets item by id
	 * 
	 * @return XDisconnectedRowSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public XDisconnectedRowSet GetItemDetail() throws SQLException, Exception {
		try {
			String query = "Select * from item where item_id = '" + item_id
					+ "'";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Item:GetItemDetail:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Item:GetItemDetail:Exception:" + e.toString());
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
	 * Deletes item by id
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void DeleteItem() throws SQLException, Exception {
		try {
			String query = "delete from item where item_id = '" + item_id + "'";

			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Item:DeleteItem:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Item:DeleteItem:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}

	}

	/**
	 * Change active status of an item
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void ChangeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update item set item_active = '" + item_active_yn
					+ "' where item_id = '" + item_id + "'";

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
	 * @return Returns the inventory_on_hand.
	 */
	public int getInventory_on_hand() {
		return inventory_on_hand;
	}

	/**
	 * @param inventory_on_hand
	 *            The inventory_on_hand to set.
	 */
	public void setInventory_on_hand(int inventory_on_hand) {
		this.inventory_on_hand = inventory_on_hand;
	}

	/**
	 * @return Returns the inventory_threshold.
	 */
	public int getInventory_threshold() {
		return inventory_threshold;
	}

	/**
	 * @param inventory_threshold
	 *            The inventory_threshold to set.
	 */
	public void setInventory_threshold(int inventory_threshold) {
		this.inventory_threshold = inventory_threshold;
	}

	/**
	 * @return Returns the item_active_yn.
	 */
	public String getItem_active_yn() {
		return item_active_yn;
	}

	/**
	 * @param item_active_yn
	 *            The item_active_yn to set.
	 */
	public void setItem_active_yn(String item_active_yn) {
		this.item_active_yn = item_active_yn;
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

	/**
	 * @return Returns the item_long_description.
	 */
	public String getItem_long_description() {
		return item_long_description;
	}

	/**
	 * @param item_long_description
	 *            The item_long_description to set.
	 */
	public void setItem_long_description(String item_long_description) {
		this.item_long_description = item_long_description;
	}

	/**
	 * @return Returns the item_name.
	 */
	public String getItem_name() {
		return item_name;
	}

	/**
	 * @param item_name
	 *            The item_name to set.
	 */
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	/**
	 * @return Returns the item_price.
	 */
	public float getItem_price() {
		return item_price;
	}

	/**
	 * @param item_price
	 *            The item_price to set.
	 */
	public void setItem_price(float item_price) {
		this.item_price = item_price;
	}

	/**
	 * @return Returns the item_short_description.
	 */
	public String getItem_short_description() {
		return item_short_description;
	}

	/**
	 * @param item_short_description
	 *            The item_short_description to set.
	 */
	public void setItem_short_description(String item_short_description) {
		this.item_short_description = item_short_description;
	}

	/**
	 * @return Returns the item_sku.
	 */
	public String getItem_sku() {
		return item_sku;
	}

	/**
	 * @param item_sku
	 *            The item_sku to set.
	 */
	public void setItem_sku(String item_sku) {
		this.item_sku = item_sku;
	}

	/**
	 * @return Returns the item_taxable_yn.
	 */
	public String getItem_taxable_yn() {
		return item_taxable_yn;
	}

	/**
	 * @param item_taxable_yn
	 *            The item_taxable_yn to set.
	 */
	public void setItem_taxable_yn(String item_taxable_yn) {
		this.item_taxable_yn = item_taxable_yn;
	}
}