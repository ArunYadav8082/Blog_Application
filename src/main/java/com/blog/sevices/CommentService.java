package com.blog.sevices;

import com.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComments(CommentDto commentDto ,Integer postId);
	
	void deleteCommentById(Integer commentId);

}
