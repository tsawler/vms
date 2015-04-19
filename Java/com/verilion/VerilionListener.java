//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-24
//Revisions
//------------------------------------------------------------------------------
//$Log: VerilionListener.java,v $
//Revision 1.13.2.6.2.1.2.1.2.3.2.1.4.3.2.5  2009/07/22 16:29:36  tcs
//*** empty log message ***
//
//Revision 1.13.2.6.2.1.2.1.2.3.2.1.4.3.2.4  2008/09/01 21:11:43  tcs
//*** empty log message ***
//
//Revision 1.13.2.6.2.1.2.1.2.3.2.1.4.3.2.3  2007/06/11 15:35:01  tcs
//Cleaned up way messagehandler is instantiated/destroyed
//
//Revision 1.13.2.6.2.1.2.1.2.3.2.1.4.3.2.2  2007/04/04 16:52:51  tcs
//Restored commented out lines
//
//Revision 1.13.2.6.2.1.2.1.2.3.2.1.4.3.2.1  2007/03/30 17:23:06  tcs
//Commented out some stuff that seemed to conflict with tomcat 6
//
//Revision 1.13.2.6.2.1.2.1.2.3.2.1.4.3  2007/03/14 00:19:43  tcs
//*** empty log message ***
//
//Revision 1.13.2.6.2.1.2.1.2.3.2.1.4.2  2007/01/22 19:25:58  tcs
//Removed refs to jcaptcha (went with diff solution)
//
//Revision 1.13.2.6.2.1.2.1.2.3.2.1.4.1  2007/01/16 16:30:36  tcs
//Added instantiation of Captcha singleton
//
//Revision 1.13.2.6.2.1.2.1.2.3.2.1  2005/12/03 03:21:29  tcs
//Added var
//
//Revision 1.13.2.6.2.1.2.1.2.3  2005/10/12 02:11:29  tcs
//Explicitly stop message handler on exit
//
//Revision 1.13.2.6.2.1.2.1.2.2  2005/10/12 00:05:03  tcs
//Added message handler support
//
//Revision 1.13.2.6.2.1.2.1.2.1  2005/09/08 18:15:46  tcs
//Changes to way ds is retrieved, and some singleton stuff
//
//Revision 1.13.2.6.2.1.2.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.13.2.6.2.1  2005/07/12 17:00:14  tcs
//Added call to SetUpVars
//
//Revision 1.13.2.6  2005/04/27 18:11:15  tcs
//Added systempath var
//
//Revision 1.13.2.5  2005/02/24 13:15:33  tcs
//Added cache timeout
//
//Revision 1.13.2.4  2005/02/24 13:10:57  tcs
//Added setting of hashmap of classobjects to singleton
//
//Revision 1.13.2.3  2004/12/14 13:35:07  tcs
//Removed email notification of startup
//
//Revision 1.13.2.2  2004/12/10 18:20:02  tcs
//Added email notification of process startup
//
//Revision 1.13.2.1  2004/12/10 18:00:50  tcs
//Added session_timeout attribute support
//
//Revision 1.13  2004/10/20 17:26:10  tcs
//added support for admin_email
//
//Revision 1.12  2004/09/17 18:28:34  tcs
//Added memory threshold attribute
//
//Revision 1.11  2004/07/12 18:40:10  tcs
//Added column for site description
//
//Revision 1.10  2004/06/28 16:59:49  tcs
//Corrected typo in setUse_Sef_yn for singleton
//
//Revision 1.9  2004/06/27 02:39:35  tcs
//Removed dependence on context variables; changes for new listener logic
//
//Revision 1.8  2004/06/25 16:27:41  tcs
//Removed use of result sets
//
//Revision 1.7  2004/06/24 18:14:49  tcs
//Mods for Singleton class for ds
//
//Revision 1.1 2004/05/23 04:52:45 tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.ClassObjects;
import com.verilion.database.DbBean;
import com.verilion.database.GenericTable;
import com.verilion.database.SingletonObjects;
import com.verilion.database.SystemPreferences;
import com.verilion.scheduler.MessageHandlerScheduler;

