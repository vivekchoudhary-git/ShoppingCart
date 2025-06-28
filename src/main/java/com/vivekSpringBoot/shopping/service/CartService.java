package com.vivekSpringBoot.shopping.service;

import com.vivekSpringBoot.shopping.model.Cart;

public interface CartService {

	public Cart saveCart(Integer productId,Integer userId);
	
	
}
