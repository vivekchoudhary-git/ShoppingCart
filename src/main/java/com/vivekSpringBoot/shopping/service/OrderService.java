package com.vivekSpringBoot.shopping.service;

import java.util.List;

import com.vivekSpringBoot.shopping.model.OrderRequest;
import com.vivekSpringBoot.shopping.model.ProductOrder;

public interface OrderService {

	public void saveOrderData(Integer userid,OrderRequest orderRequest);
	
	public List<ProductOrder> getAllOrdersByUserDtlsId(Integer userId);
	
	public Boolean updateOrderStatusByUser(Integer oid,String status);
	
}
