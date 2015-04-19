//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-12-06
//Revisions
//------------------------------------------------------------------------------
//$Log: ResultsTable.java,v $
//Revision 1.1.2.27.4.1.6.1  2006/01/20 23:51:34  tcs
//Changed style of table to collapse
//
//Revision 1.1.2.27.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.27  2005/05/18 17:58:48  tcs
//Preliminary support for different input types in qbe
//
//Revision 1.1.2.26  2005/05/18 16:53:29  tcs
//Added bNewButton, and suppress checkbox when not needed
//
//Revision 1.1.2.25  2005/05/18 12:58:27  tcs
//Added explicit "this" to setters
//
//Revision 1.1.2.24  2005/05/03 12:54:08  tcs
//Added table row rollover effect
//
//Revision 1.1.2.23  2005/04/26 14:16:16  tcs
//Changes to pagination for filtered search
//
//Revision 1.1.2.22  2005/04/26 13:18:23  tcs
//Added confirm dialog for delete
//
//Revision 1.1.2.21  2005/04/25 17:57:09  tcs
//Corrected grammatical error in default tooltip for active items
//
//Revision 1.1.2.20  2005/04/25 11:23:09  tcs
//changes for new template model
//
//Revision 1.1.2.19  2005/04/22 18:14:17  tcs
//Modifications for jsp template model
//
//Revision 1.1.2.18  2005/03/03 17:55:24  tcs
//Formatting
//
//Revision 1.1.2.17  2005/02/22 02:01:56  deanh
//Added String variables to allow for customization of the column name, text, hover text and buttons.
//
//Revision 1.1.2.16  2005/01/20 17:10:41  tcs
//progress on filtering
//
//Revision 1.1.2.15  2005/01/20 16:58:58  tcs
//Removed need for setting publish url directly
//
//Revision 1.1.2.14  2005/01/19 17:53:01  tcs
//Moved a lot of code up to ResultsTable from other classes
//
//Revision 1.1.2.13  2005/01/19 02:15:51  tcs
//Began work of moving logic up to here from subclasses of processpage
//
//Revision 1.1.2.12  2005/01/13 17:58:58  tcs
//Progress on filtering search results
//
//Revision 1.1.2.11  2005/01/13 16:34:30  tcs
//Inital code for filtering query
//
//Revision 1.1.2.10  2005/01/13 15:58:20  tcs
//Added support for changing number of rows on ResultsTable
//
//Revision 1.1.2.9  2004/12/19 22:19:50  tcs
//Cosmetic output changes
//
//Revision 1.1.2.8  2004/12/18 23:44:59  tcs
//Initial code for paging results
//
//Revision 1.1.2.7  2004/12/17 17:23:38  tcs
//Started support for sortable col headings
//
//Revision 1.1.2.6  2004/12/17 14:31:05  tcs
//Moved menu to right of page (formatting html)
//
//Revision 1.1.2.5  2004/12/09 19:04:06  tcs
//Corrected display of additional columns
//
//Revision 1.1.2.4  2004/12/09 18:48:50  tcs
//Added style to tr tags
//
//Revision 1.1.2.3  2004/12/08 15:53:37  tcs
//Complete
//
//Revision 1.1.2.2  2004/12/06 19:10:55  tcs
//Suppressed javascript if not needed for a page
//
//Revision 1.1.2.1  2004/12/06 19:01:42  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.object.Errors;

/**
 * Generates an html table from a resultset
 * 
 * <P>
 * Dec 06, 2004
 * <P>
 * 
 * @author tsawler
 */
public class ResultsTable {

   public XDisconnectedRowSet rs = new XDisconnectedRowSet();
   public String[] sColHeadings = {};
   public String[] sColValues = {};
   public String[] sFilterFields = {};
   public String[] sFilterFieldsDisplay = {};
   public String[] sFieldType = {};
   public boolean[] bSortable;
   public String sEditPage = "";
   public String sNewButtonUrl = "";
   public String sLinkColName = "";
   public boolean bPublishButton = true;
   public boolean bUnpublishButton = true;
   public boolean bDeleteButton = true;
   public boolean bPublishColumn = true;
   public boolean bNewButton = true;
   public boolean bAutoRetrieve = true;
   public String sIdColumn = "";
   private String sPublishCol = "";
   private String sPublishedColumnName = "Status";
   private String sPublishButton = "Activate Selected";
   private String sUnpublishButton = "Deactivate Selected";
   private String sPublishText = "Active";
   private String sUnpublishText = "Inactive";
   private String sPublishTitle = "This item is active, and will appear on "
         + "the live web site. Click to deactivate.";
   private String sUnpublishTitle = "This item is not active, and will not appear "
         + "on the live web site. Click to activate.";
   private String sPager = "";
   protected HashMap myHm = new HashMap();
   public int iNumResultsetRows = 0;
   public HttpSession session = null;
   public String sClass = "";
   public String sUrl = "";

