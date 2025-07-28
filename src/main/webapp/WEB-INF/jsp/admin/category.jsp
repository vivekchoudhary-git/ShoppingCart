<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page isELIgnored="false" %>
    
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
<!-- <link rel="stylesheet" href="resources/css/style.css"> -->

<!-- 	we can see our css file i.e style.css at http://localhost:8080/resources/css/style.css after using pageContext.request.contextPath -->
<!-- this is recommended by chatGPT -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

</head>
<body>

<div class="container-fluid p-5 mt-1">

<div class="row">

<div class="col-md-4">
<div class="card card-sh">
<div class="card-header text-center fs-4">
<p class="fs-4"> Add Category</p>

<!-- showing success and error alert message in jsp -->
<c:if test="${not empty sessionScope.successMsg }">
<div style="color : green;font-size : 20px;font-family : georgia" role="alert">
<c:out value="${sessionScope.successMsg}"></c:out>
<!-- we can also remove successMsg value after displaying the alert message using this tag Note:this is clean way -->
<c:remove var="successMsg" scope="session"/>               
</div>

<!-- here we are removing session values Note : it should be written just after sessionScope.successMsg else the successMsg will be deleted before showing the alert message. -->
<%-- <%
session.removeAttribute("successMsg");
%> --%>

</c:if>

<c:if test="${not empty sessionScope.errorMsg }">
<div style="color:red;font-size : 20px; font-family : georgia">
<c:out value="${sessionScope.errorMsg}"></c:out>
</div>

<!-- here we are removing session values Note : it should be written just after sessionScope.errorMsg else the errorMsg will be deleted before showing the alert message. -->
<%
session.removeAttribute("errorMsg");
%>

</c:if>

</div>

<div class="card-body">
<form action="/admin/saveCategory" method="post" enctype="multipart/form-data" id="categoryForm" novalidate="novalidate">

<div class="mb-3">
<label>Enter Category</label><input class="form-control" type="text" name="name">
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

<div class="mb-3">
<label>Upload Image</label><input class="form-control" type="file" name="file">
</div>

<button class="btn btn-primary col-md-12"> Add </button>

</form>
</div>

</div>
</div>

<div class="col-md-8">
<div class="card card-sh">
<div class="card-header text-center fs-4">
<div class="card-body">
<table class="table">
  <thead>
    <tr>
      <th scope="col">Sr No.</th>
      <th scope="col">Category</th>
      <th scope="col">Status</th>
      <th scope="col">Image</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  
  <tbody> 
  <c:forEach items="${catList}" var="cat" varStatus="c">
  
    <tr>
      <th scope="row"><c:out value="${c.count}"></c:out></th>
      <td><c:out value="${cat.name}"></c:out></td>
      <td><c:out value="${cat.isActive}"></c:out></td>
      <td><img alt="not found" src="${pageContext.request.contextPath}${categoryImageUrl}${cat.imageName}" width="50px" height="50px"></td>
      <td><a href="/admin/editCategory/${cat.id}" class="btn btn-primary btn-sm"><i class="fa-solid fa-pen-to-square"></i> Edit </a><a href="/admin/deleteCategory/${cat.id}" class="btn btn-danger btn-sm ms-2"><i class="fa-solid fa-trash-can"></i> Delete </a></td>
    </tr>
    		
    </c:forEach>
  </tbody>
</table>
</div>
</div>
</div>
</div>

</div>

</div>




 <!-- starts jQuery Validation Library -->
 
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/category.js"></script>
 
  <!-- ends jQuery Validation Library -->


<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
crossorigin="anonymous">
</script>

</body>
</html>