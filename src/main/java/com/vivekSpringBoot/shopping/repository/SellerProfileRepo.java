package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vivekSpringBoot.shopping.dto.SellersDTO;
import com.vivekSpringBoot.shopping.model.SellerProfile;

public interface SellerProfileRepo extends JpaRepository<SellerProfile, Integer> {

	Boolean existsByGstNo(String gstNo);
	
	// Note : use aliases to match DTO fields exactly
	@Query(value = "select sell.id as sellerId, "+
			"us.name as name, "+
			"us.phone_no as phoneNo, "+
			"us.email as email, "+
			"us.profile_image as profileImage, "+
			"us.is_enabled as isEnabled, "+
			"sell.company_name as companyName, "+
			"sell.company_address as companyAddress, "+
			"sell.gst_no as gstNo, "+
			"sell.account_status as accountStatus "+
			"from user_dtls as us "+
			"inner join seller_profile as sell "+
			"on us.id = sell.user_id ",nativeQuery = true)
	List<SellersDTO> fetchAllSellersData();
	
}
