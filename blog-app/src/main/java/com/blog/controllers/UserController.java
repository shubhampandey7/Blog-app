package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.UserDto;
import com.blog.payloads.ApiResponse;
import com.blog.services.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/user")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto=userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto,HttpStatus.OK);	
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		
		List<UserDto> userList=userService.getAllUser();
		
		return new ResponseEntity<>(userList,HttpStatus.OK);
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer id){
		
		UserDto user=this.userService.getUserById(id);
		
		return new ResponseEntity<>(user,HttpStatus.OK);	//new response is created bcoz we are sending httpstatus else not needed
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user,@PathVariable("userId") Integer id){
		
		UserDto updateUser=this.userService.updateUser(user, id);
		
		return ResponseEntity.ok(updateUser);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer id){
		
		this.userService.deleteUserById(id);
		return new ResponseEntity<ApiResponse>(new  ApiResponse("User id "+id+" is deleted sucessfully !!!",true),HttpStatus.OK);
		//apirespose class is created bcoz we need to send message multiple time from multiple api so we can use it
		
	}
		

 }
