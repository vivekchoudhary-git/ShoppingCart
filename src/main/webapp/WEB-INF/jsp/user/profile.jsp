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

<div class="container-fluid mt-5 p-5 bg-light">

<div class="row">
<div class="col-md-10 offset-md-1">
<p class="fs-3 text-center">My Profile</p>

<c:if test="${not empty sessionScope.successMsg }">
<div style="color:green;font-size:20px;font-family:georgia;text-align:center">
<c:out value="${sessionScope.successMsg}"></c:out>
</div>
<!-- remove successMsg after showing it -->
<c:remove var="successMsg" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.errorMsg }">
<div style="color:red;font-size:20px;font-family:georgia;text-align:center">
<c:out value="${sessionScope.errorMsg}"></c:out>
</div>
<!-- remove successMsg after showing it -->
<c:remove var="errorMsg" scope="session"/>
</c:if>

<hr>

<div style="text-align:center">
<img style="length:100px;width:100px;border-radius:50%" alt="not found" src="${userImageUrl}${userDtls.profileImage}">

</div>

<div class="col-md-8 offset-md-2">

<form action="/user/updateProfile" method="post" enctype="multipart/form-data">
<table class="table table-borderless">

  <tbody>
    <tr>
      <th>Name</th><td> : </td><td><input class="form-control" type="text" name="name" value="${userDtls.name}"></td>
    </tr>
    
    <tr>
      <th>Phone No</th><td> : </td><td><input class="form-control" type="text" name="phoneNo" value="${userDtls.phoneNo}"></td>
    </tr>
    
    <tr>
      <th>Email</th><td> : </td><td><input class="form-control" type="email" name="email" value="${userDtls.email}" readonly="readonly"></td>
    </tr>
    
     <tr>
      <th>Address</th><td> : </td><td><input class="form-control" type="text" name="address" value="${userDtls.address}"></td>
    </tr>
    
    <tr>
      <th>City</th><td> : </td><td><input class="form-control" type="text" name="city" value="${userDtls.city}"></td>
    </tr>
    
    <tr>
      <th>State</th><td> : </td><td><input class="form-control" type="text" name="state" value="${userDtls.state}"></td>
    </tr>
    
     <tr>
      <th>Pincode</th><td> : </td><td><input class="form-control" type="text" name="pincode" value="${userDtls.pincode}"></td>
    </tr>
    
    <tr>
      <th>Image </th><td> : </td><td><input class="form-control" type="file" name="file"></td>
    </tr>
    
    <tr>
      <th>Role</th><td> : </td><td><input class="form-control" type="text" name="role" value="${userDtls.role}" readonly="readonly"></td>
    </tr>
    
     <tr>
      <th>Account Active</th><td> : </td><td><input class="form-control" type="text" name="isEnabled" value="${userDtls.isEnabled}" readonly="readonly"></td>
    </tr>
    
    <tr><td><input type="hidden" name="id" value="${userDtls.id}"></td></tr>
    
    <tr>
         <td colspan="3" class="text-center"><button class="btn btn-sm btn-primary mt-3" type="submit">Update Profile</button></td>
    </tr>
    
  </tbody>
</table>
</form>

</div>

</div>

<hr>

<div class="col-md-10 offset-md-1">

<div class="row">

<div class="col-md-6 offset-md-3">
<p class="fs-3 text-center">Change Password</p>

<form action="/user/changePassword" method="post">
<table class="table table-borderless">

  <tbody>
    <tr>
      <th>Current Password</th><td> : </td><td><input class="form-control" type="password" name="currentPassword"></td>
    </tr>
    
    <tr>
      <th>New Password</th><td> : </td><td><input class="form-control" type="password" name="newPassword"></td>
    </tr>
    
    <tr>
      <th>Confirm Password</th><td> : </td><td><input class="form-control" type="password" name="confirmPassword"></td>
    </tr>
    
    <tr>
    <td colspan="3" class="text-center"><button class="btn btn-sm btn-primary mt-3" type="submit">Update Password</button></td>
    </tr>
    
  </tbody>
</table>

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