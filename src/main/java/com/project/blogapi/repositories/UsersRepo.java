package com.project.blogapi.repositories;

import org.springframework.data.repository.CrudRepository;
import com.project.blogapi.entities.Users;

public interface UsersRepo extends CrudRepository<Users, Integer> {
	
	public Users findById(int id);
	
}
