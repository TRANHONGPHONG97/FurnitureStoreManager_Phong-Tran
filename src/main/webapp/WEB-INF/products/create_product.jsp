<!DOCTYPE html>
<html lang="en">
<head>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
          integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="../../assetss/list.css">
    <link rel="stylesheet" href="../../assetss/create.css">

    <title>Create Product</title>
</head>
<body>
<div class="container">
    <div class="row header">
        <div class="col-sm-6 header--left">
            <h1>Create New Product</h1>
        </div>
        <div class="col-sm-2"></div>
    </div>
    <div class="row">
        <div class="col-sm-12 padding-0">
            <form action="" method="post">
                <c:if test="${requestScope.product != null}">
                    <input type="hidden" name="id" value="<c:out value='${product.getId()}' />"/>
                </c:if>
                <div class="row">
                    <div class="col-sm-12">
                        <label>PRODUCT NAME</label><input type="text" name="name" id="name" size="45"
                                                          value="${product.getName() }"/>
                        <label>IMAGE</label><input type="text" name="image" value="${product.getImage()}" size="45"/>
                        <label>PRICE</label><input type="text" name="price" id="price" size="45"
                                                   value="${product.getPrice() }"/>
                        <label>QUANTITY</label><input type="number" name="quantity" id="quantity" size="45"
                                                      value="${product.getQuantity() }"/>
                        <label>CATEGORY</label>
                        <select name="category_id">
                            <c:forEach items="${listCategory}" var="category">
                                <option value="${category.getId()}">${category.getName()}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <br>
                    </div>
                    <div class="col-sm-1"><input type="submit" class="btn btn-outline-success "
                                                 style="color: black; font-weight: bold" title="Th??m" value="Create">
                        <div class="col-sm-5"></div>
                    </div>
                    <div class="btn-group">
                        <a href="/product" title="Quay l???i" class="btn btn-outline-info" style="color: black; font-weight: bold">
                            <i class="glyphicon glyphicon-floppy-disk" aria-hidden="true" ></i> Back
                        </a>
                    </div>
                </div>

            </form>
        </div>
        <div class="footer" style="margin-left: 200px">
            <c:if test="${requestScope['success'] == true}">
                <ul class="success">
                    <li style="color: darkgreen; font-weight: bold">Th??m m???i th??nh c??ng</li>
                </ul>
            </c:if>
            <c:if test="${!requestScope['errors'].isEmpty()}">
                <ul class="error">
                    <c:forEach items="${requestScope['errors']}" var="item">
                        <li style="color: red; font-weight: bold">${item}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </div>
</div>

</body>
<%--<div class="page-error" id="errors">--%>
<%--    ${errors}--%>
<%--</div>--%>
</html>
