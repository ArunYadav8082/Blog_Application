package com.blog.sevices;

import java.util.List;

import com.blog.payloads.CategoryDto;


public interface CategoryService 
{

	// create category
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// update category
	
	CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryId);
	
	// delete category
	
	void deleteCategory(Integer categoryId);
	
	// get category by Id
	
	CategoryDto findCategoryById(Integer categoryId);
	
	// get All category
	
	List<CategoryDto> findAllCategories();
}
