package com.vivekSpringBoot.shopping.serviceimpl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.vivekSpringBoot.shopping.dto.SellerOrderDTO;
import com.vivekSpringBoot.shopping.model.Cart;
import com.vivekSpringBoot.shopping.model.OrderAddress;
import com.vivekSpringBoot.shopping.model.OrderRequest;
import com.vivekSpringBoot.shopping.model.ProductOrder;
import com.vivekSpringBoot.shopping.repository.ProductOrderRepo;
import com.vivekSpringBoot.shopping.service.OrderService;
import com.vivekSpringBoot.shopping.utility.EmailUtility;
import com.vivekSpringBoot.shopping.utility.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private CartServiceImpl cartServiceImpl;
	
	@Autowired
	private ProductOrderRepo productOrderRepo;
	
	@Autowired
	private EmailUtility emailUtility;
	
	@Override
	public void saveOrderData(Integer userid, OrderRequest orderRequest) throws UnsupportedEncodingException, MessagingException {
		
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
            
            ProductOrder savedOrder = productOrderRepo.save(productOrder);
            
            if(!ObjectUtils.isEmpty(cartsList)) {
            
            emailUtility.sendEmailToUserAboutOrderStatus(savedOrder, OrderStatus.SUCCESS.getName());
            }else {
            	
            	System.out.println("Email could not be sent");
            }
            
            
		}
		
		
	}

	@Override
	public List<ProductOrder> getAllOrdersByUserDtlsId(Integer userId) {
		
		List<ProductOrder> ordersList = productOrderRepo.findByUserDtlsId(userId);
		
		if(CollectionUtils.isEmpty(ordersList)) {
			
			System.out.println("ordersList is null");
		}
		
		return ordersList;
	}

	@Override
	public ProductOrder updateOrderStatus(Integer oid, String status) {
		
		Optional<ProductOrder> productOrderOptional = productOrderRepo.findById(oid);
		
	   if(productOrderOptional.isPresent()) {
		   
		   ProductOrder productOrder = productOrderOptional.get();
		   
		   productOrder.setStatus(status);
		   ProductOrder updatedProductOrder = productOrderRepo.save(productOrder);
		   
		   return updatedProductOrder;
	   }
	   
		return null;
	}

	@Override
	public List<ProductOrder> getAllOrders() {

		List<ProductOrder> allOrdersList = productOrderRepo.findAll();
		
		if(!CollectionUtils.isEmpty(allOrdersList)) {
			
			return allOrdersList;
		}
		
		return null;
	}

	@Override
	public ProductOrder getOrderDataByOrderId(String orderId) {
		
		ProductOrder productOrder = productOrderRepo.findByorderId(orderId);
		
		if(!ObjectUtils.isEmpty(productOrder)) {
			
			return productOrder;
		}
		
		return null;
	}

	@Override
	public Page<ProductOrder> getAllOrdersPaginated(Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<ProductOrder> ordersListPaginated = productOrderRepo.findAll(pageable);
		
		return ordersListPaginated;
	}

	@Override
	public Page<SellerOrderDTO> getOrdersOfEachSellerPaginated(Integer sellerid,Integer pageNo,Integer pageSize) {
		
		Pageable pageable= PageRequest.of(pageNo, pageSize);
		
		Page<SellerOrderDTO> sellerOrdersList = productOrderRepo.findOrdersOfEachSellers(sellerid, pageable);
		
		return sellerOrdersList;
	}

	@Override
	public SellerOrderDTO getOrderDataByOrderIdAndSellerId(String orderId, Integer sellerId) {
		
		SellerOrderDTO sellerOrder = productOrderRepo.findOrdersOfEachSellerByOrderId(sellerId, orderId);
		
		return sellerOrder;
	}

	
	@Scheduled(cron = "0 */5 * ? * *")
	@Override
	public long countAllOrders() {
		
		long countOrders = productOrderRepo.countOrders();
			
		return countOrders;
	}

	
	@Scheduled(cron = "0 */5 * ? * *")
	@Override
	public long countAllDeliveredOrders() {
		
		long deliveredOrdersCount = productOrderRepo.countDeliveredOrders();
		System.out.println("countAllDeliveredOrders method called :"+deliveredOrdersCount);                         // only for checking crone scheduler
		
		return deliveredOrdersCount;
	}

	
	@Scheduled(cron = "0 */5 * ? * *")
	@Override
	public long countAllPendingOrders() {
		
		long pendingOrdersCount = productOrderRepo.countPendingOrders();
		
		return pendingOrdersCount;
	}

	
	@Scheduled(cron = "0 */5 * ? * *")
	@Override
	public long countAllCancelledOrders() {
		
		long cancelledOrdersCount = productOrderRepo.countCancelledOrders();
		
		return cancelledOrdersCount;
	}

	
	@Scheduled(cron = "0 */5 * ? * *")
	@Override
	public Double totalRevenueGeneratedByDeliveredOrders() {
		
		Double revenueDeliveredOrders = productOrderRepo.revenueGeneratedByDeliveredOrders();
		
		return revenueDeliveredOrders;
	}

	@Override
	public long countSellerOrdersData(Integer sellerId) {
		
		long sellerOrdersCount = productOrderRepo.countOrdersOfSeller(sellerId);
		
		return sellerOrdersCount;
	}

	@Override
	public long countSellerDeliveredOrdersData(Integer sellerId) {
		
		long sellerDeliveredOrdersCount = productOrderRepo.countSellerDeliveredOrders(sellerId);
		
		return sellerDeliveredOrdersCount;
	}

	@Override
	public long countSellerPendingOrdersData(Integer sellerId) {
		
		long sellerPendingOrdersCount = productOrderRepo.countSellerPendingOrders(sellerId);
		
		return sellerPendingOrdersCount;
	}

	@Override
	public long countSellerCancelledOrdersData(Integer sellerId) {
		
		long sellerCancelledOrdersCount = productOrderRepo.countSellerCancelledOrders(sellerId);
		
		return sellerCancelledOrdersCount;
	}

	@Override
	public Double totalRevenueGeneratedBySellerByDeliveredOrders(Integer sellerId) {
		
		Double revenueOfSeller = productOrderRepo.revenueGeneratedBySellerDeliveredOrders(sellerId);
		
		return revenueOfSeller;
	}

	
	
}
