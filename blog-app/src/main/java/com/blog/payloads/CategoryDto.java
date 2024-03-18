package com.blog.payloads;




import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter

public class CategoryDto {
	
    private Integer categoryId;
	
	
	@Size(min=5,message="Title should of minimum 5 character")
	private String categoryTitle;
	
	
	@Size(min=15,message="Description should of minimum 15 character")
	private String categoryDescription;

}
