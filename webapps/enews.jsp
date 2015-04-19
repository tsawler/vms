<%@include file="/AdminSecurity.jsp" %>
<%@ taglib uri="/WEB-INF/vtags.tld" prefix="v" %>
<%@ page language="java" import="com.fredck.FCKeditor.*" %>
<%@ taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>
<%@include file="/admin/checkLogin.jsp" %>
<%
String error = "";
String errorMessage = "";
//session = request.getSession(true);

try {
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<v:gtag />
<title>vMaintain Admin tool</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="/admin/adminstyles.css" type="text/css"  />
<link href="/css/stylesheet.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/js/calendar1.js"></script>
<script language="javascript" src="/js/calendar2.js"></script>

</head>

<body class="treenavpage" style="height: 100%;">
&nbsp;
<table cellpadding="2" cellspacing="0" style="border: 1px solid silver; height: 95%; width: 100%;">
  <tr style="height: 15px;">
    <td colspan="2" class="treetoplevel" style="border: 1px solid silver;">vMaintain
      Admin tool<br />
      <%=session.getAttribute("userAdminName")%></td>
  </tr>
  <tr>
    <td width="17%" class="tableheader" style="text-align: left; border: 1px solid silver;" valign="top"><strong>Main Menu</strong><br />
        <br />
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tabledata">
          <tr>
            <td class="rowheader">Customers</td>
          </tr>
          <tr>
            <td><a href="/admin/jpage/1/p/SearchCustomers/a/searchcustomers/class/customer/clear/1/limit/10/offset/0/content.do?filter=1">Maintain customers</a></td>
          </tr>
          <tr>
            <td><a href="/admin/jpage/1/p/AddCustomer/a/addcustomer/content.do">New customer</a></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td class="rowheader">News</td>
          </tr>
          <tr>
            <td><a href="/admin/jpage/1/p/NewsItemChoice/a/newsitemchoice/class/newsobject/clear/1/limit/10/offset/0/content.do?filter=1">Maintain news items</a></td>
          </tr>
          <tr>
            <td><a href="/admin/jpage/1/p/NewsItemDetail/a/newsitemdetail/content.do?id=-1&edit=false&cancelPage=/admin/jpage/1/p/NewsItemChoice/a/newsitemchoice/class/newsobject/limit/10/offset/0/content.do">Add news item</a></td>
          </tr>
          <tr>
            <td><a href="/admin/jpage/1/p/NewsCategoriesChoice/a/newscategorieschoice/class/newscategories/clear/1/limit/10/offset/0/content.do?filter=1">Maintain news categories</a></td>
          </tr>
          <tr>
            <td><a href="/admin/jpage/1/p/NewsCategoriesDetail/a/newscategorydetail/content.do?id=-1&edit=false&cancelPage=/admin/jpage/1/p/NewsCategoriesChoice/a/newscategorieschoice/class/newscategories/limit/10/offset/0/content.do">New category</a></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td class="rowheader">Pages</td>
          </tr>
          <tr>
            <td><a href="/admin/jpage/1/p/EditPageChoose/a/editpagechoose/class/page/clear/1/limit/10/offset/0/content.do?filter=1">Maintain pages</a></td>
          </tr>
          <tr>
            <td><a href="/admin/jpage/1/p/EditPage/a/editpage/language_id/1content.do?id=-1&edit=false&cancelPage=/admin/jpage/1/p/EditPageChoose/a/editpagechoose/class/page/limit/10/offset/0/content.do">Add page</a></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td class="rowheader">Templates</td>
          </tr>
          <tr>
                <td>Maintain Templates</td>
          </tr>
          <tr>
                <td>Maintain JSP templates</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td class="rowheader">Modules</td>
          </tr>
          <tr>
                <td>Maintain Modules</td>
          </tr>
          <tr>
                <td>New module</td>
          </tr>
          <tr>
                <td>Module mappings</td>
                </tr>
                <tr>
            <td>&nbsp;</td>
          </tr>
                <tr>
            <td class="rowheader">System Admin</td>
          </tr>
          <tr>
                <td>System preferences</td>
          </tr>
          <tr>
                <td>Menu admin</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td class="rowheader">User Admin</td>
          </tr>
          <tr>
                <td>Maintain users</td>
          </tr>
          <tr>
                <td>New user</td>
          </tr>
          <tr>
                <td>Maintain roles</td>
          </tr>
          <tr>
                <td>New role</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><a href="/">To public site</a></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><a href="/admin/logout.jsp">Logout</a></td>
          </tr>
        </table>
    </td>
    <td class="tabledata" style="border: 1px solid silver;" valign="top" width="83%" cellpadding="0" cellspacing="0">
        <table style="border-bottom: 1px solid silver; height: 100%; width: 100%"  cellpadding="2" cellspacing="0">
        <tr style="height: 15px;">
        <td class="tableheader"><strong><%=page_detail_title%></strong></td>
        </tr>
        <tr style="height: 15px;"><td class="tabledata">
        <v:message />
        </td></tr>
        <tr><td class="tabledata" valign="top">
                <div class="content">
                <%=page_detail_contents%>
                <FCK:editor id="news_teaser_text" basePath="/FCKeditor/"
												imageBrowserURL="/FCKeditor/editor/filemanager/browser/default/browser.html??Type=Image&Connector=connectors/jsp/connector"
												linkBrowserURL="/FCKeditor/editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector"
												width="100%"
												height="400">
												
								</FCK:editor>
								
						 </td>
							  </tr>
							</table>
							<input type="hidden" name="cancelPage" value="$CANCEL$" />
							<input type="hidden" name="id" value="$NEWSID$" />
							</form>
							<script language="JavaScript">
							<!-- 
								var cal1 = new calendar1(document.forms['newsform'].elements['start_date']);
								cal1.year_scroll = true;
								cal1.time_comp = false;
								var cal2 = new calendar1(document.forms['newsform'].elements['end_date']);
								cal2.year_scroll = true;
								cal2.time_comp = false;
							//-->
							</script>
                </div>
                 </td>
        </tr></table>
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
<%@include file="footer.jsp" %>

