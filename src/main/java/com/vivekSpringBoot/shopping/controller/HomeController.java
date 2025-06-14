package com.vivekSpringBoot.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.model.Product;
import com.vivekSpringBoot.shopping.service.CategoryService;
import com.vivekSpringBoot.shopping.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryServiceImpl;
	
	@Autowired
	private ProductService productServiceImpl;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/")
	public String index() {
		
		return "indexx";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "loginn";
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "registerr";
	}
	
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
	
}
