<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500&display=swap" rel="stylesheet">
    <title>Login page</title>
</head>
<body>
    <header>
        <jsp:include page="WEB-INF/header.jsp"/>
    </header>
    <section class="Login">
        <div class="container">
            <form method="POST" action="${pageContext.request.contextPath}/app/login">
                <div class="loginForm">
                    <div class="mt-4">
                        <span>Login</span>
                        <input type="text" name="login" class="form-control">
                    </div>
                    <div class="mt-4">
                        <span>Password</span>
                        <input type="password" name="password" class="form-control">
                    </div>
                    <div class="mt-4 Submit">
                        <input type="submit" value="Login" class="btn btn-primary">
                    </div>
                </div>
            </form>

        </div>
    </section>

    <footer class="footer">
        <jsp:include page="WEB-INF/footer.jsp" />
    </footer>
</body>
</html>
