<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<META http-equiv="content-type" content="text/html; charset=windows-1251">
<html>
<meta charset="utf-8">
<title>Add User</title>

<style>
    <%@include file="/WEB-INF/css/genericStyles.css" %>
    <%@include file="/WEB-INF/css/add-usersStyles.css" %>
</style>
<script>
    <%@include file="/WEB-INF/javaScript/dataCheck.js"  %>
</script>
<head>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <title>Add user</title>
</head>
<body>
<h2 align="right">Admin ${name} (<a
        href="${pageContext.request.contextPath}/logout">Logout</a>)</h2>
<div class="container mx-3 my-4 mx-auto">
    <form name="registration" method="POST"
          action="${pageContext.request.contextPath}/admin/add-user"
          class="registration-form">
        <div class='container'>
            <div class='window'>
                <div class='content'>
                    <h3 class='welcome'>Add user</h3>
                    <label>Login
                        <input v-validate="'alpha:ru'" type="text" name="login"
                               class='input-line full-width' id="login"
                               minlength="3"
                               maxlength="20" onkeyup='logCheck();' required/>
                        <span id='messageLogin'></span>
                    </label>
                    <c:if test="${not empty sessionScope.ifLoginExistsMessage}">
                        <div class="alert alert-danger my-4" role="alert">
                                ${sessionScope.ifLoginExistsMessage}
                        </div>
                    </c:if>
                    <label>Password
                        <input type="password" name="password" id="password"
                               placeholder="password" minlength="4"
                               maxlength="20" class='input-line full-width'
                               onkeyup='check();' required/>
                    </label>
                    <label>Confirm password
                        <input type="password" placeholder="Confirm Password"
                               name="confirmPassword" id="confirmPassword"
                               minlength="4"
                               maxlength="20" class='input-line full-width'
                               onkeyup='check();' required/>
                        <span id='message'></span>
                    </label>
                    <label> Email
                        <input type="text" name="email" id="email"
                               placeholder="Email"
                               class='input-line full-width' onkeyup='eCheck()' required/><span
                                id='messageEmail'></span>
                    </label>
                    <c:if test="${not empty sessionScope.ifEmailExistsMessage}">
                        <div class="alert alert-danger my-4" role="alert">
                                ${sessionScope.ifEmailExistsMessage}
                        </div>
                    </c:if>
                    <label> First name
                        <input type="text" name="firstName"
                               class='input-line full-width' id="firstName"
                               placeholder="Firstname"
                               minlength="2"
                               maxlength="24" onkeyup='fNameCheck()' required/>
                        <span id='messageFname'></span>
                    </label>
                    <label>Last name
                        <input type="text" name="lastName"
                               class='input-line full-width' id="lastName" minlength="2"
                               maxlength="24" onkeyup='lNameCheck()' required/>
                        <span id='messageLname'></span>
                    </label>
                    <label>Birthday
                        <input type="date" name="birthday"
                               class='input-line full-width' id="birthday"
                               min="1940-01-01" max="2021-12-31"
                               onkeyup='dateChek()' required/>
                        <span id='messageDate'></span>

                    </label>
                    <label class='input-line full-width'> Role:
                        <select name="idRole" class="select-type5">
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.id}"
                                        selected="${role}">${role.name}</option>
                            </c:forEach>
                        </select> <br/>
                    </label>
                    <br>
                    <button type="submit" value="ok"
                            class='ghost-round full-width'>ADD
                    </button>
                    <button type="submit" class='ghost-round full-width'
                            name="blah" value="cancel"
                            onclick="document.location.href='/admin'">Cancel
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>