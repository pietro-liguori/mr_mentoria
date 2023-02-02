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

import com.vili.mrmentoria.api.domain.Reuniao;
import com.vili.mrmentoria.api.domain.dto.ReuniaoDTO;
import com.vili.mrmentoria.api.services.ReuniaoService;
import com.vili.mrmentoria.engine.controllers.IController;
import com.vili.mrmentoria.engine.services.IService;

@RestController
@RequestMapping(value = "/reunioes")
public class ReuniaoController implements IController<Reuniao, ReuniaoDTO> {

	@Autowired
	private ReuniaoService reuniaoService;
	
	@Override
	public IService<Reuniao> getService() {
		return reuniaoService;
	}

	@Override
	public Class<Reuniao> getEntityType() {
		return Reuniao.class;
	}
	
	@Override
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Reuniao> findById(Object id) {
		return IController.super.findById(id);
	}
	
	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<List<Reuniao>> findAll() {
		return IController.super.findAll();
	}
	
	@Override
	@GetMapping("/page/{page}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Page<Reuniao>> findPage(HttpServletRequest request) {
		return IController.super.findPage(request);
	}
	
	@Override
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Reuniao> insert(ReuniaoDTO dto) {
		return IController.super.insert(dto);
	}
	
	@Override
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Reuniao> update(ReuniaoDTO dto, Object id) {
		return IController.super.update(dto, id);
	}
	
	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Void> delete(Object id) {
		return IController.super.delete(id);
	}
}
