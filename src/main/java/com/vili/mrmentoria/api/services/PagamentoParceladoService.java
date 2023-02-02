package com.vili.mrmentoria.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.PagamentoParcelado;
import com.vili.mrmentoria.api.repositories.PagamentoParceladoRepository;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class PagamentoParceladoService implements IService<PagamentoParcelado> {

	@Autowired
	private PagamentoParceladoRepository pagamentoParceladoRepository;
	
	@Override
	public IRepository<PagamentoParcelado> getRepository() {
		return pagamentoParceladoRepository;
	}
}
