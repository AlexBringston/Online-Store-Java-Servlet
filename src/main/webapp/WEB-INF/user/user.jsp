<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>User page</title>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<section class="User">
    <div class="container">
        <h1>
            <%= "Hello COMMON USEEEEEEEEEEEEEEEEEER" %>
        </h1>
        <br/>
        <a href="${pageContext.request.contextPath}/app/logout">Вийти з акаунту</a>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>

</body>
</html>