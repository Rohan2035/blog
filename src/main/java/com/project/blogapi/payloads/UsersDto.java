package com.project.blogapi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsersDto {
	
	private int id;
	
	@NotNull
	@NotBlank
	@Size(min = 4, message = "Username must be min of 4 characters")
	private String name;
	
	@Email(message="Email address is not valid")
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	@Size(min = 4, message = "Password should be min of 4 characters")
	private String password;
	
	@NotNull
	@NotBlank
	private String about;

}
