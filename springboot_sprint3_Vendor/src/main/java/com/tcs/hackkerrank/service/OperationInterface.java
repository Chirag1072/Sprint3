package com.tcs.hackkerrank.service;

import java.util.List;

import com.tcs.hackkerrank.model.BaseEntity;

public interface OperationInterface {
	List<BaseEntity> findAll(String query);
	BaseEntity findById(Long id,Class<?> clazz);
	BaseEntity insertEntity(BaseEntity entity);
	BaseEntity updateEntity(BaseEntity entity, Class<?> clazz, Long id);
	BaseEntity delete(Long id,Class<?>clazz);
	void deleteAll(String query);
}
