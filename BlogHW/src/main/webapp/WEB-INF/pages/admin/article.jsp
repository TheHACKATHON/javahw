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
                <h1 class="my-4 col-md-4">${empty requestScope.article ? "Create" : "Edit"} article</h1>
                <div class="my-4 col-xl-8 text-right">
                    <c:if test="${not empty requestScope.article && not empty requestScope.article.id}">
                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin/article/delete?id=${requestScope.article.id}">Delete</a>
                    </c:if>
                </div>
            </div>

            <form method="post" action="${pageContext.request.contextPath}/admin/article">
                <input type="hidden" value="${not empty requestScope.article ? requestScope.article.id : ""}"
                    name="id">
                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text"
                           class="form-control"
                           id="title"
                           required
                           placeholder="Articles that made my Week #001"
                           name="title" value="${not empty requestScope.article ? requestScope.article.title : ""}">
                </div>
                <div class="form-group">
                    <label for="subtitle">Subtitle</label>
                    <input type="text"
                           class="form-control"
                           id="subtitle"
                           required
                           name="subtitle"
                           value="${not empty requestScope.article ? requestScope.article.subtitle : ""}">
                </div>
                <div class="form-group">
                    <label for="imageUrl">Image URL</label>
                    <input type="text"
                           class="form-control"
                           id="imageUrl"
                           required
                           name="imageUrl"
                           value="${not empty requestScope.article ? requestScope.article.imageUrl : ""}">
                </div>
                <div class="form-group">
                    <label for="htmlData">Html body</label>
                    <textarea class="form-control"
                              id="htmlData"
                              name="htmlData"
                              required
                              rows="10">
                        ${not empty requestScope.article ? requestScope.article.text : ""}
                    </textarea>
                </div>
                <button type="submit" class="btn btn-primary text-right">Submit</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
<jsp:include page="../includes/scripts.jsp"/>
</body>
</html>
