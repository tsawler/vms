//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: DaysOfMonth.java,v $
//Revision 1.2.4.1  2005/05/27 13:38:18  tcs
//Added public constructor
//
//Revision 1.2  2004/05/26 13:11:01  tcs
//Added class inputbox for ddlb
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import com.verilion.object.Errors;

/**
 * Generates a drop down list of days of month for HTML forms. Allows selection
 * of particulare day of month at the time the form is displayed.
 * 
 * <P>
 * Dec 19, 2003
 * <P>
 * 
 * @author tsawler
 */
public class DaysOfMonth {

	public DaysOfMonth() {

	}

	/**
	 * Builds drop down list of days of month, and selects the one passed as
	 * selectedValue, for inclusion on html page. Does not put form tags or
	 * submit button on menu.
	 * 
	 * @param selectName
	 *            string The name of the form to be created
	 * @param selectedValue
	 *            int the selected day of the month
	 * @return String formatted select list of days of month
	 * @throws Exception
	 */
	public static String makeDropDownListDaysOfMonth(String selectName,
			int selectedValue) throws Exception {

		String theMenu = "";

		try {
			// build our menu
			theMenu = "<select class=\"inputbox\" name=\"" + selectName + "\">";

			for (int i = 1; i < 32; i++) {
				theMenu += "<option value=\"";
				theMenu += i;
				theMenu += "\"";
				if (i == selectedValue) {
					theMenu += " selected";
				}
				theMenu += ">";
				theMenu += i;
				theMenu += "</option>";
			}

			theMenu += "</select>";

		} catch (Exception e) {
			Errors.addError("DaysOfMonth.makeDropDownListDaysOfMonth:Exception:"
					+ e.toString());
		}
		// return the complete menu
		return theMenu;
	}
}