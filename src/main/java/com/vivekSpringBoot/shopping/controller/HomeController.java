package com.vivekSpringBoot.shopping.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.model.Product;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.service.CategoryService;
import com.vivekSpringBoot.shopping.service.ProductService;
import com.vivekSpringBoot.shopping.service.UserDtlsService;
import com.vivekSpringBoot.shopping.utility.EmailUtility;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryServiceImpl;
	
	@Autowired
	private ProductService productServiceImpl;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private UserDtlsService userDtlsServiceImpl;
	
	@Autowired
	private EmailUtility emailUtility;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/")
	public String index() {
		
		return "indexx";
	}
	
	@GetMapping("/signin")
	public String login() {
		
		return "loginn";
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "registerr";
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
	
	
	@GetMapping("/product")
	public String product(@RequestParam(value = "category",defaultValue = "all") String category,Model model) {
		
		model.addAttribute("showSearch", true);                          /* to show search bar in header */
		
		String productImageUrl = environment.getProperty("product.image.url");
		
		List<Category> activeCategoryList = categoryServiceImpl.getAllActiveCategoriesList();
		List<Product> activeProductList = productServiceImpl.getAllActiveProductsList(category);
		
		model.addAttribute("activeCategoryList", activeCategoryList);
		model.addAttribute("activeProductList", activeProductList);
		model.addAttribute("paramValue", category);                           // it will be used to highlight selected category option
		model.addAttribute("productImageUrl", productImageUrl);
		
		return "productt";
	}
	
	@GetMapping("/viewProduct/{id}")
	public String viewProduct(@PathVariable("id") int id,Model model) {
		
		String productImageUrl = environment.getProperty("product.image.url");
		
		Product product = productServiceImpl.getProductById(id);
		
		System.out.println("home controller product : "+product);
		
		model.addAttribute("product", product);
		model.addAttribute("productImageUrl", productImageUrl);
		
		return "viewProductt";
	}
	
	@PostMapping("/saveReg")
	public String saveRegistrationDetails(@ModelAttribute UserDtls userDtls,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		String userImageName = (!file.isEmpty() && file != null) ? file.getOriginalFilename() : "default.jpg";
		
		String userImageUploadPath = environment.getProperty("userimage.upload.path");
		
		userDtls.setProfileImage(userImageName);
		
		UserDtls savedUserDtls = userDtlsServiceImpl.saveUserDtlsData(userDtls);
		
		if(!ObjectUtils.isEmpty(savedUserDtls)) {
			
			if(!file.isEmpty() && file != null) {
			
			Path userImagePath = Paths.get(userImageUploadPath);
			
			if(!Files.exists(userImagePath)) {
				
				Files.createDirectories(userImagePath);
			}
			
			Path fullUserImagePath = userImagePath.resolve(file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), fullUserImagePath, StandardCopyOption.REPLACE_EXISTING);
			
			}
			session.setAttribute("successMsg", "Successfully User Details are saved");
		}else {
			session.setAttribute("errorMsg", "Failed User Details are not saved");
		}
		
		return "redirect:/register";
	}
	
	
	@GetMapping("/forgotPass")
	public String forgotPassword() {
		
		return "forgotPassw";
	}
	
	@GetMapping("/forgotPassEmail")
	public String forgotPasswordEmail(@RequestParam("email") String email,HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataByEmail(email);
		
		if(ObjectUtils.isEmpty(userDtls)) {
			
			session.setAttribute("errorMsg", "This email does not exist");
			
			return "redirect:/forgotPass";
		}else {
			
			// send email to user email address to reset the password
			String passResetToken = UUID.randomUUID().toString();
			
			userDtlsServiceImpl.updatePassResetToken(email, passResetToken);
			
			String modifiedURL = EmailUtility.generateURL(request);
			// modifiedURL : http://localhost:8080/
			
			String passResetEmailURL = modifiedURL+"resetPass?resetToken="+passResetToken;
			
			if(emailUtility.sendEmailToResetPassword(passResetEmailURL, email)) {
				
				session.setAttribute("successMsg", "Check your email to reset your password");
			}else {
				session.setAttribute("errorMsg", "Something went wrong.Please enter your Email again");
			}
			
			return "redirect:/forgotPass";
		}
		
	}
	
	// Below mapping is done to open Email reset Password link
	@GetMapping("/resetPass")
	public String showResetPassword(@RequestParam("resetToken") String resetToken,Model model) {
		
		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsByToken(resetToken);
		
		if(userDtls == null) {
			
			model.addAttribute("msg", "Your Link is invalid or Expired");
			
			return "messagee";
		}else {
			
			model.addAttribute("resetToken", resetToken);
			
			return "resetPasw";
		}
		
		
	}
	
	// note : here request mapping name is same as mentioned above (study about this)
	@PostMapping("/resetPass")
	public String resetPassword(@RequestParam("resetToken") String resetToken,@RequestParam("password") String password,Model model) {
		
		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsByToken(resetToken);
		
		if(ObjectUtils.isEmpty(userDtls)) {
			
			model.addAttribute("msg", "Your Link is invalid or Expired");
			
			return "messagee";
		}else {
			
			userDtls.setPassResetToken(null);
			
			String encodedPass = bCryptPasswordEncoder.encode(password);
			
			userDtls.setPassword(encodedPass);
			
			UserDtls updatedUserDtls = userDtlsServiceImpl.updateUserDtlsData(userDtls);
			
			if(updatedUserDtls != null) {
				
			    model.addAttribute("msg", "Your Password has been changed Successfully");
			} else {
				model.addAttribute("msg", "Your Link is invalid or Expired");
			}
			
		}
		
		return "messagee";
	}
	
	
	// search product by keyword
	@GetMapping("/searchProduct")
	public String searchAnyProduct(@RequestParam("keyword") String keyword,@RequestParam(value = "category",defaultValue = "all") String category,Model model ,HttpSession session) {
		
		model.addAttribute("showSearch", true);                          /* to show search bar in header */
		
		String productImageUrl = environment.getProperty("product.image.url");
		
		if(keyword.trim() == null || keyword.trim().isEmpty()) {
			
			List<Product> allActiveProductsList = productServiceImpl.getAllActiveProductsList(category);
			model.addAttribute("activeProductList", allActiveProductsList);
		}else {
			
			List<Product> searchedProductList = productServiceImpl.searchProductByKeyword(keyword);
			model.addAttribute("activeProductList", searchedProductList);
		}
		
		   List<Category> activeCategoryList = categoryServiceImpl.getAllActiveCategoriesList();
		
			model.addAttribute("productImageUrl", productImageUrl);
			model.addAttribute("activeCategoryList", activeCategoryList);
			model.addAttribute("paramValue", category);                           // it will be used to highlight selected category option
		
		
		return "productt";
	}
	
	
}
