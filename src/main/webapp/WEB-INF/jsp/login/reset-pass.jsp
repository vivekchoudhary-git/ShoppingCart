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
<p class="fs-4 text-center">Reset Password</p>


<!-- start : show error messages -->

<c:if test="${not empty sessionScope.errorMsg}">

<div style="color:red;font-size:20px;font-family:georgia;text-align:center">
<c:out value="${errorMsg}"></c:out>
</div>

<c:remove var="errorMsg" scope="session"/>

</c:if>

<!-- ends : show error messages -->

</div>
<div class="card-body">
<form action="/resetPass" method="post">

<div class="mb-3">
<label class="form-label">New Password</label><input class="form-control" name="password" type="password">
</div>

<div class="mb-3">
<label class="form-label">Confirm Password</label><input class="form-control" type="password">
</div>

<input type="hidden" name="resetToken" value="${resetToken}" >

<button type="submit" class="btn bg-primary text-white col-md-4 offset-md-4">Reset</button>

</form>
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