package com.ecommercewebsite.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercewebsite.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUserId(int userId);

	public Optional<User> findByEmail(String email);
}
