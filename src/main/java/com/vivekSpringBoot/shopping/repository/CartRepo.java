package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vivekSpringBoot.shopping.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	public Cart findByProductIdAndUserDtlsId(Integer pid,Integer uid);
	
	@Query(value = "select count(*) from cart where user_dtls_id = :uid",nativeQuery = true)
	public Integer countCartUsingUserId(@Param("uid") Integer uid);
	
	
	public List<Cart> findByUserDtlsId(Integer uid);
	
}
