<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title><fmt:message key="label.products" /></title>
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
<section class="orderItems">
    <div class="container">
        <div class="row names">
            <div class="col">
                <fmt:message key="label.image" />
            </div>
            <div class="col">
                <fmt:message key="label.product.name" />
            </div>
            <div class="col">
                <fmt:message key="label.price" />
            </div>
            <div class="col">
                <fmt:message key="label.category" />
            </div>
            <div class="col">
                <fmt:message key="label.size" />
            </div>
            <div class="col">
                <fmt:message key="label.color" />
            </div>
            <div class="col">
                <fmt:message key="label.action" />
            </div>
        </div>
        <div class="items">
            <c:forEach var="product" items="${products}">
                <div class="row item">
                    <div class="col">
                        <img src="${product.imageLink }" width="120">
                    </div>
                    <div class="col">
                            ${product.name }
                    </div>
                    <div class="col">
                            ${product.price }
                    </div>
                    <div class="col">
                            ${product.category }
                    </div>
                    <div class="col">
                            ${product.size }
                    </div>
                    <div class="col">
                            ${product.color }
                    </div>
                    <div class="col">
                        <a href="${pageContext.request.contextPath }/app/deleteProduct?id=${product.id }"
                           onclick="return confirm('Are you sure?')">
                            <fmt:message key="label.remove" />
                        </a>
                        <a href="${pageContext.request.contextPath }/app/changeProduct?action=open&id=${product.id }">
                            <fmt:message key="label.change" />
                        </a>
                    </div>
                </div>

            </c:forEach>
        </div>

        <div class="addProductButton">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/addProduct"
               role="button"><fmt:message key="label.add.new.product" /></a>
        </div>
        <div class="links">
            <a href="${requestScope['javax.servlet.forward.request_uri']}?page=1"><fmt:message key="label.first.page" /></a>
            <c:if test="${currentPage gt 1}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${currentPage - 1}"
                   id="prevPage"><fmt:message key="label.prev.page" /></a>
            </c:if>
            <c:if test="${currentPage lt pageCount}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${currentPage + 1}"
                   id="nextPage"><fmt:message key="label.next.page" /></a>
            </c:if>
            <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pageCount}"><fmt:message
                    key="label.last.page" /></a>
        </div>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
