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
	
	<!-- <link rel="stylesheet" href="resources/css/style.css"> -->
	
		<!-- 	we can see our css file i.e style.css at http://localhost:8080/resources/css/style.css after using pageContext.request.contextPath -->
<!-- this is recommended by chatGPT -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

</head>
<body>
<div class="container card-sh">
<div class="col-md-12 p-5">
<div class="row">

<div class="col-md-6 text-end">
<img alt="image not found" src="${pageContext.request.contextPath}${productImageUrl}${product.imageName}" width="330px" height="400px">
</div>

<div class="col-md-6">
<p class="fs-3">${product.title}</p>
<p><span class="fw-bold">Description : </span><br>
${product.description}
</p>

<p><span class="fw-bold">Product Details : </span><br>Status :
<c:if test="${product.stock > 0}">
<span class="badge bg-success">Available</span>
</c:if>

<c:if test="${product.stock <= 0}">
<span class="badge bg-warning">Out of Stock</span>
 </c:if>
<br>
Category : ${product.category} <br> Policy : 7 Days Replacement and Return
 </p>

 
 <p class="fs-5 fw-bold">
 Price :  <i class="fa-solid fa-rupee-sign"></i> ${product.discountedPrice}
 <span class="fs-6 text-decoration-line-through text-secondary">${product.price}</span><span class="fs-6 text-success"> ${product.discount}% Off</span>
 </p>
 
 
 <div class="row">
 
 <div class="col-md-4 text-success text-center p-2">
 <i class="fa-solid fa-money-bill-wave"></i>
 <p>Cash On Delivery</p>
 </div>
 
 <div class="col-md-4 text-danger text-center p-2">
<i class="fa-solid fa-rotate-left"></i>
 <p>Return Available</p>
 </div>
 
 <div class="col-md-4 text-primary text-center p-2">
<i class="fa-solid fa-truck"></i>
 <p>Free Shipping</p>
 </div>
 
 </div>
 
 <c:if test="${product.stock > 0}">
 <a href="/login" class="btn btn-danger col-md-12">Add to Cart</a>
 </c:if>
 
 <c:if test="${product.stock <= 0}">
 <a href="#" class="btn btn-warning col-md-12 text-white">Out of Stock</a>
 </c:if>
 
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