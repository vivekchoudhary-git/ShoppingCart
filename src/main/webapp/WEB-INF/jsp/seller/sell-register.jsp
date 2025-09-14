<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> sell-register.jsp (seller) </title>

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


<!-- 	we can see our css file i.e style.css at http://localhost:8080/resources/css/style.css after using pageContext.request.contextPath -->
        <!-- this is recommended by chatGPT -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

</head>
<body>

<div class="container mt-5 p-9">
<div class="row">
<div class="col-md-6 p-6">
<img alt="not found" src="${pageContext.request.contextPath}/resources/images/ecom.png">
</div>
<div class="col-md-6 mt-3 p-5">
<div class="card shadow p-3 mb-5 bg-body-tertiary rounded">
<div class="card-header">
<p class="fs-4 text-center"> Seller Registeration </p>

<c:if test="${not empty sessionScope.successMsg}">
<div style="color : green;font-size : 20px;font-family : georgia;text-align : center;" role="alert">
<c:out value="${sessionScope.successMsg}"/>
<!-- we can also remove successMsg value after displaying the alert message using this tag Note:this is clean way -->
<c:remove var="successMsg" scope="session"/>
</div>
</c:if>

<c:if test="${not empty sessionScope.errorMsg}">
<div style="color : red;font-size : 20px;font-family : georgia;text-align : center;" role="alert">
<c:out value="${sessionScope.errorMsg}"/>
<!-- we can also remove successMsg value after displaying the alert message using this tag Note:this is clean way -->
<c:remove var="errorMsg" scope="session"/>
</div>
</c:if>

</div>
<div class="card-body">
<form action="/seller/saveSellReg" method="post" enctype="multipart/form-data" id="sellerRegForm" novalidate="novalidate">

<div class="row">
<div class="col">
<label class="form-label">Full Name</label><input class="form-control" name="name" type="text">
<c:if test="${not empty errors and errors.hasFieldErrors('name')}">
<span style="color:red">${errors.getFieldError('name').defaultMessage}</span>
</c:if>
</div>

<div class="col">
<label class="form-label">Mobile Number</label><input class="form-control" name="phoneNo" type="number">
<c:if test="${not empty errors and errors.hasFieldErrors('phoneNo')}">
<span style="color:red">${errors.getFieldError('phoneNo').defaultMessage}</span>
</c:if>
</div>
</div>

<div class="mb-3">
<label class="form-label">Email</label><input class="form-control" name="email" type="email">
<c:if test="${not empty errors and errors.hasFieldErrors('email')}">
<span style="color:red">${errors.getFieldError('email').defaultMessage}</span>
</c:if>
</div>

<div class="row">
<div class="col">
<label class="form-label">Address</label><input class="form-control" name="address" type="text">
<c:if test="${not empty errors and errors.hasFieldErrors('address')}">
<span style="color:red">${errors.getFieldError('address').defaultMessage}</span>
</c:if>
</div>

<div class="col">
<label class="form-label">City</label><input class="form-control" name="city" type="text">
<c:if test="${not empty errors and errors.hasFieldErrors('city')}">
<span style="color:red">${errors.getFieldError('city').defaultMessage}</span>
</c:if>
</div>
</div>

<div class="row">
<div class="col">
<label class="form-label">State</label><input class="form-control" name="state" type="text">
<c:if test="${not empty errors and errors.hasFieldErrors('state')}">
<span style="color:red">${errors.getFieldError('state').defaultMessage}</span>
</c:if>
</div>

<div class="col">
<label class="form-label">Pincode</label><input class="form-control" name="pincode" type="number">
<c:if test="${not empty errors and errors.hasFieldErrors('pincode')}">
<span style="color:red">${errors.getFieldError('pincode').defaultMessage}</span>
</c:if>
</div>
</div>

<div class="row">
<div class="col">
<label class="form-label">Password</label><input class="form-control" name="password" type="password" id="pass">
<c:if test="${not empty errors and errors.hasFieldErrors('password')}">
<span style="color:red">${errors.getFieldError('password').defaultMessage}</span>
</c:if>
</div>

<div class="col">
<label class="form-label">Confirm Password</label><input class="form-control" name="confirmpassword" type="password">
<c:if test="${not empty errors and errors.hasFieldErrors('confirmpassword')}">
<span style="color:red">${errors.getFieldError('confirmpassword').defaultMessage}</span>
</c:if>
</div>
</div>

<div class="mb-3 mt-3">
<label class="form-label">Profile Image</label><input class="form-control" name="file" type="file">
</div>

<hr class="mt-5">

<div class="row mt-5">
<div class="col">
<label class="form-label">Company Name</label><input class="form-control" name="companyName" type="text" id="pass">
<c:if test="${not empty errors and errors.hasFieldErrors('companyName')}">
<span style="color:red">${errors.getFieldError('companyName').defaultMessage}</span>
</c:if>

</div>

<div class="col">
<label class="form-label">Company Address</label><input class="form-control" name="companyAddress" type="text">
<c:if test="${not empty errors and errors.hasFieldErrors('companyAddress')}">
<span style="color:red">${errors.getFieldError('companyAddress').defaultMessage}</span>
</c:if>
</div>
</div>

<div class="row">
<div class="col">
<label class="form-label">GST No</label><input class="form-control" name="gstNo" type="text" id="pass">
<c:if test="${not empty errors and errors.hasFieldErrors('gstNo')}">
<span style="color:red">${errors.getFieldError('gstNo').defaultMessage}</span>
</c:if>
</div>

<div class="col">
<label class="form-label">PAN No</label><input class="form-control" name="panNo" type="text">
<c:if test="${not empty errors and errors.hasFieldErrors('panNo')}">
<span style="color:red">${errors.getFieldError('panNo').defaultMessage}</span>
</c:if>
</div>
</div>

<div class="row">
<div class="col">
<label class="form-label">Bank Account</label><input class="form-control" name="bankAccount" type="text" id="pass">
<c:if test="${not empty errors and errors.hasFieldErrors('bankAccount')}">
<span style="color:red">${errors.getFieldError('bankAccount').defaultMessage}</span>
</c:if>
</div>

<div class="col">
<label class="form-label">IFSC Code</label><input class="form-control" name="ifscCode" type="text">
<c:if test="${not empty errors and errors.hasFieldErrors('ifscCode')}">
<span style="color:red">${errors.getFieldError('ifscCode').defaultMessage}</span>
</c:if>
</div>
</div>

<div class="mb-3 mt-3">
<label class="form-label">Company Registration Certificate</label><input class="form-control" name="compfile" type="file">
<c:if test="${not empty errors and errors.hasFieldErrors('compfile')}">
<span style="color:red">${errors.getFieldError('compfile').defaultMessage}</span>
</c:if>
</div>


<!-- Global error for the class-level validation -->
   <c:if test="${not empty errors and errors.globalErrorCount > 0}">
       <div style="color:red">
           <c:forEach var="err" items="${errors.globalErrors}">
               ${err.defaultMessage}<br/>
           </c:forEach>
       </div>
   </c:if>


<button type="submit" class="btn bg-primary text-white col-md-12">Register</button>

</form>
</div>

<div class="card-footer text-center">
Have an account ? <a href="/signin?seller=true" class="text-decoration-none">Login</a>
</div>

</div>
</div>

</div>
</div>


 <!-- starts jQuery Validation Library -->
 
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
 
  <!-- ends jQuery Validation Library -->


<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
crossorigin="anonymous">
</script>

</body>
</html>