package com.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	
	private List<PostDto> content;
	private Integer pageSize;
	private Integer pageNumber;
	private boolean lastPage;
	private long totalElement;
	private Integer totalPage;

}
