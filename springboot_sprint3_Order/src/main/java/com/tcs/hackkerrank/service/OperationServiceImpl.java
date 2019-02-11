package com.tcs.hackkerrank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.hackkerrank.dao.OperationRepositoryDao;
import com.tcs.hackkerrank.model.Order;
@Service
public class OperationServiceImpl implements OperationInterface {

	@Autowired
	private OperationRepositoryDao dao;
	
	@Override
	public List<Order> findAll(String query) {
		return dao.findAll(query);
	}

	@Override
	public Order findById(Long id, Class<?> clazz) {
		return dao.findEntityById(id, clazz);
	}

	@Override
	public Order insertEntity(Order entity) {
		return dao.insertEntity(entity);
	}

	@Override
	public Order updateEntity(Order entity, Class<?> clazz, Long id) {
		entity = dao.updateEntity(entity, clazz, id);
		return entity;
	}

	@Override
	public Order delete(Long id, Class<?> clazz) {
		return dao.deleteEntity(clazz, id);
		
	}

	@Override
	public void deleteAll(String query) {
		dao.deleteAll(query);
	}

	@Override
	public List<Order> findByCustomerId(Long id) {
		List<Order> orderList =  dao.findAll("select o from Order o where o.customerId="+id.toString());
		
		return orderList;
	}

}
