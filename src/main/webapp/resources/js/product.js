$(function(){
	
	var $productForm=$("#productForm");
	
	$productForm.validate({
		
		rules:{
			
			title:{
				required : true,
				lettersOnly : true
			},
			
			description:{
				required : true,
				all : true
			},
			
			category:{
				required : true,
				all : true
			},
			
			price : {
				required : true,
				space : true,
				numericOnly : true
				
			},
			
			discount : {
				required : true,
				space : true,
				numericOnly : true
				
			},
			
			isActive : {
				required : true
				
			},
			
			stock : {
				required : true,
				space : true,
				numericOnly : true
			},
			
			file : {
				required : true	
			}
				
		},
		messages:{
			title:{
				required : 'enter title',
				lettersOnly : 'invalid name format'
			},
			
			description:{
				required : 'describe the product',
				all : 'first letter must not be space'
				
			},
			
			category:{
				required : 'select category',
				all : 'first letter must not be space'
			},
			
			price:{
				required : 'please enter price',
				space : 'space not allowed',
				numericOnly : 'invalid price'
				
			},
			
			discount : {
				required : 'please enter discount',
				space : 'space not allowed',
				numericOnly : 'invalid discount'
			},
				
			isActive : {
				required : 'select'

			},
			
			stock : {
				required : 'enter stock',
				space : 'space not allowed',
				numericOnly : 'invalid stock'
			},
			
			file : {
				required : 'upload image'	
			}
			
		}
		
			
		
	})
	
	
})


    jQuery.validator.addMethod('lettersOnly', function(value, element) {
		return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
	});
	
	
	
	jQuery.validator.addMethod('numericOnly', function(value, element) {
		return /^[0-9]+$/.test(value);
	});
	
	
			jQuery.validator.addMethod('space', function(value, element) {
		return /^[^-\s]+$/.test(value);
	});
	
	
	jQuery.validator.addMethod('all', function(value, element) {
		return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
	});
