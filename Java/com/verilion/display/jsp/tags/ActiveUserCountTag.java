//------------------------------------------------------------------------------
//Copyright (c) 2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-23
//Revisions
//------------------------------------------------------------------------------
//$Log: ActiveUserCountTag.java,v $
//Revision 1.1.2.1  2007/02/06 18:24:04  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * TagLib to display banner
 * 
 * @author tsawler
 * 
 */
public class ActiveUserCountTag extends BaseTag {

   private static final long serialVersionUID = 1L;
   private String theHTML = "";
   Statement st = null;
   ResultSet rs = null;
   String query = "";
   int sessionCount = 0;

   public int doStartTag() throws JspException {
      try {
         st = conn.createStatement();
         query = "select sessions from sessions";
         rs = st.executeQuery(query);
         while (rs.next()) {
            sessionCount = rs.getInt(1);
         }
         if (sessionCount < 1)
            sessionCount = 1;
         rs.close();
         rs = null;
         st.close();
         st = null;
         theHTML = sessionCount + " active users";

         pc.getOut().write(theHTML);
      } catch (Exception e) {
         e.printStackTrace();
         throw new JspTagException("An IOException occurred.");
      }
      return SKIP_BODY;
   }

}