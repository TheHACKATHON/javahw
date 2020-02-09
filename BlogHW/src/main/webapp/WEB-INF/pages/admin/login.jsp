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
        <div class="col-md-8">
            <h1 class="my-4">Login</h1>
            <form method="post" action="${pageContext.request.contextPath}/admin/login">
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control${not empty requestScope.emailValidationError ? ' is-invalid' : ''}"
                           id="email" name="email" placeholder="Enter email" required
                           value="${not empty sessionScope.lastEmail ? sessionScope.lastEmail : ''}">
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                    <c:if test="${not empty requestScope.emailValidationError}">
                        <div class="invalid-feedback">
                                ${requestScope.emailValidationError}
                        </div>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control${not empty requestScope.validationError ? ' is-invalid' : ''}"
                           id="password" name="password" placeholder="Password" required>
                    <c:if test="${not empty requestScope.validationError}">
                        <div class="invalid-feedback">
                                ${requestScope.validationError}
                        </div>
                    </c:if>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>

        <jsp:include page="../includes/sidebar.jsp"/>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
<jsp:include page="../includes/scripts.jsp"/>
</body>
</html>
