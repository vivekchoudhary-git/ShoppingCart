package com.vivekSpringBoot.shopping.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;

import com.vivekSpringBoot.shopping.model.OrderRequest;
import com.vivekSpringBoot.shopping.model.ProductOrder;

public interface OrderService {

	public void saveOrderData(Integer userid,OrderRequest orderRequest) throws UnsupportedEncodingException, MessagingException;
	
	public List<ProductOrder> getAllOrdersByUserDtlsId(Integer userId);
	
	public ProductOrder updateOrderStatus(Integer oid,String status);
	
	public List<ProductOrder> getAllOrders();
	
	public ProductOrder getOrderDataByOrderId(String orderId);
	
	// this method is for pagination
	public Page<ProductOrder> getAllOrdersPaginated(Integer pageNo,Integer pageSize);
	
}
