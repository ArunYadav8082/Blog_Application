/**
 * 
 */
package com.blog.ServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.payloads.PostDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.sevices.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	// create comments
	@Override
	public CommentDto createComments(CommentDto commentDto, Integer postId) {
	
		Post post = this.postRepo.findById(postId).
		       orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		
		Comment comment = this.modelMapper.map(commentDto,Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	
	@Override
	public void deleteCommentById(Integer commentId) {
	
		Comment comment = this.commentRepo.findById(commentId)
		        .orElseThrow(()-> new ResourceNotFoundException("comment", "commentId", commentId));
		this.commentRepo.delete(comment);

	}

}
