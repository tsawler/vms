package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.verilion.database.DBUtils;
import com.verilion.object.Errors;

/**
 * Manipulates customer_address_detail table.
 * <P>
 * Jun 12, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class CustomerAddressDetail {

	private int customer_address_id = 0;
	private int ct_address_type_id = 0;
	private String customer_address = "";
	private String customer_town_city = "";
	private int ct_province_state_id = 0;
	private int ct_country_id = 0;
	private String customer_zip_postal = "";
	private String primary_address_yn = "";
	private int customer_id = 0;
	private ResultSet rs = null;
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;

	DBUtils myDbUtils = new DBUtils();

	public CustomerAddressDetail() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateCustomerAddressByCtAddressId() throws SQLException,
			Exception {
		try {
			String Update = "UPDATE customer_address_detail SET "
					+ "ct_address_type_id = '"
					+ ct_address_type_id
					+ "', "
					+ "customer_address = '"
					+ customer_address
					+ "', "
					+ "customer_town_city = '"
					+ customer_town_city
					+ "', "
					+ "ct_province_state_id = '"
					+ ct_province_state_id
					+ "', "
					+ "ct_country_id = '"
					+ ct_country_id
					+ "', "
					+ "customer_zip_postal = '"
					+ customer_zip_postal
					+ "', "
					+ "primary_address_yn = '"
					+ primary_address_yn
					+ "' "
					+ "WHERE customer_address_id = '"
					+ customer_address_id
					+ "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerAddressDetail:updateCustomerAddressByCtAddressId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerAddressDetail:updateCustomerAddressByCtAddressId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateCustomerAddressByCustomerIdAndCtAddressTypeId()
			throws SQLException, Exception {
		try {
			String Update = "UPDATE customer_address_detail SET "
					+ "ct_address_type_id = '"
					+ ct_address_type_id
					+ "', "
					+ "customer_address = '"
					+ customer_address
					+ "', "
					+ "customer_town_city = '"
					+ customer_town_city
					+ "', "
					+ "ct_province_state_id = '"
					+ ct_province_state_id
					+ "', "
					+ "ct_country_id = '"
					+ ct_country_id
					+ "', "
					+ "customer_zip_postal = '"
					+ customer_zip_postal
					+ "', "
					+ "primary_address_yn = '"
					+ primary_address_yn
					+ "' "
					+ "WHERE customer_id = '"
					+ customer_id
					+ "' "
					+ "AND ct_address_type_id = '"
					+ ct_address_type_id + "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerAddressDetail:updateCustomerAddressByCustomerIdAndCtAddressTypeId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerAddressDetail:updateCustomerAddressByCustomerIdAndCtAddressTypeId:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Adds an address.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addCustomerAddressDetail() throws SQLException, Exception {
		try {
			String save = "insert into customer_address_detail ("
					+ "ct_address_type_id, " + "customer_address, "
					+ "customer_town_city, " + "ct_province_state_id, "
					+ "ct_country_id, " + "customer_zip_postal, "
					+ "primary_address_yn, " + "customer_id) " + "values ("
					+ "'"
					+ ct_address_type_id
					+ "', "
					+ "'"
					+ customer_address
					+ "', "
					+ "'"
					+ customer_town_city
					+ "', "
					+ "'"
					+ ct_province_state_id
					+ "', "
					+ "'"
					+ ct_country_id
					+ "', "
					+ "'"
					+ customer_zip_postal
					+ "', "
					+ "'"
					+ primary_address_yn + "', " + "'" + customer_id + "')";

			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerAddressDetail:addCustomerAddressDetail:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerAddressDetail:addCustomerAddressDetail:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Returns the city from customer's primary address as string.
	 * 
	 * @param inCustomerId
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getCustomerCity(int inCustomerId) throws SQLException,
			Exception {
		String theCity = "";
		try {
			String query = "select " + "cad.customer_town_city " + "from "
					+ "customer as c, " + "customer_address_detail as cad "
					+ "where " + "c.customer_id = '" + inCustomerId + "' "
					+ "and " + "c.customer_id = cad.customer_id "
					+ "and cad.primary_address_yn = 'y'";

			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				theCity = rs.getString("customer_town_city");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("CustomerAddressDetail:getCustomerCity:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerAddressDetail:getCustomerCity:Exception:"
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
		return theCity;
	}

	/**
	 * Returns the province from customer's primary address as string.
	 * 
	 * @param inCustomerId
	 * @return String
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getCustomerProvince(int inCustomerId) throws SQLException,
			Exception {
		String theProvince = "";
		try {
			String query = "select "
					+ "ct_ps.ct_province_state_name "
					+ "from "
					+ "customer as c, "
					+ "customer_address_detail as cad, "
					+ "ct_province_state as ct_ps "
					+ "where "
					+ "c.customer_id = '"
					+ inCustomerId
					+ "' "
					+ "and cad.customer_id = '"
					+ inCustomerId
					+ "' "
					+ "and cad.primary_address_yn = 'y' "
					+ "and cad.ct_province_state_id = ct_ps.ct_province_state_id";

			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				theProvince = rs.getString("ct_province_state_name");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("CustomerAddressDetail:getCustomerProvince:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerAddressDetail:getCustomerProvince:Exception:"
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
		return theProvince;
	}

	/**
	 * Delete method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deleteAddressByCustomerId() throws SQLException, Exception {
		try {
			String Update = "delete from customer_address_detail where customer_id = '"
					+ customer_id + "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("CustomerAddressDetail:deleteAddressByCustomerId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("CustomerAddressDetail:deleteAddressByCustomerId:Exception:"
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
	 * @return Returns the ct_address_type_id.
	 */
	public int getCt_address_type_id() {
		return ct_address_type_id;
	}

	/**
	 * @param ct_address_type_id
	 *            The ct_address_type_id to set.
	 */
	public void setCt_address_type_id(int ct_address_type_id) {
		this.ct_address_type_id = ct_address_type_id;
	}

	/**
	 * @return Returns the ct_country_id.
	 */
	public int getCt_country_id() {
		return ct_country_id;
	}

	/**
	 * @param ct_country_id
	 *            The ct_country_id to set.
	 */
	public void setCt_country_id(int ct_country_id) {
		this.ct_country_id = ct_country_id;
	}

	/**
	 * @return Returns the ct_province_state_id.
	 */
	public int getCt_province_state_id() {
		return ct_province_state_id;
	}

	/**
	 * @param ct_province_state_id
	 *            The ct_province_state_id to set.
	 */
	public void setCt_province_state_id(int ct_province_state_id) {
		this.ct_province_state_id = ct_province_state_id;
	}

	/**
	 * @return Returns the customer_address.
	 */
	public String getCustomer_address() {
		return customer_address;
	}

	/**
	 * @param customer_address
	 *            The customer_address to set.
	 */
	public void setCustomer_address(String customer_address) {
		this.customer_address = myDbUtils.fixSqlFieldValue(customer_address);
	}

	/**
	 * @return Returns the customer_address_id.
	 */
	public int getCustomer_address_id() {
		return customer_address_id;
	}

	/**
	 * @param customer_address_id
	 *            The customer_address_id to set.
	 */
	public void setCustomer_address_id(int customer_address_id) {
		this.customer_address_id = customer_address_id;
	}

	/**
	 * @return Returns the customer_id.
	 */
	public int getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id
	 *            The customer_id to set.
	 */
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * @return Returns the customer_town_city.
	 */
	public String getCustomer_town_city() {
		return customer_town_city;
	}

	/**
	 * @param customer_town_city
	 *            The customer_town_city to set.
	 */
	public void setCustomer_town_city(String customer_town_city) {
		this.customer_town_city = myDbUtils
				.fixSqlFieldValue(customer_town_city);
	}

	/**
	 * @return Returns the customer_zip_postal.
	 */
	public String getCustomer_zip_postal() {
		return customer_zip_postal;
	}

	/**
	 * @param customer_zip_postal
	 *            The customer_zip_postal to set.
	 */
	public void setCustomer_zip_postal(String customer_zip_postal) {
		this.customer_zip_postal = customer_zip_postal;
	}

	/**
	 * @return Returns the primary_address_yn.
	 */
	public String getPrimary_address_yn() {
		return primary_address_yn;
	}

	/**
	 * @param primary_address_yn
	 *            The primary_address_yn to set.
	 */
	public void setPrimary_address_yn(String primary_address_yn) {
		this.primary_address_yn = primary_address_yn;
	}
}