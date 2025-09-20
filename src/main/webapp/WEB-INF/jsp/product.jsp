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
<p class="fs-5">Category</p>
<a href="/product" class="list-group-item list-group-item-action ${paramValue == '0' ? 'active' : '' }" aria-current="${paramValue == '0' ? 'page' : ''}">
    All
</a>
  
<c:forEach items="${activeCategoryList}" var="cat">
<a href="/product?category=${cat.id}" class="list-group-item list-group-item-action ${paramValue == cat.id ? 'active' :'' }" aria-current="${paramValue == cat.id ? 'page' : ''}">${cat.name}</a>
</c:forEach>
  
</div>
</div>
</div>
</div>

<div class="col-md-10">
<div class="card shadow-sm p-3 mb-5 bg-body-tertiary rounded">
<div class="card-body">
<p class="fs-3 text-center">Products</p>                                  <!--only for testing-->

<!--only for testing starts-->
<c:if test="${searchBarPagination == true }">
<div class="row mb-3 align-items-center">
  <div class="col-md-6">
    <p class="fs-3 m-0"></p>                                                             <!--no text is written,used only for space-->
  </div>
  <div class="dropdown text-end">
    <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
      Sort by
    </button>
    <ul class="dropdown-menu dropdown-menu-end">
      <li><a class="dropdown-item" href="/searchProduct?sortDir=asc&keyword=${keyword}">Price: Low to High</a></li>
      <li><a class="dropdown-item" href="/searchProduct?sortDir=desc&keyword=${keyword}">Price: High to Low</a></li>
    </ul>
  </div>

</div>
</c:if>
<!--only for testing ends-->

<div class="row">

<c:if test="${not empty activeProductsListPaginated}">
<c:forEach items="${activeProductsListPaginated}" var="prod">
<div class="col-md-3">
<div class="card">
<div class="card-body">
<img alt="image not found" src='<c:url value="${pageContext.request.contextPath}${productImageUrl}${prod.imageName}" ></c:url>' width="100%" height="150px">
<p class="fs-5 text-center">${prod.title}</p>
<div class="row text-center">
<p class="fs-6 fw-bold">&#8377; ${prod.discountedPrice} <span class="text-decoration-line-through text-secondary"> ${prod.price} </span><span class="fs-6 text-success">${prod.discount}% off</span></p>
<a class="btn btn-primary col-md-7 offset-md-2" href="/viewProduct/${prod.id}">View Details</a>
</div>

</div> 
</div>
</div>
</c:forEach>

<c:if test="${searchBarPagination == false }">
<!-- Pagination Starts -->
<div class="row mt-5">

<div class="col-md-4">
<p>Total Products : ${totalProducts}</p>
</div>

<div class="col-md-6">
<nav aria-label="Page navigation example">
  <ul class="pagination">
    <li class="page-item ${isFirst ? 'disabled' : '' }">
      <a class="page-link" href="/product?pageNo=${pageNo-1}&pageSize=4" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    
    <!-- Note : here i is a local variable -->
    <c:forEach begin="1" end="${totalPages}" var="i">
    
    <li class="page-item ${pageNo+1 == i ? 'active':''}"><a class="page-link" href="/product?pageNo=${i-1}&pageSize=4">${i}</a></li>
    
    </c:forEach>
    
    <li class="page-item ${isLast ? 'disabled' : '' }">
      <a class="page-link" href="/product?pageNo=${pageNo+1}&pageSize=4" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
</div>

</div>
 <!-- Pagination Ends -->
</c:if>

<c:if test="${searchBarPagination == true }">
<!-- Pagination Starts -->
<div class="row mt-5">

<div class="col-md-4">
<p>Total Products : ${totalProducts}</p>
</div>

<div class="col-md-6">
<nav aria-label="Page navigation example">
  <ul class="pagination">
    <li class="page-item ${isFirst ? 'disabled' : '' }">
      <a class="page-link" href="/searchProduct?pageNo=${pageNo-1}&pageSize=4&keyword=${keyword}" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    
    <!-- Note : here i is a local variable -->
    <c:forEach begin="1" end="${totalPages}" var="i">
    
    <li class="page-item ${pageNo+1 == i ? 'active':''}"><a class="page-link" href="/searchProduct?pageNo=${i-1}&pageSize=4&keyword=${keyword}">${i}</a></li>
    
    </c:forEach>
    
    <li class="page-item ${isLast ? 'disabled' : '' }">
      <a class="page-link" href="/searchProduct?pageNo=${pageNo+1}&pageSize=4&keyword=${keyword}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
</div>

</div>
 <!-- Pagination Ends -->
</c:if>


</c:if>

<c:if test="${empty activeProductsListPaginated}">

<p class="text-center" style="color:red;font-family:georgia;font-size :25px;">Product Not Available</p>

</c:if>

</div>
</div>
</div>


</div>

</div>

</div>



<!-- note : when i remove below script only then Category and User dropdown works in header. study why it happens -->

<!-- <script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
crossorigin="anonymous">
</script> -->

</body>
</html>