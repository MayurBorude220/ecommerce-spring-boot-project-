package com.ecommercewebsite.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private Set<CartItem> cartitem = new HashSet<>();
	
	@OneToOne
	private User user;

	
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(int cartId, Set<CartItem> cartitem) {
		super();
		this.cartId = cartId;
		this.cartitem = cartitem;
	}
	
	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Set<CartItem> getCartitem() {
		return cartitem;
	}

	public void setCartitem(Set<CartItem> cartitem) {
		this.cartitem = cartitem;
	}

	
	
}
