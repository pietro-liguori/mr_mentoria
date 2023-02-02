package com.vili.mrmentoria.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Pagamento;
import com.vili.mrmentoria.api.repositories.PagamentoRepository;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class PagamentoService implements IService<Pagamento> {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Override
	public IRepository<Pagamento> getRepository() {
		return pagamentoRepository;
	}
}
