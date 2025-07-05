<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	
	     <!-- below link can throw error sometimes therefore it is not recommended by chatGPT -->
<!-- 	<link rel="stylesheet" href="resources/css/style.css"> -->
	
<!-- 	we can see our css file i.e style.css at http://localhost:8080/resources/css/style.css after using pageContext.request.contextPath -->
        <!-- this is recommended by chatGPT -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

</head>
<body>

<div class="container mt-5 p-5">

<form action="/user/saveOrder" method="post">
<div class="row">
<div class="col-md-6">
<!-- <form action=""> -->

<div class="mb-3 row">
<p class="text-center fs-2">Billing Address</p>
<hr>

<div class="col p-1">
<label>First Name</label><input type="text" name="firstName" class="form-control mt-1" required="required">
</div>

<div class="col p-1">
<label>Last Name</label><input type="text" name="lastName" class="form-control mt-1" required="required">
</div>
</div>

<div class="mb-3 row">
<div class="col p-1">
<label>Email</label><input type="email" name="email" class="form-control mt-1" required="required">
</div>

<div class="col p-1">
<label>Mobile No</label><input type="text" name="mobileNo" class="form-control mt-1" required="required">
</div>
</div>

<div class="mb-3 row">
<div class="col p-1">
<label>Address</label><input type="text" name="address" class="form-control mt-1" required="required">
</div>

<div class="col p-1">
<label>City</label><input type="text" name="city" class="form-control mt-1" required="required">
</div>
</div>

<div class="mb-3 row">
<div class="col p-1">
<label>State</label><input type="text" name="state" class="form-control mt-1" required="required">
</div>

<div class="col p-1">
<label>Pincode</label><input type="text" name="pincode" class="form-control mt-1" required="required">
</div>
</div>

<!-- </form> -->

</div>

<div class="col-md-6">

<p class="text-center fs-2">Payment Type</p>
<hr>

<div class="card">
<div class="card-body">

<table class="table table-borderless">
<tbody>
<tr><td>Total Price</td><td> : </td><td>&#8377 ${totalOrderPrice}</td></tr>
<tr><td>Delivery charge</td><td> : </td><td>&#8377 250</td></tr>
<tr><td>Tax</td><td> : </td><td>&#8377 100</td></tr>
<tr class="border-top"><td>Total Amount</td><td> : </td><td>&#8377 ${finalOrderTotalPrice}</td></tr>
</tbody>
</table>

</div>
</div>

<div class="card shadow p-3 mb-5 mt-2 bg-bd-tertiary rounded">
<div class="card-body">

<!-- <form action=""> -->
<div class="mb-3">
<label class="form-label">Payment Mode</label>
<select class="form-control" name="paymentType">
<option>---- Select ----</option>
<option>COD</option>
<option>ONLINE</option>
<option>UPI</option>
</select>
</div>

<button class="btn btn-primary col-md-12">Place Order</button>

<!-- </form> -->
</div>
</div>

</div>


</div>

</form>

</div>













<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
crossorigin="anonymous">
</script>


</body>
</html>