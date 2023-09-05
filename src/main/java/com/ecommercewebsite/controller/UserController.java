package com.ecommercewebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercewebsite.payload.UserDto;
import com.ecommercewebsite.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private PasswordEncoder passwordencoder;

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userdto) {
		
		UserDto save = this.userservice.create(userdto);
		return new ResponseEntity<UserDto>(save, HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable int userId)
	{
		UserDto userid = this.userservice.getByUserId(userId);
		return new ResponseEntity<UserDto>(userid, HttpStatus.FOUND);
	}
}
