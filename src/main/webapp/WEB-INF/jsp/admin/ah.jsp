<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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

<!-- Navbar start -->

<nav class="navbar navbar-expand-lg bg-primary fixed-top navbar-dark" style="margin-bottom : 0px;border :1px solid black; height : 60.86px;"">
	<div class="container-fluid">
		<a class="navbar-brand" href="#"><i class="fa-solid fa-cart-plus"></i> Ecom Store</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">

				<c:choose>
					<c:when test="${user == null}">
						<li class="nav-item">
							<a class="nav-link active" aria-current="page" href="/"><i class="fa-solid fa-house-chimney"></i> Home</a>
						</li>
					</c:when>
					<c:otherwise>
						<c:if test="${user.role == 'ROLE_ADMIN'}">
							<li class="nav-item">
								<a class="nav-link active" href="/admin/"><i class="fa-solid fa-house-chimney"></i> Home</a>
							</li>
						</c:if>
						<c:if test="${user.role == 'ROLE_USER'}">
							<li class="nav-item">
								<a class="nav-link active" href="/"><i class="fa-solid fa-house-chimney"></i> Home</a>
							</li>
						</c:if>
					</c:otherwise>
				</c:choose>

				<li class="nav-item">
					<a class="nav-link active" aria-current="page" href="/product">Product</a>
				</li>

				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						Category
					</a>
					<ul class="dropdown-menu">
						<c:forEach var="c" items="${categorys}">
							<li>
								<a class="dropdown-item" href="/products?category=${c.name}">${c.name}</a>
							</li>
						</c:forEach>
					</ul>
				</li>

			</ul>

			<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
				<c:choose>
					<c:when test="${user == null}">
						<li class="nav-item">
							<a class="nav-link active" href="signin"><i class="fa-solid fa-right-to-bracket"></i> Login</a>
						</li>
						<li class="nav-item">
							<a class="nav-link active" href="/register">Register</a>
						</li>
						<li class="nav-item">
							<a class="nav-link active" href="/admin/">Admin</a>
						</li>
					</c:when>
					<c:otherwise>
						<c:if test="${user.role == 'ROLE_USER'}">
							<li class="nav-item">
								<a class="nav-link active" href="/user/cart">
									<i class="fa-solid fa-cart-shopping"></i> Cart [ ${countCart} ]
								</a>
							</li>
						</c:if>

						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle active" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
								<i class="fa-solid fa-user"></i> ${user.name}
							</a>
							<ul class="dropdown-menu">
								<c:if test="${user.role == 'ROLE_USER'}">
									<li><a class="dropdown-item" href="/user/profile">Profile</a></li>
									<li><a class="dropdown-item" href="/user/user-orders">My Orders</a></li>
								</c:if>
								<c:if test="${user.role == 'ROLE_ADMIN'}">
									<li><a class="dropdown-item" href="/admin/profile">Profile</a></li>
								</c:if>
								<li><a class="dropdown-item" href="/logout">Logout</a></li>
								<li><hr class="dropdown-divider"></li>
							</ul>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>

<!-- Navbar end -->

<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
crossorigin="anonymous">
</script>

</body>
</html>