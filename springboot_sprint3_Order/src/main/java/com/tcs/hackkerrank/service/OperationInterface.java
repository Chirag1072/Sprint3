package com.tcs.hackkerrank.service;

import java.util.List;

import com.tcs.hackkerrank.model.Order;

public interface OperationInterface {
	List<Order> findAll(String query);
	Order findById(Long id,Class<?> clazz);
	List<Order> findByCustomerId(Long id);
	Order insertEntity(Order entity);
	Order updateEntity(Order entity, Class<?> clazz, Long id);
	Order delete(Long id,Class<?>clazz);
	void deleteAll(String query);
}
