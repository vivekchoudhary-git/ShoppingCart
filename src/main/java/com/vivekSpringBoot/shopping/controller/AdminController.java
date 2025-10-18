package com.vivekSpringBoot.shopping.controller;

import java.io.File;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.lowagie.text.DocumentException;
import com.vivekSpringBoot.shopping.dto.AdminRegDTO;
import com.vivekSpringBoot.shopping.dto.SellersDTO;
import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.model.Product;
import com.vivekSpringBoot.shopping.model.ProductOrder;
import com.vivekSpringBoot.shopping.model.SellerProfile;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.service.CategoryService;
import com.vivekSpringBoot.shopping.service.OrderService;
import com.vivekSpringBoot.shopping.service.ProductService;
import com.vivekSpringBoot.shopping.service.SellerProfileService;
import com.vivekSpringBoot.shopping.serviceimpl.UserDtlsServiceImpl;
import com.vivekSpringBoot.shopping.utility.DiscountUtility;
import com.vivekSpringBoot.shopping.utility.EmailUtility;
import com.vivekSpringBoot.shopping.utility.OrderStatus;
import com.vivekSpringBoot.shopping.utility.OrdersPDF;
import com.vivekSpringBoot.shopping.utility.SellerAccountStatus;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CategoryService categoryServiceImpl;
	
	@Autowired
	private ProductService productServiceImpl;
	
	@Autowired
	private DiscountUtility discountUtility;
	
	@Autowired
	private UserDtlsServiceImpl userDtlsServiceImpl;
	
	@Autowired
	private OrderService orderServiceImpl;
	
	@Autowired
	private EmailUtility emailUtility;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private SellerProfileService sellerProfileServiceImpl;
	
	@Autowired
	private OrdersPDF ordersPDF;
	
	@GetMapping("/")
	public String index() {
		
		return "adminIndex";
	}
	
	@GetMapping("/loadAddProduct")
	public String addProductPage(Model model) {
		
		List<Category> categoryList = categoryServiceImpl.getAllCategory();
		
        model.addAttribute("categoryList", categoryList);
		
		return "addProductt";
	}
	
	@GetMapping("/loadCategory")
	public String categoryPage(Model model) {
		
		String categoryImageUrl = environment.getProperty("category.image.url");
		
		List<Category> categoryList = categoryServiceImpl.getAllCategory();
		
		if(CollectionUtils.isEmpty(categoryList)) {
			
			System.out.println("categoryList is Empty");
		}
		
		model.addAttribute("catList", categoryList);
		model.addAttribute("categoryImageUrl", categoryImageUrl);
		
		return "categoryy";
	}
	
	
	// handled this in GlobalModelAttribute class
   // add this method to all controller so that whenever any controller gets called we can fetch logged in user data
