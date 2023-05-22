package com.project.blogapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogapi.entities.Comment;
import com.project.blogapi.entities.Post;
import com.project.blogapi.entities.Users;
import com.project.blogapi.exceptions.ResourceNotFoundException;
import com.project.blogapi.payloads.CommentDto;
import com.project.blogapi.repositories.CommentRepo;
import com.project.blogapi.repositories.PostRepo;
import com.project.blogapi.repositories.UsersRepo;
import com.project.blogapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UsersRepo usersRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void createComment(CommentDto commentDto, int postId, int userId) throws ResourceNotFoundException {
		
		if(!this.postRepo.existsById(postId)) throw new ResourceNotFoundException("Post not found");
		if(!this.usersRepo.existsById(userId)) throw new ResourceNotFoundException("User not found");
		
		Post post = this.postRepo.findByPostId(postId);
		
		Users user = this.usersRepo.findById(userId);
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		this.commentRepo.save(comment);
		
	}

	@Override
	public void deleteComment(int commentId) throws ResourceNotFoundException {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
		
		this.commentRepo.delete(comment);
		
	}

}
