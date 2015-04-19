//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-22
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessPerformAction.java,v $
//Revision 1.12.2.1.4.2.6.1.2.2  2007/03/20 18:24:20  tcs
//Cleaned up imports
//
//Revision 1.12.2.1.4.2.6.1.2.1  2007/01/23 19:28:35  tcs
//Added support for multipart requests
//
//Revision 1.12.2.1.4.2.6.1  2006/12/23 15:32:09  tcs
//Add parameterization for type safety
//
//Revision 1.12.2.1.4.2  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.12.2.1.4.1  2005/08/15 16:59:47  tcs
//Added support for n number of actions (and implemented 5, delete a single record)
//
//Revision 1.12.2.1  2005/04/25 17:39:24  tcs
//Changes for jsp templating
//
//Revision 1.12  2004/11/03 14:19:36  tcs
//Updated javadocs
//
//Revision 1.11  2004/11/03 13:41:28  tcs
//Improved javadocs
//
//Revision 1.10  2004/11/03 13:14:35  tcs
//Completed generalization of publish/unpublish/delete routines
//
//Revision 1.9  2004/11/02 18:04:46  tcs
//Added info to javadocs
//
//Revision 1.8  2004/11/02 17:54:57  tcs
//Imrpoved javadocs
//
//Revision 1.7  2004/11/02 17:46:31  tcs
//Improved implementation of reflection
//
//Revision 1.6  2004/11/02 15:24:32  tcs
//Changes for new ClassObjects support
//
//Revision 1.5  2004/10/27 18:06:31  tcs
//Removed debugging info
//
//Revision 1.4  2004/10/27 15:33:34  tcs
//Changes due to workflow
//
//Revision 1.3  2004/10/27 11:48:54  tcs
//Changes due to refactoring
//
//Revision 1.2  2004/10/26 17:07:23  tcs
//Changed to conform to com.verilion.database.DatabaseInterface interface
//
//Revision 1.1  2004/10/22 17:17:17  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.ClassObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;

/**
 * <p>Generic class for performing an action (publish, unpublish, delete, etc.)
 * from a menu choice. This class expects the following values to be passed as part of the http post:
 * <ul>
 * <li>action = 1 (publish), 2 (unpublish), 3 (delete), 4 (cancel)</li>
 * <li>action_n = checked box for each value in the row of
 * the form being displayed </li>
 * <li>item_id_n = the primary key corresponding to action_n above</li>
 * <li>cancelPage = the page to go back to after processing is finished</a>
 * </ul>
 * <p>
 * This class uses reflection to look up the java object that should
 * be used to perform changes on, by name. The object name/class are
 * stored in the table class_objects, and are referenced through
 * the javabean ClassObjects(). The URL used to invoke this <strong>
 * must</strong> pass class/value. This will be stored as a name/value
 * pair in the hashmap, and used to look up the class to invoke.
 * <p>
 * October 22, 2004
 * </p>
 * 
 * @author tsawler
 * @see com.verilion.database.DatabaseInterface
 *  
 */
public class ProcessPerformAction extends ProcessPage {

   /*
    * (non-Javadoc)
    * 
    * @see com.verilion.display.html.ProcessPage#ProcessForm(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse,
    *      javax.servlet.http.HttpSession, java.sql.Connection,
    *      com.verilion.display.HTMLTemplateDb, java.util.HashMap)
    */
   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      int pageCount = 0;
      int action = 0;
      String temp_id = "";
      String className = "";

