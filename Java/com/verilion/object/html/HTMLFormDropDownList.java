//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: HTMLFormDropDownList.java,v $
//Revision 1.7.2.1.4.1.6.1.2.1.2.2  2007/08/12 02:09:27  tcs
//Added method to allow for custom style
//
//Revision 1.7.2.1.4.1.6.1.2.1.2.1  2007/03/23 11:03:21  tcs
//Added id tag to generated html
//
//Revision 1.7.2.1.4.1.6.1.2.1  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//Revision 1.7.2.1.4.1.6.1  2006/12/26 12:14:59  tcs
//Corrected Javadocs
//
//Revision 1.7.2.1.4.1  2005/08/16 00:38:56  tcs
//Overloaded methods to allow for custom javascript
//
//Revision 1.7.2.1  2005/02/21 16:40:00  tcs
//Added method to permit ddlb form that is disabled
//
//Revision 1.7  2004/11/26 19:12:24  tcs
//Added style def to button
//
//Revision 1.6  2004/06/26 03:16:21  tcs
//Modified to use XDisconnectedRowSets
//
//Revision 1.5  2004/06/24 17:58:18  tcs
//Mods for listeners and connection pooling improvements
//
//Revision 1.4  2004/06/01 11:43:41  tcs
//Added additional XHTML stuff
//
//Revision 1.3  2004/05/26 13:11:01  tcs
//Added class inputbox for ddlb
//
//Revision 1.2  2004/05/26 06:21:47  tcs
//Made xhtml 1.0 compliant
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import java.sql.ResultSet;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Generates a drop down list for inclusion on html page
 * 
 * <P>
 * Dec 05, 2003
 * <P>
 * 
 * @author tsawler
 */
public class HTMLFormDropDownList {

