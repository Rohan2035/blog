package com.project.blogapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogapi.entities.Category;
import com.project.blogapi.exceptions.ResourceAlreadyExistsException;
import com.project.blogapi.exceptions.ResourceNotFoundException;
import com.project.blogapi.payloads.CategoryDto;
import com.project.blogapi.repositories.CategoryRepo;
import com.project.blogapi.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto getCategory(int id) throws ResourceNotFoundException {
		
		if(!this.categoryRepo.existsById(id)) throw new ResourceNotFoundException("Category not found");
		
		Category category = categoryRepo.findById(id);
		
		CategoryDto categoryDto = this.categoryToCategoryDto(category);
		
		return categoryDto;
		
	}

	@Override
	public void postCategory(CategoryDto categoryDto) throws ResourceAlreadyExistsException {
		
		if(this.categoryRepo.existsByCategoryTitle(categoryDto.getCategoryTitle())) throw new ResourceAlreadyExistsException("Category already exists");
		
		Category category = this.categoryDtoToCategory(categoryDto);
		
		this.categoryRepo.save(category);
		
	}

	@Override
	public void updateCategory(CategoryDto categoryDto, int id) throws ResourceNotFoundException, ResourceAlreadyExistsException {
		
		if(!this.categoryRepo.existsById(id)) throw new ResourceNotFoundException("Category not found");
		
		if(this.categoryRepo.existsByCategoryTitle(categoryDto.getCategoryTitle())) throw new ResourceAlreadyExistsException("Category already exists");
		
		Category category = this.categoryRepo.findById(id);
		
		category = this.categoryDtoToCategory(categoryDto);
		
		this.categoryRepo.save(category);
		
	}

	@Override
	public void deleteCategory(int id) throws ResourceNotFoundException {
		
		if(!this.categoryRepo.existsById(id)) throw new ResourceNotFoundException("Category not found");
		
		this.categoryRepo.delete(this.categoryRepo.findById(id));
		
	}

	@Override
	public List<CategoryDto> getAllCategory() throws ResourceNotFoundException {
		
		if(this.categoryRepo.count() == 0) throw new ResourceNotFoundException("No categories found");
		
		List<Category> categoryList = this.categoryRepo.findAll();
		
		List<CategoryDto> categoryDtoList = categoryList.stream().map(category -> this.categoryToCategoryDto(category)).collect(Collectors.toList());
		
		return categoryDtoList;
		
	}
	
	public CategoryDto categoryToCategoryDto(Category category) {
		
		return this.modelMapper.map(category, CategoryDto.class);
		
	}
	
	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		
		return this.modelMapper.map(categoryDto, Category.class);
		
	}

}
