package com.project.blogapi.payloads;

import java.util.Date;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private int postId;
	
	@NotNull
	@NotBlank
	private String postTitle;
	
	@NotNull
	@NotBlank
	private String postDescription;
		
	private String imageData;
	
	private CategoryDto category;
	
	private UsersDto users;
	
	private Date addedDate;
	
	private Set<CommentDto> comments;
	
}