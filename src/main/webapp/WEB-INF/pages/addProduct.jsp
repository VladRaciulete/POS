<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add Product">
  <h1>Add Products</h1>

  <form class="needs-validation" novalidate="" method="POST" action="${pageContext.request.contextPath}/AddProduct" enctype="multipart/form-data">

    <div class="row">
      <div class="col-md-6 mb-3">
        <label for="name" class="form-label">Name</label>
        <input type="text" class="form-control" name="name" id="name" placeholder="" value="" required>
        <div class="invalid-feedback">
          Valid name is required.
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-6 mb-3">
        <label for="quantity" class="form-label">Quantity</label>
        <input type="text" class="form-control" name="quantity" id="quantity" placeholder="" value="" required>
        <div class="invalid-feedback">
          Valid quantity is required.
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-6 mb-3">
        <label for="price" class="form-label">Price</label>
        <input type="text" class="form-control" name="price" id="price" placeholder="" value="" required>
        <div class="invalid-feedback">
          Valid price is required.
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-6 mb-3">
        <label for="category_id" class="form-label">Category</label>
        <select class="form-select" name="category_id" id="category_id" required>
          <option value="">Choose...</option>
          <c:forEach var="category" items="${categories}" varStatus="status">

            <option value="${category.id}" >${category.name}</option>

          </c:forEach>
        </select>
        <div class="invalid-feedback">
          Please select a valid category.
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-6 mb-3">
        <label for="file" class="form-label">Photo</label>
        <input type="file" name="file" id="file" required>
        <div class="invalid-feedback">
          Photo is required.
        </div>
      </div>
    </div>

    <button class="btn btn-primary btn-lg" type="submit">Save</button>
  </form>

  <script src="form-validation.js"></script>
</t:pageTemplate>