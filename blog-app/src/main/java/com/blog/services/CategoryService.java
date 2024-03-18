package com.blog.services;

import java.util.List;



import com.blog.payloads.CategoryDto;


public interface CategoryService {
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	public List<CategoryDto> getAllCategory();
	public CategoryDto getCategoryById(Integer id);
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
	public void deleteCategoryById(Integer id);

}
