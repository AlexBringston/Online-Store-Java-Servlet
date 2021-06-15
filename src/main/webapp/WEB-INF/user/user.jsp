<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix = "ex" uri ="/WEB-INF/MyTags.tld"%>
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
        <ex:Hello>
            <h1><fmt:message key="label.hello" /> <c:out value="${sessionScope.user.firstName}"/></h1>
        </ex:Hello>
        <h1>
            <fmt:message key="label.current.balance" />: <c:out value="${sessionScope.user.balance}"/>
        </h1>
        <br/>
        <button type="button" class="btn btn-secondary btn-lg" id="depositButton">Make a deposit</button>
        <form action="${pageContext.request.contextPath}/app/makeDeposit" id="depositForm" class="depositForm">
            <label for="amount"></label><input type="number" id="amount" name="amount" min="1" max="10000"
                                               required/>
            <input type="submit" class="btn btn-outline-success" value="Submit">
        </form>
        <div class="row names">
            <div class="col">
                <fmt:message key="label.order.id" />
            </div>
            <div class="col">
                <fmt:message key="label.when.order.was.made" />
            </div>
            <div class="col">
                <fmt:message key="label.status" />
            </div>
            <div class="col">
                <fmt:message key="label.action" />
            </div>
        </div>
        <div class="items">
            <jsp:useBean id="dateValue" class="java.util.Date"/>
            <c:forEach var="order" items="${requestScope.orders}">
                <div class="row item">
                    <div class="col">
                            ${order.id}
                    </div>
                    <div class="col">
                        <fmt:parseDate value="${sessionScope.user.createdAt }" var="dateValue"
                                       pattern="yyyy-MM-dd HH:mm:ss"/>
                        <fmt:formatDate value="${dateValue}" type="date"/>
                        <fmt:formatDate value="${dateValue}" timeStyle = "short" type="time"/>
                    </div>
                    <div class="col">
                            ${order.status}
                    </div>
                    <div class="col">
                        <a href="${pageContext.request.contextPath }/app/showOrder?id=${order.id}">
                            <fmt:message key="label.show.order.details" />
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="links">
            <a href="${requestScope['javax.servlet.forward.request_uri']}?page=1"><fmt:message key="label.first.page" /></a>
            <c:if test="${requestScope.currentPage gt 1}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${requestScope.currentPage - 1}"
                   id="prevPage"><fmt:message key="label.prev.page" /></</a>
            </c:if>
            <c:if test="${requestScope.currentPage lt requestScope.pageCount}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${requestScope.currentPage + 1}"
                   id="nextPage"><fmt:message key="label.next.page" /></a>
            </c:if>
            <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${requestScope.pageCount}"><fmt:message key="label.last.page" /></a>
        </div>
        <div class="Total">
            <a href="${pageContext.request.contextPath}/app/cart"><fmt:message key="label.shopping.cart" /></a>
            <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logout" /></a>
        </div>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>
<script src="https://code.jquery.com/jquery-3.6.0.slim.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>