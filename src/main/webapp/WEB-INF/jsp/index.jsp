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
<!-- INDEX.JSP -->

<!-- Carousel starts (slide page) -->

<div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="resources/images/ecom1.png" class="d-block w-100" alt="Ecom1 image" >
    </div>
    <div class="carousel-item">
      <img src="resources/images/ecom3.jpg" class="d-block w-100" alt="Ecom3 image" >
    </div>
    <div class="carousel-item">
      <img src="resources/images/ecom2.jpg" class="d-block w-100" alt="Ecom2 image" >
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>

<!-- Carousel ends (slide page) -->

	<!-- Start Category Module -->
<div class="container">
    <div class="row">
        <p class="text-center fs-4">Category</p>
    
        <c:forEach items="${activeCategoriesList}" var="cat">
        <div class="col-md-2">
            <div class="card rounded-circle shadow-sm p-3 mb-5 bg-body-tertiary rounded">
                <div class="card-body text-center">
                    <img alt="image not found" src="${pageContext.request.contextPath}${categoryImageUrl}${cat.imageName}" width="65%" height="140px">
                   <a class="text-decoration-none" href="/product?category=${cat.name}"><p>${cat.name}</p></a>
                </div>
            </div>
        </div>
        </c:forEach>

    </div>
</div>
<!-- End Category Module -->


<!-- Product Module Start -->
<div class="container-fluid bg-light p-3">
    <div class="row">
        <p class="text-center fs-4">Latest product</p>

        <c:forEach items="${activeProductsList}" var="prod">
        <div class="col-md-3">
            <div class="card shadow-sm p-3 mb-5 bg-body-tertiary rounded">
                <div class="card-body text-center">
                    <img alt="image not found" src="${pageContext.request.contextPath}${productImageUrl}${prod.imageName}" width="65%" height="140px">
                    <a class="text-decoration-none" href="/viewProduct/${prod.id}"><p>${prod.title}</p></a>
                </div>
            </div>
        </div>
       </c:forEach>

    </div>
</div>
<!-- Product Module End  --> 
	
	


<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
crossorigin="anonymous">
</script>

</body>
</html>