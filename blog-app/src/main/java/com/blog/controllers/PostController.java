package com.blog.controllers;



import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	//create file path at which image will be uploaded
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		
		PostDto createpost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createpost,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		PostDto post=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
		
	}
	
	//@RequestParam ->captures values from the query parameters or form parameters in the URL, which are usually appended to the URL after a “?” symbol. 
	//for e.g /api/posts?pageNumber=0&pageSize=4
	
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy){
		
		PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getAllPostByUser(@PathVariable Integer userId,
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE) Integer pageSize){
		
		PostResponse postResponse=this.postService.getAllPostByUser(userId, pageNumber, pageSize);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getAllPostByCategory(@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE) 
	Integer pageSize){
		
		
		PostResponse postResponse=this.postService.getAllPostByCategory(categoryId,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<PostResponse> getAllPostByKeyword(@PathVariable String keyword,
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE) 
	Integer pageSize){
		
		PostResponse postResponse=this.postService.searchPosts(keyword, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	}
	
	
	//@PathVariable -> captures values from the URL path itself, which is usually separated by slashes (“/”) e.g /api/post/2
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto post,@PathVariable Integer postId){
		
		PostDto updatePost=this.postService.updatePost(post, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deltePost(@PathVariable Integer postId){
		
		this.postService.deletePost(postId);
		return new  ResponseEntity<ApiResponse>(new ApiResponse("Post of id "+postId+" deleted sucessfully",true),HttpStatus.OK);
		
	}
	
	//post image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam ("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postDto=this.postService.getPostById(postId);
		
		String filePath=this.fileService.uploadImage(path, image);
		
		
		postDto.setImage(filePath);
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.ACCEPTED);
		
	}
	
	//download image
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downLoadFile(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		
		//get image
		InputStream file=this.fileService.getResource(path, imageName);
		//adds to response so we can see in webpage
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(file, response.getOutputStream());
		
	}
	
	
	
	
	

}
