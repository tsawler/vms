//------------------------------------------------------------------------------
//Copyright (c) 2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-23
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessEditBanner.java,v $
//Revision 1.1.2.2.2.2  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.1.2.2.2.1  2007/03/23 11:03:03  tcs
//Corrected error in sql
//
//Revision 1.1.2.2  2007/03/15 17:47:41  tcs
//Take a banner of any size
//
//Revision 1.1.2.1  2007/01/23 19:28:01  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.GenericTable;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;
import com.verilion.object.Errors;
import com.verilion.object.html.HTMLFormDropDownList;

/**
 * Edit/add a banner
 * 
 * <P>
 * January 23rd, 2007
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessEditBanner extends ProcessPage {

   @Override
   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {
      ResultSet rs = null;
      String banner_name = "";
      String image = "";
      String cancelPage = "";
      String banner_active_yn = "";
      String url = "";
      int ct_banner_position_id = 0;
      int banner_id = 0;
      String theMenu = "";
      int ct_language_id = 1;
      String alt_text = "";

      cancelPage = (String) request.getParameter("cancelPage");
      banner_id = Integer.parseInt((String) request.getParameter("id"));

      if (((String) request.getParameter("edit")).equals("false")) {
         try {
            if (banner_id == 0) {
               MasterTemplate.searchReplace("$IMG$", "");
               MasterTemplate.searchReplace("$BANNERNAME$", "");
               MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               MasterTemplate.searchReplace("$URL$", "");
               MasterTemplate.searchReplace("$ID$", banner_id + "");
               MasterTemplate.searchReplace("$ACTIVEY$", "");
               MasterTemplate.searchReplace("$ACTIVEN$", "");
               MasterTemplate.searchReplace("$ALTTEXT$", "");
            } else {
               Statement st = null;
               st = conn.createStatement();
               String query = "select * from banners where banner_id = "
                     + banner_id;
               ResultSet myrs = null;
               myrs = st.executeQuery(query);
               if (myrs.next()) {
                  banner_name = myrs.getString("banner_name");
                  image = myrs.getString("banner_file");
                  banner_active_yn = myrs.getString("banner_active_yn");
                  ct_banner_position_id = myrs.getInt("ct_banner_position_id");
                  url = myrs.getString("url");
                  ct_language_id = myrs.getInt("ct_language_id");
                  alt_text = myrs.getString("alt_text");
               }
               myrs.close();
               myrs = null;
               st.close();
               st = null;
               MasterTemplate.searchReplace("$IMG$", "<img src=\"/banners/"
                     + image
                     + "\">");
               MasterTemplate.searchReplace("$BANNERNAME$", banner_name);
               MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               MasterTemplate.searchReplace("$URL$", url);
               MasterTemplate.searchReplace("$ID$", banner_id + "");
               MasterTemplate.searchReplace("$ALTTEXT$", alt_text);
            }
            // create drop down list of languages
			GenericTable myGtable = new GenericTable("ct_languages");
			myGtable.setConn(conn);
			myGtable.setSSelectWhat(" ct_language_id, ct_language_name ");
			myGtable.setSCustomOrder(" order by ct_language_name ");
			rs = myGtable.getAllRecordsDisconnected();

			theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
					"ct_language_id", ct_language_id, rs, "ct_language_id",
					"ct_language_name", -1, "");
			rs.close();
			rs = null;
			MasterTemplate.searchReplace("$DDLBLANG$", theMenu);
			
            // create drop down list of locations
            Statement st = null;
            st = conn.createStatement();
            String query = "select * from ct_banner_position";
            rs = st.executeQuery(query);

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_banner_position_id",
                  ct_banner_position_id,
                  rs,
                  "ct_banner_position_id",
                  "ct_banner_position",
                  -1,
                  "");
            rs.close();
            rs = null;
            st.close();
            st = null;
            MasterTemplate.searchReplace("$DDLB$", theMenu);
            
//          active status
            if (banner_active_yn.equalsIgnoreCase("y")) {
               MasterTemplate.searchReplace("$ACTIVEY$", "selected");
               MasterTemplate.searchReplace("$ACTIVEN$", "");
            } else {
               MasterTemplate.searchReplace("$ACTIVEY$", "");
               MasterTemplate.searchReplace("$ACTIVEN$", "selected");
            }

         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditBanner:ProcessForm:Exception:"
                  + e.toString());
         } finally {
            if (rs != null) {
               rs.close();
               rs = null;
            }
         }
      }
      return super.ProcessForm(
            request,
            response,
            session,
            conn,
            MasterTemplate,
            hm);
   }

   public HTMLTemplateDb ProcessFormMultipart(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm,
         MultipartRequest multi) throws Exception {

      ResultSet rs = null;
      String banner_name = "";
      String image = "";
      String cancelPage = "";
      String banner_active_yn = "";
      String url = "";
      int ct_banner_position_id = 0;
      int banner_id = 0;
      String theMenu = "";
      int ct_banner_sizes_id = 1;
      int ct_language_id = 1;
      String alt_text = "";

      cancelPage = (String) multi.getParameter("cancelPage");
      banner_id = Integer.parseInt((String) multi.getParameter("id"));
      

      if (((String) multi.getParameter("edit")).equals("false")) {
         try {
            if (banner_id == 0) {
               MasterTemplate.searchReplace("$IMG$", "");
               MasterTemplate.searchReplace("$BANNERNAME$", "");
               MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               MasterTemplate.searchReplace("$URL$", "");
               MasterTemplate.searchReplace("$ID$", banner_id + "");
               MasterTemplate.searchReplace("$ALTTEXT$", alt_text);
            } else {
               Statement st = null;
               st = conn.createStatement();
               String query = "select * from banners where banner_id = "
                     + banner_id;
               ResultSet myrs = null;
               myrs = st.executeQuery(query);
               if (myrs.next()) {
                  banner_name = myrs.getString("banner_name");
                  image = myrs.getString("banner_file");
                  banner_active_yn = myrs.getString("banner_active_yn");
                  ct_banner_position_id = myrs.getInt("ct_banner_position_id");
                  url = myrs.getString("url");
                  banner_active_yn = myrs.getString("banner_active_yn");
                  ct_language_id = myrs.getInt("ct_language_id");
                  alt_text = myrs.getString("alt_text");
               }
               myrs.close();
               myrs = null;
               st.close();
               st = null;
               MasterTemplate.searchReplace("$IMG$", "<img src=\"/banners/"
                     + image
                     + "\">");
               MasterTemplate.searchReplace("$BANNERNAME$", banner_name);
               MasterTemplate.searchReplace("$CANCEL$", cancelPage);
               MasterTemplate.searchReplace("$URL$", url);
               MasterTemplate.searchReplace("$ID$", banner_id + "");
               MasterTemplate.searchReplace("$ALTTEXT$", alt_text);
            }
            // create drop down list of locations
            Statement st = null;
            st = conn.createStatement();
            String query = "select * from ct_banner_position";
            rs = st.executeQuery(query);

            theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
                  "ct_banner_position_id",
                  ct_banner_position_id,
                  rs,
                  "ct_banner_position_id",
                  "ct_banner_position",
                  -1,
                  "");
            rs.close();
            rs = null;
            st.close();
            st = null;
            MasterTemplate.searchReplace("$DDLB$", theMenu);
            
            // create drop down list of languages
			GenericTable myGtable = new GenericTable("ct_languages");
			myGtable.setConn(conn);
			myGtable.setSSelectWhat(" ct_language_id, ct_language_name ");
			myGtable.setSCustomOrder(" order by ct_language_name ");
			rs = myGtable.getAllRecordsDisconnected();

			theMenu = HTMLFormDropDownList.makeDropDownListSnippet(
					"ct_language_id", ct_language_id, rs, "ct_language_id",
					"ct_language_name", -1, "");
			rs.close();
			rs = null;
			MasterTemplate.searchReplace("$DDLBLANG$", theMenu);
            
            //active status
            if (banner_active_yn.equalsIgnoreCase("y")) {
               MasterTemplate.searchReplace("$ACTIVEY$", "selected");
               MasterTemplate.searchReplace("$ACTIVEN$", "");
            } else {
               MasterTemplate.searchReplace("$ACTIVEY$", "");
               MasterTemplate.searchReplace("$ACTIVEN$", "selected");
            }

         } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ProcessEditBanner:ProcessFormMulti:Exception:"
                  + e.toString());
         } finally {
            if (rs != null) {
               rs.close();
               rs = null;
            }
         }
      } else {
         int w = 0;
         int h = 0;
         String filename = "";
         File theFile = null;
         String thePath = SingletonObjects.getInstance().getSystem_path()
               + "tmp/";

         // see if we have a new image file
         Enumeration files = multi.getFileNames();

         while (files.hasMoreElements()) {
            String name = (String) files.nextElement();
            filename = multi.getFilesystemName(name);
         }

         if (filename == null) {
            filename = "";
         }

         if (filename.length() > 0) {
            theFile = new File(thePath + filename);

            FileInputStream fis = new FileInputStream(theFile);

            ImageIO.setUseCache(false);
            BufferedImage myImage = ImageIO.read(theFile);

            // get original height/width
            // int h = myImage.getHeight();
            w = myImage.getWidth();
            h = myImage.getHeight();
            
            if ((w == 468) && (h == 60 )) {
               ct_banner_sizes_id = 1;
            } else if ((w == 120) && (h == 60)) {
               ct_banner_sizes_id = 2;
            } else if ((w == 120) && (h == 90)) {
               ct_banner_sizes_id = 3;
            } else if ((w == 234) && (h == 60)) {
               ct_banner_sizes_id = 4;
            } else if ((w == 341) && (h == 65)) {
               ct_banner_sizes_id = 5;
            } else {
               // new banner size!
               GenericTable bTable = new GenericTable();
               bTable.setConn(conn);
               bTable.setSTable("ct_banner_sizes");
               bTable.addUpdateFieldNameValuePair("ct_banner_sizes_desc", h + "x" + w, "string");
               bTable.addUpdateFieldNameValuePair("ct_banner_sizes_height", h + "", "int");
               bTable.addUpdateFieldNameValuePair("ct_banner_sizes_width", w + "", "int");
               bTable.insertRecord();
               bTable.setSSequence("ct_banner_sizes_ct_banner_sizes_id_seq");
               bTable.clearUpdateVectors();
               ct_banner_sizes_id = bTable.returnCurrentSequenceValue();
            }

            // move the file to the /banners directory
            File newFile = new File(SingletonObjects.getInstance()
                  .getSystem_path()
                  + "/banners/"
                  + filename);
            FileOutputStream fos = new FileOutputStream(newFile);

            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
               fos.write(buf, 0, i);
            }
            fis.close();
            fos.close();
            theFile.delete();
         }

         // get our parameters
         banner_name = multi.getParameter("banner_name");
         alt_text = multi.getParameter("alt_text");
         cancelPage = multi.getParameter("cancelPage");
         ct_language_id = Integer.parseInt((String) multi.getParameter("ct_language_id"));
         ct_banner_position_id = Integer.parseInt((String) multi
               .getParameter("ct_banner_position_id"));
         url = multi.getParameter("url");
         banner_active_yn = multi.getParameter("active_yn");

         if (banner_id > 0) {
            String query = "update banners set banner_name = ?, url = ?, "
                  + "ct_banner_position_id = ?, banner_active_yn = ?, ct_language_id = ?, "
            	  + "alt_text = ? ";
            if (filename.length() > 0) {
               query += ", ct_banner_sizes_id = ?, banner_file = ? ";
            }
            query += " where banner_id = ?";

            PreparedStatement pst = null;
            pst = conn.prepareStatement(query);
            pst.setString(1, banner_name);
            pst.setString(2, url);
            pst.setInt(3, ct_banner_position_id);
            pst.setString(4, banner_active_yn);
            pst.setInt(5, ct_language_id);
            pst.setString(6, alt_text);
            if (filename.length() > 0) {
               pst.setInt(7, ct_banner_sizes_id);
               pst.setString(8, filename);
               pst.setInt(9, banner_id);
            } else {
               pst.setInt(7, banner_id);
            }
            pst.executeUpdate();
            pst.close();
            pst = null;
         } else {
            String query = "insert into banners (banner_name, url, ct_banner_position_id, banner_active_yn, ct_language_id, alt_text ";
            if (filename.length() > 0) {
               query += ", ct_banner_sizes_id, banner_file";
            }
            query += ") values (?, ?, ?, ?, ?, ?";

            if (filename.length() > 0) {
               query += ", ?, ?";
            }
            query += ")";
            PreparedStatement pst = null;
            pst = conn.prepareStatement(query);
            pst = conn.prepareStatement(query);
            pst.setString(1, banner_name);
            pst.setString(2, url);
            pst.setInt(3, ct_banner_position_id);
            pst.setString(4, banner_active_yn);
            pst.setInt(5, ct_language_id);
            pst.setString(6, alt_text);
            if (filename.length() > 0) {
               pst.setInt(7, ct_banner_sizes_id);
               pst.setString(8, filename);
            }
            pst.executeUpdate();
            pst.close();
            pst = null;
         }

         // Generate completion message
         session.setAttribute("Error", "Update successful");
         response.sendRedirect((String) multi.getParameter("cancelPage"));

      }
      return MasterTemplate;
   }
}