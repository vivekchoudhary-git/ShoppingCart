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
<p class="fs-4">Your Cart</p>

<c:if test="${not empty sessionScope.successMsg }">
<div style="color : green;font-size : 20px;font-family : georgia;" role="alert">
<c:out value="${sessionScope.successMsg}"/>
<!-- after displaying the message ,remove the session content -->
<c:remove var="successMsg" scope="session"/>
</div>
</c:if>

<c:if test="${not empty sessionScope.errorMsg}">
<div style="color : red;font-size : 20px;font-family : georgia;" role="alert">
<c:out value="${sessionScope.errorMsg}"/>
<!-- after displaying the message ,remove the session content -->
<c:remove var="errorMsg" scope="session"/>
</div>
</c:if>

</div>

<div class="card-body">
<table class="table">

  <thead>
    <tr>
      <th scope="col">Sr No.</th>
      <th scope="col">Product Image</th>
      <th scope="col">Product Name</th>
      <th scope="col">Price</th>
      <th scope="col">Quantity</th>
      <th scope="col">Total Price</th>
   
    </tr>
  </thead>
  
  <tbody>
  
  <c:forEach items="${cartsList}" var="cart" varStatus="c">
    <tr>
      <th scope="row">${c.count}</th>
      <td><img alt="not found" src="${pageContext.request.contextPath}${productImageUrl}${cart.product.imageName}" width="50px" height="50px"></td>
      <td>${cart.product.title}</td>
      <td>${cart.product.discountedPrice}</td>
      <td><a href="/user/upCartQty?symbol=dec&cid=${cart.id}"><i class="fa-solid fa-minus"></i></a>  [ ${cart.quantity} ] <a href="/user/upCartQty?symbol=inc&cid=${cart.id}"><i class="fa-solid fa-plus"></i></a></td>
      <td>${cart.totalPrice}</td>
    </tr>
    </c:forEach>
    
    <tr><td colspan="4"><td style="font-weight:bold;">Total Amount</td><td>&#8377  ${totalOrderPrice}</td></tr>
    
  </tbody>
  
</table>

<div class="text-center">
<a class="btn btn-warning" href="/user/order">Proceed to Payment</a>
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