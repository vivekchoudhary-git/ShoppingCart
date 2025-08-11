package com.vivekSpringBoot.shopping.serviceimpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivekSpringBoot.shopping.dto.SellersDTO;
import com.vivekSpringBoot.shopping.model.SellerProfile;
import com.vivekSpringBoot.shopping.repository.SellerProfileRepo;
import com.vivekSpringBoot.shopping.service.SellerProfileService;
import com.vivekSpringBoot.shopping.utility.SellerAccountStatus;

@Service
public class SellerProfileServiceImpl implements SellerProfileService{

	@Autowired
	private SellerProfileRepo sellerProfileRepo;
	
	@Override
	public SellerProfile saveSellerProfileDetails(SellerProfile sellerProfile) {
	
		sellerProfile.setAccountStatus(SellerAccountStatus.PENDING.getDescription());
		sellerProfile.setAccountCreatedAt(LocalDateTime.now());
		
		SellerProfile savedSellerProfile = sellerProfileRepo.save(sellerProfile);
		
		
		return savedSellerProfile;
	}

	@Override
	public Boolean checkSellerExists(String gstNo) {
		
		Boolean isSellerExists = sellerProfileRepo.existsByGstNo(gstNo);
		
		return isSellerExists;
	}

	@Override
	public List<SellersDTO> getAllSellersDetails() {
		
		List<SellersDTO> allSellersList = sellerProfileRepo.fetchAllSellersData();
		
		return allSellersList;
	}

	@Override
	public SellerProfile updateSellerAccountStatus(int id, String accStatus) {
		
		Optional<SellerProfile> sellerProfileOptional = sellerProfileRepo.findById(id);
		
		if(sellerProfileOptional.isPresent()) {
			
			SellerProfile sellerProfile = sellerProfileOptional.get();
			
			sellerProfile.setAccountStatus(accStatus);
			SellerProfile updatedSellerProfile = sellerProfileRepo.save(sellerProfile);
			
			return updatedSellerProfile;
		}
		
		return null;
	}

	
}