   public ResultsTable(HashMap hm, HttpSession session) {
      myHm = hm;
      this.session = session;
   }

   public String GenerateHTMLResultsTable() throws Exception {

      int iCount = 0;
      String theTable = "";

      if ((bPublishButton)
            || (bUnpublishButton)
            || (bDeleteButton)
            || (bUnpublishButton)
            || (bPublishButton)) {
         theTable = "<script language=\"javascript\">\n"
               + "<!--\n"
               + "function performaction ( selectedtype )\n"
               + "{\n document.rt.action.value = selectedtype ; \n"
               + "document.rt.submit() ; \n}\n"
               + "function confirmDelete()\n"
               + "{\n"
               + "var agree=confirm(\"Are you sure you wish to delete the selected items?\");\n"
               + "if (agree) {\n"
               + "document.rt.action.value = 3;\n"
               + "document.rt.submit()\n;"
               + "}\n"
               + "else\n"
               + "return false;\n"
               + "}\n"
               + "-->\n"
               + "</script>\n";
      }

      try {

         // first build our top menu
         theTable += "<table border=\"0\" width=\"100%\">"
               + "<tr><td style=\"text-align: right\" colspan=\"2\" class=\"content\">";
         if (bPublishButton) {
            theTable += "<a href=\"javascript:performaction('1')\">"
                  + sPublishButton
                  + "</a> :: ";
         }
         if (bUnpublishButton) {
            theTable += "<a href=\"javascript:performaction('2')\">"
                  + sUnpublishButton
                  + "</a> :: ";
         }
         if (bDeleteButton) {
            theTable += "<a href=\"#\" onclick=\"return confirmDelete()\">"
                  + "Delete Selected</a> :: ";
         }
         if ((sNewButtonUrl.length() > 0) && (bNewButton)) {
            theTable += "<a href=\"" + sNewButtonUrl + "\">New</a> :: ";
         }

         theTable += "</td></tr>";

         theTable += "<tr><td style=\"text-align: left;\" class=\"content\">";

         if (sFilterFields.length > 0) {
            theTable += "<form name=\"qbe\" action=\""
                  + sUrl
                  + "\"><input type=\"hidden\" name=\"filter\" value=\"1\" />";
            for (int i = 0; i <= (sFilterFields.length - 1); i++) {

               try {
                  // this field is not just a text box.
                  if (sFieldType[i].equalsIgnoreCase("checkbox")) {
                     theTable += "&nbsp;"
                           + sFilterFieldsDisplay[i]
                           + "<input type=\"checkbox\" name=\"filter_"
                           + sFilterFields[i]
                           + "\"";
                     if (session.getAttribute("filter_" + sFilterFields[i]) != null) {
                        theTable += " checked ";
                     }
                     theTable += ">";
                  } else if (sFieldType[i].equalsIgnoreCase("ddlb")) {
                     
                  }
               } catch (Exception exc) {
                  // no field type set, so assume it's a text box
                  theTable += sFilterFieldsDisplay[i]
                        + ": <input type=\"text\" class=\"inputbox\" name=\"filter_"
                        + sFilterFields[i]
                        + "\"";
                  if (session.getAttribute("filter_" + sFilterFields[i]) != null) {
                     theTable += " value=\""
                           + session.getAttribute("filter_" + sFilterFields[i])
                           + "\"";
                  }
                  theTable += ">";
               }

            }
            theTable += "&nbsp;<input class=\"inputbox\" type=\"submit\" value=\"Filter\"></form>";
         } else {
            theTable += "&nbsp;";
         }
         theTable += "</td>";

         // if we are paging results, put up the ddlb
         if (myHm.get("limit") != null) {
            theTable += "</td><td style=\"text-align: right;\" class=\"content\"><br />";
            theTable += "<form name=\"numpicker\" action=\""
                  + session.getAttribute("lastPage")
                  + "\" />";
            theTable += "Display <select class=\"inputbox\" name=\"limit\">";
            for (int i = 1; i <= 10; i++) {
               theTable += "<option value=\"" + (i * 5) + "\" ";
               if (Integer.parseInt((String) myHm.get("limit")) == (i * 5))
                  theTable += " selected ";
               theTable += ">" + (i * 5) + "</option>";
            }
            theTable += "</select> results <input type=\"submit\" value=\"Go\" class=\"inputbox\"></form></td>";
         } else {
            theTable += "<td>&nbsp;</td>";
         }
         theTable += "</tr></table>";

         // build pager, if applicable
         if (myHm.get("limit") != null) {

            // use integer division to calculate the number of pages we need
            int iPageCount = iNumResultsetRows
                  / Integer.parseInt((String) myHm.get("limit"));
            if ((((float) iNumResultsetRows / (float) Integer
                  .parseInt((String) myHm.get("limit"))) - iPageCount) > 0)
               iPageCount = iPageCount + 1;

            sPager += "<table width=\"100%\" cellpadding=\"1\" cellspacing=\"1\"><tr class=\"pager\"><td align=\"center\">";
            if (Integer.parseInt((String) myHm.get("offset")) > 0) {
               sPager += "<a class=\"pager\" style=\"text-decoration: none;\" href=\""
                     + sUrl
                           .replaceAll(
                                 "/limit/([0-9]|[0-9][0-9])/offset/([0-9]|[0-9][0-9])/",
                                 "/limit/"
                                       + myHm.get("limit")
                                       + "/offset/"
                                       + (Integer.parseInt((String) myHm
                                             .get("offset")) - Integer
                                             .parseInt((String) myHm
                                                   .get("limit")))
                                       + "/")
                     + "\">"
                     + "<<</a>&nbsp;&nbsp;";
            } else {
               sPager += "<span style=\"color: grey\"><<</span>&nbsp;&nbsp;";
            }

            for (int i = 1; i <= iPageCount; i++) {
               if (((i - 1) * Integer.parseInt((String) myHm.get("limit"))) != Integer
                     .parseInt((String) myHm.get("offset"))) {
                  sPager += "<a class=\"pager\" href=\""
                        + sUrl
                              .replaceAll(
                                    "/limit/([0-9]|[0-9][0-9])/offset/([0-9]|[0-9][0-9])/",
                                    "/limit/"
                                          + myHm.get("limit")
                                          + "/offset/"
                                          + ((i - 1) * Integer
                                                .parseInt((String) myHm
                                                      .get("limit")))
                                          + "/")
                        + "\">"
                        + i
                        + "</a>"
                        + "&nbsp;";
               } else {

                  sPager += "<strong>"
                        + "<span style=\"color: red;\">"
                        + i
                        + "</span></strong>"
                        + "&nbsp;";
               }
            }

            if ((Integer.parseInt((String) myHm.get("offset"))
                  / Integer.parseInt((String) myHm.get("limit")) != (iPageCount - 1))) {

               sPager += "&nbsp;<a class=\"pager\" style=\"text-decoration: none;\" href=\""
                     + sUrl
                           .replaceAll(
                                 "/limit/([0-9]|[0-9][0-9])/offset/([0-9]|[0-9][0-9])/",
                                 "/limit/"
                                       + myHm.get("limit")
                                       + "/offset/"
                                       + (Integer.parseInt((String) myHm
                                             .get("offset")) + Integer
                                             .parseInt((String) myHm
                                                   .get("limit")))
                                       + "/")
                     + "\">"
                     + ">></a>";
            } else {
               sPager += "&nbsp;<span style=\"color: grey\">>></span>";
            }
            sPager += "</td></tr></table>";
         }
         theTable += sPager;

         // Now build our table and headings
         theTable += "<form name=\"rt\" action=\""
               + sUrl
               + "\" method=\"post\">";
         theTable += "<table width=\"100%\" class=\"collapse\">";
         theTable += "<tr class=\"tableheader\"><td width=\"3%\">&nbsp;</td>";

         if (sColHeadings.length > 0) {
            for (int i = 0; i <= (sColHeadings.length - 1); i++) {
               try {
                  if (bSortable[i] == false) {
                     theTable += "<td><strong>"
                           + sColHeadings[i]
                           + "</strong></td>";
                  } else {
                     theTable += "<td><a href=\""
                           + sUrl
                           + "?&action=6"
                           + "&colnum="
                           + i
                           + "\"><strong>"
                           + sColHeadings[i]
                           + "</strong></a></td>";
                  }
               } catch (Exception e) {
                  theTable += "<td><strong>"
                        + sColHeadings[i]
                        + "</strong></td>";
               }
            }
         }

         if (bPublishColumn) {
            theTable += "<td><strong>"
                  + sPublishedColumnName
                  + "</strong></td>";
         }

         theTable += "</tr>";

         // now fill out the rows for our column values
         while (rs.next()) {
            // init/increment counter
            iCount++;

            // do the checkbox column
            theTable += "<tr class=\"content\" onMouseOver=\"this.className='tableheader'\" onMouseOut=\"this.className='content'\">"
                  + "<td>";
            if (bPublishButton || bUnpublishButton || bDeleteButton) {
               theTable += "<div align=\"center\"><input type=\"checkbox\" name=\"action_"
                     + iCount
                     + "\"><input type=\"hidden\" name=\"item_id_"
                     + iCount
                     + "\" value=\""
                     + rs.getString(sIdColumn)
                     + "\"></div>";
            } else {
               theTable += "&nbsp;";
            }
            theTable += "</td>";

            // do the linking column
            theTable += "<td>"
                  + "<a title=\"Click here to edit this item.\" href=\""
                  + sEditPage
                  + rs.getString(sIdColumn)
                  + "&edit=false\">"
                  + rs.getString(sLinkColName)
                  + "</a></td>";

            // do the rest of the columns
            int iNumCols = 0;

            try {
               iNumCols = sColValues.length;
            } catch (Exception e) {

            }

            if (iNumCols > 0) {
               for (int i = 0; i <= iNumCols - 1; i++) {
                  theTable += "<td>" + rs.getString(sColValues[i]) + "</td>";
               }
            }

            // if applicable, do publish/unpublish column
            if (bPublishColumn) {
               theTable += "<td>" + "<a href=\"" + sUrl;
               if (rs.getString(sPublishCol).equals("y")) {
                  theTable += "?action=2"
                        + "&cancelPage="
                        + sUrl
                        + "&count=1"
                        + "&action_1=1"
                        + "&item_id_1="
                        + rs.getString(sIdColumn)
                        + "\" class=\"pub\" "
                        + "title='"
                        + sPublishTitle
                        + "'>"
                        + sPublishText
                        + "</a>";
               } else {
                  theTable += "?action=1"
                        + "&cancelPage="
                        + sUrl
                        + "&count=1"
                        + "&action_1=1"
                        + "&item_id_1="
                        + rs.getString(sIdColumn)
                        + "\" class=\"upub\" "
                        + "title='"
                        + sUnpublishTitle
                        + "'>"
                        + sUnpublishText
                        + "</a>";
               }

               theTable += "</td>";
            }
            theTable += "</tr>";
         }

         theTable += "</table>"
               + "<input type=\"hidden\" name=\"action\" />"
               + "<input type=\"hidden\" name=\"count\" value=\""
               + iCount
               + "\" />"
               + "<input type=\"hidden\" name=\"cancelPage\" value=\""
               + sUrl
               + "\" /></form>";

         if (sPager.length() > 0) {
            theTable += sPager;
         }

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ResultsTable.GenerateHTMLResultsTable:Exception:"
               + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }
      // return the complete menu
      return theTable;
   }

