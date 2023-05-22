package com.project.blogapi.service;

import java.util.List;

import com.project.blogapi.exceptions.ResourceAlreadyExistsException;
import com.project.blogapi.exceptions.ResourceNotFoundException;
import com.project.blogapi.payloads.CategoryDto;

public interface CategoryService {
	
	public CategoryDto getCategory(int id) throws ResourceNotFoundException;
	
	public void postCategory(CategoryDto categoryDto) throws ResourceAlreadyExistsException;
	
	public void updateCategory(CategoryDto categoryDto, int id) throws ResourceNotFoundException, ResourceAlreadyExistsException;
	
	public void deleteCategory(int id) throws ResourceNotFoundException;
	
	public List<CategoryDto> getAllCategory() throws ResourceNotFoundException;
	
}
