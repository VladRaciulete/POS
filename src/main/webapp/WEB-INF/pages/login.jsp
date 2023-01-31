<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Login">
  <h3 class="h3 mt-5 mb-5 font-weight-normal text-center">Sign in</h3>

  <!-- Testeaza daca message este nul (daca a aparut o eroare la login) -->
  <c:if test="${message != null}">
    <div class="alert alert-warning text-center" role="alert">
        ${message}
    </div>
  </c:if>

  <div class="container d-flex justify-content-center">
    <!-- Login form-->
    <form class="form-signin w-50" method="POST" action="j_security_check">

      <div class="row mb-3">
          <label for="username" class="sr-only">Username</label>
          <input type="text" id="username" name="j_username" class="form-control" placeholder="Username" required autofocus>
      </div>

      <div class="row mb-3">
          <label for="password" class="sr-only">Password</label>
          <input type="password" id="password" name="j_password" class="form-control" placeholder="Password" required>
      </div>

      <div class="row mb-3">
          <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </div>
  </form>
  </div>
</t:pageTemplate>