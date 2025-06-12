<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <!-- this search.jsp is not in becoder project.it has been created after suggestion by chatGPT -->
    
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

<div class="container-fluid bg-primary py-1 mt-5" style="height : 50px;border : 1px solid black;">
<div class="row">
<div class="col-md-8 offset-md-1 mb-5">
<form action="">
<div class="input-group">
<input class="form-control" type="text" name="search">
<button class="btn btn-light text-dark ms-3 col-md-2"><i class="fa-solid fa-magnifying-glass"></i>Search</button>
</div>
</form>
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