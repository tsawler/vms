//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-03
//Revisions
//------------------------------------------------------------------------------
//$Log: ModuleTag.java,v $
//Revision 1.1.2.5.4.1.10.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//Revision 1.1.2.5.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.5  2005/03/06 10:54:50  tcs
//Fixed  minor typos
//
//Revision 1.1.2.4  2005/03/06 10:43:19  tcs
//Chanaged to derive from superclass
//
//Revision 1.1.2.3  2005/03/04 14:43:24  tcs
//Formatting
//
//Revision 1.1.2.2  2005/03/04 14:24:37  tcs
//Modified to get session from page context instead of parameters
//
//Revision 1.1.2.1  2005/03/03 16:53:10  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.verilion.object.html.modules.ModuleObject;

/**
 * TagLib to display Modules.
 * 
 * @author tsawler
 *  
 */
public class ModuleTag extends BaseTag {
   
   private static final long serialVersionUID = -4965726296134383924L;
   private int position = 0;

   public int doStartTag() throws JspException {
      try {
         String theModules = ModuleObject.makeModuleHtml(conn, position, pc
               .getSession(), (HttpServletRequest) pc.getRequest());
         pc.getOut().write(theModules);
      } catch (Exception e) {
         throw new JspTagException("An IOException occurred.");
      }
      return SKIP_BODY;
   }

  
   /**
    * @return Returns the position.
    */
   public int getPosition() {
      return position;
   }
   
   /**
    * @param position The position to set.
    */
   public void setPosition(int position) {
      this.position = position;
   }
}