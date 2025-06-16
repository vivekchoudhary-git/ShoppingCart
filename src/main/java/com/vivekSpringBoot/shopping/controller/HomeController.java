package com.vivekSpringBoot.shopping.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	
}
