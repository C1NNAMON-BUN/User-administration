<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="en">
<head>
    <style>
        <%@include file="/WEB-INF/css/genericStyles.css" %>
        <%@include file="/WEB-INF/css/edit-usersStyles.css" %>
    </style>
    <script>
        <%@include file="/WEB-INF/javaScript/dataCheck.js"  %>
    </script>
    <title>Add new user</title>
</head>
<body>
<h2 align="right">Admin ${currentUser.getFirstName()} (<a
        href="${pageContext.request.contextPath}/logout">Logout</a>)</h2>
<div class="container mx-3 my-4 mx-auto">
    <form name="registration" method="POST"
          action="${pageContext.request.contextPath}/admin/edit-user"
          class="registration-form">
        <div class='container'>
            <div class='window'>
                <div class='content'>
                    <h3 class='welcome'>Edit user</h3>
                    <c:if test="${not empty error}">
                        <p style="font-max-size: 2px; color: #ff512f">${error}</p>
                    </c:if>
                    <script>
                        var msg = "${error}";
                        if(msg != ""){$("#modalAdd").show();
                            document.getElementById('modal-message').innerHTML ='<c:remove var="error" scope="session" /> ';
                        }
                    </script>

                    <label>Login
                        <input v-validate="'alpha:ru'" type="text" name="login"
                               class='input-line full-width' id="login"
                               value="${objecttoedit.getLogin()}" readonly/>
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
                               onkeyup='check();' value="${objecttoedit.getPassword()}"
                               required/>
                    </label>
                    <label>Confirm password
                        <input type="password" placeholder="Confirm Password"
                               name="password2" id="password2"
                               minlength="4"
                               maxlength="20" class='input-line full-width'
                               onkeyup='check();'  value="${objecttoedit.getPassword()}" required/>
                        <span id='message'></span>
                    </label>
                    <label> Email
                        <input type="text" name="email" id="email"
                               placeholder="Email"
                               class='input-line full-width' onkeyup='eCheck()' value="${objecttoedit.getEmail()}" required/><span
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
                               minlength="2"  value="${objecttoedit.getFirstName()}"
                               maxlength="24" onkeyup='fNameCheck()' required/>
                        <span id='messageFname'></span>
                    </label>
                    <label>Last name
                        <input type="text" name="lastName" value="${objecttoedit.getLastName()}"
                               class='input-line full-width' id="lastName" minlength="2"
                               maxlength="24" onkeyup='lNameCheck()' required/>
                        <span id='messageLname'></span>
                    </label>
                    <label>Birthday
                        <input type="date" name="birthday"  value="${objecttoedit.getBirthday()}"
                               class='input-line full-width' id="birthday"
                               min="1940-01-01"
                               onkeyup='dateChek()' required/>
                        <span id='messageDate'></span>

                    </label>
                    <label class='input-line full-width'> Role:
                        <select name="idRole" class="select-type5" >
                            <c:forEach items="${allRoles}" var="role">
                                <option value="${role.getIdRole()}">${role.getName()}</option>
                            </c:forEach>
                        </select> <br/>
                    </label>
                    <br>

                    <button type="submit" value="ok"
                            class='ghost-round full-width'>OK
                    </button>
                    <button type="button" id="evaluationFormEditCancel" class='ghost-round full-width' value="cancel"
                            onclick="document.location.href='/admin'">Cancel</button>
                </div>
            </div>
        </div>
    </form>
</div>
<script>
        $(document).ready(function() {

        $('#evaluationFormEdit').click(function() {
            $('#evaluationForm').find(':input').each(function(i, elem) {
                $(this).data("previous-value", $(this).val());
            });
        });

        function restore() {
        $('#evaluationForm').find(':input').each(function(i, elem) {
        $(this).val($(this).data("previous-value"));
    });
    }

        $('#evaluationFormEditCancel').click(function() {
        restore();
    });
    });
</script>
</body>
</html>