//	@ModelAttribute                               // when this controller will be called then this method will automatically called.
//	public void getLoggedInUserDetails(Principal principal,Model model) {
//		
//		if(principal != null) {
//		String userEmail = principal.getName();
//		
//		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataByEmail(userEmail);
//		
//		model.addAttribute("userDtls", userDtls);
//		}
//		
//		List<Category> allActiveCatgList = categoryServiceImpl.getAllActiveCategoriesList();
//		model.addAttribute("activeCatg", allActiveCatgList);
//			
//	}
	
	
	@PostMapping("/saveCategory")
	public String insertCategoryData(@ModelAttribute Category category,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg";
		
		category.setImageName(imageName);
		
		Boolean isNameExists = categoryServiceImpl.checkByName(category.getName());
		
		if(isNameExists) {
			session.setAttribute("errorMsg", "Category name already exists");
		}else {
		
		Category savedCategory = categoryServiceImpl.saveCategory(category);
		
		if(ObjectUtils.isEmpty(savedCategory)) {
			session.setAttribute("errorMsg", "category is empty : Internal Server Error");
		}else {
			
			// here we are saving images in the project folder
			
			// here we are fetching path till images folder.
//			File savedFile = new ClassPathResource("webapp/resources/images").getFile();          // its not working also not recommended by chatGPT
//			System.out.println("savedFile : "+savedFile);
//			
//			// here we are defining path where images will be saved
//			Path path = Paths.get(savedFile.getAbsolutePath()+File.separator+"category_img"+File.separator+file.getOriginalFilename());    // its not working also not recommended by chatGPT
//			System.out.println("savedFile : "+savedFile);
//			System.out.println("path : "+path);
//			
//			
//			// StandardCopyOption.REPLACE_EXISTING = if we would save the same image again then it would replace the existing image with new image.
//			Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
			
		// ------	suggested by chatGPT : using external directory to save uploaded images (highly recommended)---------
			
			// when image is uploaded then this code will execute
			if(file != null && !file.isEmpty()) {
//			String uploadDir = "D:/JAVA/externaldirectory/shoppingcart/categoryimage";     // this is hard coded
			String uploadDir = environment.getProperty("category.upload.path");            // this is dynamic
			
            File uploadPath = new File(uploadDir);                    // check it is required or not

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();           // Create the directory if it doesn't exist
            }

            Path path = Paths.get(uploadDir, file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			
			session.setAttribute("successMsg", "Successfully saved Category Details");
			}else {
				// when image is not uploaded then category will be saved without the image successfully
				session.setAttribute("successMsg", "Successfully saved Category Details");
			}
		}
		
		}
		
		return "redirect:/admin/loadCategory";
	}
	
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategoryData(@PathVariable("id") int id,HttpSession session) {
		
		Boolean deleteStatus = categoryServiceImpl.deleteCategoryById(id);
		
		if(deleteStatus) {
			session.setAttribute("successMsg", "Successfully category is deleted");
		}else {
			session.setAttribute("errorMsg", "Failed category is not deleted");
		}
		
		return "redirect:/admin/loadCategory";
	}
	
	@GetMapping("/editCategory/{id}")
	public String editCategoryData(@PathVariable("id") int id,Model model) {
		
		String categoryImageUrl = environment.getProperty("category.image.url");
		
		Category oldCategory = categoryServiceImpl.getCategoryDetailsById(id);
		
		model.addAttribute("oldCategory", oldCategory);
		model.addAttribute("categoryImageUrl", categoryImageUrl);
		
		return "editCategoryy";
	}
	
	@PostMapping("/updateCategory")
	public String updateCategoryData(@ModelAttribute Category category,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		Category oldCategory = categoryServiceImpl.getCategoryDetailsById(category.getId());
		
		String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : oldCategory.getImageName();
		
		if(!ObjectUtils.isEmpty(oldCategory)) {
			
			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImageName(imageName);
		
		}
		
		Category savedUpdatedCategory = categoryServiceImpl.updateCategoryDetails(oldCategory);
		
		if(!ObjectUtils.isEmpty(savedUpdatedCategory)) {
			
			// here we are uploading the updated image of Category 
			// when image is uploaded then this code will execute
			if(file != null && !file.isEmpty()) {
			String uploadDir = environment.getProperty("category.upload.path");
			
			File uploadPathFile = new File(uploadDir);                        // check it is required or not
			
			Path path = Paths.get(uploadDir,file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
			
			session.setAttribute("successMsg", "Successfully Category is Updated");
			}else {
				// when image is not uploaded then category will be saved without the image successfully
				session.setAttribute("successMsg", "Successfully Category is Updated");
			}
			
		}else {
			
			session.setAttribute("errorMsg", "Failed Category is not Updated");
		}
		
		return "redirect:/admin/editCategory/"+category.getId();
	}
	
	@PostMapping("/saveProduct")
	public String insertProductData(@ModelAttribute Product product,@RequestParam("categoryId") Integer categoryId,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg" ;
		
		Category category = categoryServiceImpl.getCategoryDetailsById(categoryId);
		
		// setting category object in product
		product.setCategory(category);
		product.setImageName(imageName);
		
		// set Discounted Price of the product
		
		if(product.getDiscount() == null) {
			
			product.setDiscount(0);
		}else if (product.getDiscount() < 0 || product.getDiscount() > 100) {
			
			session.setAttribute("errorMsg", "Invalid Discount");
			return "redirect:/admin/loadAddProduct";
		}
		
		Double discountedPrice = discountUtility.calculateDiscountedPrice(product.getDiscount(), product.getPrice());
		
		product.setDiscountedPrice(discountedPrice);

		Product savedProduct = productServiceImpl.saveProductData(product);
		
		// setting product object in category list.
		category.getProductsList().add(savedProduct);
		
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
				
				session.setAttribute("successMsg", "Successfully data is saved in database");
				
			}
			
			session.setAttribute("successMsg", "Successfully data is saved in database");
		}else {
			
			session.setAttribute("errorMsg", "Failed data is not saved in database");
		}
		
		return "redirect:/admin/loadAddProduct";
	}
	
	
	// applying logic if admin does not search anything then all products will be shown otherwise only searched products will be shown
	@GetMapping("/viewProducts")
	public String viewAllProducts(@RequestParam(value = "keyword",defaultValue = "") String keyword,@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,@RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,Model model) {
		
		String productImageUrl = environment.getProperty("product.image.url");
		
//		List<Product> productsList = null;
		
		Page<Product> productsListPaginated = null;
		
		if(keyword == null || keyword.isEmpty()) {
		
//		productsList = productServiceImpl.getAllProducts();
		
		productsListPaginated = productServiceImpl.getAllProductsPaginated(pageNo, pageSize);
		
		}else {
			
//			productsList = productServiceImpl.searchProductByKeyword(keyword);
			
			productsListPaginated = productServiceImpl.searchProductByKeywordPaginated(keyword, pageNo, pageSize);
		}
		
		List<Product> paginatedList = productsListPaginated.getContent();
		
		if(!CollectionUtils.isEmpty(paginatedList)) {
			
			model.addAttribute("productsList", paginatedList);
			model.addAttribute("totalProducts",productsListPaginated.getTotalElements());
			model.addAttribute("isFirst", productsListPaginated.isFirst());
			model.addAttribute("isLast", productsListPaginated.isLast());
			model.addAttribute("totalPages",productsListPaginated.getTotalPages());
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("productImageUrl", productImageUrl);
		}
		
		return "productss";
	}
	
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProductData(@PathVariable("id") int id,HttpSession session) {
		
		Boolean deleteStatus = productServiceImpl.deleteProductById(id);
		
		if(deleteStatus) {
			
			session.setAttribute("successMsg", "Successfully product is deleted");
		}else {
			
			session.setAttribute("errorMsg", "Failed product is not deleted");
		}
		
		return "redirect:/admin/viewProducts";
	}
	
	@GetMapping("/editProduct/{id}")
	public String editProductData(@PathVariable("id") int id,Model model) {
		
		List<Category> categoryList = categoryServiceImpl.getAllCategory();
		
		String productImageUrl = environment.getProperty("product.image.url");
		
		Product oldProduct = productServiceImpl.getProductById(id);
		
		if(oldProduct != null) {
			model.addAttribute("oldProduct", oldProduct);
		}
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("productImageUrl", productImageUrl);
		
		return "editProductt";
	}
	
	@PostMapping("/updateProduct")
	public String updateProductData(@ModelAttribute Product product,@RequestParam("categoryId") int categoryId,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		        // set Discounted Price of the product
		
				if(product.getDiscount() == null) {
					
					product.setDiscount(0);
				}else if (product.getDiscount() < 0 || product.getDiscount() > 100) {
					
					session.setAttribute("errorMsg", "Invalid Discount");
					return "redirect:/admin/editProduct/"+product.getId();
				}
		
		Product savedUpdatedProduct = productServiceImpl.updateProduct(product, file,categoryId);
		
		if(savedUpdatedProduct != null) {
			
			session.setAttribute("successMsg", "Successfully Product is Updated");
			
		}else {
			
			session.setAttribute("errorMsg", "Failed Product is not Updated");
		}
		
		return "redirect:/admin/editProduct/"+product.getId();
	}
	
	
	@GetMapping("/viewUsers")
	public String viewAllUsers(@RequestParam("userType") Integer userType,Model model) {
		
		String userImageUrl = environment.getProperty("userimage.url");
		
		List<UserDtls> userDtlsList = null;
		
		if(userType == 1) {
			
		 userDtlsList = userDtlsServiceImpl.getAllUsersByRole("ROLE_USER");
		}else if(userType == 2) {
			
			userDtlsList = userDtlsServiceImpl.getAllUsersByRole("ROLE_ADMIN");
		}
		
		if(!CollectionUtils.isEmpty(userDtlsList)) {
			
			model.addAttribute("usersListRoleWise", userDtlsList);
		}
		
		    model.addAttribute("userImageUrl",userImageUrl);
		    model.addAttribute("userType", userType);
		
		return "viewUsers";
	}
	
	// here updating status i.e isEnabled Property of UserDtls
	@GetMapping("/updateStatus")
	public String updateUserStatus(@RequestParam("id") int id,@RequestParam("status") Boolean status,@RequestParam("userType") Integer userType,HttpSession session) {
		
		UserDtls updatedUserDtls = userDtlsServiceImpl.updateUserDtlsStatus(id, status);
		
		System.out.println("updatedUserDtls checking : "+updatedUserDtls);
		
		if(!ObjectUtils.isEmpty(updatedUserDtls)) {
			
			session.setAttribute("successMsg", "Status is Updated");
		}else {
			session.setAttribute("errorMsg", "Failed Status is not Updated");
		}
		
		return "redirect:/admin/viewUsers?userType="+userType;
	}
	
	
	@GetMapping("/allOrders")
	public String viewAllOrders(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,@RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,Model model) {
		
		model.addAttribute("orderDisplayLogic", true);    
		
//		List<ProductOrder> allOrdersList = orderServiceImpl.getAllOrders();
		
		Page<ProductOrder> allOrdersListPaginated = orderServiceImpl.getAllOrdersPaginated(pageNo, pageSize);
		
		List<ProductOrder> ordersListPaginated = allOrdersListPaginated.getContent();
		
		model.addAttribute("allOrdersList", ordersListPaginated);
		model.addAttribute("totalOrders", allOrdersListPaginated.getTotalElements());
		model.addAttribute("isFirst", allOrdersListPaginated.isFirst());
		model.addAttribute("isLast", allOrdersListPaginated.isLast());
		model.addAttribute("totalPages", allOrdersListPaginated.getTotalPages());
		model.addAttribute("pageNo", pageNo);
		
		return "orderss";
	}
	
	
	@PostMapping("/changeStatusAdmin")
	public String updateOrderStatusAdmin(@RequestParam("oid") Integer oid,@RequestParam("statusId") Integer statusId,HttpSession session) throws UnsupportedEncodingException, MessagingException {
		
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
		
		return "redirect:/admin/allOrders";
	}
	
	@GetMapping("/searchOrder")
	public String searchAnyOrder(@RequestParam("orderId") String orderId,@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,@RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,Model model) {
		
		model.addAttribute("orderDisplayLogic", true);                  // logic to display order list or order object based on condition
		
		ProductOrder productOrder = orderServiceImpl.getOrderDataByOrderId(orderId);
		
		if(!ObjectUtils.isEmpty(productOrder)) {
			
			model.addAttribute("searchOrder", productOrder);
			model.addAttribute("orderDisplayLogic", false); 
		}else {
			
			// showing all orders when orderId is empty in searh bar
			
//			List<ProductOrder> allOrdersList = orderServiceImpl.getAllOrders();
//			model.addAttribute("allOrdersList", allOrdersList);
			
			Page<ProductOrder> allOrdersListPaginated = orderServiceImpl.getAllOrdersPaginated(pageNo, pageSize);
			
			List<ProductOrder> ordersListPaginated = allOrdersListPaginated.getContent();
			
			model.addAttribute("allOrdersList", ordersListPaginated);
			model.addAttribute("totalOrders", allOrdersListPaginated.getTotalElements());
			model.addAttribute("isFirst", allOrdersListPaginated.isFirst());
			model.addAttribute("isLast", allOrdersListPaginated.isLast());
			model.addAttribute("totalPages", allOrdersListPaginated.getTotalPages());
			model.addAttribute("pageNo", pageNo);
			
		}
		
		return "orderss";
	}
	
	
	@GetMapping("/register")
	public String loadAdminRegistration() {
		
		
		return "registerAdminn";
	}
	
	
	@PostMapping("/saveAdminReg")
	public String saveAdminRegistrationDetails(@ModelAttribute AdminRegDTO adminRegDTO,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
        String userImageName = (!file.isEmpty() && file != null) ? file.getOriginalFilename() : "default.jpg";
		
        if(userDtlsServiceImpl.checkUserExists(adminRegDTO.getEmail())) {
        	
        	session.setAttribute("errorMsg", "Admin already exists");
        }else {
        
    		String userImageUploadPath = environment.getProperty("userimage.upload.path");
    		
    		adminRegDTO.setProfileImage(userImageName);
    		
    		UserDtls newAdmin = new UserDtls();
    		
    		newAdmin.setName(adminRegDTO.getName());
    		newAdmin.setPhoneNo(adminRegDTO.getPhoneNo());
    		newAdmin.setEmail(adminRegDTO.getEmail());
    		newAdmin.setAddress(adminRegDTO.getAddress());
    		newAdmin.setCity(adminRegDTO.getCity());
    		newAdmin.setState(adminRegDTO.getState());
    		newAdmin.setPincode(adminRegDTO.getPincode());
    		newAdmin.setPassword(adminRegDTO.getPassword());
    		newAdmin.setProfileImage(adminRegDTO.getProfileImage());
    		
    		UserDtls savedUserDtls = userDtlsServiceImpl.saveAdminUserDtlsData(newAdmin);
    		
    		if(!ObjectUtils.isEmpty(savedUserDtls)) {
    			
    			if(!file.isEmpty() && file != null) {
    			
    			Path userImagePath = Paths.get(userImageUploadPath);
    			
    			if(!Files.exists(userImagePath)) {
    				
    				Files.createDirectories(userImagePath);
    			}
    			
    			Path fullUserImagePath = userImagePath.resolve(file.getOriginalFilename());
    			
    			Files.copy(file.getInputStream(), fullUserImagePath, StandardCopyOption.REPLACE_EXISTING);
    			
    			}
    			session.setAttribute("successMsg", "Successfully Admin Details are saved");
    		}else {
    			session.setAttribute("errorMsg", "Failed Admin Details are not saved");
    		}
    		      
        }

		return "redirect:/admin/register";
		
	}
	
	
	@GetMapping("/viewAdmProfile")
	public String viewAdminProfile(Model model) {
		
		String userImageUrl = environment.getProperty("userimage.url");
		model.addAttribute("userImageUrl", userImageUrl);
		
		return "adminProfile";
	}
	
	
	@PostMapping("/updateAdmProfile")
	public String updateAdminProfile(@ModelAttribute UserDtls userDtls,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
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
			
			
			session.setAttribute("successMsg", "Admin Profile is Updated");
		}else {
			
			session.setAttribute("errorMsg", "Admin Profile is not Updated");
		}
		
		return "redirect:/admin/viewAdmProfile";
		
	}
	
	
	// created method to get logged in user details
	private UserDtls getLoggedInUserDetails(Principal principal) {
		
        String userEmail = principal.getName();
		
		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataByEmail(userEmail);
		
		return userDtls;
	}
	
	
	@PostMapping("/changeAdmPassword")
	public String changeAdminPassword(@RequestParam("currentPassword") String currentPassword,@RequestParam("newPassword") String newPassword,Principal principal,HttpSession session) {
		
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
		
		return "redirect:/admin/viewAdmProfile";
	}
	
	
	@GetMapping("/viewSellers")
	public String viewAllSellers(HttpSession session,Model model) {
		
		String userImageUrl = environment.getProperty("userimage.url");
		
		List<SellersDTO> allSellersList = sellerProfileServiceImpl.getAllSellersDetails();
		
		if(CollectionUtils.isEmpty(allSellersList)) {
			
			session.setAttribute("errorMsg", "Could not get Sellers List , Internal Server Error");
		}else {
			
			model.addAttribute("allSellersList", allSellersList);
			model.addAttribute("userImageUrl", userImageUrl);
		}
		
		return "sellerss";
	}
	
	
	@PostMapping("/upAccStatus")
	public String changeSellerAccountStatus(@RequestParam("sid") Integer sellerId,@RequestParam("accStatusId") Integer accStatusId,HttpSession session) {
		
		String accStatus = SellerAccountStatus.getDescriptionById(accStatusId);
		
		SellerProfile updatedSellAccStatus = sellerProfileServiceImpl.updateSellerAccountStatus(sellerId, accStatus);
		
		if(ObjectUtils.isEmpty(updatedSellAccStatus)) {
			
			session.setAttribute("errorMsg", "Could not update Account Status");
		}else {
			session.setAttribute("successMsg", "Account Status is updated");
		}
		
		
		return "redirect:/admin/viewSellers";
		
	}
	
	
	@GetMapping("/stats")
	public String viewAllStatistics(Model model) {
		
		long ordersCount = orderServiceImpl.countAllOrders();
		long deliveredOrdersCount = orderServiceImpl.countAllDeliveredOrders();
		long pendingOrdersCount = orderServiceImpl.countAllPendingOrders();
		long cancelledOrdersCount = orderServiceImpl.countAllCancelledOrders();
		Double revenueDeliveredOrders = orderServiceImpl.totalRevenueGeneratedByDeliveredOrders();
		
		model.addAttribute("ordersCount", ordersCount);
		model.addAttribute("deliveredOrdersCount", deliveredOrdersCount);
		model.addAttribute("pendingOrdersCount", pendingOrdersCount);
		model.addAttribute("cancelledOrdersCount", cancelledOrdersCount);
		model.addAttribute("revenueDeliveredOrders", revenueDeliveredOrders);
		
		return "statss";
	}
	
	
	@GetMapping("/downPdf")
	public String downloadOrdersPDF(HttpServletResponse response) throws DocumentException, IOException {
		
		ordersPDF.export(response);
		
		return "redirect:/admin/allOrders";
	}
	
	
	
}




// --------------- Notes -------------

// we can also use "RedirectAttributes" instead of "Session" to display alert messages on JSP.


// userType == 1 for ROLE_USER
// userType == 2 for ROLE_ADMIN