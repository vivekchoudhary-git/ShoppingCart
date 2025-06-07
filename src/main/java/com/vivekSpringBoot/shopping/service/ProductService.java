package com.vivekSpringBoot.shopping.service;

import java.util.List;

import com.vivekSpringBoot.shopping.model.Product;

public interface ProductService {

	public Product saveProductData(Product product);
	public List<Product> getAllProducts();
	public Boolean deleteProductById(int id);
	
}
