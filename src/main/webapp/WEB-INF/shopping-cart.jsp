<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>Cart Page</title>
</head>
<body>
<header>
    <jsp:include page="${pageContext.request.contextPath}/WEB-INF/header.jsp"/>
</header>
<section class="cart">
    <div class="container">
        <div class="row names">
            <div class="col">
                Image
            </div>
            <div class="col">
                Product name
            </div>
            <div class="col">
                Size
            </div>
            <div class="col">
                Price
            </div>
            <div class="col">
                Quantity
            </div>
            <div class="col">
                Total
            </div>
            <div class="col">
                Action
            </div>
        </div>
        <c:set var="total" value="0"/>
        <div class="items">
            <c:forEach var="item" items="${sessionScope.cart }">
                <c:set var="total" value="${total + item.product.price * item.quantity }"/>
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
                        <input type="number" min="1" step="1" max="20" onkeydown="return false"
                               value="${item.quantity }" />
                    </div>
                    <div class="col">
                            ${item.product.price * item.quantity }
                    </div>
                    <div class="col">
                        <a href="${pageContext.request.contextPath }/app/cart?action=remove&id=${item.product.id }"
                           onclick="return confirm('Are you sure?')">Remove</a>
                    </div>
                </div>

            </c:forEach>
        </div>

        <div class="Total">
            <a href="${pageContext.request.contextPath }/app/products">Continue Shopping</a>
            <span>Total ${total}</span>
            <c:if test="${sessionScope.userRole == 'CLIENT'}">
                <a href="${pageContext.request.contextPath }/app/order">Make order</a>
            </c:if>
        </div>

    </div>
</section>

<footer class="footer">
    <jsp:include page="${pageContext.request.contextPath}/WEB-INF/footer.jsp"/>
</footer>
</body>
</html>