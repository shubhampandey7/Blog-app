package com.blog.services;





import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	public PostDto updatePost(PostDto postDto,Integer id);
	public void deletePost(Integer id);
	public PostDto getPostById(Integer id);
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy);
	
	//get all post by category
	public PostResponse getAllPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize);
	//get all post by user
	public PostResponse getAllPostByUser(Integer userId,Integer pageNumber,Integer pageSize);
	//get all post by keywords 
	public PostResponse searchPosts(String keyword,Integer pageNumber,Integer pageSize);

}
