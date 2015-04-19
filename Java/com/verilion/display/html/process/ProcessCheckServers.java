//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-02-25
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessCheckServers.java,v $
//Revision 1.1.2.2  2005/02/25 14:16:01  tcs
//Added generated date
//
//Revision 1.1.2.1  2005/02/25 13:23:15  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Servers;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.utility.TestSocket;

/**
 * Show server statuses
 * 
 * February 25 2005
 * 
 * @author tsawler
 *  
 */
public class ProcessCheckServers extends ProcessPage {

   /**
    * Build html table showing status of servers in servers table in db
    * 
    * @param request
    * @param response
    * @param session
    * @throws Exception
    */
   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      XDisconnectedRowSet crs = new XDisconnectedRowSet();

      try {
         Date today = new Date();
         Servers myServers = new Servers();
         myServers.setConn(conn);
         crs = myServers.getAllActiveServers();
         MasterTemplate.getRecordSet("<!--Record-->", "<!--/Record-->");
         while (crs.next()) {
            TestSocket ts = new TestSocket();
            boolean up = true;
            try {
               up = ts.CheckSocket(crs.getString("server_name"), crs
                     .getInt("port"));
            } catch (Exception e) {
               up = false;
            }
            if (up) {
               MasterTemplate.searchReplaceRecordSet(
                     "<!--Status-->",
                     "OK - verified");
            } else {
               MasterTemplate.searchReplaceRecordSet(
                     "<!--Status-->",
                     "<span class=\"caution\">Maintenance</span>");
            }
            MasterTemplate.searchReplaceRecordSet("<!--Name-->", crs
                  .getString("server_name"));
            MasterTemplate.searchReplaceRecordSet("<!--Desc-->", crs
                  .getString("server_description"));
            MasterTemplate.insertRecordSet();
         }
         MasterTemplate.replaceRecordSet();
         // put date on screen
         MasterTemplate.searchReplace("$VDATE$", today.toString());

      } catch (Exception e) {
         e.printStackTrace();
      }

      return MasterTemplate;
   }

}