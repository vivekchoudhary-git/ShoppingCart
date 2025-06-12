<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
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

<div class="container-fluid mt-1">

<div class="row">

<div class="col-md-2 p-0">
<div class="card shadow-sm p-0 mb-5 bg-body-tertiary rounded">
<div class="card-body">
<div class="list-group text-center">
  <a href="#" class="list-group-item list-group-item-action active" aria-current="true">
    Category
  </a>
  
  <c:forEach items="${activeCategoryList}" var="cat">
  <a href="#" class="list-group-item list-group-item-action">${cat.name}</a>
  </c:forEach>
  
</div>
</div>
</div>
</div>

<div class="col-md-10">
<div class="card shadow-sm p-3 mb-5 bg-body-tertiary rounded">
<div class="card-body">
<p class="fs-3 text-center">Products</p>
<div class="row">

<c:forEach items="${activeProductList}" var="prod">
<div class="col-md-3">
<div class="card">
<div class="card-body">
<img alt="image not found" src='<c:url value="resources/images/product_img/${prod.imageName}" ></c:url>' width="100%" height="150px">
<p class="fs-5 text-center">${prod.title}</p>
<div class="row text-center">
<p class="fs-6 fw-bold">&#8377; ${prod.discountedPrice} <span class="text-decoration-line-through text-secondary"> ${prod.price} </span><span class="fs-6 text-success">${prod.discount}% off</span></p>
<a class="btn btn-primary col-md-7 offset-md-2" href="/viewProduct">View Details</a>
</div>

</div> 
</div>
</div>
</c:forEach>

</div>
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