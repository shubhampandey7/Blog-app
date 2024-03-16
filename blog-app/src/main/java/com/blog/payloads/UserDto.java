package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private int id;
	
	@NotBlank
	@Size(min=3,message="Name should be at least 3 character")
	private String name;
	
	@Email(message="Invalid email address")
	private String email;
	
	
	@Pattern(regexp="^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",message="Password shoud consists upercase,lowercase,number,special character,minimum length 8 and maximun 16")
	private String password;
	
	@NotBlank
	@Size(min=10,message="Minimum 10 character is required")
	private String about;

}
