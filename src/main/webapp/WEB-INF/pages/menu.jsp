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

                    <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'Users' ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Users">Users</a>
                        </li>
                    </c:if>

                    <c:if test="${pageContext.request.isUserInRole('DIRECTOR')}">
                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'Validate' ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Cashiers">Cashiers</a>
                        </li>
                    </c:if>

                    <li class="nav-item">
                        <a class="nav-link ${activePage eq 'Products' ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Products">Products</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link disabled">Disabled</a>
                    </li>
                </ul>


                <ul class="navbar-nav">
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${pageContext.request.getRemoteUser() == null}">
                                <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>








            </div>
        </div>
    </nav>
</header>