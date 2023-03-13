package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandle {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex)
	{
		String message = ex.getMessage();
		
		ApiResponse apiResponse = new ApiResponse(message,false);
		
		return new ResponseEntity<ApiResponse>(apiResponse ,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> 
	                   handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	
	                  
		Map<String, String> resp = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error->
		{
			resp.put(error.getField(), error.getDefaultMessage());
			
		});
		
	   return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
		
		
	                
	   }  
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> badCredentialException(ApiException ex)
	{
		String message = ex.getMessage();
		
		ApiResponse apiResponse = new ApiResponse(message,true);
		
		return new ResponseEntity<ApiResponse>(apiResponse ,HttpStatus.BAD_REQUEST);
	}
}
