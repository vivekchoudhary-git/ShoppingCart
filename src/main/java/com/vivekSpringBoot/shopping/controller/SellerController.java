package com.vivekSpringBoot.shopping.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.dto.SellerOrderDTO;
import com.vivekSpringBoot.shopping.dto.SellerRegisterDTO;
import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.model.Product;
import com.vivekSpringBoot.shopping.model.ProductOrder;
import com.vivekSpringBoot.shopping.model.SellerProfile;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.service.CategoryService;
import com.vivekSpringBoot.shopping.service.OrderService;
import com.vivekSpringBoot.shopping.service.ProductService;
import com.vivekSpringBoot.shopping.service.SellerProfileService;
import com.vivekSpringBoot.shopping.service.UserDtlsService;
import com.vivekSpringBoot.shopping.utility.DiscountUtility;
import com.vivekSpringBoot.shopping.utility.EmailUtility;
import com.vivekSpringBoot.shopping.utility.OrderStatus;

@Controller
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private UserDtlsService userDtlsServiceImpl;
	
	@Autowired
	private SellerProfileService sellerProfileServiceImpl;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private DiscountUtility discountUtility;
	
	@Autowired
	ProductService productServiceImpl;
	
	@Autowired
	private CategoryService categoryServiceImpl;
	
	@Autowired
	private OrderService orderServiceImpl;
	
	@Autowired
	private EmailUtility emailUtility;
	
	@GetMapping("/entry")
	public String sellerLogin(HttpServletRequest request) {
		
     // using seller=true logic for custom seller login page
		return "redirect:/signin?seller=true";
	}
	
	
	@GetMapping("/sellReg")
	public String loadRegisteration() {
		
		
		return "sellerRegg";
	}
	
	
	@PostMapping("/saveSellReg")
	public String saveSellerRegistrationData(@ModelAttribute SellerRegisterDTO sellerRegisterDTO,@RequestParam("file") MultipartFile file,@RequestParam("compfile") MultipartFile compfile,HttpSession session) throws IOException {
		
		// Set UserDtls Data
		UserDtls newSellerUser = new UserDtls();
		
		newSellerUser.setName(sellerRegisterDTO.getName());
		newSellerUser.setPhoneNo(sellerRegisterDTO.getPhoneNo());
		newSellerUser.setEmail(sellerRegisterDTO.getEmail());
		newSellerUser.setAddress(sellerRegisterDTO.getAddress());
		newSellerUser.setCity(sellerRegisterDTO.getCity());
		newSellerUser.setState(sellerRegisterDTO.getState());
		newSellerUser.setPincode(sellerRegisterDTO.getPincode());
		newSellerUser.setPassword(sellerRegisterDTO.getPassword());
		
		// set SellerProfile Data
		SellerProfile sellerProfile = new SellerProfile();
		
		sellerProfile.setCompanyName(sellerRegisterDTO.getCompanyName());
		sellerProfile.setCompanyAddress(sellerRegisterDTO.getCompanyAddress());
		sellerProfile.setGstNo(sellerRegisterDTO.getGstNo());
		sellerProfile.setPanNo(sellerRegisterDTO.getPanNo());
		sellerProfile.setBankAccount(sellerRegisterDTO.getBankAccount());
		sellerProfile.setIfscCode(sellerRegisterDTO.getIfscCode());
		
		UserDtls savednewSellerUser = null;
		SellerProfile savedSellerProfile = null;
		
    if(userDtlsServiceImpl.checkUserExists(newSellerUser.getEmail())) {
        	
        	session.setAttribute("errorMsg", "User already Exists");
        }else if (sellerProfileServiceImpl.checkSellerExists(sellerProfile.getGstNo())){
        	
        	session.setAttribute("errorMsg", "Seller already Exists");
        	
        }else {
      			
           String userImageName = (!file.isEmpty() && file != null) ? file.getOriginalFilename() : "default.jpg";
           
            String sellerCertificateName = (!compfile.isEmpty() && compfile !=null) ? compfile.getOriginalFilename() : "defaultCertifcate.jpg";
    		
    		String userImageUploadPath = environment.getProperty("userimage.upload.path");
    		
    		String sellerCertificateUploadPath = environment.getProperty("sellerCertificate.upload.path");
    		
    		newSellerUser.setProfileImage(userImageName);
    		
    		sellerProfile.setRegCertificate(sellerCertificateName);
    		
    		newSellerUser.setSellerProfile(sellerProfile);
    		
    		sellerProfile.setUserDtls(newSellerUser);
    		
    		savednewSellerUser = userDtlsServiceImpl.saveSellerUserDtlsData(newSellerUser);
    		
    		savedSellerProfile = sellerProfileServiceImpl.saveSellerProfileDetails(sellerProfile);
    		
    		// saving newSellerUser image to the specified folder
    		if(!ObjectUtils.isEmpty(savednewSellerUser)) {
    			
    			if(!file.isEmpty() && file != null) {
    			
    			Path userImagePath = Paths.get(userImageUploadPath);
    			
    			if(!Files.exists(userImagePath)) {
    				
    				Files.createDirectories(userImagePath);
    			}
    			
    			Path fullUserImagePath = userImagePath.resolve(file.getOriginalFilename());
    			
    			Files.copy(file.getInputStream(), fullUserImagePath, StandardCopyOption.REPLACE_EXISTING);
    			
    			}
 
    		}else {
    			session.setAttribute("errorMsg", "Failed Sellers Details are not saved");
    		}	
      			
    		
    		// saving company registration certificate image to the specified folder
		     if(!ObjectUtils.isEmpty(savedSellerProfile)) {
    			
    			if(!compfile.isEmpty() && compfile != null) {
    			
    			Path sellerCertificatePath = Paths.get(sellerCertificateUploadPath);
    			
    			if(!Files.exists(sellerCertificatePath)) {
    				
    				Files.createDirectories(sellerCertificatePath);
    			}
    			
    			Path fullSellerCertificatePath = sellerCertificatePath.resolve(compfile.getOriginalFilename());
    			
    			Files.copy(compfile.getInputStream(), fullSellerCertificatePath, StandardCopyOption.REPLACE_EXISTING);
    			
    			}
 
    		}else {
    			session.setAttribute("errorMsg", "Failed Sellers Details are not saved");
    		}	
    			
      		}
        	
        
		if(ObjectUtils.isEmpty(savednewSellerUser) && ObjectUtils.isEmpty(savedSellerProfile)) {
			
			session.setAttribute("errorMsg", "Failed Seller profile is not saved");
		}else {
			
			session.setAttribute("successMsg", "Seller profile is saved successfully");
		}
		
		
		return "redirect:/seller/sellReg";
	}
	
	
	@GetMapping("/index")
	public String loadIndex() {
		
		return "sellerIndexx";
	}
	
	
	@GetMapping("/loadAddProduct")
	public String addProduct(Model model) {
		
         List<Category> categoryList = categoryServiceImpl.getAllCategory();
		
        model.addAttribute("categoryList", categoryList);
		
		return "addSellerProductt";
	}
	
	
	@PostMapping("/saveProd")
	public String saveSellerProductData(@ModelAttribute Product product,@RequestParam("file") MultipartFile file,Principal principal,HttpSession session) throws IOException {
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		SellerProfile loggedInSellerProfile = sellerProfileServiceImpl.getSellersProfileByUserDtlsId(userDtls.getId());
		
		// applied logic that product will be added only if sellerprofile account is approved.
		if(loggedInSellerProfile.getAccountStatus().equals("Approved")) {
			
			String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg" ;
			
			product.setImageName(imageName);
			
			// set Discounted Price of the product
			
			if(product.getDiscount() == null) {
				
				product.setDiscount(0);
			}else if (product.getDiscount() < 0 || product.getDiscount() > 100) {
				
				session.setAttribute("errorMsg", "Invalid Discount");
				return "redirect:/seller/loadAddProduct";
			}
			
			Double discountedPrice = discountUtility.calculateDiscountedPrice(product.getDiscount(), product.getPrice());
			
			product.setDiscountedPrice(discountedPrice);

			product.setSellerProfile(loggedInSellerProfile);
			
			Product savedProduct = productServiceImpl.saveProductData(product);
			
			List<Product> sellerProductsList = loggedInSellerProfile.getProductsList();
			sellerProductsList.add(savedProduct);
			sellerProfileServiceImpl.updateSellerProfileDetails(loggedInSellerProfile);
		
			System.out.println("savedProduct : "+savedProduct);
			
			if(!ObjectUtils.isEmpty(savedProduct)) {
				
				if(!file.isEmpty() && file != null) {
					
					String productUploadPath = environment.getProperty("product.upload.path");
					
					File productUploadPathFile = new File(productUploadPath);
					
					if(!productUploadPathFile.exists()) {
						productUploadPathFile.mkdirs();                      // Create the directory if it doesn't exist
					}
					
					Path productFullPath = Paths.get(productUploadPath,file.getOriginalFilename());
					
					Files.copy(file.getInputStream(), productFullPath, StandardCopyOption.REPLACE_EXISTING);
					
					session.setAttribute("successMsg", "Successfully Product data is saved in database");
					
				}
				
				session.setAttribute("successMsg", "Successfully Product data is saved in database");
			}else {
				
				session.setAttribute("errorMsg", "Failed Product data is not saved in database");
			}
			
		}else {
			session.setAttribute("errorMsg", "Account approval is pending.Can not add Product");
		}
		
		return "redirect:/seller/loadAddProduct";
		
	}
	
	
	// created method to get logged in user details
	private UserDtls getLoggedInUserDetails(Principal principal) {
		
        String userEmail = principal.getName();
		
		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataByEmail(userEmail);

		return userDtls;
	}
	
	
	@GetMapping("/viewProd")
	public String viewProductsDetails(@RequestParam(value = "keyword",defaultValue = "") String keyword,@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,@RequestParam(value = "pageSize",defaultValue = "2") Integer pageSize,Model model,HttpSession session,Principal principal) {
		
		String productImageUrl = environment.getProperty("product.image.url");
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		SellerProfile loggedInSellerProfile = sellerProfileServiceImpl.getSellersProfileByUserDtlsId(userDtls.getId());
		
		Page<Product> productsListPaginated = null;
		
		if(keyword == null || keyword.isEmpty()){
			
		productsListPaginated = productServiceImpl.getAllProductsBySellerIdPaginated(loggedInSellerProfile.getId(), pageNo, pageSize);
		}else {
			
		productsListPaginated = productServiceImpl.searchSellerProductByKeywordPaginated(loggedInSellerProfile.getId(),keyword,pageNo,pageSize);
		}
		
		
//		Page<Product> productsListPaginated = productServiceImpl.getAllProductsBySellerIdPaginated(loggedInSellerProfile.getId(), pageNo, pageSize);
		
		if(productsListPaginated == null) {
			
			session.setAttribute("errorMsg", "Products not found");
		}else {
		List<Product> productsList = productsListPaginated.getContent();
		
		model.addAttribute("productsList", productsList);
		model.addAttribute("totalProducts", productsListPaginated.getTotalElements());
		model.addAttribute("isFirst", productsListPaginated.isFirst());
		model.addAttribute("isLast", productsListPaginated.isLast());
		model.addAttribute("totalPages", productsListPaginated.getTotalPages());
		model.addAttribute("productImageUrl", productImageUrl);
		model.addAttribute("pageNo", pageNo);
		
		}
		
		return "sellerProductss";
	}
	
	
	@GetMapping("/editProd")
	public String editProductDetails(@RequestParam("pid") Integer pid,Model model) {
		
		String productImageUrl = environment.getProperty("product.image.url");
		
		Product oldProduct = productServiceImpl.getProductById(pid);
		model.addAttribute("oldProduct", oldProduct);
		model.addAttribute("productImageUrl", productImageUrl);
		
		return "sellEditProductt";
	}
	
	
	@PostMapping("/upProd")
	public String updateProductDetails(@ModelAttribute Product product,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		 // set Discounted Price of the product
		
		if(product.getDiscount() == null) {
			
			product.setDiscount(0);
		}else if (product.getDiscount() < 0 || product.getDiscount() > 100) {
			
			session.setAttribute("errorMsg", "Invalid Discount");
			return "redirect:/seller/editProd?pid="+product.getId();
		}

      Product savedUpdatedProduct = productServiceImpl.updateProduct(product, file);

      if(savedUpdatedProduct != null) {
	
	  session.setAttribute("successMsg", "Successfully Product is Updated");
	
      }else {
	
	  session.setAttribute("errorMsg", "Failed Product is not Updated");
      }
		
		
		return "redirect:/seller/editProd?pid="+product.getId();
	}
	
	
	@GetMapping("/deleteProd/{pid}")
	public String deleteProduct(@PathVariable("pid") Integer pid,HttpSession session) {
		
        Boolean deleteStatus = productServiceImpl.deleteSellerProductById(pid);
		
		if(deleteStatus) {
			
			session.setAttribute("successMsg", "Successfully product is deleted");
		}else {
			
			session.setAttribute("errorMsg", "Failed product is not deleted");
		}
		
		return "redirect:/seller/viewProd";
	}
	
	
	@GetMapping("/orders")
	public String viewOrders(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,@RequestParam(value = "pageSize",defaultValue = "2") Integer pageSize,Model model,Principal principal) {
		
		model.addAttribute("orderDisplayLogic", true); 
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		SellerProfile loggedInSellerProfile = sellerProfileServiceImpl.getSellersProfileByUserDtlsId(userDtls.getId());
		
		Page<SellerOrderDTO> sellerOrdersPaginated = orderServiceImpl.getOrdersOfEachSellerPaginated(loggedInSellerProfile.getId(),pageNo,pageSize);
		
		List<SellerOrderDTO> sellerOrdersList = sellerOrdersPaginated.getContent();
		
		model.addAttribute("sellerOrdersList", sellerOrdersList);
		model.addAttribute("totalOrders", sellerOrdersPaginated.getTotalElements());
		model.addAttribute("isFirst", sellerOrdersPaginated.isFirst());
		model.addAttribute("isLast", sellerOrdersPaginated.isLast());
		model.addAttribute("totalPages", sellerOrdersPaginated.getTotalPages());
		model.addAttribute("pageNo", pageNo);
		
		
		return "sellerOrderss";
	}
	
	
	@GetMapping("/searchOrder")
	public String searchSellerOrder(@RequestParam("orderId") String orderId,@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,@RequestParam(value = "pageSize",defaultValue = "2") Integer pageSize,Model model,Principal principal) {
		
		model.addAttribute("orderDisplayLogic", true); 
		
		UserDtls userDtls = getLoggedInUserDetails(principal);
		SellerProfile loggedInSellerProfile = sellerProfileServiceImpl.getSellersProfileByUserDtlsId(userDtls.getId());
		
		SellerOrderDTO sellerOrder = orderServiceImpl.getOrderDataByOrderIdAndSellerId(orderId, loggedInSellerProfile.getId());
		
		if(sellerOrder != null) {
			
			model.addAttribute("sellerOrder", sellerOrder);
			model.addAttribute("orderDisplayLogic", false); 
			
		}else {
			
			Page<SellerOrderDTO> sellerOrdersPaginated = orderServiceImpl.getOrdersOfEachSellerPaginated(loggedInSellerProfile.getId(),pageNo,pageSize);
			
			List<SellerOrderDTO> sellerOrdersList = sellerOrdersPaginated.getContent();
			
			model.addAttribute("sellerOrdersList", sellerOrdersList);
			model.addAttribute("totalOrders", sellerOrdersPaginated.getTotalElements());
			model.addAttribute("isFirst", sellerOrdersPaginated.isFirst());
			model.addAttribute("isLast", sellerOrdersPaginated.isLast());
			model.addAttribute("totalPages", sellerOrdersPaginated.getTotalPages());
			model.addAttribute("pageNo", pageNo);
			
		}
		return "sellerOrderss";
	}
	
	
	@PostMapping("/changeStatusSeller")
	public String updateOrderStatusSeller(@RequestParam("oid") Integer oid,@RequestParam("statusId") Integer statusId,HttpSession session) throws UnsupportedEncodingException, MessagingException {
		
		String statusName = OrderStatus.getNameById(statusId);
		
		ProductOrder updatedOrderStatus = orderServiceImpl.updateOrderStatus(oid, statusName);
			
		if(!ObjectUtils.isEmpty(updatedOrderStatus)) {
		
			session.setAttribute("successMsg", "Order Status Changed");
		
			// sending email to user about the status of the order
		if(emailUtility.sendEmailToUserAboutOrderStatus(updatedOrderStatus, statusName)) {
			
			session.setAttribute("successMsg", "Order Status Changed,Email sent to User");
		}else {
			session.setAttribute("successMsg", "Order Status Changed,Email could not sent to User");
		}
			
		}else {	
			session.setAttribute("errorMsg", "Order Status is not Changed");
		
		}
		
		return "redirect:/seller/orders";
	}
	
	
}
