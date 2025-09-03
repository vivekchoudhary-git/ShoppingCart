package com.vivekSpringBoot.shopping.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Product product;
	@ManyToOne
	private UserDtls userDtls;                        // note UserDtls Bean class does not have Cart as Property
	private Integer quantity;
	@Transient                               // this annotation will not allow to create  totalPrice column in table
	private Double totalPrice;
	
	@Transient
	private Double totalOrderPrice;           // added later
	
}
