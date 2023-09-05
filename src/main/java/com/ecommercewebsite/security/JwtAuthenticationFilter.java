package com.ecommercewebsite.security;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

      Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private UserDetailsService userdetailsservice;
	
	@Autowired
	private JwtHelper jwthelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 1. get token
		String requestToken=request.getHeader("Authorization");
		logger.info("messaage" +requestToken);
		
		String username = null;
		String jwtToken = null;
		
		if(requestToken!=null && requestToken.trim().startsWith("Bearer")) {
			//get  actual token
		jwtToken=requestToken.substring(7);
		
		try {
			username=this.jwthelper.getUsernameFromToken(jwtToken);
			
		}catch(ExpiredJwtException e) {
			logger.info("Invalid Token message","Jwt token Expire");
			
		}catch(IllegalArgumentException e2) {
			logger.info("Invalid Token message","unable to get token");
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			// validate
			UserDetails userdetails = this.userdetailsservice.loadUserByUsername(username);
			
			if( this.jwthelper.validateToken(jwtToken, userdetails)) {
				
				UsernamePasswordAuthenticationToken auth = 
						new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(auth);
			
		        }else {
		        	logger.info("Invalid message"," Invalid Jwt token");
		        	
		        }
		    }else {
		    	logger.info("User message","username is null or auth is already there");
		    }
			
			
		}else {
			logger.info("Token message{}","token does not start with bearer");
		}
		filterChain.doFilter(request, response);
		
	}
	
	

}
