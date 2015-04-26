package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Manipulates generic table in the database
 * 
 * @author tsawler
 * 
 */
public class GenericTable implements DatabaseInterface {

	private ResultSet rs = null;
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;
	private String sSelectWhat = "*";
	private String sCustomFrom = "";
	private String sCustomWhere = " where true ";
	DBUtils myDbUtil = new DBUtils();
	private String sTable = "";
	private String sPrimaryKey = "";
	private String sCustomOrder = "";
	int iPrimaryKey = 0;
	private String sSequence = "";
	private String sCustomLimit = "";

	String updateWhat = "";
	String insertWhat = "";
	Vector<String> updateFieldNamesVector = new Vector<String>();
	Vector<String> updateFieldValuesVector = new Vector<String>();
	Vector<String> updateFieldTypesVector = new Vector<String>();

	public GenericTable(String theTable) {
		this.sTable = theTable;
		this.clearUpdateVectors();
	}

	public GenericTable() {
		this.clearUpdateVectors();
	}

	public RowSetDynaClass getAllRecordsDynaBean() throws SQLException,
			Exception {

		RowSetDynaClass resultset = null;

		try {
			String query = "select " + sSelectWhat + " from " + sTable
					+ sCustomFrom + sCustomWhere + sCustomOrder + " "
					+ sCustomLimit;

			// System.out.println("query:\n" + query);
			st = conn.createStatement();
			rs = st.executeQuery(query);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("GenericTable:getAllRecordsDynaBean:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("GenericTable:getAllRecordsDynaBean:Exception:"
					+ e.toString());
		}
		return resultset;
	}

	public RowSetDynaClass getAllActiveRecordsDynaBean() throws SQLException,
			Exception {

		RowSetDynaClass resultset = null;

		try {
			String query = "select " + sSelectWhat + " from " + sTable
					+ sCustomFrom + sCustomWhere + " and active_yn = 'y' "
					+ sCustomOrder + " " + sCustomLimit;

			st = conn.createStatement();
			rs = st.executeQuery(query);
			resultset = new RowSetDynaClass(rs, false);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("GenericTable:getAllActiveRecordsDynaBean:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("GenericTable:getAllActiveRecordsDynaBean:Exception:"
					+ e.toString());
		}
		return resultset;
	}

	public XDisconnectedRowSet getAllActiveRecordsDisconnected()
			throws SQLException, Exception {

		XDisconnectedRowSet resultset = new XDisconnectedRowSet();

		try {
			String query = "select " + sSelectWhat + " from " + sTable + " "
					+ sCustomFrom + " " + sCustomWhere
					+ " and active_yn = 'y' " + sCustomOrder + " "
					+ sCustomLimit;

			st = conn.createStatement();
			rs = st.executeQuery(query);
			resultset.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("GenericTable:getAllActiveRecordsDisconnected:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("GenericTable:getAllActiveRecordsDisconnected:Exception:"
					+ e.toString());
		}
		return resultset;
	}

	public XDisconnectedRowSet getAllRecordsDisconnected() throws SQLException,
			Exception {

		XDisconnectedRowSet resultset = new XDisconnectedRowSet();

		try {
			String query = "select " + sSelectWhat + " from " + sTable + " "
					+ sCustomFrom + " " + sCustomWhere + " " + sCustomOrder
					+ " " + sCustomLimit;
			// System.out.println(query);
			st = conn.createStatement();
			rs = st.executeQuery(query);
			resultset.populate(rs);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("GenericTable:getAllRecordsDisconnected:SQLException:"
					+ e.toString());
			e.printStackTrace();
			System.out.println(e.toString());
		} catch (Exception e) {
			Errors.addError("GenericTable:getAllRecordsDisconnected:Exception:"
					+ e.toString());
		}
		return resultset;
	}

