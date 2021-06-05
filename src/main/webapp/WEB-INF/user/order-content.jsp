<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>Orders</title>
</head>
<body>
<div id="localePlaceHolder">
    <c:if test="${sessionScope.locale == 'english'}">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="${sessionScope.locale == 'ukrainian'}">
        <fmt:setLocale value="uk-UA"/>
    </c:if>
</div>
<fmt:setBundle basename="messages"/>

<header>
    <jsp:include page="../header.jsp"/>
</header>

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
                <fmt:message key="label.size" />
            </div>
            <div class="col">
                <fmt:message key="label.price" />
            </div>
            <div class="col">
                <fmt:message key="label.quantity" />
            </div>
            <div class="col">
                <fmt:message key="label.total" />
            </div>
        </div>
        <div class="items">
            <c:forEach var="orderItem" items="${orderItems}">
                <div class="row item">
                    <div class="col">
                        <img src="${orderItem.product.imageLink }" width="120">
                    </div>
                    <div class="col">
                            ${orderItem.product.name }
                    </div>
                    <div class="col">
                            ${orderItem.product.size }
                    </div>
                    <div class="col">
                            ${orderItem.product.price }
                    </div>
                    <div class="col">
                            ${orderItem.quantity }
                    </div>
                    <div class="col">
                            ${orderItem.product.price * orderItem.quantity }
                    </div>
                </div>

            </c:forEach>
        </div>
        <div class="totalAmount">
            <fmt:message key="label.total" />: ${totalCost}
        </div>

        <div class="links">
            <a href="${requestScope['javax.servlet.forward.request_uri']}?id=${orderId}&page=1"><fmt:message key="label.first.page" /></a>
            <c:if test="${currentPage gt 1}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?id=${orderId}&page=${currentPage - 1}"
                   id="prevPage"><fmt:message key="label.prev.page" /></a>
            </c:if>
            <c:if test="${currentPage lt pageCount}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?id=${orderId}&page=${currentPage + 1}"
                   id="nextPage"><fmt:message key="label.next.page" /></a>
            </c:if>
            <a href="${requestScope['javax.servlet.forward.request_uri']}?id=${orderId}&page=${pageCount}"><fmt:message key="label.last.page" /></a>
        </div>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
