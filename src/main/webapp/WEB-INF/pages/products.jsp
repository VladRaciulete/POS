<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Products">
  <h1>Those are the available products!</h1>


    <form method="POST" action="${pageContext.request.contextPath}/Products">
      <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddProduct">Add Product</a>
        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddCategory">Add Category</a>
        <button class="btn btn-danger" type="submit">Delete products</button>
      </c:if>

      <div class="container text-center">
        <div class="row">

          <c:forEach var="elem" items="${productsByCategoryList}">

            <h2>${elem.categoryName}</h2>

            <c:forEach var="product" items="${elem.products}">

              <div class="border border-dark col-3">
                <div class="row">

                  <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
                    <div class="col">
                      <div class="row">
                        <input type="checkbox" name="product_ids" value="${product.id}">
                      </div>

                      <div class="row">
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditProduct?id=${product.id}">Edit Product</a>
                      </div>
                    </div>
                  </c:if>

                  <div class="col">

                    <div class="row">
                      POZA
                    </div>

                    <div class="row">
                        ${product.name}
                    </div>

                  <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
                    <div class="row">
                       quantity=${product.quantity}
                    </div>
                  </c:if>

                  </div>
                </div>
              </div>

            </c:forEach>
                <br>
                <br>
          </c:forEach>

        </div>
      </div>
    </form>
</t:pageTemplate>