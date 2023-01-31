<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Edit User">
    <h1 class="mt-5 mb-5 font-weight-normal text-center">Edit user</h1>

    <!-- Form pentru editarea unui user -->
    <div class="container d-flex justify-content-center text-center">
        <form class="needs-validation w-50" novalidate="" method="POST" action="${pageContext.request.contextPath}/EditUser">

            <div class="row">
                <div class="col mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" name="username" id="username" placeholder="" value="${user.username}" required>
                    <div class="invalid-feedback">
                        Valid Username is required.
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" id="email" placeholder="" value="${user.email}" required>
                    <div class="invalid-feedback">
                        Valid Email is required.
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="" value="${user.password}" required>
                    <div class="invalid-feedback">
                        Valid Password is required.
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col mb-3">
                    <label for="user_groups" class="form-label">Groups</label>
                    <select class="custom-select d-block w-100 text-center" name="user_groups" id="user_groups" multiple>
                        <c:forEach var="user_group" items="${userGroups}" varStatus="status">
                            <!-- Afiseaza toate grupurile de acces primite de la servlet -->
                            <option value="${user_group}" >${user_group}</option>

                        </c:forEach>

                    </select>
                </div>
            </div>
            <input type="hidden" name="user_id" value="${user.id}">
            <button class="btn btn-primary btn-lg btn-block" type="submit">Save</button>
        </form>
    </div>
    <script src="form-validation.js"></script>
</t:pageTemplate>