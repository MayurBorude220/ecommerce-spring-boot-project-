package com.ecommercewebsite.service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercewebsite.entity.Product;
import com.ecommercewebsite.exception.ResourseNotFoundException;
import com.ecommercewebsite.model.Cart;
import com.ecommercewebsite.model.CartItem;
import com.ecommercewebsite.model.User;
import com.ecommercewebsite.payload.CartDto;
import com.ecommercewebsite.payload.ItemRequest;
import com.ecommercewebsite.repository.CartRepository;
import com.ecommercewebsite.repository.Productrepository;
import com.ecommercewebsite.repository.UserRepository;

@Service
public class CartService {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private Productrepository productrepo;
	
	@Autowired
	private CartRepository cartrepo;
	
	@Autowired
	private ModelMapper mapper;

	public CartDto addItem(ItemRequest item, String email) {

		int productId = item.getProductId();
		int quantity = item.getQuantity();
		// fetch user
		User user = this.userrepo.findByEmail(email)
				.orElseThrow(() -> new ResourseNotFoundException("user not found"));
		// fetch product
		Product product = this.productrepo.findById(productId)
				.orElseThrow(() -> new ResourseNotFoundException("product not found"));

		if (!product.isStock()) {
			new ResourseNotFoundException("product out of Stock");
		}

		// create cartitem with productId and quantity

		CartItem cartitem = new CartItem();
		cartitem.setProduct(product);
		cartitem.setQuantity(quantity);
		double totalprice = product.getProduct_price() * quantity;
		cartitem.setTotalprice(totalprice);

		// getting cart from user
		Cart cart = user.getCart();

		if (cart == null) {
		 cart = new Cart();

		}
		
		cartitem.setCart(cart);
		
		Set<CartItem> items = cart.getCartitem();
		
		// here we check item is available in cartItem or not
		//if cartitem is available then we increment qnt only else add new product in cartitem
		//
		//by default false
		AtomicReference<Boolean> flag = new AtomicReference<>(false);// we cannot return in lamda fucntion
		Set<CartItem>newproduct=items.stream().map((i)->{
			if(i.getProduct().getId()==product.getId()) {
				i.setQuantity(quantity);
				i.setTotalprice(totalprice);
				flag.set(true);
				
			}
			return i;
		}).collect(Collectors.toSet());
		
		if(flag.get())
		{
			items.clear();
			items.addAll(newproduct);
		}
		else {
			cartitem.setCart(cart);
			items.add(cartitem);
		}
		Cart save = this.cartrepo.save(cart);
		
		return this.mapper.map(save, CartDto.class);

	}
	
	public CartDto getCartAll(String email) {
		User user = this.userrepo.findByEmail(email).orElseThrow(()-> new ResourseNotFoundException("user not found"));
		
		// find cart
		Cart cart = this.cartrepo.findByUser(user).orElseThrow(()-> new ResourseNotFoundException("Cart not found"));
		return this.mapper.map(cart, CartDto.class);
		
	}
	
	// get cart by cart id
	public CartDto getCartById(int cartId) {
		//User user = this.userrepo.findByEmail(username).orElseThrow(()-> new ResourseNotFoundException("user not found"));
		Cart findById = this.cartrepo.findById(cartId).orElseThrow(()-> new ResourseNotFoundException("Cart not found"));
		
		return this.mapper.map(findById, CartDto.class);
	}
}
