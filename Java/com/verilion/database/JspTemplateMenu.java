package com.verilion.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

/**
 * Database routines for faq related activities.
 * 
 * @author tsawler
 * 
 */
public class JspTemplateMenu {

	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	public XDisconnectedRowSet getMenusForTemplateName(String inTemplateName)
			throws Exception {

		XDisconnectedRowSet drs = new XDisconnectedRowSet();

		StringBuffer queryBuf = new StringBuffer(
				"select jm.menu_id, m.ct_menu_type_id from jsp_template_menu jm ");
		queryBuf.append("left join menu m on (jm.menu_id = m.menu_id) ");
		queryBuf.append("where jsp_template_id = (select jsp_template_id from jsp_templates where jsp_template_name = ?)");
		pst = conn.prepareStatement(queryBuf.toString());
		pst.setString(1, inTemplateName);

		rs = pst.executeQuery();
		drs.populate(rs);
		rs.close();
		rs = null;
		pst.close();
		pst = null;

		return drs;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}