   /**
    * Creates a drop down list form from a resultset and some parameters.
    * 
    * @param formName -
    *           The name that we want our form to have
    * @param selectedValue -
    *           The value that we want to be initially selected on form
    * @param theAction -
    *           The action we want our form to take on submit
    * @param rs -
    *           The resultset used to create the list
    * @param idCol -
    *           The name of the column in table where value is found
    * @param valCol -
    *           The name of the column in table that is displayed in list
    * @param buttonName -
    *           The name we want our submit button to have
    * @return String - the complete HTML form
    * @throws Exception
    */
   public static String makeDropDownList(
         String formName,
         int selectedValue,
         String theAction,
         XDisconnectedRowSet rs,
         String idCol,
         String valCol,
         String buttonName) throws Exception {

      String theMenu = "";
      

      try {
         // build our menu
         theMenu = "<form action=\"" + theAction + "\" method = \"post\">";
         theMenu += "<select class=\"inputbox \" name=\"" + formName + "\">";
         if (selectedValue > 0) {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         } else {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         }

         theMenu += "</select>";
         theMenu += "&nbsp;<input class=\"inputbox\" type=\"submit\" value = \"" + buttonName + "\">";
         theMenu += "</form>";

         rs.close();
         rs = null;
      } catch (Exception e) {
         Errors.addError("HTMLFormDropDownList.makeDropDownList:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theMenu;
   }
   
   /**
    * Same makeDropDownList, but allows for additional javascript
    * 
    * @param formName
    * @param selectedValue
    * @param theAction
    * @param rs
    * @param idCol
    * @param valCol
    * @param buttonName
    * @param javascript
    * @return fully formatted html form with ddlb
    * @throws Exception
    */
   public static String makeDropDownList(
         String formName,
         int selectedValue,
         String theAction,
         XDisconnectedRowSet rs,
         String idCol,
         String valCol,
         String buttonName,
         String javascript) throws Exception {

      String theMenu = "";
      

      try {
         // build our menu
         theMenu = "<form action=\"" + theAction + "\" method = \"post\">";
         theMenu += "<select " + javascript + " class=\"inputbox \" name=\"" + formName + "\">";
         if (selectedValue > 0) {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         } else {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         }

         theMenu += "</select>";
         theMenu += "&nbsp;<input class=\"inputbox\" type=\"submit\" value = \"" + buttonName + "\">";
         theMenu += "</form>";

         rs.close();
         rs = null;
      } catch (Exception e) {
         Errors.addError("HTMLFormDropDownList.makeDropDownList:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theMenu;
   }
   
   public static String makeDropDownList(
         String formName,
         int selectedValue,
         String theAction,
         XDisconnectedRowSet rs,
         String idCol,
         String valCol,
         String buttonName,
         String javascript,
         String formname,
         String elementid) throws Exception {

      String theMenu = "";
      

      try {
         // build our menu
         theMenu = "<form action=\"" + theAction + "\" name=\"" + formname + "\" method = \"post\">\n";
         theMenu += "<select id=\"" + elementid + "\" " + javascript + " class=\"inputbox \" name=\"" + formName + "\">\n";
         if (selectedValue > 0) {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>\n";
            }
         } else {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>\n";
            }
         }

         theMenu += "</select>\n";
         theMenu += "&nbsp;<input class=\"inputbox\" type=\"submit\" value = \"" + buttonName + "\">\n";
         theMenu += "</form>\n";

         rs.close();
         rs = null;
      } catch (Exception e) {
         Errors.addError("HTMLFormDropDownList.makeDropDownList:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theMenu;
   }
   
   /**
    * Creates a drop down list form from a resultset and some parameters.
    * 
    * @param formName -
    *           The name that we want our form to have
    * @param selectedValue -
    *           The value that we want to be initially selected on form
    * @param theAction -
    *           The action we want our form to take on submit
    * @param rs -
    *           The resultset used to create the list
    * @param idCol -
    *           The name of the column in table where value is found
    * @param valCol -
    *           The name of the column in table that is displayed in list
    * @param buttonName -
    *           The name we want our submit button to have
    * @param isDisabled - true to disable the buttons, false to leave active
    * @return String - the complete HTML form
    * @throws Exception
    */
   public static String makeDropDownList(
         String formName,
         int selectedValue,
         String theAction,
         XDisconnectedRowSet rs,
         String idCol,
         String valCol,
         String buttonName,
         boolean isDisabled) throws Exception {

      String theMenu = "";

      try {
         // build our menu
         theMenu = "<form action=\"" + theAction + "\" method = \"post\">";
         if (isDisabled) {
            theMenu += "<select class=\"inputbox \" name=\"" + formName + "\">";
         } else {
            theMenu += "<select disabled class=\"inputbox \" name=\"" + formName + "\">";
         }
         if (selectedValue > 0) {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         } else {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         }

         theMenu += "</select>";
         if (isDisabled) {
            theMenu += "&nbsp;<input disabled class=\"inputbox\" type=\"submit\" value = \"" + buttonName + "\">";
         } else {
            theMenu += "&nbsp;<input class=\"inputbox\" type=\"submit\" value = \"" + buttonName + "\">";
         }
         theMenu += "</form>";

         rs.close();
         rs = null;
      } catch (Exception e) {
         Errors.addError("HTMLFormDropDownList.makeDropDownList:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theMenu;
   }

   /**
    * Creates a drop down list form from a resultset and some parameters. Does
    * not add form tag, and does not add button. Will set an initial value in
    * form to whatever firstVal is set to (with description of firstValDesc)
    * provided firstvaldesc != "".
    * 
    * @param selectName -
    *           The name that we want our ddlb to have
    * @param selectedValue -
    *           The value that we want to be initially selected on list
    * @param rs -
    *           The resultset used to create the list
    * @param idCol -
    *           The name of the column in table where value is found
    * @param valCol -
    *           The name of the column in table that is displayed in list
    * @param firstVal -
    *           the value of the first item in the list
    * @param firstValDesc -
    *           the text value for first value in the list
    * @return String - the complete HTML form
    * @throws Exception
    */
   public static String makeDropDownListSnippet(
         String selectName,
         int selectedValue,
         ResultSet rs,
         String idCol,
         String valCol,
         int firstVal,
         String firstValDesc) throws Exception {

      String theMenu = "";

      try {
         // build our menu
         theMenu = "<select class=\"inputbox\" name=\"" + selectName + "\" id=\"" + selectName + "\">";
         if (firstValDesc != "") {
            theMenu += "<option value=\"" + firstVal + "\">";
            theMenu += firstValDesc;
            theMenu += "</option>";
         }
         if (selectedValue > 0) {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         } else {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";

            }
         }
         theMenu += "</select>";

         rs.close();
         rs = null;
      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("HTMLFormDropDownList.makeDropDownListSnippet:Exception:"
                     + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theMenu;
   }
   
   /**
    * Creates a drop down list form from a resultset and some parameters. Does
    * not add form tag, and does not add button. Will set an initial value in
    * form to whatever firstVal is set to (with description of firstValDesc)
    * provided firstvaldesc != "".
    * 
    * @param selectName -
    *           The name that we want our ddlb to have
    * @param selectedValue -
    *           The value that we want to be initially selected on list
    * @param rs -
    *           The resultset used to create the list
    * @param idCol -
    *           The name of the column in table where value is found
    * @param valCol -
    *           The name of the column in table that is displayed in list
    * @param firstVal -
    *           the value of the first item in the list
    * @param firstValDesc -
    *           the text value for first value in the list
    * @param javascript - 
    * 			allows for custom javascript to be called
    * @return String - the complete HTML form
    * @throws Exception
    */
   public static String makeDropDownListSnippet(
         String selectName,
         int selectedValue,
         ResultSet rs,
         String idCol,
         String valCol,
         int firstVal,
         String firstValDesc,
         String javascript) throws Exception {

      String theMenu = "";

      try {
         // build our menu
         theMenu = "<select class=\"inputbox\" " + javascript + " name=\"" + selectName + "\">";
         if (firstValDesc != "") {
            theMenu += "<option value=\"" + firstVal + "\">";
            theMenu += firstValDesc;
            theMenu += "</option>";
         }
         if (selectedValue > 0) {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         } else {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";

            }
         }
         theMenu += "</select>";

         rs.close();
         rs = null;
      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("HTMLFormDropDownList.makeDropDownListSnippet:Exception:"
                     + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theMenu;
   }
   
   public static String makeDropDownListSnippet(
         String formName,
         int selectedValue,
         String theAction,
         XDisconnectedRowSet rs,
         String idCol,
         String valCol,
         String buttonName,
         String javascript,
         String elementid,
         String firstVal,
         String firstValDesc) throws Exception {

      String theMenu = "";
      

      try {
         // build our menu
         theMenu = "<select id=\"" + elementid + "\" " + javascript + " class=\"inputbox \" name=\"" + formName + "\">\n";
         if (firstValDesc != "") {
            theMenu += "<option value=\"" + firstVal + "\">";
            theMenu += firstValDesc;
            theMenu += "</option>";
         }
         if (selectedValue > 0) {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>\n";
            }
         } else {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>\n";
            }
         }

         theMenu += "</select>\n";

         rs.close();
         rs = null;
      } catch (Exception e) {
         Errors.addError("HTMLFormDropDownList.makeDropDownList:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theMenu;
   }
   
   public static String makeDropDownListSnippet(
         String selectName,
         int selectedValue,
         ResultSet rs,
         String idCol,
         String valCol,
         int firstVal,
         String firstValDesc,
         String javascript,
         String style) throws Exception {

      String theMenu = "";

      try {
         // build our menu
         theMenu = "<select class=\"" + style + "\" " + javascript + " name=\"" + selectName + "\">";
         if (firstValDesc != "") {
            theMenu += "<option value=\"" + firstVal + "\">";
            theMenu += firstValDesc;
            theMenu += "</option>";
         }
         if (selectedValue > 0) {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               if (rs.getInt(idCol) == selectedValue) {
                  theMenu += " selected=\"selected\"";
               }
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";
            }
         } else {
            while (rs.next()) {
               theMenu += "<option value=\"";
               theMenu += rs.getInt(idCol);
               theMenu += "\"";
               theMenu += ">";
               theMenu += rs.getString(valCol);
               theMenu += "</option>";

            }
         }
         theMenu += "</select>";

         rs.close();
         rs = null;
      } catch (Exception e) {
         e.printStackTrace();
         Errors
               .addError("HTMLFormDropDownList.makeDropDownListSnippet:Exception:"
                     + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theMenu;
   }
   
}