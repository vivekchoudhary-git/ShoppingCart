package com.vivekSpringBoot.shopping.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivekSpringBoot.shopping.model.Product;
import com.vivekSpringBoot.shopping.repository.ProductRepo;
import com.vivekSpringBoot.shopping.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;
	
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

}
