package com.vivekSpringBoot.shopping.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.vivekSpringBoot.shopping.model.UserDtls;
import com.vivekSpringBoot.shopping.service.UserDtlsService;
import com.vivekSpringBoot.shopping.utility.AppConstant;

@Service
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserDtlsService userDtlsServiceImpl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		  String email = request.getParameter("username");
		
		  UserDtls userDtls = userDtlsServiceImpl.getUserDtlsDataByEmail(email);
		  
		  if(!ObjectUtils.isEmpty(userDtls)) {
			  
		  if(userDtls.getIsEnabled()) {
			  
			  if(userDtls.getIsAccountNonLocked()) {
				  
				if(userDtls.getFailedAttempt() < AppConstant.MAX_PASSWORD_ATTEMPT) {
					
					userDtlsServiceImpl.increaseFailedAttempt(userDtls);
				}else {
					
					userDtlsServiceImpl.lockUserAccount(userDtls);
					
					exception = new LockedException("Your account is locked");
				}
				  
			  }else {
				  
				if(userDtlsServiceImpl.unlockUserAccount(userDtls)) {
					  // account unlocked silently; now treat as normal failed login
				    // do NOT override the exception here — let it stay as BadCredentials
					
				//	exception = new LockedException("Your account is now Unlocked.Please Login.......");
				}else {
					
				 exception = new LockedException("Your account is locked.Please try again after sometime.");
				}
			  }
			  
		  }else {
			  
			  exception = new LockedException("Your account is Inactive or Blocked");
			  
		  }
		  }else {
			  
			  exception = new LockedException("Invalid Email or Password");
		  }
		
		  super.setDefaultFailureUrl("/signin?error");
		  super.onAuthenticationFailure(request, response, exception);
		  
	}

	
	
}
