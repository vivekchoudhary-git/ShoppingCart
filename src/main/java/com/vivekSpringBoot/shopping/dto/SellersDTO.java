package com.vivekSpringBoot.shopping.dto;

// Spring boot throws errors "No converter found capable of converting" when we use DTO class for mapping and sql native query in repository.

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
// public class SellersDTO {
//	
//	private Integer sellerId;                        // this is seller_profile table id
//	private String name;
//	private String phoneNo;
//	private String email;
//	private String profileImage; 
//	private Boolean isEnabled;                       // this is of UserDtls
//	private String companyName;
//	private String companyAddress;
//	private String gstNo;
//	
//}

public interface SellersDTO {
	
	Integer getSellerId();
    String getName();
    String getPhoneNo();
    String getEmail();
    String getProfileImage();
    Boolean getIsEnabled();
    String getCompanyName();
    String getCompanyAddress();
    String getGstNo();
    String getAccountStatus();                   // added later
	
}

