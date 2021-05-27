<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <div class="col-md-2 mx-auto Name">
            <a href="${pageContext.request.contextPath}/app/products">Green line shop</a>
        </div>
        <div class="col-md-8 mx-auto">
            <div class="input-group">
                <input class="form-control border-end-0 border" type="search" placeholder="search..."
                       id="example-search-input">
                <span class="input-group-append">
                    <button class="btn btn-outline-secondary bg-white border-start-0 border-bottom-0 border ms-n5"
                            type="button">
                        <i class="fa fa-search"></i>
                    </button>
                </span>
            </div>
        </div>
        <div class="col-md-2 mx-auto Icon">
            <div>
                <a href="${pageContext.request.contextPath}/app/authorization">
                    <img src="${pageContext.request.contextPath}/img/user-icon.svg" alt="Account"/>
                </a>
            </div>

        </div>
    </div>

</div>
