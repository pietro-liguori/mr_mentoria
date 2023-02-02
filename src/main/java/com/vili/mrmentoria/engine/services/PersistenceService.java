package com.vili.mrmentoria.engine.services;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.vili.mrmentoria.engine.IDataTransferObject;
import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.exceptions.custom.DatabaseException;
import com.vili.mrmentoria.engine.exceptions.custom.NoSuchEntityException;
import com.vili.mrmentoria.engine.exceptions.custom.ResourceNotFoundException;
import com.vili.mrmentoria.engine.repositories.IRepository;

public interface PersistenceService<T extends IEntity> {

	IRepository<T> getRepository();

	default T save(T entity) {
		if (entity == null)
			throw new NoSuchEntityException("Entity can't be null");
		
		entity.setUpdatedAt(new java.sql.Date(new Date().toInstant().toEpochMilli()));
		
		if (entity.getId() != null) {
			Optional<T> temp = getRepository().findById(entity.getId());
			
			if (temp.isPresent())
				entity = update(temp.get(), entity);
			else
				entity.setCreatedAt(new java.sql.Date(new Date().toInstant().toEpochMilli()));
		} else {
			entity.setCreatedAt(new java.sql.Date(new Date().toInstant().toEpochMilli()));
		}
				
		return getRepository().save(entity);
	}

	@SuppressWarnings("unchecked")
	default T save(IDataTransferObject dto) {	
		if (dto == null)
			throw new NoSuchEntityException("Entity can't be null");
		
		T entity = (T) dto.toEntity();
		entity.setUpdatedAt(new java.sql.Date(new Date().toInstant().toEpochMilli()));
		
		if (entity.getId() != null) {
			Optional<T> temp = getRepository().findById(entity.getId());
			
			if (temp.isPresent())
				entity = update(temp.get(), entity);
			else
				entity.setCreatedAt(new java.sql.Date(new Date().toInstant().toEpochMilli()));
		} else {
			entity.setCreatedAt(new java.sql.Date(new Date().toInstant().toEpochMilli()));
		}
		
		return getRepository().save(entity);
	}

	default void delete(Long id) {
		try {
			getRepository().deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	default T update(T oldEntity, T newEntity) {
		for (Field field : newEntity.getClass().getDeclaredFields()) {
			if (!field.getName().equals("id") && !field.getName().equals("createdAt")) {
				try {
					Object oldValue = field.get(oldEntity);
					Object newValue = field.get(newEntity);
	
					if (!newValue.equals(oldValue))
						field.set(oldEntity, newValue);
				} catch (IllegalArgumentException e) {
					// TODO
				} catch (IllegalAccessException e) {
					// TODO
				} catch (SecurityException e) {
					// TODO
				}
			}
		}
		
		return oldEntity;
	}
}
