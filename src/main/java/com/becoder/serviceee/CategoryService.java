package com.becoder.serviceee;

import java.util.List;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;

public interface CategoryService {
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();
	
//	public CategoryDto getVategoryById(Integer id);

	public CategoryDto getCategoryById(Integer id);

	public Boolean deleteCategoryById(Integer id);
	
	

}
