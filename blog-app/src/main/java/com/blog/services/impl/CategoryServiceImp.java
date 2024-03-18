package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category=dtoTocategory(categoryDto);
		
		Category savedCategory=categoryRepo.save(category);
		
		return this.categoryToDto(savedCategory);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		List<Category> categoryList=categoryRepo.findAll();
		List<CategoryDto> categoryDtoList=categoryList.stream().map((category)->this.categoryToDto(category)).collect(Collectors.toList());
		return categoryDtoList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		
		Category category=categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category",id));
		return this.categoryToDto(category);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		
		Category category=categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category",id));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updateCategory=this.categoryRepo.save(category);
		return this.categoryToDto(updateCategory);
	}

	@Override
	public void deleteCategoryById(Integer id) {
		
		Category category=this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category",id));
		this.categoryRepo.delete(category);
		
	}
	
	public CategoryDto categoryToDto(Category category) {
		
		CategoryDto categoryDto=this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}
	
	public Category dtoTocategory(CategoryDto categoryDto) {
		
		Category category=this.modelMapper.map(categoryDto,Category.class);
		return category;
	}

}
