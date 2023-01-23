<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Products">
  <h1>Those are the available products!</h1>

  <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddProduct">Add Product</a>

  <div class="container text-center">
    <c:forEach var="product" items="${products}">
      <div class="row">

        <div class="col">
            ${product.id}
        </div>

        <div class="col">
            ${product.name}
        </div>

      </div>
    </c:forEach>
  </div>
</t:pageTemplate>