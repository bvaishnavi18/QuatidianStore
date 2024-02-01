package com.quatidianStore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quatidianStore.entity.Cart;
import com.quatidianStore.entity.User;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer> {

	public List<Cart> findByUser(User user);
    	
	
	
}
