
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cases</title>
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/style.css">
</head>

<body>

<%@ include file="header.jsp" %>

<h1>Case Register</h1>

<a class="btn" href="case-form.jsp">+ New Case</a>

<div class="card table-wrap">
<table>

<tr>
    <th>Case No.</th>
    <th>Title</th>
    <th>Category</th>
    <th>Status</th>
    <th>Priority</th>
    <th>Investigator</th>
    <th>Action</th>
</tr>

<%
List<Map<String, Object>> rows =
    (List<Map<String, Object>>) request.getAttribute("rows");

if (rows != null) {
    for (Map<String, Object> row : rows) {
%>

<tr>
    <td><%=row.get("case_no")%></td>
    <td><%=row.get("title")%></td>
    <td><%=row.get("category")%></td>
    <td><%=row.get("status")%></td>
    <td><%=row.get("priority")%></td>
    <td><%=row.get("investigator")%></td>

    <td>
        <a href="cases?action=edit&id=<%=row.get("id")%>">
            Edit
        </a>

        <form method="post"
              action="cases"
              style="display:inline"
              onsubmit="return confirm('Delete case and linked records?')">

            <input type="hidden" name="action" value="delete">

            <input type="hidden"
                   name="id"
                   value="<%=row.get("id")%>">

            <button class="danger">Delete</button>
        </form>
    </td>
</tr>

<%
    }
}
%>

</table>
</div>

</body>
</html>
