package com.tcs.hackkerrank.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tcs.hackkerrank.model.BaseEntity;

@Repository
@Transactional
public class OperationRepositoryDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public BaseEntity insertEntity(BaseEntity entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	public BaseEntity findEntityById(Long id, Class<?> requestedClass) {
		return (BaseEntity) entityManager.find(requestedClass, id);
	}
	
	public List<BaseEntity> findAll(String query){
		return (List<BaseEntity>) entityManager.createQuery(query).getResultList();
	}
	
	public BaseEntity updateEntity(BaseEntity entity, Class<?> requestedClass,Long id) {
		BaseEntity fetchEntity = this.findEntityById(id, requestedClass);
		if(null == fetchEntity) {
			return null;
		}
		return entityManager.merge(entity);
	}
	
	public BaseEntity deleteEntity(Class<?> requestedClass,Long id) {
		BaseEntity fetchEntity = this.findEntityById(id, requestedClass);
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
