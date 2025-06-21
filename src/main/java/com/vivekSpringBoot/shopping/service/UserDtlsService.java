package com.vivekSpringBoot.shopping.service;

import java.util.List;

import com.vivekSpringBoot.shopping.model.UserDtls;

public interface UserDtlsService {

	public UserDtls saveUserDtlsData(UserDtls userDtls);
	
	public UserDtls getUserDtlsDataByEmail(String email);
	
	public List<UserDtls> getAllUsersByRole(String role);
	
	// updating isEnabled property of UserDtls
	public UserDtls updateUserDtlsStatus(int id,Boolean status);
	
}
