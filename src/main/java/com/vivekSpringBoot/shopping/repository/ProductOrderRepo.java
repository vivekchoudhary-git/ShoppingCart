package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekSpringBoot.shopping.model.ProductOrder;

public interface ProductOrderRepo extends JpaRepository<ProductOrder, Integer> {

	public List<ProductOrder> findByUserDtlsId(Integer userId);
	
}
