package com.vili.mrmentoria.engine.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.exceptions.custom.ResourceNotFoundException;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.request.Request;

public interface SearchService<T extends IEntity> {

	IRepository<T> getRepository();
	
	default List<T> findAll() {
		return getRepository().findAll();
	}

	default T findById(Long id) {
		Optional<T> entity = getRepository().findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	default Page<T> findPage(Request request) {
		Specification<T> spec = request.getSpecification();

		if (spec == null)
			return getRepository().findAll(request.getPageRequest());
		
		return getRepository().findAll(spec, request.getPageRequest());
	}
}
