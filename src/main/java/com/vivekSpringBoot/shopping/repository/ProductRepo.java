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
	
	@Query(value = "select * from product"+" where is_active = true"+" AND (LOWER(title) LIKE LOWER('%' || :keyword || '%')"+" OR LOWER(category) LIKE LOWER('%' || :keyword || '%'))",nativeQuery = true)
	public List<Product> searchAnyProductByTitleOrCategory(@Param("keyword") String keyword);
	
}




// Note :

// If keyword = "" (empty string), this query returns:
//
// All active products, regardless of title or category.
//
// So what does LIKE '%%' do?
// It matches everything, because:
//
// % means "match zero or more characters"
//
// So %% is equivalent to % — which matches everything