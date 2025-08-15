<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>add-product (seller)</title>

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

<div class="container p-5 mt-3">

<div class="row">

<div class="col-md-6 offset-md-3">
<div class="card card-sh">
<div class="card-header text-center fs-4">
<p class="fs-4" style="font-weight : bold">Add Product</p>

<c:if test="${not empty sessionScope.successMsg }">
<div style="color : green;font-size : 20px;font-family : georgia" role="alert">
<c:out value="${sessionScope.successMsg}"></c:out>
<!-- remove session value -->
<c:remove var="successMsg" scope="session"/>
</div>
</c:if>

<c:if test="${not empty sessionScope.errorMsg }">
<div style="color : red;font-size : 20px;font-family : georgia" role="alert">
<c:out value="${sessionScope.errorMsg}"></c:out>
<!-- remove session value -->
<c:remove var="errorMsg" scope="session"/>
</div>
</c:if>

</div>
<div class="card-body">
<form action="/seller/saveProd" method="post" enctype="multipart/form-data" id="sellerProductForm" novalidate="novalidate">

<div class="mb-3">
<label>Enter Title</label><input class="form-control" type="text" name="title">
</div>

<div class="mb-3">
<label>Enter Description</label><textarea name="description" rows="3" cols="" class="form-control"></textarea>
</div>

<div class="row">

<div class="mb-3 col">
<label>Category ( <i class="fa-solid fa-list"></i> ) </label>
<select class="form-control" name="category">
<option value="" disabled="disabled" selected="selected">---- Select ----</option>
<c:forEach items="${categoryList}" var="cat">
<option value="${cat.name}">${cat.name}</option>
</c:forEach>
</select>
</div>

<div class="mb-3 col">
<label>Enter Price ( <i class="fa-solid fa-indian-rupee-sign"></i> )</label><input class="form-control" type="number" name="price">
</div>

</div>

<div class="row">

<div class="mb-3 col">
<label>Enter Discount ( <i class="fa-solid fa-percent"></i> )</label><input class="form-control" type="number" name="discount">
</div>

<div class="mb-3 col">
<label>Discounted Price ( <i class="fa-solid fa-indian-rupee-sign"></i> ) </label><input class="form-control" type="number" name="discountedPrice" readonly="readonly">
</div>

</div>

<div class="mb-3">
<label>Status</label>

<div class="form-check">
  <input class="form-check-input" type="radio" name="isActive" id="radioDefault1" checked value="true">
  <label class="form-check-label" for="radioDefault1">
    Active
  </label>
</div>
<div class="form-check">
  <input class="form-check-input" type="radio" name="isActive" id="radioDefault2" value="false">
  <label class="form-check-label" for="radioDefault2">
   Inactive
  </label>
</div>

</div>

<div class="row">

<div class="mb-3 col">
<label>Enter Stock ( <i class="fa-solid fa-layer-group"></i> ) </label><input class="form-control" type="number" name="stock">
</div>

<div class="mb-3 col">
<label>Upload Image ( <i class="fa-solid fa-cloud-arrow-up"></i> ) </label><input class="form-control" type="file" name="file">
</div>

</div>

<button class="btn btn-primary col-md-12">Submit</button>

</form>
</div>
</div>
</div>

</div>

</div>




<!-- starts jQuery Validation Library -->
 
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/product.js"></script>
 
  <!-- ends jQuery Validation Library -->



<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
crossorigin="anonymous">
</script>

</body>
</html>