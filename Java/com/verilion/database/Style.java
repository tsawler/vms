//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: Style.java,v $
//Revision 1.1  2004/05/23 04:52:46  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.verilion.object.Errors;

/**
 * Manipulates style table in db, stylesheets
 * <P>
 * Dec 11, 2003
 * <P>
 * package com.verilion.database
 * <P>
 * @author tsawler
 *  
 */
public class Style {

	private int style_id = 0;
	private String style_name = "";
	private String font_family = "";
	private int font_size = 0;
	private String font_style = "";
	private int line_height = 0;
	private String font_weight = "";
	private String font_variant = "";
	private String text_decoration = "";
	private ResultSet rs = null;
	private Connection conn;
	private Statement st = null;
	private PreparedStatement pst = null;

	public Style() {
		super();
	}

	/**
	 * Update method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateStyle() throws SQLException, Exception {
		try {
			String Update =
				"UPDATE style SET "
					+ "style_name = '"
					+ style_name
					+ "', "
					+ "font_family ='"
					+ font_family
					+ ", "
					+ "font_size = '"
					+ font_size
					+ "', "
					+ "font_style = '"
					+ font_style
					+ "', "
					+ "line_height = '"
					+ line_height
					+ "', "
					+ "font_weight = '"
					+ font_weight
					+ ", "
					+ "font_variant = '"
					+ font_variant
					+ ", "
					+ "text_decoartion = '"
					+ text_decoration
					+ "' "
					+ "WHERE style_id = '"
					+ style_id
					+ "'";

			pst = conn.prepareStatement(Update);
			pst.executeUpdate();
		}
		catch (SQLException e) {
			Errors.addError(
				"Style:updateStyle:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"Style:updateStyle:Exception:" + e.toString());
		}
	}

	/**
	 * Insert method.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addStyle() throws SQLException, Exception {
		try {
			String save =
				"INSERT INTO style ("
					+ "style_name, "
					+ "font_family, "
					+ "font_size, "
					+ "font_style, "
					+ "line_height, "
					+ "font_weight, "
					+ "font_variant, "
					+ "text_decoration) "
					+ "VALUES("
					+ "'"
					+ style_name
					+ ", "
					+ "'"
					+ font_family
					+ ", "
					+ "'"
					+ font_size
					+ ", "
					+ "'"
					+ font_style
					+ ", "
					+ "'"
					+ line_height
					+ "', "
					+ "'"
					+ font_weight
					+ "', "
					+ "'"
					+ font_variant
					+ ", "
					+ "'"
					+ text_decoration
					+ "')";
			pst = conn.prepareStatement(save);
			pst.executeUpdate();
		}
		catch (SQLException e) {
			Errors.addError("Style:addStyle:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError("Style:addStyle:Exception:" + e.toString());
		}
	}

	/**
	 * Gets all style names/ids
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public ResultSet getStyleNamesIds() throws SQLException, Exception {
		try {
			String query =
				"select style_id, style_name from style order by style_name";

			st = conn.createStatement();
			rs = st.executeQuery(query);
		}
		catch (SQLException e) {
			Errors.addError(
				"Style:getStyleNamesIds:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"Style:getStyleNamesIds:Exception:" + e.toString());
		}
		return rs;
	}

	/**
	 * Gets style by id
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public ResultSet getStyleById() throws SQLException, Exception {
		try {
			String query =
				"select * from style where style_id = '"
					+ style_id
					+ "'";

			st = conn.createStatement();
			rs = st.executeQuery(query);
		}
		catch (SQLException e) {
			Errors.addError(
				"Style:getStyleById:SQLException:" + e.toString());
		}
		catch (Exception e) {
			Errors.addError(
				"Style:getStyleById:Exception:" + e.toString());
		}
		return rs;
	}

	/**
	 * @return Returns the conn.
	 */
	public Connection getConn() {
		return conn;
	}
	/**
	 * @param conn The conn to set.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	/**
	 * @return Returns the font_family.
	 */
	public String getFont_family() {
		return font_family;
	}
	/**
	 * @param font_family The font_family to set.
	 */
	public void setFont_family(String font_family) {
		this.font_family = font_family;
	}
	/**
	 * @return Returns the font_size.
	 */
	public int getFont_size() {
		return font_size;
	}
	/**
	 * @param font_size The font_size to set.
	 */
	public void setFont_size(int font_size) {
		this.font_size = font_size;
	}
	/**
	 * @return Returns the font_style.
	 */
	public String getFont_style() {
		return font_style;
	}
	/**
	 * @param font_style The font_style to set.
	 */
	public void setFont_style(String font_style) {
		this.font_style = font_style;
	}
	/**
	 * @return Returns the font_variant.
	 */
	public String getFont_variant() {
		return font_variant;
	}
	/**
	 * @param font_variant The font_variant to set.
	 */
	public void setFont_variant(String font_variant) {
		this.font_variant = font_variant;
	}
	/**
	 * @return Returns the font_weight.
	 */
	public String getFont_weight() {
		return font_weight;
	}
	/**
	 * @param font_weight The font_weight to set.
	 */
	public void setFont_weight(String font_weight) {
		this.font_weight = font_weight;
	}
	/**
	 * @return Returns the line_height.
	 */
	public int getLine_height() {
		return line_height;
	}
	/**
	 * @param line_height The line_height to set.
	 */
	public void setLine_height(int line_height) {
		this.line_height = line_height;
	}
	/**
	 * @return Returns the style_id.
	 */
	public int getStyle_id() {
		return style_id;
	}
	/**
	 * @param style_id The style_id to set.
	 */
	public void setStyle_id(int style_id) {
		this.style_id = style_id;
	}
	/**
	 * @return Returns the style_name.
	 */
	public String getStyle_name() {
		return style_name;
	}
	/**
	 * @param style_name The style_name to set.
	 */
	public void setStyle_name(String style_name) {
		this.style_name = style_name;
	}
	/**
	 * @return Returns the text_decoration.
	 */
	public String getText_decoration() {
		return text_decoration;
	}
	/**
	 * @param text_decoration The text_decoration to set.
	 */
	public void setText_decoration(String text_decoration) {
		this.text_decoration = text_decoration;
	}
}