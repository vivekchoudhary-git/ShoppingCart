package com.vivekSpringBoot.shopping.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.vivekSpringBoot.shopping.model.Cart;
import com.vivekSpringBoot.shopping.model.Product;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.repository.CartRepo;
import com.vivekSpringBoot.shopping.service.CartService;
import com.vivekSpringBoot.shopping.service.ProductService;
import com.vivekSpringBoot.shopping.service.UserDtlsService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductService productServiceImpl;
	
	@Autowired
	private UserDtlsService userDtlsServiceImpl;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Override
	public Cart saveCart(Integer productId, Integer userId) {
	
		Product product = productServiceImpl.getProductById(productId);
		
		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataById(userId);
		
		Cart oldCart = cartRepo.findByProductIdAndUserDtlsId(productId, userId);
		
// here we are applying logic that- first we check whether the cart already exist with selected productId and UserId.
// If not then we create new Cart but if it already exists than we increase the quantity and total price.		
		
		Cart cart = null;
		
		if(ObjectUtils.isEmpty(oldCart)) {
		
		cart = new Cart();	
		
		cart.setProduct(product);
		cart.setUserDtls(userDtls);
		cart.setQuantity(1);
		cart.setTotalPrice(1*product.getDiscountedPrice());
		
		}else {
			
			cart = oldCart;
			
			cart.setQuantity(oldCart.getQuantity()+1);
			cart.setTotalPrice(oldCart.getQuantity()*oldCart.getProduct().getDiscountedPrice());
			
		}
		
		Cart savedCart = cartRepo.save(cart);
		
		System.out.println("savedCart : "+savedCart);
		
		return savedCart;
	}

	@Override
	public Integer countCartByUserId(Integer uid) {
		
		Integer cartCount = cartRepo.countCartUsingUserId(uid);
		
		if(cartCount == null) {
			
			System.out.println("cartCount is null");
		}
		
		return cartCount;
	}

	@Override
	public List<Cart> getCartsByUserId(Integer uid) {
		
		List<Cart> cartList = cartRepo.findByUserDtlsId(uid);
		
// logic to calculate total amount of all carts which belongs to single user.		
		List<Cart> updatedCartList = new ArrayList<>();
		
		Double orderPriceTotal = 0.0;
		
		for(Cart cart : cartList) {
			
			cart.setTotalPrice(cart.getQuantity()*cart.getProduct().getDiscountedPrice());
			
			orderPriceTotal = orderPriceTotal+cart.getTotalPrice();
			
			cart.setTotalOrderPrice(orderPriceTotal);
			
			updatedCartList.add(cart);
			
		}
		
		if(CollectionUtils.isEmpty(cartList)) {
			
			System.out.println("cartList is null");
		}
		
		return updatedCartList;
		
	}

	@Override
	public void updateCartQuantityById(Integer cid,String symbol) {
		
		Optional<Cart> oldCartOptional = cartRepo.findById(cid);
		
		int updatedQty;
		
		if(oldCartOptional.isPresent()) {
			
			Cart oldCart = oldCartOptional.get();
			
			if(symbol.equals("dec")) {
				
				updatedQty = oldCart.getQuantity()-1;
				
	            oldCart.setQuantity(updatedQty);
				
				cartRepo.save(oldCart);
				
				if(updatedQty == 0) {
					
					cartRepo.delete(oldCart);
				}
				
			}else {
				
				updatedQty = oldCart.getQuantity()+1;
				
                oldCart.setQuantity(updatedQty);
				
				cartRepo.save(oldCart);
				
			}
			
			
		}
		
		
	}


}
