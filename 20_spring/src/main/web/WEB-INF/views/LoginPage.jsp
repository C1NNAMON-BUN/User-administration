<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="en">
<head>
    <style>
        <%@include file="/WEB-INF/css/genericStyles.css" %>
        <%@include file="/WEB-INF/css/indexStyles.css" %>
    </style>
    <title>Login</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login">
    <title>Login</title>
    <div class='container'>
        <div class='window'>
            <div class='content'>
                <h3 class='welcome'>Hello!</h3>
                <input for="login" type='text'
                       name="username"
                       id="username"
                       placeholder='username'
                       class='input-line full-width'/>
                <input for="Password" type='password' name="password"
                       id="Password" placeholder='Password'
                       class='input-line full-width'/> <br><br>
                <c:if test="${not empty error}">
                    <p style="font-max-size: 2px; color: #ff512f">${error}</p>
                </c:if>
                <script>
                    var msg = "${error}";
                    if(msg != ""){$("#modalAdd").show();
                        document.getElementById('modal-message').innerHTML ='<c:remove var="error" scope="session" /> ';
                    }
                </script>
                <button class='ghost-round full-width' id="button">Sign in</button>
                <a href="/registration " class='ghost-round full-width btn'   >Registration</a>
                <div class="alert alert-danger my-4" role="alert">
                        ${loginerror}
                    </div>

            </div>
        </div>
    </div>
</form>
</body>
</html>
