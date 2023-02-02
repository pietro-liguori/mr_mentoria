package com.vili.mrmentoria.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Desconto;
import com.vili.mrmentoria.api.repositories.DescontoRepository;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class DescontoService implements IService<Desconto> {

	@Autowired
	private DescontoRepository descontoRepository;
	
	@Override
	public IRepository<Desconto> getRepository() {
		return descontoRepository;
	}
}
