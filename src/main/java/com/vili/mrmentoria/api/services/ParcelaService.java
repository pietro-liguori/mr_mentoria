package com.vili.mrmentoria.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Parcela;
import com.vili.mrmentoria.api.repositories.ParcelaRepository;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class ParcelaService implements IService<Parcela> {

	@Autowired
	private ParcelaRepository parcelaRepository;
	
	@Override
	public IRepository<Parcela> getRepository() {
		return parcelaRepository;
	}
}
