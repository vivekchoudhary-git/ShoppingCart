package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekSpringBoot.shopping.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Boolean existsByName(String categoryName);
	
	List<Category> findByIsActiveTrue();
	
}
