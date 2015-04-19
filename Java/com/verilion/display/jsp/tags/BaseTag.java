//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-06
//Revisions
//------------------------------------------------------------------------------
//$Log: BaseTag.java,v $
//Revision 1.1.2.6.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.6  2005/03/08 16:38:04  tcs
//Modified to extend tagsupport (we'll do a separate one for body tag support, if needed)
//
//Revision 1.1.2.5  2005/03/08 12:30:11  tcs
//Changed to extend BodyTagSupport
//
//Revision 1.1.2.4  2005/03/06 10:42:36  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.sql.Connection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Base java tag
 * 
 * @author tsawler
 * 
 */
public class BaseTag extends TagSupport {

   private static final long serialVersionUID = 1559699228535153910L;
   public PageContext pc = null;
   public Tag parent = null;
   public Connection conn = null;

   public void setPageContext(PageContext p) {
      pc = p;
   }

   public void setParent(Tag t) {
      parent = t;
   }

   public Tag getParent() {
      return parent;
   }

   public int doStartTag() throws JspException {
      try {

      } catch (Exception e) {
         throw new JspTagException("An IOException occurred.");
      }
      return SKIP_BODY;
   }

   public int doEndTag() throws JspException {
      return EVAL_PAGE;
   }

   public void release() {
      pc = null;
      parent = null;
   }

   /**
    * @return Returns the conn.
    */
   public Connection getConn() {
      return conn;
   }

   /**
    * @param conn
    *           The conn to set.
    */
   public void setConn(Connection conn) {
      this.conn = conn;
   }

}