<%@include file="/PageSecurity.jsp"%>
<%
String username = "";
if (session.getAttribute("username") != null) {
	username = (String) session.getAttribute("username");
} else {
	username = "guest";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Live Chat</title>
<script type='text/javascript' src='/dwr/engine.js'> </script>
  <script type='text/javascript' src='/dwr/interface/Chat.js'> </script>
  <script type='text/javascript' src='/dwr/util.js'> </script>
  <script type="text/javascript">
    function sendMessage() {
      Chat.addMessage("<%=username%>" + ": " + dwr.util.getValue("text"));
    }
	function initChat() {
		dwr.engine.setActiveReserveAjax(true);
		Chat.initSession();
	}
  </script>
<link rel="stylesheet" href="/pmc.css" type="text/css" />
<style type="text/css">
#chatlog{
border:1px solid black; padding:5px; height: 350px; width: 90%; overflow: auto; text-align: left;
	color:#b30000;
	text-decoration:none;
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size:11px;
}
</style>
<style>
.old { background: #FEFEFE; }
.new { background: #FFFFE0; }
</style>
</head>

<body onload="dwr.engine.setActiveReverseAjax(true)"">
<div id="content">
<ul id="chatlog" style="list-style-type:none;">
</ul>
<br />
<br />
  Your Message:<br />
  <input id="text" maxsize="255" style="width: 70%;" onkeypress="dwr.util.onReturn(event, sendMessage)"/>
  <input type="button" value="Send Message" onclick="sendMessage()"/>



</div>
</body>
</html>
<%@include file="/footer.jsp" %>