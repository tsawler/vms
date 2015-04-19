<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en"><!-- InstanceBegin template="/Templates/AdminTemplate.dwt.jsp" codeOutsideHTMLIsLocked="true" -->
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

<!-- InstanceBeginEditable name="doctitle" -->
<title>vMaintain Admin tool</title>
<!-- InstanceEndEditable -->
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
<!-- InstanceBeginEditable name="head" -->
<script type="text/javascript" src="http://yui.yahooapis.com/2.2.0/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.2.0/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.2.0/build/container/container-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.2.0/build/animation/animation-min.js"></script>
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.2.0/build/container/assets/container.css">
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.2.0/build/calendar/assets/calendar.css"> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.2.0/build/calendar/calendar-min.js"></script>

<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display" %>
<%@ page import="org.displaytag.sample.*,
                 org.displaytag.tags.TableTag,
                 org.apache.commons.beanutils.*" %>
<%@ taglib uri="/WEB-INF/vtags.tld" prefix="v" %>
<%@ page language="java" import="com.fredck.FCKeditor.*" %>
<jsp:useBean id="myQueue" scope="request" class="com.verilion.database.MessageQueue" />


<script type="text/javascript">
YAHOO.namespace("example.calendar");
YAHOO.namespace("example.container");

function startDateCal(type,args,objs){
	var arrDates = YAHOO.example.calendar.startDate.getSelectedDates();
	var date = arrDates[0]; 
	var displayDate= date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
	document.custForm.start_date.value = displayDate;
	YAHOO.example.calendar.startDate.hide();
	YAHOO.example.container.startDatePanel.hide();
}

function endDateCal(type,args,objs){
	var arrDates = YAHOO.example.calendar.endDate.getSelectedDates();
	var date = arrDates[0]; 
	var displayDate= date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
	document.custForm.end_date.value = displayDate;
	YAHOO.example.calendar.endDate.hide();
	YAHOO.example.container.endDatePanel.hide();
}

function initCals() {
	// make start date calendar
	YAHOO.example.calendar.startDate = new YAHOO.widget.Calendar("start_date","startDateDiv", { title:"", close:false } );
	YAHOO.example.calendar.startDate.selectEvent.subscribe(startDateCal,YAHOO.example.calendar.startDate, true);
	YAHOO.example.calendar.startDate.render();
	// make end date calendar
	YAHOO.example.calendar.endDate = new YAHOO.widget.Calendar("end_date","endDateDiv", { title:"", close:false } );
	YAHOO.example.calendar.endDate.selectEvent.subscribe(endDateCal,YAHOO.example.calendar.endDate, true);
	YAHOO.example.calendar.endDate.render();
	
	// Instantiate start date panel
	YAHOO.example.container.startDatePanel = new YAHOO.widget.Panel("startDatePanel", { 
		width:"160px", height:"220px", visible:false, constraintoviewport:true, x:250, y:150 
	} );
	YAHOO.example.container.startDatePanel.render();
	
	// make end date panel
	YAHOO.example.container.endDatePanel = new YAHOO.widget.Panel("endDatePanel", { 
		width:"160px", height:"220px", visible:false, constraintoviewport:true,  x:250, y:150 
	} );
	YAHOO.example.container.endDatePanel.render();
	
	YAHOO.util.Event.addListener("startDate", "click", YAHOO.example.container.startDatePanel.show, YAHOO.example.container.startDatePanel, true);
	YAHOO.util.Event.addListener("startDate", "click", YAHOO.example.calendar.startDate.show, YAHOO.example.calendar.startDate, true);
	
	YAHOO.util.Event.addListener("endDate", "click", YAHOO.example.container.endDatePanel.show, YAHOO.example.container.endDatePanel, true);
	YAHOO.util.Event.addListener("endDate", "click", YAHOO.example.calendar.endDate.show, YAHOO.example.calendar.endDate, true);
}

YAHOO.util.Event.addListener(window, "load", initCals);
</script>
	<style>
	.error { color: red; }
	</style>
<script>
function validateForm()
	{
		var okay = true;
		var result = "";
		result += checkRequired("end_date");
		result += checkRequired("start_date");
		if (result != "") {
			okay = false;
		}
		return okay;
	}
function checkDate()
	{
	    validator.DateValid(replyDate, DWRUtil.getValue("end_date"));
	}
function replyDate(valid)
	{
	    processReply(valid, "end_date", "end_date-error", "Invalid date; use YYYY-MM-DD");
	}
function checkDate2()
	{
	    validator.DateValid(replyDate2, DWRUtil.getValue("start_date"));
	}
function replyDate2(valid)
	{
	    processReply(valid, "start_date", "start_date-error", "Invalid date; use YYYY-MM-DD");
	}
function processReply(valid, id, errid, error)
	{
	    if (valid)
	    {
	        DWRUtil.setValue(errid, "");
	    }
	    else
	    {
	        DWRUtil.setValue(errid, error);
	    }
	}
</script>
<!-- InstanceEndEditable -->
</head>

