<%@page language="java" import="java.sql.*,java.util.*,java.text.*,
javax.mail.MessagingException,
javax.mail.Session,
javax.mail.Transport,
javax.mail.internet.InternetAddress,
javax.mail.internet.MimeMessage,
javax.mail.BodyPart,
javax.mail.Message,
javax.mail.Multipart,
javax.mail.internet.MimeBodyPart,
javax.mail.internet.MimeMultipart,
javax.mail.internet.MimeMultipart,
com.verilion.object.mail.SendMessage
"%>
<%

String tabstract = "";
String poster = "";
String attached = "";
String later = "";
String title = "";
String name = "";
String phone = "";

try {

if (request.getParameter("abstract") != null) {
	tabstract = "checked";
} else {
	tabstract = "unchecked";
}

if (request.getParameter("poster") != null) {
	poster = "checked";
} else {
	poster = "unchecked";
}



try {
	String theMessage = "";
	SendMessage myMessage = new SendMessage();
	myMessage.setTo("wyerxa@bioatlantech.nb.ca");
	myMessage.setSubject("NACREW Online registration");
	myMessage.setFrom("donotreply@verilion.com");
	//myMessage.setMailHost("192.168.0.118");
	theMessage = "The following form was submitted for NACREW registration:";
	theMessage += "\n\n Presentation Abstract: " + tabstract;
	theMessage += "\nPoster abstract: " + poster;
	theMessage += "\nTitle: " + request.getParameter("title");
	theMessage += "\nAuthors and affiliation: " + request.getParameter("authors");
	theMessage += "\nName of presenter: " + request.getParameter("name");
	theMessage += "\nContact person - phone: " + request.getParameter("phone");
	theMessage += "\nEmail: " + request.getParameter("email");
	theMessage += "\n\nThe abstract:\n\n" + (String) request.getParameter("abstracttext");
	myMessage.setMessage(theMessage);
	myMessage.SendEmail();

} catch (Exception e) {

}
	

} catch (Exception e) {

}
%>

Thank you for submitting your abstract to NACREW 2009.<br>
Merci pour soumettre votre résumé pour NACREW 2009.