package com.vivekSpringBoot.shopping.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.dto.SellerRegisterDTO;
import com.vivekSpringBoot.shopping.model.SellerProfile;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.service.SellerProfileService;
import com.vivekSpringBoot.shopping.service.UserDtlsService;

@Controller
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private UserDtlsService userDtlsServiceImpl;
	
	@Autowired
	private SellerProfileService sellerProfileServiceImpl;
	
	@Autowired
	private Environment environment;
	
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
	public String addProduct() {
		
		
		return "addSellerProductt";
	}
	
	
}
