package com.blog.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepo;
import com.blog.sevices.PostService;

@Service
public class PostServiceImpl implements PostService
{
 
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper moddelMapper;
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	      // create post
	@Override
	public PostDto createPost(PostDto postDto,Integer categoryId ,Integer userId) {
		
    User user = this.userRepo.findById(userId).
		      orElseThrow(()-> new ResourceNotFoundException("User", "user Id", userId));
    
    Category cat = this.catRepo.findById(categoryId).
		      orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));
    
      Post post = this.moddelMapper.map(postDto,Post.class);
      
      post.setCategory(cat);
      post.setUser(user);
      post.setImageName("default.png");
      post.setAddedDate(new Date());
      
      Post savedPost = this.postRepo.save(post);
		
		return this.moddelMapper.map(savedPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
	
		Post post = this.postRepo.findById(postId)
				  .orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post savedpost = this.postRepo.save(post);
		
		return this.moddelMapper.map(savedpost,PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId)
				  .orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
		
		this.postRepo.delete(post);
		
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
	
	Post post = this.postRepo.findById(postId)
		  .orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
	PostDto postDto = this.moddelMapper.map(post, PostDto.class);
	
		return postDto;
	}
	
	// get All post according to pageNumber and pageSize and to sorting order customized !!!

	@Override
	public PostResponse getAllPost(Integer PageNumber,Integer PageSize ,String sortBy , String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		/*Sort sort = null;
		
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort = Sort.by(sortBy).ascending();
		}
		else
		{
			sort = Sort.by(sortBy).descending();
		}*/
		
		PageRequest p = PageRequest.of(PageNumber, PageSize, sort);
		
		Page<Post> postPage = this.postRepo.findAll(p);
		
		List<Post> posts = postPage.getContent();
		
		List<PostDto> postDtos = posts.stream().map(post->this.moddelMapper.map(post,PostDto.class))
		                                              .collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElements(postPage.getTotalElements());
		postResponse.setLastPage(postPage.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
    Category cat = this.catRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
    
    List<Post> posts = this.postRepo.findByCategory(cat);
    
    List<PostDto> postDtos = posts.stream().map(post->this.moddelMapper.map(post,PostDto.class))
    		                                                              .collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).
					orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
	    
	    List<Post> posts = this.postRepo.findByUser(user);
	    
	    List<PostDto> postDtos = posts.stream().map(post->this.moddelMapper.map(post,PostDto.class))
	    		                                                         .collect(Collectors.toList());
		return postDtos;
	}

	// searching By title
	@Override
	public List<PostDto> searchPosts(String name) {
		
		List<Post> posts = this.postRepo.searchPostByTitleContaining(name);
		
		List<PostDto> postDtos = posts.stream().
				  map(post->this.moddelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
