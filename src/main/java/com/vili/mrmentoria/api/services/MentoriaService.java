package com.vili.mrmentoria.api.services;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.api.repositories.MentoriaRepository;
import com.vili.mrmentoria.api.security.UserSS;
import com.vili.mrmentoria.engine.exceptions.custom.AuthorizationException;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.request.Request;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class MentoriaService implements IService<Mentoria> {

	@Autowired
	private MentoriaRepository mentoriaRepository;
	
	@Override
	public IRepository<Mentoria> getRepository() {
		return mentoriaRepository;
	}

	@Override
	public Page<Mentoria> findPage(Request request) {
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Access denied");
		}
		
		try {
			Field field = null; 
			
			if (user.hasRole(PerfilPessoa.MENTOR))
				field = Mentoria.class.getDeclaredField("mentor");
			else if (user.hasRole(PerfilPessoa.MENTORADO))
				field = Mentoria.class.getDeclaredField("mentorados");
			
			if (field != null) {
				String[] id = { user.getId().toString() };
				String[] nesting = { "id" };
				request.buildFilter(field, id, nesting);
			}
		} catch (NoSuchFieldException | SecurityException e) {
			return null;
		}
		
		return IService.super.findPage(request);
	}
}
