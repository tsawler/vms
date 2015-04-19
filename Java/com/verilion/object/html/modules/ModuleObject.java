// ------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-04
//Revisions
//------------------------------------------------------------------------------
//$Log: ModuleObject.java,v $
//Revision 1.11.2.3.4.1.6.1.2.1.2.2  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.11.2.3.4.1.6.1.2.1.2.1  2008/11/11 17:53:34  tcs
//Added support  for "order" column
//
//Revision 1.11.2.3.4.1.6.1.2.1  2007/01/29 19:42:23  tcs
//Extra sanity check for jar name
//
//Revision 1.11.2.3.4.1.6.1  2006/12/23 15:32:09  tcs
//Add parameterization for type safety
//
//Revision 1.11.2.3.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.11.2.3  2005/05/03 12:24:01  tcs
//Added support for modules with more than one class
//
//Revision 1.11.2.2  2005/05/02 17:01:58  tcs
//Completed support for uploaded jars
//
//Revision 1.11.2.1  2005/04/29 13:29:22  tcs
//Working on using JarClassLoader
//
//Revision 1.11  2004/10/22 17:18:20  tcs
//Deleted unnecessary instantiation of method object
//
//Revision 1.10  2004/07/06 16:39:07  tcs
//Corrected javadocs
//
//Revision 1.9  2004/06/30 18:33:39  tcs
//Changed to return html by module position
//
//Revision 1.8  2004/06/25 16:34:22  tcs
//Changed way conn is handled
//
//Revision 1.7  2004/06/25 02:15:37  tcs
//Closed statement
//
//Revision 1.6  2004/06/24 17:58:19  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.5  2004/06/06 21:51:26  tcs
//Added blank line between modules
//
//Revision 1.4  2004/06/06 21:06:54  tcs
//*** empty log message ***
//
//Revision 1.3  2004/06/06 01:13:50  tcs
//Changes due to refactoring
//
//Revision 1.2  2004/06/04 18:47:28  tcs
//Version 0.9
//
//Revision 1.1 2004/06/04 16:51:21 tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.lang.reflect.Method;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Modules;
import com.verilion.database.SingletonObjects;
import com.verilion.object.Errors;
import com.verilion.utility.JarClassLoader;

/**
 * Builds html for modules as seen on left/right columns of Page
 * <P>
 * Jun 4, 2004
 * <P>
 * com.verilion.object.html.modules
 * 
 * @author tsawler
 * 
 */
public class ModuleObject {

	/**
	 * Reads all active entries for modules from modules table, creates
	 * instances of the module objects (using reflection), and builds the html
	 * for active modules for a given position.
	 * 
	 * @param conn
	 * @param positionId
	 * @param session
	 * @return String
	 * @throws Exception
	 */
	public static String makeModuleHtml(Connection conn, int positionId,
			HttpSession session, HttpServletRequest request) throws Exception {

		String theModules = "";
		XDisconnectedRowSet rs = new XDisconnectedRowSet();

		try {
			Modules myModule = new Modules();
			myModule.setConn(conn);
			myModule.setModule_position_id(positionId);

			rs = myModule.getAllActiveModulesForPositionId();

			while (rs.next()) {
				Object instance = null;
				Class<?> c = null;
				try {
					if (rs.getString("jarfile") != null) {
						if (rs.getString("jarfile").length() > 3) {
							// this is an uploaded module.
							JarClassLoader jcl = new JarClassLoader(
									SingletonObjects.getInstance()
											.getSystem_path()
											+ "UserJars/", ModuleObject.class
											.getClassLoader());
							c = jcl.LoadModuleClassDataFromJar(rs
									.getString("jarfile"), rs
									.getString("module_class_name"));
							instance = c.newInstance();
						} else {
							c = Class
									.forName(rs.getString("module_class_name"));
							instance = c.newInstance();
						}
					} else {
						// this is a system module. Use reflection to load the
						// class &
						// create instance
						c = Class.forName(rs.getString("module_class_name"));
						instance = c.newInstance();
					}

					// load external method & invoke with our connection
					Method m = c.getMethod("getHtmlOutput", new Class[] {
							Connection.class, HttpSession.class,
							HttpServletRequest.class });
					Object result = (String) m.invoke(instance, new Object[] {
							conn, session, request });

					// cast the returned object to a string and append it to our
					// html
					theModules += (String) result;
					if (((String) result).length() > 0)
						theModules += "<br />";
				} catch (ClassNotFoundException e) {
					Errors.addError("Class not found exception in "
							+ "ModuleObject:makeModuleHtml for"
							+ rs.getString("module_class_name"));
				} catch (Exception e) {
					e.printStackTrace();
					Errors.addError("ModuleObject:makeModuleHtml:"
							+ e.toString());
				}

			}

			// should we pass our result through html tidy here??

			rs.close();
			rs = null;
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("ModuleObject.makeModuleHtml:Exception:"
					+ e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}

		// return the completed html to calling class
		return theModules;
	}
}