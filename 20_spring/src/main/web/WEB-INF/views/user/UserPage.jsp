<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="en">
<head>
    <style>
        <%@include file="/WEB-INF/css/genericStyles.css" %>
        <%@include file="/WEB-INF/css/user-pageStyles.css" %>
    </style>
    <title>User Page</title>
</head>
<body>
    <div class='container'>
        <div class='window'>
            <h1 style="text-align:center">Hello, ${currentUser.getFirstName()}!</h1>
            <p style="text-align:center">Click <a href="${pageContext.request.contextPath}/logout">here</a> to log out</p>
        </div>
    </div>
</body>
</html>
