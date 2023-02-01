<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">POS</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">

                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.requestURI.substring(pageContext.request.requestURI.lastIndexOf("/")) eq '/about.jsp' ? ' active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/about.jsp">About</a>
                    </li>

                    <!-- Testeaza daca userul conectat are grupul de acces ADMIN -->
                    <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'Users' ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Users">Users</a>
                        </li>
                    </c:if>

                    <!-- Testeaza daca userul conectat are grupul de acces DIRECTOR -->
                    <c:if test="${pageContext.request.isUserInRole('DIRECTOR')}">
                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'Cashiers' ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Cashiers">Cashiers</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'Statistics' ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Statistics">Statistics</a>
                        </li>
                    </c:if>

                    <c:if test="${pageContext.request.isUserInRole('VALID_CASHIER')}">
                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'Checkout' ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Checkout">Checkout</a>
                        </li>
                    </c:if>

                    <c:if test="${!pageContext.request.isUserInRole('CASHIER') && !pageContext.request.isUserInRole('VALID_CASHIER')}">
                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'Products' ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Products">Products</a>
                        </li>
                    </c:if>
                </ul>


                <ul class="navbar-nav">
                    <li class="nav-item">
                        <c:if test="${pageContext.request.getRemoteUser() != null}">
                            <p class="nav-link m-0 text-white">${pageContext.request.getRemoteUser()}    </p>
                        </c:if>
                    </li>

                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${pageContext.request.getRemoteUser() == null}">
                                <!-- In cazul in care userul NU e conectat se afiseaza link catre LOGIN -->
                                <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                            </c:when>
                            <c:otherwise>
                                <!-- In cazul in care userul e conectat se afiseaza link catre LOGOUT -->
                                <a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>