package com.vili.mrmentoria.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Custo;
import com.vili.mrmentoria.api.repositories.CustoRepository;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class CustoService implements IService<Custo> {

	@Autowired
	private CustoRepository custoRepository;

	@Override
	public IRepository<Custo> getRepository() {
		return custoRepository;
	}	
}
