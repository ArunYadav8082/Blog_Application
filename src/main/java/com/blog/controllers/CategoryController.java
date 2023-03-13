package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.sevices.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService catService;
	
	// create category
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory( @Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto catDto = this.catService.createCategory(categoryDto);
		
		return  new ResponseEntity<CategoryDto>(catDto , HttpStatus.CREATED);
		
	}
	
	// update category
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto
			                                                  , @PathVariable Integer catId)
	{
		CategoryDto updatedCat = this.catService.updateCategory(categoryDto, catId);
	
		return  new ResponseEntity<CategoryDto>(updatedCat , HttpStatus.OK);
	}
	
	// delete category
	@DeleteMapping("/{catId}")
	public ResponseEntity<?> deleteCategory(@Valid @PathVariable Integer catId)
	{
		this.catService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>
		                        (new ApiResponse("Deleted Category Successfully !!",true),HttpStatus.OK);
		
	}
	
	// find category by id
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> findCategoryById(@PathVariable Integer catId)
	{
		
		return  ResponseEntity.ok(this.catService.findCategoryById(catId));
		
	}
	
	// find All categories
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> findAllCategories()
	{
		return ResponseEntity.ok(this.catService.findAllCategories());
		
	}

}
