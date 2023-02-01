<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="CheckOut">
  <br>

  <!-- Form pe care il vad doar userii cu grupurile de acces ADMIN si DIRECTOR -->
  <c:if test="${pageContext.request.isUserInRole('ADMIN') || pageContext.request.isUserInRole('DIRECTOR')}">
    <form method="POST" action="${pageContext.request.contextPath}/Products">

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

      <br>

      <!-- Afisarea produselor -->
      <div class="container text-center">
        <c:forEach var="elem" items="${productsByCategoryList}">
          <!-- Pentru fiecare element din lista afiseaza: -->
          <c:if test="${elem != null}">

            <h2>${elem.categoryName}</h2>

            <div class="row">
              <c:forEach var="product" items="${elem.products}">
                <!-- Pentru fiecare produs din elementul listei afiseaza: -->
                <div class="col-4 border border-dark pt-1 pb-1">

                  <div class="row">
                    <div class="col d-flex justify-content-center">
                      <input type="checkbox" name="delete_product_ids" value="${product.id}">
                    </div>

                    <div class="col d-flex justify-content-center">
                      <div>
                        <img src="${pageContext.request.contextPath}/ProductPhotos?id=${product.id}" alt="." width="128">
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col d-flex justify-content-center">
                      <div class="col d-flex justify-content-center">
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditProduct?id=${product.id}">Edit</a>
                      </div>
                    </div>

                    <div class="col d-flex justify-content-center">
                      $${product.price} ${product.name} = ${product.quantity}
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
  </c:if>

  <!-- Form pe care il vad doar userii fara grupuri de acces sau utilizatorii neconectati -->
  <c:if test="${!pageContext.request.isUserInRole('ADMIN') && !pageContext.request.isUserInRole('DIRECTOR') && !pageContext.request.isUserInRole('VALID_CASHIER') && !pageContext.request.isUserInRole('CASHIER')}">

      <!-- Afisarea produselor -->
      <div class="container text-center">
        <c:forEach var="elem" items="${unscannedTransactions}">
          <!-- Pentru fiecare element din lista afiseaza: -->
          <c:if test="${elem != null}">

                <div class="col-4 border border-dark pt-1 pb-1">

                  <div class="row">
                    <div class="col d-flex justify-content-center">
                      $${elem.transaction_id} ${elem.transaction_type}${elem.payment_type}${elem.total}
                    </div>
                  </div>
                </div>

            </div>

            <p><br></p>
            <p class="bg-dark"><br></p>

          </c:if>
        </c:forEach>

        </div>
      </div>

  </c:if>
</t:pageTemplate>