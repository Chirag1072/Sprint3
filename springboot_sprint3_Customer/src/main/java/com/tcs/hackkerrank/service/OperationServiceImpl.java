package com.tcs.hackkerrank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.hackkerrank.dao.OperationRepositoryDao;
import com.tcs.hackkerrank.model.BaseEntity;
@Service
public class OperationServiceImpl implements OperationInterface {

	@Autowired
	private OperationRepositoryDao dao;
	
	@Override
	public List<BaseEntity> findAll(String query) {
		List<String> strList = new ArrayList<>();
		return dao.findAll(query);
	}

	@Override
	public BaseEntity findById(Long id, Class<?> clazz) {
		return dao.findEntityById(id, clazz);
	}

	@Override
	public BaseEntity insertEntity(BaseEntity entity) {
		return dao.insertEntity(entity);
	}

	@Override
	public BaseEntity updateEntity(BaseEntity entity, Class<?> clazz, Long id) {
		entity = dao.updateEntity(entity, clazz, id);
		return entity;
	}

	@Override
	public BaseEntity delete(Long id, Class<?> clazz) {
		return dao.deleteEntity(clazz, id);
		
	}

	@Override
	public void deleteAll(String query) {
		dao.deleteAll(query);
	}

}