      try {
         // get our parameters
         action = Integer.parseInt((String) request.getParameter("action"));
         
         // now perform the appropriate action on the selected rows
         // 1 = publish
         // 2 = unpublish
         // 3 = delete
         // 4 = cancel

         if (action < 4) {
            // get our class name
            className = "";
            
            // find which object matches the supplied class
            ClassObjects myClassObject = new ClassObjects();
            myClassObject.setConn(conn);
            myClassObject.setClass_object((String) hm.get("class"));
            className = myClassObject.getClassObjectClassByName();
            // create object and set connection

            Class<?> c = Class.forName(className);
            Object instance = c.newInstance();
            Method m = c.getMethod("setConn", new Class[] { Connection.class });
            m.invoke(instance, new Object[] { conn });

            switch (action) {
            case 1:
               // want to publish all selected rows
               pageCount = Integer.parseInt((String) request.getParameter("count"));
               for (int i = 1; i <= pageCount; i++) {
                  if (request.getParameter("action_" + i) != null) {
                     // set the id
                     temp_id = (String) request.getParameter("item_id_" + i);
                     Method myMethod = c.getMethod(
                           (String) "setPrimaryKey",
                           new Class[] { String.class });
                     myMethod.invoke(instance, new Object[] { temp_id });

                     // do the change
                     myMethod = c.getMethod(
                           "changeActiveStatus",
                           new Class[] { String.class });
                     myMethod.invoke(instance, new Object[] { "y" });
                  }
               }
               break;

            case 2:
               // want to unpublish all selected rows
               pageCount = Integer.parseInt((String) request.getParameter("count"));
               for (int i = 1; i <= pageCount; i++) {
                  if (request.getParameter("action_" + i) != null) {
                     // set the id
                     temp_id = (String) request.getParameter("item_id_" + i);
                     Method myMethod = c.getMethod(
                           "setPrimaryKey",
                           new Class[] { String.class });
                     myMethod.invoke(instance, new Object[] { temp_id });

                     // do the change
                     myMethod = c.getMethod(
                           "changeActiveStatus",
                           new Class[] { String.class });
                     myMethod.invoke(instance, new Object[] { "n" });
                  }
               }
               break;

            case 3:
               // want to delete all selected rows
               pageCount = Integer.parseInt((String) request.getParameter("count"));
               for (int i = 1; i <= pageCount; i++) {
                  if (request.getParameter("action_" + i) != null) {

                     if (request.getParameter("action_" + i) != null) {
                        // set the id
                        temp_id = (String) request.getParameter("item_id_" + i);
                        Method myMethod = c.getMethod(
                              "setPrimaryKey",
                              new Class[] { String.class });
                        myMethod.invoke(instance, new Object[] { temp_id });

                        // delete the record
                        myMethod = c.getMethod(
                              "deleteRecord",
                              new Class[] { });
                        myMethod.invoke(instance, new Object[] { });
                     }
                  }
               }
               break;
            }
         }

         if (action == 4) {
            session.setAttribute("Error", "Cancelled.");
         }
         
         if (action > 4) {

            className = "";
            
            // find which object matches the supplied class
            ClassObjects myClassObject = new ClassObjects();
            myClassObject.setConn(conn);
            myClassObject.setClass_object((String) hm.get("class"));
            className = myClassObject.getClassObjectClassByName();
            // create object and set connection
            Class<?> c = Class.forName(className);
            Object instance = c.newInstance();
            Method m = c.getMethod("setConn", new Class[] { Connection.class });
            m.invoke(instance, new Object[] { conn });
            
            switch (action) {
            
            case 5:
               // deleting a single record from some class
               temp_id = (String) request.getParameter("id");
               Method myMethod = c.getMethod(
                     "setPrimaryKey",
                     new Class[] { String.class });
               myMethod.invoke(instance, new Object[] { temp_id });

               // delete the record
               myMethod = c.getMethod(
                     "deleteRecord",
                     new Class[] { });
               myMethod.invoke(instance, new Object[] { });
               break;
            }
         }

         response.sendRedirect((String) request.getParameter("cancelPage"));

      } catch (Exception e) {
         e.printStackTrace();
      }
      return MasterTemplate;
   }
   
   public HTMLTemplateDb ProcessFormMultipart(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm,
         MultipartRequest multi) throws Exception {

      int pageCount = 0;
      int action = 0;
      String temp_id = "";
      String className = "";

      try {
//         MultipartRequest multi = new MultipartRequest(request,
//               SingletonObjects.getInstance().getSystem_path() + "tmp/", 50 * 1024);
         // get our parameters
         action = Integer.parseInt((String) multi.getParameter("action"));
         
         // now perform the appropriate action on the selected rows
         // 1 = publish
         // 2 = unpublish
         // 3 = delete
         // 4 = cancel

         if (action < 4) {
            // get our class name
            className = "";
            
            // find which object matches the supplied class
            ClassObjects myClassObject = new ClassObjects();
            myClassObject.setConn(conn);
            myClassObject.setClass_object((String) hm.get("class"));
            className = myClassObject.getClassObjectClassByName();
            // create object and set connection

            Class<?> c = Class.forName(className);
            Object instance = c.newInstance();
            Method m = c.getMethod("setConn", new Class[] { Connection.class });
            m.invoke(instance, new Object[] { conn });

            switch (action) {
            case 1:
               // want to publish all selected rows
               pageCount = Integer.parseInt((String) multi.getParameter("count"));
               for (int i = 1; i <= pageCount; i++) {
                  if (multi.getParameter("action_" + i) != null) {
                     // set the id
                     temp_id = (String) multi.getParameter("item_id_" + i);
                     Method myMethod = c.getMethod(
                           (String) "setPrimaryKey",
                           new Class[] { String.class });
                     myMethod.invoke(instance, new Object[] { temp_id });

                     // do the change
                     myMethod = c.getMethod(
                           "changeActiveStatus",
                           new Class[] { String.class });
                     myMethod.invoke(instance, new Object[] { "y" });
                  }
               }
               break;

            case 2:
               // want to unpublish all selected rows
               pageCount = Integer.parseInt((String) multi.getParameter("count"));
               for (int i = 1; i <= pageCount; i++) {
                  if (multi.getParameter("action_" + i) != null) {
                     // set the id
                     temp_id = (String) multi.getParameter("item_id_" + i);
                     Method myMethod = c.getMethod(
                           "setPrimaryKey",
                           new Class[] { String.class });
                     myMethod.invoke(instance, new Object[] { temp_id });

                     // do the change
                     myMethod = c.getMethod(
                           "changeActiveStatus",
                           new Class[] { String.class });
                     myMethod.invoke(instance, new Object[] { "n" });
                  }
               }
               break;

            case 3:
               // want to delete all selected rows
               pageCount = Integer.parseInt((String) multi.getParameter("count"));
               for (int i = 1; i <= pageCount; i++) {
                  if (multi.getParameter("action_" + i) != null) {

                     if (multi.getParameter("action_" + i) != null) {
                        // set the id
                        temp_id = (String) multi.getParameter("item_id_" + i);
                        Method myMethod = c.getMethod(
                              "setPrimaryKey",
                              new Class[] { String.class });
                        myMethod.invoke(instance, new Object[] { temp_id });

                        // delete the record
                        myMethod = c.getMethod(
                              "deleteRecord",
                              new Class[] { });
                        myMethod.invoke(instance, new Object[] { });
                     }
                  }
               }
               break;
            }
         }

         if (action == 4) {
            session.setAttribute("Error", "Cancelled.");
         }
         
         if (action > 4) {

            className = "";
            
            // find which object matches the supplied class
            ClassObjects myClassObject = new ClassObjects();
            myClassObject.setConn(conn);
            myClassObject.setClass_object((String) hm.get("class"));
            className = myClassObject.getClassObjectClassByName();
            // create object and set connection
            Class<?> c = Class.forName(className);
            Object instance = c.newInstance();
            Method m = c.getMethod("setConn", new Class[] { Connection.class });
            m.invoke(instance, new Object[] { conn });
            
            switch (action) {
            
            case 5:
               // deleting a single record from some class
               temp_id = (String) multi.getParameter("id");
               Method myMethod = c.getMethod(
                     "setPrimaryKey",
                     new Class[] { String.class });
               myMethod.invoke(instance, new Object[] { temp_id });

               // delete the record
               myMethod = c.getMethod(
                     "deleteRecord",
                     new Class[] { });
               myMethod.invoke(instance, new Object[] { });
               break;
            }
         }

         response.sendRedirect((String) multi.getParameter("cancelPage"));

      } catch (Exception e) {
         e.printStackTrace();
      }
      return MasterTemplate;
   }

}