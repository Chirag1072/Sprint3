package com.tcs.hackkerrank.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tcs.hackkerrank.model.Order;
import com.tcs.hackkerrank.model.OrderLineItem;

@Repository
@Transactional
public class OperationRepositoryDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Order insertEntity(Order entity) {
		 entityManager.persist(entity);
		for (OrderLineItem item : entity.getOrderLineItemList()) {
			entityManager.persist(item);
		}
		return entity;
	}
	
	public Order findEntityById(Long id, Class<?> requestedClass) {
		return (Order) entityManager.find(requestedClass, id);
	}
	
	public List<Order> findAll(String query){
		return (List<Order>) entityManager.createQuery(query).getResultList();
	}
	
	public Order updateEntity(Order entity, Class<?> requestedClass,Long id) {
		Order fetchEntity = this.findEntityById(id, requestedClass);
		if(null == fetchEntity) {
			return null;
		}
		return entityManager.merge(entity);
	}
	
	public Order deleteEntity(Class<?> requestedClass,Long id) {
		Order fetchEntity = this.findEntityById(id, requestedClass);
		if(null == fetchEntity) {
			return null;
		}
		entityManager.remove(fetchEntity);
		return fetchEntity;

	}
	
	public void deleteAll(String query) {
		entityManager.createQuery(query).executeUpdate();
	}

}
