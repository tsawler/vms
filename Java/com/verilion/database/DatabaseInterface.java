package com.verilion.database;

import java.sql.SQLException;

/**
 * Define methods required for all database objects implementing this interface.
 * 
 * The methods below are used by
 * {@link com.verilion.display.html.process.admin.ProcessPerformAction
 * ProcessPerformAction}.
 * 
 * @author tsawler
 * @since 1.2.09
 * 
 */
public interface DatabaseInterface {

	public void setPrimaryKey(String theKey);

	public void deleteRecord() throws SQLException, Exception;

	public void changeActiveStatus(String psStatus) throws SQLException,
			Exception;

	public void createCustomWhere(String psCustomWhere);

}