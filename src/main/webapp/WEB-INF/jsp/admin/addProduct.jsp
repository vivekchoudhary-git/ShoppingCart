<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	
	<link rel="stylesheet" href="resources/css/style.css">

</head>
<body>

<div class="container p-5 mt-3">

<div class="row">

<div class="col-md-6 offset-md-3">
<div class="card card-sh">
<div class="card-header text-center fs-4">Add Product</div>
<div class="card-body">
<form action="">

<div class="mb-3">
<label>Enter Title</label><input class="form-control" type="text" name="title">
</div>

<div class="mb-3">
<label>Enter Description</label><textarea rows="3" cols="" class="form-control"></textarea>
</div>

<div class="mb-3">
<label>Category</label>
<select class="form-control">
<option>---- Select ----</option>
<option>Electronics</option>
</select>
</div>

<div class="mb-3">
<label>Enter Price</label><input class="form-control" type="text" name="title">
</div>

<div class="row">

<div class="mb-3 col">
<label>Enter Stock</label><input class="form-control" type="text" name="stocks">
</div>

<div class="mb-3 col">
<label>Upload Image</label><input class="form-control" type="file" name="image">
</div>

</div>

<button class="btn btn-primary col-md-12">Submit</button>

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