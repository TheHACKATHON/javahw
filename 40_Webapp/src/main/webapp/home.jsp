<jsp:useBean id="homeworkId" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="taskId" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="taskName" scope="request" type="java.lang.String"/>
<jsp:useBean id="taskPath" scope="request" type="java.lang.String"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>itstep homeworks</title>
    <link rel="stylesheet" href="/style/site.css">
</head>
<body>
<jsp:include page="sidebar.jsp"/>
<% if (!taskPath.isEmpty()){ %>
<main data-homework="<%=homeworkId%>" data-task="<%=taskName%>" data-taskId="<%=taskId%>" data-taskPath="<%=taskPath%>">
    <%
        String path = (String) request.getAttribute("taskPath");
        path += "/index.html";
    %>
    <jsp:include page="<%= path %>" flush="true"></jsp:include>
</main>
<% } %>
<script src="/scripts/updateLinks.js"></script>
<script src="/scripts/sidebar.js"></script>
</body>
</html>
