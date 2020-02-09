<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="date" value="<%=new java.util.Date()%>" />
<footer class="py-5 bg-dark">
    <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Your Website <fmt:formatDate type="date" pattern="YYYY" value="${date}"/></p>
    </div>
</footer>