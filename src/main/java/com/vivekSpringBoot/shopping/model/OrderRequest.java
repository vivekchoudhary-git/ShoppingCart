package com.vivekSpringBoot.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String paymentType;
	
}
