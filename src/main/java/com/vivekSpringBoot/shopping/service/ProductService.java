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
	public Product updateProduct(Product product,MultipartFile file,int CategoryId) throws IOException;
	
	// this method is not for pagination
	public List<Product> getAllActiveProductsList(String category);
	
	public List<Product> searchProductByKeyword(String keyword);
	
	// this method is for pagination
	Page<Product> getAllActiveProductsListPaginated(Integer categoryId,Integer pageNo,Integer pageSize);
	
	// this method is for pagination (including active and inactive products)
	public Page<Product> searchProductByKeywordPaginated(String keyword,Integer pageNo,Integer pageSize);
	
	// this method is for pagination and sorting (only active products)
	public Page<Product> searchActiveProductByKeywordPaginated(String keyword,Integer pageNo,Integer pageSize,String sortDir,String sortBy);
	
	// this method is to get all products list by seller id paginated
	public Page<Product> getAllProductsBySellerIdPaginated(Integer sid,Integer pageNo,Integer pageSize);
	
	// this method gives paginated Products list by keyword and seller id(including active and inactive products)
	public Page<Product> searchSellerProductByKeywordPaginated(Integer sid,String keyword,Integer pageNo,Integer pageSize);
	
	// this method is to delete Product (customized as per SellerProfile -> Product relation)
	public Boolean deleteSellerProductById(int id);
	
}
