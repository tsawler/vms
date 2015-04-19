<%
int k = 1;
%>

<div id="masterdiv">
<div class="menutitle" onClick="SwitchMenu('sub<%=k%>')">Users</div>
<span class="submenu" id="sub<%=k%>">
- <a href="/customer/jpage/1/p/SearchCustomers/admin/1/content.do">Maintain members</a><br />
- <a href="/admin/jpage/1/p/AddCustomer/a/addcustomer/admin/1/content.do">New member</a><br />
</span>
<%
k++;
%>

<div class="menutitle" onClick="SwitchMenu('sub<%=k%>')">Tasks</div>
<span class="submenu" id="sub<%=k%>">
- <a href="/createmessage/jpage/1/p/createmessage/admin/1/content.do">Create & send message</a><br />
- <a href="/viewqueue/jpage/1/p/viewqueue/admin/1/content.do">View message queue</a><br />
</span>
<%
k++;
%>

<div class="menutitle" onClick="SwitchMenu('sub<%=k%>')">Reports</div>
<span class="submenu" id="sub<%=k%>">
- <a href="/stories/jpage/1/p/searchstories/admin/1/content.do">Maintain stories</a><br />
- <a href="/admin/jpage/1/p/DisplayStory/a/displaystory/admin/1/content.do?cancelPage=/stories/jpage/1/p/searchstories/admin/1/content.do&id=0&edit=false">New story</a><br />
</span>
<%
k++;
%>


<div class="menutitle" onClick="SwitchMenu('sub<%=k%>')">User Admin</div>
<span class="submenu" id="sub<%=k%>">
- <a href="/users/jpage/1/p/UsersChoose/admin/1/content.do">Maintain users</a><br />
- <a href="/admin/jpage/1/p/RolesChoose/a/roleschoose/class/roles/clear/1/admin/1/content.do?filter=1">Maintain roles</a><br />
- <a href="/admin/jpage/1/p/RoleEdit/a/roleedit/admin/1/content.do?cancelPage=/admin/jpage/1/p/RolesChoose/a/roleschoose/class/roles/admin/1/content.do&amp;id=-1&amp;edit=false">New role</a><br />
</span>
<%
k++;
%>

<br /><br />
<a href="/admin/logout.jsp">Logout</a>
</div>