package com.project.blogapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogapi.payloads.CommentDto;
import com.project.blogapi.service.CommentService;
import com.project.blogapi.utilities.ApiResponse;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/create-comment/user/{userId}/post/{postId}")
	public ResponseEntity<ApiResponse> createComment(@PathVariable int postId, @PathVariable int userId, @RequestBody CommentDto commentDto) {
		
		this.commentService.createComment(commentDto, postId, userId);
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setMessage("Comment successfully added");
		apiResponse.setStatus(true);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/delete-comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId) {
		
		this.commentService.deleteComment(commentId);
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setMessage("Comment successfully deleted");
		apiResponse.setStatus(true);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
		
	}

}
