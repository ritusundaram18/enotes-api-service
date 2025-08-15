package com.becoder.serviceee;

import java.util.List;

import com.becoder.entity.Category;

public interface CategoryService {
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategory();
	
	

}
