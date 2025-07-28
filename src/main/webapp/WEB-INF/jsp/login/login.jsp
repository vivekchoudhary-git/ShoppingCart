<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page session="true" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<!-- this link is to use bootstrap taken from bootstrap website -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
	
	 <!-- this link is to use icons taken from font awesome website -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
	integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />

</head>
<body>


<div class="container mt-5 p-9">
<div class="row">
<div class="col-md-6 p-4">
<img alt="not found" src="resources/images/ecom.png">
</div>
<div class="col-md-6 mt-3 p-5">
<div class="card shadow p-3 mb-5 bg-body-tertiary rounded">
<div class="card-header">
<p class="fs-4 text-center">Login</p>

<!-- start : show logout message -->
<c:if test="${not empty param.logout}">

<div style="color:green;font-size : 20px; font-family : georgia;text-align:center;">
<c:out value="Logout Successful"></c:out>
</div>

 <!-- using javaScript to remove ?logout=true from the URL after displaying the message -->
 <script>
        if (window.location.search.includes('logout=true')) {
            const url = new URL(window.location.href);
            url.searchParams.delete('logout');
            window.history.replaceState({}, document.title, url.pathname);
        }
 </script>

</c:if>
<!-- ends : show logout message -->


<!-- start : show Login error messages -->

    <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
    <c:choose>
        <c:when test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message == 'Bad credentials'}">
            <p class="text-center text-danger fs-5">Invalid username or password. Please try again.</p>
        </c:when>
        <c:when test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message == 'User is disabled'}">
            <p class="text-center text-danger fs-5">Your account is disabled. Contact support.</p>
        </c:when>
        <c:otherwise>
            <p class="text-center text-danger fs-5">
                <c:out value="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}" />
            </p>
        </c:otherwise>
    </c:choose>
    
   <!--  remove session value after displaying the message -->
    <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
    
</c:if>

<!-- ends : show Login error messages -->

</div>
<div class="card-body">
<form action="/login" method="post">

<div class="mb-3">
<label class="form-label">Email</label><input class="form-control" name="username" type="email" required="required">
</div>

<div class="mb-3">
<label class="form-label">Password</label><input class="form-control" name="password" type="password" required="required">
</div>

<button type="submit" class="btn bg-primary text-white col-md-12">Login</button>

</form>
</div>

<div class="card-footer text-center">
<a href="/forgotPass" class="text-decoration-none">Forgot Password</a><br>
Don't have an account ? <a href="/register" class="text-decoration-none">Create One</a>
</div>

</div>
</div>

</div>
</div>


<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
crossorigin="anonymous">
</script>

</body>
</html>