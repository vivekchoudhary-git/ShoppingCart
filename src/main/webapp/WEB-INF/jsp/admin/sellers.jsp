<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>sellers.jsp (admin)</title>

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

<div class="container mt-5 p-2" style="font-weight : bolder;">

<div class="card card-sh">

<div class="card-header text-center">

<p class="fs-4"> Sellers </p>

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
      <th scope="col">Sellers ID</th>
      <th scope="col">Profile</th>
      <th scope="col">Name</th>
      <th scope="col">Email</th>
      <th scope="col">Mobile No</th>
      <th scope="col">Company Name</th>
      <th scope="col">Company Address</th>
      <th scope="col">GST No.</th>
      <th scope="col">Status</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  
  <tbody>
  <c:forEach items="${allSellersList}" var="sellersDTO" varStatus="c">
    <tr>
      <th scope="row">${c.count}</th>
      <td>${sellersDTO.sellerId}</td>
      <td><img alt="not found" src="${userImageUrl}${sellersDTO.profileImage}" width="50px" height="50px"></td>
      <td>${sellersDTO.name}</td>
      <td>${sellersDTO.email}</td>
      <td>${sellersDTO.phoneNo}</td>
      <td>${sellersDTO.companyName}</td>
      <td>${sellersDTO.companyAddress}</td>
      <td>${sellersDTO.gstNo}</td>
      <td>${sellersDTO.accountStatus}</td>
     
     <td style="min-width:250px;">
    <form action="/admin/upAccStatus" method="post">
    <div class="row">

    <div class="col">
    
    <input type="hidden" value="${sellersDTO.sellerId}" name="sid">
    
    <select class="form-control" name="accStatusId">
    <option value="" disabled="disabled" selected="selected">-- Select --</option>
    <option value="1">Pending</option>
    <option value="2">Approved</option>
    <option value="3">Rejected</option>
    <option value="4">Suspended</option>
    </select>
    </div>

    <div class="col">
      <button class="btn btn-sm btn-primary">Update</button>
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