package com.vivekSpringBoot.shopping.utility;

public enum OrderStatus {

	IN_PROGRESS(1,"In Progress"),
	ORDER_RECEIVED(2,"Order Received"),
	PRODUCT_PACKED(3,"Product is packed"),
	OUT_FOR_DELIVERY(4,"Out for delivery"),
	DELIVERED(5,"Delivered"),
	CANCELLED(6,"Cancelled");
	
	private Integer id;
	private String name;
	
	private OrderStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
