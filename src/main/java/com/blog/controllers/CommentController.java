package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.sevices.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	// create 
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComments(
			                      @PathVariable Integer postId, @RequestBody CommentDto commentDto)
	{
		CommentDto commentDto1 = this.commentService.createComments(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(commentDto1 , HttpStatus.CREATED);
		
	}
		
	
	// delete
    
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComments(@PathVariable Integer commentId)
	{
		this.commentService.deleteCommentById(commentId);
		
		return new ResponseEntity<ApiResponse> 
		                    (new ApiResponse("Comments Deleted SuccessFully !!" , true), HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
}
