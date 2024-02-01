package com.quatidianStore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quatidianStore.entity.OrderDetail;
import com.quatidianStore.entity.User;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
	public List<OrderDetail> findByUser(User user);
	
     public List<OrderDetail> findByOrderStatus(String status);
}
