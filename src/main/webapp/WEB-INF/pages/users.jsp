<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <br>
    <h1 class="text-center">Users</h1>
    <br>
    <!-- Form pentru afisarea userilor(Se afiseaza doar daca grupul de acces al userului conectat e ADMIN)-->
    <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
        <form method="POST" action="${pageContext.request.contextPath}/Users">
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddUser">Add user</a>
                </div>

                <div class="col d-flex justify-content-center">
                    <button class="btn btn-danger" type="submit">Delete users</button>
                </div>
            </div>

            <br>

            <div class="container text-center">
                <c:forEach var="user" items="${users}">

                    <!-- Daca username-ul userului din lista e cel al userului conectat nu afiseaza userul -->
                    <c:if test="${user.username != pageContext.request.getRemoteUser()}">
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

                            <div class="col">
                                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditUser?id=${user.id}">Edit User</a>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </form>
    </c:if>
</t:pageTemplate>

