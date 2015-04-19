<?xml version="1.0" encoding="iso-8859-1"?>
<%@include file="/PageSecurity.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <%@taglib uri="/WEB-INF/vtags.tld" prefix="v"%>
    <%@ taglib uri="http://www.opensymphony.com/oscache" prefix="oscache" %>
    <v:gtag />
  	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>Verilion.com - Internet Professional Services</title>
    <meta
     name="Author"
     content="info@verilion.com" />
    <meta
     name="description"
     content="Verilion  is an Internet Professional Services firm (IPS) offering a range of e-business services to clients across North America." />
    <meta
     name="robots"
     content="All" />
    <meta
     name="REVISIT-AFTER"
     content="3 days" />
    <meta
     name="Copyright"
     content="Copyright 2003-2006 Verilion Inc." />
    <meta
     name="keywords"
     content="internet professional services, ips, IPS, ecommerce, ebusiness, canada, java, jsp, hosting, servlet, servlets, online commerce, portal development e-business, e-commerce, fredericton, new brunswick, frederickton" />
    <meta
     http-equiv="Content-Type"
     content="text/html; charset=iso-8859-1" />

    <style
     type="text/css">
    /*<![CDATA[*/
body {
   background-color : #b8e480;
   height : 100%;
   margin : 0;
   border : 0;
   border-collapse : collapse;
}
html {
   height : 100%;
}
table {
   border-collapse : collapse;
}
.inputbox form {
	padding: 0; 
	margin: 0; 
	float: left; 
	width: 96%;
} 
.inputbox {
	font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: normal;
	color: #333333;
	background: #E5E9F2;
	border: 1px solid;
}
td.c6 {
   font-family : Arial, Helvetica, sans-serif;
   font-size : 80%;
   padding : 0;
}
span.c5 {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 70%;
}
span.c4 {
   font-family : Arial, Helvetica, sans-serif;
   font-size : 80%;
}
td.c3 {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 70%;
   padding : 0;
}
td.c2 {
   color : #000000;
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   padding : 0;
}
.content {
   color : #000000;
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   padding : 0;
}
span.c1 {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
}
#menudiv {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : #fff;
   text-align : left;
   margin-left : 4px;
}
#menudiv td {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : #fff;
   text-align : left;
   margin-left : 4px;
}
#menudiv a {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : #fff;
   text-align : left;
   margin-left : 4px;
   text-decoration : none;
}
#menudiv a:hover {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : #fff;
   text-align : left;
   margin-left : 4px;
   text-decoration : underline;
}
#contentdiv {
    margin-right: 58px;
}
#contentdiv h3 {
    margin-bottom: 3px;
	font-family : Tahoma, Verdana, Arial, Helvetica, sans-serif;
	font-size: 150%;
	font-weight : bold;
}
a:visited {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : red;
   text-align : left;
   margin-left : 4px;
   text-decoration : underline;
}
a:link {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : red;
   text-align : left;
   margin-left : 4px;
   text-decoration : underline;
}
a:hover {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : #c20034;
   text-align : left;
   margin-left : 4px;
   text-decoration : underline;
}
#left a {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : red;
   text-align : left;
   margin-left : 4px;
   text-decoration : none;
}
#left a:visited {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : red;
   text-align : left;
   margin-left : 4px;
   text-decoration : none;
}
#left a:link {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : red;
   text-align : left;
   margin-left : 4px;
   text-decoration : none;
}
#left a:hover {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   color : #c20034;
   text-align : left;
   margin-left : 4px;
   text-decoration : underline;
}
#left .modulebox {
	border-color: #666;
	border-width: 1px;
	border-style: solid;
	border-collapse:collapse;
	padding: 0;
	width: 100%;
	margin: 0;
}
#left .newsbox {
   width : 100%;
   padding : 0;
   margin : 0;
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   font-style : normal;
   font-weight : normal;
   text-decoration : none;
   color : #4d6e71;
   border-color: #666;
   border-width: 1px;
   border-style: solid;
   border-collapse:collapse;
}
#left img.newsbox {
	/* display: table-cell; */
	margin-left: auto;
	margin-right: auto;
	width: 50%;
}
.moduletitle {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   width : 100%;
   font-style : normal;
   font-weight : bold;
   text-decoration : none;
   text-align : center;
   padding : 2px;
   background: #000;
   color: #fff;
}
.moduletext {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   font-style : normal;
   font-weight : normal;
   text-decoration : none;
   padding : 2px;
}
a.moduletext:link {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   font-style : normal;
   font-weight : normal;
   color : #000;
   text-decoration : none;
}
a.moduletext:visited {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   font-style : normal;
   font-weight : normal;
   color : #000;
   text-decoration : none;
}
a.moduletext:hover {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   text-decoration : underline;
   font-style : normal;
   color : #000;
   font-weight : normal;
}
a.moduletext:active {
   color : red;
}
a.menutext:link {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   font-style : normal;
   font-weight : normal;
   color : #000;
   text-decoration : none;
}
a.menutext:visited {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   font-style : normal;
   font-weight : normal;
   color : #000;
   text-decoration : none;
}
a.menutext:hover {
   font-family : Verdana, Arial, Helvetica, sans-serif;
   font-size : 11px;
   text-decoration : underline;
   font-style : normal;
   color : #000;
   font-weight : normal;
}
a.menutext:active {
   color : red;
}
#titlediv h1 {
   font-family : Tahoma, Verdana, Arial, Helvetica, sans-serif;
   margin : 0;
   font-size : 18pt;
   font-style : normal;
   font-weight : 800;
   color : #c30032;
   text-decoration : none;
}
img {
   display: block;
}
.caution {
	font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif; 
	font-size: 11px; 
	font-weight: bold;
	color: red;
}
ul.newslist {
	list-style-image: url('/images/arrow.png');
	/* margin-left: -17px; */
}
.s {
	display:block
}
.s * {
	display:block;
	height:1px;
	overflow:hidden;
	background:#000
}
.s1 {
	border-right:1px solid #000;
	padding-right:1px;
	margin-right:3px;
	border-left:1px solid #000;
	padding-left:1px;
	margin-left:3px;
	background:#000;
}
.s2 {
	border-right:1px solid #000;
	border-left:1px solid #000;
	padding:0px 1px;
	background:#000;
	margin:0px 1px;
}
.s3 {
	border-right:1px solid #000;
	border-left:1px solid #000;
	margin:0px 1px;
}
.s4 {
	border-right:1px solid #000;
	border-left:1px solid #000;
}
.s5 {
	border-right:1px solid #000;
	border-left:1px solid #000;
}
.s_content {
	padding:0px 5px;
	background:#000;
} 
    /*]]>*/
    </style>
