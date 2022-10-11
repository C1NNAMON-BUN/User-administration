<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tag" uri="/WEB-INF/tld/UserListTagDescriptor.tld" %>
<jsp:useBean id="userDao" class="com.nix.zhylina.dao.impl.HibernateUserDao"/>

<html>
<style>
    <%@include file="/WEB-INF/css/adminPageStyles .css" %>
    <%@include file="/WEB-INF/css/genericStyles.css" %>

</style>
<head>
    <title>Admin page</title>
</head>
<body>
<h2 align="right">Admin ${name} (<a
        href="${pageContext.request.contextPath}/logout">Logout</a>)</h2>

<h4 style="margin-left: 480px; margin-top: 5%;">
    <a href="${pageContext.request.contextPath}/admin/add-user" class="button">Add
        new user</a></h4>
<table id="cells">
    <tr>
        <th id="outer">Login</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Age</th>
        <th>Role</th>
        <th>Action</th>
    </tr>
    <tbody>
    <tr>
        <tag:UserListTag users="${userDao.findAll()}"/>
    </tbody>
</table>
<c:if test="${not empty sessionScope.errorDuringDeletion}">
    <script type="text/javascript">
        alert("${sessionScope.errorDuringDeletion}");
    </script>
</c:if>
</body>
</html>
