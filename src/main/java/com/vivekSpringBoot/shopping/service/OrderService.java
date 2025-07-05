package com.vivekSpringBoot.shopping.service;

import com.vivekSpringBoot.shopping.model.OrderRequest;

public interface OrderService {

	public void saveOrderData(Integer userid,OrderRequest orderRequest);
	
}
