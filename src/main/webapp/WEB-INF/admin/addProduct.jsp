<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <title>Add product</title>
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

<section class="changeProduct">
    <div class="container">
        <form method="POST" action="${pageContext.request.contextPath }/app/addProduct?action=add">
            <div class="form-group">
                <input type="hidden" name="id">
                <div class="row">
                    <div class="col-sm-3">
                        <label for="name"><fmt:message key="label.product.name" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="name" id="name" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="nameUK"><fmt:message key="label.product.name.uk" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="nameUK"
                               id="nameUK" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="imageLink"><fmt:message key="label.product.image.link" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="imageLink" id="imageLink" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="price"><fmt:message key="label.price" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" class="form-control form-control-lg mb-3" name="price" id="price" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="category"><fmt:message key="label.category" />:</label>
                    </div>
                    <div class="col-sm-5">
                        <select id="category" class="form-select col-3 form-select-lg mb-3" name="category">
                            <option value="Woman-Жіноча">
                                <fmt:message key="label.category.woman" />
                            </option>
                            <option value="Man-Чоловіча">
                                <fmt:message key="label.category.man" />
                            </option>
                            <option value="Children-Дитяча">
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
                            <option value="Small">
                                <fmt:message key="label.size.small" />
                            </option>
                            <option value="Medium">
                                <fmt:message key="label.size.medium" />
                            </option>
                            <option value="Large">
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
                            <option value="Red">
                                <fmt:message key="label.color.red" />
                            </option>
                            <option value="Green">
                                <fmt:message key="label.color.green" />
                            </option>
                            <option value="Blue">
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
                        <button type="submit" class="btn btn-primary col-4 mb-2"><fmt:message key="label.confirm.add" /></button>
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
