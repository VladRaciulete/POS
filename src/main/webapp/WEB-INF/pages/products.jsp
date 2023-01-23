<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Products">
  <h1>Those are the available products!</h1>

  <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddProduct">Add Product</a>
    <button class="btn btn-danger" type="submit">Delete users</button>
  </c:if>

  <div class="container text-center">
    <div class="row">
    <c:forEach var="product" items="${products}">
      <div class="border border-dark col">
        <div class="row">

          <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
            <div class="col">
              <input type="checkbox" name="product_ids" value="${product.id}">
            </div>
          </c:if>

          <div class="col">

            <div class="row">
                POZA
            </div>


            <div class="row">
                  ${product.name} > ${product.quantity}
            </div>

            <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
              <div class="row">
                <a class="w-75 btn btn-secondary" href="${pageContext.request.contextPath}/EditProduct?id=${product.id}">Edit Product</a>
              </div>
            </c:if>

          </div>
        </div>
      </div>
    </c:forEach>
    </div>
  </div>
</t:pageTemplate>