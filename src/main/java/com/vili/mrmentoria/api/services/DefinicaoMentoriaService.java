package com.vili.mrmentoria.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.DefinicaoMentoria;
import com.vili.mrmentoria.api.repositories.DefinicaoMentoriaRepository;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class DefinicaoMentoriaService implements IService<DefinicaoMentoria> {

	@Autowired
	private DefinicaoMentoriaRepository definicaoRepository;
	
	@Override
	public IRepository<DefinicaoMentoria> getRepository() {
		return definicaoRepository;
	}
}
