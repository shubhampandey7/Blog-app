package com.blog.services;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	List<UserDto> getAllUser();
	UserDto getUserById(Integer userId);
	void deleteUserById(Integer userId);
	

}
