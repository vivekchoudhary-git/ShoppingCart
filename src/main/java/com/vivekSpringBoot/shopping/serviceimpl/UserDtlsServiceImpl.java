package com.vivekSpringBoot.shopping.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.repository.UserDtlsRepo;
import com.vivekSpringBoot.shopping.service.UserDtlsService;
import com.vivekSpringBoot.shopping.utility.AppConstant;

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
		userDtls.setIsAccountNonLocked(true);
		userDtls.setFailedAttempt(0);
		userDtls.setLockTime(null);
		
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

	@Override
	public void increaseFailedAttempt(UserDtls userDtls) {
		
		Integer failedAttempt = userDtls.getFailedAttempt();
		failedAttempt = failedAttempt+1;
		
		userDtls.setFailedAttempt(failedAttempt);
		userDtlsRepo.save(userDtls);
		
	}

	
	@Override
	public void lockUserAccount(UserDtls userDtls) {
		
		userDtls.setIsAccountNonLocked(false);
		userDtls.setLockTime(new Date());
		userDtlsRepo.save(userDtls);
		
	}

	@Override
	public boolean unlockUserAccount(UserDtls userDtls) {
		
		long lockTime = userDtls.getLockTime().getTime();
		
		long unLockTime = lockTime+AppConstant.UNLOCK_TIME_DURATION;
		
		if(unLockTime < System.currentTimeMillis()) {
			
			userDtls.setIsAccountNonLocked(true);
			userDtls.setFailedAttempt(0);
			userDtls.setLockTime(null);
			userDtlsRepo.save(userDtls);
			
			return true;
		}
		
		return false;
	}

	@Override
	public void updatePassResetToken(String email, String resetToken) {
		
		UserDtls userDtls = userDtlsRepo.findByEmail(email);
		
		if(!ObjectUtils.isEmpty(userDtls)) {
			
			userDtls.setPassResetToken(resetToken);
			userDtlsRepo.save(userDtls);
		}
		
	}

	@Override
	public UserDtls getUserDtlsByToken(String resetToken) {
		
		UserDtls userDtls = userDtlsRepo.findByPassResetToken(resetToken);
		
		return userDtls;
	}

	@Override
	public UserDtls updateUserDtlsData(UserDtls userDtls) {
	
		UserDtls updatedUserDtls = userDtlsRepo.save(userDtls);
		
		return updatedUserDtls;
	}

	@Override
	public UserDtls getUserDtlsDataById(Integer id) {
		
		Optional<UserDtls> optionalUserDtls = userDtlsRepo.findById(id);                      // note : Optional is accessible only when data type is Integer and not int
		
		UserDtls userDtls = null;
		
		if(!optionalUserDtls.isEmpty()) {
			
			userDtls = optionalUserDtls.get();
		}else {
			
			System.out.println("UserDtls object is null");
		}
		
		return userDtls;
	}



}
