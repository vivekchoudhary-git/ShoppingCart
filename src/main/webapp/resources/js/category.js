
$(function(){
	
	var $categoryForm = $("#categoryForm");
	
	$categoryForm.validate({
		
		rules : {
			
			name : {
				required : true,
				lettersOnly : true,
				all : true
			}
			
		},
		
		messages : {
			
			name : {
				required : 'enter category name',
				lettersOnly : 'invalid category name',
				all : 'first letter must not be space'
			}
			
		}
		
		
	})
	
	
})



  jQuery.validator.addMethod('lettersOnly', function(value, element) {
		return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
	});
	
  jQuery.validator.addMethod('all', function(value, element) {
		return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
	});