
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Role Management Application</title>
</head>
<body>
<center>
    <h1>Role Management</h1>
    <h2>
        <%--    <a href="users?action=users">List All Users</a>--%>
        <a href="role">List All Role</a>
    </h2>
</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>Add New ROLE</h2>
            </caption>
            <tr>
                <th>ROLE Name:</th>
                <td>
                    <input type="text" name="name" id="name" size="45"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save"/>
                </td>

            </tr>
        </table>
    </form>
</div>
</body>
</html>

