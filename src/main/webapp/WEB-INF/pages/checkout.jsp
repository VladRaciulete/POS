<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Checkout">
  <br>


  <!-- Form pe care il vad doar userii fara grupuri de acces sau utilizatorii neconectati -->
  <c:if test="${!pageContext.request.isUserInRole('ADMIN') && !pageContext.request.isUserInRole('DIRECTOR') && !pageContext.request.isUserInRole('VALID_CASHIER') && !pageContext.request.isUserInRole('CASHIER')}">
    <form method="POST" action="${pageContext.request.contextPath}/Products">

      <!-- Afisarea produselor -->
      <div class="container text-center">
        <c:forEach var="elem" items="${productsToSell}">
          <!-- Pentru fiecare element din lista afiseaza: -->
          <c:if test="${elem != null}">


                  <div class="row">
                    <div class="col d-flex justify-content-center">
                      <input type="checkbox" name="buy_product_ids" value="${elem.id}">
                    </div>

                    <div class="col d-flex justify-content-center">
                      <div>
                        <img src="${pageContext.request.contextPath}/ProductPhotos?id=${elem.id}" alt="." width="128">
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col d-flex justify-content-center">
                      $${elem.price} ${elem.name}
                    </div>
                  </div>
                </div>

              <
            </div>

            <p><br></p>
            <p class="bg-dark"><br></p>

          </c:if>

        </c:forEach>
        <div class="col d-flex justify-content-end">
          <button class="btn btn-success" type="submit">Go to checkout</button>
        </div>
      </div>
    </form>
  </c:if>
</t:pageTemplate>