   /**
    * @return Returns the bDeleteButton.
    */
   public boolean isBDeleteButton() {
      return bDeleteButton;
   }

   /**
    * @param deleteButton
    *           The bDeleteButton to set.
    */
   public void setBDeleteButton(boolean deleteButton) {
      bDeleteButton = deleteButton;
   }

   /**
    * @return Returns the bPublishButton.
    */
   public boolean isBPublishButton() {
      return bPublishButton;
   }

   /**
    * @param publishButton
    *           The bPublishButton to set.
    */
   public void setBPublishButton(boolean publishButton) {
      this.bPublishButton = publishButton;
   }

   /**
    * @return Returns the bUnpublishButton.
    */
   public boolean isBUnpublishButton() {
      return bUnpublishButton;
   }

   /**
    * @param unpublishButton
    *           The bUnpublishButton to set.
    */
   public void setBUnpublishButton(boolean unpublishButton) {
      this.bUnpublishButton = unpublishButton;
   }

   /**
    * @return Returns the rs.
    */
   public XDisconnectedRowSet getRs() {
      return rs;
   }

   /**
    * @param rs
    *           The rs to set.
    */
   public void setRs(XDisconnectedRowSet rs) {
      this.rs = rs;
   }

   /**
    * @return Returns the sColHeadings.
    */
   public String[] getSColHeadings() {
      return sColHeadings;
   }