/**
 * A custom listener for context events.
 */
public class VerilionListener implements ServletContextListener,
      ServletContextAttributeListener, HttpSessionAttributeListener,
      HttpSessionListener {

   private HashMap hmClassMap = new HashMap();
   private String hostName = "";

   @SuppressWarnings("unused")
   public void contextInitialized(ServletContextEvent event) {

      Connection conn = null;
      SystemPreferences myPrefs = new SystemPreferences();

      try {
         hostName = SetUpVars.InitVars();
      } catch (Exception e2) {
         e2.printStackTrace();
      }

      System.out
            .println("========================================================");
      System.out.println("=  Starting vMaintain for " + hostName);
      System.out
            .println("========================================================");

      try {
         if ((SingletonObjects.getInstance().getHost_name() == null)
               || (SingletonObjects.getInstance().getHost_name().equalsIgnoreCase(""))) {
            // get a db connection (initializes connection pool
            // and our Singleton object)
            try {
               conn = DbBean.getDbConnection();
            } catch (Exception e) {
               System.out.println("Error initing db: " + e.toString());
               e.printStackTrace();
               conn = null;
            }
            if (conn != null) {
               System.out.println("Getting database connection "
                     + conn.toString());

               // start message handler
               MessageHandlerScheduler myScheduler = new MessageHandlerScheduler();
               System.out.println("Starting message handler...");
               System.out.println("MessageHandler: " + myScheduler.toString());
               
               // start chat clear handler
               /*
               ClearChatScheduler myChatScheduler = new ClearChatScheduler();
               System.out.println("Starting Chat watcher...");
               System.out.println("ChatWathcer: " + myChatScheduler.toString());
				*/
               
               // get system preferences
               myPrefs.setConn(conn);
               myPrefs.getSystemPreferencesByProperties();

               // get hashmap of class objects
               ClassObjects myClassObjects = new ClassObjects();
               myClassObjects.setConn(conn);
               hmClassMap = myClassObjects.getAllClassObjects();

               // init singleton values
               SingletonObjects.getInstance().setMyScheduler(myScheduler);
               SingletonObjects.getInstance().setUse_sef_yn(
                     myPrefs.getUse_sef_yn());
               SingletonObjects.getInstance().setInsecure_port(
                     myPrefs.getInsecure_port());
               SingletonObjects.getInstance().setSecure_port(
                     myPrefs.getSecure_port());
               SingletonObjects.getInstance()
                     .setMailhost(myPrefs.getMailhost());
               SingletonObjects.getInstance().setUpload_path(
                     myPrefs.getUpload_path());
               SingletonObjects.getInstance()
                     .setDatabase(myPrefs.getDatabase());
               SingletonObjects.getInstance().setHost_name(hostName);
               SingletonObjects.getInstance().setHomepage_page_id(
                     myPrefs.getHomepage_page_id());
               SingletonObjects.getInstance().setSystem_online_yn(
                     myPrefs.getSystem_online_yn());
               SingletonObjects.getInstance().setSite_description(
                     myPrefs.getSite_descripton());
               SingletonObjects.getInstance().setMemory_threshold(
                     myPrefs.getMemory_threshold());
               SingletonObjects.getInstance().setAdmin_email(
                     myPrefs.getAdmin_email());
               SingletonObjects.getInstance().setSession_timeout(
                     myPrefs.getSession_timeout());
               SingletonObjects.getInstance().setCacheTimeout(
                     myPrefs.getCache_timeout());
               SingletonObjects.getInstance().setSystem_path(
                     myPrefs.getSystem_path());
               SingletonObjects.getInstance().setHmClassMap(hmClassMap);
               SingletonObjects.getInstance().setStore_max_subcategories(
                     myPrefs.getStore_max_subcategories());
               SingletonObjects.getInstance()
                     .setHomepage(myPrefs.getHomepage());
            }
         } else {
            System.out.println("Application already initialized");
         }
         System.out.println("= Application init successful for " + hostName);
         System.out
               .println("========================================================");
         DbBean.closeDbConnection(conn);
         conn = null;

      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Application initialization failed for "
               + hostName
               + "!");
      } finally {
         if (conn != null) {
            try {
               DbBean.closeDbConnection(conn);
            } catch (SQLException e1) {
               ;
            } catch (Exception e) {
               ;
            }

         }
      }
   }

   public void contextDestroyed(ServletContextEvent event) {
      SingletonObjects.getInstance().setUse_sef_yn("");
      SingletonObjects.getInstance().setInsecure_port("");
      SingletonObjects.getInstance().setSecure_port("");
      SingletonObjects.getInstance().setMailhost("");
      SingletonObjects.getInstance().setUpload_path("");
      SingletonObjects.getInstance().setDatabase("");
      SingletonObjects.getInstance().setHost_name("");
      SingletonObjects.getInstance().setHomepage_page_id(0);
      SingletonObjects.getInstance().setSystem_online_yn("");
      SingletonObjects.getInstance().setSite_description("");
      SingletonObjects.getInstance().setMemory_threshold(0);
      SingletonObjects.getInstance().setAdmin_email("");
      SingletonObjects.getInstance().setSession_timeout("");
      SingletonObjects.getInstance().setCacheTimeout(0);
      SingletonObjects.getInstance().setSystem_path("");
      SingletonObjects.getInstance().setHmClassMap(null);
      MessageHandlerScheduler myScheduler = SingletonObjects.getInstance()
            .getMyScheduler();
      myScheduler.Stop();
      System.out.println("Application shut down");
   }

   public void attributeAdded(ServletContextAttributeEvent event) {
      System.out.println("Attribute added");

   }

   public void attributeRemoved(ServletContextAttributeEvent event) {
      System.out.println("Attribute removed");

   }

   public void attributeReplaced(ServletContextAttributeEvent event) {
      System.out.println("Attribute replaced");
   }

   public void attributeAdded(HttpSessionBindingEvent sessionBindingEvent) {

   }

   public void attributeRemoved(HttpSessionBindingEvent sessionBindingEvent) {

   }

   public void attributeReplaced(HttpSessionBindingEvent sessionBindingEvent) {

   }

   public void sessionDestroyed(HttpSessionEvent sessionEvent) {

      // Get the session that was invalidated
      HttpSession session = sessionEvent.getSession();
      Connection c = null;
      try {
         c = DbBean.getDbConnection();
      } catch (Exception e) {
         e.printStackTrace();
      }
      GenericTable myTable = new GenericTable("logged_in_users");
      myTable.setConn(c);
      myTable.setSSelectWhat("*");
      myTable.setSCustomWhere(" and session_id = '" + session.getId() + "'");
      myTable.setSCustomLimit("limit 1");
      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      try {
         drs = myTable.getAllRecordsDisconnected();
         while (drs.next()) {
            GenericTable myUpdateTable = new GenericTable();
            myUpdateTable.setConn(c);
            myUpdateTable.setSTable("logged_in_users");
            myUpdateTable.setSPrimaryKey("logged_in_user_id");
            myUpdateTable.setIPrimaryKey(drs.getInt("logged_in_user_id"));
            try {
               myUpdateTable.deleteRecord();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      } catch (SQLException e1) {
         e1.printStackTrace();
      } catch (Exception e1) {
         e1.printStackTrace();
      }

      try {
         DbBean.closeDbConnection(c);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void sessionCreated(HttpSessionEvent sessionEvent) {

      HttpSession session = sessionEvent.getSession();
      GenericTable myTable = new GenericTable("logged_in_users");
      Connection c = null;
      try {
         c = DbBean.getDbConnection();
      } catch (Exception e) {
         e.printStackTrace();
      }
      myTable.setConn(c);
      myTable.setSTable("logged_in_users");
      myTable.addUpdateFieldNameValuePair(
            "session_id",
            session.getId(),
            "string");
      try {
         myTable.insertRecord();
      } catch (Exception e) {

      }

      try {
         DbBean.closeDbConnection(c);
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

}