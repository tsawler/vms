<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/PageSecurity.jsp"%>
<html>
<head>
<title>Verilion Inc.</title>
<%@taglib uri="/WEB-INF/vtags.tld" prefix="v"%>
<%@ taglib uri="http://www.opensymphony.com/oscache" prefix="oscache" %>
<v:gtag />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="pics-label" content='(pics-1.1 "http://www.icra.org/ratingsv02.html" comment "ICRAonline EN v2.0" l gen true for "http://www.verilion.com" r (nz 1 vz 1 lz 1 oz 1 cz 1) "http://www.rsac.org/ratingsv01.html" l gen true for "http://www.verilion.com" r (n 0 s 0 v 0 l 0))' />
<meta name='description' content='Verilion Inc - database driven web applications, web design, software design and development, based in Canada.'/>
<meta name='keywords' content='Verilion, Content Management Systems, vMaintain, Web, CMS, Content, Management, Canada, Java, ASP.NET, JSP, servlets, hosting, database design, Java hosting, ASP, Web, Design'/>
<meta name="Rating" content="General" />
<meta name="Revisit-After" content="7 Days" />
<meta name="distribution" content="Global" />
<meta name="allow-search" content="yes" />
<meta name="audience" content="all" />
<meta name="Robots" content="ALL" />
<meta name="language" content="en-CA, English" />
<link rel="shortcut icon" href="/images/verilion.ico" type="image/x-icon" />
<link rel="alternate" type="application/xml" title="RSS" href="http://www.verilion.com/RSSFeed.doxml" />
<link href="mailto:administrators@verilion.com" rev="made" />
<link href="/css_2006/business.css" rel="stylesheet" type="text/css">
</head>

<body>
<div id="container">
	<div id="topright">
	  <p>TCSI Ltd.</p>
	  <h4>Technology. Simplicity.</h4>
	  <p>www.tcsi.ca</p>
	</div>
	<div id="top">
	  <h2>TCSI Ltd.</h2>
	  <p align="center">www.now-design.co.uk</p>
	</div>
	<div class="cl"></div>
	<div id="gallery"><img src="/images_2006/1.jpg" alt="Image of calculator" width="226" height="140"><img src="/images_2006/2.jpg" alt="Image of Laptop" width="226" height="140"><img src="/images_2006/3.jpg" alt="Image of network cable" width="221" height="140"></div>
	<div id="content">
		<div id="menu"><a href="#">home</a><a href="#">products</a><a href="#">services</a><a href="#">about us</a><a href="#">contact</a></div>
		<div id="pad">
          <p><img src="/images_2006/team.jpg" alt="Team work" width="190" height="188" style="padding: 8px; padding-right: 0px;float:right; "></p>
          <p>&nbsp;</p>
          <v:message />
		<span class="title"><%=page_detail_title%></span>
		<br />
		<div class="content"><%=page_detail_contents%>
		<br />
		</div>
		</div>
	</div>
	<div id="news">
	<oscache:cache key="<%=customer_id + ""%>" scope="session" time="1800">
	<br /><v:moduletag conn="<%=conn%>" position="1" />
	</oscache:cache>
	</div>
	<div id="footer">:: <a href="/public/jpage/1/p/Privacy/content.do" title="Read our privacy policy.">privacy</a>
:: <a href="/public/jpage/1/p/Contact/content.do" title="Our contact information.">contact</a> ::
</div>
</div>
</body>
</html>
<%@include file="/footer.jsp" %>
