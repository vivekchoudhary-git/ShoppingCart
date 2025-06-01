package com.vivekSpringBoot.shopping.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
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

import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.service.CategoryService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CategoryService categoryServiceImpl;
	
	@GetMapping("/")
	public String index() {
		
		return "adminIndex";
	}
	
	@GetMapping("/loadAddProduct")
	public String addProductPage() {
		
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
			
            File uploadPath = new File(uploadDir);

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
		
		String imageName = file != null ? file.getOriginalFilename() : oldCategory.getImageName();
		
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
			
			File uploadPathFile = new File(uploadDir);
			
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
	
	
}




// --------------- Notes -------------

// we can also use "RedirectAttributes" instead of "Session" to display alert messages on JSP.