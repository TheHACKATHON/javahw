<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Blog Post</title>
    <jsp:include page="includes/styles.jsp"/>
</head>

<body>

<jsp:include page="includes/menu.jsp"/>

<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8">
            <h1 class="mt-4">${requestScope.article.title}
            </h1>
            <p class="lead">by
                <a href="#">${requestScope.article.author}</a>
            </p>
            <hr>
            <p>Posted on <fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                         value="${requestScope.article.date}"/>
            </p>
            <hr>
            <img class="img-fluid rounded" src="${requestScope.article.imageUrl}" alt="">
            <hr>
            <div class="subtitle">
                <h2>${requestScope.article.subtitle}</h2>
            </div>
            <article>
                ${requestScope.article.text}
            </article>
            <hr>
        </div>
        <jsp:include page="includes/sidebar.jsp"/>

    </div>
</div>

<jsp:include page="includes/footer.jsp"/>
<jsp:include page="includes/scripts.jsp"/>
</body>
</html>
