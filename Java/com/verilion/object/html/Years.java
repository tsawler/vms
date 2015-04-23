//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: Years.java,v $
//Revision 1.3  2004/05/27 18:50:24  tcs
//Made XHTML 1.0 strict compliant
//
//Revision 1.2  2004/05/26 13:11:01  tcs
//Added class inputbox for ddlb
//
//Revision 1.1  2004/05/23 04:52:52  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import com.verilion.object.Errors;

/**
 * Generates a drop down list of years for inclusion in html form, and allows
 * selection of a particular year, passed as selectedValue.
 * 
 * <P>
 * Dec 19, 2003
 * <P>
 * 
 * @author tsawler
 */
public class Years {

	/**
	 * Makes a drop down list box of years from StartYear to EndYear, and with
	 * selectedValue preselected.
	 * 
	 * @param selectName
	 *            - the name of this form element
	 * @param selectedValue
	 *            - the year to preselect when the form is drawn
	 * @param StartYear
	 *            - where to start the list
	 * @param EndYear
	 *            - where to end the lst
	 * @return String - the html menu
	 * @throws Exception
	 */
	public static String makeDropDownListYears(String selectName,
			int selectedValue, int StartYear, int EndYear) throws Exception {

		String theMenu = "";

		try {
			// build our menu
			if (selectedValue == 0) {
				theMenu = "<select class=\"inputbox\" selected name=\""
						+ selectName + "\">";
			} else {
				theMenu = "<select class=\"inputbox\" name=\"" + selectName
						+ "\">";
			}
			theMenu += "<option value='0'>&nbsp;</option>";
			for (int i = StartYear; i < EndYear + 1; i++) {
				theMenu += "<option value=\"";
				theMenu += i;
				theMenu += "\"";
				if (i == selectedValue) {
					theMenu += " selected=\"selected\"";
				}
				theMenu += ">";
				theMenu += i;
				theMenu += "</option>";
			}

			theMenu += "</select>";

		} catch (Exception e) {
			Errors.addError("Years.makeDropDownListYears:Exception:"
					+ e.toString());
		}
		// return the complete menu
		return theMenu;
	}
}