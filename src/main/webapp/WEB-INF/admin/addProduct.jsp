<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>

<section class="changeProduct">
    <div class="container">
        <form method="POST" action="${pageContext.request.contextPath }/app/addProduct?action=add">
            <div class="form-group">
                <input type="hidden" name="id">
                <div class="row">
                    <div class="col-sm-3">
                        <label for="name">Product name:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="name" id="name">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="description">Product description:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="description" id="description">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="imageLink">Product image link:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="imageLink" id="imageLink">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="price">Product price:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="price" id="price">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="category">Product category:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="category" id="category">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="size">Product size:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="size" id="size">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="color">Product color:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="color" id="color">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/manageProducts"
                           role="button">Return back</a>
                    </div>
                    <div class="col-sm-5">
                        <button type="submit" class="btn btn-primary col-4 mb-2">Confirm add</button>
                    </div>

                </div>
            </div>
        </form>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
