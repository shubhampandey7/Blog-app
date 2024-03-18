package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	String fieldName;
	long fieldValue;
	public ResourceNotFoundException(String fieldName,long fieldValue) {
		super(String.format("%s not found with id: %s",fieldName,fieldValue));
		
		this.fieldValue = fieldValue;
	}
	
	

}