<script type='text/javascript' src='/dwr/engine.js'> </script>
<script type='text/javascript' src='/dwr/interface/Validator.js'> </script>
<script type='text/javascript' src='/dwr/util.js'> </script>
<script type="text/javascript">
function showanswer(id) {
	var wheretogo = "/faqhelper.jsp?id=" + id + "&a=2";
  	Validator.getIncludedFile(wheretogo, function(data) {
    dwr.util.setValue("resultdiv", data, { escapeHtml:false });
});
}
 
function showquestions() {
	var wheretogo = "/faqhelper.jsp?id=" + dwr.util.getValue("faq_cat_id") + "&a=1";
  	Validator.getIncludedFile(wheretogo, function(data) {
    dwr.util.setValue("questiondiv", data, { escapeHtml:false });
  });
  dwr.util.setValue("resultdiv", "");
}
</script>
	<link rel="alternate" type="application/xml" title="RSS" href="http://www.verilion.com/RSSFeed.doxml" />
    <link href="mailto:administrators@verilion.com" rev="made" />
    
  </head>
  <body style="margin: 0; height: 100%;">
    <table style="border: 0; height: 100%; width: 100%; padding: 0; border-collapse: collapse;">
        <tr>
          <td
		   style="background: #ffcc68; width: 335px; margin: 0; padding: 0;"
           valign="top">
		   <div id="left">
            <table style="border: 0;  border-collapse: collapse; padding: 0; width: 335px; margin:0;">
                <tr><td colspan="2" style="margin: 0; padding: 0; background: #b8e480; margin:0;"><img src="/assets/templates/verilion20/images/vlogotop.jpg" width="335" height="96" alt="" /></td></tr>
				<tr><td colspan="2" style="padding: 0; margin:0;">
				<v:randomimage images="enter.jpg,fingerkeysred.jpg,girllaptop.jpg,homekey.jpg,notebook.jpg" heights="239,239,239,239,239" widths="335,335,335,335,335" alts="image,image,image,image,image" path="/assets/templates/verilion20/images/rotate" />
				</td></tr>
                <tr style="background: #ffcc68">
                  <td
                   colspan="2" style="width: 335px; border: 0; padding: 0; "
                   align="center">
                    <table style="border: 0; border-collapse: collapse; width: 98%; padding: 0; ">
                        <tr>
                          <td
                           valign="bottom"

                           style="width: 99%">&nbsp;</td>
                        </tr>
                        <tr>
                          <td
                           align="center"
                           valign="top"
                           style="width: 99%">
						   <div style="width: 200px">
						   <v:moduletag conn="<%=conn%>" position="1" /></div>
						   <!--
						   <div style="width: 50%; margin-left: auto; margin-right: auto;">
						   <a href="/RSSFeed.doxml"><img src="/images/rss.png" height="14" width="36" style="border: 0; display: inline;" alt="Subscribe to our RSS 2.0 Feed" /></a>
						   </div>-->
						   <span style="font-family: verdana; font-size: 10px; color: #444">&copy; 2003-2006 Verilion Inc. <br />All
                            rights reserved.<br />
							Powered by vMaintain<br />
							<a href="/public/jpage/1/p/Privacy/content.do">Privacy</a></span></td>
                        </tr>
                    </table>
                  </td>
                </tr>
            </table>
			</div>
          </td>
          <td
		   style="background: #b8e480; width: 101px"
           valign="top">
		   <div id="menudiv">
            <table
			 style="background: #000; padding: 0; width: 101px; margin-top: 50px;">
				<tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <span style="font-weight: bold; color: #fff">Menu</span>
				   </td>
                </tr>
                <tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Return to the home page" href="http://www.verilion.com/public/jpage/1/p/Home/content.do">Home</a>
				   </td>
                </tr>
                <tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="About Verilion" href="http://www.verilion.com/public/jpage/1/p/About/content.do">About</a>
				   </td>
                </tr>
				<tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="The latest news" href="http://www.verilion.com/public/jpage/1/p/NewsArchive/a/news/content.do">News</a>
				   </td>
                </tr>
				<tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Our contact information" href="http://www.verilion.com/public/jpage/1/p/Contact/content.do">Contact</a>
				   </td>
                </tr>
				<!--
                <tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Our clients" href="/public/jpage/1/p/Clients/content.do">Clients</a>
				   </td>
                </tr>
				-->
                <tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Case studies" href="http://www.verilion.com/public/jpage/1/p/Case/content.do">Case studies</a>
				   </td>
                </tr>
				<!--
                <tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Our hosted services offerings" href="/public/jpage/1/p/HostedServices/content.do">Hosted services</a>
				   </td>
                </tr>
				-->
				<tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Hosting packages" href="http://www.verilion.com/public/jpage/1/p/Entry/content.do">Hosting</a>
				   </td>
                </tr>
				<tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Customer support" href="http://www.verilion.com/public/jpage/1/p/Support/content.do">Support</a>
				   </td>
                </tr>
				<tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Technologies we use" href="/public/jpage/1/p/Applications/content.do">Technology</a>
				   </td>
                </tr>
				<tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="About our development methodologies" href="http://www.verilion.com/public/jpage/1/p/Methodology/content.do">Methodology</a>
				   </td>
                </tr>
				<tr>
                  <td
                   style="border: 1px solid white; width: 101px;">
				   <a title="Careers at Verilion" href="http://www.verilion.com/public/jpage/1/p/Careers/content.do">Careers</a>
				   </td>
                </tr>
            </table>
			</div>
          </td>
          <td
		   style="background: #b8e480; width: 100%"
           valign="top">
            <table
			 style="border: 0; padding: 0; width: 100%">
                <tr>
                  <td
                   style="width: 17px"><img src="/assets/templates/verilion20/images/mainspacer.gif"
                   height="17"
                   width="17"
				   alt="" /></td>
                  <td
                   valign="top"
                   style="width: 100%">
                    <table
					 style="border: 0; padding: 10px; width: 100%;">   
                        <tr>
                          <td
                           class="c2">
						   <div id="titlediv">
						   <table class="c2" style="width: 100%; border:0">
						   <tr>
						   <td colspan="2" style="height: 10px">&nbsp;</td></tr>
						   <tr>
						   <td valign="bottom" style="border-bottom: 1px solid #c30032">
						   <h1>Frequently Asked Questions</h1>
						   </td>
						   <td valign="bottom" align="right" style="width: 150px; height: 50px; border-bottom: 1px solid #c30032">&nbsp;
						   
						   </td>
						   </tr>
						   </table>
						   </div>
						   <v:message />
						  <div id="contentdiv"><br />
						  <jsp:useBean id="faq" scope="request" class="com.verilion.database.Faq" />
					Show questions for <select name="faq_cat_id" id="faq_cat_id" onchange="showquestions()">
					<option value="0">Choose category...</option>
					<%
					faq.setConn(conn);
					XDisconnectedRowSet drs = new XDisconnectedRowSet();
					drs = faq.getAllActiveFaqCategories();
					while(drs.next()) {
					%>
					<option value="<%=drs.getInt("faq_categories_id")%>"><%=drs.getString("faq_categories_name")%></option>
					<%
					}
					%>
					</select>
					<div id="questiondiv"></div>
					<div id="resultdiv"></div>
						  </div>
						  <br /><br />
						  </td>
                        </tr>
                      
                    </table>
					<%
					if (sPageName.equals("Homee")) {
					%>
                    <table
					 style="border: 0; padding: 0; width: 98%;">
                      
                        <tr>
                          <td
                           colspan="2"><img src="/assets/templates/verilion20/images/header_latestnews.gif"
                           height="26"
						   alt=""
                           width="308" /><v:latestnews conn="<%=conn%>" showTitle="0" /></td>
                        </tr>
                        <tr>
                          <td
                           valign="top"
                           style="width: 12px;">&nbsp;</td>
                          <td
                           style="width: 255px;">
                            <table
							 style="border: 0; padding: 0; width: 300px;">
                              
                                <tr>
                                  <td
                                   class="c3">&nbsp;</td>
                                </tr>
                              
                            </table>
                          </td>
                        </tr>
                      
                    </table>
					<%
					}
					%>
                    <br />
                  </td>
                </tr>
            </table>
          </td>
        </tr>
    </table>
  </body>
</html>
<%@include file="/footer.jsp" %>
