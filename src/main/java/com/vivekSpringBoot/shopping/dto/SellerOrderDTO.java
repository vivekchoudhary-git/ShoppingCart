package com.vivekSpringBoot.shopping.dto;

import java.time.LocalDate;

//Spring boot throws errors "No converter found capable of converting" when we use DTO class for mapping and sql native query in repository.
// spring is unable to map data from postgres database table to Class SellerOrderDTO

//Use a projection interface (getter-based projection)

//public class SellerOrderDTO {
//
//	// ProductOrder
//	private Integer id;                     // ProductOrder id
//	private String orderId;
//	private LocalDate orderDate;
//	private Double price;
//	private Integer quantity;
//	private String status;
//	
//	// Product
//	private String title;
//	
//	// OrderAddress
//	private String firstName;
//	private String lastName;
//	private String email;
//	private String mobileNo;
//	private String address;
//	private String city;
//	private String state;
//	private String pincode;
//	
//}


public interface SellerOrderDTO {

	// ProductOrder
	 Integer getId();                     // ProductOrder id
	 String getOrderId();
	 LocalDate getOrderDate();
	 Double getPrice();
	 Integer getQuantity();
	 String getStatus();
	
	// Product
	 String getTitle();
	
	// OrderAddress
	 String getFirstName();
	 String getLastName();
	 String getEmail();
	 String getMobileNo();
	 String getAddress();
	 String getCity();
	 String getState();
	 String getPincode();
	
}
