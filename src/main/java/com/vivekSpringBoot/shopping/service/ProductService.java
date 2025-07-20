package com.vivekSpringBoot.shopping.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.model.Product;

public interface ProductService {

	public Product saveProductData(Product product);
	public List<Product> getAllProducts();
	public Boolean deleteProductById(int id);
	public Product getProductById(int id);
	public Product updateProduct(Product product,MultipartFile file) throws IOException;
	
	// this method is not for pagination
	public List<Product> getAllActiveProductsList(String category);
	
	public List<Product> searchProductByKeyword(String keyword);
	
	// this method is for pagination
	Page<Product> getAllActiveProductsListPaginated(String category,Integer pageNo,Integer pageSize);
	
}
