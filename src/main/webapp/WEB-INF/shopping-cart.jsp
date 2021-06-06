<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
    <title>Cart Page</title>
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
    <jsp:include page="${pageContext.request.contextPath}/WEB-INF/header.jsp"/>
</header>
<section class="cart">
    <div class="container">
        <div class="row names">
            <div class="col">
                <fmt:message key="label.image"/>
            </div>
            <div class="col">
                <fmt:message key="label.product.name"/>
            </div>
            <div class="col">
                <fmt:message key="label.size"/>
            </div>
            <div class="col">
                <fmt:message key="label.price"/>
            </div>
            <div class="col">
                <fmt:message key="label.quantity"/>
            </div>
            <div class="col">
                <fmt:message key="label.total"/>
            </div>
            <div class="col">
                <fmt:message key="label.action"/>
            </div>
        </div>
        <c:set var="total" value="0"/>
        <c:set var="i" value="0"/>
        <form method="POST" action="${pageContext.request.contextPath }/app/order">
            <div class="items">
                <c:forEach var="item" items="${sessionScope.cart }">
                    <c:set var="i" value="${i + 1 }"/>
                    <div class="row item">
                        <div class="col">
                            <img src="${item.product.imageLink }" width="120">
                        </div>
                        <div class="col">
                                ${item.product.name }
                        </div>
                        <div class="col">
                                ${item.product.size }
                        </div>
                        <div class="col">
                                ${item.product.price }
                        </div>
                        <div class="col">
                            <input type="number" name="quantity" min="1" step="1" max="20" onkeydown="return false"
                                   id="quantity_${i}" onchange="countTotal([${item.product.price},
                                ${i}])"
                                   value="${item.quantity }" />
                        </div>
                        <div class="col localTotalPrice" id="localTotalPrice_${i}">
                                ${item.product.price * item.quantity }
                        </div>
                        <div class="col">
                            <a href="${pageContext.request.contextPath }/app/cart?action=remove&id=${item.product.id }"
                               onclick="return confirm('Are you sure?')"><fmt:message key="label.remove"/></a>
                        </div>
                    </div>

                </c:forEach>
            </div>

            <div class="Total">
                <a href="${pageContext.request.contextPath }/app/products"><fmt:message key="label.continue"/></a>
                <span id="totalAmount">><fmt:message key="label.total"/>: ${total}</span>
                <c:if test="${sessionScope.userRole == 'CLIENT'}">
                    <input type="submit" class="btn btn-outline-success" value="<fmt:message key="label.make.order"/>">
                </c:if>
            </div>
        </form>


    </div>
</section>

<footer class="footer">
    <jsp:include page="${pageContext.request.contextPath}/WEB-INF/footer.jsp"/>
</footer>
</body>
</html>