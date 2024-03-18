package com.blog.entities;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Integer categoryId;
	
	@Column(name="title")
	private String categoryTitle;
	
	@Column(name="description")
	private String categoryDescription;
	
	//each category will have multiple post
	//cascade->When we perform some action on the target entity, the same action will be applied to the associated entity.
	//fetch->Lazy Loading is a design pattern that we use to defer initialization of an object as long as it’s possible.
	//column will be added in post
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL,fetch=FetchType.LAZY) 
	private Set<Post> posts=new LinkedHashSet<>();

}
