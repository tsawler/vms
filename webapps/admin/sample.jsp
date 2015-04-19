<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@include file="/AdminSecurity.jsp" %>
<%@ taglib uri="/WEB-INF/vtags.tld" prefix="v" %>
<%@include file="/admin/checkLogin.jsp" %>
<%
String error = "";
String errorMessage = "";
try {
%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<v:gtag />

<title>vMaintain Admin tool</title>

<link href="/css/stylesheet.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='/FCKeditor/fckeditor.js'></script>
<script type='text/javascript' src='/js/ajax/engine.js'></script>
<script type='text/javascript' src='/js/ajax/util.js'></script>
<script type='text/javascript' src='/js/ajax/Validator.js'></script>
<script type='text/javascript' src='/js/dragsortcmp.js'></script>
<script type='text/javascript' src='/js/tabber.js'></script>
<link rel="stylesheet" type="text/css" href="/css/ajax.css" />
<script type="text/javascript" src="/js/prototype.js"></script>
<script type="text/javascript" src="/js/ajaxtags-1.1.js"></script>

<script type="text/javascript">
/* <![CDATA[ */
document.write('<style type="text/css">.tabber{display:none;}<\/style>');
/* ]]> */
</script>
<link rel="stylesheet" href="/admin/adminstyles.css" type="text/css"  />
<script type="text/javascript">
/* <![CDATA[ */
var persistmenu="yes" 
var persisttype="sitewide"

if (document.getElementById){ //DynamicDrive.com change
document.write('<style type="text/css">\n')
document.write('.submenu{display: none;}\n')
document.write('</style>\n')
}

function SwitchMenu(obj){
	if(document.getElementById){
	var el = document.getElementById(obj);
	var ar = document.getElementById("masterdiv").getElementsByTagName("span"); 
		if(el.style.display != "block"){ 
			for (var i=0; i<ar.length; i++){
				if (ar[i].className=="submenu") 
				ar[i].style.display = "none";
			}
			el.style.display = "block";
		}else{
			el.style.display = "none";
		}
	}
}

function get_cookie(Name) { 
var search = Name + "="
var returnvalue = "";
if (document.cookie.length > 0) {
offset = document.cookie.indexOf(search)
if (offset != -1) { 
offset += search.length
end = document.cookie.indexOf(";", offset);
if (end == -1) end = document.cookie.length;
returnvalue=unescape(document.cookie.substring(offset, end))
}
}
return returnvalue;
}

function onloadfunction(){
if (persistmenu=="yes"){
var cookiename=(persisttype=="sitewide")? "switchmenu" : window.location.pathname
var cookievalue=get_cookie(cookiename)
if (cookievalue!="")
document.getElementById(cookievalue).style.display="block"
}
}

function savemenustate(){
var inc=1, blockid=""
while (document.getElementById("sub"+inc)){
if (document.getElementById("sub"+inc).style.display=="block"){
blockid="sub"+inc
break
}
inc++
}
var cookiename=(persisttype=="sitewide")? "switchmenu" : window.location.pathname
var cookievalue=(persisttype=="sitewide")? blockid+";path=/" : blockid
document.cookie=cookiename+"="+cookievalue
}

if (window.addEventListener)
window.addEventListener("load", onloadfunction, false)
else if (window.attachEvent)
window.attachEvent("onload", onloadfunction)
else if (document.getElementById)
window.onload=onloadfunction

if (persistmenu=="yes" && document.getElementById)
window.onunload=savemenustate
/* ]]> */
</script>
</head>

<body class="treenavpage" style="height: 100%;">
&nbsp;
<table style="padding: 0; border: 1px solid silver; width: 100%;">
  <tr style="height: 15px;">
    <td class="treetoplevel" style="border: 1px solid silver;">vMaintain
      Admin tool<br />
      John Smith  </td>
	  <td class="treetoplevel" style="text-align: right;">&nbsp;<v:messagecount admin="true" style="headerlinks" /><v:usercount conn="<%=conn%>" /></td>
  </tr>
  <tr>
    <td width="17%" class="tableheader" style="text-align: left; border: 1px solid silver;" valign="top"><strong>Main Menu</strong><br />
        <br />
		<%@include file="/admin/nav_menu_fi.jsp"%>    </td>
    <td class="tabledata" style="border: 1px solid silver;" valign="top" width="83%" cellpadding="0" cellspacing="0">
        <table style="border-bottom: 1px solid silver; height: 100%; width: 100%"  cellpadding="2" cellspacing="0">
        <tr style="height: 15px;">
        <td class="tableheader">Task List</td>
        </tr>
        <tr style="height: 15px;"><td class="tabledata">
        <v:message />
        </td></tr>
        <tr><td class="tabledata" valign="top">		 
		<div class="content">
		<table style="width: 100%">
			<tr>
				<td class="content" style="text-align: right;">
					<a href="javascript:performAction()">Save</a> ::
					<a href="javascript:performAction()">Cancel</a>
				</td>
			</tr>
		</table>
		

		<strong>Task list for John Smith</strong>
		<br /><br />
		<table class="tabledatacollapse" style="width: 500px;">
		<tr class="tableheader">
		<td>Task</td>
		<td>Priority</td>
		<td>Date</td>
		<td>Status</td>
		</tr>
		<tr>
		<td>Fix light in room 123</td>
		<td>High</td>
		<td>2008/09/01</td>
		<td>Open</td>
		</tr>
		<tr>
		<td>Replace faucet</td>
		<td>High</td>
		<td>2008/09/01</td>
		<td>Open</td>
		</tr>
		<tr>
		<td>Air cond. not working</td>
		<td>High</td>
		<td>2008/09/01</td>
		<td>Open</td>
		</tr>
		<tr>
		<td>TV broken</td>
		<td>High</td>
		<td>2008/09/01</td>
		<td>Closed</td>
		</tr>
		<tr>
		<td>Check locks</td>
		<td>High</td>
		<td>2008/09/01</td>
		<td>Open</td>
		</tr>
		<tr>
		<td>Fix light in room 32</td>
		<td>High</td>
		<td>2008/09/01</td>
		<td>Open</td>
		</tr>
		</table>
		</div>
		    
	    </td>
  </tr>
  <tr style="height: 15px">
    <td class="treetoplevel" colspan="2" style="text-align: right; border: 1px solid silver;">Copyright &copy; Verilion
      Inc.</td>
  </tr>
</table>
</body>
</html>
<%
}
catch (Exception e){
	e.printStackTrace();
   error = "true";
   errorMessage = "Please login";
   session.setAttribute("loginError", error);
   session.setAttribute("loginErrorMessage", errorMessage);
   response.sendRedirect("/admin/Error.jsp");
}
%>
<%@include file="/admin/footer.jsp" %>
