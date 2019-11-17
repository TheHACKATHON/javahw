<%@ page import="com.yevseienko.models.User" %>
<jsp:useBean id="loginedUser" scope="request" type="com.yevseienko.models.User"/>
<jsp:useBean id="users" scope="request" type="java.util.ArrayList<com.yevseienko.models.User>"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script async src="../../script/site.js"></script>
    <link rel="stylesheet" href="../../style/table.css">
</head>
<body>
<header>
    <div>
        <span>Logined as <span class="username"><%=loginedUser.getUsername()%></span></span>
        <button id="logout">Logout</button>
    </div>
    <h2>Table</h2>
    <table>
        <thead>
        <tr>
            <th>
                <span>Username</span>
            </th>
            <th>
                <span>Email</span>
            </th>
            <th>
                <span>First name</span>
            </th>
            <th>
                <span>Last name</span>
            </th>
            <th>
                <span>Gender</span>
            </th>
            <th>
                <span>Birth</span>
            </th>
            <th>
                <span>Subscribe</span>
            </th>
        </tr>
        </thead>
        <tbody>
            <%for(User user: users){%>
                <tr>
                    <td><%=user.getUsername()%></td>
                    <td><%=user.getEmail()%></td>
                    <td><%=user.getFirstName()%></td>
                    <td><%=user.getLastName()%></td>
                    <td><%=user.getGender()%></td>
                    <td><%=user.getBirth()%></td>
                    <td><%=user.isSubscribe()%></td>
                </tr>
            <%}%>
        </tbody>
    </table>
</header>
</body>
</html>