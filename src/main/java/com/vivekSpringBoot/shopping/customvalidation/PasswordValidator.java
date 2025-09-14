package com.vivekSpringBoot.shopping.customvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<StrongPassword,String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
	
		if(password == null || password.isEmpty()){
			
			return false;
		}
		
		// must contain at least 1 uppercase, 1 lowercase, and 1 digit
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
	}


}
