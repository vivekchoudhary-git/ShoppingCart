package com.vivekSpringBoot.shopping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Component
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(length = 500)
	private String title;
	@Column(length = 5000)
	private String description;
	private String category;
	private Double price;
	@Column(nullable = true)                    // The annotation @Column(nullable = true) tells the JPA provider (like Hibernate) that this column can be NULL in the database.
	private Integer discount;                          // added later  // use Integer to handle null values
	private Double discountedPrice;            // added later
	private int stock;
	private String imageName;
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private SellerProfile sellerProfile;              // added later
	
	
}
