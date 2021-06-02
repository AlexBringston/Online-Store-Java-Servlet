<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
    <div class="row">
        <div class="col-md-3 mx-auto Name">
            <a href="${pageContext.request.contextPath}/app/products">Green line shop</a>
        </div>
        <div class="col-md-7 mx-auto">
            <c:out value="${sessionScope.userRole}"/>
            ${requestScope['javax.servlet.forward.request_uri']}?${pageContext.request.queryString}
        </div>
        <div class="col-md-2 mx-auto Icon">
            <div>
                <a href="${pageContext.request.contextPath}/app/authorization">
                    <img src="https://drive.google.com/uc?export=view&id=1rZT7voIJ3Wgx8OZkmlVWSNfjJYBIV_vS" alt="Account"/>
                </a>
            </div>

        </div>
    </div>

</div>
