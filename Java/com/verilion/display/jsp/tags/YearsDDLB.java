// ------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-03
//Revisions
//------------------------------------------------------------------------------
//$Log: YearsDDLB.java,v $
//Revision 1.1.2.2.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.2  2005/03/06 10:43:19  tcs
//Chanaged to derive from superclass
//
//Revision 1.1.2.1  2005/03/03 14:51:55  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.verilion.object.html.Years;

/**
 * TagLib to display ddlb of years. Allows form element name, start year, end
 * year and selected year to be specified
 * 
 * @author tsawler
 *
 */
public class YearsDDLB extends BaseTag {

	private static final long serialVersionUID = -2838775803313629647L;
	private String selectName = "year";
	private int selectedValue = 0;
	private int startYear = 0;
	private int endYear = 0;

	public int doStartTag() throws JspException {
		try {
			String theMenu = Years.makeDropDownListYears(selectName,
					selectedValue, startYear, endYear);
			pc.getOut().write(theMenu);
		} catch (Exception e) {
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	/**
	 * @return Returns the endYear.
	 */
	public int getEndYear() {
		return endYear;
	}

	/**
	 * @param endYear
	 *            The endYear to set.
	 */
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	/**
	 * @return Returns the selectedValue.
	 */
	public int getSelectedValue() {
		return selectedValue;
	}

	/**
	 * @param selectedValue
	 *            The selectedValue to set.
	 */
	public void setSelectedValue(int selectedValue) {
		this.selectedValue = selectedValue;
	}

	/**
	 * @return Returns the selectName.
	 */
	public String getSelectName() {
		return selectName;
	}

	/**
	 * @param selectName
	 *            The selectName to set.
	 */
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	/**
	 * @return Returns the startYear.
	 */
	public int getStartYear() {
		return startYear;
	}

	/**
	 * @param startYear
	 *            The startYear to set.
	 */
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
}