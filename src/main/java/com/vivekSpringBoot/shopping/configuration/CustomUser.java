package com.vivekSpringBoot.shopping.configuration;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vivekSpringBoot.shopping.model.UserDtls;

public class CustomUser implements UserDetails {

	@Autowired
	private UserDtls userDtls;
	
	public CustomUser(UserDtls userDtls) {
		
		this.userDtls = userDtls;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userDtls.getRole());
		
		return Arrays.asList(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		
		return userDtls.getPassword();
	}

	@Override
	public String getUsername() {
	
		return userDtls.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return userDtls.getIsAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return userDtls.getIsEnabled();
	}

}
