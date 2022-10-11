<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    <%@include file="/WEB-INF/css/genericStyles.css" %>
    <%@include file="/WEB-INF/css/edit-usersStyles.css" %>
</style>
<script>
    <%@include file="/WEB-INF/javaScript/dataCheck.js" %>
</script>
<head>
    <title>Edit User</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/admin/edit-user">
    <div class='container'>
        <div class='window'>
            <div class='content'>
                <h3 class='welcome'>Edit user</h3>
                <label>Login
                    <input type="text" name="login"
                           class='input-line full-width' value="${login}"
                           readonly/>
                </label>
                <label>Password
                    <input name="password" id="password" type="password"
                           minlength="4"
                           maxlength="20"
                           class='input-line full-width' value="${password}"
                           onkeyup='check();'/>
                </label>
                <label>Confirm password
                    <input type="password" name="confirmPassword"
                           minlength="4"
                           maxlength="20"
                           id="confirmPassword" class='input-line full-width'  value="${password}"
                           onkeyup='check();'/>
                    <span id='message'></span>
                </label>
                <label> Email
                    <input type="text" name="email" id = "email"
                           class='input-line full-width' value="${email}"
                           onkeyup='eCheck()' required/><span id='messageEmail'></span>
                </label>
                <c:if test="${not empty sessionScope.ifEmailExistsMessage}">
                    <div class="alert alert-danger my-4" role="alert">
                            ${sessionScope.ifEmailExistsMessage}
                    </div>
                </c:if>
                <label> First name
                    <input type="text" name="firstName" id = "firstName"
                           class='input-line full-width' value="${firstName}"
                           minlength="2"
                           maxlength="24"
                           onkeyup='fNameCheck()' required/>
                    <span id='messageFname'></span>
                </label>
                <label>Last name
                    <input type="text" name="lastName" id = "lastName"
                           class='input-line full-width' value="${lastName}"
                           minlength="2"
                           maxlength="24"
                           onkeyup='lNameCheck()' required/>
                    <span id='messageLname'></span>
                </label>
                <label>Birthday
                    <input type="date" name="birthday" id = "birthday"
                           class='input-line full-width' value="${birthday}"
                           min="1940-01-01" max="2021-12-31"
                           onkeyup='dateChek()' required/>
                    <span id='messageDate'></span>
                </label>
                <label class='input-line full-width'> Role:
                    <select name="idRole" class="select-type5">
                        <c:forEach items="${roleList}" var="role">
                            <option value="${role.id}"
                                    selected="${role}">${role.name }</option>
                        </c:forEach>
                    </select> <br/>
                </label>
                <c:if test="${not empty sessionScope.emptyFieldsChecking}">
                    <div class="alert alert-danger my-4" role="alert">
                            ${sessionScope.emptyFieldsChecking}
                    </div>
                </c:if>
                <br>
                <button type="submit" value="ok" class='ghost-round full-width'>
                    OK
                </button>
                <button type="submit" class='ghost-round full-width' name="blah"
                        value="cancel"
                        onclick="document.location.href='/admin'">Cancel
                </button>
            </div>
        </div>
    </div>
</form>
</body>
