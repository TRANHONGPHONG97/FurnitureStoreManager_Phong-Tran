<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>USER MANAGER</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="../../assets/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
    <!-- or -->
    <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">

    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

</head>

<body onload="time()" class="app sidebar-mini rtl">
<!-- Navbar-->
<header class="app-header">
    <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                    aria-label="Hide Sidebar"></a>
    <!-- Navbar Right Menu-->
    <ul class="app-nav">


        <!-- User Menu-->
        <li><a class="app-nav__item" href="/logout">Logout <i class='bx bx-log-out bx-rotate-180'></i> </a>
        </li>
    </ul>
</header>
<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="/images/anhbo.jpg" width="50px"
                                        alt="User Image">
    </div>
    <hr>
    <ul class="app-menu">
    <c:if test="${acc.getIdrole() == 1}">
        <li><a class="app-menu__item" href="/user_manager"><i class='app-menu__icon bx bx-id-card'></i>
            <span class="app-menu__label">User Management</span></a></li>
    </c:if>
        <li><a class="app-menu__item" href="/product"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span
                class="app-menu__label">Product Management</span></a>
        </li>
        <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-task'></i><span
                class="app-menu__label">Order management</span></a></li>
        <li><a><img src="/images/anh3.jpg" alt="" style="height: 380px; width: 230px"></a></li>
    </ul>
</aside>
<main class="app-content">
    <div class= "col-sm-12" style="background-color: chocolate">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item"><h3 ><b >CHÀO MỪNG BẠN ĐẾN VỚI CỬA HÀNG TRANG TRÍ NỘI THẤT PHONG TRẦN</b></h3 ></li>
        </ul>
        <div id="clock"></div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">

                    <div class="row element-button">
                        <div class="col-sm-2">

                            <a style="font-size: larger" class="btn btn-outline-success" href="user_manager?action=create" title="Thêm"><i
                                    class="fas fa-plus"></i>
                                Create new user</a>
                        </div>
                        <div class="col-sm-4"></div>
                        <div class="formm col-sm-5" style="
                                           border: 1px solid black; border-radius: 3px; background-color: limegreen;
                                            font-weight: bold">
                            <form action="user_manager" style="padding: 5px;">
                                Search: <input placeholder="search" type="text" hint="search" value="${requestScope.q}" name="q"> Filter:
                                <select name="idrole" id="">
                                    <option value="-1">All</option>
                                    <c:forEach items="${applicationScope.listRole}" var="role">
                                        <c:choose>
                                            <c:when test="${role.getId() == requestScope.idrole}">
                                                <option selected value="${role.getId()}">${role.getName()}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${role.getId()}">${role.getName()}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
<%--                                <button  class="btn btn-add btn-sm" type="get">Submit</button>--%>
<%--                                <button type="submit" class="btn btn-primary"><span class="bi-search"></span> Search</button>--%>
                                <button type="get" class="btn btn-primary"><span class="fa fa-search"></span> Submit</button>
                            </form>
                        </div>
                    </div>
                    <table style=" text-align: center" class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0"
                           border="0"
                           id="sampleTable">
                        <thead>
                        <tr >
                            <th>#</th>
                            <th>USER NAME</th>
                            <th>PASSWORD</th>
                            <th>PHONE</th>
                            <th>EMAIL</th>
                            <th>ROLE</th>
                            <th>ACTION</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${requestScope.list}">

                            <tr>
                                <td><c:out value="${user.getIdUser()}"/></td>
                                <td><c:out value="${user.getUserName()}"/></td>
                                <td><c:out value="${user.getPassword()}"/></td>
                                <td><c:out value="${user.getPhone()}"/></td>
                                <td><c:out value="${user.getEmail()}"/></td>

                                <td>
                                    <c:if test="${user.getIdrole() == 1}">
                                        <c:out value="admin"/>
                                    </c:if>
                                    <c:if test="${user.getIdrole() == 2}">
                                        <c:out value="user"/>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="/user_manager?action=edit&id=${user.getIdUser()}"
                                       class="fas fa-edit"> Edit </a>
                                    <a onclick="showMessage(${user.getIdUser()})"
                                       class="fas fa-trash-alt"> Delete</a>
                                </td>

                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>
                    <nav class= col-sm-12 style="display: flex; justify-content: center" aria-label="Page navigation example">
                        <ul class="pagination">
                            <c:if test="${requestScope.currentPage != 1}">
                                <li class="page-item ">
                                    <a class="page-link " href="user_manager?page=${requestScope.currentPage - 1}" style="background-color: aquamarine">Previous</a>
                                </li>
                            </c:if>

                            <%--Để hiển thị số Trang.
                            Điều kiện khi không hiển thị liên kết cho trang hiện tại--%>

                            <c:forEach begin="1" end="${noOfPages}" var="i">
                                <c:choose>
                                    <c:when test="${requestScope.currentPage eq i}">
                                        <li class="page-item "><a class="page-link" href="user_manager?page=${i}">${i}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item ">
                                            <a class="page-link"
                                               href="user_manager?page=${i}">${i}</a> </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <%--Để hiển thị liên kết Tiếp theo--%>

                            <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                <li class="page-item ">
                                    <a class="page-link" href="user_manager?page=${requestScope.currentPage + 1}" style="background-color: aquamarine">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</main>

<!-- Essential javascripts for application to work-->
<script src="../../assets/js/jquery-3.2.1.min.js"></script>
<script src="../../assets/js/popper.min.js"></script>
<script src="../../assets/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="src/jquery.table2excel.js"></script>
<script src="../../assets/js/main.js"></script>
<!-- The javascript plugin to display page loading on top-->
<script src="../../assets/js/plugins/pace.min.js"></script>
<!-- Page specific javascripts-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<!-- Data table plugin-->
<script type="text/javascript" src="../../assets/js/plugins/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../../assets/js/plugins/dataTables.bootstrap.min.js"></script>
<script>
    function showMessage(id) {
        if (confirm("Bạn có muốn xóa người dùng này không?") === true){
            window.location.href ="/user_manager?action=delete&id=" +id;
        }
    }
</script>
</body>

</html>