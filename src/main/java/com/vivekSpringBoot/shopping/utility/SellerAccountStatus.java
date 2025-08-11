package com.vivekSpringBoot.shopping.utility;

public enum SellerAccountStatus {

	PENDING(1,"Pending"),                      // Awaiting admin review and approval
	APPROVED(2,"Approved"),                                                           // Seller is active and allowed to sell
	REJECTED(3,"Rejected"),                                                                   // Application denied during approval process
	SUSPENDED(4,"Suspended");                                                      // Seller account temporarily or permanently blocked after approval
	
	private int id;
	private String description;
	
	private SellerAccountStatus(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	
	public static String getDescriptionById(int id) {
		
		for( SellerAccountStatus sas : SellerAccountStatus.values()) {
			
			if(sas.getId() == id) {
				
				return sas.getDescription();
			}
			
		}
		
		return null;
	}
	
	
	
	
}
