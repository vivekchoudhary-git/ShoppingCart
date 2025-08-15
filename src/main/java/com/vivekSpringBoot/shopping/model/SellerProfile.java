package com.vivekSpringBoot.shopping.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SellerProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String companyName;
	private String companyAddress;
	private String gstNo;
	private String panNo;
	private String bankAccount;
	private String ifscCode;
	private String regCertificate;             // company registration certificate name
	
	private String accountStatus;                     // to check status of seller account
	private LocalDateTime accountCreatedAt;
	private LocalDateTime accountUpdatedAt;
	
	@OneToOne
	@JoinColumn(name = "user_id",nullable = false,unique = true)
	private UserDtls userDtls;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> productsList = new ArrayList<>();           // to handle null pointer exception  // added later
	
	
	// using helper methods as recommended by chatGPT
	
//	public void addProduct(Product product) {
//		productsList.add(product);
//		product.setSellerProfile(this);           // bidirectional mapping
//	}
//	
//	public void removeProduct(Product product) {
//		productsList.remove(product);
//		product.setSellerProfile(null);
//	}
	
	
}
