package com.vivekSpringBoot.shopping.service;

import java.util.List;

import com.vivekSpringBoot.shopping.model.UserDtls;

public interface UserDtlsService {

	public UserDtls saveUserDtlsData(UserDtls userDtls);
	
	public UserDtls getUserDtlsDataByEmail(String email);
	
	public List<UserDtls> getAllUsersByRole(String role);
	
	// updating isEnabled property of UserDtls
	public UserDtls updateUserDtlsStatus(int id,Boolean status);
	
	public void increaseFailedAttempt(UserDtls userDtls);
	
	public void lockUserAccount(UserDtls userDtls);
	
	public boolean unlockUserAccount(UserDtls userDtls);
	
	public void updatePassResetToken(String email,String resetToken);
	
	public UserDtls getUserDtlsByToken(String resetToken);
	
	public UserDtls updateUserDtlsData(UserDtls userDtls);
	
	public UserDtls getUserDtlsDataById(Integer id);
	
	public UserDtls saveAdminUserDtlsData(UserDtls userDtls);
	
}
