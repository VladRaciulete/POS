<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Edit Product">
  <h1>Edit product</h1>
  <!-- Form pentru editarea unui produs -->
  <form class="needs-validation" novalidate="" method="POST" action="${pageContext.request.contextPath}/EditProduct">

    <div class="row">
      <div class="col-md-6 mb-3">
        <label for="name" class="form-label">Name</label>
        <input type="text" class="form-control" name="name" id="name" placeholder="" value="${product.name}" required>
        <div class="invalid-feedback">
          Valid name is required.
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-6 mb-3">
        <label for="quantity" class="form-label">Quantity</label>
        <input type="text" class="form-control" name="quantity" id="quantity" placeholder="" value="${product.quantity}" required>
        <div class="invalid-feedback">
          Valid quantity is required.
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-6 mb-3">
        <label for="price" class="form-label">Quantity</label>
        <input type="text" class="form-control" name="price" id="price" placeholder="" value="${product.price}" required>
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
            <!-- Afiseaza toate categoriile primite de la servlet -->
            <option value="${category.id}" ${product.name eq category.name ? 'selected' : ''}>${category.name}</option>

          </c:forEach>

        </select>
        <div class="invalid-feedback">
          Please select a valid category.
        </div>
      </div>
    </div>

    <hr class="mb-4">
    <input type="hidden" name="product_id" value="${product.id}">

    <button class="btn btn-primary btn-lg" type="submit">Save</button>
  </form>

  <script src="form-validation.js"></script>
</t:pageTemplate>