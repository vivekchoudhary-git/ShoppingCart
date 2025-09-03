package com.vivekSpringBoot.shopping.service;

import java.util.List;

import com.vivekSpringBoot.shopping.model.Cart;

public interface CartService {

	public Cart saveCart(Integer productId,Integer userId);
	
	public Integer countCartByUserId(Integer uid);
	
	public List<Cart> getCartsByUserId(Integer uid);
	
	public void updateCartQuantityById(Integer cid,String symbol);
	
	public void deleteCartByUserId(Integer uid);
	
}
