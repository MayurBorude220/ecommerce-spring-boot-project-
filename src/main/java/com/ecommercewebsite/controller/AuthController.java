package com.ecommercewebsite.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercewebsite.exception.ResourseNotFoundException;
import com.ecommercewebsite.payload.JwtRequest;
import com.ecommercewebsite.payload.JwtResponse;
import com.ecommercewebsite.payload.UserDto;
import com.ecommercewebsite.security.JwtHelper;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private UserDetailsService userservice;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private JwtHelper iwthelper;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtrequest) {
		this.authenticateUser(jwtrequest.getUsername(), jwtrequest.getPassword());
		UserDetails loadUserByUsername = this.userservice.loadUserByUsername(jwtrequest.getUsername());
		String token = this.iwthelper.generateToken(loadUserByUsername);
		JwtResponse response = new JwtResponse();
		response.setToken(token);
		response.setUser(this.mapper.map(loadUserByUsername, UserDto.class));
		return new ResponseEntity<JwtResponse>(response,HttpStatus.ACCEPTED);
	}
	
	private void authenticateUser(String email, String password) {
		try {
			authmanager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		}catch(BadCredentialsException e) {
			throw new ResourseNotFoundException("Invalid username and password");
		}
		catch(DisabledException e) {
			throw new ResourseNotFoundException("User not Active");
		}
	}

}
