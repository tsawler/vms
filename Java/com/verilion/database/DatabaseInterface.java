//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-26
//Revisions
//------------------------------------------------------------------------------
//$Log: DatabaseInterface.java,v $
//Revision 1.5.2.2.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.5.2.2  2004/12/17 18:13:19  tcs
//Added methods for custom query, limit, and offest
//
//Revision 1.5.2.1  2004/12/03 13:48:44  tcs
//Made visible
//
//Revision 1.5  2004/11/02 17:46:03  tcs
//Changed mutator for setPrimaryKey to take String parameter
//
//Revision 1.4  2004/10/26 17:56:27  tcs
//Added throws declaration to deleteRecord
//
//Revision 1.3  2004/10/26 17:06:54  tcs
//Improved javadocs
//
//Revision 1.2  2004/10/26 16:59:48  tcs
//Formatting
//
//Revision 1.1  2004/10/26 16:56:55  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.SQLException;

/**
 * Define methods required for all database objects implementing this interface.
 * 
 * The methods below are used by
 * {@link com.verilion.display.html.process.admin.ProcessPerformAction ProcessPerformAction}.
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