package com.vivekSpringBoot.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

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
		
		return "productt";
	}
	
	@GetMapping("/viewProduct")
	public String viewProduct() {
		
		return "viewProductt";
	}
	
}
