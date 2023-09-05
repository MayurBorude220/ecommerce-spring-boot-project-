package com.ecommercewebsite.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercewebsite.payload.CartDto;
import com.ecommercewebsite.payload.ItemRequest;
import com.ecommercewebsite.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartservive;

	@PostMapping("/save")
	public ResponseEntity<CartDto> saveCart(@RequestBody ItemRequest item, Principal principal) {

		CartDto addItem = this.cartservive.addItem(item, principal.getName());

		return new ResponseEntity<CartDto>(addItem, HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<CartDto> getAllCart(Principal principal) {
		CartDto cartAll = this.cartservive.getCartAll(principal.getName());
		return new ResponseEntity<CartDto>(cartAll, HttpStatus.OK);
	}

	@GetMapping("/{cartId}")
	public ResponseEntity<CartDto> getCartById(@PathVariable int cartId) {

		CartDto cartById = this.cartservive.getCartById(cartId);
		return new ResponseEntity<CartDto>(cartById, HttpStatus.OK);
	}
}
