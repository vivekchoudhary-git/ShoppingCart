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

<div class="container mt-5 p-5" style="font-weight : bolder;">

<div class="card card-sh">

<div class="card-header text-center">
<p class="fs-4">My Orders</p>

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
      <th scope="col">Order Date</th>
      <th scope="col">Product Detail</th>
      <th scope="col">Price</th>
      <th scope="col">Status</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  
  <tbody>
  <c:forEach items="${ordersList}" var="order">
    <tr>
      <td>${order.orderId}</td>
      <td>${order.orderDate}</td>
      <td>${order.product.title}</td>
      <td>Quantity : ${order.quantity} <br>
          Price : ${order.price} <br>
          Total Price : ${order.quantity * order.price}
      </td>
      <td>${order.status}</td>
      
      <td>
      <c:choose>
      <c:when test="${order.status != 'Cancelled' }">
      <a href="/user/changeStatus?oid=${order.id}&statusId=6" class="btn btn-sm btn-danger">Cancel Order</a>
      </c:when>
      
      <c:otherwise>
      <a href="#" class="btn btn-sm btn-danger disabled">Cancel Order</a>
      </c:otherwise>
      </c:choose>
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