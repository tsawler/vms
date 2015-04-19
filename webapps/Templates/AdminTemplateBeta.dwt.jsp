<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@include file="/AdminSecurity.jsp" %>
<%@ taglib uri="/WEB-INF/vtags.tld" prefix="v" %>
<%@include file="/admin/checkLogin.jsp" %>
<head>
	<v:gtag />
	<!-- TemplateInfo codeOutsideHTMLIsLocked="true" -->
	<!-- TemplateBeginEditable name="doctitle" -->
  <title>vMaintain Admin</title>
  <!-- TemplateEndEditable -->
    <script type="text/javascript" src="http://yui.yahooapis.com/2.2.0/build/utilities/utilities.js"></script> 
    <script type="text/javascript" src="/js/yui-ext/yui-ext.js"></script>


    <link rel="stylesheet" type="text/css" href="/js/yui-ext/resources/css/tabs.css"/>

	<link rel="stylesheet" type="text/css" href="/js/yui-ext/resources/css/layout.css"/>
	<link rel="stylesheet" type="text/css" href="/js/yui-ext/resources/css/grid.css"/>
    <link rel="stylesheet" href="/admin/admin.css" type="text/css"  />
	
	<style type="text/css">
	body {font:normal 9pt verdana; margin:0;padding:0;border:0px none;overflow:hidden;}
	.ylayout-panel-north {
	    border:0px none;
	    background-color:#c3daf9;
	}
	#nav {
	}
	#autoTabs, #center1, #center2, #west {
	    padding:10px;
	}
	#north, #south{
	    font:normal 8pt arial, helvetica;
	    padding:4px;
	}
	.ylayout-panel-center p {
	    margin:5px;
	}
	#props-panel .ygrid-col-0{
	}
	#props-panel .ygrid-col-1{
	}
	</style>
	
	<link href="/css/stylesheet.css" rel="stylesheet" type="text/css" />
	<script type='text/javascript' src='/js/editor/fckeditor.js'></script>
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

<script type="text/javascript" src="/admin/adminjs.js"></script>
	<!-- TemplateBeginEditable name="head" --><!-- TemplateEndEditable -->
</head>
<body>
	<!-- TemplateBeginEditable name="BeforeHTML" --><!-- TemplateEndEditable -->
<div id ="container">
  <div id="west" class="ylayout-inactive-content">
		<%@include file="/admin/nav_menu.jsp"%>
  </div>
  <div id="north" class="ylayout-inactive-content">
    vMaintain Administration - logged in as <%=session.getAttribute("userAdminName")%>&nbsp;
  </div>

  <div id="east" class="ylayout-inactive-content">
      
  </div>
  <div id="center2" class="ylayout-inactive-content">
  <v:message />
  	<!-- TemplateBeginEditable name="WorkArea" -->
  	<%=page_detail_contents%><br />
  	<!-- TemplateEndEditable -->
  </div>
  <div id="props-panel" class="ylayout-inactive-content" style="width:200px;height:200px;overflow:hidden;">
  </div>
  <div id="south" class="ylayout-inactive-content">
    <v:usercount conn="<%=conn%>" />
  </div>
</div>
 </body>
</html>
<%@include file="/admin/footer.jsp" %>