   /**
    * @param colHeadings
    *           The sColHeadings to set.
    */
   public void setSColHeadings(String[] colHeadings) {
      this.sColHeadings = colHeadings;
   }

   /**
    * @return Returns the sColValues.
    */
   public String[] getSColValues() {
      return sColValues;
   }

   /**
    * @param colValues
    *           The sColValues to set.
    */
   public void setSColValues(String[] colValues) {
      this.sColValues = colValues;
   }

   /**
    * @return Returns the sEditPage.
    */
   public String getSEditPage() {
      return sEditPage;
   }

   /**
    * @param editPage
    *           The sEditPage to set.
    */
   public void setSEditPage(String editPage) {
      this.sEditPage = editPage;
   }

   /**
    * @return Returns the sNewButtonUrl.
    */
   public String getSNewButtonUrl() {
      return sNewButtonUrl;
   }

   /**
    * @param newButtonUrl
    *           The sNewButtonUrl to set.
    */
   public void setSNewButtonUrl(String newButtonUrl) {
      this.sNewButtonUrl = newButtonUrl;
   }

   /**
    * @return Returns the sIdColumn.
    */
   public String getSIdColumn() {
      return sIdColumn;
   }

   /**
    * @param idColumn
    *           The sIdColumn to set.
    */
   public void setSIdColumn(String idColumn) {
      this.sIdColumn = idColumn;
   }

