<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<section class="users">
    <div class="container">
        <div class="row names">
            <div class="col">
                Login
            </div>
            <div class="col">
                First name
            </div>
            <div class="col">
                Last name
            </div>
            <div class="col">
                When was created
            </div>
            <div class="col">
                Role
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
            <c:forEach var="user" items="${users}">
                <div class="row item">
                    <div class="col">
                            ${user.login }
                    </div>
                    <div class="col">
                            ${user.firstName }
                    </div>
                    <div class="col">
                            ${user.lastName }
                    </div>
                    <div class="col">
                        <fmt:parseDate value="${user.createdAt }" var="dateValue" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy"/>
                        <br>
                        <fmt:formatDate value="${dateValue}" pattern="hh:mm a"/>
                    </div>
                    <div class="col">
                            ${user.role }
                    </div>
                    <div class="col">
                            ${user.status}
                    </div>
                    <div class="col">
                        <c:if test="${user.status == 'ACTIVATED'}">
                            <a href="${pageContext.request.contextPath}/app/changeUserStatus?id=${user.id}"
                               class="btn btn-outline-danger">Block</a>
                        </c:if>
                        <c:if test="${user.status == 'BLOCKED'}">
                            <a href="${pageContext.request.contextPath}/app/changeUserStatus?id=${user.id}"
                               class="btn btn-outline-success">Activate</a>
                        </c:if>
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
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
