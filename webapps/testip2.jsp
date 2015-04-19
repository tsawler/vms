<%@page language="java" import="com.verilion.database.*, java.sql.*,java.util.*,java.text.DecimalFormat,
org.sourceforge.jxutil.sql.XDisconnectedRowSet, com.verilion.display.HTMLTemplateDb,
java.lang.reflect.Method, com.verilion.database.SingletonObjects,javax.xml.parsers.DocumentBuilderFactory, javax.xml.parsers.ParserConfigurationException, org.w3c.dom.*, org.xml.sax.SAXException, org.apache.commons.httpclient.HttpClient, org.apache.commons.httpclient.HttpException, org.apache.commons.httpclient.HttpMethod, org.apache.commons.httpclient.NameValuePair, org.apache.commons.httpclient.methods.GetMethod,grn.xml.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>GeoLocation test for GrandRoundsNow</title>
</head>

<body>

<%

HttpClient httpclient = new HttpClient();

// Prepare a request object
HttpMethod method = new GetMethod("http://iplocationtools.com/ip_query.php?ip=" + request.getRemoteHost()); 

httpclient.executeMethod(method);
String respString = method.getResponseBodyAsString();	
method.releaseConnection();

if (respString.indexOf("New Brunswick") < 0) {
	%>
	You <strong>are not</strong> located in NB<br />
	<%
} else {
	%>
	You <strong>are</strong> located in NB<br />
	<%
}
%>

<br />
<strong>What I can tell about your IP and location:</strong><br />

<%
GeoLocationBean myBean = new GeoLocationBean(respString);
myBean.SAXParserBeanHelper();
%>

IP: <%=myBean.getIp()%><br />
City: <%=myBean.getCity()%><br />
Province/State: <%=myBean.getRegionName()%><br />
Country: <%=myBean.getCountryName()%><br />
Zip/Postal: <%=myBean.getZipPostalCode()%><br />
Latitude: <%=myBean.getLatitude()%><br />
Longitude: <%=myBean.getLongitude()%><br />
</body>
</html>
