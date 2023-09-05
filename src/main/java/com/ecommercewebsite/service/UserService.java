package com.ecommercewebsite.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommercewebsite.exception.ResourseNotFoundException;
import com.ecommercewebsite.model.User;
import com.ecommercewebsite.payload.UserDto;
import com.ecommercewebsite.repository.RoleRepository;
import com.ecommercewebsite.repository.UserRepository;





@Service
public class  UserService {
	
	@Autowired
	private UserRepository userRepositroy;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public UserDto create(UserDto userDto) {
		  User user=this.toEntity(userDto);
		        String passEncode = this.passwordEncoder.encode(user.getPassword());
		        user.setPassword(passEncode);
		  User userCreate=this.userRepositroy.save(user);
		return this.toDto(userCreate);
	}

	public UserDto update(UserDto t, int userId) {
		User u=userRepositroy.findById(userId).orElseThrow(()->new ResourseNotFoundException("User not found by this id"));
		u.setPhoneno(t.getPhoneno());
		u.setPassword(t.getPassword());
		u.setName(t.getName());
		u.setGender(t.getGender());
		u.setEmail(t.getEmail());
		
		User Updateuser=this.userRepositroy.save(u);
		return this.toDto(Updateuser);
	}


	public void delete(int userId) {
		User u=userRepositroy.findById(userId).orElseThrow(() ->new ResourseNotFoundException("UserId not Found"));
		        userRepositroy.delete(u);
	
	}


	public List<UserDto> getAll() {
	        List<User> alluser=userRepositroy.findAll();	
		               List<UserDto> allUserDto=alluser.stream().map(user -> this.toDto(user)).collect(Collectors.toList());
		
		return allUserDto;
	}


	public UserDto getByUserId(int userId) {
				User finduser=userRepositroy.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User Not Found"+userId));
		          
				return this.toDto(finduser); 
	}


	public UserDto getByEmailId(String emailId) {
		       User findemail=userRepositroy.findByEmail(emailId).orElseThrow(() -> new ResourseNotFoundException("User Email Is is Not Exit"+emailId));
		return this.toDto(findemail);
	}
	@Autowired
	private ModelMapper mapper;
	public UserDto toDto(User u) {
//		UserDto dto=new UserDto();
//		dto.setUserId(u.getUserId());
//		dto.setName(u.getName());
//		dto.setEmail(u.getEmail());
//		dto.setPassword(u.getPassword());
//		dto.setGender(u.getGender());
//		dto.setPhone(u.getPhone());
		//dto.setRoles(u.getRoles());
		//return dto;
		
		return this.mapper.map(u,UserDto.class);
	}
	
public User toEntity(UserDto dto) {
//	User  u=new User();
//	u.setUserId(dto.getUserId());
//	u.setPhone(dto.getPhone());
//	u.setPassword(dto.getPassword());
//	u.setName(dto.getName());
//	u.setGender(dto.getGender());
//	u.setEmail(dto.getEmail());
//	//u.setRoles(u.getRoles());
//	return u;
	return this.mapper.map(dto,User.class);
}
}