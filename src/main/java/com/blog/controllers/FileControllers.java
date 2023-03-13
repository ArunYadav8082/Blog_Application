package com.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.payloads.FileResponse;
import com.blog.sevices.FileService;

@RestController
@RequestMapping("/file")
public class FileControllers {
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> uploadImage(
			@RequestParam("image") MultipartFile image) throws IOException
			
	{
		
	//	System.out.println("file Handler");
		String fileName = this.fileService.uploadImage(path, image);
		
		return new ResponseEntity<FileResponse>
		                  (new FileResponse(fileName ,"File Uploading Successfully!!"),HttpStatus.OK);
		
	}
	
	// serve image
	
	@GetMapping(value ="/profiles/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
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
