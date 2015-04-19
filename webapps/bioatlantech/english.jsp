<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>NACREW</title>
<style type="text/css">
html {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11pt;
	}
	</style>
</head>

<body>
<table style="width: 538px" align="center">
<tr>
<td>
<div align="center">
<h1>NACREW</h1>
August 9th to 12th, 2009<br />
Moncton, New Brunswick, Canada
</div>
<h2>Guidelines for Abstracts, Presentations and Posters</h2>
<strong>Presentations</strong> are limited to 15 minutes with an additional 5 minutes for questions and discussion. Abstracts must be received by BioAtlantech no later than June 20th, 2009.  All abstracts and PowerPoint presentations will be made available on the NACREW conference web site as pdf files unless we are otherwise directed by the author. 
<br />
<br />
<strong>Abstracts</strong> should be 400 words or less.  Please use Microsoft Word or a compatible text program. (Times New Roman - font size 12, Margins - 0.5” L - R and T - B). 
<em>(Presenters will be forwarded instructions for PowerPoint file transfer.)</em>
<br /><br />
<img src="img.png" height="148" width="537" alt="image" />
<br /><br />
<strong>Posters</strong> Those wishing to present posters at NACREW must also submit an abstract by June 20th. Poster abstracts should be no more than 400 words but authors may include additional figures or images for the digital proceedings.<br /><br />
<form action="mail.jsp"method="post">
 <em>(Please indicate if your abstract is for a Presentation or Poster)</em><br /><br />
<input type="checkbox" name="abstract" />Presentation Abstract		<br />
<input type="checkbox" name="poster" />Poster Abstract<br /><br />

<table style="border: none;">
<tr>
<td>
ABSTRACT TITLE: 
</td>
<td><input type="text" name="title" /></td>
</tr>
<tr>
<td>
Authors and Affiliation: 
</td>
<td><input type="text" name="authors"  />
</td>
</tr>
<tr>
<td>
Name of Presenter: 
</td>
<td>
<input type="text" name="name" />
</td>
</tr>
<tr>
<td>
Contact person - Telephone: 
</td>
<td>
<input type="text" name="phone" />
</td>
</tr>
<tr>
<td>
Email address: 
</td>
<td>
<input type="text" name="email" />
</td>
</tr>
<tr>
<td colspan="2">Abstract:</td>
</tr>
<tr>
<td colspan="2">
<textarea name="abstracttext" style="width:100%; height: 200px;"></textarea>
</td>
</tr>
</table>
<input type="submit" value="Send Information" />
</form>
</td>
</tr>
</table>
</body>
</html>