   /**
    * @return Returns the bPublishColumn.
    */
   public boolean isBPublishColumn() {
      return bPublishColumn;
   }

   /**
    * @param publishColumn
    *           The bPublishColumn to set.
    */
   public void setBPublishColumn(boolean publishColumn) {
      this.bPublishColumn = publishColumn;
   }

   /**
    * @return Returns the sPublishCol.
    */
   public String getSPublishCol() {
      return sPublishCol;
   }

   /**
    * @param publishCol
    *           The sPublishCol to set.
    */
   public void setSPublishCol(String publishCol) {
      this.sPublishCol = publishCol;
   }

   /**
    * @return Returns the sLinkColName.
    */
   public String getSLinkColName() {
      return sLinkColName;
   }

   /**
    * @param linkColName
    *           The sLinkColName to set.
    */
   public void setSLinkColName(String linkColName) {
      this.sLinkColName = linkColName;
   }

   /**
    * @return Returns the bSortable.
    */
   public boolean[] getBSortable() {
      return bSortable;
   }

   /**
    * @param sortable
    *           The bSortable to set.
    */
   public void setBSortable(boolean[] sortable) {
      this.bSortable = sortable;
   }

   /**
    * @return Returns the iNumResultsetRows.
    */
   public int getINumResultsetRows() {
      return iNumResultsetRows;
   }

   /**
    * @param numResultsetRows
    *           The iNumResultsetRows to set.
    */
   public void setINumResultsetRows(int numResultsetRows) {
      this.iNumResultsetRows = numResultsetRows;
   }

   /**
    * @return Returns the myHm.
    */
   public HashMap getMyHm() {
      return myHm;
   }

   /**
    * @param myHm
    *           The myHm to set.
    */
   public void setMyHm(HashMap myHm) {
      this.myHm = myHm;
   }

   /**
    * @return Returns the sFilterFields.
    */
   public String[] getSFilterFields() {
      return sFilterFields;
   }

