package com.project.blogapi.service;

import java.util.List;

import com.project.blogapi.exceptions.ResourceAlreadyExistsException;
import com.project.blogapi.exceptions.ResourceNotFoundException;
import com.project.blogapi.payloads.UsersDto;

public interface UsersService {
	
	UsersDto createUser(UsersDto users) throws ResourceAlreadyExistsException;
	
	UsersDto updateUser(UsersDto user, int userId) throws ResourceNotFoundException;
	
	UsersDto getUsersById(int userId) throws ResourceNotFoundException;
	
	List<UsersDto> getAllUsers() throws ResourceNotFoundException;
	
	void deleteUser(int userId) throws ResourceNotFoundException;

}
