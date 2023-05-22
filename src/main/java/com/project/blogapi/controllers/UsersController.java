package com.project.blogapi.controllers;

import java.util.List;
import java.util.Optional;

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

import com.project.blogapi.payloads.UsersDto;
import com.project.blogapi.service.UsersService;
import com.project.blogapi.utilities.ApiResponse;

import jakarta.validation.Valid;

@RestController
public class UsersController {
	
	@Autowired
	private UsersService usersService;
	
	@GetMapping("/get-user/{id}")
	public ResponseEntity<UsersDto> getUsersDto(@PathVariable int id) {
		
		UsersDto userDto = this.usersService.getUsersById(id);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
		
	}
	
	@PostMapping("/create-user")
	public ResponseEntity<ApiResponse> postUserDto(@Valid @RequestBody UsersDto userDto) {
		
		this.usersService.createUser(userDto);
		ApiResponse apiResponse = new ApiResponse("User created successfully", true);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);	
		
	}
	
	@PutMapping("/update-user/{id}")
	public ResponseEntity<UsersDto> updateUserDto(@Valid @RequestBody UsersDto userDto, @PathVariable int id) {
			
		UsersDto updatedUsersDto = this.usersService.updateUser(userDto, id);
		return ResponseEntity.of(Optional.of(updatedUsersDto));			
		
	}
	
	@GetMapping("/get-all-users")
	public ResponseEntity<List<UsersDto>> getAllUsersDto() {
		
		List<UsersDto> usersDtos = this.usersService.getAllUsers();
		return ResponseEntity.of(Optional.of(usersDtos));
		
	}
	
	@DeleteMapping("/delete-user/{id}")
	public ResponseEntity<ApiResponse> deleteUserDto(@PathVariable int id) {
		
		this.usersService.deleteUser(id);
		
		ApiResponse apiResponse = new ApiResponse("User deleted successfully", true);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
		
	}

}
