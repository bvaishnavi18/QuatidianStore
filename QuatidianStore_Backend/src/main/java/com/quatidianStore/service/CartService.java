package com.quatidianStore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quatidianStore.configuration.JwtRequestFilter;
import com.quatidianStore.dao.CartDao;
import com.quatidianStore.dao.ProductDao;
import com.quatidianStore.dao.UserDao;
import com.quatidianStore.entity.Cart;
import com.quatidianStore.entity.Product;
import com.quatidianStore.entity.User;

@Service
public class CartService {

	@Autowired
	private CartDao cartDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	
	public void deleteCartItem(Integer cartID) 
	{
		cartDao.deleteById(cartID);
	}
	
	public Cart addToCart(Integer productId) {
		Product product = productDao.findById(productId).get();
		String username = JwtRequestFilter.CURRENT_USER;
		User user = null;
		if (username != null) {
			user = userDao.findById(username).get();
		}

		List<Cart> cartList = cartDao.findByUser(user);
		List<Cart> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId)
				.collect(Collectors.toList());

		if (filteredList.size()>0) {
			return null;
		}
		
		
		if (product != null && user != null) {
			Cart cart = new Cart(product, user);
			return cartDao.save(cart);
		}

		return null;

	}

	public List<Cart> getCartDetails() {
		String username = JwtRequestFilter.CURRENT_USER;
		User user = userDao.findById(username).get();
		return cartDao.findByUser(user);
	}

}
