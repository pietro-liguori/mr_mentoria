package com.vili.mrmentoria.api.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vili.mrmentoria.api.domain.DefinicaoMentoria;
import com.vili.mrmentoria.api.domain.dto.DefinicaoMentoriaDTO;
import com.vili.mrmentoria.api.services.DefinicaoMentoriaService;
import com.vili.mrmentoria.engine.controllers.IController;
import com.vili.mrmentoria.engine.services.IService;

@RestController
@RequestMapping(value = "/definicoes")
public class DefinicaoMentoriaController implements IController<DefinicaoMentoria, DefinicaoMentoriaDTO> {

	@Autowired
	private DefinicaoMentoriaService definicaoService;
	
	@Override
	public IService<DefinicaoMentoria> getService() {
		return definicaoService;
	}

	@Override
	public Class<DefinicaoMentoria> getEntityType() {
		return DefinicaoMentoria.class;
	}
	
	@Override
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<DefinicaoMentoria> findById(Object id) {
		return IController.super.findById(id);
	}
	
	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<List<DefinicaoMentoria>> findAll() {
		return IController.super.findAll();
	}
	
	@Override
	@GetMapping("/page/{page}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Page<DefinicaoMentoria>> findPage(HttpServletRequest request) {
		return IController.super.findPage(request);
	}
	
	@Override
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<DefinicaoMentoria> insert(DefinicaoMentoriaDTO dto) {
		return IController.super.insert(dto);
	}
	
	@Override
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<DefinicaoMentoria> update(DefinicaoMentoriaDTO dto, Object id) {
		return IController.super.update(dto, id);
	}
	
	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Void> delete(Object id) {
		return IController.super.delete(id);
	}
}
