package com.project.blogapi.service;

import com.project.blogapi.exceptions.ResourceNotFoundException;
import com.project.blogapi.payloads.CommentDto;

public interface CommentService {
	
	public void createComment(CommentDto commentDto, int postId, int userId) throws ResourceNotFoundException;
	public void deleteComment(int commentId) throws ResourceNotFoundException;

}
