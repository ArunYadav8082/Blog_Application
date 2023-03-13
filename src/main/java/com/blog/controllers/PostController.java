package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.Constant;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.sevices.FileService;
import com.blog.sevices.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	// create post controller
	
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			                                  @PathVariable Integer userId,
			                                  @PathVariable Integer catId)
	{
		PostDto createPost = this.postService.createPost(postDto, catId, userId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	// get post by category
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer catId)
	{
		List<PostDto> postByCategory = this.postService.getPostByCategory(catId);
		
		return new ResponseEntity<List<PostDto>>(postByCategory,HttpStatus.OK);
	}
	
	// get post by user
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
	{
		List<PostDto> postByUsers = this.postService.getPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(postByUsers,HttpStatus.OK);
	}
	
	// get All posts pagination and sorting implements Also
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> findAllPosts(
		@RequestParam(value = "pageNumber", defaultValue = Constant.PAGE_NUMBER ,required= false) Integer pageNumber,
		@RequestParam(value = "pageSize", defaultValue = Constant.PAGE_SIZE ,required= false) Integer pageSize,
		@RequestParam(value = "sortBy", defaultValue = Constant.SORT_BY ,required= false) String sortBy,
		@RequestParam(value = "sortDir", defaultValue = Constant.SORT_DIR ,required= false) String sortDir
			)
	{
		PostResponse allPost = this.postService.getAllPost(pageNumber,pageSize, sortBy,sortDir);
		
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
		
	}
	
	// get post By Id
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> findPostById(@PathVariable Integer postId)
	{
		PostDto post = this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
		
	}
	
	// delete post by id
	
	@DeleteMapping("post/{postId}")
	public ApiResponse deletePostById(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post Delete Successfully", true);
		
	}
	
	// update post
	
	@PutMapping("post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable Integer postId)
	{
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
	}
	
	// seach post method by title
	
	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords)
	{
		List<PostDto> results = this.postService.searchPosts(keywords);
		
		return new ResponseEntity<List<PostDto>>(results , HttpStatus.OK);
		
	}
	
	// upload image
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException
			
	{
		PostDto postDto = this.postService.getPostById(postId);
		
		String imageName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(imageName);
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPostDto , HttpStatus.OK);
		
	}
	
	// get image 
	
	@GetMapping(value ="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName, HttpServletResponse response
			) throws IOException
	{
		System.out.println("path"+":"+path);
		
		InputStream resource = this.fileService.getResource(path, imageName);
		//System.out.println("Downloading Handler1111");
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	//	System.out.println("Downloading Handler222");
		StreamUtils.copy(resource, response.getOutputStream());
		//System.out.println("Downloading Handler333");
	}

	
	

}
