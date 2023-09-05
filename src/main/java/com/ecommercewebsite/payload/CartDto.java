package com.ecommercewebsite.payload;

import java.util.Set;

public class CartDto {

	private int cartId;
	
	private Set<CartItemDto> cartitemdto;
	private UserDto userdto;
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public Set<CartItemDto> getCartitemdto() {
		return cartitemdto;
	}
	public void setCartitemdto(Set<CartItemDto> cartitemdto) {
		this.cartitemdto = cartitemdto;
	}
	public UserDto getUserdto() {
		return userdto;
	}
	public void setUserdto(UserDto userdto) {
		this.userdto = userdto;
	}
	
}
