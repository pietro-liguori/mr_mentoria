package com.vili.mrmentoria.engine.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.exceptions.custom.InvalidRequestParamException;
import com.vili.mrmentoria.engine.request.Request;
import com.vili.mrmentoria.engine.services.IService;

public interface SearchController<E extends IEntity> {

	IService<E> getService();
	
	Class<E> getEntityType();
	
	default ResponseEntity<List<E>> findAll() {
		List<E> list = getService().findAll();
		return ResponseEntity.ok().body(list);
	}

	default ResponseEntity<E> findById(Object id) {
		Long aux = null;

		try {
			aux = Long.parseLong((String) id);
			
			if (aux < 1) 
				throw new InvalidRequestParamException(Arrays.asList(new FieldMessage("id", "Path variable must be a positive whole number")));
		} catch (NumberFormatException e) {
			if (((String) id).equals(Request.PAGE_ATTRIBUTE))
				throw new InvalidRequestParamException(Arrays.asList(new FieldMessage(Request.PAGE_ATTRIBUTE, "Must inform a page number")));
			else 
				throw new InvalidRequestParamException(Arrays.asList(new FieldMessage("id", "Path variable must be a positive whole number")));
 		}
		
		E obj = (E) getService().findById(aux);
		return ResponseEntity.ok().body(obj);
	}
	
	default ResponseEntity<Page<E>> findPage(HttpServletRequest request) {
		Page<E> obj = getService().findPage(new Request(request, getEntityType()));
		return ResponseEntity.ok().body(obj);
	}
}
