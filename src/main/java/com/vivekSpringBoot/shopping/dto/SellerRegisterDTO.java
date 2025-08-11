package com.vivekSpringBoot.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerRegisterDTO {

	private String name;
	private String phoneNo;
	private String email;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String password;
	private String profileImage;  
	
	private String companyName;
	private String companyAddress;
	private String gstNo;
	private String panNo;
	private String bankAccount;
	private String ifscCode;
	private String regCertificate;
	
	
}
