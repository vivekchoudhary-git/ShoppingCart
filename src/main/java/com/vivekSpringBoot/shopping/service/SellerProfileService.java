package com.vivekSpringBoot.shopping.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.dto.SellersDTO;
import com.vivekSpringBoot.shopping.model.SellerProfile;

public interface SellerProfileService {

	public SellerProfile saveSellerProfileDetails(SellerProfile sellerProfile);
	
	public Boolean checkSellerExists(String gstNo);
	
	List<SellersDTO> getAllSellersDetails();
	
	SellerProfile updateSellerAccountStatus(int id,String accStatus);
	
	SellerProfile getSellersProfileByUserDtlsId(int uid);
	
	public SellerProfile updateSellerProfileDetails(SellerProfile sellerProfile);
	
	public Boolean updateSellerProfileAndUserDtls(SellerProfile sellerProfile,MultipartFile profileFile,MultipartFile certificateFile) throws IOException;
	
}
