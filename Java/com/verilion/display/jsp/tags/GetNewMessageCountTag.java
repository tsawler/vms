//------------------------------------------------------------------------------
//Copyright (c) 2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-02-12
//Revisions
//------------------------------------------------------------------------------
//$Log: GetNewMessageCountTag.java,v $
//Revision 1.1.2.1  2007/02/12 19:30:10  tcs
//Added ability to specify style & whether or not we're in the admin site
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.verilion.database.CustomerMessages;
import com.verilion.database.DbBean;
import com.verilion.database.JspTemplate;

/**
 * TagLib to display count for unread messages
 * 
 * @author tsawler
 * 
 */
public class GetNewMessageCountTag extends BaseTag {

	private static final long serialVersionUID = 1L;
	private String theHTML = "";
	private String admin = "";
	private String style = "";
	Statement st = null;
	ResultSet rs = null;
	String query = "";
	int sessionCount = 0;
	boolean bGetOwnConn = false;
	int messageCount = 0;
	int customer_id = 0;
	private Connection c = null;

	public int doStartTag() throws JspException {
		try {
			if (conn == null) {
				c = DbBean.getDbConnection();
				bGetOwnConn = true;
			}

			try {
				customer_id = Integer.parseInt(pc.getSession()
						.getAttribute("customer_id").toString());
			} catch (Exception e) {
				System.out.println(e.toString());
			}

			CustomerMessages myMessages = new CustomerMessages();
			if (conn != null) {
				myMessages.setConn(conn);
			} else {
				myMessages.setConn(c);
			}
			myMessages.setTo_id(customer_id);
			messageCount = myMessages.getNewMessageCountForCustomerId();
			JspTemplate myJspTemplate = new JspTemplate();
			if (conn != null) {
				myJspTemplate.setConn(conn);
			} else {
				myJspTemplate.setConn(c);
			}
			String theTemplate = "";
			theTemplate = myJspTemplate
					.returnJspTemplateIdForPageName("messages");

			if (admin.length() > 0) {
				theHTML = "<div><a ";
				if (style.length() > 0) {
					theHTML += " class=\"" + style + "\" ";
				}
				theHTML += "href=\"/messages/jpage/1/p/messages/admin/1/content.do\">"
						+ messageCount + " new messages</a></div>";
			} else {
				theHTML = "<div><a ";
				if (style.length() > 0) {
					theHTML += " class=\"" + style + "\" ";
				}
				theHTML += "href=\"/" + theTemplate
						+ "/jpage/1/p/messages/content.do\">" + messageCount
						+ " new messages</a></div>";
			}

			pc.getOut().write(theHTML);

			if (bGetOwnConn) {
				DbBean.closeDbConnection(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}