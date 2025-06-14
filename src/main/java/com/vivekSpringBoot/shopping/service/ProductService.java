package com.vivekSpringBoot.shopping.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.model.Product;

public interface ProductService {

	public Product saveProductData(Product product);
	public List<Product> getAllProducts();
	public Boolean deleteProductById(int id);
	public Product getProductById(int id);
	public Product updateProduct(Product product,MultipartFile file) throws IOException;
	public List<Product> getAllActiveProductsList(String category);
	
}
