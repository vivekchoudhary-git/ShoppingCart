package com.vivekSpringBoot.shopping.configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

//  @Configuration                            // this annotation also creates instance of the class 
@Service
public class AuthSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Set<String> rolesSet = AuthorityUtils.authorityListToSet(authorities);
		
		if(rolesSet.contains("ROLE_ADMIN")) {
			
			response.sendRedirect("/admin/");             // this is the home page of ADMIN 
		}else if(rolesSet.contains("ROLE_SELLER")){
			
			response.sendRedirect("/seller/index");                   // this is the home page of Seller
		}else {
			
			response.sendRedirect("/");                   // this is the home page of User and others
		}
		
	}

	
	
}
