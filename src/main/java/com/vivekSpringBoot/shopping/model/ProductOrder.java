package com.vivekSpringBoot.shopping.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ProductOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String orderId;
	private LocalDate orderDate;
	@ManyToOne
	private Product product;
	private Double price;                             // discounted price
	private Integer quantity;
	@ManyToOne
	private UserDtls userDtls;
	private String status;
	private String paymentType;
	@OneToOne(cascade = CascadeType.ALL)
	private OrderAddress orderAddress;
	
	
}