	public int returnCurrentSequenceValue() throws SQLException, Exception {

		int newId = 0;
		try {
			String query = "select currval('" + sSequence + "')";

			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				newId = rs.getInt(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			Errors.addError("GenericTable:returnCurrentSequenceValue:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			Errors.addError("GenericTable:returnCurrentSequenceValue:Exception:"
					+ e.toString());
		}
		return newId;
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

	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception {
		st = conn.createStatement();
		String query = "update " + sTable + " set active_yn = '" + psStatus
				+ "' where " + sPrimaryKey + " = " + iPrimaryKey;
		st.executeUpdate(query);
		st.close();
		st = null;
	}

	public void createCustomWhere(String psCustomWhere) {
		this.sCustomWhere += psCustomWhere;
	}

	public void deleteRecord() throws SQLException, Exception {
		try {
			st = conn.createStatement();
			String query = "delete from " + sTable + " where " + sPrimaryKey
					+ " = " + iPrimaryKey;
			st.executeUpdate(query);
			st.close();
			st = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteRecords() throws SQLException, Exception {
		try {
			st = conn.createStatement();
			String query = "delete from " + sTable + " " + sCustomWhere;
			st.executeUpdate(query);
			st.close();
			st = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateRecord() throws SQLException, Exception {

		try {
			String query = "update " + this.updateWhat + " set ";

			for (int i = 0; i < updateFieldNamesVector.size(); i++) {
				query += updateFieldNamesVector.elementAt(i) + " = ?, ";
			}
			query = query.substring(0, (query.length() - 2));
			query += this.sCustomWhere;

			pst = conn.prepareStatement(query);
			// System.out.println(query);
			int j = 1;
			for (int i = 0; i < updateFieldNamesVector.size(); i++) {
				String theType = updateFieldTypesVector.elementAt(i).toString();
				if (theType.equalsIgnoreCase("string")) {
					try {
						pst.setString(j, updateFieldValuesVector.elementAt(i)
								.toString());
					} catch (Exception e) {
						pst.setString(j, "");
					}
				} else if (theType.equalsIgnoreCase("int")) {
					try {
						pst.setInt(j, Integer.parseInt(updateFieldValuesVector
								.elementAt(i).toString()));
					} catch (Exception e) {
						pst.setInt(j, 0);
					}
				} else if (theType.equalsIgnoreCase("float")) {
					float f = 0.00f;
					try {
						f = Float.valueOf(
								updateFieldValuesVector.elementAt(i).toString()
										.trim()).floatValue();
					} catch (NumberFormatException e) {
						f = 0.00f;
					}
					pst.setFloat(j, f);
				} else if (theType.equalsIgnoreCase("date")) {
					String inDate = updateFieldValuesVector.elementAt(i)
							.toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date dt = sdf.parse(inDate);
					pst.setDate(j, new java.sql.Date(dt.getTime()));
				} else if (theType.equalsIgnoreCase("timestamp")) {
					SimpleDateFormat FORMAT = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					String inTimeStamp = updateFieldValuesVector.elementAt(i)
							.toString();
					java.sql.Timestamp dateEntered = new java.sql.Timestamp(
							FORMAT.parse(inTimeStamp).getTime());
					pst.setTimestamp(j, dateEntered);
				}
				j++;
			}

			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("GenericTable:updateRecord:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("GenericTable:updateRecord:Exception:"
					+ e.toString());
		}
	}

	public void insertRecord() throws SQLException, Exception {

		try {
			String query = "insert into " + this.sTable + " ( ";

			for (int i = 0; i < updateFieldNamesVector.size(); i++) {
				query += updateFieldNamesVector.elementAt(i) + ", ";
			}

			query = query.substring(0, (query.length() - 2));
			query += ") values (";

			for (int i = 0; i < updateFieldNamesVector.size(); i++) {
				query += "?,";
			}
			query = query.substring(0, (query.length() - 1));
			query += ")";
			// System.out.println("query for insert: " + query);
			pst = conn.prepareStatement(query);
			int j = 1;
			for (int i = 0; i < updateFieldNamesVector.size(); i++) {
				String theType = updateFieldTypesVector.elementAt(i).toString();
				// System.out.println("trying type of " +
				// updateFieldTypesVector.elementAt(i));
				// System.out.println("with name of " +
				// updateFieldNamesVector.elementAt(i));
				// System.out.println("and value of " +
				// updateFieldValuesVector.elementAt(i));
				if (theType.equalsIgnoreCase("string")) {
					pst.setString(j, updateFieldValuesVector.elementAt(i)
							.toString());
				} else if (theType.equalsIgnoreCase("int")) {
					pst.setInt(
							j,
							Integer.parseInt(updateFieldValuesVector.elementAt(
									i).toString()));
				} else if (theType.equalsIgnoreCase("float")) {
					float f = 0.00f;
					try {
						f = Float.valueOf(
								updateFieldValuesVector.elementAt(i).toString()
										.trim()).floatValue();
					} catch (NumberFormatException e) {
						f = 0.00f;
					}
					pst.setFloat(j, f);
				} else if (theType.equalsIgnoreCase("date")) {
					String inDate = updateFieldValuesVector.elementAt(i)
							.toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date dt = sdf.parse(inDate);
					pst.setDate(j, new java.sql.Date(dt.getTime()));
				} else if (theType.equalsIgnoreCase("timestamp")) {
					SimpleDateFormat FORMAT = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					String inTimeStamp = updateFieldValuesVector.elementAt(i)
							.toString();
					java.sql.Timestamp dateEntered = new java.sql.Timestamp(
							FORMAT.parse(inTimeStamp).getTime());
					pst.setTimestamp(j, dateEntered);
				}
				j++;
			}
			pst.executeUpdate();
			pst.close();
			pst = null;
		} catch (SQLException e) {
			e.printStackTrace();
			Errors.addError("GenericTable:insertRecord:SQLException:"
					+ e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("GenericTable:insertRecord:Exception:"
					+ e.toString());
		}
	}

	public void clearUpdateVectors() {
		this.updateFieldNamesVector.clear();
		this.updateFieldTypesVector.clear();
		this.updateFieldValuesVector.clear();
	}

	public void setPrimaryKey(String theKey) {
		this.setIPrimaryKey(Integer.parseInt(theKey));
	}

	public String getSCustomWhere() {
		return sCustomWhere;
	}

	public void setSCustomWhere(String customWhere) {
		sCustomWhere = " where true " + customWhere;
	}

	public int getIPrimaryKey() {
		return iPrimaryKey;
	}

	public void setIPrimaryKey(int primaryKey) {
		iPrimaryKey = primaryKey;
	}

	public String getSPrimaryKey() {
		return sPrimaryKey;
	}

	public void setSPrimaryKey(String primaryKey) {
		sPrimaryKey = primaryKey;
	}

	public String getSTable() {
		return sTable;
	}

	public void setSTable(String table) {
		sTable = table;
	}

	public String getSCustomFrom() {
		return sCustomFrom;
	}

	public void setSCustomFrom(String customFrom) {
		sCustomFrom = customFrom;
	}

	public String getSCustomOrder() {
		return sCustomOrder;
	}

	public void setSCustomOrder(String customOrder) {
		sCustomOrder = customOrder;
	}

	public String getSSelectWhat() {
		return sSelectWhat;
	}

	public void setSSelectWhat(String selectWhat) {
		sSelectWhat = selectWhat;
	}

	public void addUpdateFieldNameValuePair(String sField, String sValue,
			String sType) {
		this.updateFieldNamesVector.add(sField);
		this.updateFieldValuesVector.add(sValue);
		this.updateFieldTypesVector.add(sType);
	}

	public void addSet(String sField, String sValue, String sType) {
		this.addUpdateFieldNameValuePair(sField, sValue, sType);
	}

	public String getUpdateWhat() {
		return updateWhat;
	}

	public void setUpdateWhat(String updateWhat) {
		this.updateWhat = updateWhat;
	}

	public String getInsertWhat() {
		return insertWhat;
	}

	public void setInsertWhat(String insertWhat) {
		this.insertWhat = insertWhat;
	}

	public String getSSequence() {
		return sSequence;
	}

	public void setSSequence(String sequence) {
		sSequence = sequence;
	}

	public String getSCustomLimit() {
		return sCustomLimit;
	}

	public void setSCustomLimit(String customLimit) {
		sCustomLimit = customLimit;
	}
}