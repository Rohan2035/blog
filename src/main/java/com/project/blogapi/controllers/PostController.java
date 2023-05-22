package com.project.blogapi.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.blogapi.config.AppConstants;
import com.project.blogapi.payloads.PostDto;
import com.project.blogapi.payloads.PostResponse;
import com.project.blogapi.service.FileService;
import com.project.blogapi.service.PostService;
import com.project.blogapi.utilities.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@GetMapping("/get-post/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable int postId) {
		
		PostDto postDto = this.postService.getPost(postId);
		
		return new ResponseEntity<>(postDto, HttpStatus.OK);
		
	}
	
	@GetMapping("/get-post-by-user/{userId}")
	public ResponseEntity<PostResponse> getPostByUser(
			@PathVariable int userId,
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize
			){
		
		PostResponse postResponse = this.postService.getPostByUsers(userId, pageNumber, pageSize);
		
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/get-post-by-category/{categoryId}")
	public ResponseEntity<PostResponse> getPostByCategory(
			@PathVariable int categoryId,
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize
			) {
		
		PostResponse postResponse = this.postService.getPostByCategory(categoryId, pageNumber, pageSize);
		
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/get-all-post")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy
			) {
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy);
		
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
		
	}
	
	@PostMapping("/user/{userId}/category/{categoryId}/create-post")
	public ResponseEntity<ApiResponse> createPost(@Valid @RequestBody PostDto  postDto, @PathVariable int userId, @PathVariable int categoryId) {
		
		this.postService.createPost(postDto, userId, categoryId);
		
		ApiResponse apiResponse = new ApiResponse("Post created successfully", true);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
		
	}
	
	
	@PutMapping("/update-post/{postId}")
	public ResponseEntity<ApiResponse> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable int postId) {
		
		this.postService.updatePost(postDto, postId);
		
		ApiResponse apiResponse = new ApiResponse("Post updated successfully", true);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete-post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId) {
		
		this.postService.deletePost(postId);
		
		ApiResponse apiResponse = new ApiResponse("Post deleted successfully", true);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/search-post/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword) { 
		
		List<PostDto> postDtos = this.postService.searchPost(keyword);
		
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
		
	}
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable int postId) throws IOException {	
			
		String filename = this.fileService.uploadImage(path, image);
		PostDto postDto = this.postService.getPost(postId);
		
		postDto.setImageData(filename);
		
		this.postService.updatePost(postDto, postId);
		
		PostDto updatedPostDto = this.postService.getPost(postId);
		
		return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
		
	}
	
	/** Go for image name from the package explorer and not with the postman name */
	
	@GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) {
		
		try {
			
			InputStream resource = this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		
		} catch(FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