<body class="treenavpage" style="height: 100%;">
<!-- InstanceBeginEditable name="BeforeHTML" -->&nbsp;<!-- InstanceEndEditable -->
<table style="padding: 0; border: 1px solid silver; width: 100%;">
  <tr style="height: 15px;">
    <td class="treetoplevel" style="border: 1px solid silver;">vMaintain
      Admin tool<br />
      <%=session.getAttribute("userAdminName")%>
	  </td>
	  <td class="treetoplevel" style="text-align: right;">&nbsp;<v:messagecount admin="true" style="headerlinks" /><v:usercount conn="<%=conn%>" /></td>
  </tr>
  <tr>
    <td width="17%" class="tableheader" style="text-align: left; border: 1px solid silver;" valign="top"><strong>Main Menu</strong><br />
        <br />
		<%@include file="/admin/nav_menu.jsp"%>
    </td>
    <td class="tabledata" style="border: 1px solid silver;" valign="top" width="83%" cellpadding="0" cellspacing="0">
        <table style="border-bottom: 1px solid silver; height: 100%; width: 100%"  cellpadding="2" cellspacing="0">
        <tr style="height: 15px;">
        <td class="tableheader"><!-- InstanceBeginEditable name="Title" --><%=page_detail_title%><!-- InstanceEndEditable --></td>
        </tr>
        <tr style="height: 15px;"><td class="tabledata">
        <v:message />
        </td></tr>
        <tr><td class="tabledata" valign="top">
		<!-- InstanceBeginEditable name="WorkArea" -->
		<div class="content">
		<%
		int status = 2;
		myQueue.setConn(conn);
		if (request.getParameter("status") != null) {
			status = Integer.parseInt((String) request.getParameter("status"));
			if (status == 1) {
				RowSetDynaClass resultset = myQueue.GetAllSentMessagesDynaBean();
				request.setAttribute("results", resultset);
			} else if (status == 2) {
				RowSetDynaClass resultset = myQueue.GetAllUnsentMessagesDynaBean();
				request.setAttribute("results", resultset);
			} else {
				RowSetDynaClass resultset = myQueue.GetAllMessagesDynaBean();
				request.setAttribute("results", resultset);
			}
		} else {
			RowSetDynaClass resultset = myQueue.GetAllUnsentMessagesDynaBean();
			request.setAttribute("results", resultset);
		}
		%>
		<div class="content">
		<%=page_detail_contents%>
		</div>
		<div align="center">
		<fieldset><legend>Message Queue</legend>
		<form name="custForm" id="custForm" method="post" action="/viewqueue/jpage/1/p/viewqueue/admin/1/content.do">
		<table class="tabledata">
		  <tr>
		    <td>Status</td>
			<td>From&nbsp;<a href="#" id="startDate"><img src="/images/cal.png" alt="cal" height="14" width="14" border="0"></a></td>
			<td>To&nbsp;<a href="#" id="endDate"><img src="/images/cal.png" alt="cal" height="14" width="14" border="0"></a></td>
		  </tr>
		  <tr>
		    <td>
			<select name="status" class="inputbox">
			<option value="1"<%if (status == 1){%> selected <%}%>>Sent</option>
			<option value="2"<%if (status == 2){%> selected <%}%>>Unsent</option>
			<option value="0"<%if (status == 0){%> selected <%}%>>Both</option>
			</select>
			</td>
			<td>
			<input class="inputbox" size="30" type="text" id="start_date" onChange="checkDate2()" name="start_date" />
			
			</td>
			<td>
			<input class="inputbox" size="30" type="text" id="end_date" onChange="checkDate()" name="end_date" />
			<input type="submit" class="inputbox" value="Go"></td>
		  </tr>
		  <tr>
		  <td></td>
		  <td><span id="start_date-error" class="error"></span></td>
		  <td><span id="end_date-error" class="error"></span></td></tr>
		  </table>
		  </form>
		  
		  </fieldset>
		
		</div>
		<div>
			<display:table name="requestScope.results.rows" defaultsort="1" id="queue" pagesize="10"class="tabledatacollapse" style="width: 100%" >
			  <display:column property="message_queue_id" title="Id" />
			  <display:column property="message_queue_to" title="To" />
			  <display:column property="message_queue_from" title="From" />
			  <display:column property="message_queue_subject" title="Subject" />
			  <display:column property="message_queue_send_date" title="In queue since" />
			  <display:column property="message_queue_sent_date" title="Sent date" />
			</display:table>
		</div>
		</div>
		<div id="startDatePanel">
	<div class="hd">Choose Date</div>
	<div class="bd"><div>&nbsp;</div><div id="startDateDiv"></div></div>
	<div class="ft">&nbsp;</div>
</div>
<div id="endDatePanel">
	<div class="hd">Choose Date</div>
	<div class="bd"><div>&nbsp;</div><div id="endDateDiv"></div></div>
	<div class="ft">&nbsp;</div>
</div>
		<!-- InstanceEndEditable --></td>
        </tr></table>
    </td>
  </tr>
  <tr style="height: 15px">
    <td class="treetoplevel" colspan="2" style="text-align: right; border: 1px solid silver;">Copyright &copy; Verilion
      Inc.</td>
  </tr>
</table>

</body>
<!-- InstanceEnd --></html>
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
