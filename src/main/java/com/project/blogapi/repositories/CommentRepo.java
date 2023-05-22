package com.project.blogapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogapi.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	
	
}
