package com.project.blogapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogapi.entities.Users;
import com.project.blogapi.exceptions.ResourceAlreadyExistsException;
import com.project.blogapi.exceptions.ResourceNotFoundException;
import com.project.blogapi.payloads.UsersDto;
import com.project.blogapi.repositories.UsersRepo;
import com.project.blogapi.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UsersRepo usersRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UsersDto createUser(UsersDto userDto) {
		
		if(this.usersRepo.existsById(userDto.getId())) throw new ResourceAlreadyExistsException("User already exist");
		
		Users user = this.dtoToUsers(userDto);
		
		Users savedUser = this.usersRepo.save(user);
		
		return this.userToDto(savedUser);
		
	}

	@Override
	public UsersDto updateUser(UsersDto userDto, int userId) {
		
		if(!this.usersRepo.existsById(userId)) throw new ResourceNotFoundException("User with id " + userId + " not found");
		
		Users user = this.usersRepo.findById(userId);
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		Users updatedUser = this.usersRepo.save(user);
		
		UsersDto updatedUserDto = this.userToDto(updatedUser);
		
		return updatedUserDto;
		
	}

	@Override
	public UsersDto getUsersById(int userId) {
		
		if(!this.usersRepo.existsById(userId)) throw new ResourceNotFoundException("User with id " + userId + " not found");
		
		Users user = this.usersRepo.findById(userId);
		UsersDto userDto = this.userToDto(user); 
		
		return userDto;
		
	}

	@Override
	public List<UsersDto> getAllUsers() {
		
		if(this.usersRepo.count() == 0) throw new ResourceNotFoundException("Empty Resources");
		
		List<Users> userList = (List<Users>) this.usersRepo.findAll();
		
		List<UsersDto> userDtoList = userList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		
		return userDtoList;
		
	}

	@Override
	public void deleteUser(int userId) {
		
		if(!this.usersRepo.existsById(userId)) throw new ResourceNotFoundException("User with id " + userId + " not found");
		
		Users user = this.usersRepo.findById(userId);
		this.usersRepo.delete(user);
		
	}
	
	public Users dtoToUsers(UsersDto userDto) {
		
		Users user = this.modelMapper.map(userDto, Users.class);
				
		return user;
		
	}
	
	public UsersDto userToDto(Users user) {
		
		UsersDto userDto = this.modelMapper.map(user, UsersDto.class);
		
		return userDto;
		
	}
	
}
