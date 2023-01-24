<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add Category">

    <h1>Add Category</h1>

    <form class="needs-validation" novalidate="" method="POST" action="${pageContext.request.contextPath}/AddCategory">

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="name" class="form-label">Category Name</label>
                <input type="text" class="form-control" name="name" id="name" placeholder="" value="" required>
                <div class="invalid-feedback">
                    Valid name is required.
                </div>
            </div>
        </div>

        <button class="btn btn-primary btn-lg" type="submit">Save</button>
    </form>

    <script src="form-validation.js"></script>
</t:pageTemplate>