package com.project.blogapi.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private int categoryId;
	
	@NotNull
	@NotBlank
	private String categoryTitle;
	
	@NotNull
	@NotBlank
	private String categoryDescription;

}
