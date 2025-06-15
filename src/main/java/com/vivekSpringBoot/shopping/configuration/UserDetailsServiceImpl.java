package com.vivekSpringBoot.shopping.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.repository.UserDtlsRepo;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		UserDtls userDtls = userDtlsRepo.findByEmail(username);
		
		if(ObjectUtils.isEmpty(userDtls)) {
			
			throw new UsernameNotFoundException("User Not Found");
		}
		
		return new CustomUser(userDtls);
	}

}
