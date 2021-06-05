<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>User page</title>
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
<section class="User">
    <div class="container">
        <h1>
            Hello <c:out value="${sessionScope.user.firstName}"/>
        </h1>

        <h1>
            Current balance: <c:out value="${sessionScope.user.balance}"/>
        </h1>
        <br/>
        <div class="row names">
            <div class="col">
                Order id
            </div>
            <div class="col">
                When order was made
            </div>
            <div class="col">
                Status
            </div>
            <div class="col">
                Action
            </div>
        </div>
        <div class="items">
            <jsp:useBean id="dateValue" class="java.util.Date"/>
            <c:forEach var="order" items="${orders}">
                <div class="row item">
                    <div class="col">
                            ${order.id}
                    </div>
                    <div class="col">
                        <fmt:parseDate value="${order.createdAt}" var="dateValue" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy hh:mm a"/>
                    </div>
                    <div class="col">
                            ${order.status}
                    </div>
                    <div class="col">
                        <a href="${pageContext.request.contextPath }/app/showOrder?id=${order.id}">
                            Show order details
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="links">
            <a href="${requestScope['javax.servlet.forward.request_uri']}?page=1">First
                page</a>
            <c:if test="${currentPage gt 1}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${currentPage - 1}"
                   id="prevPage">Previous page</a>
            </c:if>
            <c:if test="${currentPage lt pageCount}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${currentPage + 1}"
                   id="nextPage">Next page</a>
            </c:if>
            <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pageCount}">Last page</a>
        </div>
        <div class="Total">
            <a href="${pageContext.request.contextPath}/app/cart">Shopping cart</a>
            <a href="${pageContext.request.contextPath}/app/logout">Logout</a>
        </div>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>

</body>
</html>