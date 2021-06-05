<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>

    <title>Products</title>
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
<div class="container">
    <div class="row">
        <aside class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
            <jsp:include page="${pageContext.request.contextPath}/WEB-INF/aside.jsp"/>
        </aside>
        <main class="col-xs-12 col-sm-8 col-md-9 col-lg-10 products" id="products" data-page-count="${pageCount}"
              data-page-number="${currentPage}">
            <div class="container">
                <div class="menu-bar">
                    <form method="get" onsubmit="">
                        <div class="menu-row">
                            <div class="col-3">
                                <label for="sortType"><fmt:message key="label.sort.by"/>:</label>
                                <select class="form-select col-3 form-select-lg" name="parameter" id="sortType"
                                        size="1">
                                    <option value="name"
                                            <c:if test="${sortWay == 'name'}">selected</c:if>  ><fmt:message
                                            key="label.sort.name"/>
                                    </option>
                                    <option value="price"
                                            <c:if test="${sortWay == 'price'}">selected</c:if> ><fmt:message
                                            key="label.sort.price"/>
                                    </option>
                                    <option value="novelty"
                                            <c:if test="${sortWay == 'novelty'}">selected</c:if> ><fmt:message
                                            key="label.sort.novelty"/></option>
                                    <option value="category"
                                            <c:if test="${sortWay == 'category'}">selected</c:if> ><fmt:message
                                            key="label.sort.category"/></option>
                                    <option value="color"
                                            <c:if test="${sortWay == 'color'}">selected</c:if> ><fmt:message
                                            key="label.sort.color"/></option>
                                    <option value="size"
                                            <c:if test="${sortWay == 'size'}">selected</c:if> ><fmt:message
                                            key="label.sort.size"/></option>
                                </select>
                            </div>
                            <div class="col-3">
                                <label for="sortDirection"><fmt:message key="label.sort.direction"/>:</label>
                                <select class="form-select col-3 form-select-lg" name="sortDirection" id="sortDirection"
                                        size="1">
                                    <option value="ascending"
                                            <c:if test="${sortDirection == 'ascending'}">selected</c:if> ><fmt:message
                                            key="label.sort.ascending"/>
                                    </option>
                                    <option value="descending"
                                            <c:if test="${sortDirection == 'descending'}">selected</c:if> ><fmt:message
                                            key="label.sort.descending"/>
                                    </option>
                                </select>
                            </div>

                            <button type="submit" class="btn col-3 btn-primary"><fmt:message key="label.sort"/></button>
                        </div>


                    </form>

                </div>
                <div class="row">
                    <c:forEach var="p" items="${products }">
                        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xlg-2">
                            <div id="product${p.id }" class="panel panel-default product">
                                <div class="panel-body">
                                    <div class="thumbnail">
                                        <img src="${p.imageLink}" alt="${p.name }">

                                    </div>
                                    <h4 class="name">${p.name }</h4>
                                    <div class="line">
                                        <div class="code"><fmt:message key="label.code"/>: ${p.id }</div>
                                        <a class="btn btn-primary pull-right buy-btn"
                                           href="${pageContext.request.contextPath }/app/cart?&action=buy&id=${p.id }"
                                           data-id-product="${p.id }"><fmt:message key="label.buy"/>
                                        </a>
                                    </div>

                                    <div class="price">₴ ${p.price }</div>

                                    <div class="list-group">
    <span class="list-group-item"><small><fmt:message key="label.category"/>:</small><span
            class="category">${p.category }</span></span>
                                        <span class="list-group-item"><small><fmt:message
                                                key="label.size"/>:</small><span
                                                class="size">${p.size }</span></span>
                                        <span
                                                class="list-group-item"><small><fmt:message key="label.color"/>:</small><span
                                                class="size">${p.color }</span></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="links">
                    <a
                            href="${requestScope['javax.servlet.forward.request_uri']}?page=1&parameter=${sortWay}&sortDirection=${sortDirection}"><fmt:message
                            key="label.first.page"/></a>
                    <c:if test="${currentPage gt 1}">
                        <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${currentPage - 1}&parameter=${sortWay}&sortDirection=${sortDirection}"
                           id="prevPage"><fmt:message key="label.prev.page"/></a>
                    </c:if>
                    <c:if test="${currentPage lt pageCount}">
                        <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${currentPage + 1}&parameter=${sortWay}&sortDirection=${sortDirection}"
                           id="nextPage"><fmt:message key="label.next.page"/></a>
                    </c:if>
                    <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pageCount}&parameter=${sortWay}&sortDirection=${sortDirection}"><fmt:message
                            key="label.last.page"/></a>
                </div>

            </div>
        </main>
    </div>

</div>

<footer class="footer">
    <jsp:include page="${pageContext.request.contextPath}/WEB-INF/footer.jsp"/>
</footer>
</body>
</html>

