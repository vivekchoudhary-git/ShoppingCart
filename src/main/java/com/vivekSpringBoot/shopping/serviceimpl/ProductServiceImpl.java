package com.vivekSpringBoot.shopping.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vivekSpringBoot.shopping.model.Product;
import com.vivekSpringBoot.shopping.repository.ProductRepo;
import com.vivekSpringBoot.shopping.service.ProductService;
import com.vivekSpringBoot.shopping.utility.DiscountUtility;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private DiscountUtility discountUtility;
	
	@Override
	public Product saveProductData(Product product) {
	
		Product savedProduct = productRepo.save(product);
		
		return savedProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		
		List<Product> productsList = productRepo.findAll();
		
		return productsList;
	}

	@Override
	public Boolean deleteProductById(int id) {
		
		Optional<Product> productOptional = productRepo.findById(id);
		
		if(!productOptional.isEmpty()) {
			
			Product product = productOptional.get();
			
			productRepo.delete(product);
			
			return true;
		}
		
		return false;
	}

	@Override
	public Product getProductById(int id) {
		
		Optional<Product> productOptional = productRepo.findById(id);
		
		if(!productOptional.isEmpty()) {
			
			Product product = productOptional.get();
			return product;
		}
		
		return null;
	}

	@Override
	public Product updateProduct(Product product,MultipartFile file) throws IOException {
	
        Double discountedPrice = discountUtility.calculateDiscountedPrice(product.getDiscount(), product.getPrice());
		
		product.setDiscountedPrice(discountedPrice);
		
		Optional<Product> oldProductOptional = productRepo.findById(product.getId());
		
		if(!oldProductOptional.isEmpty()) {
			
			Product oldProduct = oldProductOptional.get();
			
	       String updatedImageName = (!file.isEmpty() && file != null) ? file.getOriginalFilename() : oldProduct.getImageName();
			
			oldProduct.setTitle(product.getTitle());
			oldProduct.setDescription(product.getDescription());
			oldProduct.setCategory(product.getCategory());
			oldProduct.setPrice(product.getPrice());
			oldProduct.setDiscount(product.getDiscount());
			oldProduct.setDiscountedPrice(product.getDiscountedPrice());
			oldProduct.setIsActive(product.getIsActive());
			oldProduct.setStock(product.getStock());
			oldProduct.setImageName(updatedImageName);
			
			Product savedUpdatedProduct = productRepo.save(oldProduct);
			
			if(!file.isEmpty() && file != null) {
				
                String productUploadPath = environment.getProperty("product.upload.path");
				
				Path productImageFullPath = Paths.get(productUploadPath, file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), productImageFullPath, StandardCopyOption.REPLACE_EXISTING);
				
			}
			
			return savedUpdatedProduct;
		}
		
		return null;
	}

	
	// this method is not for pagination
	@Override
	public List<Product> getAllActiveProductsList(String category) {
		
		List<Product> activeProductsList = null;
		
		if(category.equals("all")) {
		 activeProductsList = productRepo.findByIsActiveTrue();
		
         if(CollectionUtils.isEmpty(activeProductsList)) {
			
			System.out.println("activeProductsList is Empty");
		}
         
		}else {
			
		activeProductsList = productRepo.fetchActiveProductByCategory(category);
		}
		
		return activeProductsList;
	}

	@Override
	public List<Product> searchProductByKeyword(String keyword) {
	
		List<Product> productList = productRepo.searchAnyProductByTitleOrCategory(keyword);
		
		if(!ObjectUtils.isEmpty(productList)) {
			
			return productList;
		}
		
		return null;
	}
	
	
	// this method is for pagination
	@Override
	public Page<Product> getAllActiveProductsListPaginated(String category,Integer pageNo,Integer pageSize) {
		
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Product> activeProductsListPage = null;
		
		if(category.equals("all")) {
			
			activeProductsListPage = productRepo.findByIsActiveTrue(pageable);
		}else {
			
			activeProductsListPage = productRepo.fetchActiveProductByCategory(category,pageable);
		}
		
		return activeProductsListPage;
	}

	@Override
	public Page<Product> getAllProductsPaginated(Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Product> allProductsListPaginated = productRepo.findAll(pageable);
		
		
		return allProductsListPaginated;
	}

	@Override
	public Page<Product> searchProductByKeywordPaginated(String keyword, Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Product> searchProductsListPaginated = productRepo.searchAnyProductByTitleOrCategoryPaginated(keyword, pageable);
		
		return searchProductsListPaginated;
	}

	@Override
	public Page<Product> searchActiveProductByKeywordPaginated(String keyword, Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Product> searchActiveProductsListPaginated = productRepo.searchAnyActiveProductByTitleOrCategoryPaginated(keyword, pageable);
		
		return searchActiveProductsListPaginated;
	}
	
	
	
	
	

}
