package com.blog.sevices;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

	// create post
	
	PostDto createPost(PostDto postDto,Integer categoryId ,Integer userId);
	
	// update post
	
	PostDto updatePost(PostDto postDto ,Integer postId);
	
	// delete post
	
	void deletePost(Integer postId);
	
	//get post by Id
	
	PostDto getPostById(Integer postId);
	
	// get All posts
	
	PostResponse getAllPost(Integer PageNumer,Integer PageSize,String sortBy,String sortDir);
	
	// get All post by category
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	// get All post by User
	
	List<PostDto> getPostByUser(Integer userId);
	
	// search All posts
	
	List<PostDto> searchPosts(String name);
	
	
}
