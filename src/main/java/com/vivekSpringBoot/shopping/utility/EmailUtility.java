package com.vivekSpringBoot.shopping.utility;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailUtility {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public static String generateURL(HttpServletRequest request) {
		
		String requestURL = request.getRequestURL().toString();
		// requestURL : http://localhost:8080/forgotPassEmail
		
		String modifiedURL = requestURL.replaceAll("forgotPassEmail","");
		// modifiedURL : http://localhost:8080/
		
		return modifiedURL;
	}
	
	
	public Boolean sendEmailToResetPassword(String passResetEmailURL,String recepientEmail) throws UnsupportedEncodingException, MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		
		mimeMessageHelper.setFrom("vivekchoudhary7997@gmail.com", "ShoppingCart");
		
		mimeMessageHelper.setTo(recepientEmail);
		
		mimeMessageHelper.setSubject("Reset Password on ShoppingCart");
		
		String content = "<p>Click the link below to reset your password:</p>" + 
			    "<p><a href=\"" + passResetEmailURL + "\">Reset your password</a></p>";
		
		mimeMessageHelper.setText(content,true);
		
		javaMailSender.send(mimeMessage);
		
		return true;
	}
	
}
