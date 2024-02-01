package com.quatidianStore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quatidianStore.configuration.JwtRequestFilter;
import com.quatidianStore.dao.CartDao;
import com.quatidianStore.dao.ProductDao;
import com.quatidianStore.dao.UserDao;
import com.quatidianStore.entity.Cart;
import com.quatidianStore.entity.Product;
import com.quatidianStore.entity.User;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CartDao cartDao;
	
	public Product addnewProduct(Product product) {
		return productDao.save(product);
	}

	public List<Product> getAllProducts(int pageNumber, String searchKey) {
		// for a pagination
		Pageable pageable = PageRequest.of(pageNumber, 10);
		if (searchKey.equals("")) {
			return (List<Product>) productDao.findAll(pageable);

		} else {
			return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
			}

	}

	public void deleteProductDetails(Integer productId) {
		productDao.deleteById(productId);
	}

	public Product getProductDetailById(Integer productId) {
		return productDao.findById(productId).get();

	}

	public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {

		if (isSingleProductCheckout && productId != 0) {
			// buy single
			List<Product> list = new ArrayList<>();
			Product product = productDao.findById(productId).get();
			list.add(product);
			return list;

		} else {
               String username= JwtRequestFilter.CURRENT_USER;
               User user= userDao.findById(username).get();
              List<Cart> carts= cartDao.findByUser(user);
             return  carts.stream().map((x)->x.getProduct()).collect(Collectors.toList());
              
		}
	}

}
