//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-04-29
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessMediaManagerChoose.java,v $
//Revision 1.1.2.9.4.3  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.9.4.2  2005/08/17 15:15:15  tcs
//Changed style of popup boxes
//
//Revision 1.1.2.9.4.1  2005/08/10 01:36:00  tcs
//Fixed URLs for new model
//
//Revision 1.1.2.9  2005/05/06 15:49:40  tcs
//Changed javascript call for popups
//
//Revision 1.1.2.8  2005/05/06 11:55:50  tcs
//Complete and functional
//
//Revision 1.1.2.7  2005/05/05 18:19:11  tcs
//Added folder creation
//
//Revision 1.1.2.6  2005/05/04 14:23:46  tcs
//Added parameter to "new" link
//
//Revision 1.1.2.5  2005/05/02 13:17:32  tcs
//Changed up.gif definition
//
//Revision 1.1.2.4  2005/04/29 18:23:58  tcs
//Added error message display for delete of folder
//
//Revision 1.1.2.3  2005/04/29 16:48:56  tcs
//Implemented delete functionality
//
//Revision 1.1.2.2  2005/04/29 16:02:21  tcs
//In progress
//
//Revision 1.1.2.1  2005/04/29 13:28:00  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;

/**
 * Media manager chooser
 * 
 * <P>
 * April 29th 2005
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessMediaManagerChoose extends ProcessPage {

   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      String theDirectory = SingletonObjects.getInstance().getSystem_path()
            + "UserFiles/Image";
      String theUrlDirectory = "/UserFiles/Image";
      int numberTokens = 0;
      String thePostURL = "";

      if (hm.get("dir") != null) {
         StringTokenizer st = null;
         st = new StringTokenizer((String) hm.get("dir"), "!");
         numberTokens = st.countTokens();

         if (numberTokens > 1) {
            while (st.hasMoreElements()) {
               String theToken = (String) st.nextToken();
               theDirectory += "/" + theToken;
               theUrlDirectory += "/" + theToken;
            }
         } else if (numberTokens == 1) {
            theDirectory += "/" + hm.get("dir");
            theUrlDirectory += "/" + hm.get("dir");
         }
      }

      try {
         File dir = new File(theDirectory);
         File[] children = dir.listFiles();
         java.util.Arrays.sort(children);
         if (children == null) {
            // Either dir does not exist or is not a directory
         } else {
            MasterTemplate
                  .appendString("<script language=\"JavaScript\" type=\"text/JavaScript\">\n"
                        + "<!--\n"
                        + "function openBrWindow(theURL,winName,features) {\n"
                        + "window.open(theURL,winName,features); \n"
                        + "}\n"
                        + "function confirmDelete()\n"
                        + "{\n"
                        + "var agree=confirm(\"Are you sure you wish to delete the selected items?\");\n"
                        + "if (agree) {\n"
                        + "	document.imageform.action.value = 8 ;\n"
                        + "  document.imageform.submit() ;\n"
                        + "	}\n"
                        + "else\n"
                        + "	return false ;\n"
                        + "}\n"
                        + "//-->\n"
                        + "</script>\n"
                        + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"/js/utility.js\"></script>\n"
                        + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"/js/popup.js\"></script>\n");
            if (hm.get("dir") != null) {
               thePostURL = "/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/dir/"
                     + hm.get("dir")
                     + "/admin/1/content.do";
            } else {
               thePostURL = "/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/admin/1/content.do";
            }
            MasterTemplate
                  .appendString("<table class=\"content\" style=\"width: 100%\">"
                        + "<tr>"
                        + "<td style=\"text-align: right;\">"
                        + "<a href=\"#\" onclick=\"return !showPopupWithLocWidth('popimageadd', event, -130, 10, 225);\">New Image</a> :: "
                        + "<a href=\"#\" onclick=\"return !showPopupWithLoc('popfolderadd', event, -130, 10);\">New Folder</a> :: "
                        + "<a href=\"#\" onclick=\"return confirmDelete()\">Delete selected</a>"
                        + "</td>"
                        + "</tr>"
                        + "</table><br />"
                        + "<div onclick=\"event.cancelBubble = true;\" class=\"popup\" id=\"popfolderadd\">"
                        + "<table class=\"content\">"
                        + "<form action=\""
                        + thePostURL
                        + "\" method=\"post\">"
                        + "<input type=\"hidden\" name=\"act\" value=\"9\">"
                        + "<tr>"
                        + "<td>Folder name:</td><td><input class=\"inputbox\" type=\"text\" name=\"foldername\"><input type=\"submit\" class=\"inputbox\" value=\"go\"></td>"
                        + "</tr>"
                        + "</table>"
                        + "</form>"
                        + "</div>"
                        + "<div onclick=\"event.cancelBubble = true;\" class=\"popup\" id=\"popimageadd\">"
                        + "<form name=\"imf\" action=\""
                        + thePostURL
                        + "?act=10");
            if (hm.get("dir") != null) {
               MasterTemplate.appendString("&path="
                     + ((String) hm.get("dir")).replaceAll("!", "/")
                     + "\" method=\"post\" enctype=\"multipart/form-data\">");
            } else {
               MasterTemplate
                     .appendString("&path=\" method=\"post\" enctype=\"multipart/form-data\">");
            }
            MasterTemplate
                  .appendString("<table class=\"content\">"
                        + "<tr><td>Image:</td><td><input class=\"inputbox\" type=\"file\" name=\"input_file\" size=\"10\">"
                        + "&nbsp;<input type=\"submit\" class=\"inputbox\" value=\"Go\" /></td></tr></table></form></div>");

            MasterTemplate
                  .appendString("<form name=\"imageform\" action=\"/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/");
            if (hm.get("dir") != null) {
               MasterTemplate.appendString("dir/" + hm.get("dir") + "/");
            }
            MasterTemplate
                  .appendString("admin/1/content.do\" method=\"post\">");
            MasterTemplate
                  .appendString("<input type=\"hidden\" name=\"numberElements\" value=\""
                        + children.length
                        + "\">");
            MasterTemplate
                  .appendString("<input type=\"hidden\" name=\"action\">");
            MasterTemplate
                  .appendString("<table class=\"tabledatacollapse\" style=\"width: 100%;\">");
            MasterTemplate
                  .appendString("<tr class=\"tableheader\"><td width=\"3%\">&nbsp;</td><td>Item</td><td>Name</td></tr>");

            if (hm.get("dir") != null) {
               StringTokenizer st = null;
               st = new StringTokenizer((String) hm.get("dir"), "!");
               numberTokens = st.countTokens();
               if (numberTokens == 1) {
                  MasterTemplate
                        .appendString("<tr><td>&nbsp;</td><td style=\"width: 80px;\"><a href=\"/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/admin/1/content.do\">"
                              + "<div align=\"center\"><img src=\"/images/up.gif\" border=\"0\" height=\"32\" width=\"32\"></div></a></td><td><a href=\""
                              + "/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/admin/1/content.do\">parent dir</a></td>"
                              + "</tr>");
               } else {
                  st = null;
                  st = new StringTokenizer((String) hm.get("dir"), "!");
                  numberTokens = (st.countTokens() - 1);
                  String sPath = "";
                  for (int k = 0; k < numberTokens; k++) {
                     String theToken = (String) st.nextToken();
                     sPath += "/" + theToken;
                  }
                  MasterTemplate
                        .appendString("<tr><td>&nbsp;</td><td style=\"width: 80px;\"><a href=\"/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/admin/1/content.do\">"
                              + "<div align=\"center\"><img src=\"/images/up.gif\" border=\"0\" height=\"32\" width=\"32\"></div></a></td>"
                              + "<td><a href=\""
                              + "/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/dir"
                              + sPath
                              + "/admin/1/content.do\">parent dir</a></td>"
                              + "</tr>");
               }

            }

            for (int j = 0; j < children.length; j++) {
               String filename = children[j].getName();
               if (children[j].isDirectory()) {
                  MasterTemplate
                        .appendString("<tr><td><div align=\"center\"><input type=\"checkbox\" name=\"folder_action_"
                              + j
                              + "\">");
                  MasterTemplate
                        .appendString("<input type=\"hidden\" name=\"foldername_"
                              + j
                              + "\""
                              + " value=\""
                              + theUrlDirectory
                              + "/"
                              + filename
                              + "\">"
                              + "</div></td><td style=\"width: 80px;\">");
                  MasterTemplate
                        .appendString("<div align=\"center\"><a href=\"/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/dir/");
                  if (hm.get("dir") != null) {
                     MasterTemplate.appendString((String) hm.get("dir") + "!");
                  }
                  MasterTemplate
                        .appendString(filename
                              + "/admin/1/content.do\">"
                              + "<img src=\"/images/folder.gif\" border=\"0\" width=\"32\" height=\"32\"></div></td>");
                  MasterTemplate
                        .appendString("<td><a href=\"/admin/jpage/1/p/MediaManagerChoose/a/mediamanagerchoose/dir/");
                  if (hm.get("dir") != null) {
                     MasterTemplate.appendString((String) hm.get("dir") + "!");
                  }
                  MasterTemplate.appendString(filename
                        + "/admin/1/content.do\">"
                        + filename
                        + "</a></td></tr>");
               }
            }

            for (int i = 0; i < children.length; i++) {
               // Get filename of file or directory
               String filename = children[i].getName();
               if (children[i].isFile()) {

                  MasterTemplate
                        .appendString("<tr><td><div align=\"center\"><input type=\"checkbox\" name=\"item_action_"
                              + i
                              + "\">");
                  MasterTemplate
                        .appendString("<input type=\"hidden\" name=\"item_name_"
                              + i
                              + "\" value=\""
                              + theUrlDirectory
                              + "/"
                              + filename
                              + "\"></div></td>");
                  MasterTemplate
                        .appendString("<td style=\"width: 80px; text-align: center;\" align=\"center\"><a href=\"#\" onClick=\"openBrWindow('"
                              + theUrlDirectory
                              + "/"
                              + filename
                              + "','','resizable=yes,width=300,height=300')\"><img border=\"0\" src=\""
                              + theUrlDirectory
                              + "/");
                  MasterTemplate.appendString(filename
                        + "\" width=\"75\"></td><td>"
                        + filename
                        + "</td></tr>");
               }
            }

            MasterTemplate.appendString("</table></form>");
         }

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("ProcessNewsItemChoice:BuildPage:Exception:"
               + e.toString());
      }
      return MasterTemplate;
   }

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int numberOfElements = 0;
      int action = 0;
      String message = "";

      try {
         if (request.getParameter("action") != null)
            action = Integer.parseInt((String) request.getParameter("action"));
         if (request.getParameter("numberElements") != null)
            numberOfElements = Integer.parseInt((String) request
                  .getParameter("numberElements"));

         if (action == 8) {
            // deleting folders/pictures

            // do pictures first
            for (int i = 0; i < numberOfElements; i++) {
               if (request.getParameter("item_action_" + i) != null) {
                  try {
                     File f = new File(SingletonObjects.getInstance()
                           .getSystem_path()
                           + (String) request.getParameter("item_name_" + i));
                     f.delete();
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               }
            }

            // now do folders
            for (int i = 0; i < numberOfElements; i++) {
               if (request.getParameter("folder_action_" + i) != null) {
                  try {
                     File f = new File(SingletonObjects.getInstance()
                           .getSystem_path()
                           + (String) request.getParameter("foldername_" + i));
                     boolean result = f.delete();
                     if (!result) {
                        message = "Cannot delete a non-empty folder";
                     }
                  } catch (Exception e) {
                     message = "Cannot delete a non-empty folder";
                     e.printStackTrace();
                  }
               }
            }
         }

         if (request.getParameter("act") != null) {
            int act = Integer.parseInt((String) request.getParameter("act"));
            if (act == 9) {
               // adding folder
               String theDirectory = SingletonObjects.getInstance()
                     .getSystem_path()
                     + "UserFiles/Image";
               int numberTokens = 0;
               if (hm.get("dir") != null) {
                  StringTokenizer st = null;
                  st = new StringTokenizer((String) hm.get("dir"), "!");
                  numberTokens = st.countTokens();

                  if (numberTokens > 1) {
                     while (st.hasMoreElements()) {
                        String theToken = (String) st.nextToken();
                        theDirectory += "/" + theToken;
                     }
                  } else if (numberTokens == 1) {
                     theDirectory += "/" + hm.get("dir");
                  }
               }

            } else if (act == 10) {
               // adding image
               String isPath = "";
               try {
                  DiskFileUpload upload = new DiskFileUpload();
                  List items = upload.parseRequest(request);
                  isPath = SingletonObjects.getInstance().getSystem_path()
                        + "/UserFiles/Image/";
                  isPath += request.getParameter("path");
                  Iterator itr = items.iterator();

                  while (itr.hasNext()) {
                     FileItem item = (FileItem) itr.next();
                     if (!item.isFormField()) {
                        File fullFile = new File(item.getName());
                        System.out.println("saving to "
                              + isPath
                              + ", file is "
                              + fullFile.getName());
                        File f = new File(isPath, fullFile.getName());
                        item.write(f);

                     }
                  }

               } catch (Exception e) {
                  System.out.println(e.toString());
                  e.printStackTrace();
               }
            }
         }
      } catch (Exception e) {

      }
      if (message.length() > 0) {
         session.setAttribute("Error", message);
      }
      return this.BuildPage(
            request,
            response,
            session,
            conn,
            MasterTemplate,
            hm);

   }
}