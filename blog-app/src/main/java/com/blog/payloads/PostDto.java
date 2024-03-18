package com.blog.payloads;

import java.util.Date;



import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer id;
	@Size(min=4,message="Minimun Title size is 4")
	private String title;
	@Size(min=10,message="Minimun Content size is 10")
	private String content;
	
	private Date addedDate;
	private String image;
	private UserDto user;
	private CategoryDto category;
}
