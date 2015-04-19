<%@ taglib uri="/WEB-INF/vtags.tld" prefix="v" %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="http://www.verilion.com/css/vstylesheet.css" type="text/css"  />
<link rel="stylesheet" href="http://www.verilion.com/admin/adminstyles.css" type="text/css"  />
<script language="JavaScript" src="/jscript/CalendarPopup.js"></script>
</head>

<body class="tabledata" style="background: #ddd;">

<v:querybox 
	legend="Search by name" 
	action="test.jsp" 
	formname="myform" 
	method="post" 
	searchterms="Last name|lastname,First name|firstname,Date of birth|birthdate,Active|active_yn" 
	searchdetail="text|lastname|Flintstone,text|firstname|Fred,text|birthdate| ,yn|active_yn|n" />

<script language="JavaScript" id="jscal1xx">
			var cal1xx = new CalendarPopup();
			cal1xx.showNavigationDropdowns();
		  </script>
</body>
</html>
