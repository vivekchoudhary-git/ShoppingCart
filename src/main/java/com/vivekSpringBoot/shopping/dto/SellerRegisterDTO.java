package com.vivekSpringBoot.shopping.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.vivekSpringBoot.shopping.customvalidation.PasswordMatches;
import com.vivekSpringBoot.shopping.customvalidation.StrongPassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches                                                // class level validation
public class SellerRegisterDTO {

	@NotBlank(message = "Name is Required sv")
	private String name;
	
	@NotBlank(message = "phoneNo is Required sv")
	private String phoneNo;
	
	@NotBlank(message = "email is Required sv")
	@Email(message = "Invalid Email Format sv")
	private String email;
	
	@NotBlank(message = "address is Required sv")
	private String address;
	
	@NotBlank(message = "city is Required sv")
	private String city;
	
	@NotBlank(message = "state is Required sv")
	private String state;
	
	@NotBlank(message = "pincode is Required sv")
	private String pincode;
	
	@NotBlank(message = "Password is Required sv")
	@StrongPassword
	private String password;
	
	private String confirmpassword;                                   // added later
	
	private String profileImage;  
	
	@NotBlank(message = "company Name is Required sv")
	private String companyName;
	
	@NotBlank(message = "company address is Required sv")
	private String companyAddress;
	
	@NotBlank(message = "gst no is Required sv")
	private String gstNo;
	
	@NotBlank(message = "pan no is Required sv")
	private String panNo;
	
	@NotBlank(message = "bank account is Required sv")
	private String bankAccount;
	
	@NotBlank(message = "ifscCode is Required sv")
	private String ifscCode;
	
	@NotBlank(message = "regCertificate is Required sv")
	private String regCertificate;
	
	
}
