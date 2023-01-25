<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Validate cashiers">
    <br>
    <h1 class="text-center">Validate cashiers</h1>
    <br>
    <br>

    <form method="POST" action="${pageContext.request.contextPath}/ValidateCashiers">

        <div class="container text-center">
            <h4>INVALID CASHIERS</h4>
            <br>
            <c:forEach var="cashier" items="${invalidCashiers}">
                <div class="row mb-1">

                    <div class="col">
                            ${cashier.id}
                    </div>

                    <div class="col">
                            ${cashier.username}
                    </div>

                    <div class="col">
                            ${cashier.email}
                    </div>

                    <c:if test="${pageContext.request.isUserInRole('DIRECTOR')}">
                        <div class="col">
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/ValidateCashiers?id=${cashier.id}">Validate Cashier</a>
                        </div>
                    </c:if>

                </div>
            </c:forEach>
        </div>

        <br>
        <p class="bg-dark"><br></p>

        <div class="container text-center">
            <h4>VALID CASHIERS</h4>
            <br>
            <c:forEach var="cashier" items="${validCashiers}">
                <div class="row mb-1">
                    <div class="col">
                            ${cashier.id}
                    </div>

                    <div class="col">
                            ${cashier.username}
                    </div>

                    <div class="col">
                            ${cashier.email}
                    </div>

                    <c:if test="${pageContext.request.isUserInRole('DIRECTOR')}">
                        <div class="col">
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/InvalidateCashiers?id=${cashier.id}">Invalidate Cashier</a>
                        </div>
                    </c:if>

                </div>
            </c:forEach>
        </div>

    </form>

    <script src="form-validation.js"></script>
</t:pageTemplate>