package com.vivekSpringBoot.shopping.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vivekSpringBoot.shopping.model.Cart;
import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.service.CartService;
import com.vivekSpringBoot.shopping.service.CategoryService;
import com.vivekSpringBoot.shopping.serviceimpl.UserDtlsServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDtlsServiceImpl userDtlsServiceImpl;
	
	@Autowired
	private CategoryService categoryServiceImpl;
	
	@Autowired
	private CartService cartServiceImpl;
	
	@Autowired
	private Environment environment;
	

	@GetMapping("/")
	public String userHome() {
		
		return "userHome";
	}
	
	    // handled this in GlobalModelAttribute class 
	    // add this method to all controller so that whenever any controller gets called we can fetch logged in user data
//		@ModelAttribute                               // when this controller will be called then this method will automatically called.
//		public void getLoggedInUserDetails(Principal principal,Model model) {
//			
//			if(principal != null) {
//			String userEmail = principal.getName();
//			
//			UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataByEmail(userEmail);
//			
//			model.addAttribute("userDtls", userDtls);
//			}
//			
//			List<Category> allActiveCatgList = categoryServiceImpl.getAllActiveCategoriesList();
//			model.addAttribute("activeCatg", allActiveCatgList);
//			
//		}
	
	
	@GetMapping("/addCart")
	public String addToCart(@RequestParam("pid") Integer pid,@RequestParam("uid") Integer uid,HttpSession session) {
		
		Cart savedCart = cartServiceImpl.saveCart(pid, uid);
		
		if(!ObjectUtils.isEmpty(savedCart)) {
			
			session.setAttribute("successMsg", "Product is added in Cart");
		}else {
			
			session.setAttribute("errorMsg", "Product is not added in Cart");
		}
		
		return "redirect:/viewProduct/"+pid;
	}
	
	@GetMapping("openCart")
	public String viewCart(Principal principal,Model model) {
		
		String productImageUrl = environment.getProperty("product.image.url");
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		
		if(!ObjectUtils.isEmpty(userDtls)) {
			
			List<Cart> updatedCartList = cartServiceImpl.getCartsByUserId(userDtls.getId());
			
			Cart lastIndexCart = updatedCartList.get(updatedCartList.size()-1);
			
			Double totalOrderPrice = lastIndexCart.getTotalOrderPrice();
			
			model.addAttribute("cartsList", updatedCartList);
			model.addAttribute("productImageUrl", productImageUrl);
			model.addAttribute("totalOrderPrice", totalOrderPrice);
			
		}
		
		return "cartt";
	}

	
	// created method to get logged in user details
	private UserDtls getLoggedInUserDetails(Principal principal) {
		
        String userEmail = principal.getName();
		
		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataByEmail(userEmail);
		
		return userDtls;
	}
	
	
	@GetMapping("/upCartQty")
	public String updateCartQuantity(@RequestParam("symbol") String symbol,@RequestParam("cid") Integer cid) {
		
		cartServiceImpl.updateCartQuantityById(cid, symbol);
		
		return "redirect:/user/openCart";
	}
	
}
