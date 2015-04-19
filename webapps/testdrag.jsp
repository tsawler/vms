<html>
<head>
</head>
<body>
<table id=t>
<tr><td>World</td></tr>
<tr><td>Hello</td></tr>
</table>

<script type='text/javascript'>
        var t = document.getElementById('t').firstChild
        x=t.firstChild.cloneNode(true);
        t.firstChild.removeNode(false);
        t.appendChild(x)
</script> 
</body>
</html>
