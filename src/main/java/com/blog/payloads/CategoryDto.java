package com.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	@NotBlank
	@Size(min= 4, max = 50 ,message = "categoryTitle is minimun of four characters"
			+ " and maximum 50 characters!!")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 15,max = 100, message = "categoryDescription is minimum 15"
			+ " characters and max 100 characters")
	private String categoryDescription;
}
