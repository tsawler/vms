//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-01-17
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessMailform.java,v $
//Revision 1.1.2.1.10.1.4.1  2007/04/04 16:53:15  tcs
//Added method for  multipart requests
//
//Revision 1.1.2.1.10.1  2006/06/11 22:39:16  tcs
//Code cleanup
//
//Revision 1.1.2.1  2005/01/17 04:30:11  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.verilion.database.SingletonObjects;
import com.verilion.display.HTMLTemplateDb;
import com.verilion.object.mail.SendMessage;

/**
 * Email a submitted form somewhere
 * 
 * <P>
 * January 17 2005
 * <P>
 * 
 * @author tsawler
 * 
 */
public class ProcessMailform extends ProcessPage {

   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception {

      String myMessage = "";
      Enumeration parmNames = request.getParameterNames();

      // get the basic information
      String to = (String) request.getParameter("to");
      String subject = (String) request.getParameter("subject");
      String intro = (String) request.getParameter("intro");

      // start buidling our message
      myMessage = intro + ":\n\n";

      try {
         // get all parameter names and values, and add them to the message
         while (parmNames.hasMoreElements()) {
            String myName = (String) parmNames.nextElement();
            if ((myName.equalsIgnoreCase("send information")
                  || (myName.equalsIgnoreCase("intro"))
                  || (myName.equalsIgnoreCase("subject")) || (myName
                  .equalsIgnoreCase("to")))) {
               // do nothing
            } else {
               myMessage = myMessage
                     + myName
                     + ": "
                     + (String) request.getParameter(myName)
                     + "\n";
            }
         }

         // send the email
         SendMessage.setTo(to);
         SendMessage.setFrom(SingletonObjects.getInstance().getAdmin_email());
         SendMessage.setSubject(subject);
         SendMessage.setMessage(myMessage);
         try {
            SendMessage.SendEmail();
         } catch (Exception e) {
            e.printStackTrace();
         }

         // Get the session and set success message
         session.setAttribute("Error", "Request successfully submitted");

         if (request.getParameter("fpage") != null) {
            response.sendRedirect((String) request.getParameter("fpage")
                  + "?msg=Request successfully submitted");
         } else {
            response.sendRedirect("http://"
                  + SingletonObjects.getInstance().getHost_name());
         }

      } catch (Exception e) {

      }
      return MasterTemplate;
   }

   @SuppressWarnings("unchecked")
   @Override
   public HTMLTemplateDb ProcessFormMultipart(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm,
         MultipartRequest multi) throws Exception {

      String filename = "";
      //String type = "";
      String thePath = SingletonObjects.getInstance().getSystem_path().toString() + "tmp/";
      //File theFile = null;
      String from = "";
      String to = "";
      String subject = "Photo submission";
      String name = "";
      String myMessage = "";
      
      Enumeration files = multi.getFileNames();
      while (files.hasMoreElements()) {
         String theFileName = (String) files.nextElement();
         filename = multi.getFilesystemName(theFileName);
         //type = multi.getContentType(theFileName);
      }
      to = multi.getParameter("to");
      from = multi.getParameter("from");
      name = multi.getParameter("name");
      myMessage = "There is a new photo submission.\n"
         + "name: "
         + name
         + "\n"
         + "email: " 
         + from;
      
      SendMessage.setTo(to);
      SendMessage.setFrom(SingletonObjects.getInstance().getAdmin_email());
      SendMessage.setSubject(subject);
      SendMessage.setMessage(myMessage);
      try {
      SendMessage.SendEmailWithAttachment(thePath, filename);
      } catch (Exception e) {
         e.printStackTrace();
      }
      session.setAttribute("Error", "Message sent");
      hm.put("PRESERVEMSG", "true");
      if (request.getParameter("fpage") != null) {
         response.sendRedirect((String) request.getParameter("fpage")
               + "?msg=Request successfully submitted");
      } else {
         response.sendRedirect("http://"
               + SingletonObjects.getInstance().getHost_name());
      }
      
      return super.ProcessFormMultipart(
            request,
            response,
            session,
            conn,
            MasterTemplate,
            hm,
            multi);
   }

}