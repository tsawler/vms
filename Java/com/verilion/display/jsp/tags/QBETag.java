//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-30
//Revisions
//------------------------------------------------------------------------------
//$Log: QBETag.java,v $
//Revision 1.1.2.1.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.1  2005/03/30 20:38:37  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

/**
 * Displays form to perform query against some table(s) or views in the
 * database.
 * 
 * @author tsawler
 *  
 */
public class QBETag extends BaseTag {

   private static final long serialVersionUID = -7992368010530122264L;
   String legend = "Search";
   String formname = "searchform";
   String action = "";
   String method = "post";
   String tablestyle = "tabledata";
   String inputclass = "inputbox";
   String searchterms = "";
   String searchdetail = "";

   boolean bHasPostedData = false;
   boolean bIsDateField = false;

   StringTokenizer st = null;
   StringTokenizer st2 = null;
   StringTokenizer st3 = null;

   public void setPageContext(PageContext p) {
      pc = p;
   }

   public int doStartTag() throws JspException {
      try {
         HttpServletRequest request = (HttpServletRequest) pc.getRequest();
         Enumeration enumeration = request.getParameterNames();
         if (enumeration.hasMoreElements()) {
            bHasPostedData = true;
         }
         pc.getOut().print(
               "<div align=\"center\"><fieldset><legend>"
                     + legend
                     + "</legend>");
         pc.getOut().print(
               "<form name=\""
                     + formname
                     + "\" action=\""
                     + action
                     + "\" method=\""
                     + method
                     + "\">");
         pc.getOut().print("<table class=\"" + tablestyle + "\">");

         st = new StringTokenizer(searchterms, ",");
         st2 = new StringTokenizer(searchdetail, ",");

         int i = 0;
         i = st.countTokens();

         pc.getOut().print("<tr>");

         for (int j = 1; j <= i; j++) {
            pc.getOut().print("<td>");
            StringTokenizer mySt = new StringTokenizer(st.nextToken(), "|");
            String sLabel = mySt.nextToken();
            String sField = mySt.nextToken();
            if (sField.indexOf("date") > 0) {
               sLabel += "&nbsp;<a href=\"#\" onClick=\"cal1xx.select(document.forms[0]."
                     + sField
                     + ",'anchor1xx','yyyy-MM-dd'); return false;\""
                     + "title=\"cal1xx.select(document.forms[0]."
                     + sField
                     + ",'anchor1xx','yyyy-MM-dd'); return false;\""
                     + "name=\"anchor1xx\" id=\"anchor1xx\"><img src=\"/jscript/cal.png\""
                     + "border=\"0\" height=\"14\" width=\"14\"></a>";
            }
            pc.getOut().print(sLabel + "</td>");
         }

         pc.getOut().print("<td>&nbsp;</td></tr><tr>");

         for (int j = 1; j <= i; j++) {
            pc.getOut().print("<td>");
            searchdetail = st2.nextToken();
            st3 = new StringTokenizer(searchdetail, "|");
            String sType = st3.nextToken();
            String sName = st3.nextToken();
            String sInitialValue = st3.nextToken();
            if (bHasPostedData) {
               if ((sType.equals("text"))
                     || (sType.equals("password"))
                     || (sType.equals("yn"))) {
                  if (request.getParameter(sName) != null) {
                     if (!request.getParameter(sName).equals(" ")) {
                        sInitialValue = request.getParameter(sName);
                     } else {
                        sInitialValue = "";
                     }
                  }
               }
            }
            if (sType.equalsIgnoreCase("text")) {
               pc.getOut().print(
                     "<input type=\"text\" class=\""
                           + inputclass
                           + "\" name=\""
                           + sName
                           + "\" value=\""
                           + sInitialValue
                           + "\" />");
            } else if (sType.equalsIgnoreCase("password")) {
               pc.getOut().print(
                     "<input type=\"password\" class=\""
                           + inputclass
                           + "\" name=\""
                           + sName
                           + "\" value=\""
                           + sInitialValue
                           + "\" />");
            } else if (sType.equalsIgnoreCase("yn")) {
               pc.getOut().print(
                     "<select class=\""
                           + inputclass
                           + "\" name=\""
                           + sName
                           + "\"");
               if (sInitialValue.equalsIgnoreCase("y")) {
                  pc.getOut().print(" selected ");
               }
               pc.getOut().print("><option value=\"y\">" + "Yes" + "</option>");
               pc.getOut().print("<option value=\"n\"");
               if (sInitialValue.equalsIgnoreCase("n")) {
                  pc.getOut().print(" selected ");
               }
               pc.getOut().print(">No</option></select>");
            }

            pc.getOut().print("</td>");
         }

         pc
               .getOut()
               .print(
                     "<td><input class=\"inputbox\" type=\"submit\" value=\"Go\" />");
         
         pc.getOut().print("<input type=\"hidden\" name=\"saction\" value=\"" + request.getParameter("saction") + "\" />");
         pc.getOut().print("<input type=\"hidden\" name=\"legend\" value=\"" + request.getParameter("legend") + "\" />");
         pc.getOut().print("<input type=\"hidden\" name=\"searchterms\" value=\"" + request.getParameter("searchterms") + "\" />");
         pc.getOut().print("<input type=\"hidden\" name=\"searchdetail\" value=\"" + request.getParameter("searchdetail") + "\" />");
         pc.getOut().print("<input type=\"hidden\" name=\"method\" value=\"" + request.getParameter("method") + "\" />");
         pc.getOut().print("<input type=\"hidden\" name=\"formname\" value=\"" + request.getParameter("formname") + "\" />");
         pc.getOut().print("</td></tr></table></form>");
         pc.getOut().print("</fieldset></div>");

      } catch (Exception e) {
         e.printStackTrace();
         throw new JspTagException("An IOException occurred.");
      }
      return SKIP_BODY;
   }

