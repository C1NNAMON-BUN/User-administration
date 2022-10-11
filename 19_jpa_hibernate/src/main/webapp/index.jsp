<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    <%@include file="/WEB-INF/css/genericStyles.css" %>
    <%@include file="/WEB-INF/css/indexStyles.css" %>
</style>
<body>
<form method="post" action="${pageContext.request.contextPath}/login">
    <title>Login</title>
    <div class='container'>
        <div class='window'>
            <div class='content'>
                <h3 class='welcome'>Edit user</h3>
                <input for="Login" type='text' name="login" id="login"
                       placeholder='Username'
                       class='input-line full-width'/>
                <input for="Password" type='password' name="password"
                       id="Password" placeholder='Password'
                       class='input-line full-width'/> <br><br>
                <button class='ghost-round full-width'>Sign in</button>
                <c:if test="${not empty sessionScope.message}">
                    <div class="alert alert-danger my-4" role="alert">
                            ${sessionScope.message}
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</form>
</body>
</html>
