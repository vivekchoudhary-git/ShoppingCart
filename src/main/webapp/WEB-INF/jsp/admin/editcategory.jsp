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

<div class="container-fluid p-5 mt-3">

<div class="row">

<div class="col-md-6 offset-md-3">
<div class="card card-sh">
<div class="card-header text-center fs-4">
<p class="fs-4"> Edit Category</p>

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
<form action="/admin/updateCategory" method="post" enctype="multipart/form-data">

<input type="hidden" name="id" value="${oldCategory.id}">

<div class="mb-3">
<label>Enter Category</label><input class="form-control" type="text" name="name" value="${oldCategory.name}">
</div>

<div class="mb-3">
<label>Status</label>

<div class="form-check">
  <input class="form-check-input" type="radio" name="isActive" id="radioDefault1" <c:if test="${oldCategory.isActive == true}">checked</c:if> value="true">
  <label class="form-check-label" for="radioDefault1">
    Active
  </label>
</div>
<div class="form-check">
  <input class="form-check-input" type="radio" name="isActive" id="radioDefault2" <c:if test="${oldCategory.isActive == false}">checked</c:if> value="false">
  <label class="form-check-label" for="radioDefault2">
   Inactive
  </label>
</div>

</div>

<div class="mb-3">
<label>Upload Image</label><input class="form-control" type="file" name="file">
</div>

<div class="mb-3" style="padding-left : 40%">
<img alt="not found" src="${pageContext.request.contextPath}${categoryImageUrl}${oldCategory.imageName}" width="100px" height="100px">
</div>

<button class="btn btn-primary col-md-12"> Update </button>

</form>
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