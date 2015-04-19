<?xml version="1.0" encoding="iso-8859-1"?>
<%@include file="/PageSecurity.jsp"%>
<%
int menu_id = 0;
int category_id = 0;
try {
	menu_id = Integer.parseInt((String) request.getParameter("mid"));
} catch (Exception e) {
	// do nothing -- must be at top level
}
try {
	category_id = Integer.parseInt((String) request.getParameter("cid"));
} catch (Exception e) {
	// do nothing - not showing products for a category
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@taglib uri="/WEB-INF/vtags.tld" prefix="v"%>
<v:gtag />
<title>Verilion</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="pics-label" content='(pics-1.1 "http://www.icra.org/ratingsv02.html" comment "ICRAonline EN v2.0" l gen true for "http://www.verilion.com" r (nz 1 vz 1 lz 1 oz 1 cz 1) "http://www.rsac.org/ratingsv01.html" l gen true for "http://www.verilion.com" r (n 0 s 0 v 0 l 0))' />
<link rel="shortcut icon" href="/images/verilion.ico" type="image/x-icon" />
<link rel="alternate" type="application/xml" title="RSS" href="http://www.verilion.com/RSSFeed.doxml" />
<link href="mailto:administrators@verilion.com" rev="made" />
<link rel="stylesheet" href="/css/vmaintain.css" type="text/css"  />
<script type="text/javascript">
//<![CDATA[
<!--
function reloadPage(init) {
if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
document.pgW=innerWidth; document.pgH=innerHeight; onresize=reloadPage; }}
else if (innerWidth!=document.pgW || innerHeight!=document.pgH) location.reload();
}
reloadPage(true);
//-->
//]]>
</script>
</head>

<body>

<table id="layouttable">
  <tr>
    <td colspan="3"><img src="/images/vlogo.gif" width="268" height="80" alt="Verilion. Technology. Simplicity." title="It's pronounced verr-ILL-ee-yun" /></td>
  </tr>
  <tr>
    <td colspan="3" style="padding-bottom: 8px; padding-top: 8px; border-bottom: 1px solid silver; border-top: 1px solid silver;"><v:mainmenutag conn="<%=conn%>" /></td>
  </tr>
  <tr>
    <td style="width: 150px; padding-right: 15px;" valign="top"><br /><v:moduletag conn="<%=conn%>" position="1" /></td>
    <td valign="top" style="padding-left: 15px; border-left: 1px solid silver;">
		<v:message />
		<span class="title"><%=page_detail_title%></span>
		<br />
		<div class="content"><v:storeproducttag conn="<%=conn%>"/>
		<br />
		</div>
	</td>
    <td style="width: 150px;" valign="top"><v:storemenutag mid="<%=category_id%>" conn="<%=conn%>"/><br /><v:shoppingcart /></td>
  </tr>
  <tr>
  	<td colspan="3" style="border-top: 1px solid silver;">
	<table border="0" style="width: 100%;" cellspacing="0" cellpadding="0"><tr>
  	<td style="text-align: left;"><span class="content">:: <a style="color: #999; text-decoration: none;" href="#" title="This site is powered by vMaintain">powered by vMaintain</a> ::</span></td>
    <td colspan="2" style="text-align: right;">
	<span class="content">
:: <a href="/public/jpage/1/p/Privacy/content.do" title="Read our privacy policy.">privacy</a>
:: <a href="/public/jpage/1/p/Contact/content.do" title="Our contact information.">contact</a> ::
</span>
	</td>
	</tr>
	</table>
	</td>
  </tr>
</table>
</body>
</html>
<%@include file="/footer.jsp" %>