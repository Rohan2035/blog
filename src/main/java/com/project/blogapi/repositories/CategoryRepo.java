package com.project.blogapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.blogapi.entities.Category;

public interface CategoryRepo extends CrudRepository<Category, Integer> {
	
	public Category findById(int id);
	
	public boolean existsByCategoryTitle(String name);
	
	public List<Category> findAll();

}
