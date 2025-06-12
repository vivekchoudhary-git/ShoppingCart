package com.vivekSpringBoot.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String product(Model model) {
		
		model.addAttribute("showSearch", true);                          /* to show search bar in header */
		
		List<Category> activeCategoryList = categoryServiceImpl.getAllActiveCategoriesList();
		List<Product> activeProductList = productServiceImpl.getAllActiveProductsList();
		
		model.addAttribute("activeCategoryList", activeCategoryList);
		model.addAttribute("activeProductList", activeProductList);
		
		return "productt";
	}
	
	@GetMapping("/viewProduct")
	public String viewProduct() {
		
		return "viewProductt";
	}
	
}
