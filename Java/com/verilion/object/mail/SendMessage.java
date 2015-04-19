//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: SendMessage.java,v $
//Revision 1.3.2.1.6.1.4.1  2006/06/11 22:37:40  tcs
//Changed default smtp server
//
//Revision 1.3.2.1.6.1  2005/10/12 00:06:47  tcs
//Explicitly set date for outbound messages
//
//Revision 1.3.2.1  2005/04/20 17:19:41  tcs
//Changed default mailserver. This should go into properties.
//
//Revision 1.3  2004/08/03 18:57:27  tcs
//Added method to send html email
//
//Revision 1.2  2004/08/03 15:30:34  tcs
//Added method to send email with attachment
//
//Revision 1.1  2004/05/23 04:52:52  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.mail;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.verilion.database.SingletonObjects;
import com.verilion.object.Errors;

/**
 * Sends Internet email messages.
 * <P>
 * Nov 21, 2003
 * <P>
 * @author tsawler
 * 
 */
public class SendMessage {

	private static String To = "";
	private static String From = "";
	private static String MailHost = "localhost";
	private static String Subject = "";
	private static String message = "";
	
	/**
	 * Constructor
	 */
	public SendMessage() {
		
	}

	/**
	 * Sends an Internet email message.
	 */
	public static void SendEmail() throws MessagingException, Exception {
		String msgText = message;
		Properties props = new Properties();
		MailHost = SingletonObjects.getInstance().getMailhost();
		props.put("mail.smtp.host", MailHost);
		props.put("mail.smtp.localhost", MailHost);
		Session MySession = Session.getDefaultInstance(props, null);
		
		try {
			Message msg = new MimeMessage(MySession);
            Calendar cal = Calendar.getInstance();
            Date d = cal.getTime();
            msg.setSentDate(d);
			InternetAddress from = new InternetAddress(From);
			msg.setFrom(from);
			InternetAddress address = new InternetAddress(To);
			msg.setRecipient(javax.mail.Message.RecipientType.TO, address);
			msg.setSubject(Subject);
			msg.setContent(msgText, "text/plain");
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Errors.addError("sendMessage:SendEmail:MessagingException:" + mex.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("sendMessage:SendEmail:Exception:" + e.toString());
		}
	}
	
	/**
	 * Sends an html formatted Internet email message.
	 */
	public static void SendHtmlEmail() throws MessagingException, Exception {
		String msgText = message;
		Properties props = new Properties();
		MailHost = SingletonObjects.getInstance().getMailhost();
		props.put("mail.smtp.host", MailHost);
		props.put("mail.smtp.localhost", MailHost);
		Session MySession = Session.getDefaultInstance(props, null);
		
		try {
			Message msg = new MimeMessage(MySession);
            Calendar cal = Calendar.getInstance();
            Date d = cal.getTime();
            msg.setSentDate(d);
			InternetAddress from = new InternetAddress(From);
			msg.setFrom(from);
			InternetAddress address = new InternetAddress(To);
			msg.setRecipient(javax.mail.Message.RecipientType.TO, address);
			msg.setSubject(Subject);
			msg.setContent(msgText, "text/html");
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Errors.addError("sendMessage:SendEmail:MessagingException:" + mex.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Errors.addError("sendMessage:SendEmail:Exception:" + e.toString());
		}
	}
	
	/**
	 * Sends an email message with an attachment
	 * 
	 * @param pathName - path to file (includes trailing slash)
	 * @param fileName - the file name
	 * @throws MessagingException
	 * @throws Exception
	 */
	public static void SendEmailWithAttachment(String pathName, 
	        String fileName) throws MessagingException, Exception {
		String msgText = message;
		Properties props = new Properties();
		MailHost = SingletonObjects.getInstance().getMailhost();
		props.put("mail.smtp.host", MailHost);
		props.put("mail.smtp.localhost", MailHost);
		Session MySession = Session.getDefaultInstance(props, null);
		
		try {
			Message msg = new MimeMessage(MySession);
            Calendar cal = Calendar.getInstance();
            Date d = cal.getTime();
            msg.setSentDate(d);
			InternetAddress from = new InternetAddress(From);
			msg.setFrom(from);
			InternetAddress address = new InternetAddress(To);
			msg.setRecipient(javax.mail.Message.RecipientType.TO, address);
			msg.setSubject(Subject);
			
			// set the message text
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(msgText);
			Multipart multiPart = new MimeMultipart();
			
			// now do the attachment
			multiPart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			FileDataSource source = new FileDataSource(pathName + fileName);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multiPart.addBodyPart(messageBodyPart);
			
			// put the message together
			msg.setContent(multiPart);
			
			// send the message
			Transport.send(msg);
			
		} catch (MessagingException mex) {
			mex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the from.
	 */
	public static String getFrom() {
		return From;
	}

	/**
	 * @param from The from to set.
	 */
	public static void setFrom(String from) {
		From = from;
	}

	/**
	 * @return Returns the mailHost.
	 */
	public static String getMailHost() {
		return MailHost;
	}

	/**
	 * @param mailHost The mailHost to set.
	 */
	public static void setMailHost(String mailHost) {
		MailHost = mailHost;
	}

	/**
	 * @return Returns the message.
	 */
	public static String getMessage() {
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public static void setMessage(String message) {
		SendMessage.message = message;
	}

	/**
	 * @return Returns the subject.
	 */
	public static String getSubject() {
		return Subject;
	}

	/**
	 * @param subject The subject to set.
	 */
	public static void setSubject(String subject) {
		Subject = subject;
	}

	/**
	 * @return Returns the to.
	 */
	public static String getTo() {
		return To;
	}

	/**
	 * @param to The to to set.
	 */
	public static void setTo(String to) {
		To = to;
	}

}