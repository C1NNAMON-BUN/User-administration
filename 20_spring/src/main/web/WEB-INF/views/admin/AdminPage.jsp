<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="en">
<head>
    <style>
        <%@include file="/WEB-INF/css/adminPageStyles .css" %>
        <%@include file="/WEB-INF/css/genericStyles.css" %>
    </style>
    <title>AdminPage</title>
</head>
<body>
<body>
<h2 align="right">Admin ${currentUser.getFirstName()} (<a
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

        <c:forEach items="${userslist}" var="user">
    <tr>
        <td>${user.getLogin()}</td>
        <td>${user.getFirstName()}</td>
        <td>${user.getLastName()}</td>
        <td>${user.getBirthday()}</td>
        <td>${user.getRoleEntity()}</td>
        <td>
            <form method="get" action="${pageContext.request.contextPath}/admin/edit-user">
                <p>
                    <button  id="evaluationFormEdit" type="submit" name="id" class='ghost-round full-width' value="${user.getId()}">edit</button>
                </p>
            </form>
            <form method="get" action="${pageContext.request.contextPath}/delete-user">
                <p>
                    <c:choose>
                        <c:when test="${currentUser.login  eq user.login}"> </c:when>
                        <c:otherwise>
                            <button type="submit" name="id" class='ghost-round full-width' value="${user.getId()}"
                                    onclick="return confirm('Are you sure you want to delete?')">delete
                            </button>
                        </c:otherwise>
                    </c:choose>
                </p>
            </form>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${not empty sessionScope.errorDuringDeletion}">
    <script type="text/javascript">
        alert("${sessionScope.errorDuringDeletion}");
    </script>
</c:if>
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

</body>
</html>
