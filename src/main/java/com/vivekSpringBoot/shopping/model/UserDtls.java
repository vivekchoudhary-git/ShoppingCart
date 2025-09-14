package com.vivekSpringBoot.shopping.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import com.vivekSpringBoot.shopping.customvalidation.PasswordMatches;
import com.vivekSpringBoot.shopping.customvalidation.StrongPassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component     
@PasswordMatches                                                // class level validation
public class UserDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "Name is Required sv")
	private String name;
	@NotBlank(message = "phoneNo is Required sv")
	private String phoneNo;
	
	@Email(message = "Invalid Email Format sv")
	@NotBlank(message = "Email is Required sv")
	private String email;
	@NotBlank(message = "address is Required sv")
	private String address;
	@NotBlank(message = "city is Required sv")
	private String city;
	@NotBlank(message = "state is Required sv")
	private String state;
	@NotBlank(message = "pincode is Required sv")
	private String pincode;
	
	@NotBlank(message = "Password is Required sv")
	@StrongPassword
	private String password;
	
	@Transient
	private String confirmpassword;                             // this will not be stored in database              // added later
	private String profileImage;                             // this is profile image name
	private String role;                                   // added later
	private Boolean isEnabled;                           // added later
	private Boolean isAccountNonLocked;                    // added later
	private Integer failedAttempt;                            // added later
	private Date lockTime;                                       // added later
	private String passResetToken;                                 // to reset the password
	
	@OneToOne(mappedBy = "userDtls",cascade = CascadeType.ALL)                      // note userDtls should match with userDtls mentioned in SellerProfile Class
	private SellerProfile sellerProfile;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Cart> cartList;
	
}
