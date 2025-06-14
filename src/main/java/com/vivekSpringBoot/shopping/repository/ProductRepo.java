package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vivekSpringBoot.shopping.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

	List<Product> findByIsActiveTrue();
	
	@Query(value = "select * from product where category = :category AND is_active = true",nativeQuery = true)
	public List<Product> fetchActiveProductByCategory(@Param("category") String category);
	
}
