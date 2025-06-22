package com.vivekSpringBoot.shopping.global;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.service.CategoryService;
import com.vivekSpringBoot.shopping.service.ProductService;
import com.vivekSpringBoot.shopping.service.UserDtlsService;

@ControllerAdvice
public class GlobalModelAttribute {

	@Autowired
	private CategoryService categoryServiceImpl;
	
	@Autowired
	private UserDtlsService userDtlsServiceImpl;
	
	
	@ModelAttribute                               
	public void getLoggedInUserDetails(Principal principal,Model model) {
		
		if(principal != null) {
		String userEmail = principal.getName();
		
		UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataByEmail(userEmail);
		
		System.out.println("logged in as : "+userDtls.getName());
		
		model.addAttribute("userDtls", userDtls);
		}else {
			
			System.out.println("no user has logged in");
		}
		
		List<Category> allActiveCatgList = categoryServiceImpl.getAllActiveCategoriesList();
//		System.out.println("allActiveCatgList is loaded at GlobalModelAttribute");
		model.addAttribute("activeCatg", allActiveCatgList);
			
	}
	
}


//                                   NOTES :

// This @ControllerAdvice class runs for all controllers in your application.
// The @ModelAttribute method will add userDtls and activeCatg to the model before any controller method executes.
// Now you can use ${userDtls} and ${activeCatg} directly in any JSP page rendered by any controller, without writing this logic in every controller.

