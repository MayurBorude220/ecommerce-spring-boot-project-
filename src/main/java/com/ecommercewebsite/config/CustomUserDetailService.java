package com.ecommercewebsite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommercewebsite.exception.ResourseNotFoundException;
import com.ecommercewebsite.model.User;
import com.ecommercewebsite.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userrepo.findByEmail(username).orElseThrow(()->new ResourseNotFoundException("user not found"));
		
		return user;
	}

}
