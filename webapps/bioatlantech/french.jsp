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
Du 9 au 12 ao&ucirc;t 2009<br />
Moncton (Nouveau-Brunswick) Canada</div>
<h2>Lignes directrices pour les r&eacute;sum&eacute;s, les pr&eacute;sentations et les affiches </h2>
Les <strong>pr&eacute;sentations</strong> sont limit&eacute;es &agrave; 15 minutes, plus cinq minutes pour les questions et les discussions. BioAtlantech doit recevoir les r&eacute;sum&eacute;s au plus tard le 20 juin 2009. Tous les r&eacute;sum&eacute;s et les pr&eacute;sentations PowerPoint seront affich&eacute;s sur le site Web de la conf&eacute;rence NACREW sous forme de fichiers pdf, sauf avis contraire de l'auteur.  
<br />
<br />
Les <strong>r&eacute;sum&eacute;s</strong> ne doivent pas d&eacute;passer 400 mots. Veuillez utiliser le logiciel Microsoft Word ou un logiciel de traitement de texte compatible. (Times New Roman &ndash; taille de police 12, marges &ndash; 0,5 po G - D et H - B). 
<br /><br />
<img src="imgfr.png" height="160" width="537" alt="image" />
<br /><br />
<strong>Affiches</strong> &ndash; Ceux qui veulent pr&eacute;senter des affiches &agrave; la conf&eacute;rence NACREW doivent &eacute;galement fournir un r&eacute;sum&eacute; au plus tard le 20 juin. Les r&eacute;sum&eacute;s des affiches ne doivent pas d&eacute;passer 400 mots, mais les auteurs peuvent inclure d'autres figures ou images pour la proc&eacute;dure num&eacute;rique.<br /><br />
<form action="mail.jsp"method="post">
 <em>(Veuillez indiquer si votre r&eacute;sum&eacute; s'applique &agrave; une pr&eacute;sentation ou &agrave; une affiche)</em><br /><br />
<input type="checkbox" name="abstract" />
R&eacute;sum&eacute; de pr&eacute;sentation <br />
<input type="checkbox" name="poster" />
R&eacute;sum&eacute; d&rsquo;affiche<br /><br />


<table style="border: none;">
<tr>
<td>
  <strong>R&eacute;sum&eacute; de pr&eacute;sentation :</strong> </td>
<td><input type="text" name="title" /></td>
</tr>
<tr>
<td>
Auteur et affiliation : </td>
<td><input type="text" name="authors"  /></td>
</tr>
<tr>
<td>
Nom du pr&eacute;sentateur :</td>
<td>
<input type="text" name="name" /></td>
</tr>
<tr>
<td>
Personne-ressource (t&eacute;l&eacute;phone) :</td>
<td>
<input type="text" name="phone" /></td>
</tr>
<tr>
<td>
Personne-ressource (courriel &eacute;lectronique) : </td>
<td>
<input type="text" name="email" />
</td>
</tr>
<tr>
<td colspan="2">R&eacute;sum&eacute;:</td>
</tr>
<tr>
<td colspan="2">
<textarea name="abstracttext" style="width:100%; height: 200px;"></textarea>
</td>
</tr>
</table>
<input type="submit" value="Envoyez-nous l'information" />
</form>
</td>
</tr>
</table>
</body>
</html>
