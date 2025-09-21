package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vivekSpringBoot.shopping.dto.SellerOrderDTO;
import com.vivekSpringBoot.shopping.model.ProductOrder;

public interface ProductOrderRepo extends JpaRepository<ProductOrder, Integer> {

	public List<ProductOrder> findByUserDtlsId(Integer userId);
	
	public ProductOrder findByorderId(String orderId);
	
	@Query(value = "select po.id as id,po.order_id as orderId,po.order_date as orderDate,po.price as price,po.quantity as quantity,po.status as status,p.title as title,oa.first_name as firstName,oa.last_name as lastName,oa.email as email,oa.mobile_no as mobileNo,oa.address as address,oa.city as city,oa.state as state,oa.pincode as pincode "+
			" from product_order po "+
			" inner join product p "+
			" on po.product_id = p.id "+
			" inner join order_address oa "+
			" on po.order_address_id = oa.id "+
			" where p.seller_id = :sellerId ",nativeQuery = true)
	public Page<SellerOrderDTO> findOrdersOfEachSellers(@Param("sellerId") Integer sellerId,Pageable pageable);
	
	
	@Query(value = "select po.id as id,po.order_id as orderId,po.order_date as orderDate,po.price as price,po.quantity as quantity,po.status as status,p.title as title,oa.first_name as firstName,oa.last_name as lastName,oa.email as email,oa.mobile_no as mobileNo,oa.address as address,oa.city as city,oa.state as state,oa.pincode as pincode "+
			" from product_order po "+
			" inner join product p "+
			" on po.product_id = p.id "+
			" inner join order_address oa "+
			" on po.order_address_id = oa.id "+
			" where p.seller_id = :sellerId  and po.order_id = :orderId ",nativeQuery = true)
	public SellerOrderDTO findOrdersOfEachSellerByOrderId(@Param("sellerId") Integer sellerId,@Param("orderId") String orderId);
	
	
	@Query(value = "select count(*) from product_order",nativeQuery = true)
	public long countOrders();
	
	@Query(value = "select count(*) from product_order where status = 'Delivered'",nativeQuery = true)
	public long countDeliveredOrders();
	
	@Query(value = "select count(*) from product_order where status != 'Delivered' and status != 'Cancelled'",nativeQuery = true)
	public long countPendingOrders();
	
	@Query(value = "select count(*) from product_order where status = 'Cancelled'",nativeQuery = true)
	public long countCancelledOrders();
	
	@Query(value = "select sum(price*quantity) from product_order where status = 'Delivered'",nativeQuery = true)
	public Double revenueGeneratedByDeliveredOrders();
	
	
	@Query(value = " select count(*) "+
			" from product_order  po inner join product p "+
			" on po.product_id = p.id "+
			" where p.seller_id= :sellerId ",nativeQuery = true)
	public long countOrdersOfSeller(@Param("sellerId") Integer sellerId);
	
	
	@Query(value = " select count(*) "+
			" from product_order  po inner join product p "+
			" on po.product_id = p.id "+
			" where p.seller_id= :sellerId and po.status='Delivered' ",nativeQuery = true)
	public long countSellerDeliveredOrders(@Param("sellerId") Integer sellerId);
	
	
	@Query(value = " select count(*) "+
			" from product_order  po inner join product p "+
			" on po.product_id = p.id "+
			" where p.seller_id= :sellerId and po.status !='Delivered' and po.status != 'Cancelled' ",nativeQuery = true)
	public long countSellerPendingOrders(@Param("sellerId") Integer sellerId);
	
	
	@Query(value = " select count(*) "+
			" from product_order  po inner join product p "+
			" on po.product_id = p.id "+
			" where p.seller_id= :sellerId and po.status ='Cancelled' ",nativeQuery = true)
	public long countSellerCancelledOrders(@Param("sellerId") Integer sellerId);
	
	
	@Query(value = " select sum(po.price*po.quantity) "+
			" from product_order  po inner join product p "+
			" on po.product_id = p.id "+
			" where p.seller_id= :sellerId and po.status ='Delivered' ",nativeQuery = true)
	public Double revenueGeneratedBySellerDeliveredOrders(@Param("sellerId") Integer sellerId);
	
}
