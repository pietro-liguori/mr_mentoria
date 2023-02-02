package com.vili.mrmentoria.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.PagamentoAVista;
import com.vili.mrmentoria.api.repositories.PagamentoAVistaRepository;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class PagamentoAVistaService implements IService<PagamentoAVista> {

	@Autowired
	private PagamentoAVistaRepository pagamentoAVistaRepository;
	
	@Override
	public IRepository<PagamentoAVista> getRepository() {
		return pagamentoAVistaRepository;
	}
}
