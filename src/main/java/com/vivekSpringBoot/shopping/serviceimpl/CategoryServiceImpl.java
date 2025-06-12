package com.vivekSpringBoot.shopping.serviceimpl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.vivekSpringBoot.shopping.model.Category;
import com.vivekSpringBoot.shopping.repository.CategoryRepo;
import com.vivekSpringBoot.shopping.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public Category saveCategory(Category category) {
		
		Category savedCategory = categoryRepo.save(category);
		
		return savedCategory;
	}

	@Override
	public List<Category> getAllCategory() {
		
		List<Category> categoryList = categoryRepo.findAll();
		
		return categoryList;
	}

	@Override
	public Boolean checkByName(String categoryName) {
		
		Boolean isNameExists = categoryRepo.existsByName(categoryName);
	
		return isNameExists;
	}

	@Override
	public Boolean deleteCategoryById(int id) {
		
		Category category = categoryRepo.findById(id).orElse(null);             // if it does not find category object of specific id then it will return null
		
		if(!ObjectUtils.isEmpty(category)) {
			
			categoryRepo.delete(category);
			return true;
		}else {
			
			return false;
		}
		
	}

	@Override
	public Category getCategoryDetailsById(int id) {
		
		Category category = categoryRepo.findById(id).orElse(null);
		
		return category;
	}

	@Override
	public Category updateCategoryDetails(Category category) {
		
		Category updatedCategory = categoryRepo.save(category);
		
		return updatedCategory;
	}

	@Override
	public List<Category> getAllActiveCategoriesList() {
	
		List<Category> activeCategoryList = categoryRepo.findByIsActiveTrue();
		
		if(CollectionUtils.isEmpty(activeCategoryList)) {
			
			System.out.println("activeCategoryList is Empty");
		}
		
		return activeCategoryList;
	}

	
	
}
