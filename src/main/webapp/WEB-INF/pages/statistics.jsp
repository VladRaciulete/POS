<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Statistics">
    <h3 class="mt-5 mb-5 font-weight-normal text-center">Statistics</h3>

    <div class="container text-center">

        <c:forEach var="stat" items="${statistics}">
            <div class="row mb-1">

                <div class="col">
                       Product : ${stat.product_name}
                </div>

                <div class="col">
                        Products sold : ${stat.quantity}
                </div>

            </div>
        </c:forEach>

    </div>

    <script src="form-validation.js"></script>
</t:pageTemplate>