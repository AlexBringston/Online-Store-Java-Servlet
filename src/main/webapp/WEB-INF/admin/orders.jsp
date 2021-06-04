<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>Manage orders</title>
</head>
<body>

<header>
    <jsp:include page="../header.jsp"/>
</header>
<section class="Orders">
    <div class="container">
        <div class="row names">
            <div class="col">
                Order id
            </div>
            <div class="col">
                User
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
                                    <c:if test="${order.status == 'REGISTERED'}">selected</c:if>  > Registered
                            </option>
                            <option value="PAID"
                                    <c:if test="${order.status == 'PAID'}">selected</c:if> > Paid
                            </option>
                            <option value="CANCELLED"
                                    <c:if test="${order.status == 'CANCELLED'}">selected</c:if> >Cancelled</option>
                        </select>
                    </div>
                    <div class="col">
                        <input type="submit" class="btn btn-outline-secondary" value="Submit new status"/>
                    </div>
                </div>
            </form>
            </c:forEach>
        </div>
        <div class="links">
            <a
                    href="${requestScope['javax.servlet.forward.request_uri']}?page=1">First
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
