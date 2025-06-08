package com.vivekSpringBoot.shopping.utility;

import org.springframework.stereotype.Service;

@Service
public class DiscountUtility {

	public static Double calculateDiscountedPrice(int discount,Double price) {
		
		Double discountValue = price*(discount/100.0);                    // note : 100 is integer write it as 100.0 to make it double
		
		Double discountedPrice= price - discountValue;
		
		return discountedPrice;
	}
	
	
}
