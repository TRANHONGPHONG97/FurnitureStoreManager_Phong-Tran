<%@ page import="com.cg.model.User" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 7/12/2022
  Time: 9:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List ROLE Management Application</title>
</head>
<body>
<center>
    <h1>List ROLE Management</h1>
    <h2>
        <a href="/role?action=create">Add New Role</a>
    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Role</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
        <%--        <%--%>
        <%--            if(request.getAttribute("listUser")==null){--%>
        <%--                System.out.println("List user is null");--%>
        <%--            }--%>
        <%--        %>--%>
        <c:forEach var="role" items="${listRole}">
            <tr>
                <td><c:out value="${role.getId()}"/></td>
                <td><c:out value="${role.getName()}"/></td>
                <td>
                    <a href="/role?action=edit&id=${role.getId()}">Edit</a>
                    <a href="/role?action=delete&id=${role.getId()}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        <%--        <%--%>
        <%--            String strOut = "";--%>
        <%--            for (User u : (List<User>) request.getAttribute("listUser")) {--%>
        <%--                strOut += "<tr>";--%>
        <%--                strOut += "<td>" + u.getId() +"</td>";--%>
        <%--                strOut += "<td>" + u.getName() +"</td>";--%>
        <%--                strOut += "<td>" + u.getEmail() +"</td>";--%>
        <%--                strOut += "<td>" + u.getCountry() +"</td>";--%>
        <%--                strOut +="</tr>";--%>
        <%--            }--%>

        <%--            out.println(strOut);--%>
        <%--        %>--%>
    </table>

</div>

</body>
</html>
