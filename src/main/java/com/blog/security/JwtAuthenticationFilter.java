package com.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.sevices.UserService;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
  @Autowired
  private JwtTokenHelper jwtUtility;
  
  @Autowired
  private UserDetailsService userService;

 @Override
  protected void doFilterInternal(HttpServletRequest Request, HttpServletResponse Response, FilterChain filterChain) throws ServletException, IOException {
	  String authorization = Request.getHeader("Authorization");
	  String token = null;
	  String userName = null;

	 if(null != authorization && authorization.startsWith("Hearer ")) {
		            token = authorization.substring(7);
		            
		 try {
		            	
		       userName = this.jwtUtility.getUsernameFromToken(token);
		            	
		            	
		            	
						
		} catch (IllegalArgumentException e) {

		    System.out.println("Illegal Arguments");
		}
		 catch (ExpiredJwtException e) {
						
		  	System.out.println("Jwt Token Expired");
		}
		   catch (MalformedJwtException e) {
						
		   	System.out.println("Invalid Jwt Exception");
		}
	}
	  else
 {
		  System.out.println("Token not start with Bearer!!!!");
	  }
		            
		   if(token!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		   {
		        	// validitate token
		        	
		      UserDetails userDetails = this.userService.loadUserByUsername(userName);
		        	
		        	
		     if(this.jwtUtility.validateToken(token, userDetails))
		     {
		        		
		    	 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                 = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

         usernamePasswordAuthenticationToken.
                               setDetails(new WebAuthenticationDetailsSource().buildDetails(Request));
       

         SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		       }
		      else
		      {
		        		System.out.println("Invalid Token !!!");
		       }
		        	
		      }
		      else
		    {
		        	System.out.println("UserName is Null");
		     }
		           
		        
          filterChain.doFilter(Request, Response);
		 
		        
		  }
			

		    
	}


