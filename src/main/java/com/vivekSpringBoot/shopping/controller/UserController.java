package com.vivekSpringBoot.shopping.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.model.Cart;
import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.model.OrderRequest;
import com.vivekSpringBoot.shopping.model.ProductOrder;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.service.CartService;
import com.vivekSpringBoot.shopping.service.CategoryService;
import com.vivekSpringBoot.shopping.service.OrderService;
import com.vivekSpringBoot.shopping.service.UserDtlsService;
import com.vivekSpringBoot.shopping.serviceimpl.UserDtlsServiceImpl;
import com.vivekSpringBoot.shopping.utility.EmailUtility;
import com.vivekSpringBoot.shopping.utility.OrderStatus;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDtlsService userDtlsServiceImpl;
	
	@Autowired
	private CategoryService categoryServiceImpl;
	
	@Autowired
	private CartService cartServiceImpl;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private OrderService orderServiceImpl;
	
	@Autowired
	private EmailUtility emailUtility;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
			
			Cart lastIndexCart = null;
			Double totalOrderPrice = null;
			
			if(updatedCartList != null && !CollectionUtils.isEmpty(updatedCartList)) {
				
				model.addAttribute("cartsList", updatedCartList);
				lastIndexCart = updatedCartList.get(updatedCartList.size()-1);
				totalOrderPrice = lastIndexCart.getTotalOrderPrice();
			}else {
				System.out.println("updatedCartList is null");
			}
			
			
			
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
	
	
	@GetMapping("/order")
	public String loadOrderPage(Principal principal,Model model) {
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		
		List<Cart> updatedCartsList = cartServiceImpl.getCartsByUserId(userDtls.getId());
		
		// this price does not include GST and Delivery charges
		Double totalOrderPrice = updatedCartsList.get(updatedCartsList.size()-1).getTotalOrderPrice();
		
		// this price includes Tax (Rs 100) and Delivery(Rs 250) charges
		Double finalOrderTotalPrice = totalOrderPrice+250+100;
		
		model.addAttribute("totalOrderPrice", totalOrderPrice);
		model.addAttribute("finalOrderTotalPrice", finalOrderTotalPrice);
		
		return "orderr";
	}
	
	
	@PostMapping("/saveOrder")
	public String saveOrderDetails(@ModelAttribute OrderRequest orderRequest,Principal principal) throws UnsupportedEncodingException, MessagingException {
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		
		orderServiceImpl.saveOrderData(userDtls.getId(), orderRequest);
		
		return "redirect:/user/success";                                // here we are redirecting so that on refresh the order does not get saved again and again
	}
	
	@GetMapping("/success")
	public String getSuccessPage(Principal principal) {
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		
		cartServiceImpl.deleteCartByUserId(userDtls.getId());
		
		return "successs";
	}
	
	// to view all orders of the logged in user
	@GetMapping("/userOrders")
	public String viewUserOrders(Principal principal,Model model) {
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		
		List<ProductOrder> ordersList = orderServiceImpl.getAllOrdersByUserDtlsId(userDtls.getId());
		
		model.addAttribute("ordersList", ordersList);
		
		return "myOrderss";
	}
	
	
	@GetMapping("/changeStatus")
	public String updateOrderStatus(@RequestParam("oid") Integer oid,@RequestParam("statusId") Integer statusId,HttpSession session) throws UnsupportedEncodingException, MessagingException {
		
		String statusName = OrderStatus.getNameById(statusId);
		
		ProductOrder updatedOrderStatus = orderServiceImpl.updateOrderStatus(oid, statusName);
			
		if(!ObjectUtils.isEmpty(updatedOrderStatus)) {
			
			session.setAttribute("successMsg", "Order Status Changed");
		
			if(emailUtility.sendEmailToUserAboutOrderStatus(updatedOrderStatus, statusName)) {
				
				session.setAttribute("successMsg", "Order Status Changed,Please check email");
			}else {
				
				session.setAttribute("successMsg", "Order Status Changed,Email could not sent");
			}
			
		}else {
			session.setAttribute("errorMsg", "Order Status is not Changed");
		
		}
		
		return "redirect:/user/userOrders";
	}
	
	
	@GetMapping("/viewProfile")
	public String loadProfilePage(Model model) {
		
		String userImageUrl = environment.getProperty("userimage.url");
		model.addAttribute("userImageUrl", userImageUrl);
		
		return "profilee";
	}
	
	
	@PostMapping("/updateProfile")
	public String updateProfileData(@ModelAttribute UserDtls userDtls,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		String userImageUploadPath = environment.getProperty("userimage.upload.path");
		
		String imageName = file.getOriginalFilename();
		
		if(imageName != null && !imageName.isEmpty()) {
			
		userDtls.setProfileImage(imageName);
		}
		
		UserDtls updatedUserDtlsData = userDtlsServiceImpl.updateUserDtlsData(userDtls);
		
		if(!ObjectUtils.isEmpty(updatedUserDtlsData)) {
			
			if(!file.isEmpty() && file != null) {
				
				Path userImagePath = Paths.get(userImageUploadPath);
				
				if(!Files.exists(userImagePath)) {
				
					Files.createDirectories(userImagePath);
				}
				
				Path fullUserImagePath = userImagePath.resolve(file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), fullUserImagePath, StandardCopyOption.REPLACE_EXISTING);
			}
			
			
			session.setAttribute("successMsg", "Profile is Updated");
		}else {
			
			session.setAttribute("errorMsg", "Profile is not Updated");
		}
		
		return "redirect:/user/viewProfile";
	}
	
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("currentPassword") String currentPassword,@RequestParam("newPassword") String newPassword,Principal principal,HttpSession session) {
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		
		boolean passResult = bCryptPasswordEncoder.matches(currentPassword, userDtls.getPassword());
		
		if(passResult) {
			
			String encodedNewPass = bCryptPasswordEncoder.encode(newPassword);
			
			userDtls.setPassword(encodedNewPass);
			UserDtls updatedUserDtls = userDtlsServiceImpl.updateUserDtlsData(userDtls);
			
			if(!ObjectUtils.isEmpty(updatedUserDtls)) {
				
				session.setAttribute("successMsg", "Password is Updated");
			}else {
				session.setAttribute("errorMsg", "Password is not Updated");
			}
			
		}else {
			session.setAttribute("errorMsg", "Invalid Password");
		}
		
		return "redirect:/user/viewProfile";
	}
	
	
}
