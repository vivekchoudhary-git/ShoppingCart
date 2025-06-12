package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekSpringBoot.shopping.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

	List<Product> findByIsActiveTrue();
	
}
