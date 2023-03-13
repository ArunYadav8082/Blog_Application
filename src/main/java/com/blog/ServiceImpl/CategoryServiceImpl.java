package com.blog.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepository;
import com.blog.sevices.CategoryService;

@Service
public class CategoryServiceImpl  implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
    
	// create category 
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category Cat = this.modelMapper.map(categoryDto, Category.class);
		Category saveCat = this.categoryRepo.save(Cat);
		return this.modelMapper.map(saveCat, CategoryDto.class);
	}

	// update category
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
		     .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		 Category updatedCat = this.categoryRepo.save(cat);
		 
		return this.modelMapper.map(updatedCat, CategoryDto.class);
 }

	 // delete category
	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id",categoryId));
		
		this.categoryRepo.delete(cat);
		
	}
 
	// Find by Id category
	@Override
	public CategoryDto findCategoryById(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).
		      orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	 // find All Categories
	@Override
	public List<CategoryDto> findAllCategories() {
		
		List<Category> categories = this.categoryRepo.findAll();
		
	 List<CategoryDto> catDto = categories.stream()
			                    .map((cat)->this.modelMapper.map(cat, CategoryDto.class))
		                                                     .collect(Collectors.toList());
		return catDto;
	}

}
