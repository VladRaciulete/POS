<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <br>
    <h1 class="text-center">Users</h1>
    <br>

    <form method="POST" action="${pageContext.request.contextPath}/Users">
        <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddUser">Add user</a>
                </div>

                <div class="col d-flex justify-content-center">
                    <button class="btn btn-danger" type="submit">Delete users</button>
                </div>
            </div>
        </c:if>

        <br>

        <div class="container text-center">
            <c:forEach var="user" items="${users}">
                <div class="row mb-1">
                    <div class="col">
                        <input type="checkbox" name="user_ids" value="${user.id}">
                    </div>

                    <div class="col">
                            ${user.username}
                    </div>

                    <div class="col">
                            ${user.email}
                    </div>

                    <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
                        <div class="col">
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditUser?id=${user.id}">Edit User</a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>

        </div>

    </form>

</t:pageTemplate>

