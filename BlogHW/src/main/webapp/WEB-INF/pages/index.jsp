<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Blog Home</title>

    <jsp:include page="includes/styles.jsp"/>
</head>

<body>
<jsp:include page="includes/menu.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1 class="my-4">Posts</h1>

            <c:forEach items="${requestScope.articles}" var="article">

            <div class="card mb-4">
                <img class="card-img-top" src="${article.imageUrl}" alt="Card image cap">
                <div class="card-body">
                    <h2 class="card-title">${article.title}
                    </h2>
                    <p class="card-text">${article.subtitle}</p>
                    <a href="${pageContext.request.contextPath}/article?id=${article.id}" class="btn btn-primary">Read
                        More &rarr;</a>
                </div>
                <div class="card-footer text-muted">
                    Posted on <fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                              value="${article.date}"/> by
                    <a href="#">${article.author}
                    </a>
                </div>
            </div>
            </c:forEach>
        </div>

        <jsp:include page="includes/sidebar.jsp"/>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>
<jsp:include page="includes/scripts.jsp"/>
</body>
</html>
