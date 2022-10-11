<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file="/WEB-INF/css/genericStyles.css" %>
    <%@include file="/WEB-INF/css/user-pageStyles.css" %>
</style>
<html>
<head>
    <title>User Info</title>
</head>
<body>
<div class='container'>
    <div class='window'>
        <h1 style="text-align:center">Hello, ${name}!</h1>
        <p style="text-align:center">Click <a href="${pageContext.request.contextPath}/logout">here</a> to log out</p>
    </div>
</div>
</body>
</html>