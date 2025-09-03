package com.vivekSpringBoot.shopping.utility;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.vivekSpringBoot.shopping.model.ProductOrder;

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
	
	// sending email to user to tell the status of order like,delivered,packed,in progress etc.
	public Boolean sendEmailToUserAboutOrderStatus(ProductOrder productOrder,String status) throws UnsupportedEncodingException, MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
		
		mimeMessageHelper.setFrom("vivekchoudhary7997@gmail.com", "ShoppingCart");
		mimeMessageHelper.setTo(productOrder.getOrderAddress().getEmail());
		mimeMessageHelper.setSubject("Product Order Status");
		
		String content = String.format(
			    "<p>Hello %s,</p>" +                                     // note : %s is place holder
			    "<p>Thank you, your order is <b>%s</b></p>" +
			    "<p><b>Product Details:</b></p>" +
			    "<p>Name: %s</p>" +
			    "<p>Category: %s</p>" +
			    "<p>Quantity: %s</p>" +
			    "<p>Price: Rs %s</p>" +
			    "<p>Payment Type: %s</p>",
			    productOrder.getOrderAddress().getFirstName(),
			    status,
			    productOrder.getProduct().getTitle(),
			    productOrder.getProduct().getCategory().getName(),
			    productOrder.getQuantity(),
			    productOrder.getPrice(),
			    productOrder.getPaymentType()
			);
		
		mimeMessageHelper.setText(content, true);
		
		javaMailSender.send(mimeMessage);
		
		return true;
	}
	
	
}
