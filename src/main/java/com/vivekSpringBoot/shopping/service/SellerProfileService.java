package com.vivekSpringBoot.shopping.service;

import java.util.List;

import com.vivekSpringBoot.shopping.dto.SellersDTO;
import com.vivekSpringBoot.shopping.model.SellerProfile;

public interface SellerProfileService {

	public SellerProfile saveSellerProfileDetails(SellerProfile sellerProfile);
	
	public Boolean checkSellerExists(String gstNo);
	
	List<SellersDTO> getAllSellersDetails();
	
	SellerProfile updateSellerAccountStatus(int id,String accStatus);
	
	SellerProfile getSellersProfileByUserDtlsId(int uid);
	
}
