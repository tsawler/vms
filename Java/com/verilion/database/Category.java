//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-11-29
//Revisions
//------------------------------------------------------------------------------
//$Log: Category.java,v $
//Revision 1.1.2.6.2.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.1.2.6  2005/12/03 03:22:09  tcs
//Simplified a couple of methods
//
//Revision 1.1.2.5  2005/12/01 18:37:57  tcs
//Finished crumbtrail code
//
//Revision 1.1.2.4  2005/11/30 19:46:58  tcs
//Fixed check for null in sql
//
//Revision 1.1.2.3  2005/11/29 16:15:10  tcs
//Added GetSubcategory method
//
//Revision 1.1.2.2  2005/11/29 16:09:35  tcs
//Fixed comments
//
//Revision 1.1.2.1  2005/11/29 16:09:18  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.objects.CategoryObject;
import com.verilion.object.Errors;

/**
 * Manipulates category in db
 * <P>
 * November 29 2005
 * <P>
 * package com.verilion.database
 * <P>
 * 
 * @author tsawler
 * 
 */
public class Category {

	private int category_id = 0;
	private int parent_category_id = 0;
	private String category_name = "";
	private String category_active_yn = "";
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
	public void UpdateCategory() throws SQLException, Exception {
		try {
			String Update = "UPDATE category SET " + "parent_category_id = '"
					+ parent_category_id + "', " + "category_name = '"
					+ category_name + "', " + "category_active_yn = '"
					+ category_active_yn + "' " + "WHERE category_id = '"
					+ category_id + "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("Category:UpdateCategory:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("Category:v:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * Add method
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void AddCategory() throws SQLException, Exception {
		try {
			String save = "INSERT INTO category (" + "category_name, "
					+ "category_active_yn, " + "parent_category_id) "
					+ "VALUES(" + "'" + category_name + "', " + "'"
					+ category_active_yn + "', " + "'" + parent_category_id
					+ "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Category:AddCategory:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Category:AddCategory:Exception:" + e.toString());
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
	public XDisconnectedRowSet GetAllActiveCategories() throws SQLException,
			Exception {
		try {
			String query = "Select * from category where category_active_yn = 'y' order by category_name";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Category:GetAllActiveCategories:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Category:GetAllActiveCategories:Exception:"
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

	public XDisconnectedRowSet GetSubcategory() throws SQLException, Exception {
		try {
			String query = "";

			if (parent_category_id > 0) {
				query = "Select * from category where category_active_yn = 'y' and parent_category_id = '"
						+ parent_category_id + "' order by category_name";
			} else {
				query = "Select * from category where category_active_yn = 'y' and parent_category_id is null order by category_name";
			}

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("Category:GetAllActiveCategories:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("Category:GetAllActiveCategories:Exception:"
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

	@SuppressWarnings("unchecked")
	public List GetDescendantsOfCategoryId(int id) throws SQLException,
			Exception {

		List myList = new ArrayList();

		try {
			String query = "select category_id from category where "
					+ "parent_category_id = '" + id
					+ "' and category_active_yn = 'y'";

			st = conn.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				myList.add(rs.getString(1));
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myList;
	}

	public XDisconnectedRowSet GetParentsForCatId() throws SQLException,
			Exception {
		try {
			String query = "";

			query = "select category_id, category_name from category where category_id = (select parent_category_id from category where "
					+ "category_id = '" + category_id + "')";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Category:GetParentsForCatId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Category:GetParentsForCatId:Exception:"
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

	@SuppressWarnings("unchecked")
	public List GetAllParentsForCatId() throws SQLException, Exception {

		List myList = new ArrayList();

		try {
			String query = "";
			boolean done = false;
			int count = 0;

			query = "select parent_category_id from category where category_id = '"
					+ category_id + "'";

			st = conn.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				count = rs.getInt(1);

			if (count == 0) {
				done = true;
			}

			rs.close();
			rs = null;
			st.close();
			st = null;

			while (!done) {
				CategoryObject co = new CategoryObject();
				query = "select category_id, category_name, parent_category_id from category "
						+ "where category_id = (select case when parent_category_id is null then category_id "
						+ "when parent_category_id is not null then parent_category_id end "
						+ "as parent_category_id from verilion.category where category_id = '"
						+ category_id + "')";
				st = conn.createStatement();
				rs = st.executeQuery(query);

				if (rs.next()) {
					co.setCategory_name(rs.getString("category_name"));
					co.setCategory_id(rs.getInt("category_id"));
					category_id = rs.getInt("parent_category_id");
					myList.add(0, co);
				} else {
					done = true;
				}

				rs.close();
				rs = null;
				st.close();
				st = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("Category:GetAllParentsForCatId:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("Category:GetAllParentsForCatId:Exception:"
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
		return myList;
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
			String query = "Select * from category where " + customWhere
					+ customOrder;

			st = conn.createStatement();
			rs = st.executeQuery(query);
			crs.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("Category:GetRows:SQLException:" + e.toString());
		} catch (Exception e) {
			Errors.addError("Category:GetRows:Exception:" + e.toString());
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
	 * Deletes category by id
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void DeleteCategory() throws SQLException, Exception {
		try {
			String query = "delete from category where category_id = '"
					+ category_id + "'";

			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Category:DeleteCategory:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Category:DeleteCategory:Exception:" + e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}

	}

	/**
	 * Change active status of an category
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void ChangeActiveStatus() throws SQLException, Exception {
		try {
			String query = "update category set category_active_yn = '"
					+ category_active_yn + "' where category_id = '"
					+ category_id + "'";

			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			Errors.addError("Category:ChangeActiveStatus:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("Category:ChangeActiveStatus:Exception:"
					+ e.toString());
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}

	}

	/**
	 * @return Returns the category_active_yn.
	 */
	public String getCategory_active_yn() {
		return category_active_yn;
	}

	/**
	 * @param category_active_yn
	 *            The category_active_yn to set.
	 */
	public void setCategory_active_yn(String category_active_yn) {
		this.category_active_yn = category_active_yn;
	}

	/**
	 * @return Returns the category_id.
	 */
	public int getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id
	 *            The category_id to set.
	 */
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	/**
	 * @return Returns the category_name.
	 */
	public String getCategory_name() {
		return category_name;
	}

	/**
	 * @param category_name
	 *            The category_name to set.
	 */
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
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
	 * @return Returns the parent_category_id.
	 */
	public int getParent_category_id() {
		return parent_category_id;
	}

	/**
	 * @param parent_category_id
	 *            The parent_category_id to set.
	 */
	public void setParent_category_id(int parent_category_id) {
		this.parent_category_id = parent_category_id;
	}

}