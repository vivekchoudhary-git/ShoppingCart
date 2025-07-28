<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>index.jsp (admin)</title>

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
	<!-- <link rel="stylesheet" href="resources/css/style.css"> -->
	
	<!-- 	we can see our css file i.e style.css at http://localhost:8080/resources/css/style.css after using pageContext.request.contextPath -->
<!-- this is recommended by chatGPT -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">


</head>
<body>

<div class="container p-5 mt-1">
<p class="text-center fs-3">Admin Dashboard</p>

<div class="row p-5">

<div class="col-md-4">
<a class="text-decoration-none" href="/admin/loadAddProduct">
<div class="card card-sh">
<div class="card-body text-center text-primary">
<i class="fa-solid fa-square-plus fa-3x"></i>
<h4>Add Product</h4>
</div>
</div>
</a>
</div>

<div class="col-md-4">
<a class="text-decoration-none" href="loadCategory">
<div class="card card-sh">
<div class="card-body text-center text-primary">
<i class="fa-solid fa-table-list fa-3x"></i>
<h4>Category</h4>
</div>
</div>
</a>
</div>

<div class="col-md-4">
<a class="text-decoration-none" href="/admin/viewProducts">
<div class="card card-sh">
<div class="card-body text-center text-success">
<i class="fa-solid fa-book-open fa-3x"></i>
<h4>View Products</h4>
</div>
</div>
</a>
</div>

<div class="col-md-4 mt-3">
<a class="text-decoration-none" href="/admin/allOrders">
<div class="card card-sh">
<div class="card-body text-center text-warning">
<i class="fa-solid fa-box-open fa-3x"></i>
<h4>Orders</h4>
</div>
</div>
</a>
</div>

<div class="col-md-4 mt-3">
<a class="text-decoration-none" href="/admin/viewUsers?userType=1">
<div class="card card-sh">
<div class="card-body text-center text-primary">
<i class="fa-solid fa-users fa-3x"></i>
<h4>Users</h4>
</div>
</div>
</a>
</div>

<div class="col-md-4 mt-3">
<a class="text-decoration-none" href="/admin/register">
<div class="card card-sh">
<div class="card-body text-center text-danger">
<i class="fa-solid fa-circle-user fa-3x"></i>
<h4>Add Admin</h4>
</div>
</div>
</a>
</div>

<div class="col-md-4 mt-3">
<a class="text-decoration-none" href="/admin/viewUsers?userType=2">
<div class="card card-sh">
<div class="card-body text-center text-danger">
<i class="fa-solid fa-circle-user fa-3x"></i>
<h4>Admins</h4>
</div>
</div>
</a>
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