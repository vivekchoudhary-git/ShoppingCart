package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	// this method is for pagination
	@Query(value = "select * from product where category = :category AND is_active = true", 
		       countQuery = "select count(*) from product where category = :category AND is_active = true", 
		       nativeQuery = true)
	Page<Product> fetchActiveProductByCategory(@Param("category") String category, Pageable pageable);

	
	// Alternate way to write the above method without SQL Query
//	Page<Product> findByCategoryAndIsActiveTrue(String category, Pageable pageable);

	
	// this method is for pagination
	Page<Product> findByIsActiveTrue(Pageable pageable);
	
	// this method is for pagination (including both active and inactive products)
	@Query(value = "SELECT * FROM product " +
            "WHERE " +
            "(LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(category) LIKE LOWER(CONCAT('%', :keyword, '%')))",
    countQuery = "SELECT COUNT(*) FROM product " +
                 "WHERE " +
                 "(LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                 "OR LOWER(category) LIKE LOWER(CONCAT('%', :keyword, '%')))",
    nativeQuery = true)
    Page<Product> searchAnyProductByTitleOrCategoryPaginated(@Param("keyword") String keyword, Pageable pageable);

	
	// this method is for pagination (only active products)
	@Query(value = "SELECT * FROM product " +
            "WHERE is_active = true " +
            "AND (LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(category) LIKE LOWER(CONCAT('%', :keyword, '%')))",
    countQuery = "SELECT COUNT(*) FROM product " +
                 "WHERE is_active = true" +
                 "AND (LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                 "OR LOWER(category) LIKE LOWER(CONCAT('%', :keyword, '%')))",
    nativeQuery = true)
    Page<Product> searchAnyActiveProductByTitleOrCategoryPaginated(@Param("keyword") String keyword, Pageable pageable);
	
	
	Page<Product> findBySellerProfileId(Integer sid,Pageable pageable);
	
	
	// using method overloading concept
	// this method is for pagination (including both active and inactive products)
	@Query(value = "SELECT * FROM product " +
            "WHERE seller_id = :sid AND " +
            "(LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(category) LIKE LOWER(CONCAT('%', :keyword, '%')))",
    countQuery = "SELECT COUNT(*) FROM product " +
                 "WHERE seller_id = :sid AND " +
                 "(LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                 "OR LOWER(category) LIKE LOWER(CONCAT('%', :keyword, '%')))",
    nativeQuery = true)
    Page<Product> searchAnyProductByTitleOrCategoryPaginated(@Param("keyword") String keyword,@Param("sid") Integer sid ,Pageable pageable);
	
	
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