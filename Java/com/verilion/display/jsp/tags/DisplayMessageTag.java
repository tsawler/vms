//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-04
//Revisions
//------------------------------------------------------------------------------
//$Log: DisplayMessageTag.java,v $
//Revision 1.1.2.4.4.1.2.1.4.1.2.1  2007/02/02 13:54:23  tcs
//Removed debugging info
//
//Revision 1.1.2.4.4.1.2.1.4.1  2006/06/11 22:38:30  tcs
//In progress
//
//Revision 1.1.2.4.4.1.2.1  2005/08/22 14:23:44  tcs
//Changing the way messages are removed from session.
//
//Revision 1.1.2.4.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.4  2005/04/14 18:22:36  tcs
//Modified to return nothing if no message displayed.
//
//Revision 1.1.2.3  2005/03/06 10:54:50  tcs
//Fixed  minor typos
//
//Revision 1.1.2.2  2005/03/06 10:43:19  tcs
//Chanaged to derive from superclass
//
//Revision 1.1.2.1  2005/03/04 14:41:08  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * TagLib to display message in session on page.
 * 
 * @author tsawler
 *  
 */
public class DisplayMessageTag extends BaseTag {

   private static final long serialVersionUID = 4439755924834265887L;

   public int doStartTag() throws JspException {
      HashMap hm = (HashMap) pc.getSession().getAttribute("pHm");
      try {
         if (hm.get("PRESERVEMSG") == null) {
            if (pc.getSession().getAttribute("Error") != null) {
               String theMessage = pc.getSession().getAttribute("Error")
                     .toString();
               pc.getOut().write(
                     "<div class=\"caution\" style=\"text-align: center\">"
                           + theMessage
                           + "</div><br />");
               pc.getSession().removeAttribute("Error");
            } else if (pc.getRequest().getParameter("msg") != null) {
               pc.getOut().write(
                     "<div class=\"caution\">"
                           + pc.getRequest().getParameter("msg")
                           + "</div><br />");
            }
         }
      } catch (Exception e) {
         throw new JspTagException("An IOException occurred.");
      }
      return SKIP_BODY;
   }
}