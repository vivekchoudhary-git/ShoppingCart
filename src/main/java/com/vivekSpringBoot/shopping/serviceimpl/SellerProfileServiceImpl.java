package com.vivekSpringBoot.shopping.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.dto.SellersDTO;
import com.vivekSpringBoot.shopping.model.SellerProfile;
import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.repository.SellerProfileRepo;
import com.vivekSpringBoot.shopping.service.SellerProfileService;
import com.vivekSpringBoot.shopping.utility.SellerAccountStatus;

@Service
public class SellerProfileServiceImpl implements SellerProfileService{

	@Autowired
	private SellerProfileRepo sellerProfileRepo;
	
	@Autowired
	private Environment environment;
	
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

	@Override
	public SellerProfile getSellersProfileByUserDtlsId(int uid) {
		
		SellerProfile existingSellerProfile = sellerProfileRepo.findByUserDtlsId(uid);
		
		return existingSellerProfile;
	}

	@Override
	public SellerProfile updateSellerProfileDetails(SellerProfile sellerProfile) {
		
		SellerProfile savedSellerProfile = sellerProfileRepo.save(sellerProfile);
		
		return savedSellerProfile;
	}

	@Override
	public Boolean updateSellerProfileAndUserDtls(SellerProfile sellerProfile,MultipartFile profileFile,MultipartFile certificateFile) throws IOException {
		
		String userImageUploadPath = environment.getProperty("userimage.upload.path");
		String sellerCertificateUploadPath = environment.getProperty("sellerCertificate.upload.path");
		
		Optional<SellerProfile> existingSellerProfileOptional = sellerProfileRepo.findById(sellerProfile.getId());
		
		UserDtls userDtls = sellerProfile.getUserDtls();
		
		if(existingSellerProfileOptional.isEmpty()) {
			
			System.out.println("existingSellerProfile is null");
			
			return false;
		}else {
			
			SellerProfile existingSellerProfile = existingSellerProfileOptional.get();
			UserDtls existingUserDtls = existingSellerProfile.getUserDtls();
			
			// updating Seller
			String regCertificateName = certificateFile.getOriginalFilename();
			if(regCertificateName != null && !regCertificateName.isEmpty() ) {
				existingSellerProfile.setRegCertificate(regCertificateName);
			}
			
			existingSellerProfile.setCompanyName(sellerProfile.getCompanyName());
			existingSellerProfile.setCompanyAddress(sellerProfile.getCompanyAddress());
			existingSellerProfile.setGstNo(sellerProfile.getGstNo());
			existingSellerProfile.setPanNo(sellerProfile.getPanNo());
			existingSellerProfile.setBankAccount(sellerProfile.getBankAccount());
			existingSellerProfile.setIfscCode(sellerProfile.getIfscCode());
			existingSellerProfile.setAccountUpdatedAt(LocalDateTime.now());
			
			// updating User
			String userProfileImageName = profileFile.getOriginalFilename();
			if(userProfileImageName != null && !userProfileImageName.isEmpty()) {
				
				existingUserDtls.setProfileImage(userProfileImageName);
			}
			
			existingUserDtls.setName(userDtls.getName());
			existingUserDtls.setPhoneNo(userDtls.getPhoneNo());
			existingUserDtls.setEmail(userDtls.getEmail());
			existingUserDtls.setAddress(userDtls.getAddress());
			existingUserDtls.setCity(userDtls.getCity());
			existingUserDtls.setState(userDtls.getState());
			existingUserDtls.setPincode(userDtls.getPincode());
			
			SellerProfile updatedSellerAndUser = sellerProfileRepo.save(existingSellerProfile);
			
			if(!ObjectUtils.isEmpty(updatedSellerAndUser)) {
				
				if(!profileFile.isEmpty() && profileFile != null) {
					
					Path userImagePath = Paths.get(userImageUploadPath);
					
					if(!Files.exists(userImagePath)) {
					
						Files.createDirectories(userImagePath);
					}
					
					Path fullUserImagePath = userImagePath.resolve(profileFile.getOriginalFilename());
					
					Files.copy(profileFile.getInputStream(), fullUserImagePath, StandardCopyOption.REPLACE_EXISTING);
				}
				
                   if(!certificateFile.isEmpty() && certificateFile != null) {
					
					Path sellerCertificatePath = Paths.get(sellerCertificateUploadPath);
					
					if(!Files.exists(sellerCertificatePath)) {
					
						Files.createDirectories(sellerCertificatePath);
					}
					
					Path fullSellerCertificatePath = sellerCertificatePath.resolve(certificateFile.getOriginalFilename());
					
					Files.copy(certificateFile.getInputStream(), fullSellerCertificatePath, StandardCopyOption.REPLACE_EXISTING);
				}
				
				return true;
			}else {
				
				return false;
			}
			
		}
		
	}

	
}
