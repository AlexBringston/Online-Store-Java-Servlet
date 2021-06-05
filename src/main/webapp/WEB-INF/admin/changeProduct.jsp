<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>Change product</title>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<div id="localePlaceHolder">
    <c:if test="${sessionScope.locale == 'english'}">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="${sessionScope.locale == 'ukrainian'}">
        <fmt:setLocale value="uk-UA"/>
    </c:if>
</div>
<fmt:setBundle basename="messages"/>
<section class="changeProduct">
    <div class="container">
        <form method="POST" action="${pageContext.request.contextPath }/app/changeProduct?action=change">
            <div class="form-group">
                <input type="hidden" name="id" value="${requestScope.product.id}">
                <div class="row">
                    <div class="col-sm-3">
                        <label for="name"><fmt:message key="label.product.name" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="name" id="name"
                               value="${requestScope.product.name }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="name"><fmt:message key="label.product.name.uk" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="nameUK" id="nameUK"
                               value="${requestScope.product.nameUK }">
                    </div>

                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="imageLink"><fmt:message key="label.product.image.link" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="imageLink" id="imageLink"
                               value="${requestScope.product.imageLink }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="price"><fmt:message key="label.price" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="price" id="price"
                               value="${requestScope.product.price }">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="category"><fmt:message key="label.category" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <select id="category" class="form-select col-3 form-select-lg mb-3" name="category">
                            <option value="Woman" <c:if test="${(requestScope.product.category == 'Woman') or
                            (requestScope.product.category == 'Жіноча')}">selected</c:if> >
                                <fmt:message key="label.category.woman" />
                            </option>
                            <option value="Man" <c:if test="${(requestScope.product.category == 'Man') or
                            (requestScope.product.category == 'Чоловіча')}">selected</c:if> >
                                <fmt:message key="label.category.man" />
                            </option>
                            <option value="Children" <c:if test="${(requestScope.product.category == 'Children') or
                            (requestScope.product.category == 'Дитяча')}">selected</c:if>>
                                <fmt:message key="label.category.children" />
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="size"><fmt:message key="label.size" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <select id="size" class="form-select col-3 form-select-lg mb-3" name="size">
                            <option value="Small" <c:if test="${(requestScope.product.size == 'Small') or
                            (requestScope.product.size == 'Малий')}">selected</c:if>>
                                <fmt:message key="label.size.small" />
                            </option>
                            <option value="Medium" <c:if test="${(requestScope.product.size == 'Medium') or
                            (requestScope.product.size == 'Середній')}">selected</c:if>>
                                <fmt:message key="label.size.medium" />
                            </option>
                            <option value="Large" <c:if test="${(requestScope.product.size == 'Large') or
                            (requestScope.product.size == 'Великий')}">selected</c:if>>
                                <fmt:message key="label.size.large" />
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="color"><fmt:message key="label.color" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <select id="color" class="form-select col-3 form-select-lg mb-3" name="color">
                            <option value="Red" <c:if test="${(requestScope.product.color == 'Red') or
                            (requestScope.product.color == 'Червоний')}">selected</c:if>>
                                <fmt:message key="label.color.red" />
                            </option>
                            <option value="Green" <c:if test="${(requestScope.product.color == 'Green') or
                            (requestScope.product.color == 'Зелений')}">selected</c:if>>
                                <fmt:message key="label.color.green" />
                            </option>
                            <option value="Blue" <c:if test="${(requestScope.product.color == 'Blue') or
                            (requestScope.product.color == 'Синій')}">selected</c:if>>
                                <fmt:message key="label.color.blue" />
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/manageProducts"
                           role="button"><fmt:message key="label.return.back" /></a>
                    </div>
                    <div class="col-sm-5">
                        <button type="submit" class="btn btn-primary col-4 mb-2"><fmt:message key="label.confirm.change"
                        /></button>
                    </div>

                </div>
            </div>
        </form>
    </div>
</section>

<footer class="footer">
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
