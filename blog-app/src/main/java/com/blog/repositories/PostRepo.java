package com.blog.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;


@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
	
	public Page<Post> findByUser(User user,Pageable p);
	public Page<Post> findByCategory(Category category,Pageable p);
	//searches keyword of postTitle
	public Page<Post> findByTitleContaining(String keyword,Pageable p);

}
