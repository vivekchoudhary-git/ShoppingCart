package com.vivekSpringBoot.shopping.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler authSuccessHandlerImpl;
	
	@Autowired
	private AuthFailureHandlerImpl authFailureHandlerImpl;
	
	@Bean
	public UserDetailsService getUserDetailsServiceBean() {
		
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoderBean() {
		
		return new BCryptPasswordEncoder();
	}
	
	// this method will check whether user password is correct or not
	@Bean
	public DaoAuthenticationProvider getDaoAuthenticationProviderBean() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsServiceBean());
		daoAuthenticationProvider.setPasswordEncoder(getBCryptPasswordEncoderBean());
		
		return daoAuthenticationProvider;
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(getDaoAuthenticationProviderBean());
	}

	@Override
	protected void configure(HttpSecurity httpsecurtiy) throws Exception {
		
		httpsecurtiy.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/seller/entry","/seller/sellReg","/seller/saveSellReg").permitAll()
		.antMatchers("/seller/**").hasRole("SELLER")
		.antMatchers("/**").permitAll().and().formLogin().loginPage("/signin")
		.loginProcessingUrl("/login").successHandler(authSuccessHandlerImpl).failureHandler(authFailureHandlerImpl).and()                              
		.logout().logoutUrl("/logout").logoutSuccessUrl("/signin?logout=true").and()
		.csrf().disable();
		
		
	}
	
	
	
}
