package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	
	long fieldValue;
	public ResourceNotFoundException(long fieldValue) {
		super(String.format("User not found with id: %s",fieldValue));
		
		this.fieldValue = fieldValue;
	}
	
	

}
