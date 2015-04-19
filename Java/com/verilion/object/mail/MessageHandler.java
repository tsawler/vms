//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-10-11
//Revisions
//------------------------------------------------------------------------------
//$Log: MessageHandler.java,v $
//Revision 1.1.2.1  2005/10/12 00:06:13  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.mail;

import java.sql.Connection;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DbBean;
import com.verilion.database.MessageQueue;
import com.verilion.database.SingletonObjects;

/**
 * Sends Internet email messages.
 * <P>
 * Nov 21, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class MessageHandler {

   private static String To = "";
   private static String From = "";
   private static String MailHost = "localhost";
   private static String Subject = "";
   private static String message = "";

   /**
    * Constructor
    */
   public MessageHandler() {

   }

   /**
    * Sends an Internet email message.
    */
   public void SendMessagesInQueue() throws Exception {

      Connection conn = DbBean.getDbConnection();
      MessageQueue myHandler = new MessageQueue();
      myHandler.setConn(conn);
      XDisconnectedRowSet drs = new XDisconnectedRowSet();
      drs = myHandler.GetAllUnsentMessages();

      while (drs.next()) {
         To = drs.getString("message_queue_to");
         From = drs.getString("message_queue_from");
         Subject = drs.getString("message_queue_subject");
         message = drs.getString("message_queue_message");
         MailHost = SingletonObjects.getInstance().getMailhost();
         try {
            SendMessage.setTo(To);
            SendMessage.setFrom(From);
            SendMessage.setMailHost(MailHost);
            SendMessage.setMessage(message);
            SendMessage.setSubject(Subject);
            SendMessage.SendHtmlEmail();
         } catch (Exception e) {
            System.out.println("Error sending email : " + e.toString());
         }
         MessageQueue myQueue = new MessageQueue();
         myQueue.setConn(conn);
         myQueue.setMessage_queue_id(drs.getInt("message_queue_id"));
         myQueue.MarkMessageSent();
      }
      DbBean.closeDbConnection(conn);
   }

}