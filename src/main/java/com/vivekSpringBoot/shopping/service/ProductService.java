package com.vivekSpringBoot.shopping.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.model.Product;

public interface ProductService {

	public Product saveProductData(Product product);
	
	// this method is not for pagination
	public List<Product> getAllProducts();
	
	// this method is for pagination
	public Page<Product> getAllProductsPaginated(Integer pageNo,Integer pageSize);
	
	public Boolean deleteProductById(int id);
	public Product getProductById(int id);
	public Product updateProduct(Product product,MultipartFile file) throws IOException;
	
	// this method is not for pagination
	public List<Product> getAllActiveProductsList(String category);
	
	public List<Product> searchProductByKeyword(String keyword);
	
	// this method is for pagination
	Page<Product> getAllActiveProductsListPaginated(String category,Integer pageNo,Integer pageSize);
	
	// this method is for pagination
	public Page<Product> searchProductByKeywordPaginated(String keyword,Integer pageNo,Integer pageSize);
	
}
