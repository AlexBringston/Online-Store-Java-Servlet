<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
<div id="localePlaceHolder">
    <c:if test="">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="">
        <fmt:setLocale value="en"/>
    </c:if>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-4 mx-auto Name">
            <a href="${pageContext.request.contextPath}/app/products">Green line shop</a>
        </div>
        <div class="col-md-3 mx-auto">
        </div>
<%--        <div class="col-md-1 mx-auto" >--%>
<%--            <select size="1" class="" id="locale" onchange="document.submit()">--%>
<%--                <option value="english">--%>
<%--                    ENG--%>
<%--                </option>--%>
<%--                <option value="ukrainian">--%>
<%--                    UA--%>
<%--                </option>--%>
<%--            </select>--%>
<%--        </div>--%>
        <div class="col-md-1 Icon">
            <div>
                <a href="${pageContext.request.contextPath}/app/cart">
                    <img src="https://i.imgur.com/pDQKt4y.png" alt="Cart"/>
                </a>
            </div>
        </div>
        <div class="col-md-1 Icon">
            <div>
                <a href="${pageContext.request.contextPath}/app/authorization">
                    <img src="https://drive.google.com/uc?export=view&id=1rZT7voIJ3Wgx8OZkmlVWSNfjJYBIV_vS" alt="Account"/>
                </a>
            </div>

        </div>
    </div>

</div>
