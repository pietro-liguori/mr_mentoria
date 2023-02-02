package com.vili.mrmentoria.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Reuniao;
import com.vili.mrmentoria.api.repositories.ReuniaoRepository;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class ReuniaoService implements IService<Reuniao> {

	@Autowired
	private ReuniaoRepository reuniaoRepository;
	
	@Override
	public IRepository<Reuniao> getRepository() {
		return reuniaoRepository;
	}
}