   /**
    * @param filterFields
    *           The sFilterFields to set.
    */
   public void setSFilterFields(String[] filterFields) {
      this.sFilterFields = filterFields;
   }

   /**
    * @return Returns the sFilterFieldsDisplay.
    */
   public String[] getSFilterFieldsDisplay() {
      return sFilterFieldsDisplay;
   }

   /**
    * @param filterFieldsDisplay
    *           The sFilterFieldsDisplay to set.
    */
   public void setSFilterFieldsDisplay(String[] filterFieldsDisplay) {
      this.sFilterFieldsDisplay = filterFieldsDisplay;
   }

   /**
    * @return Returns the sClass.
    */
   public String getSClass() {
      return sClass;
   }

   /**
    * @param class1
    *           The sClass to set.
    */
   public void setSClass(String class1) {
      this.sClass = class1;
   }

   /**
    * @return Returns the sUrl.
    */
   public String getSUrl() {
      return sUrl;
   }

   /**
    * @param url
    *           The sUrl to set.
    */
   public void setSUrl(String url) {
      this.sUrl = url;
   }

   /**
    * @return Returns the bAutoRetrieve.
    */
   public boolean isBAutoRetrieve() {
      return bAutoRetrieve;
   }

   /**
    * @param autoRetrieve
    *           The bAutoRetrieve to set.
    */
   public void setBAutoRetrieve(boolean autoRetrieve) {
      this.bAutoRetrieve = autoRetrieve;
   }

   /**
    * @return Returns the sPublishButton.
    */
   public String getSPublishButton() {
      return sPublishButton;
   }

   /**
    * @param publishButton
    *           The sPublishButton to set.
    */
   public void setSPublishButton(String publishButton) {
      this.sPublishButton = publishButton;
   }

   /**
    * @return Returns the sPublishText.
    */
   public String getSPublishText() {
      return sPublishText;
   }

   /**
    * @param publishText
    *           The sPublishText to set.
    */
   public void setSPublishText(String publishText) {
      this.sPublishText = publishText;
   }

   /**
    * @return Returns the sPublishTitle.
    */
   public String getSPublishTitle() {
      return sPublishTitle;
   }

   /**
    * @param publishTitle
    *           The sPublishTitle to set.
    */
   public void setSPublishTitle(String publishTitle) {
      this.sPublishTitle = publishTitle;
   }

   /**
    * @return Returns the sUnpublishButton.
    */
   public String getSUnpublishButton() {
      return sUnpublishButton;
   }

   /**
    * @param unpublishButton
    *           The sUnpublishButton to set.
    */
   public void setSUnpublishButton(String unpublishButton) {
      this.sUnpublishButton = unpublishButton;
   }

   /**
    * @return Returns the sUnpublishText.
    */
   public String getSUnpublishText() {
      return sUnpublishText;
   }

   /**
    * @param unpublishText
    *           The sUnpublishText to set.
    */
   public void setSUnpublishText(String unpublishText) {
      this.sUnpublishText = unpublishText;
   }

   /**
    * @return Returns the sUnpublishTitle.
    */
   public String getSUnpublishTitle() {
      return sUnpublishTitle;
   }

   /**
    * @param unpublishTitle
    *           The sUnpublishTitle to set.
    */
   public void setSUnpublishTitle(String unpublishTitle) {
      this.sUnpublishTitle = unpublishTitle;
   }

   /**
    * @return Returns the sPublishedColumnName.
    */
   public String getSPublishedColumnName() {
      return sPublishedColumnName;
   }

   /**
    * @param publishedColumnName
    *           The sPublishedColumnName to set.
    */
   public void setSPublishedColumnName(String publishedColumnName) {
      this.sPublishedColumnName = publishedColumnName;
   }

   /**
    * @return Returns the bNewButton.
    */
   public boolean isBNewButton() {
      return bNewButton;
   }

   /**
    * @param newButton
    *           The bNewButton to set.
    */
   public void setBNewButton(boolean newButton) {
      this.bNewButton = newButton;
   }

   /**
    * @return Returns the sFieldType.
    */
   public String[] getSFieldType() {
      return sFieldType;
   }

   /**
    * @param fieldType
    *           The sFieldType to set.
    */
   public void setSFieldType(String[] fieldType) {
      this.sFieldType = fieldType;
   }

}