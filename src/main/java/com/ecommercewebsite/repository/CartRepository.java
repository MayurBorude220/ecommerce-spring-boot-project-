package com.ecommercewebsite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercewebsite.model.Cart;
import com.ecommercewebsite.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	public Optional<Cart> findByUser(User user);
	
	//public Optional<Cart> findByUser(int cartId);
	

}
