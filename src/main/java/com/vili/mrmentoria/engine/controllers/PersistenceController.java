package com.vili.mrmentoria.engine.controllers;

import java.net.URI;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vili.mrmentoria.engine.IDataTransferObject;
import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.exceptions.custom.InvalidRequestParamException;
import com.vili.mrmentoria.engine.services.IService;

public interface PersistenceController<E extends IEntity, T extends IDataTransferObject> {

	IService<E> getService();
	
	default ResponseEntity<E> insert(T dto) {
		E obj = getService().save(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	default	ResponseEntity<Void> delete(Object id) {
		Long aux = null;

		try {
			aux = Long.parseLong((String) id);
			
			if (aux < 1) 
				throw new InvalidRequestParamException(Arrays.asList(new FieldMessage("id", "Path variable must be a positive whole number")));
		} catch (NumberFormatException e) {
			throw new InvalidRequestParamException(Arrays.asList(new FieldMessage("id", "Path variable must be a positive whole number")));
		}

		getService().delete(aux);
		return ResponseEntity.noContent().build();
	}

	default ResponseEntity<E> update(T dto, Object id) {
		dto.setId((Long) id);
		E obj = getService().save(dto);
		return ResponseEntity.ok().body(obj);
	}
}
