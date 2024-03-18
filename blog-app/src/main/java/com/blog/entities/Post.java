package com.blog.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer postId;
	
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	
	@ManyToOne() //multiple post will be on each categegory
	private Category category;
	
	@ManyToOne() //multiple user will be post one each categegory
	private User user;

}
