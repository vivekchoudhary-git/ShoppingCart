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
	
	     <!-- below link can throw error sometimes therefore it is not recommended by chatGPT -->
<!-- 	<link rel="stylesheet" href="resources/css/style.css"> -->
	
<!-- 	we can see our css file i.e style.css at http://localhost:8080/resources/css/style.css after using pageContext.request.contextPath -->
        <!-- this is recommended by chatGPT -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

</head>
<body>

<div class="container-fluid mt-5 p-5" style="font-weight : bolder;">

<div class="row">
<div class="col-md-2 mb-2">
<a href="/admin/"><button class="btn btn-sm btn-warning"><i class="fa-solid fa-arrow-left"></i> Back</button></a>
</div>
</div>

<!-- search option starts -->
<div class="row">
<div class="col-md-4">
<form action="">
<div class="row">

<div class="col">
<input type="text" name="" placeholder="Enter Order ID" class="form-control">
</div>

<div class="col">
<button class="btn btn-sm btn-primary">Search</button>
</div>
</div>
</form>
</div>
</div>
<!-- search option ends -->

<div class="card card-sh">

<div class="card-header text-center">
<p class="fs-4">All Orders</p>

<c:if test="${not empty sessionScope.successMsg }">
<div style="color:green;text-align:center;font-size:20px;font-family:georgia;">
<c:out value="${successMsg}"></c:out>
</div>

 <!-- remove successMsg value after displaying the message -->
<c:remove var="successMsg" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.errorMsg }">
<div style="color:red;text-align:center;font-size:20px;font-family:georgia;">
<c:out value="${errorMsg}"></c:out>
</div>

 <!-- remove successMsg value after displaying the message -->
<c:remove var="errorMsg" scope="session"/>
</c:if>

</div>

<div class="card-body">
<table class="table">

  <thead>
    <tr>
      <th scope="col">Order Id</th>
      <th scope="col">Delivery Details</th>
      <th scope="col">Order Date</th>
      <th scope="col">Product Detail</th>
      <th scope="col">Price</th>
      <th scope="col">Status</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  
  <tbody>
  <c:forEach items="${allOrdersList}" var="order">
    <tr>
      <td>${order.orderId}</td>
      <td>Name : ${order.orderAddress.firstName} ${order.orderAddress.lastName} <br>
          Email : ${order.orderAddress.email} <br>
          Mobile No : ${order.orderAddress.mobileNo} <br>
          Address : ${order.orderAddress.address} <br>
          City : ${order.orderAddress.city} <br>
          State : ${order.orderAddress.state} <br>
          Pincode : ${order.orderAddress.pincode} <br>
      
      </td>
      <td>${order.orderDate}</td>
      <td>${order.product.title}</td>
      <td>Quantity : ${order.quantity} <br>
          Price : ${order.price} <br>
          Total Price : ${order.quantity * order.price}
      </td>
      <td>${order.status}</td>
          
     <td>
    <form action="/admin/changeStatusAdmin" method="post">
    <div class="row">

    <div class="col">
    
    <input type="hidden" value="${order.id}" name="oid">
    
    <select class="form-control" name="statusId">
    <option value="" disabled="disabled" selected="selected">-- Select --</option>
    <option value="1">In Progress</option>
    <option value="2">Order Received</option>
    <option value="3">Product is packed</option>
    <option value="4">Out for delivery</option>
    <option value="5">Delivered</option>
    <option value="6">Cancelled</option>
    </select>
    </div>

    <div class="col">
    <c:choose>
    <c:when test="${order.status == 'Cancelled' || order.status == 'Delivered' }">
    <button class="btn btn-sm btn-primary disabled">Update</button>
    </c:when>
    
    <c:otherwise>
      <button class="btn btn-sm btn-primary">Update</button>
    </c:otherwise>
    </c:choose>
    
    </div>
    </div>
    </form>
    </td>
      
      
    </tr>
    </c:forEach>
  </tbody>
  
</table>
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