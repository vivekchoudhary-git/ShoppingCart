package com.vivekSpringBoot.shopping.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.repository.UserDtlsRepo;
import com.vivekSpringBoot.shopping.service.UserDtlsService;

@Service
public class UserDtlsServiceImpl implements UserDtlsService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Override
	public UserDtls saveUserDtlsData(UserDtls userDtls) {
		
		UserDtls savedUserDtls = userDtlsRepo.save(userDtls);
		
		return savedUserDtls;
	}

}
