package com.vivekSpringBoot.shopping.customvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vivekSpringBoot.shopping.model.UserDtls;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{

	@Override
	public boolean isValid(Object ob, ConstraintValidatorContext context) {
		
		if(ob instanceof UserDtls) {
			
			UserDtls userDtls= (UserDtls)ob;
			
			if(userDtls.getPassword()==null || userDtls.getConfirmpassword()==null) {
				
				return false;
			}
			
			userDtls.getPassword().equals(userDtls.getConfirmpassword());
			
		}
		
		return false;
	}
	
}
