package com.vivekSpringBoot.shopping.service;

import java.util.List;

import com.vivekSpringBoot.shopping.model.Category;

public interface CategoryService {

	public Boolean checkByName(String categoryName);
	public Category saveCategory(Category category);
	public List<Category> getAllCategory();
	public Boolean deleteCategoryById(int id);
	public Category getCategoryDetailsById(int id);
	public Category updateCategoryDetails(Category category);
	public List<Category> getAllActiveCategoriesList();
	
}
