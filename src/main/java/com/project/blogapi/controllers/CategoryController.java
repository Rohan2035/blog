package com.project.blogapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogapi.payloads.CategoryDto;
import com.project.blogapi.service.CategoryService;
import com.project.blogapi.utilities.ApiResponse;

import jakarta.validation.Valid;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/get-category/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable int id) {
		
		CategoryDto categoryDto = this.categoryService.getCategory(id);
		
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
		
	}
	
	@PostMapping("/post-category/")
	public ResponseEntity<ApiResponse> postCategory(@Valid @RequestBody CategoryDto categoryDto) {
		
		this.categoryService.postCategory(categoryDto);
		
		ApiResponse apiResponse = new ApiResponse("Category created sucessfully", true);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		
	}
	
	@PutMapping("/update-category/{id}")
	public ResponseEntity<ApiResponse> putCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int id) {
		
		this.categoryService.updateCategory(categoryDto, id);
		
		ApiResponse apiResponse = new ApiResponse("Category updated successfully", true);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete-category/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id) {
		
		this.categoryService.deleteCategory(id);
		
		ApiResponse apiResponse = new ApiResponse("Category deleted sucessfully", true);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/get-all-category/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		
		List<CategoryDto> categoryDtoList = this.categoryService.getAllCategory();
		
		return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
		
	}

}
