package com.project.blogapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.blogapi.entities.Category;
import com.project.blogapi.entities.Post;
import com.project.blogapi.entities.Users;
import com.project.blogapi.exceptions.ResourceAlreadyExistsException;
import com.project.blogapi.exceptions.ResourceNotFoundException;
import com.project.blogapi.payloads.PostDto;
import com.project.blogapi.payloads.PostResponse;
import com.project.blogapi.repositories.CategoryRepo;
import com.project.blogapi.repositories.PostRepo;
import com.project.blogapi.repositories.UsersRepo;
import com.project.blogapi.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UsersRepo usersRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	public PostDto postToPostPostDto(Post post) {
		
		return this.modelMapper.map(post, PostDto.class);
		
	}
	
	public Post postDtoToPost(PostDto postDto) {
		
		return this.modelMapper.map(postDto, Post.class);
		
	}
	
	
	@Override
	public PostDto getPost(int postId) throws ResourceNotFoundException {
		
		if(!this.postRepo.existsById(postId)) 
			throw new ResourceNotFoundException("Post not found");
		
		Post post = this.postRepo.findByPostId(postId);
		
		PostDto postDto = this.postToPostPostDto(post);
		
		return postDto;
		
	}

	@Override
	public void createPost(PostDto postDto, int userId, int categoryId) throws ResourceAlreadyExistsException, ResourceNotFoundException {
		
		if(!this.usersRepo.existsById(userId))
			throw new ResourceNotFoundException("User not found");
		
		if(!this.categoryRepo.existsById(categoryId))
			throw new ResourceNotFoundException("Category not found");
		
		if(this.postRepo.existsByPostTitle(postDto.getPostTitle()))
			throw new ResourceAlreadyExistsException("Post with same title already exists");
		
		Users user = this.usersRepo.findById(userId);
		Category category = this.categoryRepo.findById(categoryId);
		
		Post post = this.postDtoToPost(postDto);
		post.setImageData("default.png");
		post.setAddedDate(new Date());
		post.setUsers(user);
		post.setCategory(category);
		
		this.postRepo.save(post);
		
	}

	@Override
	public void deletePost(int postId) throws ResourceNotFoundException {
		
		if(!this.postRepo.existsById(postId)) throw new ResourceNotFoundException("Post not found");
		
		this.postRepo.delete(this.postRepo.findByPostId(postId));
		
	}

	@Override
	public void updatePost(PostDto postDto, int postId) throws ResourceAlreadyExistsException, ResourceNotFoundException {
		
		if(!this.postRepo.existsById(postId)) throw new ResourceNotFoundException("Post do not exists");
		
		Post post = this.postRepo.findByPostId(postId);
		
		post.setPostTitle(postDto.getPostTitle());
		post.setPostDescription(postDto.getPostDescription());
		post.setImageData(postDto.getImageData());
		post.setAddedDate(new Date());
		
		this.postRepo.save(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) throws ResourceNotFoundException {
		
		if(this.postRepo.count() == 0) throw new ResourceNotFoundException("No posts found");
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos = posts.stream().map((post) -> this.postToPostPostDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostByCategory(int categoryId, int pageNumber, int pageSize) {
		
		if(!this.categoryRepo.existsById(categoryId)) throw new ResourceNotFoundException("Category not found");
		
		Category category = this.categoryRepo.findById(categoryId);
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = this.postRepo.findByCategory(category, pageable);
		
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> this.postToPostPostDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
		
	}

	@Override
	public PostResponse getPostByUsers(int usersId, int pageNumber, int pageSize) {
		
		if(!this.usersRepo.existsById(usersId)) throw new ResourceNotFoundException("User ID does not exists");
		
		Users users = this.usersRepo.findById(usersId);
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePost = this.postRepo.findByUsers(users, pageable);
		
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> this.postToPostPostDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
		
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		
		List<Post> posts = this.postRepo.findByPostTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post) -> this.postToPostPostDto(post)).collect(Collectors.toList());
		
		return postDtos;
		
		
		
	}

}
