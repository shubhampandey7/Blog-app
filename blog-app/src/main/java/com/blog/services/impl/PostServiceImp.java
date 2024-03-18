package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImp implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User",userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category",categoryId));
		Post post=this.modelMapper.map(postDto,Post.class); //this can done creating methods too postToDto /dtoToPost
		post.setCategory(category);
		post.setAddedDate(new Date());
		post.setImageName("default.png");
		post.setUser(user);
		
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer id) {
		Post post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post",id));
		if(postDto.getTitle()!=null)
		post.setTitle(postDto.getTitle());
		if(postDto.getContent()!=null)
		post.setContent(postDto.getContent());
		if(postDto.getImage()!=null)
		post.setImageName(postDto.getImage());
		
		Post updatePost=this.postRepo.save(post);
		return this.modelMapper.map(updatePost,PostDto.class);
	}

	@Override
	public void deletePost(Integer id) {
		Post post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post",id));
		this.postRepo.delete(post);

	}

	@Override
	public PostDto getPostById(Integer id) {
		Post post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post",id));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy) {
		
		//list can be used but will have large no of post so on each page data will shown 
		//Pageable will help to create pagesize of page for e.g page 0 will give 5 post like this page number goes on
		//PostResponse class is created bcoz in web we have to provide lost of information.
		//Sort.by(sortBy) is used to sort on which property also using . or passing one more string asc/desc sorting is done
		Pageable p=PageRequest.of(pageNumber,pageSize,Sort.by(sortBy));
		
		Page<Post> pagePost=this.postRepo.findAll(p);
		
		List<Post> allPost=pagePost.getContent();
		List<PostDto> postDtoList=allPost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalPage(pagePost.getTotalPages());
		
		return postResponse;
	}

	
	@Override
	public PostResponse getAllPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category",categoryId));
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=this.postRepo.findByCategory(category, p);
		List<Post> allpost=pagePost.getContent();
		List<PostDto> postDtoList=allpost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(postDtoList);
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
		
		
		
		return postResponse;
	}
	@Override
	public PostResponse getAllPostByUser(Integer userId,Integer pageNumber,Integer pageSize) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User",userId));
		
		Pageable p=PageRequest.of(pageNumber,pageSize);
		Page<Post> page=this.postRepo.findByUser(user, p);
		
		List<Post> allPage=page.getContent();
		List<PostDto> postDtoList=allPage.stream().map((post)->this.modelMapper.map(page,PostDto.class)).collect(Collectors.toList());
		
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setLastPage(page.isLast());
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElement(page.getTotalElements());
		postResponse.setTotalPage(page.getTotalPages());
		
		
		return postResponse;
	}

	@Override
	public PostResponse searchPosts(String keyword,Integer pageNumber,Integer pageSize) {
		
		Pageable p=PageRequest.of(pageNumber,pageSize);
		Page<Post> page=this.postRepo.findByTitleContaining(keyword,p);
		
		List<Post> allPages=page.getContent();
		List<PostDto> postDtoList=allPages.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setLastPage(page.isLast());
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElement(page.getTotalElements());
		postResponse.setTotalPage(page.getTotalPages());
		return postResponse;
		
	}

}
