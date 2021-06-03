<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>Products manager</title>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>

<section class="orderItems">
    <div class="container">
        <div class="row names">
            <div class="col">
                Image
            </div>
            <div class="col">
                Product name
            </div>
            <div class="col">
                Price
            </div>
            <div class="col">
                Category
            </div>
            <div class="col">
                Size
            </div>
            <div class="col">
                Color
            </div>
            <div class="col">
                Action
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
                            Remove
                        </a>
                        <a href="${pageContext.request.contextPath }/app/changeProduct?action=open&id=${product.id }">
                            Change
                        </a>
                    </div>
                </div>

            </c:forEach>
        </div>

        <div class="addProductButton">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/addProduct"
               role="button">Add new product</a>
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
