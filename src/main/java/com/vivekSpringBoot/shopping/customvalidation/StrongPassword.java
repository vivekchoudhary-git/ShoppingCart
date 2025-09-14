package com.vivekSpringBoot.shopping.customvalidation;

// Custom Annotation

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {PasswordValidator.class})
@Target({ElementType.FIELD})                                         // field or class property level
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

   String message() default "Weak Password, Password must contain at least 1 uppercase, 1 lowercase, and 1 digit";
	
   Class<?>[] groups() default {};                                // we are not using it. but whether we use it or not we must declare it as per chatGPT
   
   Class<? extends Payload>[] payload() default {};              // we are not using it. but whether we use it or not we must declare it as per chatGPT
   
	
}
