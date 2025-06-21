package com.vivekSpringBoot.shopping.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.repository.UserDtlsRepo;
import com.vivekSpringBoot.shopping.service.UserDtlsService;

@Service
public class UserDtlsServiceImpl implements UserDtlsService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDtls saveUserDtlsData(UserDtls userDtls) {
		
		userDtls.setRole("ROLE_USER");
		userDtls.setIsEnabled(true);
		
		String encodedPassword = bCryptPasswordEncoder.encode(userDtls.getPassword());
		
		userDtls.setPassword(encodedPassword);
		
		UserDtls savedUserDtls = userDtlsRepo.save(userDtls);
		
		return savedUserDtls;
	}

	@Override
	public UserDtls getUserDtlsDataByEmail(String email) {
		
		UserDtls userDtls = userDtlsRepo.findByEmail(email);
		
		return userDtls;
	}

	@Override
	public List<UserDtls> getAllUsersByRole(String role) {
		
		List<UserDtls> userDtlsList = userDtlsRepo.findByRole(role);
		
		if(!CollectionUtils.isEmpty(userDtlsList)) {
			
			System.out.println("userDtlsList is not empty or null");
		}
		
		return userDtlsList;
	}

	@Override
	public UserDtls updateUserDtlsStatus(int id, Boolean status) {
		
		UserDtls userDtls = userDtlsRepo.findById(id);
		
		if(!ObjectUtils.isEmpty(userDtls)) {
			
			userDtls.setIsEnabled(status);
			userDtlsRepo.save(userDtls);
		}
		
		return userDtls;
	}



}
