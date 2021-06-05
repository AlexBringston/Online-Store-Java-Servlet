<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
<div id="localePlaceHolder">
    <c:if test="${sessionScope.locale == 'english'}">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="${sessionScope.locale == 'ukrainian'}">
        <fmt:setLocale value="uk-UA"/>
    </c:if>
</div>
<fmt:setBundle basename="messages"/>
<section class="changeProduct">
    <div class="container">
        <form method="POST" action="${pageContext.request.contextPath }/app/changeProduct?action=change">
            <div class="form-group">
                <input type="hidden" name="id" value="${product.id}">
                <div class="row">
                    <div class="col-sm-3">
                        <label for="name"><fmt:message key="label.product.name" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="name" id="name"
                               value="${product.name }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="description"><fmt:message key="label.product.description" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="description" id="description"
                               value="${product.description }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="imageLink"><fmt:message key="label.product.image.link" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="imageLink" id="imageLink"
                               value="${product.imageLink }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="price"><fmt:message key="label.price" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="price" id="price"
                               value="${product.price }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="category"><fmt:message key="label.category" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="category" id="category"
                               value="${product.category }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="size"><fmt:message key="label.size" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="size" id="size"
                               value="${product.size }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="color"><fmt:message key="label.color" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="color" id="color"
                               value="${product.color }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/manageProducts"
                           role="button"><fmt:message key="label.return.back" /></a>
                    </div>
                    <div class="col-sm-5">
                        <button type="submit" class="btn btn-primary col-4 mb-2"><fmt:message key="label.confirm.change"
                        /></button>
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
