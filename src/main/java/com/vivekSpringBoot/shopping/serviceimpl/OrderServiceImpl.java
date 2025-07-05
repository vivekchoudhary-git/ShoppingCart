package com.vivekSpringBoot.shopping.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivekSpringBoot.shopping.model.Cart;
import com.vivekSpringBoot.shopping.model.OrderAddress;
import com.vivekSpringBoot.shopping.model.OrderRequest;
import com.vivekSpringBoot.shopping.model.ProductOrder;
import com.vivekSpringBoot.shopping.repository.ProductOrderRepo;
import com.vivekSpringBoot.shopping.service.OrderService;
import com.vivekSpringBoot.shopping.utility.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private CartServiceImpl cartServiceImpl;
	
	@Autowired
	private ProductOrderRepo productOrderRepo;
	
	@Override
	public void saveOrderData(Integer userid, OrderRequest orderRequest) {
		
		List<Cart> cartsList = cartServiceImpl.getCartsByUserId(userid);
		
		for(Cart cart : cartsList) {
			
			ProductOrder productOrder = new ProductOrder();
			
			productOrder.setOrderId(UUID.randomUUID().toString());
			productOrder.setOrderDate(LocalDate.now());
			productOrder.setProduct(cart.getProduct());
			productOrder.setPrice(cart.getProduct().getDiscountedPrice());
			productOrder.setQuantity(cart.getQuantity());
			productOrder.setUserDtls(cart.getUserDtls());
			productOrder.setStatus(OrderStatus.IN_PROGRESS.getName());
			productOrder.setPaymentType(orderRequest.getPaymentType());
			
			OrderAddress orderAddress = new OrderAddress();
			
			orderAddress.setFirstName(orderRequest.getFirstName());
			orderAddress.setLastName(orderRequest.getLastName());
            orderAddress.setEmail(orderRequest.getEmail());
            orderAddress.setMobileNo(orderRequest.getMobileNo());
            orderAddress.setAddress(orderRequest.getAddress());
            orderAddress.setCity(orderRequest.getCity());
            orderAddress.setState(orderRequest.getState());
            orderAddress.setPincode(orderRequest.getPincode());
            
            productOrder.setOrderAddress(orderAddress);
            
            productOrderRepo.save(productOrder);
			
		}
		
		
	}

	
	
}
