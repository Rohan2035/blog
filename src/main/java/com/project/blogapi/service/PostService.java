package com.project.blogapi.service;

import java.util.List;

import com.project.blogapi.exceptions.ResourceAlreadyExistsException;
import com.project.blogapi.exceptions.ResourceNotFoundException;
import com.project.blogapi.payloads.PostDto;
import com.project.blogapi.payloads.PostResponse;

public interface PostService {
	
	public PostDto getPost(int postId) throws ResourceNotFoundException;
	
	public void createPost(PostDto postDto, int userId, int categoryId) throws ResourceAlreadyExistsException, ResourceNotFoundException;
	
	public void deletePost(int postId) throws ResourceNotFoundException;
	
	public void updatePost(PostDto postDto, int postId) throws ResourceAlreadyExistsException, ResourceNotFoundException;
	
	public PostResponse getPostByCategory(int categoryId, int pageNumber, int pageSize) throws ResourceNotFoundException;
	
	public PostResponse getPostByUsers(int usersId, int pageNumber, int pageSize);
	
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) throws ResourceNotFoundException;
	
	public List<PostDto> searchPost(String keyword); 

}
