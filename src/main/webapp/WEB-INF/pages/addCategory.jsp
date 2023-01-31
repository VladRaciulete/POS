<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add Category">
    <h3 class="mt-5 mb-5 font-weight-normal text-center">Add Category</h3>

    <!-- Form pentru adaugarea unei categorii -->
    <div class="container d-flex justify-content-center text-center">
        <form class="needs-validation w-50" novalidate="" method="POST" action="${pageContext.request.contextPath}/AddCategory">

            <div class="row">
                <div class="col mb-3">
                    <label for="name" class="form-label">Category Name</label>
                    <input type="text" class="form-control" name="name" id="name" placeholder="" value="" required>
                    <div class="invalid-feedback">
                        Valid name is required.
                    </div>
                </div>
            </div>

            <button class="btn btn-primary btn-lg" type="submit">Save</button>
        </form>
    </div>
    <script src="form-validation.js"></script>
</t:pageTemplate>