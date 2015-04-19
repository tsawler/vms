//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-26
//Revisions
//------------------------------------------------------------------------------
//$Log: QueryFormTag.java,v $
//Revision 1.1.2.3.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.3  2005/03/27 16:59:20  tcs
//In progress...
//
//Revision 1.1.2.2  2005/03/27 11:03:52  tcs
//Keeps values between submits
//
//Revision 1.1.2.1  2005/03/27 10:51:25  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Displays form to perform query against some table(s) or views in the
 * database. Also handles submit of form, and result table
 * 
 * @author tsawler
 *  
 */
public class QueryFormTag extends SimpleTagSupport {

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

   public void doTag() throws JspException, IOException {

      JspWriter out = getJspContext().getOut();
      PageContext pc = (PageContext) getJspContext();
      HttpServletRequest request = (HttpServletRequest) pc.getRequest();
      Enumeration myEnum = request.getParameterNames();
      if (myEnum.hasMoreElements()) {
         bHasPostedData = true;
      }

      out.print("<div align=\"center\"><fieldset><legend>"
            + legend
            + "</legend>");
      out.print("<form name=\""
            + formname
            + "\" action=\""
            + action
            + "\" method=\""
            + method
            + "\">");
      out.print("<table class=\"" + tablestyle + "\">");

      st = new StringTokenizer(searchterms, ",");
      st2 = new StringTokenizer(searchdetail, ",");

      int i = 0;
      i = st.countTokens();

      out.print("<tr>");

      for (int j = 1; j <= i; j++) {
         out.print("<td>");
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
         out.print(sLabel + "</td>");
      }

      out.print("<td>&nbsp;</td></tr><tr>");

      for (int j = 1; j <= i; j++) {
         out.print("<td>");
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
            out.print("<input type=\"text\" class=\""
                  + inputclass
                  + "\" name=\""
                  + sName
                  + "\" value=\""
                  + sInitialValue
                  + "\" />");
         } else if (sType.equalsIgnoreCase("password")) {
            out.print("<input type=\"password\" class=\""
                  + inputclass
                  + "\" name=\""
                  + sName
                  + "\" value=\""
                  + sInitialValue
                  + "\" />");
         } else if (sType.equalsIgnoreCase("yn")) {
            out.print("<select class=\""
                  + inputclass
                  + "\" name=\""
                  + sName
                  + "\"");
            if (sInitialValue.equalsIgnoreCase("y")) {
               out.print(" selected ");
            }
            out.print("><option value=\"y\">"
                  + "Yes"
                  + "</option>");
            out.print("<option value=\"n\"");
            if (sInitialValue.equalsIgnoreCase("n")) {
               out.print(" selected ");
            }
            out.print(">No</option></select>");
         }
         
         out.print("</td>");
      }
      
      out.print("<td><input class=\"inputbox\" type=\"submit\" value=\"Go\" /></td>");
      out.print("</tr></table></form></fieldset></div>");

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

