package com.vivekSpringBoot.shopping.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

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
public class UserDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String phoneNo;
	private String email;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String password;
	private String profileImage;                             // this is profile image name
	private String role;                                   // added later
	private Boolean isEnabled;                           // added later
	private Boolean isAccountNonLocked;                    // added later
	private Integer failedAttempt;                            // added later
	private Date lockTime;                                       // added later
	private String passResetToken;                      // to reset the password
}
