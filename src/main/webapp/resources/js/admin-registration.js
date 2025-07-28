
$(function(){
	
	var $adminRegisForm=$("#adminRegisForm");
	
	$adminRegisForm.validate({
		
		rules:{
			
			name:{
				required : true,
				lettersOnly : true
			},
			
			phoneNo:{
				required : true,
				space : true,
				numericOnly : true,
				minlength : 10,
				maxlength : 10
			},
			
			email:{
				required : true,
				space : true,
				email : true
			},
			
			address : {
				required : true,
				all : true
				
			},
			
			city : {
				required : true,
				all : true
				
			},
			
			state : {
				required : true,
				all : true
				
			},
			
			pincode : {
				required : true,
				space : true,
				numericOnly : true
			},
			
			password : {
				required : true,
				space : true
			},
			
			confirmpassword : {
				required : true,
				space : true,
				equalTo : '#pass'
				
			},
			
			file : {
				required : true	
			}
				
		},
		messages:{
			name:{
				required : 'please provide name',
				lettersOnly : 'invalid name format'
			},
			
			phoneNo:{
				required : 'please provide phone number',
				space : 'space not allowed',
				numericOnly : 'invalid phone no',
				minlength : 'must be 10 digit',
				maxlength : 'must be 10 digit'
			},
			
			email:{
				required : 'please provide email',
				space : 'space not allowed',
				email : 'invalid email'
				
			},
			
			address:{
				required : 'please enter address',
				all : 'invalid address (dont start with space)'
				
			},
			
			city : {
				required : 'please enter city',
				all : 'invalid city (dont start with space)'
			},
			
			state : {
				required : 'please enter state',
				all : 'invalid state (dont start with space)'
			},
			
			pincode : {
				required : 'please enter pincode',
				space : 'space not allowed',
				numericOnly : 'invalid pincode'
			},
			
			password : {
				required : 'please enter password',
				space : 'space not allowed'
			},
			
			confirmpassword : {
				required : 'please enter password',
				space : 'space not allowed',
				equalTo : 'password does not match'
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
