<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="CheckOut">
  <br>

  <!-- Form pe care il vad doar userii cu grupurile de acces ADMIN si DIRECTOR -->
  <c:if test="${pageContext.request.isUserInRole('VALID_CASHIER') || pageContext.request.isUserInRole('ADMIN')}">
    <form method="POST" action="${pageContext.request.contextPath}/Buy">


        <div class="col d-flex justify-content-center">
          <button class="btn btn-primary btn-lg" type="submit">Approve and Scan products</button>
        </div>


      <br>



      <div class="container text-center">
        <c:forEach var="elem" items="${unscannedTransactions}">

        <c:if test="${elem != null}">

        <div class="col-4 border border-dark pt-1 pb-1">

          <div class="row">
            <div class="col d-flex justify-content-center">
              Tiplul tranzactiei ${elem.transaction_type}<br>
              Cum s-a platit ${elem.payment_type}<br>
              Total: ${elem.total}
            </div>
            <c:forEach var="elem" items="${transactionDetailsDtoList}"><br>
              <c:if test="${elem != null}"><br>
                ${elem.product_name}<br>
                Cantitatea: ${elem.quantity} Pretul: ${elem.price}
              </c:if>


            </c:forEach>

            <div class="row">
              <div class="col d-flex justify-content-center">
                <input type="checkbox" name="scan_transaction_id" value="${elem.transaction_id}">
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
                      Tiplul tranzactiei ${elem.transaction_type}<br>
                      Cum s-a platit ${elem.payment_type}<br>
                      Total: ${elem.total}
                    </div>
                    <c:forEach var="elem" items="${transactionDetailsDtoList}"><br>
                      <c:if test="${elem != null}"><br>
                        ${elem.product_name}<br>
                        Cantitatea: ${elem.quantity} Pretul: ${elem.price}
                      </c:if>
                    </c:forEach>
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