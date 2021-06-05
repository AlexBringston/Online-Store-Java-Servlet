<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div id="localePlaceHolder">
    <c:if test="${sessionScope.locale == 'english'}">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="${sessionScope.locale == 'ukrainian'}">
        <fmt:setLocale value="uk-UA"/>
    </c:if>
</div>
<fmt:setBundle basename="messages"/>

<div id="productCatalog" class="panel panel-success">
    <div class="panel-heading">Product categories</div>
    <div class="list-group">
        <c:forEach var="category" items="${requestScope.categories}">
            <a
                    href="${pageContext.request.contextPath}/app/products/category/${category.name}"
               class="list-group-item">
                <span>${category.name}</span>
            </a>
        </c:forEach>
    </div>
    <br>
    <div class="panel-heading">Product colors</div>
    <div class="list-group">
        <c:forEach var="color" items="${colors}">
            <a
                    href="${pageContext.request.contextPath}/app/products/color/${color.name}" class="list-group-item">
                <span>${color.name}</span>
            </a>
        </c:forEach>
    </div>
    <br>
    <div class="panel-heading">Product sizes</div>
    <div class="list-group">
        <c:forEach var="size" items="${sizes}">
            <a
                    href="${pageContext.request.contextPath}/app/products/size/${size.name}" class="list-group-item">
                <span>${size.name}</span>
            </a>
        </c:forEach>
    </div>
</div>