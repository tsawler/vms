<%
Runtime runtime = Runtime.getRuntime();
%>
<table border="1">
<tr>
<td>Total memory</td>
<td align="right"><%=runtime.totalMemory()%></td>
</tr>
<tr>
<td>Free memory</td>
<td align="right"><%=runtime.freeMemory()%></td>
</tr>
<%
System.gc();
%>
<tr>
<td>After gc</td>
<td align="right"><%=runtime.freeMemory()%></td>
</tr>
</table>
<%
System.out.println("******");
System.out.println("GC ran - free ram is " + runtime.freeMemory());
System.out.println("******");
%>
