<cfsetting enablecfoutputonly="true">
<!---
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2004 Frederico Caldeira Knabben
 * 
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 * 
 * For further information visit:
 * 		http://www.fckeditor.net/
 * 
 * File Name: sample02_mx.cfm
 * 	Sample page for ColdFusion MX.
 * 
 * Version:  2.0 RC3
 * Modified: 2004-12-07 12:24:12
 * 
 * File Authors:
 * 		Hendrik Kramer (hk@lwd.de)
--->

<!--- ::
	  * You must set the url path to the base directory for your media files (images, flash, files)
	  * The best position for this variable is in your Application.cfm file
	  * 
	  * Possible variable scopes are:
	  * <cfset APPLICATION.userFilesPath = "/UserFiles/">
	  * OR:
	  * <cfset SERVER.userFilesPath = "/UserFiles/">
	  *
	  * Note #1: Do _not_ set the physical directory on your server, only a path relative to your current webroot
	  * Note #2: Directories will be automatically created
	  :: --->
<cfset APPLICATION.userFilesPath = "/UserFiles/">

<cfoutput>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>FCKeditor - Sample</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="robots" content="noindex, nofollow">
	<link href="../sample.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h1>FCKEditor - ColdFusion Component (CFC) - Sample 2</h1>

This sample displays a normal HTML form with a FCKeditor with full features enabled; invoked by a ColdFusion Component.<br>
ColdFusion is a registered trademark and product of <a href="http://www.macromedia.com/software/coldfusion/" target="_blank">Macromedia, Inc</a>.
<hr>
<form method="POST" action="#cgi.script_name#">
</cfoutput>

<cfif listFirst( server.coldFusion.productVersion ) LT 6>
	<cfoutput><br><em style="color: red;">This sample work only with a ColdFusion MX server and higher, because it uses some advantages of this version.<br>Please try our other <a href="sample01.cfm">ColdFusion Sample Integration</a>.</em></cfoutput>
	<cfabort>
</cfif>

<cfscript>
	fckEditor = createObject("component", "/fckeditor/fckeditor");
	fckEditor.instanceName	= "myEditor";
	fckEditor.value			= 'This is some sample text. You are using <a href="http://fckeditor.net/" target="_blank">FCKeditor</a>.';
	fckEditor.basePath		= "/fckeditor/";
	fckEditor.width			= "100%";
	fckEditor.height		= 200;
	fckEditor.create(); // create the editor.
</cfscript>

<cfoutput>
<br />
<input type="submit" value="Submit">
<br />
</cfoutput>

<cfdump 
	var="#FORM#" 
	label="Dump of FORM Variables"
>

<cfoutput></form></body></html></cfoutput>

<cfsetting enablecfoutputonly="false">