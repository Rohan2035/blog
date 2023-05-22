package com.project.blogapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogapi.entities.Category;
import com.project.blogapi.entities.Post;
import com.project.blogapi.entities.Users;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	public Post findByPostId(int postid);
	
	public Page<Post> findByUsers(Users user, Pageable pageable);
	
	public Page<Post> findByCategory(Category category, Pageable pageable);
	
	public List<Post> findAll();
	
	public boolean existsByPostTitle(String postTitle);
	
	public List<Post> findByPostTitleContaining(String keyword);
	
}
