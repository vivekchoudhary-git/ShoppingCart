package com.vivekSpringBoot.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekSpringBoot.shopping.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	public Cart findByProductIdAndUserDtlsId(Integer pid,Integer uid);
	
}
