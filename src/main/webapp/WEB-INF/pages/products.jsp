<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Products">
  <br>
    <form method="POST" action="${pageContext.request.contextPath}/Products">

      <c:if test="${pageContext.request.isUserInRole('ADMIN') || pageContext.request.isUserInRole('DIRECTOR')}">
        <div class="row">
          <div class="col d-flex justify-content-center">
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddProduct">Add Product</a>
          </div>

          <div class="col d-flex justify-content-center">
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddCategory">Add Category</a>
          </div>

          <div class="col d-flex justify-content-center">
            <button class="btn btn-danger" type="submit">Delete products</button>
          </div>
        </div>
      </c:if>

    <br>

      <div class="container text-center">

            <c:forEach var="elem" items="${productsByCategoryList}">

              <c:if test="${elem != null}">

              <h2>${elem.categoryName}</h2>

              <div class="row">
                <c:forEach var="product" items="${elem.products}">

                  <div class="col-4 border border-dark pt-1 pb-1">

                    <div class="row">

                      <c:if test="${pageContext.request.isUserInRole('ADMIN') || pageContext.request.isUserInRole('DIRECTOR')}">
                        <div class="col d-flex justify-content-center">
                          <div class="col d-flex justify-content-center">
                            <input type="checkbox" name="product_ids" value="${product.id}">
                          </div>
                        </div>
                      </c:if>

                      <div class="col d-flex justify-content-center">
                        <div>
                          <img src="${pageContext.request.contextPath}/ProductPhotos?id=${product.id}" width="128">
                        </div>
                      </div>

                    </div>



                    <div class="row">

                      <c:if test="${pageContext.request.isUserInRole('ADMIN') || pageContext.request.isUserInRole('DIRECTOR')}">
                        <div class="col d-flex justify-content-center">
                          <div class="col d-flex justify-content-center">
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditProduct?id=${product.id}">Edit</a>
                          </div>
                        </div>
                      </c:if>

                      <div class="col d-flex justify-content-center">
                          ${product.name}
                            <c:if test="${pageContext.request.isUserInRole('ADMIN') || pageContext.request.isUserInRole('DIRECTOR')}">
                          = ${product.quantity}
                        </c:if>
                      </div>

                    </div>

                  </div>

                </c:forEach>
              </div>
              <p><br></p>
              <p class="bg-dark"><br></p>
            </c:if>

          </c:forEach>

      </div>

    </form>
</t:pageTemplate>