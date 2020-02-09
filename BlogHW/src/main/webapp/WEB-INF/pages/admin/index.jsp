<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Blog Login</title>

    <jsp:include page="../includes/styles.jsp"/>
</head>

<body>
<jsp:include page="../includes/menu.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="row col-md-12">
                <h1 class="my-4 col-md-4">Admin</h1>
                <div class="my-4 col-xl-8 text-right">
                    <a href="${pageContext.request.contextPath}/admin/article" class="btn btn-dark">Create</a>
                </div>
            </div>
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Title</th>
                    <th scope="col">Author</th>
                    <th scope="col">Posted on</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.articles}" var="article">
                    <tr>
                        <th scope="row"><a href="${pageContext.request.contextPath}/article?id=${article.id}">${article.title}</a></th>
                        <td>${article.author}</td>
                        <td>
                            <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${article.date}"/>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/article?id=${article.id}">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/article/delete?id=${article.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
<jsp:include page="../includes/scripts.jsp"/>
</body>
</html>
