<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title><fmt:message key="label.orders" /></title>
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
<section class="Orders">
    <div class="container">
        <div class="row names">
            <div class="col">
                <fmt:message key="label.order.id" />
            </div>
            <div class="col">
                <fmt:message key="label.user" />
            </div>
            <div class="col">
                <fmt:message key="label.order.date" />
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
            <c:forEach var="order" items="${orders}">
            <form method="POST" action="${pageContext.request.contextPath}/app/manageOrders">
                <input type="hidden" value="${order.id}" name="id"/>
                <div class="row item">
                    <div class="col">
                            ${order.id}
                    </div>
                    <div class="col">
                            ${order.user.login}
                    </div>
                    <div class="col">
                        <fmt:parseDate value="${order.createdAt}" var="dateValue" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy hh:mm a"/>
                    </div>

                    <div class="col">
                        <select class="form-select" size="1" name="status">
                            <option value="REGISTERED"
                                    <c:if test="${order.status == 'REGISTERED'}">selected</c:if>  > <fmt:message
                                    key="label.registered" />
                            </option>
                            <option value="PAID"
                                    <c:if test="${order.status == 'PAID'}">selected</c:if> > <fmt:message
                                    key="label.paid" />
                            </option>
                            <option value="CANCELLED"
                                    <c:if test="${order.status == 'CANCELLED'}">selected</c:if> ><fmt:message
                                    key="label.cancelled" /></option>
                        </select>
                    </div>
                    <div class="col">
                        <input type="submit" class="btn btn-outline-secondary" value="<fmt:message key="label.submit.status" />"/>
                    </div>
                </div>
            </form>
            </c:forEach>
        </div>
        <div class="links">
            <a href="${requestScope['javax.servlet.forward.request_uri']}?page=1"><fmt:message key="label.first.page"
            /></a>
            <c:if test="${currentPage gt 1}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${currentPage - 1}"
                   id="prevPage"><fmt:message key="label.prev.page" /></a>
            </c:if>
            <c:if test="${currentPage lt pageCount}">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${currentPage + 1}"
                   id="nextPage"><fmt:message key="label.next.page" /></a>
            </c:if>
            <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pageCount}"><fmt:message
                    key="label.next.page" /></a>
        </div>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>

</body>
</html>
