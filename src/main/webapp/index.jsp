<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>JSP - Hello World трататам</title>
</head>
<body>
<header>
    <jsp:include page="WEB-INF/header.jsp"/>
</header>
<section>
    <div class="container">
        <h1>
            <%= "Some start page or whatever" %>
        </h1>
        <br/>
        <a href="${pageContext.request.contextPath}/app/products">Kinda products</a>
    </div>
</section>

<footer class="footer">
    <jsp:include page="WEB-INF/footer.jsp"/>
</footer>

</body>
</html>