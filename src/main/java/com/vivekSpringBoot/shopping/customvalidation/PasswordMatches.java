package com.vivekSpringBoot.shopping.customvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ElementType.TYPE})                                                       // Note :  class level
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {

	 String message() default "Password and Confirm Password must match";
	
	 Class<?>[] groups() default {};                                // we are not using it. but whether we use it or not we must declare it as per chatGPT
	   
	 Class<? extends Payload>[] payload() default {};              // we are not using it. but whether we use it or not we must declare it as per chatGPT
	
	
}
