<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="localePlaceHolder">
    <c:if test="${sessionScope.locale == 'english'}">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="${sessionScope.locale == 'ukrainian'}">
        <fmt:setLocale value="uk-UA"/>
    </c:if>
</div>
<fmt:setBundle basename="messages"/>
<div class="container">
    <div class="row">
        <div class="col-md-4 mx-auto Name">
            <a href="${pageContext.request.contextPath}/app/products">Green line shop</a>
        </div>

        <div class="col-md-6 mx-auto">
        </div>
        <div class="col-md-2 mx-auto Text">
            <span><fmt:message key="label.all.rights.reserved" /></span>
        </div>
    </div>

</div>
