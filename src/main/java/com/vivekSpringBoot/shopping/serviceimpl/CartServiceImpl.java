package com.vivekSpringBoot.shopping.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


}
