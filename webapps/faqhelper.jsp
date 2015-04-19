<%@include file="/PageSecurity.jsp"%>
<jsp:useBean id="faq" scope="request" class="com.verilion.database.Faq" />
<%
int id = 0;
int a = 0;
XDisconnectedRowSet drs = new XDisconnectedRowSet();

try {
	id = Integer.parseInt((String) request.getParameter("id"));
	a = Integer.parseInt((String) request.getParameter("a"));
} catch (Exception e){
	id = 0;
	a = 0;
}
faq.setConn(conn);

if (a == 1) {
	%>
	<br /><strong>Questions</strong><br />	<ul>
	<%
	// showing questions
	drs = faq.getAllActiveQuestionsForCategory(id);
	while (drs.next()) {
	%>

	<li><a href="#" onclick="showanswer('<%=drs.getInt("faq_id")%>')"><%=drs.getString("question")%></a></li>
	<%
	}
	%>
	</ul>
	<%
} else if (a == 2) {
	drs = faq.getFaqById(id);
	while (drs.next()) {
		%>
		<strong><%=drs.getString("question")%></strong><br />
		<%=drs.getString("answer")%>
		<%
	}
}
%>
<%@include file="/footer.jsp"%>
