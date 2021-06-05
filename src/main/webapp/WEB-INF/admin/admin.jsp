<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>Admin page</title>
</head>
<body>
<fmt:setBundle basename="messages"/>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<section class="Admin">
    <div class="container">
        <h1>
            <fmt:message key="label.welcome.admin" />
        </h1>
        <br/>
        <div class="links">
            <a href="${pageContext.request.contextPath}/app/manageProducts"><fmt:message key="label.manage.products" /></a>
            <a href="${pageContext.request.contextPath}/app/manageUsers"><fmt:message key="label.manage.users" /></a>
            <a href="${pageContext.request.contextPath}/app/manageOrders"><fmt:message key="label.manage.orders" /></a>
            <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logout" /></a>
        </div>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>

</body>
</html>