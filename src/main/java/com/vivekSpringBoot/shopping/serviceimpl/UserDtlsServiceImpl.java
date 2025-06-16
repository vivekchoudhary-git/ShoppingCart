package com.vivekSpringBoot.shopping.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

}