   /**
    * @return Returns the action.
    */
   public String getAction() {
      return action;
   }

   /**
    * @param action
    *           The action to set.
    */
   public void setAction(String action) {
      this.action = action;
   }

   /**
    * @return Returns the formname.
    */
   public String getFormname() {
      return formname;
   }

   /**
    * @param formname
    *           The formname to set.
    */
   public void setFormname(String formname) {
      this.formname = formname;
   }

   /**
    * @return Returns the inputclass.
    */
   public String getInputclass() {
      return inputclass;
   }

   /**
    * @param inputclass
    *           The inputclass to set.
    */
   public void setInputclass(String inputclass) {
      this.inputclass = inputclass;
   }

   /**
    * @return Returns the legend.
    */
   public String getLegend() {
      return legend;
   }

   /**
    * @param legend
    *           The legend to set.
    */
   public void setLegend(String legend) {
      this.legend = legend;
   }

   /**
    * @return Returns the method.
    */
   public String getMethod() {
      return method;
   }

   /**
    * @param method
    *           The method to set.
    */
   public void setMethod(String method) {
      this.method = method;
   }

   /**
    * @return Returns the tablestyle.
    */
   public String getTablestyle() {
      return tablestyle;
   }

   /**
    * @param tablestyle
    *           The tablestyle to set.
    */
   public void setTablestyle(String tablestyle) {
      this.tablestyle = tablestyle;
   }

   /**
    * @return Returns the searchdetail.
    */
   public String getSearchdetail() {
      return searchdetail;
   }

   /**
    * @param searchdetail
    *           The searchdetail to set.
    */
   public void setSearchdetail(String searchdetail) {
      this.searchdetail = searchdetail;
   }

   /**
    * @return Returns the searchterms.
    */
   public String getSearchterms() {
      return searchterms;
   }

   /**
    * @param searchterms
    *           The searchterms to set.
    */
   public void setSearchterms(String searchterms) {
      this.searchterms = searchterms;
   }

}